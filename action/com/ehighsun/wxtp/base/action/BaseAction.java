package com.ehighsun.wxtp.base.action;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.ehighsun.wxtp.dao.IFindPageByMap;
import com.ehighsun.wxtp.pojo.PageBean;
import com.ehighsun.wxtp.service.BaseService;
import com.ehighsun.wxtp.util.PageUtil;
import com.ehighsun.wxtp.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	
	private Object object;
	private Integer id;
	private String ids;
	
	private String hql;
	private String count_str;
	
	private Object[] param = null;/*查询语句中,where xx=?,给问号赋值*/
	private PageBean pageBean;
	
	private String page;
	private Integer pageSize;
	private String searchContent;
	private Map<String, Object> map = new HashMap<>();
	
	private String currentPage;
	private String targetPage;
	private String mainPage;
	private String findPageTarget; /*分页所需要跳转的目标地址*/
	private String pageCodeParam; /*分页时要传的参数*/
	private Map<String, Object> json = new HashMap<>();
	
	@Resource(name="FindPageByMapWithoutFK")
	private IFindPageByMap findPageByMapImpl;
	
	private Object result;
	private List<?> results;

	@Resource(name="baseService")
	private BaseService baseService;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	
	public BaseAction() {
		super();
		pageCodeParam = "";
		pageSize = 6;
	}

	public void initialize(){
		hql = "From "+object.getClass().getSimpleName();
		count_str = "select count(*) from "+object.getClass().getSimpleName();
	}
	
	public String getObjectById() {
		
		this.result = baseService.getObjectById(object, id);
		
		return "getObjectById";
	}

	public String saveObject() {
		baseService.saveObject(object);
		
		return "saveObject";
	}

	public String saveOrUpdateObject() {
		baseService.saveOrUpdateObject(object);
		return "saveOrUpdateObject";
	}

	public String deleteObject() {
		
		object = baseService.getObjectById(object, id);
		baseService.deleteObject(object);
		json.put("message", "true");
		return "deleteObject";
	}

	public String deleteObjects() {
		
		String[] idList = ids.split(",");
		for (String id : idList) {
			
			System.out.println("idList:"+id);
			
			Object object_del = baseService.getObjectById(object, Integer.parseInt(id));
			
			baseService.deleteObject(object_del);
		}

		json.put("message", "true");
		
		return "deleteObjects";
	}
	
	public String findAll() {
		results = baseService.find(hql);
		return "findAll";
	}
	
	public String findPage() {
		
		findPageFunc();

		
		return "findPage";
	}
	

	public String findPageByMap(){
		
		findPageByMapFunc();
		
		return "findPageByMap";
	}

	
	/*普通分页*/
	public void findPageFunc(){
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		
		pageBean = new PageBean(Integer.parseInt(page), pageSize);
		
		Long count = baseService.count(count_str);
		
		
		String pageCode = PageUtil.genPagination(request.getContextPath()
				+ findPageTarget , count,
				Integer.parseInt(page), pageSize, "mainPage="+mainPage+"&findPageTarget="+findPageTarget+pageCodeParam);

		request.getSession().setAttribute("pageCode", pageCode);
		results = baseService.find(hql, param, pageBean);
		
	}
	
	/*带条件查询分页*/
	public void findPageByMapFunc(){
		
		/*searchContent 是json字符串,不能传到jsp,先转码转成url编码*/
		pageCodeParam = "&searchContent="+URLEncoder.encode(searchContent);
		
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}		
		
		pageBean = new PageBean(Integer.parseInt(page), pageSize);
		
		
		JSONObject jsonObject = new JSONObject();
		/*searchContent 被转成url编码，使用前先要转回utf8编码*/
		jsonObject = JSONObject.parseObject(URLDecoder.decode(searchContent));
		
		for (Entry<String, Object> entry : jsonObject.entrySet()) {
			if (entry.getValue() !=null && !entry.getValue().equals("")) {
				map.put(entry.getKey(), entry.getValue());
			}
		}
		
		/*有用，勿删*/
		hql = "From "+object.getClass().getSimpleName();
		count_str = "select count(*) from "+object.getClass().getSimpleName();
		/*有用，勿删*/
		
		Long count = baseService.countByMap(findPageByMapImpl, count_str, map);
		
		
		String pageCode = PageUtil.genPagination(request.getContextPath()
				+ findPageTarget , count,
				Integer.parseInt(page), pageSize, "mainPage="+mainPage+"&findPageTarget="+findPageTarget+pageCodeParam);

		request.getSession().setAttribute("pageCode", pageCode);

		results = baseService.findPageByMap(findPageByMapImpl,hql, map, pageBean);
		
	}
	
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Object[] getParam() {
		return param;
	}

	public void setParam(Object[] param) {
		this.param = param;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getFindPageTarget() {
		return findPageTarget;
	}

	public void setFindPageTarget(String findPageTarget) {
		this.findPageTarget = findPageTarget;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getCount_str() {
		return count_str;
	}

	public void setCount_str(String count_str) {
		this.count_str = count_str;
	}

	public IFindPageByMap getFindPageByMapImpl() {
		return findPageByMapImpl;
	}

	public void setFindPageByMapImpl(IFindPageByMap findPageByMapImpl) {
		this.findPageByMapImpl = findPageByMapImpl;
	}

	public String getPageCodeParam() {
		return pageCodeParam;
	}

	public void setPageCodeParam(String pageCodeParam) {
		this.pageCodeParam = pageCodeParam;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


}
