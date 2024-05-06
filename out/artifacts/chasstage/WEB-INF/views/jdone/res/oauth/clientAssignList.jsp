<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>终端授权</title>
    <%@ include file="/WEB-INF/views/framework/include/default.jsp" %>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
    <link rel="stylesheet" type="text/css"
          href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript"
            src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
    <script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/clientAssignList.js?v=0002"></script>
    <style type="text/css">
        table {
            max-width: 98%;
            background-color: transparent;
            border-collapse: collapse;
            border-spacing: 0;
            margin: 0px 0px;
        }

        .table {
            width: 100%;
            margin-bottom: 20px;
            margin: 0px 15px;
        }

        .table th, .table td {
            padding: 4px;
            line-height: 20px;
            text-align: left;
            vertical-align: top;
            border: 1px solid #95B8E7;
            border-top: 1px solid #95B8E7
        }

        .table th {
            font-weight: bold
        }

        .form-horizontal .control-label {
            float: left;
            width: 160px;
            padding-top: 5px;
            text-align: right
        }
    </style>
</head>
<body>
<div id="content" class="row-fluid">
    <div class="searchDiv">
        <form id="searchForm" autocomplete="off">
            <input type="hidden" id="clientId" name="clientId" value="${param.clientId}"/>
            <table id="table_form">
                <tr class="searchTr">
                    <th>&nbsp;&nbsp;资源名称：</th>
                    <td>
                        <input type="text" id="resName" name="resName" class="easyui-textbox keydownSearch"/>
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
<div id="additional" style="display: none"></div>
<div id="updateSqsxDiv" style="display:none;overflow: hidden">
    <form id="mainForm" autocomplete="off" class="form-horizontal" method="post">
        <table class="table" cellpadding="0" cellspacing="0" width="95%">
            <tr>
                <th width="30%"><label class="control-label">授权类型:</label>
                </th>
                <td colspan="3">
                    <input name="sqlx" type="radio" value="long" id="sqlx-long"/>
                    <label for="sqlx-long">永久</label>
                    <input name="sqlx" type="radio" value="temp" id="sqlx-temp"/>
                    <label for="sqlx-temp">固定期限</label>
                    <span class="help-inline">
							<font color="red">*</font>
					</span>
                </td>
            </tr>
            <tr id="sqsxTr">
                <th width="30%"><label class="control-label">授权期限:</label></th>
                <td colspan="3">
                    <input type="text" id="sqsxKssj" name="sqsxKssj" style="height: 25px;width: 150px;"
                           class="required  search-textbox keydownSearch Wdate"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'sqsxJzsj\')}'});"/>
                    -
                    <input type="text" id="sqsxJzsj" name="sqsxJzsj" class="required search-textbox keydownSearch Wdate" style="height: 25px;width: 150px;"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sqsxKssj\')}'});"/>
                    <span class="help-inline">
							<font color="red">*</font>
                </span>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
