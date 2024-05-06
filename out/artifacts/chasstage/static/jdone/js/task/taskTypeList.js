$(function() {
	initDataGrid();
});

function initDataGrid() {
	$("#dg").datagrid({
		url : ctx + "/task/getTaskTypePageData",
		width : '100%',
		pagination : true,
		rownumbers : true,
		checkOnSelect : true,
		toolbar : initToolbar(),
		columns : [ [ {
			field : 'id',
			align : 'center',
			checkbox : true
		}, {
			field : 'typeName',
			title : '分类名称',
			align : 'center',
			width : '20%'
		}, {
			field : 'typeMark',
			title : '分类标识',
			align : 'center',
			width : '20%'
		}, {
			field : 'deployIdName',
			title : '部署名称',
			align : 'center',
			width : '20%'
		}, {
			field : 'createTime',
			title : '创建时间',
			align : 'center',
			width : '20%'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '19%',
			formatter : function(value, row, index) {
				var str;
				str = "<a href='updateTaskType?id=" + row.id + "'>修改</a>";
				return str;
			}
		} ] ]
	});
}

function initToolbar() {
	var toolbar = [ {
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			window.location.href = ctx + "/task/addTaskType";
		}
	}, {
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
			var rows = $('#dg').datagrid('getSelections');
			if (rows.length == 0) {
				$.messager.alert('提示', '请选择你要删除的数据！', 'info');
			} else {
				var ids = "";
				for (var i = 0; i < rows.length; i++) {
					if (ids == "") {
						ids = rows[0].id;
					} else {
						ids += "," + rows[i].id;
					}
				}
				// var ids = JSON.parse(ids);
				$.ajax({
					type : "POST",
					data : {
						"ids" : ids
					},
					dataType : "JSON",
					async : false,
					url : ctx + "/task/delectTaskTypeData",
					success : function(data) {
						$.messager.alert('提示', data.msg, 'info', function() {
							window.location.reload();
						});

					}

				});
			}

		}
	} ];

	return toolbar;
}
