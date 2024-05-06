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
     <script type="text/javascript" src="${ctx}/static/jdone/js/db/resTableEditForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<div id="tableInfo" class="easyui-tabs" style="width:100%;height:100%;">
		<div title="修改数据表">
			<form id="resTableForm" name="resTableForm" style="margin-top: 5px;">
				<input id="id" name="id" type="hidden" value="${resTable.id}">
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
								<input name="tableMark" id="tableMark" class="required" style="width: 45%" readonly="readonly" type="text" maxlength="200"/>
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
				<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="updateTable()">&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/db/resTable/resTableList'">
				</div>			
			</form>
		</div>
		<div title="字段信息">
			<iframe width="100%" height="95%" frameborder="0" src="${ctx}/db/resField/resFieldList"></iframe>
		</div>
	</div>
</body>
</html>
