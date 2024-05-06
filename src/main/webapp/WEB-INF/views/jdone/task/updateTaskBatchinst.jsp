<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/task/updateTaskBatchinst.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
<form id="ff"  method="post">
	<div id="tt" class="easyui-tabs" style="width:100%;height:800px;">
		<div title="修改任务基本信息">
	    <div class="form-panel">
	    	<div class="panel-body">
	    	<input id="deployId" type="hidden" value="${taskBatchinst.deployId}" />
	    	<input id="taskType" type="hidden" value="${taskBatchinst.taskType}" />
	    	<input id="inputType" type="hidden" value="${taskBatchinst.inputType}" />
	    	<input id="inUrl" type="hidden" value="${taskBatchinst.inputUrl}" />
	    	<input id="inputSource" type="hidden" value="${taskBatchinst.inputSource}" />
	    	<input id="inTable" type="hidden" value="${taskBatchinst.inputTable}" />
	    	<input id="outputType" type="hidden" value="${taskBatchinst.outputType}" />
	    	<input id="outUrl" type="hidden" value="${taskBatchinst.outputUrl}" />
	    	<input id="outputSource" type="hidden" value="${taskBatchinst.outputSource}" />
	    	<input id="outTable" type="hidden" value="${taskBatchinst.outputTable}" />
	    	<input id="desc" type="hidden" value="${taskBatchinst.taskDesc}" />
	    	<input id="inputFieldStr" type="hidden" value="${taskBatchInput.inputField}" />
	    	<input id="logoField" type="hidden" value="${taskBatchInput.logoField}" />
	    	<input id="updateField" type="hidden" value="${taskBatchInput.updateField}" />
	    	<input id="outputFieldStr" type="hidden" value="${outputFieldStr}" />
	    	<input id="paramNameStr" type="hidden" value="${paramNameStr}" />
	    	<input id="operatorStr" type="hidden" value="${operatorStr}" />
	    	<input id="paramValueStr" type="hidden" value="${paramValueStr}" />
	    	<input name="id" type="hidden" value="${taskBatchinst.id}"/>
	    	<input name="inputId" type="hidden" value="${taskBatchInput.id}"/>
	    	<input name="createTime" type="hidden" value="${taskBatchinst.createTime}"/>
	    	<input name="dataFlag" type="hidden" value="${taskBatchinst.dataFlag}"/>
	    	<table class="table_form">
	    		<tr>
	    			<th width="12%">任务名称:</th>
	    			<td width="38%"><input class="required" type="text" name="taskName" value="${taskBatchinst.taskName}"></input></td>
	    			<th width="12%">任务标识:</th>
		    		<td width="38%"><input class="required" type="text" name="taskMark" value="${taskBatchinst.taskMark}" readonly="readonly"  style="width:90%;border:1px solid #f9f9f9"></input></td>
	    		</tr>
	    		<tr>
	    			<th width="12%">部署名称:</th>
		    		<td width="38%">
		 				<select name="deployId" id="deployList" class="input-xlarge required" onclick="loadJobAndTypeOption()">
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
	    			<th width="12%">输入类型:</th>
	    			<td colspan="3">
	    			<input type="radio"  name="inputType" value="0" checked="checked" onclick="inputChange(this.value)"/>HTTP接口
	    			<input type="radio"  name="inputType" value="1" onclick="inputChange(this.value)"/>数据库
	    			</td>
	    		</tr>
	    		<tr class="0">	 
					<th width="12%">输入HTTP接口URL:</th>
	    			<td colspan="3"><input  type="text" name="inputUrl" id="inputUrl" style="width:90%;"/>
	    			</td>
	    		</tr>
	    		<tr class="1" >	 
					<th width="12%">输入数据源:</th>
	    			<td width="38%">
	    				<select name="inputSource" id="inputSourceList" >
	    					<option value="" selected="selected"></option>
	    				</select>
					</td>
	    			<th width="12%">输入表名:</th>
		    		<td width="38%">
		    			<input name="inputTable" id="inputTable" readonly="readonly" type="text" />
						<a href="#" onclick="showTables(1);">选择</a>
		    		</td>
	    		</tr>
	    		<tr>
	    			<th width="12%">输出类型:</th>
	    			<td colspan="3">
	    			<input type="radio"  name="outputType" value="0" checked="checked" onclick="outputChange(this.value)"/>HTTP接口
	    			<input type="radio"  name="outputType" value="1" onclick="outputChange(this.value)"/>数据库
	    			</td>
	    		</tr>
	    		<tr class="2">	 
					<th width="12%">输出HTTP接口URL:</th>
	    			<td colspan="3"><input  type="text" name="outputUrl" id="outputUrl" style="width:90%;"/>
	    			</td>
	    		</tr>
	    		<tr class="3">	 
					<th width="12%">输出数据源:</th>
	    			<td width="38%">
	    				<select name="outputSource" id="outputSourceList" >
	    					<option value="" selected="selected"></option>
	    				</select>
					</td>
	    			<th width="12%">输出表名:</th>
		    		<td width="38%">
		    			<input name="outputTable" id="outputTable"  readonly="readonly" type="text" />
						<a href="#" onclick="showTables(2);">选择</a>
		    		</td>
	    		</tr>
	    		<tr>
	    			<th width="12%">任务描述:</th>
	    			<td colspan="3"><textarea class="required" name="taskDesc" id="taskDesc" style="width:66%;height:80px;"></textarea></td>
	    		</tr>
	    	</table>
	    	<div class="form-btns">
					<input id="btnSubmit" class="button" type="button" onclick="submitForm()" value="保 存">&nbsp;
					<input id="btnCancel" class="button" type="button" onclick="window.location.href='${ctx}/task/taskBatchinstList'" value="返 回">
			</div>	
			<div id="refTablesDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="选择表名" style="width: 800px; height: 500px;">
				<iframe scrolling="auto" id='openRefTables' name="openRefTables
				" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
			</div>	
	    	</div>
	    </div>
	    </div>
	    <div title="输入字段配置">
	    	<div class="form-panel">
	    	<div class="panel-body">
	    	<input name="taskId" type="hidden" />
	    	<table class="table_form">
	    		<tr>
	    			<th width="12%">输入类型:</th>
	    			<td colspan="3">
	    			<input type="radio" disabled="disabled"  name="deployInputType" value="0" />HTTP接口
	    			<input type="radio" disabled="disabled"  name="deployInputType" value="1" />数据库
	    			</td>
	    		</tr>
	    		<tr class="0">	 
					<th width="12%">输入HTTP接口URL:</th>
	    			<td colspan="3"><input type="text" readonly="readonly" id="deployInputUrl" style="width:90%;border:1px solid #f9f9f9">
	    			</td>
	    		</tr>
	    		<tr class="0">	 
					<th width="12%">输入字段:</th>
	    			<td colspan="3"><input  type="text" id="HttpField" name="inputField" style="width:75%;">（字段之间用“，”隔开）
	    			</td>
	    		</tr>
	    		<tr class="1">	 
					<th width="12%">输入数据源:</th>
	    			<td width="38%">
	    				<select disabled="disabled" id="deployInputSourceList" >
	    					<option value="" selected="selected"></option>
	    				</select>
					</td>
	    			<th width="12%">输入表名:</th>
		    		<td width="38%">
		    			<input disabled="disabled" id="deployInputTable"  type="text" />
		    		</td>
	    		</tr>
	    		<tr class="1">	 
					<th width="12%">输入字段配置:</th>
	    			<td colspan="3">
	    				<table id="deployInputTab"></table>
	    			</td>
	    		</tr>
	    		<tr>	 
					<th width="12%">唯一标识字段:</th>
	    			<td colspan="3">
	    				<select name="logoField" id="logoFieldList" onclick="addOption(this)">
	    					<option value="" selected="selected"></option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>	 
					<th width="12%">输入表更新字段:</th>
	    			<td colspan="3">
	    				<select name="updateField" id="updateFieldList" onclick="addOption(this)">
	    					<option value="" selected="selected"></option>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    	</div>
	   		</div>
		</div>
		<div title="输出字段配置">
	   		<div class="form-panel">
	    	<div class="panel-body">
	    	<table class="table_form">
	    		<tr>
	    			<th width="12%">输出类型:</th>
	    			<td colspan="3">
	    			<input type="radio" disabled="disabled"  name="deployOutputType" value="0" />HTTP接口
	    			<input type="radio" disabled="disabled"  name="deployOutputType" value="1" />数据库
	    			</td>
	    		</tr>
	    		<tr class="2">	 
					<th width="12%">输出HTTP接口URL:</th>
	    			<td colspan="3"><input type="text" readonly="readonly" id="deployOutputUrl" style="width:90%;border:1px solid #f9f9f9">
	    			</td>
	    		</tr>
	    		<tr class="3">	 
					<th width="12%">输出数据源:</th>
	    			<td width="38%">
	    				<select disabled="disabled" id="deployOutputSourceList" >
	    					<option value="" selected="selected"></option>
	    				</select>
					</td>
	    			<th width="12%">输出表名:</th>
		    		<td width="38%">
		    			<input disabled="disabled" id="deployOutputTable"  type="text" />
		    		</td>
	    		</tr>
	    		<tr>	 
					<th width="12%">输出字段配置:</th>
	    			<td colspan="3">
	    				<table id="deployOutputTab" style="width: 100%">
	    					
	    				</table>
	    			</td>
	    		</tr>
	    	</table>
	    	</div>
	    	</div>
		</div>
		<div title="输入参数配置">
			<div class="form-panel">
	    	<div class="panel-body">
	    		<table id="deployParamTab" class="table_form">
	    					
	    		</table>
	    		<div class="form-btns">
					<input id="btnSubmit" class="button" type="button" onclick="addTr(1)" value="添加">&nbsp;
					<input id="btnCancel" class="button" type="button" onclick="delTr(1)" value="删除">
			</div>
	    	</div>
	    	</div>
		</div>
	</div>
</form>

</body>

</html>
