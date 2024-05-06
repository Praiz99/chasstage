var infoId;
$(function() {
	infoId = $("#infoId").val();
	initDataGrid();
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/info/getInfoVersionData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		toolbar : initToolbar(),
		queryParams:{infoId:infoId}, 
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'version', title:'版本号',align:'center',width:'25%'},
	              {field:'versionState',title:'版本描述',align:'center',width:'25%'},
	              {field:'isDefault',title:'是否默认版本',align:'center',width:'24%',
	            	  formatter:function(value,row,index){ 
	  	                	var text = "";
		    				if(row.isDefault==0){
		    					text = "否";
		    				}else if(row.isDefault==1){
		    					text = "是";
		    				}
	                        return text; 
		    			}
	              },
	              {field:'_operate',title:'操作',align:'center',width:'25%',formatter:formatOper}
	          ]]		
	});
}

function formatOper(val,row,index){
	var defaultState = '';
	if(row.isDefault == 0){
		defaultState = "设置默认";
	}else if(row.isDefault == 1){
		defaultState = "取消默认";
	}
    return '<a style="text-decoration:underline;" href="javascript:;" onclick="changeInfoDefault(&quot;'+row.id+'&quot;)">'+ defaultState +'</a>';
} 

function initToolbar() {
	var toolbar = [ {
		text : '新增',
		handler : function() {
			window.location.href=ctx + "/info/openInfoVersionForm?infoId="+infoId;
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
		url : ctx + "/info/deleteVersionById?id=" + ids,
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

function changeInfoDefault(id){
	$.ajax({
		type : "post",
		url : ctx + "/info/modifyInfoDefault?id=" + id,
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

