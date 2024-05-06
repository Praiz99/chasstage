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
    <script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
    <script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript"
            src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <script type="text/javascript">
        $(function () {
            $("#src").html(parent.src);
            if ($("#id").val() == "") {  //新增时才给定最大排序值
                $("#orderId").val(parent.desort);
            }
            dicInit();
        });
    </script>
</head>
<body>
<!-- 菜单操作 开始 -->
<form id="mainForm" class="form-horizontal" method="post" autocomplete="off">
    <!-- 隐藏域   开始-->
    <input type="hidden" name="id" id="id" value="${resource.id}"/>
    <input id="isGroup" name="isGroup" type="hidden" value="0"/>
    <input type="hidden" name="groupId" id="groupId" value="${pid}"/> <!-- 父菜单ID判断是否添加子菜单或父菜单 -->
    <div id="main">
        <h2 class="subfild">
            <span>${not empty resource.id?'修改资源':'添加资源'}</span>
        </h2>
    </div>
    <!-- 隐藏域  结束 -->
    <table class="table" cellpadding="0" cellspacing="0" width="99%">
        <tr>
            <th width="10%"><label class="control-label">资源名称:</label></th>
            <td>
                <input type="text" name="name" class="required" value="${resource.name}"/>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
            <th width="10%"><label class="control-label">应用标识:</label></th>
            <td>

                <input type="text" id="appMarkDic" class="dic required" initValue="${resource.appMark}"
                       dicName="JDONE_CONF_APP" multi='false' dynamic="true" hiddenField='appMark' showDirection='bottom'
                       searchField='name,sname,code'/>
                <input type="hidden" name='appMark' id="appMark" value="${resource.appMark}"/>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
        </tr>
        <tr>
            <th width="10%"><label class="control-label">资源类型:</label></th>
            <td>
                <select id="resType" name="resType" style="width: 206px" class="required">
                    <option value="page" <c:if test="${resource.resType eq 'page'}"> selected </c:if> >功能页面</option>
                    <option value="data" <c:if test="${resource.resType eq 'data'}"> selected </c:if>>数据查询接口</option>
                    <option value="biz" <c:if test="${resource.resType eq 'biz'}"> selected </c:if>>业务处理接口</option>
                </select>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
            <th width="10%"><label class="control-label">是否需要传递用户信息:</label></th>
            <td>
                <select id="isNeedUserInfo" style="width: 206px" name="isNeedUserInfo" class="required" type="text">
                    <option value="1" <c:if test="${resource.isNeedUserInfo eq 1}"> selected </c:if> >是</option>
                    <option value="0" <c:if test="${resource.isNeedUserInfo eq 0}"> selected </c:if>>否</option>
                </select>

                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
        </tr>
        <tr>
            <th width="10%"><label class="control-label">排序ID:</label></th>
            <td colspan="3">
                <input id="orderId" name="orderId" type="number" class="required" value="${resource.orderId}"/>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
        </tr>
        <tr>
            <th width="10%"><label class="control-label">资源标识:</label></th>
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
            <th width="10%"><label class="control-label">资源地址:</label></th>
            <td colspan="3" style="height:80px;">
                <textarea style="width:95%;height: 60px;" class="required" id="resUri"
                          name="resUri">${resource.resUri}</textarea>
                <span class="help-inline">
							<font color="red">*</font>
					</span>
            </td>
        </tr>
        <tr>
            <th style="width:10%;"><label class="control-label">备注:</label></th>
            <td colspan="3" style="height:80px;">
                <textarea style="width:95%;height: 60px;" id="remark" name="remark">${resource.remark}</textarea>
            </td>
        </tr>
    </table>
</form>
</body>
</html>