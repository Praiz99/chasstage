$(function(){
	$("#ff").validate();
	getDeployList();
});  


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

function loadTypeOption(){
	var deployId = $("#deployList").val();
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

function submitForm(){
    
    $('#ff').form('submit',{
      	    url:"fileUpload",
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
      	    			window.location.href = ctx+"/task/uploadRecordList";
    	        	});
      	    	}else{
      	    		$.messager.alert('提示', data.msg, 'error');
      	    	}
      			

      	    }
      });
      	
    }
 

 
