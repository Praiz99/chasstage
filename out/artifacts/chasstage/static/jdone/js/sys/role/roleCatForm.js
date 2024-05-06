$(document).ready(function() {
	$("#roleCatForm").validate();
	var groupMark = $("#groupMark").val();
	if (groupMark != null && groupMark != "") {
		$.ajax({
			type: "post",
			url: ctx+"/sys/roleCat/findByGroupMark?groupMark="+groupMark,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#roleCatForm').form('load', data);
			}
		});
	}
});

//保存分组信息
function saveGroup(){
	if(!$("#roleCatForm").valid()){
		return false;
	}
	var groupData = $("#roleCatForm").serializeObject();
	//根据groupMark校验分组唯一性
	$.ajax({
		type: "post",
		url: ctx+"/sys/roleCat/findByGroupMark",
		data: groupData,
		cache: false,
		success: function(data) {
			if(data != "" && data.id != groupData.id){
				$.messager.alert("系统提示", "当前分组已经存在，请重新配置!");
				return false;
			}
			$.ajax({
				type: "post",
				url: ctx+"/sys/roleCat/save",
				data: groupData,
				dataType: 'json',
				cache: false,
				success: function(data) {
					window.location.href=ctx + "/sys/role/roleList";
				}
			});
		}
	});
}

