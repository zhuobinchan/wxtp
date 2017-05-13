<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>活动介绍</title>


</head>
<body >
 		<%
		   request.setAttribute("vEnter", "\r\n");   
		   request.setAttribute("nEnter", "<br>");
		%>    
        <div class="bj">
           <div class="hdjs" align="center">
            <div class="hdjs_01" align="left" style="width:80%;"><strong>活动介绍：</strong><p><br>
            ${fn:replace(competition.introduction, vEnter, nEnter) }</p></div>
            <div class="hdjs_02" align="left" style="width:80%;"><strong>活动规则：</strong><p><br>
			${fn:replace(competition.rule, vEnter, nEnter) }
			</p></div>
           </div>
 
           <div class="an_new">
            <div align="center"  class="aniu_01" id="share_btn"><a href="#">我要分享</a></div>
            <div align="center"  class="aniu_02"><a href="tp_lb.jsp">我要投票</a></div>
           </div> 
              <div id="shareit">
               <img class="arrow" src="images/share-it.png" width="60%">
               </div>          
        </div>

</body>
<script src="${pageContext.request.contextPath}/L/js/properties.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/user/js/jquery-1.11.1.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="${pageContext.request.contextPath}/L/js/weixin.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/L/js/fenx.js" type="text/javascript"></script>
<script>
  $("#share_btn").on("click", function() {
      $("#shareit").show();
  });
  $("#shareit").on("click", function(){
    $("#shareit").hide(); 
  });
</script>
</html>

