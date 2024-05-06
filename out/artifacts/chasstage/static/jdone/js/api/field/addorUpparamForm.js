/*$(document).ready(function() {
	$("#addorUpwbjkglForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/api/wbjkgl/wbjkglUpdateForm?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#addorUpwbjkglForm').form('load', data);
			    $("#addSpan").text("修改外部接口");  
			}
		});
	}
	});*/

function submit(){
	$('#addorUpwbjkglForm').form('submit',{
		success:function(data){
			window.location.href = ctx+"/api/outer/outerList";
		}
	});
	
}

