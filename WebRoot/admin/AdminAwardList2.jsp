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
	
	function changeIsGetAwardById(id){
		if(window.confirm("你确定要领取奖品吗？(注意：确定后无法撤销领取)")){
			$.post("adminOtherAwardList2_changeIsGetAwardByAId.action", {
				id : id
			}, function(result) {
				if (result.message.trim() == "true") {
					location.reload(true);
				} else {
					alert("领取失败");
				}
			});
		}

	}

	function searchFunc() {

		var search_content = {
			name : "",
			telephone : "",
			awardName : ""
		};

		search_content.name = $("#search_name").val();
		search_content.telephone = $("#search_tel").val();
		search_content.awardName = $("#search_award").val();

		$("#searchContent").val(JSON.stringify(search_content));

		$("#search_fm").submit();

	}
</script>
</head>
<body>
	<div class="container-fluid">
		<div id="tooBar" style="padding: 10px 0px 0px 10px;">

			<form id="excelForm" action="adminOtherAwardList2_exportExcel" method="post" enctype="multipart/form-data">
				<button id="search_btn" type="button" class="btn btn-primary" title="Search" >
					导入数据&nbsp;</i>
				</button>
				<input type="text" id="viewExcelUrl" style="margin-top: 10px;"/>
					<h1 style="display: none"><input type="file" id="excelFile" accept=".xls" name="excel"/></h1>
				<button id="qr_btn" type="button" class="btn btn-success" title="Search" onclick="">
					确认导入&nbsp;</i>
				</button>
				<a href="${pageContext.request.contextPath}/resource/地推礼品.xls" class="btn btn-danger"   target= "_self ">数据模版下载，可供参考</a>
			</form>
			<script>
				
				$().ready(function(){
					
					$("#excelFile").change(function(){
					
						$("#viewExcelUrl").val($(this).val());
					
					});
					
					$("#qr_btn").click(function(){
					
						if($("#excelFile").val().trim()==""){
							alert("请选择要上传的文件。");
						}else {
							$("#excelForm").submit();
							alert("上传成功。");
						}
						
					});
				
					$("#search_btn").click(function(){
						$("#excelFile").click();
					});

				});
				
			
			</script>				
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
									<th>获奖码</th>
									<th>奖品</th>
									<th>领奖状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${results }" var="awardList" varStatus="idx">
									<tr>
										<td><input type="checkbox" /></td>
										<td style="text-align: center;vertical-align: middle;"  myids="${awardList.id }">${(page-1)*6+idx.index+1 }</td>
										<td style="text-align: center;vertical-align: middle;">${awardList.telephone }</td>
										<td style="text-align: center;vertical-align: middle;">${awardList.name }</td>
										<td style="text-align: center;vertical-align: middle;">${awardList.isGetAward!=1?"没有领奖":"已经领奖" }</td>
										<td>
											<c:if test="${awardList.isGetAward!=1 }">
												<button class="btn btn-danger" type="button" onclick="javascript:changeIsGetAwardById('${awardList.id }')">领取奖品</button>
											</c:if>
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
	</div>
</body>
</html>
