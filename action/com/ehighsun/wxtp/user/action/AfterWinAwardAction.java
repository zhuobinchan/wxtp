package com.ehighsun.wxtp.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.AwardList;
import com.ehighsun.wxtp.pojo.User;
import com.opensymphony.xwork2.ActionSupport;

public class AfterWinAwardAction extends ActionSupport {
	
	private String userName;
	private String userTel;
	private String targetUrl;
	private Map<String, Object> json = new HashMap<>();
	
	@Resource(name="baseDao")
	private BaseDao<AwardList> awardListDao;
	
	public String txxx(){
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");
		
//		if (user==null){
//			HttpServletRequest request = ServletActionContext.getRequest();
//			String reqPamrs = request.getQueryString();
//			String requestUrl = request.getRequestURI()+(reqPamrs==null?"":"?"+reqPamrs);
//			
//	        int position = requestUrl.indexOf("/", 2);
//	        
//	        requestUrl = requestUrl.substring(position, requestUrl.length());
//			
//			targetUrl = requestUrl;			
//			return "noLogin";
//		}
//		
		if (user.getIsWinAward()!=1) {
			json.put("message", "true");
			json.put("status", "noWinAward");
		}else {
			List<AwardList> awardLists = awardListDao.find("From AwardList where openId = ?",new Object[]{user.getOpenId()});
			
			if (awardLists!=null) {
				AwardList awardList = awardLists.get(0);
				if(awardList.getName()!=null && !awardList.getName().equals("") 
						&& awardList.getTelephone()!=null && !awardList.getTelephone().equals("")){
					
					json.put("message", "true");
					json.put("status", "hadInfo");
					
				}else {
					awardList.setTelephone(userTel);
					awardList.setName(userName);
					awardListDao.merge(awardList);
					
					json.put("message", "true");
					json.put("status", "txSuccess");
				}
				

			}
			
		}
		
		return "ReturnAjax";
	}
	
	public String getUserName() {
		return userName;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}

}
