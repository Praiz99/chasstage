$(function(){
	initDataGrid();
	$("#mainForm").validate();
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/sys/dic/getDicCatListData",
		width:'100%',
		height:'auto', 
		title:"分类列表",
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		toolbar:initToolbar(),
		remoteSort:false, 
		fitColumns:true, 
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'catName',title:'分类名称',align:'center',width:'29%'},
	              {field:'catMark',title:'分类标识',align:'center',width:'29%'},
	              {field:'catDesc',title:'分类描述',align:'center',width:'29%'},
	              {field:'opt',title:'操作',width:'10%',align:'center',  
	                  formatter:function(value,row,index){  
	                   var update = "<a href='#' mce_href='#' onclick='addDicCat(\"" + row.id + "\");'>[修改]</a> ";
	                   return update;  
	                  }  
	                }  
	          ]]
	});
}

function initToolbar(){
    var toolbar = [{
        text:'增加',
        iconCls:'icon-add',
        handler:function(){
        	addDicCat();
        }
    },{
        text:'删除',
        iconCls:'icon-cut',
        handler:function(){
        	var rows = $('#datagrid').datagrid('getSelections');
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
	            if (r) {
	            	doDeleteDicCat(rows);
	            }
	        });
      }
    }];
    return toolbar;
}

//增加字典值信息
function addDicCat(id){
	$('#dd').dialog({
		href:ctx + "/sys/dic/addorEditDicCat?id="+id, 
		lock : true,
		title:"字典分类信息",
		width:800,
		inline:false,
		height: 400,
		cache:false,
		buttons:[{
			text:'保存',
			handler:function(){
				$('#mainForm').form('submit',{
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
	                        $('#dd').dialog('close');        
	                        $('#datagrid').datagrid('reload');   
	                    }
	                }
	            });
             }
		},{
			text:'关闭',
			handler:function(){
				$('#dd').dialog('close');
              }
		}]
	});
}

function doDeleteDicCat(rows){
	var ids = "";
	for ( var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
		if (ids == "") {
			ids = rows[0].id;
		} else {
			ids += "," + rows[i].id;
		}
	}
     $.ajax({
       			url:ctx+'/com/dic/doDeleteDicCat?ids='+ids,
       			async:false,
       			success:function (data){
       				if(data.success){
       					$.messager.alert("系统提示",data.msg);
       				}else{
       					$.messager.alert("系统提示",data.msg);
       				}
       				$('#datagrid').datagrid('reload'); 
       			}
       		});
}