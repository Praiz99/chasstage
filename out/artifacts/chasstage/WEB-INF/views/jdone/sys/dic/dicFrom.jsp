<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>字典管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/dic/dicIndex.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="margin: 0px;">
	<div style="width: 99%; height: 37px; margin-left: 5px;margin-top: 3px;">
		<div id="toptoolbar"></div>
		<input type="hidden" name="enableEdit" id="enableEdit" value="${enableEdit}">
	</div>
	<div id="content" class="row-fluid">
		<div title="字典管理" id="left" class="accordion-group" style="width: 17%; height: 90%;float: left;margin-left: 5px;border: 1px solid #BED5F3;">
			<div class="easyui-tree" style="width: auto; margin-top: 2px; margin-left: 4px;">
				<input id="searchInput" class="easyui-textbox" onkeydown="if(event.keyCode==13) searchData();">
				<span id="search" class="l-btn-icon icon-search" onclick="javascript:searchData();" title="搜索字典" style="cursor: pointer; margin-left: 2px; top: 55px;">&nbsp;</span>
				<span id="refresh" class="l-btn-icon icon-reload" onclick="javascript:refreshTree();" title="刷新" style="cursor: pointer; margin-left: 18px; top: 55px;">&nbsp;</span>
			</div>
			<ul id="tree1" class="easyui-tree"></ul>
		</div>
		<div id="center" style="height: 90%; width: 80%;float: left;border: 1px solid #BED5F3;margin-left: 5px;">
			<div id="tabs" class="easyui-tabs" fit="true" border="false">
				<iframe id="operFrame" frameborder="0" scrolling="yes" src=""
					style="width: 100%; height: 800px;"></iframe>
			</div>
		</div>
	</div>
</body>
</html>

