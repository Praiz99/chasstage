//提交删除申请
function deleteAlarm(){
	//校验流程表单，固定写法
	if(!$("#startProcessForm").valid()){
		return false;
	}
	//获取流程表单数据，固定写法
	var tableData = $("#startProcessForm").serializeObject();
//	if(tableData.currentProUserId == null && $("#tranRole").val() != ""){
//		$.messager.alert('系统提示','请选择上报用户！','warning');
//		return false;
//	}
	tableData.nextNodeProUserId = tableData.nextNodeProUserId==null?"":tableData.nextNodeProUserId.toString();
	//设置业务数据
	tableData = setBizData(tableData);
	$.ajax({
		type: "post",
		url: ctx+"/demo/act/startProcess",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(data.success){
				$.messager.alert('系统提示','测试流程启动成功！','info',function(){
					closeDialog();
				});
			}else{
				$.messager.alert('系统提示','测试流程启动失败！','error',function(){
					closeDialog();
				});
			}
		}
	});
}

//设置业务数据
function setBizData(data){
	//消息类型：jq警情；ws文书
	data.msgType = "jq";
	//消息标题
	data.msgTitle = $("#bizName").val(); 
	//是否发送短信
	data.isMobileRmd = data.isMobileRmd == null ? 0 : parseInt(data.isMobileRmd);
	//是否发送邮件
	data.isMailRmd = data.isMailRmd == null ? 0 : parseInt(data.isMailRmd);
	return data;
}

//关闭对话框
function closeDialog(){
	$('#closeDialog', parent.document).click();
}