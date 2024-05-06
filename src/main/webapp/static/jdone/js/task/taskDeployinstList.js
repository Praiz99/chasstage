$(function(){
	initDataGrid();
});

function initDataGrid(){
	var taskDeployId = $("#taskDeployId").val(); 
	$("#dg").datagrid({
		url:"getTaskDeployinstData?deployId="+taskDeployId,
		width:'100%',
		pagination:true,
		rownumbers : true,
		checkOnSelect:true,
		toolbar:initToolbar(taskDeployId),
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'instIp',title:'实例IP端口',align:'center',width:'20%'},
	              {field:'instMark',title:'实例标识',align:'center',width:'20%'},
	              {field:'instStatus',title:'实例状态',align:'center',width:'20%'},
	              {field:'createTime',title:'创建时间',align:'center',width:'20%'},
	              {field:'opt',title:'操作',align:'center',width:'19%',  
	                  formatter:function(value,row,index){ 
	                	var str;
	                	str ="<a href='updateTaskDeployinst?id="+row.id+"'>修改</a>&nbsp<a href='deployinstTaskList'>任务列表</a>"
	                    return str;  
	                  }  
	                }
	          ]]		
	});
}

function initToolbar(taskDeployId){
    var toolbar = [{
        text:'添加',
        iconCls:'icon-add',
        handler:function(){
        	window.location.href = ctx+"/task/addTaskDeployinst?taskDeployId="+taskDeployId;
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
        		$.ajax({ 
        	        type: "POST", 
        	        data :{
        	        	"ids": ids   
        	        },
        	        dataType: "JSON", 
        	        async: false, 
        	        url:ctx + "/task/delectTaskDeployinstData",
        	        success: function(data) {
        	        	$.messager.alert('提示', data.msg, 'info',function(){
        	        			window.location.reload();
        	        	});
        	        	
        	        }

        	    });
        	}
        	

        }
    },{
        text:'返回',
        iconCls:'icon-back',
        handler:function(){
        	window.location.href = ctx+"/task/taskDeployList";
        }
    }];
    
    return toolbar;
}

