$(document).ready(function(){
	var vs = $('select  option:selected').val();
	 if(vs=='update'){
			$("#BaseVersion").show();//显示
	 }else{
	 	 $("#BaseVersion").hide();//隐藏
	 }
});
function saveUpgradeRecord(){
	if(!$("#upgradeRecordForm").valid()){
		return false;
	}
	var tableData = $("#upgradeRecordForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/app/version/saveVer",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
				window.parent.closeDialog();
				window.parent.$("#datagrid").datagrid('reload');
			/*	exportRaw(data.uploadName+'.sql',data.data);*/
		}
	});
}

function saveFileUpgrade(){
	if(!$("#upgradeRecordForm").valid()){
		return false;
	}
	var tableData = $("#upgradeRecordForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/app/version/saveVer",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			if(data.success){
				window.parent.closeDialog();
				window.parent.$("#datagrid").datagrid('reload');
				window.parent.fileUpgrade(data.id);	
			}else{
				alert(data.msg);
			}
				
			//	exportRaw(data.uploadName+'.sql',data.data);
		}
	});
}

function func(){
	 var vs = $('select  option:selected').val();
	 if(vs=='update'){
			$("#BaseVersion").show();//显示
	 }else{
	 	 $("#BaseVersion").hide();//隐藏
	 }
}

function fakeClick(obj) {
	  var ev = document.createEvent("MouseEvents");
	  ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
	  obj.dispatchEvent(ev);
	}

function exportRaw(name, data) {
	  var urlObject = window.URL || window.webkitURL || window;
	  var export_blob = new Blob([data]);
	  var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
	  save_link.href = urlObject.createObjectURL(export_blob);
	  save_link.download = name;
	  fakeClick(save_link);
	} 