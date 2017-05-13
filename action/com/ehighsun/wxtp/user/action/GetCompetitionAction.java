package com.ehighsun.wxtp.user.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.xmlbeans.impl.regex.REUtil;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Competition;
import com.ehighsun.wxtp.pojo.User;
import com.opensymphony.xwork2.ActionSupport;

public class GetCompetitionAction extends ActionSupport {

	private Competition competition;
	private String targetUrl;
	
	@Resource(name="baseDao")
	private BaseDao<Competition> competitionDao;

	public String GetCompetitionInfo(){
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");
		if(user==null){
			
			HttpServletRequest request = ServletActionContext.getRequest();
			String reqPamrs = request.getQueryString();
			String requestUrl = request.getRequestURI()+(reqPamrs==null?"":"?"+reqPamrs);
	        int position = requestUrl.indexOf("/", 2);
	        requestUrl = requestUrl.substring(position, requestUrl.length());
			targetUrl = requestUrl;
			return "userNoLogin";
		}
		
		List<Competition> competitions = null;
		competitions = competitionDao.find("From Competition where status='1'");
		
		if(competitions!=null && competitions.size()>0){
			competition = competitions.get(0);
		}
		
		return "GetCompetitionInfo";
	}
	
	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
}
