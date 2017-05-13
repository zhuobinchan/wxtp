package com.ehighsun.wxtp.admin.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ehighsun.wxtp.base.action.BaseAction;
import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.dao.CrudDao;
import com.ehighsun.wxtp.pojo.OtherAwardList;
import com.ehighsun.wxtp.pojo.Team;
import com.ehighsun.wxtp.util.ReadInExcelUtil;

public class AdminOtherAwardListAction extends BaseAction{
	private OtherAwardList otherAwardList = new OtherAwardList();
	private File excel;
	private String excelFileName;
	
	@Resource(name = "baseDao")
	private BaseDao<OtherAwardList> baseDao;
	
	public AdminOtherAwardListAction() {
		super();
		setObject(otherAwardList);
		initialize();
	}
	
	@Override
	public String findPage() {
		setMainPage("AdminAwardList1.jsp");
		setFindPageTarget("/admin/adminOtherAwardList_findPage");
		return super.findPage();
	}
	
	public String exportExcel() {
		baseDao.executeSql("delete from otherawardlist");
		List<OtherAwardList> otherAwardLists = ReadInExcelUtil.readAwardListsByExcel(excel);
		for (OtherAwardList otherAwardList : otherAwardLists) {
			super.getBaseService().saveObject(otherAwardList);
		}
		return findPage();
	}
	
	public String changeIsGetAwardByAId() {
		baseDao.executeSql("update otherawardlist set isGetAward = 1 where id = "+super.getId());
		super.getJson().put("message", "true");
		return "ReturnAjax";
	}
	
	
	public OtherAwardList getOtherAwardList() {
		return otherAwardList;
	}


	public void setOtherAwardList(OtherAwardList otherAwardList) {
		this.otherAwardList = otherAwardList;
	}


	public File getExcel() {
		return excel;
	}
	public void setExcel(File excel) {
		this.excel = excel;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
	
}
