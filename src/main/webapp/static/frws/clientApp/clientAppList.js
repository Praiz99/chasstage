$(document).ready(function(){
	initDataGrid();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/clientApp/findPageList",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		onLoadSuccess:function(data){
			if (data.total == 0) {
				$(this).datagrid('appendRow',
					{appName : '<div style="text-align:center;color:red">没有相关记录！</div>'}).datagrid('mergeCells',
					{	index : 0,
						field : 'appName',
						colspan : 5
					});
			}
	    },
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'appName', title:'应用名称',align:'center',width:'20%'},
	              {field:'appMark',title:'应用标识',align:'center',width:'20%'},
	              {field:'clientRootUrl',title:'客户端应用根路径',align:'center',width:'30%'},
	              {field:'tjxgsj',title:'添加修改时间',align:'center',width:'15%'},
	              {field:'_operate',title:'操作',align:'center',width:'13%',formatter:formatOper}
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	window.location.href = ctx+"/clientApp/clientAppForm";
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
	            	deleteClientApp(rows);
	            }
	        });
        }
    }];
    return toolbar;
}

//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" onclick="editClientApp('+index+')">修改</a>';  
} 

//修改数据
function editClientApp(index){  
    $('#datagrid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row){  
        window.location.href = ctx+"/clientApp/clientAppForm?id="+row.id;  
    }  
}

//删除数据
function deleteClientApp(rows){
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
		url: ctx+"/clientApp/delete?id="+ids,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
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

