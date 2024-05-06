<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/task/updateTaskType.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	    <form id="ff"  method="post">
	    <div class="form-panel">
	    	<div class="panel-top">
				<div class="tbar-title">
					<span>添加任务分类</span>	
				</div>
			</div>
	    	<div class="panel-body">
	    	<input name="id" type="hidden" value="${taskType.id}" />
	    	<input id="deployId" type="hidden" value="${taskType.deployId}" />
	    	<input name="createTime" type="hidden" value="${taskType.createTime}" />
	    	<input name="dataFlag" type="hidden" value="${taskType.dataFlag}" />
	    	<table class="table_form">
	    		<tr>
	    			<th width="12%">分类名称:</th>
	    			<td width="38%"><input class="required" type="text" name="typeName" value="${taskType.typeName}"></input></td>
	    			<th width="12%">分类标识:</th>
		    		<td width="38%"><input class="required" type="text" name="typeMark" value="${taskType.typeMark}"></input></td>
	    		</tr>
	    		<tr>
	    			<th width="12%">部署名称:</th>
	    			<td width="38%">
	    				<select name="deployId" id="deployList" class="input-xlarge required">
							<option value="" selected="selected"></option>
				 		</select>
	    			</td>
	    		</tr>
	    	</table>
	    	<div class="form-btns">
					<input id="btnSubmit" class="button" type="button" onclick="submitForm()" value="保 存">&nbsp;
					<input id="btnCancel" class="button" type="button" onclick="window.history.go(-1);" value="返 回">
			</div>		
	    	</div>
	    </div>
	    </form>

</body>

</html>
