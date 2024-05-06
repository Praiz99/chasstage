<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/rmdMsgList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="actProHis" name="actProHis">
		<input id="bizId" name="bizId" type="hidden" value="${bizId}">
		<input id="bizType" name="bizType" type="hidden" value="${bizType}">
		<div class="form-panel">
			<div class="panel-top">
				<div class="tbar-title" style="border-radius: 0px; background-color: c9dfff;">
					<span style="color: black;">待处理人</span>	
				</div>
			</div>
		</div>
	</form>
	<div id="content" class="row-fluid" style="margin: 0px 5px 0px 5px;">
		<table id="datagrid"></table>
	</div>
</body>
</html>
