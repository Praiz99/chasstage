<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>任务管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/task/taskList.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group" style="width: 17%; height: 95%;float: left;">
			<div class="accordion-heading">
		    	<a class="accordion-toggle" style="color:#2fa4e7;text-decoration:none">任务管理<i class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" style="width: 25px;height: 18px;vertical-align: baseline;float: right;" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree" style="overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;height: 600px;"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="center" style="height: 95%; width: 80%;float: left;border: 1px solid #e5e5e5">
			<iframe width="100%" id="tableList" height="800px;" frameborder="0" ></iframe>
		</div>
	</div>
</body>
</html>
