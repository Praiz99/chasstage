/**
 * 数据字典-Dictionary.js
 */
(function ($)
{
    var l = $.ligerui;

    $.fn.ligerDictionary = function (options)
    {
        return $.ligerui.run.call(this, "ligerDictionary", arguments);
    };

    $.fn.ligerGetDictionaryManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetDictionaryManager", arguments);
    };
	
    $.fn.getDicManager = function(){
    	var managers = [];
    	this.each(function(){
    		var ligeruiid = $(this).attr("ligeruiid");
    		if(typeof(ligeruiid)==='string' && $.trim(ligeruiid) != ''){
    			var manager = $.ligerui.get(ligeruiid);
    			if(manager) managers.push(manager);
    		}
    	});
    	
    	return managers.length==1 ? managers[0]:managers;
    };
    
    //接口方法扩展
    $.ligerMethos.Dictionary = $.ligerMethos.Dictionary || {};
    
    $.ligerui.controls.Dictionary = function (element, options)
    {
        $.ligerui.controls.Dictionary.base.constructor.call(this, element, options);
    };
    
    $.ligerDefaults.Dictionary = {
    		valueField:'code',	//值字段名称
    		labelField:'name',	//选项字段名称
    		titleField:'name',	//标题字段名称 title
    		data:null,			//json数据 {"Total":10,"Rows":data}
    		url:null,			//获取数据的url
    		dataAction:'local', //
    		searchField:'name,code,jpcode,scode', //默认是labelField 多个查询字段以,隔开,例如 searchField = 'name,code,scode'
    		pageParmName:'page',//向服务端请求数据时 提交请求当前页的参数名称
    		pagesizeParmName:'pagesize', //每页展示记录数参数名称
            sortorderParmName: 'orderBy', //提交的排序参数名称
            queryFieldParamName:'queryFields',//向服务端提交的查询字段串参数名称
            queryValueParamName:'queryValue',//向服务端提交的查询值参数名称
    		totalName:'Total',
    		recordName:'Rows',
    		parms: [],			//参数
    		width:null,			//字典宽度
    		resultWidth:null,	//结果容器宽度
    		page:1,				//当前页
    		pageSize:10,		//每页展示记录数
    		order_by:null,		//排序映射 是一个以排序字段名称为键的键值对象, 例如 :{code:'asc',name:'desc'},默认以valueField升序排序
    		hiddenField:'code',	//如果页面上有id为code且为隐藏域的dom对象则选中的字典值将会赋给该dom对象,如果不存在则新建一隐藏域并把hiddenField的值作为该隐藏域的名称
    		multi:false,		//是否多选
    		multiSplit:';',		//多选时的分隔符
    		initValue:null,		//设置初始值,当为单选时该值可以为:string,object 如果是多选时该值可以为:string Array。
    		isFixOptions:false,	//是否固定选项数目
    		checkInterval:300,	//联想时间间隔
    		method:'POST',		//请求数据的方式
    		itemClick:null,		//选中某个选项时执行的函数
    		itemRender:null,	//选项渲染函数
    		filterRegx:null,	//过滤正则表达式 {code:'^3.*?'} dataAction为server时 该参数无效
    		isEnable:true,		//字典启用、禁用开关,true:启用  false:禁用
    		showDirection:'auto',	//展示方向 auto:根据实际情况计算出展示位置,left:在左边展示,right:在右边展示,top:向上展示,bottom:向下展示
    		valueChangeHandle:null,	//值改变处理事件
    		isInitSelectFirst:false	//是否初始化时选中第一条数据
	};
    
    $.ligerDefaults.DictionaryString = {
    		pageStatMessage:"第<span class='red'>{currentPage}</span>页，共<span class='red'>{totalPage}</span>页"
    };
    
    $.ligerui.controls.Dictionary.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.Dictionary';
        },
        __idPrev: function ()
        {
            return 'Dictionary';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.Dictionary;
        },
        _init: function ()
        {
        	$.ligerui.controls.Dictionary.base._init.call(this);
        	var g = this, p = this.options;
        	//值字段名称
        	if(!p.valueField || $.trim(p.valueField)==''){
        		p.valueField = 'code';
        	}
        	//label字段名称
        	if(!p.labelField || $.trim(p.labelField)==''){
        		p.labelField = 'name';
        	}
        	
        	//隐藏域名称
        	if(!p.hiddenField || $.trim(p.hiddenField)==''){
        		p.hiddenField = p.valueField;
        	}
        	
        	//查询字段
        	if(!p.searchField || $.trim(p.searchField)==''){
        		p.searchField = p.labelField;
        	}
        	
        	//排序
        	if(!p.order_by || $.trim(p.order_by)=='' || typeof(p.order_by)!='object'){
        		p.order_by = {};
        		 //p.order_by[p.valueField] = "asc"; --------add by hy 20200330：去除默认按照code升序排序 
        	}
        	
        	//ajax请求方式
        	if(!p.method || $.trim(p.method)==''){
        		p.method = 'POST';
        	}
        	
        	if(typeof(p.multi) != 'boolean'){
        		p.multi = false;
        	}
        	
        	if(typeof(p.isEnable) != 'boolean'){
        		p.isEnable = true;
        	}
        	g.isEnable = p.isEnable;
        	
        	if(typeof(p.isFixOptions) != 'boolean'){
        		p.isFixOptions = true;
        	}
        	
        	if(typeof(p.isInitSelectFirst) != 'boolean'){
        		p.isInitSelectFirst = false;
        	}
        	
        	if((typeof(p.showDirection) != 'string') || p.showDirection==''){
        		p.showDirection = 'auto';
        	}
        	
        	//联想时间间隔
        	if(typeof(p.checkInterval) != 'number'){
        		p.checkInterval = 500;
        	}
        	
        	//设置多选时的分隔符
        	if(!p.multiSplit || p.multiSplit.length==0) p.multiSplit=';';
        	
        	//当前查询的值
        	g.prev_value = '';
        	
        	//选中的值
        	g.selectedValues = [];
        	g.selectedDatas = [];
        	
        	//匹配到的值
        	g.matchedData = [];
        	//当前展示的选项
        	g.currentShowData = [];
        	
        	g.currentPage = 1;
        	g.totalPage = 0;
        	g.total = 0;
        	
        	//是否正在联想
        	g.is_suggest = false;
        },
        _render: function ()
        {
        	var g = this, p = this.options;
        	//输入框
        	g.searchField = $(g.element);
        	//启用
        	if(g.isEnable===true){
        		g.searchField.addClass('dic-input-enable');
        	}
        	//禁用
        	else{
        		g.searchField.addClass('dic-input-disabled');
        		g.searchField.attr("disabled",true);
        	}
        	
        	if(p.width && p.width != 'auto'){
        		var w = p.width;
        		if(typeof(p.width)=='number') w = w + 'px';
        		g.searchField.css('width',w);
        	}
        	//输入框中的文字居中
        	g.searchField.css('line-height',g.searchField.height()+"px");
        	
        	//隐藏域,设置了隐藏域的id
        	if($("input[type='hidden'][id='"+p.hiddenField+"']").length>0){
        		g.hiddenField = $("input[type='hidden'][id='"+p.hiddenField+"']");
        		g.hiddenField.addClass('dic-value');
        		g.hiddenField.attr("dicId",g.id);
        	}
        	//构建名称为p.hiddenField的隐藏域
        	else{
        		g.hiddenField = $("<input type='hidden' name='"+p.hiddenField+"' />");
        		g.hiddenField.addClass('dic-value');
        		g.hiddenField.attr("dicId",g.id);
        		g.hiddenField.insertAfter(g.searchField);
        	}
        	
        	//构建结果DIV
        	var htmlArr = [];
        	htmlArr.push("<div class='result-container' tabindex='0' hidefocus='true' style='outline:0;'>");
        	htmlArr.push("  <div class='item-container'></div>");
        	htmlArr.push("  <div class='page-container'>");
        	htmlArr.push("    <div class='page-container-num'></div>");
        	htmlArr.push("    <div class='page-container-btn'>");
        	
        	htmlArr.push("      <a class='page-btn' name='first' href='javascript:void(0)'>首页</a>");
        	htmlArr.push("      <a class='page-btn' name='prev' href='javascript:void(0)'>上页</a>");
        	htmlArr.push("      <a class='page-btn' name='next' href='javascript:void(0)'>下页</a>");
        	htmlArr.push("      <a class='page-btn' name='last' href='javascript:void(0)'>末页</a>");
        	
        	if(p.multi===true){
        		htmlArr.push("      <a class='page-btn' href='javascript:void(0)' name='clear'>清除</a>");
        	}
        	
        	htmlArr.push("    </div>");
        	htmlArr.push("  </div>");
        	htmlArr.push("</div>");
        	
        	//结果容器
        	g.resultContainer = $(htmlArr.join(''));
        	g.resultContainer.insertAfter(g.searchField);
        	
        	//设置结果容器宽度
        	var w = 0;
        	
        	if(p.resultWidth){
        		if(typeof(p.resultWidth)=='number') w = p.resultWidth;
        		if(typeof(p.resultWidth)=='string') w = parseInt(p.resultWidth);
        	}
        	
        	if(w==0)
        	{
        	/*
        		w = g.searchField.width();
            	//默认宽度
            	var dw = 230;
            	//ie浏览器
            	if($.browser.msie){
            		if(p.multi===true) dw=250;
            		if(w<dw) w=dw;
            	}
            	//其他浏览器
            	else{
            		dw = 270;
            		if(p.multi===true) dw=300;
            		if(w<dw) w=dw;
            	}
              */
        		//新样式调整,结果容器多选默认设置成270
        		if(p.multi===true){
        			w=320;
        		}else{
        			w=270;
        		}
        	}
        	
        	//浏览器边框兼容问题
        	//if($.browser.msie) w+=2;，新样式调整
        	
        	g.resultContainer.width(w);
        	
        	g.itemContainer = $(".item-container",g.resultContainer);
        	//分页条
        	g.pageContainer = $(".page-container",g.resultContainer);
        	//页码描述容器
        	g.pageDescript = $(".page-container-num",g.resultContainer);
        	//翻页按钮容器
        	g.pageBtnContainer = $(".page-container-btn",g.resultContainer);
        	
        	//隐藏结果框
        	g.resultContainer.hide();
        	
        	//绑定字典事件
        	g._bindDicEvent();
        	
        	//绑定分页按钮事件
        	$(".page-btn",g.pageBtnContainer).bind('click',function(){
        		var btnName = $(this).attr('name');
        		g._pageBtnClick(btnName);
        	});
        	
        	//设置初值
        	var initValue = g.hiddenField.val();
        	if(p.initValue){
        		initValue = p.initValue;
        	}
        	
        	g.setValue(initValue,p.isInitSelectFirst);
        },
        
        //匹配值
        _matchValue:function(value,isInitSelectFirst){
        	var g = this, p = this.options;
        	
        	g.selectedDatas = [];
        	
        	if(!value || value==''){
        		if(!isInitSelectFirst){
            		g.trigger('matchComplete');
            		return;
        		}
        	}
        	
        	if(p.dataAction=='local'){
        		if(!p.data || !p.data[p.recordName]){
        			g.trigger('matchComplete');
        			return;
        		}
	        	//单选
	        	if(p.multi===false){
	        		//初始化时选择第一个字典项
	        		if(isInitSelectFirst===true){
	        			g.selectedDatas.push(p.data[p.recordName][0]);
	        		}else{
	        			for(var i in p.data[p.recordName]){
	        				var item = p.data[p.recordName][i];
	        				if(item[p.valueField]==value){
	        					g.selectedDatas.push(item);
	        					break;
	        				}
	        			}
	        		}
	        	}
	        	//多选
	        	else if(p.multi===true){
	        		//初始化时选择第一个字典项
	        		if(isInitSelectFirst===true){
	        			g.selectedDatas.push(p.data[p.recordName][0]);
	        		}else{
		        		var ary = value.split(',');
	        			for(var i in p.data[p.recordName]){
	        				var item = p.data[p.recordName][i];
	        				var isMatch = false;
	        				for(var j in ary){
								if(item[p.valueField]==ary[j]){
									isMatch = true;
									break;
								}
	        				}
	        				if(isMatch){
	        					g.selectedDatas.push(item);
	        				}
	        			}	        			
	        		}
	        	}
	        	g.trigger('matchComplete');
        	}
        	
        	else if(p.dataAction=='server'){
				if(!p.url || p.url.length==0){
	        		g.trigger('matchComplete');
	        		return;
				}
				
				var qr_word = value;
				
				//终止前面的请求
				g._abortAjax();
				
				//获取请求参数
				var params = g._getAjaxSubmitParams();
				
				//移除联想提交参数
				removeArrItem(params,function(param){
					return param.name == p.queryFieldParamName;
				});
				removeArrItem(params,function(param){
					return param.name == p.queryValueParamName;
				});
				
				//重新设置联想提交参数
				params.push({name:p.queryFieldParamName,value:p.valueField});      //提交查询字段 2014/02/28
				params.push({name:p.queryValueParamName,value:qr_word});		   //提交查询值 2014/02/28
				
				//请求数据
				g.xhr = $.ajax({
					url:p.url,
					type:p.method,
					data:params,
					dataType:'json',
					success:function(json){
						if(!json) json={};
						if(!json[p.totalName]) json[p.totalName] = 0;
						if(!json[p.recordName]) json[p.recordName] = [];
		        		//初始化时选择第一个字典项
		        		if(isInitSelectFirst===true){
		        			if(json[p.recordName].length>0){
		        				g.selectedDatas.push(json[p.recordName][0]);
		        			}
		        		}else{
		        			g.selectedDatas = g.selectedDatas.concat(json[p.recordName]);
		        		}
						g.trigger('matchComplete');
					}
				});
        	}
        },
        
        //选中字典项 当为单选时val可以为:string,object 为多选时val可以为:string,Array。
        setValue:function(value,isInitSelectFirst){
        	var g = this, p = this.options;
        	var val = "";
        	if(value){
        		//字符串、数字
        		if($.type(value)=='string' || $.type(value)=='number'){
        			val = value;
        		}
        		//数组
        		else if($.isArray(value) && value.length>0){
        			//类似：['1','2','3']
        			if($.type(value[0])=='string' || $.type(value[0])=='number'){
        				val = value.join(",");
        			}
        			//类似：[{code:'1',name:'a'},{code:'2',name:'b'}]
        			else{
        				var vls = [];
        				for(var j in value){
        					if(value[j][p.valueField]){
        						vls.push(value[j][p.valueField]);
        					}
        				}
        				val = vls.join(",");
        			}
        		}
        		//对象
        		else{
        			if(value[p.valueField]){
        				val = value[p.valueField];
        			}
        		}
        	}
        	
        	//如果有设置value，初始化选择第一个字典项功能失效
        	if(val && val!=''){
        		isInitSelectFirst = false;
        	}
        	
        	//旧值
        	var oldValue = g.selectedValues.join(",");
        	
        	g.bind('matchComplete',function(){
        		//单选
        		g.selectedValues = [];
        		var ary = [];
        		for(var i in g.selectedDatas){
        			g.selectedValues.push(g.selectedDatas[i][p.valueField]);
        			ary.push(g.selectedDatas[i][p.labelField]);
        			if(p.multi===false) break;
        		}
        		g.hiddenField.val(g.selectedValues.join(','));
        		var label = ary.join(p.multiSplit);
        		//多选
        		if(p.multi===true) if(label.length>0) label = label + p.multiSplit;
        		
        		g.searchField.val(label);
        		g.prev_value = label;
        		
        		//处理校验
    			if(g.searchField.hasClass('error')){
    				g.searchField.removeClass('error');
    				g.searchField.removeClass('dic-input-error');
    				g.searchField.unbind('mouseover');
    			}
    			
        		g.unbind('matchComplete');
        		
        		if(isInitSelectFirst===true){
        			if(g.selectedValues.length>0){
        				g.trigger('valueChange');
        			}
        		}else{
        			if(oldValue != val) g.trigger('valueChange');
        		}
        	});
        	
        	//匹配值
        	g._matchValue(val,isInitSelectFirst);
        },
        
        //获取选中字典项的值
        getValue:function(){
        	var g = this;
        	return g.getSelectedValues();        	
        },
        
        //获取选中字典项的名称
        getText:function(){
        	var g = this, p = this.options;
    		if(g.selectedDatas.length==0) return "";
    		var ary = [];
    		for(var i in g.selectedDatas){
    			ary.push(g.selectedDatas[i][p.labelField]);
    			if(p.multi===false) break;
    		}
    		
        	return ary.join(',');        	
        },
        
        //字典的启用和禁用开关;true:禁用字典,false:启用字典
        setDisabled:function(val){
        	var g = this, p = this.options;
        	if(typeof(val) != 'boolean') return;
        	//禁用
        	if(val===true){
        		if(g.isEnable===true){
        			if(g.searchField.hasClass('dic-input-enable')) g.searchField.removeClass('dic-input-enable');
        			if(!g.searchField.hasClass('dic-input-disabled')) g.searchField.addClass('dic-input-disabled');
        			g.searchField.attr("disabled",true);
                	g.isEnable = false;
        		}
        	}
        	//启用
        	else{
        		if(g.isEnable===false){
        			if(g.searchField.hasClass('dic-input-disabled')) g.searchField.removeClass('dic-input-disabled');
        			if(!g.searchField.hasClass('dic-input-enable')) g.searchField.addClass('dic-input-enable');
        			g.searchField.attr("disabled",false);
        			g.isEnable = true;
        		}
        	}
        },
        
        //获取选中的代码值 value1,value2
        getSelectedValues:function(){
        	var g = this;
        	return g.selectedValues.join(',');
        },
        
        //获取选中的字典项 [object,object]
        getSelectedItemDatas:function(){
        	var g = this;
        	return g.selectedDatas;
        },
        
        //绑定字典事件
        _bindDicEvent:function(){
        	var g = this, p = this.options;
        	//获取焦点时
        	g.searchField.bind('focus',function(){
        		if(g.resultContainer.is(':hidden')){
        			if(g.searchField.hasClass('error')){
        				g.searchField.addClass('dic-input-error');
        			}
        			g.is_suggest = false;
        			g.suggest();
        		}
        	});
        	
        	//输入值时进行联想
        	g.searchField.keyup(function(event){
        		g._keyPress(event);
        		//修正不能再次给输入框绑定keyup事件,2015-4-14 add by dsx
        		//return false;
        	});
        	
        	//结果容器绑定keyup事件
        	g.resultContainer.keyup(function(e){
        		g._pageKeyPress(e.keyCode,e.shiftKey);
        		//修正不能再次给g.resultContainer绑定keyup事件,2015-4-14 add by dsx
        		//return false;
        	});
        	
			var stop_hide = false;
			//如果点的是结果容器则不隐藏结果
			g.resultContainer.mouseup(function(e) { stop_hide = true; });
			//点空白处隐藏结果
			$('html').mouseup(function(e) {
				//当前点击的DOM对象
				var target = e.target || e.srcElement;
				var jqObj = $(target);
				//如果是字典输入框则不隐藏结果
				if((jqObj.hasClass('dic-input-enable') || jqObj.hasClass('dic-input-disabled')) && g.id==jqObj.attr('ligeruiid')){
					stop_hide = true;
				}
				if (stop_hide){
					stop_hide = false;
				}else{
					g._hideReuslt();
				}
			});
			
			//处理值改变事件
			if($.isFunction(p.valueChangeHandle)){
				g.bind('valueChange',function(){
					var val = g.selectedValues.join(",");
					p.valueChangeHandle(val,g);
				});
			}
			
			//窗口大小发生改变，重新定位
            $(window).bind("resize.Dictionary", function (e){
            	if(g.resultContainer.is(':visible')){
                	//计算展示位置
                	var position = g._adjustShowPosition();
                	//重新定位
            		g.resultContainer.offset(position);
            	}
            });
        },
        
        _pageBtnClick:function(btnName){
        	var g = this, p = this.options;
        	if(g.matchedData.length==0 && btnName!='clear') return;
        	if(btnName=='clear'){
        		if(g.selectedDatas.length > 0){
        			//旧值
        			var oldValue = g.selectedValues.join(",");
        			//去除选中状态
        			$.each(g.selectedValues,function(){
        				var value = this;
	        			$("input[value="+value+"]:checked").parent().removeClass('selected');
	        			$("input[value="+value+"]:checked").prop('checked',false);
        			});
        			//清空
        			g.clear();
        			//触发值改变
        			if(oldValue != '') g.trigger('valueChange');
        		}
        	}
        	else{
    			if(btnName=='first'){
    				if(g.currentPage == 1) return;
    				g.currentPage = 1;
    			}
    			else if(btnName=='prev'){
    				if(g.currentPage < 2) return;
    				g.currentPage -= 1;
    			}
    			else if(btnName=='next'){
    				if(g.currentPage == g.totalPage) return;
    				g.currentPage += 1;
    			}
    			else if(btnName=='last'){
    				if(g.currentPage == g.totalPage) return;
    				g.currentPage = g.totalPage;
    			}
    			//展示当前页的数据
    			g._showCurrentPageData();
        	}
        },
        
        //处理按键事件
        _keyPress:function(e){
        	var g = this, p = this.options;
			if ($.inArray(e.keyCode, [27,38,40,9]) > -1 && g.resultContainer.is(':visible')) {
				
					e.preventDefault();
					e.stopPropagation();
					e.cancelBubble = true;
					e.returnValue  = false;
					
					switch (e.keyCode) {
						case 38: 
							//上一行
							g.resultContainer.focus();
							g._pageKeyPress(e.keyCode, e.shiftKey);
							break;
						case 40: 
							g.resultContainer.focus();
							g._pageKeyPress(e.keyCode, e.shiftKey);
							//下一行
							break;
						case 9:  
							//按下tab 关闭结果
							g._hideReuslt();
							g.searchField.blur();
							break;
						case 27: 
							//按下esc 关闭结果
							g._hideReuslt();
							g.searchField.blur();
							break;
					}
				} else {
					
					//按键进行联想
					if(g.timer_valchange) clearTimeout(g.timer_valchange);
					g._setTimerCheckValue();
					
					//按下enter触发选中事件
					if(e.keyCode==13){
						$(".over",g.resultContainer).trigger("click");
						//多选，搜索输入框需要获取焦点
						if(p.multi===true) g.searchField.focus();
					}
				}
        },
        
        //翻页按键处理
        _pageKeyPress:function(keyCode,hasShiftKey){
        	var g = this, p = this.options;
			if ($.inArray(keyCode, [37,38,39,40,13]) > -1 && g.resultContainer.is(':visible'))
			{
				switch (keyCode) 
				{
					case 37: 
						
						//按了shift + ← 就直接转到第一页
						if (hasShiftKey){
							g._pageBtnClick("first");
						}
						//只按了← 向上翻页
						else{
							g._pageBtnClick("prev");
						}
						break;
	
					case 38: 
						
						if($(".over",g.resultContainer).length==0){
							$(".dic-item[index='0']",g.resultContainer).addClass('over');
						}
						else{
							var index = Number($(".over",g.resultContainer).attr("index"));
							if(index == 0) return;
							$(".over",g.resultContainer).removeClass("over");
							$(".dic-item[index='"+(index-1)+"']",g.resultContainer).addClass('over');
						}
						//上一行
						break;
						
					case 39: 
						
						//按了shift + → 就直接转到第最后一页
						if(hasShiftKey){
							g._pageBtnClick("last");
						}
						//只按了→ 向下翻页
						else{
							g._pageBtnClick("next");
						}
						break;
	
					case 40: 
						if($(".over",g.resultContainer).length==0){
							$(".dic-item[index='0']",g.resultContainer).addClass('over');
						}
						else{
							var index = Number($(".over",g.resultContainer).attr("index"));
							if((index+1) == g.currentShowData.length) return;
							$(".over",g.resultContainer).removeClass("over");
							$(".dic-item[index='"+(index+1)+"']",g.resultContainer).addClass('over');
						}
						break;
					case 13: 
						//按下enter 选中，并关闭结果
						$(".over",g.resultContainer).trigger("click");
						break;
				}
			}
        },
        
        //定时进行联想
        _setTimerCheckValue:function(){
        	var g = this, p = this.options;
        	g.timer_valchange = setTimeout(function(){
        		g._checkValue();
        	},p.checkInterval);
        },
        
        _checkValue:function(){
        	var g = this, p = this.options;
			var now_value = g.searchField.val();
			if (now_value != g.prev_value) {
				g.prev_value = now_value;
				g.is_suggest = true;
				g.suggest();
			} 
        },
        
        //联想
        suggest:function(){
        	var g = this, p = this.options;
        	
        	g.total = 0;
        	g.totalPage = 0;
        	g.matchedData = [];
        	g.currentShowData = [];
        	
        	if(g.is_suggest===true){
        		g.currentPage = 1;
        		if(p.multi===false){
        			var oldValue = g.selectedValues.join(",");
        			g.selectedDatas=[];
        			g.selectedValues=[];
        			g.hiddenField.val('');
        			if(oldValue!='') g.trigger('valueChange');
        		}
        	}
        	
			var q_word = (g.is_suggest===true) ? $.trim(g.searchField.val()) : '';
			//多选就用分隔符分割查询串
			if(p.multi===true && q_word.length>0){
        		var sAry = q_word.split(p.multiSplit);
        		q_word = sAry[sAry.length-1];
        	}
			
			if (q_word.length < 1 && g.is_suggest===true) {
				if(p.multi===true && $.trim(g.searchField.val())==''){
					var oldValue = g.selectedValues.join(",");
        			g.selectedDatas=[];
        			g.selectedValues=[];
        			g.hiddenField.val('');
        			if(oldValue!='') g.trigger('valueChange');
				}
				
				//隐藏结果
				g._hideReuslt();
				
				//检查是否有校验
    			if(g.searchField.hasClass('error')){
    				g.searchField.addClass('dic-input-error');
    			}
    			
				return;
			}
			
			if(p.dataAction == 'local'){
				if(!p.data || !p.data[p.recordName]) return;
				//构建正则表达式函数
				var fun = g._createRegxFunction(q_word);
				//设置了过滤
				if(fun){
					for(var i in p.data[p.recordName]){
						if(fun(p.data[p.recordName][i])){
							g.matchedData.push(p.data[p.recordName][i]);
						}
					}
				}
				//没有设置过滤
				else{
					g.matchedData = g.matchedData.concat(p.data[p.recordName]);
				}
				
				if(g.matchedData.length > 0) {
					g.total = g.matchedData.length;
					g.totalPage = g.total%p.pageSize==0 ? g.total/p.pageSize:(g.total-g.total%p.pageSize)/p.pageSize+1;
					//排序
					g._sortMatchData();
					//计算当前选中项所在的页码
					g._calculateCurrentPage();
					//展示当前数据
					g._showCurrentPageData();
				}else{
					g._showResult();
				}
			}
			else if(p.dataAction=='server'){
				g._showCurrentPageData();
			}
        },
        //展示结果
        _showResult:function(){
        	var g = this, p = this.options;
        	//重置联想标识位
        	g.is_suggest = false;
        	
    		//构建分页信息
    		g._buildPageInfo();
    		
        	//构建展示结果数据
    		g._buildResult();

    		//计算位置，并展示
    		var position = {left:0,top:0};
        	if(g.resultContainer.is(':hidden')){
        		g.resultContainer.show();
            	//计算展示位置
            	position = g._adjustShowPosition();
        		g.resultContainer.offset(position);
        	}
        	//计算展示位置，防止出现滚动条还需要重新计算位置
        	position = g._adjustShowPosition();
        	//重新定位
        	g.resultContainer.offset(position);
        },
        //隐藏结果
        _hideReuslt:function(){
        	var g = this, p = this.options;
        	//修正复制粘贴文本到搜索框中无法自动清除的bug,2022-11-29 add by dsx
        	//if(g.resultContainer.is(':visible')){  
    		g.resultContainer.hide();
        	g.itemContainer.html('');
        	g.pageDescript.html('');
        	//隐藏结果时重新给搜索框赋值，以防止未选择还有值在上面
        	var text = g.getText();
        	if(p.multi===true && text!=''){
        		text = text.replace(/,/g,p.multiSplit);
        		text += p.multiSplit;
        	}
        	g.searchField.val(text);
        	g.prev_value = text;
        	//}
        },
        //构建页码信息
        _buildPageInfo:function(){
        	var g = this, p = this.options;
        	var stat = p.pageStatMessage;
        	stat = stat.replace(/{currentPage}/, g.currentPage);
        	stat = stat.replace(/{totalPage}/, g.totalPage);
        	g.pageDescript.html(stat);
        	
        	//是否使用分页条
        	var usePageBar = true;
        	
        	//本地数据
        	if(p.dataAction == 'local'){
        		if(g.matchedData.length <= p.pageSize) usePageBar = false;
        	}
        	//服务端数据
        	else if(p.dataAction == 'server'){
        		if(g.total <= p.pageSize) usePageBar = false;
        	}
        	
        	//使用分页条
        	if(usePageBar===true){
        		g.pageContainer.show();
				g.options.isFixOptions = true;
        	}
        	//不使用分页条
        	else{
        		g.pageContainer.hide();
        		g.options.isFixOptions = false;
        	}
        },
        
        //构建结果
        _buildResult:function(){
        	var g = this, p = this.options;
        	g.itemContainer.html('');
        	//没有匹配到数据
        	if(g.currentShowData.length==0) {
        		var noMatch = $("<div class='dic-item' style='text-align:center;'>没有匹配的数据项</div>");
        		g.itemContainer.append(noMatch);
        		return;
        	}
        	
        	for(var i in g.currentShowData){
        		var item = $("<div class='dic-item' index='"+i+"'></div>");
        		//第一条高亮
        		if(i==0) item.addClass('over');
        		if(p.multi===true){
        			item.append("<input type='checkbox' value='"+g.currentShowData[i][p.valueField]+"' />");
        		}
        		item.append(g.currentShowData[i][p.labelField]);
        		g.itemContainer.append(item);
        		//有设置标题
        		if(p.titleField && p.titleField.length>0){
        			item.attr('title',g.currentShowData[i][p.titleField]);
        		}
        		//有设置itemRender函数
        		if($.isFunction(p.itemRender)){
        			p.itemRender(g,item,g.currentShowData[i]);
        		}
        		
        		if($.inArray(g.currentShowData[i][p.valueField],g.selectedValues) > -1){
        			item.addClass('selected');
        			$(":checkbox",item).prop('checked',true);
        		}
        		
        		item.mouseover(function(){
        			$(".over",g.resultContainer).removeClass("over");
        			$(this).addClass('over');
        		}).mouseout(function(){
        			$(this).removeClass('over');
        		}).click(function(e){
        			//旧值
        			var oldValue = g.selectedValues.join(",");
        			//当前点击的dom对象
        			var target = e.target || e.srcElement;
        			var index = $(this).attr("index");
        			//当前选中的值
        			var value = g.currentShowData[index][p.valueField];
        			if(p.multi===false){
        				g.selectedDatas = [];
        				g.selectedValues = [];        				
        				g.selectedDatas.push(g.currentShowData[index]);
        				g.selectedValues.push(value);	
        				g.hiddenField.val(value);
        				g.searchField.val(g.currentShowData[index][p.labelField]);
        				g.prev_value = g.searchField.val();
        				g._hideReuslt();
        			}
        			else if(p.multi===true){
            			if($(this).hasClass('selected')){
            				$(this).removeClass('selected');
            				if(target.tagName.toLowerCase() != 'input'){
            					$(":checkbox",$(this)).prop('checked',false);
            				}
            				var pos = $.inArray(value,g.selectedValues);
            				if(pos < 0) return ;
            				g.selectedDatas.splice(pos, 1);
            				g.selectedValues.splice(pos, 1);
            				g.hiddenField.val(g.selectedValues.join(','));
                			var labelAry = [];
                			for(var i in g.selectedDatas){
                				var label = g.selectedDatas[i][p.labelField];
                				labelAry.push(label);
                			}
                			var label = labelAry.join(p.multiSplit);
                			if(label.length>0) label = label + p.multiSplit;
                			g.searchField.val(label);
            				g.prev_value = label;
            			}
            			else{
	    	        		$(this).addClass('selected');
            				if(target.tagName.toLowerCase() != 'input'){
            					$(":checkbox",$(this)).prop('checked',true);
            				}
	        				g.selectedDatas.push(g.currentShowData[index]);
	        				g.selectedValues.push(value);
	        				g.hiddenField.val(g.selectedValues.join(','));
	        				
                			var labelAry = [];
                			for(var i in g.selectedDatas){
                				var label = g.selectedDatas[i][p.labelField];
                				labelAry.push(label);
                			}
                			var label = labelAry.join(p.multiSplit);
                			if(label.length>0) label = label + p.multiSplit;
                			
                			g.searchField.val(label);
            				g.prev_value = label;
            			}
        			}
        			
        			//有设置点击后的处理函数
        			if($.isFunction(p.itemClick)){
        				p.itemClick(g,item,g.currentShowData[index]);
        			}
        			
        			if(g.searchField.hasClass('error')){
        				g.searchField.removeClass('error');
        				g.searchField.removeClass('dic-input-error');
        				g.searchField.unbind('mouseover');
        			}
        			
        			//重新触发失去焦点事件
        			g.searchField.trigger('blur');
        			g.searchField.trigger('change');
        			
        			//触发值改变事件
        			var newValue = g.selectedValues.join(",");
        			if(oldValue != newValue) g.trigger('valueChange');
        		});
        	}
        	if(p.isFixOptions===true){
        		if(g.currentShowData.length < p.pageSize){
        			for(var i=g.currentShowData.length;i<p.pageSize;i++ ){
        				g.itemContainer.append("<div class='dic-item' index='"+i+"'>&nbsp;</div>");
        			}
        		}
        	}
        },
        
        //展示当前页的数据
        _showCurrentPageData:function(){
        	var g = this, p = this.options;
        	g.currentShowData = [];
        	if(p.dataAction == 'local'){
            	//设置展示数据
    			var sPos = (g.currentPage-1) * p.pageSize;
    			var ePos = sPos + p.pageSize;
    			if(ePos > g.total){
    				ePos = g.total;
    			}
    			var tempData = g.matchedData.slice(sPos,ePos);
    			g.currentShowData = g.currentShowData.concat(tempData);
    			//展示
    			g._showResult();
        	}
        	else if(p.dataAction == 'server'){
				if(!p.url || p.url.length==0) return ;
				//终止前面的请求
				g._abortAjax();
				//获取请求参数
				var params = g._getAjaxSubmitParams();
				//请求数据
				g.xhr = $.ajax({
					url:p.url,
					type:p.method,
					data:params,
					dataType:'json',
					success:function(json){
						if(!json) json={};
						if(!json[p.totalName]) json[p.totalName] = 0;
						if(!json[p.recordName]) json[p.recordName] = [];
						
						//计算总页数
						g.total = json[p.totalName];
						g.totalPage = g.total%p.pageSize==0 ? g.total/p.pageSize:(g.total-g.total%p.pageSize)/p.pageSize+1;
						
						//匹配到的数据
						g.matchedData = json[p.recordName];
						//排序
						g._sortMatchData();
						//待展示的数据
						g.currentShowData = g.currentShowData.concat(g.matchedData);
						
		    			//展示
		    			g._showResult();
					}
				});
        	}
        },
        
        //创建正则匹配函数
        _createRegxFunction:function(str){
        	var g = this, p = this.options;
        	var fields = p.searchField.split(",");
        	//联想过滤
        	var regx = "";
        	if(fields.length>0 && str && str.length>0){
	        	for(var i in fields){
	        		var s = " /.*?"+str+".*?/gi.test(o['"+fields[i]+"']) ||";
	        		regx += s;
	        	}
	        	if(regx.length>0){
	        		regx = regx.substring(0,regx.length-2);
	        	}
        	}
        	
        	//默认过滤
    		if(typeof(p.filterRegx)=='string'){
    			if(isJsonString(p.filterRegx)){
    				p.filterRegx = eval("("+p.filterRegx+")");
    			}
    		}
    		var filterStr = '';
    		if(p.filterRegx && typeof(p.filterRegx)=='object'){
    			for(var ele in p.filterRegx){
    				if(p.filterRegx[ele] && p.filterRegx[ele].length>0){
    					filterStr = "/" + p.filterRegx[ele] + "/gi.test(o['"+ele+"']) &&";
    				}
    			}
    			if(filterStr.length>0){
    				filterStr = filterStr.substring(0,filterStr.length-2);
    			}
    		}
    		
    		if(filterStr.length>0){
    			if(regx.length>0){
    				regx = "(" + regx + ") && (" + filterStr + ")";
    			}
    			else{
    				regx = filterStr;
    			}
    		}
    		
    		if(regx.length==0) return null;
    		
        	regx = "return " + regx + ";";
        	
        	var fun = new Function("o",regx);
        	
        	return fun;
        },
        //排序
        _sortMatchData:function(){
        	var g = this, p = this.options;
        	if(g.matchedData.length==0) return;
        	
			//排序
			if(p.order_by && typeof(p.order_by)=='object'){
				var d = p.order_by;
				for(var field in d){
					//升序
					if(/^asc$/i.test(d[field])){
						g.matchedData.sort(function(a,b){
							return a[field] < b[field]?-1:1;
						});
					}
					else if(/^desc$/i.test(d[field])){
						g.matchedData.sort(function(a,b){
							return a[field] > b[field]?-1:1;
						});
					}
				}
			}
/*			
			//相似度排序
			else{
				var q_word = g.is_suggest===true ? $.trim(g.searchField.val()) : '';
	        	if(p.multi===true && q_word.length>0){
	        		var sAry = q_word.split(p.multiSplit);
	        		q_word = sAry[sAry.length-1];
	        	}
	        	if(q_word=='') return ;
	        	
	        	//所有查询字段
	        	var fields = p.searchField.split(",");
	        	
	        	//计算最小相似度
	        	for(var i in g.matchedData){
	        		var leval = 0;
	        		for(var j in fields){
	        			var temp = g._calculateSimilarity(q_word,g.matchedData[i][fields[j]]);
	        			if(temp > leval) leval = temp;
	        		}
	        		g.matchedData[i]['leval'] = leval;
	        	}
	        	
	        	//相似度排序
				g.matchedData.sort(function(a,b){
					return a['leval'] > b['leval'] ? -1:1;
				});
			}
*/						
        },
        
        //终止ajax请求
        _abortAjax:function(){
        	var g = this, p = this.options;
        	if(g.xhr){
        		g.xhr.abort();
        		g.xhr = false;
        	}
        },
        //获取查询时需提交的参数
        _getAjaxSubmitParams:function(){
        	var g = this, p = this.options;
    		var params = [];
    		//处理设置的参数
    		if(p.parms){
    			if(p.parms instanceof Array){
    				$(p.parms).each(function(){
    					params.push({name:this.name,value:this.value});
    				});
    			}
    			else if (typeof p.parms == "object")
                {
                    for (var name in p.parms)
                    {
                    	params.push({ name: name, value: p.parms[name] });
                    }
                }
    		}
			//设置当前请求页码
			params.push({name:p.pageParmName,value:g.currentPage});
			params.push({name:p.pagesizeParmName,value:p.pageSize});
			//查询字段以及对应的查询值,只支持模糊查询
			var q_word = (g.is_suggest) ? $.trim(g.searchField.val()) : '';
			//处理动态字典 翻页时不带查询条件 2015-04-18 
			if(g.is_suggest===false){
				if(p.multi===false){
					if(g.selectedDatas.length==0){
						q_word = $.trim(g.searchField.val());
					}
				}else{
					var ary = $.trim(g.searchField.val()).split(p.multiSplit);
					if(g.selectedDatas.length!=ary.length){
						q_word = $.trim(g.searchField.val());
					}
				}
			}
			
        	if(p.multi===true && q_word.length>0){
        		var sAry = q_word.split(p.multiSplit);
        		q_word = sAry[sAry.length-1];
        	}
        	
			params.push({name:p.queryFieldParamName,value:p.searchField});//提交查询字段 2014 02 28
			params.push({name:p.queryValueParamName,value:q_word});		  //提交查询值 2014 02 28
			
			//处理排序
			if(p.order_by && typeof(p.order_by)=='object'){
				var order_by_value = null;
				var d = p.order_by;
				for(var field in d){
					//升序
					if((/^asc$/i.test(d[field])) || (/^desc$/i.test(d[field]))){
						if(!order_by_value) order_by_value = {};
						order_by_value[field] = d[field];
					}
				}
				if(order_by_value){
					params.push({name:p.sortorderParmName,value:JSON.stringify(order_by_value)});
				}
			}
			
			return params;
        },
        //加载服务端数据
        _loadServerData:function(){
        	var g = this, p = this.options;
        },
        //计算结果容器的展示坐标
        _adjustShowPosition:function(){
        	var g = this,p = this.options;
        	
        	//计算偏移
    		var posX = g.searchField.offset().left;
    		var posY = g.searchField.offset().top + g.searchField.outerHeight()+1;
    		var h = g.resultContainer.height();
    		//自动计算
    		if(p.showDirection=='auto'){
	    		if(h<$(window).height()){
	        		//如果向下浮出现滚动条
	        		if((posY+h)>$(window).height()){
	        			//上浮动超过窗口上限
	        			if((g.searchField.offset().top-h-3)<0){
	        				//超过上限则则Y居中偏移，计算X偏移
	        				if((posX-g.resultContainer.width()-5)<0){
	        					//向右偏移
	        					if((posX+g.searchField.outerWidth()+5+g.resultContainer.width())<$(window).width()){
	        						posX = posX+g.searchField.outerWidth()+5;
	        						posY = ($(window).height()-g.resultContainer.height())/2;
	        					}
	        				}
	        				//向左偏移
	        				else{
	        					posX = posX-g.resultContainer.width()-5;
	        					posY = ($(window).height()-g.resultContainer.height())/2;
	        				}
	        			}
	        			//向上偏移
	        			else{
	        				posY = g.searchField.offset().top-h-3;
	        			}
	        		}    			
	    		}
    		}
    		
    		//向上
    		else if(p.showDirection=='top'){
    			posY = g.searchField.offset().top-h-3;
    		}
    		
    		//向左
    		else if(p.showDirection=='left'){
				posX = posX-g.resultContainer.width()-5;
				posY = ($(window).height()-g.resultContainer.height())/2;
    		}
    		
    		//向右
    		else if(p.showDirection=='right'){
				posX = posX+g.searchField.outerWidth()+5;
				posY = ($(window).height()-g.resultContainer.height())/2;
    		}
    		
    		var position = {};
    		position.left = posX;
    		position.top = posY;
    		
    		return position;
        },
        
        //清空、即重置
        clear:function(){
        	var g = this;
        	//当前查询的值
        	g.prev_value = '';
        	//选中的值
        	g.selectedValues = [];
        	g.selectedDatas = [];
					g.hiddenField.val('');
					g.searchField.val('');
    		
        	//匹配到的值
        	// g.matchedData = [];
        	
        	//当前展示的选项
        	// g.currentShowData = [];
        	// g.currentPage = 1;
        	// g.totalPage = 0;
        	// g.total = 0;
        	
        	//是否正在联想
        	g.is_suggest = false;
        },
        
        //移除参数
        removeParm : function(name)
        {
            var g = this;
            var parms = g.get('parms');
            if (!parms) parms = {};
            if (parms instanceof Array)
            {
                removeArrItem(parms, function (p) { return p.name == name; }); 
            } else
            {
                delete parms[name];
            }
            g.set('parms', parms);
        },
        //设置参数
        setParm: function (name, value)
        {
            var g = this;
            var parms = g.get('parms');
            if (!parms) parms = {};
            if (parms instanceof Array)
            {
                removeArrItem(parms, function (p) { return p.name == name; });
                parms.push({ name: name, value: value });
            } else
            {
                parms[name] = value;
            }
            g.set('parms', parms); 
        },
        
        //修改字典配置属性
        changeOptions:function(obj,value){
        	var g = this, p = this.options;
        	//设置单个属性
        	if(typeof(obj)=='string'){
        		this.options[obj] = value;
        	}
        	//批量设置属性
        	else if(typeof(obj)=='object' && !value){
        		this.options = $.extend({},p,obj);
        	}
        },
        
        //加载数据
        loadData:function(data){
        	var g = this, p = this.options;
        	
        	//清空选中的值
        	g.clear();
        	
        	g.matchedData = [];
        	g.currentShowData = [];
        	
        	g.currentPage = 1;
        	g.totalPage = 0;
        	g.total = 0;
        	
        	//是否正在联想
        	g.is_suggest = false;
        	
        	//server
        	if(p.dataAction==='server' && typeof(data)=='string'){
        		p.url = data;
        	}
        	//local
        	else if(p.dataAction==='local'){
        		p.data = null;
        		if(!data) return ;
        		//array
        		if($.isArray(data)){
        			p.data = {Rows:data,Total:data.length};
        		}
        		//object
        		else if(typeof(data)=='object'){
        			p.data = data;
        		}
        	}
        },
        
        //设置字段过滤正则表达式
        setFieldFilterRegx:function(fieldName,regx){
        	var g = this, p = this.options;
        	var filterRegx = p.filterRegx;
        	if(!p.filterRegx) p.filterRegx = {};
        	if(typeof(fieldName)=='string' && fieldName!='' && typeof(regx)=='string'){
        		p.filterRegx[fieldName] = regx;
        	}
        },
        
        //计算当前选中项所在页
        _calculateCurrentPage:function(){
        	var g = this, p = this.options;
        	
        	//如果为联想则不计算。
			if(g.is_suggest===true) return;
			
			//匹配到的字典项个数不大于每页展示数则展示第一页
			if( g.matchedData.length <= p.pageSize) return;
			
			//当前选中的值
			var selectValues = g.getSelectedValues();
			if(selectValues=='') return;
			
			//单选
			var value = selectValues;
			//多选则取第一个
			if(p.multi===true){
				value = selectValues.split(',')[0];
			}
			
			//计算选中项的索引
			var pos = 0;
			for(var i in g.matchedData){
				if(value==g.matchedData[i][p.valueField]){
					pos = i;
					break;
				}
			}
			//第一页
			if(pos < p.pageSize) return;
			
			//有小数部分则整数部分加1，返回整数值
			g.currentPage = Math.ceil((Number(pos)+1)/p.pageSize);
        },
        
        //计算字符串的相似度，返回的值越大则相似度越高
        //source:待计算的字符串，target:待比较的字符串
        _calculateSimilarity:function(source,target){
        	
        	if(!source || $.trim(source)=='') return 0;
        	if(!target || $.trim(target)=='') return 0;
        	
        	//都转为大写
        	var s = source.toUpperCase();
        	var t = target.toUpperCase();
        	
        	var d = new Array(s.length+1);
        	for(var i=0;i<d.length;i++){
        		d[i] = new Array(t.length+1);
        		for(var j=0;j<d[i].length;j++){
        			d[i][j] = 0;
        		}
        	}
        	
        	//初始化相似度计算
        	for(var i=0;i<=s.length;i++){
        		d[i][0] = i;
        	}
        	for(var j=0;j<=t.length;j++){
        		d[0][j] = j;
        	}
        	
        	//计算相似度
    	    for(var i = 1; i <= s.length; i++)
    	    {
	    	    for(j = 1; j <= t.length; j++)
	    	    {
	    	    	//source串第i个字符target串第j个字符相等则不需要编辑操作
		            if(s.charAt(i-1) == t.charAt(j-1))
		            {
		                 d[i][j] = d[i - 1][j - 1]; 
		            }
		            //需要编辑操作
		            else
		            {
		            	//source 插入字符
		                var edIns = d[i][j - 1] + 1;
		                //source 删除字符
		                var edDel = d[i - 1][j] + 1;
		              	//source 替换字符
		                var edRep = d[i - 1][j - 1] + 1;
		                //去最小值
		                d[i][j] = Math.min(Math.min(edIns, edDel), edRep);
		             }
	    	     }
    	    }
    	    
    	    return (1-d[s.length][t.length]/Math.max(s.length,t.length));
        }
    });
    
    function removeArrItem(arr, filterFn)
    {
        for (var i = arr.length - 1; i >= 0; i--)
        {
            if (filterFn(arr[i]))
            {
                arr.splice(i, 1);
            }
        }
    }
    
    /**
     * 判断字符串是不是标准的json串,其中属性名称都必须带上双引号
     * @param str 待判断的字符串
     */
    function isJsonString(str){
    	var result = true;
    	try{
    		JSON.parse(str);
    	}catch(e){
    		result = false;
    	}
    	
    	return result;
    }
    
})(jQuery);    