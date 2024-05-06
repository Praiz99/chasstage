<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>微服务日志查询</title>
    <%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
    <script type="text/javascript" src="${ctx}/static/jdone/js/log/logApiRecordList.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
    <script type="text/javascript" src="${ctx}/static/jdone/js/common/ext/grid-search.js"></script>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
</head>
<body>
<div >
    <div class="row-fluid">
        <form id="searchForm">
            <table>
                <tr class="searchTr">
                    <td><label class="search-label">请求IP:</label><input name="reqIp" type="text" class="search-textbox keydownSearch" /></td>
                    <td><label class="search-label">请求类型:</label><input name="apiType" type="text" class="search-textbox keydownSearch" /></td>
                    <td><label class="search-label">接口标识:</label>
                        <input name="apiMark" type="text" class="search-textbox keydownSearch" />
                    </td>
                    <td><label class="search-label">请求时间:</label>
                        <input name="reqTimeStart" style="width: 100px;" id="reqTimeStart" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,maxDate:'#F{$dp.$D(\'reqTimeEnd\')}'});"/>
                        -
                        <input name="reqTimeEnd" style="width: 100px;" id="reqTimeEnd" type="text" class="search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'reqTimeStart\')}'});"/>
                    </td>
                    <td><label class="search-label">请求授权终端ID:</label>
                        <input name="reqAppClientId" type="text" class="search-textbox keydownSearch" />
                    </td>
                    <td><label class="search-label">响应应用标识:</label>
                        <input name="respAppMark" type="text" class="search-textbox keydownSearch" />
                    </td>
                    <td><label class="search-label">响应IP:</label>
                        <input name="respIp" type="text" class="search-textbox keydownSearch" />
                    </td>
                    <td><label class="search-label">响应码:</label>
                        <input name="respCode" type="text" class="search-textbox keydownSearch" />
                    </td>

                </tr>
            </table>
            <div class="form-actions">
                <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchFunc()">
                <input id="btnCancel" class="btn" type="button" value="重置" onclick="ClearQuery();">
            </div>
        </form>
    </div>
    <div class="extend-search">
    </div>
    <table id="datagrid"></table>
</div>

</body>
</html>
