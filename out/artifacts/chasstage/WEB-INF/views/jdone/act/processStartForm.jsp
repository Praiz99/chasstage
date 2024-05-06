<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/zfba/style/common/form/css/zfbaCommonForm.css">
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/processStartForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body >
	<form id="startProcessForm" name="startProcessForm" method="post">
		<input type="hidden" id="bizId" name="bizId" value="${bizId}">
		<input type="hidden" id="bizType" name="bizType" value="${bizType}">
		<input type="hidden" id="actKey" name="actKey" value="${actKey}">
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
						<input id="nextProId" name="currentNodeMark" type="hidden" value="">
						<div id="transDiv">
							<label id="tranRole">目标任务：</label> <br>
							<select id="nextOrg" name="currentProOrgSysCode" style="width: 260px;float: none;" onchange="getApprovalUser();">
								<option value="">===请选择===</option>
							</select><br>
							<select id="nextUsers" name="currentProUserId" multiple="multiple" style="WIDTH: 260px; height: 100px;float: none;">
							</select><br>
							<br>
						</div>
					</td>
				</tr>
				<tr>
					<th class="must">处理意见</th>
					<td >
						<textarea rows="5" id="proOpinion" class="form-textarea required" name="proOpinion" maxlength="512" style="width: 98%"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		</div>
	</form>
	<div class="bottomNav" >
		<input type="button" class="btn-b-90" value="提交审批"  onclick="startWorkflow();"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button"  class="btn-b-90" value="关&nbsp;闭" onclick="closeDialog();"/>
	</div>
</body>
</html>
