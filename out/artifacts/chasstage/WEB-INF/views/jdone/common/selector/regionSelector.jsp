<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>选择区域</title>

		<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
		<link title="index" rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/rmd/web.css"></link>
		<script src="${ctx}/static/jdone/js/common/selector/selector.regionList.js?m=1.1.4" type="text/javascript"></script>
		<script type="text/javascript">
			var ctx = '${ctx}';
			var isSingle = ${param.isSingle};
			var idField = "${param.idField}";
			var otherField = "${param.otherField}";
		</script>
	</head>
	<body >

	<div style="height: 100%;width: 100%;" id="bigDiv">
	<div id="cc" class="easyui-layout" style="width:100%;height:89%;">
    <div data-options="region:'center', title:'全部区域'" style="padding:5px;background:#eee;">
    <div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>&nbsp;区域名称：</th>
                            <td>
                                <input name="name" class="easyui-textbox keydownSearch"/>
							</td>
                            <th>&nbsp;&nbsp;区域代码：</th>
                            <td>
                                <input name="dsCode" class="easyui-textbox keydownSearch"/>
							</td>
                            <td>
                            	&nbsp;&nbsp;<a class="easyui-linkbutton" href="#" id="keydownSearch" data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
                            </td>
                            <td>
                            	&nbsp;&nbsp;<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="ClearQuery();">清空</a>
                            </td>
                        </tr>
                    </table>
				</form>

			</div>
			<table id="datagrid"></table>
			</div>

			 <div id="content" data-options="region:'east',title:'选择列表',split:true" style="width:20%;">
			 <table cellpadding="1" cellspacing="1" class="table-grid">
					<tbody id="regList">
						<tr class="hidden" bordercolor="red"></tr>
					</tbody>
			</table>
			 </div>
    </div>
    <br/>
    		<div align="center"style="padding-left: 0px;margin-bottom: 8px;">
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search'" onclick="selectReg()">选择</a>
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="dellAll();">清空</a>
			 <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-cancel'" onclick="parent.win.dialog('close');">取消</a>
			</div>

	</div>

	<!--  ============================================    -->

	<div id="divSec" style="height: 100%;width: 100%; display: none;">
			<div id="cc" class="easyui-layout" style="width:100%;height:95%;">
   		    <div data-options="region:'center', title:'全部区域'" style="padding:5px;background:#eee;">
    		<div class="searchDiv">
				<form id="searchForms">
					<table>
                        <tr class="searchTr">
                            <th>&nbsp;区域名称：</th>
                            <td>
                                <input name="name" class="easyui-textbox keydownSearch" style="width: 120px;"/>
							</td>
                            <th>&nbsp;&nbsp;区域编码：</th>
                            <td>
                                <input name="dsCode" class="easyui-textbox keydownSearch" style="width: 120px;"/>
							</td>
                            <td>
                            	&nbsp;&nbsp;<a class="easyui-linkbutton" href="#" id=""keydownSearch data-options="iconCls:'icon-search'" onclick="searchForm('');">查找</a>
                            </td>
                            <td>
                            	&nbsp;&nbsp;<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="Clear();">清空</a>
                            </td>
                        </tr>
                    </table>
				</form>
			</div>
			<table id="datagridSec"></table>
			</div>
    </div>

	</div>

</body>

</html>
