<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/task/addTaskDeployinst.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	    <form id="ff"  method="post">
	    <div class="form-panel">
	    	<div class="panel-top">
				<div class="tbar-title">
					<span>添加部署实例</span>	
				</div>
			</div>
	    	<div class="panel-body">
	    	<input id="deployId" name="deployId" type="hidden" value="${taskDeployId}"/>
	    	<table class="table_form">
	    		<tr>
	    			<th width="12%">实例IP端口:</th>
	    			<td width="38%"><input class="required" type="text" name="instIp" placeholder="(示例：172.0.0.1:6379)"></input></td>
	    			<th width="12%">实例标识:</th>
		    		<td width="38%"><input class="required" type="text" name="instMark" ></input></td>
	    		</tr>
	    		<tr>
	    			<th width="12%">实例状态:</th>
	    			<td colspan="3"><input class="required" type="text"  name="instStatus" ></input></td>
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
