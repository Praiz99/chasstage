$(document).ready(function() {
	$("#number").show();
	$.ajaxSetup({cache:false});
	$("#orgForm").validate();
	var id = $("#id").val();
	var opType = $("#opType").val();
	if(opType != ""){
		$("#btnCancel").hide();
	}
	InitOrgDic();
	initData(id);
	$(".fzrSfz").change(function(){
		var fzrSfz = $(".fzrSfz").val();
		if(fzrSfz !="" || fzrSfz != null){
			$.ajax({
				type: "post",
				url: ctx+"/sys/org/findSysUserByIdCard?fzrSfz="+fzrSfz,
				dataType: 'json',
				cache: false,
				success: function(data) {
					$('#fzrXm').val(data.name);
				}
			});
			}
	});
	
});

function initData(id){
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/sys/org/findById?id="+id,
			dataType: 'json',
			cache: false,
			success: function(data) {
				$('#orgForm').form('load', data);
				if(data.sysCode!=null && data.sysCode!=""){
					$("#number").hide();
				}
				$("#jzflDic").attr("initVal", data.jzfl);
				if(data.pid != null && data.pid != ''){
					$.ajax({
						type: "post",
						url: ctx+"/sys/org/findById?id="+data.pid,
						dataType: 'json',
						cache: false,
						success: function(result) {
							$("#nameDic").val(result.name);
						}
					});
				}
				$("#dwjbDic").attr("initVal", data.dwjb);
				dicInit(true);
			}
		});
	}else{
		dicInit(true);
	}
}

function orgChange(){
	nameDic.changeOptions({"url":ctx + "/sys/org/findPageList","parms":{name:$("#nameDic").val()}});
}

function InitOrgDic(){
	nameDic = $("#nameDic").ligerDictionary({
		dataAction:'server',
		url : ctx + "/sys/org/findPageList",
		pageParmName:'page',//向服务端请求数据时 提交请求当前页的参数名称
		pagesizeParmName:'rows', //每页展示记录数参数名称
		totalName:'total',
		recordName:'rows',
		method:'POST',
		page:1,				//当前页
		pageSize:10,		//每页展示记录数
		valueField:'id',// 字段值名称
		labelField:'name',// 选项字段名称
		searchField:'name,code',
		hiddenField:'pid',
		resultWidth:300
	});
}

function saveOrg(){
	if($("#orgSel").val()==""){
		$("#pid").val("");
	}
	if(!$("#orgForm").valid()){
		return false;
	}
	var id = $("#id").val();
	var pid = $("#pid").val();
	if(id != null && id != "" && id == pid){
		$.messager.alert("系统提示", "上级机构不能选择自己，请重新选择!");
		return false;
	}
	var tableData = $("#orgForm").serializeObject();
	if(id != null && id != ""){
		$.ajax({
			type: "post",
			url: ctx+"/sys/org/findSysCode",
			data: tableData,
			dataType: 'json',
			cache: false,
			success: function(data) {
				if(!data.msg){
					$.messager.confirm('系统提示', '是否把该映射关系插入到映射表中或者重新生成?', function(r) {
			            if (r) {
							$.ajax({
								type: "post",
								url: ctx+"/sys/org/saveSysCode",
								data: tableData,
								dataType: 'json',
								cache: false,
								success: function(data) {
									saveOrgForm(tableData);
								}
							});
			            }
			        });
				}else{
					saveOrgForm(tableData);
				}
			}
		});	
	}else{
		$.ajax({
			type: "post",
			url: ctx+"/sys/org/findSysCode",
			data: tableData,
			dataType: 'json',
			cache: false,
			success: function(data) {
				if(!data.isOrg){
					$.messager.alert("系统提示", "机构代码已存在，请勿重复添加!");
					return false;
				}else{
					saveOrgForm(tableData);
				}
	}
});
}}

function saveOrgForm(data){
	$.ajax({
		type: "post",
		url: ctx+"/sys/org/save",
		data: data,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','保存成功！','info',function(){
				if(data.opType == "2"){
					$('#orgForm').form('load', data.sysorg);
					$('.icon-reload', parent.document).click();
				}else{
					window.location.href=ctx + '/sys/org/orgList';
				}
			});
		}
	});
}
function generateNumbering(){
	var pid = $("#pid").val();
	if(pid == null || pid == ""){
		$.messager.alert("系统提示", "请先选择上级机构!");
		return false;
	}
	var pid = $("#code").val();
	if(pid == null || pid == ""){
		$.messager.alert("系统提示", "请先输入代码!");
		return false;
	}
	var tableData = $("#orgForm").serializeObject();
	$.ajax({
		type: "post",
		url: ctx+"/sys/org/getSysCode",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$("#sysCode").val(data.sysCode);
		}
	});
}

