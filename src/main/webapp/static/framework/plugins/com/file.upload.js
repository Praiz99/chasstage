
(function ($)
{
    var l = $.ligerui;

    $.fn.ligerFileUpload = function (options)
    {
        return $.ligerui.run.call(this, "ligerFileUpload", arguments);
    };

    $.fn.ligerGetFileUploadManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetFileUploadManager", arguments);
    };
	
    //接口方法扩展
    $.ligerMethos.FileUpload = $.ligerMethos.FileUpload || {};
    
    $.ligerui.controls.FileUpload = function (element, options)
    {
        $.ligerui.controls.FileUpload.base.constructor.call(this, element, options);
    };
    
    $.ligerDefaults.FileUpload = {
            iconPath:"static/framework/plugins/com/images/filetype/",
            swf:"static/framework/plugins/uploadify/uploadify.swf",
            uploader:'',
            bizId:'',
            bizType:'',
            bizTableName:'',
			width: 70,
			height:25,
			buttonClass:'browse_btn',
			auto:true,
			buttonText:'浏览...',
			fileObjName:'fileData',
			progressData:'percentage',
			successTimeout:60,
			isCustom:false,
			completeShowType:'1',
			removeCompleted:false,
			overrideEvents:['onUploadError','onSelectError'],
		    onSelectError:uploadify_onSelectError,
		    onUploadError:uploadify_onUploadError,
			data:null
    };
    
    $.ligerui.controls.FileUpload.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.FileUpload';
        },
        __idPrev: function ()
        {
            return 'FileUpload';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.FileUpload;
        },
        _init: function ()
        {
            $.ligerui.controls.FileUpload.base._init.call(this);
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
            if(!p.iconPath || p.iconPath.length==0) p.iconPath = "static/framework/plugins/com/images/filetype/";
            p.iconPath = p.rootPath + p.iconPath;
            
            //上传swf路径
            if(!p.swf || p.swf.length==0) p.swf = "static/framework/plugins/uploadify/uploadify.swf";
            p.swf = p.rootPath + p.swf;
            
            //判断上传模式
            if(typeof(p["isCustom"])!= 'boolean'){
            	p["isCustom"] = false;
            }
            
            p.formData = p.formData || {};
            
            p["uploader"] = p["uploader"] || '';
            
        	//通用上传模式需要获取上传地址
        	if(p["isCustom"]===false){
        		
        		//获取上传地址
                if(!p["getAddressUrl"] || p["getAddressUrl"]==''){
                	p["getAddressUrl"] = p.rootPath + "com/frws/getUploadUrl";
                }
               	$.ajax({
            		async:false,
            		dataType:'json',
            		url:p.getAddressUrl,
            		success:function(data,statu){
            			if(statu){
	            			if(data.status){
	            				p.uploader = data.uploadUrl;
	            				p.formData = $.extend(p.formData,data);
	            			}else{
	            				alert(data.msg);
	            			}
            			}
            			return ;
            		}
            	});
               	
                //校验是否有传bizId和bizType
                if(!p.formData["bizId"] || p.formData["bizId"]==''){
                	p.formData["bizId"] = p["bizId"];
                }
                if(!p.formData["bizType"] || p.formData["bizType"]==''){
                	p.formData["bizType"] = p["bizType"];
                }
                if(!p.formData["bizIableName"] || p.formData["bizIableName"]==''){
                	p.formData["bizIableName"] = p["bizIableName"];
                }
                p.formData["bizId"] = p.formData["bizId"] || '';
                p.formData["bizType"] = p.formData["bizType"] || '';
                p.formData["bizIableName"] = p.formData["bizIableName"] || '';
                if(p.formData["bizId"]=='' || p.formData["bizType"]==''){
                	alert("bizId和bizType不能为空");
                	g.destory();
                	return ;
                }
        	}
        	
            if(!p["uploader"] || p["uploader"]==''){
            	alert("未设置上传地址");
            	g.destory();
            	return ;
            }  
            
	        //上传进度条模版
	        if(!p.itemTemplate || p.itemTemplate.length==0){
	        	  p.itemTemplate = g._createItemTemplate();
	        }
	        
	        //上传文件数量限制
	        if(p.uploadLimit && p.uploadLimit>0){
	        	p["queueSizeLimit"] = p.uploadLimit;
	        	p.uploadLimit = 0;
	        }
            
            //上传成功后给隐藏域赋值
            function uploadSuccess(file,data,response){ 
            	data = eval("("+data+")");
            	if(!data || !data.fileId || data.fileId.length==0) return;
            	
            	var fileObj = {};
            	fileObj.id = data.fileId;
            	fileObj.name = file.name;
            	fileObj.downUrl = data.downUrl;
            	fileObj.deleteUrl = data.deleteUrl;
            	fileObj.fileId = data.fileId;
            	
            	var fileType = "";
            	if(file.type && file.type != ''){
            		fileType = file.type.substring(1);
            	}
            	fileObj.type = fileType;
            	
            	var suffix = 'B';
    			var fileSize = file.size;
    			if(fileSize > 1024){
    				fileSize = Math.round(fileSize / 1024);
    				suffix = 'KB';
    			}
    			if (fileSize > 1000) {
    				fileSize = Math.round(fileSize / 1000);
    				suffix = 'MB';
    			}
    			if (fileSize > 1000) {
    				fileSize = Math.round(fileSize / 1000);
    				suffix = 'GB';
    			}
    			fileObj.size = fileSize + suffix;
    			
            	if(p.completeShowType=='1'){
                	$(".data","#"+file.id).remove();
                	$(".fileName","#"+file.id).css("cursor","pointer");
                	//点击下载
                	$(".fileName","#"+file.id).bind('click',function(){
                		window.open(fileObj.downUrl);
                	});
                	//删除
                	var delBtn = $("<a href='javascript:void(0)'>删除</a>");
                	$("#"+file.id).append(delBtn);
                	delBtn.bind('click',function(){
                		if(g.deleting===true) return;
                		g.deleting = true;
                		$.ajax({
                			url:fileObj.deleteUrl,
                			dataType:'jsonp',
                			jsonpCallback:'deleteCallBack',
                			success:function(responseData){
                				g.deleting=false;
                				alert(responseData.msg);
                				$("#"+file.id).remove();
                				for(var i in g.fileIds){
                					if(g.fileIds[i] == fileObj.fileId){
                						g.fileIds.splice(i,1);
                						g.uploadFiles.splice(i, 1);
                						break;
                					}
                				}
                				g.hiddenInput.val(g.fileIds.join(","));
                				g._resetUploadLimit();
                			}
                		});
                	});
            	}
            	
            	if(!g.fileIds) g.fileIds = [];
            	g.fileIds.push(fileObj.fileId);
            	g.uploadFiles.push(fileObj);
            	g.hiddenInput.val(g.fileIds.join(","));
            	g._resetUploadLimit();
            }
            
            //单个文件上传成功后
            var loadSuccess = p.onUploadSuccess;
            if(!loadSuccess){
            	p.onUploadSuccess = uploadSuccess;
            }else{                
                p.onUploadSuccess = function(file,data,response){
                	uploadSuccess(file,data,response);
                	loadSuccess(file,data,response);
                };
            }
            
            //上传状态
            g.isComplete = false;
            
            //队列中的所有文件都已处理
            function allFileProcessed(data){
            	g.isComplete = true;
            }
            
            var allProcess = p.onQueueComplete;
            if(!allProcess){
            	p.onQueueComplete = allFileProcessed;
            }else{                
                p.onQueueComplete = function(queueData){
                	allFileProcessed();
                	allProcess(queueData);
                };
            }
            
            p.onSelect = function(file){
            	if(!file) return;
				if(p.queueSizeLimit && p.queueSizeLimit>0 && g.fileIds && (g.fileIds.length+1)>p.queueSizeLimit){
					g.executeMethod('cancel', file.id);
					alert('已选文件的数量超过了上传文件数量限制(' + p.queueSizeLimit + ')');
					return;
				}
            	var fileNameSpan = $("#"+file.id).find("span[class='fileName']");
            	var htmlString = fileNameSpan.html();
            	var imgString = '<img src="'+p.iconPath+file.type.substring(1)+'.gif" />';
            	htmlString = imgString + htmlString;
            	fileNameSpan.html(htmlString);           	
            };
            
            p.onSelectError = function uploadify_onSelectError(file, errorCode, errorMsg){
            	// 上传配置
            	var settings = this.settings;
            	//处理错误
            	switch (errorCode) {
            	case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
            		if (settings.queueSizeLimit > errorMsg) {
            			this.queueData.errorMsg = "已选文件的数量超过了上传文件数量限制(" + p.queueSizeLimit + ")";
            			
            		} else {
            			this.queueData.errorMsg = '已选文件的数量超过了上传文件数量限制(' + p.queueSizeLimit + ')';
            		}
            		break;
            	case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
            		this.queueData.errorMsg = "文件大小超过限制，单个文件最大为 " + this.settings.fileSizeLimit;
            		break;
            	case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
            		this.queueData.errorMsg = "文件大小为0";
            		break;
            	case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
            		this.queueData.errorMsg = "文件格式不正确，仅限 " + this.settings.fileTypeExts;
            		break;
            	}
            	if (errorCode != SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
            		delete this.queueData.files[file.id];
            	}
            }
            
            //获取flash 对象id
            p.onInit = function(instance){
            	g.movieName = instance.movieName;
            };
            
            //初始化计数器
            g.counter = 9999;
            
            //上传的文件数
            g.uploadFiles = [];
            
            //是否正在删除
            g.deleting = false;
        },
        _render: function ()
        {
        	var g = this, p = this.options;
        	g.upload = $(g.element);
        	var htmlArr = [];
        	htmlArr.push('<input type="file" name="file_upload_'+g.id+'" id="file_upload_'+g.id+'" />');
        	//影藏域
        	htmlArr.push('<input type="hidden" name="fileIds" />');
        	
        	//队列DIV
        	if(!p.queueID || $("#"+p.queueID).length==0){
        		htmlArr.push('<div id="file_queue_'+g.id+'">');
        		htmlArr.push('</div>');
        		p.queueID = 'file_queue_' + g.id;
        	}        	        	
        	g.upload.html(htmlArr.join(''));
        	
        	//文件队列DIV
        	g.fileQueue = $("#"+p.queueID);
        	g.fileInput = $("#file_upload_"+g.id,g.upload);
        	g.hiddenInput = $("[name='fileIds']",g.upload);
        	
        	g.fileInput.uploadify(p);
        	$("#file_upload_"+g.id+"-button",g.upload).removeClass('uploadify-button');
        	g.fileIds = [];
        	g.isDestory = false;
        	
        	//初始化数据
        	var initData = p.data;
        	if(!p.data || ($.type(p.data)=='array' && p.data.length==0)){
        		var bid = p.formData["bizId"] || '';
        		initData = g._getFilesInfoByBid(bid);
        	}
           	//初始化数据
        	g.setFileData(initData);
        },
        
        //根据bizId获取文件数据
        _getFilesInfoByBid:function(bid){
        	if(!bid || bid=='') return null;
        	
        	var p = this.options, filesInfo = null;
        	
        	var initUrl = p.initUrl || "com/frws/getFilesInfoByBizId";
        	initUrl = p.rootPath + initUrl;
           	$.ajax({
        		async:false,
        		dataType:'json',
        		url:initUrl,
        		type:"POST",
        		data:{"bid":bid,"btype":p["bizType"]},
        		success:function(data,statu){
        			if(data){
        				filesInfo = data;
        			}
        		}
        	});
           	
           	return filesInfo;
        },
        
        //设置数据
        setFileData:function(fileData){
        	var g = this, p = this.options;
        	g.fileIds = [];
        	g.uploadFiles = [];
    		if(p.completeShowType=='1'){
    			if($.isArray(fileData)){
    				for(var i in fileData){
    					if(p.removeCompleted===false){
    						g._createItem(fileData[i]);
    					}
    					g.fileIds.push(fileData[i].fileId);
    					g.uploadFiles.push(fileData[i]);
    				}
    			}else if(typeof(fileData) == 'object' && fileData!=null){
    				if(p.removeCompleted===false){        					
    					g._createItem(fileData);
    				}
					g.fileIds.push(fileData.fileId);
					g.uploadFiles.push(fileData);
    			}        			
    			g.hiddenInput.val(g.fileIds.join(","));
    			g._resetUploadLimit();
    		}
        },
        
        //获取上传成功的文件id
        getFileIds:function(){
        	var g = this, p = this.options;
        	if(!g.fileIds) g.fileIds = [];
        	return g.fileIds.join(",");
        },
        //获取上传状态
        getUploadStatus:function(){
        	var g = this, p = this.options;
        	return g.isComplete;
        },
        //清空当前控件DIV中的内容
        _clear:function(){
        	var g = this, p = this.options;
        	if(g.isDestory) return;
        	g.fileInput.uploadify('destroy');
        	g.upload.html('');
        	g.fileInput = null;
        	g.fileQueue = null;
        	g.hiddenInput = null;
        	g.isComplete = false;
        	g.fileIds = []; 
        	g.counter = 9999;
        	g.uploadFiles=[];
        	g.deleting=false;
        },
        //重新渲染
        reRender:function(){
        	var g = this, p = this.options;
        	g._clear();
        	g.trigger("render");
        	g._render();
        },        
        //销毁上传控件
        destory:function(){
        	var g = this, p = this.options;
        	if(g.isDestory) return;
        	g._clear();
        	g.upload.liger('destory');
        	g.isDestory = true;
        },
        
        //上传itemTemplate
        _createItemTemplate:function(){
        	var g = this, p = this.options;
        	if(!p.completeShowType || p.completeShowType=='' || p.completeShowType=='0')
        		return false;
        	var itemTemplateArr = [];
        	if(p.completeShowType=='1'){
              	itemTemplateArr.push('<div id="${fileID}" class="uploadify-queue-item">');
              	itemTemplateArr.push('  <span class="fileName">');
              	itemTemplateArr.push('    ${fileName} (${fileSize})');
              	itemTemplateArr.push('    <span class="data">');
              	itemTemplateArr.push('    </span>');
              	itemTemplateArr.push('  </span>');              	
              	itemTemplateArr.push('</div>');
        	}
        	
        	return itemTemplateArr.join("");
        },
        //创建item
        _createItem:function(file){
        	var g = this, p = this.options;
        	if(!g.movieName){
        		alert("请设置正确的swf路径");
        		return;
        	}
        	//获取当前fileQueue中文件的数量
        	if(g.counter < 9999) g.counter -= 1;
        	var htmlArr = [];
        	var fileName = file.name;
        	if(fileName.length > 23) fileName = fileName.substring(0,20) + " ...";
        	htmlArr.push('<div id="' + g.movieName + '_' + g.counter + '" class="uploadify-queue-item">');
        	htmlArr.push('  <span class="fileName" style="cursor: pointer;">');
        	htmlArr.push('  <img src="'+p.iconPath+file.type.toLowerCase()+'.gif">');
        	htmlArr.push('    '+fileName+' ('+file.size+')');
        	htmlArr.push('  </span>');
        	htmlArr.push('</div>');
        	var item = $(htmlArr.join(""));
        	g.fileQueue.append(item);
        	$("span.fileName",item).bind('click',function(){
        		window.open(file.downUrl);
        	});
        	var delBtn = $('<a style="padding-left:10px;" href="javascript:void(0)">删除</a>');
        	item.append(delBtn);
        	delBtn.bind('click',function(){
				if(g.deleting===true) return;
				g.deleting = true;
        		$.ajax({
        			url:file.deleteUrl,
        			dataType:'jsonp',
        			jsonpCallback:'deleteCallBack',
        			success:function(responseData){
        				g.deleting=false;
        				alert(responseData.msg);                				
        				item.remove();
        				for(var i in g.fileIds){
        					if(g.fileIds[i] == file.fileId){
        						g.fileIds.splice(i,1);
        						g.uploadFiles.splice(i, 1);
        						break;
        					}
        				}
        				g.hiddenInput.val(g.fileIds.join(","));
        				g._resetUploadLimit();
        			}
        		});
        	});
        },
        
        //重置文件上传限制
        _resetUploadLimit:function(){
        	var g = this, p = this.options;
			if(p.queueSizeLimit && p.queueSizeLimit>0){
				var limit = p.queueSizeLimit - g.fileIds.length;
				if(limit<1){
					limit = 1;
				}
	        	setTimeout(function(){
	        		g.changeSettings('queueSizeLimit', limit);
	        	},1000);
			}        	
        },
        
        //开始上传
        startUpload:function(){
        	var g = this, p = this.options;
        	if(!p.uploader || p.uploader=='') {
        		alert("请设置上传地址");
        		return;
        	}
        	$("#file_upload_"+g.id,g.upload).uploadify('upload','*');
        },
        //执行相应的方法
        executeMethod:function(methodName,param){
        	var g = this, p = this.options;
        	if(!p.uploader || p.uploader=='') {
        		alert("请设置上传地址");
        		return;
        	}
        	if(methodName && methodName.length>0){
        		if(param && param.length>0){
        			$("#file_upload_"+g.id,g.upload).uploadify(methodName,param);
        		}else{
        			$("#file_upload_"+g.id,g.upload).uploadify(methodName);
        		}
        	}
        },
        
        //执行相应的方法
        changeSettings:function(paramName,value){
        	var g = this, p = this.options;
        	if(!p.uploader || p.uploader=='') {
        		alert("请设置上传地址");
        		return;
        	}
        	if(paramName && paramName.length>0){
        		$("#file_upload_"+g.id,g.upload).uploadify('settings',paramName,value);
        	}
        }        
    });
})(jQuery);