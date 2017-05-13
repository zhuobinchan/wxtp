package com.ehighsun.wxtp.pojo;

public class Award {
	private Integer awardId;
	private String name;
	private Integer number;//剩余数量
	
	//标识是否为谢谢参与,1代表谢谢参与
	private Integer isNothing;
	
	
	//开始区间
	private Integer startSection;
	//结束区间
	private Integer endSection;
	//百分比：(方案一:根据每个奖品的百分比计算出所对应的开始区间和结束区间)
	//百分比：(方案二:通过循环，每次对应物品的奖项概率进行判断，直到中奖，或者循环结束)
	private Integer percent;
	//每日投放量
	private Integer releaseNumber;
	//每日成功送出奖品量
	private Integer todaySendOutNumber;
	
	//转盘度数范围设置，0-360度
	private Integer startDegrees;
	private Integer endDegrees;
	
	public Integer getAwardId() {
		return awardId;
	}
	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getStartSection() {
		return startSection;
	}
	public void setStartSection(Integer startSection) {
		this.startSection = startSection;
	}
	public Integer getEndSection() {
		return endSection;
	}
	public void setEndSection(Integer endSection) {
		this.endSection = endSection;
	}
	public Integer getPercent() {
		return percent;
	}
	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	public Integer getReleaseNumber() {
		return releaseNumber;
	}
	public void setReleaseNumber(Integer releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	public Integer getTodaySendOutNumber() {
		return todaySendOutNumber;
	}
	public void setTodaySendOutNumber(Integer todaySendOutNumber) {
		this.todaySendOutNumber = todaySendOutNumber;
	}
	public Integer getStartDegrees() {
		return startDegrees;
	}
	public void setStartDegrees(Integer startDegrees) {
		this.startDegrees = startDegrees;
	}
	public Integer getEndDegrees() {
		return endDegrees;
	}
	public void setEndDegrees(Integer endDegrees) {
		this.endDegrees = endDegrees;
	}
	public Integer getIsNothing() {
		return isNothing;
	}
	public void setIsNothing(Integer isNothing) {
		this.isNothing = isNothing;
	}
}
