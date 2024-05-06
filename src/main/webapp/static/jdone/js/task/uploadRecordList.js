$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#dg").datagrid({
		url:ctx + "/task/getTaskUploadRecordData",
		width:'100%',
		pagination:true,
		rownumbers : true,
		checkOnSelect:true,
		toolbar:initToolbar(),
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'jobName',title:'任务名称',align:'center',width:'14%'},
	              {field:'jobMark',title:'任务标识',align:'center',width:'14%'},
	              {field:'deployIdName',title:'部署名称',align:'center',width:'14%'},
	              {field:'taskTypeName',title:'任务分类',align:'center',width:'14%'},
	              {field:'jobDesc',title:'任务描述',align:'center',width:'14%'},
	              {field:'createTime',title:'上传时间',align:'center',width:'14%'},
	              {field:'opt',title:'操作',align:'center',width:'15%',  
	                  formatter:function(value,row,index){ 
	                	var str;
	                		str ="<a href='addTask?taskMark="+row.jobMark+"&taskType="+row.taskType+"' >配置</a>&nbsp<a href='http/fileDownload?jobName="+row.jobName+"'>下载</a>";
	                    return str;  
	                  }  
	                }
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'上传',
        iconCls:'icon-add',
        handler:function(){
        	window.location.href = ctx+"/task/uploadPage";
        }
    },{
        text:'删除',
        iconCls:'icon-remove',
        handler:function(){
        	var rows = $('#dg').datagrid('getSelections');
        	if(rows.length ==0){
        		$.messager.alert('提示', '请选择你要删除的数据！', 'info');
        	}else{
        		var ids = "";
        		for(var i=0; i<rows.length; i++){
        			if (ids == "") {
        				ids = rows[0].id;
        			} else {
        				ids += "," + rows[i].id;
        			}
        		}
//        		var ids = JSON.parse(ids);
        		$.ajax({ 
        	        type: "POST", 
        	        data :{
        	        	"ids": ids   
        	        },
        	        dataType: "JSON", 
        	        async: false, 
        	        url:ctx + "/task/delectTaskUploadRecordData",
        	        success: function(data) {
        	        	$.messager.alert('提示', data.msg, 'info',function(){
        	        			window.location.reload();
        	        	});
        	        	
        	        }

        	    });
        	}
        	

        }
    }];
    
    return toolbar;
}



