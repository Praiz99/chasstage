var id = "";
var instId = "";
var bizId = "";
var nodeBackType = "";
var nowNodeId = "";
var nextNodeResult = null;
$(document).ready(function() {
	$("#shForm").validate();
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
			if(data.nodeId == null || data.nodeId == ""){
				$("#rejectBack").hide();
			}
			if(data.id == null){
				return false;
			}
			$.ajax({
				type: "post",
				url: ctx+"/act/pendTask/findNextProInfo",
				data: {id: data.id},
				dataType: 'json',
				cache: false,
				success: function(result) {
					nextNodeResult = result;
					if(result != null && result.length > 0){
						for ( var i = 0; i < result.length; i++) {
							if(i == 0){
								$("#nextNodeSelect").append("<option value='" + result[i].nextNode.id + "' selected='selected'>" + result[i].nextNode.actNodeName + "</option>");
							}else{
								$("#nextNodeSelect").append("<option value='" + result[i].nextNode.id + "'>" + result[i].nextNode.actNodeName + "</option>");
							}
						}
						setNextProVal(result[0]);
					}else{
						//目标任务为空即到达最后节点时，隐藏上报相关选项
						$("#shActFrame" , parent.document).attr("src", ctx + "/act/pendTask/processApprovalForm?id=" + id + "&instId=" + instId);
					}
				}
			});
		}
	});
	
});

//上传节点改变事件
function nextNodeChange(){
	var nextNodeId = $("#nextNodeSelect").val();
	for ( var i = 0; i < nextNodeResult.length; i++) {
		if(nextNodeId == nextNodeResult[i].nextNode.id){
			$("#tranRole").html("");
			$("#nextOrg").html("");
			$("#nextUsers").html("");
			setNextProVal(nextNodeResult[i]);
		}
	}
}

//上报表单赋值
function setNextProVal(nextProInfo){
	var tranRole = "";
	var tranRoleId = "";
	for ( var i = 0; i < nextProInfo.roles.length; i++) {
		tranRole += nextProInfo.roles[i].name + ",";
		tranRoleId += nextProInfo.roles[i].id + ",";
	}
	$("#nextNodeId").val(nextProInfo.nextNode.id);
	$("#nextProMark").val(nextProInfo.nextNode.nodeMark);
	$("#tranRole").append(tranRole.substring(0, tranRole.length-1));
	$("#tranRole").val(tranRoleId.substring(0, tranRoleId.length-1));
	for ( var j = 0; j < nextProInfo.nextOrgs.length; j++) {
		if(j == 0){
			$("#nextOrg").append("<option value='" + nextProInfo.nextOrgs[j].sysCode + "' selected='selected'>" + nextProInfo.nextOrgs[j].name + "</option>");
		}else{
			$("#nextOrg").append("<option value='" + nextProInfo.nextOrgs[j].sysCode + "'>" + nextProInfo.nextOrgs[j].name + "</option>");
		}
	}
	getApprovalUser();
}

//上报机构改变事件
function getApprovalUser(){
	var roles = $("#tranRole").val();
	var nextOrgSysCode = $("#nextOrg").val();
	if(nextOrgSysCode != null && nextOrgSysCode != ""){
		$.ajax({
			type: "post",
			url: ctx+"/act/actInstance/findApprovalUsers",
			data: {roles: roles, sysCode: nextOrgSysCode},
			dataType: 'json',
			cache: false,
			success: function(data) {
				$("#nextUsers").html("");
				if(data.length<2){
					for ( var j = 0; j < data.length; j++) {
							$("#nextUsers").append("<option value='" + data[j].id + "' selected='selected'>" + data[j].name + "【" + data[j].loginId + "】</option>");
					}
				}else{
					for ( var j = 0; j < data.length; j++) {
						$("#nextUsers").append("<option value='" + data[j].id + "' selected='selected'>" + data[j].name + "【" + data[j].loginId + "】</option>");
					}
				}
			}
		});
	}else{
		$("#nextUsers").html("");
	}
}

//变更上报审批人
function modifyApprovalUser(approver, dealType ){
	if(approver == null || dealType == null) {
		return false;
	}
	if(dealType == "add") {
		$("#nextUsers").append("<option value='" + approver.ryid + "' selected='selected'>" + approver.ryxm + "【" + approver.ryjh + "】</option>");
	} else if(dealType == "delete") {
		var deleteUser = $('#nextUsers').find('option[value=' + approver.ryid + ']');
		deleteUser.remove();
	}
}

//处理意见点击事件
function clyjOnClick(){
	if($("input[name='proResultType']:checked").val()=="reject"){
		$("#transTr").hide();
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
		$("#transTr").show();
	}
	if (window.parent.clyjCallBack && typeof (window.parent.clyjCallBack) == "function") {
		var param = {};
		param.proType = "sh";
		param.proResultType = $("input[name='proResultType']:checked").val();
		param.proOpinionMark = "proOpinion";
		window.parent.clyjCallBack(param);
	}
}

//展示流程历史
function showProHis(){
	window.open(ctx + "/act/pendTask/processHistory?instId=" + instId);
}

//获取地址栏参数
function getUrlParms(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.parent.location.href.substr(1).match(reg);
	if (r != null){
		return unescape(r[2]);
	}
	return null;
}

// 提交流程审核信息
function shButtionClick(){
	var msgId = getUrlParms("msgId");
	if(msgId != null && msgId != ""){
		$.ajax({
			type: "post",
			url: ctx+"/act/rmdMsg/findById",
			data: {id: msgId},
			dataType: 'json',
			cache: false,
			success: function(data) {
				if(data.id == null || data.id == "" || data.msgStatus == "01"){
					$.messager.alert('系统提示','当前消息不存在或已被处理！','warning');
					return false;
				}
				processCompleteSubmit();
			}
		});
	} else {
		$.messager.alert('系统提示','当前消息不存在或已被处理！','warning');
		return false;
	}
}

function processCompleteSubmit(){
	var tableData = $("#shForm").serializeObject();
	tableData.actInstId = instId;
	tableData.taskId = id;
	tableData.proType = "sh";
	if(!$("#shForm").valid()){
		return false;
	}
	if(tableData.proResultType == null || tableData.proResultType == ""){
		$.messager.alert('系统提示','请选择处理方式！','warning');
		return false;
	}
	if(tableData.currentProUserId == null && $("#tranRole").val() != "" && tableData.proResultType == "approve"){
		$.messager.alert('系统提示','请选择上报用户！','warning');
		return false;
	}
	tableData.currentProUserId = tableData.currentProUserId==null?"":tableData.currentProUserId.toString();
	tableData.bizId = bizId;
	tableData.isMobileRmd = tableData.isMobileRmd == null ? 0 : parseInt(tableData.isMobileRmd);
	tableData.isMailRmd = tableData.isMailRmd == null ? 0 : parseInt(tableData.isMailRmd);
	tableData.bizData = $('#bizData', parent.document).val();
	$(".shBtn").attr("disabled","disabled");
	$.ajax({
		type: "post",
		url: ctx+"/act/engine/completeProcess",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$(".shBtn").removeAttr("disabled");
			if(data.success){
				parent.window.hideActProForm();
				if (window.parent.actCallBack && typeof (window.parent.actCallBack) == "function") {
					window.parent.actCallBack(data);
				}
				
			}else{
				$.messager.alert('系统提示','流程处理失败！' + data.msg,'warning');
			}
			if(window.parent.opener && window.parent.opener.callback){
				window.parent.opener.callback();
			}
		},
		error: function(data) {
			$(".shBtn").removeAttr("disabled");
			if(window.parent.opener && window.parent.opener.callback){
				window.parent.opener.callback();
			}
			$.messager.alert('系统提示','流程处理失败！','warning');
		}
	});
}

