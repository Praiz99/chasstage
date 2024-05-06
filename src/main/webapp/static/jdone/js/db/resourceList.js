var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pid"
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			if (treeNode.isParent == true) {
				var sourceId = treeNode.pid == null ? treeNode.id : treeNode.pid;
				if(treeNode.name == "查询脚本"){
					$("#tableList").attr("src", ctx + "/db/resScript/resScriptList?sourceId=" + sourceId);
				}else{
					$("#tableList").attr("src", ctx + "/db/resTable/resTableList?sourceId=" + sourceId);
				}
			} else {
				if(treeNode.getParentNode().name == "查询脚本"){
					$("#tableList").attr("src", ctx + "/db/resScript/resScriptList?groupId=" + treeNode.id);
				}else{
					$("#tableList").attr("src", ctx + "/db/resTable/resTableList?groupId=" + treeNode.id);
				}
			}
		}
	}
};

$(document).ready(function(){
	$.ajaxSetup({cache:false});
	$("#toptoolbar").datagrid({
		width:'100%',
		toolbar:initToolbar(),
		onBeforeLoad: function(data){
			$(".datagrid-view").css("min-height","0px");
			$(".datagrid-view").css("height","0px");
		}
	});
	refreshTree();
});

function initToolbar(){
    toolbar = [{
        text:'增加分组',
        iconCls:'icon-add',
        handler:function(){
        	window.location.href = ctx+"/db/resGroup/resGroupForm";
        }
    }, '-',{
        text:'修改分组',
        iconCls:'icon-edit',
        handler:function(){
        	doUpdateGroup();
        }
    }, '-',{
        text:'删除分组',
        iconCls:'icon-cut',
        handler:function(){
        	doDeleteGroup();
        }
     }];
    return toolbar;
}

//修改分组
function doUpdateGroup(){
	var selectedNode = $.fn.zTree.getZTreeObj("ztree").getSelectedNodes();
	if(selectedNode.length==0 || selectedNode[0].isParent==true){
		$.messager.alert("系统提示","请选择要修改的分组！！");
		return;
	}else{
		window.location.href = ctx+"/db/resGroup/resGroupForm?id="+selectedNode[0].id;
	}
}

//删除分组
function doDeleteGroup(){
	var selectedNode = $.fn.zTree.getZTreeObj("ztree").getSelectedNodes();
	if(selectedNode.length==0 || selectedNode[0].isParent==true){
		$.messager.alert("系统提示","请选择要删除的分组！！");
		return;
	}else{
		var url = ctx + "/db/resTable/findPageList?groupId=" + selectedNode[0].id;
		if(selectedNode[0].getParentNode().name == "查询脚本"){
			url = ctx + "/db/resScript/findPageList?groupId=" + selectedNode[0].id;
		}
		$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					url : url,
					data: {page: 1, rows: 10},
					dataType : 'json',
					cache : false,
					success : function(data) {
						if(data.rows.length > 0){
							$.messager.alert("系统提示","该分组下存在数据，无法删除！！");
						}else{
							window.location.href = ctx+"/db/resGroup/delete?id="+selectedNode[0].id;
						}
					}
				});
			}
		});
	}
}

//刷新分组
function refreshTree(){
	$.getJSON(ctx+"/db/resGroup/treeData",function(data){
		$.fn.zTree.init($("#ztree"), setting, data);
		$.fn.zTree.getZTreeObj("ztree").expandAll(true);
	});
}

