package com.ehighsun.wxtp.pojo;

import java.sql.Timestamp;

public class AwardList {
	private Integer id;
	private String name;
	private String telephone;
	private String awardName;
	private Timestamp awardTime;
	private String openId;
	private Integer isGetAward;//0代表没有领取，代表已经领取奖品
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Timestamp getAwardTime() {
		return awardTime;
	}
	public void setAwardTime(Timestamp awardTime) {
		this.awardTime = awardTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getIsGetAward() {
		return isGetAward;
	}
	public void setIsGetAward(Integer isGetAward) {
		this.isGetAward = isGetAward;
	}
	
	
}
