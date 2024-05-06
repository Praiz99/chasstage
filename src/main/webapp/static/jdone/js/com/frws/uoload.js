var urls;

$(function(){
	initDataGrid();
});

function initDataGrid(){
	 $.ajax({
			type:'get',
		    url:ctx+'/com/upload/findupload',
		    dataType: 'json',
		    success: function(data){
      	 if(data.success == true) {
      		 urls=data.url;          		 
	     }else {
	        		 $.messager.alert('提示',data.msg, 'error');
	        	}	
      }			
		});
}	

function onsub(){
	alert(urls);
	 $.ajax({
			type:'POST',
		    url:urls+'/com/upload/file',
		    dataType: 'json',
		    data:$('#fileUpload').serialize(), 
		    enctype:'multipart/form-data',
		    success: function(data){
		    	$.messager.alert('提示',"上传成功", 'info');
     }			
		});
}
