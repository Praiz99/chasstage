$(function (){
	initAppDataGrid();
	dicInit(true);
	/*if (!!window.ActiveXObject || "ActiveXObject" in window){
		//兼容IE给body添加高度。
		$("html").css({"height":"98%","width":"99%","overflow":"hidden"});
		$("body").css({"height":"98%","width":"99%","overflow":"hidden"});
	}*/
	InitHpSetDic();
});
var dic;
function InitHpSetDic(){
	$.ajax({
		type : "post",
		url : ctx + "/sys/hpset/getHpSetRoleCodeBylevel?appId="+appId,
		dataType : 'json',
		cache : false,
		success : function(data) {
			dic = $("#applyRoleDic").ligerDictionary({
				dataAction:'local',
				data : {"Rows":data.name,"Total":data.name.length},
				valueField:'code',//字段值名称
				hiddenField:'applyRoleCode',
				labelField:'name',//选项字段名称
				width:'auto',
				resultWidth:180
			});
		}
	});
}

var datagrid;
var appId ;
function initAppDataGrid(){
	appId = $("#appId").val();
	datagrid = $('#datagrid').datagrid({
		url:ctx+"/sys/hpset/getSetAddressPageData",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		queryParams:{"appId":appId},
		singleSelect: false,
		toolbar : initToolbar(),
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'indexName', title:'首页名称',align:'center',width:'10%'},
	              {field:'indexUrl',title:'首页地址',align:'center',width:'25%'},
	              {field:'applyRoleName', title:'角色名称',align:'center',width:'14%'},
	              {field:'applyRoleCode', title:'角色代码',align:'center',width:'13%'},
	              {field:'crtateUserId',title:'创建人',align:'center',width:'6%'},
	              {field:'crtateTime',title:'创建时间',align:'center',width:'8%'},
	              {field:'orderId',title:'排序编号',align:'center',width:'8%'},
	              {field:'isDisable',title:'是否禁用',align:'center',width:'8%'},
	              {field:'_operate',title:'操作',align:'center',width:'6%',formatter:formatOper}
	          ]]		
	});
}
//增加修改列
function formatOper(val,row,index){  
    return "<a href='#' style='color:blue' onclick='edit(\"" + index+ "\");'>[修改]</a>";
}
function edit(index){
	 $('#datagrid').datagrid('selectRow',index);
	    var row = $('#datagrid').datagrid('getRows')[index];  
	    if (row){  
	      window.location.href = ctx+'/sys/hpset/SettingAddressForm?id='+row.id;
	    }
}

function initToolbar() {
	var toolbar = [{
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			window.location.href=ctx+'/sys/hpset/SettingAddressForm?appId='+appId;
		}
	},'-',{
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
		var rows = $('#datagrid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("系统提示", "请至少选择一行数据!");
			return false;
		}
		var ids = "";
		$(rows).each(function (i,o){
			ids += o.id+",";
		});
		$.messager.confirm('系统提示','删除后不可恢复,是否删除?',function(r){
			    if (r){
			    	$.ajax({ 
						url:ctx+'/sys/hpset/deleteHpSetById?id='+ids,
						type:'POST',
						dataType:'json',
						//data:{id:ids},
						success : function (data){
							$('#datagrid').datagrid('reload');
							searchFunc("");
							$.messager.alert('温馨提示',"删除成功！");
						},error : function(data){
							$.messager.alert('系统提示',"删除失败！");
						}
					});
			    }
			});
		}
	}];
	return toolbar;
}

//点击查找按钮出发事件
function searchFunc(idList) {
	var data = $("#searchForm").serializeObject();
	data.applyRoleName = $("#applyRoleDic").val();
	data.id = idList;
	$("#datagrid").datagrid('load',data);   
}

//清除查询条件
function ClearQuery() {
	$("#trs").form('clear');
}

//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  ,flag 是否加载按钮组并自处理返回结果,fun 关闭回调事件
var win;
function EasyextDialog(url, title, width, height, shadow,flag,fun) {  
	var ids = Math.round(Math.random()*1000);
    var content = '<iframe id="dialog'+ids+'" name="dialog'+ids+'" src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="'+ids+'" title="' + title + '"></div>';//style="overflow:hidden;"可以去掉滚动条  
    $(document.body).append(boarddiv); 
    if(flag){
    	win = $('#'+ids).dialog({  
    		content: content,  
    		width: width,  
    		height: height,  
    		modal: shadow,  
    		title: title,  
    		onClose: function () {  
    			//关闭后事件
    			if(fun){
    				fun();
    			};
    		}
    	});  
    }
    if(flag){
    	$("#"+ids).next().css("text-align","center");
    }
    win.dialog('open');  
}