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
		url : ctx + "/data/modifySpb/findPageList",
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
			field : 'sqrXm',
			title : '申请人姓名',
			align : 'center',
			width : '10%'
		}, {
			field : 'sqrSfzh',
			title : '申请人身份证',
			align : 'center',
			width : '15%',
			formatter:function(value,row,index){ 
            	var str = "<a style='color:#34a8ff;text-decoration:underline;cursor:pointer;' " +
            				"onclick=\"editDataSpb('"+index+"');\">" + value + "</a>";
            	return str; 
            }
		}, {
			field : 'actKey',
			title : '流程key',
			align : 'center',
			width : '15%'
		}, {
			field : 'modifyType',
			title : '变更类型',
			align : 'center',
			width : '10%'
		}, {
			field : 'modifyBizType',
			title : '变更业务类型',
			align : 'center',
			width : '8%'
		}, {
			field : 'modifyBizTable',
			title : '变更业务表名',
			align : 'center',
			width : '12%'
		}, {
			field : 'tjxgsj',
			title : '添加修改时间',
			align : 'center',
			width : '15%'
		}, {
			field : '_operate',
			title : '编辑',
			align : 'center',
			width : '12%',
			formatter : function(val, row, index) {
    			var h="<a href='#' class='oper-btn oper-view' onclick=\"viewFlow('"+row.id+"','"+row.modifyBizType+"');\">查看流程</a>";
				return h;
			}} ] ]
	});
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" class="oper-btn oper-view" onclick="editDataSpb(' + index + ')">查看</a>';
}

function editDataSpb(index) {
	$('#datagrid').datagrid('unselectAll');
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/data/modifySpb/dataModifySpbForm?id="+row.id;
	}
}

//查看流程
function viewFlow(bizId,modifyType){
	var biztype = "tysjbg_" + modifyType;
	$('#openProcessList')[0].src = ctx + "/act/pendTask/processHistoryNew?bizId="+bizId+"&bizType="+biztype;
	$('#processDialog').dialog('open');
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

