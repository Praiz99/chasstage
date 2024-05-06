$(function() {
	dicInit(true);
	initDataGrid();
});

function initDataGrid() {
	$("#datagrid").datagrid({
		url : ctx + "/act/pendTask/getInstPendTaskData",
		width : '100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [ {
			field : 'id',
			align : 'center',
			checkbox : true
		},{
			field : 'biz_typeName',
			title : '业务类型',
			align : 'center',
			width : '10%'
		}, {
			field : 'biz_id',
			title : '业务ID',
			align : 'center',
			width : '10%'
		}, {
			field : 'start_org_sys_codeName',
			title : '发起单位',
			align : 'center',
			width : '14%'
		}, {
			field : 'start_user_idName',
			title : '发起人',
			align : 'center',
			width : '10%'
		}, {
			field : 'start_time',
			title : '发起时间',
			align : 'center',
			width : '8%'
		}, {
			field : 'node_idName',
			title : '当前节点',
			align : 'center',
			width : '8%'
		}, {
			field : 'node_pro_org_sys_codeName',
			title : '当前处理单位',
			align : 'center',
			width : '14%'
		}, {
			field : 'nodeProUserId',
			title : '待处理人',
			align : 'center',
			width : '8%',
            formatter:function(value,row,index){ 
  			  var str = "<a style='text-decoration:underline;' href='javascript:;' onclick='viewDclr(\"" + index + "\")'  >待处理人</a>";
                return str; 
            }
		}, {
			field : '_operate',
			title : '编辑',
			align : 'center',
			width : '8%',
			formatter : formatOper
		} ] ]
	});
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" onclick="viewTask(' + index + ')">详情</a>  <a href="#" onclick="viewFlow(' + index + ')">流程轨迹</a>';
}


//点击查找按钮出发事件
function searchFunc() {
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid('load',data);   
}
jQuery.fn.removeSelected = function() {
    this.val("");
};

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('clear');
}

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function viewFlow(index){
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		var url = ctx + "/act/pendTask/processHistory?bizId="+row.biz_id+"&bizType="+row.biz_type;
		var content = '<iframe id="page_setLcgj" name="pageLcgjDialog" src="' + url + '" width="100%" height="88%" frameborder="0" scrolling="no"></iframe>';  
		$('#lcgj').dialog({
			content: content,  
			width: '740',  
			height: '400',  
			modal: true,  
			title: "流程轨迹",
			buttons:[{
				text:'关闭',
				handler:function(){
					$('#lcgj').dialog('close');
	            }
			}]
		});
	}
}

function viewDclr(index){
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		var url = ctx + "/act/pendTask/rmdMsgList?bizId="+row.biz_id+"&bizType="+row.biz_type;
		var content = '<iframe id="page_setLcgj" name="pageLcgjDialog" src="' + url + '" width="100%" height="88%" frameborder="0" scrolling="no"></iframe>';  
		$('#dclr').dialog({
			content: content,  
			width: '840',  
			height: '500',  
			modal: true,  
			title: "待处理人",
			buttons:[{
				text:'关闭',
				handler:function(){
					$('#dclr').dialog('close');
	            }
			}]
		});
	}
}


function viewTask(index){
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		location.href = ctx + '/act/pendTask/instPendTaskForm?id='+row.id;
	}
}