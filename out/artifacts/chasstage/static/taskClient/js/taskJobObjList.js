$(function() {
	initDataGrid();
});

function initDataGrid() {
	$("#dg")
			.datagrid(
					{
						url : ctx + "/taskClient/getTaskJobObjData",
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
									field : 'name',
									title : '任务名称',
									align : 'center',
									width : '16%'
								},
								{
									field : 'mark',
									title : '任务标识',
									align : 'center',
									width : '16%'
								},
								{
									field : 'categoryMark',
									title : '分组标识',
									align : 'center',
									width : '16%'
								},
								{
									field : 'cronExp',
									title : '运行策略',
									align : 'center',
									width : '16%'
								},
								{
									field : 'status',
									title : '任务状态',
									align : 'center',
									width : '16%',
									formatter : function(value, row, index) {
										var statusStr;
										if (row.status == 0) {
											statusStr = "已停用";
										} else if (row.status == 1) {
											statusStr = "已启用";
										}
										return statusStr;
									}
								},
								{
									field : 'opt',
									title : '操作',
									align : 'center',
									width : '19%',
									formatter : function(value, row, index) {
										var str;
										if (row.status == 0) {
											str = "<a href='taskJobDetalis?categoryMark="
													+ row.categoryMark
													+ "&jobMark="
													+ row.mark
													+ "'>详情</a>&nbsp<a href='#' onclick=\"startTask('"
													+ row.categoryMark
													+ "','"
													+ row.mark + "')\">启动</a>";
										} else {
											str = "<a href='taskJobDetalis?categoryMark="
													+ row.categoryMark
													+ "&jobMark="
													+ row.mark
													+ "'>详情</a>&nbsp<a href='#' onclick=\"stopTask('"
													+ row.categoryMark
													+ "','"
													+ row.mark + "')\">停止</a>";

										}
										return str;
									}
								} ] ]
					});
}

function initToolbar() {
	var toolbar = [{
		text : '返回',
		iconCls : 'icon-back',
		handler : function() {
			window.location.href = window.history.go(-1);
		}
	} ];
	return toolbar;
}

// 启动任务
function startTask(categoryMark, jobMark) {
	$.ajax({
		type : "POST",
		data : {
			"categoryMark" : categoryMark,
			"jobMark" : jobMark
		},
		dataType : "JSON",
		async : false,
		url : ctx + "/task/job/handle/start",
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info', function() {
				window.location.reload();
			});

		}

	});
}
// 停止任务
function stopTask(categoryMark, jobMark) {
	$.ajax({
		type : "POST",
		data : {
			"categoryMark" : categoryMark,
			"jobMark" : jobMark
		},
		dataType : "JSON",
		async : false,
		url : ctx + "/task/job/handle/stop",
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info', function() {
				window.location.reload();
			});

		}

	});
}
