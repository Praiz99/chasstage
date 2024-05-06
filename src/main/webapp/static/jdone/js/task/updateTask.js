$(function(){
	$("#ff").validate();
	getDeployList();
	var strategyType = $("#strategyType").val();
	$(":radio[name='strategyType'][value='" + strategyType + "']").prop("checked", "checked");
	ChangeCSS(strategyType);
	initParamsTab();
});

function initParamsTab(){
	var addHTML = "<tr><td style='text-align: center;width: 6%'><input id='allCkb' onclick='allCheck()' type='checkbox'></td>"
		+ "<td style='text-align: center;width: 47%'>key</td>"
		+ "<td style='text-align: center;width: 47%'>value</td></tr>"
		+ "<tr><td colspan='3' style='text-align:center'><a href='#' onclick='addTr()'>添加</a>&nbsp"
		+ "<a href='#' onclick='delTr()'>删除</td></tr>";
	$("#paramsTab").append(addHTML);
	//加载任务参数信息
	var paramKeyStr = $("#paramKeyStr").val();
	var paramValueStr = $("#paramValueStr").val();
	if(paramKeyStr != "" && paramValueStr != ""){
		var paramKeys = paramKeyStr.split(","); 
		var paramValues = paramValueStr.split(",");
		for(var i = 0;i < paramKeys.length;i++){
			trHTML = "<tr><td style='text-align: center;width: 6%'><input type='checkbox' name='ckb'></td>"
		 		+ "<td style='text-align: center;width: 47%'>"
		 		+ "<input type='text' name='keys' style='border:1px solid #b7d2ff;text-align: center' value='"+paramKeys[i]+"'/></td>"
		 		+ "<td style='text-align: center;width: 47%'>"
		 		+ "<input type='text' name='values' style='border:1px solid #b7d2ff;text-align: center' value='"+paramValues[i]+"'/></td></tr>";
		 	// 在最后行前面插入一行
		 	$("#paramsTab tr:last").before(trHTML);
		}
		
	}
}

function loadJobAndTypeOption(){
	var dId = $("#deployList").val();
	getJobList(dId);
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
				var taskType = $("#taskType").val();
				$("#taskTypeList").val(taskType); 
			}
		});
	}else{
		$("#taskTypeList").html("<option value='' selected='selected'></option>");
	}
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
			var deployId = $("#deployId").val();
			$("#deployList").val(deployId);
			loadTypeOption(deployId);
			getJobList(deployId);
			
		}
	});
}

function getJobList(dId){
	$.ajax({
		type : "post",
		url : "getJobImpClassData?deployId=" + dId,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#jobList").html("<option value='' selected='selected'></option>");
			for(var i =0;i<data.length;i++){ 
				if(typeof(data[i].text) != "undefined"){
					$("#jobList").append($("<option/>").text(data[i].text).attr("value",data[i].name));
				}
			}
			var jobImpClass = $("#jobImpClass").val();
			$("#jobList").val(jobImpClass);
		}
	});
}

function submitForm(){
    $('#ff').form('submit',{
      	    url:"updateTaskData",
      	    onSubmit: function(){
      	    	var isValid = !$("#ff").valid()
				if (isValid){
					return false;		// hide progress bar while the form is invalid
				}
				return true;	// return false will stop the form submission
      	    },
      	    success:function(data){
      			var data = eval('(' + data + ')'); // change the JSON string to javascript object
      	    	if(data.success){
      	    		$.messager.alert('提示', data.msg, 'info',function(){
      	    			window.location.href = ctx+"/task/taskTableList";
    	        	});
      	    	}else{
      	    		$.messager.alert('提示', data.msg, 'error');
      	    	}
      			

      	    }
      });
      	
    }
 
 function ChangeCSS(val){
     switch (val) {
	case "0":
		$(".1").css("display","");
		$(".2").css("display","none");
		$(".3").css("display","none");
		$(".4").css("display","none");
		break;
		
	case "1":
		$(".1").css("display","");
		$(".2").css("display","none");
		$(".3").css("display","none");
		$(".4").css("display","none");
		break;
		
	case "2":
		var week = document.getElementById("week").value;
		if(week != ""){
			var weeks = week.split(","); 
			for(var i=0; i<weeks.length; i++){
				$("input:checkbox[name='week'][value='"+weeks[i]+"']").prop('checked','true');
			}
		}
		$(".0").css("display","none");
		$(".1").css("display","");
		$(".2").css("display","");
		$(".3").css("display","none");
		$(".4").css("display","none");
		break;
		
	case "3":
		var month = document.getElementById("month").value;
		if(month != ""){
			var months = month.split(","); 
			for(var i=0; i<months.length; i++){
				$("input:checkbox[name='month'][value='"+months[i]+"']").prop('checked','true');
			}
		}
		$(".1").css("display","");
		$(".2").css("display","none");
		$(".3").css("display","");
		$(".4").css("display","none");
		break;
		
	case "4":
		$(".1").css("display","none");
		$(".2").css("display","none");
		$(".3").css("display","none");
		$(".4").css("display","");
		break;
     }
     
  }
 
//添加行
 function addTr() {
 	trHTML = "<tr><td style='text-align: center;width: 6%'><input type='checkbox' name='ckb'></td>"
 		+ "<td style='text-align: center;width: 47%'>"
 		+ "<input type='text' name='keys' style='border:1px solid #b7d2ff;text-align: center'/></td>"
 		+ "<td style='text-align: center;width: 47%'>"
 		+ "<input type='text' name='values' style='border:1px solid #b7d2ff;text-align: center'/></td></tr>";
 	// 在最后行前面插入一行
 	$("#paramsTab tr:last").before(trHTML);
 }
 //删除行
 function delTr() {
 	// 删除选中的行
 	var ckbs = $("input[name='ckb']:checked");
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
 	if ($('#allCkb').is(':checked')) {
 		$("input:checkbox[name='ckb']").prop("checked", "checked");
 	} else {
 		$("input:checkbox[name='ckb']").prop("checked", "");
 	}
 } 