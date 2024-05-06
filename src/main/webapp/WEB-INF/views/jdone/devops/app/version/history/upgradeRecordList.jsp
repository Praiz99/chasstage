<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>版本历史</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/devops/app/version/history/upgradeRecordList.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
	<script type="text/javascript" src="${ctx}/static/jdone/js/common/ext/grid-search.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	var mode = '${mode}';
    </script>
</head>
<body  class="easyui-layout new-dialog">
	<div>
		<div class="row-fluid">
			<form id="searchForm">
				<c:if test="${not empty appMark}">
					<input name="appMark" type="hidden" value="${appMark}" />
				</c:if>
				<table>
					<tr class="searchTr">
						<c:if test="${empty appMark}">
							<td><label class="search-label">应用标识:</label><input name="appMark" type="text" class="search-textbox keydownSearch" /></td>
						</c:if>
						<td><label class="search-label">上线时间:</label>
							<input name="onlineStartTime" style="width: 100px;" id="onlineStartTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,maxDate:'#F{$dp.$D(\'onlineEndTime\')}'});"/>
						-
						<input name="onlineEndTime" style="width: 100px;" id="onlineEndTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'onlineStartTime\')}'});"/>
						</td>
						<td><label class="search-label">版本号:</label>
							<input name="appVersionNo" type="text" class="search-textbox keydownSearch" />
						</td>
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
		<div id="processNotice" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="升级版本详情" style="width:1000px; height:600px;overflow:hidden">
			<iframe scrolling="no" id='openProcessNotice' name="openProcessNotice" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
		</div>
		<div id="addUpgradeRecord" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="升级版本信息" style="width:1000px; height:600px;overflow:hidden">
			<iframe scrolling="no" id='openAddUpgradeRecord' name="openAddUpgradeRecord" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
		</div>
</body>
</html>
