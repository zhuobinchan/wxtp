package com.ehighsun.wxtp.commonality.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Award;
import com.ehighsun.wxtp.pojo.AwardList;
import com.ehighsun.wxtp.pojo.User;
import com.ehighsun.wxtp.util.PropertiesUtil;
import com.opensymphony.xwork2.ActionSupport;

public class LotteryAction extends ActionSupport {

	private HashMap<String, Object> json = new HashMap<>();
	
	@Resource(name="baseDao")
	private BaseDao<AwardList> baseDao;
	
	@Resource(name="baseDao")
	private BaseDao<User> userDao;
	
	@Resource(name="baseDao")
	private BaseDao<Award> awardDao;
	
	private Integer cjTime;//抽奖次数。
	private Integer gailv;//概率事件的基数。
	private Integer hjcs;//可获奖次数，超过这个次数就一直不可以获奖。
	
	public LotteryAction() throws IOException {
		
		String propertiesPath = ServletActionContext.getRequest().getRealPath("/WEB-INF/classes/gz.properties");
		
		cjTime = Integer.parseInt(PropertiesUtil.getProperites(propertiesPath).getProperty("cjTime"));
		gailv = Integer.parseInt(PropertiesUtil.getProperites(propertiesPath).getProperty("gailv"));
		hjcs = Integer.parseInt(PropertiesUtil.getProperites(propertiesPath).getProperty("hjcs"));
		
	}

	public String drawA() {

		
		User user = (User) ServletActionContext.getContext().getSession().get("user");
		
		if(!user.getPollStatus().equals("1")) return "noPoll";
		
		if(user.getIsSharePollTeamMessage()!=1) return "noShare";
		
//		if( user==null && (user.getName().trim().equals("")) ) return "noInfo";
		
		/*判断抽奖次数，是否超过2次start*/
		if( user.getAwardTime()>=cjTime){
			System.out.println("user.getAwardTime():"+user.getAwardTime());
			json.put("message", "true"); 
			json.put("name", "noTimes");
			json.put("num", 0);
			return "cantLottery";
		}
		
		/*抽奖次数没超过2，则继续抽奖，数据库+1*/
		System.out.println("user.getAwardTime():"+user.getAwardTime());
		userDao.executeSql("update user set awardTime = "+(user.getAwardTime()+1)+" where uid="+user.getUid());
		user.setAwardTime(user.getAwardTime()+1);
		ServletActionContext.getContext().getSession().put("user", user);
		/*判断抽奖次数，是否超过2次end*/
		
		/*记录奖品名，用于传回前端显示*/
		String awardName ="none";
		Integer lotteryNum = null;
		
		
		/*生成概率的随机数,10000是随机概率里的基数,这个随机不是转盘的角度数，而是代表中奖奖品的随机数*/
		Integer x_num = 1 + (int) (Math.random() * gailv);

		if(user.getIsWinAward() < hjcs ){
			/*获取奖品概率区间，随机一个数，判断获取奖品*/
			/*判断奖品数是否获奖并且奖品数是否为0，若都满足则返回奖品信息 */		
			List<Award> awardList = awardDao.find("From Award where number > 0 and "+x_num+" between startSection and endSection");
			
			/* 1、判断数据库是否有奖品
			 * 2、判断今天送出数是否大于设定日送出数
			 * 3、中奖区域isNoting是否为谢谢参与，谢谢参与的值为1，奖品为0
			 * */
			if(awardList!=null && awardList.size()!=0 && awardList.get(0).getTodaySendOutNumber()<awardList.get(0).getReleaseNumber() && awardList.get(0).getIsNothing()==0){
				
				/*start_point区域开始角度,point区域角度大小，例如1/4半圆是，point为90*/
				Integer start_point = awardList.get(0).getStartDegrees();//区域开始角度
				Integer point = awardList.get(0).getEndDegrees()-awardList.get(0).getStartDegrees();//区域角度大小，例如1/4半圆是，point为90
				/*谢谢奖品区域随机一个角度,+10和-20作用是划分清晰边界,后边界偏移量要是前边界的2倍*/
				lotteryNum = (start_point+10) + (int) (Math.random() * (point-20));
				
				/*获奖奖品品*/
				awardName = awardList.get(0).getName();
				/*若获奖，数据库中奖品数量相应减1*/
				Integer awardNum = awardList.get(0).getNumber() - 1;
				/*若获奖，数据库中今日送出数量加1*/
				Integer todaySendOutNumber = awardList.get(0).getTodaySendOutNumber() + 1;
				
				awardList.get(0).setTodaySendOutNumber(todaySendOutNumber);
				awardList.get(0).setNumber(awardNum);
				
				awardDao.merge(awardList.get(0));
				
			}else lotteryNum = noZhongjiang();
			
		}else lotteryNum = noZhongjiang();
		
		
		
		System.out.println("x_num:"+x_num+",lotteryNum:"+lotteryNum+",award:"+awardName);
		
		/*中奖了则需要保存记录到数据库*/
		if(!awardName.equals("none")){
			
			String user_name = user.getName();
			String user_telephone = user.getTelephone();
			Timestamp awardTime = new Timestamp(System.currentTimeMillis());
			String user_openId = user.getOpenId();
			baseDao.executeSql("insert into awardlist (name , telephone , awardName ,awardTime,openId,isGetAward) values('"+user_name+"','"+user_telephone+"','"+awardName+"','"+awardTime+"','"+user_openId+"',0)");
			user.setIsWinAward(1);
			userDao.executeSql("update user set isWinAward = isWinAward+1 where uid="+user.getUid());
			ServletActionContext.getContext().getSession().put("user", user);			
		}
		
		if (lotteryNum!=null) {
			json.put("message", "true");
			json.put("num", lotteryNum);
			json.put("name",awardName);
		}else {
			System.out.println("在LotteryAction:lotteryNum最终的值为空,查询不到谢谢参与区域：message返回false");
			json.put("message", "false");
		}
		
		return "success";
	}
	
	/*若没有中奖，则在谢谢参与区域随机一个角度*/
	public Integer noZhongjiang(){
		List<Award> awardList = awardDao.find("From Award where isNothing = 1");
		
		if(awardList!=null){
			/*随机一个谢谢参与区域*/
			Integer suiji_xiexie = 0 + (int) (Math.random() * (awardList.size()-1));
			/*谢谢参与区域随机一个角度*/ /*start_point区域开始角度,point区域角度大小，例如1/4半圆是，point为90*/
			Integer start_point = awardList.get(suiji_xiexie).getStartDegrees();
			Integer point = awardList.get(suiji_xiexie).getEndDegrees()-awardList.get(suiji_xiexie).getStartDegrees();
			/*+10和-20主要为了让他不落在边界上,后边界偏移量要是前边界的2倍*/
			Integer lotteryNum = (start_point+10) + (int) (Math.random() * (point-20));
			System.out.println("在LotteryAction:lotteryNum不中奖生成的值为:"+lotteryNum);
			return lotteryNum;
		}

		json.put("message", "false");
		System.out.println("在LotteryAction:noZhongjiang(),查询不到谢谢参与区域：message返回false");
		return 0;
	}

	public HashMap<String, Object> getJson() {
		return json;
	}

	public void setJson(HashMap<String, Object> json) {
		this.json = json;
	}

	public Integer getCjTime() {
		return cjTime;
	}

	public Integer getGailv() {
		return gailv;
	}

	public void setCjTime(Integer cjTime) {
		this.cjTime = cjTime;
	}

	public void setGailv(Integer gailv) {
		this.gailv = gailv;
	}

}
