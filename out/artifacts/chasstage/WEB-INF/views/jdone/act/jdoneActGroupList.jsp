<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>流程分组管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/jdone/js/act/jdoneActGroupList.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
<script type="text/javascript" src="${ctx}/static/jdone/js/common/ext/grid-search.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div >
		<div class="row-fluid">
			<form id="searchForm">
				<table>
					<tr class="searchTr">
						<td><label class="search-label">分组名称:</label><input name="groupName" type="text" class="search-textbox keydownSearch" /></td>
						<td><label class="search-label">分组标识:</label><input name="groupMark" type="text" class="search-textbox keydownSearch" /></td>
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
		<div id="processDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="数据变更申请" style="width: 700px; height: 450px;top:10%; left:20%;">
			<iframe scrolling="auto" id='openProcessList' name="openProcessList" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
		</div>
	</div>
</body>
</html>
