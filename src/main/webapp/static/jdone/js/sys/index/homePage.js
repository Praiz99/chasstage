$(function(){
		ddl();
		tcl();
});

	//紧急通知
var win;
	function tcl(){
		var falg = $('#falg').val();
		if(falg == 'true'){
			return false;
		}
		var id = $('#noticeId').val();
		if(id != ''){
			var content = $('#noticeContent').val();
			if(content.substring(17) !=""){
				content = content.substring(0,17)+"...";
			}
			
			var title = $('#noticeTitle').val();
			if(title.length >12){
				title = title.substring(0,12)+"...";
			}
			content = '<table width="165px" border="0"><tr><td><a style="cursor:hand;" onclick=doPreview(\"' +id + '\")>' + content + '</a></td></tr>';
			content += '<tr><td style="text-align: right;"><input type="button" style="float:right;margin-right:5px;" onclick=buTiShiNotice(\"' +id + '\") value="不再提醒"/></td></tr></table>';
			win = $.messager.show({
				title : title,
				msg : content,
				width : 190,
				closable : false,
				timeout : 0// 取消自动关闭
			});
			$(".messager-body").css({"overflow":"hidden","height":"10px","width":"155px"});
		}
	}
	var formatDate,noticeTitle;
	function ddl(){
		$.ajax({
			url: ctx+"/rmd/notice/noticeShowData",
			type:'POST',
			cache: false,
			data:{isRelease:1},
			success:function(data){
				 $(data).each(function (i,o) {
					 if(o.noticeTitle.length > 14){
						 noticeTitle = o.noticeTitle.substring(0,14)+"...";
					 }else{
						 noticeTitle = o.noticeTitle;
					 }
				 $("#contents").append("<li class='content-list-item' style='height:30px;cursor: pointer;display:inline-block;' id='contentsli' onclick='doPreview(\"" + o.id + "\")'> <i class='icon'></i><span style='display:inline-block; width:185px'>"+noticeTitle +"</span>&nbsp;&nbsp;&nbsp;"+o.createTime+"</li>");
			  });  
			 $(".divs").vTicker({ 
				showItems : 10, //显示多少行
				pause : 2000, //滚动间歇 
				speed : 700, //滚动速度 
				animation : "fade", //滚动动画‘fade’，既滚动时首位淡入淡出
				mousePause: true ,     //鼠标移动到内容上是否暂停滚动，默认为true。
				height: 135,        //滚动内容的高度。
				direction : "up" //滚动方向 
				});
			}
		});
	}
	
	/*预览信息*/
	 function doPreview(id) {
		var url = ctx + "/rmd/notice/previewNotice/" + id;
		window.open(url);
	}
	//查看更多
	function theMore(){
		var url = ctx+"/rmd/notice/userNoticeList";
		window.open(url);
	}
	//不再提醒
	function buTiShiNotice(id){
		$.ajax({
			url: ctx+"/rmd/notice/noNotice/" + id,
			type:'POST',
			cache: false,
			success:function(data){
				win.window('close');
			}
			});
	}
	//局部刷新
	function refreshs(){
		$.post(ctx +"/rmd/notice/noticeShowData",function(data){
			$("#contents").empty();
			 $(data).each(function (i,o) {
				$("#contents").append("<li class='content-list-item' style='height:20px;cursor: pointer;display:inline-block;' id='contentsli' onclick='doPreview(\"" + o.id + "\")'> <i class='icon'></i><span style='display:inline-block; width:185px'>"+o.noticeTitle +"</span>&nbsp;&nbsp;&nbsp;"+o.createTime+"</b></li>"); 
			}); 
		},"json");
	}
	/* $( ".content-list" ).sortable({
		  revert: true,
		  handle:'h2'
		}); */
	
	/* function resizeWidth() {
		if ($('#main').width() / 3 < minwidth) {
			$('.content-item').width(($('#main').width() / 2) - 15);
		} else {
			$('.content-item').width(($('#main').width() / 3) - 15);
		}
	} */