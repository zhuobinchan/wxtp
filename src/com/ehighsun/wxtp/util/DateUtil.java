package com.ehighsun.wxtp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}
	
	/*计算当前年份差,仅仅年相减*/
	public static int calculateAge(String birthDay) throws ParseException{
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String d1 = df.format(new Date());
		
		Date date2 = df.parse(birthDay);
		String d2 = df.format(date2);

		return Integer.parseInt(d1)-Integer.parseInt(d2);
	}
}
