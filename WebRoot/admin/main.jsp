<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/uniform.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/unicorn.main.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/unicorn.grey.css" class="skin-color" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/admin/js/jquery-1.11.1.js"></script>
<script
	src="${pageContext.request.contextPath}/admin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jeDate/jedate.js"></script> 
<script type="text/javascript">
// $(function(){
// 	var sectionPage="section.jsp";var topicPage="topic.jsp";var userPage="user.jsp";var zonePage="zone.jsp";var addTeacher="addTeacher.jsp";var addCourse="addCourse.jsp";var addChapter="addchapter.jsp"
// 	var curPage='${mainPage}';var addAdv="addAdvertisement";var addclient="addclient.jsp";var addHR="addHR.jsp";var student="student.jsp";
// 	if(sectionPage.indexOf(curPage)>=0&&curPage!=""){
// 		$("#sectionLi").addClass("active");
// 	} else if(topicPage.indexOf(curPage)>=0&&curPage!=""){
// 		$("#topicLi").addClass("active");
// 	} else if(userPage.indexOf(curPage)>=0&&curPage!=""){
// 		$("#userLi").addClass("active");
// 	} else if(zonePage.indexOf(curPage)>=0&&curPage!=""){
// 		$("#zoneLi").addClass("active");
// 	}else if(addTeacher.indexOf(curPage)>=0&&curPage!=""){
// 		$("#addTeacherLi").addClass("active");
// 	}else if(addCourse.indexOf(curPage)>=0&&curPage!=""){
// 		$("#addCourseLi").addClass("active");
// 	}else if(addChapter.indexOf(curPage)>=0&&curPage!=""){
// 		$("#addChapterLi").addClass("active");
// 	}else if(addAdv.indexOf(curPage)>=0&&curPage!=""){
// 		$("#addAdvLi").addClass("active");
// 	}else if(addclient.indexOf(curPage)>=0&&curPage!=""){
// 		$("#addclientLi").addClass("active");
// 	}else if(addHR.indexOf(curPage)>=0&&curPage!=""){
// 		$("#addHRLi").addClass("active");
// 	}else if(student.indexOf(curPage)>=0&&curPage!=""){
// 		$("#addStudentLi").addClass("active");
// 	}
	
// });

</script>
</head>
<%-- <%  if(session.getAttribute("currentUser")==null)
{response.sendRedirect("login.jsp"); return; } 
%> --%>
<body>
	<div id="header">
		<h1 style="margin-left: 0px;padding-left: 0px;">
			<a href="#">商品运输管理系统</a>
		</h1>
		<!-- <h2 style="padding: 0px; margin-top: 10px; margin-bottom: 0px;">
			<a href="#"><font color="#cccccc">Java1234论坛</font></a>
		</h2>
		<h3 style="margin: 0px 0px 0px 40px;">
			<a href="#"><font color="#cccccc">后台管理</font></a>
		</h3> -->
	</div>
		

	<div id="sidebar">
		<ul>
		
			<li class="submenu"><a href="#"><i class="icon icon-th-list"></i>
					<span>功能</span> <span class="label">5</span></a>
				<ul>
					<li id="addTeacherLi"><a href="${pageContext.request.contextPath}/admin/adminCompetition_findPage"><i
						class="icon icon-home"></i>赛事管理</a></li>
					<li id="addTeacherLi"><a href="${pageContext.request.contextPath}/admin/adminTeam_findPage?mainPage=adminTeam.jsp&findPageTarget=/admin/adminTeam_findPage"><i
						class="icon icon-home"></i>队伍管理</a></li>
					<li id="addTeacherLi"><a href="${pageContext.request.contextPath}/admin/adminPicture_findPage"><i
						class="icon icon-home"></i>照片管理</a></li>					
				</ul>
			</li>

			<li class="submenu"><a href="#"><i class="icon icon-th-list"></i>
					<span>奖品管理</span> <span class="label">5</span></a>
				<ul>
					<li id="addTeacherLi"><a href="${pageContext.request.contextPath}/admin/adminAward_findPage?mainPage=AdminAward.jsp&findPageTarget=/admin/adminAward_findPage"><i
						class="icon icon-home"></i>奖品管理</a></li>
					<li id="addTeacherLi"><a href="${pageContext.request.contextPath}/admin/adminAwardList_findPage"><i
						class="icon icon-home"></i>龙狮获奖名单</a></li>
					<li id="addTeacherLi"><a href="${pageContext.request.contextPath}/admin/adminOtherAwardList_findPage"><i
						class="icon icon-home"></i>人气获奖名单</a></li>	
					<li id="addTeacherLi"><a href="${pageContext.request.contextPath}/admin/adminOtherAwardList2_findPage"><i
						class="icon icon-home"></i>地推获奖名单</a></li>				
				</ul>
			</li>
			
		</ul>

	</div>

	<div id="style-switcher">
		<i class="icon-arrow-left icon-white"></i> <span>颜色:</span> <a
			href="#grey"
			style="background-color: #555555; border-color: #aaaaaa;"></a> <a
			href="#blue" style="background-color: #2D2F57;"></a> <a href="#red"
			style="background-color: #673232;"></a>
	</div>

	<div id="content">
		<div id="content-header">
			<h1>微信投票后台管理</h1>
			<!-- <div class="btn-group">
				<a class="btn btn-large tip-bottom" title="Manage Files"><i
					class="icon-file"></i></a> <a class="btn btn-large tip-bottom"
					title="Manage Users"><i class="icon-user"></i></a> <a
					class="btn btn-large tip-bottom" title="Manage Comments"><i
					class="icon-comment"></i><span class="label label-important">5</span></a>
				<a class="btn btn-large tip-bottom" title="Manage Orders"><i
					class="icon-shopping-cart"></i></a>
			</div> -->
		</div>
		<div id="breadcrumb">
			
			</a> <a href="#" class="current">${crumb1 }</a>
		</div>
		<jsp:include page="${mainPage }"></jsp:include>
		<div class="row-fluid">
			<div id="footer" class="span12">
				2016 &copy;&nbsp;&nbsp;&nbsp;&nbsp; 微信投票&nbsp;&nbsp;&nbsp;&nbsp; <a
					href="#">后台管理</a>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/admin/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/admin/js/jquery.ui.custom.js"></script>
	<script src="${pageContext.request.contextPath}/admin/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/admin/js/jquery.uniform.js"></script>
	<!-- <script src="js/select2.min.js"></script> -->
	<script src="${pageContext.request.contextPath}/admin/js/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/admin/js/unicorn.js"></script>
	<script src="${pageContext.request.contextPath}/admin/js/unicorn.tables.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/admin/js/uploadPreview.min.js"></script>
</body>
</html>