$(document).ready(function() {
	$("#logConfForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/log/conf/operation/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#logConfForm').form('load', data);
				$('#operTyprDic').attr("initVal", data.operTypr);
				dicInit(true);
			}
		});
	}else{
		dicInit(true);
	}
});

//保存数据源信息
function saveLogConf(){
	if(!$("#logConfForm").valid()){
		return false;
	}
	var tableData = $("#logConfForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/log/conf/operation/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示',data.msg,'info',function(){
				window.location.href=ctx + "/log/conf/operation/logConfList";
			});
		}
	});
}
