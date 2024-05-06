<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<%@ taglib prefix="act" uri="/tags/jdone/act/actEngine"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<script src="${ctx}/static/framework/plugins/act/com.act.ProcessEngine.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	/**/
     	var engine = null;
    	var fz="";
    	var keyVal="";
    	 $(function(){
    		 $("input[type='checkbox']").on('click',function(){
    			  if (!$(this).prop("checked")) {
    				  keyVal="";
    				  $("input[name='"+this.name+"']:checkbox").each(function () {
    	                    if ($(this).prop("checked")) {
    	                    	if(keyVal!=""){
    	         					 keyVal+=";"+this.value;
    	         				 }else{
    	         					 keyVal+=this.value;
    	         				 }
    	                    }
    	               });
                  }else{
                	  if(fz == "" || fz ==this.name){
         				 if(keyVal!=""){
         					 keyVal+=";"+this.value;
         				 }else{
         					 keyVal+=this.value;
         				 }
         			 }else if(fz !=this.name){
         				 $("input[name='"+fz+"']:checkbox").each(function() { 
      						$(this).attr("checked", false);
      				 	 });
         					 keyVal=this.value;
         			 } 
                  }
    			 fz=this.name;
    			 initAct();
    	     });
/*     		engine = $("#actProcessEngineDiv").ligerProcessEngine({
    			actKey:'${actKey}',
    			bizId:'${bizId}',
    			bizType:'${bizType}',
    			bizName:'${bizName}',
    			bizFormDom:'#myform',
    			showSubmitButtons:true,
				submitCallBack:function(g,response){
					alert("处理成功");
					g.reRender();
				},    			
				width:500
			}); */
    	});  
     	function initAct(){
  		   $("#actProcessEngineDiv").empty();
  		   $('#actProcessEngineDiv').removeAttr('ligeruiid');
     		if(keyVal!=""){
     		   var actKey="";
     		   var bizId="";
     		   var bizType="";
     		   var actVal=keyVal.split(";");
     		   for(var i=0;i<actVal.length;i++){
     			   bizId+=actVal[i].split(",")[0]+",";
     			   bizType+=actVal[i].split(",")[1]+",";
     			   actKey=actVal[i].split(",")[2];
     		   }
     		   bizId = bizId.substring(0, bizId.length - 1);
     		   bizType = bizType.substring(0, bizType.length - 1);
     		   engine = $("#actProcessEngineDiv").ligerProcessEngine({
 	       			actKey:actKey,
 	       			bizId:bizId,
 	       			bizType:bizType,
 	       			bizName:'${bizName}',
 	       			bizFormDom:'#myform',
 	       			showSubmitButtons:true,
 	   				submitCallBack:function(g,response){
       				   $("input[name='"+fz+"']:checkbox").each(function() { 
	   						$(this).attr("checked", false);
	   				    });
 	   				   alert("处理成功");
 	   	     		   for(var i=0;i<bizId.split(",").length;i++){
 	   	     			$("#ck"+bizId.split(",")[i]+"").attr("disabled",true);
 	   	     			$("#"+bizId.split(",")[i]+"").append("【已处理】");
 	        		   }
 	   		    	   fz="";
 	   	    	       keyVal="";
 	   					g.reRender();
 	   				},    			
 	   				width:500
 	   			}); 
     	   }
    	}
    	function test(){
    		var engine = $("#actProcessEngineDiv").ligerGetProcessEngineManager();
    		alert(JSON.stringify(engine.getSubmitData()));
    	}
    </script>
</head>
<body>
 		<c:forEach items="${resultDataList }" var="fz">
			流程：${fz.actModelName } 环节：${fz.nodeName}  <br>
			<c:forEach items="${fz.children }" var="msg">
					<input type="checkbox" id="ck${msg.bizId}" name="${msg.actKey}_${msg.nodeMark}" value="${msg.bizId},${msg.bizType},${msg.actKey}"><span  id="${msg.bizId}">${msg.msgTitle }</span><br>
			</c:forEach>
		</c:forEach>
		
		
 <%--    <act:engine id="actProcessEngineDiv" bizId="${bizId}" bizType="${bizType}" actKey="${actKey}" bizName="${bizName}" bizFormDom="#myform" /> --%>
	<div id='actProcessEngineDiv' style='width:450px;'></div>
</body>
</html>
