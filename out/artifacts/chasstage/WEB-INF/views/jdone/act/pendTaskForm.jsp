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
	        	<span style="width:125px;">待处理流程详情</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">业务类型:</label>
					</th>
					<td width="38%">
						<label>${biz_type}</label>
					</td>
					<th width="12%">
						<label class="control-label">业务ID:</label>
					</th>
					<td width="38%">
						<label>${pendTask.bizId}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">发起人:</label>
					</th>
					<td width="38%">
						<label>${fqr}</label>
					</td>
					<th width="12%">
						<label class="control-label">发起单位:</label>
					</th>
					<td width="38%">
						<label>${fqdw}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">接收人:</label>
					</th>
					<td width="38%">
						<label>${jsr}</label>
					</td>
					<th width="12%">
						<label class="control-label">接收单位:</label>
					</th>
					<td width="38%">
						<label>${jsdw}</label>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/act/pendTask/instPendTaskList'">
		</div>
	</form>
</body>
</html>
