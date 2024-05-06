<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>应用管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/jquery.json-2.2.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/appList.js"></script>
<script type="text/javascript"src="${ctx}/static/jdone/js/common/selector/selector.socpe.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<input type="hidden" id="resType" value="${resType}"/>
	<div id="content" class="row-fluid">
		<table id="datagrid"></table>
	</div>
	<div id="hpSetDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="配置首页" style="width: 800px; height: 500px;top:12%; left:15%;">
		<iframe scrolling="auto" id='hp' name="hp" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
	</div>
</body>
</html>
