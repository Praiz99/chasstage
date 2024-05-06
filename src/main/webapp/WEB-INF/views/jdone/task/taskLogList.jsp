<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>任务管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/task/taskLogList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
		<div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>任务名称：</th>
                            <td>
                                <input name="taskName" class="easyui-textbox" />
							</td>
                            <th>时间：</th>
                            <td>
                                <input  class="easyui-datetimebox" name=exeStartTime   style="width:150px">—
                                <input  class="easyui-datetimebox" name=exeCmpltTime   style="width:150px">
							</td>
                            <td>
                            	<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
                            </td>
                            <td>
                            	<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="ClearQuery();">清空</a>
                            </td>
                        </tr>
                    </table>
				</form>
			</div>
		<table id="dg"></table>
</body>
</html>
