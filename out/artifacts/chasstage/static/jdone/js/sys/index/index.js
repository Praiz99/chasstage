//菜单管理器
var menuManager;
$(function(){

	//初始化菜单
	menuManager = new Menu({
		bindDom:'#left-menu',
		midField:'id',
		pidField:'pid',
		nameField:'name',
		murlField:'url',
		iconField:'menuIco',
		dataType:'1',
		data:appMenus,
		itemClick:function(mgr,menu){
		if(menu[mgr.murlField] && menu[mgr.murlField]!=''){
			addTab(menu[mgr.nameField],menu[mgr.murlField]);
		}
	}});

	bindEvent();
	$("#mainIframe").attr("src", homepageUrl);
});

function bindEvent(){
	tabClose();
	tabCloseEven();

	//注销
    $('.close-btn').click(function() {
        $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
            if (r) {
            	window.location.href = ctx+"/index/loginOut";
            }
        });
    });

    //切换系统
    $('.hd-top .user-info .more-info').mouseover(function(e) {
       $(this).attr('class','more-info active');
 	   $('.user-opt').show();
 	   var position = $('.hd-top .user-info .more-info').offset();
 	   position.left = position.left-20;
 	   position.top = position.top+20;
 	   $('.user-opt').offset(position);
    });

    $("body").click(function(e) {
    	$(".more-info").attr('class','more-info');
  		$('.user-opt').hide();
    });
}

//添加页签
function addTab(subtitle,url){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			width:$('#mainPanle').width()-10,
			height:$('#mainPanle').height()-3
		});
	}else{
		$('#tabs').tabs('select',subtitle);
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:99%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children("span").text();
		if(subtitle!="首页"){
			$('#tabs').tabs('close',subtitle);
		}
	});

	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children("span").text();
		$('#mm').data("currtab",subtitle);

		return false;
	});
}

//绑定右键菜单事件
function tabCloseEven()
{
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		if(currtab_title=="首页") return;
		$('#tabs').tabs('close',currtab_title);
	});

	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t=="首页") return;
			$('#tabs').tabs('close',t);
		});
	});

	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t!=currtab_title && t!="首页")
				$('#tabs').tabs('close',t);
		});
	});

	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	});
}

function changeApp(obj){
	var aid = $(obj).attr("id");
	window.location.href = ctx + "/index?aid=" + aid;
	return false;
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

//计算时间
function clockon() {
    var now = new Date();
    var year = now.getFullYear(); //getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    var week;
    month = month + 1;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    if (hour < 10) hour = "0" + hour;
    if (minu < 10) minu = "0" + minu;
    if (sec < 10) sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;

    $("#bgclock").html(time);

    setTimeout("clockon()", 200);
}

function openSoftDownload(){
	$('#openDownloadList')[0].src = ctx + "/info/openSoftDownload";
	$('#helpDialog').dialog('open');
}

/**
 * 添加浙警智评
 * @param zjzpXtbs
 */
function addZjzp(zjzpXtbs){
	try {
		addDynamicCss("http://jzpt.gat.zj:10001/web_jznew/pages/jzwd/zjzp/css/jzwd-zjzp.css", function () {
			addDynamicJS("http://jzpt.gat.zj:10001/web_jznew/pages/jzwd/zjzp/js/jzwd-zjzp.js", function () {
				addDynamicCss("http://jzpt.gat.zj:10001/web_jznew/pages/jzwd/zjzp/js/hsycmsAlert/hsycmsAlert.min.css", function () {
					addDynamicJS("http://jzpt.gat.zj:10001/web_jznew/pages/jzwd/zjzp/js/hsycmsAlert/hsycmsAlert.min.js", function () {
						$("#zjzp").jzwdZjzp({'xtbs':zjzpXtbs});
					});
				});
			});
		});
	} catch (e) {
	}
}

function addDynamicJS(src, callback) {
	var script = document.createElement("script");
	script.setAttribute("type", "text/javascript");
	script.src = src;
	document.body.appendChild(script);
	if (callback != undefined) {
		script.onload = function () {
			callback();
		}
	}
}

function addDynamicCss(src, callback) {
	var script = document.createElement("link");
	script.setAttribute("type", "text/css");
	script.setAttribute("rel", "stylesheet");
	script.href = src;
	document.body.appendChild(script);
	if (callback != undefined) {
		script.onload = function () {
			callback();
		}
	}
}
