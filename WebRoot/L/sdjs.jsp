<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link href="${pageContext.request.contextPath}/L/css/css.css" rel="stylesheet"/>
    <title>狮队介绍</title>


</head>
<body >
    
        <div class="bj">
           <div class="sdjs" align="center">
            <div class="sdjs_01" align="left" style="width:80%;"><strong>${team.name }</strong>
            <p style="margin-top: 10px">${team.introduction }</p></div>
           
           </div>
           
           <c:if test="${session.user.pollStatus=='0' }">
            <div class="an_new">
            	<div align="center"  class="aniu_03"><a href="user_AddPoll?teamId=${team.teamId }">我要投票</a></div>
           	</div>
           </c:if>
           
           <c:if test="${session.user.pollStatus=='1' }">
           <div class="an_new">
            <div align="center"  class="aniu_03"><a href="user_AddPoll?teamId=${team.teamId }" style="background:#2A2A2A">我的投票情况</a></div>
           </div>
           </c:if>
        </div>

</body>
<script src="${pageContext.request.contextPath}/L/js/properties.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/user/js/jquery-1.11.1.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="${pageContext.request.contextPath}/L/js/weixin.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/L/js/fenx.js" type="text/javascript"></script>
</html>
