$(document).ready(function(){
	initDataGrid();
});


function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/log/getLogOperationData",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'appName', title:'系统名称',align:'center',width:'10%'},
	              {field:'moduleName',title:'模块名称',align:'center',width:'10%'},
	              {field:'operTypeName',title:'操作类型',align:'center',width:'10%'},
	              {field:'operName',title:'操作名称',align:'center',width:'10%'},
	              {field:'visitUrl',title:'响应地址',align:'center',width:'22%'},
	              {field:'trgTime',title:'操作时间',align:'center',width:'15%'},
	              {field:'trgObjName',title:'操作用户',align:'center',width:'8%'},
	              {field:'_operate',title:'操作',align:'center',width:'12%',formatter:formatOper}
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'删除',
        handler:function(){
			var rows = $('#datagrid').datagrid('getSelections');
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
	            if (r) {
	            	deleteOrg(rows);
	            }
	        });
        }
    }];
    
    return toolbar;
}

//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" class="oper-btn oper-view" onclick="viewLog(&quot;'+row.id+'&quot;)">查看</a>';  
} 

function viewLog(id){
	location.href = ctx + '/log/logOperationForm?id='+id;
}

function downloadLog(id){
	location.href = ctx + '/log/DownLoadLogDetail?id='+id;
}

//点击查找按钮出发事件
function searchFunc() {
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid('load',data);   
	 
}

//清除查询条件
function ClearQuery() {
    $("#searchForm").find("input").val("");
}
