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
    <script type="text/javascript">
        $(function () {
            $("#src").html(parent.src);
            if ($("#id").val() == "") {  //新增时才给定最大排序值
                $("#orderId").val(parent.desort);
            }
            $("#groupId").val(parent.pid);
        });
    </script>
</head>
<body>
<!-- 菜单操作 开始 -->
<form id="mainForm" class="form-horizontal" method="post" autocomplete="off">
    <!-- 隐藏域   开始-->
    <input type="hidden" name="id" id="id" value="${resource.id}"/>
    <input id="isGroup" name="isGroup" type="hidden" value="1"/>
    <input type="hidden" name="groupId" id="groupId" value=""/> <!-- 父菜单ID判断是否添加子菜单或父菜单 -->
    <div id="main">
        <h2 class="subfild">
            <span>${not empty resource.id?'修改分组':'添加分组'}</span>
        </h2>
    </div>
    <!-- 隐藏域  结束 -->
    <table class="table" cellpadding="0" cellspacing="0" width="99%">
        <tr>
            <th width="12%"><label class="control-label">分组名称:</label></th>
            <td>
                <input type="text" name="name" class="required" value="${resource.name}"/>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
            <th width="12%"><label class="control-label">排序ID:</label></th>
            <td>
                <input id="orderId" name="orderId" type="number" class="required"
                       value="${resource.orderId}"/>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
        </tr>
        <tr>
            <th width="12%"><label class="control-label">分组标识:</label></th>
            <td colspan="3">
                <span>${pmark}</span>
                <input id="pmark" name="pmark" type="hidden" value="${pmark}"/>
                <input id="mark" name="mark" class="required" type="text" value="${cmark}"/>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
        </tr>
        <tr>
            <th style="width:12%;"><label class="control-label">备注:</label></th>
            <td colspan="3" style="height:80px;">
                <textarea style="width:98%;height: 60px;" id="remark" name="remark">${resource.remark}</textarea>
            </td>
        </tr>
    </table>
</form>
</body>
</html>