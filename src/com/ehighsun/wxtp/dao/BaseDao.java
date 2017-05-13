package com.ehighsun.wxtp.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
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
public interface BaseDao<T> {

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	@Transactional
	public Serializable save(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 */
	@Transactional
	public void delete(T o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	@Transactional
	public void update(T o);

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 */
	@Transactional
	public void saveOrUpdate(T o);

	/**
	 * 合并对象
	 * 
	 * @param o
	 */
	@Transactional
	public void merge(T o);

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	@Transactional
	public List<T> find(String hql);

	/**
	 * 查询集合
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional
	public List<T> find(String hql, Object[] param);

	/**
	 * 查询集合
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional
	public List<T> find(String hql, List<Object> param);

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
	public List<T> find(String hql, Object[] param, PageBean pageBean);

	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param
	 * @param page
	 * @param rows
	 * @return
	 */
	@Transactional
	public List<T> find(String hql, List<Object> param, PageBean pageBean);

	@Transactional
	public List<T> findPageByMap(String hql, Map<String, Object> map,
			PageBean pageBean);

	@Transactional
	public List<T> findPageByMap(String hql, Map<String, Object> map,
			Map<String, String> order, PageBean pageBean);

	/**
	 * 获得一个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 * @return Object
	 */
	@Transactional
	public T get(Class<T> c, Serializable id);

	/**
	 * 获得一个对象
	 * 
	 * @param hql
	 * @param param
	 * @return Object
	 */
	@Transactional
	public T get(String hql, Object[] param);

	/**
	 * 获得一个对象
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional
	public T get(String hql, List<Object> param);

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @return
	 */
	@Transactional
	public Long count(String hql);

	@Transactional
	public Long countByMap(String hql, Map<String, Object> map);

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional
	public Long count(String hql, Object[] param);

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional
	public Long count(String hql, List<Object> param);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	@Transactional
	public Integer executeHql(String hql);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @param param
	 * @return 响应数目
	 */
	@Transactional
	public Integer executeHql(String hql, Object[] param);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	@Transactional
	public Integer executeHql(String hql, List<Object> param);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	@Transactional
	public Integer executeSql(String sql);

	@Transactional
	public List executeOurSql(String sql);

	@Transactional
	public List<T> findTopN(String hql, List<Object> param, int N);

	@Transactional
	public List<T> findByMap(String hql, Map<String, Object> map);

	@Transactional
	public void evict(T o);
	@Transactional
	public List<T> findLike(String hql, Object[] param,PageBean pageBean);

}