$(function() {
	initDataGrid();
});

function initDataGrid() {
	var apiId=$("#apiId").val();
	$("#datagrid").datagrid({
		url : ctx + "/api/field/findparamPageList?apiId="+apiId,
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
		toolbar : initToolbar(),
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [ 
		    {field : 'id',align : 'center',checkbox : true},
			{field : 'paramDesc',title : '参数描述',align : 'center',width : '20%'},
			{field : 'paramName',title : '参数名称',align : 'center',width : '20%'},
			{field : 'paramType',title : '参数类型',align : 'center',width : '20%',
				formatter:function(value,row,index){
	          		  var statusStr;
	          		  if(row.paramType == 01){
	          			  statusStr = "字符";
	          		  }else if(row.paramType == 02){
	          			  statusStr = "数字";
	          		  }
	          		  return statusStr;
	          	  }},
			{field : 'defaultValue',title : '参数默认值',align : 'center',width : '20%'},
			{field : 'isMust',title : '是否必须',align : 'center',width : '10%',
				formatter:function(value,row,index){
	          		  var statusStr;
	          		  if(row.isMust == 0){
	          			  statusStr = "否";
	          		  }else if(row.isMust == 1){
	          			  statusStr = "是";
	          		  }
	          		  return statusStr;
	          	  }},
			{field : '_operate',title : '编辑',align : 'center',width : '9%',formatter : formatOper}
			] ]
	});
}

function initToolbar() {
	var toolbar = [/* {
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			window.location.href = ctx + "/api/field/addorUpparamForm";
		}
	}, '-', */{
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
		window.location.href=ctx + "/api/field/addorUpparamForm?id="+row.id;
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
		url : ctx + "/api/field/deleteparam?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}

/*按区域名称查找*/
function searchFunc(idList){
	var data = $("#addorUpwbjkglForm").serializeObject();
	data.id = idList;
	$("#datagrid").datagrid("load",data);//加载和显示数据
}
/*清理所有数据*/
function ClearQuery(){
	$("#addorUpwbjkglForm").find("input").val("");
	}

function saveparam(){
	//$("#addorUpparamForm").valid();
	if(!$("#addorUpparamForm").valid()){
		return false;
	}
	 $('#addorUpparamForm').form('submit',{
           url: "saveparam",
           success: function(result){
               var result = eval('('+result+')');
               if (!result.success){
                   $.messager.show({
                       title: '提示',
                       msg: result.msg
                   });
               } else {
               	$.messager.show({
                       title: '提示',
                       msg: result.msg
                   });
                   document.getElementById("addorUpparamForm").reset(); 
                   $('#datagrid').datagrid('reload'); 
               }
           }
       });
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

