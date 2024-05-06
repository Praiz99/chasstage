<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
	<script type="text/javascript" src="${ctx}/static/framework/utils/json2.js"></script>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/data/css/dataModifySubmitForm.css">
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneModifyApproverForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="startProcessForm" name="startProcessForm" method="post">
		<input id="bizId" name="bizId" type="hidden" value="${bizId}">
		<input id="bizType" name="bizType" type="hidden" value="${bizType}">
		<input id="taskId" name="taskId" type="hidden" value="${taskId}">
		<input id="nodeProId" name="nodeProId" type="hidden" value="${nodeProId}">
		<div class="panel-detail" style="margin: 3px;">
		<table id="nextTable" class="common_table_form">
			<tbody>
				<tr class="sub-caption">
					<td colspan="4">变更审批人
					</td>
				</tr>
				<tr id="transTr">
					<th >传送给</th>
					<td style="height:130px;">
						<input id="nextNodeId" name="currentNodeId" type="hidden" value="">
						<input id="nextNodeMark" name="currentNodeMark" type="hidden" value="">
						<div id="transDiv">
							<div style="margin: 3px 0px;">
								<label>目标节点：</label>
								<select id="nextNodeSelect" style="width: 200px;float: none;" onchange="nextNodeChange();">
								</select>
							</div>
							<div style="margin: 3px 0px;">
								<label id="tranRole">目标任务：</label> <br>
								<select id="nextOrg" name="currentProOrgSysCode" style="width: 260px;float: none;" onchange="getApprovalUser();">
									<option value="">===请选择===</option>
								</select>
							</div>
							<select id="nextUsers" name="currentProUserId" multiple="multiple" style="WIDTH: 260px; height: 100px;float: none;">
							</select><br>
							<br>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		</div>
	</form>
	<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
		<input id="btnSubmit" class="btn btn-primary" type="button" value="确认变更" onclick="updateApprover()">
		<input id="btnCancel" class="btn" type="button" value="关&nbsp;闭" onclick="closeDialog();">
	</div>
</body>
</html>
