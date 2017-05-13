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

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.PageBean;

@Repository("baseDao")
@SuppressWarnings("all")
public class BaseDaoImpl<T> implements BaseDao<T> {

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

	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	public List<T> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	public List<T> find(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	public List<T> findLike(String hql, Object[] param,PageBean pageBean) {
		
		
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, "%"+param[i]+"%");
			}
		}
		return q.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
		
		
	}	
	
	public List<T> find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}

	public List<T> find(String hql, Object[] param, PageBean pageBean) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

	public List<T> find(String hql, List<Object> param, PageBean pageBean) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	public T get(String hql, Object[] param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public T get(String hql, List<Object> param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public Long count(String hql) {
		return (Long) this.getCurrentSession().createQuery(hql).uniqueResult();
	}

	public Long count(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	public Integer executeHql(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.executeUpdate();
	}

	public void merge(T o) {
		// TODO Auto-generated method stub
		this.getCurrentSession().merge(o);
	}

	public Integer executeSql(String sql) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public List executeOurSql(String sql) {
		Query q = this.getCurrentSession().createSQLQuery(sql);

		return q.list();
	}

	@Override
	public List<T> findTopN(String hql, List<Object> param, int N) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		q.setFirstResult(0);
		q.setMaxResults(N);
		return q.list();
	}

	@Override
	public List<T> findByMap(String hql, Map<String, Object> map) {
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

	public List<T> findPageByMap(String hql, Map<String, Object> map,
			PageBean pageBean) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(hql);
		stringBuffer.append(" where 1=1 ");

		for (Entry<String, Object> entry : map.entrySet()) {// 把查询条件放到where的后面
			stringBuffer.append(" and " + entry.getKey() + "=:"
					+ entry.getKey());
		}

		Query query = this.getCurrentSession().createQuery(
				stringBuffer.toString());

		/**
		 * 给hql语句的参数赋值
		 */
		for (Entry<String, Object> entry : map.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

	@Override
	public Long countByMap(String hql, Map<String, Object> map) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(hql);
		stringBuffer.append(" where 1=1 ");

		for (Entry<String, Object> entry : map.entrySet()) {// 把查询条件放到where的后面
			stringBuffer.append(" and " + entry.getKey() + "=:"
					+ entry.getKey());
		}

		Query query = this.getCurrentSession().createQuery(
				stringBuffer.toString());

		/**
		 * 给hql语句的参数赋值
		 */
		for (Entry<String, Object> entry : map.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return (Long) query.uniqueResult();

	}

	@Override
	public void evict(T o) {
		this.getCurrentSession().evict(o);
	}

	@Override
	public List<T> findPageByMap(String hql, Map<String, Object> map,
			Map<String, String> order, PageBean pageBean) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(hql);
		stringBuffer.append(" where 1=1 ");

		for (Entry<String, Object> entry : map.entrySet()) {// 把查询条件放到where的后面
			stringBuffer.append(" and " + entry.getKey() + "=:"
					+ entry.getKey());
		}
		if (order != null) {
			stringBuffer.append(" order by ");
		}
		for (Entry<String, String> entry : order.entrySet()) {// 把查询条件放到where的后面
			stringBuffer.append(entry.getKey() + " " + entry.getValue() + ",");
		}
		if (order != null) {
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}

		System.out.println("sql语句：" + stringBuffer.toString());

		Query query = this.getCurrentSession().createQuery(
				stringBuffer.toString());

		/**
		 * 给hql语句的参数赋值
		 */
		for (Entry<String, Object> entry : map.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

}
