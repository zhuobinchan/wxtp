package com.ehighsun.wxtp.weixin.pojo;

public class JS_SDK {
	private long time;
	private String nonceStr;
	private String signature;
	private String accerssToken;
	private String jsApiTicket;
	private String url;

	public JS_SDK() {

	}

	public static void main(String[] args) {

	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAccerssToken() {
		return accerssToken;
	}

	public void setAccerssToken(String accerssToken) {
		this.accerssToken = accerssToken;
	}

	public String getJsApiTicket() {
		return jsApiTicket;
	}

	public void setJsApiTicket(String jsApiTicket) {
		this.jsApiTicket = jsApiTicket;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
