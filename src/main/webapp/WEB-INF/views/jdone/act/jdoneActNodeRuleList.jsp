<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>流程节点流转规则管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneActNodeRuleList.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<div id="content" class="row-fluid">
		<input id="nodeMark" name="nodeMark" type="hidden" value="${nodeRule.nodeMark}">
		<input id="modelId" name="modelId" type="hidden" value="${nodeRule.modelId}">
		<input id="nodeId" name="nodeId" type="hidden" value="${nodeId}">
		<table id="datagrid"></table>
	</div>
</body>
</html>
