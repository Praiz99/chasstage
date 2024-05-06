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
     <script type="text/javascript" src="${ctx}/static/jdone/js/db/resGroupForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 90%;">
	<form id="resGroupForm" name="resGroupForm">
		<input id="id" name="id" type="hidden" value="${resGroup.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty resGroup.id?'修改分组':'添加分组'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">分组名称：</label>
					</th>
					<td width="38%">
						<input name="groupName" class="required" type="text" maxlength="50"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">分组标识：</label>
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
						<label class="control-label">资源类型：</label>
					</th>
					<td width="38%">
						<select name="resType" id="resType">
							<option value="01">数据表</option>
							<option value="02">查询脚本</option>
						</select>
					</td>
					<th width="12%">
						<label class="control-label">数据源：</label>
					</th>
					<td width="38%">
						<select name="sourceId" id="dbSourceList" class="input-xlarge required">
						</select>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">分组描述：</label>
					</th>
					<td colspan="3">
						<textarea name="groupDesc" type="text" style="height: 60px;width: 80%;" maxlength="200"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveGroup()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/db/resource/resourceList'">
		</div>			
	</form>
</body>
</html>
