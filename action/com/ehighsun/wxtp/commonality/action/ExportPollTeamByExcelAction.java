package com.ehighsun.wxtp.commonality.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.User;
import com.ehighsun.wxtp.util.WriteInExcelUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ExportPollTeamByExcelAction extends ActionSupport {
	
	private Integer teamId;
	private String filename;
	
	private InputStream stream;//文件传输流
	
	@Resource(name = "baseDao")
	private BaseDao<User> baseDao;

	public String ExportPollTeam() {
		List<User> users = baseDao.find("from User where uid in (select user.uid from PollTeamList where team.teamId = "+teamId+" )");
		try {
			filename = new String("用户信息.xls".getBytes(),"ISO8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String path = ServletActionContext.getRequest().getRealPath(
				"/userMessage/tempResume.xls");
		File downFile = WriteInExcelUtil.ExportUserByTeamId(users,path);
		if (!downFile.exists()) {
			return "error";
		} else {
			try {
				stream = new FileInputStream(downFile);
			} catch (FileNotFoundException e) {
				System.out.println("临时文件不存在");
			}
			return "ExportPollTeam";
		}
		
		
	}
	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getStream() {
		return stream;
	}
	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	
}
