var groupLevel;
var pid;
$(function() {
	groupLevel = $("#groupLevel").val();
	pid = $("#pid").val();
	initDataGrid();
});

function initDataGrid() {
	$("#datagrid").datagrid({
		url : ctx + "/sys/datarange/findPageList",
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
			{field : 'name',title : '分组名称',align : 'center',width : '25%'},
			{field : 'mark',title : '权限标识',align : 'center',width : '20%'},
			{field : 'isEnableRange',title : '是否设置范围',align : 'center',width : '10%',
				formatter:function(value,row,index){
	          		  var statusStr;
	          		  if(row.isEnableRange == 0){
	          			  statusStr = "否";
	          		  }else if(row.isEnableRange == 1){
	          			  statusStr = "是";
	          		  }
	          		  return statusStr;
	          	  }},
			{field : 'isEnableAuth',title : '是否权限控制',align : 'center',width : '10%',
				formatter:function(value,row,index){
	          		  var statusStr;
	          		  if(row.isEnableAuth == 0){
	          			  statusStr = "否";
	          		  }else if(row.isEnableAuth == 1){
	          			  statusStr = "是";
	          		  }
	          		  return statusStr;
	          	  }},
	          	{field : 'defaultRange',title : '默认范围',align : 'center',width : '15%',
	  				formatter:function(value,row,index){
	  	          		  var statusStr;
	  	          		  if(row.defaultRange == 'user'){
	  	          			  statusStr = "本身";
	  	          		  }else if(row.defaultRange == 'org'){
	  	          			  statusStr = "单位";
	  	          		  }else if(row.defaultRange == 'reg'){
	  	          			  statusStr = "区县";
	  	          		  }else if(row.defaultRange == 'city'){
	  	          			  statusStr = "本市";
	  	          		  }else if(row.defaultRange == 'province'){
	  	          			  statusStr = "全省";
	  	          		  }
	  	          		  return statusStr;
	  	          	  }},
			{field : '_operate',title : '编辑',align : 'center',width : '19%',formatter : formatOper}
			] ]
	});
}

function initToolbar() {
	var toolbar = [ {
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			parent.$("#mainForm").attr("src","datarangeForm?id="+pid);
			window.location.href=ctx + "/sys/datarange/datarangeForm?pid="+pid;
		}
	} ,{
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
	var h='<a href="#" onclick="editUser(' + index + ')">修改</a>&nbsp';
	h+='<a href="#" onclick="openSelectSocpe(\''+row.id+'\',\''+row.name+'\',\''+null+'\')">设置范围</a>';
    return h;
}

function editUser(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		window.location.href=ctx + "/sys/datarange/datarangeForm?id="+row.id;
		parent.$("#mainForm").attr("src","datarangeForm?id="+row.id);
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
		url : ctx + "/sys/datarange/deleteDatarangeById?id=" + ids,
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

