var url;
var id;
var flag;
$(function(){
	id = $("#id").val();
	if(id!=""){
			url = ctx + "/api/inner/findinnerPageList?groupId="+id;
	}else{
		url = ctx + "/api/inner/findinnerPageList";
	}
	initDataGrid();
});

function initDataGrid() {
	$("#datagrid").datagrid({
		url :url,
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
		toolbar : initToolbar(),
		emptyMsg: '<span>无记录</span>',
		columns : [ [ 
		    {field : 'id',align : 'center',checkbox : true},
			{field : 'serviceName',title : '接口名称',align : 'center',width : '17%'},
			{field : 'serviceMark',title : '接口标识',align : 'center',width : '18%'},
			{field : 'serviceUrl',title : '接口地址',align : 'center',width : '28%'},
			/*{field : 'serviceGroup',title : '接口分组标识',align : 'center',width : '15%'},*/
			{field : 'serviceType',title : '接口类型',align : 'center',width : '18%',
				formatter:function(value,row,index){
	          		  var statusStr;
	          		  if(row.serviceType == 01){
	          			  statusStr = "请求服务";
	          		  }else if(row.serviceType == 02){
	          			  statusStr = "webservice";
	          		  }else if(row.serviceType == 03){
	          			  statusStr = "http rest";
	          		  }
	          		  return statusStr;
	          	  }},
			{field : '_operate',title : '编辑',align : 'center',width : '18%',formatter : formatOper}
			] ]
	});
}

function initToolbar() {
	var toolbar = [ {
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			window.location.href = ctx + "/api/inner/addorUpinnerForm";
		}
	}, '-', {
		text : '删除',
		iconCls : 'icon-cut',
		handler : function() {
			var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
				if (r) {
					deleteRegion(rows);
				}
			});
		}
	} ];

	return toolbar;
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	var h = "";
  	h += "<a href='#' style='color:blue' onclick='editUser(\"" + index + "\");'>修改</a>";
  	h += "&nbsp;<a href='#' style='color:blue' onclick='addRtn(\"" + index+ "\");'>返回值设置</a> ";
	h += "&nbsp;<a href='#' style='color:blue' onclick='addParam(\"" +index + "\");'>参数设置</a> ";
  	return h;
	//return '<a href="#" onclick="editUser(' + index + ')">修改</a>';
}

function editUser(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/api/inner/addorUpinnerForm?id="+row.id;
	}
}

function addRtn(index){
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/api/field/addorUprtnForm?apiId="+row.id+"&fl=0";
	}
	/*window.location.href=ctx + "/api/field/addorUprtnForm?apiId="+id;*/
}

function addParam(index){
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/api/field/addorUpparamForm?apiId="+row.id+"&fl=0";
	}
	/*window.location.href=ctx + "/api/field/addorUpparamForm?apiId="+id;*/
}

/* 删除样式 */
function deleteRegion(rows) {
	var ids = "";
	for ( var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
		if (ids == "") {
			ids = rows[0].id;
		} else {
			ids += "," + rows[i].id;
		}
	}
	var serviceMarks = "";
	for ( var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
		if (serviceMarks == "") {
			serviceMarks = rows[0].serviceMark;
		} else {
			serviceMarks += "," + rows[i].serviceMark;
		}
	}
	$.ajax({
		type : "post",
		url : ctx + "/api/inner/findlicenses?apiMark=" + serviceMarks,
		dataType : 'json',
		cache : false,
		success : function(data) {
			if(data.success){
				$.messager.alert("系统提示",data.msg);
				$("#datagrid").datagrid('reload');
			}else{
				$.ajax({
					type : "post",
					url : ctx + "/api/inner/deleteinner?id=" + ids,
					dataType : 'json',
					cache : false,
					success : function(data) {
						if(data.success){
							$.messager.alert("系统提示",data.msg);
							$("#datagrid").datagrid('reload');
						}else{
							$.messager.alert("系统提示",data.msg);
						}
					}
				});
			}
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
    $("#searchForm").find("input").val("");
}

/* 表单数据传成json 
 * 
 * $.fn是指jquery的命名空间，加上fn上的方法及属性，会对jquery实例每一个有效
 * 
 * push在js中是压栈的意思
 * */
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

