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
			}else{
				searchFunc("modelName", treeNode.name);
			}
		}
	}
};
var ft = false;
$(document).ready(function(){
	$.ajaxSetup({cache:false});
	refreshTree();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
	
	if(getUrlParam("isSingle") == "true"){
		 ft = true;
	}
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/act/model/findPageList",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		singleSelect:ft,//如果为true，则只允许选择一行。
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id', align:'center', checkbox : true},
	              {field:'modelName', title:'模型名称',align:'center',width:'25%'},
	              {field:'modelMark',title:'模型标识',align:'center',width:'25%'},
	              {field:'groupMarkName',title:'分类',align:'center',width:'25%'},
	              {field:'createTime',title:'创建时间',align:'center',width:'23%'},
	          ]]
	});
}

function getSelectorResult() {
	var data = $('#datagrid').datagrid('getSelections');
	return data;
}
//刷新树
function refreshTree(){
	$.getJSON(ctx+"/act/model/treeData",function(data){
		$.fn.zTree.init($("#ztree"), setting, data);
		initDataGrid();
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

//采用正则表达式获取地址栏参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null)return unescape(r[2]);
    return null; //返回参数值
}
