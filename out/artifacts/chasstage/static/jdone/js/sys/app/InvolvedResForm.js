$(function (){
	initPermissionTree();
});
var ztree,cztree;
function initPermissionTree(){
	var setting = {
			check: {
				enable: true,
				chkboxType : { "Y" : "ps", "N" : "ps" }
			},
			edit: {  //禁止点击跳转
				enable : false,
				drag : {  //禁止拖拽
					isMove : false,
					isCopy : false
				}
			},
			callback: {
				
			},
			async: {
				enable: true,
				type:'post',
				dataType:'text',
				url: "getAppResObjMenuTree"
			}
		};
	var zNodes =[];
	setting.async.otherParam = ["appId",getUrlParam("appId")];
	ztree = $.fn.zTree.init($("#mztree"), setting, zNodes);
	
	setting = {
			check: {
				enable: true,
				chkboxType : { "Y" : "ps", "N" : "ps" }
			},
			edit: {  //禁止点击跳转
				enable : false,
				drag : {  //禁止拖拽
					isMove : false,
					isCopy : false
				}
			},
			callback: {
				
			},
			async: {
				enable: true,
				type:'post',
				dataType:'text',
				url: "getAppResGroupOperMenuTree"
			}
		};
	var zNodes =[];
	setting.async.otherParam = ["appId",getUrlParam("appId")];
	cztree = $.fn.zTree.init($("#cztree"), setting, zNodes);
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function Submit(){
	var filter = function (node) {
		var flag = false;
		if((node.checkedOld == false && node.checked == true)){
			flag = true;
		}
	    return flag;
	};
	var cnodes = ztree.getNodesByFilter(filter); // 查找节点集合
	filter = function (node) {
		var flag = false;
		if((node.checkedOld == false && node.checked == true)){
			flag = true;
		}
	    return flag;
	};
	var opercnodes = cztree.getNodesByFilter(filter); // 查找节点集合
	filter = function (node) {
		var flag = false;
		if((node.checkedOld == true && node.checked == false)){
			flag = true;
		}
	    return flag;
	};
	var dnodes = ztree.getNodesByFilter(filter); // 查找节点集合
	filter = function (node) {
		var flag = false;
		if((node.checkedOld == true && node.checked == false)){
			flag = true;
		}
	    return flag;
	};
	var operdnodes = cztree.getNodesByFilter(filter); // 查找节点集合
	$.ajax({
		url:'saveAppResobj',
		data:{cnodes:JSON.stringify(cnodes),dnodes:JSON.stringify(dnodes),appId:getUrlParam("appId"),
			opercnodes:JSON.stringify(opercnodes),operdnodes:JSON.stringify(operdnodes)},
		dataType:'json',
		type:'POST',
		success:function (data){
			if(data.success){
				parent.$.messager.alert('提示',data.msg);
				parent.win.dialog('close'); 
			}else{
				parent.$.messager.alert('提示',data.msg);
				parent.win.dialog('close'); 
			}
		}
	});
}
