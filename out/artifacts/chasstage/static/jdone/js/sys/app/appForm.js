$(function (){
	$("html body").css({"width":"99%","height":"100%"});
});

function Submit(){
	var obj = {};
	obj.name = $("#name").val();
	obj.isDisabled = $("input[name='isDisabled']:checked").val();
	obj.mark = $("#mark").val();
	obj.appLevel = $("select[name='appLevel'] option:selected").val();
	obj.id = $("#id").val();
	obj.orderId = $("#orderId").val();
	obj.indexUrl = $("#indexUrl").val();
	$.ajax({ 
		url:'saveApp',
		type:'POST',
		dataType:'json',
		data:obj,
		success : function (data){
			parent.$('#datagrid').datagrid('reload');
			if(data.success){
				top.$.messager.alert('提示',data.msg);
			}else{
				top.$.messager.alert('提示',data.msg);
			}
			parent.$(".panel-tool-close").trigger("click");  //关闭页面
		}
	});
}

function Close(){
	parent.$(".panel-tool-close").trigger("click");  //关闭页面
}

function NametoPy(){
	var py = $("#name").toPinyin();
	if(py == ""){
		return false;
	}
	py = py.split(" ");
	var pyv = "";
	$(py).each(function (i,o){
		if(o != ""){
			pyv += o.split("")[0].toLocaleLowerCase();
		}
	});
	$("#mark").val(pyv);
}
