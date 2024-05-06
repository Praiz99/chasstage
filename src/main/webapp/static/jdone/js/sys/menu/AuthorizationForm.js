var ztree,aztree;
var setting = {
	check: {
		enable: true,
		chkboxType: { "Y": "s", "N": "s" }  //取消勾选关联父节点
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	edit: {  //禁止点击跳转
		enable : false,
		showRemoveBtn : false,
		showRenameBtn : false,
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
		url: "getAuthorizationMenuTree"
	}
};
var zNodes =[];
$(function (){
	setting.async.otherParam = ["menuId",getUrlParam("menuId")];
	ztree = $.fn.zTree.init($("#mztree"), setting, zNodes);  //资源角色树
	
	setting.async.url = 'getLikedAppMenuTree';
	aztree = $.fn.zTree.init($("#aztree"), setting, zNodes);  //关联应用树
});

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function Submit(){
	var filter = function (node) {
		var flag = false;
		if((node.checkedOld == false && node.checked == true && node.Pid != "-1")){
			flag = true;
		}
	    return flag;
	};
	var roledata = ztree.getNodesByFilter(filter); // 查找节点集合
	filter = function (node) {
		var flag = false;
		if((node.checkedOld == false && node.checked == true)){
			flag = true;
		}
	    return flag;
	};
	var adata = aztree.getNodesByFilter(filter); // 查找节点集合
	filter = function (node) {
		var flag = false;
		if((node.checkedOld == true && node.checked == false && node.Pid != "-1")){
			flag = true;
		}
	    return flag;
	};
	var roledel = ztree.getNodesByFilter(filter); // 查找节点集合
	filter = function (node) {
		var flag = false;
		if((node.checkedOld == true && node.checked == false)){
			flag = true;
		}
	    return flag;
	};
	var adel = aztree.getNodesByFilter(filter); // 查找节点集合
	$.ajax({
		url:'saveAuthorizationMenuData',
		dataType:'json',
		type:'POST',
		data:{roledata:JSON.stringify(roledata),roledel:JSON.stringify(roledel),menuId:getUrlParam("menuId"),
			adata:JSON.stringify(adata),adel:JSON.stringify(adel),menus:JSON.stringify(parent.AllotPermission())},
		success:function (data){
			if(data.success){
				parent.$.messager.alert('提示',data.msg);
				parent.win.dialog("close");
			}
		}
	});
}