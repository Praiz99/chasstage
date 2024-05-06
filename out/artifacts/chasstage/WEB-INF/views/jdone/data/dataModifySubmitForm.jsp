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
    <script type="text/javascript" src="${ctx}/static/jdone/js/data/dataModifySubmitForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body >
	<form id="startProcessForm" name="startProcessForm" method="post">
		<input type="hidden" id="dataStr" value='${dataStr}'>
		<div class="panel-detail" style="margin: 3px;">
		<table id="nextTable" class="common_table_form">
			<tbody>
				<tr class="sub-caption">
					<td colspan="4">${bizName}
					</td>
				</tr>
				<tr id="transTr">
					<th >传送给</th>
					<td style="height:130px;">
						<input id="nextNodeId" name="currentNodeId" type="hidden" value="">
						<input id="nextNodeMark" name="currentNodeMark" type="hidden" value="">
						<div id="transDiv">
							<label id="tranRole">目标任务：</label> <br>
							<select id="nextOrg" name="currentProOrgSysCode" style="width: 260px;float: none;" onchange="getApprovalUser();">
								<option value="">===请选择===</option>
							</select><br>
							<select id="nextUsers" name="currentProUserId" multiple="multiple" style="WIDTH: 260px; height: 100px;float: none;">
							</select><br>
							<input type='checkbox' id='isMobileRmd' name='isMobileRmd' value='1'>发短信
							<input type='checkbox' id='isMailRmd' name='isMailRmd' value='1'>发邮件
							<br>
						</div>
					</td>
				</tr>
				<tr>
					<th class="must" id="bgyyTitle">变更原因</th>
					<td >
						<textarea rows="5" id="proOpinion" class="form-textarea required" name="proOpinion" maxlength="512" style="width: 98%"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		</div>
	</form>
	<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
		<input id="btnSubmit" class="btn btn-primary" type="button" value="提交审批" onclick="startWorkflow()">
		<input id="btnCancel" class="btn" type="button" value="关&nbsp;闭" onclick="closeDialog();">
		<input id="btnSubmit" class="btn btn-primary" type="button" value="流程历史" onclick="openProcessHistory()">
		<input id="btnProcessRecession" class="btn btn-primary" type="button" value="流程撤回" onclick="processRecession()">
	</div>
</body>
</html>
