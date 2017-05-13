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
import com.ehighsun.wxtp.pojo.OtherAwardList2;
import com.ehighsun.wxtp.pojo.Team;
import com.ehighsun.wxtp.util.ReadInExcelUtil;

public class AdminOtherAwardList2Action extends BaseAction{
	private OtherAwardList2 otherAwardList2 = new OtherAwardList2();
	private File excel;
	private String excelFileName;
	
	@Resource(name = "baseDao")
	private BaseDao<OtherAwardList> baseDao;
	
	public AdminOtherAwardList2Action() {
		super();
		setObject(otherAwardList2);
		initialize();
	}
	
	@Override
	public String findPage() {
		setMainPage("AdminAwardList2.jsp");
		setFindPageTarget("/admin/adminOtherAwardList2_findPage");
		return super.findPage();
	}
	
	public String exportExcel() {
		baseDao.executeSql("delete from otherawardlist2");
		List<OtherAwardList2> otherAwardList2s = ReadInExcelUtil.readAwardList2sByExcel(excel);
		for (OtherAwardList2 otherAwardList2 : otherAwardList2s) {
			super.getBaseService().saveObject(otherAwardList2);
		}
		return findPage();
	}
	
	public String changeIsGetAwardByAId() {
		baseDao.executeSql("update otherawardlist2 set isGetAward = 1 where id = "+super.getId());
		super.getJson().put("message", "true");
		return "ReturnAjax";
	}
	
	public OtherAwardList2 getOtherAwardList2() {
		return otherAwardList2;
	}

	public void setOtherAwardList2(OtherAwardList2 otherAwardList2) {
		this.otherAwardList2 = otherAwardList2;
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
