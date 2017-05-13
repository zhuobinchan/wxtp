package com.ehighsun.wxtp.user.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.ConstraintViolationException;

import com.alibaba.fastjson.JSON;
import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Competition;
import com.ehighsun.wxtp.pojo.PollTeamList;
import com.ehighsun.wxtp.pojo.Team;
import com.ehighsun.wxtp.pojo.User;
import com.opensymphony.xwork2.ActionSupport;

public class TeamPollAction extends ActionSupport{

	private List<Team> teams;
	private Team team;
	private Integer total;
	private Integer teamId;
	
	private String targetUrl;
	
	private Competition competition;
	
	private HashMap<String, Object> json = new HashMap<>();
	@Resource(name="baseDao")
	private BaseDao<Team> teamDao;
	@Resource(name="baseDao")
	private BaseDao<Competition> competitionDao;
	
	public String GetAllTeams(){

		User user = (User) ServletActionContext.getContext().getSession().get("user");		
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}
		
		teams = teamDao.find("select new Team( teamId,  name,  members, introduction,  poll,  basePoll,  totalPoll,  teamLogo) From Team");
//		total = 0;
//		
//		for (Team team : teams) {
//			total = total + team.getPoll();
//		}
		
		json.put("message", "true");
		json.put("teams", teams);
		

		return "ReturnAjax";
	}
	
	public String GetTeamById(){

		User user = (User) ServletActionContext.getContext().getSession().get("user");				
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}
//		if(user.getPollStatus().equals("0")) return "noPoll";
		
		team = teamDao.get(Team.class, teamId);
		
		return "GetTeamById";
	}
	
	public String AddPoll(){

		User user = (User) ServletActionContext.getContext().getSession().get("user");
		
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}
		
		if (!JudgeCompetition()){
			return "competitionIsEnd";
		}
		
		
		if(!user.getPollStatus().equals("1")){
		
			List polls = teamDao.executeOurSql("select poll from team where teamId = "+teamId);
			
			Integer poll = 1 + (Integer) (polls!=null?polls.get(0):null);
			
	
			teamDao.executeSql("update team set poll ="+poll+" where teamId = "+teamId);
			teamDao.executeSql("update user set pollStatus = 1 where openId = '"+user.getOpenId()+"'");
			user.setPollStatus("1");
			
			try {
				teamDao.executeSql("insert into poll_team (teamId,uid) VALUES ("+teamId+","+user.getUid()+")");
			} catch (ConstraintViolationException e) {
				System.out.println("投票，重复插入！报异常");
			}
			
			teamDao.executeSql("update team set totalPoll = if(ISNULL(poll),0,poll)+if(ISNULL(basePoll),0,basePoll) where teamId = "+teamId);
			
			ServletActionContext.getContext().getSession().put("user", user);
		
//		return "gotoFenX";
//		json.put("message", "true");
		}

		team = teamDao.get(Team.class, teamId);
//		json.put("message", "false");
		
		return "gotoFenX";
	}
	
	private boolean JudgeCompetition() {
	    List<Competition> competitions = competitionDao.find("From Competition where status = 1");
		
	    competition = competitions.get(0);
	    System.out.println("now:"+System.currentTimeMillis()+",guiding:"+competition.getEndTime().getTime());
	    if(System.currentTimeMillis() <= competition.getEndTime().getTime()) return true;
	    
	    return false;
	}

	public String CheckRank(){
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");				
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}
		if(user.getPollStatus().equals("0")) return "noPoll";
		
		teams = teamDao.find("From Team order by totalPoll desc");
		
		return "CheckRank";
	}
	
	public boolean JudegeLogin(User user){
		
		if (user == null) {

			HttpServletRequest request = ServletActionContext.getRequest();
			String reqPamrs = request.getQueryString();
			String requestUrl = request.getRequestURI()
					+ (reqPamrs == null ? "" : "?" + reqPamrs);

			int position = requestUrl.indexOf("/", 2);

			// url = url.replace("/wxtp", "");

			requestUrl = requestUrl.substring(position, requestUrl.length());

			targetUrl = requestUrl;
			System.out.println("UserLoginInterceptor-targetUrl:" + targetUrl);
			return false;
		}

		return true;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public HashMap<String, Object> getJson() {
		return json;
	}

	public void setJson(HashMap<String, Object> json) {
		this.json = json;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
}
