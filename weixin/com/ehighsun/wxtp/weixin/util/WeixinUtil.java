package com.ehighsun.wxtp.weixin.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Formatter;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ehighsun.wxtp.util.StringUtil;
import com.ehighsun.wxtp.weixin.pojo.SNSUserInfo;
import com.ehighsun.wxtp.weixin.pojo.Token;
import com.ehighsun.wxtp.weixin.pojo.WeixinOauth2Token;
import com.ehighsun.wxtp.weixin.pojo.WeixinUserInfo;

public class WeixinUtil {

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（get、post）
	 * @param outputstr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	@SuppressWarnings("deprecation")
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputstr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据提交时
			if (null != outputstr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputstr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException e) {
			System.out.println(("Weixin server connection timed out."));
		} catch (Exception e) {
			System.out.println(("https request error:" + e.toString()));
		}
		return jsonObject;
	}

	// 拼接请求地址
	public final static String access_token_get_user_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	public static WeixinOauth2Token getOauth2Token(String appId,
			String appSecret, String code) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = access_token_get_user_url;
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInteger("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				Integer errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("获取网页授权凭证失败 errcode:{}" + errorCode
						+ " errmsg:{}" + errorMsg);
			}
		}
		return wat;
	}

	public final static String weixin_userInfo_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	public static WeixinUserInfo getWeixinUserInfo(String openId) {
		WeixinUserInfo weixinUserInfo = null;
		String requestUrl = weixin_userInfo_url;
		requestUrl = requestUrl.replace("ACCESS_TOKEN",
				TokenThread.token.getAccessToken()).replace("OPENID", openId);
		System.out.println("weixinUtil_ACCESS_TOKEN:"
				+ TokenThread.token.getAccessToken());
		System.out.println("weixinUtil_OPENID:" + openId);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		System.out.println(JSON.toJSONString(jsonObject, true));
		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
				weixinUserInfo.setSubscribe(jsonObject.getInteger("subscribe"));
				// 用户的标识，对当前公众号唯一
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 用户的昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
				weixinUserInfo.setSex(jsonObject.getInteger("sex"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
				weixinUserInfo
						.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
				weixinUserInfo.setSubscribeTime(jsonObject
						.getString("subscribe_time"));
				// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
				weixinUserInfo.setUnionId(jsonObject.getString("unionid"));
				// 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
				weixinUserInfo.setRemark(jsonObject.getString("remark"));
				// 用户所在的分组ID
				weixinUserInfo.setGroupId(jsonObject.getInteger("groupid"));

			} catch (Exception e) {
				weixinUserInfo = null;
				Integer errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("获取用户信息失败 errcode:{" + errorCode
						+ "} errmsg:{" + errorMsg + "}");
			}
			Integer errorCode = jsonObject.getInteger("errcode");
			if (StringUtil.isNotEmpty(errorCode)&&errorCode==40001) {
				System.out.println("errorCode:"+errorCode);
				refeshTokenThead();
			}
		}
		return weixinUserInfo;
	}

	public final static String sns_userInfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

	@SuppressWarnings({ "deprecation", "unchecked" })
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;
		// 拼接请求地址
		String requestUrl = sns_userInfo_url;
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
				"OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				// 用户的标识
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				snsUserInfo.setSex(jsonObject.getInteger("sex"));
				// 用户所在国家
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				snsUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				snsUserInfo.setPrivilegeList(JSONArray.parseArray(
						jsonObject.getString("privilege"), String.class));
			} catch (Exception e) {
				e.printStackTrace();
				snsUserInfo = null;
				Integer errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("获取用户信息失败 errcode:{" + errorCode
						+ "} errmsg:{" + errorMsg + "}");
			}
			Integer errorCode = jsonObject.getInteger("errcode");
			if (StringUtil.isNotEmpty(errorCode)&&errorCode==40001) {
				System.out.println("errorCode:"+errorCode);
				refeshTokenThead();
			}
		}
		return snsUserInfo;
	}

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取accessToekn
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密匙
	 * @return
	 */
	public static Token getToken(String appid, String appsecret) {
		Token token = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken((jsonObject.getString("access_token")));
				token.setExpiresIn(jsonObject.getInteger("expires_in"));
			} catch (JSONException e) {
				token = null;
				// 获取token失败
				System.out.println(("获取token失败 errcode:"+ jsonObject.getInteger("errcode") + " errmsg:" + jsonObject.getString("errmsg")));
			}
		}
		return token;
	}

	// 获取JSAPI_Ticket
	public static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * 获取jsapi_ticket
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String JSApiTIcket(String accessToken) {
		int result = 0;
		String jsApiTicket = null;
		// 拼装创建菜单Url
		String url = jsapi_ticket_url.replace("ACCESS_TOKEN",
				TokenThread.token.getAccessToken());
		// 调用接口获取jsapi_ticket
		JSONObject jsonObject = httpRequest(url, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				jsApiTicket = jsonObject.getString("ticket");
			} catch (JSONException e) {
				if (0 != jsonObject.getInteger("errcode")) {
					result = jsonObject.getInteger("errcode");
					System.out
							.println(("JSAPI_Ticket获取失败 errcode:"
									+ jsonObject.getInteger("errcode")
									+ " errmsg:" + jsonObject
									.getString("errmsg")));
				}
			}
		}
		return jsApiTicket;
	}

	/**
	 * sha1加密
	 * 
	 * @param str
	 * @return
	 */
	public static String sha1Encrypt(String str) {
		String signature = null;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(str.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signature;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static final String download_media_url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 从微信服务器下载多媒体文件
	 * 
	 * @author qincd
	 * @date Nov 6, 2014 4:32:12 PM
	 * @return 返回保存路径
	 */
	public static String downloadMediaFromWx(String mediaId, String fileSavePath)
			throws IOException {
		if (StringUtil.isEmpty(TokenThread.token.getAccessToken())
				|| StringUtil.isEmpty(mediaId))
			return null;

		String requestUrl = download_media_url.replace("ACCESS_TOKEN",
				TokenThread.token.getAccessToken())
				.replace("MEDIA_ID", mediaId);// 拼接URL
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		InputStream in = conn.getInputStream();

		File dir = new File(fileSavePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!fileSavePath.endsWith("/")) {
			fileSavePath += "/";
		}

		/*
		 * savePath:服务器的绝对路径，物理路径,E:.... fileSavePath:保存到数据的相对路径
		 */
		String savePath = ServletActionContext.getServletContext().getRealPath(
				"/");
		savePath = savePath + fileSavePath;

		String ContentDisposition = conn.getHeaderField("Content-disposition");
		System.out.println("ContentDisposition:" + ContentDisposition);
		String weixinServerFileName = ContentDisposition.substring(
				ContentDisposition.indexOf("filename") + 10,
				ContentDisposition.length() - 1);

		/* 添加素材的名称 */
		// fileSavePath += weixinServerFileName;
		savePath += weixinServerFileName;

		System.out.println("savePath:" + savePath);

		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(savePath));
		byte[] data = new byte[1024];
		int len = -1;

		while ((len = in.read(data)) != -1) {
			bos.write(data, 0, len);
		}

		bos.close();
		in.close();
		conn.disconnect();

		// /*保存到数据库时添加上项目的名称*/
		// fileSavePath =
		// ServletActionContext.getServletContext().getContextPath() +
		// fileSavePath;

		/* 返还的是从微信服务器下载后的素材文件名 */
		return weixinServerFileName;
	}

	// 当code代码为40001时重新获取accesstoken
	public static void refeshTokenThead() {
		// 在token失效后，立刻获取新的token并且把token放到线程的缓存中
		System.out.println("在catch体内重新获取accessToken令牌：");
		System.out.println(Calendar.getInstance().getTime());
		Token token = WeixinUtil.getToken(TokenThread.appid,
				TokenThread.appsecret);
		String jsapi_ticket = WeixinUtil.JSApiTIcket(token.getAccessToken());
		System.out.println("catch体内的accessToken：" + token.getAccessToken());
		TokenThread.setToken(token);
		TokenThread.setJsapi_ticket(jsapi_ticket);
	}
}
