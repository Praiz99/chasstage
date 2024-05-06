<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<%@ taglib prefix="system" uri="/tags/jdone/sys/system" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>大中心测试连接</title>
</head>
<body style="background-color: white">
<div style="width: 400px;height: 88px;background-color: white;position: absolute;top: 0;bottom: 0;left: 0;right: 0;margin: auto;">

    <h1 style="<c:if test="${access}">color:#0170BA;</c:if><c:if test="${!access}">color:red;</c:if>text-align: center;">${message}</h1>
</div>

</body>
</html>
