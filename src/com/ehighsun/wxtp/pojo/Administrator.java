package com.ehighsun.wxtp.pojo;

public class Administrator {

	private Integer aid;
	private String account;
	private String password;
	private String name;
	private String telephone;
	
	public String getAccount() {
		return account;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
}
