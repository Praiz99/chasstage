$(document).ready(function(e) {
	$("#bd").height($(window).height() - $("#ft").height());
	$("iframe").height($("#bd").height() - $(".tab").height() - 12);

	$(top.window).resize(function(e) {
		$("#bd").height($(window).height() - $("#ft").height());
		$("iframe").height($("#bd").height() - $(".tab").height() - 12);
	});
	$(".tab").on("click", "li", function() {
		$(this).addClass("current").siblings().removeClass("current");
	});

	$(".sidebar-hide").click(function() {
		$(".sidebar").hide();
		$("#bd").css("padding-left", "0");
		$(".sidebar-show").show();
		$.each($('iframe'), function() {
			if ($(this)[0].contentWindow.resizeWidth) {
				$(this)[0].contentWindow.resizeWidth();
			}
		});

	});
	$(".sidebar-show").click(function() {
		$(".sidebar").show();
		$("#bd").css("padding-left", "203px");
		$(this).hide();
		$.each($('iframe'), function() {
			if ($(this)[0].contentWindow.resizeWidth) {
				$(this)[0].contentWindow.resizeWidth();
			}
		});
	});
	
	//InitLeftMenu();

});

var _menus = [];

$.ajax({
	url:'geteasyUiTreeMenuData',
	dataType:'json',
	type:'POST',
	async:false,
	success:function (data){
		_menus = data;
	}
});

//初始化左侧
function InitLeftMenu() {
    var menulist = "";
    $.each(_menus, function(i, n) {
    	menulist += '<li class="nav-li"><a href="javascript:;" class="ue-clear">' + 
    	'<i class="nav-ivon"></i><span class="nav-text">' + n.menuname + '</span></a>';
    	menulist += buildMenuTwo(n.menus);
    	menulist += "</li>";
    });
    $("#accordion").append(menulist);
}

//二级菜单
function  buildMenuTwo(menus){
	var content = '<ul class="subnav">';
	if(menus != undefined){
		$.each(menus,function(i,m){
			if(m.menus){
				content += '<li class="lastnav-li"><a href="javascript:;" class="ue-clear">' + 
		    	'<i class="nav-ivon"></i><span class="nav-text">' + m.menuname + '</span></a>';
				content += buildMenuLast(m.menus);
				content += "</li>";
			}else{
				content += '<li class="subnav-li" href="' + m.url + '" data-id="' +m.menuid +
				'"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">' + 
				m.menuname + '</span></a></li>';
			}
		});
	}
	content += '</ul>';
	return content;
}

//三级菜单
function  buildMenuLast(menus){
	var content = '<ul class="lastsubnav">';
	if(menus != undefined){
		$.each(menus,function(i,s){
			content += '<li class="lastsubnav-li" href="' + s.url + '" data-id="' +s.menuid +
			'"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">' + 
			s.menuname + '</span></a></li>';
		});
	}
	content += '</ul>';
	return content;
}

