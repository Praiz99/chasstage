<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>接口授权信息</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/jdone/js/api/inner/authinfoList.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div id="content" class="row-fluid">
	
	<div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>&nbsp;接口标识：</th>
                            <td>
                                <input name="apiMark" class="easyui-textbox"/>
							</td>
							<th>&nbsp;&nbsp;授权码：</th>
                            <td>
                                <input name="snNo" class="easyui-textbox"/>
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
