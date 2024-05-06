<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<title>选择用户</title>
		<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
		<link title="index" rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/rmd/web.css"></link>
		<link rel="stylesheet" type="text/css"href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"/>
		<script type="text/javascript"src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
	    <script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	    <script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	    <script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
	    <script src="${ctx}/static/dic/JDONE_SYS_ORG.js" type="text/javascript"></script>
		<script type="text/javascript"src="${ctx}/static/jdone/js/common/selector/selector.userList.js?m=1.1.3"></script>
		<script type="text/javascript">
			var ctx = '${ctx}';
			var isSingle = ${param.isSingle};
			var idField = "${param.idField}";
			var otherField = "${param.otherField}";
			var ctx = '${ctx}';
		</script>

</head>
	<body>
	<div id="cc" class="easyui-layout" style="width:100%;height:90%;">
    <div data-options="region:'west',title:'查询条件'" style="width:20%;">


    <div id="aa" class="easyui-accordion" style="width:99%;height:99%;">
    <div title="按机构查找"data-options="animate:true">
		<div id="ztree" class="ztree" style="margin:0;margin-top:10px;padding:10px 0 0 10px;"></div>
    </div>

    <div title="按角色查找" id="userRole">
							<table border="0" width="99%" class="table-detail">
								<tr>
									<!-- nowrap属性表示禁止单元格中的文字自动换行 -->
									<td width="20%" nowrap="nowrap"><span class="label" style="width:30px;">系统:</span></td>
									<td width="80%">
										<select class="systemId" name="systemId" onchange="initMenuData(this);" style="width: 100%;">
											<c:forEach items="${appList}" var="app">
												<option value="${app.id}">${app.name}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>
			<div id="userZtree" class="ztree" style="margin:0;margin-top:10px;padding:10px 0 0 10px;"></div>
    </div>

</div>

	</div>

    <div id="content" data-options="region:'east',title:'用户列表',split:true" style="width:20%;">
			<table id="sysUserList" width="99%" class="table-grid" cellpadding="1"
				cellspacing="1">
				<tbody id="userList">
					<tr class="hidden"></tr>
				</tbody>
			</table>
		</div>
    <div data-options="region:'center',title:'全部用户'" style="padding:5px;background:#eee;">
    	<div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>姓名：</th>
                            <td>
                                <input name="name" class="easyui-textbox keydownSearch"/>
							</td>
                            <th>所属机构：</th>
                            <td>
                                <!-- <input name="orgCode" class="easyui-textbox keydownSearch"/> -->
                                <input type="text" id="orgDic" class="dic" dicName="JDONE_SYS_ORG" hiddenField='orgCode' style="width:200px;"/>
								<input type="hidden" name='orgCode' id='orgCode'/>

							</td>
                            <td>
                            	<a class="easyui-linkbutton" href="#" id="keydownSearch" data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
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
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search'" onclick="selectUser()">选择</a>
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="dellAll();">清空</a>
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-cancel'" onclick="parent.win.dialog('close');">取消</a>
			</div>
</body>
</html>
