package com.ehighsun.wxtp.user.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.PageBean;
import com.ehighsun.wxtp.pojo.Suishoupai;
import com.ehighsun.wxtp.pojo.User;
import com.ehighsun.wxtp.util.PageUtil;
import com.ehighsun.wxtp.util.PropertiesUtil;
import com.ehighsun.wxtp.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class UserPictureAction extends ActionSupport{
	
	private String targetUrl;
	private Integer picId;
	private List<Suishoupai> pictures;
	private Suishoupai picture;
	private String page;
	
	private Integer pageSize = 6;
	
	private String search_content;
	
	@Resource(name="baseDao")
	private BaseDao<Suishoupai> pDao;
	
	private Map<String, Object> json = new HashMap<String, Object>();
	
	public String GetPictures(){
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");		
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}		
		
		pictures = pDao.find("From Suishoupai  where status=? order by poll desc",new Object[]{"1"},new PageBean(1, pageSize));
		
		json.put("message", "true");
		json.put("pictures", pictures);
		
		return "ReturnAjax";
	}

	/*为相片投票，每人每天只能投票5次*/
	public String PollPicture() throws IOException{
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");		
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}
		String propertiesPath = ServletActionContext.getRequest().getRealPath("/WEB-INF/classes/gz.properties");
		
		Integer picPollTimes = Integer.parseInt(PropertiesUtil.getProperites(propertiesPath).getProperty("picPollTimes"));
		
		if(user.getPollStatusShuiShouPai()<picPollTimes){
			
			/*若照片投票次数少于5次，则增加照片票数和增加用户投票次数*/
			pDao.executeSql("update suishoupai set poll=poll+1 where id="+picId);
			pDao.executeSql("update user set pollStatusShuiShouPai=pollStatusShuiShouPai+1 where openId ='"+user.getOpenId()+"'");
			user.setPollStatusShuiShouPai(user.getPollStatusShuiShouPai()+1);
			ServletActionContext.getContext().getSession().put("user", user);
			
			json.put("message", "true");
			/*返还投票剩余次数*/
			json.put("pollTimes", picPollTimes-user.getPollStatusShuiShouPai());
			return "ReturnAjax";
			
		}else {
			
			json.put("message", "false");
			return "ReturnAjax";			
			
		}
		
	}
	
	/*获取某个相片的详细信息*/
	public String GetPicById(){
		User user = (User) ServletActionContext.getContext().getSession().get("user");		
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}
		
		picture = pDao.get(Suishoupai.class, picId);
		
		return "GetPicById";
	}
	
	/*搜索相片*/
	public String SearchPictures(){

		User user = (User) ServletActionContext.getContext().getSession().get("user");		
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}		
		
		pictures = pDao.findLike("From Suishoupai where (userName like ? or noId like ?) and status=1 order by poll desc",new Object[]{search_content,search_content},new PageBean(Integer.parseInt(page), pageSize));
		
		json.put("message", "true");
		json.put("pictures", pictures);
		
		return "ReturnAjax";
		
	}
	
	public String SortPicturesByCreateTime(){
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");		
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}		
		
		pictures = pDao.findLike("From Suishoupai where (userName like ? or noId like ?) and status=1 order by id desc",new Object[]{search_content,search_content},new PageBean(Integer.parseInt(page), pageSize));
		
		json.put("message", "true");
		json.put("pictures", pictures);
		
		return "ReturnAjax";
		
	}
	
	public String SortPicturesByPoll(){
		
		User user = (User) ServletActionContext.getContext().getSession().get("user");		
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}		
		
		pictures = pDao.findLike("From Suishoupai where (userName like ? or noId like ?) and status=1 order by poll desc",new Object[]{search_content,search_content},new PageBean(Integer.parseInt(page), pageSize));
		
		json.put("message", "true");
		json.put("pictures", pictures);
		
		return "ReturnAjax";
		
	}
	
	
	/*加载更多*/
	public String findPage(){
		
		if (StringUtil.isEmpty(page) || !page.matches("[0-9]+")) {
			page = "1";
		}
		
		System.out.println("page:"+page+",search_content:"+search_content);
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), pageSize);
		
		pictures = pDao.findLike("From Suishoupai where (userName like ? or noId like ?) and status=1 order by poll desc", new Object[]{search_content,search_content}, pageBean);
		
		System.out.println("pictures:"+pictures.size());
		
		if(pictures==null || pictures.size()==0){
			json.put("message", "false");
		}else {
			json.put("message", "true");
			json.put("pictures", pictures);
		}
		
		return "ReturnAjax";
	}
	
	
	public boolean JudegeLogin(User user){
		
		if (user == null) {

			HttpServletRequest request = ServletActionContext.getRequest();
			String reqPamrs = request.getQueryString();
			String requestUrl = request.getRequestURI()
					+ (reqPamrs == null ? "" : "?" + reqPamrs);

			int position = requestUrl.indexOf("/", 2);

			// url = url.replace("/wxtp", "");

			requestUrl = requestUrl.substring(position, requestUrl.length());

			targetUrl = requestUrl;
			System.out.println("UserLoginInterceptor-targetUrl:" + targetUrl);
			return false;
		}

		return true;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}


	public Suishoupai getPicture() {
		return picture;
	}

	public void setPicture(Suishoupai picture) {
		this.picture = picture;
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}

	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public String getSearch_content() {
		return search_content;
	}

	public void setSearch_content(String search_content) {
		this.search_content = search_content;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
