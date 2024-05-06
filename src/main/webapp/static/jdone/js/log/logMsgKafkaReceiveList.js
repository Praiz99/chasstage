$(function(){
	initDataGrid();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url: ctx + "/log/kafka/getLogMsgKafkaReceiveData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'topic',title:'消息容器',align:'center',width:'8%'},
	              {field:'msgType',title:'消息类型',align:'center',width:'9%'},
	              {field:'recTime',title:'接收时间',align:'center',width:'9%'},
	              {field:'recAppName',title:'接收应用',align:'center',width:'9%'},
	              {field:'recIp',title:'接收服务器IP',align:'center',width:'9%'},
	              {field:'recClientGroup',title:'接收端分组',align:'center',width:'9%'},
	              {field:'recClientId',title:'接收端ID',align:'center',width:'9%'},
	              {field:'sendAppName',title:'发送应用',align:'center',width:'9%'},
	              {field:'sendIp',title:'发送服务器IP',align:'center',width:'9%'},
	              {field:'sendTime',title:'发送时间',align:'center',width:'9%'},
	              {field:'opt',title:'操作',align:'center',width:'8%',  
            	  formatter : function(value, row, index) {
						var str;
						str = "<a class='oper-btn oper-view' href='logMsgKafkaReceiveDetail?id="
								+ row.id
								+ "'>详情</a>" +
							 "&nbsp;&nbsp;<a class='oper-btn' href=\"javascript:receiveAgain('"+row.id+"')\">重收</a>";
						return str;
						}
	              }
	          ]]		
	});
}

function searchFunc(){
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid("load",data);//加载和显示数据
}

function receiveAgain(id){
	$.ajax({
		type: "post",
		url: ctx+"/log/kafka/sendOrReceiveAgainById?id="+id+"&type=receive",
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(data.success){
				var form = $("#searchForm").serializeObject();
				$("#datagrid").datagrid("load",form);//加载和显示数据
			}else{
				$.messager.error("系统提示", data.msg);
			}
		}
	});
}

function ClearQuery(){
	$("#searchForm").form('clear');
}