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
    <script type="text/javascript" src="${ctx}/static/jdone/js/res/oauth/assignForm.js?s=222222121"></script>
</head>
<body>
<input  type="hidden" id="clientMark" value="${param.clientMark}"  />
<input type="hidden" id="sqlx" value="${assign.sqlx}"/>
<form id="mainForm" class="form-horizontal" method="post" autocomplete="off">
    <input type="hidden" name="resId" id="resId" value="${resource.id}"/>
    <input type="hidden" name="id" id="id" value="${assign.id}"/>
    <div id="main">
        <h2 class="subfild">
            <span>${not empty assign.id?'修改授权':'添加授权'}</span>
        </h2>
    </div>
    <table class="table" cellpadding="0" cellspacing="0" width="99%">
        <tr>
            <th width="10%"><label class="control-label">授权终端:</label></th>
            <td>
                <input type="text" id="clientIdDic" class="dic required" initValue="${assign.clientId}" dicName="JDONE_OAUTH_CLIENT" multi='false' dynamic="true" hiddenField='clientId' showDirection='bottom' searchField='name,sname,code'/>
                <input type="hidden" name='clientId'  id="clientId" value="${assign.clientId}"/>

                <span class="help-inline">
							<font color="red">*</font>
                </span>
            </td>
            <th width="10%"><label class="control-label">授权类型:</label>

            </th>
            <td>
                <input name="sqlx" type="radio" value="long" <c:if test="${assign.sqlx eq 'long'}"> checked </c:if>
                       id="sqlx-long"/>
                <label for="sqlx-long">永久</label>
                <input name="sqlx"  type="radio" value="temp" id="sqlx-temp" <c:if
                        test="${assign.sqlx eq 'temp'}"> checked </c:if> />
                <label for="sqlx-temp">固定期限</label>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
        </tr>
        <tr id="sqsxTr">
            <th width="10%"><label class="control-label">授权期限:</label></th>
            <td colspan="3">
                <input type="text" id="sqsxKssj" name="sqsxKssj" class="required search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'sqsxJzsj\')}'});"   value="<fmt:formatDate value='${assign.sqsxKssj}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                -
                <input type="text" id="sqsxJzsj" name="sqsxJzsj"  class="required search-textbox keydownSearch Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sqsxKssj\')}'});"   value="<fmt:formatDate value='${assign.sqsxJzsj}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                <span class="help-inline">
							<font color="red">*</font>
                </span>
            </td>
        </tr>
    </table>
    <div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveAssign()">&nbsp;
        <c:if test="${param.clientMark ne 'client'}">
            <input id="btnCancel" class="btn" type="button" value="返 回"
                   onclick="gobackforType('${resource.id}') ">
        </c:if>
        <c:if test="${param.clientMark eq 'client'}">
            <input id="btnCancel" class="btn" type="button" value="返 回"
                   onclick="gobackforType('${assign.clientId}') ">
        </c:if>
    </div>
</form>
</body>
</html>