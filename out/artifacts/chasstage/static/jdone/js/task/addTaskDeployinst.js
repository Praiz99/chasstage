$(function(){
	$("#ff").validate();
});  

function submitForm(){
	  var deployId = document.getElementById("deployId").value      
      $('#ff').form('submit',{
        	    url:"addTaskDeployinstData",
        	    onSubmit: function(){
        	    	var isValid = !$("#ff").valid()
    				if (isValid){
    					return false;		// hide progress bar while the form is invalid
    				}
    				return true;;	// return false will stop the form submission
        	    },
        	    success:function(data){
        			var data = eval('(' + data + ')'); // change the JSON string to javascript object
        	    	if(data.success){
        	    		$.messager.alert('提示', data.msg, 'info',function(){
//        	        			window.location.reload();
        	    			window.location.href = ctx+"/task/taskDeployinstList?taskDeployId="+deployId;
        	        	});
//                        $('#ff').form('clear');
        	    	}else{
        	    		$.messager.alert('提示', data.msg, 'error');
        	    	}
        			

        	    }
        });
        	
      }
   

   
  