$(function (){
	initDataGrid();  //加载角色表格.
	initResTree();  //加载浏览权限数据
	initRank();  //初始化浏览等级
});

function initDataGrid(appId){
	$.ajax({
		url:'../permission/getRolesByRoleKing?appId=-1',
		dataType:'text',
		type:'POST',
		async:false,
		success:function (data){
			$("#roletabs").html(data);
		}
	});
}
var ztree,setting,zNodes;
function initResTree(){
	setting = {
			check: {
				enable: true,
				chkboxType: { "Y": "ps", "N": "ps" }  //勾选关联父节点
			},
			edit: {  //禁止点击跳转
				enable : true,
				drag : {  //禁止拖拽
					isMove : false,
					isCopy : false
				},
				showRemoveBtn: function (treeId, treeNode){
					if(treeNode.id == undefined){
						return false;
					}
					return true;
				},
				showRenameBtn: false
			},
			callback: {
				callbackFlag :true,
				onClick: function (event, treeId, treeNode){
					if(treeNode.url != undefined){
						var rank = treeNode.url.split("_");
						$($("input[value='"+rank[1]+"']").next()).prev().prop("checked", "checked");
						rdochecked('rdolist');
					}else{  //还未进行浏览权限控制的接口
						$("#rank").html("");
						initRank();
					}
				},
				beforeCheck: function (treeId, treeNode){  //不允许手动选中.
					return false;
				},
				beforeRemove: function (treeId, treeNode){  //删除接口浏览权限数据
					$.ajax({
						url:'deleteSkimthroughDataById',
						dataType:'json',
						type:'POST',
						data:{id:treeNode.id},
						success:function (data){
							if(!data.success){
								$.messager.alert('提示',data.msg);
							}
							//重新加载显示数据状态.
							treeNode.id = ""; //清空ID防止后台误认为是修改.
							setting.async.otherParam = ["rolecode",rolecode];
							ztree = $.fn.zTree.init($("#mztree"), setting, zNodes);
							$("#rank").html("");
							initRank();
						}
					});
					return false;
				}
			},
			async: {
				enable: true,
				url: "getskimthroughPageData"
			}
	};
	zNodes =[];
	ztree = $.fn.zTree.init($("#mztree"), setting, zNodes);
}

function initRank(){
	var data = [{name:"地市级",value:1},{name:"区县级",value:2},{name:"机构级",value:3}];
	var rank = "";
	$(data).each(function (i,o){
		if(false){
			rank += '<input type="radio" name="rank" class="rdolist" checked="checked"  value="'+o.value+'"> '+
			'<label class="rdobox checked">'+
			'<span class="check-image" style="background: url(../../static/jdone/style/sys/imgs/index/input-checked.png);"></span>'+
			'<span class="radiobox-content">'+o.name+'</span>'+   
			'</label>';
		}else{
			rank += '<input type="radio" name="rank" class="rdolist"  value="'+o.value+'"> '+
			'<label class="rdobox unchecked">'+
			'<span class="check-image" style="background: url(../../static/jdone/style/sys/imgs/index/input-unchecked.png);"></span>'+
			'<span class="radiobox-content">'+o.name+'</span>'+   
			'</label>';
		}
	});
	$("#rank").html(rank);
	$(".rdobox").click(function () {
		$(this).prev().prop("checked", "checked");
		rdochecked('rdolist');
	});
}

function rdochecked(tag) {
    $('.' + tag).each(function (i) {
        var rdobox = $('.' + tag).eq(i).next();
        if ($('.' + tag).eq(i).prop("checked") == false) {
            rdobox.removeClass("checked");
            rdobox.addClass("unchecked");
            rdobox.find(".check-image").css("background", "url(../../static/jdone/style/sys/imgs/index/input-unchecked.png)");
        }
        else {
            rdobox.removeClass("unchecked");
            rdobox.addClass("checked");
            rdobox.find(".check-image").css("background", "url(../../static/jdone/style/sys/imgs/index/input-checked.png)");
        }
    });
}
//切换显示，数据.
function Switchdisplay(obj){
	if($(obj).hasClass("datagrid-row-collapse")){
		$(obj).removeClass("datagrid-row-collapse");
		$(obj).addClass("datagrid-row-expand");
	}else if($(obj).hasClass("datagrid-row-expand")){
		$(obj).removeClass("datagrid-row-expand");
		$(obj).addClass("datagrid-row-collapse");
	}
	$(obj).parent().next().toggle();
}
var rolecode = "";
function clickInitPermissionTreeByRole(obj,appId){
	$($("tr")).each(function (i,o){
		if($(o).hasClass("cur")){
			$(o).css("background-color","");
		}
	});
	$(obj).css("background-color","#eaf2ff");

	$($(obj).find("td div")).each(function (i,o){
		if($(o).attr("id") == "code"){
			rolecode = $(o).attr("value");
		}
	});
	setting.async.otherParam = ["rolecode",rolecode];
	ztree = $.fn.zTree.init($("#mztree"), setting, zNodes);
}

function Submit(){
	if(rolecode == ""){
		$.messager.alert('提示',"请选择角色进行接口授权!");
		return false;
	}
	var cnodes = ztree.getSelectedNodes(); // 查找节点集合
	if(cnodes.length == 0){
		$.messager.alert('提示',"请选择接口进行等级授权!");
		return false;
	}
	var radioRank = $("input[name='rank']:checked").val();
	if(radioRank == undefined){
		$.messager.alert('提示',"请选择等级授权!");
		return false;
	}
	
	$.ajax({
		url:'saveSkimthrough',
		dataType:'json',
		type:'POST',
		data:{rolecode:rolecode,cnodes:JSON.stringify(cnodes),radioRank:radioRank},
		success:function (data){
			$.messager.alert("提示",data.msg);
			setting.async.otherParam = ["rolecode",rolecode];
			ztree = $.fn.zTree.init($("#mztree"), setting, zNodes);
		}
	});
}