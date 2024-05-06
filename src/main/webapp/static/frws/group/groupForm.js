$(document).ready(function() {
	$("#groupForm").validate();
	var id = $("#id").val();
	$.ajax({
		type : "post",
		url : ctx + "/authApp/findAll",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var l =0;l<data.length;l++){ 
				$("#authAppList").append('<label><input name="authAppList" type="checkbox" value="' + data[l].authAppMark + '"/>' + data[l].authAppName + '</label>');
			}
			$.ajax({
				type : "post",
				url : ctx + "/clientApp/findAll",
				dataType : 'json',
				cache : false,
				success : function(result) {
					for(var m =0;m<result.length;m++){ 
						$("#clientList").append('<label><input name="clientList" type="checkbox" value="' + result[m].appMark + '"/>' + result[m].appName + '</label>');
					}
					if (id != null && id != "") {
						$.ajax({
							type: "post",
							url: ctx+"/group/findById?id="+id,
							dataType: 'json',
							cache: false,
							success: function(data) {
								$('#groupForm').form('load', data);
								var authAppArray = data.authAppList.split(',');
								for(var i=0;i<authAppArray.length;i++){
									$("input:checkbox[name='authAppList'][value='"+authAppArray[i]+"']").prop('checked','true');
								}
								var clientAppArray = data.clientList.split(',');
								for(var j=0;j<clientAppArray.length;j++){
									$("input:checkbox[name='clientList'][value='"+clientAppArray[j]+"']").prop('checked','true');
								}
							}
						});
					}
				}
			});
		}
	});
});

//保存分组信息
function saveClientApp(){
	if(!$("#groupForm").valid()){
		return false;
	}
	
	var tableData = $("#groupForm").serializeObject();
	tableData.authAppList = tableData.authAppList==null?null:tableData.authAppList.toString();
	tableData.clientList = tableData.clientList==null?null:tableData.clientList.toString();
	$.ajax({
		type: "post",
		url: ctx+"/group/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示', data.msg,'info',function(){
				window.location.href=ctx + "/group/groupList";
			});
		}
	});
}

