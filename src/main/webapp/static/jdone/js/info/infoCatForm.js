function saveInfoCat(){
	if(!$("#catForm").valid()){
		return false;
	}
	var tableData = $("#catForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/info/relfileCatSave",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				window.location.href=ctx + '/info/openInfoCatList';
			});
		}
	});
}