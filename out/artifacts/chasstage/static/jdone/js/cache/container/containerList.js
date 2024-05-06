$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#configForm").datagrid({
		url:ctx+"/cache/container/getContainerPageData",		
		width:'100%',
		pagination:true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
		columns:[[
		      {field:'id',align:'center',checkbox:true},    
		      {field:'containerName',title:'容器名称',align:'center',width:'20%'}, 
		      {field:'containerMark',title:'容器标识',align:'center',width:'15%'},		      
		      {field:'cacheType',title:'缓存类型',align:'center',width:'10%'}, 
		      {field:'connMark',title:'连接器',align:'center',width:'15%'}, 
		      {field:'isEnable',title:'启用状态',align:'center',width:'10%',
		    	  formatter:function(value,row,index){
		    		  if(row.isEnable==1){
		    			  return "已启用";
		    		  }else{
		    			  return "已停用";
		    		  }
		    	  } },	
		      {field:'tjxgsj',title:'操作时间',align:'center',width:'12%'},	
		      {field:'operation',title:'操作',align:'center',width:'17%',
	    	     formatter:function(value,row,index){ 
	    	    	   var info = ""; 		
	    	    	    info += "<a href='#' onclick='addOrUpdateContainer(\"" + row.id + "\");'>[编辑]</a> ";
	    	    		info += "<a href='#' onclick='addOrUpdateContainer(\"" + row.id + "\");'>[数据管理]</a> ";
	    	    		if(row.isEnable==1){
		    	    		 info += "<a href='#' onclick='startOrStop(\"" + row.id + "\");'>[停用]</a> "; 
		    	    	}else{
		    	    		 info += "<a href='#' onclick='startOrStop(\"" + row.id + "\");'>[启用]</a> "; 
		    	    	}
	    	    		info += "<a href='#' onclick='clearContainer(\"" + row.id + "\");'>[清除]</a> ";
                     return info;  
                  } 
		      }
		  ]]	
	});	
}	
	
function initToolbar(){
    var toolbar = [{
        text:'添加',
        iconCls:'icon-add',
        handler:function(){
        	addOrUpdateContainer();
        }
      },{
    	  text:'删除',
    	  iconCls:'icon-cut',
    	  handler:function(){
    		  var rows =$('#configForm').datagrid('getSelections');
    		  if(rows.length == 0){
    			  $.messager.alert("系统提示","请至少选择一行数据!");
    			  return false;
    		  }
    		  $.messager.confirm("系统提示","您确认要删除吗?",function(r){
    			  if(r){
    				  deleteContainer(rows);
    			  } 			  
    		  });
    	  }
 
      }];
    return toolbar;
  }

//保存修改缓存信息
function addOrUpdateContainer(id){
	window.location.href = ctx+"/cache/container/addorEditContainerForm?id="+id;
}
function startOrStop(id){   
	$.ajax({
		type: "post",
		url: ctx+"/cache/container/startOrStop?id="+id,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示', data.msg,'info',function(){
				$('#configForm').datagrid('reload');
			});
		}
	});		
}

function submitForm(){   
	if(!$("#mainForm").valid()){
		return false;
	}
	var tableData = $("#mainForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/cache/container/saveContainer",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示', data.msg,'info',function(){
				window.location.href=ctx + "/cache/container/containerList";
			});
		}
	});		
}

//删除缓存信息
function  deleteContainer(rows){	
	var rowsid = "";
	for ( var i = 0; i < rows.length; i++) {// 拼接字符串，ID主键之间用逗号隔开
		if (rowsid == "") {
			rowsid = rows[0].id;
		} else {
			rowsid += "," + rows[i].id;
		}
	}
     $.ajax({
    	 	type: "post",
   			url:ctx+'/cache/container/delcontainer?rowsid='+rowsid,
   			dataType: 'json',
   			cache: false,
   			success:function (data){
   				if(data.success){
   					$.messager.alert("系统提示",data.msg);
   				}else{
   					$.messager.alert("系统提示",data.msg);
   				}
   				$('#configForm').datagrid('reload'); 
   			}
       		});
}

//清除容器缓存
function clearContainer(id){
	 $.ajax({
			type:'get',
		    url:ctx+'/cache/container/clear',
		    dataType: 'json',
		    data:{id:id},
		    success: function(data){
		    	if(data.success){
		    		$.messager.alert("温馨提示","清除成功！");
		    	}else{
		    		$.messager.alert("温馨提示",data.msg);
		    	}
		    }			
	});		
}



function back(){
    window.location.href = ctx+"/cache/container/containerList";
 }
