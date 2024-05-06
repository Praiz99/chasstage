<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/framework/utils/json2.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/db/resTableRefForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
    <style type="text/css">
		input[type="checkbox"] {
			margin: 0;
		}
	</style>
</head>
<body style="height: 95%;">
	<div id="tableInfo" class="easyui-tabs" style="width:100%;height:100%;">
		<div title="引用数据表">
			<form id="resTableForm" name="resTableForm">
				<input id="id" name="id" type="hidden" value="${resTable.id}">
				<div id="main">
					<h2 class="subfild">
			        	<span style="width: 100px;">${not empty resTable.id?'修改数据表':'添加数据表'}</span>
			        </h2>
		        </div>
				<table class="table">
					<tbody>
						<tr>
							<th width="12%">
								<label class="control-label">数据源：</label>
							</th>
							<td width="38%">
								<select name="sourceId" id="dbSourceList" class="required" onchange="sourceChange();">
								</select>
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
							<th width="12%">
								<label class="control-label">是否视图：</label>
							</th>
							<td width="38%">
								<select name="isView">
									<option value="0">是</option>
									<option value="1" selected="selected">否</option>
								</select>
							</td>
						</tr>
						<tr>
							<th width="12%">
								<label class="control-label">表英文名：</label>
							</th>
							<td width="38%">
								<input name="tableMark" class="required" style="width: 45%" readonly="readonly" type="text" maxlength="200"/>
								<span class="help-inline">
									<font color="red">*</font>
								</span>
								<input id="btnCancel" class="btn" type="button" value="选择" onclick="showRefTables();">
							</td>
							<th width="12%">
								<label class="control-label">表中文名：</label>
							</th>
							<td width="38%">
								<input name="tableName" class="required" type="text" maxlength="100"/>
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<th width="12%">
								<label class="control-label">所属分组：</label>
							</th>
							<td colspan="3">
								<select name="groupId" id="groupList" class="required">
								</select>
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<th width="12%">
								<label class="control-label">描述：</label>
							</th>
							<td colspan="3">
								<textarea name="tableDesc" type="text" style="height: 60px;width: 80%;" maxlength="50"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<div style="width:98%; margin-left: 15px;margin-top: 2px;margin-bottom: 10px;">
				<table id="fieldGrid"></table>
			</div>
			<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveTable()">&nbsp;
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/db/resTable/resTableList'">
			</div>
			<div id="refTablesDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="引用表" style="width: 80%; height: 80%;">
				<iframe scrolling="auto" id='openRefTables' name="openRefTables
				" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
			</div>
		</div>
	</div>
</body>
</html>
