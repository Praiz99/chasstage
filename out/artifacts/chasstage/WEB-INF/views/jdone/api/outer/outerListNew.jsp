<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>内接口管理</title>
 <%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/bootstrap.min.css"> --%>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/jeesite.css"> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script> 
    <script type="text/javascript" src="${ctx}/static/jdone/js/api/outer/outerListNew.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	var bizMark = 'wbjk';
    	var mark = 'outer';
    </script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/group/groupIndex.js"></script>
</head>
<body  class="easyui-layout" style="overflow-y: hidden"  scroll="no">
     <div region="north" style="width:auto;height: 37px;overflow-y: hidden">
				<div id="toptoolbar"></div>
				<div id="tt"></div>
	 </div>
       <div region="west" split="true" title="应用-分组树" style="width:180px;" id="west">
            	<ul id="tree1" class="easyui-tree"></ul>
       </div>
    
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<iframe id="operFrame" frameborder="0" scrolling="no" src="outerList" style="width:100%;height:95%;"/>
		</div>
    </div>
</body>
</html>
