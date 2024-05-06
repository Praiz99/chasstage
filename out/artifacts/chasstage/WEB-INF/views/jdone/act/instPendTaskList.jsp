<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>我的待办</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
<script type="text/javascript" src="${ctx}/static/jdone/js/common/ext/grid-search.js"></script>
<script type="text/javascript" src="${ctx}/static/dic/ZD_JDONE_ACT_YWLX.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/act/instPendTaskList.js"></script>
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
						<td><label class="search-label">业务类型:</label>
						<input type="text"  pageSize="10" hiddenField="bizType" dicName="ZD_JDONE_ACT_YWLX" class="dic search-textbox keydownSearch"/>
						<input type="hidden" name="bizType" id="bizType">
						<td><label class="search-label">业务ID:</label><input name="bizId" type="text" class="search-textbox keydownSearch" /></td>
						<td><label class="search-label">发起人:</label>
							<input type="text"  pageSize="10" hiddenField="fqr" dicName="ZD_SYS_USER_ID" dynamic="true" class="dic search-textbox keydownSearch"/>
							<input type="hidden" name="fqr" id="fqr">
						</td>
						<td><label class="search-label">发起单位:</label>
							<input type="text"  pageSize="10" hiddenField="fqdw" dicName="ZD_JDONE_ORG_SYS_CODE" dynamic="true"  class="dic search-textbox keydownSearch"/>
							<input type="hidden" name="fqdw" id="fqdw">
						</td>
						<td><label class="search-label">发起时间:</label>
							<input name="sendStartTime" style="width: 100px;" id="sendStartTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,maxDate:'#F{$dp.$D(\'sendEndTime\')}'});"/>
						-
						<input name="sendEndTime" style="width: 100px;" id="sendEndTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'sendStartTime\')}'});"/>
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
		<div id="lcgj"></div>
		<div id="dclr"></div>
	</div>
</body>
</html>
