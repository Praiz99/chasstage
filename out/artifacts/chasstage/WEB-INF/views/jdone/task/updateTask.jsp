<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/task/updateTask.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	    <form id="ff"  method="post">
	    <div class="form-panel">
	    	<div class="panel-top">
				<div class="tbar-title">
					<span>修改任务</span>	
				</div>
			</div>
	    	<div class="panel-body" >
	    	<input id="strategyType" type="hidden"  value="${taskExeStrategy.strategyType}"/>
	    	<input id="week" type="hidden"  value="${taskExeStrategy.week}"/>
	    	<input id="month" type="hidden" value="${taskExeStrategy.month}"/>
	    	<input id="deployId" type="hidden" value="${taskinst.deployId}" />
	    	<input id="jobImpClass" type="hidden" value="${taskinst.jobImpClass}" />
	    	<input id="taskType" type="hidden" value="${taskinst.taskType}" />
	    	<input name="id" type="hidden" value="${taskinst.id}" />
	    	<input id="paramKeyStr" type="hidden" value="${paramKeyStr}" />
	    	<input id="paramValueStr" type="hidden" value="${paramValueStr}" />
	    	<table  class="table_form">
	    		<tr>
	    			<th width="12%">任务名称:</th>
	    			<td width="38%"><input class="required" type="text" name="taskName" value="${taskinst.taskName}"></input></td>
	    			<th width="12%">部署名称:</th>
		    		<td width="38%">
	   				 	<select name="deployId" id="deployList" class="input-xlarge required" onclick="loadJobAndTypeOption()">
	   				 		<option value="" selected="selected"></option>
				 		</select>
	   				</td> 
	    		</tr>
	    		<tr>
	    			<th width="12%">任务标识:</th>
	    			<td width="38%"><input class="required" type="text" name="taskMark" value="${taskinst.taskMark}" readonly="readonly"></input></td>
	    			<th width="12%">执行类:</th>
		    		<td width="38%">
	   				   <select name="jobImpClass" id="jobList" class="input-xlarge required">
	   				   		<option value="" selected="selected"></option>
						 </select>
	   				 </td>
	    		</tr>
	    		<tr>
	    			<th width="12%">备注:</th>
	    			<td width="38%"><input  type="text"  name="remark" value="${taskinst.remark}"></input></td>
	    			<th width="12%">任务分类:</th>
		    		<td width="38%">
	   				 <select name="taskType" id="taskTypeList" class="input-xlarge required">
	   					    <option value="" selected="selected"></option>
					 </select>
	   				</td>
	    		</tr>
	    		<tr>
	    			<th width="12%">执行策略:</th>
	    			<td colspan="3">
	    			<input type="radio"  name="strategyType" value="0" checked="checked" onclick="ChangeCSS(this.value)"/>间隔时间
	    			<input type="radio"  name="strategyType" value="1" onclick="ChangeCSS(this.value)"/>每天
	    			<input type="radio"  name="strategyType" value="2" onclick="ChangeCSS(this.value)"/>每周
	    			<input type="radio"  name="strategyType" value="3" onclick="ChangeCSS(this.value)"/>每月
	    			<input type="radio"  name="strategyType" value="4" onclick="ChangeCSS(this.value)"/>固定时间
	    			</td>
	    		</tr>
	    		<tr class="2" style="display:none">	 
					<th width="12%">执行日:</th>
	    			<td colspan="3"><input type="checkbox"  name="week" value="1"/>星期一
	    				<input type="checkbox"  name="week" value="2"/>星期二
	    				<input type="checkbox"  name="week" value="3"/>星期三
	    				<input type="checkbox"  name="week" value="4"/>星期四
	    				<input type="checkbox"  name="week" value="5"/>星期五
	    				<input type="checkbox"  name="week" value="6"/>星期六
	    				<input type="checkbox"  name="week" value="7"/>星期日
	    			</td>
	    		</tr>
	    		<tr class="3" style="display:none">	 
					<th width="12%">执行月份:</th>
	    			<td colspan="3"><input type="checkbox"  name="month" value="1"/>一月
	    				<input type="checkbox"  name="month" value="2"/>二月
	    				<input type="checkbox"  name="month" value="3"/>三月
	    				<input type="checkbox"  name="month" value="4"/>四月
	    				<input type="checkbox"  name="month" value="5"/>五月
	    				<input type="checkbox"  name="month" value="6"/>六月<br>
	    				<input type="checkbox"  name="month" value="7"/>七月
	    				<input type="checkbox"  name="month" value="8"/>八月
	    				<input type="checkbox"  name="month" value="9"/>九月
	    				<input type="checkbox"  name="month" value="10"/>十月
	    				<input type="checkbox"  name="month" value="11"/>十一月
	    				<input type="checkbox"  name="month" value="12"/>十二月
	    			</td>
	    		</tr>
	    		<tr class="3" style="display:none" >	 
					<th width="12%">执行日:</th>
	    			<td colspan="3"><input  type="text" name="day"  style="width:80px;" value="${taskExeStrategy.day}">日
	    			</td>
	    		</tr>
	    		<tr class="1">	 
					<th width="12%">运行时分:</th>
	    			<td colspan="3"><input  type="text" name="hour"  style="width:80px;" value="${taskExeStrategy.hour}">小时<input class="required" type="text" name="minute"  style="width:80px;" value="${taskExeStrategy.minute}">分钟（24小时制）
	    			</td>
	    		</tr>
	    		<tr class="4" style="display:none">	 
					<th width="12%">运行时间:</th>
	    			<td colspan="3"><input  class="easyui-datetimebox" name=fixTime  style="width:200px" value="${fixTime}">
	    			</td>
	    		</tr>
	    		<tr>	 
					<th width="12%">任务参数:</th>
	    			<td colspan="3">
	    				<table id="paramsTab" style="width: 100%">
	    					
	    				</table>
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
