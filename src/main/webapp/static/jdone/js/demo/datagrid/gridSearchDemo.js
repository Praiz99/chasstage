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
	$("#datagrid").datagrid({
		url : ctx + "/act/actGroup/findPageList",
		width : '100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [ {
			field : 'id',
			align : 'center',
			checkbox : true
		}, {
			field : 'groupName',
			title : '分组名称',
			align : 'center',
			width : '15%'
		}, {
			field : 'groupMark',
			title : '分组标识',
			align : 'center',
			width : '15%'
		}, {
			field : 'groupDesc',
			title : '分组描述',
			align : 'center',
			width : '25%'
		}, {
			field : 'createTime',
			title : '添加时间',
			align : 'center',
			width : '20%'
		}, {
			field : '_operate',
			title : '编辑',
			align : 'center',
			width : '22%',
			formatter : formatOper
		} ] ]
	});
}

/*function initToolbar() {
	var toolbar = [ {
		text : '新增',
		handler : function() {
			window.location.href = ctx + "/act/actGroup/actGroupForm";
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
					deleteGroup(rows);
				}
			});
		}
	} ];

	return toolbar;
}*/

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" class="oper-btn oper-edit" onclick="editGroup(' + index + ')">修改</a><a href="#" class="oper-btn oper-view" onclick="editGroup(' + index + ')">查看</a><a href="#" class="oper-btn oper-info" onclick="editGroup(' + index + ')">详情</a>';
}

function editGroup(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/act/actGroup/actGroupForm?id="+row.id;
	}
}

/* 删除样式 */
function deleteGroup(rows) {
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
		url : ctx + "/act/actGroup/delete?id=" + ids,
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

