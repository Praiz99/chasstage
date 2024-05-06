$(function() {
	initDataGrid();
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/info/getInfoRelfileData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		toolbar : initToolbar(),
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'资料名称',align:'center',width:'13%'},
	              {field:'mark',title:'资料标识',align:'center',width:'13%'},
	              {field:'state',title:'资料描述',align:'center',width:'13%'},
	              {field:'cat_name',title:'资料分类',align:'center',width:'13%'},
	              {field:'is_publish',title:'是否发布',align:'center',width:'13%',
	  	                  formatter:function(value,row,index){ 
	  	                	var text = "";
		    				if(row.is_publish==0){
		    					text = "未发布";
		    				}else if(row.is_publish==1){
		    					text = "已发布";
		    				}
	                        return text; 
		    			}
	              },
	              {field:'version',title:'默认版本号',align:'center',width:'13%'},
	              {field:'_operate',title:'操作',align:'center',width:'20%',formatter:formatOper}
	          ]]		
	});
}

function formatOper(val,row,index){
	var publishState = '';
	var isModify = '';
	if(row.is_publish == 0){
		isModify = "修改";
	}
	if(row.version_id != null && row.version_id != "" && row.is_publish == 0){
		publishState = "发布";
	}else if(row.version_id != null && row.version_id != "" && row.is_publish == 1){
		publishState = "取消发布";
	}
    return '<a style="text-decoration:underline;" href="javascript:;" onclick="viewLog(&quot;'+row.id+'&quot;)">'+ isModify +'</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
    '<a style="text-decoration:underline;" href="javascript:;" onclick="changeInfoPublish(&quot;'+row.id+'&quot;)">'+ publishState +'</a>&nbsp;&nbsp;&nbsp;&nbsp;'+
    '<a style="text-decoration:underline;" href="javascript:;" onclick="openVersion(&quot;'+row.id+'&quot;)">版本管理</a>';
} 

function initToolbar() {
	var toolbar = [ {
		text : '新增',
		handler : function() {
			window.location.href=ctx + "/info/openInfoRelfileForm";
		}
	} , {
		text : '删除',
		handler : function() {
			var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
				if (r) {
					deleteRegion(rows);
				}
			});
		}
	} ];

	return toolbar;
}

/* 删除样式 */
function deleteRegion(rows) {
	var ids = "";
	for ( var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
		if (ids == "") {
			ids = rows[0].id;
		} else {
			ids += "," + rows[i].id;
		}
	}
	$.ajax({
		type : "post",
		url : ctx + "/info/deleteRelfileById?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}

function viewLog(id){
	window.location.href = ctx + '/info/openInfoRelfileForm?id='+id;
}

function changeInfoPublish(id){
	$.ajax({
		type : "post",
		url : ctx + "/info/modifyInfoPublish?id=" + id,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$.messager.alert('系统提示',data.msg,'info',function(){
				$("#datagrid").datagrid('reload');
			});
		}
	});
}


//点击查找按钮触发事件
function searchFunc() {
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid('load',data);   
}
jQuery.fn.removeSelected = function() {
    this.val("");
};

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('clear');
}

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//打开版本管理
function openVersion(id){
	$('#openVersionList')[0].src = ctx + "/info/infoVersionList?id="+id;
	$('#versionDialog').dialog('open');
}

