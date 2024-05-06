var rowList = [];
var batchFlog = 0;
$(function() {
	initFieldInfo();
});

function initFieldInfo(){
	var id = $('#resId').val();
	var tableMark = $('#tableMark').val();
	$("#fieldGrid").datagrid({
		url : ctx + "/db/resField/findAllList?resId=" + id + "&tableMark=" + tableMark,
		width : '100%',
		onLoadSuccess:function(data){
			rowList = data.rows;
			$("#tableGrid").datagrid("loadData",data.phyRows);
	    },
	    onSelect:function(rowIndex, rowData){
	    	$("#fieldGrid").datagrid("unselectRow",rowIndex);
	    },
		columns : [ [ {
			field : 'fieldMark',
			title : '标识',
			align : 'center',
			width : '32%'
		}, {
			field : 'fieldName',
			title : '名称',
			align : 'center',
			width : '30%'
		}, {
			field : 'fieldType',
			title : '类型',
			align : 'center',
			width : '30%'
		}, {
			field : '_operate',
			title : '操作',
			align : 'center',
			width : '10%'
		} ] ]
	});
	$("#tableGrid").datagrid({
		width : '100%',
		onSelect:function(rowIndex, rowData){
	    	$("#tableGrid").datagrid("unselectRow",rowIndex);
	    },
		columns : [ [ {
			field : '_operate',
			title : '操作',
			align : 'center',
			width : '10%',
			formatter : formatNew
		}, {
			field : 'fieldType',
			title : '类型',
			align : 'center',
			width : '30%'
		}, {
			field : 'fieldName',
			title : '名称',
			align : 'center',
			width : '30%'
		}, {
			field : 'fieldMark',
			title : '标识',
			align : 'center',
			width : '32%'
		} ] ]
	});
}

/* 修改样式 */
function formatNew(val, row, index) {
	if(row.fieldMark == null){
		return '';
	}
	if(rowList[index].fieldMark == null){
		return '<a href="#" onclick="editField(' + index + ')" title="同步到元数据列" class="icon-back">&nbsp;&nbsp;</a>';
	}else if(row.fieldMark == rowList[index].fieldMark && row.fieldName == rowList[index].fieldName && row.fieldType == rowList[index].fieldType){
		return '<a title="元数据列与物理列相等" class="icon-remove">&nbsp;&nbsp;</a>';
	}else{
		return '<a href="#" onclick="editField(' + index + ')" title="覆盖同步到元数据列" class="icon-back">&nbsp;&nbsp;</a>';
	}
}

function editField(index) {
	$('#tableGrid').datagrid('getData')[index];// 选择一行，行索引从0开始。
	var row = $('#tableGrid').datagrid('getData').rows[index];
	row.resId = $('#resId').val();
	row.isPkField = rowList[index].isPkField==null?0:rowList[index].isPkField;
	row.id = rowList[index].id;
	delete row.createTime;
	delete row.updateTime;
	if(batchFlog == 0){
		$.messager.confirm('系统提示', '您确定要同步物理列名为:［' + row.fieldMark + '］到元数据吗?', function(r) {
			if (r){
				saveSynField(row);
			}
		});
	}else if(batchFlog == 1){
		saveSynField(row);
	}
}

function batchSynField() {
	var synList = $(".icon-back");
	batchFlog = 1;
	$.messager.confirm('系统提示', '您确定要同步所有物理列到元数据吗?', function(r) {
		if (r){
			for ( var i = 0; i < synList.length; i++) {
				synList[i].click();
			}
		}
		batchFlog = 0;
	});
}

function saveSynField(row) {
	$.ajax({
		type : "post",
		url : ctx + "/db/resField/save",
		data: row,
		dataType : 'json',
		cache : false,
		success : function(data) {
			initFieldInfo();
		}
	});
}


