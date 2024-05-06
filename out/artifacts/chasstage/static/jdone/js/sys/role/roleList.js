var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "roleKind"
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			//树结构点击查询节点及下节点数据
			if(treeNode.isParent == true){
				searchFunc("roleKind", treeNode.id);
				$("#center").hide();
				$("#right").show();
			}else{
				$("#right").hide();
				$("#center").show();
				$("#center").find("iframe").attr("src",ctx + "/sys/role/roleForm?id=" + treeNode.id + "&opType=1");
			}
		}
	}
};

$(document).ready(function(){
	$.ajaxSetup({cache:false});
	$("#center").hide();
	$("#toptoolbar").datagrid({
		width:'100%',
		toolbar:initTopToolbar(),
		onBeforeLoad: function(data){
			$(".datagrid-view").css("min-height","0px");
			$(".datagrid-view").css("height","0px");
		}
	});
	refreshTree();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
});

function initTopToolbar(){
    toolbar = [{
        text:'增加分组',
        iconCls:'icon-add',
        handler:function(){
        	window.location.href = ctx+"/sys/roleCat/roleCatForm";
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
	if(selectedNode.length==0 || selectedNode[0].isParent==false){
		$.messager.alert("系统提示","请选择要修改的分组！！");
		return;
	}else{
		window.location.href = ctx+"/sys/roleCat/roleCatForm?groupMark="+selectedNode[0].id;
	}
}

//删除分组
function doDeleteGroup(){
	var selectedNode = $.fn.zTree.getZTreeObj("ztree").getSelectedNodes();
	if(selectedNode.length==0 || selectedNode[0].isParent==false){
		$.messager.alert("系统提示","请选择要删除的分组！！");
		return;
	}else{
		$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					url : ctx + "/sys/role/findByRoleKind?roleKind=" + selectedNode[0].id,
					dataType : 'json',
					cache : false,
					success : function(data) {
						if(data.length > 0){
							$.messager.alert("系统提示","该分组下存在数据，无法删除！！");
						}else{
							window.location.href = ctx+"/sys/roleCat/delete?groupMark="+selectedNode[0].id;
						}
					}
				});
			}
		});
	}
}

function initDataGrid(){
	var datas = $.fn.zTree.getZTreeObj("ztree").getNodes();
	$("#datagrid").datagrid({
		url:ctx + "/sys/role/findPageList",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'角色名称',align:'center',width:'25%'},
	              {field:'code',title:'角色编号',align:'center',width:'15%'},
	              {field:'roleKind',title:'角色分类',align:'center',width:'20%',formatter: function(value,row,index){
	            	  for ( var i = 0; i < datas.length; i++) {
	            		  if(datas[i].id == value){
	            			  return datas[i].name;
	            		  }
	            	  }
	              }},
	              {field:'createTime',title:'创建时间',align:'center',width:'20%'},
	              {field:'_operate',title:'操作',align:'center',width:'19%',formatter:formatOper}
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	window.location.href = ctx+"/sys/role/roleForm";
        }
    },'-',{
        text:'删除',
        iconCls:'icon-cut',
        handler:function(){
			var rows = $('#datagrid').datagrid('getSelections');
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
	            if (r) {
	            	deleteRole(rows);
	            }
	        });
        }
    }];
    return toolbar;
}

//刷新树
function refreshTree(){
	$.getJSON(ctx+"/sys/role/treeData",function(data){
		$.fn.zTree.init($("#ztree"), setting, data);
		initDataGrid();
	});
}

//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" onclick="editRole('+index+')">修改</a><a href="#" style="margin-left:10px;" onclick="openRoleRangeSet(\''+row.id+'\',\''+row.name+'\',\''+row.level+'\',\''+row.isUseRange+'\');">设置范围</a>';  
}

//修改数据
function openRoleRangeSet(id, name, level, isUse){
	if(isUse == "0"){
		$.messager.alert("系统提示", "该角色未启用设置范围!");
		return false;
	}
	openSelectSocpe(id, name, level);
}

//修改数据
function editRole(index){  
    $('#datagrid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row){  
    	$("#right").hide();
		$("#center").show();
        $("#center").find("iframe").attr("src",ctx + "/sys/role/roleForm?id=" + row.id + "&opType=1");
    }  
} 

//删除数据
function deleteRole(rows){
	var ids = "";
	for ( var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
		if (ids == "") {
			ids = rows[0].id;
		} else {
			ids += "," + rows[i].id;
		}
	}
	$.ajax({
		type: "post",
		url: ctx+"/sys/role/delete?id="+ids,
		dataType: 'json',
		cache: false,
		success: function(data) {
			searchFunc("id", '');
			refreshTree();
		}
	});
}

//点击查找按钮出发事件
function searchFunc(field, idList) {
	var data = $("#searchForm").serializeObject();
	data[field] = idList;
	$("#datagrid").datagrid('load',data);   
	 
}

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('clear');
}

