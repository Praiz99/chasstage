$(document).ready(function() {
	$("#resScriptForm").validate();
	var id = $("#id").val();
	initScriptInfo(id);
});

function initScriptInfo(id){
	$.ajax({
		type : "post",
		url : ctx + "/db/source/findAll",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#dbSourceList").append($("<option/>").text(data[i].sourceName).attr("value",data[i].id));
			}
			if (id != null && id != "") {
				$.ajax({
					type: "post",
					url: ctx+"/db/resScript/findById?id="+id,
					dataType: 'json',
					cache: false,
					success: function(data) {
						$('#resScriptForm').form('load', data);
						//初始化分组列表
						sourceChange();
					}
				});
			}else{
				//初始化分组列表
				sourceChange();
			}
		}
	});
	
}

//数据源改变事件
function sourceChange(){
	var sour = $("#dbSourceList option:selected").val();
	initGroup(sour);
}

//初始化分组列表
function initGroup(sour){
	$("#groupList").html("");
	$.ajax({
		type : "post",
		url : ctx + "/db/resGroup/findAll?sourceId=" + sour + "&resType=02",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#groupList").append($("<option/>").text(data[i].groupName).attr("value",data[i].id));
			}
		}
	});
}

//脚本测试
function testScript(){
	var data = $("#resScriptForm").serializeObject();
	if(data.sourceId==""){
		$.messager.alert("系统提示","请先填写数据源！！");
		return false;
	}
	if(data.scriptContent==""){
		$.messager.alert("系统提示","请先填写脚本内容！！");
		return false;
	}
	$.ajax({
		type: "post",
		url: ctx+"/db/resScript/testScript",
		dataType: 'json',
		cache: false,
		data: data,
		success: function(data) {
			$.messager.alert("系统提示",data.msg,"info");
		}
	});
}

//保存表信息及字段信息
function updateScript(){
	if(!$("#resScriptForm").valid()){
		return false;
	}
	var scriptData = $("#resScriptForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/db/resScript/save",
		data: scriptData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			window.location.href=ctx + "/db/resScript/resScriptList?groupId=" + scriptData.groupId;
		}
	});
}

