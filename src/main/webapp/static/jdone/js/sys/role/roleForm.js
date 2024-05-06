$(document).ready(function() {
	$("#roleForm").validate();
	var id = $("#id").val();
	if($("#currentIsSuper").val() != "true"){
		$(".sfgly").hide();
	}
/*	if($("#currentIsSuper").val() == "true" || $("#currentIsAdmin").val() == "true"){
		$(".sfszsyfw").hide();
	}*/
	initData(id);
});

function initData(id){
	$.ajax({
		type : "post",
		url : ctx + "/sys/roleCat/findAllCat",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#roleCatList").append($("<option/>").text(data[i].groupName).attr("value",data[i].groupMark));
			}
			if (id != null && id != "") {
				$.ajax({
					type: "post",
					url: ctx+"/sys/role/findById?id="+id,
					dataType: 'json',
					cache: false,
					success: function(data) {
						$('#roleForm').form('load', data);
						$("#levelDic").attr("initVal", data.level);
						dicInit(true);
						if(data.isUseRange == 1){
							$('#useRange').show();
						}
					}
				});
			}else{
				dicInit(true);
			}
		}
	});
}

function adminChange(){
	var isAdmin = $("input[name='isAdmin']:checked").val();
	if(isAdmin == "1"){
		$("input[name=isEnableAllotRole][value=1]").prop("checked",true); 
	}else{
		$("input[name=isEnableAllotRole][value=0]").prop("checked",true); 
	}
}

function levelChange(){
	$("input[name=code]").val($("#roleLev").val());
}

function saveRole(){
	if(!$("#roleForm").valid()){
		return false;
	}
	var tableData = $("#roleForm").serializeObject();
	tableData.isSuperAdmin = 0;
	tableData.isEnableAllotRole = $("input[name='isEnableAllotRole']:checked").val();
	//根据code校验角色唯一性
	$.ajax({
		type: "post",
		url: ctx+"/sys/role/findByCode",
		data: tableData,
		dataType : 'json',
		cache: false,
		success: function(data) {
			if(data.length != 0 && data[0].id != tableData.id){
				$.messager.alert("系统提示", "角色编号重复，请重新配置!");
				return false;
			}
			//根据级别和是否管理员校验角色唯一性
			$.ajax({
				type: "post",
				url: ctx+"/sys/role/findByLevelAndIsAdmin",
				data: tableData,
				dataType : 'json',
				cache: false,
				success: function(result) {
					if(result.length != 0 && result[0].id != tableData.id && tableData.isAdmin == 1){
						$.messager.alert("系统提示", "当前角色级别下已存在管理员，请重新配置!");
						return false;
					}
					$.ajax({
						type: "post",
						url: ctx+"/sys/role/save",
						data: tableData,
						dataType: 'json',
						cache: false,
						success: function(data) {
							$.messager.alert('系统提示','保存成功！','info',function(){
								$('#refreshFlog', parent.document).click();
								if(data.opType == "2"){
									$('#roleForm').form('load', data.sysrole);
								}else{
									window.location.href=ctx + '/sys/role/roleList';
								}
							});
						}
					});
				}
			});
		}
	});
}

function goBack(id){
	if(id == ""){
		window.location.href = ctx + '/sys/role/roleList';
	}else{
		$("#center", parent.document).hide();
		$("#right", parent.document).show();
	}
}

