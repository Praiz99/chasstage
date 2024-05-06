var ctx;
var bizMark;
var mark;
$(function(){
	$("#toptoolbar").datagrid({
		width:'100%',
		toolbar:initToolbar()
	});
	bindTree();
});

function initToolbar(){
    toolbar = [{
        text:'增加分组',
        iconCls:'icon-add',
        handler:function(){
        	addParamGroup();
        }
    },{
        text:'修改分组',
        iconCls:'icon-edit',
        handler:function(){
        	doUpdateGroup();
        }
    },{
        text:'删除分组',
        iconCls:'icon-cut',
        handler:function(){
        	deleteGroup();
        }
     }];
    return toolbar;
}
//增加参数分组
function addParamGroup(){
	$('#tt').dialog({    
		title:"参数分组信息",
		width:1000,
		height: 600,
		model:true,
	    href:ctx + "/sys/group/addorUpgroupForm?bizMark="+bizMark,    
	    buttons:[{
			text:'保存',
			handler:function(){
				 $('#addorUpgroupForm').form('submit',{
					 url:ctx + "/sys/group/savegroup",
		                onSubmit: function(){
		                	if(!$("#addorUpgroupForm").valid()){
		                		return false;
		                	}
		                	//$("#addorUpgroupForm").validate();
		                },
		                success: function(result){
		                    var result = eval('('+result+')');
		                    if (!result.success){
		                        $.messager.show({
		                            title: '提示',
		                            msg: result.msg
		                        });
		                    } else {
		                    	$.messager.show({
		                            title: '提示',
		                            msg: result.msg
		                        });
		                        $('#tt').dialog('close');        
		                        $('#tree1').tree("reload"); 
		                    }
		                }
		            });
             }
		},{
			text:'关闭',
			handler:function(){
				$('#tt').dialog('close');
              }
		}]
	});    
}

//修改分组
function doUpdateGroup(){
	var selectedNode = $('#tree1').tree('getSelected');
	if(!selectedNode){
		$.messager.alert("系统提示","请选择要修改的分组！！");
		return;
	}else{
	$('#tt').dialog({    
		title:"参数分组信息",
		width:1000,
		height: 600,
		model:true,
	    href:ctx + "/sys/group/addorUpgroupForm?id="+selectedNode.id,    
	    buttons:[{
			text:'保存',
			handler:function(){
				 $('#addorUpgroupForm').form('submit',{
					 url:ctx + "/sys/group/savegroup",
		                onSubmit: function(){
		                	if(!$("#addorUpgroupForm").valid()){
		                		return false;
		                	}
		                },
		                success: function(result){
		                    var result = eval('('+result+')');
		                    if (!result.success){
		                        $.messager.show({
		                            title: '提示',
		                            msg: result.msg
		                        });
		                    } else {
		                    	$.messager.show({
		                            title: '提示',
		                            msg: result.msg
		                        });
		                        $('#tt').dialog('close');        
		                        $('#tree1').tree("reload"); 
		                    }
		                }
		            });
             }
		},{
			text:'关闭',
			handler:function(){
				$('#tt').dialog('close');
              }
		}]
	});   } 
}
//删除分组
function deleteGroup(){
	var selectedNode = $('#tree1').tree('getSelected');
	if(!selectedNode){
		$.messager.alert("系统提示","请选择要删除的分组！！");
		return;
	}else{
           $.messager.confirm('系统提示', '您确定要删除该记录', function(r) {
            		if(r){
            				$.ajax({
            					url:ctx+'/api/outer/findouter?groupId='+selectedNode.id+'&mark='+mark,
            					async:false,
            					success:function (data){
            						if(data.success){
            							$.messager.alert("系统提示",data.msg);
            							$('#tree1').tree("reload");
            						}else{
            							$.ajax({
            	            				url:ctx+'/sys/group/deletegroup?id='+selectedNode.id,
            	            				async:false,
            	            				success:function (data){
            	            					if(data.success){
            	            						$.messager.alert("系统提示",data.msg);
            	            						$('#tree1').tree("reload");
            	            					}else{
            	            						$.messager.alert("系统提示",data.msg);
            	            					}
            	            				}
            	            			});
            						}
            					}
            				});
            		} 
            		 });
        }
	}
