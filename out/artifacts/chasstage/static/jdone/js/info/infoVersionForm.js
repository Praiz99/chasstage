$(function(){
	initFileUpload();
});

function initFileUpload(){
	var versionId = $("#versionId").val();
	obj = $("#fileUpload").ligerFileUpload({
		bizId:versionId,
		bizType:"RELFILE"
	});	
}

function saveInfoVersion(){
	if(!$("#versionForm").valid()){
		return false;
	}
	var tableData = $("#versionForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/info/relfileVersionSave",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				window.location.href=ctx + '/info/infoVersionList?id='+$("#infoId").val();
			});
		}
	});
}