<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    <title>投票列表</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/L/js/jquery-1.11.1.js"></script>

</head>
<body >
    
        <div class="bj">
           <div style=" padding-top:50px;" align="center"><img src="images/tp_top.png" width="60%" ></div>
           <div style="width:100%; ">
            <div class="ctd" style="margin-top: 10px">
              <ul id="content_ul">
              </ul>
            </div>
           </div>
      
        </div>

</body>
<script type="text/javascript">

	$(document).ready(function(){
	
		$.post("user_GetAllTeams.action",
		function(result) {
		
			if(result.message.trim()=="true"){
				var content_ul = $("#content_ul");
				
				for(var i=0;i<result.teams.length;i++){
					content_ul.append('<li><div class="dm">'
					+'<a href="user_GetTeamById?teamId='+result.teams[i].teamId+'">'
					+'<img style="width:55px;height:55px" src="${pageContext.request.contextPath}/'+result.teams[i].teamLogo+'"/><p>'+result.teams[i].name+'</p></a></div>'
					+'<a class="tp_tu" href="user_GetTeamById?teamId='+result.teams[i].teamId+'"><img src="images/tp_st.png" width="55"/>'
					+'</a></li>');
				}
				
			}
		
		});	
	
	});

</script>
<script src="${pageContext.request.contextPath}/L/js/properties.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/user/js/jquery-1.11.1.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="${pageContext.request.contextPath}/L/js/weixin.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/L/js/fenx.js" type="text/javascript"></script>
</html>
