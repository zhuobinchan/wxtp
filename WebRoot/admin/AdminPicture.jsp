<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
input[type="text"] {
	height: 26px !important;
	line-height: 26px !important;
	padding: 0 6px !important;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/bootstrap-select.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/docs.min.css">
<link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/admin/js/bootstrap-select.min.js"></script>
<script src="../client/js/i18n/defaults-*.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/admin/js/jquery-1.11.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/admin/js/uploadPreview.min.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	$(function() {
		$("#headUrl").uploadPreview({
			Img : "ImgPr",
			Width : 220,
			Height : 220
		});
	});
	
	function modify(userName,nickName,userTel,imgUrl,createTime,pollNum,noId) {
		
		$("#myModalLabel").html("随手拍信息");
		
		$("#userName").html("姓名："+userName);
		$("#noId").html("编号："+noId);
		$("#userTel").html("联系电话："+userTel);
		$("#nickName").html("微信昵称："+nickName);
		$("#createTime").html("发布时间："+createTime);
		$("#sspPic").attr("src","${pageContext.request.contextPath}/"+imgUrl);
		$("#pollNum").html("票数："+pollNum);
		
	}

	function resetValue() {
		$("#sspPic").attr(src,"");
		$("#userTel").html("");
		$("#createTime").html("");
		$("#userName").html("");
	}
	
	function deleteObj(objId) {
		if (confirm("确定要删除这条数据吗?")) {
			$.post("adminPicture_deleteObject.action?mainPage=AdminPicture.jsp", {
				id : objId
			}, function(result) {
				if (result.message.trim()=="true") {
					alert("数据删除成功！");
					window.location.reload(true);
				} else {
					alert("数据删除失败！");
				}
			});
		}
	}

	function deleteObjs() {
		var selectedSpan = $(".CheckCheckbos:checked");
		if (selectedSpan.length == 0) {
			alert("请选择要删除的数据！");
			return;
		}
		var strIds = [];
		
		$(".CheckCheckbos:checked").each(function(){
			strIds.push($(this).val());
		})
		
		var ids = strIds.join(",");
		if (confirm("您确定要删除这" + selectedSpan.length + "条数据吗？")) {
			$.post("adminPicture_deleteObjects.action?mainPage=AdminPicture.jsp", {
				ids : ids
			}, function(result) {
				if (result.message.trim()=="true") {
					alert("批量数据已成功删除！");
					location.reload(true);
				} else {
					alert("批量数据删除失败！");
				}
			}, "json");
		} else {
			return;
		}
	}
	
	function passObjs() {
		var selectedSpan = $(".CheckCheckbos:checked");
		if (selectedSpan.length == 0) {
			alert("请选择要上架图片！");
			return;
		}
		var strIds = [];
		
		$(".CheckCheckbos:checked").each(function(){
			strIds.push($(this).val());
		})
		
		var ids = strIds.join(",");
		if (confirm("您确定要在微信端显示这" + selectedSpan.length + "张图片吗？")) {
			$.post("adminPicture_passPictures.action?mainPage=AdminPicture.jsp", {
				ids : ids
			}, function(result) {
				if (result.message.trim()=="true") {
					alert("图片上架成功！");
					location.reload(true);
				} else {
					alert("图片下架失败！");
				}
			}, "json");
		} else {
			return;
		}
	}

	function failObjs() {
		var selectedSpan = $(".CheckCheckbos:checked");
		if (selectedSpan.length == 0) {
			alert("请选择要下架的数据！");
			return;
		}
		var strIds = [];
		
		$(".CheckCheckbos:checked").each(function(){
			strIds.push($(this).val());
		})
		
		var ids = strIds.join(",");
		if (confirm("您确定要取消在微信端显示这" + selectedSpan.length + "张图片吗？")) {
			$.post("adminPicture_failPictures.action?mainPage=AdminPicture.jsp", {
				ids : ids
			}, function(result) {
				if (result.message.trim()=="true") {
					alert("图片下架成功！");
					location.reload(true);
				} else {
					alert("图片下架失败！");
				}
			}, "json");
		} else {
			return;
		}
	}	
	function searchFunc(){
	
		
		var search_content = {
			userName:"",
			userTel:"",
			status:"",
			noId:""
		};
		
		search_content.userName = $("#search_userName").val();
		search_content.userTel = $("#search_userTel").val();
		search_content.status = $("#search_status").val();
		search_content.noId = $("#search_noId").val();
		
		$("#searchContent").val(JSON.stringify(search_content));
		
		$("#search_fm").submit();
		
		
	}
</script>

<style type="text/css">
	
	.driverImg,.driverImg_detail{
		 border-radius: 10px 10px 10px 10px;
		width: 100px;
		height: 100px;
	}
	.driverImg_detail:HOVER{
		box-shadow: 1px 1px 5px 5px #888888;
		 border-radius: 10px 10px 10px 10px;
		width: 300px;
		height: 300px;	
	}
	.driverImg:HOVER{
		box-shadow: 1px 1px 5px 5px #888888;
		 border-radius: 10px 10px 10px 10px;
		width: 120px;
		height: 120px;
	}
	.driverName:HOVER {
		color: red;
	}
	.deleteDriver{
		margin-top: -10px;
		position: absolute;
		color: red;
	}
	.deleteDriver:HOVER {
		font-size:20pt;
		cursor:pointer;
		color: blue;
	}
	.goodsNum{
		position:absolute;
		margin-top:60px;
		margin-left:10px;
		box-shadow: 1px 1px 1px 1px #888888;
		border-radius: 3px 3px 3px 3px;
		background-color: #f8a734;
		color: white;
	}
</style>
</head>
<body>
	<div class="container-fluid">
		<div id="tooBar" style="padding: 10px 0px 0px 10px;">
			<form id="search_fm" action="adminPicture_findPageByMap" method="post"
				class="form-search" style="display: inline;">
				&nbsp; 姓名：<input id="search_userName" value="" type="text"
					class="input-medium search-query" placeholder="输入...">
				&nbsp; 手机号码：<input id="search_userTel" value="" type="text"
					class="input-medium search-query" placeholder="输入...">
				&nbsp; 编号：<input id="search_noId" value="" type="text"
					class="input-medium search-query" placeholder="输入...">
				&nbsp; 图片状态：<select id="search_status">
								<option value="">请选择</option>
								<option value="1">上架</option>
								<option value="0">下架</option>
							</select>						
				<input id="searchContent" name="searchContent" type="hidden" value="">				
				<button id="search_btn" type="button" class="btn btn-primary" title="Search" onclick="javascript:searchFunc()">
					查询&nbsp;<i class="icon  icon-search"></i>
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" role="button"
					class="btn btn-danger" onclick="javascrip:deleteObjs()">批量删除</a>
				&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" role="button"
					class="btn btn-success" onclick="javascrip:passObjs()">批量上架</a>
				&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" role="button"
					class="btn btn-warning" onclick="javascrip:failObjs()">批量下架</a>											
			</form>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-content nopadding">
						<div class="container-fluid">
						
					
						<c:forEach var="ssp" items="${results}" varStatus="status">
							
							<c:if test="${(status.index+1) mod 5 ==1}">  
								<div class="row-fluid" style="height: 160px">
							</c:if>	
								
									<div class="span2">
										<img class="driverImg" onclick="CheckCheck('checkbox${status.index+1}')" alt="haha" src="${pageContext.request.contextPath}/${ssp.imgUrl }">
										<b class="deleteDriver" onclick="deleteObj(${ssp.id })">X</b>
										<lable class="goodsNum">${ssp.poll }</lable>
									<div class="row-fluid">
									<div class="span2"><input class="CheckCheckbos" type="checkbox" value="${ssp.id }" id="checkbox${status.index+1}"/></div>
									<div class="span8" >
									<label style="padding-top: 3px;margin-left: -8px" class="control-label driverName" for="address"
									data-backdrop="static" data-toggle="modal" data-target="#dlg"
									 onclick="return modify('${ssp.userName }','${ssp.nickName }','${ssp.userTel }','${ssp.imgUrl }','${ssp.createTime }','${ssp.poll }','${ssp.noId }')">
									 ${ssp.userName }
									 <c:if test="${ssp.status==1 }">
									 	<font color="#2de03c">(已上架)</font>
									 </c:if>
									 
									 
									 </label></div>	
									</div>
									</div>
							<c:if test="${(status.index+1) mod 5 ==0}">  
								</div>
							</c:if>		
								</c:forEach>
							</div>
							<script type="text/javascript">
							
								function CheckCheck(id){
									if($("#"+id).attr("checked")!="checked"){
										$("#"+id).attr("checked","checked");
									}else $("#"+id).removeAttr("checked");
								}
							
							</script>
					</div>
				</div>
				<div class="pagination alternate">
					<ul class="clearfix">${pageCode }
					</ul>
				</div>


			</div>
		</div>
		<div id="dlg" class="modal hide fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" onclick="return resetValue()">×</button>
				<h3 id="myModalLabel">下订单</h3>
			</div>
			<div class="modal-body">
					<table class="table table-hover table-bordered">
						<tr>
							<td><label class="control-label" for="carNum" id="userName">用户名：</label>
							</td>
							<td rowspan="6" style="text-align: center;">
							<img class="driverImg_detail" alt="haha" id="sspPic" src="${pageContext.request.contextPath}/${ssp.imgUrl }">
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="carNum" id="noId">编号：</label>
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="carNum" id="userTel">联系电话：</label>
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="carNum" id="nickName">微信昵称：</label>
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="carNum" id="createTime">发片时间：</label>
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="carNum" id=pollNum>票数：</label>
							</td>
						</tr>												
					</table>
			
			
			</div>
			<div class="modal-footer">
				<font id="error" style="color: red;"></font>
				<button class="btn" data-dismiss="modal" aria-hidden="true"
					onclick="return resetValue()">关闭</button>
			</div>
		</div>
	</div>
</body>
</html>
