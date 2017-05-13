package com.ehighsun.wxtp.admin.action;

import java.util.List;

import javax.annotation.Resource;

import com.ehighsun.wxtp.base.action.BaseAction;
import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Competition;
import com.ehighsun.wxtp.pojo.Team;

public class AdminCompetition extends BaseAction {

	private Competition competition = new Competition();
	@Resource(name = "baseDao")
	private BaseDao<Competition> baseDao;
	public AdminCompetition() {
		super.setObject(competition);
		super.initialize();
	}

	@Override
	public String findPage() {
		
		super.setMainPage("AdminCompetition.jsp");
		super.setFindPageTarget("/admin/adminCompetition_findPage");
		return super.findPage();
	}

	@Override
	public String findPageByMap() {
		
		super.setMainPage("AdminCompetition.jsp");
		super.setFindPageTarget("/admin/adminCompetition_findPageByMap");
		return super.findPageByMap();
	}
	
	@Override
	public String saveObject() {
		if ("1".equals(competition.getStatus())) {
			baseDao.executeSql("update competition set status = '0'");
		}
		return super.saveObject();
	}
	
	@Override
	public String saveOrUpdateObject() {
		if ("1".equals(competition.getStatus())) {
			baseDao.executeSql("update competition set status = '0'");
		}
		List<Team> teams = baseDao.get(Competition.class, competition.getCompetitionId()).getTeams();
		competition.setTeams(teams);
		return super.saveOrUpdateObject();
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	
	
	
}
