package com.ehighsun.wxtp.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ehighsun.wxtp.dao.IFindPageByMap;
import com.ehighsun.wxtp.pojo.PageBean;


@Component("FindPageByMapWithoutFK")
public class FindPageByMapWithoutFK implements IFindPageByMap {
	
	@Override
	public List<?> findPageByMap(Session session,String hql, Map<String, Object> map,
			PageBean pageBean) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(hql);
		stringBuffer.append(" where 1=1 ");

		
		for (Entry<String, Object> entry : map.entrySet()) {// 把查询条件放到where的后面

				stringBuffer.append(" and " + entry.getKey() + "=:"
						+ entry.getKey());
		}

		Query query = session.createQuery(
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
	public Long countByMap(Session session, String hql, Map<String, Object> map) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(hql);
		stringBuffer.append(" where 1=1 ");

		for (Entry<String, Object> entry : map.entrySet()) {// 把查询条件放到where的后面
			stringBuffer.append(" and " + entry.getKey() + " =:"
					+ entry.getKey());
		}

		Query query = session.createQuery(
				stringBuffer.toString());

		/**
		 * 给hql语句的参数赋值
		 */
		for (Entry<String, Object> entry : map.entrySet()) {

				query.setParameter(entry.getKey(), entry.getValue());
				
		}

		return (Long) query.uniqueResult();
	}


}
