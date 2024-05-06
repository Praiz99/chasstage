<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>外部接口管理</title>
    <%@ include file="/WEB-INF/views/framework/include/default.jsp" %>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
    <script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/assignList.js?v=21"></script>
    <style type="text/css">
        .subfild {
            height: 26px;
            border-bottom: 1px solid #dbdbdb;
            margin-bottom: 20px;
            padding: 0px 0px;
        }

        .subfild span {
            display: block;
            margin-left: 1px;
            width: 75px;
            text-align: center;
            height: 24px;
            line-height: 24px;
            border-bottom: 3px solid #0e8bc5;
            font-size: 16px;
        }
    </style>
</head>
<body>

<div id="main">
    <h2 class="subfild" >
        <span style="width: 350px;text-align: left;" >对资源【${resName}】进行授权</span>
    </h2>
</div>
<input  type="hidden" value="${resType}" id="resType" />
<div id="content" class="row-fluid">
    <div class="searchDiv">
        <form id="searchForm" autocomplete="off">
            <input type="hidden" id="resId" name="resId" value="${resId}"/>
            <table id="table_form">
                <tr class="searchTr">
                    <th>&nbsp;&nbsp;终端名称：</th>
                    <td>
                        <input type="text" id="clientIdDic" class="dic required"  dicName="JDONE_OAUTH_CLIENT" multi='false' dynamic="true" hiddenField='clientId' showDirection='bottom' searchField='name,sname,code'/>
                        <input type="hidden" name='clientId'  id="clientId" />
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
    </div>
    <table id="datagrid"></table>
</div>
<div id="additional" style="display: none" >
</body>
</html>
