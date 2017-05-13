package com.ehighsun.wxtp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ehighsun.wxtp.pojo.User;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class WriteInExcelUtil {
	public static File ExportUserByTeamId(List<User> users,String tempPath) {
		try {
			WritableWorkbook wwb = null;
			// 创建可写入的Excel工作簿

			File file = new File(tempPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			// 以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);
			// 创建工作表
			WritableSheet ws = wwb.createSheet("选中最佳人气狮队用户信息", 0);
			// 查询数据库中所有的数据
			// 要插入到的Excel表格的行号，默认从0开始
						
			
			List<Label> titleLabels = new ArrayList<Label>();
			titleLabels.add(new Label(0, 0, "用户编号(uid)"));
			
			titleLabels.add(new Label(1, 0, "昵称(nickname)"));
			titleLabels.add(new Label(2, 0, "微信用户标识(openId)"));
			titleLabels.add(new Label(3, 0, "性别(sex)"));
			titleLabels.add(new Label(4, 0, "国家(country)"));
			titleLabels.add(new Label(5, 0, "省份(province)"));
			titleLabels.add(new Label(6, 0, "城市(city)"));
			titleLabels.add(new Label(7, 0, "头像链接(headImgUrl)"));
			titleLabels.add(new Label(8, 0, "电话(telephone)"));
			titleLabels.add(new Label(9, 0, "姓名(name)"));

			for (Label label : titleLabels) {
				ws.addCell(label);
			}

			List<Label> dataLabels = new ArrayList<Label>();

			for (int i = 0; i < users.size(); i++) {
				dataLabels.add(new Label(0, i + 1, String.valueOf(users.get(i).getUid())));
				dataLabels.add(new Label(1, i + 1, users.get(i).getNickname()));
				dataLabels.add(new Label(2, i + 1, users.get(i).getOpenId()));
				dataLabels.add(new Label(3, i + 1, users.get(i).getSex()==0?"女":"男"));
				dataLabels.add(new Label(4, i + 1, users.get(i).getCountry()));
				dataLabels.add(new Label(5, i + 1, users.get(i).getProvince()));
				dataLabels.add(new Label(6, i + 1, users.get(i).getCity()));
				dataLabels.add(new Label(7, i + 1, users.get(i).getHeadImgUrl()));
				dataLabels.add(new Label(8, i + 1, users.get(i).getTelephone()));
				dataLabels.add(new Label(9, i + 1, users.get(i).getName()));
			}

			for (Label label : dataLabels) {
				ws.addCell(label);
			}
			// 写进文档
			CellView cellView = new CellView();
			ws.setColumnView(0, cellView);
			wwb.write(); // 关闭Excel工作簿对象
			wwb.close();
			return file;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
