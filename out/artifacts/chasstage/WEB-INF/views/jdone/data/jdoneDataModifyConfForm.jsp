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
    <script type="text/javascript" src="${ctx}/static/jdone/js/data/jdoneDataModifyConfForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="dataConfForm" name="dataConfForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${dataConf.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty dataConf.id?'修改配置':'添加配置'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">流程key:</label>
					</th>
					<td width="38%">
						<input name="actKey" type="text" maxlength="100"/>
					</td>
					<th width="12%">
						<label class="control-label">变更类型:</label>
					</th>
					<td width="38%">
						<select name="modifyType" >
							<option value=""  selected="selected"></option>
							<option value="UPDATE">UPDATE</option>
							<option value="DELETE">DELETE</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更业务类型:</label>
					</th>
					<td width="38%">
						<input name="modifyBizType" type="text" maxlength="100"/>
					</td>
					<th width="12%">
						<label class="control-label">变更业务类型名称:</label>
					</th>
					<td width="38%">
						<input name="modifyBizTypeName" type="text" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更业务表名:</label>
					</th>
					<td width="38%">
						<input name="modifyBizTable" type="text" maxlength="100"/>
					</td>
					<th width="12%">
						<label class="control-label">业务标识字段名:</label>
					</th>
					<td width="38%">
						<input name="bizMarkField" type="text" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更sqlId:</label>
					</th>
					<td colspan="3">
						<input name="sqlId" type="text" maxlength="200"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更sql片段:</label>
					</th>
					<td colspan="3">
						<textarea name="modifySqlPart" style="width:85%;height: 60px;" maxlength="500"></textarea>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">参数类型:</label>
					</th>
					<td width="38%">
						<input name="paramClass" type="text" maxlength="200"/>
					</td>
					<th width="12%">
						<label class="control-label">业务标识参数名称:</label>
					</th>
					<td width="38%">
						<input name="bizMarkParam" type="text" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更原参数名:</label>
					</th>
					<td width="38%">
						<input name="bvParamNames" type="text" maxlength="500"/>
					</td>
					<th width="12%">
						<label class="control-label">变更后参数名:</label>
					</th>
					<td width="38%">
						<input name="avParamNames" type="text" maxlength="500"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">审核审批处理地址:</label>
					</th>
					<td colspan="3">
						<input name="approveUrl" type="text" style="width:40%;" maxlength="500"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveDataConf()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/data/modifyConf/dataModifyConfList'">
		</div>
	</form>
</body>
</html>
