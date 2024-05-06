<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneActGroupForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="actGroupForm" name="actGroupForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${actGroup.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty actGroup.id?'修改分组':'添加分组'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">分组名称:</label>
					</th>
					<td width="38%">
						<input name="groupName" class="required" type="text" maxlength="30"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">分组标识:</label>
					</th>
					<td width="38%">
						<input name="groupMark" class="required" type="text" maxlength="30"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">分组描述:</label>
					</th>
					<td colspan="3">
						<textarea name="groupDesc" class="required" style="width:85%;height: 60px;" maxlength="200"></textarea>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveActGroup()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/act/actGroup/actGroupList'">
		</div>
	</form>
</body>
</html>
