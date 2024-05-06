<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
     <script type="text/javascript" src="${ctx}/static/jdone/js/db/resTableAddForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="resTableForm" name="resTableForm" action="save" method="post">
		<input id="id" name="id" type="hidden" value="${resTable.id}">
		<div class="form-panel">
			<div class="panel-top">
				<div class="tbar-title">
					<span>添加数据表</span>	
				</div>
			</div>
			<div class="panel-body">
				<table class="table_form">
					<tbody>
						<tr>
							<th width="12%">
								数据源：
							</th>
							<td width="38%">
								<select name="sourceId" id="dbSourceList" class="required">
								</select>
							</td>
							<th width="12%">
								是否视图：
							</th>
							<td width="38%">
								<select name="isView">
									<option value="0">是</option>
									<option value="1" selected="selected">否</option>
								</select>
							</td>
						</tr>
						<tr>
							<th width="12%" class="must">
								<span style="padding-right:4px">*</span>表英文名：
							</th>
							<td width="38%">
								<input name="tableMark" class="required" type="text" maxlength="200"/>
							</td>
							<th width="12%" class="must">
								<span style="padding-right:4px">*</span>表中文名：
							</th>
							<td width="38%">
								<input name="tableName" class="required" type="text" maxlength="100"/>
							</td>
						</tr>
						<tr>
							<th width="12%">
								所属分组：
							</th>
							<td colspan="3">
								<select name="groupId" id="groupList">
								</select>
							</td>
						</tr>
						<tr>
							<th width="12%">
								描述：
							</th>
							<td colspan="3">
								<textarea name="sourceDesc" type="text" style="height: 50px;" maxlength="50"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</form>
	<div class="form-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span>添加字段</span>	
			</div>
		</div>
	</div>
	<div style="margin-left: 5px;margin-top: 2px;margin-bottom: 10px;">
		<table id="fieldGrid"></table>
	</div>
	<div class="form-panel">
		<div class="panel-body">
			<div class="form-btns">
				<input id="btnSubmit" class="button" type="submit" value="保 存">&nbsp;
				<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/db/resTable/resTableList'">
			</div>	
		</div>	
	</div>		
</body>
</html>
