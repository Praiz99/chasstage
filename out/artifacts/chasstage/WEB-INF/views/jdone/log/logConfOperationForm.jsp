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
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/log/logConfOperationForm.js"></script>
    <script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_RZCZLX.js"></script>
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
						<label class="control-label">操作类型:</label>
					</th>
					<td width="38%">
						<select name="operTypr">
							<option value="0" selected>登录</option>
							<option value="1" selected>查询</option>
							<option value="2">新增</option>
							<option value="3">修改</option>
							<option value="4">删除</option>
							<option value="5" selected>登出</option>
							<option value="6" selected>导出</option>
							<option value="99" selected>常规访问</option>
						</select>
					</td>
					<th width="12%">
						<label class="control-label">页面/操作路径:</label>
					</th>
					<td width="38%">
						<input name="servletPath" class="required" type="text" maxlength="200"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">页面/操作名称:</label>
					</th>
					<td width="38%">
						<input name="operName" type="text" maxlength="50"/>
					</td>
					<th width="12%">
						<label class="control-label">页面/操作标识:</label>
					</th>
					<td width="38%">
						<input name="pageOperMark" type="text" maxlength="30"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">请求类型:</label>
					</th>
					<td width="38%">
						<select name="reqType" class="required">
							<option value="normal" selected="selected">常规请求</option>
							<option value="ajax">AJAX请求</option>
						</select>
					</td>
					<th width="12%">
						<label class="control-label">业务分类:</label>
					</th>
					<td width="38%">
						<input name="bizLogBigType" type="text" maxlength="30"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">业务具体分类:</label>
					</th>
					<td width="38%">
						<input name="bizLogLitType" type="text" maxlength="50"/>
					</td>
					<th width="12%">
						<label class="control-label">业务标识:</label>
					</th>
					<td width="38%">
						<input name="bizMarkVar" type="text" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">业务模板Bean:</label>
					</th>
					<td colspan="3">
						<input name="templateVarsBean" type="text" style="width:40%;" maxlength="200"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">数据敏感等级:</label>
					</th>
					<td colspan="3">
						<select name="dataLevel" style="width: 240px;">
							<option value="1" selected>普通业务操作信息（一般）</option>
							<option value="2">涉及公民个人信息（重要）</option>
							<option value="3">涉及个人隐私信息（敏感）</option>
							<option value="4">涉及侦查手段等高敏信息（特殊）</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">业务描述模板:</label>
					</th>
					<td colspan="3">
						<textarea name="operDescTemplate" style="width:85%;height: 60px;" ></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveLogConf()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/log/conf/operation/logConfList'">
		</div>
	</form>
</body>
</html>
