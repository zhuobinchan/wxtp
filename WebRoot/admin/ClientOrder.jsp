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
<link rel="stylesheet" href="../client/css/bootstrap-select.css">
<link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<script src="../client/js/bootstrap-select.min.js"></script>
<script src="../client/js/i18n/defaults-*.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/uploadPreview.min.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	$(function() {
		$("#logo").uploadPreview({
			Img : "ImgPr",
			Width : 220,
			Height : 220
		});
	});
	function modify(name,nickName,telephone, email,headUrl,gender,introduction) {
		
		$("#name").append(name);
		$("#nickName").append(nickName);
	    $("#telephone").append(telephone);
		$("#email").append(email);
		$("#gender").append(gender);
		$("#introduction").append(introduction);
		
		$("#ImgPr").attr("src", "${pageContext.request.contextPath}/" + headUrl);
	}

	function resetValue() {
		$("#name").html("教师姓名：");
		$("#nickName").html("教师昵称：");
		$("#telephone").html("联系电话：");
		$("#email").html("邮箱地址：");
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
			oNo:"",
			status:""
		};
		
		search_content.oNo = $("#search_oNo").val();
		search_content.oNo = $("#search_status").val();
		
		$("#searchContent").val(JSON.stringify(search_content));
		
		$("#search_fm").submit();
		
		
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div id="tooBar" style="padding: 10px 0px 0px 10px;">

				<button type="button" class="btn btn-success"  data-backdrop="static"
					data-toggle="modal" data-target="#dlg">新建订单</button>
			<form id="search_fm" action="Organization_teacherFilter" method="post"
				class="form-search" style="display: inline;">
				&nbsp; 订单编号： <input id="search_oNo" value="" type="text"
					class="input-medium search-query" placeholder="输入订单编号...">
				&nbsp; 订单状态：<select id="search_status">
								<option value="">请选择</option>
								<option value="0">待处理</option>
								<option value="1">已接单</option>
								<option value="2">失败订单</option>
							</select>					
				<input id="searchContent" name="searchContent" type="hidden" value="">				
				<button id="search_btn" type="button" class="btn btn-primary" title="Search" onclick="javascript:searchFunc()">
					查询&nbsp;<i class="icon  icon-search"></i>
				</button>
			</form>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-content nopadding">
						<table class="table table-bordered table-striped with-check table-hover">
							<thead>
								<tr>
									<th><i class=""></i></th>
									<th>序号</th>
									<th>订单编号</th>
									<th>下订时间</th>
									<th>订单状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${results }" var="order" varStatus="idx">
									<tr>
										<td><input type="checkbox" /></td>
										<td style="text-align: center;vertical-align: middle;"  myids="${order.oid }">${(page-1)*6+idx.index+1 }</td>
										<td style="text-align: center;vertical-align: middle;">${order.oNo }</td>
 										<td style="text-align: center;vertical-align: middle;">
											<fmt:formatDate  value="${order.createTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td style="text-align: center;vertical-align: middle;">
										
										<c:if test="${order.status==0}"><font color="#efd320">待处理</font></c:if>
										<c:if test="${order.status==1}"><font color="#079c33">已接单</font></c:if>	
										<c:if test="${order.status==2}"><font color="red">失败订单</font></c:if>											
										</td>
										<td style="text-align: center;vertical-align: middle;">
											<button class="btn btn-info" type="button"
												data-backdrop="static" data-toggle="modal"
												data-target="#dlg"
												onclick="return modify('${order.oid }','${order.oNo }','${order.createTime }')">查看更多
											</button>&nbsp;&nbsp;
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
				<h3 id="myModalLabel">下订单</h3>
			</div>
			<div class="modal-body">
				<form id="fm"
					action="AdminOrganization_saveObject.action"
					method="post" enctype="multipart/form-data">
					<table>

						<tr>
							 <input id="id" type="hidden" name="organization.id" value="${organization.id }"/>
							 <input id="currentPage" type="hidden" name="currentPage" value="${page }">
							<td><label class="control-label" for="name">机构名称：</label>
							</td>
							<td>
								<div class="dropdown">
								  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
								    Dropdown
								    <span class="caret"></span>
								  </button>
								  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
								    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
								    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
								    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
								    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
								  </ul>
								</div>

							</td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="linkMan">联系人：</label>
							</td>
							<td><input id="linkMan" type="text"
								name="organization.linkMan" placeholder="请输入…"></td>
						</tr>	
						
						<tr>
							<td><label class="control-label" for="telephone">联系电话：</label>
							</td>
							<td><input id="telephone" type="text"
								name="organization.telephone" placeholder="请输入…"></td>
						</tr>
						
						<tr>
							<td><label class="control-label" for="email">邮箱：</label>
							</td>
							<td><input id="email" type="text"
								name="organization.email" placeholder="请输入…"></td>
						</tr>

						<tr>
							<td><label class="control-label" for="address">地址：</label>
							</td>
							<td><input id="address" type="text"
								name="organization.address" placeholder="请输入…"></td>
						</tr>	

						<tr>
							<td><label class="control-label" for="introduction">机构介绍：</label>
							</td>
							<td>
							<textarea id="introduction" rows="5" cols="50" style="width: 405px;"  name="organization.introduction"></textarea>
							</td>
						</tr>

					</table>
				</form>			

			
			
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jedate/jedate.js"></script>
<script type="text/javascript">
	jeDate({
		dateCell : "#dateinfo",
		isinitVal : true,
		isTime : true, //isClear:false,
		minDate : "2014-09-19 00:00:00",
		okfun : function(val) {
			alert(val)
		}
	})

	jeDate({
		dateCell : "#startinfo",
		isinitVal : true,
		isTime : true, //isClear:false,
		minDate : "2014-09-19 00:00:00",
		okfun : function(val) {
			alert(val)
		}
	})

	$("#jedatebox").css("z-index", "99999");
</script>