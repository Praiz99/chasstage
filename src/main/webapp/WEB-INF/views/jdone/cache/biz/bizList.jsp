<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>连接器管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/jdone/js/cache/biz/bizList.js?1=1"></script>
	<script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
	<body>
	       <div id="configForm"></div> 	 
	       <div id="detainfo"></div>         
	</body>
</html>