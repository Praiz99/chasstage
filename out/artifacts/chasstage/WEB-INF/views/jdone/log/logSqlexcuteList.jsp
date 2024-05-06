<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>SQL执行日志管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/log/logSqlexcuteList.js"></script>
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
						<td><label class="search-label">系统名称:</label><input name="appName" type="text" class="search-textbox keydownSearch" /></td>
						<td><label class="search-label">模块名称:</label><input name="moduleName" type="text" class="search-textbox keydownSearch" /></td>
						<td><label class="search-label">执行时长:</label>
							<select name="costName" class="search-textbox keydownSearch" >
								<option value="">--请选择--</option>
	                              	<option value="0-3000">低于3s</option>
	                              	<option value="3000-5000">3s至5s</option>
	                              	<option value="5000-8000">5s至8s</option>
	                              	<option value="8000-">8s以上</option>
							</select>
						</td>
						<td><label class="search-label">执行结果:</label>
							<select name="excuteResult" class="search-textbox keydownSearch" >
								<option value="">--请选择--</option>
	                              	<option value="01">成功</option>
	                              	<option value="00">失败</option>
							</select>
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
