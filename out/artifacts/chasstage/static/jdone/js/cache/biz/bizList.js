$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#configForm").datagrid({
		url:ctx+"/cache/biz/getBizPageData",		
		width:'100%',
		pagination:true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
		columns:[[
		      {field:'id',align:'center',checkbox:true},    
		      {field:'bizName',title:'业务名称',align:'center',width:'22%'}, 
		      {field:'bizType',title:'业务类型',align:'center',width:'20%'},		      
		      {field:'cacheMark',title:'所属容器',align:'center',width:'15%'}, 
		      {field:'isEnable',title:'是否启用',align:'center',width:'12%',
		    	  formatter:function(value,row,index){
		    		  if(row.isEnable==1){
		    			  return "已启用";
		    		  }else{
		    			  return "已停用";
		    		  }
		    	  } },	
		      {field:'tjxgsj',title:'操作时间',align:'center',width:'15%'},	
		      {field:'operation',title:'操作',align:'center',width:'15%',
	    	     formatter:function(value,row,index){ 
	    	    	   var info = ""; 		
	    	    	    info += "<a href='#' onclick='addOrUpdateBiz(\"" + row.id + "\");'>[编辑]</a> ";
	    	    		info += "<a href='#' onclick='javascript:void(\"" + row.id + "\");'>[数据管理]</a> ";
	    	    		if(row.isEnable==1){
		    	    		 info += "<a href='#' onclick='startOrStop(\"" + row.id + "\");'>[停用]</a> "; 
		    	    	}else{
		    	    		 info += "<a href='#' onclick='startOrStop(\"" + row.id + "\");'>[启用]</a> "; 
		    	    	}
		    	    	info += "<a href='#' onclick='clearBizCache(\"" + row.id + "\");'>[清除]</a> ";
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
        	addOrUpdateBiz();
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
    				  deleteBizCache(rows);
    			  } 			  
    		  });
    	  }
 
      }];
    return toolbar;
  }

//保存修改缓存信息
function addOrUpdateBiz(id){
	window.location.href = ctx+"/cache/biz/addorEditBizForm?id="+id;
}
function submitForm(){   
	if(!$("#mainForm").valid()){
		return false;
	}
	var tableData = $("#mainForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/cache/biz/saveBiz",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示', data.msg,'info',function(){
				window.location.href=ctx + "/cache/biz/bizList";
			});
		}
	});		
}

//删除缓存信息
function  deleteBizCache(rows){	
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
   			url:ctx+'/cache/biz/delBiz?rowsid='+rowsid,
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

function startOrStop(id){   
	$.ajax({
		type: "post",
		url: ctx+"/cache/biz/startOrStop?id="+id,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示', data.msg,'info',function(){
				$('#configForm').datagrid('reload');
			});
		}
	});		
}

//清除业务缓存
function clearBizCache(id){
	 $.ajax({
			type:'get',
		    url:ctx+'/cache/biz/clear',
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
    window.location.href = ctx+"/cache/biz/bizList";
 }
