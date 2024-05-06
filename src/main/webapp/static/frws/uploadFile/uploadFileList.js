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
		url:ctx + "/uploadFile/findPageList",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		onLoadSuccess:function(data){
			if (data.total == 0) {
				$(this).datagrid('appendRow',
					{realName : '<div style="text-align:center;color:red">没有相关记录！</div>'}).datagrid('mergeCells',
					{	index : 0,
						field : 'realName',
						colspan : 6
					});
			}
	    },
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'realName', title:'文件名',align:'center',width:'20%'},
	              {field:'fileType',title:'文件类型',align:'center',width:'20%'},
	              {field:'bizType',title:'业务类型',align:'center',width:'15%'},
	              {field:'orgSysCode',title:'所属机构系统代码',align:'center',width:'15%'},
	              {field:'operTime',title:'添加修改时间',align:'center',width:'15%'},
	              {field:'_operate',title:'操作',align:'center',width:'13%',formatter:formatOper}
	          ]]		
	});
}

function formatOper(val,row,index){  
    return '<a href="#" onclick="viewUploadFile('+index+')">查看</a>';  
} 

//查看数据
function viewUploadFile(index){  
    $('#datagrid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row){  
        window.location.href = ctx+"/uploadFile/uploadFileForm?id="+row.id;  
    }  
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

