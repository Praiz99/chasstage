var sexMap = {"1": "男", "0": "女"};
var orgDic ;

$(document).ready(function(){
	initDataGrid();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
	InitOrgDic();
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/sys/user/findPageList",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'姓名',align:'center',width:'20%'},
	              {field:'sex',title:'性别',align:'center',width:'20%',formatter: function(value,row,index){
	            	  	return sexMap[value];
            	  }},
	              {field:'loginId',title:'登录名',align:'center',width:'20%'},
	              {field:'orgName',title:'所属机构',align:'center',width:'37%'},
	          ]]		
	});
}

function InitOrgDic(){
	orgDic = $("#orgDic").ligerDictionary({
		dataAction:'server',
		url : ctx + "/sys/org/findPageList",
		pageParmName:'page',//向服务端请求数据时 提交请求当前页的参数名称
		pagesizeParmName:'rows', //每页展示记录数参数名称
		totalName:'total',
		recordName:'rows',
		method:'POST',
		page:1,				//当前页
		pageSize:10,		//每页展示记录数
		valueField:'code',// 字段值名称
		labelField:'name',// 选项字段名称
		searchField:'name,code',
		hiddenField:'code',
		resultWidth:300
	});
}

function orgDicChange(){
	orgDic.changeOptions({"url":ctx + "/sys/org/findPageList?name=" + $("#orgDic").val()});
}

//点击查找按钮出发事件
function searchFunc(idList) {
	var data = $("#searchForm").serializeObject();
	data.id = idList;
	$("#datagrid").datagrid('load',data);   
}

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('clear');
}

function userSelectSave() {
	var rows = $('#datagrid').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert("系统提示", "请至少选择一行数据!");
		return false;
	}
	return rows;
}


