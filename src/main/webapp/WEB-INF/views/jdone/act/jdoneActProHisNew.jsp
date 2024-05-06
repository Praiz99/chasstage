<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>流程审核历史</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneActProHisNew.js?1=1"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="actProHis" name="actProHis">
		<input id="bizId" name="bizId" type="hidden" value="${bizId}">
		<input id="bizType" name="bizType" type="hidden" value="${bizType}">
		<div class="form-panel">
			<div class="panel-top" style="margin:0px 0px 0px 0px">
				<div class="tbar-title" style="border-radius: 0px; background-color: c9dfff;">
					<span style="color: black;">流程审核历史</span>	
				</div>
			</div>
		</div>
	</form>
	<table id="datagrid"></table>
	<div id="ReceiveFeedBackDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="变更处理人" style="width:500px; height:80%; left:25%;">
		<iframe scrolling="auto" id='openReceiveFeedBack' name="openReceiveFeedBack" frameborder="0" src="" style="width: 100%; height:100%;"></iframe>
	</div>
</body>
</html>
