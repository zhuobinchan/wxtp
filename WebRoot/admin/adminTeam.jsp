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
<link rel="stylesheet" href="../admin/css/bootstrap-select.css">
<script src="../admin/js/bootstrap-select.min.js"></script>
<script src="../admin/js/i18n/defaults-*.min.js"></script>
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
	function openAddDlg() {
		$("#myModalLabel").html("添加狮队");
		$("#fm").attr("action","adminTeam_saveObject.action");
		resetValue();
	}
	function saveBroadcast() {
		
		if ($("#name").val() == null
				|| $("#name").val() == '') {
			$("#error").html("请输入狮队名称！");
			$("#name").focus();
			return false;
		}
		if ($("#competitionId").val() == null
				|| $("#competitionId").val() == '') {
			$("#error").html("请选择赛事名称！");
			$("#competitionId").focus();
			return false;
		}
		
		
		var str = $("#introduction").html();
 		str = str.replace(/&lt;/g,"<");
 		str = str.replace(/&gt;/g,">");
		$("#introductionInput").val(str);
		/* $.post("Section_save.action", $("#fm").serialize()); */
		$("#fm").submit();
		alert("保存成功！");
	}
	
	function modify(id, name,members,poll,basePoll,totalPoll,teamLogo,competitionId) {
	
		$("#fm").attr("action","adminTeam_saveOrUpdateObject.action");
		$("#id").val(id);
		$("#name").val(name);
		$("#members").val(members);
		$("#poll").val(poll);
		$("#basePoll").val(basePoll);
		$("#totalPoll").val(totalPoll);
		$("#ImgPr").attr("src", "${pageContext.request.contextPath}/" + teamLogo);
		
		$.post("adminTeam_getTeamIntroductionAjax.action", {
			id : id
		}, function(result) {
			var str = $("#introduction").html(result.introduction);
 			str = str.replace(/&lt;/g,"<");
 			str = str.replace(/&gt;/g,">");
			$("#introductionInput").val(str);
		});
		$("#competitionId").val(competitionId);
	}
	function deleteObj(objId) {
		if (confirm("确定要删除这条数据吗?")) {
			$.post("adminTeam_deleteObject.action?mainPage=adminTeam.jsp", {
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
			$.post("adminTeam_deleteObjects.action?mainPage=adminTeam.jsp", {
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
		$("#members").val("");
// 		$("#introduction").val("");
		$("#poll").val("");
		$("#basePoll").val("");
		$("#totalPoll").val("");
		$("#ImgPr").attr("src", "");
		$("#introduction").html("");
		$("#introductionInput").val("");
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
	
			
// 		var search_content = {
// 			name:"",
// 			telephone:"",
// 			organization.id:""
// 		};
		
		var search_name = $("#search_name").val();
		var search_competition = $("#search_competition").val();
		
		var str = "{'name':'"+search_name+"',"
			      +"'competition.competitionId':'"+search_competition+"'}";
		
// 		$("#searchContent").val(JSON.stringify(search_content));
		$("#searchContent").val(str);
		$("#search_fm").submit();
		
		
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div id="tooBar" style="padding: 10px 0px 0px 10px;">

			<form id="search_fm" action="adminTeam_findPageByMap?mainPage=adminTeam.jsp&findPageTarget=/admin/adminTeam_findPageByMap" method="post"
				class="form-search" style="display: inline;">
				&nbsp; 狮队名称： <input id="search_name" name="team.name" value="" type="text"
					class="input-medium search-query" placeholder="输入机构名称...">
				<input id="searchContent" name="searchContent" type="hidden" value="">	
				 选择赛事：
				 <select id="search_competition" class="input-medium search-query" name="team.competition.competitionId">
					<option value="" >请选择</option>
					<c:forEach var="competition" items="${competitions }">
						<option value="${competition.competitionId }" >${competition.name }</option>
					</c:forEach>
			 	 </select>
				
				
				<button id="search_btn" type="button" class="btn btn-primary" title="Search" onclick="javascript:searchFunc()">
					查询&nbsp;<i class="icon  icon-search"></i>
				</button>
				<br> <br>
				<button class="btn btn-primary" type="button" data-backdrop="static"
					data-toggle="modal" data-target="#dlg"
					onclick="return openAddDlg()">添加狮队</button>
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
						<h5>狮队管理</h5>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered table-striped with-check">
							<thead>
								<tr>
									<th><i class=""></i></th>
									<th>序号</th>
									<th>狮队名称</th>
									<th>狮队成员</th>
									<th>狮队票数</th>
									<th>狮队logo</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${results }" var="team" varStatus="idx">
									<tr>
										<td><input type="checkbox" /></td>
										<td style="text-align: center;vertical-align: middle;"  myids="${team.teamId }">${(page-1)*6+idx.index+1 }</td>
										<td style="text-align: center;vertical-align: middle;">${team.name }</td>
										<td style="text-align: center;vertical-align: middle;">${team.members }</td>
										<td style="text-align: center;vertical-align: middle;">${team.totalPoll }</td>
										<td
											style="text-align: center;vertical-align: middle;width: 110px;vertical-align: middle;">
											<img style="width: 100px;"
											src='${pageContext.request.contextPath}/${team.teamLogo }'></img>
										</td><td style="text-align: center;vertical-align: middle;">
											<button class="btn btn-info" type="button"
												data-backdrop="static" data-toggle="modal"
												data-target="#dlg"
												<%
												    request.setAttribute("vEnter", "\r\n");   
												    request.setAttribute("nEnter", "[enter]");
												%> 
												onclick="return modify('${team.teamId }','${team.name }','${team.members }','${team.poll }','${team.basePoll }','${team.totalPoll }','${team.teamLogo }','${team.competition.competitionId }')">修改
											</button>&nbsp;&nbsp;
											<button class="btn btn-danger" type="button"
												onclick="javascript:deleteObj('${team.teamId }')">删除</button>
											<a class="btn btn-info" href="exportUserByExcel.action?teamId=${team.teamId }">导出该队用户信息</a>
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
				<h3 id="myModalLabel">狮队信息添加或修改</h3>
			</div>
			<div class="modal-body">
				<form id="fm"
					action="adminTeam_saveObject.action"
					method="post" enctype="multipart/form-data">
					<table>

						<tr>
							 <input id="id" type="hidden" name="team.teamId" value="${team.teamId }"/>
							 <input id="currentPage" type="hidden" name="currentPage" value="${page }">
							<td><label class="control-label" for="name">狮队名称：</label>
							</td>
							<td><input id="name" type="text"
								name="team.name" placeholder="请输入…"></td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="name">狮队成员：</label>
							</td>
							<td><input id="members" type="text"
								name="team.members" placeholder="请输入…"></td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="competitionId">所属板块：</label>
							</td>
							<td><select id="competitionId"
								name="team.competition.competitionId">
									<option value="">请选择</option>
									<c:forEach var="competition" items="${competitions }">
										<option value="${competition.competitionId }">${competition.name }</option>
									</c:forEach>
							</select></td>
						</tr>
						
						<tr>
							<td>
								<label class="control-label" for="name">狮队介绍：
								<br>此处可以从<a href="http://xiumi.us/studio/v5#/paper/for/new" target="_blank">秀米</a><br>直接粘贴到粉红色框内</label>
							</td>
							<td>
							<div id="introduction" style="width: 100%;height: 300px;background-color: rgba(255, 192, 192, 0.28); overflow: scroll;">
							</div>
							<input id="introductionInput" type="text"
								name="team.introduction" style="display: none">
							</td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="name">真实票数：</label>
							</td>
							<td><input id="poll" type="text"
								name="team.poll" disabled="disabled" placeholder="请输入…"></td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="name">基础票数：</label>
							</td>
							<td><input id="basePoll" type="text"
								name="team.basePoll" placeholder="请输入…"></td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="name">总票数（基础票数+真实票数）：</label>
							</td>
							<td><input id="totalPoll" type="text"
								name="team.totalPoll" disabled="disabled" placeholder="请输入…"></td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="headUrl" >狮队logo：</label></td>
							<td>
								<img onclick="imgFile()" id="ImgPr" class="img-polaroid" style="width: 120px;height: 120px;"/>
								<h1 style="display: none"><input type="file" id="headUrl" name="img"/></h1>
							</td>
						</tr>
						<script>
							function imgFile(){
									$("#headUrl").click();
							}
						</script>
						
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
	src="${pageContext.request.contextPath}/admin/jedate/jedate.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/bootstrap-wysiwyg.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/prefixfree.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/jquery.hotkeys.js"></script>
<script type="text/javascript">
$('#introduction').wysiwyg();
</script>