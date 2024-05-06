<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>流程审核审批</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <link rel='stylesheet' type='text/css' href='${ctx}/static/jdone/style/act/css/processCompleteForm.css'>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/act/com.act.ProcessEngine.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>	
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	var actEngine = null;
    	$(function(){
    		actEngine = $("#shsp").ligerProcessEngine({
    			actKey:'mytest',
    			bizId:'11111111111111111111111111111111',
    			bizType:'act-test',
    			bizName:'act-test',
    			bizFormDom:'#myform',
    			//isOnlyChangeProUsers:true,
				showSubmitButtons:true,
				submitCallBack:function(g,response){
					g.reRender();
				},
				proResultSelectCallBack:function(mgr,value){
					alert(value);
				}
			});
    	});
    </script>
</head>
<body style="height: 95%;width: 98%;">
	<div style="width: 450px;overflow: hidden;border: solid 1px #95B8E7;">
		<%--
		<bpmn:act2ApproveEntry actKey="mytest" bizId="11111111111111111111111111111111" bizType="act-test" initType="shsp"></bpmn:act2ApproveEntry>
	 	--%>
	 	<div id='shsp'></div>
	</div>
</body>
</html>
