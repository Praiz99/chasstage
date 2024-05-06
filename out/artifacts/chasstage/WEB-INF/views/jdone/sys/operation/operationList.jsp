<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>外部接口管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/operationgroup/operationList.js"></script>
<script type="text/javascript"src="${ctx}/static/jdone/js/common/selector/selector.socpe.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div id="content" class="row-fluid">
	
	<div class="searchDiv">
	           <input type="hidden" id="resType" value="${resType}"/>
				<form id="searchForm">
				 <input type="hidden" id="groupLevel" name="groupLevel" value="${groupLevel}"/>
				  <input type="hidden" id="pid" name="pid" value="${pid}"/>
					<table id="table_form">
                        <tr class="searchTr">
							<th>&nbsp;&nbsp;名称：</th>
                            <td>
                                <input name="name" class="easyui-textbox"/>
							</td>
							<th>&nbsp;&nbsp;标识：</th>
                            <td>
                                <input name="mark" class="easyui-textbox"/>
							</td>
                            <td>
                            	&nbsp;&nbsp;<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
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
</body>
</html>
