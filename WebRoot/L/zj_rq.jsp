<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<title>获奖信息</title>
<script src="${pageContext.request.contextPath}/L/js/jquery-1.11.1.js"></script>
<style>
*{padding:0; margin:0;}
.bj_tx{ min-height:600px; width:100%; height:100%; background:url(images/bj.jpg) no-repeat; background-size:100% 100%}

.tj{ margin-top:10px;}
.tj a{ color:#fff;  background:#ff7906; height:40px;border-radius: 5px;-webkit-border-radius:5px;-moz-border-radius:5px; text-decoration:none; width:60%; display:block; line-height:40px;}

.jpxx{ width:75%; margin:0 auto; line-height:20px;}
.jpxx li{ border:2px solid #fff;list-style:none;padding:5px 10px; }
.djm, .jpmc{ width:50%; float:left;}
.ljzt{ width:100%}
.x_bt{ color:#ff9e19; font-weight:bold;overflow:hidden; text-overflow:ellipsis; white-space:nowrap;}
.x_nr{ overflow:hidden; text-overflow:ellipsis; white-space:nowrap;}
</style>
</head>

<body>

<div class="bj_tx">
<div style="padding-top:50px;" align="center"><img src="images/sy_04.png" width="100%" /></div>
<div style="padding-top:30px; margin-bottom:20px;" align="center"><img src="images/xinx.png" width="60%" /></div>
<div class="bgbg">
<div class="jpxx" align="center">
  <div class="djm">
   <ul>
    <li class="x_bt">兑奖码</li>
   </ul>
  </div>
  <div class="jpmc">
   <ul>
    <li class="x_bt">奖品</li>
   </ul>
  </div>
</div>
<div style="clear:both;"></div>
<div class="jpxx" align="center">
  <div class="ljzt">
   <ul>
    <li class="x_bt">领奖状态</li>
   </ul>
  </div>
  </div>
 </div>

<c:if test="${session.user.isWinAward==1 }">
	<div align="center" class="tj" id="ljqr"><a href="#">现场领奖确认</a></div>
</c:if>
<div style="padding:40px 0" align="center"><img src="images/foot_zb.png" width="70%"></div>
  
</div>
<div style="clear:both;"></div>




<script type="text/javascript">
	$(document).ready(function(){
		$.post("getAwardList_getOtherAwardListByOpenId",function(result){
			if(result.message.trim()=="true"){
				for(var i =0 ;i<result.awardList.length;i++){
					$(".djm ul").append('<li class="x_nr">'+result.awardList[i].myUnionId+'</li>');
					$(".jpmc ul").append('<li class="x_nr">'+result.awardList[i].awardName+'</li>');
					$(".ljzt ul").append('<li class="x_nr">'+(result.awardList[i].isGetAward==0?"未领奖":"已领奖")+'</li>');
				}
			}else if(result.message.trim()=="false"){
				$(".bgbg").html('<div align="center" style=" line-height:60px;font-size:20px">你没有中奖！</div>');
				$(".tj").hide();
					
			}
			
		});
		
		$("#ljqr").click(function(){
			if(confirm("是否到达领奖中心，并且确认领取奖品？")){
				$.post("getAwardList_confirmGetOtherAward",function(result){
					if(result.message.trim()=="true"){
						
						if(result.status.trim()=="isGet") alert("您已领取过奖品,谢谢参与。");
						if(result.status.trim()=="isGetSuccess") {
							alert("领取奖品成功,谢谢参与。");
							window.location.reload(true);
						}
					
					}else if(result.message.trim()=="false"){
						
						if(result.status.trim()=="noWinAward") alert("您并没有获奖，如有疑问请联系客服。");
						if(result.status.trim()=="isGetFail") alert("很抱歉，领奖失败，请联系客户咨询。");
						
					}
					
				});				
			}
		
		});
		
	});
</script>
</body>
</html>

