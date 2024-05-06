var roleList = [];
var regcode = orgcode = new Array();
$(document).ready(function() {
	$("#userRoleForm").validate();
	var id = $("#id").val();
	timeHide();
	initData(id);
	InitOrgDic();
	$("#ifBj").hide();
	$("#ifXz").show();

});

//初始化数据
function initData(id) {
	// 初始化机构列表数据
	initOrgList('id', 'pid', 'code', ctx + "/sys/org/treeData", true);
	// 初始化角色列表数据
	initRoleList('id', 'roleKind', 'id', ctx + "/sys/role/treeData", false);
	if (id != null && id != "") {
		// 加载表单数据
		$.ajax({
			type : "post",
			url : ctx + "/sys/user/findById?id=" + id,
			dataType : 'json',
			cache : false,
			success : function(data) {
				$('#userRoleForm').form('load', data);
				initGrid(id, roleList, data);
			}
		});
	}
}

// 初始化角色datagrid
function initGrid(id, roleList, formData) {
	$("#roleGrid").datagrid({
		url : ctx + "/sys/userRole/findPageList?userId=" + id,
		width : '99%',
		pagination : false,
		rownumbers : true,
		checkOnSelect : false,
		singleSelect : true,
		columns : [ [ {
			field : 'name',
			title : '姓名',
			width : '10%',
			align : 'center',
			formatter : function(value, row, index) {
				return formData.name;
			}
		}, {
			field : 'ridName',
			title : '角色名称',
			align : 'center',
			width : '10%'
		}, {
			field : 'fgOrgCodeName',
			title : '分管机构',
			width : '20%',
			align : 'center'
		}, {
			field : 'grantType',
			title : '授权类型',
			width : '10%',
			align : 'center',
            formatter: function (value, row, index) {
                if (value == undefined || value == "" || value == "null") {
                    return "";
                } else {
                    if (value == "1") {
                        return "永久";
                    } else if (value == "0") {
                        return "临时";
                    }
                }
            }
		}, {
			field : 'grantStartTime',
			title : '授权开始时间',
			width : '17%',
			align : 'center',
            formatter: function (value, row, index) {
                if (row.grantType == "1") {
                    return "";
                } else {
                	  return value;
                }
            }
		}, {
			field : 'grantEndTime',
			title : '授权结束时间',
			width : '17%',
			align : 'center',
            formatter: function (value, row, index) {
                if (row.grantType == "1") {
                    return "";
                } else {
                	  return value;
                }
            }
		}, {
			field : '_operate',
			title : '操作',
			align : 'center',
			width : '15%',
			formatter : formatOper
		} ] ]
	});
}

function closeDialog() {
	parent.$('#ReceiveFeedBackDialog').dialog('close');
}

//增加修改列
function formatOper(val, row, index) {
	return '<a href="#" class="icon-edit" title="编辑" onclick="editUserRole('
			+ index
			+ ',this)" style="margin-right: 20px;"></a><a href="#" class="icon-cut" title="删除" onclick="deleteUserRole('
			+ index + ')"></a>';
} 

//修改数据
function editUserRole(index, comment) {
	if (comment.className == "icon-edit") {
		$(comment).attr("class", "icon-undo");
		$('#roleGrid').datagrid('selectRow', index);
		var row = $('#roleGrid').datagrid('getSelected');
		$('#userRoleForm').form('load', row);
		$.ajax({
			url : ctx + "/sys/role/findById?id="+row.rid,
			dataType : 'json',
			type : 'POST',
			async : false,
			success : function(data) {
				$('#roleSel').val(data.name);
				$('#orgSel').val(row.fgOrgName);
				$('#roleId').val(row.id);
			}
		});
		$.ajax({
			url : ctx + "/sys/userRole/findById?id="+row.id,
			dataType : 'json',
			type : 'POST',
			async : false,
			success : function(data) {
				if(data.grantType=="0"){
					$("#stateon").attr("checked","checked"); 
					$("#stateoff").removeAttr("checked");
					$("#time").show();
					$('#kssj').val(data.grantStartTime);
					$('#jssj').val(data.grantEndTime);
				}else{
					$("#stateoff").attr("checked","checked"); 
					$("#stateon").removeAttr("checked");
					$("#time").hide();
				}
			}
		});
		$('#addBtn').val("保存");
		$("#ifXz").hide();
		$("#ifBj").show();
	} else {
		$(comment).attr("class", "icon-edit");
		$('#userRoleForm')[0].reset();
		$('#addBtn').val("添加");
		$("#ifBj").hide();
		$("#ifXz").show();
	}
}

//删除数据
function deleteUserRole(index) {
	
	$('#roleGrid').datagrid('selectRow', index);// 关键在这里
	var row = $('#roleGrid').datagrid('getSelected');
	
	var rowDatas = $('#userRoleForm').serializeObject();

	rowDatas.rid = row.rid;
	
	$.ajax({
		type : "post",
		url : ctx + "/sys/userRole/isPermission",
		data : rowDatas,
		dataType : 'json',
		cache : false,
		success : function(data) {
			if(data.success){
				$.messager.alert("系统提示", "您无权对该角色进行操作！！！");
				return false;
			}else{
				$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
					if (r) {
						$.ajax({
							type : "post",
							url : ctx + "/sys/userRole/delete?id=" + row.id,
							dataType : 'json',
							cache : false,
							success : function(data) {
								$('#userRoleForm')[0].reset();
								$('#addBtn').val("添加");
								$("#roleGrid").datagrid('reload');
							}
						});
					}
				});
			}
				
		}
	});

}

function timeShow(){
	$("#time").show();
}
function timeHide(){
	$("#time").hide();
}

function addUserRole() {
	var rowDatas = $('#userRoleForm').serializeObject();
	if(rowDatas.rid==null || rowDatas.rid==""){
		$.messager.alert("系统提示", "请选择角色!");
		return false;
	}
	if(rowDatas.grantType==0){
		if((rowDatas.kssj==null || rowDatas.kssj=="") || (rowDatas.jssj==null || rowDatas.jssj=="")){
			$.messager.alert("系统提示", "请选择授权时间!");
			return false;
		}
	}
	rowDatas.id = $('#roleId').val();
	
	$.ajax({
		type : "post",
		url : ctx + "/sys/userRole/isPermission",
		data : rowDatas,
		dataType : 'json',
		cache : false,
		success : function(data) {
			if(data.success){
				$.messager.alert("系统提示", "您无权对该角色进行操作！！！");
				return false;
			}else{
				//校验分配的角色唯一性
				$.ajax({
					type : "post",
					url : ctx + "/sys/userRole/findByRid",
					data : rowDatas,
					dataType : 'json',
					cache : false,
					success : function(data) {
						if(data.length!=0 && data[0].id!=rowDatas.id){
							$.messager.alert("系统提示", "当前角色已经分配，请重新分配!");
							return false;
						}
						$.ajax({
							type : "post",
							url : ctx + "/sys/userRole/save",
							data : rowDatas,
							dataType : 'json',
							cache : false,
							success : function(data) {
								$('#userRoleForm')[0].reset();
								$('#addBtn').val("添加");
								$("#roleGrid").datagrid('reload');
								 var diczzmm = $("#nameDic").getDicManager();
								 diczzmm.loadData();
							}
						});
					}
				});
				timeHide();
			}
				
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
		hiddenField:'fgOrgCode',
		multi:true,
		resultWidth:300
	});
}

function SelectOrgs(){
	EasyextDialog( ctx + '/system/sysOrg?idField=code&hidden=true&isSingle=false','机构','80%','100%',true,true,null,"orgs");
}
var win,regs,orgs;
function EasyextDialog(url, title, width, height, shadow,flag,fun,type) {  
	var ids = Math.round(Math.random()*1000);
    var content = '<iframe id="org'+ids+'" name="org'+ids+'" src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="'+ids+'" title="' + title + '"></div>';//style="overflow:hidden;"可以去掉滚动条  
    $(parent.document.body).append(boarddiv); 
    if(flag){
    	win = parent.$('#'+ids).dialog({  
    		content: content,  
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
    				var client = parent.frames.document['org'+ids];
    				if(type == "orgs"){
    					orgs = null;
    					orgs = client.$("#divSec").datagrid("getChecked");
    					$(orgs).each(function (i,o){
    						/*if(!orgcode.contains(o.sysCode) && !orgold.contains(o.sysCode)){*/
    							orgcode.push(o.sysCode);
    						/*}*/
    					});
    					 var diczzmm = $("#nameDic").getDicManager();
    					 diczzmm.clear();
    					 diczzmm.setValue(orgcode);
    					/*$("#fgOrgCode").datagrid('load',{sysCodes:orgcode.join(",")+","+orgold.join(",")});*/
    				}
    				win.dialog('close');
    			}
    		},
    		{ 
    			text:'关闭', 
    			iconCls:'icon-cancel', 
    			left:50,
    			handler:function(){
    				win.dialog('close');  
    			}
    		}
    		]
    	});  
    }else{
    	win = parent.$('#'+ids).dialog({  
    		content: content,  
    		width: width,  
    		height: height,  
    		modal: shadow,  
    		title: title,  
    		onClose: function () {  
    			//关闭后事件
    			if(fun){
    				fun();
    			}
    		}
    	});  
    }
    if(flag){
    	parent.$("#"+ids).next().css("text-align","center");
    }
    win.dialog('open');  
    return ids;
}