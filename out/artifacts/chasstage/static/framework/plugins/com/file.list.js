
(function ($)
{
    var l = $.ligerui;

    $.fn.ligerFileList = function (options)
    {
        return $.ligerui.run.call(this, "ligerFileList", arguments);
    };

    $.fn.ligerGetFileListManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetFileListManager", arguments);
    };
	
    //接口方法扩展
    $.ligerMethos.FileList = $.ligerMethos.FileList || {};
    
    $.ligerui.controls.FileList = function (element, options)
    {
        $.ligerui.controls.FileList.base.constructor.call(this, element, options);
    };
    
    $.ligerDefaults.FileList = {
            iconPath:"static/framework/plugins/com/images/filetype/",
			data:null,
			itemTemplate:null
    };
    
    $.ligerui.controls.FileList.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.FileList';
        },
        __idPrev: function ()
        {
            return 'FileList';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.FileList;
        },
        _init: function ()
        {
            $.ligerui.controls.FileList.base._init.call(this);
            var g = this, p = this.options;           
            //获取根路径
            function getUrlRootPath(){
            	//获取应用上下文路径
            	var contextpath = window.document.location.pathname;
            	var i = contextpath.indexOf("/");
            	if(i == 0){
            		contextpath = contextpath.substr(1);
            	}
            	i = contextpath.indexOf("/");
            	contextpath = "/" + contextpath.substr(0, i + 1);
            	
            	return contextpath;
            }
            
            p.rootPath = getUrlRootPath(); 
            
            //图标路径
            p.iconPath = p.iconPath || "static/framework/plugins/com/images/filetype/";
            p.iconPath = p.rootPath + p.iconPath;
            
            if($.type(p.data)!='array'){
            	alert("请设置合法的文件元数据");
            	return;
            }
        },
        _render: function ()
        {
        	var g = this, p = this.options;
        	g.list = $(g.element);
        	
           	//初始化数据
        	$.each(p.data,function(i,file){
        		g._createItem(file);
        	});
        },

        //清空当前控件DIV中的内容
        _clear:function(){
        	var g = this, p = this.options;
        	if(g.isDestory) return;
        	g.list.html('');
        },
        
        //重新渲染
        reRender:function(){
        	var g = this, p = this.options;
        	g._clear();
        	g.trigger("render");
        	g._render();
        },        
        
        //销毁文件列表
        destory:function(){
        	var g = this, p = this.options;
        	if(g.isDestory) return;
        	g._clear();
        	g.list.liger('destory');
        	g.isDestory = true;
        },
        
        //上传itemTemplate
        _createDefaultTemplate:function(){
        	var g = this, p = this.options;
        	var itemTemplateArr = [];
              	itemTemplateArr.push('<div id="{fileID}" class="uploadify-queue-item">');
              	itemTemplateArr.push('  <span class="fileName" style="cursor: pointer;" title="点击下载">');
              	itemTemplateArr.push('    <img src="'+p.iconPath+'{fileType}.gif"/>');
            	itemTemplateArr.push('    {fileName} ({fileSize})');
              	itemTemplateArr.push('  </span>');              	
              	itemTemplateArr.push('</div>');
        	
        	return itemTemplateArr.join("");
        },
        
        //创建item
        _createItem:function(file){
        	var g = this, p = this.options;
        	var fileName = file.name;
        	if(fileName.length > 23) fileName = fileName.substring(0,20) + " ...";
        	
        	var template = p.itemTemplate || g._createDefaultTemplate();
        	template = template.replace(/{fileId}/, file.fileId);
        	template = template.replace(/{fileName}/,fileName);
        	template = template.replace(/{fileType}/,file.type.toLowerCase());
        	template = template.replace(/{fileSize}/,file.size);
        	
        	var item = $(template);
        	g.list.append(item);
        	
        	$("span.fileName",item).bind('click',function(){
        		window.open(file.downUrl);
        	});
        }   
    });
})(jQuery);