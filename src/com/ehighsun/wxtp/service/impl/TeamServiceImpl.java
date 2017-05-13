package com.ehighsun.wxtp.service.impl;

import java.io.File;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Team;
import com.ehighsun.wxtp.util.FileUploadUtil;
import com.ehighsun.wxtp.util.StringUtil;

@Service("teamService")
public class TeamServiceImpl extends BaseServiceImpl {
	@Resource(name="baseDao")
	private BaseDao<Team> baseDao;
	public void saveTeam(File img, String imgFileName, Team team) {

		if (img != null&&StringUtil.isNotEmpty(img.getPath())) {

			FileUploadUtil fileUtil = new FileUploadUtil();

			String imageUrl = fileUtil.updateFile(img, imgFileName,
					"images/teamLogo");

			team.setTeamLogo(imageUrl);
		}
		if (!StringUtil.isNotEmpty(team.getBasePoll())) {
			team.setBasePoll(0);
		}
		if (!StringUtil.isNotEmpty(team.getPoll())) {
			team.setPoll(0);
		}
		team.setTotalPoll(team.getBasePoll()+team.getPoll());
		super.saveObject(team);

	}

	public void saveOrUpdateTeam(File img, String imgFileName,
			Team team) {

		FileUploadUtil fileUtil = new FileUploadUtil();
		Team index = (Team) super.getObjectById(new Team(), team.getTeamId());
		
		if (img != null&&StringUtil.isNotEmpty(img.getPath())) {

			String pathname = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/" + index.getTeamLogo());

			fileUtil.deleteFile(pathname);

			String imageUrl = fileUtil.updateFile(img, imgFileName,
					"images/teamLogo");

			team.setTeamLogo(imageUrl);

		} else if (StringUtil.isNotEmpty(index.getTeamLogo())) {
			team.setTeamLogo(index.getTeamLogo());
		}

//		super.saveOrUpdateObject(Team);
		if (!StringUtil.isNotEmpty(team.getBasePoll())) {
			team.setBasePoll(0);
		}
		if (!StringUtil.isNotEmpty(team.getPoll())) {
			team.setPoll(0);
		}
		team.setTotalPoll(team.getBasePoll()+team.getPoll());
		baseDao.merge(team);

	}
	
	public void deleteTeam(Integer id){
		
		Team team = (Team) super.getObjectById(new Team(), id);
		
		if(StringUtil.isNotEmpty(team.getTeamLogo())){
			
			String pathname = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/" + team.getTeamLogo());
			
			FileUploadUtil.deleteFile(pathname);
		}
		super.deleteObject(team);
	}
	
}
