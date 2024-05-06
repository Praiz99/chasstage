$(function() {
	$("#ff").validate();
	var inputType = $("input[name='inputType']:checked").val();
	inputChange(inputType);
	var outputType = $("input[name='outputType']:checked").val();
	outputChange(outputType);
	getDeployList();
	$('#tt').tabs({
		border : false,
		onSelect : function(title) {
			if (title == "输入字段配置") {
				enterInputDeployPage();
			}
			if (title == "输出字段配置") {
				enterOutputDeployPage();
			}
			if (title == "输入参数配置") {
				enterParamDeployPage();
			}
		}
	});
});

function submitForm() {
	$('#ff').form('submit', {
		url : "addTaskBatchinstData",
		onSubmit : function() {
			var isValid = !$("#ff").valid()
			if (isValid) {
				return false; // hide progress bar while the form is invalid
			}
			return true; // return false will stop the form submission
		},
		success : function(data) {
			var data = eval('(' + data + ')'); // change the JSON string to
			// javascript object
			if (data.success) {
				$.messager.alert('提示', data.msg, 'info', function() {
					window.location.href = ctx + "/task/taskBatchinstList";
				});
			} else {
				$.messager.alert('提示', data.msg, 'error');
			}
		}
	});
}
function getDeployList(){
	$.ajax({
		type : "post",
		url : "getTaskDeployName",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for(var i =0;i<data.length;i++){ 
				$("#deployList").append($("<option/>").text(data[i].text).attr("value",data[i].name));
			}
		}
	});
}

function loadJobAndTypeOption(){
	var dId = $("#deployList").val();
	loadTypeOption(dId);
}

function loadTypeOption(deployId){
	if(deployId != ""){
		$.ajax({
			type : "post",
			url : "getTaskTypeName?deployId=" + deployId,
			dataType : 'json',
			cache : false,
			success : function(data) {
				$("#taskTypeList").html("<option value='' selected='selected'></option>");
				for(var i =0;i<data.length;i++){ 
					$("#taskTypeList").append($("<option/>").text(data[i].text).attr("value",data[i].name));
				}
			}
		});
	}else{
		$("#taskTypeList").html("<option value='' selected='selected'></option>");
	}
}

function getSourceList(id) {
	$.ajax({
		type : "post",
		url : ctx + "/db/source/findAll",
		dataType : 'json',
		cache : false,
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				$(id).append(
						$("<option/>").text(data[i].sourceName).attr("value",
								data[i].id));
			}
		}
	});
}

function showTables(type) {
	var id;
	if (type == 1) {
		id = "#inputSourceList option:selected";
	} else {
		id = "#outputSourceList option:selected";
	}
	var sourceId = $(id).val();
	if (sourceId == null) {
		$.messager.alert("系统提示", "请先选择数据源!");
		return false;
	}
	$('#openRefTables')[0].src = ctx + "/task/tableList?sourceId=" + sourceId
			+ "&type=" + type;
	$('#refTablesDialog').dialog('open');
}

function setRefTable(data, type) {
	if (data != null) {
		var id;
		if (type == "1") {
			id = "input[name=inputTable]";
		} else {
			id = "input[name=outputTable]";
		}
		$(id).val(data.tableMark);
	}
}

function inputChange(val) {
	switch (val) {
	case "0":
		$(".0").css("display", "");
		$(".1").css("display", "none");
		$("#inputSourceList,#deployInputSourceList").empty();
		$("#inputSourceList,#deployInputSourceList").append(
				"<option value='' selected='selected'></option>");
		$("#inputTable,#deployInputTable").val("");
		$("#HttpField").attr("disabled", false);
		break;

	case "1":
		$(".1").css("display", "");
		$(".0").css("display", "none");
		$("#inputUrl").val("");
		$("#HttpField").attr("disabled", "disabled");
		var id = "#inputSourceList,#deployInputSourceList";
		getSourceList(id);
		break;
	}

}

function outputChange(val) {
	switch (val) {
	case "0":
		$(".2").css("display", "");
		$(".3").css("display", "none");
		$("#outputSourceList,#deployOutputSourceList").empty();
		$("#outputSourceList,#deployOutputSourceList").append(
				"<option value='' selected='selected'></option>");
		$("#outputTable,#deployOutputTable").val("");
		// 清空表格所有的行
		$("#deployOutputTab tr").remove();
		break;

	case "1":
		$(".3").css("display", "");
		$(".2").css("display", "none");
		$("#outputUrl").val("");
		// 清空表格所有的行
		$("#deployOutputTab tr").remove();
		var id = "#outputSourceList,#deployOutputSourceList";
		getSourceList(id);
		break;
	}

}

// 进入输入字段配置页面
function enterInputDeployPage() {
	var inputType = $("input[name='inputType']:checked").val();
	$(":radio[name='deployInputType'][value='" + inputType + "']").prop(
			"checked", "checked");
	if (inputType == "0") {
		var inputUrl = $("#inputUrl").val();
		$("#deployInputUrl").val(inputUrl);
		$("#deployInputTab").html("");
	} else if (inputType == "1") {
		var inputSource = $("#inputSourceList").val();
		var deployInputSource = $("#deployInputSourceList").val();
		var inputTable = $("#inputTable").val();
		var deployInputTable = $("#deployInputTable").val();
		// 如果数据源或选择的表发生改变，则重新加载表字段信息
		if (inputSource != deployInputSource || inputTable != deployInputTable) {
			$("#deployInputSourceList").val(inputSource);
			$("#deployInputTable").val(inputTable);
			$("#HttpField").val("");
			$("#deployInputTab").html("");
			getFieldData(inputSource, inputTable, "#deployInputTab");
		}
	}
}

// 根据数据源表名获取字段数据
function getFieldData(inputSource, inTable, id) {
	$
			.ajax({
				type : "post",
				url : ctx + "/db/resField/findRefField?sourceId=" + inputSource
						+ "&tableMark=" + inTable,
				dataType : 'json',
				cache : false,
				success : function(data) {
					var fieldList = data.rows;
					if (id == "#deployInputTab") {
						var str = "<tr>";
						for (var i = 0; i < fieldList.length; i++) {
							str += "<td style='border:1px solid white;'><input type='checkbox' name='inputField' value='"
									+ fieldList[i].fieldMark
									+ "'/>"
									+ fieldList[i].fieldMark;
							+"</td>"
							if (i != 0 && (i + 1) % 5 == 0
									&& i != fieldList.length) {
								str += "</tr>"
							}
							if (i == fieldList.length) {
								str += "</tr>"
							}
						}
						$("#deployInputTab").append(str);
					} else if (id == "#deployOutputTab") {
						var str = "<tr><td style='text-align: center;width: 50%'>输出表字段</td>"
								+ "<td style='text-align: center;width: 50%'>输入表字段</td></tr>";
						for (var i = 0; i < fieldList.length; i++) {
							str += "<tr><td style='text-align: center;width: 50%'>"
									+ "<input type='text' style='text-align: center;width: 50%;border:1px solid #f9f9f9' "
									+ "readonly='readonly' name='outputFields' value="
									+ fieldList[i].fieldMark
									+ "></td>"
									+ "<td style='text-align: center;width: 50%'>"
									+ "<select name='inputFields' style='float: none;border:1px solid #b7d2ff' onclick='addOption(this)'>"
									+ "<option value='' selected='selected'></option></select></td></tr>";
						}
						$("#deployOutputTab").append(str);
					}
				}
			});
}

// 进入输出页面配置页面
function enterOutputDeployPage() {
	var outputType = $("input[name='outputType']:checked").val();
	$(":radio[name='deployOutputType'][value='" + outputType + "']").prop(
			"checked", "checked");
	if (outputType == "0") {
		var outputUrl = $("#outputUrl").val();
		$("#deployOutputUrl").val(outputUrl);
		var trLength = $("#deployOutputTab").find("tr").length;
		if (trLength == 0) {
			var addHTML = "<tr><td style='text-align: center;width: 6%'><input id='allCkb' onclick='allCheck(0)' type='checkbox'></td>"
					+ "<td style='text-align: center;width: 47%'>输出表字段</td>"
					+ "<td style='text-align: center;width: 47%'>输入表字段</td></tr>"
					+ "<tr><td colspan='3' style='text-align:center'><a href='#' onclick='addTr(0)'>添加</a>&nbsp"
					+ "<a href='#' onclick='delTr(0)'>删除</td></tr>";
			$("#deployOutputTab").append(addHTML);
		}
	} else if (outputType == "1") {
		var outputSource = $("#outputSourceList").val();
		var deployOutputSource = $("#deployOutputSourceList").val();
		var outputTable = $("#outputTable").val();
		var deployOutputTable = $("#deployOutputTable").val();
		$("#deployOutputSourceList").val(outputSource);
		$("#deployOutputTable").val(outputTable);
		// 如果数据源或选择的表发生改变，则重新加载表字段信息
		if (outputSource != deployOutputSource
				|| outputTable != deployOutputTable) {
			// 清空表格所有的行
			$("#deployOutputTab tr").remove();
			getFieldData(outputSource, outputTable, "#deployOutputTab");
		}
	}
}

// 进入输入参数配置页面
function enterParamDeployPage() {
	var trLength = $("#deployParamTab").find("tr").length;
	if (trLength == 0) {
		var addHTML = "<tr><td style='text-align: center;width: 7%'><input id='allCkb_paramPage' onclick='allCheck(1)' type='checkbox'></td>"
				+ "<td style='text-align: center;width: 41%'>字段名</td>"
				+ "<td style='text-align: center;width: 11%'>运算符</td>"
				+ "<td style='text-align: center;width: 41%'>字段值</td></tr>";
		$("#deployParamTab").append(addHTML);
	}
}

// 添加行
function addTr(val) {
	var trHTML;
	if (val == '0') {
		trHTML = "<tr><td style='text-align: center;width: 6%'><input type='checkbox' name='ckb'></td>"
				+ "<td style='text-align: center;width: 47%'>"
				+ "<input type='text' name='outputFields' style='border:1px solid #b7d2ff;text-align: center'/></td>"
				+ "<td style='text-align: center;width: 47%'>"
				+ "<select name='inputFields' style='float: none;border:1px solid #b7d2ff' onclick='addOption(this)'>"
				+ "<option value='' selected='selected'></option></select></td></tr>";
		// 在最后行前面插入一行
		$("#deployOutputTab tr:last").before(trHTML);
	} else {
		trHTML = "<tr><td style='text-align: center;width: 7%'><input type='checkbox' name='ckb_paramPage'></td>"
				+ "<td style='text-align: center;width: 41%'>"
				+ "<input type='text' name='paramNames' style='border:1px solid #b7d2ff;text-align: center'/></td>"
				+ "<td style='text-align: center;width: 11%'>"
				+ "<select name='operators' style='float: none;border:1px solid #b7d2ff'>"
				+ "<option value='' selected='selected'></option>"
				+ "<option value='=' >=</option>"
				+ "<option value='<>' ><></option>"
				+ "<option value='>' >></option>"
				+ "<option value='<' ><</option>"
				+ "<option value='>=' >>=</option>"
				+ "<option value='<=' ><=</option>"
				+ "<option value='BETWEEN' >BETWEEN</option>"
				+ "<option value='LIKE' >LIKE</option>"
				+ "<option value='IN' >IN</option>"
				+ "<option value='IS' >IS</option></select></td>"
				+ "<td style='text-align: center;width: 41%'>"
				+ "<input type='text' name='paramValues' style='border:1px solid #b7d2ff;text-align: center'/></td></tr>";
		// 插入数据
		$("#deployParamTab tr:last").after(trHTML);
	}
}

// 初始化下拉框
function addOption(val) {
	var textContent = val.textContent;
	var inputFieldStr = "";
	var inputType = $("input[name='inputType']:checked").val();
	if (inputType == "0") {
		inputFieldStr = $("input[name='inputField']").val();
	} else if (inputType == "1") {
		if(val.id == "updateFieldList"){
			var id = "input[name='inputField']";
		}else{
			var id = "input[name='inputField']:checked";
		}
		$(id).each(function() {
			inputFieldStr += $(this).val() + ",";
		})
		// 截掉最后一个","
		inputFieldStr = inputFieldStr.substr(0, inputFieldStr.length - 1);
	}
	// 替换掉所有的","
	var inputFieldContent = inputFieldStr.replace(/,/g, "");
	// 如果输入字段改变，则重新初始化下拉框
	if (inputFieldContent != textContent) {
		var selectStr = "<option value='' selected='selected'></option>";
		var fields = inputFieldStr.split(",");
		if (inputFieldStr != "") {
			for (var i = 0; i < fields.length; i++) {
				selectStr += "<option value=" + fields[i] + ">" + fields[i]
						+ "</option>";
			}
		}
		val.innerHTML = selectStr;
	}
}

function addUpdateFieldOption(val){
	var textContent = val.textContent;
	var	inputFieldStr = $("input[name='inputField']").val();
	// 替换掉所有的","
	var inputFieldContent = inputFieldStr.replace(/,/g, "");
	// 如果输入字段改变，则重新初始化下拉框
	if (inputFieldContent != textContent) {
		var fields = inputFieldStr.split(",");
		if (inputFieldStr != "") {
			for (var i = 0; i < fields.length; i++) {
				$("#updateFieldList").append("<option value=" + fields[i] + ">" + fields[i] + "</option>");
			}
		}
	}
}

// 删除行
function delTr(val) {
	var name;
	if (val == '0') {
		name = "input[name='ckb']:checked";
	} else {
		name = "input[name='ckb_paramPage']:checked";
	}
	// 删除选中的行
	var ckbs = $(name);
	if (ckbs.size() == 0) {
		$.messager.alert('提示', '请选中要删除的行！', 'error');
		return;
	}
	ckbs.each(function() {
		$(this).parent().parent().remove();
	});
}

// 全选
function allCheck(val) {
	var id;
	var ckb;
	if (val == '0') {
		id = '#allCkb';
		ckb = "input:checkbox[name='ckb']";
	} else {
		id = '#allCkb_paramPage';
		ckb = "input:checkbox[name='ckb_paramPage']";
	}
	if ($(id).is(':checked')) {
		$(ckb).prop("checked", "checked");
	} else {
		$(ckb).prop("checked", "");
	}
}
