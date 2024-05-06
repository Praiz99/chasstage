<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>配置地址</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/settingAddressList.js"></script>
 	<script type="text/javascript" src="${ctx}/static/dic/JDONE_SYS_ROLE_CODENAME.js"></script>
 	<script type="text/javascript">
 	var ctx ='${ctx}';
 </script>
</head>
<body>
	<form id="searchForm">
			<div class="form-panel">
				<div class="panel-body">
					<table id="table_form" class="table_form" style="margin-bottom: 8px;">
	                    <tr id="trs">
			    			<th style="width: 20%;">适用角色名称:</th>
				    		<td width="89%">
				    			<input type="text" id="applyRoleDic"  style="width: 150px;" initVal="">
					    		<input type="hidden" id="applyRoleCode" name="applyRoleCode">
			                 	<a id="btnCx" class="easyui-linkbutton"  data-options="iconCls:'icon-search'" onclick="searchFunc('');">查询</a>
			                 	<a id="btnCz" class="easyui-linkbutton"  data-options="iconCls:'icon-clear'" onclick="ClearQuery();">重置</a>
				    		</td>
	    				</tr>
	                    <tr>
				    		<td>
				    			<input type="hidden" id="applyRole" name="applyRoleName">
					    		<input type="hidden" id="appId" name="appId" value="${appId}">
					    		<input type="hidden" id="appLevel" name="appLevel" value="${appLevel}">
				    		</td>
	    				</tr>
	                 </table>
				</div>
			</div>
		</form>
	<table id="datagrid"></table>
</body>
</html>
