//打开人员选择器
function openMjChooser(id) {
	$("#num").val(id);
	$('#win_mj').window({
		collapsible:false,
		minimizable:false,
		maximizable:false,
		draggable:false,
		resizable:false,
		modal:true
	});
	$("#win_iframe_mj").attr("src", ctx + "/jdone/select/mj/openMjSelect");
}
//选择民警
function moveToRight() {
	var kxmj = $("#win_iframe_mj").contents().find("input[name='kxmj']:checked");
	if (kxmj.size() == 0) {
		$.messager.alert('提示', '请勾选需选择的民警！', 'error');
		return;
	}
	kxmj.each(function() {
		$(this).parent().parent().remove();
		$(this).attr("name","dsrmj");
		$("#win_iframe_mj").contents().find("#listtable2").append($(this).parent().parent());
	});
}
//移除民警
function moveToLeft() {
	var dsrmj = $("#win_iframe_mj").contents().find("input[name='dsrmj']:checked");
	if (dsrmj.size() == 0) {
		$.messager.alert('提示', '请勾选需移除的民警！', 'error');
		return;
	}
	dsrmj.each(function() {
		$(this).parent().parent().remove();
		$(this).attr("name","kxmj");
		$("#win_iframe_mj").contents().find("#listtable1").append($(this).parent().parent());
	});
}
//确定所选民警
function confirm_mj() {
	var val = $("#win_iframe_mj").contents().find("input[name='dsrmj']");
	var mjNameStr = "";
	var mjSfzhStr = "";
	val.each(function() {
		mjNameStr +=  $(this).parent().next().text() + ",";
		mjSfzhStr +=  $(this).val() + ","; 
	});
	if(mjNameStr != "" && mjSfzhStr != ""){
		// 截掉最后一个","
		mjNameStr = mjNameStr.substr(0, mjNameStr .length - 1);
		mjSfzhStr = mjSfzhStr.substr(0, mjSfzhStr .length - 1);
	}
	var id = $("#num").val();
	try{
		$("#clrXm"+id).val(mjNameStr);
		$("#clrSfzh"+id).val(mjSfzhStr);
	}catch(e){
	}
	try{
		$("#clrXm"+id).text(mjNameStr);
		$("#clrSfzh"+id).text(mjSfzhStr);
	}catch(e){
	}
	try{
		$("#clrXm"+id).html(mjNameStr);
		$("#clrSfzh"+id).html(mjSfzhStr);
	}catch(e){
	}
	$('#win_mj').window('close');
}

//根据条件查询可选民警
function search_mj() {
	var data = $("#win_iframe_mj").contents().find("#mj_searchForm").serializeObject();
	$.ajax({
		type : "post",
		url : ctx + "/jdone/select/mj/getMjData",
		data : data,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#win_iframe_mj").contents().find("#listtable1").html("");
			for (var i = 0; i < data.length; i++) {
				var html = "<tr><td><input type='checkbox' name='kxmj' value='"+data[i].idCard+"'/></td><td>"+data[i].name+"</td></tr>";
				$("#win_iframe_mj").contents().find("#listtable1").append(html);
			}
		}
	});
}
