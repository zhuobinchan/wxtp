package com.ehighsun.wxtp.pojo;

import java.util.List;

public class Team {

	private Integer teamId;
	private String name;
	private String members;
	private String introduction;
	private Integer poll;
	private Integer basePoll;
	private Integer totalPoll;
	private String teamLogo;
	
	private Competition competition;
	
	private List<PollTeamList> pollTeamLists;
	
	
	
	
	public Team(Integer teamId, String name, String members,
			String introduction, Integer poll, Integer basePoll,
			Integer totalPoll, String teamLogo) {
		super();
		this.teamId = teamId;
		this.name = name;
		this.members = members;
		this.introduction = introduction;
		this.poll = poll;
		this.basePoll = basePoll;
		this.totalPoll = totalPoll;
		this.teamLogo = teamLogo;
	}
	public Team() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getTeamId() {
		return teamId;
	}
	public String getName() {
		return name;
	}
	public String getMembers() {
		return members;
	}
	public String getIntroduction() {
		return introduction;
	}
	public Integer getPoll() {
		return poll;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public void setPoll(Integer poll) {
		this.poll = poll;
	}
	public Integer getBasePoll() {
		return basePoll;
	}
	public void setBasePoll(Integer basePoll) {
		this.basePoll = basePoll;
	}
	public Integer getTotalPoll() {
		return totalPoll;
	}
	public void setTotalPoll(Integer totalPoll) {
		this.totalPoll = totalPoll;
	}
	public String getTeamLogo() {
		return teamLogo;
	}
	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}
	public Competition getCompetition() {
		return competition;
	}
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	public List<PollTeamList> getPollTeamLists() {
		return pollTeamLists;
	}
	public void setPollTeamLists(List<PollTeamList> pollTeamLists) {
		this.pollTeamLists = pollTeamLists;
	}
	
	
}
