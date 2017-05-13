package com.ehighsun.wxtp.user.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.Suishoupai;
import com.ehighsun.wxtp.pojo.User;
import com.ehighsun.wxtp.util.StringUtil;
import com.ehighsun.wxtp.weixin.util.WeixinUtil;

public class UploadPictureAction {

	private Suishoupai ssp = new Suishoupai();
	private String targetUrl;
	
	private String userName;
	private String userTel;
	private String zpName;
	private String imgUrl;
	
	private Map<String, Object> json = new HashMap<String, Object>();
	
	@Resource(name="baseDao")
	private BaseDao<Suishoupai> sspDao;
	
	public String SavePicture(){

		User user = (User) ServletActionContext.getContext().getSession().get("user");		
		
		/*判断用户是否有上传过图片*/
		if(user.getIsUploadPhotoShuiShouPai()==1){
			
			json.put("message", "hadUpload");
			return "ReturnAjax";
			
		}
		if (!JudegeLogin(user)) {
			return "userNoLogin";
		}		
		
		
		ssp.setUserName(userName);
		ssp.setUserTel(userTel);
		ssp.setZpName(zpName);
		ssp.setMediaId(imgUrl);
		
		ssp.setCreateTime(new Timestamp(System.currentTimeMillis()));
		ssp.setStatus("1");
		ssp.setPoll(0);
		ssp.setNickName(user.getNickname());
		ssp.setOpenId(user.getOpenId());
		
		if(StringUtil.isNotEmpty(ssp.getMediaId())){

//			ServletActionContext.getServletContext().getRealPath("/");
			try {
				String sourceName = WeixinUtil.downloadMediaFromWx(ssp.getMediaId(), "/resource/images");
				ssp.setImgUrl("/resource/images/"+sourceName);
			} catch (IOException e) {
				System.out.println("图片下载到服务器异常，以下是异常情况。");
				e.printStackTrace();
				
				json.put("message", "fwqyc");
				return "ReturnAjax";
			}
			
		}

		try {
			sspDao.save(ssp);
			sspDao.executeSql("update suishoupai set noId=concat('NO', id) where openId='"+user.getOpenId()+"'");
			sspDao.executeSql("update user set isUploadPhotoShuiShouPai=1 where uid="+user.getUid());
			
			user.setIsUploadPhotoShuiShouPai(1);		
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "fwqyc");
			return "ReturnAjax";
		}

		ServletActionContext.getContext().getSession().put("user", user);
		json.put("message", "true");
		
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
	
	public String pollPicture(){
		
		return "pollPicture";
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public String getZpName() {
		return zpName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public void setZpName(String zpName) {
		this.zpName = zpName;
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
