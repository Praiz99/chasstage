<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>自定义操作列表</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/utils/jQuery.Hz2Py-min.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/oper/operForm.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/oper/operview.js"></script>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<style type="text/css">
	html,body{
		height: 98%;
	}
</style>
</head>
<body>
	<div id="content" class="row-fluid">
		<div id="right" style="height: 100%; width: 100%;float: left;">
			<div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>参数名称:</th>
                            <td>
                                <input name="name" class="easyui-textbox keydownSearch"/>
							</td>
							<th>参数标识:</th>
                            <td>
                                <input name="mark" class="easyui-textbox keydownSearch"/>
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
	<div id="dialog"></div>
</body>
</html>