$(function() {
	initDataGrid();
	//模糊查询enter事件
	$('.keydownSearch').bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#btnSubmit").click();
	    }
	});
});

function initDataGrid() {
	$("#datagrid").datagrid({
		url : ctx + "/log/conf/operation/findPageList",
		width : '100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		toolbar : initToolbar(),
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [ {
			field : 'id',
			align : 'center',
			checkbox : true
		}, {
			field : 'appName',
			title : '应用名称',
			align : 'center',
			width : '12%'
		}, {
			field : 'moduleName',
			title : '模块名称',
			align : 'center',
			width : '12%'
		}, {
			field : 'operTypeName',
			title : '操作分类',
			align : 'center',
			width : '12%'
		}, {
			field : 'servletPath',
			title : '操作路径',
			align : 'center',
			width : '20%'
		}, {
			field : 'operName',
			title : '页面\操作名称',
			align : 'center',
			width : '20%'
		}, {
			field : 'pageOperMark',
			title : '页面\操作标识',
			align : 'center',
			width : '10%'
		}, {
			field : '_operate',
			title : '编辑',
			align : 'center',
			width : '11%',
			formatter : formatOper
		} ] ]
	});
}

function initToolbar() {
	var toolbar = [ {
		text : '新增',
		handler : function() {
			window.location.href = ctx + "/log/conf/operation/logConfForm";
		}
	}, '-', {
		text : '删除',
		handler : function() {
			var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
				if (r) {
					deleteLogConf(rows);
				}
			});
		}
	} ];

	return toolbar;
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" class="oper-btn oper-edit" onclick="editLogConf(' + index + ')">修改</a>';
}

function editLogConf(index) {
	$('#datagrid').datagrid('unselectAll');
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/log/conf/operation/logConfForm?id="+row.id;
	}
}

/* 删除样式 */
function deleteLogConf(rows) {
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
		url : ctx + "/log/conf/operation/delete?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}

/*按区域名称查找*/
function searchFunc(){
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid("load",data);//加载和显示数据
}

/*清理所有数据*/
function ClearQuery(){
	$("#searchForm").form('clear');
}

