$(function(){
	dicInit(true);
	initDataGrid();
});

function initDataGrid(){
	$("#frwsForm").datagrid({
		url:ctx+"/com/frws/getFilePageData",		
		width:'100%',
		pagination:true,
		checkOnSelect:true,
		emptyMsg: '<span>暂无数据</span>',
		columns:[[
		      {field:'id',align:'center',checkbox:true},    
		      {field:'realName',title:'文件名称',align:'center',width:'15%',
		    	  formatter:function(value,row,index){ 
		    		    var info = "<a href='#' style='text-decoration:underline;' >"+value+"</a>";	
		    		    return info;
		    	  }      
		      }, 
		      {field:'bizTypeName',title:'文件分类',align:'center',width:'15%'},	
		      {field:'bizTableName',title:'关联数据表',align:'center',width:'15%'},	
		      {field:'createUserIdName',title:'上传人',align:'center',width:'10%'}, 		   
		      {field:'updateTime',title:'上传时间',align:'center',width:'15%'},		
		      {field:'storageMark',title:'容器标识',align:'center',width:'12%'},	
		      {field:'frwsMarkName',title:'服务标识',align:'center',width:'12%'}, 		   
		      {field:'dsdmName',title:'所属地市',align:'center',width:'5%'}	
/*		      {field:'operation',title:'操作',align:'center',width:'19%',
	    	     formatter:function(value,row,index){ 
	    	    	   var info = ""; 	
	    	    	 if(row.id!=undefined){
	    	    		info += "<a href='#' onclick='addOrUpdateFrws(\"" + row.id + "\");'>[修改]</a> ";
	    	    	 }
                     return info;  
                  } 
		      }*/
		  ]]	
	});	
}	

//查询方法
function searchFunc(){
	var data = $("#searchForm").serializeObject();
	$("#frwsForm").datagrid("load",data);//加载和显示数据
}

//清空查询条件
function ClearQuery() {
	$("#table_form").find("input").val("");
}
	






