package com.ehighsun.wxtp.admin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ehighsun.wxtp.base.action.BaseAction;
import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Award;
import com.ehighsun.wxtp.pojo.AwardList;

public class AdminAwardListAction extends BaseAction {

	private AwardList awardList = new AwardList();
	private List<Award> awards;

	@Resource(name = "baseDao")
	private BaseDao<AwardList> baseDao;
	
	public AdminAwardListAction() {
		super.setObject(awardList);
		super.initialize();
	}

	@Override
	public String findPage() {
		
		awards = (List<Award>) super.getBaseService().find("From Award where startSection != -1");
		
		super.setMainPage("adminAwardList.jsp");
		super.setFindPageTarget("/admin/adminAwardList_findPage");
		
		return super.findPage();
	}

	@Override
	public String findPageByMap() {
		awards = (List<Award>) super.getBaseService().find("From Award where startSection != -1");
		super.setMainPage("adminAwardList.jsp");
		super.setFindPageTarget("/admin/adminAwardList_findPageByMap");
		return super.findPageByMap();
	}
	
	public String changeIsGetAwardByAId() {
		Map<String, Object> json = new HashMap<String, Object>();
		baseDao.executeSql("update awardlist set isGetAward = 1 where id = "+super.getId());
		json.put("message", "true");
		super.setJson(json);
		return "ReturnAjax";
	}

	public List<Award> getAwards() {
		return awards;
	}

	public void setAwards(List<Award> awards) {
		this.awards = awards;
	}
	
	
}
