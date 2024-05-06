$(document).ready(function() {
	$("#regionForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/sys/region/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#regionForm').form('load', data);
			}
		});
	}
});


function saveRegion(){
	if(!$("#regionForm").valid()){
		return false;
	}
	var tableData = $("#regionForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/sys/region/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				window.location.href=ctx + '/sys/region/regionList';
			});
		}
	});
}