$(document).ready(function(){
	initDataGrid();
});


function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/log/getLogSqlexcuteData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'appName', title:'系统名称',align:'center',width:'6%'},
	              {field:'moduleName',title:'模块名称',align:'center',width:'6%'},
	              {field:'sqlMark',title:'sql语句标识',align:'center',width:'34%'},
	              {field:'sqlFuncDesc',title:'sql功能描述',align:'center',width:'15%'},
	              {field:'sqlType',title:'SQL类型',align:'center',width:'6%'},
	              {field:'cost',title:'执行时长',align:'center',width:'4%',formatter: 
	            	  function(value,row,index){
		            	  var Str;
	        			  if(value == null){
	        				  Str = "0ms";
	        			  }else{
	        				  Str = value + "ms";
	        			  }
	        			  return Str;  
                  }},
	              {field:'excuteStartTime',title:'执行时间',align:'center',width:'10%'},
	              {field:'excuteResult',title:'执行结果',align:'center',width:'4%',formatter: 
	            	  function(value,row,index){
	            	  	  if (value == '01') return "成功";
	            	  	  else return "失败";
	              }},
	              {field:'excuteServer',title:'执行服务器IP',align:'center',width:'6%'},
	              {field:'_operate',title:'操作',align:'center',width:'6%',formatter:formatOper}
	          ]]		
	});
}


//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" class="oper-btn oper-info" onclick="viewLog(&quot;'+row.id+'&quot;)">详情</a>';  
} 

function downloadLog(id){
	location.href = ctx + '/log/DownLoadLogDetail?id='+id;
}

function viewLog(id){
	location.href = ctx + '/log/logSqlexcuteForm?id='+id;
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
    $("#searchForm").find("input").val("");
    $("select[name='costName']").removeSelected();
	$("select[name='excuteResult']").removeSelected();
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

