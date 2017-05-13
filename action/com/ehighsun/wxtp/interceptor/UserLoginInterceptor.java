package com.ehighsun.wxtp.interceptor;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.ehighsun.wxtp.pojo.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class UserLoginInterceptor extends MethodFilterInterceptor  {

	private String targetUrl;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		
		User user = (User) session.get("user");
		
		ActionContext actionContext = invocation.getInvocationContext(); 
		HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response= (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		String reqPamrs = request.getQueryString();

		System.out.println("User:"+user);
		System.out.println("request请求："+request.getRequestURI()+",reqPamrs:"+reqPamrs);
		
		
		targetUrl = request.getRequestURI()+(reqPamrs==null?"":"?"+reqPamrs);
//		targetUrl = targetUrl.replace("&", "&");
		
		System.out.println("UserLoginInterceptor-targetUrl:"+targetUrl);
		
		
//		return "aaa";	
		
		if(user == null){
//			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc7eee0d7cb088852&redirect_uri=http%3a%2f%2fzhuobinchan.tunnel.2bdata.com%2fwxtp%2fGetUserInfoAction?targetUrl="+targetUrl+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
//			invocation.getStack().setValue("targetUrl", targetUrl);
			System.out.println("UserLoginInterceptor-targetUrl:"+targetUrl);
			return "userNoLogin";
		}else{
			return invocation.invoke();
		}
	}

	
	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

}
