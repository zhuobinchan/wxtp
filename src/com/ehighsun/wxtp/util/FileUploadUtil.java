package com.ehighsun.wxtp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.UUID;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class FileUploadUtil {

	// 上传文件
	public String updateFile(File file, String fileName,String folderName) {
		String houzhui = fileName.substring(fileName.indexOf('.'));
		String randomDir = generateRandomDir(UUID.randomUUID().toString());
		String path = ServletActionContext.getServletContext().getRealPath(
				"/"+folderName+"/" + randomDir);
		String saveName = UUID.randomUUID().toString();
		String savePath = folderName+"/"+randomDir + "/" + saveName + houzhui;
		File result = new File(path, saveName + houzhui);
		try {
			path = result.getCanonicalPath();
			System.out.println(path);
			FileUtils.copyFile(file, result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savePath;
	}
	
	// 上传文件
	public String updateSmbFile(File file, String fileName,String folderName) {
		String houzhui = fileName.substring(fileName.indexOf('.'));
		String randomDir = generateRandomDir(UUID.randomUUID().toString());
		
		String path = ServletActionContext.getServletContext().getRealPath(
				"/"+folderName+"/" + randomDir);
		
		String saveName = UUID.randomUUID().toString();
		
		String savePath = folderName+"/"+randomDir + "/" + saveName + houzhui;

		String smbFolderUrl = "smb://shixiya:abc123.@192.168.18.11/shixiya/resource/"+folderName+"/"+randomDir;
		String smbUrl = "smb://shixiya:abc123.@192.168.18.11/shixiya/resource/"+folderName+"/"+randomDir+"/"+saveName+houzhui;
//		String smbUrl = "smb://shixiya:abc123.@192.168.18.11/resource/"+folderName+"/"+fileName;
		try {
			
			SmbFile smbFolderFile = new SmbFile(smbFolderUrl);
			if (!smbFolderFile.exists()) {
				smbFolderFile.mkdirs();
			}
			SmbFile smbFile = new SmbFile(smbUrl);
			CopyRecourse(file,smbFile);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "http://sxyres.000861.com/shixiya/resource/"+savePath;
	}
	
	public static void DelRemoteResource(String resUrl){
		
		String remoteUrl = "smb://shixiya:abc123.@192.168.18.11/"+resUrl;
		
		try {
			
			SmbFile remoteRe = new SmbFile(remoteUrl);
			if(remoteRe.isFile() && remoteRe.exists()){
				remoteRe.delete();
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		}
		
	}
	
	public void CopyRecourse(File file,SmbFile remoteFile){
      InputStream in = null ;  
      OutputStream out = null ;  
	  try {
		in = new  BufferedInputStream( new  FileInputStream(file));
		out = new  BufferedOutputStream( new  SmbFileOutputStream(remoteFile));  
		byte [] buffer =  new   byte [ 1024 ];  
		while  (in.read(buffer) != - 1 ) {  
			 out.write(buffer);  
		     buffer = new   byte [ 1024 ];  
		}    
			  
	  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (SmbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } finally  {  
	   
		    try {
				out.close();
			    in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}    

	  }   
		
	  
	  
	  
	}

	// 分目录进行保存
	public String generateRandomDir(String uuidFileName) {
		// 获得唯一文件名的hashcode
		int hashcode = uuidFileName.hashCode();
		// 获得一级目录
		int d1 = hashcode & 0xf;
		// 获得二级目录
		int d2 = (hashcode >>> 4) & 0xf;

		return d2 + "/" + d1;// 共有256目录l
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		if (StringUtil.isNotEmpty(fileName)) {
			File file = new File(fileName);
			if (file.isFile() && file.exists()) {
				file.delete();
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			return false;
		}

	}

}
