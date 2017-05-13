package com.ehighsun.wxtp.pojo;

import java.sql.Timestamp;

public class Suishoupai {
	
	private Integer id;
	private String zpName;
	private String userName;
	private String userTel;
	private String imgUrl;
	private Timestamp createTime;
	private String status;
	private String mediaId;
	private Integer poll;
	private String openId;
	private String nickName;
	private String noId;//镜像id，由主键生成时同时生产
	
	public Integer getPoll() {
		return poll;
	}
	public void setPoll(Integer poll) {
		this.poll = poll;
	}
	public Integer getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserTel() {
		return userTel;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNoId() {
		return noId;
	}
	public void setNoId(String noId) {
		this.noId = noId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getZpName() {
		return zpName;
	}
	public void setZpName(String zpName) {
		this.zpName = zpName;
	}
}
