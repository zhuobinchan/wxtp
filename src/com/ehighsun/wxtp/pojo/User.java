package com.ehighsun.wxtp.pojo;

import java.util.List;

public class User {
	
	private Integer uid;
	// 用户标识
	private String openId;
	// 用户昵称
	private String nickname;
	// 性别（1是男性，2是女性，0是未知）
	private Integer sex;
	// 国家
	private String country;
	// 省份
	private String province;
	// 城市
	private String city;
	// 用户头像链接
	private String headImgUrl;
	// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	private Integer subscribe;
	// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	private String subscribeTime;
	//狮队投票次数
	private String pollStatus;
	private String telephone;
	private String name;
	
	//每天抽奖次数，每天只能抽两次，每天12点把抽奖次数设置为0
	private Integer awardTime;
	
	//是否中过奖，中一次奖以后永远也不能中奖，0代表中过奖，1代表没有中过奖
	private Integer isWinAward;
	
	//是否有将投票页面分享到朋友圈，1代表有，0代表没有
	private Integer isSharePollTeamMessage;
	
	//随手拍是否已经上传图片
	private Integer isUploadPhotoShuiShouPai;
	//随手拍投票次数，每日投票5次
	private Integer pollStatusShuiShouPai;
	
	private List<PollTeamList> pollTeamLists;
	
	public String getOpenId() {
		return openId;
	}
	public String getNickname() {
		return nickname;
	}
	
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getSubscribe() {
		return subscribe;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getPollStatus() {
		return pollStatus;
	}
	public void setPollStatus(String pollStatus) {
		this.pollStatus = pollStatus;
	}
	public Integer getAwardTime() {
		return awardTime;
	}
	public void setAwardTime(Integer awardTime) {
		this.awardTime = awardTime;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getName() {
		return name;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PollTeamList> getPollTeamLists() {
		return pollTeamLists;
	}
	public void setPollTeamLists(List<PollTeamList> pollTeamLists) {
		this.pollTeamLists = pollTeamLists;
	}
	public Integer getIsSharePollTeamMessage() {
		return isSharePollTeamMessage;
	}
	public void setIsSharePollTeamMessage(Integer isSharePollTeamMessage) {
		this.isSharePollTeamMessage = isSharePollTeamMessage;
	}
	public Integer getIsWinAward() {
		return isWinAward;
	}
	public void setIsWinAward(Integer isWinAward) {
		this.isWinAward = isWinAward;
	}
	public Integer getIsUploadPhotoShuiShouPai() {
		return isUploadPhotoShuiShouPai;
	}
	public void setIsUploadPhotoShuiShouPai(Integer isUploadPhotoShuiShouPai) {
		this.isUploadPhotoShuiShouPai = isUploadPhotoShuiShouPai;
	}
	public Integer getPollStatusShuiShouPai() {
		return pollStatusShuiShouPai;
	}
	public void setPollStatusShuiShouPai(Integer pollStatusShuiShouPai) {
		this.pollStatusShuiShouPai = pollStatusShuiShouPai;
	}
	
	
}
