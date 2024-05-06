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
		url:ctx + "/log/kafka/getLogMsgKafkaSendData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'topic',title:'消息容器',align:'center',width:'16%'},
	              {field:'msgType',title:'消息类型',align:'center',width:'16%'},
	              {field:'sendTime',title:'发送时间',align:'center',width:'16%'},
	              {field:'appName',title:'发送应用',align:'center',width:'16%'},
	              {field:'sendIp',title:'发送服务器',align:'center',width:'16%'},
	              {field:'opt',title:'操作',align:'center',width:'8%',  
            	  formatter : function(value, row, index) {
						var str;
						str = "<a class='oper-btn oper-view' href='logMsgKafkaSendDetail?id="
								+ row.id+ "'>详情</a>" +
							 "&nbsp;&nbsp;<a class='oper-btn' href=\"javascript:sendAgain('"+row.id+"')\">重发</a>";
						return str;
						}
	              }
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'重发',
        handler:function(){
        	resend();
        }
    }];
    
    return toolbar;
}

function resend(){
	$('#openProcessResend')[0].src = ctx + "/log/kafka/logMsgKafkaResendForm";
	$('#processResend').dialog('open');
}

function closeDialog(){
	$('#processResend').dialog('close');
}

function searchFunc(){
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid("load",data);//加载和显示数据
}

function sendAgain(id){
	$.ajax({
		type: "post",
		url: ctx+"/log/kafka/sendOrReceiveAgainById?id="+id+"&type=send",
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