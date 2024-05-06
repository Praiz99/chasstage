$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#configForm").datagrid({
		url:ctx+"/cache/connector/getConnectorPageData",		
		width:'100%',
		pagination:true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
		columns:[[
		      {field:'id',align:'center',checkbox:true},    
		      {field:'name',title:'连接器名称',align:'center',width:'15%'}, 
		      {field:'mark',title:'连接器标识',align:'center',width:'9%'},		      
		      {field:'cacheType',title:'缓存类型',align:'center',width:'10%'}, 
		      {field:'connObjType',title:'连接对象类型',align:'center',width:'15%'}, 
		      {field:'connAddr',title:'服务地址',align:'center',width:'15%'},		 
		      {field:'cacheStatus',title:'服务状态',align:'center',width:'10%',
		    	  formatter:function(value,row,index){
		    		  if(row.cacheStatus==0){
		    			  return "正常";
		    		  }else{
		    			  return "异常";
		    		  }
		    	  } },	
		      {field:'tjxgsj',title:'操作时间',align:'center',width:'10%'},	
		      {field:'operation',title:'操作',align:'center',width:'15%',
	    	     formatter:function(value,row,index){ 
	    	    	   var info = ""; 		
	    	    	    info += "<a href='#' onclick='addOrUpdateContainer(\"" + row.id + "\");'>[编辑]</a> ";
	    	    		info += "<a href='#' onclick='addOrUpdateContainer(\"" + row.id + "\");'>[数据管理]</a> ";
	 	    	    	info += "<a href='#' onclick='testCacheService(\"" + row.id + "\");'>[检测] </a> ";
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
	window.location.href = ctx+"/cache/connector/addorEditConnectorForm?id="+id;
}
function submitForm(){   
	if(!$("#mainForm").valid()){
		return false;
	}
	var tableData = $("#mainForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/cache/connector/saveConnector",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示', data.msg,'info',function(){
				window.location.href=ctx + "/cache/connector/connectorList";
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
   			url:ctx+'/cache/connector/delConnector?rowsid='+rowsid,
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

//检测缓存服务
function testCacheService(id){
	 $.ajax({
			type:'get',
		    url:ctx+'/cache/connector/testCacheService',
		    dataType: 'json',
		    data:{id:id},
		    success: function(data){
		    		if(data.success) {		    			
		         		$.messager.alert('温馨提示',data.msg, 'info');
			        }else {
			        	$.messager.alert('温馨提示', data.msg, 'error');
			        }	
		    		$('#configForm').datagrid('reload'); 
         }			
		});		
}

function back(){
    window.location.href = ctx+"/cache/connector/connectorList";
 }
