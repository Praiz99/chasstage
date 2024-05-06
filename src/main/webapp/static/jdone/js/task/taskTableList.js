$(function(){
	initDataGrid();
});

function initDataGrid(){
	var depId = $("#depId").val();
	var taskType = $("#taskType").val();
	$("#dg").datagrid({
		url:ctx + "/task/getTaskData?deployId=" + depId + "&taskType=" + taskType,
		width:'100%',
		pagination:true,
		rownumbers : true,
		checkOnSelect:true,
		toolbar:initToolbar(depId,taskType),
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'taskName',title:'任务名称',align:'center',width:'16%'},
	              {field:'exeCount',title:'执行次数',align:'center',width:'16%'},
	              {field:'lastStartTime',title:'上次启动时间',align:'center',width:'16%'},
	              {field:'createTime',title:'添加时间',align:'center',width:'16%'},
	              {field:'taskStatus',title:'任务状态',align:'center',width:'16%',
	            	  formatter:function(value,row,index){
	            		  var statusStr;
	            		  if(row.taskStatus == 0){
	            			  statusStr = "已停用";
	            		  }else if(row.taskStatus == 1){
	            			  statusStr = "已启用";
	            		  }
	            		  return statusStr;
	            	  }},
	              {field:'opt',title:'操作',align:'center',width:'19%',  
	                  formatter:function(value,row,index){ 
	                	var str;
	                	if(row.taskStatus == 0){
	                		str ="<a href='updateTask?id="+row.id+"'>修改</a>&nbsp<a href='javascript:void(0)' onclick=\"startTask('"+row.id+"')\">启动</a>";
	                	}else{
	                		str ="<a href='javascript:void(0)' onclick=\"stopTask('"+row.id+"')\">停止</a>";

	                	}
	                    return str;  
	                  }  
	                }
	          ]]		
	});
}

function initToolbar(depId,taskType){
		var toolbar = [{
			text:'添加',
			iconCls:'icon-add',
			handler:function(){
				window.location.href = ctx+"/task/addTask?deployId=" + depId + "&taskType=" + taskType;
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
						url:ctx + "/task/delectTaskData",
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

function startTask(rowId){
	$.ajax({ 
        type: "POST", 
        data :{
        	"id": rowId   
        },
        dataType: "JSON", 
        async: false, 
        url:ctx + "/task/startTask",
        success: function(data) {
        	$.messager.alert('提示', data.msg, 'info',function(){
        		$("#dg").datagrid('reload');
        		//	window.location.reload();
        	});
        	
        }

    });
}

function stopTask(rowId){
	$.ajax({ 
        type: "POST", 
        data :{
        	"id": rowId   
        },
        dataType: "JSON", 
        async: false, 
        url:ctx + "/task/stopTask",
        success: function(data) {
        	$.messager.alert('提示', data.msg, 'info',function(){
        		$("#dg").datagrid('reload');
        		//	window.location.reload();
        	});
        	
        }

    });
}
