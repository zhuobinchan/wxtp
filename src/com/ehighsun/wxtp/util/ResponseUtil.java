package com.ehighsun.wxtp.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	public static void write(HttpServletResponse response, Object o) {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(o.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}

	}
}
