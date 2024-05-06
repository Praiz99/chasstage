<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>任务列表</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/org/orgForm.css">
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<div class="form-panel">
       	<div class="panel-top">
				<div class="tbar-title">
					<span>任务详情</span>	
				</div>
			</div>
	    	<div class="panel-body">
	    	<table class="table_form">
	    		<tr>
	    			<th width="20%">任务名称:</th>
	    			<td>${taskJobObj.name}</td>
	    			<th width="20%">任务标识:</th>
		    		<td>${taskJobObj.mark}</td>
	    		</tr>
	    		<tr>
	    			<th width="20%">分组标识:</th>
	    			<td>${taskJobObj.categoryMark}</td>
	    			<th width="20%">任务执行类:</th>
		    		<td>${taskJobObj.jobClass}</td>
	    		</tr>
	    		<tr>
	    			<th width="20%">任务执行策略:</th>
	    			<td>${taskJobObj.cronExp}</td>
	    			<th width="20%">任务参数:</th>
	    			<td>${taskJobObj.jobParams}</td>
	    		</tr>
	    		<tr>
		    		<th width="20%">任务描述:</th>
		    		<td colspan="3">${taskJobObj.desc}</td>
	    		</tr>
	    	</table>
	    	<div class="form-btns">
					<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.history.go(-1);">
				</div>	
	    	</div>
	</div>

</body>

</html>
