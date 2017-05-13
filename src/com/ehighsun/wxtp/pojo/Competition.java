package com.ehighsun.wxtp.pojo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Competition {

	private Integer competitionId;
	private String status;//1代表正在进行中，0代表没有进行
	private String name;
	private Timestamp startTime;
	private Timestamp endTime;
	private List<Team> teams = new ArrayList<>();
	private String introduction;
	private String rule;
	
	
	public Integer getCompetitionId() {
		return competitionId;
	}
	public String getName() {
		return name;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setCompetitionId(Integer competitionId) {
		this.competitionId = competitionId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public String getIntroduction() {
		return introduction;
	}
	public String getRule() {
		return rule;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
