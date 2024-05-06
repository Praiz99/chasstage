$(document).ready(function() {
	$("#dicCatForm").validate();
});

//保存分类信息
function saveCat(){
	if(!$("#dicCatForm").valid()){
		return false;
	}
	var catData = $("#dicCatForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/com/dic/saveCat",
		data: catData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			window.location.href=ctx + "/com/dic/dicCatList";
		}
	});
}
