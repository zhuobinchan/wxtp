package com.ehighsun.wxtp.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.ehighsun.wxtp.pojo.PageBean;

public interface IFindPageByMap {
	
	public Long countByMap(Session session,String hql, Map<String, Object> map);
	
	public List<?> findPageByMap(Session session,String hql, Map<String, Object> map,
			PageBean pageBean);
	
}
