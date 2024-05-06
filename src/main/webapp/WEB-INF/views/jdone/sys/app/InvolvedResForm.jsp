<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>关联资源</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/InvolvedResForm.js"></script>
</head>
<body>
	<div class="easyui-tabs" style="height: 99%;">
		 <div title="菜单">
			 <ul id="mztree" class="ztree" style="display: block;"></ul>
		 </div>
		 <div title="操作">
		 	<ul id="cztree" class="ztree" style="display: block;"></ul> 
		 </div>
	</div>
</body>
</html>
