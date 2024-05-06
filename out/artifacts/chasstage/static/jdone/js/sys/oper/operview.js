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
		url:"getOperPageData",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		queryParams: {appId:parent.appId,name:$("input[name='name']").val(),treeId:getQueryString("treeId")},
		onLoadSuccess:function(data){
			
	    },
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'名称',align:'center',width:'20%'},
	              {field:'mark',title:'标识',align:'center',width:'20%'},
	              {field:'isEnableAuth',title:'是否启用权限',align:'center',width:'13%',formatter:function (val,row,index){
	            	  if(val == 0){
	            		  return "否";
	            	  }else if(val == 1){
	            		  return "是";
	            	  }
	              }},
	              {field:'isDisabled',title:'是否禁用',align:'center',width:'13%',formatter:function (val,row,index){
	            	  if(val == 0){
	            		  return "否";
	            	  }else if(val == 1){
	            		  return "是";
	            	  }
	              }},
	              {field:'createTime',title:'添加时间',align:'center',width:'22%'},
	              {field:'_operate',title:'操作',align:'center',width:'10%',formatter:formatOper}
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	Dialog("operForm","80%","80%",true,"新增",null);
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
	            	deleteUser(rows);
	            }
	        });
        }
    }];
    if(getQueryString("treeId") == undefined){  //如果是没有选择分组那么就隐藏新增按钮
    	toolbar.splice(0, 1);
    }
    return toolbar;
}

//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" onclick="editUser('+index+')" style="margin-right: 20px;">修改</a>';  
} 

//修改数据
function editUser(index){  
    $('#datagrid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row){  
    	Dialog("operForm?id="+row.id,"80%","80%",true,"修改",null);
    }  
}

//删除数据
function deleteUser(rows){
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
		url: "deleteOper?id="+ids,
		dataType: 'json',
		cache: false,
		success: function(data) {
			parent.initZtree();
			parent.oldtreeId = getQueryString("treeId");
			$("#datagrid").datagrid('reload');
		}
	});
}

//点击查找按钮出发事件
function searchFunc() {
	var data = {name:$("input[name='name']").val(),mark:$("input[name='mark']").val(),treeId:getQueryString("treeId")};
	$("#datagrid").datagrid('load',data);   
	 
}

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('clear');
}
var win;
function Dialog(url,width,height,shadow,title,fun){
	win = $('#dialog').dialog({  
		href: url,  
		width: width,  
		height: height,  
		modal: shadow,  
		title: title,  
		onClose: function () {  
			//关闭后事件
			if(fun){
				fun();
			}
		},
		buttons:[{ 
			text:'保存', 
			iconCls:'icon-save', 
			left:50,
			handler:function(){ 
				Submit();
			}
		},
		{ 
			text:'关闭', 
			iconCls:'icon-cancel', 
			left:50,
			handler:function(){
				win.dialog('close');  
			}
		}]
	});  
}


function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 