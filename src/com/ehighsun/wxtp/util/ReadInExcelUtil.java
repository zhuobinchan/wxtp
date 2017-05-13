package com.ehighsun.wxtp.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.ehighsun.wxtp.pojo.OtherAwardList;
import com.ehighsun.wxtp.pojo.OtherAwardList2;

public class ReadInExcelUtil {
	public static List<OtherAwardList> readAwardListsByExcel(File excel) {
		List<OtherAwardList> otherAwardLists = null;
		try {
			Workbook rwb = Workbook.getWorkbook(excel);
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			Integer clos=rs.getColumns();//得到所有的列
			Integer rows=rs.getRows();//得到所有的行
			System.out.println("clos:"+clos+"rows:"+rows);
			otherAwardLists = new ArrayList<>();
			for (int i = 1; i < rows; i++) { 
				
				for (int j = 0; j < clos; j++) {
					OtherAwardList otherAwardList = new OtherAwardList();
					otherAwardList.setName(rs.getCell(j++, i).getContents());//默认最左边编号也算一列 所以这里得j++
					otherAwardList.setTelephone(rs.getCell(j++,i).getContents().replaceAll(" ",""));
					otherAwardList.setAwardName(rs.getCell(j++,i).getContents().replaceAll(" ",""));
					otherAwardList.setOpenId(rs.getCell(j++,i).getContents().replaceAll(" ",""));
					otherAwardList.setMyUnionId(rs.getCell(j++,i).getContents().replaceAll(" ",""));
					otherAwardList.setIsGetAward(0);
					otherAwardLists.add(otherAwardList);
				}
				
			}
			
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return otherAwardLists;
	}
	
	public static List<OtherAwardList2> readAwardList2sByExcel(File excel) {
		List<OtherAwardList2> otherAwardList2s = null;
		try {
			Workbook rwb = Workbook.getWorkbook(excel);
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			Integer clos=rs.getColumns();//得到所有的列
			Integer rows=rs.getRows();//得到所有的行
			System.out.println("clos:"+clos+"rows:"+rows);
			otherAwardList2s = new ArrayList<>();
			OtherAwardList2 otherAwardList2 = null;
			for (int i = 1; i < rows; i++) { 
				for (int j = 0; j < clos; j++) {
					otherAwardList2 = new OtherAwardList2();
					otherAwardList2.setName(rs.getCell(j++, i).getContents());//默认最左边编号也算一列 所以这里得j++
					otherAwardList2.setTelephone(rs.getCell(j++,i).getContents().replaceAll(" ",""));
					otherAwardList2.setAwardName(rs.getCell(j++,i).getContents().replaceAll(" ",""));
					otherAwardList2.setIsGetAward(0);
					otherAwardList2s.add(otherAwardList2);
				}
			}
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return otherAwardList2s;
		
	}
}
