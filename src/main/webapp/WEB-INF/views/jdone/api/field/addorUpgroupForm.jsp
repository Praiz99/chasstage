<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="addorUpgroupForm" name="addorUpgroupForm" action="savegroup" method="post" >
		<input id="id" name="id" type="hidden" value="${apiGroup.id}">
		<input name="apiType" id="apiType" type="hidden"  value="${apiGroup.apiType}">
		<div class="form-panel">
		    <div class="panel-top">
				<div class="tbar-title">
					<span>${bs}接口分组</span>	
				</div>
			</div>
		<div class="panel-body">
	    	<table class="table_form">
				<tr>
					<th width="12%">
						分组名称:
					</th>
					<td width="38%">
						<input name="groupName" class="required" type="text" maxlength="50" value="${apiGroup.groupName}">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						分组标识:
					</th>
					<td width="38%">
					<input name="groupMark" type="text" id="groupMark" maxlength="50" minlength="1" value="${apiGroup.groupMark}">
					</td>
				</tr>
				<tr>
					<th width="12%">
						备注:
					</th>
					<td width="38%">
						<input name="remark" type="text" id="remark" maxlength="50" minlength="1" value="${apiGroup.remark}">
					</td>
				</tr>
		</table>
		<%-- <div class="form-btns">
			<input id="btnSubmit" class="button" type="submit" value="保 存">&nbsp;
			<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/api/field/groupList'">
		</div> --%>
	</div>
	</div>	
	</form>
</body>
</html>
