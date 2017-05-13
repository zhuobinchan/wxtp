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
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/datetimepicker.css">
<script src="${pageContext.request.contextPath}/admin/js/bootstrap-select.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/i18n/defaults-*.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/uploadPreview.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/admin/js/bootstrap-datetimepicker.min.js"></script>	
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
	function openAddDlg() {
		$("#myModalLabel").html("新建赛事");
		$("#fm").attr("action","adminAward_saveObject.action");
	}
	function saveBroadcast() {
		
		if ($("#name").val() == null
				|| $("#name").val() == '') {
			$("#error").html("请输奖品名称！");
			$("#name").focus();
			return false;
		}
		if ($("#number").val() == null
				|| $("#number").val() == '') {
			$("#error").html("请输奖品数量！");
			$("#number").focus();
			return false;
		}
		if ($("#percent").val() == null
				|| $("#percent").val() == '') {
			$("#error").html("请输比例名称！");
			$("#percent").focus();
			return false;
		}
		if (!$.isNumeric($("#percent").val()) ) {
			$("#error").html("比例请输入数字！");
			$("#percent").focus();
			return false;
		}
		if ($("#releaseNumber").val() == null
				|| $("#releaseNumber").val() == '') {
			$("#error").html("请输每日投放量！");
			$("#releaseNumber").focus();
			return false;
		}
		
		if ($("#startDegrees").val() == null
				|| $("#startDegrees").val() == '') {
			$("#error").html("请输开始角度！");
			$("#startDegrees").focus();
			return false;
		}
		if (!$.isNumeric($("#startDegrees").val()) ) {
			$("#error").html("请输开始角度！");
			$("#startDegrees").focus();
			return false;
		}
		if ($("#endDegrees").val() == null
				|| $("#endDegrees").val() == '') {
			$("#error").html("请输结束角度！");
			$("#endDegrees").focus();
			return false;
		}
		if (!$.isNumeric($("#endDegrees").val()) ) {
			$("#error").html("请输结束角度！");
			$("#endDegrees").focus();
			return false;
		}
		
		
		
		var sumPercent=0;
		var canSetRoundPercent=0;
		 $.ajaxSetup({  
    		async : false  
		});     
		$.post("adminAward_isOverBoundPercent",function(result, status){
			if(status == "success"){
				if(result.message.trim()=="true"){
					sumPercent = result.sumPercent;
					canSetRoundPercent = result.canSetRoundPercent;
					$("#percent").attr("canSetRoundPercent",canSetRoundPercent);
				}
			}
		},"json");
		canSetRoundPercent = $("#percent").attr("canSetRoundPercent");
		
		
		
		if(parseInt($("#percent").val())>parseInt(canSetRoundPercent)){
			
			$("#error").html("超过百分比比例，需要在0-"+canSetRoundPercent+"范围内");
			return false;
		}
		/* $.post("Section_save.action", $("#fm").serialize()); */
		$("#fm").submit();
		alert("保存成功！");
	}
	function modify(awardId, name,number,percent,releaseNumber,startDegrees,endDegrees,isNothing,todaySendOutNumber) {
	
		$("#fm").attr("action","adminAward_saveOrUpdateObject.action");
		$("#awardId").val(awardId);
		$("#name").val(name);
		$("#number").val(number);
		$("#percent").val(percent);
		$("#releaseNumber").val(releaseNumber);
		$("#startDegrees").val(startDegrees);
		$("#endDegrees").val(endDegrees);
		$("#isNothing").val(isNothing);
		$("#todaySendOutNumber").val(todaySendOutNumber);
	}
	function deleteObj(objId) {
		if (confirm("确定要删除这条数据吗?")) {
			$.post("adminAward_deleteObject.action?mainPage=adminAward.jsp", {
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
		var selectedSpan = $(".checked").parent().parent().next("td");
		if (selectedSpan.length == 0) {
			alert("请选择要删除的数据！");
			return;
		}
		var strIds = [];
		for (var i = 0; i < selectedSpan.length; i++) {
			strIds.push(selectedSpan[i].attributes["myids"].nodeValue);
		}
		var ids = strIds.join(",");
		if (confirm("您确定要删除这" + selectedSpan.length + "条数据吗？")) {
			$.post("adminAward_deleteObjects.action?mainPage=adminAward.jsp", {
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
	
	function resetValue() {
		$("#id").val("");
		$("#name").val("");
		$("#number").val("");
		$("#percent").val("");
		$("#releaseNumber").val("");
		$("#startDegrees").val("");
		$("#endDegrees").val("");
		$("#isNothing").val("");
		$("#todaySendOutNumber").val("");
	}
	
	
	function searchFunc(){
	
		
		var search_content = {
			name:"",
		};
		
		search_content.name = $("#search_name").val();
		
		$("#searchContent").val(JSON.stringify(search_content));
		
		$("#search_fm").submit();
		
		
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div id="tooBar" style="padding: 10px 0px 0px 10px;">

			<form id="search_fm" action="adminAward_findPageByMap" method="post"
				class="form-search" style="display: inline;">
				&nbsp; 奖品名称： <input id="search_name" name="award.name" value="" type="text"
					class="input-medium search-query" placeholder="输入奖品名称...">
				<input id="searchContent" name="searchContent" type="hidden" value="">	
				<button id="search_btn" type="button" class="btn btn-primary" title="Search" onclick="javascript:searchFunc()">
					查询&nbsp;<i class="icon  icon-search"></i>
				</button>
				<br> <br>
				<button class="btn btn-primary" type="button" data-backdrop="static"
					data-toggle="modal" data-target="#dlg"
					onclick="return openAddDlg()">添加奖品</button>
				&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" role="button"
					class="btn btn-danger" onclick="javascrip:deleteObjs()">批量删除</a>
			</form>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<!-- <span class="icon"> <input type="checkbox"
							id="title-checkbox" name="title-checkbox" />
						</span> -->
						<h5>奖品管理</h5>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered table-striped with-check">
							<thead>
								<tr>
									<th><i class=""></i></th>
									<th>序号</th>
									<th>奖品名称</th>
									<th>剩余数量</th>
									<th>中奖概率</th>
									<th>每日投放量</th>
									<th>每日成功送出奖品量</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${results }" var="award" varStatus="idx">
									<tr>
										<td><input type="checkbox" /></td>
										<td style="text-align: center;vertical-align: middle;"  myids="${award.awardId }">${(page-1)*6+idx.index+1 }</td>
										<td style="text-align: center;vertical-align: middle;">${award.name }</td>
										<td style="text-align: center;vertical-align: middle;">${award.number }</td>
										<td style="text-align: center;vertical-align: middle;">${award.percent }</td>
										<td style="text-align: center;vertical-align: middle;">${award.releaseNumber }</td>
										<td style="text-align: center;vertical-align: middle;">${award.todaySendOutNumber }</td>
										
										<td style="text-align: center;vertical-align: middle;">
											<button class="btn btn-info" type="button"
												data-backdrop="static" data-toggle="modal"
												data-target="#dlg"
												<%
												    request.setAttribute("vEnter", "\r\n");   
												    request.setAttribute("nEnter", "[enter]");
												%> 
												onclick="return modify('${award.awardId }','${award.name }','${award.number }','${award.percent }','${award.releaseNumber }','${award.startDegrees }','${award.endDegrees }','${award.isNothing }','${award.todaySendOutNumber }')">修改
											</button>&nbsp;&nbsp;
											<button class="btn btn-danger" type="button"
												onclick="javascript:deleteObj('${award.awardId }')">删除</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
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
				<h3 id="myModalLabel">奖品添加或修改</h3>
			</div>
			<div class="modal-body">
				<form id="fm"
					action="adminAward_saveObject.action"
					method="post" enctype="multipart/form-data">
					<table>

						<tr>
							 <input id="awardId" type="hidden" name="award.awardId" />
							 <input id="currentPage" type="hidden" name="currentPage" value="${page }">
							 <input id="todaySendOutNumber" type="hidden" name="award.todaySendOutNumber" />
							<td><label class="control-label" for="name">奖品名称：</label>
							</td>
							<td><input id="name" type="text"
								name="award.name" placeholder="请输入…"></td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="number">剩余数量：</label>
							</td>
							<td>
								<input id="number" type="text" name="award.number" placeholder="请输入…">
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="percent" canSetRoundPercent="">中奖概率：<br>如果不能修改信息<br>请讲概率设为0再进行修改</label>
							</td>
							<td>
								<input id="percent" type="text" name="award.percent" placeholder="请输入…">
							</td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="startDegrees">角度范围(开始值)：</label>
							</td>
							<td>
								<input id="startDegrees" type="text" name="award.startDegrees" placeholder="请输入…">
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="endDegrees">角度范围(结束值)：</label>
							</td>
							<td>
								<input id="endDegrees" type="text" name="award.endDegrees" placeholder="请输入…">
							</td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="releaseNumber">每日投放量：</label>
							</td>
							<td>
								<input id="releaseNumber" type="text" name="award.releaseNumber" placeholder="请输入…" >
							</td>
						</tr>
					
						<tr>
							<td><label class="control-label" for="isNothing">是否为谢谢参与：</label>
							</td>
							<td>
								<select id="isNothing" class="input-medium search-query" name="award.isNothing">
									<option value="0" >否</option>
									<option value="1" >是</option>
			 	 				</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="modal-footer">
				<font id="error" style="color: red;"></font>
				<button class="btn" data-dismiss="modal" aria-hidden="true"
					onclick="return resetValue()">关闭</button>
				<button class="btn btn-primary" onclick="javascript:saveBroadcast()">保存</button>
				<!-- <button class="btn btn-primary" type="submit">保存</button> -->
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jedate/jedate.js"></script>