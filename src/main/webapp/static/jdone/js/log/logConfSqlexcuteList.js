$(function(){
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
		url:ctx + "/log/conf/sqlexcute/findPageList",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		toolbar : initToolbar(),
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'msgType',title:'应用名称',align:'center',width:'7%'},
	              {field:'sendTime',title:'模块名称',align:'center',width:'7%'},
	              {field:'sqlMark',title:'sql语句标识',align:'center',width:'36%'},
	              {field:'sqlType',title:'sql类型',align:'center',width:'6%'},
	              {field:'sqlFuncDesc',title:'sql功能描述',align:'center',width:'16%'},
	              {field:'isFilter',title:'是否过滤',align:'center',width:'4%',
	            	  formatter:function(value,row,index){
	            		  var statusStr;
	            		  if(row.isFilter == 1){
	            			  statusStr = "是";
	            		  }else if(row.isFilter == 0){
	            			  statusStr = "否";
	            		  }
	            		  return statusStr;
	            	  }},
	              {field:'minCost',title:'最小耗时',align:'center',width:'4%',
            		  formatter:function(value,row,index){
            			  var Str;
            			  if(value == null){
            				  Str = "0ms";
            			  }else{
            				  Str = value + "ms";
            			  }
            			  return Str;  
            		  }},
	              {field:'tjxgsj',title:'添加修改时间',align:'center',width:'9%'},
	              {field:'opt',title:'操作',align:'center',width:'8%',  
            	  formatter : function(value, row, index) {
						var str;
						str = "<a class='oper-btn oper-edit' href='logConfForm?id="
								+ row.id
								+ "'>修改</a>"
						return str;
						}
	              }
	          ]]		
	});
}

function initToolbar() {
	var toolbar = [ {
		text : '新增',
		handler : function() {
			window.location.href = ctx + "/log/conf/sqlexcute/logConfForm";
		}
	}, '-', {
		text : '删除',
		handler : function() {
			var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
			if (rows.length == 0) {
				$.messager.alert("系统提示", "请至少选择一行数据!");
				return false;
			}
			$.messager.confirm('系统提示', '您确定要删除吗?', function(r) {
				if (r) {
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
						url : ctx + "/log/conf/sqlexcute/delete?id=" + ids,
						dataType : 'json',
						cache : false,
						success : function(data) {
							$("#datagrid").datagrid('reload');
						}
					});
				}
			});
		}
	} ];

	return toolbar;
}

function searchFunc(){
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid("load",data);//加载和显示数据
}

function ClearQuery(){
	$("#searchForm").form('clear');
}