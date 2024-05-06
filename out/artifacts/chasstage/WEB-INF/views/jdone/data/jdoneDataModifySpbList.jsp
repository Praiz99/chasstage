<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>通用数据变更记录管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript" src="${ctx}/static/jdone/js/data/jdoneDataModifySpbList.js"></script>
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
						<td><label class="search-label">申请人姓名:</label><input name="sqrXm" type="text" class="search-textbox keydownSearch" /></td>
						<td><label class="search-label">申请人身份证:</label><input name="sqrSfzh" type="text" class="search-textbox keydownSearch" /></td>
						<td><label class="search-label">流程key:</label><input name="actKey" type="text" class="search-textbox keydownSearch" /></td>
						<td><label class="search-label">变更类型:</label><select name="modifyType" class="search-textbox keydownSearch" >
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
	<div id="processDialog" class="easyui-dialog" closed="true" closable="false" buttons="#dlg-buttons" modal="true" title="流程轨迹" style="width: 700px; height: 400px;top:10%;overflow:hidden;">
		<iframe scrolling="auto" id='openProcessList' name="openProcessList" frameborder="0" src="" style="width: 100%; height: 88%;"></iframe>
		<div style="padding:5px;text-align:center;">
			<a  class="easyui-linkbutton" icon="icon-cancel" onclick="$('#processDialog').window('close')">关闭</a>
		</div>
	</div>
</body>
</html>
