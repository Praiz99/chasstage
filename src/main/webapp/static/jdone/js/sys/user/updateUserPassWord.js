$(document).ready(function() {
	$("#userForm").validate();
});
function saveUser(){
	if(!$("#userForm").valid()){
		return false;
	}
	var tableData = $("#userForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/sys/user/updateUser",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				closeDialog();
			});
		}
	});
}
//关闭对话框
function closeDialog(){
	window.parent.closeDialog();
}
