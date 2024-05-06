<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<%@ taglib prefix="act" uri="/tags/jdone/act/actEngine"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel='stylesheet' type='text/css' href='${ctx}/static/framework/plugins/act/css/processEngine.css'> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/act/com.act.ProcessEngine.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
     	var engine = null;
    	$(function(){
    		engine = $("#actProcessEngineDiv").ligerProcessEngine({
    			actKey:'${actKey}',
    			bizId:'${bizId}',
    			bizType:'${bizType}',
    			bizName:'${bizName}',
    			isOnlyStartNewInst:true,
    			isOnlyCompleteTask:true,	
    			isOnlyChangeProUsers:true,
    			showSubmitButtons:true,
	   			initCallBack:function(isInitSucess,response){
	   				if(!isInitSucess){
	   				 $.messager.alert('系统提示',"流程引擎初始化失败:" + response.msg,'error',function(){
	                	closeDialog();
	    			});
	   				}
	   		    },
				submitCallBack:function(g,response){
					parent.$('#ReceiveFeedBackDialog').dialog('close');
					parent.$("#datagrid").datagrid('reload');
					g.reRender();
				},    			
				width:500
			});
    	}); 
    	function closeDialog(){
    		parent.$('#ReceiveFeedBackDialog').dialog('close');
    		parent.$("#datagrid").datagrid('reload');   
    	}
    </script>
</head>
<body >
	<div id='actProcessEngineDiv' style='width:450px;'></div>
</body>
</html>
