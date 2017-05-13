package com.ehighsun.wxtp.user.action;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.User;

public class ShareMessageAction {
	private HashMap<String, Object> json = new HashMap<>();
	@Resource(name = "baseDao")
	private BaseDao<User> baseDao;
	
	public String PollTeamToTimeline() {
		User user = (User) ServletActionContext.getContext().getSession().get("user");
		baseDao.executeSql("update user set isSharePollTeamMessage = 1 where openId = '"+user.getOpenId()+"'");
		json.put("message", "true");
		user.setIsSharePollTeamMessage(1);
		ServletActionContext.getContext().getSession().put("user", user);
		return "ReturnAjax";
	}

	public HashMap<String, Object> getJson() {
		return json;
	}

	public void setJson(HashMap<String, Object> json) {
		this.json = json;
	}
	
}
