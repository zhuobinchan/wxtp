package com.ehighsun.wxtp.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ehighsun.wxtp.dao.CrudDao;
import com.ehighsun.wxtp.dao.IFindPageByMap;
import com.ehighsun.wxtp.pojo.PageBean;



@Repository("crudDao")
@SuppressWarnings("all")
public class CrudDaoImpl implements CrudDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Long count(String hql) {
		return (Long) this.getCurrentSession().createQuery(hql).uniqueResult();
	}

	
	@Override
	public Serializable save(Object o) {
		return this.getCurrentSession().save(o);
	}

	@Override
	public void delete(Object o) {
		this.getCurrentSession().delete(o);
	}

	@Override
	public void update(Object o) {
		this.getCurrentSession().update(o);
	}

	@Override
	public void saveOrUpdate(Object o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	@Override
	public Object get(Object c, Serializable id) {
		System.out.println(c.getClass().getName());
		return  this.getCurrentSession().get(c.getClass(), id);
	}

	@Override
	public void merge(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public List find(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}	

	@Override
	public List find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}

	@Override
	public List find(String hql, Object[] param, PageBean pageBean) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

	@Override
	public List find(String hql, List<Object> param, PageBean pageBean) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

	@Override
	public List<?> findByMap(String hql, Map<String, Object> map) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(hql);
		stringBuffer.append(" where 1=1 ");

		for (Entry<String, Object> entry : map.entrySet()) {// 把查询条件放到where的后面
			stringBuffer.append(" and " + entry.getKey() + "=:"
					+ entry.getKey());
		}

		System.out.println(stringBuffer.toString() + "/////////////");
		Query query = this.getCurrentSession().createQuery(
				stringBuffer.toString());

		/**
		 * 给hql语句的参数赋值
		 */
		for (Entry<String, Object> entry : map.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.list();
	}

	
	@Override
	public List<?> findPageByMap(IFindPageByMap impl, String hql,
			Map<String, Object> map, PageBean pageBean) {
		return impl.findPageByMap(this.getCurrentSession(),hql, map, pageBean);
	}

	@Override
	public Long countByMap(IFindPageByMap impl, String hql,
			Map<String, Object> map) {
		return impl.countByMap(this.getCurrentSession(), hql, map);
	}

}
