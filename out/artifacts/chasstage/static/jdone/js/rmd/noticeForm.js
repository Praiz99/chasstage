var noticeId,msg;

$(document).ready(function (){
	 $("#noticeForm").validate();
	var id = $("#id").val();
	if (id != null && id != "") {
		$.ajax({
			type: "post",
			url: ctx+"/rmd/notice/edit?id="+id,
			dataType: 'json',
			cache: false
		});
	}
	$(".l-btn-text").text("选择文件");
	recieverString = $("#rmdObj").val();

	//初始化密级
	$(".security").hide();
	if($("#isEnableAuth").val()=='1') $(".security").show();
	
	//发布按钮绑定事件
	$("#dataFormSave").bind('click',function(){
		$("#isRelease").val("1");
		saveHandle();
	});
	
	//保存草稿按钮绑定事件
	$("#drafSave").bind('click',function(){
		$("#isRelease").val("0");
		saveHandle();
	});
	
	//绑定秘密改变事件
	$("#isEnableAuth").bind('change',enableAuthChange);
	
});

//密级change事件 当为0隐藏
function enableAuthChange(){
	$(".security").hide();
	if(this.value=='1') $(".security").show();
}

	//保存处理
	function saveHandle(){
		var contents = CKEDITOR.instances.noticeContent.getData(),falg = true;//获取值
		//正文为空
		if ($.trim(contents) == '') {
			falg = false;
			$.messager.confirm("温馨提示", "正文不能为空");
			return falg;
		} else {
			$("#noticeContent").val(contents);
			/*if ($("#isEnableAuth").val() == '0') {
				$("#rmdObj").val('');
			} else {
				var value = getRecieverData();
				$("#rmdObj").val(value);
			}*/
			 $('#noticeForm').form('submit', {
				 url : "save",
				 onSubmit : function() {
				 var isValid = !$("#noticeForm").valid();
				 if (isValid) {
					return false;
				 }
				 /*--开始时间和结束时间比较--*/ 
				 var beginTime = $("#startDate").val();
				 var endTtime = $("#expireDate").val();
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
				 }else{
					 return true;
				 }
				 
				 return true;
			},
				success : function(data) {
					window.location.href = ctx + "/rmd/notice/noticeList";
				}
		});
	}	 
	}

	/*// 获取当前接收对象
	function getRecieverData(){
		var users = $("input[name=users]",".security").val();
		var obj = null;
		if(users && users != ''){
			if(!obj)obj={};
			obj["users"] = users;
			obj["usersNames"] = $("textarea[name=usersNames]",".security").val();
		}
		
		var orgs = $("input[name=orgs]",".security").val();
		if(orgs && orgs != ''){
			if(!obj)obj={};
			obj["orgs"] = orgs;
			obj["orgsNames"] = $("textarea[name=orgsNames]",".security").val();
		}
		
		var roles = $("input[name=roles]",".security").val();
		if(roles && roles != ''){
			if(!obj)obj={};
			obj["roles"] = roles;
			obj["rolesNames"] = $("textarea[name=rolesNames]",".security").val();
		}
		
		var regs = $("input[name=regs]",".security").val();
		if(regs && regs != ''){
			if(!obj)obj={};
			obj["regs"] = regs;
			obj["regsNames"] = $("textarea[name=regsNames]",".security").val();
		}
		
		return obj;	
	}*/
	//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  ,flag 是否加载按钮组并自处理返回结果,fun 关闭回调事件
	var win;
	function EasyextDialog(url, title, width, height,top, shadow,flag,fun) {  
		//防止缓存
		var ids = Math.round(Math.random()*1000);
		url=ctx + "/rmd/notice/appointRecieverForm?ids="+ids;
		
	    var content = '<iframe id="'+ids+'" name="'+ids+'" src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
	    var boarddiv = '<div id="'+ids+'" title="' + title + '"></div>';//style="overflow:hidden;"可以去掉滚动条  
	    $(document.body).append(boarddiv);
	    if(flag){
	    	win =$('#'+ids).dialog({  
	    		content: content,  
	    		width: width,  
	    		height: height,  
	    		modal: shadow,  
	    		title: title, 
	    		top:top,
	    		onClose: function () {  
	    			//关闭后事件
	    			if(fun){
	    				fun();
	    			}
	    		}
	    	})
	    }else{
	    	win = $('#'+ids).dialog({  
	    		content: content,  
	    		width: width,  
	    		height: height,  
	    		modal: shadow,  
	    		title: title,  
	    		top:top,
	    		onClose: function () {  
	    			//关闭后事件
	    			if(fun){
	    				fun();
	    			}
	    		}
	    	}) 
	    }
	    if(flag){
	    	$("#"+ids).next().css("text-align","center");
	    }
	    win.dialog('open');  
	}	
	
	$(".panel-body").attr("height","90%");
	
	//清空接收对象值
	function clearHandle(){
		$.messager.confirm("清空提示","确定要清空选择的接收对象",function(yes) {
			if(yes){
			    $("textarea[name=usersNames]",".security").val('');
			    $("input[name=users]",".security").val('');
			    $("textarea[name=orgsNames]",".security").val('');
			    $("input[name=orgs]",".security").val('');
			    $("textarea[name=rolesNames]",".security").val('');
			    $("input[name=roles]",".security").val('');
			    $("textarea[name=regsNames]",".security").val('');
			    $("input[name=regs]",".security").val('');
			}
		});
	}
