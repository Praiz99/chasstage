var groupLevel;
var pid;
$(function() {
	groupLevel = $("#groupLevel").val();
	pid = $("#pid").val();
	initDataGrid();
});

function initDataGrid() {
	$("#datagrid").datagrid({
		url : ctx + "/sys/param/findPageList",
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
		queryParams:{groupLevel:groupLevel}, 
		toolbar : initToolbar(),
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [ 
		    {field : 'id',align : 'center',checkbox : true},
			{field : 'name',title : '参数名称',align : 'center',width : '25%'},
			{field : 'mark',title : '参数标识',align : 'center',width : '25%'},
			{field : 'value',title : '参数值',align : 'center',width : '40%'},
			{field : '_operate',title : '编辑',align : 'center',width : '9%',formatter : formatOper}
			] ]
	});
}

function initToolbar() {
	var toolbar = [ {
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			parent.$("#mainForm").attr("src","paramForm?id="+pid);
			window.location.href=ctx + "/sys/param/paramForm?pid="+pid;
		}
	} , {
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
	return '<a href="#" onclick="editUser(' + index + ')">修改</a>';
}

function editUser(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
//		window.location.href=ctx + "/sys/param/paramForm?id="+row.id;
		parent.$("#mainForm").attr("src","paramForm?id="+row.id);
	}
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
	$.ajax({
		type : "post",
		url : ctx + "/sys/param/deleteparamById?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}

/*按区域名称查找*/
function searchFunc(idList){
	var data = $("#searchForm").serializeObject();
	data.id = idList;
	$("#datagrid").datagrid("load",data);//加载和显示数据
}
/*清理所有数据*/
function ClearQuery(){
	$("#table_form").find("input").val("");
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
