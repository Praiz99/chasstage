$(document).ready(function() {
	$("#dataConfForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/data/modifyConf/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#dataConfForm').form('load', data);
			}
		});
	}
});

//保存数据源信息
function saveDataConf(){
	if(!$("#dataConfForm").valid()){
		return false;
	}
	var tableData = $("#dataConfForm").serializeObject();
	tableData.operTypeName = $('#operTypr option:selected').text();
	$.ajax({
		type: "post",
		url: ctx+"/data/modifyConf/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示',data.msg,'info',function(){
				window.location.href=ctx + "/data/modifyConf/dataModifyConfList";
			});
		}
	});
}
