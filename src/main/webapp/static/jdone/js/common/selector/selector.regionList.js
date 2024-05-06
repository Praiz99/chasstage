
$(function() {
	if(getUrlParam("hidden") !=null && getUrlParam("hidden").toString().length>1){
		$("#bigDiv").css("display","none");
		$("#divSec").css("display","block");
		$("#datagridSec").datagrid({
			url : ctx + "/system/findPageDate",
			width : '100%',
			height:'88%',
			pagination : true, // 显示分页栏
			onUncheck:onUnchecks, //取消选择一行。
			rownumbers : true, // 显示每行列号
			fitColumns : true,
			checkOnSelect : true, // 复选框标识
			emptyMsg: '<span>无记录</span>',
			columns : [ [ {field : 'id',align : 'center',checkbox : true
			}, {field : 'name',title : '区域名称',align : 'center',width : '25%'
			}, {field : 'code',title : '区域代码',align : 'center',width : '25%'
			}, {field : 'dsdm',title : '所属地市',align : 'center',width : '24%'
			}, {field : 'sname',title : '区域简称',align : 'center',width : '25%'
			} ] ]
		});

	} else {
		initDataGrid();
		$("#bigDiv").css("display","block");
		$("#divSec").css("display","none");
		if(parent.$("textarea[name='regs']").val()!=undefined && parent.$("textarea[name='regsNames']").val()!=undefined){
		//处理父窗口的选择值
		var ids =parent.$("textarea[name='regs']").val().split(",");//得到父窗口的值
		var names =parent.$("textarea[name='regsNames']").val().split(",");//得到父窗口的值
		for(var i = 0; i < ids.length; i ++){
			if(names[i] != undefined && names[i] != "undefined" && names[i] != null && names[i] !=""){
				var data = {
						code: ids[i],
						name: names[i]
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

function initDataGrid() {
	$("#datagrid").datagrid({
		url : ctx + "/system/findPageDate",
		width : '100%',
		height:'88%',
		pagination : true, // 显示分页栏
		onUncheck:onUnchecks, //取消选择一行。
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		singleSelect:isSingle,//如果为true，则只允许选择一行。
		onSelect:onSelectRow,	//在用户选择一行的时候触发
		onCheck :checkRow,
		onSelectAll:onCheckAllRow,//在用户选择所有行的时候触发
		onUnselectAll:onUnCheckAllRow,//在用户取消选择所有行的时候触发
		emptyMsg: '<span>无记录</span>',
		onLoadSuccess:function(data){
			var leng = $(".table-grid tr").length;
			 for(var i=1; i<leng; i++)  {
				var index = $(".table-grid tr").eq(i).find("td").find("input").val();
				index = index.split("#");
				var val = index[0];
				$(data.rows).each(function(t,o){
					if(o.code == val){
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
	    },
		columns : [ [ {field : 'id',align : 'center',checkbox : true
		}, {field : 'name',title : '区域名称',align : 'center',width : '25%'
		}, {field : 'code',title : '区域代码',align : 'center',width : '25%'
		}, {field : 'dsdm',title : '所属地市',align : 'center',width : '24%'
		}, {field : 'sname',title : '区域简称',align : 'center',width : '24%'
		} ] ]
	});

}

//按区域名称查找
function searchForm(idList){
	var data = $("#searchForms").serializeObject();
	data.id = idList;
	$("#datagridSec").datagrid("load",data);//加载和显示数据
}

//清理所有数据
function Clear(){
	$("#searchForms").form('clear');
}
//按区域名称查找
function searchFunc(idList){
	var data = $("#searchForm").serializeObject();
	data.id = idList;
	$("#datagrid").datagrid("load",data);//加载和显示数据
}

//清理所有数据
function ClearQuery(){
	$("#searchForm").form('clear');
	}
/*
 表单数据传成json
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


//行选择事件(如果为true,当用户点击行的时候该复选框就会被选中或取消选中)
var checked =true;
function checkRow(index, row){
	if(checked){
		selectMulti(row);
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
		if(row.code == val){
			$(".table-grid tr").eq(i).find("td").find("a").closest("tr").remove();
		}
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
	$("#regList").html('');
}

//选择行事件
function onSelectRow(row,index){
	if(!isSingle){
		selectMulti(index);
	}
}

function selectMulti(obj) {
	var data = {};
	if(idField == "id"){
		data = {id: obj.id, name:obj.name};
	}else if(idField == "code"){
		data = {code: obj.code, name:obj.name};
	}

	if(otherField != ''){
		data.other = obj[otherField];
	}
	else{
		data.other = '';
	}

	add(data);
};

//删除所有选中事件
function dellAll() {
	$("#regList").empty();
	var rows = $('#datagrid').datagrid("getRows");
	var len = $(".table-grid tr").length;
	if (len == 0) {
		$(rows).each(function(i) {
			var table = $("table[class='datagrid-btable']")[1];
			$($(table).find("tr")).each(function(w, e) {
				if ($(e).attr("datagrid-row-index") == i) {
					$(e).find("input").attr("checked", false);
					$(e).attr('class','datagrid-row');
				}
			});
		});
	}
};

function add(data) {
	if(data.code == undefined) return;
	var len = $("#reg_" + data.code).length;
	if (len > 0) return;
	if(isSingle)$("#regList").empty();
	var aryData = '<tr id="reg_' + data.code + '">'+
	       		'<td>'+
	       		'<input type="hidden" class="pk" name="reg" value="' + data.code + "#" + data.name + '#' + data.other + '"> '+
	       		data.name+
	       		'</td>'+
	       		'<td><a onclick="javascript:del(this);"><img src="'+ctx+'/static/framework/plugins/easyui-1.5.1/themes/icons/clear.png"></a> </td>'+
	       		'</tr>';

	$("#regList").append(aryData);
};

/**
 * 单行清空
 * @param obj
 */
function del(obj) {
	var rows = $('#datagrid').datagrid("getRows");
	var index = $(obj).parent().prev().find("input").val();//如果直接获取input的class只能顺序删除
	index = index.split("#");
	var val = index[0];
	$(rows).each(function(i,o){
		if(o.code == val){
			var table = $("table[class='datagrid-btable']")[1];
			$($(table).find("tr")).each(function (q,r){
				if($(r).attr('datagrid-row-index') == i){
					$(r).find("input").attr("checked",false);
					$(r).attr('class','datagrid-row');
				}
			});
			return;
		}
	});
	$(obj).parent().parent().remove();
};

function selectReg() {
	var pleaseSelect = "请选择要接收对象";
	var aryReg = $("input[name='reg']", $("#regList"));

	if(aryReg.length == 0) {
		$.messager.confirm("温馨提示",pleaseSelect);
		return;
	}

	var aryId = new Array();
	var aryName = new Array();
	var aryOther = new Array();

	aryReg.each(function() {
		var data = $(this).val();
		var aryTmp = data.split("#");
		aryId.push(aryTmp[0]);
		aryName.push(aryTmp[1]);
		aryOther.push(aryTmp[2]);
	});

	var obj = {ids: aryId.join(","), names: aryName.join(","), others: aryOther.join(",")};
	//子窗口给父窗口传值
	window.parent.$("#regs").val(obj.ids);
	window.parent.$("#regsNames").val(obj.names);
	parent.win.dialog('close');
}


//采用正则表达式获取地址栏参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null)return unescape(r[2]);
    return null; //返回参数值
}


