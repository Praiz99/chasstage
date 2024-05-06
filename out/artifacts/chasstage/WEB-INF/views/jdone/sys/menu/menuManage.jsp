<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/menu/menuCommonForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/menu/menuManage.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="overflow: hidden;">
	<div style="width:99%;height: 35px;margin-left: 5px;margin-top: 3px;">
		<div id="toptoolbar"></div>
	</div>
	<div id="content" class="row-fluid" style="height: 90%">
		<div id="left" class="accordion-group" style="width: 20%; height: 99%;float: left;margin-left: 5px;border: 1px solid #BED5F3;overflow: auto;">
			<div class="panel-header" style="height: 20px;border-color: white;border-bottom-color: #BED5F3;">
		    	<a class="accordion-toggle" style="font-weight: bold;">菜单树<i class="easyui-linkbutton" id="refreshFlog" data-options="plain:true,iconCls:'icon-reload'" style="width: 25px;height: 18px;vertical-align: baseline;float: right;" onclick="refreshTree();"></i></a>
		    </div>
		    <table border="0" class="table-detail"  style="margin: 1px;width: 99%;">
				<tbody>
					<tr style="border: 1px solid #7babcf;">
						<th style="width: 35%; height: 25px; border: 1px solid #7babcf; color: #6F8DC6; background-color: #ebf5ff; font-weight: bold;">所属应用:</th>
						<td style="font-family: sans-serif;">
							<select style="height: 23px;margin-left: 2px;" id="apps" onchange="refreshTree();">
								<c:forEach var="app" items="${apps}">
									<option value="${app.id}">${app.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr style="border: 1px solid #7babcf;">
						<th style="width: 30%; height: 25px; border: 1px solid #7babcf; color: #6F8DC6; background-color: #ebf5ff; font-weight: bold;">菜单名称:</th>
						<td style="border: 1px solid #7babcf;font-family: sans-serif;">
							<input id="menuName" name="menuName" type="text" class="inputText" style="width: 75%;margin-left: 2px;height: 19px;">
							<a href="#" onclick="menuTreeSearch();" class="icon-search" title="搜索菜单">&nbsp;&nbsp;&nbsp;&nbsp;</a> 
						</td>
					</tr>
				</tbody>
			</table>
			<div id="ztree" class="ztree" style="overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;height: auto;"></div>
		</div>
		<div id="center" style="height: 99%; width: 78%;float: left;border: 1px solid #BED5F3;margin-left: 5px;">
			<div class="panel-header" style="height: 20px;border-color: white;border-bottom-color: #BED5F3;font-weight: bold;">菜单管理</div>
			<iframe id="operFrame" width="100%" height="94%" frameborder="0" src=""></iframe>
		</div>
	</div>
</body>
</html>