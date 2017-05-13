<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'uploadPicture.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">

	table{
		width: 100%;
	}
	#img{
		width: 150px;
		height: 150px;
	}
</style>
  </head>
  <body>

	<form action="userPicture_SavePicture" method="post">
	<table>
		<tr>
			<td colspan="2">
			<img src="" id="img" onclick="chooseImage()">
			<input type="hidden" id="imgUrl" value="" name="ssp.mediaId">
			</td>
			
		</tr>
		<tr>
			<td>姓名：</td>
			<td><input type="text" name="ssp.userName"/></td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td><input type="text" name="ssp.userTel"/></td>
		</tr>		
		<tr>
			<td colspan="2"><input type="submit" value="提交" ></td>
		</tr>
	</table>
	</form>
  </body>
<script src="${pageContext.request.contextPath}/user/js/jquery-1.11.1.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="${pageContext.request.contextPath}/user/wx/weixin.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/user/wx/uploadPicture.js" type="text/javascript"></script>
</html>
