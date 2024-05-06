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
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneActNodeForm.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_DWLX.js"></script>
    <script type="text/javascript" src="${ctx}/static/dic/JDONE_SYS_ROLE_CODENAME.js"></script>
    <script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_DWJB.js"></script>
    <script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_ACT_BACKTYPE.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="actNodeForm" name="actNodeForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${actNode.id}">
		<input id="modelId" name="modelId" type="hidden" value="${actNode.modelId}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty actNode.id?'修改节点':'添加节点'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="10%">
						<label class="control-label">节点名称:</label>
					</th>
					<td width="40%">
						<input name="actNodeName" class="required" type="text" maxlength="50">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="10%">
						<label class="control-label">节点标识:</label>
					</th>
					<td width="40%">
						<input name="nodeMark" class="required" type="text" maxlength="50"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>							
				</tr>
				<tr>
					<th width="10%">
						<label class="control-label">处理角色:</label>
					</th>
					<td width="40%">
          				<input type="text" class="dic" name="roleCodeName" multi="true" dicName="JDONE_SYS_ROLE_CODENAME" id="roleCodeTypeDic" resultWidth="200" hiddenField="roleCodeType"/>
            			<input type="hidden" name="roleCode" id="roleCodeType"/>
					</td>
					<th width="10%">
						<label class="control-label">处理单位类型:</label>
					</th>
					<td width="40%">
						<input type="text" class="dic" name="proOrgTypeName" dicName="ZD_SYS_DWLX" multi="true" id="proOrgTypeDic" resultWidth="200" hiddenField="orgType"/>
          				<input type="hidden" name="proOrgType" id="orgType"/>
					</td>
				</tr>
				<tr>
					<th width="10%">
						<label class="control-label">处理单位范围:</label>
					</th>
					<td width="40%">
						<select name="proOrgRange" class="valid">
							<option value="00" selected="selected">任意</option>
							<option value="01">本单位</option>
							<option value="02">本区域内</option>
							<option value="03">本市内</option>
							<option value="04">本市以及公安厅内</option>
						</select>
					</td>
					<th width="10%">
						<label class="control-label">处理单位级别:</label>
					</th>
					<td width="40%">
						<input type="text" class="dic" name="proOrgLevelName" multi="true" dicName="ZD_SYS_DWJB" id="proOrgLevelTypeDic" resultWidth="200" hiddenField="proOrgLevelType"/>
            			<input type="hidden" name="proOrgLevel" id="proOrgLevelType"/>
					</td>
				</tr>
				<tr>
					<th width="10%">
						<label class="control-label">节点类型:</label>
					</th>
					<td width="40%">
						<label><input name="isVreifyNode" type="checkbox" value="1" onclick="proTypeChange();"/>审核节点</label>
						<label><input name="isApprovalNode" type="checkbox" value="1" onclick="proTypeChange();"/>审批节点</label>
					</td>
					<th width="10%">
						<label class="control-label">节点退回类型:</label>
					</th>
					<td width="40%">
						<input type="text" class="dic" initVal='01' name="nodeBackTypeName" dicName="ZD_SYS_ACT_BACKTYPE" id="nodeBackTypeDic" resultWidth="200" hiddenField="nodeBackTypeId"/>
            			<input type="hidden" name="nodeBackType" id="nodeBackTypeId"/>
					</td>
				</tr>
				<tr>
					<th width="10%">
						<label class="control-label">默认节点类型:</label>
					</th>
					<td width="40%">
						<select name="defaultProType" id="defaultProTypeList" class="valid">
							<option value="00" selected="selected"></option>
						</select>
					</td>
					<th width="10%">
						<label class="control-label">排序:</label>
					</th>
					<td width="40%">
						<input name="orderId" class="required" type="text" value="0" maxlength="50"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="10%">
						<label class="control-label">传入节点:</label>
					</th>
					<td width="40%" id="transNodeList">
					</td>
					<th width="10%">
						<label class="control-label">是否禁用:</label>
					</th>
					<td width="40%">
						<input name="isDisable" type="radio" value="1"/>
						<span class="text">是</span>
						<input name="isDisable" type="radio" value="0" checked="checked"/>
						<span class="text">否</span>
					</td>
				</tr>
				<tr id="enableTran">
					<th width="10%">
						<label class="control-label">节点流转:</label>
					</th>
					<td colspan="3">
						<input name="enableTranSelf" type="radio" value="1"/>
						<span class="text">是</span>
						<input name="enableTranSelf" type="radio" value="0"/>
						<span class="text">否</span>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveActNode()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/act/actNode/actNodeList'">
		</div>
	</form>
</body>
</html>
