var setting = {
	data : {
		simpleData : {
			enable : true,// 设置 zTree是否开启异步加载模式  加载全部信息
			idKey : "id",//id编号命名 默认
			pIdKey : "roleKind" //	id编号命名 默认
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			//树结构点击查询节点及下节点数据
			if(treeNode.children != null && treeNode.children.length != 0){
				searchFunc("roleKind", treeNode.id);
			}else{
				searchFunc("id", treeNode.id);
			}
		}
	}
};

$(document).ready(function(){
	refreshTree();

	//绑定回车事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
	if(parent.$("textarea[name='roles']").val()!=undefined && parent.$("textarea[name='rolesNames']").val()!=undefined){
	 //处理父窗口的选择值
	var ids =parent.$("textarea[name='roles']").val().split(",");//得到父窗口的值
	var names =parent.$("textarea[name='rolesNames']").val().split(",");//得到父窗口的值
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
});

function initDataGrid(){
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
	var datas = treeObj.getNodes();
	$("#datagrid").datagrid({
		url:ctx + "/system/findPageListData",
		width:'100%',
		height:'88%',
		onUncheck:onUnchecks, //取消选择一行。
		pagination:true,
		rownumbers: true,
		onSelect:onSelectRow,	//在用户选择一行的时候触发
		onCheck :checkRow,
		singleSelect:isSingle,//如果为true，则只允许选择一行。
		onSelectAll:onCheckAllRow,//在用户选择所有行的时候触发
		onUnselectAll:onUnCheckAllRow,//在用户取消选择所有行的时候触发
		checkOnSelect:true,
		emptyMsg: '<span>无记录</span>',
		onLoadSuccess:function(data){
			var trs = $(".table-grid tr").length;
			for(var i=1;i<trs;i++){
				var index = $(".table-grid tr").eq(i).find("td").find("input").val();
				index = index.split("#");
				var val = index[0];
				$(data.rows).each(function(n,o){
					if(o.code == val){
						var table = $("table[class='datagrid-btable']")[1];
						$($(table).find("tr")).each(function(m,k){
							if($(k).attr("datagrid-row-index") == n){
								$(k).find("input").attr("checked",true);
								$(k).attr('class','datagrid-row datagrid-row-checked datagrid-row-selected');
							}
						});
						return;
					}
				});
			}
	    },
	    columns:[[
	              {field:'id',align:'center',checkbox:true
	            	  },{field:'name', title:'名称',align:'center',width:'33%'
	            	  },{field:'code',title:'编号',align:'center',width:'33%'
	            	  },{field:'roleKind',title:'角色分类',align:'center',width:'32%',formatter: function(value,row,index){
	            	  for ( var i = 0; i < datas.length; i++) {
	            		  if(datas[i].id == value){
	            			  return datas[i].name;
	            		  }
	            	  }
	              }},
	          ]]
		});
 	}

	//刷新树
	function refreshTree(){
		$.getJSON(ctx+"/system/treeDataRole",function(data){
			$.fn.zTree.init($("#ztree"), setting, data);
			initDataGrid();
		});
	}

	//点击查找按钮出发事件
	function searchFunc(field, idList) {
		var data = $("#searchForm").serializeObject();
		data[field] = idList;
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

	//删除所有角色
	function dellAll() {
		$("#sysRoleList").empty();

		var rows = $('#datagrid').datagrid("getRows");
		var len = $(".table-grid tr").length;
		if(len == 0){
			$(rows).each(function(i){
				var table = $(".datagrid-btable")[1];
				$(table).find("tr").each(function(w,e){
				if($(e).attr("datagrid-row-index") == i){
					$(e).find("input").attr("checked",false);
					$(e).attr('class','datagrid-row');
				}
				});
			});
		}
	};

	//删除指定的角色
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
		$(obj).parents("tr").remove();
	};

	//角色列表选择事件
	function selectMulti(obj) {
		var data = {};
		if(idField == "id"){
			data = {id: obj.id, name:obj.name};
		}else if(idField == "code"){
			data = {code: obj.code, name:obj.name};
		}
		add(data);
	};

	//添加数据
	function add(data) {
		if(data.code ==undefined) return;
		var len = $("#role_" + data.code).length;
		if(len > 0) return;
		if(isSingle)$("#sysRoleList").empty();
		var aryData = '<tr id="role_' + data.code + '">'+
			'<td>'+
			'<input type="hidden" class="pk" name="roleData" value="' + data.code + "#" + data.name + '"><span> '+
			data.name+
			'</span></td>'+
			'<td><a onclick="javascript:del(this);"><img src="'+ctx+'/static/framework/plugins/easyui-1.5.1/themes/icons/clear.png"></a> </td>'+
			'</tr>';
		$("#sysRoleList").append(aryData);
	};

	//选择行事件(点击行时触发)
	function onSelectRow(row,index){
		if(!isSingle){
			selectMulti(index);
		}
	}
	//取消行选中事件
	function onUnchecks(index,row){
		var val=null;
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
	//行选择事件(如果为true,当用户点击行的时候该复选框就会被选中或取消选中)
	var checked =true;
	function checkRow(index,row){
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
		$("#sysRoleList").html('');
	}
	//选择角色
	function selectRole(){
		var pleaseSelect = "请选择要接收对象";

		var chIds = $("input[name='roleData']", $("#sysRoleList"));
		if(chIds.length == 0) {
			$.messager.confirm("温馨提示",pleaseSelect);
			return;
		}

		var aryroleIds = new Array();
		var arynames = new Array();

		$.each(chIds, function(i, ch){
			var aryTmp = $(ch).val().split("#");
			aryroleIds.push(aryTmp[0]);
			arynames.push(aryTmp[1]);
		});

		var obj = {ids: aryroleIds.join(","), names: arynames.join(",")};
		window.parent.$("#roles").val(obj.ids);
		window.parent.$("#rolesNames").val(obj.names);
		parent.win.dialog('close');
	}
