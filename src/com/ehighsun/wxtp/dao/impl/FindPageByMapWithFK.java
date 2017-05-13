package com.ehighsun.wxtp.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ehighsun.wxtp.dao.IFindPageByMap;
import com.ehighsun.wxtp.pojo.PageBean;


@Component("FindPageByMapWithFK")
public class FindPageByMapWithFK implements IFindPageByMap {

	@Override
	public List<?> findPageByMap(Session session, String hql,
			Map<String, Object> map, PageBean pageBean) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(hql);
		stringBuffer.append(" where 1=1 ");

		
		/*entry.getKey().replace(".", ""),作用是为了查询外键例如:organization.id : organization.id(organization.id会报错) */
		for (Entry<String, Object> entry : map.entrySet()) {// 把查询条件放到where的后面

				stringBuffer.append(" and " + entry.getKey() + "=:"
						+ entry.getKey().replace(".", ""));
		}

		Query query = session.createQuery(
				stringBuffer.toString());

		/**
		 * 给hql语句的参数赋值
		 */
		for (Entry<String, Object> entry : map.entrySet()) {

			
			
			/*
			 * 1.result用于判断是否为外键，外键的map的value必须为integer
			 * 2.原理: 判断map里的key是否含有点".",若含有这判断视为外键，map的value需要转换成integer类型
			 *  
			 *  */
//			boolean result=entry.getValue().toString().matches("[0-9]+");
			int result=entry.getKey().toString().indexOf(".");
			System.out.println(entry.getKey().toString()+":find:"+result);
			if(result != -1){

					query.setParameter(entry.getKey().replace(".", ""), Integer.parseInt(entry.getValue().toString()));
					
				}else query.setParameter(entry.getKey().replace(".", ""), entry.getValue().toString());
			
			
		}

		return query.setFirstResult(pageBean.getStart())
				.setMaxResults(pageBean.getPageSize()).list();
	}

	@Override
	public Long countByMap(Session session, String hql, Map<String, Object> map) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(hql);
		stringBuffer.append(" where 1=1 ");

		/*
		 * entry.getKey().replace(".", ""),作用是为了查询外键例如:organization.id : organization.id(organization.id会报错) 
		 * */
		for (Entry<String, Object> entry : map.entrySet()) {// 把查询条件放到where的后面

			stringBuffer.append(" and " + entry.getKey() + " =:"
					+ entry.getKey().replace(".",""));
		}

		Query query = session.createQuery(
				stringBuffer.toString());

		/**
		 * 给hql语句的参数赋值
		 */
		for (Entry<String, Object> entry : map.entrySet()) {
			
			/*
			 * 1.result用于判断是否为外键，外键的map的value必须为integer
			 * 2.原理: 判断map里的key是否含有点".",若含有这判断视为外键，map的value需要转换成integer类型
			 *  
			 *  */
//			boolean result=entry.getValue().toString().matches("[0-9]+");
			int result=entry.getKey().toString().indexOf(".");
			System.out.println(entry.getKey().toString()+":count:"+result);
			if(result != -1){

				query.setParameter(entry.getKey().replace(".", ""), Integer.parseInt(entry.getValue().toString()));
				
			}else query.setParameter(entry.getKey().replace(".", ""), entry.getValue().toString());
			
		}

		return (Long) query.uniqueResult();
	}

}
