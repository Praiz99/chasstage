<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/views/framework/include/default.jsp" %>
    <link rel="stylesheet" type="text/css"
          href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/menu/jq22.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript"
            src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/resourceView.js?v=0613"></script>
    <script type="text/javascript">
        var ctx = "${ctx}";
    </script>
</head>
<body>
<!-- 菜单操作 开始 -->
<form id="mainForm" class="form-horizontal" method="post" autocomplete="off">
    <table class="table" cellpadding="0" cellspacing="0" width="99%">
        <tr>
            <th width="10%"><label class="control-label">终端标识:</label></th>
            <td style="width: 200px;">
                <span>${client.clientId}</span>
            </td>
            <th width="10%"><label class="control-label">终端名称:</label></th>
            <td>
                <span>${client.clientName}</span>
            </td>
        </tr>
        <tr>
            <th width="10%"><label class="control-label">终端秘钥:</label></th>
            <td>
                <span>${client.clientSecret}</span>
            </td>
            <th width="10%"><label class="control-label">允许访问实例:</label></th>
            <td>
                <span title="${client.resourceIds}"
                      style='width:200px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;display: block'>${client.resourceIds}</span>
            </td>
        </tr>
        <tr>
            <th width="10%"><label class="control-label">允许访问的资源列表:</label></th>
            <td colspan="3" height="270px;">
                <div id="scopeListDiv" class="plus-tag tagbtn clearfix"
                     style="width: 560px;height: 240px;word-wrap:break-word;overflow: auto">
                    <c:forEach items="${assignList}" var="assgin">
                        <a href="javascript:;" id="${assgin.id}">
                            <span>${assgin.resMark}</span>
                            <em></em>
                        </a>
                    </c:forEach>
                </div>
            </td>
        </tr>
        <tr>
            <th width="10%"><label class="control-label">其他扩展配置:</label></th>
            <td colspan="3" height="110px;">
                <div style="width: 560px;height: 100px;word-wrap:break-word;overflow: auto">
                    <table class="table" cellpadding="0" cellspacing="0" style="width: 95% !important;">
                        <tr>
                            <th><label>申请单位:</label></th>
                            <td colspan="3">
                                <span title="${additionalMap.sqdwOrgcode}"
                                      style='width:400px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;display: block'>${additionalMap.sqdwOrgcode}</span>
                            </td>

                        </tr>
                        <tr>
                            <th><label>申请令牌类型:</label></th>
                            <td style="width: 150px;">
                                <c:if test="${additionalMap.userTokenType eq 'user-login-id_card'}">身份证生成令牌</c:if>
                                <c:if test="${additionalMap.userTokenType eq 'user-login-login_id'}">警号生成令牌</c:if>
                                <c:if test="${additionalMap.userTokenType eq 'user-login-id'}">用户ID生成令牌</c:if>
                            </td>
                            <th width="100px;"><label>是否启用IP限制:</label></th>
                            <td>
                                <c:if test="${additionalMap.isRestrictIp eq '1'}">是</c:if>
                                <c:if test="${additionalMap.isRestrictIp eq '0'}">否</c:if>
                            </td>
                        </tr>
                        <tr>
                            <th><label>限制IP的地址:</label></th>
                            <td colspan="3">
                                <span title="${additionalMap.ipList}"
                                      style='width:400px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;display: block'>${additionalMap.ipList}</span>
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</form>
<div id="assignInfoDiv" style="display: none">
    <table class="table" cellpadding="0" cellspacing="0" style="width: 95% !important;">
        <tr>
            <th style="width: 120px;text-align: right !important;" ><label>资源名称:</label></th>
            <td style="width: 150px;">
                <span id="info_resName"></span>
            </td>
            <th style="text-align: right !important;"><label>资源标识:</label></th>
            <td>
                <span id="info_resMark"></span>
            </td>
        </tr>
        <tr>
            <th style="width: 120px;text-align: right !important;"><label>资源地址:</label></th>
            <td colspan="3">
                <span id="info_resUri"></span>
            </td>
        </tr>
        <tr>
            <th style="width: 120px;text-align: right !important;"><label>资源类型:</label></th>
            <td style="width: 150px;">
                <span id="info_resType"></span>
            </td>
            <th width="100px;"><label>是否需要用户信息:</label></th>
            <td>
                <span id="info_isNeedUserInfo"></span>
            </td>
        </tr>
        <tr>
            <th style="width: 120px;text-align: right !important;"><label>申请时效:</label></th>
            <td colspan="3">
                <span id="info_sqsx"></span>
            </td>
        </tr>
        <tr>
            <th style="width: 120px;text-align: right !important;"><label>有效时间:</label></th>
            <td colspan="3">
                <span id="info_sqsx_ksjzsj"></span>
            </td>
        </tr>
        <tr>
            <th style="width: 120px;text-align: right !important;"><label>其他属性:</label></th>
            <td colspan="3">
                <span id="info_props" title=""
                      style='width:400px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;display: block'></span>
            </td>
        </tr>
    </table>
</div>
</body>
</html>