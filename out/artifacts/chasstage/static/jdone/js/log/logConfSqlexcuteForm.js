$(document).ready(function() {
	$("#logConfForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/log/conf/sqlexcute/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#logConfForm').form('load', data);
			}
		});
	}
});

//保存数据源信息
function saveLogConf(){
	if(!$("#logConfForm").valid()){
		return false;
	}
	var tableData = $("#logConfForm").serializeObject();
	tableData.operTypeName = $('#operTypr option:selected').text();
	$.ajax({
		type: "post",
		url: ctx+"/log/conf/sqlexcute/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示',data.msg,'info',function(){
				window.location.href=ctx + "/log/conf/sqlexcute/logConfList";
			});
		}
	});
}
