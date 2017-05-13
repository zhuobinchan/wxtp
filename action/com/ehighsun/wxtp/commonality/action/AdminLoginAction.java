package com.ehighsun.wxtp.commonality.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.pojo.Administrator;
import com.ehighsun.wxtp.service.BaseService;
import com.ehighsun.wxtp.util.StringUtil;

public class AdminLoginAction {

	private String account;
	private String password;
	private String errorMessage;
	
	@Resource(name="baseService")
	private BaseService baseService;
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	
	public String Login(){
		
		password = StringUtil.md5(password);
		
		List<Administrator> admins = (List<Administrator>) baseService.find("From Administrator where account=? and password =?",new Object[]{account,password});
		
		Administrator admin = null;
		
		if(admins!=null && admins.size()>0) admin = admins.get(0);
		
		if(admin!=null){
			
			request.getSession().setAttribute("admin", admin);
			
			return "success";
		}else {
			
			errorMessage = "账号或密码不正确，请重新输入。";
			return "input";
		}
		
		
	}
	
	
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
