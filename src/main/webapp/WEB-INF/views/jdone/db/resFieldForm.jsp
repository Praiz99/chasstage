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
    <script type="text/javascript" src="${ctx}/static/jdone/js/db/resFieldForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="fieldForm" name="fieldForm">
		<input id="id" name="id" type="hidden" value="${resField.id}">
		<input id="resId" name="resId" type="hidden" value="${resField.resId}">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width: 100px;">${not empty resField.id?'修改字段':'添加字段'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">字段名称：</label>
					</th>
					<td width="38%">
						<input name="fieldMark" class="required" type="text" maxlength="30"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">字段中文名称：</label>
					</th>
					<td width="38%">
						<input name="fieldName" type="text" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">字段类型：</label>
					</th>
					<td width="38%">
						<input name="fieldType" class="required" type="text" maxlength="50"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">字段长度：</label>
					</th>
					<td width="38%">
						<input name="fieldLength" class="digits" type="text" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">是否权限控制：</label>
					</th>
					<td width="38%">
						<input type="radio" value="1" name="isEnableAuth"/>是
						<input type="radio" value="0" checked="checked" name="isEnableAuth"/>否
					</td>
					<th width="12%">
						<label class="control-label">字段描述：</label>
					</th>
					<td width="38%">
						<input name="fieldDesc" type="text" maxlength="200"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveField()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/db/resField/resFieldList'">
		</div>	
	</form>
</body>
</html>
