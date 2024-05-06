$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url:"getTaskDeployData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		toolbar : initToolbar(),
		fitColumns : true,
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'deployName',title:'部署名称',align:'center',width:'14%'},
	              {field:'deployMark',title:'部署标识',align:'center',width:'14%'},
	              {field:'deployType',title:'部署类型',align:'center',width:'14%'},
	              {field:'deployDesc',title:'部署描述',align:'center',width:'14%'},
	              {field:'deployCount',title:'部署实例数',align:'center',width:'12%'},
	              {field:'createTime',title:'创建时间',align:'center',width:'14%'},
	              {field:'opt',title:'操作',align:'center',width:'15%',  
            	  formatter : function(value, row, index) {
						var str;
						str = "<a class='oper-btn oper-view' href='taskDeployDetalis?taskDeployId="
								+ row.id
								+ "'>详情</a>&nbsp<a class='oper-btn oper-info' href='taskDeployinstList?taskDeployId="
								+ row.id + "'>部署实例</a>"
						return str;
						}
	              }
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'添加',
        handler:function(){
        	window.location.href = ctx+"/task/addTaskDeploy";
        }
    },{
        text:'删除',
        handler:function(){
        	var rows = $('#datagrid').datagrid('getSelections');
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
        	        url:ctx + "/task/delectTaskDeployData",
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

