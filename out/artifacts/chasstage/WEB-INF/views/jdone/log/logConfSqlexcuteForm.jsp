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
    <script type="text/javascript" src="${ctx}/static/jdone/js/log/logConfSqlexcuteForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="logConfForm" name="logConfForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${logConf.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty logConf.id?'修改配置':'添加配置'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">应用名称:</label>
					</th>
					<td width="38%">
						<input name="appName" type="text" maxlength="50"/>
					</td>
					<th width="12%">
						<label class="control-label">应用标识:</label>
					</th>
					<td width="38%">
						<input name="appMark" type="text" maxlength="32"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">模块名称:</label>
					</th>
					<td width="38%">
						<input name="moduleName" type="text" maxlength="50"/>
					</td>
					<th width="12%">
						<label class="control-label">模块标识:</label>
					</th>
					<td width="38%">
						<input name="moduleMark" type="text" maxlength="30"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">sql语句标识:</label>
					</th>
					<td width="38%">
						<input name="sqlMark" type="text" maxlength="100"/>
					</td>
					<th width="12%">
						<label class="control-label">sql类型:</label>
					</th>
					<td width="38%">
						<select name="sqlType" class="required">
							<option value="" selected="selected"></option>
							<option value="SELECT" >SELECT</option>
							<option value="UPDATE" >UPDATE</option>
							<option value="INSERT" >INSERT</option>
							<option value="DELETE" >DELETE</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">日志记录最小耗时:</label>
					</th>
					<td width="38%">
						<input name="minCost" type="text"/>
					</td>
					<th width="12%">
						<label class="control-label">是否过滤:</label>
					</th>
					<td width="38%">
						<input name="isFilter"  type="radio" value="1" /><label>是</label>
						<input name="isFilter"  type="radio" value="0"/><label>否</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">sql功能描述:</label>
					</th>
					<td colspan="3">
						<textarea name="sqlFuncDesc" style="width:85%;height: 60px;" maxlength="200"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveLogConf()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/log/conf/sqlexcute/logConfList'">
		</div>
	</form>
</body>
</html>
