<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	$(document).ready(function(){
    		initDataGrid();
    	});

    	function initDataGrid(){
    		$("#datagrid").datagrid({
    			url : ctx + "/demo/act/getActPendTaskMsg",
    			width : '100%',
    			pagination : true,
    			rownumbers : true,
    			checkOnSelect : true,
    			toolbar:initToolbar(),
    			emptyMsg: '<span>无记录</span>',
    			columns : [ [
    					{
    						field : 'recObjName',
    						title : '姓名',
    						align : 'center',
    						width : '8%'
    					},
    					{
    						field : 'recObjMark',
    						title : '身份证号',
    						align : 'center',
    						width : '12%'
    					},
    					{
    						field : 'recOrgName',
    						title : '所属单位',
    						align : 'center',
    						width : '20%'
    					}, {
    						field : 'sendObjName',
    						title : '发送人',
    						align : 'center',
    						width : '8%'
    					}, {
    						field : 'sendOrgName',
    						title : '发送人单位',
    						align : 'center',
    						width : '20%'
    					}, {
    						field : 'createTime',
    						title : '发送时间',
    						align : 'center',
    						width : '10%'
    					}, {
    						field : 'isMobileRmd',
    						title : '短信提醒',
    						align : 'center',
    						width : '8%',
    						formatter : function(value, row, index) {
    							if(row.isMobileRmd == "1" ){
    								return "是";
    							}
    							return "否";
    						}
    					},{field:'_operate',title:'操作',align:'center',width:'13%',formatter:formatOper} ] ]
    		});
    	}
    	

    	function initToolbar(){
    	    var toolbar = [{
    	        text:'批量审核审批',
    	        iconCls:'icon-add',
    	        handler:function(){
    	        	plshsp();
    	        }
    	    }];
    	    
    	    return toolbar;
    	}

    	function plshsp(){  
    		$('#openReceiveFeedBack')[0].src = ctx+"/demo/act/processBatch";
    		$('#ReceiveFeedBackDialog').dialog('open');
    	} 
    	
    	function formatOper(val,row,index){  
    	    return '<a href="#" onclick="msgDeal('+index+')">处理</a><a style="margin-left:5px;" href="#" onclick="deleteActInst('+index+')">作废</a>';  
    	}
    	
    	function msgDeal(index){
    	    var row = $('#datagrid').datagrid('getRows')[index];
    	    window.open(ctx+"/demo/act/actNodeCompeleteForm?prevNodeProId="+row.nodeProId);
    	}
    	
    	function deleteActInst(index){
    	    var row = $('#datagrid').datagrid('getRows')[index];
    	    window.open(ctx+"/act2/engine/deleteProcessInst?bizType="+row.bizType+"&bizId="+row.bizId+"&actKey=mytest");
    	}
    	
    </script>
</head>
<body style="height: 95%;">
	<div id="content" class="row-fluid" style="margin: 0px 5px 0px 5px;">
		<table id="datagrid"></table>
	</div>
	<div id="ReceiveFeedBackDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="批量审核审批" style="width:920px; height: 550px;">
		<iframe scrolling="auto" id='openReceiveFeedBack' name="openReceiveFeedBack" frameborder="0" src="" style="width: 100%; height:100%;"></iframe>
	</div>
</body>
</html>
