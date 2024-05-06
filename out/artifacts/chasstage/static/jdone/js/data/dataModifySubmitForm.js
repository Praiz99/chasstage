var bizType = "";
var actKey = "";
var dataParam = {};
$(document).ready(function() { 
	$("#startProcessForm").validate();
	dataParam = $("#dataStr").val();
	dataParam = eval('(' + dataParam + ')');
	actKey = dataParam.actKey;
	bizId = dataParam.bizId;
	bizType = dataParam.bizType;
	if(actKey == null || actKey == ""){
		$.messager.alert('系统提示','请传入流程模型标识！','warning');
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
	bizMarkValue = dataParam.bizMarkValue;
	$.ajax({
		type: "post",
		url: ctx+"/data/modifyConf/getDspData?bizMarkValue=" + bizMarkValue + "&modifyBizType=" + bizType,
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(!data.success){
				$("#btnProcessRecession").hide();
			}
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
	$("#nextNodeMark").val(nextProInfo.nextNode.nodeMark);
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

//提交流程申请
function startWorkflow(){
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
	
	//判断是否重复提交申请
	dataParam = $("#dataStr").val();
	dataParam = eval('(' + dataParam + ')');
	bizType = dataParam.bizType;
	bizMarkValue = dataParam.bizMarkValue;
	$.ajax({
		type: "post",
		url: ctx+"/data/modifyConf/getDspData?bizMarkValue=" + bizMarkValue + "&modifyBizType=" + bizType,
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(data.success){
				$.messager.alert('系统提示', '该数据变更申请已提交过一次!','warning');
				return false;
			}else{
				dataParam.currentProUserId = tableData.currentProUserId==null?"":tableData.currentProUserId.toString();
				dataParam.isMobileRmd = tableData.isMobileRmd == null ? 0 : parseInt(tableData.isMobileRmd);
				dataParam.isMailRmd = tableData.isMailRmd == null ? 0 : parseInt(tableData.isMailRmd);
				dataParam.proOpinion = tableData.proOpinion;
				dataParam.currentProOrgSysCode = tableData.currentProOrgSysCode;
				dataParam.currentNodeId = tableData.currentNodeId;
				dataParam.currentNodeMark = tableData.currentNodeMark;
				$.ajax({
					type: "post",
					url: ctx+"/data/modifyConf/dataModifyProcess?dataStr=" + encodeURI(encodeURI(JSON.stringify(dataParam))),
					data: dataParam,
					dataType: 'json',
					cache: false,
					success: function(data) {
						if(data.success){
							$.messager.alert('系统提示','数据变更申请已提交！','info',function(){
								closeDialog();
							});
						}else{
							$.messager.alert('系统提示','数据变更申请提交失败！','error',function(){
								closeDialog();
							});
						}
					}
				});
			}
		}
	});
}

//关闭对话框
function closeDialog(){
	window.parent.closeDialog();
}

//打开流程历史
function openProcessHistory(){
	dataParam = $("#dataStr").val();
	dataParam = eval('(' + dataParam + ')');
	bizType = dataParam.bizType;
	bizMarkValue = dataParam.bizMarkValue;
	$.ajax({
		type: "post",
		url: ctx+"/data/modifyConf/getDspData?bizMarkValue=" + bizMarkValue + "&modifyBizType=" + bizType,
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(data.success){
				var url = ctx + "/act/pendTask/processHistoryNew?bizId="+data.data.id+"&bizType=tysjbg_"+bizType;
				window.open(url);
			}else{
				$.messager.alert('系统提示', '该数据没有提交记录!','warning');
				return false;
			}
		}
	});
}

function processRecession(){
	dataParam = $("#dataStr").val();
	dataParam = eval('(' + dataParam + ')');
	bizType = dataParam.bizType;
	bizMarkValue = dataParam.bizMarkValue;
	
	$.messager.confirm('系统提示', '您确定要撤回上报操作？', function(r) {
        if (r) {
        	$.ajax({
        		type: "post",
        		url: ctx+"/data/modifyConf/getDspData?bizMarkValue=" + bizMarkValue + "&modifyBizType=" + bizType,
        		dataType: 'json',
        		cache: false,
        		success: function(data) {
        			if(data.success){
        				$.ajax({
							type : "post",
							url : ctx + "/data/modifySpb/delete?bizId="+data.data.id+"&bizType=tysjbg_"+bizType,
							dataType : 'json',
							cache : false,
							success : function(dataObj) {
								if(dataObj.success){
									$.messager.alert('系统提示',dataObj.msg,'info',function(){
										closeDialog();
									});
        						}else{
        							$.messager.alert('系统提示', dataObj.msg ,'warning');
        							return false;
        						}
							}
						});
        			}else{
        				$.messager.alert('系统提示', '该数据没有提交记录!','warning');
        				return false;
        			}
        		}
        	});
        }
	});
	
}