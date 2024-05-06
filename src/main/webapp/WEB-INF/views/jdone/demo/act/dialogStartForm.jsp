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
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	/**/
     	var engine = null;
    	/* $(function(){
    		engine = $("#actProcessEngineDiv").ligerProcessEngine({
    			actKey:'${actKey}',
    			bizId:'${bizId}',
    			bizType:'${bizType}',
    			bizName:'${bizName}',
    			bizFormDom:'#myform',
    			isOnlyStartNewInst:true,
    			showSubmitButtons:true,
				submitCallBack:function(g,response){
					alert("处理成功");
					g.reRender();
				},    			
				width:500
			});
    	});  */
    	function test(){
    		var engine = $("#actProcessEngineDiv").ligerGetProcessEngineManager();
    		alert(JSON.stringify(engine.getSubmitData()));
    	}
    </script>
</head>
<body >

    <act:engine id="actProcessEngineDiv" bizId="${bizId}" bizType="${bizType}" actKey="${actKey}" bizName="${bizName}"/>
	<!-- <div id='actProcessEngineDiv' style='width:450px;'></div> -->
	<form id='myform' action="test">
		<input type='hidden' name='msgType' value='act-test'/>
		<input type='hidden' name='msgTypeName' value='流程测试'/>
		<input type='hidden' name='sjbh' value='A111111'/>
		<input type='hidden' name='sjmc' value='测试名称'/>
	</form>
</body>
</html>
