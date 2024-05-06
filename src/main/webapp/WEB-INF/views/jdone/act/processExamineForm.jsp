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
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/act/css/processCompleteForm.css">
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/processExamineForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="margin: 0px;height: 95%;overflow: auto;">
	<form id="shForm" name="shForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${pendTask.id}">
		<input id="instId" name="instId" type="hidden" value="${pendTask.instId}">
		<table id="shTable" class="table" style="margin: 0px;margin-top: 3px; max-width: 100%;">
			<tbody>
				<tr>
					<td title="${bizName}" style="width: 20%;border: none;text-align: center;"><label>${bizName}</label></td>
					<td style="width: 45%;border: none;text-align: center;">
						<label id="upTran">
							<input type="radio" style="width: auto;" name="proResultType" class="upTranLoad" value="approve" onchange="clyjOnClick()">
							<span >上报</span>
						</label>
						<label id="rejectBack">
							<input type="radio" style="width: auto;" name="proResultType" value="reject" onchange="clyjOnClick()" >
							<span >退回</span>
						</label>
					</td>
					<td style="width: 23%;border: none;text-align: center;">
						<input type="button" style="width: 60px; height: 22px;" value="流程历史" onclick="showProHis()">
					</td>
				</tr>
			</tbody>
		</table>
		<table id="nextTable" style="margin: 0px;max-width: 100%;border-bottom: 1px solid #95B8E7;">
			<tbody>
				<tr style="border-bottom: 1px solid #95B8E7;display: none;" id="nodeBackList">
					<th width="20%">
						<label>退回节点:</label>
					</th>
					<td width="75%">
						<select id="nodeBackSelect" style="width: 65%;margin-top: 3px;margin-bottom: 3px;" name="nodeBackId">
						</select>
					</td>
				</tr>
				<tr style="border-bottom: 1px solid #95B8E7;">
					<th width="20%">
						<label>审核意见:</label>
					</th>
					<td width="75%">
						<textarea id="proOpinion" class="required" name="proOpinion" maxlength="512" style="width:90%; height: 100px;margin-top: 3px;margin-bottom: 3px;"></textarea>
					</td>
				</tr>
				<tr id="transTr">
					<th width="20%">
						<label>上报给:</label>
					</th>
					<td width="75%" style="padding-bottom: 3px;">
						<input id="nextNodeId" name="currentNodeId" type="hidden" value="">
						<input id="nextNodeMark" name="currentNodeMark" type="hidden" value="">
						<div id="transDiv">
							<div style="margin: 3px 0px;">
								<label>目标节点：</label>
								<select id="nextNodeSelect" style="width: 65%;" onchange="nextNodeChange();">
								</select>
							</div>
							<label id="tranRole">目标任务：</label> <br>
							<select id="nextOrg" name="currentProOrgSysCode" style="width: 90%;" onchange="getApprovalUser();">
							</select><br>
							<select  id="nextUsers" name="currentProUserId" multiple="multiple" style="width: 90%;; height: 100px">
							</select><br>
							<input type="checkbox" id="isMobileRmd" style="width: auto;" name="isMobileRmd" value="1">发短信
							<input type="checkbox" id="isMailRmd" style="width: auto;" name="isMailRmd" value="1">发邮件
							<br>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="shBtn btn btn-primary" style="height: 30px;" type="button" value="确定" onclick="shButtionClick()">&nbsp;
			<input id="btnCancel" type="reset" class="shBtn btn" style="height: 30px;" value="重置">
		</div>
	</form>
</body>
</html>
