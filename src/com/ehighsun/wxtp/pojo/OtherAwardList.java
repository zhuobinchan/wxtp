package com.ehighsun.wxtp.pojo;

import java.sql.Timestamp;

public class OtherAwardList {
	private Integer id;
	private String name;
	private String telephone;
	private String awardName;
	private String openId;
	private String myUnionId;
	private Integer isGetAward;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getMyUnionId() {
		return myUnionId;
	}
	public void setMyUnionId(String myUnionId) {
		this.myUnionId = myUnionId;
	}
	public Integer getIsGetAward() {
		return isGetAward;
	}
	public void setIsGetAward(Integer isGetAward) {
		this.isGetAward = isGetAward;
	}
}
