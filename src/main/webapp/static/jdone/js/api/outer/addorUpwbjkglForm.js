$(document).ready(function() {
	$("#addorUpwbjkglForm").validate();
	});

function sub(){
	var serviceMark=$("#serviceMark").val();
	var id=$("#id").val();
	if(id==null||id==undefined||id==""){
		$.ajax({
			url:ctx+'/api/outer/findouterbymark?serviceMark='+serviceMark,
			async:false,
			success:function (data){
				if(data.success){
					$.messager.alert("系统提示",data.msg);
					$('#tree1').tree("reload");
				}else{
					  $('#addorUpwbjkglForm').form('submit',{
			        	    url:"save",
			        	    onSubmit: function(){
			        	    	var isValid = !$("#addorUpwbjkglForm").valid();
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
			        	        			window.location.href="outerList";
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
		 $('#addorUpwbjkglForm').form('submit',{
     	    url:"save",
     	    onSubmit: function(){
     	    	var isValid = !$("#addorUpwbjkglForm").valid();
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
     	        			window.location.href="outerList";
     	        	});
     	    	}else{
     	    		$.messager.alert('提示', data.msg, 'error');
     	    	}
     			

     	    }
     });
	}
}