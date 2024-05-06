var tableDic, tbUrl;

$(function(){
	initDataGrid();
	tbUrl=ctx+"/sys/dic/tablesName";
	if($("#sourceMark").length>0){
		var sourceMark = $("#sourceMark").val();
		var initTable = $("#tableMarkName").attr("initVal");
		var dicOptions={
				valueField:'tableMark',
				labelField:'tableName',
				searchField:'tableMark,tableName',
				hiddenField:'tableMark',
				itemClick:tbChange,
				pageSize:10
		};
		
		tableDic = $("#tableMarkName").ligerDictionary(dicOptions);
		$.getJSON(tbUrl+"?sourceMark="+sourceMark+"&time="+new Date().getTime(),function(data){
			if(data && data.length>0){
				tableDic.options.data = {Rows:data,Total:data.length};
				if(initTable && initTable!=''){
					tableDic.setValue(initTable);
				}
			}else{
				tableDic.options.data = null;
				tableDic.setDisabled(true);
			}
		});
	}
});
function changeDb(){
	tableDic.clear();
	$.getJSON(tbUrl+"?sourceMark="+$("#sourceMark").val()+"&time="+new Date().getTime(),function(data){
		if(data && data.length>0){
			tableDic.options.data = {Rows:data,Total:data.length};
			tableDic.setDisabled(false);
		}else{
			tableDic.options.data = null;
			tableDic.setDisabled(true);
		}
	});
}

function tbChange(g,item,data){
	$("#dicName").val(data.tableName);
	$("#dicMark").val(data.tableMark);
}


function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/sys/dic/getDicListData?id="+$("#id").val(),
		width:'100%',
		height:'auto', 
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		toolbar:initToolbar(),
		remoteSort:false, 
		fitColumns:true, 
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'code',title:'代码',align:'center',width:'17%'},
	              {field:'name',title:'名称',align:'center',width:'17%'},
	              {field:'fpy',title:'全拼',align:'center',width:'17%'},
	              {field:'spy',title:'简拼',align:'center',width:'17%'},
	              {field:'orderId',title:'排序',align:'center',width:'12%'},
	              {field:'opt',title:'操作',width:'17%',align:'center',  
	                  formatter:function(value,row,index){ 
	                	  var enableEditFlag = $("#enableEdit").val()=="true" ? true : false;
	                	  if($("#type").val()==0 || enableEditFlag===false){
	                		  return ;
	                	  }else{
	                		  var update = "<a href='#' mce_href='#' onclick='addDicCode(\"" + row.id + "\");'>[修改]</a> ";
	  	                  	  return update;  
	                	  }
	                  }  
	                }  
	          ]]
	});
}

function initToolbar(){
	var enableEditFlag = $("#enableEdit").val()=="true" ? true : false;
	var toolbar=null;
	if(enableEditFlag===false){
		return toolbar;
	}
    var toolbar = [{
        text:'增加',
        iconCls:'icon-add',
        handler:function(){
        	addDicCode();
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
	            	doDeleteDicCode(rows);
	            }
	        });
      }
    }];
    if($("#type").val()==0){
    	toolbar.splice(0, 2);
    }
    return toolbar;
}

//增加字典值信息
function addDicCode(id){
	$('#dd').dialog({
		href:ctx + "/sys/dic/addorEditDicCode?id="+id+"&dicId="+$("#id").val()+"&flag="+$("#flag").val(), 
		lock : true,
		title:"字典值信息",
		width:600,
		inline:false,
		height: 300,
		top:150,
		cache:false,
		buttons:[{
			text:'保存',
			handler:function(){
				if(!$("#mainForm").valid()){
					return false;
				}
				var mainData = $("#mainForm").serializeObject();
				$.ajax({
					type: "post",
					url: ctx+"/com/dic/saveorUpdateDicCode",
					data: mainData,
					dataType: 'json',
					cache: false,
					success: function(result) {
	                    if (!result.success){
	                        $.messager.alert("提示",result.msg);
	                    } else {
	                    	$.messager.alert("提示",result.msg);
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

function doDeleteDicCode(rows){
	var ids = "";
	for ( var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
		if (ids == "") {
			ids = rows[0].id;
		} else {
			ids += "," + rows[i].id;
		}
	}
     $.ajax({
       			url:ctx+'/com/dic/doDeleteDicCode?ids='+ids+"&dicId="+$("#id").val(),
       			async:false,
       			type: "post",
       			dataType: 'json',
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
function saveDic(){
	if($("#dicName").val()==""){
		$.messager.alert("系统提示","请填写字典信息后保存！");
		return;
	}
	if(!$("#dicForm").valid()){
		return false;
	}
	var tableData = $("#dicForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx + "/com/dic/saveDic",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(result) {
            $('#refresh', parent.document).click();
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
            	window.location.href = window.location.href;
            }
        }
    });
	
}

//通过名称获取全拼和简拼
function changeName() {
	$('#spy').val('');
	$('#fpy').val('');
	// 设置全拼与简拼
	setQP('name', 'fpy', '不能为空');
	setJP('name', 'spy', '不能为空');
	
	// 小写字母转大写
	var spyStr = $('#spy').val();
	$('#spy').val(spyStr.toUpperCase());
	var fpyStr = $('#fpy').val();
	$('#fpy').val(fpyStr.toUpperCase());
}