var nodeMark = "";
var modelId = "";
var nodeId = "";
var nodeRuleList = [];
$(function() {
	nodeMark = $('#nodeMark').val();
	modelId = $('#modelId').val();
	nodeId = $('#nodeId').val();
	initRuleInfo();
});


function initRuleInfo(){
	$("#datagrid").datagrid({
		url : ctx + "/act/nodeRule/findPageList?nodeMark=" + nodeMark+"&modelId=" + modelId,
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
			field : 'ruleName',
			title : '规则名称',
			align : 'center',
			width : '20%'
		}, {
			field : 'ruleMark',
			title : '规则标识',
			align : 'center',
			width : '15%'
		}, {
			field : 'ruleTypeName',
			title : '规则类型',
			align : 'center',
			width : '15%',
			formatter:function(value,row,index){
				return value;
			}
		}, {
			field : 'nodeName',
			title : '节点名称',
			align : 'center',
			width : '20%'
		}, {
			field : 'createTime',
			title : '添加时间',
			align : 'center',
			width : '15%'
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
		text : '新增规则',
		iconCls : 'icon-add',
		handler : function() {
			window.location.href = ctx + "/act/nodeRule/nodeRuleForm?nodeMark=" + nodeMark+ 
			"&modelId=" + modelId+ "&nodeId=" + nodeId;
		}
	}, '-', {
		text : '删除规则',
		iconCls : 'icon-cut',
		handler : function() {
			var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
				if (r) {
					deleteNodeRule(rows);
				}
			});
		}
	}];
	
	return toolbar;
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" onclick="editNodeRule(' + index + ')">编辑</a>';
}

function editNodeRule(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/act/nodeRule/nodeRuleForm?id=" + row.id + "&nodeMark=" + nodeMark+ "&nodeId=" + nodeId;
	}
}

/* 删除样式 */
function deleteNodeRule(rows) {
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
		url : ctx + "/act/nodeRule/delete?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}

