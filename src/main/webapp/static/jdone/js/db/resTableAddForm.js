var editRow = undefined; //定义全局变量：当前编辑的行

$(document).ready(function() {
	$("#resTableForm").validate();
	var id = $("#id").val();
	initTableInfo(id);
	initFieldInfo(id);
});

function initTableInfo(id){
	$.ajax({
		type : "post",
		url : ctx + "/db/resGroup/findAll",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#groupList").append($("<option/>").text(data[i].groupName).attr("value",data[i].id));
			}
		}
	});
	$.ajax({
		type : "post",
		url : ctx + "/db/source/findAll",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#dbSourceList").append($("<option/>").text(data[i].sourceName).attr("value",data[i].id));
			}
			if (id != null && id != "") {
				$.ajax({
					type: "post",
					url: ctx+"/db/resTable/findById?id="+id,
					dataType: 'json',
					cache: false,
					success: function(data) {
						$('#resTableForm').form('load', data);
					}
				});
			}
		}
	});
	
}

function initFieldInfo(id){
	$("#fieldGrid").datagrid({
		url : ctx + "/db/resField/findPageList?resId=" + id,
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
        striped: true, //行背景交换
		nowap: true, //列内容多时自动折至第二行
		toolbar : initToolbar(),
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [ {
			field : 'id',
			align : 'center',
			checkbox : true
		}, {
			field : 'fieldMark',
			title : '名称',
			align : 'center',
			width : '25%',
			editor: { type: 'validatebox', options: { required: true}}
		}, {
			field : 'fieldName',
			title : '中文名称',
			align : 'center',
			width : '25%',
			editor: { type: 'validatebox', options: { required: true}}
		}, {
			field : 'fieldType',
			title : '类型',
			align : 'center',
			width : '20%',
			editor: { type: 'validatebox', options: { required: true}}
		}, {
			field : 'fieldLength',
			title : '长度',
			align : 'center',
			width : '15%',
			editor: { type: 'validatebox', options: { required: true}}
		}, {
			field : '_operate',
			title : '编辑',
			align : 'center',
			width : '13%',
			formatter : formatOper
		} ] ]
	});
}

function initToolbar() {
	var toolbar = [ {
		text : '新增字段',
		iconCls : 'icon-add',
		handler : function() {
        	//添加时如果没有正在编辑的行，则在datagrid的最后一行插入一行
        	$('#fieldGrid').datagrid('appendRow', {  });
        	var editIndex = $('#fieldGrid').datagrid('getRows').length - 1;
        	//将新插入的那一行开户编辑状态
        	$('#fieldGrid').datagrid('beginEdit', editIndex);
        	//给当前编辑的行赋值
        	editRow = editIndex;
		}
	}, '-', {
		text : '删除字段',
		iconCls : 'icon-cut',
		handler : function() {
			/*var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
				if (r) {
					deleteSource(rows);
				}
			});*/
		}
	} ];

	return toolbar;
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" onclick="editSource(' + index + ')">编辑</a>';
}

function editSource(index) {
	$('#fieldGrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#fieldGrid').datagrid('getSelected');
	if (row) {
		//window.location.href=ctx + "/db/resTable/resTableForm?id="+row.id;
	}
}


