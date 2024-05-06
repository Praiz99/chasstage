$(document).ready(function() {
	$("#actModelForm").validate();
	var id = $("#id").val();
	var opType = $("#opType").val();
	if(opType != ""){
		$("#btnCancel").hide();
	}
	if(id == null || id == ""){
		$("#tableInfo").tabs('close', 1);
	}
	initData(id);
	
});

function initData(id){
	$.ajax({
		type : "post",
		url : ctx + "/act/actGroup/findAll",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#groupMark").append($("<option/>").text(data[i].groupName).attr("value",data[i].groupMark));
			}
			if (id != null && id != "") {
				$.ajax({
					type: "post",
					url: ctx+"/act/model/findById?id="+id,
					dataType: 'json',
					cache: false,
					success: function(data) {
						$('#actModelForm').form('load', data);
						$('#groupMarkDic').attr("initVal", data.groupMark);
						$('#modelMark').attr('readonly', 'readonly');
						dicInit(true);
					}
				});
			}else{
				dicInit(true);
			}
		}
	});
}

function saveActModel(){
	if(!$("#actModelForm").valid()){
		return false;
	}
	var tableData = $("#actModelForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/act/model/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				if(data.opType == "2"){
					$('#actModelForm').form('load', data);
				}else{
					window.location.href=ctx + '/act/model/list';
				}
			});
		}
	});
}

