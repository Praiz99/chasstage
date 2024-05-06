(function ($)
{
    var l = $.ligerui;

    $.fn.ligerImageUpload = function (options)
    {
        return $.ligerui.run.call(this, "ligerImageUpload", arguments);
    };

    $.fn.ligerGetImageUploadManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetImageUploadManager", arguments);
    };
	
    //接口方法扩展
    $.ligerMethos.ImageUpload = $.ligerMethos.ImageUpload || {};
    
    $.ligerui.controls.ImageUpload = function (element, options)
    {
        $.ligerui.controls.ImageUpload.base.constructor.call(this, element, options);
    };
    
    $.ligerDefaults.ImageUpload = {
            iconPath:"static/framework/plugins/com/images/filetype/",
            swf:"static/framework/plugins/uploadify/uploadify.swf",
            bizId:'',
            bizType:'',
            bizTableName:'',
			width: 70,
			height:25,
			buttonClass:'browse_btn',
			auto:true,
			buttonText:'选择照片',
			fileObjName:'fileData',
			progressData:'percentage',
			successTimeout:20,
			uploadScript:'',
			completeShowType:'3',
			removeCompleted:false,
			removeTimeout:0,
			data:null,
			imageTableID:null,
			imgTableOptions:null,
			imgWidth:90,
			imgHeight:100,
			defaultIcon:null,
			isView:false,
			isCustom:false,
			queueID:null,
			overrideEvents:['onSelectError','onuploadScriptror','onCancel'],
			onSelectError:null,//uploadify_onSelectError,
			onuploadScriptror:null,//uploadify_onuploadScriptror,
			operateCallBack:null
    };
    
    $.ligerui.controls.ImageUpload.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.ImageUpload';
        },
        __idPrev: function ()
        {
            return 'ImageUpload';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.ImageUpload;
        },
        _init: function ()
        {
            $.ligerui.controls.ImageUpload.base._init.call(this);
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
            
            //设置图片列表的配置
            p["imgTableOptions"] = p["imgTableOptions"] || {};
            if(p['imgWidth']){
            	p.imgTableOptions['imgWidth'] = p['imgWidth'];
            }
            if(p['imgHeight']){
            	p.imgTableOptions['imgHeight'] = p['imgHeight'];
            }
            
            //判断上传模式
            if(typeof(p["isCustom"])!= 'boolean'){
            	p["isCustom"] = false;
            }
            
            p.formData = p.formData || {};
            
            p["uploadScript"] = p["uploadScript"] || '';
            
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
	            				p.uploadScript = data.uploadUrl;
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
        	
            if(!p["uploadScript"] || p["uploadScript"]==''){
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
	        
	        var successLimit=0;
            //上传成功后给隐藏域赋值
            function uploadSuccess(file,data,response){           	
            	data = eval("("+data+")");
            	if(!data || !data.fileId || data.fileId.length==0) {
            		alert(data.msg);
            		return;
            	}
            	
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
    			$data	= $(this).data('uploadifive'),
                settings = $data.settings;
    			
            	if(p.completeShowType=='1'){
            		$(".fileinfo","#"+settings.id+"-file-"+successLimit).remove();
                	$(".close","#"+settings.id+"-file-"+successLimit).remove();
                	$(".fileName","#"+settings.id+"-file-"+successLimit).css("cursor","pointer");
                	$(".fileName","#"+settings.id+"-file-"+successLimit).bind('click',function(){
                		window.open(data.downUrl);
                	});
                	var delBtn = $("<a style='padding-left:10px;' href='javascript:void(0)' id='"+successLimit+"'>删除</a>");
                	$("#"+settings.id+"-file-"+successLimit).append(delBtn);
                	delBtn.bind('click',function(){
                		var aId=$(this).attr("id");
                		if(g.deleting===true) return;
                		g.deleting = true;
                		$.ajax({
                			url:data.deleteUrl,
                			dataType:'jsonp',
                			jsonpCallback:'deleteCallBack',
                			success:function(responseData){
                				g.deleting = false;
                				alert(responseData.msg);                				
                				$("#"+settings.id+"-file-"+aId).remove();
                				for(var i in g.fileIds){
                					if(g.fileIds[i] == data.fileId){
                						g.fileIds.splice(i,1);
                						g.uploadFiles.splice(i, 1);
                						break;
                					}
                				}
                				
                				g._resetUploadLimit(-1);
                				
                            	if(p.operateCallBack){
                            		p.operateCallBack(g,fileObj);
                            	}
                			}
                		});
                	});
                	successLimit++;
            	}
            	if(p.completeShowType=='2'){
            		$data	= $(this).data('uploadifive'),
                    settings = $data.settings;
            		$("#"+settings.id).hide();
            		g.noPic.hide();
            		g.viewImage.attr("href",data.downUrl);
            		g.viewImage.attr("title",file.name);
            		$("img:first",g.viewImage).attr("src",data.downUrl);
            		$("img:first",g.viewImage).attr("title",file.name);
            		g.viewImage.show();
            		
            		g.imageOperate.empty();
            		var delBtn = $("<a href='javascript:void(0)'>删除</a>");
            		g.imageOperate.append(delBtn);
            		delBtn.bind('click',function(){
            			if(g.deleting===true)return;
            			g.deleting = true;
                		$.ajax({
                			url:data.deleteUrl,
                			dataType:'jsonp',
                			jsonpCallback:'deleteCallBack',
                			success:function(responseData){
                				g.deleting = false;
                				alert(responseData.msg);                				
                				$("#"+file.id).remove();
                				for(var i in g.fileIds){
                					if(g.fileIds[i] == data.fileId){
                						g.fileIds.splice(i,1);
                						g.uploadFiles.splice(i, 1);
                						break;
                					}
                				}
                				$("#uploadifive-file_upload_"+g.id).show();
            					g.noPic.show();
            					g.viewImage.hide();
                        		$("img:first",g.viewImage).remove(); 
                        		g.viewImage.append('<img style="width:'+p.imgWidth+'px;height:'+p.imgHeight+'px;cursor:pointer;" />');
                				delBtn.unbind('click');
                				delBtn.remove();
                				g.imageOperate.hide();
                				
                				g._initUploadControl();
                				g.fileInput.show();
                				
                            	if(p.operateCallBack){
                            		p.operateCallBack(g,fileObj);
                            	}
                			}
                		});
            		});
            	}
            	
            	if(p.completeShowType=='3'){
            		var img = {};
            		img.id = data.fileId;
            		img.name = file.name;
            		img.downUrl = data.downUrl;
            		img.deleteUrl = data.deleteUrl;
            		img.fileId = data.fileId;
            		g.imageTable.addImage(img);
            	}
            	
            	if(!g.fileIds) g.fileIds = [];
            	g.fileIds.push(data.fileId);
            	g.hiddenInput.val(g.fileIds.join(","));
            	g.uploadFiles.push(fileObj);
            	
            	if(p.operateCallBack){
            		p.operateCallBack(g,fileObj);
            	}
            }
            //单个文件上传成功后
            var loadSuccess = p.onUploadComplete;
            if(!loadSuccess){
            	p.onUploadComplete = uploadSuccess;
            }else{                
                p.onUploadComplete = function(file,data,response){
                	uploadSuccess(file,data,response);
                	loadSuccess(file,data,response);
                };
            }
            
            //上传状态
            g.isComplete = false;
            //队列中的所有文件都已处理
            function allFileProcessed(data){
            	g.isComplete = true;
            	if(p.completeShowType=='2'){
            		g.fileInput.empty();
            		g.fileInput.hide();
            		g.imageOperate.show();
            	}         	
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
            	//处理文件图标
            	g.fileQueue.find(".uploadifive-queue-item").each(function(idx,ele){
            		$(ele).find("span.fileName").each(function(i,o){
            			if($(o).find("img").length==0){
            				var htmlString = $(o).html();
            				var i = htmlString.lastIndexOf(".");
            				if(i>-1){
            					var imgString = '<img src="'+p.iconPath+htmlString.substring(i+1)+'.gif" style="margin-right:4px;"/>';
            					htmlString = imgString + htmlString;
            					$(o).html(htmlString);
            				}
            			}
            		});
            	});           	
            };
            
	        p.onCancel = function(file){
	        	$data = g.fileInput.data('uploadifive');
	        	if(file && file['complete']){
	        		$data.removeQueueItem(file,true);
	        		g._resetUploadLimit(1);
	        	}
	        };
	        
            //获取flash 对象id
            p.onInit = function(instance){
            	g.movieName = g.id;//instance.movieName;
            };
            
            if(p.completeShowType=='2'){
            	p.defaultIcon = p.defaultIcon || "static/framework/plugins/com/images/assets/nopic.jpg";
          		p.multi = false;
          		p.removeCompleted = true;
            }
            
            g.uploadFiles = [];
            g.deleting = false;
            g.rotating = false;
        },
        _render: function ()
        {
        	var g = this, p = this.options;
        	g.upload = $(g.element);
        	g.upload.addClass("image_upload_"+g.id);
        	var htmlArr = [];
        	if(p.completeShowType=='2'){
        		htmlArr.push('<img src="'+p.rootPath+p.defaultIcon+'" style="width:'+p.imgWidth+'px;height:'+p.imgHeight+'px;cursor:pointer;margin-bottom:3px;" id="no_img_'+g.id+'" />');
        		htmlArr.push('<span class="image-mark" style="width:'+p.imgWidth+'px;height:'+p.imgHeight+'px;cursor:pointer;" id="view_img_'+g.id+'"><img style="width:'+p.imgWidth+'px;height:'+p.imgHeight+'px;cursor:pointer;" /></span>');
        		htmlArr.push('<div style="text-align:center;width:'+p.imgWidth+'px;" id="img_operate_'+g.id+'" />');
        	}
        	htmlArr.push('<div name="file_upload_'+g.id+'" id="file_upload_'+g.id+'"></div>');
        	//隐藏域
        	htmlArr.push('<input type="hidden" name="fileIds" />');
        	
        	//队列DIV
        	if(!p.queueID || $("#"+p.queueID).length==0){
        		htmlArr.push('<div id="file_queue_'+g.id+'">');
        		htmlArr.push('</div>');
        		p.queueID = 'file_queue_' + g.id;
        	}
        	
        	//图片列表
        	if(p.completeShowType=='3'){
        		if(!p.imageTableID || p.imageTableID==''){
	        		htmlArr.push('<div id="imageList_'+g.id+'">');
	        		htmlArr.push('</div>');
	        		p.imageTableID = 'imageList_'+g.id;
        		}
        		g.imageData = {"Rows":[],"Total":0};
        	}
        	
        	g.upload.html(htmlArr.join(''));
        	
        	//文件队列DIV
        	g.fileQueue = $("#"+p.queueID);
        	g.fileInput = $("#file_upload_"+g.id,g.upload);
        	g.hiddenInput = $("[name='fileIds']",g.upload);
        	g.fileIds = [];
        	g.isDestory = false;
        	
        	g._initUploadControl();
        	
        	if(p.completeShowType=='2'){
        		g.noPic = $("#no_img_"+g.id,g.upload);
        		g.viewImage = $("#view_img_"+g.id,g.upload);
        		g.viewImage.hide();
        		g.imageOperate = $("#img_operate_"+g.id,g.upload);
        		g.imageOperate.hide();
        		
        		if(p.isView===true){
					$(".image_upload_"+g.id).magnificPopup({
						delegate: 'span.image-mark',
						type: 'image',
						closeOnContentClick: false,
						closeBtnInside: false,
						mainClass: 'mfp-with-zoom',
						image: {
							verticalFit: true,
							titleSrc: function(item) {
								return item.el.attr('title');
							}
						},
						gallery: {
							enabled: true,
							navigateByImgClick: true,
							preload: [0,1]
						},
						zoom: {
							enabled: true,
							duration: 300, // don't foget to change the duration also in CSS
							opener: function(element) {
								return element.find('img',g.upload);
							}
						}    					
					});
        		}
        	}
        	if(p.completeShowType=='3'){
	        	g.imageList = $("#"+p.imageTableID,g.upload);
        		if($("#"+p.imageTableID,g.upload).length==0){
        			g.imageList = $("#"+p.imageTableID);
        		}
	        	g.imageTable = g.imageList.ligerImageList($.extend({
					 idField:'id',
					 nameField:'name',
					 titleField:'name',
					 urlField:'downUrl',
					 dataAction:'local',
					 templateRender:function(obj,img,l,h){
						var delBtn = $('<a style="padding-left:10px;" href="javascript:void(0)">删除</a>');
						$(obj).append(delBtn);
						delBtn.bind('click',function(){
							if(g.deleting===true) return;
							g.deleting = true;
	                		$.ajax({
	                			url:img.deleteUrl,
	                			dataType:'jsonp',
	                			jsonpCallback:'deleteCallBack',
	                			success:function(responseData){
	                				g.deleting=false;
	                				alert(responseData.msg);
	                				for(var i in g.fileIds){
	                					if(g.fileIds[i] == img.fileId){
	                						g.fileIds.splice(i,1);
	                						g.uploadFiles.splice(i, 1);
	                						break;
	                					}
	                				}
	                				//移除图片
	                        		var rowid = $(obj).attr("rowid");
	                        		var colid = $(obj).attr("colid");
	                        		var pos  = $(obj).attr("pos");
	                        		g.imageTable.removeImage(rowid,colid,pos);
	                        		
	                        		g.hiddenInput.val(g.fileIds.join(","));
	                        		
	                        		g._resetUploadLimit(-1);
	                        		
	                            	if(p.operateCallBack){
	                            		p.operateCallBack(g,img);
	                            	}
	                			}
	                		});
						});
						
						//可旋转
						if(h.useRotate===true){
							var rotate = $('<a class="rotate-btn" style="padding-left:10px;" href="javascript:void(0)">旋转</a>');
							$(obj).append(rotate);
							var rotateUrl = img.downUrl.substring(0,img.downUrl.indexOf("handle")) + "handle/rotate/file/" + img.fileId;
							rotate.bind('click',function(){
								if(g.rotating===true) return;
								g.rotating = true;
								$.ajax({
		                			url:rotateUrl,
		                			dataType:'jsonp',
		                			jsonpCallback:'rotateCallBack',
		                			success:function(responseData){
		                				g.rotating = false;
		                				var rowid = $(obj).attr("rowid");
		                				var colid = $(obj).attr("colid");
		                				var td = $("td[rowid='"+rowid+"'][colid='"+colid+"']",g.imageList);
		                				$(".image-mark > img",td).attr("src",img.downUrl+"&r="+Math.random());
		                			}
								})
							});
						}
					 }
	        	},p.imgTableOptions));
        	}
        	
        	//初始化数据
        	var initData = p.data;
        	if(!p.data || ($.type(p.data)=='array' && p.data.length==0)){
        		var bid = p.formData["bizId"] || '';
        		initData = g._getFilesInfoByBid(bid);
        	}
        	
        	g.setFileData(initData);
        },
        
        //设置数据
        setFileData:function(fileObj){
        	var g = this, p = this.options;
        	g.fileIds = [];
        	g.uploadFiles = [];
    		if(p.completeShowType=='1'){
    			g.fileQueue.html('');
    			if(fileObj instanceof Array){
    				for(var i in fileObj){
    					if(!p.removeCompleted && p.removeCompleted==false){
    						g._createItem(fileObj[i]);
    					}
    					g.fileIds.push(fileObj[i].fileId);
    					g.uploadFiles.push(fileObj[i]);
    				}
    				g._resetUploadLimit(fileObj.length);
    			}else if(fileObj instanceof Object){
    				if(!p.removeCompleted && p.removeCompleted==false){
    					g._createItem(fileObj);
    				}
					g.fileIds.push(fileObj.fileId);
					g.uploadFiles.push(fileObj);
					g._resetUploadLimit(1);
    			}
    			
    			g.hiddenInput.val(g.fileIds.join(","));
    			
    		}else if(p.completeShowType=='2'){
    			var dt = null;
    			if(fileObj instanceof Array){
    				dt = fileObj[0];
    			}else if(fileObj instanceof Object){
    				dt = fileObj;
    			}
    			if(!dt) {
					g.imageOperate.hide();
					g.imageOperate.empty();
					g.viewImage.hide();
					$("#uploadifive-file_upload_"+g.id).show();
    				g.noPic.show();
    				g._initUploadControl();
    				g.fileInput.show();
    				g.hiddenInput.val(g.fileIds.join(","));
    				return;
    			}
    			
				g.fileIds.push(dt.fileId);
				g.uploadFiles.push(dt);
				
				//初始化展示
				$("#uploadifive-file_upload_"+g.id).hide();
				g.noPic.hide();
				g.viewImage.attr("href",dt.downUrl);
				g.viewImage.attr("title",dt.name);
        		$("img:first",g.viewImage).attr("src",dt.downUrl);
        		$("img:first",g.viewImage).attr("title",dt.name);
        		g.viewImage.show();
        		
        		g.fileInput.hide();
        		g.fileInput.empty();
        		
        		g.imageOperate.empty();
        		
        		var delBtn = $("<a href='javascript:void(0)'>删除</a>");
        		g.imageOperate.append(delBtn);
        		
        		g.imageOperate.show();
        		
        		delBtn.bind('click',function(){
        			if(g.deleting===true) return;
        			g.deleting = true;
            		$.ajax({
            			url:dt.deleteUrl,
            			dataType:'jsonp',
            			jsonpCallback:'deleteCallBack',
            			success:function(responseData){
            				g.deleting = false;
            				alert(responseData.msg);                				           					           					
        					g.imageOperate.hide();
        					g.viewImage.hide();
            				for(var i in g.fileIds){
            					if(g.fileIds[i] == dt.fileId){
            						g.fileIds.splice(i,1);
            						g.uploadFiles.splice(i, 1);
            						break;
            					}
            				}
            				delBtn.unbind('click');
            				delBtn.remove();
            				$("#uploadifive-file_upload_"+g.id).show();
            				g.noPic.show();
            				
            				g._initUploadControl();
            				g.fileInput.show();
            				
            				g.hiddenInput.val(g.fileIds.join(","));
            				
                        	if(p.operateCallBack){
                        		p.operateCallBack(g,dt);
                        	}
            			}
            		});
        		});
        		
        		g.hiddenInput.val(g.fileIds.join(","));
    		}
    		
    		//照片列表
    		else if(p.completeShowType=='3'){
    			g.imageTable.reload();
    			if(fileObj instanceof Array){
    				for(var i in fileObj){
    					g.imageTable.addImage(fileObj[i]);
    					g.fileIds.push(fileObj[i].fileId);
    					g.uploadFiles.push(fileObj[i]);
    				}
    				g._resetUploadLimit(fileObj.length);
    			}else if(fileObj instanceof Object){        				
    				g.imageTable.addImage(fileObj);
					g.fileIds.push(fileObj.fileId);
					g.uploadFiles.push(fileObj);
					g._resetUploadLimit(1);
    			}
    			
    			g.hiddenInput.val(g.fileIds.join(","));
    		}
        },
        
        //获取上传成功的文件id
        getFileIds:function(){
        	var g = this, p = this.options;
        	return g.hiddenInput.val();
        },
        
        //通过业务ID获取文件信息
        _getFilesInfoByBid:function(bid){
        	
        	if(!bid || bid=='') return null;
        	
        	var g = this, p = this.options, filesInfo = null;
        	
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
        
        //初始化上传控件
        _initUploadControl:function(){
        	var g = this, p = this.options;
        	if($("#file_upload_"+g.id,g.upload).length==0) return;
        	$("#file_upload_"+g.id,g.upload).empty();
        	$("#file_upload_"+g.id,g.upload).uploadifive(p);
        	$(".swfupload",g.upload).css("position","relative");
        	$("#file_upload_"+g.id+"-button",g.upload).removeClass('uploadify-button');
        	$("#file_upload_"+g.id+"-button",g.upload).css("margin-top","-"+p.height+"px");
        	g.fileInput = $("#file_upload_"+g.id,g.upload);
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
        	g.fileInput.uploadifive('destroy');
        	g.upload.html('');
        	g.fileInput = null;
        	g.fileQueue = null;
        	g.hiddenInput = null;
        	g.isComplete = false;
        	g.fileIds = [];
        	g.uploadFiles = [];
        	g.deleting = false;
        	g.rotating = false;
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
        	if(p.completeShowType=='1' || p.completeShowType=='2'){
        		itemTemplateArr.push('<div id="${instanceID}" class="uploadifive-queue-item">');
              	itemTemplateArr.push('  <span class="filename fileName">');
              	//itemTemplateArr.push('    ${fileName} (${fileSize})');
              	itemTemplateArr.push('  </span>');   
              	itemTemplateArr.push('    <span class="filesize">');
              	itemTemplateArr.push('    </span>');
              	itemTemplateArr.push('<span class="fileinfo"></span><a class="close" href="#">X</a>');
              	itemTemplateArr.push('</div>');
              	if(p.completeShowType=='2'){
              		p.multi = false;
              		p.removeCompleted = true;
              	}
        	}else if(p.completeShowType=='3'){
        		p.multi = true;
        		p.removeCompleted = true;
        		p.removeTimeout = 1.5;
            	itemTemplateArr.push('<div id="${fileID}" class="uploadifive-queue-item">');
            	itemTemplateArr.push('  <span class="filename fileName">');
            	//itemTemplateArr.push('    ${fileName} (${fileSize})');
            	itemTemplateArr.push('  </span>');
              	itemTemplateArr.push('  <span class="filesize">');
              	itemTemplateArr.push('  </span>');            	
            	itemTemplateArr.push('  <span class="fileinfo">');
            	itemTemplateArr.push('  </span>');
            	itemTemplateArr.push('  <div class="progress">');
            	itemTemplateArr.push('    <div class="progress-bar"></div>');
            	itemTemplateArr.push('  </div>');
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
        	var count = $(".uploadifive-queue-item",g.fileQueue).length;
        	if(count > 0) count += 1;
        	var htmlArr = [];
        	htmlArr.push('<div id="' + g.movieName + '_' + count + '" class="uploadifive-queue-item">');
        	htmlArr.push('  <span class="fileName" style="cursor: pointer;">');
        	htmlArr.push('  <img src="'+p.iconPath+file.type+'.gif">');
        	htmlArr.push('    '+file.name+' ('+file.size+')');
        	htmlArr.push('  </span>');
        	htmlArr.push('</div>');
        	var item = $(htmlArr.join(""));
        	g.fileQueue.append(item);
        	
        	$(".fileName",item).bind('click',function(){
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
        				g.deleting = false;
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
        				
        				g._resetUploadLimit(-1);
        				
                    	if(p.operateCallBack){
                    		p.operateCallBack(g,file);
                    	}
        			}
        		});
        	});
        },     
        removeParm : function(name)
        {
            var g = this;
            var parms = g.get('formData');
            if (!parms) parms = {};
            if (parms instanceof Array)
            {
                removeArrItem(parms, function (p) { return p.name == name; }); 
            } else
            {
                delete parms[name];
            }
            g.set('formData', parms);
        },
        setParm: function (name, value)
        {
            var g = this;
            var parms = g.get('formData');
            if (!parms) parms = {};
            if (parms instanceof Array)
            {
                removeArrItem(parms, function (p) { return p.name == name; });
                parms.push({ name: name, value: value });
            } else
            {
                parms[name] = value;
            }
            g.set('formData', parms); 
        },
        
        //重置文件上传限制
        _resetUploadLimit:function(size){
        	var g = this, p = this.options;
			if(p.queueSizeLimit && p.queueSizeLimit>0){
				$data = g.fileInput.data('uploadifive');
				if(size != undefined){
					$data.queue.count = $data.queue.count + size;
				}
			}        	
        },
        
        startUpload:function(){
        	var g = this, p = this.options;
        	if(!p.uploadScript || p.uploadScript=='') return;
        	$("#file_upload_"+g.id,g.upload).uploadifive('upload');
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