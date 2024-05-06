var idLists = "";
var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pid",
			rootPId : '0'
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			//树结构点击查询节点及下节点数据
			if(treeNode.children != null && treeNode.children.length != 0){
				idLists = showTreeData(treeNode, idLists);
				if(idLists == ""){
					idLists = treeNode.id;
				}
				searchFunc(idLists);
				$("#center").hide();
				$("#right").show();
			}else{
				$("#right").hide();
				$("#center").show();
				$("#center").find("iframe").attr("src",ctx + "/sys/org/orgForm?id=" + treeNode.id + "&opType=1");
			}
		}
	}
};

function showTreeData(treeNode, idList){
	var childrenList = treeNode.children;
	if(childrenList != null && childrenList.length != 0){
		idList += treeNode.id + ",";
		for (index in childrenList){
			idList = showTreeData(childrenList[index], idList);
		};
	}
	return idList;
}

$(document).ready(function(){
	$.ajaxSetup({cache:false});
	$("#center").hide();
	refreshTree();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
});

function initDataGrid(url){
	$("#datagrid").datagrid({
		url: url,
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'名称',align:'center',width:'25%'},
	              {field:'sname',title:'简称',align:'center',width:'20%'},
	              {field:'code',title:'代码',align:'center',width:'20%'},
	              {field:'createTime',title:'添加时间',align:'center',width:'18%'},
	              {field:'_operate',title:'修改',align:'center',width:'15%',formatter:formatOper}
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	window.location.href = ctx+"/sys/org/orgForm";
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
	            	deleteOrg(rows);
	            }
	        });
        }
    }];
    
    return toolbar;
}

//刷新树
function refreshTree(){
	initDataGrid("");
	$('#datagrid').datagrid('loading');
	$.ajax({
		type: "post",
		url: ctx+"/sys/org/treeData",
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.fn.zTree.init($("#ztree"), setting, data);
			initDataGrid(ctx + "/sys/org/findPageList");
		}
	});
}

//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" onclick="editUser('+index+')">修改</a>';  
} 

//修改数据
function editUser(index){  
    $('#datagrid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#datagrid').datagrid('getRows')[index];  
    if (row){  
        window.location.href = ctx+"/sys/org/orgForm?id="+row.id;  
    }  
} 

//删除数据
function deleteOrg(rows){
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
		url: ctx+"/sys/org/delete?id="+ids,
		dataType: 'json',
		cache: false,
		success: function(data) {
			searchFunc('');
			refreshTree();
		}
	});
}

//点击查找按钮出发事件
function searchFunc(idList) {
	var data = $("#searchForm").serializeObject();
	data.id = idLists;
	$("#datagrid").datagrid('load',data);   
	 
}

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('clear');
}

