var id = "";
var instId = "";
var nodeId = "";
$(document).ready(function() {
	id = $("#taskId").val();
	instId = $("#instId").val();
	$.ajax({
		type: "post",
		url: ctx+"/act/pendTask/findById?id="+id,
		dataType: 'json',
		cache: false,
		success: function(data) {
			nodeId = data.nodeId;
			if(data.id == null || data.id == ""){
				hideActProForm();
				return false;
			}
			$.ajax({
				type: "post",
				url: ctx+"/act/actNode/findById",
				data: {id: nodeId},
				dataType: 'json',
				cache: false,
				success: function(nextNode) {
					$("#nodeBackType").val(nextNode.nodeBackType);
					if(nextNode.isVreifyNode == 0){
						$('.easyui-tabs').tabs('close','审核');
					}
					if(nextNode.isApprovalNode == 0){
						$('.easyui-tabs').tabs('close','审批');
					}
					if(nextNode.defaultProType === "01"){
						$('.easyui-tabs').tabs('select',"审核");
					}
					if(nextNode.defaultProType === "02"){
						$('.easyui-tabs').tabs('select',"审批");
					}
				},
				error: function(data) {
					$('.easyui-tabs').tabs('close','审批');
				}
			});
		}
	});
	
});

//隐藏流程处理表单
function hideActProForm(){
	$("#actTableInfo").hide();
	$("#proHis").after("<input type='button' id='processHistory' value='流程历史' onclick='window.open(\"" + ctx + "/act/pendTask/processHistory?instId=" + instId + "\")'>");
}

//展示流程状态
function showProcessPic(){
	$.ajax({
		type: "post",
		url: ctx+"/act/actInstance/findById",
		data: {id: instId},
		dataType: 'json',
		cache: false,
		success: function(data) {
			window.open(ctx+"/act/pendTask/processPic?modelId=" + data.modelId + "&nodeId=" + nodeId,"");
		}
	});
}

