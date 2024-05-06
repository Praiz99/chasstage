var id = parent.$('#id').val();
var tableMark = parent.$('#tableMark').val();
$(function() {
	initFieldInfo();
});

function initFieldInfo(){
	$("#fieldGrid").datagrid({
		url : ctx + "/db/resField/findPageList?resId=" + id,
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
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
			width : '25%'
		}, {
			field : 'fieldName',
			title : '中文名称',
			align : 'center',
			width : '25%'
		}, {
			field : 'fieldType',
			title : '类型',
			align : 'center',
			width : '15%'
		}, {
			field : 'fieldLength',
			title : '长度',
			align : 'center',
			width : '15%'
		}, {
			field : 'isPkField',
			title : '主键',
			align : 'center',
			width: '5%',
			formatter:function(value,row,index){
				if(row.isPkField == true){
					return "<input type='checkbox' value='1' offval='0' checked onclick='checkPkField(" + index + ",this)'>";
				}
				return "<input type='checkbox' value='1' offval='0' onclick='checkPkField(" + index + ",this)'>";
			}
		}, {
			field : '_operate',
			title : '操作',
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
			window.location.href = ctx + "/db/resField/resFieldForm?resId=" + id;
		}
	}, '-', {
		text : '删除字段',
		iconCls : 'icon-cut',
		handler : function() {
			var rows = $('#fieldGrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
				if (r) {
					deleteField(rows);
				}
			});
		}
	}, '-', {
		text : '同步字段',
		iconCls : 'icon-reload',
		handler : function() {
			window.open(ctx + "/db/resField/resFieldSyn?resId=" + id + "&tableMark=" + tableMark);
		}
	} ];
	if(tableMark == null){
		toolbar.splice(3,2);
	}
	
	return toolbar;
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" onclick="editField(' + index + ')">编辑</a>';
}

function editField(index) {
	$('#fieldGrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#fieldGrid').datagrid('getSelected');
	if (row) {
		window.location.href=ctx + "/db/resField/resFieldForm?id="+row.id;
	}
}

/* 删除样式 */
function deleteField(rows) {
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
		url : ctx + "/db/resField/delete?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#fieldGrid").datagrid('reload');
		}
	});
}

/* 校验主键*/
function checkPkField(index, data) {
	$('#fieldGrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#fieldGrid').datagrid('getSelected');
	if (row) {
		$.ajax({
			type : "post",
			url : ctx + "/db/resField/findPkFieldList?resId=" + id + "&isPkField=1",
			dataType : 'json',
			cache : false,
			success : function(list) {
				var flog = 0;
				for ( var m = 0; m < list.rows.length; m++) {
					if(list.rows[m].isPkField == '1' && list.rows[m].id != row.id){
						flog++;
					}
				}
				if(flog > 0) {
					$.messager.alert("系统提示", "您已经设置了一个主键，最多只允许有一个主键！请重新选择。", "error");
					$(data).prop("checked",false);
					return false;
				}else if(flog == 0 && data.checked == true){
					updatePkField(row, 1);
				}else if(flog == 0 && data.checked == false){
					updatePkField(row, 0);
				}
			}
		});
	}
}

/* 校验主键*/
function updatePkField(row, isPkField) {
	var data = {};
	data.id = row.id;
	data.isPkField = isPkField;
	$.ajax({
		type : "post",
		url : ctx + "/db/resField/updatePkField",
		data: data,
		dataType : 'json',
		cache : false,
		success : function(data) {
		}
	});
}


