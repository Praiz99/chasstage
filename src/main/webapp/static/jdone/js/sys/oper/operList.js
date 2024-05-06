$(function (){
	initZtree();
	//高度兼容IE
	if (!!window.ActiveXObject || "ActiveXObject" in window){
		var browser=navigator.appName; 
		var b_version=navigator.appVersion; 
		var version=b_version.split(";"); 
		var trim_Version=version[1].replace(/[ ]/g,""); 
		$("#layout").css({"height":(window.screen.availHeight-210)+"px"});
		$("#p").css({"height":(window.screen.availHeight-210)+"px"});
		$("#layout").layout("resize",{  
	        width:"100%",  
	        height:(window.screen.availHeight-210)+"px"  
	    }); 
		if(trim_Version=="MSIE7.0"){
			$("ul").css("margin-left","14px");
		}
	}
	$("#mainForm").attr("src","OperView");
});

function Close(){
	$(".panel-tool-close").trigger("click");  //关闭页面
}
/*============================================ztree操作 开始=============================================*/
var ztree,oldtreeId;  //oldtreeId 是保存上次操作选择的节点,防止对该分组新增修改删除后刷新树用户找不到修改的是哪个分组.
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		edit: {  //禁止点击跳转
			enable : true,
			drag : {  //禁止拖拽
				isMove : false,
				isCopy : false
			},
			showRenameBtn: false,
			showRemoveBtn: showRemoveBtn
		},
		callback: {
			callbackFlag :true,
			onClick: zTreeOnClick,
			onRemove: onRemoveTree,
			beforeRemove:function (treeId, treeNode){
				deleteOperById(treeNode.id);
				return false;
			},
			onNodeCreated: function (event, treeId, treeNode){
				var node = ztree.getNodeByParam("id", oldtreeId);  //新增后继续选择展开上次选择的节点.
				if(node){
					ztree.expandNode(node, true, true, true);
					ztree.selectNode(node);
				}
			},
			onExpand:function (event, treeId, treeNode){  //展开事件
				oldtreeId = treeNode.id;
				ztree.selectNode(ztree.getNodeByTId(treeNode.tId));
				zTreeOnClick(event, treeId, treeNode);
			}
		},
		async: {
			enable: true,
			type:'post',
			dataType:'text',
			url: "getOperGroupTreeData"
		}
};
var zNodes =[];
function initZtree(){
	ztree = $.fn.zTree.init($("#ztree"), setting, zNodes);
}
function initGridByAppId(){
	$("#mainForm").attr("src","OperView");
}
function zTreeOnClick(event, treeId, treeNode){
	if(treeNode.Pid == "-1"){
		$("#mainForm").attr("src","OperView?treeId="+treeNode.id);
	}else{
		$("#mainForm").attr("src","operForm?id="+treeNode.id+"&treeId="+treeNode.id);
	}
}
function showRemoveBtn(treeId, treeNode){
	if(treeNode.Pid != "-1"){
		return true;
	}
	return false;
}
function onRemoveTree(event, treeId, treeNode){
	//...
}
/*============================================ztree操作 结束=============================================*/
/*============================================页面事件 开始===============================================*/

/*============================================页面事件 结束===============================================*/
/*============================================页面操作     开始=============================================*/
var groupId = "";
function addGroup(){
	EasyextDialog('../../sys/group/addorUpgroupForm?bizMark=czfz','添加分组','60%','60%',true,true);
}
function updateGroup(){
	var nodes = ztree.getSelectedNodes();
	if(nodes.length == 0 || nodes[0].Pid != "-1"){
		$.messager.alert('提示',"请选择分组!");
		return;
	}
	groupId = nodes[0].id;
	EasyextDialog('../../sys/group/addorUpgroupForm?id='+nodes[0].id,'修改分组','60%','60%',true,true);
}
function deleteGroup(){
	var nodes = ztree.getSelectedNodes();
	if(nodes.length == 0 || nodes[0].Pid != "-1"){
		$.messager.alert('提示',"请选择分组!");
		return;
	}
	if(nodes[0].children != null && nodes[0].children.length != 0){
		$.messager.alert('提示',"无法删除,该分组下有子数据!");
		return;
	}
	$.ajax({
		url:'deletegroup?id='+nodes[0].id,
		dataType:'json',
		type:'POST',
		success:function (data){
			$.messager.alert('提示',data.msg);
			initZtree();
		}
	});
}
function deleteOperById(obj){
	$.messager.confirm('Confirm','是否删除?',function(r){
	    if (r){
	    	$.ajax({ 
				url:'deleteOper',
				type:'POST',
				dataType:'json',
				data:{id:obj},
				success : function (data){
					initZtree();
					$("#mainForm").attr("src","");
					if(data.success){
						$.messager.alert('提示',data.msg);
					}else{
						$.messager.alert('提示',data.msg);
					}
				}
			});
	    }
	});
}
function doSearch(val){ 
	setting.async.otherParam = ["name",val];
	ztree = $.fn.zTree.init($("#ztree"), setting, zNodes);
}
/*============================================页面操作     结束=============================================*/
/*========================================工具 开始==============================================*/
//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  ,flag 是否加载按钮组并自处理返回结果,fun 关闭回调事件
var win;
function EasyextDialog(url, title, width, height, shadow,flag,fun) {  
	var ids = Math.round(Math.random()*1000);
	ids = "group_"+ids;
    var content = '<iframe id="'+ids+'" name="'+ids+'" src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="'+ids+'" title="' + title + '"></div>';//style="overflow:hidden;"可以去掉滚动条  
    $(document.body).append(boarddiv); 
    if(flag){
    	win = $('#'+ids).dialog({  
    		content: content,  
    		width: width,  
    		height: height,  
    		modal: shadow,  
    		title: title,  
    		onClose: function () {  
    			//关闭后事件
    			if(fun){
    				fun();
    			}
    		},
    		buttons:[{ 
    			text:'保存', 
    			iconCls:'icon-save', 
    			left:50,
    			handler:function(){ 
    				var client = frames.document[ids];
    				if(client.$("#addorUpgroupForm").length > 0){
    					$.ajax({
    						url:'savegroup',
    						dataType:'json',
    						type:'POST',
    						data:{bizMark:"czfz",groupName:client.$("#groupName").val(),groupMark:client.$("#groupMark").val(),
    							groupDesc:client.$("textarea[name='groupDesc']").val(),id:groupId},
    						success:function (data){
    							parent.$.messager.alert('提示',data.msg);
    							initZtree();
    						}
    					});
    					groupId = "";
    					win.dialog('close');
    				}else{
    					client.Submit();
    				}
    			}
    		},
    		{ 
    			text:'关闭', 
    			iconCls:'icon-cancel', 
    			left:50,
    			handler:function(){
    				win.dialog('close');  
    			}
    		}
    		]
    	});  
    }else{
    	win = $('#'+ids).dialog({  
    		content: content,  
    		width: width,  
    		height: height,  
    		modal: shadow,  
    		title: title,  
    		onClose: function () {  
    			//关闭后事件
    			if(fun){
    				fun();
    			}
    		}
    	});  
    }
    if(flag){
    	//$("#"+ids).next().css("text-align","center");  按钮居中
    }
    win.dialog('open');  
}

function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 
/*========================================工具 结束==============================================*/