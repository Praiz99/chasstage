
//机构选择器
function OrgDialog(conf){
	if(!conf.isSingle) conf.isSingle = false;
	//isSingle判断是多选还是单选
	var url = ctx +'/system/sysOrg?isSingle=' + conf.isSingle + "&idField=" + conf.idField + "&otherField=";
	if(conf.otherField) url += conf.otherField;
	//重新选择的时候，展现上次数据
	var selectOrgs = "";
	if(conf.idField && conf.ids && conf.names){	
		selectOrgs = {				
				ids: conf.idField,
				names: conf.names
		};
		if(conf.others)
			selectOrgs.others = conf.others;
	}	
	EasyextDialog(url,'_',$(window).height() * 0.95,$(window).width() * 0.95,'15px',true,true);
}


/**
 * 用户选择器
 * @param conf 参数
 */
function UserDialog(conf){
	if(!conf.isSingle) conf.isSingle = false;
	var url =ctx + "/system/userList?isSingle=" + conf.isSingle + "&idField=" + conf.idField;
	//重新选择的时候，展现上次数据
	var selectUsers = "";
	if(conf.idField && conf.ids && conf.names){
		selectUsers = {				
				ids: conf.idField,
				names: conf.names
		};
	}
	EasyextDialog(url,'_',$(window).height() * 0.95,$(window).width() * 0.95,'15px',true,true);
}


/**
 * 角色选择器
 * @param conf 参数
 */
function RoleDialog(conf){
	if(!conf.isSingle) conf.isSingle = false;
	var url = ctx + "/system/roleList?isSingle=" + conf.isSingle + "&idField=" + conf.idField;
	
	//重新选择的时候，展现上次数据
	var selectRoles = "";
	if(conf.idField && conf.ids && conf.names){
		selectRoles = {				
				ids: conf.idField,
				names: conf.names
		};
	}		
	
	EasyextDialog(url,'_',$(window).height() * 0.95,$(window).width() * 0.95,'15px',true,true);
}

/**
 * 区域选择器
 * @param conf 参数
 */
function RegionDialog(conf){
if(!conf.isSingle) conf.isSingle = false;
	var url = ctx + "/system/regionList?isSingle=" + conf.isSingle + "&idField=" + conf.idField + "&otherField=";
	
	//重新选择的时候，展现上次数据
	var selectRegs = "";
	if(conf.idField && conf.ids && conf.names){
		selectRegs = {		
				ids: conf.idField,
				names: conf.names
		};
		if(conf.others)
			selectRegs.others = conf.others;
	}
	EasyextDialog(url,'_',$(window).height() * 0.95,$(window).width() * 0.95,'15px',true,true);
}

var win;
/*
 * 		url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层 ,
 * 		flag是否加载按钮组并自处理返回结果,fun 关闭回调事件
 */
function EasyextDialog(url,title,height,width,top,shadow,flag,fun) {  
	var ids = Math.round(Math.random()*1000);
    var content = '<iframe id="'+ids+'" name="'+ids+'" src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="'+ids+'" title="' + title + '" ></div>';//style="overflow:hidden;"可以去掉滚动条  
    $(document.body).append(boarddiv); 
    if(flag){
    	win = $('#'+ids).dialog({  
    		content: content,  
    		height: height,  
    		width: width,  
    		modal: shadow,  
    		title: title,  
    		top:top,
    		onClose: function () {  
    			//关闭后事件
    			if(fun){
    				fun();
    			}
    		}
    	}) 
    }else{
    	win = $('#'+ids).dialog({  
    		content: content,  
    		height: height,  
    		width: width,  
    		modal: shadow,  
    		title: title, 
    		top:top,
    		onClose: function () {  
    			//关闭后事件
    			if(fun){
    				fun();
    			}
    		}
    	})  
    }
    if(flag){
    	$("#"+ids).next().css("text-align","center");
    }
    win.dialog('open');  
}