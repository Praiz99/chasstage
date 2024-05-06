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
			//树结构点击查询节点及下节点数据
			if(treeNode.isParent == true){
				searchFunc("groupMark", treeNode.id);
				$("#center").hide();
				$("#right").show();
			}else{
				$("#right").hide();
				$("#center").show();
				$("#center").find("iframe").attr("src",ctx + "/act/model/actModelForm?id=" + treeNode.id + "&opType=1");
			}
		}
	}
};

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

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/act/model/findPageList",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id', align:'center', checkbox : true},
	              {field:'modelName', title:'模型名称',align:'center',width:'20%'},
	              {field:'modelMark',title:'模型标识',align:'center',width:'25%'},
	              {field:'groupMarkName',title:'分类',align:'center',width:'20%'},
	              {field:'createTime',title:'创建时间',align:'center',width:'20%'},
	              {field:'_operate',title:'操作',align:'center',width:'13%',formatter:formatOper}
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	window.location.href = ctx+"/act/model/actModelForm";
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
	            	deleteModel(rows);
	            }
	        });
        }
    }];
    return toolbar;
}

//刷新树
function refreshTree(){
	$.getJSON(ctx+"/act/model/treeData",function(data){
		$.fn.zTree.init($("#ztree"), setting, data);
		initDataGrid();
	});
}

//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" onclick="editModel('+index+')">设置</a>';  
} 

//修改流程图
function showModel(index){  
	$('#datagrid').datagrid('selectRow',index);// 关键在这里  
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row){  
		window.open(ctx+"/act/model/openModeler?id="+row.id);  
	}  
} 

//修改数据
function editModel(index){  
    $('#datagrid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row){  
    	window.location.href = ctx+"/act/model/actModelForm?id="+row.id;  
    }  
} 

//删除数据
function deleteModel(rows){
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
		url: ctx+"/act/model/delete?id="+ids,
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

