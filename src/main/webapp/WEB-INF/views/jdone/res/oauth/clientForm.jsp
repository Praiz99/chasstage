<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="/WEB-INF/views/framework/include/default.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <link rel="stylesheet" type="text/css"
          href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript"
            src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/clientForm.js?v=2111"></script>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
    <style type="text/css">
        #clientSecret {
            outline: none;
            background: transparent;
            border: none;
            outline: medium;
        }

        #clientSecret :focus {
            outline: none;
            background-color: transparent;
        }
    </style>
</head>
<body style="overflow-y: hidden;">
<input id="viewFlag" type="hidden" value="${viewFlag}">
<form id="clientForm" class="form-horizontal" name="clientForm" autocomplete="off">
    <input id="id" name="id" type="hidden" value="${client.id}">
    <div id="main">
        <h2 class="subfild">
            <span id="titleSpan" style="width: 100px;">${not empty client.id?'修改客户端':'新增客户端'}</span>
        </h2>
    </div>
    <table class="table">
        <tbody>
        <tr>
            <th width="12%">
                <label class="control-label">标识:</label>
            </th>
            <td width="38%">
                <input name="clientId" class="required" value="${client.clientId}" type="text" maxlength="100"/>
                <span class="help-inline">
							<font color="red">*</font>
						</span>
            </td>
            <th width="12%">
                <label class="control-label">名称:</label>
            </th>
            <td width="38%">
                <input name="clientName" class="required" value="${client.clientName}" type="text" maxlength="200"/>
                <span class="help-inline">
							<font color="red">*</font>
						</span>
            </td>
        </tr>
        <tr>
            <th width="12%">
                <label class="control-label">秘钥:</label>
            </th>
            <td width="38%" colspan="3">
                <input name="clientSecret" id="clientSecret" style="border: 0" readonly class="required" type="text"
                       value="${client.clientSecret}" maxlength="1000">
                <span class="help-inline">
							<font color="red">*</font>
						</span>
                <a href="javascript:autoGenerateSecret();">生成秘钥</a>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveClient()">&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回"
               onclick="window.location.href='${ctx}/res/oauth/client/clientList'">
    </div>
</form>
</body>
</html>
