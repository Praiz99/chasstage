/*$(function(){
	dicInit(true);
});*/
//选择民警
function moveToRight() {
	var kxmj = $("#listtable1 input[name='kxmj']:checked");
	if (kxmj.size() == 0) {
		$.messager.alert('提示', '请勾选需选择的民警！', 'error');
		return;
	}
	kxmj.each(function() {
		$(this).parent().parent().remove();
		$(this).attr("name","kxmj");
		$("#listtable2").append($(this).parent().parent());
		$("input[name='kxmj']").attr("checked",false);
	});
}
//移除民警
function moveToLeft() {
	var kxmj = $("input[name='kxmj']:checked");
	if (kxmj.size() == 0) {
		$.messager.alert('提示', '请勾选需移除的民警！', 'error');
		return;
	}
	kxmj.each(function() {
		$(this).parent().parent().remove();
		$(this).attr("name","kxmj");
		$("#listtable1").prepend($(this).parent().parent());
		$("input[name='kxmj']").attr("checked",false);
	});
}
//确定所选民警
function confirm_mj() {
	var val = $("#listtable2 input[name='kxmj']");
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
	obj={};
	obj["mjNameStr"] = mjNameStr;
	obj["mjSfzhStr"] = mjSfzhStr;
	obj["mjName"] = $("input[name='mjName']").val();
	obj["sfzh"] = $("input[name='sfzh']").val();
	$("input[name='mjName']").remove();
	$("input[name='sfzh']").remove();
	return obj;
}


function bottomBtnClick(){	 	 
	 var obj = confirm_mj();
	 if(obj){
		if($("input[name='kxmj']:checked").size()==0){
			$.messager.alert('系统提示', '请勾选需选择的民警！', 'info');
			return;
		}
		 closeBtn();
	 }
}
function closeBtn(){
	parent.$(".panel-tool-close").trigger("click");  //关闭页面
}
function search_mj(){
	//var data = $("#mj_searchForm").serializeObject();
	initVal("","","","",true);
}
function initVal(obj1,obj2,obj3,obj4,flag){
	dicInit(true);
	if(typeof flag =="undefined"){
		flag = false;
	}
	$(".searchDiv").append('<input type="hidden" name="sfzh" id="'+obj3+'" value="'+obj3+'">');
	$(".searchDiv").append('<input type="hidden" name="mjName" id="'+obj2+'" value="'+obj2+'">');
	var xbr ="",sfzhVal =[];
	if(obj1 !=undefined && obj1.defaultValue!=""){
		xbr = obj1.split(",");
	}
	if(obj4 !=undefined && obj4.defaultValue!=""){
		sfzhVal = obj4.split(",");
	}
	var data = $("#mj_searchForm").serializeObject();
		$.ajax({
			type : "post",
			url : ctx + "/sys/user/getMjData",
			data : data,
			dataType : 'json',
			cache : false,
			success : function(data) {
				$("#listtable1").html("");
				//$("#listtable2").html("");
				for (var i = 0; i < data.length; i++) {
				  if(xbr.length>0){
				     for(var j =0 ;j<xbr.length;j++){
					    if(data[i].name == xbr[j]){
						data.splice(i,1);
					    };
					};
				  }
				  $("#listtable1").append("<tr><td><input type='checkbox' name='kxmj' value='"+data[i].idCard+"'/></td><td style='white-space: nowrap;'>"+data[i].name+"</td><td>("+data[i].loginId+")"+"</td></tr>");
			    };
			    if(xbr!=""){
				    for(var j =0 ;j<xbr.length;j++){
					    if(flag == false){//<td>("+data[i].loginId+")"+"</td>
					    	if(sfzhVal.length>0){
					    		$("#listtable2").append("<tr><td><input type='checkbox' name='kxmj' value='"+sfzhVal[j]+"'/></td><td>"+xbr[j]+"</td></tr>");
					    	}else{
					    		$("#listtable2").append("<tr><td><input type='checkbox' name='kxmj'/></td><td>"+xbr[j]+"</td></tr>");
					    	}
				    	}
				    }
			    }
			}
		});
	};