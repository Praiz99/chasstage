<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文件服务管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
		<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/jdone/js/com/frws/frwsFileForm.js"></script>
	<script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
	<body>
	       <div id="frwsForm"></div> 	 
	         <div id="detainfo"></div>         
	</body>
</html>