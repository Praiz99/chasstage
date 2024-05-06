$(document).ready(function() {
	$("#authAppForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/authApp/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#authAppForm').form('load', data);
				$('#authCode').html(data.authCode);
			}
		});
	}
});

//保存分组信息
function saveAuthApp(){
	if(!$("#authAppForm").valid()){
		return false;
	}
	var tableData = $("#authAppForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/authApp/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示', data.msg,'info',function(){
				window.location.href=ctx + "/authApp/authAppList";
			});
		}
	});
}

