<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title>选择角色</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link title="index" rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/rmd/web.css"></link>

<script type="text/javascript"src="${ctx}/static/jdone/js/common/selector/selector.roleList.js?m=1.1.2"></script>
<link rel="stylesheet" type="text/css"href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/>
<script type="text/javascript"src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>

<script type="text/javascript" >
var ctx = "${ctx}";
var isSingle = ${param.isSingle};
var idField = "${param.idField}";
var otherField = "${param.otherField}";
</script>

</head>
	<body>
	<div id="cc" class="easyui-layout" style="width:100%;height:89%;">
    <div data-options="region:'west',title:'应用系统'" style="width:20%;">
    <div class="accordion-heading">
		 <a class="accordion-toggle" style="color:#2fa4e7;text-decoration:none">角色分类<i class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" style="width: 25px;height: 18px;vertical-align: baseline;" onclick="refreshTree();"></i></a>
	 </div>
			<div id="ztree" class="ztree" style="margin:0;margin-top:10px;padding:10px 0 0 10px;"></div>
	</div>

    <div id="content"data-options="region:'east',title:'角色列表',split:true" style="width:20%;">
			<table id="sysRoleList" width="99%" class="table-grid" cellpadding="1"cellspacing="1">
				<tbody id="roleList">
					<tr class="hidden"></tr>
				</tbody>
			</table>
		</div>
    <div data-options="region:'center',title:'全部角色'" style="padding:5px;background:#eee;">
    	<div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>角色名称：</th>
                            <td>
                                <input name="name" class="easyui-textbox keydownSearch"/>
							</td>
                            <th>角色编号：</th>
                            <td>
                                <input name="code" class="easyui-textbox keydownSearch"/>
							</td>
                           <td>
                            	<a class="easyui-linkbutton" href="#" id="keydownSearch" data-options="iconCls:'icon-search'" onclick="searchFunc('id', '');">查找</a>
                            </td>
                            <td>
                            	<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="ClearQuery();">清空</a>
                            </td>
                        </tr>
                    </table>
				</form>
			</div>
    	<table id="datagrid"></table>
    </div>
	 </div>
	<br/>
    		<div align="center"style="padding-left: 0px;margin-bottom: 5px;">
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search'" onclick="selectRole()">选择</a>
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="dellAll();">清空</a>
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-cancel'" onclick="parent.win.dialog('close');">取消</a>
			</div>
</body>
</html>
