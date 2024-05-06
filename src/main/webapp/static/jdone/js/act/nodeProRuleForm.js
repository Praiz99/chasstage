function saveActNode(){
	if(!$("#actNodeForm").valid()){
		return false;
	}
	var tableData = $("#actNodeForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/act/nodeRule/saveNodeProRule",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
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
