$(document).ready(function() {
	$("#actGroupForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/act/actGroup/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#actGroupForm').form('load', data);
			}
		});
	}
});

//保存数据源信息
function saveActGroup(){
	if(!$("#actGroupForm").valid()){
		return false;
	}
	var tableData = $("#actGroupForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/act/actGroup/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			window.location.href=ctx + "/act/actGroup/actGroupList";
		}
	});
}
