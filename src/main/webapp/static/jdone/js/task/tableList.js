$(function() {
	initDataGrid();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
});

function initDataGrid() {
	var sourceId = $("#sourceId").val();
	$("#datagrid").datagrid({
		url : ctx + "/db/resTable/findRefTable?sourceId=" + sourceId,
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [ {
			field : 'tableMark',
			title : '表名',
			align : 'center',
			width : '50%'
		},{
			field : '_operate',
			title : '编辑',
			align : 'center',
			width : '48%',
			formatter : formatOper
		} ] ]
	});
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" onclick="editSource(' + index + ')">选择</a>';
}

function editSource(index) {
	var type = $("#type").val();
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		parent.$('#refTablesDialog').dialog('close');
		parent.setRefTable(row,type);
	}
}

/*按区域名称查找*/
function searchFunc(idList){
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid("load",data);//加载和显示数据
}
/*清理所有数据*/
function ClearQuery(){
	$("#searchForm").form('clear');
}

