<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<script type="text/javascript" src="${ctx}/static/jdone/js/utils/jQuery.Hz2Py-min.js"></script>
<script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_DSDM.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/jquery.json-2.2.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/SelectRange.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div class="easyui-tabs" id="tabs" style="height: 100%;">
		<input type="hidden" id="resType" value="${resType}"/>
		<input type="hidden" id="resName" value="${resName}"/>
		<input type="hidden" id="resMark" value="${resMark}"/>
		<input type="hidden" id="showTypes" value="${showTypes}"/>
		<input type="hidden" id="city" value="${city}"/>
		<input type="hidden" id="regs" value="${regs}"/>
		<input type="hidden" id="orgs" value="${orgs}"/>
	</div>
</body>
</html>
