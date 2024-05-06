
(function ($)
{
    var l = $.ligerui;

    $.fn.ligerImageList = function (options)
    {
        return $.ligerui.run.call(this, "ligerImageList", arguments);
    };

    $.fn.ligerGetImageListManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetImageListManager", arguments);
    };
	
    //接口方法扩展
    $.ligerMethos.ImageList = $.ligerMethos.ImageList || {};
    
    $.ligerui.controls.ImageList = function (element, options)
    {
        $.ligerui.controls.ImageList.base.constructor.call(this, element, options);
    };
    
    $.ligerDefaults.ImageList = {
    		url:'',
    		data:null,
    		checkBox:false,
    		colSpace:'100',
    		rowSpace:'100',
    		titleField:'title',
    		idField:'id',
    		nameField:'name',
    		urlField:'url',
    		usePage:false,
    		rowSize:2,
    		colSize:4,
    		dataAction:'server',
    		method:'get',
    		useRootPath:false,
    		rootPath:'',
    		imgWidth:'100',
    		imgHeight:'100',
    		showName:true,
    		useRotate:false,
    		align:'center',
    		templateRender:null,
    		isView:true,//是否可放大浏览
    		viewMask:false,//浏览时是否有遮罩
    		pageParmName:'page',
    		pagesizeParmName:'pagesize',
    		totalName:'Total',
    		recordName:'Rows',
    		parms: [],
    		width:'auto',
    		useCover:false
    };
    
    $.ligerDefaults.ImageListString = {
    		 pageStatMessage: '第{currentPage}页，共{totalPage}页，每页显示：{pageCount}张，总共：{total}张。'
    };    
    
    $.ligerui.controls.ImageList.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.ImageList';
        },
        __idPrev: function ()
        {
            return 'ImageList';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.ImageList;
        },
        _init: function ()
        {
            $.ligerui.controls.ImageList.base._init.call(this);
            var g = this, p = this.options;
            
            if(p.useRootPath){
            	p.url = p.rootPath + p.url;
            }
            
            //初始化分页数据
    		g.currentPage = 1;
    		g.totalPage = 0;
    		g.pageCount = p.rowSize * p.colSize;
    		g.total = 0;
    		g.currentData = [];
    		
        	if(!p.data){
        		p.data = {};
        		p.data[p.recordName] = [];
        		p.data[p.totalName] = 0;
        	}
        },
        _render: function ()
        {
        	var g = this, p = this.options;
        	g.imageList = $(g.element);
        	
        	var htmlArr = [];
        	
        	htmlArr.push("<table class ='image_table' cellpadding='0' cellspacing='0' border='0' id='table_"+g.id+"'>");
        	htmlArr.push("<thead></theah>");
        	htmlArr.push("<tbody></tbody>");
        	htmlArr.push("</table>");
        	
        	htmlArr.push("<div class='sundun-panel-bar-new' style='height:28px;'>");
        	htmlArr.push("  <div class='l-panel-bbar-inner'>");
        	htmlArr.push("    <div style='float:left;margin-left:100px;'><span class='l-bar-text-msg' style='line-height:14px;'></span></div>");
            htmlArr.push("    <div class='l-bar-group' style='float:right;margin-right:100px;'>");
            htmlArr.push("      <div class='l-bar-button l-bar-btnnext' name='next'><span>下一页</span></div>");
            htmlArr.push("      <div class='l-bar-button l-bar-btnlast' name='last'><span>末页</span></div>");
            htmlArr.push("    </div>");			  
            htmlArr.push("    <div class='l-bar-separator' style='float:right;'></div>");
            htmlArr.push("    <div class='l-bar-group' style='float:right;margin-top:2px'><span class='pcontrol'> <input type='text' size='4' value='1' style='width:20px' maxlength='3' /> / <span></span></span></div>");
            htmlArr.push("    <div class='l-bar-separator' style='float:right;'></div>");
            htmlArr.push("    <div class='l-bar-group' style='float:right;'>");
            htmlArr.push("      <div class='l-bar-button l-bar-btnfirst' name='first'><span>首页</span></div>");
            htmlArr.push("      <div class='l-bar-button l-bar-btnprev'  name='prev'><span>上一页</span></div>");
            htmlArr.push("    </div>");
            htmlArr.push("    <div class='l-bar-separator' style='float:right;'></div>");
            htmlArr.push("    <div class='l-bar-group' style='float:right;'>");
            htmlArr.push("      <div class='l-bar-button l-bar-btnload' name='refresh'><span>刷新</span></div>");
            htmlArr.push("    </div>");        	
        	htmlArr.push("    <div class='l-clear'></div>");
        	htmlArr.push("  </div>");
        	htmlArr.push("</div>");
        	
        	g.imageList.html(htmlArr.join(""));
        	
        	//照片表体
        	g.table = $("table:first",g.imageList);
        	g.table.body = $("tbody:first",g.table);
        	
        	//设置宽度
        	if(p.width=='auto'){
        		g.imageList.width(g.table.width());
        	}else{
	        	g.imageList.css("width",p.width);
	    		g.table.width(g.imageList.width());
        	}
        	
        	//分页条
        	g.pageBar = $(".sundun-panel-bar-new:first",g.imageList);
        	
        	//绑定事件
        	g._bindImageTableEvent();
        	
        	//加载数据
        	g.loadImageData();
        },

        //绑定事件
        _bindImageTableEvent:function(){
        	var g = this, p = this.options;
        	
        	//分页条按钮点击事件
        	$(".l-bar-button",g.pageBar).bind("click",function(){
        		var btnName = $(this).attr('name');
        		g._pageBtnClick(btnName);
        	});
        	
        	//分页条输入框值改变事件
        	$('.pcontrol input', g.pageBar).bind('change',function(){
        		if(!$.isNumeric(this.value)){
        			this.value = g.currentPage;
        			return ;
        		}
        		g.currentPage = Number(this.value);
        		if(g.currentPage > g.totalPage){
        			g.currentPage = g.totalPage;
        			this.value = g.totalPage;
        		}
        		g.loadImageData();
        	});
        },
        
        //分页条按钮点击
        _pageBtnClick:function(btnName){
        	var g = this, p = this.options;
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
			//加载数据
			g.loadImageData();
        },
        
        //添加图片
        addImage:function(img){
        	var g = this, p = this.options;
        	if(p.dataAction == 'server' && p.usePage===true) return;
        	if(!p.data){
        		p.data = {};
        		p.data[p.recordName] = [];
        		p.data[p.totalName] = 0;
        	}
        	
        	p.data[p.recordName].push(img);
        	g.total = g.total + 1;
        	p.data[p.totalName] = g.total;
        	
        	//分页
        	if(p.usePage===true){
        		//总页数
        		g.totalPage = Math.ceil(g.total/g.pageCount);
        		if((g.total-1) >= (g.currentPage*g.pageCount)) return ;
        		//需在当前页展示
        		g.currentData.push(img);
        	}
        	//不分页
        	else{
				g.currentData.push(img);
        	} 
    		//算出新添加的照片在第几行、第几列
    		
    		//当前页展示的总数
    		var showSize = g.currentData.length;
    		//行id
    		var rowid = Math.ceil(showSize/p.colSize)-1;
    		//列id
    		var colid = showSize - rowid*p.colSize-1;
    		
    		//是否需要新建一行
    		var isNewRow = false;
    		if((showSize-1)%p.colSize == 0) isNewRow = true;
    		if(isNewRow==true){
    			var row = $("<tr rowid='"+rowid+"'></tr>");
    			g.table.body.append(row);
    		}
    		
    		var pos = g.currentData.length-1;
    		var htmlArr = g._createImageDiv(img, rowid,colid,pos);
    		var tr = $("tr[rowid='"+rowid+"']",g.table).append(htmlArr);
    		
    		if(p.templateRender && typeof(p.templateRender)=='function'){
    			var template = $(".template[colid="+colid+"]",tr);
    			p.templateRender(template,g.currentData[pos],g,p);
    		}
        },
        
        //删除图片
        removeImage:function(rowid,colid,datapos){
        	var g = this, p = this.options;
        	if(p.dataAction == 'server' || p.usePage===true) return;
        	
        	if(p.data[p.recordName].length>datapos){
        		p.data[p.recordName].splice(datapos,1);
        	}
        	g.reload();
        	return;
        	
        	$("tr[rowid="+rowid+"] > td[colid="+colid+"]",g.table).remove();
        	
        	//改行没有子元素则删除该行
        	if($("tr[rowid="+rowid+"]",g.table).children().length==0){
        		$("tr[rowid="+rowid+"]",g.table).remove();
        	}
        	
        	if($("tr",g.table.body).length==0){
        		p.data[p.recordName] = [];
        		p.data[p.totalName] = 0;
        		g.currentData = [];
        		g.total = 0;
        	}
        },      
        
        //获取选中的图片id
        getCheckedImg:function(){
        	var g = this, p = this.options;
        	var ids = [];
        	$(".img_checkbox:checked",g.table).each(function(){
        		ids.push(this.value);
        	});
        	
        	return ids.join(",");
        },
        //选中的照片
        selectImg:function(ids){
        	var g = this, p = this.options;
        	if(!ids || ids.length==0) return;
        	$(".img_checkbox",g.table).attr("checked",false);
        	var idAry = ids.split(",");
        	$(".img_checkbox",g.table).each(function(){
        		for(var i in idAry){
        			if(this.value==idAry[i]){
        				this.checked = true;
        				break;
        			}
        		}
        	});
        },
        
        //全选
        selectAll:function(){
        	var g = this, p = this.options;
        	$(".img_checkbox",g.table).each(function(){
        		this.checked = true;
        	});
        },
        
        //反选
        invertSelect:function(){
        	var g = this, p = this.options;
        	var selectedAry = $(".img_checkbox:checked",g.table);
        	$(".img_checkbox",g.table).each(function(){
        		this.checked = true;
        	});
        	if(selectedAry.length>0){
        		selectedAry.each(function(){
        			$(this).attr("checked",false);
        		});
        	}
        },
        
        //加载图片数据
        loadImageData:function(data){
        	var g = this, p = this.options;
        	if(data)
        	{
        		g.currentPage = 1;
        		g.totalPage = 0;
        		g.total = 0;
        		
            	//服务端数据
            	if(p.dataAction=='server' && typeof(data)=="string"){
            		p.url = data;
            	}
            	//本地数据
            	else if(p.dataAction=='local'){
            		p.data = data;
            		if(data && $.isArray(data)){
            			p.data[p.recordName] = data;
            			p.data[p.totalName] = data.length;
            		}
            		if(p.data[p.recordName]){
            			g.total = p.data[p.recordName].length;
            			g.totalPage = Math.ceil(g.total/g.pageCount);
            		}
            	}
        	}
        	
        	g.currentData = [];
        	
        	//加载服务端数据
        	if(p.dataAction=='server'){
        		var params = [];
        		if(p.parms){
        			//数组
        			if($.isArray(p.parms)){
        				$(p.parms).each(function(){
        					params.push({name:this.name,value:this.value});
        				});
        			}
        			//对象
        			else if (typeof(p.parms) == "object")
                    {
                        for (var name in p.parms)
                        {
                        	params.push({ name: name, value: p.parms[name] });
                        }
                    }        			
        			if(p.usePage===true){
        				params.push({name:p.pageParmName,value:g.currentPage});
        				params.push({name:p.pagesizeParmName,value:g.pageCount});
        			}
        		}
            	if(p.url && p.url.length>0){
    	        	$.ajax({
    	                type: p.method,
    	                cache: false,
    	                url: p.url,
    	                data: params,
    	                dataType: "json",
    	                async:false,
    	                success: function(data){
    	                	if(data && $.isArray(data[p.recordName])){
        	                	g.total = data[p.totalName];
        	                	g.currentData = g.currentData.concat(data[p.recordName]);
        	                	g.totalPage = Math.ceil(g.total/g.pageCount);	
    	                	}
    	                	//展示数据
    	                	g._showImageList();
    	                },
    	                error:function (XMLHttpRequest, textStatus, errorThrown) {
    	                	alert("加载数据出错");
    	                }
    	            });
            	}
        	}
        	//加载本地数据
        	else{
        		g.currentData = g.currentData.concat(p.data[p.recordName]);
        		//分页展示
        		if(p.usePage===true){
        			var start_pos = (g.currentPage-1) * g.pageCount;
        			var end_pos = start_pos + g.pageCount;
        			if(end_pos > g.total) end_pos = g.total;
        			g.currentData = p.data[p.reocrdName].slice(start_pos,end_pos);
        		}
        		//展示数据
        		g._showImageList();
        	}
        },
        
        //展示图片列表
        _showImageList:function(){
        	var g = this, p = this.options;
        	//清空之前的数据
        	g.table.body.empty();
        	//构建分页条
        	if(p.usePage===true){
        		g._buildPageBar();
        	}else{
        		g.pageBar.hide();
        	}
        	
        	//填充表体
        	g._createTableBody();
        	
        	//模版渲染
        	if(p.templateRender && typeof(p.templateRender)=='function'){
	    		$(".template",g.table.body).each(function(){
	    			var datapos = $(this).attr("pos");
	    			p.templateRender(this,g.currentData[datapos],g,p);
	    		});
        	}
        	
        	//启用浏览
    		if(p.isView===true){
    			var viewOption = {
    				    delegate: 'span.image-mark',
    				    type: 'image',
    				    closeOnContentClick: false,
    				    closeBtnInside: false,
    				    mainClass: 'mfp-zoom-in',
    				    image: {
    				        verticalFit: true,
    				        titleSrc: function(item){
    				        	if(item.el.attr("isFirst")=='true'){
    				        		return item.el.attr('name');
    				        	}
    				        	
    				            return item.el.attr('title');
    				        }
    				    },
    				    gallery: {
    				        enabled: true,
    				        navigateByImgClick: true,
    				        preload: [
    				            0,
    				            1
    				        ]
    				    },
    				    zoom: {
    				        enabled: true,
    				        duration: 300,
    				        opener: function(element){
    				            return element.find('img,div');
    				        }
    				    }
    			};
    			
    			//不启用封面
    			if(p.useCover===false){
    				g.table.magnificPopup(viewOption);    				
    			}
    			
    			//启用封面
    			else if(p.useCover===true){
    				for(var i in g.currentData){
    					var img = g.currentData[i];
    					if(!img[p.idField] || img[p.idField]=='') continue;
    					if(!g._hasChildren(img)) continue;
    	   				$('.image-mark-cover[id='+img[p.idField]+']',g.table.body).magnificPopup(viewOption); 
    				}
    			}
    		}        	
        },
        
        //构建分页信息
        _buildPageBar:function(){
        	var g = this, p = this.options;
        	
            $('.pcontrol input', g.pageBar).val(g.currentPage);
            if (!g.totalPage) g.totalPage = 0;
            $('.pcontrol span', g.pageBar).html(g.totalPage);
            
        	var stat = p.pageStatMessage;
        	stat = stat.replace(/{currentPage}/, g.currentPage);
        	stat = stat.replace(/{totalPage}/, g.totalPage);
        	stat = stat.replace(/{pageCount}/, g.pageCount);
        	stat = stat.replace(/{total}/, g.total);
        	$(".l-bar-text-msg",g.pageBar).html(stat);
        	
        	g.pageBar.show();
        },
        
        //创建图片列表表体
        _createTableBody:function(){
        	var g = this, p = this.options;
        	if(!g.currentData || g.currentData.length==0) return "";
        	var t = g.currentData.length;
    		var rowNum = Math.ceil(t/p.colSize);
        	if(p.usePage===true){
            	if(t > g.pageCount) rowNum = p.rowSize;
        	}
        	for(var i=0;i<rowNum;i++){
        		g.addRow(i);
        	}
        },
        
        //创建图片行
        addRow:function(rowid){
        	var g = this, p = this.options;
        	var startpos = p.colSize * rowid;
        	var endpos = startpos + p.colSize;
        	if(endpos > g.currentData.length){
        		endpos = g.currentData.length;
        	}
        	var row = $("<tr rowid='"+rowid+"'></tr>");
        	var htmlArr=[];
        	for(var i=startpos;i<endpos;i++){
        		htmlArr.push(g._createImageDiv(g.currentData[i], rowid, i%p.colSize,i));
        	}
        	row.html(htmlArr.join(""));
        	
        	g.table.body.append(row);
        },
        
        //创建图片DIV
        _createImageDiv:function(img,rowid,colid,pos){
        	var g = this, p = this.options;
        	var htmlArr = [];
        	htmlArr.push("<td style='text-align:"+p.align+";' rowid='"+rowid+"' colid='"+colid+"' id='"+g.id+"_"+pos+"'>");
        	htmlArr.push('<div>');
        	if(p.useCover===false){
        		if(!img[p.urlField] || img[p.urlField]=='') return '';
	        	htmlArr.push('      <span class="image-mark" id="'+img[p.idField]+'" href="'+img[p.urlField]+'" title="'+img[p.titleField]+'">');
	        	htmlArr.push('         <img src="'+img[p.urlField]+'" width="'+p.imgWidth+'px" height="'+p.imgHeight+'px" />');
	        	htmlArr.push('      </span>');
        	}
        	//使用封面
        	else{
        		if(!img[p.idField] || img[p.idField]=='') return '';
            	//有子分类
            	if(img.children && img.children.length>0){
            		var firstChild = img.children[0];
            		if(!img[p.titleField] || img[p.titleField]=='') img[p.titleField] = firstChild[p.titleField];
            		if(!img[p.nameField] || img[p.nameField]=='') img[p.nameField] = firstChild[p.nameField];
            		if(!img[p.urlField] || img[p.urlField]=='') img[p.urlField] = firstChild[p.urlField];
            		var childName = firstChild[p.titleField] || firstChild[p.nameField];
            		htmlArr.push('<span class="image-mark-cover" id="'+img[p.idField]+'">');
            		//设置封面
    	        	htmlArr.push('      <span class="image-mark" id="'+img[p.idField]+'" href="'+img[p.urlField]+'" title="'+img[p.titleField]+'" name="'+childName+'" isFirst="true">');
    	        	htmlArr.push('         <img src="'+img[p.urlField]+'" width="'+p.imgWidth+'px" height="'+p.imgHeight+'px" />');
    	        	htmlArr.push('      </span>');

            		for(var i in img.children){
            			var child = img.children[i];
            			if(child[p.urlField]==img[p.urlField]) continue;
            			var title = child[p.titleField];
            			if(!title || title==''){
            				title = child[p.nameField];
            				if(!title) title = '';
            			}
            			htmlArr.push('<span class="image-mark" href="'+child[p.urlField]+'" title="'+title+'" style="display:none">');
            			htmlArr.push('   <div src="'+child[p.urlField]+'" width="'+p.imgWidth+'px" height="'+p.imgHeight+'px" />');
            			htmlArr.push('</span>');
            		}
            		
            		htmlArr.push('</span>');
            	}
            	//无子分类
            	else{
            		if(!img[p.urlField] || img[p.urlField]=='') return '';
            		if(!img[p.nameField] || img[p.nameField]=='') return '';
            		if(!img[p.titleField] || img[p.titleField]=='') img[p.titleField] = img[p.nameField];
            		htmlArr.push('<span class="image-mark-cover" id="'+img[p.idField]+'">');
            		//设置封面
    	        	htmlArr.push('      <span class="image-mark" id="'+img[p.idField]+'" href="'+img[p.urlField]+'" title="'+img[p.titleField]+'">');
    	        	htmlArr.push('         <img src="'+img[p.urlField]+'" width="'+p.imgWidth+'px" height="'+p.imgHeight+'px" />');
    	        	htmlArr.push('      </span>');
    	        	
    	        	htmlArr.push('</span>');
            	}
        	}

        	//下标题
        	htmlArr.push('      <div id="caption_'+g.id+'_'+pos+'" style="color:black;">');
        	
        	if(p.showName || p.checkBox){
	        	htmlArr.push('      <div style="padding:3px;word-wrap:break-word;">');	        	
	        	if(p.checkBox){
	        		htmlArr.push('    <input type="checkbox" class="img_checkbox" id="checkbox_'+g.id+"_"+pos+'" style="vertical-align:middle;" value="'+img[p.idField]+'" />');
	        	}
	        	if(p.showName){
	        		htmlArr.push('    <span style="vertical-align:middle;">'+img[p.nameField]+'</span>');
	        	}	        	
	        	htmlArr.push('     </div>');	
        	}
        	
        	htmlArr.push('          <div style="border:0px;" class="template" rowid="'+rowid+'" colid="'+colid+'" pos="'+pos+'" id="template_'+g.id+'_'+pos+'"></div>');
	        htmlArr.push('      </div>');
	        
        	htmlArr.push('</div>');     	
        	htmlArr.push("</td>");
        	
        	return htmlArr.join("");
        },
        
        //是否有子分类
        _hasChildren:function(img){
        	if(img.children && img.children.length>0) return true;
        	return false;
        },
        
        //清空图片列表
        clearImageList:function(){
        	var g = this, p = this.options;
        	g.imageList.html("");
        	g.table.body = null;
        	g.table = null;
        	g.pageBar = null;
        	
            //重置数据
    		g.currentPage = 1;
    		g.totalPage = 1;
    		g.pageCount = p.rowSize * p.colSize;
    		g.currentData = [];
    		g.total = 0;
        },
        
        //重新加载
        reload:function(){
        	var g = this, p = this.options;
        	g.clearImageList();
        	g._render();
        },   
        
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
})(jQuery);