<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
     <script type="text/javascript" src="${ctx}/static/jdone/js/db/resScriptEditForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<div id="scriptInfo" class="easyui-tabs" style="width:100%;height:800px;">
		<div title="修改查询脚本">
			<form id="resScriptForm" name="resScriptForm">
				<input id="id" name="id" type="hidden" value="${resScript.id}">
				<div class="form-panel">
					<div class="panel-body">
						<table class="table_form">
							<tbody>
								<tr>
									<th width="12%" class="must">
										<span style="padding-right:4px">*</span>脚本名称：
									</th>
									<td width="38%">
										<input name="scriptName" class="required" type="text" maxlength="50"/>
									</td>
									<th width="12%" class="must">
										<span style="padding-right:4px">*</span>脚本标识：
									</th>
									<td width="38%">
										<input name="scriptMark" class="required" type="text" maxlength="50"/>
									</td>
								</tr>
								<tr>
									<th width="12%" class="must">
										<span style="padding-right:4px">*</span>数据源：
									</th>
									<td width="38%">
										<select name="sourceId" id="dbSourceList" class="required" onchange="sourceChange();">
										</select>
									</td>
									<th width="12%" class="must">
										<span style="padding-right:4px">*</span>所属分组：
									</th>
									<td width="38%">
										<select name="groupId" id="groupList" class="required">
										</select>
									</td>
								</tr>
								<tr>
									<th width="12%">
										查询脚本：
									</th>
									<td colspan="3">
										<textarea name="scriptContent" type="text" style="height: 50px;"></textarea>
									</td>
								</tr>
								<tr>
									<th width="12%">
										描述：
									</th>
									<td colspan="3">
										<textarea name="scriptDesc" type="text" style="height: 50px;" maxlength="200"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="form-btns">
							<input id="btnSubmit" class="button" type="button" value="保 存" onclick="updateScript()">&nbsp;
							<input class="button" type="button" onclick="testScript()" value="脚本测试">&nbsp;
							<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/db/resScript/resScriptList'">
						</div>				
					</div>
				</div>
			</form>
		</div>
		<div title="字段信息">
			<iframe width="100%" height="95%" frameborder="0" src="${ctx}/db/resField/resFieldList"></iframe>
		</div>
	</div>
</body>
</html>
