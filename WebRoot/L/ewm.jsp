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
    <title>关注二维码</title>


</head>
<body >
    
        <div class="bj">
           <div style=" min-height:500px; padding-top:50px" align="center"><img src="${pageContext.request.contextPath}/L/images/ewm.png" width="70%" ></div>
        </div>

</body>
<script src="js/properties.js" type="text/javascript"></script>
<script src="js/jquery-1.11.1.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="js/weixin.js" type="text/javascript"></script>
<script type="text/javascript">
wx.hideOptionMenu();
</script>
</html>
