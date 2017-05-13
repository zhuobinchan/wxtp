<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<title>地推奖品</title>
<script src="${pageContext.request.contextPath}/L/js/jquery-1.11.1.js"></script>
<style>
*{padding:0; margin:0;}
.bj_tx{ font-size:12px; min-height:600px; width:100%; height:100%; background:url(images/bj.jpg) no-repeat; background-size:100% 100%}
.tj{ margin-top:10px;}
.tj a{ color:#fff;  background:#ff7906; height:40px;border-radius: 5px;-webkit-border-radius:5px;-moz-border-radius:5px; text-decoration:none; width:60%; display:block; line-height:40px;}

.jpxx{ width:75%; margin:0 auto; line-height:20px;}
.jpxx li{ border:2px solid #fff;list-style:none;padding:5px 10px; }
.xinm, .lxdh, .zjjp, .lqzt{ width:50%; float:left;}
.x_bt{ color:#ff9e19; font-weight:bold;overflow:hidden; text-overflow:ellipsis; white-space:nowrap;}
.x_nr{ overflow:hidden; text-overflow:ellipsis; white-space:nowrap;}
.input_ymlb{ width: 73%;height: 35px;margin-top: 20px;color: #ccc;border: none;padding-right:18%;padding-left:7%;border-radius: 8px;
-webkit-border-radius:8px;-moz-border-radius:8px;font-size: 15px;}
</style>
</head>

<body>

<div class="bj_tx">
<div style="padding-top:50px;" align="center"><img src="images/sy_04.png" width="100%" /></div>
<div style="padding-top:30px; " align="center"><img src="images/xinx_dt.png" width="70%"  /></div>
<div style="height:56px; width:77%; margin:0 auto 15px" align="center">
	<input type="text" placeholder="请输入选手姓名和选手编号"  class="input_ymlb" />
    <a href="#"><img style="position:relative; top:-33px; left:40%;" src="images/ss.png" id="search_btn"></a>
</div>
<div class="jpxx" align="center">
  <div class="xinm">
   <ul>
    <li class="x_bt">您的姓名</li>
   </ul>
  </div>
  <div class="lxdh">
   <ul>
    <li class="x_bt">联系电话</li>
   </ul>
  </div>
</div>
<div style="clear:both;"></div>

</div>



<script type="text/javascript">
	$(document).ready(function(){
	
		$("#search_btn").click(function(){
		
			$(".jpxx .x_nr").each(function(){
				$(this).remove();
			});
				
				
			$.post("getAwardList_GetOtherAward2",{
				dtString:$(".input_ymlb").val()
			},function(result){
			
				if(result.message.trim()=="true"){
					for(var i =0 ;i<result.awardList.length;i++){
						$(".xinm ul").append('<li class="x_nr">'+result.awardList[i].name+'</li>');
						$(".lxdh ul").append('<li class="x_nr">'+result.awardList[i].telephone+'</li>');
					}
				}else if(result.message.trim()=="false"){
					alert("查不到信息。");
				}
				
			});
		
		
		});
	

	});
</script>
</body>
</html>
