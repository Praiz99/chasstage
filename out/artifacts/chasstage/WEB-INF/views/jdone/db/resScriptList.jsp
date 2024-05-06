<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>查询脚本管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/jdone/js/db/resScriptList.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div id="content" class="row-fluid">
		<div class="searchDiv">
			<input id="groupId" name="groupId" type="hidden" value="${resScript.groupId}">
			<input id="sourceId" name="sourceId" type="hidden" value="${resScript.sourceId}">
			<form id="searchForm">
				<table>
					<tr class="searchTr">
						<th>脚本标识：</th>
						<td><input name="scriptMark" class="easyui-textbox keydownSearch" /></td>
						<th>脚本名称：</th>
						<td><input name="scriptName" class="easyui-textbox keydownSearch" /></td>
						<td>
							<a class="easyui-linkbutton" href="#" id="keydownSearch" data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
						</td>
						<td>
							<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="ClearQuery();">清空</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<table id="datagrid"></table>
	</div>
</body>
</html>
