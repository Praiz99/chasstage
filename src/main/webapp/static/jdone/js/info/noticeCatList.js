$(function() {
	initDataGrid();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
});
	//工具条绑定
	function initToolbar() {
		var toolbar = [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				 window.location.href = ctx + "/info/notice/noticeCatForm";
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-cut',
			handler : function() {
				var rows = $('#datagrid').datagrid('getSelections');// 返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
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
	//表格绑定
    function initDataGrid() {
    	$("#datagrid").datagrid({
    		url : ctx + "/info/notice/getNoticeCatPageData",
    		width : '100%',
    		pagination : true, // 显示分页栏
    		rownumbers : true, // 显示每行列号
    		checkOnSelect : true, // 复选框标识
    		fitColumns : true,
    		toolbar : initToolbar(),
    		emptyMsg: '<span>无记录</span>',
    		columns : [ [{
    			field : 'id',align : 'center',checkbox : true
    		}, {
    			field : 'mark',title : '分类标识',align : 'center',width : '27%'
    		}, {
    			field : 'name',title : '分类名称',align : 'center',width : '27%'
    		},{
    			field : 'tjrSfzhName',title : '添加人',align : 'center',width : '25%'
    		},{
    			field : '_operate',title : '编辑',align : 'center',width : '18%',
    			formatter:fromatOper
    			}
    		] ]
    	});
    }
    
    function fromatOper(value,row,index){  
		var h = "";
		 if(row.id!=undefined){
      	h += "<a href='#' style='color:blue' onclick='editUser(\"" + index + "\");'>修改</a>";
      	return h;
		 }
} 
    /*修改公告*/
    function editUser(index) {   
    	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
    	var row = $('#datagrid').datagrid('getRows')[index];//返回第一个被选中的行或如果没有选中的行则返回null。
    	if (row) {
    		window.location.href=ctx + "/info/notice/noticeCatForm?id="+row.id;
    	}
    }
    
  // 删除数据
    function deleteRegion(rows){
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
    		url: ctx+"/info/notice/deleteNoticeCat?id="+ids,
    		dataType: 'json',
    		cache: false,
    		success: function(data) {
    			if(data.success){
    				$.messager.alert('系统提示', data.msg, 'info',function(){
    					$("#datagrid").datagrid('reload');
    				});
    			}else{
    				$.messager.alert('系统提示', data.msg, 'error');
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

    

