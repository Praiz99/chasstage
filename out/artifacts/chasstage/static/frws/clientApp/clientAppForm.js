$(document).ready(function() {
	$("#clientAppForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/clientApp/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#clientAppForm').form('load', data);
			}
		});
	}
});

//保存分组信息
function saveClientApp(){
	if(!$("#clientAppForm").valid()){
		return false;
	}
	var tableData = $("#clientAppForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/clientApp/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示', data.msg,'info',function(){
				window.location.href=ctx + "/clientApp/clientAppList";
			});
		}
	});
}

