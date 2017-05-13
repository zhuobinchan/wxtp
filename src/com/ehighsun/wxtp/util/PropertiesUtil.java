package com.ehighsun.wxtp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

public class PropertiesUtil {

	public static Properties getProperites(String filePath) throws IOException{
		FileInputStream fis = new FileInputStream(filePath);
		Properties p = new Properties();
		p.load(fis);
		return p;
	}
	
}
