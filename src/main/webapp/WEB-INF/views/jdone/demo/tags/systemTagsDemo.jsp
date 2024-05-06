<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<%@ taglib prefix="system" uri="/tags/jdone/sys/system"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>系统标签demo</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
</head>

<body style="overflow-y: hidden" scroll="no">
	<br>
	获取参数名为test的值：<system:param mark="uploadType"/> 
	<br>
	<system:oper mark="testOper">我的测试操作</system:oper>
</body>

</html>
