$(document).ready(function() {
	var id = $("#id").val();
	initFieldInfo(id);
});

function initFieldInfo(id){
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/db/resField/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#fieldForm').form('load', data);
			}
		});
	}
	
}

//保存字段信息
function saveField(){
	if(!$("#fieldForm").valid()){
		return false;
	}
	var fieldData = $("#fieldForm").serializeObject();
	fieldData.isPkField = 0;
	$.ajax({
		type: "post",
		url: ctx+"/db/resField/save",
		data: fieldData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			window.location.href=ctx + "/db/resField/resFieldList";
		}
	});
}


