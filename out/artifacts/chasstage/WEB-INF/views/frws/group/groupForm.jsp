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
    <script type="text/javascript" src="${ctx}/static/frws/group/groupForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="groupForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${group.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width: 85px;">${not empty group.id?'修改分组':'添加分组'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">分组名称:</label>
					</th>
					<td width="38%">
						<input name="groupName" class="required" type="text" maxlength="200">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">分组标识:</label>
					</th>
					<td width="38%">
						<input name="groupMark" class="required" type="text" maxlength="200">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">容器列表:</label>
					</th>
					<td colspan="3">
						<input name="storageList" class="required" style="width: 60%;" type="text" maxlength="500">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">文件客户端列表:</label>
					</th>
					<td colspan="3" id="clientList">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">已授权应用列表:</label>
					</th>
					<td colspan="3" id="authAppList">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">已授权单位代码列表:</label>
					</th>
					<td colspan="3">
						<input name="belongMatchStrs" class="required" style="width: 60%;" type="text" maxlength="500">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveClientApp()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/group/groupList'">
		</div>
	</form>
</body>
</html>
