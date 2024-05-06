﻿$(function() {
	showImg();
	$("div").hover(
			function () { 
				var id = $(this).attr("id");
				if(id=="app-qr-box"){
					noImg();
				}
			}
		);
});
function tips_pop(){
		  var MsgPop=document.getElementById("winpop");//获取窗口这个对象,即ID为winpop的对象
		  if(MsgPop==null){
			 return; 
		  }
		  var popH=parseInt(MsgPop.style.height);//用parseInt将对象的高度转化为数字,以方便下面比较
		   if (popH==0){         //如果窗口的高度是0
			   MsgPop.style.display="block";//那么将隐藏的窗口显示出来
			   show=setInterval("changeH('up')",1);//开始以每0.002秒调用函数changeH("up"),即每0.002秒向上移动一次
		   } else {         //否则
			   hide=setInterval("changeH('down')",1);//开始以每0.002秒调用函数changeH("down"),即每0.002秒向下移动一次
		  }
	};

function changeH(str) {
	 var MsgPop=document.getElementById("winpop");
	 var popH=parseInt(MsgPop.style.height);
	 if(str=="up"){     //如果这个参数是UP
		  if (popH<=158){    //如果转化为数值的高度小于等于100
			  MsgPop.style.height=(popH+4).toString()+"px";//高度增加4个象素
		  }else{
			  clearInterval(show);//否则就取消这个函数调用,意思就是如果高度超过100象度了,就不再增长了
		  }
	 }
	 if(str=="down"){
	  if (popH>=4){       //如果这个参数是down
	  MsgPop.style.height=(popH-4).toString()+"px";//那么窗口的高度减少4个象素
	  }else{        //否则
		  clearInterval(hide);    //否则就取消这个函数调用,意思就是如果高度小于4个象度的时候,就不再减了
		  MsgPop.style.display="none";  //因为窗口有边框,所以还是可以看见1~2象素没缩进去,这时候就把DIV隐藏掉
	  }
	 }
};

window.onload=function(){
	$.ajax({
		url: ctx+"/info/notice/getIndexNotice",
		type:'POST',
		dataType: 'json',
		cache: false,
		success:function(data){
			if(data.success){
				if(data.noticeSize<1){
					document.getElementById("indexNotice").style.display='none';
				}else{
					$("#notice").html(data.msg);
				}
			}
		}
	});//加载
		setTimeout("tips_pop()",800);     //3秒后调用tips_pop()这个函数
};

/*预览信息*/
function doPreview(id) {
	var url = ctx + "/info/notice/previewNotice/" + id;
	window.open(url);
}

/*不再提醒*/
function notrmd(id) {
	$.ajax({
		url: ctx+"/info/notice/notrmd?infoId="+id,
		type:'POST',
		dataType: 'json',
		cache: false,
		success:function(data){
			if(data.success){
				tips_pop();
			}
		}
	});
}

function openNotice(){
	$('#openProcessNotice')[0].src = ctx + "/info/notice/noticeViewList";
	$('#processNotice').dialog('open');
}

function showImg(){
	document.getElementById("app-qr-box").style.display = "block";
}

function noImg(){
	document.getElementById("app-qr-box").style.display = "none";
}