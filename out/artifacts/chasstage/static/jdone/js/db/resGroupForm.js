$(document).ready(function() {
	$("#resGroupForm").validate();
	var id = $("#id").val();
	$.ajax({
		type : "post",
		url : ctx + "/db/source/findAll",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#dbSourceList").append($("<option/>").text(data[i].sourceName).attr("value",data[i].id));
			}
			if (id != null && id != "") {
				$.ajax({
					type: "post",
					url: ctx+"/db/resGroup/findById?id="+id,
					dataType: 'json',
					cache: false,
					success: function(data) {
						$('#resGroupForm').form('load', data);
						$("#resType").attr("disabled","disabled");
					}
				});
			}
		}
	});
});

//保存分组信息
function saveGroup(){
	if(!$("#resGroupForm").valid()){
		return false;
	}
	$("#resType").removeAttr("disabled");
	var groupData = $("#resGroupForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/db/resGroup/save",
		data: groupData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			window.location.href=ctx + "/db/resource/resourceList";
		}
	});
}

