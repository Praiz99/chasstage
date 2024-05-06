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
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
 <script type="text/javascript">
    	var ctx = '${ctx}';
    	function close(g,response){
    		if(response["success"] && response["success"]==true){
    			$.messager.alert('温馨提示','处理人变更成功！','info',function(){
        			(window.parent && window.parent.closeDialog && window.parent.closeDialog());
    			});
             }else{
            	 $.messager.alert('温馨提示','处理人变更失败！');
            }
    	}
    	
    </script>
</head>
<body >

<act:engine id="actProcessEngineDiv" bizId="${bizId}" bizType="${bizType}" 
actKey="${actKey}" bizName="${bizName}" instId="${instId}" isOnlyChangeProUsers="true" submitCallBack="close" />
<div id='actProcessEngineDiv' style='width:450px;'></div>
</body>
</html>
