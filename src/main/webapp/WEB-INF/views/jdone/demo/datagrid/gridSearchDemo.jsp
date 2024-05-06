<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>流程分组管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/jdone/js/demo/datagrid/gridSearchDemo.js"></script>
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
		<form>
			<table>
				<tr class="searchTr">
					<td><label class="search-label">案件编号:</label><input name="groupMark" type="text" class="search-textbox keydownSearch" /></td>
					<td><label class="search-label">名称:</label><input name="groupMark" type="text" class="search-textbox keydownSearch" /></td>
					<td><label class="search-label">案件状态:</label><select name="groupName" class="search-textbox keydownSearch" ></select></td>
					<td><label class="search-label">案件类型:</label><input name="groupMark" type="text" class="search-textbox keydownSearch" /></td>
					<td><label class="search-label">审核状态:</label><input name="groupMark" type="text" class="search-textbox keydownSearch" /></td>
					<td><label class="search-label">主单位:</label><input name="groupMark" type="text" class="search-textbox keydownSearch" /></td>
					<td><label class="search-label">分组名称:</label><input name="groupName" type="text" class="search-textbox keydownSearch" /></td>
					<td><label class="search-label">分识:</label><input name="groupMark" type="text" class="search-textbox keydownSearch" /></td>
					<td>
						<label class="search-label">创建时间:</label>
						<input name="uploadStartTime" style="width: 80px;" id="uploadStartTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' ,maxDate:'#F{$dp.$D(\'uploadEndTime\')}'});"/>
						-
						<input name="uploadEndTime" style="width: 80px;" id="uploadEndTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' ,maxDate:'#F{$dp.$D(\'uploadStartTime\')}'});"/>
					</td>
					<td><label class="search-label">分识:</label><input name="groupMark" type="text" class="search-textbox keydownSearch" /></td>
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
	<div class="datagrid-toolbar" style="margin-bottom: 0;">
		<div class="search-operarea">
			<div style="float: left;">
				<a href="javascript:;" class="l-btn l-btn-small l-btn-plain">
					<span class="l-btn-left">
						<span class="l-btn-text">新增</span>
					</span>
				</a>
				<a href="javascript:;" class="l-btn l-btn-small l-btn-plain">
					<span class="l-btn-left">
						<span class="l-btn-text">删除</span>
					</span>
				</a>
				<a href="javascript:;" class="l-btn l-btn-small l-btn-plain">
					<span class="l-btn-left">
						<span class="l-btn-text">删除</span>
					</span>
				</a>
			</div>
			<div style="float: right;">
				<div style="float: left;">
					<form class="quick-search">
						<input name="cxStartTime" id="xz" type="radio" value="1" checked="checked"/><label class="quick-label" for="xz">行政</label>
						<input name="cxStartTime" id="xs" type="radio" value="2"/><label class="quick-label" for="xs">刑事</label>
						<input name="cxStartTime" id="zwp" type="radio" value="3"/><label class="quick-label" for="zwp">转未破</label>
						<input name="cxStartTime" id="yja" type="radio" value="4"/><label class="quick-label" for="yja">已结案</label>
					</form>
				</div>
				<div style="float: right;">
					<form class="quick-search" style="border-left: 1px solid rgb(204, 204, 204);">
						<input name="cxTime" id="ys" type="radio" value="4"/><label class="quick-label" for="ys" style="margin-left: 6px;">已审</label>
						<input name="cxTime" id="ws" type="radio" value="4"/><label class="quick-label" for="ws">未审</label>
					</form>
				</div>
			</div>
		</div>
	</div>
	<table id="datagrid"></table>
</body>
</html>
