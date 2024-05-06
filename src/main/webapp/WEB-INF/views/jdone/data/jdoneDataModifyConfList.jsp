<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>通用数据变更配置管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript" src="${ctx}/static/jdone/js/data/jdoneDataModifyConfList.js"></script>
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
						<td><label class="search-label">流程key:</label><input name="actKey" type="text" class="search-textbox keydownSearch" /></td>
						<td>
							<label class="search-label">变更类型:</label>
							<select name="modifyType" class="search-textbox keydownSearch" >
								<option value=""  selected="selected"></option>
								<option value="UPDATE">UPDATE</option>
								<option value="DELETE">DELETE</option>
							</select>
						</td>
						<td><label class="search-label">变更业务类型:</label><input name="modifyBizType" type="text" class="search-textbox keydownSearch" /></td>
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
	</div>
</body>
</html>
