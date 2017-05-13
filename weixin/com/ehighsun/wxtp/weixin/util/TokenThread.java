package com.ehighsun.wxtp.weixin.util;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ehighsun.wxtp.weixin.pojo.Token;

public class TokenThread implements Runnable {

//	 // 海印微办公
//	 String appid = "wxc7eee0d7cb088852";
//	 String appsecret = "3c77e1eacc078c66edbaa4e9026efced";
	// 珠海又一城
	public static String appid=null;
	
	public static String appsecret=null;

	public static Token token = null;
	public static String jsapi_ticket = null;

	public void run() {
		while (true) {
			try {
				token = WeixinUtil.getToken(appid, appsecret);
				// 获取JSAPI_Ticket
				jsapi_ticket = WeixinUtil.JSApiTIcket(token.getAccessToken());

				if (null != token) {
					System.out.println("当前系统时间："+Calendar.getInstance().getTime());
					System.out.println("TokenThread线程里的appid："+appid);
					System.out.println("TokenThread线程里的appsecret："+appsecret);
					System.out.println("获取access_token成功，有效时长："
							+ token.getExpiresIn() + "accessToken:"
							+ token.getAccessToken());
					System.out.println("获取jsapi_ticket成功， jsapi_ticket:"
							+ jsapi_ticket);
					// 休眠7000秒
					Thread.sleep((token.getExpiresIn() - 200)* 1000);
				} else {
					// 如果access_token未null，60秒后在获取
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					System.out.println(e1.toString());
				}
				System.out.println(e.toString());
			}
		}
	}

	public TokenThread() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println(TokenThread.token.getAccessToken());
	}

	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		TokenThread.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		TokenThread.appsecret = appsecret;
	}

	public static Token getToken() {
		return token;
	}

	public static void setToken(Token token) {
		TokenThread.token = token;
	}

	public static String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public static void setJsapi_ticket(String jsapi_ticket) {
		TokenThread.jsapi_ticket = jsapi_ticket;
	}

}
