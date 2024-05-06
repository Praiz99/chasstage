var nameDic ;
var roleId = "";
	
$(document).ready(function() {
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
	roleId = $("#id").val();
	initGrid(roleId);
	InitOrgDic();
});

// 初始化角色userGrid
function initGrid(roleId) {
	$("#userGrid").datagrid({
		url : ctx + "/sys/role/getRoleFindUserList",
		width : '99%',
		queryParams:{roleId:roleId},
		pagination: true,
		rownumbers: true,
		checkOnSelect : true,
		toolbar:initToolbar(),
		emptyMsg: '<span>无记录</span>',
		columns : [ [ {
			field : 'id',
			align : 'center',
			checkbox : true
		}, {
			field : 'name',
			title : '姓名',
			align : 'center',
			width : '20%'
		}, {
			field : 'login_id',
			title : '登录名',
			align : 'center',
			width : '32%'
		}, {
			field : 'org_name',
			title : '机构代码',
			width : '45%',
			align : 'center'
		}] ]
	});
}

function initToolbar(){
    var toolbar = [{
        text:'添加用户到角色',
        iconCls:'icon-add',
        handler:function(){
        	$('#openReceiveFeedBack')[0].src = ctx+"/sys/role/userSelectList";
    	    $('#allotUserDialog').dialog('open');
        }
    },'-',{
        text:'从角色中删除用户',
        iconCls:'icon-cut',
        handler:function(){
			var rows = $('#userGrid').datagrid('getSelections');
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
	            if (r) {
	            	deleteRoleUser(rows);
	            }
	        });
        }
    }];
    return toolbar;
}

// 删除数据
function deleteRoleUser(rows) {
	var ids = "";
	for ( var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
		if (ids == "") {
			ids = rows[0].id;
		} else {
			ids += "," + rows[i].id;
		}
	}
	$.ajax({
		type : "post",
		url : ctx + "/sys/userRole/delete?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$.messager.alert('温馨提示','删除成功','info');
			$("#userGrid").datagrid('reload');
		}
	});
}

//点击查找按钮出发事件
function searchFunc(field, idList) {
	var data = $("#searchForm").serializeObject();
	data[field] = idList;
	$("#userGrid").datagrid('load',data);   
	 
}

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('clear');
}

function closeDialog() {
	$('#allotUserDialog').dialog('close');
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

function orgChange(){
	nameDic.changeOptions({"url":ctx + "/sys/org/findPageList?name=" + $("#nameDic").val()});
}

//添加用户
function saveUserRoleList(){
	var client = frames.document['openReceiveFeedBack'];
    var data = client.userSelectSave();
    var userIdList = "";
    for ( var i = 0; i < data.length; i++) {
    	if (userIdList == "") {
    		userIdList = data[0].id;
		} else {
			userIdList += "," + data[i].id;
		}
	}
    $.ajax({
		type: "post",
		url: ctx+"/sys/role/saveUserList?roleId=" + roleId + "&userIdList=" + userIdList,
		dataType: 'json',
		cache: false,
		success: function(result) {
			$.messager.alert('系统提示',result.msg,'info',function(){
				closeDialog();
			});
			$("#userGrid").datagrid('reload');
		}
	});
}
