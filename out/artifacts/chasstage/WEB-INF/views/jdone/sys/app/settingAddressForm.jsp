<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>配置首页</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/settingAddressForm.js"></script>
 	<script type="text/javascript" src="${ctx}/static/dic/JDONE_SYS_ROLE_CODENAME.js"></script>
 	<script type="text/javascript">var ctx="${ctx}"</script>
</head>
<body>
	<form id="searchForm">
		<div class="form-panel">
			<div class="panel-top">
				<div class="tbar-title">
					<span>${not empty hpSet.id?'修改配置首页':'添加配置首页'}</span>
				</div>
			</div>
			<div class="panel-body">
				<table id="table_form" class="table_form" style="margin-bottom: 8px;">
					<tr>
						<th width="15%">首页地址:</th>
						<td><input id="indexUrl" name="indexUrl"class="commom-txt required" /></td>
						<th width="15%">适用角色名称:</th>
						<td>
						<input type="text" id="applyRoleDic"  style="width: 150px;" initVal="">
			    		<input type="hidden" id="applyRole" name="applyRoleName">
						<input type="hidden" id="applyRoleCode" name="applyRoleCode">
						</td>
					</tr>
					<tr>
						<th width="15%">首页名称:</th>
						<td>
						<input class="commom-txt required" id="indexName"name="indexName" />
						</td>
						<th width="15%">是否禁用:</th>
						<td>
							<input name="isDisable" type="radio" value="1" /> 
						<span class="text">是</span>
							<input name="isDisable" type="radio" value="0" checked="checked" />
						<span class="text">否</span>
						 </td>
					<tr>
						<th width="15%">排序:</th>
						<td>
						<input name="orderId" id="orderId" class="commom-txt required" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();" placeholder ="请输入数字"/></td>
						<th width="15%">描述:</th>
						<td>
						<input name="description" id="description"class="commom-txt" /> 
						<input type="hidden" name="id" id='id' value="${hpSet.id}" />
						<input type="hidden" name="appId" id='appId' value="${hpSet.appId}"/>
					</tr>
				</table>
				<div class="form-btns">
					<input id="btnSubmit" class="button" type="button" value="保 存" onclick="savehpSet()">&nbsp;
					<input id="btnCancel"class="button" type="button" value="返 回" onclick="window.history.go(-1);">
				</div>
			</div>
		</div>
	</form>
</body>
</html>
