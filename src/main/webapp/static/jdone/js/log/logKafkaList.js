$(document).ready(function(){
	initDataGrid();
});


function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/log/kafka/getlogKafkaListDataGrid",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		onLoadSuccess:function(data){
			if (data.total == 0) {
				/*$(this).datagrid('appendRow',
					{appName : '<div style="text-align:center;">没有相关记录！</div>'}).datagrid('mergeCells',
					{	index : 0,
						field : 'appMark',
						colspan : 7
					});*/
			}
	    },
	    onLoadError:function(){
	    	/*$(this).datagrid('appendRow',
					{appName : '<div style="text-align:center;color:red">加载数据出现异常！</div>'}).datagrid('mergeCells',
					{	index : 0,
						field : 'appName',
						colspan : 12
					});*/
	    },
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'appMark', title:'应用标识',align:'center',width:'10%'},
	              {field:'appName',title:'应用名称',align:'center',width:'15%'},
	              {field:'msgType',title:'消息类型',align:'center',width:'10%'},
	              {field:'sendTopic',title:'发送主题',align:'center',width:'20%'},
	              {field:'sendResult',title:'发送结果',align:'center',width:'10%'},
	              {field:'sendTime',title:'发送时间',align:'center',width:'15%'},
	              {field:'sendIp',title:'发起人IP',align:'center',width:'9%'},
	              {field:'_operate',title:'操作',align:'center',width:'10%',formatter:formatOper}
	          ]]		
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

function deleteOrg(rows){
	var ids = "";
	$(rows).each(function (i,o){
		ids += o.id + ",";
	});
	$.ajax({
		url : ctx + "/log/kafka/deletelogKafkaListData",
		data:{ids:ids},
		dataType:'json',
		async:false,
		type:'POST',
		success:function (data){
			if(!data.success){
				$.messager.alert("提示", data.msg);
			}
			searchFunc();
		}
	});
}

//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" onclick="downloadLog(&quot;'+row.id+'&quot;)">下载</a>';  
} 

function downloadLog(id){
	location.href = ctx + '/log/kafka/DownLoadLogDetail?id='+id;
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

