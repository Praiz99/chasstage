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
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneActNodeRuleForm.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/dic/JDONE_ACT_NODE_RULETYPE.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
    <style type="text/css">
    .form-horizontal .control-label {width: 120px;}
    </style>
</head>
<body style="height: 95%;">
	<form id="nodeRuleForm" name="nodeRuleForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${nodeRule.id}">
		<input id="nodeMark" name="nodeMark" type="hidden" value="${nodeRule.nodeMark}">
		<input id="modelId" name="modelId" type="hidden" value="${nodeRule.modelId}">
		<input id="nodeId" name="nodeId" type="hidden" value="${nodeId}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty nodeRule.id?'修改规则':'添加规则'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">规则名称:</label>
					</th>
					<td width="38%">
						<input name="ruleName" type="text" class="required" maxlength="50"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">规则标识:</label>
					</th>
					<td width="38%">
						<input name="ruleMark" class="required" type="text" maxlength="50"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>							
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">规则类型:</label>
					</th>
					<td width="38%">
          				<select name="ruleType" class="valid required">
							<option></option>
							<option value="groovyshell">groovyshell</option>
							<option value="springbean">springbean</option>
						</select>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">是否启用:</label>
					</th>
					<td width="38%">
						<input name="isEnable" type="radio" value="1" checked="checked"/>
						<span class="text">是</span>
						<input name="isEnable" type="radio" value="0"/>
						<span class="text">否</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">规则脚本:</label>
					</th>
					<td colspan="3">
						<textarea id="ruleScript" name="ruleScript" maxlength="500" style="width:95%;height: 60px;"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveActNodeRule()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/act/nodeRule/nodeRuleList?nodeMark=${nodeRule.nodeMark}&modelId=${nodeRule.modelId}&nodeId=${nodeId}'">
		</div>
	</form>
</body>
</html>
