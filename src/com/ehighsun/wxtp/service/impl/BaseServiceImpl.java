package com.ehighsun.wxtp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehighsun.wxtp.dao.CrudDao;
import com.ehighsun.wxtp.dao.IFindPageByMap;
import com.ehighsun.wxtp.pojo.PageBean;
import com.ehighsun.wxtp.service.BaseService;


@Service("baseService")
public class BaseServiceImpl implements BaseService {
	
	@Resource(name="crudDao")
	private CrudDao crudDao;

	@Override
	public Long count(String hql) {
		return crudDao.count(hql);
	}	
	
	@Override
	public Long countByMap(IFindPageByMap impl,String hql, Map<String, Object> map){
		return crudDao.countByMap(impl, hql, map);
	}
	
	@Override
	public Object getObjectById(Object object, Integer id) {
		return crudDao.get(object, id);
	}

	@Override
	public void saveObject(Object object) {
		crudDao.save(object);
	}

	@Override
	public void saveOrUpdateObject(Object object) {
		crudDao.saveOrUpdate(object);
	}

	@Override
	public void deleteObject(Object object) {
		crudDao.delete(object);
	}
	
	@Override
	public List<?> find(String hql) {
		return crudDao.find(hql);
	}
	
	@Override
	public List<?> find(String hql, Object[] param, PageBean pageBean) {
		return crudDao.find(hql, param, pageBean);
	}

	@Override
	public List<?> find(String hql, List<Object> param) {
		return crudDao.find(hql, param);
	}
	
	@Override
	public List<?> findByMap(String hql, Map<String, Object> map) {
		return crudDao.findByMap(hql, map);
	}

	@Override
	public List<?> findPageByMap(IFindPageByMap impl, String hql,
			Map<String, Object> map, PageBean pageBean) {
		return crudDao.findPageByMap(impl, hql, map, pageBean);
	}

	@Override
	public List<?> find(String hql, Object[] param) {
		return crudDao.find(hql, param);
	}
	
//	@Override
//	public List<?> findPageByMap(String hql, Map<String, Object> map,
//			PageBean pageBean) {
//		return crudDao.findPageByMap(hql, map, pageBean);
//	}


	
}
