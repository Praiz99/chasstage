<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>任务管理</title>
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
					<span>日志详情</span>	
				</div>
			</div>
	    	<div class="panel-body">
	    	<table class="table_form">
	    		<tr>
	    			<th width="20%">任务名称:</th>
	    			<td>${taskLog.taskName}</td>
	    			<th width="20%">任务ID:</th>
		    		<td>${taskLog.taskId}</td>
	    		</tr>
	    		<tr>
	    			<th width="20%">执行开始时间:</th>
	    			<td>
	    				<fmt:formatDate value="${taskLog.exeStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    			</td>
	    			<th width="20%">执行结束时间:</th>
	    			<td>
	    				<fmt:formatDate value="${taskLog.exeCmpltTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<th width="20%">是否执行成功:</th>
		    		<td>
		    			<c:if test="${taskLog.isExeOk ==1}">正常</c:if>
             	 		<c:if test="${taskLog.isExeOk ==0}">失败</c:if>
		    		</td>
		    		<th width="20%">执行服务器IP:</th>
		    		<td>${taskLog.exeServerIp}</td>
	    		</tr>
	    	</table>
	    	<div class="form-btns">
					<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.history.go(-1);">
				</div>	
	    	</div>
	</div>

</body>

</html>
