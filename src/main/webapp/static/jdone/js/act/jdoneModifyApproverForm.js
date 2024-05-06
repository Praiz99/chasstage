var bizType = "";
var bizId = "";
var nextRoleCode = "";
var nextNodeResult = null;
$(document).ready(function() { 
	$("#startProcessForm").validate();
	bizId = $("#bizId").val();
	bizType = $("#bizType").val();
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
		url: ctx+"/act/pendTask/findModifyAppProInfo?bizId=" + bizId + "&bizType=" + bizType,
		dataType: 'json',
		cache: false,
		success: function(res) {
			if(res.success){
				var result = res.data;
				nextNodeResult = result;
				if(result != null && result.length > 0){
					$("#nodeProId").val(res.nodeProId);
					$("#taskId").val(res.taskId);
					for ( var i = 0; i < result.length; i++) {
						if(i == 0){
							$("#nextNodeSelect").append("<option value='" + result[i].nextNode.id + "' selected='selected'>" + result[i].nextNode.actNodeName + "</option>");
						}else{
							$("#nextNodeSelect").append("<option value='" + result[i].nextNode.id + "'>" + result[i].nextNode.actNodeName + "</option>");
						}
					}
					setNextProVal(result[0]);
				}
			}else{
				$.messager.alert('系统提示',res.msg,'warning');
			}
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
		nextRoleCode = nextProInfo.roles[i].code + ",";
	}
	$("#nextNodeId").val(nextProInfo.nextNode.id);
	$("#nextNodeMark").val(nextProInfo.nextNode.nodeMark);
	$("#tranRole").append(tranRole.substring(0, tranRole.length-1));
	$("#tranRole").val(tranRoleId.substring(0, tranRoleId.length-1));
	nextRoleCode = nextRoleCode.substring(0, nextRoleCode.length-1);
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

//提交流程申请
function updateApprover(){
	//校验流程表单，固定写法
	if(!$("#startProcessForm").valid()){
		return false;
	}
	//获取流程表单数据，固定写法
	var tableData = $("#startProcessForm").serializeObject();
	if(tableData.currentProUserId == null && $("#tranRole").val() != ""){
		$.messager.alert('系统提示','请选择上报用户！','warning');
		return false;
	}
	tableData.currentProUserId = tableData.currentProUserId==null?"":tableData.currentProUserId.toString();
	tableData.nextRoleCode = nextRoleCode;
	$.ajax({
		type: "post",
		url: ctx+"/act/pendTask/modifyApprover?dataStr=" + encodeURI(JSON.stringify(tableData)),
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(data.success){
				$.messager.alert('系统提示',data.msg,'info',function(){
					closeDialog();
				});
			}else{
				$.messager.alert('系统提示',data.msg,'error',function(){
					closeDialog();
				});
			}
		}
	});
}

//关闭对话框
function closeDialog(){
	window.parent.closeDialog();
}

