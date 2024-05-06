$(document).ready(function(){
	initDataGrid();
});

//工具条绑定
function initToolbar() {
	if(mode=="dev" ){
		var toolbar = [ {
			text : '生成版本记录',
			handler : function() {
				$('#openAddUpgradeRecord')[0].src = ctx + "/version/addUpgradeRecordForm";
				$('#addUpgradeRecord').dialog('open');
			}
		} ];

		return toolbar;
	}
}

function initDataGrid(){
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid({
		url:ctx + "/version/getUpgradeRecordData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		/*toolbar : initToolbar(),*/
		queryParams:{appMark:data.appMark},
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'appMark',title:'应用标识',align:'center',width:'15%'},
	              {field:'appVersionNo',title:'应用版本号',align:'center',width:'15%'},
	              {field:'packageName',title:'版本包名称',align:'center',width:'24%'},
	              {field:'publishTime',title:'发布时间',align:'center',width:'15%'},
	              {field:'onlineTime',title:'上线时间',align:'center',width:'15%'},
	              {field:'_operate',title:'操作',align:'center',width:'15%',formatter:formatOper}
	          ]]		
	});
}


//增加修改列
function formatOper(val,row,index){  
	var h='<div style="text-align: left;padding-left: 10px;"><a href="JavaScript:void(0)" onclick="viewLog(&quot;'+row.id+'&quot;)">详情</a>&nbsp;&nbsp;';
	if(mode=="dev" ){
		h+='<a href="JavaScript:void(0)" onclick="downSql(&quot;'+index+'&quot;)">导出脚本</a>&nbsp;&nbsp;';
	}
	h+='</div>';
	return h;
} 

function viewLog(id){
	document.getElementById("processNotice").innerHTML='<iframe scrolling="no" id="openProcessNotice" name="openProcessNotice" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>';
	$('#openProcessNotice')[0].src = ctx + "/version/upgradeRecordForm?id="+id;
	$('#processNotice').dialog('open');
}

function downSql(index){
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];//返回第一个被选中的行或如果没有选中的行则返回null。
	$.ajax({
		type: "post",
		url: ctx+"/version/saveUpgradeRecord",
		data: {id:row.id},
		dataType: 'json',
		cache: false,
		success: function(data) {
				exportRaw(data.uploadName+'.sql',data.data);
		}
	});
}

function fakeClick(obj) {
	  var ev = document.createEvent("MouseEvents");
	  ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
	  obj.dispatchEvent(ev);
	}

function exportRaw(name, data) {
	  var urlObject = window.URL || window.webkitURL || window;
	  var export_blob = new Blob([data]);
	  var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
	  save_link.href = urlObject.createObjectURL(export_blob);
	  save_link.download = name;
	  fakeClick(save_link);
} 

function closeDialog(){
	$('#processNotice').dialog("close");
	$('#addUpgradeRecord').dialog("close");
}

//点击查找按钮出发事件
function searchFunc() {
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid('load',data);   
}
jQuery.fn.removeSelected = function() {
    this.val("");
};

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('reset');
}

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

