<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="/WEB-INF/views/framework/include/default.jsp" %>
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/resourceTreeForm.js?v=202208102233032"></script>
    <style type="text/css">
        button, html input[type="button"], input[type="reset"], input[type="submit"] {
            cursor: pointer;
            -webkit-appearance: button
        }

        .btn {
            padding: 4px 12px;
            font-size: 14px;
            line-height: 20px;
            text-align: center;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05)
        }

        .btn-primary {
            color: #fff;
            text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
            background-color: #2fa4e7;
            background-repeat: repeat-x;
            border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
        }

        input {
            font-family: Helvetica, Georgia, Arial, sans-serif, 宋体;
            font-size: 13px;
            _font-size: 12px;
        }
    </style>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
</head>
<body>
<form id="searchForm" autocomplete="off">
    <input type="hidden" id="clientId" name="clientId" value="${param.clientId}"/>
    <table id="table_form">
        <tr class="searchTr">
            <th>&nbsp;&nbsp;资源名称：</th>
            <td>
                <input type="text" id="name" name="name" class="easyui-textbox keydownSearch"/>
            </td>
            <td>
                &nbsp;&nbsp;<a class="easyui-linkbutton" href="javascript:void(0)"
                               data-options="iconCls:'icon-search'"
                               onclick="searchFunc('');">查找</a>
            </td>
            <td>
                &nbsp;&nbsp;<a class="easyui-linkbutton" href="javascript:void(0)"
                               data-options="iconCls:'icon-clear'"
                               onclick="ClearQuery();">清空</a>
            </td>
        </tr>
    </table>
</form>
<table id="resourceTreegrid"></table>
<br><br>
<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
    <input id="btnSubmit" class="btn btn-primary" type="button" value="添 加" onclick="addClientAssign();">&nbsp;
    <input id="btnCancel" class="btn" type="button" value="返 回"
           onclick="window.location.href='${ctx}/res/oauth/client/clientAssignList?clientId=${param.clientId}'">
</div>
</body>
</html>
