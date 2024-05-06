
$(function(){
	$("#searchForm").validate();
	initData();
});

var appId ;
var dic ;
function InitHpSetDic(){
	appId = $("#appId").val();
	$.ajax({
		type : "post",
		url : ctx + "/sys/hpset/getHpSetRoleCodeBylevel?appId="+appId,
		dataType : 'json',
		cache : false,
		success : function(data) {
			dic = $("#applyRoleDic").ligerDictionary({
				dataAction:'local',
				data : {"Rows":data.name,"Total":data.name.length},
				valueField:'code',//字段值名称
				hiddenField:'applyRoleCode',
				labelField:'name',//选项字段名称
				multi:true,
				multiSplit:',',
				width:'auto',
				resultWidth:180,
			});
		}
	});
}

function initData(){
	var id = $("#id").val();
	if(id.length > 0){
		$.ajax({
			type:"post",
			url:ctx+"/sys/hpset/findById?id=" + id,
			dataType:'json',
			cache:false,
			success:function(data){
				$("#searchForm").form('load', data);
				$("#applyRoleCode").attr("initVal",data.applyRoleCode);
				dicInit(true);
				InitHpSetDic();
			}
		});
	}else{
		dicInit(true);
		InitHpSetDic();
	}
}

function savehpSet(){
	if(!$("#searchForm").valid()){
		return false;
	}
	var tableData = $("#searchForm").serializeObject();
	tableData.applyRoleName = $("#applyRoleDic").val();
	$.ajax({
		type: "post",
		url: ctx+"/sys/hpset/save",
		data: tableData,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('温馨提示','保存成功！','info',function(){
				window.history.go(-1);
				$(".l-btn-icon.pagination-load", window.parent.document).click();
			});
		},
		error: function(){
			$.messager.alert('系统提示','保存失败');
		}
	});
}