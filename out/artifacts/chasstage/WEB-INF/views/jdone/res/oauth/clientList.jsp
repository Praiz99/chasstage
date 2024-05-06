<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>客户端管理</title>
    <%@ include file="/WEB-INF/views/framework/include/default.jsp" %>
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/clientList.js?v=08021182"></script>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
</head>
<body style="overflow-y: hidden">
<div id="content" class="row-fluid">
    <div class="searchDiv">
        <form id="searchForm" autocomplete="off">
            <table>
                <tr class="searchTr">
                    <th>&nbsp;终端标识：</th>
                    <td>
                        <input name="clientId" class="easyui-textbox keydownSearch"/>
                    </td>
                    <th>&nbsp;&nbsp;终端名称：</th>
                    <td>
                        <input name="clientName" class="easyui-textbox keydownSearch"/>
                    </td>
                    <td>
                        &nbsp;&nbsp;<a class="easyui-linkbutton" id="keydownSearch" href="#"
                                       data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
                    </td>
                    <td>
                        &nbsp;&nbsp;<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'"
                                       onclick="ClearQuery();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <table id="datagrid"></table>
</div>
<div id="additional" style="display: none"></div>
<div id="resourceView" style="display: none"></div>
</body>
</html>
