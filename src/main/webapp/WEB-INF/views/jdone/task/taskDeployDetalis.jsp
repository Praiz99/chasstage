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
					<span>任务部署详情</span>	
				</div>
			</div>
	    	<div class="panel-body">
	    	<table class="table_form">
	    		<tr>
	    			<th width="20%">部署名称:</th>
	    			<td>${taskDeploy.deployName}</td>
	    			<th width="20%">部署标识:</th>
		    		<td>${taskDeploy.deployMark}</td>
	    		</tr>
	    		<tr>
	    			<th width="20%">部署类型:</th>
	    			<td>${taskDeploy.deployType}</td>
	    			<th width="20%">部署实例数:</th>
		    		<td>${taskDeploy.deployCount}</td>
	    		</tr>
	    		<tr>
	    			<th width="20%">创建时间:</th>
	    			<td>
	    				<fmt:formatDate value="${taskDeploy.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    			</td>
	    			<th width="20%">修改时间:</th>
	    			<td>
	    				<fmt:formatDate value="${taskDeploy.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    			</td>
	    		</tr>
	    		<tr>
		    		<th width="20%">部署描述:</th>
		    		<td colspan="3">${taskDeploy.deployDesc}</td>
	    		</tr>
	    	</table>
	    	<div class="form-btns">
					<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.history.go(-1);">
				</div>	
	    	</div>
	</div>

</body>

</html>
