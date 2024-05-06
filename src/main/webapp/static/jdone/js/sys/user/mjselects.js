
//民警选择器
var mjDialog = function(options) {
	this.url = ctx + '/sys/user/openMjSelectDialog';
	if(options){
		this.title = options.title || "人员选择";
		this.width = options.width || "750px";
		this.height = options.height || "500px";
		this.target = options.target || self;
		this.modal = options.modal || true;
		this.singleSelect = options.singleSelect || false;//是否可以多选标识
		this.confirmCallBack = options.confirmCallBack || null;//回调函数
	}
	this.win = null;
};

mjDialog.prototype._init = function(name,idCard) {//"?name="+name+"&idCard="+idCard+ 
	var content = '<iframe id="win_iframe_mj" name="win_iframe_mj" src="' + this.url +'" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="_mjDiv" title="' + this.title + '" closed="true"></div>';
    var manager = this;
    if (manager.target.$("#_mjDiv").length > 0) {
    	$("#_mjDiv").remove();
    }
    $(manager.target.document.body).append(boarddiv);
	manager.win = manager.target.$('#_mjDiv').dialog({  
		content: content,  
		width: manager.width,  
		height: manager.height,  
		modal: manager.modal, 
		title: manager.title, 
		buttons:[{ 
			text:'确定',
			left:50,
			handler:function () {
			var objs = manager.target.win_iframe_mj.confirm_mj();
				if(objs!= null){
					if(objs.mjSfzhStr.split(",").length > 1){
						if(manager.singleSelect && objs.mjSfzhStr.split(",")[1].length>0 ){
							alert("人员数量只能选择一个人!");
							return;
						}
					}
					if (manager.confirmCallBack != null) {
						if (manager.confirmCallBack(objs)) {
							manager.close();
						}
					} else {
						manager.close();
					}
					
				}
			}
		},
		{ 
			text:'关闭',
			left:50,
			handler:function(){
				manager.close();
			}
		}
		]
	});
};

mjDialog.prototype.show = function(obj1,obj2,obj3,obj4) {//三个参数：obj1用与回显名字（当文本框中有值） obj2是文本框的id(可在方法回调后用) obj3为身份证的id(可在方法回调后用)obj1用与回显sfzh（当文本框中有值）
	this._init();
	var frame =  this.target.win_iframe_mj;
    frame.onload = function(){
    	frame.initVal(obj1,obj2,obj3,obj4);
    };
	this.win.dialog('open');  
};

/*mjDialog.prototype.showNotParam = function() {
	this.win.dialog('open'); 
};*/

mjDialog.prototype.close = function() {
	this.win.dialog('close'); 
};

