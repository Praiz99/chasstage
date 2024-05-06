<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	$(document).ready(function(){
    		initDataGrid();
    	});

    	function initDataGrid(){
    		$("#datagrid").treegrid({
    			url : ctx + "/demo/act/getActPendTaskBatchMsgDate",
    			width : '100%',
    	        idField: 'id',
    	        treeField: 'sjbh',
    			checkbox : true,
    			toolbar:initToolbar(),
    	        onSelect: function (node) {
    	            if (node.checkState == 'unchecked') {
    	                $("#datagrid").treegrid("checkNode", node.id);
    	            } else {
    	                $("#datagrid").treegrid("uncheckNode", node.id);
    	            }
    	        },
    			columns : [ [
    					{
    						field : 'sjbh',
    						title : '事件编号',
    						align : 'center',
    						width : '25%'
    					},
    					{
    						field : 'sjmc',
    						title : '事件名称',
    						align : 'center',
    						width : '25%'
    					},
    					{
    						field : 'nodeName',
    						title : '处理环节',
    						align : 'center',
    						width : '15%'
    					}, {
    						field : 'actModelName',
    						title : '流程',
    						align : 'center',
    						width : '15%'
    					}, {
    						field : 'msgTitle',
    						title : '消息标题',
    						align : 'center',
    						width : '20%'
    					}] ]
    		});
    	}

    	function initToolbar(){
    	    var toolbar = [{
    	        text:'处理',
    	        handler:function(){
    	            var rows = $('#datagrid').treegrid("getCheckedNodes");// 返回第一个被选中的行或如果没有选中的行则返回null
    	            if (rows.length == 0) {
    	                $.messager.alert("系统提示", "请至少选择一行数据!");
    	                return false;
    	            }
    	                deleteAssign(rows);
    	        }
    	    }];
    	    
    	    return toolbar;
    	}
    	
    	function deleteAssign(rows) {
    		var sjbh="";
    	    var ids = "";
    	    for (var i = 0; i < rows.length; i++) {
    	        var row = rows[i];
    	    	if(sjbh!="" && sjbh !=row.sjbh){
    	    		$.messager.alert("系统提示", "请选择相同的事件进行处理!");
	                return false;
    	    	}else{
    	    		sjbh=row.sjbh;
    	    	}
    	        //是否包含URL值来判断是具体资源，而不是分组
    	        if (row.msgTitle != null && row.msgTitle.length > 0) {
    	            ids += row.id + ",";
    	        }
    	    }
    	    ids = ids.substring(0, ids.length - 1);
    	    window.open(ctx+"/demo/act/dialogActPendTaskBatchForm?ids="+ids);
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
