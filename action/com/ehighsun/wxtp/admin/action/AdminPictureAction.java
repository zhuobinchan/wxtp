package com.ehighsun.wxtp.admin.action;

import javax.annotation.Resource;

import com.ehighsun.wxtp.base.action.BaseAction;
import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Suishoupai;

public class AdminPictureAction extends BaseAction {

	private Suishoupai ssp = new Suishoupai();

	@Resource(name="baseDao")
	private BaseDao<Suishoupai> baseDao;
	
	public AdminPictureAction() {
		super.setObject(ssp);
		super.initialize();
	}
		
	
	@Override
	public String findPageByMap() {
		
		super.setPageSize(20);
		super.setHql("From Suishoupai order by poll desc");
		super.setMainPage("AdminPicture.jsp");
		super.setFindPageTarget("/admin/adminPicture_findPageByMap");
		
		return super.findPageByMap();
	}


	@Override
	public String findPage() {
		super.setPageSize(20);
		super.setHql("From Suishoupai order by poll desc");
		super.setMainPage("AdminPicture.jsp");
		super.setFindPageTarget("/admin/adminPicture_findPage");
		return super.findPage();
	}

	public String passPictures(){
		
		String[] idList = super.getIds().split(",");
		for (String id : idList) {
			baseDao.executeSql("update suishoupai set status = '1' where id ="+id);
		}

		super.getJson().put("message", "true");
		
		return "ReturnAjax";
	}

	public String failPictures(){
		
		String[] idList = super.getIds().split(",");
		for (String id : idList) {
			baseDao.executeSql("update suishoupai set status = '0' where id ="+id);
		}

		super.getJson().put("message", "true");
		
		return "ReturnAjax";
	}	
	
	public Suishoupai getSsp() {
		return ssp;
	}

	public void setSsp(Suishoupai ssp) {
		this.ssp = ssp;
	}


}
