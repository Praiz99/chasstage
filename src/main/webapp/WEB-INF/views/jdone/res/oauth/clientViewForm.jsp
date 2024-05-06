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
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/clientViewForm.js"></script>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
</head>
<body style="overflow-y: hidden;">
<input id="viewFlag" type="hidden" value="${viewFlag}">
<input id="autoapproveInput" name="autoapproveInput" type="hidden" value="${client.autoapprove}">
<input id="authorizedGrantTypesInput" name="authorizedGrantTypesInput" type="hidden"
       value="${client.authorizedGrantTypes}">
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
            <td width="38%">
                <input name="clientSecret" class="required" type="text" value="${client.clientSecret}" maxlength="1000">
                <span class="help-inline">
							<font color="red">*</font>
						</span>
            </td>
            <th width="12%">
                <label class="control-label">域:</label>
            </th>
            <td width="38%">
                <input name="scope" class="required" type="text" title="${client.scope}" value="${client.scope}" maxlength="500">
                <span class="help-inline">
							<font color="red">*</font>
                </span>
            </td>
        </tr>
        <tr>
            <th width="12%">
                <label class="control-label">自动放行:</label>
            </th>
            <td width="38%">
                &nbsp;
                <input id="autoapprove_false" class="required" name="autoapprove" type="radio" value="false"/><label
                    for="autoapprove_false">否</label>
                &nbsp;
                <input id="autoapprove_true" class="required" name="autoapprove" type="radio" value="true"/><label
                    for="autoapprove_true">是</label>
                <span class="help-inline">
							<font color="red">*</font>
                </span>
            </td>
            <th width="12%">
                <label class="control-label">实例ID:</label>
            </th>
            <td width="38%">
                <input name="resourceIds" title="${client.resourceIds}"  value="${client.resourceIds}" type="text" maxlength="500">
            </td>
        </tr>
        <tr>
            <th width="12%">
                <label class="control-label">授权模式:</label>
            </th>
            <td width="88%" colspan="3">
                <input name="grantTypes" type="checkbox" class="required" value="password" id="password"/>
                <label for="password">密码模式</label>&nbsp;
                <input name="grantTypes" type="checkbox" class="required" value="authorization_code"
                       id="authorization_code"/>
                <label for="authorization_code">授权模式</label>&nbsp;
                <input name="grantTypes" type="checkbox" class="required" value="client_credentials"
                       id="client_credentials"/>
                <label for="client_credentials">客户端模式</label>&nbsp;
                <input name="grantTypes" type="checkbox" class="required" value="refresh_token" id="refresh_token"/>
                <label for="refresh_token">刷新模式</label>&nbsp;
                <input name="grantTypes" type="checkbox" class="required" value="implicit" id="implicit"/>
                <label for="implicit">简化模式</label>
                <span class="help-inline">
							<font color="red">*</font>
                </span>
            </td>
        </tr>
        <tr>
            <th width="12%">
                <label class="control-label">令牌时效:</label>
            </th>
            <td width="38%">
                <input name="accessTokenValidity" value="${client.accessTokenValidity}" type="number" maxlength="32"/>
            </td>
            <th width="12%">
                <label class="control-label">刷新时效:</label>
            </th>
            <td width="38%">
                <input name="refreshTokenValidity" value="${client.refreshTokenValidity}" type="number" maxlength="32"/>
            </td>
        </tr>
        <tr>
            <th width="12%">
                <label class="control-label">回调地址:</label>
            </th>
            <td width="38%">
                <input name="webServerRedirectUri" value="${client.webServerRedirectUri}" type="text" maxlength="500"/>
            </td>
            <th width="12%">
                <label class="control-label">权限:</label>
            </th>
            <td width="38%">
                <input name="authorities"  title="${client.authorities}" value="${client.authorities}" style="width: 100%" type="text"
                       maxlength="1000"/>
            </td>
        </tr>
        <tr>
            <th width="12%">
                <label class="control-label">扩展参数:</label>
            </th>
            <td width="88%" colspan="3">
                <textarea name="additionalInformation" id="additionalInformation"
                          style="width: 100%;height: 60px;">${client.additionalInformation}</textarea>
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
