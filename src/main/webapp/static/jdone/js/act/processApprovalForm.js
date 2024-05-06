var id = "";
var instId = "";
var bizId = "";
var nodeBackType = "";
var nowNodeId = "";
$(document).ready(function() {
	$("#spForm").validate();
	id = $("#id").val();
	instId = $("#instId").val();
	nodeBackType = $("#nodeBackType" , parent.document).val();
	$.ajax({
		type: "post",
		url: ctx+"/act/pendTask/findById?id="+id,
		dataType: 'json',
		cache: false,
		success: function(data) {
			bizId = data.bizId;
			nowNodeId = data.nodeId;
		}
	});
	
});

//展示流程历史
function showProHis(){
	window.open(ctx + "/act/pendTask/processHistory?instId=" + instId);
}

//处理意见点击事件
function spClyjOnClick(){
	if($("input[name='proResultType']:checked").val()=="reject"){
		if(nodeBackType == "03"){
			$("#nodeBackList").show();
			$.ajax({
				type: "post",
				url: ctx+"/act/actNode/findPrevNodes?instId=" + instId + "&nodeId=" + nowNodeId,
				dataType: 'json',
				cache: false,
				success: function(backNodeList) {
					for ( var j = 0; j < backNodeList.length; j++) {
						if(j == 0){
							$("#nodeBackSelect").append("<option value='" + backNodeList[j].id + "' selected='selected'>" + backNodeList[j].actNodeName + "</option>");
						}else{
							$("#nodeBackSelect").append("<option value='" + backNodeList[j].id + "'>" + backNodeList[j].actNodeName + "</option>");
						}
					}
				}
			});
		}
	}else{
		$("#nodeBackList").hide();
	}
	if (window.parent.clyjCallBack && typeof (window.parent.clyjCallBack) == "function") {
		var param = {};
		param.proType = "sp";
		param.proResultType = $("input[name='proResultType']:checked").val();
		param.proOpinionMark = "proOpinion";
		window.parent.clyjCallBack(param);
	}
}

//进行流程处理
function spButtionClick(){
	var tableData = $("#spForm").serializeObject();
	tableData.actInstId = instId;
	tableData.taskId = id;
	tableData.proType = "sp";
	if(!$("#spForm").valid()){
		return false;
	}
	if(tableData.proResultType == null || tableData.proResultType == ""){
		$.messager.alert('系统提示','请选择处理方式！','warning');
		return false;
	}
	tableData.bizId = bizId;
	tableData.isMobileRmd = tableData.isMobileRmd == null ? 0 : parseInt(tableData.isMobileRmd);
	tableData.isMailRmd = tableData.isMailRmd == null ? 0 : parseInt(tableData.isMailRmd);
	tableData.bizData = $('#bizData', parent.document).val();
	$(".spBtn").attr("disabled","disabled");
	$.ajax({
		type: "post",
		url: ctx+"/act/engine/completeProcess",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(data.success){
				parent.window.hideActProForm();
				if (window.parent.actCallBack && typeof (window.parent.actCallBack) == "function") {
					window.parent.actCallBack(data);
				}
			}else{
				$.messager.alert('系统提示','流程处理失败！' + data.msg,'warning');
			}
			$(".spBtn").removeAttr("disabled");
			if(window.parent.opener && window.parent.opener.callback){
				window.parent.opener.callback();
			}
		},
		error: function(data) {
			$(".spBtn").removeAttr("disabled");
			if(window.parent.opener && window.parent.opener.callback){
				window.parent.opener.callback();
			}
			$.messager.alert('系统提示','流程处理失败！','warning');
		}
	});
}
