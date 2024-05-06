var bizId = "";
var bizType = "";
var actKey = "";
$(document).ready(function() {
	$("#startProcessForm").validate();
	actKey = $("#actKey").val();
	bizId = $("#bizId").val();
	bizType = $("#bizType").val();
	if(actKey == null || actKey == ""){
		$.messager.alert('系统提示','请传入流程模型标识！','warning');
		return false;
	}
	if(bizId == null || bizId == ""){
		$.messager.alert('系统提示','请传入业务ID！','warning');
		return false;
	}
	if(bizType == null || bizType == ""){
		$.messager.alert('系统提示','请传入业务类型！','warning');
		return false;
	}
	$.ajax({
		type: "post",
		url: ctx+"/act/actInstance/getProcessStratExecuteUser?actKey=" + actKey,
		dataType: 'json',
		cache: false,
		success: function(data) {
			setNextProVal(data);
		}
	});
	
});

//上报表单赋值
function setNextProVal(nextProInfo){
	var tranRole = "";
	var tranRoleId = "";
	for ( var i = 0; i < nextProInfo.roles.length; i++) {
		tranRole += nextProInfo.roles[i].name + ",";
		tranRoleId += nextProInfo.roles[i].id + ",";
	}
	$("#nextNodeId").val(nextProInfo.nextNode.id);
	$("#nextNodeMark").val(nextProInfo.nextNode.NodeMark);
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
				for ( var j = 0; j < data.length; j++) {
					$("#nextUsers").append("<option value='" + data[j].id + "' selected='selected'>" + data[j].name + "【" + data[j].loginId + "】</option>");
				}
			}
		});
	}else{
		$("#nextUsers").html("");
	}
}

