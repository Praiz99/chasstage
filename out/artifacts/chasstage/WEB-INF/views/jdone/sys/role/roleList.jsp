<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<script type="text/javascript"src="${ctx}/static/jdone/js/common/selector/selector.socpe.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/role/roleList.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<input type="hidden" id="resType" value="${resType}"/>
	<div style="width:98%;height: 37px;">
		<div id="toptoolbar" style="min-height: 0px;"></div>
	</div> 
	<div id="content" class="row-fluid" style="height: 95%;">
		<div id="left" class="accordion-group" style="width: 17%; height: 100%;float: left;">
			<div class="accordion-heading">
		    	<a class="accordion-toggle" style="color:#2fa4e7;text-decoration:none">角色分类<i class="easyui-linkbutton" id="refreshFlog" data-options="plain:true,iconCls:'icon-reload'" style="width: 25px;height: 18px;vertical-align: baseline;float: right;" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree" style="overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;height: 90%;"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="center" style="height: 100%; width: 80%;float: left;border: 1px solid #e5e5e5">
			<iframe style="width: 100%;height: 100%;" frameborder="0" src="${ctx}/sys/role/roleForm"></iframe>
		</div>
		<div id="right" style="height: 100%; width: 80%;float: left;">
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
</body>
</html>
