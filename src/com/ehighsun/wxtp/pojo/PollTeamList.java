package com.ehighsun.wxtp.pojo;

import java.io.Serializable;

public class PollTeamList implements Serializable {
	private Team team;
	private User user;
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
