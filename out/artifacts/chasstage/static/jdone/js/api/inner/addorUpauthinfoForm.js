$(function() {
	$.ajaxSetup({cache:false});
       var ip=ips;
       var cal=call;
       var occ=occurs;
       if(ip==0){
    	   document.getElementById('ipList').readOnly=true;
    	   document.getElementById('ipList').style.backgroundColor='#CCCCCC';
       }else{
    	   document.getElementById('ipList').readOnly=false; 
    	   document.getElementById('ipList').style.backgroundColor='white';
       }
       
       if(cal==0){
    	   document.getElementById('maxCallCount').readOnly=true;
    	   document.getElementById('maxCallCount').style.backgroundColor='#CCCCCC';
       }else{
    	   document.getElementById('maxCallCount').readOnly=false; 
    	   document.getElementById('maxCallCount').style.backgroundColor='white';
       }
       
       if(occ==0){
    	   document.getElementById('maxOccurs').readOnly=true;
    	   document.getElementById('maxOccurs').style.backgroundColor='#CCCCCC';
       }else{
    	   document.getElementById('maxOccurs').readOnly=false; 
    	   document.getElementById('maxOccurs').style.backgroundColor='white';
       }
});

function isIpClick(){
    		document.getElementById('ipList').readOnly=true;
    		document.getElementById('ipList').style.backgroundColor='#CCCCCC';
}

function isIpClicks(){
	document.getElementById('ipList').readOnly=false;
	 document.getElementById('ipList').style.backgroundColor='white';
}

function isCall(){
	document.getElementById('maxCallCount').readOnly=true;
	document.getElementById('maxCallCount').style.backgroundColor='#CCCCCC';
}

function isCalls(){
document.getElementById('maxCallCount').readOnly=false;
document.getElementById('maxCallCount').style.backgroundColor='white';
}

function isOccurs(){
	document.getElementById('maxOccurs').readOnly=true;
	document.getElementById('maxOccurs').style.backgroundColor='#CCCCCC';
}

function isOccurss(){
document.getElementById('maxOccurs').readOnly=false;
document.getElementById('maxOccurs').style.backgroundColor='white';
}

var fieldrtn=null;
function sc(){
	var apiId=$("#apiId").val();
	$.ajax({
		url:ctx+'/api/field/getApiRtnList',
		type:'post',
		data: {apiId:apiId},
		dataType:'JSON',
		async:false,
		success:function (data){
			fieldrtn = data;
		}
	});
	var chtml = "";
	if(fieldrtn.length>0){
		for (var i = 0; i < fieldrtn.length; i++) {
			   chtml += "<div style='word-wrap:break-word; width:450px; '>";
			   chtml += '<label style="float:left;padding:15px"><input type="checkbox" name="aaa" value="1" class="{required:true}" /><span style="margin-left:10px">'+fieldrtn[i].fieldName+'</span></label>';
			   chtml += "</div>";
			}
	}else{
		  chtml += "<div style='word-wrap:break-word; width:450px; '>";
		   chtml += '<label style="float:left;padding:15px;color:red">暂无返回值</span></label>';
		   chtml += "</div>";	
	}
		
	
	//把得到字符串利用jquery添加到元素里面生成checkbox
	$("#selectlxr").html(chtml);
	//alert(apiId);
	$('#selectlxr').dialog({
		title: "选择字段列表", width: "500px",height:"300px",
		   content: $("#selectlxr").html()
		   /*close: function () {
			   this.hide();
			   return false;
		   },
		   follow: document.getElementById("jieshouren")*/

	});
	$("input[type=checkbox]").click(function () {
		   try {
			   var isChecked = $(this).prop('checked');  
			   //alert(isChecked);
			   if (isChecked) {
				   $("#jsrtxt").val($("#jsrtxt").val() + $(this).parent().text() + ",");
			   } else {
				   $("#jsrtxt").val($("#jsrtxt").val().replace($(this).parent().text() + ',', ""));
			   }
		   } catch (e) {
			   $("#jsrtxt").val("");
		   }
		});

	}

function sub(){
	var apiId=$("#apiId").val();
	var snNo=$("#snNo").val();
	var id=$("#id").val();
	if(id==null||id==undefined||id==""){
		$.ajax({
			url:ctx+'/api/inner/findparam?apiId='+apiId+"&snNo="+snNo,
			async:false,
			success:function (data){
				if(data.success){
					$.messager.alert("系统提示",data.msg);
					$('#tree1').tree("reload");
				}else{
					$('#addorUpauthForm').form('submit',{
			     	    url:"saveauthinfo",
			     	    onSubmit: function(){
			     	    	var isValid = !$("#addorUpauthForm").valid();
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
			     	        			window.location.href="authinfoList";
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
		$('#addorUpauthForm').form('submit',{
     	    url:"saveauthinfo",
     	    onSubmit: function(){
     	    	var isValid = !$("#addorUpauthForm").valid();
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
     	        			window.location.href="authinfoList";
     	        	});
     	    	}else{
     	    		$.messager.alert('提示', data.msg, 'error');
     	    	}
     			

     	    }
     });
	}
	
	
	
}
