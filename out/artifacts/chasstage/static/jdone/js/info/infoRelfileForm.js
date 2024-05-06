function saveInfo(){
	if(!$("#infoForm").valid()){
		return false;
	}
	var tableData = $("#infoForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/info/relfileSave",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				window.location.href=ctx + '/info/openInfoRelfileList';
			});
		}
	});
}

//打开分类管理
function openCatList(){
	$('#openInfoCatList')[0].src = ctx + "/info/openInfoCatList";
	$('#infoCatDialog').dialog('open');
}
