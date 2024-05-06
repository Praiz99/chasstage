var sexMap = {"1": "男", "2": "女"};
var nameDic ;
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

window.onload = function(){
	$(".extend-search-open").click();
};

function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/sys/user/findPageList",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'姓名',align:'center',width:'10%'},
	              {field:'sex',title:'性别',align:'center',width:'10%',formatter: function(value,row,index){
	            	  	return sexMap[value];
            	  }},
	              {field:'loginId',title:'登录名',align:'center',width:'10%'},
	              {field:'idCard',title:'身份证号码',align:'center',width:'15%'},
	              {field:'orgName',title:'所属机构名称',align:'center',width:'18%'},
	              {field:'orgCode',title:'所属机构代码',align:'center',width:'12%'},
	              {field:'createTime',title:'添加时间',align:'center',width:'13%'},
	              {field:'_operate',title:'操作',align:'center',width:'10%',formatter:formatOper}
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'新增',
        handler:function(){
        	window.location.href = ctx+"/sys/user/userForm";
        }
    },'-',{
        text:'删除',
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
    },'-',{
    	text:'重置密码',
    	handler:function(){
    		var rows = $('#datagrid').datagrid('getSelections');
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
    		$.messager.confirm('系统提示', '点击确定，将会把密码修改为身份证后6位', function(r) {
    			if (r) {
    				resetPsw(rows);
    			}
    		});
    	}
    }];
    
    return toolbar;
}

//增加修改列
function formatOper(val,row,index){  
    return '<a href="#" onclick="editUser('+index+')" style="margin-right: 5px;">修改</a><a href="#" onclick="allotRole('+index+')"style="margin-left: 20px;">分配角色</a>';  
} 

//修改数据
function editUser(index){  
    $('#datagrid').datagrid('selectRow',index);// 关键在这里  
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row){  
        window.location.href = ctx+"/sys/user/userForm?id="+row.id;  
    }  
}

//分配角色
function allotRole(index){  
	$('#datagrid').datagrid('selectRow',index);// 关键在这里  
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row){  
		/*window.location.href = ctx+"/sys/user/userRoleForm?id="+row.id;*/  
		$('#openReceiveFeedBack')[0].src = ctx+"/sys/user/userRoleForm?id="+row.id;
	    $('#ReceiveFeedBackDialog').dialog('open');
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
		url: ctx+"/sys/user/delete?id="+ids,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}
function orgChange(){
	nameDic.changeOptions({"url":ctx + "/sys/org/findPageList","parms":{name:$("#nameDic").val()}});
}
function InitOrgDic(){
	nameDic = $("#nameDic").ligerDictionary({
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
//重置密码
function resetPsw(rows){
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
		url: ctx+"/sys/user/resetPsw?id="+ids,
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示','重置密码成功！','info',function(){
				$("#datagrid").datagrid('reload');
			});
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

