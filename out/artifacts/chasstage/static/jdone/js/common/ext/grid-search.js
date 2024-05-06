$(document).ready(function(){
	$('.keydownSearch').bind('keydown',function(e){
	    if (e.keyCode == 13) {
	    	$(".btn-primary").click();
	    }
	});
	//查询条件样式优化
	var extendSearch = $(".extend-search");
	if(extendSearch){
		extendSearch.append('<img class="extend-search-open" src="' + ctx + '/static/jdone/style/common/ext/imgs/expend-search-open.png">');
		var searchObj = extendSearch.find("img")[0];
		$(searchObj).bind('click', function(e){
			if ($(searchObj).attr("class") == "extend-search-open") {
				$(searchObj).attr("class", "extend-search-close");
				$(searchObj).attr("src", ctx + '/static/jdone/style/common/ext/imgs/expend-search-close.png');
				$(".row-fluid table").css("width", "100%");
				$(".form-actions").css("text-align", "center");
				$(".row-fluid").css("height", $(".row-fluid").find("tbody")[0].offsetHeight + 35);
				
			} else {
				$(searchObj).attr("class", "extend-search-open");
				$(searchObj).attr("src", ctx + '/static/jdone/style/common/ext/imgs/expend-search-open.png');
				$(".row-fluid table").css("width", "80%");
				$(".form-actions").css("text-align", "right");
				$(".row-fluid").css("height","40px");
			}
		});
	}
	//datagrid样式优化
	$(".datagrid-pager").prepend('<label class="pager-label">页行:</label>');
	/*$(".pagination-info").before('<label class="pager-label page-skip">跳转</label>');
	$(".pagination-loading").parent().parent().parent().hide();
	$(".page-skip").bind("click",function(e){
		var e = jQuery.Event("keydown");//模拟一个键盘事件
		e.keyCode = 13;//keyCode=13是回车
		$(".pagination-num").trigger(e);//模拟按下回车
	});*/
	$("input[type='radio']:checked").next().css("background", "url(" + ctx + "/static/jdone/style/common/ext/imgs/radio-checked.png) no-repeat 2px");
	$("input[type='radio']").bind("click",function(e){
		$("input[type='radio']").next().css("background", "url(" + ctx + "/static/jdone/style/common/ext/imgs/radio-uncheck.png) no-repeat 2px");
		$("input[type='radio']:checked").next().css("background", "url(" + ctx + "/static/jdone/style/common/ext/imgs/radio-checked.png) no-repeat 2px");
	});
});

//重新计算查询区域高度
function resetSearchHeight(index){  
	var extendSearch = $(".extend-search");
	if ($(searchObj).attr("class") == "extend-search-open") {
		$(".row-fluid").css("height", $(".row-fluid").find("tbody")[0].offsetHeight + 50);
	} else {
		$(".row-fluid").css("height","65px");	
	}
}
