var noticeId,msg;

$(document).ready(function (){
	 $("#noticeForm").validate();
     initFileUpload();
	
	//发布按钮绑定事件
	$("#dataFormSave").bind('click',function(){
		$("#isPublish").val("1");
		saveHandle();
	});
	
	//保存草稿按钮绑定事件
	$("#drafSave").bind('click',function(){
		$("#isPublish").val("0");
		saveHandle();
	});
	
});

function initFileUpload(){
	var businessId = $("#businessId").val();
	obj = $("#fileUpload").ligerFileUpload({
		bizId:businessId,
		uploadtype:'fj',
		bizType:"Notice_Accessory"
	});	
}
//保存处理
function saveHandle(){
	var contents = CKEDITOR.instances.content.getData(),falg = true;//获取值
	//正文为空
	if ($.trim(contents) == '') {
		falg = false;
		$.messager.alert("温馨提示", "正文不能为空");
		return falg;
	} else {
		$("#content").val(contents);
		if($('#noticeForm').valid()){
			 /*--开始时间和结束时间比较--*/ 
			 var beginTime = $("#rmdStartDate").val();
			 var endTtime = $("#rmdEndDate").val();
			 //第一个参数是匹配规则字符串，第二个参数是匹配标识符  g标识全局匹配
			 var reg = new RegExp('-','g');
			 beginTime = beginTime.replace(reg,'/');//正则替换
			 endTtime = endTtime.replace(reg,'/');
			  // 将标准时间转化为时间戳通过Date.parse()方法来处理,
			  //将时间戳转化为整数，确保万一，通过parseInt("",10)来处理
			  //将时间戳转为日期对象new Date()
			 beginTime = new Date(parseInt(Date.parse(beginTime),10));
			 endTtime = new Date(parseInt(Date.parse(endTtime),10));
			 if(beginTime>endTtime){
				 $.messager.confirm("温馨提示", "结束日期必须大于开始日期");
				 return false;
			 }
			var tableData = $('#noticeForm').serializeObject();
			$.ajax({
				type : "post",
				url : ctx + "/info/notice/save",
				data : tableData,
				dataType : 'json',
				cache : false,
				success : function(data) {
					if(data.success){
						window.location.href = ctx + "/info/notice/noticeList";
					}else{
						$.messager.alert("提示",data.msg);
					}
				}
			});
		}
	}	 
}

	function openNoticeCatList(){
		$('#openNoticeCat')[0].src = ctx + "/info/notice/noticeCatList";
		$('#noticeCat').dialog('open');
	}
