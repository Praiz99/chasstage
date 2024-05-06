$(document).ready(function(){
	initDataGrid();
});


function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/interface/log/getLogInterfaceData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'userName', title:'用户名称',align:'center',width:'8%'},
	              {field:'userIdCard',title:'用户身份证号',align:'center',width:'8%'},
	              {field:'userOrgName',title:'所属单位',align:'center',width:'34%'},
	              {field:'reqClientIp',title:'操作IP',align:'center',width:'15%'},
	              {field:'reqTime',title:'操作时间',align:'center',width:'8%'},
	              {field:'interfaceName',title:'接口名称',align:'center',width:'15%'},
	              {field:'reqResult',title:'请求结果',align:'center',width:'5%'},
	              {field:'_operate',title:'操作',align:'center',width:'6%',formatter:formatOper}
	          ]]		
	});
}


//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" class="oper-btn oper-info" onclick="viewLog(&quot;'+row.id+'&quot;)">详情</a>';  
} 

function viewLog(id){
	location.href = ctx + '/interface/log/logInterfaceForm?id='+id;
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

