<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>任务管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/task/taskTableList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
		<table id="dg"></table>
		<input id="depId"  type="hidden" value="${taskinst.deployId}">
		<input id="taskType"  type="hidden" value="${taskinst.taskType}">
</body>
</html>
