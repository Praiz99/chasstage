<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/frws/clientApp/clientAppForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="clientAppForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${clientApp.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width: 85px;">${not empty clientApp.id?'修改客户端':'添加客户端'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">应用名称:</label>
					</th>
					<td width="38%">
						<input name="appName" class="required" type="text" maxlength="200">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">应用标识:</label>
					</th>
					<td width="38%">
						<input name="appMark" class="required" type="text" maxlength="200">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">客户端根路径:</label>
					</th>
					<td colspan="3">
						<input name="clientRootUrl" class="required" style="width: 60%;" type="text" maxlength="500">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">客户端dubbo根路径:</label>
					</th>
					<td colspan="3">
						<input name="clientDubboRootUrl" style="width: 60%;" type="text" maxlength="500">
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveClientApp()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/clientApp/clientAppList'">
		</div>
	</form>
</body>
</html>
