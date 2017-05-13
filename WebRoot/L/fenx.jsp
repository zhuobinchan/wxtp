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
    <title>分享投票</title>

</head>
<body >
    
        <div class="bj">
           <div style="padding-top:50px" align="center"><img src="images/bt.png" width="70%" ></div>
           <div align="center" class="zc_ps"><p>谢谢支持<strong>${team.name }</strong>，该队已获得<strong>${team.totalPoll }</strong>票</p></div>
           <div class="zc"><p>点击“分享支持狮队”<br>即刻参与激动人心的转盘大抽奖</p></div>
           
           <div class="an_new_02">
            <div align="center"  class="aniu_04"><a id="share_btn" href="#">分享支持狮队</a></div>
            <div align="center"  class="aniu_05"><a href="user_CheckRank">查看狮队排名</a></div>
            <div id="shareit">
            	<input type="hidden" value="${team.teamId }" id="teamId">
               <img class="arrow" src="images/share-it.png" width="60%">
             </div>
            <div style="clear:both"></div>
            <c:if test="${session.user.isSharePollTeamMessage==1 }">
            <div align="center"  class="aniu_03"><a href="${pageContext.request.contextPath}/choujiang/index.jsp">我要抽奖</a></div>
             </c:if>
           </div>
           
           
        </div>

<script src="${pageContext.request.contextPath}/user/js/jquery-1.11.1.js" type="text/javascript"></script>
<script>
  $("#share_btn").on("click", function() {
      $("#shareit").show();
  });
  $("#shareit").on("click", function(){
    $("#shareit").hide(); 
  });
</script>

</body>
<script src="${pageContext.request.contextPath}/L/js/properties.js" type="text/javascript"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="${pageContext.request.contextPath}/L/js/weixin.js" type="text/javascript"></script>
<script type="text/javascript">
wx.ready(function(){
	wx.onMenuShareTimeline({
	    title: "我支持'${team.name}'队伍,请支持我投票的狮队", // 分享标题
	    link: JS_SDK_Timeline.domainName+"/wxtp/L/user_GetTeamById?teamId=${team.teamId}", // 分享链接
	    imgUrl: JS_SDK_Timeline.domainName+"/wxtp/L/images/shareMessageImg.png", // 分享图标
	    success: function () { 
	        $.post("shareMessage_PollTeamToTimeline",
	        	function(result){
	        		if(result.message.trim()=="true"){
	        			alert("已经成功分享到朋友圈");
	        			window.location.href = JS_SDK_Timeline.domainName+"/wxtp/choujiang/index.jsp";
	        		}
	        		
	        	},"json");
	    },
	    cancel: function () { 
	        alert("要分享到朋友圈才能参与抽奖喔，亲");
	    }
	});
	wx.onMenuShareAppMessage({
	    title: "我参与狮王争霸活动", // 分享标题
	    link: JS_SDK_Timeline.domainName+"/wxtp/L/index.html", // 分享链接
	    imgUrl: JS_SDK_Timeline.domainName+"/wxtp/L/images/shareMessageImg.png", // 分享图标
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
});
</script>
</html>
