$(document).ready(function(){
	initDataGrid();
});


function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/log/getLogExceptionData",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		striped:true,
		toolbar:initToolbar(),
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'appName', title:'系统名称',align:'center',width:'10%'},
	              {field:'moduleName',title:'模块名称',align:'center',width:'10%'},
	              {field:'trgObjName',title:'执行对象',align:'center',width:'10%'},
	              {field:'trgObjMark',title:'对象标识',align:'center',width:'10%'},
	              {field:'codeMark',title:'异常代码标记',align:'center',width:'10%'},
	              {field:'errorType',title:'异常类型',align:'center',width:'15%'},
	              {field:'trgObjDwmc',title:'执行单位',align:'center',width:'15%'},
	              {field:'trgTime',title:'时间',align:'center',width:'10%'},
	              {field:'_operate',title:'操作',align:'center',width:'10%',formatter:formatOper}
	          ]],
		onLoadSuccess:function(data){
			if (data.total == 0) {
				$(this).datagrid('appendRow',
					{appName : '<div style="text-align:center;">没有相关记录！</div>'}).datagrid('mergeCells',
					{	index : 0,
						field : 'appName',
						colspan : 9
					});
			}
	    },
	    onLoadError:function(){
	    	/*$(this).datagrid('appendRow',
					{appName : '<div style="text-align:center;color:red">加载数据出现异常！</div>'}).datagrid('mergeCells',
					{	index : 0,
						field : 'appName',
						colspan : 9
					});*/
	    }		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'删除',
        iconCls:'icon-cut',
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
    return '<a href="#" onclick="downloadLog(&quot;'+row.id+'&quot;)">下载</a>';  
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

