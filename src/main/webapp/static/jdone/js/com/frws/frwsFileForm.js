$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#frwsForm").datagrid({
		url:ctx+"/com/frws/getFrwsAppListData",		
		width:'100%',
		pagination:true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		columns:[[
		      {field:'id',align:'center',checkbox:true},    
		      {field:'appName',title:'应用名称',align:'center',width:'15%',
		    	  formatter:function(value,row,index){ 
		    		    var info = "<a href='#' style='text-decoration:underline;' onclick='detailsInfo(\"" + row.id + "\",\""+row.appMark+"\");'>"+value+"</a>";	
		    		    return info;
		    	  }      
		      }, 
		      {field:'appMark',title:'应用标识',align:'center',width:'15%'},	
		      {field:'appDesc',title:'应用描述',align:'center',width:'15%'},	
		      {field:'isEnable',title:'是否启用',align:'center',width:'20%',
		    	  formatter:function(value,row,index){
		    		  if(row.isEnable==1){
		    			  return "启用";
		    		  }else{
		    			  return "停用";
		    		  }
		    	  } 
		      }, 		   
		      {field:'createTime',title:'创建时间',align:'center',width:'15%'},		     
		      {field:'operation',title:'操作',align:'center',width:'19%',
	    	     formatter:function(value,row,index){ 
	    	    	   var info = ""; 	
	    	    	 if(row.id!=undefined){
	 	    	    	info += "<a href='#' onclick='checkFrwsService(\"" + row.id + "\");'>[检测] </a> ";
	 	    	    	info += "<a href='#' onclick='startFrwsService(\"" + row.id + "\");'>[启用]</a> ";
	    	    		info += "<a href='#' onclick='addOrUpdateFrws(\"" + row.id + "\");'>[修改]</a> ";
	    	    	 }
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
        	addOrUpdateFrws();
        }
      },{
    	  text:'删除',
    	  iconCls:'icon-cut',
    	  handler:function(){
    		  var rows =$('#frwsForm').datagrid('getSelections');
    		  if(rows.length == 0){
    			  $.messager.alert("系统提示","请至少选择一行数据!");
    			  return false;
    		  }
    		  $.messager.confirm("系统提示","您确认要删除吗?",function(r){
    			  if(r){
    				  deleteFrws(rows);
    			  } 			  
    		  });
    	  }
      }];
    return toolbar;
  }
	
//保存修改文件服务信息
function addOrUpdateFrws(id){
	window.location.href = ctx+"/com/frws/addorEditFrwsForm?id="+id;	
}

function submitForm(){   
	   var cacheName =   $("input[name='appName']").val();
	   var cacheMark =   $("input[name='appMark']").val();
	   if(cacheName=="" || cacheMark==""){
		   alert("名称或者标识不能 为空！ ");
		   return ;
	   }
    $('#mainForm').form('submit',{
      	    url:"saveorUpdateFrwsApp",
      	    onSubmit: function(){
      	     var isValid = $(this).form('validate');      	   
      			if (!isValid){
      				$.messager.progress('close');
      			}
      			return isValid;
      	    },
      	    success:function(data){
      			var data = eval('(' + data + ')');
      	    	if(data.success){
      	    		$.messager.alert('提示', data.msg, 'info',function(){
      	        		//window.location.reload();
      	    	     window.location.href = ctx+"/com/frws/frwsFileForm";
      	        	});
      	    	}else{
      	    		$.messager.alert('提示', data.msg, 'error');
      	    	}
      	    }
      });		
}

//删除文件服务信息
function deleteFrws(rows){
	var rowsid ="";	
	for(var i = 0;i< rows.length; i++ ){
		if(rows[i].id==undefined){
			alert("删除失败，本地文件服务无法删除！");
			return ;
		}
		if(rowsid == ""){
			rowsid = rows[0].id;
		} else {
			rowsid += "," + rows[i].id;
		}	
	}
	
	$.ajax({
		url:'delFrwsApp?rowsid='+rowsid,
		async:false,
		success:function(data){
			if(data.success){
				$.messager.alert("操作提示",data.msg,'info',function(){
					$('#frwsForm').datagrid('reload');
				});
			}else{
				$.messager.alert("操作提示",data.msg);
			}
		}	
	});	
}


//查看文件服务详细信息
function detailsInfo(id,mark){
	$('#detainfo').dialog({		
		href:ctx + "/com/frws/configDetailsFrom?id="+id+"&mark="+mark, 
		lock : true,
		title:"文件服务详情",
		width:800,
		inline:false,
		height: 400,
		cache:false,
		buttons:[{
			text:'关闭',
			handler:function(){
				$('#detainfo').dialog('close');
             }
		}]
	});
}

//启动文件服务
function startFrwsService(){
	 $.ajax({
			type:'get',
		    url:ctx+'/com/frws/startFrwsService',
		    dataType: 'json',
		    success: function(data){
         	 if(data.success == true) {
         		 $.messager.alert('提示',data.msg, 'info');	            		 
	        	}else {
	        		 $.messager.alert('提示',data.msg, 'error');
	        	}	
         }			
		});		
}

//检测文件服务
function checkFrwsService(id){
	 $.ajax({
			type:'get',
		    url:ctx+'/com/frws/checkFrwsService',
		    dataType: 'json',
		    data:{id:id},
		    success: function(data){
		    		 if(data.ret == true) {		    			
		         		  $.messager.alert('提示','服务正在运行...', 'info');
			        	}else {
			        	  $.messager.alert('提示', '服务已停止', 'error');
			          }	
		    	 $('#frwsForm').datagrid('reload'); 
         }			
		});		
}
