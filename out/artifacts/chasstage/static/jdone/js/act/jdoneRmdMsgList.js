var queryParams = {};
var checkEle = null;
$(function() {
	checkEle = $($(".rb-toolbar-bg")[0]);
	initDataGrid();
});

function initDataGrid() {
	$("#datagrid").datagrid({
		url : ctx + "/act/rmdMsg/findPageList",
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		queryParams: {msgType: 'ws'},
		columns : [[
	              {field:'msgTitle',title:'主题',align:'center',width:'25%'},
	              {field:'ryxm',title:'人员姓名',align:'center',width:'10%',
            		  formatter:function(value,row,index){ 
            			  var bizData = eval('(' + row.glywData + ')');
            			  return bizData == null ? "" : bizData.ryxm; 
    	              }},
	              {field:'sjmc',title:'案件名称',align:'center',width:'30%'},
	              {field:'ajlx',title:'案件类型',align:'center',width:'10%',
            		  formatter:function(value,row,index){ 
            			  var bizData = eval('(' + row.glywData + ')');
            			  return bizData == null ? "" : bizData.ajlx;
              		  }},
	              {field:'sjbh',title:'案件编号',align:'center',width:'15%'},
	              {field:'opt',title:'操作',align:'center',width:'10%', formatter : formatOper}
		]]
	});
}



/* 更改加载数据 */
function changeMsgData(val,e) { 
	$(e).attr("class",$(e).attr("class") + " a");
	checkEle.attr("class",checkEle.attr("class").toString().substring(0,checkEle.attr("class").length-2));
	checkEle = $(e);
	if(val == "ws" || val == "sla"){
		$("#datagrid").datagrid({
			queryParams: {msgType: val},
			columns : [[
		              {field:'msgTitle',title:'主题',align:'center',width:'25%'},
		              {field:'ryxm',title:'人员姓名',align:'center',width:'10%',
	            		  formatter:function(value,row,index){
	            			  var bizData = eval('(' + row.glywData + ')');
	            			  return bizData == null ? "" : bizData.ryxm; 
	    	              }},
		              {field:'sjmc',title:'案件名称',align:'center',width:'30%'},
		              {field:'ajlx',title:'案件类型',align:'center',width:'10%',
	            		  formatter:function(value,row,index){ 
	            			  var bizData = eval('(' + row.glywData + ')');
	            			  return bizData == null ? "" : bizData.ajlx;
	              		  }},
		              {field:'sjbh',title:'案件编号',align:'center',width:'15%'},
		              {field:'opt',title:'操作',align:'center',width:'10%', formatter : formatOper}
			]]
		});
	}
	if(val == "jq" || val == "sawp" || val == "tysjbg" || val == "aj" || val == "jdkp" || val == "qt"){
		$("#datagrid").datagrid({
			queryParams: {msgType: val},
			columns : [[
		              {field:'msgTitle',title:'主题',align:'center',width:'50%'},
		              {field:'sendObjName',title:'来源用户',align:'center',width:'25%'},
		              {field:'createTime',title:'发送时间',align:'center',width:'15%'},
		              {field:'opt',title:'操作',align:'center',width:'10%', formatter : formatOper}
			]]
		});
	}
	if(val == "baqry"){
		$("#datagrid").datagrid({
			queryParams: {msgType: val},
			columns : [[
		              {field:'msgTitle',title:'主题',align:'center',width:'50%'},
		              {field:'csyy',title:'出所原因',align:'center',width:'20%',
	            		  formatter:function(value,row,index){ 
	    	                	return value; 
	    	              }},
		              {field:'cssj',title:'出所时间',align:'center',width:'10%'},
		              {field:'ryxm',title:'人员姓名',align:'center',width:'10%',
	            		  formatter:function(value,row,index){
	            			  var bizData = eval('(' + row.glywData + ')');
	            			  return bizData == null ? "" : bizData.ryxm; 
	    	              }},
		              {field:'opt',title:'操作',align:'center',width:'10%', formatter : formatOper}
			]]
		});
	}
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
	return '<a href="#" onclick="dealTask(' + index + ')">处理</a>&nbsp; <a href="#" onclick="noPrompt(' + index + ')">不再提醒</a>';
}

function dealTask(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	var flogStr = "?";
	if(row.msgDealUrl.indexOf("?")>-1){
		flogStr = "&";
	}
	if (row) {
		window.open(ctx + "/" + row.msgDealUrl + flogStr + "msgId=" + row.id);
		/*$.ajax({
			type : "post",
			url : ctx + "/act/pendTask/getTaskByBizIdAndType?bizId=" + row.bizId + "&bizType=" + row.bizType,
			dataType : 'json',
			cache : false,
			success : function(task) {
			},
			error : function(task) {
				window.open(ctx + "/" + row.msgDealUrl + flogStr + "msgId=" + row.id);
			}
		});*/
	}
}

function noPrompt(index) {
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row) {
		$.ajax({
			type : "post",
			url : ctx + "/act/rmdMsg/noPrompt?msgId=" + row.id,
			dataType : 'json',
			cache : false,
			success : function(task) {
				$("#datagrid").datagrid('reload');
			}
		});
	}
}

/* 删除样式 */
function deleteTask(rows) {
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
		url : ctx + "/act/pendTask/delete?id=" + ids,
		dataType : 'json',
		cache : false,
		success : function(data) {
			$("#datagrid").datagrid('reload');
		}
	});
}

// 刷新当前页面
function callback() {
	$("#datagrid").datagrid('reload');
}

