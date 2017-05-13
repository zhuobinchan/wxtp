package com.ehighsun.wxtp.interceptor;

import java.util.Map;

import com.ehighsun.wxtp.pojo.Administrator;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class AdminLoginInterceptor extends MethodFilterInterceptor  {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		Map<String, Object> session = invocation.getInvocationContext().getSession();
		
		Administrator admin = (Administrator) session.get("admin");
		
		if(admin==null){
			
			return "adminNoLogin";
		}
		
		return invocation.invoke();
	}

}
