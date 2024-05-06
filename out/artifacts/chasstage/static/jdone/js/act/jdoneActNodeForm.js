$(document).ready(function() {
	$("#actNodeForm").validate();
	$("#enableTran").hide();
	var id = $("#id").val();
	var modelId = $("#modelId").val();
	initData(id, modelId);
	
});

function initData(id, modelId){
	$.ajax({
		type : "post",
		url : ctx + "/act/actNode/findAll?modelId=" + modelId,
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				if(data[i].id != id){
					$("#transNodeList").append('<label><input name="transNodeId" type="checkbox" value="' + data[i].id + '"/>' + data[i].actNodeName + '</label>');
				}
			}
			if (id != null && id != "") {
				$.ajax({
					type: "post",
					url: ctx+"/act/actNode/findById?id="+id,
					dataType: 'json',
					cache: false,
					success: function(data) {
						$('#actNodeForm').form('load', data);
						proTypeChange();
						$('#actNodeForm').form('load', data);
						$('#proOrgTypeDic').attr("initVal", data.proOrgType);
						$('#roleCodeTypeDic').attr("initVal", data.roleCode);
						$('#proOrgLevelTypeDic').attr("initVal", data.proOrgLevel);
						$('#nodeBackTypeDic').attr("initVal", data.nodeBackType);
						//传入节点数据回显
						var boxObj = $("input:checkbox[name=transNodeId]");  //获取所有的复选框
					    var transNodeIds = data.transNodeId==null?[]:data.transNodeId.split(','); //去掉它们之间的分割符“，” 
					    for(var i=0;i<boxObj.length;i++){
					       for(var j=0;j<transNodeIds.length;j++){            
					           if(boxObj[i].value == transNodeIds[j]){
					               boxObj[i].checked= true;
					           }
					       }
					    }  
						dicInit(true);
					}
				});
			}else{
				dicInit(true);
			}
		}
	});
}

function proTypeChange(){
	$("#enableTran").hide();
	$("#defaultProTypeList").html("<option value='00' selected='selected'></option>");
	if($("input[name=isVreifyNode]")[0].checked == true){
		$("#defaultProTypeList").append("<option value='01'>审核</option>");
		$("#enableTran").show();
	}
	if($("input[name=isApprovalNode]")[0].checked == true){
		$("#defaultProTypeList").append("<option value='02'>审批</option>");
	}
	
}

function saveActNode(){
	if(!$("#actNodeForm").valid()){
		return false;
	}
	var tableData = $("#actNodeForm").serializeObject();
	if($("input[name=isVreifyNode]")[0].checked == false){
		tableData.isVreifyNode = 0;
	}
	if($("input[name=isApprovalNode]")[0].checked == false){
		tableData.isApprovalNode = 0;
	}
	var tranNodeList = tableData.transNodeId==null?null:tableData.transNodeId.toString().split(',');
	if(tranNodeList != null && tranNodeList.length > 0){
		tableData.transNodeId = tranNodeList[0];
		for(var i =1;i<tranNodeList.length;i++){ 
			tableData.transNodeId += "," + tranNodeList[i];
		}
	}
	$.ajax({
		type: "post",
		url: ctx+"/act/actNode/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				window.location.href=ctx + '/act/actNode/actNodeList';
			});
		}
	});
}

