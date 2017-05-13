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
		$("#fm").attr("action","adminCompetition_saveObject.action");
	}
	function saveBroadcast() {
		
		if ($("#name").val() == null
				|| $("#name").val() == '') {
			$("#error").html("请输赛事名称！");
			$("#name").focus();
			return false;
		}

		if ($("#startTime").val() == null
				|| $("#startTime").val() == '') {
			$("#error").html("请选择开始时间！");
			$("#startTime").focus();
			return false;
		}
		
		if ($("#endTime").val() == null
				|| $("#endTime").val() == '') {
			$("#error").html("请选择结束时间！");
			$("#endTime").focus();
			return false;
		}		
		
		/* $.post("Section_save.action", $("#fm").serialize()); */
		$("#fm").submit();
		alert("保存成功！");
	}
	function modify(id, name,startTime,endTime,introduction,rule,status) {
		var introduction_removeEnter = introduction.replace(/\[enter\]/g,"\n");
		var rule_removeEnter = rule.replace(/\[enter\]/g,"\n");
		
		$("#fm").attr("action","adminCompetition_saveOrUpdateObject.action");
		$("#id").val(id);
		$("#name").val(name);
		$("#startTime").val(startTime);
		$("#endTime").val(endTime);
		$("#introduction").val(introduction_removeEnter);
	    $("#rule").val(rule_removeEnter);
	    $("#status").val(status);
	    
	}
	function deleteObj(objId) {
		if (confirm("确定要删除这条数据吗?")) {
			$.post("adminCompetition_deleteObject.action?mainPage=AdminCompetition.jsp", {
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
			$.post("adminCompetition_deleteObjects.action?mainPage=AdminCompetition.jsp", {
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
		$("#startTime").val("");
		$("#endTime").val("");
	}
	function searchUserByNickName(userNickName) {
		$.post("Section_getUserByNickName.action", {
			nickName : userNickName
		}, function(result) {
			var result = eval('(' + result + ')');
			$("#info").html(result.info);
			$("#masterId").val(result.masterId);
		});
	}
	
	function searchFunc(){
	
		
		var search_content = {
			name:"",
			status:""
		};
		
		search_content.name = $("#search_name").val();
		search_content.status = $("#search_status").val();
		
		$("#searchContent").val(JSON.stringify(search_content));
		
		$("#search_fm").submit();
		
		
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div id="tooBar" style="padding: 10px 0px 0px 10px;">

			<form id="search_fm" action="adminCompetition_findPageByMap" method="post"
				class="form-search" style="display: inline;">
				&nbsp; ${session.admin.name }赛事名称： <input id="search_name" name="team.name" value="" type="text"
					class="input-medium search-query" placeholder="输入赛事名称...">
					
				<select id="search_status" name="team.status">
					<option value="">请选择</option>
					<option value="0">没有进行</option>
					<option value="1">正在进行</option>
				</select>	
				
				<input id="searchContent" name="searchContent" type="hidden" value="">	
				<button id="search_btn" type="button" class="btn btn-primary" title="Search" onclick="javascript:searchFunc()">
					查询&nbsp;<i class="icon  icon-search"></i>
				</button>
				<br> <br>
				<button class="btn btn-primary" type="button" data-backdrop="static"
					data-toggle="modal" data-target="#dlg"
					onclick="return openAddDlg()">添加赛事</button>
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
						<h5>赛事管理</h5>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered table-striped with-check">
							<thead>
								<tr>
									<th><i class=""></i></th>
									<th>序号</th>
									<th>赛事</th>
									<th>赛事状态</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${results }" var="competition" varStatus="idx">
									<tr>
										<td><input type="checkbox" /></td>
										<td style="text-align: center;vertical-align: middle;"  myids="${competition.competitionId }">${(page-1)*6+idx.index+1 }</td>
										<td style="text-align: center;vertical-align: middle;">${competition.name }</td>
										<td style="text-align: center;vertical-align: middle;">${competition.status=="1"?"正在进行":"没有进行" }</td>
										<td style="text-align: center;vertical-align: middle;"><fmt:formatDate  value="${competition.startTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td style="text-align: center;vertical-align: middle;"><fmt:formatDate  value="${competition.endTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td style="text-align: center;vertical-align: middle;">
											<button class="btn btn-info" type="button"
												data-backdrop="static" data-toggle="modal"
												data-target="#dlg"
												<%
												    request.setAttribute("vEnter", "\r\n");   
												    request.setAttribute("nEnter", "[enter]");
												%> 
												onclick="return modify('${competition.competitionId }','${competition.name }','${competition.startTime }','${competition.endTime }','${fn:replace(competition.introduction, vEnter, nEnter)}','${fn:replace(competition.rule, vEnter, nEnter)}','${competition.status }')">修改
											</button>&nbsp;&nbsp;
											<button class="btn btn-danger" type="button"
												onclick="javascript:deleteObj('${competition.competitionId }')">删除</button>
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
				<h3 id="myModalLabel">赛事添加或修改</h3>
			</div>
			<div class="modal-body">
				<form id="fm"
					action="adminTeam_saveObject.action"
					method="post" enctype="multipart/form-data">
					<table>

						<tr>
							 <input id="id" type="hidden" name="competition.competitionId" />
							 <input id="currentPage" type="hidden" name="currentPage" value="${page }">
							<td><label class="control-label" for="name">赛事：</label>
							</td>
							<td><input id="name" type="text"
								name="competition.name" placeholder="请输入…"></td>
						</tr>
						<tr>
							<td>
								<label class="control-label" for="introduction">赛事介绍：</label>
							</td>
							<td>
								 <textarea rows="5" cols="50" style="width: 405px;" id="introduction" name="competition.introduction"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<label class="control-label" for="rule">赛事规则：</label>
							</td>
							<td>
								 <textarea id="rule" rows="5" cols="50" style="width: 405px;" name="competition.rule"></textarea>
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="name">开始时间：</label>
							</td>
							<td>
								<input id="startTime" name="competition.startTime" size="16" type="text" value="" readonly class="form_datetime">
							</td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="name">结束时间：</label>
							</td>
							<td>
								<input id="endTime" name="competition.endTime" size="16" type="text" value="" readonly class="form_datetime">
								<script type="text/javascript">
								    $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii:ss'});
								</script> 								
							</td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="name">赛事状态：<br>(PS:只能由一个赛事正在进行中，<br>如果设置了当前正在进行中，<br>其他赛事则改为没有进行)</label>
							</td>
							<td>
							<select id="status" name="competition.status">
									<option value="0">没有进行</option>
									<option value="1">正在进行</option>
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