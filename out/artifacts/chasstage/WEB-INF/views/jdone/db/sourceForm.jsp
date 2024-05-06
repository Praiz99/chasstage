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
    <script type="text/javascript" src="${ctx}/static/jdone/js/db/sourceForm.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/dic/ZD_DB_TYPE.js"></script>
    <script type="text/javascript" src="${ctx}/static/dic/ZD_DB_CON_TYPE.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 90%;">
	<form id="sourceForm" name="sourceForm">
		<input id="id" name="id" type="hidden" value="${source.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty source.id?'修改数据':'添加数据'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">数据源名称：</label>
					</th>
					<td width="38%">
						<input name="sourceName" class="required" type="text" maxlength="50"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">数据源标识：</label>
					</th>
					<td width="38%">
						<input name="sourceMark" class="required" type="text" maxlength="30"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">数据源描述：</label>
					</th>
					<td colspan="3">
						<textarea name="sourceDesc" class="required" type="text" style="height: 60px;width: 80%;" maxlength="200"></textarea>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">连接类型：</label>
					</th>
					<td width="38%">
						<input type="text" class="dic" dicName="ZD_DB_CON_TYPE" id="conTypeDic" resultWidth="170" hiddenField="conType"/>
          				<input type="hidden" name="conType" id="conType" valueChangeHandle="conTypeChange()"/>
					</td>
					<th width="12%">
						<label class="control-label">数据库类型：</label>
					</th>
					<td width="38%">
						<input type="text" class="dic" dicName="ZD_DB_TYPE" id="dbTypeDic" resultWidth="170" hiddenField="dbType"/>
          				<input type="hidden" name="dbType" id="dbType"/>
					</td>
				</tr>
				<tr class="dbUserInfo">
					<th width="12%">
						<label class="control-label">连接信息：</label>
					</th>
					<td colspan="3">
						<textarea name="conInfo" class="required" type="text" style="height: 60px;width: 80%;" maxlength="500"></textarea>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>							
				</tr>
				<tr class="jndiInfo" style="display: none;">
					<th width="12%" class="must">
						<span style="padding-right:4px">*</span>JNDI名称：
					</th>
					<td colspan="3">
						<input class="required" type="text" maxlength="500"/>
					</td>							
				</tr>
				<tr>
					<th width="12%" class="must" style="display: none;">
						<span style="padding-right:4px">*</span>数据库名称：
					</th>
					<td width="38%"  style="display: none;">
						<input name="dbName" id="dbName" class="required" type="text" maxlength="50"/>
					</td>
					<th width="12%">
						<label class="control-label">是否禁用：</label>
					</th>
					<td colspan="3">
						<input name="isEnable" type="radio" value="1" checked="checked"/>
						<span class="text">是</span>
						<input name="isEnable" type="radio" value="0">
						<span class="text">否</span>
					</td>							
				</tr>					
				<tr class="dbUserInfo">
					<th width="12%">
						<label class="control-label">数据库用户名：</label>
					</th>
					<td width="38%">
						<input name="dbUserName" type="text" maxlength="50"/>
					</td>
					<th width="12%">
						<label class="control-label">数据库密码：</label>
					</th>
					<td width="38%">
						<input name="dbUserPswd" type="text" maxlength="100"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveSource()">&nbsp;
			<input class="btn" type="button" onclick="testCon()" value="测试连接">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/db/source/sourceList'">
		</div>			
	</form>
</body>
</html>
