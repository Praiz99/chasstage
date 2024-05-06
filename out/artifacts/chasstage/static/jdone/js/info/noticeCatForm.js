function saveCat() {
	var tableData = $('#noticeCatForm').serializeObject();
	if($('#noticeCatForm').valid()){
	$.ajax({
		type : "post",
		url : ctx + "/info/notice/saveCat",
		data : tableData,
		dataType : 'json',
		cache : false,
		success : function(data) {
			if(data.success){
				window.location.href = ctx + "/info/notice/noticeCatList";
			}else{
				$.messager.alert("提示",data.msg);
			}
		}
	});
	}
}