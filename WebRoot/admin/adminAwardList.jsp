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
	
	function changeIsGetAwardById(id){
		if(window.confirm("你确定要领取奖品吗？(注意：确定后无法撤销领取)")){
			$.post("adminAwardList_changeIsGetAwardByAId.action", {
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

			<form id="search_fm" action="adminAwardList_findPageByMap" method="post"
				class="form-search" style="display: inline;">
				&nbsp; 获奖者： <input id="search_name" value="" type="text"
					class="input-medium search-query" placeholder="输入获奖者名...">
				&nbsp; 联系电话： <input id="search_tel" value="" type="text"
					class="input-medium search-query" placeholder="输入获奖电话...">
				&nbsp; 奖品：<select id="search_award">
								<option value="">请选择</option>
								<c:forEach items="${awards }" var="award">
									<option value="${award.name }">${award.name }</option>
								</c:forEach>
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
									<th>获奖者</th>
									<th>联系电话</th>
									<th>奖品</th>
									<th>是否领奖</th>
									<th>获奖时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${results }" var="awardList" varStatus="idx">
									<tr>
										<td><input type="checkbox" /></td>
										<td style="text-align: center;vertical-align: middle;"  myids="${awardList.id }">${(page-1)*6+idx.index+1 }</td>
										<td style="text-align: center;vertical-align: middle;">${awardList.name }</td>
										<td style="text-align: center;vertical-align: middle;">${awardList.telephone }</td>
										<td style="text-align: center;vertical-align: middle;">${awardList.awardName }</td>
										<td style="text-align: center;vertical-align: middle;">${awardList.isGetAward!=1?"没有领奖":"已经领奖" }</td>
 										<td style="text-align: center;vertical-align: middle;">
											<fmt:formatDate  value="${awardList.awardTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
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
