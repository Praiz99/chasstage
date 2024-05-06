function submit(){
	$('#addorUplicenseForm').form('submit',{
		success:function(data){
			window.location.href = ctx+"/api/inner/licenseList";
		}
	});
	
}

function sub(){
	var serviceMark=$("#serviceMark").val();
	var id=$("#id").val();
	if(id==null||id==undefined||id==""){
		$.ajax({
			url:ctx+'/api/inner/findinnerbymark?serviceMark='+serviceMark,
			async:false,
			success:function (data){
				if(data.success){
					$.messager.alert("系统提示",data.msg);
					$('#tree1').tree("reload");
				}else{
					 $('#addorUpinnerForm').form('submit',{
			        	    url:"saveinner",
			        	    onSubmit: function(){
			        	    	var isValid = !$("#addorUpinnerForm").valid();
			    				if (isValid){
			    					return false;		
			    				}
			    				return true;
			        	    },
			        	    success:function(data){
			        			var data = eval('(' + data + ')');
			        	    	if(data.success){
			        	    		$.messager.alert('提示', data.msg, 'info',function(){
			        	        			window.location.reload();
			        	        			window.location.href="innerList";
			        	        	});
			        	    	}else{
			        	    		$.messager.alert('提示', data.msg, 'error');
			        	    	}
			        			

			        	    }
			        });
				}
			}
		});
	}else{
		 $('#addorUpinnerForm').form('submit',{
     	    url:"saveinner",
     	    onSubmit: function(){
     	    	var isValid = !$("#addorUpinnerForm").valid();
 				if (isValid){
 					return false;		
 				}
 				return true;
     	    },
     	    success:function(data){
     			var data = eval('(' + data + ')');
     	    	if(data.success){
     	    		$.messager.alert('提示', data.msg, 'info',function(){
     	        			window.location.reload();
     	        			window.location.href="innerList";
     	        	});
     	    	}else{
     	    		$.messager.alert('提示', data.msg, 'error');
     	    	}
     			

     	    }
     });
	}
}


function licensesub(){
		 $('#addorUplicenseForm').form('submit',{
     	    url:"save",
     	    onSubmit: function(){
     	    	var isValid = !$("#addorUplicenseForm").valid();
 				if (isValid){
 					return false;		
 				}
 				return true;
     	    },
     	    success:function(data){
     			var data = eval('(' + data + ')');
     	    	if(data.success){
     	    		$.messager.alert('提示', data.msg, 'info',function(){
     	        			window.location.reload();
     	        			window.location.href="licenseList";
     	        	});
     	    	}else{
     	    		$.messager.alert('提示', data.msg, 'error');
     	    	}
     			

     	    }
     });
}