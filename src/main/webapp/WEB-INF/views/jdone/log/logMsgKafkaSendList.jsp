<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>kafka消息管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript" src="${ctx}/static/jdone/js/log/logMsgKafkaSendList.js?1=1"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
<script type="text/javascript" src="${ctx}/static/jdone/js/common/ext/grid-search.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div class="row-fluid">
		<form id="searchForm">
			<table>
				<tr class="searchTr">
					<td><label class="search-label">消息容器:</label><input name="topic" type="text" class="search-textbox keydownSearch" /></td>
					<td><label class="search-label">消息类型:</label><input name="msgType" type="text" class="search-textbox keydownSearch" /></td>
					<td>
						<label class="search-label">发送时间:</label>
						<input name="sendStartTime" style="width: 80px;" id="sendStartTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' ,maxDate:'#F{$dp.$D(\'sendEndTime\')}'});"/>
						-
						<input name="sendEndTime" style="width: 80px;" id="sendEndTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' ,minDate:'#F{$dp.$D(\'sendStartTime\')}'});"/>
					</td>
					<td><label class="search-label">应用名称:</label><input name="appName" type="text" class="search-textbox keydownSearch" /></td>
				</tr>
			</table>
			<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchFunc()">
				<input id="btnCancel" class="btn" type="button" value="重置" onclick="ClearQuery();">
			</div>
		</form>
	</div>
	<div class="extend-search">
	</div>
	<table id="datagrid"></table>
	<div id="processResend" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="消息重发" style="width:1000px; height:600px;overflow:hidden">
		<iframe scrolling="no" id='openProcessResend' name="openProcessResend" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
	</div>
</body>
</html>
