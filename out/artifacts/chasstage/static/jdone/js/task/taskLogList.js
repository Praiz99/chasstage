$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#dg").datagrid({
		url:ctx + "/task/getTaskLogData",
		width:'100%',
		pagination:true,
		rownumbers : true,
		checkOnSelect:true,
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'taskName',title:'任务名称',align:'center',width:'16%'},
	              {field:'exeStartTime',title:'执行开始时间',align:'center',width:'16%'},
	              {field:'exeCmpltTime',title:'执行结束时间',align:'center',width:'16%'},
	              {field:'isExeOk',title:'是否执行成功',align:'center',width:'16%',
	            	  formatter:function(value,row,index){
	            		  var statusStr;
	            		  if(row.isExeOk == 0){
	            			  statusStr = "失败";
	            		  }else if(row.isExeOk == 1){
	            			  statusStr = "正常";
	            		  }
	            		  return statusStr;
	            	  }},
	              {field:'exeServerIp',title:'执行服务器IP',align:'center',width:'16%'},
	              {field:'opt',title:'操作',align:'center',width:'19%',
	            		  formatter:function(value,row,index){ 
	  	                	var str;
	  	                	str ="<a href='taskLogDetalis?taskId="+row.id+"'>详情</a>"
	  	                	return str; 
	  	                	}
	              }
	          ]]		
	});
}

function searchFunc(idList){
	var data = $("#searchForm").serializeObject();
	data.id = idList;
	$("#dg").datagrid("load",data);//加载和显示数据
}
/*清楚查询条件*/
function ClearQuery(){
	$("#searchForm").find("input").val("");
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