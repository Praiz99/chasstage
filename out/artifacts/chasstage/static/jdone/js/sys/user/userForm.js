$(document).ready(function() {
	$("#userForm").validate();
	var id = $("#id").val();
	initData(id);
	if(self.frameElement != null && self.frameElement.getAttribute("data-id") == "5"){
		$("#btnCancel").hide();
	}
});

function initData(id){
	initOrgList('id','pid','code',ctx + "/sys/org/treeData",true);
	$.ajax({
		type : "post",
		url : ctx + "/sys/region/dis_management",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.rows.length;i++){ 
				$("#regList").append($("<option/>").text(data.rows[i].name).attr("value",data.rows[i].code));
			}
			if (id != null && id != "") {
				$.ajax({
					type: "post",
					url: ctx+"/sys/user/findById?id="+id,
					dataType: 'json',
					cache: false,
					success: function(data) {
						$('#userForm').form('load', data);
						$("#repassword").val(data.loginPsd);			
					}
				});
			}
		}
	});
}

function regChange(){
	initOrgList('id','pid','code',ctx + "/sys/org/treeData?regCode=" + $("#regList").val(),true);
}

function saveUser(){
	if($("#orgSel").val()==""){
		$("#pid").val("");
	}
	if(!$("#userForm").valid()){
		return false;
	}
	var tableData = $("#userForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/sys/user/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				if(self.frameElement != null && self.frameElement.getAttribute("data-id") == "5"){
					$('li[data-id=5] .close-tab', parent.document).click();
				}else{
					window.location.href=ctx + '/sys/user/userList';
				}
			});
		}
	});
}

