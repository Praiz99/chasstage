$(function() {
	initDataGrid();
});

function initDataGrid() {
	$("#dg")
			.datagrid(
					{
						url : "getTaskBatchinstData",
						width : '100%',
						pagination : true,
						rownumbers : true,
						checkOnSelect : true,
						toolbar : initToolbar(),
						columns : [ [
								{
									field : 'id',
									align : 'center',
									checkbox : true
								},
								{
									field : 'taskName',
									title : '任务名称',
									align : 'center',
									width : '14%'
								},
								{
									field : 'taskMark',
									title : '任务标识',
									align : 'center',
									width : '14%'
								},
								{
									field : 'taskDesc',
									title : '任务描述',
									align : 'center',
									width : '14%'
								},
								{
									field : 'inputType',
									title : '输入类型',
									align : 'center',
									width : '14%',
									formatter : function(value, row, index) {
										var statusStr;
										if (row.inputType == 0) {
											statusStr = "HTTP接口";
										} else if (row.inputType == 1) {
											statusStr = "数据库";
										}
										return statusStr;
									}
								},
								{
									field : 'outputType',
									title : '输出类型',
									align : 'center',
									width : '14%',
									formatter : function(value, row, index) {
										var statusStr;
										if (row.outputType == 0) {
											statusStr = "HTTP接口";
										} else if (row.outputType == 1) {
											statusStr = "数据库";
										}
										return statusStr;
									}
								},
								{
									field : 'createTime',
									title : '创建时间',
									align : 'center',
									width : '14%'
								},
								{
									field : 'opt',
									title : '操作',
									align : 'center',
									width : '15%',
									formatter : function(value, row, index) {
										var str;
										str = "<a href='updateTaskBatchinst?taskId="
												+ row.id
												+ "'>修改</a>&nbsp<a href='addTask?batchTaskMark="
												+ row.taskMark
												+ "&batchTaskType="
												+ row.taskType + "'>运行策略配置</a>"
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
			window.location.href = ctx + "/task/addTaskBatchinst";
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
					url : ctx + "/task/delectTaskBatchinstData",
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
