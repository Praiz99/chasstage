$(document).ready(function() {
	$("#nodeRuleForm").validate();
	var id = $("#id").val();
	initData(id);
});

function initData(id){
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/act/nodeRule/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#nodeRuleForm').form('load', data);
				$('#ruleTypeId').attr("initVal", data.ruleType);
				dicInit(true);
			}
		});
	}else{
		dicInit(true);
	}
}

function saveActNodeRule(){
	if(!$("#nodeRuleForm").valid()){
		return false;
	}
	var tableData = $("#nodeRuleForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/act/nodeRule/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				window.location.href=ctx + '/act/nodeRule/nodeRuleList?nodeMark=' + $("#nodeMark").val()+ "&modelId=" + $("#modelId").val();
			});
		}
	});
}

