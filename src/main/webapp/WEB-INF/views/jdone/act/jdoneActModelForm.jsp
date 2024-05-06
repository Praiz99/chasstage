<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneActModelForm.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/dic/JDONE_ACT_GROUP.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 98%;">
	<div id="tableInfo" class="easyui-tabs" style="width:100%;height:100%;">
		<div title="${not empty actModel.id?'修改模型':'添加模型'}">
			<form id="actModelForm" class="form-horizontal">
				<input id="id" name="id" type="hidden" value="${actModel.id}">
				<input id="opType" name="opType" type="hidden" value="${actModel.opType}">
				<div id="main">
					<h2 class="subfild">
			        	<span>${not empty actModel.id?'修改模型':'添加模型'}</span>
			        </h2>
		        </div>
				<table class="table">
					<tbody>
						<tr>
							<th width="12%">
								<label class="control-label">模型名称:</label>
							</th>
							<td width="38%">
								<input name="modelName" id="modelName" class="required" type="text" maxlength="30">
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
							<th width="12%">
								<label class="control-label">模型标识:</label>
							</th>
							<td width="38%">
								<input name="modelMark" id="modelMark" class="required" type="text" maxlength="30">
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>							
						</tr>
						<tr>
							<th width="12%">
								<label class="control-label">流程分类:</label>
							</th>
							<td width="38%">
            					<input type="text" id="groupMarkDic" class="dic required" dicName="JDONE_ACT_GROUP" resultWidth="200" hiddenField="conType"></input>
								<input type="hidden" name="groupMark" id="conType"/>
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
							<th width="12%">
								<label class="control-label">流程处理地址:</label>
							</th>
							<td width="38%">
								<input name="dealUrl" id="dealUrl" class="required" type="text" maxlength="50">
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<th width="12%">
								<label class="control-label">模型描述:</label>
							</th>
							<td colspan="3">
								<textarea id="modelDesc" name="modelDesc" maxlength="200" style="width:95%;height: 60px;"></textarea>
							</td>
						</tr>	
					</tbody>
				</table>
				<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveActModel()">&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/act/model/list'">
				</div>
			</form>
		</div>
		<div title="节点信息" id="nodeInfoFlog">
			<iframe width="100%" height="95%" frameborder="0" src="${ctx}/act/actNode/actNodeList"></iframe>
		</div>
	</div>
</body>
</html>
