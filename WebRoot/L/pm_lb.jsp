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
    <title>排名列表</title>


</head>
<body >
    
        <div class="bj">
           <div style=" padding-top:50px;" align="center"><img src="${pageContext.request.contextPath}/L/images/pm_top.png" width="60%" ></div>
           <div style="width:100%; ">
            <div class="ctd" style="margin-top: 10px">
              <ul>
              	<c:forEach items="${teams }" var="team">
               		<li><div class="dm_01"><a href="user_GetTeamById?teamId=${team.teamId }"><img style="width: 50px;height: 50px" src="<%=path%>/${team.teamLogo}"/><p>${team.name }</p></a></div><div class="pm">${team.totalPoll }票</div></li>
               </c:forEach>
               
               
              </ul>
            </div>
      
        </div>

</body>
<script src="${pageContext.request.contextPath}/L/js/properties.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/user/js/jquery-1.11.1.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="${pageContext.request.contextPath}/L/js/weixin.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/L/js/fenx.js" type="text/javascript"></script>
</html>

