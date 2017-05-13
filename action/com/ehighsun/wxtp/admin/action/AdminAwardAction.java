package com.ehighsun.wxtp.admin.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.base.action.BaseAction;
import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Award;
import com.ehighsun.wxtp.util.PropertiesUtil;

public class AdminAwardAction extends BaseAction {
	//总区间范围，如果100则代表1-100，如果1000则代表1-1000
	private Integer totalPercent;
	
	private Award award = new Award();
	
	@Resource(name = "baseDao")
	private BaseDao<Award> baseDao;
	public AdminAwardAction() {
		super.setObject(award);
		super.initialize();		
		try {
			totalPercent= Integer.valueOf(PropertiesUtil.getProperites(ServletActionContext.getRequest().getRealPath("/WEB-INF/classes/gz.properties")).getProperty("gailv"));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Award getAward() {
		return award;
	}
	public void setAward(Award award) {
		this.award = award;
	}
	@Override
	public String findPageByMap() {
		super.setMainPage("AdminAward.jsp");
		super.setFindPageTarget("/admin/adminAward_findPageByMap");
		return super.findPageByMap();
	}
	
	@Override
	public String saveObject() {
		Integer percent = award.getPercent();
		if (percent>0) {
			String sumPercent = baseDao.executeOurSql("SELECT IFNULL(SUM(percent),0) FROM award").get(0).toString();
			Integer startSection = totalPercent*Integer.valueOf(sumPercent==null?"0":sumPercent)/100+1;
			Integer endSection = startSection+totalPercent*percent/100-1;
			award.setStartSection(startSection);
			award.setEndSection(endSection);
		}else {
			award.setPercent(0);
			award.setStartSection(0);
			award.setEndSection(0);
		}
		award.setNumber(award.getNumber()==null?0:award.getNumber());
		award.setTodaySendOutNumber(award.getTodaySendOutNumber()==null?0:award.getTodaySendOutNumber());
		award.setStartDegrees(award.getStartDegrees()==null?0:award.getStartDegrees());
		award.setEndDegrees(award.getEndDegrees()==null?0:award.getEndDegrees());
		award.setIsNothing(award.getIsNothing()==null?0:award.getIsNothing());
		super.getBaseService().saveObject(award);
		
		return "saveObject";
	}
	@Override
	public String saveOrUpdateObject() {
		System.out.println(award.getPercent());
		
		List<Award> list = (List<Award>) super.getBaseService().find("from Award");
		//将更新的对象放进列表里面
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getAwardId()==award.getAwardId()) {
				list.set(i, award);
				break;
			}
		}
		//更新时需要重新计算区间，比如百分之20,生成区间为1-20
		Integer sumPercent = 0;
		for (Award index2 : list) {
			if (index2.getPercent()>0) {
				Integer percent = index2.getPercent()==null?0:index2.getPercent();
				Integer startSection = totalPercent*(sumPercent==null?0:sumPercent)/100+1;
				Integer endSection = startSection+totalPercent*percent/100-1;
				
				index2.setStartSection(startSection);
				index2.setEndSection(endSection);
				sumPercent += (index2.getPercent()==null?0:index2.getPercent());
				
			}else {
				index2.setPercent(0);
				index2.setStartSection(0);
				index2.setEndSection(0);
			}
			index2.setNumber(index2.getNumber()==null?0:index2.getNumber());
			index2.setTodaySendOutNumber(index2.getTodaySendOutNumber()==null?0:index2.getTodaySendOutNumber());
			index2.setStartDegrees(index2.getStartDegrees()==null?0:index2.getStartDegrees());
			index2.setEndDegrees(index2.getEndDegrees()==null?0:index2.getEndDegrees());
			index2.setIsNothing(index2.getIsNothing()==null?0:index2.getIsNothing());
			super.getBaseService().saveOrUpdateObject(index2);
		}
		return "saveOrUpdateObject";
	}
	
	public String isOverBoundPercent() {
		Map<String, Object> json = new HashMap<>();
		String sumPercent =  baseDao.executeOurSql("SELECT IFNULL(SUM(percent),0) FROM award").get(0).toString();
		String canSetRoundPercent = baseDao.executeOurSql("SELECT 100-IFNULL(SUM(percent),0) FROM award").get(0).toString();
		json.put("sumPercent", Integer.valueOf(sumPercent));
		json.put("canSetRoundPercent", Integer.valueOf(canSetRoundPercent));
		json.put("message", "true");
		super.setJson(json);
		return "ReturnAjax";
	}
	
}
