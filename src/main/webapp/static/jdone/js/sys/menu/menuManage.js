var setting = {
	check: {
		enable: true,
		chkboxType: { "Y": "s", "N": "s" }  //取消勾选关联父节点
	},
	edit: {  //禁止点击跳转
		enable : true,
		showRemoveBtn : false,
		showRenameBtn : false,
		drag : {  //禁止拖拽
			isMove : false,
			isCopy : false
		}
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pid"
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			onNodeSelect(treeNode, event);
		}
	}
};
var nowMenuId = "";
var completeUrl = "";
$(document).ready(function(){
	$.ajaxSetup({cache:false});
	$("#toptoolbar").datagrid({
		width: '100%',
		toolbar: initTopToolbar(),
		onBeforeLoad: function(data){
			$(".datagrid-view").css("min-height","0px");
			$(".datagrid-view").css("height","0px");
		}
	});
	refreshTree();
});

function initTopToolbar(){
    toolbar = ['-',{
        text:'添加菜单',
        iconCls:'icon-add',
        handler:function(){
        	addMenu();
        }
    }, '-',{
        text:'删除菜单',
        iconCls:'icon-add',
        handler:function(){
        	delMenuList();
        }
    }, '-'];
    return toolbar;
}

//菜单搜索
function menuTreeSearch() {
	var value = $("#menuName").val();
	if(value == null || value == ""){
		refreshTree();
		return false;
	}
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var allNode = treeObj.transformToArray(treeObj.getNodes());
	treeObj.hideNodes(allNode);
	var nodeList = treeObj.getNodesByParamFuzzy("name", value, null);
	var result = nodeList;
	for ( var n in nodeList) {
		findParent(treeObj, nodeList[n], result);
	}
	treeObj.showNodes(nodeList);
	if (value == "") {
		treeObj.expandAll(false);
	}
}

//菜单搜索并展示父节点
function findParent(treeObj, node, result) {
	treeObj.expandNode(node, true, false, false);
	var pNode = node.getParentNode();
	if (pNode != null) {
		result.push(pNode);
		findParent(treeObj, pNode, result);
	}
}

// 刷新树
function refreshTree(){
	completeUrl = "";
	nowMenuId = "";
	var appId = $('#apps option:selected').val();
	appId = appId == null ? "" : appId;
	$.getJSON(ctx+"/sys/menu/getMenuTreeData?appId=" + appId,function(data){
		$.fn.zTree.init($("#ztree"), setting, data);
	});
}

//节点选择时的事件处理
function onNodeSelect(node, e){
	var id = node.id;
	completeUrl = "";
	completeUrl = getCompleteUrl(node, completeUrl);
	completeUrl = completeUrl.substring(0, completeUrl.length-3);
	nowMenuId = id;
	var url = ctx + '/sys/menu/openMenuForm?id=' + id + "&appId=" + node.appId;
	if($('#operFrame').attr('src') != url){
		$('#operFrame').attr('src', url);
	}
}

//添加菜单
function getCompleteUrl(node, url){
	var pNode = node.getParentNode();
	if (pNode != null) {
		url = getCompleteUrl(pNode, url);
	}
	url += node.name + "-->";
	return url;
}


//添加菜单
function addMenu(){
	var appId = $('#apps option:selected').val();
	var url = ctx + '/sys/menu/openMenuForm?pid=' + nowMenuId + "&appId=" + appId;
	$('#operFrame').attr('src', url);
}

/* 删除菜单 */
function delMenuList() {
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var checkNodes = treeObj.getCheckedNodes(true);
	if(checkNodes == null || checkNodes.length == 0){
		$.messager.alert('系统提示','请选择要删除的菜单！','info');
		return false;
	}
	$.messager.confirm('系统提示', '您确定要删除当前菜单及其子菜单吗?', function(r) {
		if (r) {
			var ids = "";
			for(var i=0;i<checkNodes.length;i++){  
				ids += checkNodes[i].id + ",";  
			}
			var appId = $('#apps option:selected').val();
			$.ajax({
				type : "post",
				url : ctx + "/sys/menu/deleteMenuByIds?id=" + ids + "&appId=" + appId,
				dataType : 'json',
				cache : false,
				success : function(data) {
					$.messager.alert('系统提示',data.msg,'info',function(){
						refreshTree();
						$('#operFrame').attr('src', "");
					});
				}
			});
		}
	});
}

