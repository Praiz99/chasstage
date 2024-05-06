<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/user/userForm.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/org/initOrgList.js"></script>
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="userForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${sysuser.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty sysuser.id?'修改用户':'添加用户'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">姓名:</label>
					</th>
					<td width="38%">
						<input name="name" class="required" type="text" maxlength="30">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">登录名:</label>
					</th>
					<td width="38%">
						<input name="loginId" class="required" type="text" maxlength="30">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">登录密码:</label>
					</th>
					<td width="38%">
						<input id="password" class="required" name="loginPsd" type="password"  maxlength="50">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">确认密码:</label>
					</th>
					<td width="38%">
						<input name="rePassword" class="required" type="password" id="repassword" maxlength="50" equalto="#password">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">性别:</label>
					</th>
					<td width="38%">
						<select name="sex">
							<option value="1" selected="selected">男</option>
							<option value="2">女</option>
						</select>
					</td>
					<th width="12%">
						<label class="control-label">出生日期:</label>
					</th>
					<td width="38%">
						<input name="csrq" id="csrq" class="easyui-datebox" editable="false" type="text" >
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">身份证号码:</label>
					</th>
					<td width="38%">
						<input name="idCard" class="required input-xlarge card" type="text"  maxlength="30">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">手机号码:</label>
					</th>
					<td width="38%">
						<input name="phone" type="text"  maxlength="15">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">所属机构:</label>
					</th>
					<td width="38%">
						<div class="input-append">
							<input id="orgSel" name="orgName" type="text" class="input-xlarge required">
							<input name="orgCode" id="pid" type="text" style="display: none;" />
							<a href="javascript:" id="showOrg" class="btn" onclick="showMenu();" style="text-decoration: none;display: none;">
								<i class="icon-search"></i>&nbsp;
							</a>&nbsp;&nbsp;
						</div>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">所属机构系统编号:</label>
					</th>
					<td width="38%">
						<input name="orgSysCode" id="sysCode" class="required" type="text" maxlength="30" readonly="readonly">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">用户类型:</label>
					</th>
					<td width="38%">
						<select name="userType">
							<option value="1" selected="selected">正式民警</option>
							<option value="2">现役军人</option>
							<option value="3">协辅警</option>
							<option value="4">文职</option>
							<option value="5">行政职工</option>
							<option value="6">其他</option>
							<option value="7">事业职工</option>
							<option value="8">开发者</option>
						</select>
					</td>
					<th width="12%">
						<label class="control-label">办公电话:</label>
					</th>
					<td width="38%">
						<input name="officeTel" type="text">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">邮箱地址:</label>
					</th>
					<td colspan="3">
						<input name="email" class="input-xlarge email" type="text"  maxlength="50">
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveUser()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/sys/user/userList'">
		</div>
	</form>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:264px;; height: 300px;"></ul>
	</div>
</body>
</html>
