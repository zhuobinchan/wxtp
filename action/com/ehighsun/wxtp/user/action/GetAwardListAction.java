package com.ehighsun.wxtp.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.AwardList;
import com.ehighsun.wxtp.pojo.OtherAwardList;
import com.ehighsun.wxtp.pojo.OtherAwardList2;
import com.ehighsun.wxtp.pojo.User;
import com.opensymphony.xwork2.ActionSupport;

public class GetAwardListAction extends ActionSupport {
	private Map<String, Object> json = new HashMap<>();
	@Resource(name="baseDao")
	private BaseDao<AwardList> awardListDao;
	@Resource(name="baseDao")
	private BaseDao<OtherAwardList> otherAwardListDao;
	@Resource(name="baseDao")
	private BaseDao<OtherAwardList2> otherAwardList2Dao;
	
	private String name;
	private String telephone;
	
	private String dtString;
	
	public String getAwardListByOpenId() {
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");
		if (user.getIsWinAward()!=1) {
			json.put("message", "false");
			json.put("status", "noWinAward");
		}else {
			List<AwardList> awardLists = awardListDao.find("From AwardList where openId = ?",new Object[]{user.getOpenId()});
			
			if (awardLists!=null) {
				json.put("message", "true");
				json.put("awardList", awardLists);
			}else {
				json.put("message", "false");
				json.put("status", "noWinAward");
			}
		}
		return "ReturnAjax";
	}
	
	public String getOtherAwardListByOpenId() {
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");

		List<OtherAwardList> otherAwardLists = otherAwardListDao.find("From OtherAwardList where openId = ?",new Object[]{user.getOpenId()});
			
		if (otherAwardLists!=null) {
			json.put("message", "true");
			json.put("awardList", otherAwardLists);
		}else {
			json.put("message", "false");
			json.put("status", "noWinAward");
		}
			
		return "ReturnAjax";
	}
	
	public String confirmGetAward(){
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");
		if (user.getIsWinAward()!=1) {
			json.put("message", "false");
			json.put("status", "noWinAward");
		}else {
			
			List<AwardList> awardLists = awardListDao.find("From AwardList where openId = ?",new Object[]{user.getOpenId()});
			if(awardLists.get(0).getIsGetAward()==1) {
				json.put("message", "true");
				json.put("status", "isGet");
			}else if(awardLists.get(0).getIsGetAward()==0){

				if(awardLists.get(0).getTelephone().equals("") || awardLists.get(0).getName().equals("") ){
					json.put("message", "false");
					json.put("status", "noInfo");	
					return "ReturnAjax";
				}
				
				Integer status = awardListDao.executeSql("Update awardlist set isGetAward=1 where openId = '"+user.getOpenId()+"'");
				if (status!=null && status!=0) {
					json.put("message", "true");
					json.put("status", "isGetSuccess");
				}else {
					json.put("message", "false");
					json.put("status", "isGetFail");
				}
			}
			

			

		}
		
		return "ReturnAjax";
	}
	
	public String confirmGetOtherAward(){
		User user = (User) ServletActionContext.getContext().getSession().get("user");
			
			List<AwardList> awardLists = awardListDao.find("From OtherAwardList where openId = ?",new Object[]{user.getOpenId()});
			if(awardLists.get(0).getIsGetAward()==1) {
				json.put("message", "true");
				json.put("status", "isGet");
			}else if(awardLists.get(0).getIsGetAward()==0){

				Integer status = awardListDao.executeSql("Update otherawardlist set isGetAward=1 where openId = '"+user.getOpenId()+"'");
				if (status!=null && status!=0) {
					json.put("message", "true");
					json.put("status", "isGetSuccess");
				}else {
					json.put("message", "false");
					json.put("status", "isGetFail");
				}
			}
		return "ReturnAjax";
	}
	
	public String GetOtherAward2(){
		
		List<OtherAwardList2> otherAwardList2 = otherAwardList2Dao.find("From OtherAwardList2 where telephone =? or name=?",new Object[]{dtString,dtString});
		
		if (otherAwardList2!=null && otherAwardList2.size()!=0) {
			
			for(int i=0;i<otherAwardList2.size();i++){
				
				String telStr = otherAwardList2.get(i).getTelephone();
				String zyStr = telStr.substring(3, 7);
				otherAwardList2.get(i).setTelephone(telStr.replace(zyStr, "*****"));
				
			}
			
			json.put("message", "true");
			json.put("awardList", otherAwardList2);
		}else {
			json.put("message", "false");
			json.put("status", "noWinAward");
		}
			
			
		return "ReturnAjax";
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDtString() {
		return dtString;
	}

	public void setDtString(String dtString) {
		this.dtString = dtString;
	}
}
