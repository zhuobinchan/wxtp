package com.ehighsun.wxtp.weixin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.ehighsun.wxtp.weixin.pojo.JS_SDK;
import com.ehighsun.wxtp.weixin.util.TokenThread;
import com.ehighsun.wxtp.weixin.util.WeixinUtil;
import com.opensymphony.xwork2.ActionSupport;

public class GradeJsSdkAction extends ActionSupport {
	private String url;
	private JS_SDK jsSdk = new JS_SDK();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JS_SDK getJsSdk() {
		return jsSdk;
	}

	public void setJsSdk(JS_SDK jsSdk) {
		this.jsSdk = jsSdk;
	}

	public String gradeJsSdk() throws IOException {
		long time = System.currentTimeMillis() / 1000;
		String randomStr = UUID.randomUUID().toString();

		String accerssToken = TokenThread.token.getAccessToken();
		String jsApiTicket = TokenThread.jsapi_ticket;

		String content = "jsapi_ticket=" + jsApiTicket + "&" + "noncestr="
				+ randomStr + "&" + "timestamp=" + time + "&" + "url=" + url;

		String signature = WeixinUtil.sha1Encrypt(content);

		System.out.println(signature);

		jsSdk = new JS_SDK();
		jsSdk.setAccerssToken(accerssToken);
		jsSdk.setJsApiTicket(jsApiTicket);
		jsSdk.setNonceStr(randomStr);
		jsSdk.setSignature(signature);
		jsSdk.setTime(time);

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String callback = request.getParameter("callback");
		jsSdk.setUrl(url);
		String result = JSON.toJSONString(jsSdk);
		result = callback + "(" + result + ")";

		PrintWriter out = response.getWriter();
		out.print(result);
		return null;
	}
}
