
/**
 * 菜单对象
 */
var Menu = function(options) {
	this.bindDom = '';
	this.itemClick = null;
	this.defaultSelect = '';
	this.data = [];
	this.dataType = "0";	
	this.midField = 'mid';
	this.pidField = 'pid';
	this.nameField = 'name';
	this.murlField = 'murl';
	this.iconField = 'icon';
	
	if(options){
		this.bindDom = options.bindDom||'';
		this.itemClick = options.itemClick||null;
		this.defaultSelect = options.defaultSelect||'';
		if(options.data && $.type(options.data)=='array'){
			this.data = options.data;
		}
		this.dataType = options.dataType||"0";		
		this.midField = options.midField||'mid';
		this.pidField = options.pidField||'pid';
		this.nameField = options.nameField||'name';
		this.murlField = options.murlField||'murl';
		this.iconField = options.iconField||'icon';
	}
	
	this._init();
};

/**
 * 菜单数据转换
 * @param dataArray
 * @returns {Array}
 */
Menu.prototype._processDatas = function(dataArray){
	
	var resultData = [];
	
	var manager = this;
	
	//根菜单
	$.each(dataArray,function(i,o){
		if(!o[manager.pidField] || o[manager.pidField]=='' || o[manager.pidField]<=0){
			resultData.push(buildChild(o,dataArray));
		}
	});
	
	//构建子菜单
	function buildChild(data,dataArr){
		var child = [];
		$.each(dataArr,function(i,o){
			if(o[manager.pidField]==data[manager.midField]){
				o = buildChild(o,dataArr);
				child.push(o);
			}
		});
		
		var result = {};
		result = $.extend(result,data);
		result[manager.midField] = data[manager.midField];
		result[manager.pidField] = data[manager.pidField];
		result[manager.nameField] = data[manager.nameField];
		result[manager.murlField] = data[manager.murlField];
		result[manager.iconField] = data[manager.iconField];
		result.menus = child;
		
		return result;
	}
	
	return resultData;
};

/**
 * 初始化菜单
 */
Menu.prototype._getMenuByMid = function(mid){
	var result = null;
	var manager = this;
	var menuData = manager.data;
	if(!mid || !menuData || !(menuData instanceof Array)){
		return result;
	}
	
	result = matchChilds(mid,menuData);
	
	function matchChilds(dataid,childDatas){
		var data = null;
		$.each(childDatas,function(i,o){
			if(o[manager.midField]==dataid){
				data = o;
			}else{
				if(o.menus && o.menus.length>0){
					data = matchChilds(dataid,o.menus);
				}
			}
			if(data) return false;
		});
		
		return data;
	}
	
	return result;
};

/**
 * 初始化菜单
 */
Menu.prototype._init = function(){
	if(!this.bindDom || $(this.bindDom).length==0){
		return;
	}
	
	if(!this.data || this.data.length==0){
		return ;
	}
	
	this._createMenu();
};

Menu.prototype._createMenu = function(){
	var menuDivHtml = '<div class="wrapper">';
	menuDivHtml += '       <div class="main-sidebar" style="width:100%;padding-top:0px;">';
	menuDivHtml += '          <div class="sidebar">';
	menuDivHtml += '              <ul class="sidebar-menu" id="sidebar-menu"></ul>';
	menuDivHtml += '          </div>';
	menuDivHtml += '       </div>';
	menuDivHtml += '   </div>';
	$(this.bindDom).append(menuDivHtml);

	var __menuManager = this;
	var menuData = __menuManager.data;
	if(this.dataType=="1"){
		menuData = this._processDatas(menuData);
	}
		
	var _html = this._buildMenuHtml(menuData);
	
	$("#sidebar-menu").append(_html);
	
    $("#sidebar-menu li a").click(function () {
        var d = $(this), e = d.next();
        if (e.is(".treeview-menu") && e.is(":visible")) {
            e.slideUp(500, function () {
                e.removeClass("menu-open");
            }),
            e.parent("li").removeClass("active");
        } else if (e.is(".treeview-menu") && !e.is(":visible")) {
            var f = d.parents("ul").first(),
            g = f.find("ul:visible").slideUp(500);
            g.removeClass("menu-open");
            var h = d.parent("li");
            e.addClass("menu-open");
            e.slideDown(500, function () {
                f.find("li.active").removeClass("active"),
                h.addClass("active");

                var _height1 = $(window).height() - $("#sidebar-menu >li.active").position().top - 41;
                var _height2 = $("#sidebar-menu li > ul.menu-open").height() + 10;
                if (_height2 > _height1) {
                    $("#sidebar-menu >li > ul.menu-open").css({
                        overflow: "auto",
                        height: _height1
                    });
                }
            });
        }
        e.is(".treeview-menu");
        
        //响应点击
        if(e.length==0){
        	$("#sidebar-menu").find("li>a").removeClass("selected");
        	d.addClass("selected");
        }
        
        var mid = d.attr("data-id");
        if(typeof(__menuManager.itemClick)=='function'){
        	__menuManager.itemClick(__menuManager,__menuManager._getMenuByMid(mid));
        }
    });
};

/**
 * 递归创建菜单html
 * @param menuDatas
 * @returns {String}
 */
Menu.prototype._buildMenuHtml = function(menuDatas){
	var _html = "";
	if(!menuDatas || !(menuDatas instanceof Array)) return _html;
	
	var __menuManager = this;
	
	//构建根菜单
	 $.each(menuDatas, function (i) {
         var row = menuDatas[i];
         //子菜单
         var childNodes = row.menus||[];
         if(i==0){
             _html += '<li class="treeview active">';
         }else{
             _html += '<li class="treeview">';
         }
         _html += '<a href="javascript:void(0)" style="padding:8px 5px 8px 15px;" data-id="'+row[__menuManager.midField]+'">';
         if(row[__menuManager.iconField] != undefined){
			 var icon = row[__menuManager.iconField];
			 if(icon.indexOf("fa") == -1){
				 _html += '<i class="' + row[__menuManager.iconField] + '" style="margin-right:8px;"></i><span style="font-size:12px;">' + row[__menuManager.nameField] + '</span><i class="fa fa-angle-left pull-right"></i>';
			 }else{
				 _html += '<i class="' + row[__menuManager.iconField] + '"></i><span style="font-size:12px;">' + row[__menuManager.nameField] + '</span><i class="fa fa-angle-left pull-right"></i>';
			 }
		 }else{
			 _html += '<i class="' + row[__menuManager.iconField] + '"></i><span style="font-size:12px;">' + row[__menuManager.nameField] + '</span><i class="fa fa-angle-left pull-right"></i>';
		 }
         _html += '</a>';
         if(childNodes.length>0){
        	 _html += '<ul class="treeview-menu">';
        	 _html +=__menuManager._buildChildMenuHtml(childNodes);
        	 _html +="</ul>";
         }
         _html +="</li>";
	 });
	 
	return _html;
};

/**
 * 递归创建菜单html
 * @param menuDatas
 * @returns {String}
 */
Menu.prototype._buildChildMenuHtml = function(menuDatas){
	var _html = "";
	if(!menuDatas || !(menuDatas instanceof Array)) return _html;
	var __menuManager = this;
	 $.each(menuDatas, function (i) {
         var row = menuDatas[i];
         
         //子菜单
         var childNodes = row.menus||[];
         
    	 _html += '<li>';
    	 if(childNodes.length>0){
    		 if(row[__menuManager.iconField] != undefined){
    			 var icon = row[__menuManager.iconField];
    			 if(icon.indexOf("fa") == -1){
    				 _html += '<a style="font-size:12px;" href="javascript:void(0)" data-id="'+row[__menuManager.midField]+'"><i class="' + row[__menuManager.iconField] + '" style="margin-right:8px;"></i>' + row[__menuManager.nameField] + '';
    			 }else{
    				 _html += '<a style="font-size:12px;" href="javascript:void(0)" data-id="'+row[__menuManager.midField]+'"><i class="' + row[__menuManager.iconField] + '"></i>' + row[__menuManager.nameField] + '';
    			 }
    		 }else{
    			 _html += '<a style="font-size:12px;" href="javascript:void(0)" data-id="'+row[__menuManager.midField]+'"><i class="' + row[__menuManager.iconField] + '"></i>' + row[__menuManager.nameField] + '';
    		 }
             _html += '<i class="fa fa-angle-left pull-right"></i></a>';
    		 _html += '<ul class="treeview-menu">';
    		 _html += __menuManager._buildChildMenuHtml(childNodes);
    		 _html += '</ul>';
    	 }else{
    		 if(row[__menuManager.iconField] != undefined){
    			 var icon = row[__menuManager.iconField];
    			 if(icon.indexOf("fa") == -1){
    				 _html += '<a style="font-size:12px;" href="javascript:void(0)" class="menuItem" data-id="' + row[__menuManager.midField] + '"><i class="' + row[__menuManager.iconField] + '" style="margin-right:8px;"></i>' + row[__menuManager.nameField] + '</a>';
    			 }else{
    				 _html += '<a style="font-size:12px;" href="javascript:void(0)" class="menuItem" data-id="' + row[__menuManager.midField] + '"><i class="' + row[__menuManager.iconField] + '"></i>' + row[__menuManager.nameField] + '</a>';
    			 }
    		 }else{
    			 _html += '<a style="font-size:12px;" href="javascript:void(0)" class="menuItem" data-id="' + row[__menuManager.midField] + '"><i class="' + row[__menuManager.iconField] + '"></i>' + row[__menuManager.nameField] + '</a>';
    		 }
    	 }
    	 _html += '</li>';
    	 
	 });
	 
	return _html;
};