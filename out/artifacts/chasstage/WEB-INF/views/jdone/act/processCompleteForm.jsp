<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<%@ taglib prefix="bpmn" uri="/tags/jdone/act/actApprove"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/act/css/processCompleteForm.css">
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/processCompleteForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
    <bpmn:actApprove taskId="${taskId}" instId="${instId}"></bpmn:actApprove>
</body>
</html>
