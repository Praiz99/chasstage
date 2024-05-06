var modelId = parent.$('#id').val();
var nodeAllList = [];
$(function() {
	$.ajax({
		type : "post",
		url : ctx + "/act/actNode/findAll?modelId=" + modelId,
		dataType : 'json',
		cache : false,
		success : function(data) {
			nodeAllList = data;
			initNodeInfo();
		}
	});
});

function initNodeInfo(){
	$("#datagrid").datagrid({
		url : ctx + "/act/actNode/findPageList?modelId=" + modelId,
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
			field : 'actNodeName',
			title : '节点名称',
			align : 'center',
			width : '15%'
		}, {
			field : 'nodeMark',
			title : '节点标识',
			align : 'center',
			width : '15%'
		}, {
			field : 'nodeType',
			title : '节点类型',
			align : 'center',
			width : '10%',
			formatter:function(value,row,index){
				var nodeType = "";
				if(row.isVreifyNode == 1 && row.isApprovalNode == 1){
					nodeType = "审核"+ "," + "审批";
				}else if(row.isVreifyNode == 1){
					nodeType = "审核";
				}else if(row.isApprovalNode == 1){
					nodeType = "审批";
				}
				return nodeType;
			}
		}, {
			field : 'proOrgTypeName',
			title : '处理单位类型',
			align : 'center',
			width : '10%'
		}, {
			field : 'transNodeId',
			title : '传入节点',
			align : 'center',
			width : '10%',
			formatter: function(value,row,index){
				var resultStr = "";
				var valList = value == null? []:value.split(',');
				for ( var j = 0; j < valList.length; j++) {
					for ( var i = 0; i < nodeAllList.length; i++) {
			    		if(nodeAllList[i].id == valList[j]){
			    			resultStr += nodeAllList[i].actNodeName + ";";
			    		}
			    	}
				}
		    	return resultStr.substring(0, resultStr.length-1);
			}
		}, {
			field : 'createTime',
			title : '添加时间',
			align : 'center',
			width : '15%'
		}, {
			field : '_operate',
			title : '操作',
			align : 'center',
			width : '23%',
			formatter : formatOper
		} ] ]
	});
}

function initToolbar() {
	var toolbar = [ {
		text : '新增节点',
		iconCls : 'icon-add',
		handler : function() {
			window.location.href = ctx + "/act/actNode/actNodeForm?modelId=" + modelId;
		}
	}, '-', {
		text : '删除节点',
		iconCls : 'icon-cut',
		handler : function() {
			var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
				if (r) {
					deleteNode(rows);
				}
			});
		}
	}, '-', {
		text : '预览',
		iconCls : 'icon-search',
		handler : function() {
			window.open(ctx+"/act/pendTask/processPic?modelId=" + modelId,"");
		}
	}];
	
	return toolbar;
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" style="margin-right:10px;" onclick="editNode(' + index + ')">编辑</a><a href="#" style="margin-right:10px;" onclick="openNodeRule(' + index + ')">节点流转配置</a><a href="#" onclick="openNodeProRule(' + index + ')">处理规则配置</a>';
}

function editNode(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/act/actNode/actNodeForm?id=" + row.id + "&modelId=" + modelId;
	}
}

function openNodeRule(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		$('#openNodeRuleList')[0].src = ctx+"/act/nodeRule/nodeRuleList?nodeMark=" + row.nodeMark+ "&modelId=" + modelId+ "&nodeId=" + row.id;
	    $('#nodeRuleDialog').dialog('open');
	}
}

function openNodeProRule(index){
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		$('#nodeProRuleForm')[0].src = ctx+"/act/nodeRule/nodeProRuleForm?id=" + row.id;
	    $('#nodeProRuleFormDialog').dialog('open');
	}
}

function closeDialog(){
	$('#nodeProRuleFormDialog').dialog("close");
	$('#datagrid').datagrid('reload'); 
}

/* 删除样式 */
function deleteNode(rows) {
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
		url : ctx + "/act/actNode/delete?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}

