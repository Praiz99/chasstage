$(document).ready(function(){
	initDataGrid();
});


function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/slowSqlTransaction/log/getSlowTransactionData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'datname', title:'数据库名称',align:'center',width:'8%'},
	              {field:'clientAddr',title:'数据库IP',align:'center',width:'8%'},
	              {field:'query',title:'sql',align:'center',width:'35%'},
	              {field:'queryStart',title:'执行开始时间',align:'center',width:'15%'},
	              {field:'tjxgsj',title:'入库时间',align:'center',width:'8%'},
	              {field:'detectionIp',title:'抓取服务器IP',align:'center',width:'15%'},
	              {field:'_operate',title:'操作',align:'center',width:'6%',formatter:formatOper}
	          ]]		
	});
}


//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" class="oper-btn oper-info" onclick="viewLog(&quot;'+row.id+'&quot;)">详情</a>';  
} 

function viewLog(id){
	location.href = ctx + '/slowSqlTransaction/log/logSlowTransactionForm?id='+id;
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

