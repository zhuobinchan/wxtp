<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
html{overflow-y:scroll;vertical-align:baseline;}
body{font-family:Microsoft YaHei,Segoe UI,Tahoma,Arial,Verdana,sans-serif;font-size:12px;color:#fff;height:100%;line-height:1;background:#999}
*{margin:0;padding:0}
ul,li{list-style:none}
h1{font-size:30px;font-weight:700;text-shadow:0 1px 4px rgba(0,0,0,.2)}
.login-box{width:410px;margin:120px auto 0 auto;text-align:center;padding:30px;background:url(images/mask.png);border-radius:10px;}
.login-box .name,.login-box .password,.login-box .code,.login-box .remember{font-size:16px;text-shadow:0 1px 2px rgba(0,0,0,.1)}
.login-box .remember input{box-shadow:none;width:15px;height:15px;margin-top:25px}
.login-box .remember{padding-left:40px}
.login-box .remember label{display:inline-block;height:42px;width:70px;line-height:34px;text-align:left}
.login-box label{display:inline-block;width:100px;text-align:right;vertical-align:middle}
.login-box #imageCode{width:120px}
.login-box .codeImg{float:right;margin-top:26px;}
.login-box img{width:140px;height:42px;border:none}
input[type=text],input[type=password]{width:270px;height:42px;margin:12.5px auto;padding:0px 15px;border:1px solid rgba(255,255,255,.15);border-radius:6px;color:#fff;letter-spacing:2px;font-size:16px;background:transparent;}
button{cursor:pointer;width:100%;height:44px;padding:0;background:#ef4300;border:1px solid #ff730e;border-radius:6px;font-weight:700;color:#fff;font-size:24px;letter-spacing:15px;margin-top:10px; text-shadow:0 1px 2px rgba(0,0,0,.1)}
input:focus{outline:none;box-shadow:0 2px 3px 0 rgba(0,0,0,.1) inset,0 2px 7px 0 rgba(0,0,0,.2)}
button:hover{box-shadow:0 15px 30px 0 rgba(255,255,255,.15) inset,0 2px 7px 0 rgba(0,0,0,.2)}
.screenbg{position:fixed;bottom:0;left:0;z-index:-999;overflow:hidden;width:100%;height:100%;min-height:100%;}
.screenbg ul li{display:block;list-style:none;position:fixed;overflow:hidden;top:0;left:0;width:100%;height:100%;z-index:-1000;float:right;}
.screenbg ul a{left:0;top:0;width:100%;height:100%;display:inline-block;margin:0;padding:0;cursor:default;}
.screenbg a img{vertical-align:middle;display:inline;border:none;display:block;list-style:none;position:fixed;overflow:hidden;top:0;left:0;width:100%;height:100%;z-index:-1000;float:right;}
.bottom{margin:8px auto 0 auto;width:100%;position:fixed;text-align:center;bottom:0;left:0;overflow:hidden;padding-bottom:8px;color:#ccc;word-spacing:3px;zoom:1;}
.bottom a{color:#FFF;text-decoration:none;}
.bottom a:hover{text-decoration:underline;}
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/jquery-1.11.1.js"></script>
<script type="text/javascript">
$(function(){
	$(".screenbg ul li").each(function(){
		$(this).css("opacity","0");
	});
	$(".screenbg ul li:first").css("opacity","1");
	var index = 0;
	var t;
	var li = $(".screenbg ul li");	
	var number = li.size();
	function change(index){
		li.css("visibility","visible");
		li.eq(index).siblings().animate({opacity:0},3000);
		li.eq(index).animate({opacity:1},3000);
	}
	function show(){
		index = index + 1;
		if(index<=number-1){
			change(index);
		}else{
			index = 0;
			change(index);
		}
	}
	t = setInterval(show,8000);
	//根据窗口宽度生成图片宽度
	var width = $(window).width();
	$(".screenbg ul img").css("width",width+"px");
});
function loadimage(){
	document.getElementById("randImage").src = "image.jsp?"+Math.random();
}

function checkForm(){
	 var account=$("#account").val();
	 var password=$("#password").val();
	 if(account==""){
		 $(".errorMessage").html("账号不能为空！");
		 return false;
	 }
	 if(password==""){
		 $(".errorMessage").html("密码不能为空！");
		 return false;
	 }
	 return true;
}
</script>
</head>
<body>
<div class="login-box">
	<h1>投票抽奖后台登录</h1>
	<form method="post" action="${pageContext.request.contextPath}/admin_Login.action" onsubmit="return checkForm()">
		<table>
			<tr class="name">
				<td><label>账 号：</label></td>
				<td><input type="text" id="account" name="account" tabindex="1" autocomplete="off" /><p style="color: red">${falsename }</p></td>
			</tr>
			<tr class="password">
				<td><label>密  码：</label></td>
				<td><input type="password" name="password" maxlength="16" id="password" tabindex="2"/><p style="color: red">${falsePassword }</p></td>
			</tr>

			<tr>
				<td colspan="2">
					<font class="errorMessage" color="red">${errorMessage }&nbsp;</font>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<button type="submit" tabindex="5" onclick="checkForm()">登录</button> &nbsp;&nbsp;&nbsp;&nbsp;
						
				</td>
			</tr>			
		</table>
	</form>
</div>

<!-- <div class="bottom">©2015 <a href="javascript:;" target="_blank"> </a> <span>java1234</span><img width="13" height="16" src="images/copy_rignt_24.png" /></div> -->

<div class="screenbg">
	<ul>
		<li><a href="javascript:;"><img src="images/login.png"></a></li>
<!-- 		<li><a href="javascript:;"><img src="images/1.jpg"></a></li> -->
<!-- 		<li><a href="javascript:;"><img src="images/2.jpg"></a></li> -->
	</ul>
</div>
</body>
</html>