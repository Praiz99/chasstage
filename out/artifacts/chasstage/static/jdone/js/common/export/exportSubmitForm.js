var exportColumnArray = new Array();
$(document).ready(function() { 
	var exportScope = $("#exportScope").val();
	var exportOriUrl = $("#exportOriUrl").val();
	var exportColumns = eval('(' + $("#exportColumns").val() + ')');
	if(exportScope == null || exportScope == ""){
		$.messager.alert('系统提示','请传入导出范围！','warning');
		return false;
	}
	if(exportOriUrl == null || exportOriUrl == ""){
		$.messager.alert('系统提示','请传入业务处理地址！','warning');
		return false;
	}
	if(exportScope == "0"){
		$("input[name=exportScope]:eq(0)").attr("checked",'checked');
	} else {
		$("input[name=exportScope]:eq(1)").attr("checked",'checked');
		$("input[name=exportScope]").attr("disabled",'disabled');
	}
	initExportField(exportColumns);
});

//初始化导出字段
function initExportField(exportFileds){
	var exportColumns = exportFileds[0];
	var index = 0;
	var noewTr = "";
	for ( var i = 0; i < exportColumns.length; i++) {
		if(exportColumns[i].field != "id" && exportColumns[i].title !=null && exportColumns[i].title != "操作" && exportColumns[i].hidden != true){
			if(index%3==0){
				$("#fieldCheckbox").append('<tr id="fieldTr' + i + '"></tr>');
				noewTr = "fieldTr" + i;
			}
			$("#" + noewTr).append('<td><input type="checkbox" ' +
					'name="exportField" checked="checked" value="' + exportColumns[i].field
					+ '" valueType="' + (exportColumns[i].valueType == null ? '':exportColumns[i].valueType) + '"><span class="text">' + exportColumns[i].title + '</span></td>');
			index++;
		}
	}
}

//导出数据
function exportData(){
	var code = $("#veryCode").val();     
    $.ajax({     
        type:"POST",     
        url:ctx+"/com/export/validateCode",     
        data:{"code":code},     
        success : function(data) {
        	if(data.status){
        		exportColumnArray = new Array();
            	//获取导出表单数据
            	var tableData = {};
            	tableData.exportScope = $("input[name=exportScope]:checked").val();
            	tableData.exportFileType = $("input[name=exportFileType]:checked").val();
            	tableData.exportFileName = $("input[name=exportFileName]").val();
            	tableData.veryCode = code;
            	tableData.exportBizParam = eval('(' + $("#exportBizParam").val() + ')');
            	if(tableData.exportScope == "0"){
            		tableData.exportSelectData = eval('(' + $("#exportSelectData").val() + ')');
            	}
            	var fieldList = $("input[name=exportField]:checked");
            	if(fieldList.length == 0){
            		$.messager.alert('系统提示','导出字段不能为空！','warning');
            		return false;
            	}
            	for ( var i = 0; i < fieldList.length; i++) {
            		var column = {};
            		column.field = $(fieldList[i]).val();
            		column.title = $(fieldList[i]).next().html();
            		column.valueType = $(fieldList[i]).attr("valueType");
            		exportColumnArray.push(column);
            	}
            	tableData.exportColumns = exportColumnArray;
            	tableData.exportBizParam.exportStr = encodeURI(JSON.stringify(tableData));
            	postDownLoadFile({
            		url : $("#exportOriUrl").val(),
            		data : tableData.exportBizParam,
            		method : 'post'
            	});
        	}else{
        		$.messager.alert('系统提示',data.msg,'error');
				return false;
        	}
		}
    });    
	
}

// 关闭对话框
function closeDialog(){
	window.parent.closeDialog();
}

var postDownLoadFile = function (options) {
	var config = $.extend(true, {
		method : 'post'
	}, options);
	var $form = $('<form target="down-file-iframe" method="' + config.method
			+ '" ></form>');
	$form.attr('action', config.url);
	for ( var key in config.data) {
		$form.append('<input type="hidden" name="' + key + '" value="'
				+ config.data[key] + '" >');
	}
    $(document.body).append($form);
	$form[0].submit();
	$form.remove();
};

function changeImg(){     
    var imgSrc = $("#imgObj");     
    var src = imgSrc.attr("src");     
    imgSrc.attr("src",chgUrl(src));     
}     
//时间戳     
//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
function chgUrl(url){     
    var timestamp = (new Date()).valueOf();     
    url = ctx+"/com/export/verifyCode?timestamp=" + timestamp;     
    return url;     
}          
