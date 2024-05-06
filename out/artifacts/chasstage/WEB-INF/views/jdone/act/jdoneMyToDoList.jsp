<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>我的待办</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/jdone/js/act/jdoneMyToDoList.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div id="content" class="row-fluid">
		<div class="searchDiv">
			<form id="searchForm">
				<table>
					<tr class="searchTr">
						<th>业务类型：</th>
						<td>
							<input name="bizType" class="easyui-textbox keydownSearch" />
						</td>
						<td>
							<a class="easyui-linkbutton" href="#" id="keydownSearch" data-options="iconCls:'icon-search'" onclick="searchFunc();">查找</a>
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