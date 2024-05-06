<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/role/roleCatForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="roleCatForm" name="roleCatForm">
		<input id="groupMark" type="hidden" value="${sysRoleCat.groupMark}">
		<input id="id" name="id" type="hidden" value="${sysRoleCat.id}">
		<div class="form-panel">
			<div class="panel-top">
				<div class="tbar-title">
					<span>${not empty roleCat.id?'修改分组':'添加分组'}</span>	
				</div>
			</div>
			<div class="panel-body">
				<table class="table_form">
					<tbody>
						<tr>
							<th width="12%" class="must">
								<span style="padding-right:4px">*</span>分组名称：
							</th>
							<td width="38%">
								<input name="groupName" class="required" type="text" maxlength="50"/>
							</td>
							<th width="12%" class="must">
								<span style="padding-right:4px">*</span>分组标识：
							</th>
							<td width="38%">
								<input name="groupMark" class="required" type="text" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<th width="12%">
								分组描述：
							</th>
							<td colspan="3">
								<textarea name="groupDesc" type="text" style="height: 50px;" maxlength="200"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="form-btns">
					<input id="btnSubmit" class="button" type="button" value="保 存" onclick="saveGroup()">&nbsp;
					<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/sys/role/roleList'">
				</div>				
			</div>
		</div>
	</form>
</body>
</html>
