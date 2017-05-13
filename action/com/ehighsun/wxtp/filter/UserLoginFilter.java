package com.ehighsun.wxtp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.pojo.User;
import com.ehighsun.wxtp.util.PropertiesUtil;

public class UserLoginFilter implements Filter{
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String appid = PropertiesUtil.getProperites(ServletActionContext.getRequest().getRealPath("/WEB-INF/classes/gz.properties")).getProperty("weixin.appid");
		String domainName = PropertiesUtil.getProperites(ServletActionContext.getRequest().getRealPath("/WEB-INF/classes/gz.properties")).getProperty("weixin.domainName");

		
		
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;  
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String url = httpServletRequest.getRequestURI();
        
        int position = url.indexOf("/", 2);
        
//        url = url.replace("/wxtp", "");
        
        url = url.substring(position, url.length());
        
        System.out.println("url:"+url);
        
        if(url.endsWith(".jsp")||url.endsWith(".html")){
        
	        User user = (User) session.getAttribute("user");
	        if(user == null){
	        		httpServletResponse.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+domainName+"%2fwxtp%2fGetUserInfoAction%3ftargetUrl="+url+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"); 
	        		return;
	        }
        
        }
        chain.doFilter(request, response);  
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
