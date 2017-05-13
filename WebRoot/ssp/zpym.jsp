<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>上传</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" href="css/css02.css" type="text/css" />
</head>
<body>
	<div class="wrap_zpym">
		<div align="center"><img src="/wxtp/${picture.imgUrl }" width=70% style="margin:55px 0 20px 0;" /></div>
		<div class="text_zpym">
			<p>作品名称&nbsp;:<span>&nbsp;${picture.zpName}</span></p>
			<p>作品编号&nbsp;:<span>&nbsp;${picture.noId}</span></p>
			<p>作品票数&nbsp;:<span>&nbsp;${picture.poll}</span></p>
		</div>
		<div align="center">
			<a href="#"><input type="button" value="投票" class="btn_zpym" id="tp" /></a>
		</div>
         <div align="center">
			<a href="#" onclick="javaScript:window.location.href='/wxtp/ssp/ymlb.html'"><input style=" margin-top:5px;" type="button" value="返回" class="btn_zpym" /></a>
		</div>
		
		       
		<div align="center">
			<img src="images/foot.png" alt="" width=80% id="zpym_img" />
		</div>
		
	</div>
    
    <input type="hidden" id="picId" value="${picture.id}"/>
</body>
<script src="js/jquery-1.11.1.js" type="text/javascript"></script>
<script>

	$(document).ready(function(){
	
		$("#tp").click(function(){
		
			$.post("userPicture_PollPicture",{
	        	picId:$("#picId").val()
	        },function(result){
	        
	        	if(result.message.trim()=="true"){
	        		alert("投票成功，您还可以投"+result.pollTimes+"票。");
	        		window.location.reload(true);
	        	}else if(result.message.trim()=="false"){
	        		 alert("你的投票次数已经用完，请明天再来投票。");
	        	 	 window.location.href="/wxtp/ssp/ymlb.html";
	        	 }
	        	
	        });
		
		});
	
	});
	
</script>

<script src="${pageContext.request.contextPath}/L/js/properties.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="${pageContext.request.contextPath}/L/js/weixin.js" type="text/javascript"></script>

<script type="text/javascript">
wx.ready(function(){
	wx.onMenuShareTimeline({
	    title: "我参与随手拍活动,我的作品是《${picture.zpName}》,作品编号是'${picture.noId}',请大家踊跃给我的作品投票", // 分享标题
	    link: JS_SDK_Timeline.domainName+"/wxtp/ssp/userPicture_GetPicById?picId=${picture.id }", // 分享链接
	    imgUrl: JS_SDK_Timeline.domainName+"/wxtp/ssp/images/fenx_ssp.jpg", // 分享图标
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
	wx.onMenuShareAppMessage({
		title: "我参与随手拍活动,我的作品是《${picture.zpName}》,作品编号是'${picture.noId}',请大家踊跃给我的作品投票", // 分享标题
	    link: JS_SDK_Timeline.domainName+"/wxtp/ssp/userPicture_GetPicById?picId=${picture.id }", // 分享链接
	    imgUrl: JS_SDK_Timeline.domainName+"/wxtp/ssp/images/fenx_ssp.jpg", // 分享图标
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
});
</script>
</html>
