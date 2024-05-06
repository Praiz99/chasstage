<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>选择登陆身份</title>
		<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
		<script type="text/javascript" src="${ctx}/static/framework/utils/json2.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/style.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/main.css" />
		<script type="text/javascript" src="${ctx}/static/jdone/js/sys/index/loginRoleSelect.js"></script>
		<script type="text/javascript">
			var ctx = "${ctx}";
		</script>
	</head>
	<body>
		<input id="currentSelected" type="hidden" value="${currentSelected}" />
		<input id="userRoles" type="hidden" value='${userRoles}' />
		<!-- 选择角色 -->
		<div id="selectRole" style="overflow:hidden;"></div>		
	</body>
	
</html>
