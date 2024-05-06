<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>常用资料下载</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/info/infoDownloadList.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/info/infoDownload.css" />
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="overflow-x:hidden;">
	<div id="soft_box" style="width:100%;">
	</div>
</body>
</html>
