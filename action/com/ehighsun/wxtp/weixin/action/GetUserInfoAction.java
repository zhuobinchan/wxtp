package com.ehighsun.wxtp.weixin.action;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Competition;
import com.ehighsun.wxtp.pojo.User;
import com.ehighsun.wxtp.util.EmojiFilterUtil;
import com.ehighsun.wxtp.util.StringUtil;
import com.ehighsun.wxtp.weixin.pojo.SNSUserInfo;
import com.ehighsun.wxtp.weixin.pojo.Token;
import com.ehighsun.wxtp.weixin.pojo.WeixinOauth2Token;
import com.ehighsun.wxtp.weixin.pojo.WeixinUserInfo;
import com.ehighsun.wxtp.weixin.util.TokenThread;
import com.ehighsun.wxtp.weixin.util.WeixinUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetUserInfoAction extends ActionSupport {
	private String code;
	private String state;
	private String targetUrl;
	@Resource(name = "baseDao")
	private BaseDao<User> userBaseDao;
	
	Map<String, Object> session = ServletActionContext.getContext().getSession();
	
//	 海印微办公
//	String appid = "wxc7eee0d7cb088852";
//	String appsecret = "3c77e1eacc078c66edbaa4e9026efced";

	// 海印微办公
	
	private static String appid;
	
	private static String appsecret;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
	public String getAppid() {
		return appid;
	}
	@Value("${weixin.appid}")
	public void setAppid(String appid) {
		GetUserInfoAction.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}
	@Value("${weixin.appsercet}")
	public void setAppsecret(String appsecret) {
		GetUserInfoAction.appsecret = appsecret;
	}

	public String getUserInfo() {

		 System.out.println("targetUrl,getuserinfo:"+targetUrl);
		
		try {
			if (StringUtil.isNotEmpty(code)) {
				WeixinOauth2Token token = WeixinUtil.getOauth2Token(appid,
						appsecret, code);
				SNSUserInfo snsUserInfo = WeixinUtil.getSNSUserInfo(
						token.getAccessToken(), token.getOpenId());
				System.out.println("openId:"+snsUserInfo.getOpenId());
				WeixinUserInfo weixinUserInfo = WeixinUtil.getWeixinUserInfo(snsUserInfo.getOpenId());
//				System.out.println("weixinUserSubscribeInfo:"+weixinUserInfo.getSubscribe());
				System.out.println(JSON.toJSONString(weixinUserInfo,true));
				if (weixinUserInfo!=null&&weixinUserInfo.getSubscribe()==1) {//是否正确授权
					List<User> userList = userBaseDao.find("from User where openId = ?",new Object[] { weixinUserInfo.getOpenId() });
					User user = userList.size() != 0 ? userList.get(0) : new User();
					user.setCity(weixinUserInfo.getCity());
					user.setCountry(weixinUserInfo.getCountry());
					user.setHeadImgUrl(weixinUserInfo.getHeadImgUrl());
					user.setNickname(EmojiFilterUtil.filterEmoji(weixinUserInfo.getNickname()));
					user.setOpenId(weixinUserInfo.getOpenId());
					user.setProvince(weixinUserInfo.getProvince());
					user.setSex(weixinUserInfo.getSex());
					user.setSubscribe(weixinUserInfo.getSubscribe());
					user.setSubscribeTime(weixinUserInfo.getSubscribeTime());
					user.setPollStatus(user.getPollStatus()==null?"0":user.getPollStatus());//如果为空设为0
					user.setAwardTime(user.getAwardTime()==null?0:user.getAwardTime());//如果为空设为0
					user.setIsSharePollTeamMessage(user.getIsSharePollTeamMessage()==null?0:user.getIsSharePollTeamMessage());//如果为空设为0
					user.setIsWinAward(user.getIsWinAward()==null?0:user.getIsWinAward());//如果为空设为0
					
					user.setIsUploadPhotoShuiShouPai(user.getIsUploadPhotoShuiShouPai()==null?0:user.getIsUploadPhotoShuiShouPai());//如果为空设为0
					user.setPollStatusShuiShouPai(user.getPollStatusShuiShouPai()==null?0:user.getPollStatusShuiShouPai());//如果为空设为0
					
					user.setName(user.getName()==null?"":user.getName());
					user.setTelephone(user.getTelephone()==null?"":user.getTelephone());
					userBaseDao.saveOrUpdate(user);
					session.put("user", user);
				}else {
					return "attentionGongZhongHao";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("授权error:" + e.toString());
			return ERROR;
		}
		return SUCCESS;

	}
			
	

}
