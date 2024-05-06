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
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/role/roleForm.js"></script>
    <script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_JSJB.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<div id="tableInfo" class="easyui-tabs" style="width:100%;height:100%;">
		<div title="${not empty sysrole.id?'修改角色':'添加角色'}">
			<form id="roleForm" class="form-horizontal" >
				<input id="id" name="id" type="hidden" value="${sysrole.id}">
				<input id="opType" name="opType" type="hidden" value="${opType}">
				<input id="currentIsSuper" type="hidden" value="${currentIsSuper}">
				<div id="main">
					<h2 class="subfild">
			        	<span>${not empty sysrole.id?'修改角色':'添加角色'}</span>
			        </h2>
		        </div>
				<table class="table" style="width:98%">
					<tbody>
						<tr>
							<th width="12%">
								<label class="control-label">角色名称:</label>
							</th>
							<td width="38%">
								<input name="name" class="required" type="text" maxlength="30">
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
							<th width="12%">
								<label class="control-label">角色等级:</label>
							</th>
							<td width="38%">
								<input type="text" id="levelDic" class="dic required" dicName="ZD_SYS_JSJB" onchange="levelChange();" hiddenField="roleLev"></input>
								<input type="hidden" name="level" id="roleLev"/>
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<th width="12%">
								<label class="control-label">角色编号:</label>
							</th>
							<td width="38%">
								<input name="code" class="required digits" type="text" maxlength="6" minlength="6">
								<span class="help-inline">
									<font color="red">*</font>
								</span>
								<label class="control-label" style="color: red;float: none;width: auto;">(共6位,前2位自动填充)</label>
							</td>
							<th width="12%">
								<label class="control-label">角色分类:</label>
							</th>
							<td width="38%">
								<select name="roleKind" id="roleCatList">
								</select>
							</td>
						</tr>
						<tr>
							<th width="12%" class="sfgly">
								<label class="control-label">是否管理员：</label>
							</th>
							<td width="38%" class="sfgly">
								<input id="isAd_1" name="isAdmin" type="radio" value="1" onclick="adminChange();">
								<span class="text">是</span>
								<input id="isAd_0" name="isAdmin" type="radio" value="0" checked="checked" onclick="adminChange();">
								<span class="text">否</span>
							</td>
							<th width="12%">
								<label class="control-label">是否可分配角色：</label>
							</th>
							<td width="38%">
								<input id="isEnRole_1" name="isEnableAllotRole" disabled="disabled" type="radio" value="1">
								<span class="text">是</span>
								<input id="isEnRole_0" name="isEnableAllotRole" disabled="disabled" type="radio" value="0" checked="checked">
								<span class="text">否</span>
							</td>
						</tr>
						<tr>
							<th width="12%" class="sfszsyfw">
								<label class="control-label">是否设置适用范围：</label>
							</th>
							<td  colspan="3" class="sfszsyfw">
								<input id="isEnRole_1" name="isUseRange" type="radio" value="1" onclick="rangeChange();">
								<span class="text">是</span>
								<input id="isEnRole_0" name="isUseRange" type="radio" value="0" checked="checked" onclick="rangeChange();">
								<span class="text">否</span>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveRole()">&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="goBack('${sysrole.id}');">
				</div>
			</form>
		</div>
		<c:if test="${not empty sysrole.id}">
			<div  title="分配用户" id="allotUser"  >
				<iframe width="100%" height="95%" frameborder="0" src="${ctx}/sys/role/userRoleForm?id=${sysrole.id}"></iframe>
			</div>
		</c:if>
	</div>
</body>
</html>
