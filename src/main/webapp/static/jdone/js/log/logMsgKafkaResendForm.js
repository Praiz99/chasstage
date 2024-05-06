function searchFunc(){
	if(!$("#logForm").valid()){
		return false;
	}
	var tableData = $("#logForm").serializeObject();
	showLoading();
	$.ajax({
		type: "post",
		url: ctx+"/log/kafka/logMsgKafkaResend",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			disLoading();
			$.messager.alert('系统提示',data.msg,'info',function(){
				closeDialog();
			});
		}
	});
}

//关闭对话框
function closeDialog(){
	window.parent.closeDialog();
}

//弹出加载层
function showLoading() {  
    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
}

//取消加载层  
function disLoading() {  
    $(".datagrid-mask").remove();  
    $(".datagrid-mask-msg").remove();  
}