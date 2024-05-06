<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>自定义操作管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/oper/operList.js"></script>
</head>
<body>
	<div class="easyui-panel" style="padding:5px;background-color: #d6e7f7;">
		<a href="javaScript:addGroup();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">添加分组</a>
		<a href="javaScript:updateGroup();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">修改分组</a>
		<a href="javaScript:deleteGroup();" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除分组</a>
	</div>
	<div class="easyui-layout" id="layout" style="width:100%;height:92%;">
		<div id="p" data-options="region:'west'" title="操作分组" style="width:23%;">
		<table border="0" width="99%" style="margin: 1px;">
			<tbody>
				<tr style="border: 1px solid #7babcf;">
					<th style="width: 30%;  border: 1px solid #7babcf; color: #6F8DC6; background-color: #ebf5ff; font-weight: bold;">操作名称:</th>
					<td style="font-family: sans-serif;">
						<div><input class="easyui-searchbox" data-options="prompt:'请输入关键字',searcher:doSearch" ></input></div>
					</td>
				</tr>
			</tbody>
		</table>
			<ul id="ztree" class="ztree"></ul>
		</div>
		<div data-options="region:'center'" title="操作列表" style="padding: 5px;">
			<!-- 自定义操作列表 -->
			<!-- <div id="datagrid"></div> -->
			<iframe id="mainForm" name="mainForm" src="" style="width: 99%;height: 99%;" frameborder="no" border="0"></iframe>
		</div>
	</div>
</body>
</html>
