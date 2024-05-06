<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>接口管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/operationgroup/operationgroupList.js"></script>
</head>
<body style="height:98%" >
	<!-- 菜单按钮  开始 -->
	<div class="easyui-panel" style="padding:5px;background-color: #d6e7f7;">
		<a href="javaScript:saveMenuSettings();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		<a href="javaScript:openMenuSettings();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加分组</a>
		<a href="javaScript:UpdateTree();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">修改分组</a>
		<a href="javaScript:deleteMenuBytreeId();" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
	</div>
	<!-- 菜单按钮 结束 -->
	
	<!-- 菜单树  开始 -->
	<div class="easyui-layout" id="layout" style="width:100%;height:100%;">
		<div id="p" data-options="region:'west'" title="操作管理" style="width:25%;">
		 <table border="0" width="99%" class="table-detail"  style="margin: 1px;">
				<tbody>
					<tr style="border: 1px solid #7babcf;">
						<th style="width: 25%; border: 1px solid #7babcf; height: 30px; color: #6F8DC6; background-color: #ebf5ff; font-weight: bold;text-align:right;">名称:&nbsp;</th>
						<td style="border: 1px solid #7babcf;font-family: sans-serif;padding-left:5px;">
							<input id="menuName" name="menuName" type="text" class="inputText" style="width:60%;height: 25px;">
							<a  href="javascript:doSearch();" class="icon-search" title="搜索名称">&nbsp;&nbsp;&nbsp;&nbsp;</a> 
						</td>
					</tr>
				</tbody>
			</table>
			<ul id="menuTree" class="ztree"></ul>
		</div>
		<div data-options="region:'center'" title="操作配置">
			<iframe id="mainForm" name="mainForm" src="" style="width: 99%;height: 90%;" frameborder="0" scrolling="no" border="0"></iframe> 
		</div>
	</div>
	<!-- 菜单树 结束 -->
</body>
</html>