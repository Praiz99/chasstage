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
    <script type="text/javascript" src="${ctx}/static/jdone/js/devops/app/version/addVerForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body  style="overflow-y: hidden;overflow-x: hidden;">
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
						<input name="appName" class="input-xlarge" type="text">
					</td>
					<th width="12%">
						<label class="control-label">应用标识:</label>
					</th>
					<td width="38%">
						<input name="appMark" class="required input-xlarge" type="text"  value="${appname}">
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
						<label class="control-label">升级类型:</label>
					</th>
					<td width="38%">
						<select id="upgradeType"  onchange="func()" name="upgradeType" style="width:260px;">
								<option value="update" >增量</option>
								<option value="full" >全量</option>
						</select>
					</td>
				</tr>
				<tr id="BaseVersion">
					<th width="12%">
						<label class="control-label">基础版本:</label>
					</th>
					<td colspan="3">
						<input name="appBaseVersionNo" class="required input-xlarge" type="text" value="${appVersionNo}">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">版本描述:</label>
					</th>
					<td colspan="3">
						<textarea name="packageDesc" style="width:85%;height: 250px;"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveUpgradeRecord()">&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存并打包" onclick="saveFileUpgrade()">&nbsp;
		</div>
	</form>
</body>
</html>
