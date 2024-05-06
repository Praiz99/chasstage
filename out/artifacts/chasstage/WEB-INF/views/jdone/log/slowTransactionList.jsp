<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>接口日志管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/log/slowTransactionList.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
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
						<td><label class="search-label">数据库名称:</label><input name="datname" type="text" class="search-textbox keydownSearch" /></td>
						<td><label class="search-label">入库时间:</label>
							<input name="sendStartTime" style="width: 100px;" id="sendStartTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,maxDate:'#F{$dp.$D(\'sendEndTime\')}'});"/>
						-
						<input name="sendEndTime" style="width: 100px;" id="sendEndTime" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'sendStartTime\')}'});"/>
						</td>
						<td><label class="search-label">服务器ip:</label>
							<input name="detectionIp" type="text" class="search-textbox keydownSearch" />
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
	
</body>
</html>
