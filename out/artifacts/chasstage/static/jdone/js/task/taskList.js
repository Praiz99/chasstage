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
				if(treeNode.pid == null ){
					$("#tableList").attr("src", ctx + "/task/taskTableList?deployId=" + treeNode.id);
				}else{
					$("#tableList").attr("src", ctx + "/task/taskTableList?deployId="+ treeNode.pid + "&taskType=" + treeNode.id);
				}
			} else {
				$("#tableList").attr("src", ctx + "/task/updateTask?id=" + treeNode.id);
			}
		}
	}
};

$(document).ready(function(){
	$.ajaxSetup({cache:false});
	$("#toptoolbar").datagrid({
		width:'100%'
	});
	refreshTree();
});




//刷新分组
function refreshTree(){
	$("#tableList").attr("src", ctx + "/task/taskTableList");
	$.getJSON(ctx + "/task/getTaskTreeData",function(data){
		$.fn.zTree.init($("#ztree"), setting, data);
		$.fn.zTree.getZTreeObj("ztree").expandAll(true);
	});
}

