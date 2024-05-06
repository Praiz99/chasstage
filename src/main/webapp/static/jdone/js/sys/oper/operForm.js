function Submit(){
	CheckedName();
	if(isalike || isname){  //如果标识已有存在的则不让新增保存.
		return false;
	}
	var obj = {};
	obj.id = $("#id").val();
	obj.fid = $("#fid").val();
	obj.groupId = getQueryString("treeId");
	obj.name = $("#name").val();
	obj.mark = $("#mark").val();
	obj.isDisabled = $("input[name='isDisabled']:checked").val();
	obj.isEnableAuth = $("input[name='isEnableAuth']:checked").val();
	$.ajax({ 
		url:'saveOper',
		type:'POST',
		dataType:'json',
		data:obj,
		success : function (data){
			parent.initZtree();
			parent.oldtreeId = obj.groupId;
			if(data.success){
				parent.$.messager.alert('提示',data.msg);
			}else{
				parent.$.messager.alert('提示',data.msg);
			}
			$("#datagrid").datagrid("reload");
			if(typeof(win) != "undefined"){
				win.dialog("close");
			}
		}
	});
}
var isname = false;
function CheckedName(){
	if($("#name").val() == ""){
		isname = true;
		$(".namerror").remove();
		$("#name").after('<label style="position: absolute;left: 83%;top: 11%;" class="error namerror">必填信息!</label>');
	}else{
		isname = false;
		$(".namerror").remove();
	}
	if($("#mark").val() == ""){
		NametoPy();
	}
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
	CheckedIsalike();
}
var isalike = false;
function CheckedIsalike(){
	$.ajax({
		url: ctx + '/sys/oper/checkedMarkIsalike',
		data:{mark:$("#mark").val()},
		dataType:'json',
		type:'POST',
		async:false,
		success:function (data){
			if(data.flag){
				isalike = true;
				$(".error").remove();
				$("#mark").after('<label style="position: absolute;left: 79%;top: 20%;" class="error">该标识已存在!</label>');
			}else{
				isalike = false;
				$(".error").remove();
			}
		}
	});
}

function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 