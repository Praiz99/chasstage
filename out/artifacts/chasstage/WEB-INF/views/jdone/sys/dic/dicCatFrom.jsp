<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>字典管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/bootstrap.min.css">
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/jeesite.css"> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/framework/utils/spell.js" ></script>
	<script type="text/javascript" src="${ctx}/static/framework/utils/common.js" ></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/dic/dicCatList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
       <div style="width: 100%">
               <table id="datagrid"></table>
               <div id="dd"></div>
       </div>
</body>
</html>

