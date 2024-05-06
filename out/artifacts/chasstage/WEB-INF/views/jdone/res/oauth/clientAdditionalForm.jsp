<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/views/framework/include/default.jsp" %>
    <link rel="stylesheet" type="text/css"
          href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript"
            src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
    <script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        var ctx = "${ctx}";
    </script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/clientAdditionalForm.js?s=2222p21"></script>
</head>
<body>
<form id="mainForm" class="form-horizontal" method="post" autocomplete="off">
    <table class="table" cellpadding="0" cellspacing="0" style="width: 95% !important;">
        <tr>
            <th width="10%"><label class="control-label">申请单位:</label></th>
            <td colspan="3">
                <input type="text" id="sqdwDic" class="dic" dicName="JDONE_SYS_ORG" multi='false' dynamic="true" pageSize="6"
                   initVal="${additionalInformation.sqdwOrgcode}"  hiddenField='sqdwOrgcode' showDirection='bottom' searchField='name,sname,code'/>
                <input type="hidden" name='sqdwOrgcode' value="${additionalInformation.sqdwOrgcode}" id="sqdwOrgcode"/>
            </td>
        </tr>
        <tr>
            <th width="10%"><label class="control-label">用户令牌类型:</label>
            </th>
            <td colspan="3">
                <select id="userTokenType" name="userTokenType" style="width: 206px" class="required">
                    <option  <c:if test="${additionalInformation.userTokenType eq ''}"> selected </c:if>  value=""></option>
                    <option  <c:if test="${additionalInformation.userTokenType eq 'user-login-id_card'}"> selected </c:if>   value="user-login-id_card">身份证生成令牌</option>
                    <option  <c:if test="${additionalInformation.userTokenType eq 'user-login-login_id'}"> selected </c:if>  value="user-login-login_id">警号生成令牌</option>
                    <option  <c:if test="${additionalInformation.userTokenType eq 'user-login-id'}"> selected </c:if>  value="user-login-id">用户ID生成令牌</option>
                </select>
            </td>
        </tr>
        <tr>
            <th width="10%"><label class="control-label">启用IP访问限制:</label></th>
            <td colspan="3">
                <input name="isRestrictIp" type="radio"  <c:if test="${additionalInformation.isRestrictIp eq 0 or  additionalInformation.isRestrictIp eq null }"> checked </c:if> value="0" id="isRestrictIp-no"/>
                <label for="isRestrictIp-no">否</label>
                <input name="isRestrictIp"  type="radio"  <c:if test="${additionalInformation.isRestrictIp eq 1}"> checked </c:if> value="1" id="isRestrictIp-yes"/>
                <label for="isRestrictIp-yes">是</label>
            </td>
        </tr>
        <tr id="ipListTr">
            <th width="10%"><label class="control-label">允许访问IP列表:</label></th>
            <td colspan="3">
                <input name="ipList" value="${additionalInformation.ipList}"  placeholder="输入服务器ip,多个逗号隔开..." type="text" id="ipList"/>
            </td>
        </tr>
        <tr id="extParamsTr">
            <th width="10%"><label class="control-label">扩展参数:</label></th>
            <td colspan="3">
                <textarea id="extParams" style="height: 160px;width: 240px;">${additionalInformationExtParams}</textarea>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
