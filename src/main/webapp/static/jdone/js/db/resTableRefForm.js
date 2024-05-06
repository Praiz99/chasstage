$(document).ready(function() {
	$("#resTableForm").validate();
	var id = $("#id").val();
	initTableInfo(id);
});

function initTableInfo(id){
	//初始化数据源列表
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

//弹出框显示引用表列表
function showRefTables(){
	var sourceId = $("#dbSourceList option:selected").val();
	if(sourceId == null){
		$.messager.alert("系统提示", "请先选择数据源!");
		return false;
	}
	$('#openRefTables')[0].src = ctx+"/db/resTable/resTableRefList?sourceId=" + sourceId;
    $('#refTablesDialog').dialog('open');
}

//选择引用表后填充表单，并加载表字段datagrid
function setRefTable(data){
	if(data!=null){
		$("input[name=tableMark]").val(data.tableMark);
		$("input[name=tableName]").val(data.tableName);
		initFieldInfo(data.tableMark);
	}
}

//表字段datagrid
function initFieldInfo(tableMark){
	$("#fieldGrid").datagrid({
		url : ctx + "/db/resField/findRefField",
		width : '98%',
		rownumbers : true, // 显示每行列号
		fitColumns : true,
		queryParams: { sourceId: $("#dbSourceList option:selected").val(), tableMark: tableMark}, //查询参数
		emptyMsg: '<span>无记录</span>',
		onLoadSuccess:function(data){
			if (data.total == 0 && data.rows.length == 0) {
			}else{
				for(var i =0;i<data.rows.length;i++){ 
					$("#fieldGrid").datagrid('beginEdit', i);
				}
				$(".datagrid-editable-input").css("height",'22px');
				$("input[type=checkbox]").parent().css("text-align",'center');
				$("input[type=checkbox]").bind('click',function(){
					var flog = 0;
					var checkList = $("input[type=checkbox]");
					for ( var m = 0; m < checkList.length; m++) {
						if(checkList[m].checked == true){
							flog++;
						}
						if(flog > 1) {
							$.messager.alert("系统提示", "您已经设置了一个主键，最多只允许有一个主键！请重新选择。", "error");
							return false;
						}
					}
			    });
			}
	    },
		columns : [ [{
			field : 'isPkField',
			title : '主键',
			align : 'center',
			width: '5%',
			editor:{type:'checkbox',options:{on:'1',off:'0'}}
		}, {
			field : 'fieldMark',
			title : '字段名称',
			align : 'center',
			width : '30%'
		}, {
			field : 'fieldName',
			title : '字段中文名称',
			align : 'center',
			width : '30%',
			editor: { type: 'validatebox',options:{validType:['length[0,25]']}}
		}, {
			field : 'fieldType',
			title : '字段类型',
			align : 'center',
			width : '20%'
		}, {
			field : 'fieldLength',
			title : '字段长度',
			align : 'center',
			width : '15%'
		} ] ]
	});
}

//保存表信息及字段信息
function saveTable(){
	if(!$("#resTableForm").valid() || $(".validatebox-invalid").length>0){
		return false;
	}
	$("#fieldGrid").datagrid("loading");
	for(var i =0;i<$("#fieldGrid").datagrid("getRows").length;i++){
		$("#fieldGrid").datagrid('endEdit', i);
	}
	var tableData = $("#resTableForm").serializeObject();
	var fieldData = $("#fieldGrid").datagrid("getRows");
	var fieldList = "[";
	if(fieldData.length>0){
		fieldList += JSON.stringify(fieldData[0]);
		for (i = 1; i < fieldData.length; i++) {
			fieldList = fieldList + "," + JSON.stringify(fieldData[i]);
		}
	}
	tableData.fieldList = fieldList + "]";
	$.ajax({
		type: "post",
		url: ctx+"/db/resTable/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			window.location.href=ctx + "/db/resTable/resTableList";
		}
	});
}



