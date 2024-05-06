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
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/nodeProRuleForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
    <style type="text/css">
    .form-horizontal .control-label {width: 130px;}
    </style>
</head>
<body>
	<form id="actNodeForm" name="actNodeForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${actNode.id}">
		<input id="modelId" name="modelId" type="hidden" value="${actNode.modelId}">
		<br/>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">流程key:</label>
					</th>
					<td  width="38%">
						<span>
							${actNode.modelMark}
						</span>
					</td>
					<th width="12%">
						<label class="control-label">节点标识:</label>
					</th>
					<td  width="38%">
						<span>
							${actNode.nodeMark}
						</span>
					</td>							
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">获取处理机构方式:</label>
					</th>
					<td  width="38%">
          				<select name="getProOrgWay" class="valid" style="width:80%">
							<option></option>
							<option <c:if test="${actNodeProcessRule.getProOrgWay eq 'url'}">selected=selected</c:if> value="url">url</option>
							<option <c:if test="${actNodeProcessRule.getProOrgWay eq 'springbean'}">selected=selected</c:if> value="springbean">springbean</option>
						</select>
					</td>
					<th width="12%">
						<label class="control-label">获取处理机构方式标识:</label>
					</th>
					<td  width="38%">
						<input  style="width:80%" name="getProOrgWayMark" type="text"   value="${actNodeProcessRule.getProOrgWayMark}"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">获取处理用户方式:</label>
					</th>
					<td  width="38%">
						<select name="getProUserWay" class="valid"  style="width:80%">
							<option></option>
							<option <c:if test="${actNodeProcessRule.getProUserWay eq 'url'}">selected=selected</c:if> value="url">url</option>
							<option <c:if test="${actNodeProcessRule.getProUserWay eq 'springbean'}">selected=selected</c:if> value="springbean">springbean</option>
						</select>
					</td>
					<th width="12%">
						<label class="control-label">获取处理用户方式标识:</label>
					</th>
					<td  width="38%">
						<input  style="width:80%" name="getProUserWayMark" type="text" value="${actNodeProcessRule.getProUserWayMark}"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveActNode()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="关 闭" onclick="closeDialog()">
		</div>
	</form>
</body>
</html>
