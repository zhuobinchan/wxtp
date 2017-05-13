package com.ehighsun.wxtp.util;

import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author
 * 
 */
public class StringUtil {

	/**
	 * 判断是否是空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否不是空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if ((str != null) && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(Integer integer) {
		if ((integer != null) && !"".equals(integer.toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到URL中字符串参数，只适合URL中有一个参数
	 * 
	 * @param url
	 * @return
	 */
	public static String getParamFromUrl(String url) {
		String afterQuestion = url.substring(url.indexOf("?") + 1);
		return afterQuestion.substring(afterQuestion.indexOf("=") + 1);
	}

	public static String md5(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(message.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String[] ToArray(String lableNames) {
		return lableNames.split(",");
	}
	
	public static boolean isContains(String sourceString,String[] strings) {
		if(strings!=null&&strings.length!=0&&StringUtil.isNotEmpty(sourceString)){
			for (String string : strings) {
				if (!sourceString.contains(string)) {
					return false;
				}
			}
		}else {
			return false;
		}
		return true;
	}
	
	
	//"yyyy-MM-dd"是否这种日期格式
	public static boolean isDate(String s) {
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(s);

		boolean dateType = mat.matches();

		return dateType;
	}

}
