<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, width=device-width">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>技术部门</title>
<link href="${pageContext.request.contextPath}/user/poll/css/poll.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/user/js/jquery-1.11.1.js" type="text/javascript"></script>
</head>

<body>

<div class="pneal">
	<div class="pneals active"><a href="#">技术</a></div>
	<div class="pneals"><a href="#">部门</a></div>
	<div class="clear"></div>
</div>

<div class="pneanin dome1">
<!-- 	<div class="blistheader">部门</div> -->
<!-- 	<div class="blist"> -->
		
<!-- 		<ul class="blistin"> -->
<!-- 			<li class="sactive"><a href="#">销售</a></li> -->
<!-- 			<li><a href="#">技术</a></li> -->
<!-- 			<li><a href="#">优化</a></li> -->
<!-- 			<li><a href="#">行政</a></li> -->
<!-- 			<li><a href="#">财务</a></li> -->
<!-- 			<li><a href="#">人事</a></li> -->
<!-- 			<li><a href="#">销售</a></li> -->
<!-- 			<li><a href="#">销售</a></li> -->
<!-- 			<li><a href="#">销售</a></li> -->
<!-- 			<li><a href="#">销售</a></li> -->
<!-- 		</ul> -->
<!-- 	</div> -->
	<div class="listopen">
		<div class="listopenin">
			<div class="bigbox">
			
<!-- 				<div class="bigboxin Green"> -->
<!-- 					<div class="bigboxh">李四</div> -->
<!-- 					<div class="boxb">50%</div> -->
<!-- 					 <div class="boxbtn"><input type="button" value="支持"></div> -->
<!-- 					 <div class="alertbox"> -->
<!-- 						<div class="box-text ">评价成功</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="bigboxin Amethyst"> -->
<!-- 					<div class="bigboxh">小明</div> -->
<!-- 					<div class="boxb">50%</div> -->
<!-- 					 <div class="boxbtn"><input type="button" value="支持"></div> -->
<!-- 					 <div class="alertbox"> -->
<!-- 						<div class="box-text ">评价成功</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="bigboxin Belize"> -->
<!-- 					<div class="bigboxh">小红</div> -->
<!-- 					<div class="boxb">50%</div> -->
<!-- 					 <div class="boxbtn"><input type="button" value="支持"></div> -->
<!-- 					 <div class="alertbox"> -->
<!-- 						<div class="box-text ">评价成功</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="bigboxin lv"> -->
<!-- 					<div class="bigboxh">小亮</div> -->
<!-- 					<div class="boxb">50%</div> -->
<!-- 					 <div class="boxbtn"><input type="button" value="支持"></div> -->
<!-- 					 <div class="alertbox"> -->
<!-- 						<div class="box-text ">评价成功</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="bigboxin fen"> -->
<!-- 					<div class="bigboxh">丁武</div> -->
<!-- 					<div class="boxb">50%</div> -->
<!-- 					 <div class="boxbtn"><input type="button" value="支持"></div> -->
<!-- 					 <div class="alertbox"> -->
<!-- 						<div class="box-text ">评价成功</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
getAllTeams();

function getAllTeams(){
	$.post("user_GetAllTeams",
	function(result) {
		if (result.message.trim()=="true") {
			
			var bigbox = $(".bigbox");
			$(".bigbox").html("");
			
			var total = 0;
			
			for(var i=0;i<result.teams.length;i++){
				total = total + result.teams[i].totalPoll;
			}
			
			for(var i=0;i<result.teams.length;i++){
				var poll = (Math.round(result.teams[i].totalPoll / total * 100) + "%");
				bigbox.append('<div class="bigboxin">'
							 +'<div class="bigboxh" >'+result.teams[i].name+'</div>'
							 +'<div class="boxb">'+poll+'</div>'
						     +'<div class="boxbtn"><input type="button" value="支持" onclick="AddPoll('+result.teams[i].teamId+')"></div>'
							 +'<div class="alertbox">'
							 +'<div class="box-text ">投票成功</div></div></div>');			
			}
		
		} else {
				alert("服务器出问题了，请刷新！");
		}
		
		
		
		$(".boxbtn").each(function(index) {
			$(".boxbtn").eq(index).find("input").click(function(){
				$(".alertbox").eq(index).addClass("thua");
				setTimeout(function(){
					$(".alertbox").eq(index).removeClass("thua");;
				},1500)
			})
		});
	},"json");
}

function AddPoll(teamId){
	$.post("user_AddPoll",{
		teamId:teamId
	},function(result) {
		if (result.message.trim()=="true") {
			setTimeout(function(){getAllTeams();}, 1500); 
			
		} else {
				alert("服务器出问题了，请刷新！");
		}
		
	},"json");

}

</script>
<div style="text-align:center;">
</div>
</body>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script><!-- jssdk导入的js -->
<script src="${pageContext.request.contextPath}/user/wx/weixin.js" type="text/javascript"></script>
<script type="text/javascript">
wx.ready(function(){
	wx.onMenuShareTimeline({
	    title: '分享到朋友圈', // 分享标题
	    link: 'http://zhuobinchan.tunnel.2bdata.com/wxtp/user/uploadPicture.jsp', // 分享链接
	    imgUrl: '', // 分享图标
	    success: function () { 
	        $.post("shareMessage_PollTeamToTimeline",
	        	function(result){
	        		if(result.message.trim()=="true"){
	        			alert("已经成功分享到朋友圈");
	        		}
	        		
	        	},"json");
	    },
	    cancel: function () { 
	        alert("要分享到朋友圈才能参与抽奖喔，亲");
	    }
	});
});
</script>
</html>
