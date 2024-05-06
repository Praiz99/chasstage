
$(document).ready(function(){
	//字典初始化
	dicInit(true);
	refreshTree();
	refreshRoleTree();
	//绑定回车事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
	//设置字典下拉框宽度
	$(".result-container").css("width","345px");
	if(parent.$("textarea[name='users']").val()!=undefined && parent.$("textarea[name='usersNames']").val()!=undefined){
	 //处理父窗口的选择值
	var ids =parent.$("textarea[name='users']").val().split(",");//得到父窗口的值
	var names =parent.$("textarea[name='usersNames']").val().split(",");//得到父窗口的值
	for(var i = 0; i < ids.length; i ++){
		if(names[i] != undefined && names[i] != "undefined" && names[i] != null && names[i] !=""){
			var data = {
					idCard: ids[i],
					name: names[i]
			};
			add(data);
		}
	}
	}
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url: ctx+"/system/findPage",
		width:'99%',
		height:'87%',
		pagination:true,
		rownumbers: true,
		onUncheck:onUnchecks, //取消选择一行。
		onSelect:onSelectRow,	//在用户选择一行的时候触发
		onCheck :checkRow,
		singleSelect:isSingle,//如果为true，则只允许选择一行。
		onSelectAll:onCheckAllRow,//在用户选择所有行的时候触发
		onUnselectAll:onUnCheckAllRow,//在用户取消选择所有行的时候触发
		checkOnSelect:true,
		emptyMsg: '<span>无记录</span>',
		onLoadSuccess:function(data){
			var trs = $(".table-grid tr").length;
			for(var i=1;i<trs;i++){
				//$("#datagrid").parent().find("div .datagrid-header-check").children("input[type=\"checkbox\"]").eq(0).attr("style", "display:none;");
				var index = $(".table-grid tr").eq(i).find("td").find("input").val();
				index = index.split("#");
				var val = index[0];
				$(data.rows).each(function(e,w){
					if(w.idCard == val){
					var table = $(".datagrid-btable")[1];
					$($(table).find("tr")).each(function(u,r){
						if($(r).attr('datagrid-row-index') == e){
							$(r).find("input").attr("checked",true);
							$(r).attr('class','datagrid-row datagrid-row-checked datagrid-row-selected');
						}
					});
					return;
					}
				});
			}
	    },
	    columns:[ [ {field : 'id',align : 'center',checkbox : true},
	              {field:'name', title:'姓名',align:'center',width:'49%'},
	              {field:'orgCodeName',title:'所属机构',align:'center',width:'48%'},
	              {field:'loginId',title:'登录名',align:'center',hidden:true},
	              {field:'idCard',title:'身份证号码',align:'center',hidden:true}
	          ]]
	});
}

//刷新树
function refreshTree(){
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
					searchFunc(treeNode.id);
				}
			}
		};
	initDataGrid();
	$('#datagrid').datagrid('loading');
	$.ajax({
		type: "post",
		url: ctx+"/system/treeData",
		dataType: 'json',
		cache: false,
		success: function(data) {
			$.fn.zTree.init($("#ztree"), setting, data);
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

//表单数据传成json
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


//用户列表选择事件
function selectMulti(obj) {
	var data = null;
	if(idField == "id"){
		data = {id: obj.id, name:obj.name};
	}else if(idField == "login_Id"){
		data = {id: obj.loginId, name:obj.name};
	}else if(idField == "id_card"){
		data = {idCard: obj.idCard, name:obj.name};
	}
	add(data);
};

//添加数据
function add(data) {
	if(data.idCard ==undefined) return;
	var len = $("#user_" + data.idCard).length;
	if(len > 0) return;
	if(isSingle)$("#sysUserList").empty();
	var aryData = '<tr id="user_' + data.idCard + '">'+
		'<td width="60%">'+
		'<input type="hidden" class="pk" name="userData" value="' + data.idCard + "#" + data.name + '"><span> '+
		data.name+
		'</span></td>'+
		'<td><a onclick="javascript:del(this);"><img src="'+ctx+'/static/framework/plugins/easyui-1.5.1/themes/icons/clear.png"></a> </td>'+
		'</tr>';
	$("#sysUserList").append(aryData);
};

//选择行事件
function onSelectRow(row,index){
	if(!isSingle){
		selectMulti(index);
	}
}

//取消行选中事件
function onUnchecks(index,row){
	var val=null;
	var leng = $(".table-grid tr").length;
	for(var i= 1; i<leng; i++)  {
		var values = $(".table-grid tr").eq(i).find("td").find("input").val();
		if(values != undefined){
		values = values.split("#");
		val = values[0];
		}
		if(row.idCard == val){
			$(".table-grid tr").eq(i).find("td").find("a").closest("tr").remove();
		}
	}
}
//行选择事件
var checked =true;
function checkRow(index,row){
	if(checked){
		selectMulti(row);
	}
}

//全选/反选事件
function onCheckAllRow(rows){
	if (rows) {
		var data = $('#datagrid').datagrid('getSelections');
		$(data).each(function() {
			selectMulti(this);
		});
	}
}
function onUnCheckAllRow(){
	$("#sysUserList").html('');
}

// 单行清空
function del(obj) {
	var rows = $('#datagrid').datagrid("getRows");
	var index = $(obj).parent().prev().find("input").val();//如果直接获取input的class只能顺序删除
	index = index.split("#");
    var val = index[0];
	$(rows).each(function(i,o){
		if(o.idCard == val){
			var table = $("table[class='datagrid-btable']")[1];
			$($(table).find("tr")).each(function (q,r){
				if($(r).attr('datagrid-row-index') == i){
					$(r).find("input").attr("checked",false);
					$(r).attr('class','datagrid-row');
				}
			});
			return;
		}
	});
	 $(obj).closest("tr").remove();
};

//清空所有选中事件
function dellAll() {
	$("#sysUserList").empty();

	var rows = $('#datagrid').datagrid("getRows");
	var len = $(".table-grid tr").length;
	if(len == 0){
		$(rows).each(function(i){
			var table = $(".datagrid-btable")[1];
			$(table).find("tr").each(function(w,e){
				if($(e).attr("datagrid-row-index") == i){
					$(e).find("input").attr("checked",false);
					$(e).attr('class','datagrid-row');
				}
			});
		});
	}
};

function selectUser() {
	var pleaseSelect = "请选择要接收对象";
	var aryUser = $("input[name='userData']", $("#sysUserList"));

	if(aryUser.length == 0) {
		$.messager.confirm("温馨提示",pleaseSelect);
		return;
	}

	var aryId = new Array();
	var aryName = new Array();
	var aryOther = new Array();

	aryUser.each(function() {
		var data = $(this).val();
		var aryTmp = data.split("#");
		aryId.push(aryTmp[0]);
		aryName.push(aryTmp[1]);
		aryOther.push(aryTmp[2]);
	});

	var obj = {ids: aryId.join(","), names: aryName.join(","), others: aryOther.join(",")};
	window.parent.$("#users").val(obj.ids);
	window.parent.$("#usersNames").val(obj.names);
	if(typeof(window.parent.temp)=="function"){//判断调用页面的方法是否存在
		var row = $('#datagrid').datagrid('getRows')[index];
		window.parent.temp(row);
	}
	parent.win.dialog('close');
}


/**
 * 角色树操作
 * */
function initMenuData(idList){
	var sval = $("select[class='systemId'] option:selected").val();
	DataGrid(sval);
}

function DataGrid(){
	$.ajax({
		url:ctx+'/system/treeDataRoles',
		dataType:'text',
		data:{doc:$("select[class='systemId'] option:selected").val()},
		type:'POST',
		dataType:'json',
		async:false,
		success:function (datas){
			$.fn.zTree.init($("#userZtree"), setting, datas);
		}
	});
}

function refreshRoleTree(){
		$.getJSON(ctx + "/system/treesData", function(data) {
			$.fn.zTree.init($("#userZtree"), setting, data);
		});
}

var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id"
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				searchFunc(treeNode.id);
			}
		}
	};
