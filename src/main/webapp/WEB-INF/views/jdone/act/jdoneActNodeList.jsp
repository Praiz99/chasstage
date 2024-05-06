<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>流程节点管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneActNodeList.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<div id="content" class="row-fluid">
		<table id="datagrid"></table>
	</div>
	<div id="nodeRuleDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="节点流转配置" style="width: 800px; height: 420px;top:5%; left:5%;">
		<iframe scrolling="auto" id='openNodeRuleList' name="openNodeRuleList" frameborder="0" src="" style="width: 100%; height: 99%;"></iframe>
	</div>
	<div id="nodeProRuleFormDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="处理规则配置" style="width: 800px; height: 420px;">
		<iframe scrolling="no" id='nodeProRuleForm' name="nodeProRuleForm" frameborder="0" src="" style="width: 100%; height: 99%;"></iframe>
	</div>
</body>
</html>
