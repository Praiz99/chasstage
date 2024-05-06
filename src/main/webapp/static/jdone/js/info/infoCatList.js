$(function() {
	initDataGrid();
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/info/getInfoCatData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		toolbar : initToolbar(),
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'分类名称',align:'center',width:'25%'},
	              {field:'mark',title:'分类标识',align:'center',width:'25%'},
	              {field:'orderNo',title:'排序序号',align:'center',width:'24%'},
	              {field:'_operate',title:'操作',align:'center',width:'25%',formatter:formatOper}
	          ]]		
	});
}

function formatOper(val,row,index){
    return '<a style="text-decoration:underline;" href="javascript:;" onclick="updateInfoCat(&quot;'+row.id+'&quot;)">修改</a>';
} 

function initToolbar() {
	var toolbar = [ {
		text : '新增',
		handler : function() {
			window.location.href=ctx + "/info/openInfoCatForm";
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
		url : ctx + "/info/deleteInfoCatById?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}

function updateInfoCat(id){
	window.location.href = ctx + '/info/openInfoCatForm?id='+id;
}
