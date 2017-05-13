package com.ehighsun.wxtp.filter;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Competition;
import com.ehighsun.wxtp.pojo.User;

public class LotteryFilter implements Filter{

	private Competition competition;
	
	private static String conf = "applicationContext.xml";
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext(conf);
	
	private BaseDao<Competition> competitionDao = (BaseDao<Competition>) ctx.getBean("baseDao");

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
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
	       
	        User myUser = user;
	        if (!myUser.getPollStatus().equals("1")) {
	        	httpServletResponse.sendRedirect("/wxtp/L/tpm.html"); 
        		return;
			}
	        if (myUser.getIsSharePollTeamMessage()!=1) {
	        	httpServletResponse.sendRedirect("/wxtp/L/fxm.html"); 
        		return;
			}
			if (!JudgeCompetition()){
	        	httpServletResponse.sendRedirect("/wxtp/L/js.html"); 
        		return;
			}
        
        }
        chain.doFilter(request, response);  
		
	}
	
	private boolean JudgeCompetition() {
	    List<Competition> competitions = competitionDao.find("From Competition where status = 1");
		
	    competition = competitions.get(0);
	    System.out.println("now:"+System.currentTimeMillis()+",guiding:"+competition.getEndTime().getTime());
	    if(System.currentTimeMillis() <= competition.getEndTime().getTime()) return true;
	    
	    return false;
	}	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
