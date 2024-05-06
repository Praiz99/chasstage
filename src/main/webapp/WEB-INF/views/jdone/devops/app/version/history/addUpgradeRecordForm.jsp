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
    <script type="text/javascript" src="${ctx}/static/jdone/js/devops/app/version/history/addUpgradeRecordForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="upgradeRecordForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span>版本信息</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">应用名称:</label>
					</th>
					<td width="38%">
						<input name="appName" class="required input-xlarge" type="text"  maxlength="30">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">应用标识:</label>
					</th>
					<td width="38%">
						<input name="appMark" class="required input-xlarge" type="text"  maxlength="30">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">应用版本号:</label>
					</th>
					<td width="38%">
						<input name="appVersionNo" class="required input-xlarge" type="text" >
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">版本包名称:</label>
					</th>
					<td width="38%">
						<input name="packageName" class="required input-xlarge" type="text" >
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">版本包描述:</label>
					</th>
					<td colspan="3">
						<textarea name="packageDesc" style="width:85%;height: 250px;"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="确 定" onclick="saveUpgradeRecord()">&nbsp;
		</div>
	</form>
</body>
</html>
