package com.ehighsun.wxtp.admin.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;








import com.ehighsun.wxtp.base.action.BaseAction;
import com.ehighsun.wxtp.dao.impl.FindPageByMapWithFK;
import com.ehighsun.wxtp.pojo.Competition;
import com.ehighsun.wxtp.pojo.Team;
import com.ehighsun.wxtp.service.impl.TeamServiceImpl;


public class AdminTeamAction extends BaseAction {
	private Team team = new Team();
	private File img;
	private String imgFileName;
	private List<Competition> competitions;
	
	@Resource(name="teamService")
	private TeamServiceImpl teamServiceImpl;
	
	public AdminTeamAction() {
		super();
		setObject(team);
		initialize();
	}
	
	@Override
	public String findPage() {
		competitions = (List<Competition>) super.getBaseService().find("from Competition");
		return super.findPage();
	}
	
	@Override
	public String saveObject() {
		teamServiceImpl.saveTeam(img, imgFileName, team);
		return "saveObject";
	}
	
	@Override
	public String saveOrUpdateObject() {
		teamServiceImpl.saveOrUpdateTeam(img, imgFileName, team);
		return "saveOrUpdateObject";
	}
	
	@Override
	public String deleteObject() {
		teamServiceImpl.deleteTeam(super.getId());
		super.getJson().put("message", "true");
		return "deleteObject";
	}
	
	@Override
	public String deleteObjects() {
		String[] idList = super.getIds().split(",");
		for (String id : idList) {
			teamServiceImpl.deleteTeam(Integer.parseInt(id));
		}
		super.getJson().put("message", "true");
		return "deleteObjects";
	}
	
	@Override
	public String findPageByMap() {
		super.setObject(new Team());
		super.setFindPageByMapImpl(new FindPageByMapWithFK());
		super.setMainPage("adminTeam.jsp");
		super.setFindPageTarget("/admin/adminAdmin_findPageByMap");
		competitions = (List<Competition>) super.getBaseService().find("from Competition");
		return super.findPageByMap();
	}
	
	public String getTeamIntroductionAjax(){
		Team index =(Team)super.getBaseService().getObjectById(team, super.getId());
		String introduction = index.getIntroduction();
		super.getJson().put("introduction", introduction);
		return "ReturnAjax";
	}
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public File getImg() {
		return img;
	}
	public void setImg(File img) {
		this.img = img;
	}
	public String getImgFileName() {
		return imgFileName;
	}
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public List<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(List<Competition> competitions) {
		this.competitions = competitions;
	}

	
}
