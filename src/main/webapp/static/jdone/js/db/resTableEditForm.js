$(document).ready(function() {
	$("#resTableForm").validate();
	var id = $("#id").val();
	initTableInfo(id);
});

function initTableInfo(id){
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
					url: ctx+"/db/resTable/findById?id="+id,
					dataType: 'json',
					cache: false,
					success: function(data) {
						$('#resTableForm').form('load', data);
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
		url : ctx + "/db/resGroup/findAll?sourceId=" + sour + "&resType=01",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#groupList").append($("<option/>").text(data[i].groupName).attr("value",data[i].id));
			}
		}
	});
}

//保存表信息及字段信息
function updateTable(){
	if(!$("#resTableForm").valid()){
		return false;
	}
	var tableData = $("#resTableForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/db/resTable/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			window.location.href=ctx + "/db/resTable/resTableList?groupId=" + tableData.groupId;
		}
	});
}


