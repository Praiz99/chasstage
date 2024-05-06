$(document).ready(function() {
	$("#sourceForm").validate();
	//字典初始化
	//dicInit(true);
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/db/source/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#sourceForm').form('load', data);
				$('#dbTypeDic').attr("initVal", data.dbType);
				$('#conTypeDic').attr("initVal", data.conType);
				dicInit(true);
			}
		});
	}else{
		dicInit(true);
	}
});

function conTypeChange(){
	var conType = $("#conType option:selected").val();
	if(conType == "3"){
		$(".dbUserInfo").hide();
		$(".jndiInfo").show();
	}else{
		$(".dbUserInfo").show();
		$(".jndiInfo").hide();
	}
}

//测试连接
function testCon(){
	var conType = $("#conType option:selected").val();
	if(conType == "3"){
		
	}else{
		var data = $("#sourceForm").serializeObject();
		if(data.conInfo=="" || data.dbUserName=="" || data.dbUserPswd==""){
			$.messager.alert("系统提示","请先填写连接信息与用户名密码！！");
			return false;
		}else{
			$.ajax({
				type: "post",
				url: ctx+"/db/source/testCon",
				dataType: 'json',
				cache: false,
				data: data,
				success: function(data) {
					$.messager.alert("系统提示",data.msg,"info");
				}
			});
		}
	}
}

//保存数据源信息
function saveSource(){
	if(!$("#sourceForm").valid()){
		return false;
	}
	var tableData = $("#sourceForm").serializeObject();
	var conInfo = tableData.conInfo;
	if(conInfo.indexOf('?')<0){
		tableData.dbName = conInfo.substring(conInfo.lastIndexOf('/')+1);
	}else{
		tableData.dbName = conInfo.substring(conInfo.lastIndexOf('/')+1,conInfo.indexOf('?'));
	}
	//根据sourceMark校验数据源唯一性
	$.ajax({
		type: "post",
		url: ctx+"/db/source/findByMark",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(data.id != null && data.id != tableData.id){
				$.messager.alert("系统提示", "当前数据源已经存在，请重新配置!");
				return false;
			}
			$.ajax({
				type: "post",
				url: ctx+"/db/source/save",
				data: tableData,
				dataType: 'json',
				cache: false,
				success: function(data) {
					window.location.href=ctx + "/db/source/sourceList";
				}
			});
		}
	});
	
}
