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
			//树结构点击查询节点及下节点数据
			var idList = "";
			idList = showTreeData(treeNode, idList);
			if(idList == ""){
				idList = treeNode.id;
			}
			searchFunc(idList);
			searchs(idList);
		}
	}
};

function showTreeData(treeNode, idList){
	 if (treeNode.isParent) {
		var childrenList = treeNode.children;
		if (childrenList != null && childrenList.length != 0) {
			idList += treeNode.id + ",";
			for (index in childrenList) {
				idList = showTreeData(childrenList[index], idList);
			};
		}
	}
	return idList;
}
var ids;
$(document).ready(function(){
	//数据类型(0:全省,1:本市,2:本县)
	if(otherField == "1"){
		regCode = regCode.substring(0, 4);
	}else if(otherField == "2"){
		regCode = regCode.substring(0, 6);
	}else{
		regCode ="";
	}
	refreshTree(regCode);
	if(getUrlParam("hidden") !=null && getUrlParam("hidden").toString().length>1){
		$(".layout").hide();
		$("#bottoms").hide();
		$("#dd").show();
		refreshOrgTree(regCode);
	}else{
		$(".layout").show();
		$("#bottoms").show();
		$("#dd").hide();
		if(parent.$("textarea[name='orgs']").val() !=undefined && parent.$("textarea[name='orgsNames']").val()!=undefined ){
		//处理父窗口的选择值
		ids =parent.$("textarea[name='orgs']").val().split(",");//得到父窗口的值
		var names =parent.$("textarea[name='orgsNames']").val().split(",");//得到父窗口的值
		for(var i = 0; i < ids.length; i ++){
			if(names[i] != undefined && names[i] != "undefined" && names[i] != null && names[i] !=""){
						var data = {
							sysCode : ids[i],
							name : names[i]
						};
						add(data);
					}
				}
		}
		}

		//绑定回车事件
		$('.keydownSearch').next().bind('keydown', function(e){
		    if (e.keyCode == 13) {
		    	$("#keydownSearch").click();
		    }
		});

});
//刷新树
function refreshOrgTree(obj){
	initDataDivSec();
	$('#datagrid').datagrid('loading');
	$.ajax({
		type: "post",
		url: ctx+"/system/treeData",
		dataType: 'json',
		data:{sysCode:obj},
		cache: false,
		success: function(data) {
			$.fn.zTree.init($("#ztrees"), setting, data);
		}
	});
   }
//点击查找按钮促发事件
function searchs(idList) {
	var data = $("#searchForms").serializeObject();
	data.id = idList;
	$("#divSec").datagrid('load',data);
}
//清除查询条件
function clears() {
	$("#searchForms").form('clear');
}
function initDataGrid(url){
	$("#datagrid").datagrid({
		url: url,
		width:'100%',
		height:'88%',
		pagination:true,
		rownumbers: true,
		onSelect:onSelectRow,	//在用户选择一行的时候触发
		onUncheck:onUnchecks, //取消选择一行。
		onCheck :onCheckRow,
		queryParams:{sysCode:regCode},
		singleSelect:isSingle,//如果为true，则只允许选择一行。
		onSelectAll:onCheckAllRow,//在用户选择所有行的时候触发
		onUnselectAll:onUnCheckAllRow,//在用户取消选择所有行的时候触发
		checkOnSelect:true,
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		onLoadSuccess:function(data){
			var leng = $(".table-grid tr").length;
			 for(var i=1; i<leng; i++)  {
				var index = $(".table-grid tr").eq(i).find("td").find("input").val();
				index = index.split("#");
				var val = index[0];
				$(data.rows).each(function(t,o){
					if(o.sysCode == val){
						var table = $("table[class='datagrid-btable']")[1];
						$($(table).find("tr")).each(function (q,r){
							if($(r).attr('datagrid-row-index') == t){
								$(r).find("input").attr("checked",true);
								$(r).attr('class','datagrid-row datagrid-row-checked datagrid-row-selected');
							}
						});
						return;
					}
				});
			  }
			/*//i - 选择器的 i 位置, o - 当前的元素（也可使用 "this" 选择器）
			$(data.rows).each(function(i,o){
				$(ids).each(function (j,k){
					//循环遍历所有的数据
					if(o.sysCode == k){
						//如果table中每行的code与文本框中的code相等
						//var selRows = $(o).is(':checked'); 			//选中文本框中的复选款
						var table = $("table[class='datagrid-btable']")[1];
						$($(table).find("tr")).each(function (q,r){
							if($(r).attr('datagrid-row-index') == i){
								$(r).find("input").attr("checked",true);
							}
						});
	                	return;
					}
				});
			});*/
	    },
	    columns:[[
	              {field:'id',align:'center',checkbox :true},
	              {field:'name', title:'名称',align:'center',width:'43%'},
	              {field:'code',title:'代码',align:'center',width:'28%'},
	              {field:'sysCode',title:'系统编号',align:'center',width:'27%'}
	          ]]
	});


}

function initDataDivSec(){
	$("#divSec").datagrid({
		url: ctx + "/system/findPageList",
		width:'99%',
		height:'90%',
		pagination:true,
		rownumbers: true,
		queryParams:{sysCode:regCode},
		onUncheck:onUnchecks, //取消选择一行。
		checkOnSelect : true, // 复选框标识
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'名称',align:'center',width:'33%'},
	              {field:'code',title:'代码',align:'center',width:'33%'},
	              {field:'sysCode',title:'系统编号',align:'center',width:'32%'}
	          ]]
	});

}

//刷新树
function refreshTree(obj){
	initDataGrid("");
	$('#datagrid').datagrid('loading');
	$.ajax({
		type: "post",
		url: ctx+"/system/treeData",
		dataType: 'json',
		cache: false,
		data:{sysCode:obj},
		success: function(data) {
			$.fn.zTree.init($("#ztree"), setting, data);
			initDataGrid(ctx + "/system/findPageList");
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


function selectMulti(obj) {
	var data = {};
	if(idField == "id"){
		data = {id: obj.id, name:obj.name};
	}else if(idField == "sysCode"){
		data = {sysCode: obj.sysCode, name:obj.name};
	}
	if(otherField != ''){
		data.other = obj[otherField];
	}
	else{
		data.other = '';
	}
	add(data);
};


//将父窗口数据插入到右边区域中

function add(data) {
	if(data.sysCode ==undefined) return;
	var len = $("#org_" + data.sysCode).length;
	if (len > 0) return;
	if(isSingle)$("#orgList").empty();
	var aryData = '<tr id="org_' + data.sysCode + '" >'+
	       		'<td>'+
	       		'<input type="hidden" class="pk" name="org" value="' + data.sysCode + "#" + data.name + '"><span> '+
	       		data.name+
	       		'</span></td>'+
	       		'<td><a onclick="javascript:del(this);"><img src="'+ctx+'/static/framework/plugins/easyui-1.5.1/themes/icons/clear.png"></a> </td>'+
	       		'</tr>';
	$("#orgList").append(aryData);
};

//选择行事件
function onSelectRow(row,index){
	if(!isSingle){
		selectMulti(index);
	}
}
//取消行选中事件
function onUnchecks(index,row){
	var val =null;
	var leng = $(".table-grid tr").length;
	for(var i= 1; i<leng; i++)  {
		var values = $(".table-grid tr").eq(i).find("td").find("input").val();
		if(values != undefined){
		values = values.split("#");
		val = values[0];
		}
		if(row.sysCode == val){
			$(".table-grid tr").eq(i).find("td").find("a").closest("tr").remove();
		}
	}
}
//行选择事件
var checked =true;
function onCheckRow(index,row){
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
	$("#orgList").html('');
}
/**
 * 单行清空
 * @param obj
 */

function del(obj) {
	var rows = $('#datagrid').datagrid("getRows");
	var indexs = $(obj).parent().prev().find("input").val();//如果直接获取input的class只能顺序删除
	indexs = indexs.split("#");
	var vals = indexs[0];
	$(rows).each(function(i,o){
		if(o.sysCode == vals){
			var tables = $("table[class='datagrid-btable']")[1];
			$($(tables).find("tr")).each(function (q,r){
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

//清空所有事件
function dellAll() {
	$("#orgList").empty();

	var rows = $('#datagrid').datagrid("getRows");
	var leng = $(".table-grid tr").length;
	if (leng == 0) {
		$(rows).each(function(i,o) {
			var tables = $("table[class='datagrid-btable']")[1];
			$($(tables).find("tr")).each(function(q, r) {
				if ($(r).attr('datagrid-row-index') == i) {
					$(r).find("input").attr("checked", false);
					$(r).attr('class','datagrid-row');
				}
			});
		});
	}
};

function selectOrg() {
	var pleaseSelect = "请选择要接收对象";
	var aryOrg = $("input[name='org']", $("#orgList"));
	if(aryOrg.length == 0) {
		$.messager.confirm("温馨提示",pleaseSelect);
		return;
	}
	var aryId = new Array();
	var aryName = new Array();
	var aryOther = new Array();
	aryOrg.each(function() {
		var data = $(this).val();
		var aryTmp = data.split("#");
		aryId.push(aryTmp[0]);
		aryName.push(aryTmp[1]);
		aryOther.push(aryTmp[2]);
	});
	var obj = {ids: aryId.join(","), names: aryName.join(","), others: aryOther.join(",")};
	//子窗口向父窗口传值
	window.parent.$("#orgs").val(obj.ids);
	window.parent.$("#orgsNames").val(obj.names);
	parent.win.dialog('close');
}

//采用正则表达式获取地址栏参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null)return unescape(r[2]);
    return null; //返回参数值
}
