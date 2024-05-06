<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/permission/permissionList.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/skim/skimthroughForm.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css" />
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/jquery.json-2.2.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/skim/skimthroughForm.js"></script>
</head>
<body>
	<div class="easyui-panel" style="padding:5px;background-color: #d6e7f7;">
		<a href="javaScript:Submit();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存权限</a>
	</div>
	<div class="easyui-layout" style="width:100%;height:93%;">
		<div data-options="region:'west',split:true" title="角色" style="width:40%;">
			<div id="roletabs" style="padding:1px;"></div>
		</div>
		<div data-options="region:'center',title:'功能模块'">
			<ul id="mztree" class="ztree"></ul>
		</div>
		<div data-options="region:'east',split:true" title="浏览等级" style="width:25%;">
			<div style="width: 100%;" id="rank"></div>
		</div>
	</div>
</body>
</html>
