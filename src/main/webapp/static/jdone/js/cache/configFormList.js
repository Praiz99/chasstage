$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#configForm").datagrid({
		url:ctx+"/cache/container/getContainerListData",		
		width:'100%',
		pagination:true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		columns:[[
		      {field:'id',align:'center',checkbox:true},    
		      {field:'cacheName',title:'容器名称',align:'center',width:'15%',
		    	  formatter:function(value,row,index){ 
		    		    var info = "<a href='#' style='text-decoration:underline;' onclick='detailsInfo(\"" + row.id + "\",\""+row.cacheMark+"\");'>"+value+"</a>";	
		    		    return info;
		    	  }      
		      }, 
		      {field:'cacheMark',title:'容器标识',align:'center',width:'15%'},		      
		      {field:'isEnable',title:'是否启用',align:'center',width:'15%',
		    	  formatter:function(value,row,index){
		    		  if(row.isEnable==1){
		    			  return "启用";
		    		  }else{
		    			  return "停用";
		    		  }
		    	  } 
		      }, 
		      {field:'containerType',title:'容器类型',align:'center',width:'15%'}, 
		      {field:'createTime',title:'创建时间',align:'center',width:'15%'},		     
		      {field:'operation',title:'操作',align:'center',width:'25%',
	    	     formatter:function(value,row,index){ 
	    	    	   var info = ""; 		    	    	
	    	    	 if(row.id!=undefined){
	 	    	    	info += "<a href='#' onclick='checkRedisService(\"" + row.id + "\");'>[检测] </a> ";
	 	    	    	info += "<a href='#' onclick='startRedisService(\"" + row.id + "\");'>[启动]</a> ";
	    	    		info += "<a href='#' onclick='addOrUpdateContainer(\"" + row.id + "\");'>[修改]</a> ";
		    	    	if(row.enableClear==1){
		    	    		 info += "<a href='#' onclick='clearCache(\"" + row.cacheMark + "\");'>[清除]</a> "; 
		    	    		}
	    	    	 }else{
	    	    		 info += "<a href='#' onclick='clearCache(\"" + row.cacheMark + "\");'>[清除]</a> "; 
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
 
      },{
    	  text:'清除所有缓存',
    	  iconCls:'icon-reload',
    	  handler:function(){
    		  $.messager.confirm('清除缓存', '您确定要清除所有缓存吗', function(r){
    			  if(r){
    				  clearAllCache();
    			  }
    		  });
          }   	  
      }];
    return toolbar;
  }

//保存修改缓存信息
function addOrUpdateContainer(id){
	window.location.href = ctx+"/cache/container/addorEditConfigForm?id="+id;
}
function submitForm(){   
	var cacheName =   $("input[name='cacheName']").val();
	   var cacheMark =   $("input[name='cacheMark']").val();
	   if(cacheName=="" || cacheMark==""){
		   alert("名称或者标识不能 为空！ ");
		   return ;
	   }
    $('#mainForm').form('submit',{
      	    url:"saveorUpdateContainer",
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
      	    	     window.location.href = ctx+"/cache/container/configForm";
      	        	});
      	    	}else{
      	    		$.messager.alert('提示', data.msg, 'error');
      	    	}
      	    }
      });		
}

//清除分组缓存
function clearCache(cacheMark){
	var mark = cacheMark;
	$.messager.confirm('清除缓存', '您确定要清除该分组的缓存吗？', function(r){
		if (r){	
			$.ajax({
				type:'post',
			    url:ctx+'/cache/container/clearGroupCache',
			    dataType: 'json',
			    data:{mark:mark},
			    success: function(data){
	            	 if(data.success == true) {
	            		 $.messager.alert('提示', data.msg, 'info');	            		 
		        	}else {
		        		 $.messager.alert('提示', data.msg, 'error');
		        	}	
	            }
				
			});

		}
	});
}

//清除所有缓存
function clearAllCache(){
		$.ajax({
			type:'get',
		    url:ctx+'/cache/container/clearAllCache',
		    dataType: 'json',
		    success: function(data){
            	 if(data.success == true) {
            		 $.messager.alert('提示', data.msg, 'info');	            		 
	        	}else {
	        		 $.messager.alert('提示', data.msg, 'error');
	        	}	
            }
			
		});		
}


//删除缓存信息
function  deleteContainer(rows){	
	var rowsid = "";
	for ( var i = 0; i < rows.length; i++) {// 拼接字符串，ID主键之间用逗号隔开
		if(rows[i].id==undefined){
			alert("删除失败，本地缓存无法删除！");
			return ;
		}
		if (rowsid == "") {
			rowsid = rows[0].id;
		} else {
			rowsid += "," + rows[i].id;
		}
	}
     $.ajax({
       			url:'delContainer?rowsid='+rowsid,
       			async:false,
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
  
//查看缓存详细信息
function detailsInfo(id,mark){
	$('#detainfo').dialog({		
		href:ctx + "/cache/container/configDetailsFrom?id="+id+"&mark="+mark, 
		lock : true,
		title:"缓存详情",
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

//启动缓存服务
function startRedisService(){
	 $.ajax({
			type:'get',
		    url:ctx+'/cache/container/startRedisService',
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

//检测缓存服务
function checkRedisService(id){
	 $.ajax({
			type:'get',
		    url:ctx+'/cache/container/checkRedisService',
		    dataType: 'json',
		    data:{id:id},
		    success: function(data){
		    		 if(data.ret == true) {		    			
		         		 $.messager.alert('提示','服务正在运行...', 'info');
			        	}else {
			        		 $.messager.alert('提示', '服务已停止', 'error');
			        	}	
		    		 $('#configForm').datagrid('reload'); 
         }			
		});		
}

function back(){
    window.location.href = ctx+"/cache/container/configForm";
 }
