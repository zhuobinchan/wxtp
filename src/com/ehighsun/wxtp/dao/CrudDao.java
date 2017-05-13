package com.ehighsun.wxtp.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ehighsun.wxtp.pojo.PageBean;


/**
 * 基础数据库操作类
 * 
 * @author ss
 * 
 */
public interface CrudDao {

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	@Transactional
	public Serializable save(Object o);
	
	@Transactional
	public Long count(String hql);
	

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 */
	@Transactional
	public void delete(Object o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	@Transactional
	public void update(Object o);

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 */
	@Transactional
	public void saveOrUpdate(Object o);

	/**
	 * 合并对象
	 * 
	 * @param o
	 */
	@Transactional
	public void merge(Object o);

	@Transactional
	public Object get(Object c, Serializable id);

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	@Transactional
	public List<?> find(String hql);

	/**
	 * 查询集合
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional
	public List<?> find(String hql, Object[] param);

	/**
	 * 查询集合
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional
	public List<?> find(String hql, List<Object> param);

	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param
	 * @param page
	 *            查询第几页
	 * @param rows
	 *            每页显示几条记录
	 * @return
	 */
	@Transactional
	public List<?> find(String hql, Object[] param, PageBean pageBean);

	@Transactional
	public List<?> find(String hql, List<Object> param, PageBean pageBean);

	@Transactional
	public List<?> findByMap(String hql, Map<String, Object> map);	
	
//	@Transactional
//	public List<?> findPageByMap(String hql, Map<String, Object> map,
//			PageBean pageBean);
	@Transactional
	public List<?> findPageByMap(IFindPageByMap impl,String hql, Map<String, Object> map,
			PageBean pageBean);
	
	@Transactional
	public Long countByMap(IFindPageByMap impl,String hql, Map<String, Object> map);
	
}

