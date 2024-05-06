var win = null;
var name;
var menuid;
$(function() {
	$("#src").html(parent.completeUrl);
	$("#mainForm").validate();
	var id = $("#id").val();
	var businessId = $("#businessId").val();
	menuid=businessId;
	isCalls();
	// 初始化表单数据
	initData(id);
});

function initData(id){
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/sys/menu/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#mainForm').form('load', data);
				name=data.name;
				menuid=data.id;
				if(data.isEnableRange==1){
					isCall();
				}
			}
		});
	}else{
		$('input[name=orderId]').val('0');
		$('#menuIco').val('fa fa-file-text-o');
	}
}

function OptionIcon(){
	var iconVal = $("input[name='menuIco']").val();
	$('#menuIconSelect')[0].src = ctx+"/sys/menu/OpenMenuIconPage?iconVal="+iconVal;
	win = $('#menuIconDialog').dialog('open');
}

function saveMenu(){
	if(!$("#mainForm").valid()){
		return false;
	}
	var tableData = $("#mainForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/sys/menu/saveMenuSettings",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示',data.msg,'info',function(){
				$('#refreshFlog', parent.document).click();
			});
		}
	});
}
function openSocpe(){
	openSelectSocpe(menuid,name,null);
}
function isCall(){
	$("#range").show();
}
function isCalls(){
	$("#range").hide();
}