<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/task/fileUpload.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	    <form id="ff"  method="post" enctype="multipart/form-data">
	    <div class="form-panel">
	    	<div class="panel-top">
				<div class="tbar-title">
					<span>上传任务</span>	
				</div>
			</div>
	    	<div class="panel-body" >
	    	<table  class="table_form">
	    		<tr>
	    			<th width="12%">任务名称:</th>
	    			<td width="38%"><input class="required" type="text"  name="jobName" ></input></td>
	    			<th width="12%">任务标识:</th>
	    			<td width="38%"><input class="required" type="text"  name="jobMark" ></input></td>
	    		</tr>
	    		<tr>
		    		<th width="12%">部署名称:</th>
		    		<td width="38%">
		    			<select name="deployId" id="deployList" class="input-xlarge required" onclick="loadTypeOption()">
							<option value="" selected="selected"></option>
				 		</select>
		    		</td>
	    			<th width="12%">任务分类:</th>
		    		<td width="38%">
		    			<select name="taskType" id="taskTypeList" class="input-xlarge required">
							<option value="" selected="selected"></option>
						 </select>
		    		</td>
	    		</tr>
	    		<tr>
	    			<th width="12%">上传文件:</th>
	    			<td colspan="3"><input type="file" name="file" multiple="multiple"></td>
	    		</tr>
	    		<tr>
	    			<th width="12%">任务描述:</th>
	    			<td colspan="3"><textarea class="required" name="jobDesc" style="width:75%;height:65px;"></textarea></td>
	    		</tr>
	    	</table>
	    	<div class="form-btns">
					<input id="btnSubmit" class="button" type="button" onclick="submitForm()" value="上传">&nbsp;
					<input id="btnCancel" class="button" type="button" onclick="window.history.go(-1);" value="返 回">
			</div>	
	    	</div>
	    </div>
	    </form>

</body>

</html>
