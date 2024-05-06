<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/datagrid-groupview.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/skim/skimthroughForm.css"/>
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/permission/permissionList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="margin: 4px;height: 99%;">
	<input type="hidden" id="roleLevel" value="${roleLevel}"/>
	<div id="content" class="row-fluid" style="height: 100%;">
		<div id="left" class="accordion-group" style="width: 60%; height: 100%;float: left;border: 1px solid #BED5F3;">
			<div class="panel-header" style="height: 18px;border-color: white;border-bottom-color: #BED5F3;font-weight: bold;">授权对象</div>
			<div id="tt" class="easyui-tabs" style="width:100%;height:94%;padding: 3px;">   
				<div title="角色" >
					<table id="datagrid"></table>
				</div>
			</div>
		</div>
		<div id="center" style="height: 100%; width: 39%;float: left;border: 1px solid #BED5F3;margin-left: 3px;">
			<div class="panel-header" style="height: 18px;border-color: white;border-bottom-color: #BED5F3;">
				<a class="easyui-linkbutton" style="margin-top: -4px;" data-options="iconCls:'icon-save'" onclick="savePermissionList();">保存</a>
			</div>
			<div id="perTabs" class="easyui-tabs" style="width:100%;height:94%;padding: 3px;">   
				<div title="菜单/功能">
					<div id="menuZtree" class="ztree" style="overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;height: 95%;"></div>
				</div>
				<div title="自定义操作" >
					<div id="customZtree" class="ztree" style="overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;height: 95%;"></div>
				</div>
				<div title="浏览权限" >
					<div style="float: left;height: 95%;width: 50%;">
						<div style="width: 100%;display: none;" id="rank"></div>
					</div>
					<div style="float: right;height: 95%;width: 50%;">
						<div id="viewZtree" class="ztree" style="overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;height: 98%;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>