function ImageView(){
	this.parent=null;
	this.css=null;
	this.actualImgUrl=null;
	this.create = function(jqobj)
	{
		if(!this.parent || $(this.parent).length==0){
			this.parent="body";
		}
		
		if(!(typeof(this.css)==='string' && this.css.length>0)){
			this.css='text-align:center;';
		}
		
		var viewDiv=$("<div class='view-div' style='"+this.css+"'></div>");
		
		var parentObj = $(this.parent);
		parentObj.append(viewDiv);
		
		//创建工具箱
		function createToolBox(){
			var html = '';
			html += '<div class="toolbox" id="view-tool-box" style="display:none;position:absolute;float:right;">';
			html += '	<div class="tool-button"><a class="image-rotate" style="margin-left:5px;" href="javascript:void(0)">旋转</a><a class="actual-image" style="margin-left:5px;" href="javascript:void(0)">查看原图</a></div>';
			html += '</div>';
			viewDiv.append(html);
		}
		
		//加载图片
		function loadImage(url,fn){
			var img = new Image();
			img.src = url;
			if (img.complete){
				fn.call(img);
			}else{
				img.onload = function(){
					fn.call(img);
				};
			}
		}
		
		var infoArr={};
		//创建并显示图片
		function createImage()
		{
			createToolBox();
			
			var count=0;
			$(jqobj).each(function(){
				var fid = $(this).attr('fid');
				if(!fid) return;
				
				var ftype = $(this).attr('ftype');
				if(!ftype)ftype="0";
				
				$(this).attr('id','image-obj-'+fid);
				$(this).attr('fid',fid);
				$(this).attr('ftype',ftype);
				$(this).addClass('image-obj');
				
				var url = $(this).attr('href');
				if(!url) return;
				
				count++;
				infoArr[fid]={ftype:ftype,url:url,fid:fid};
				
				//加载图片
				loadImage(url,function(){
					var size=getImageSize(this.width,this.height);
					var imgDom="<img src='"+url+"' width='"+size.w+"' height='"+size.h+"' id='image-dom-"+fid+"'/>";
					$('#image-obj-'+fid).append(imgDom);
				});
				$(this).appendTo(viewDiv);
			});
		}
		
		//绑定事件
		function bindEvent(){
			var view=this;
			$(".image-obj",viewDiv).hover(function(){
				var imageId=this.id;
				imageId=imageId.replace("image-obj-","");
				$(".toolbox",viewDiv).attr("rel",imageId);
				$(".toolbox",viewDiv).insertBefore($("#image-dom-"+imageId));
				$(".toolbox",viewDiv).css("right","50px");
				$(".toolbox",viewDiv).show();
			},function(){
				$(".toolbox",viewDiv).attr("rel","");
				$(".toolbox",viewDiv).hide();
			});
			
			$(".toolbox .tool-button a",viewDiv).bind('click',function(){
				var fid=$(".toolbox",viewDiv).attr("rel");
				var css=$(this).attr("class");
				if(css==='image-rotate'){
					$("#image-dom-"+fid).rotateRight();
					resizeImg(fid);
				}else if(css==="actual-image"){
					var url="";
					if(!view.actualImgUrl){
						url=infoArr[fid].url;
					}else{
						url=view.actualImgUrl+"/"+fid+"?type="+infoArr[fid].ftype;
					}
					window.open(url);
				}
			});
		}
		
		//设置图片大小
		function resizeImg(fid){
			var imgW=$("#image-dom-"+fid).width();
			var imgH=$("#image-dom-"+fid).height();
			var size=getImageSize(imgW,imgH);
			$("#image-dom-"+fid).width(size.w);
			$("#image-dom-"+fid).height(size.h);
		}
		
		//计算照片大小
		function getImageSize(w,h){
			var imgW=w,imgH=h;
			var winW=$(window).width();
			if(imgW>winW){
				imgH=(winW/imgW)*imgH;
				imgW=winW;
			}
			
			var size={w:imgW,h:imgH};
			return size;
		}
		
		createImage.call(this);
		bindEvent.call(this);
	};
}