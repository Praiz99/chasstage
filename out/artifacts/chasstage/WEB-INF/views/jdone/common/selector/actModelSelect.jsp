<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>流程模型选择器</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/common/selector/actModelSelector.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="overflow: auto;">
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group" style="width: 17%; height: 90%;float: left;">
			<div class="accordion-heading">
		    	<a class="accordion-toggle" style="color:#2fa4e7;text-decoration:none">模型分类<i class="easyui-linkbutton" id="refreshFlog" data-options="plain:true,iconCls:'icon-reload'" style="width: 25px;height: 18px;vertical-align: baseline;float: right;" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree" style="overflow:auto;margin:0;_margin-top:10px;padding:10px 0 10px 10px;height: 90%;"></div>
		</div>
		<div id="right" style="height: 100%; width: 80%;float: left;">
			<div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>模型名称：</th>
                            <td>
                                <input name="modelName" class="easyui-textbox keydownSearch"/>
							</td>
                            <th>模型标识：</th>
                            <td>
                                <input name="modelMark" class="easyui-textbox keydownSearch"/>
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
</body>
</html>
