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
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/user/updateUserPassWord.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="userForm" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${sysuser.id}">
		<br/>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">姓名:</label>
					</th>
					<td width="38%">
						<span style="font-size: 15px">
							${sysuser.name}
						</span>
					</td>
					<th width="12%">
						<label class="control-label">登录名:</label>
					</th>
					<td width="38%">
						<span style="font-size: 15px">
							${sysuser.loginId}
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">登录密码:</label>
					</th>
					<td width="38%">
						<input id="password" class="required" name="loginPsd" value="${sysuser.loginPsd}" type="password"  maxlength="50">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">确认密码:</label>
					</th>
					<td width="38%">
						<input name="rePassword" class="required" type="password" value="${sysuser.loginPsd}" id="repassword" maxlength="50" equalto="#password">
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
						<span style="font-size: 15px">
							<c:if test="${sysuser.sex==1}">男</c:if>
							<c:if test="${sysuser.sex==2}">女</c:if>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">出生日期:</label>
					</th>
					<td width="38%">
						<fmt:formatDate value="${sysuser.csrq}" pattern="yyyy-MM-dd" var="cssj"/>
						<span style="font-size: 15px">
							${cssj}
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">身份证号码:</label>
					</th>
					<td width="38%">
						<span style="font-size: 15px">
							${sysuser.idCard}
						</span>
					</td>
					<th width="12%">
						<label class="control-label">手机号码:</label>
					</th>
					<td width="38%">
						<span style="font-size: 15px">
							${sysuser.phone}
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">所属机构:</label>
					</th>
					<td width="38%">
						<span style="font-size: 15px">
							${sysuser.orgName}
						</span>
					</td>
					<th width="12%">
						<label class="control-label">所属机构系统编号:</label>
					</th>
					<td width="38%">
						<span style="font-size: 15px">
							${sysuser.orgSysCode}
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">用户类型:</label>
					</th>
					<td width="38%">
						<span style="font-size: 15px">
							<c:if test="${sysuser.userType==1}">正式民警 </c:if>
							<c:if test="${sysuser.userType==2}">现役军人 </c:if>
							<c:if test="${sysuser.userType==3}">协辅警</c:if>
							<c:if test="${sysuser.userType==4}">文职 </c:if>
							<c:if test="${sysuser.userType==5}">行政职工 </c:if>
							<c:if test="${sysuser.userType==6}">其他 </c:if>
							<c:if test="${sysuser.userType==7}">事业职工</c:if>
							<c:if test="${sysuser.userType==8}">开发者</c:if>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">办公电话:</label>
					</th>
					<td width="38%">
						<input name="officeTel" value="${sysuser.officeTel}" type="text">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">邮箱地址:</label>
					</th>
					<td colspan="3">
						<input name="email"  value="${sysuser.email}" class="input-xlarge email" type="text"  maxlength="50">
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveUser()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="关 闭 " onclick="closeDialog()">
		</div>
	</form>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:264px;; height: 300px;"></ul>
	</div>
</body>
</html>
