<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>任务列表</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/taskClient/js/taskJobObjList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
		<table id="dg"></table>
</body>
</html>
