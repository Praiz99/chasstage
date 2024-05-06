<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="logOperForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width:125px;">慢SQL日志详情</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">进程ID:</label>
					</th>
					<td width="38%">
						<label>${slowSql.pid}</label>
					</td>
					<th width="12%">
						<label class="control-label">用户名:</label>
					</th>
					<td width="38%">
						<label>${slowSql.usename}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">数据库名称:</label>
					</th>
					<td width="38%">
						<label>${slowSql.datname}</label>
					</td>
					<th width="12%">
						<label class="control-label">数据库IP:</label>
					</th>
					<td width="38%">
						<label>${slowSql.clientAddr}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">执行开始时间:</label>
					</th>
					<td width="38%">
						<label>${slowSql.queryStart}</label>
					</td>
					<th width="12%">
						<label class="control-label">入库时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${slowSql.tjxgsj}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				
				<tr>
					<th width="12%">
						<label class="control-label">抓取服务器IP:</label>
					</th>
					<td width="38%">
						<label>${slowSql.detectionIp}</label>
					</td>
					<th width="12%">
						<label class="control-label">执行应用:</label>
					</th>
					<td width="38%">
					<label>${slowSql.applicationName}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">执行SQL:</label>
					</th>
					<td colspan="3">
						<textarea readonly="readonly" style="width:85%;height: 100px;">${slowSql.query}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/slowSqlTransaction/log/slowSqlList'">
		</div>
	</form>
</body>
</html>
