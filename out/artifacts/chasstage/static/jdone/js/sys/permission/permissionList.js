var menuTreeData = null;
var operTreeData = null;
var dataRangeTreeData = null;
var nowRoleObj = null;
var resType = "menu";
var nowTreeId = "menuZtree";
var nowTreeData = null;
var nowRangeLevel = null;
var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "ps", "N": "ps" }  //勾选关联父节点
		},
		edit: {  //禁止点击跳转
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false,
			drag : {  //禁止拖拽
				isMove : false,
				isCopy : false
			}
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid"
			}
		}
	};
$(function (){
	$.ajaxSetup({cache:false});
	//初始化授权对象列表
	initDataGrid();
	//初始化选项卡选中事件
	$('#perTabs').tabs({
		onSelect:function(title,index){
			tabSelectFun(index);
		}
	});
	//初始化浏览权限级别
	initRank();
});

//选项卡选中事件
function tabSelectFun(index){
	if(index == 0){
		resType = "menu";
		nowTreeId = "menuZtree";
		setting.data.simpleData.pIdKey = "pid";
		nowTreeData = menuTreeData;
	}else if(index == 1){
		resType = "oper";
		nowTreeId = "customZtree";
		setting.data.simpleData.pIdKey = "groupId";
		nowTreeData = operTreeData;
	}else if(index == 2){
		resType = "datarange";
		nowTreeId = "viewZtree";
		setting.data.simpleData.pIdKey = "groupId";
		nowTreeData = dataRangeTreeData;
	}
	if(nowRoleObj == null){
		return false;
	}
}


function initDataGrid(){
	$("#datagrid").datagrid({
		url:ctx + "/sys/permission/findAuthObjList",
		width:'97%',
		rownumbers: true,
		checkOnSelect:true,
		singleSelect:true,
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'name', title:'角色名',align:'center',width:'60%'},
	              {field:'code',title:'角色代码',align:'center',width:'40%'}
	          ]],
	    groupField:'level',
	  	view: groupview,
	  	groupFormatter:function(value, rows){
	  		var result = "";
	  		for ( var int = 0; int < rows.length; int++) {
				if(rows[int].level == value){
					result = rows[int].levelName;
				}
			}
	  		var str = "<b style='font-size: 18px;font-weight: bold;color: black;font-family: cursive;font-style: normal;'>" + result + "</b>";
	  		return str;
	  	},
	  	onSelect: function(rowIndex, rowData){
	  		var tabIndex = $('#perTabs').tabs('getTabIndex', $('#perTabs').tabs('getSelected'));
	  		$("#rank").show();
	  		nowRoleObj = rowData;
	  		for ( var j = 0; j < 3; j++) {
	  			tabSelectFun(j);
	  			if(j == 2){
	  				$(".rdobox")[4].click();
	  			}else{
	  				initAuthTreeData(resType, rowData.code, nowTreeId);
	  			}
			}
	  		tabSelectFun(tabIndex);
		}
	});
	//获取菜单树数据
	$.getJSON(ctx+"/sys/permission/getAllMenuTreeData",function(data){
		menuTreeData = data;
		nowTreeData = menuTreeData;
	});
	//获取自定义操作树数据
	$.getJSON(ctx+"/sys/permission/getAllOperTreeData",function(data){
		operTreeData = data;
	});
	//获取浏览权限树数据
	$.getJSON(ctx+"/sys/permission/getAllDataRangeTreeData",function(data){
		dataRangeTreeData = data;
	});
}

//初始化菜单树数据
function initAuthTreeData(resType, roleCode, treeId){
	$.fn.zTree.init($("#" + treeId), setting, nowTreeData);
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	if(resType == "menu"){
		ajaxLoading();
	}
	$.ajax({
		type : "post",
		url : ctx + "/sys/permission/getAuthTreeData?roleCode=" + roleCode + "&resType=" + resType,
		dataType : 'json',
		cache : false,
		success : function(datas) {
			for(var i = 0;i<datas.length;i++){
				var getNode = treeObj.getNodesByParam("id", datas[i].id,null);
				if(getNode.length >0 && getNode[0].isParent == true){
					continue;
				}
				if(resType == "datarange"){
					if(datas[i].defaultRange == nowRangeLevel){
						treeObj.checkNode(getNode[0],true, true);
					}
				}else{
					treeObj.checkNode(getNode[0],true, true);
				}
			}
			if(resType == "menu"){
				ajaxLoadEnd();
			}
		}
	});
}

//保存权限数据
function savePermissionList(){
	if(nowRoleObj == null){
		$.messager.alert('系统提示','请先选择要授权对象！','info');
		return false;
	}
	//封装权限数据
	var dataListJson = getDataListJson();
	var rangeLevel = "";
	if(resType == "datarange"){
		rangeLevel = nowRangeLevel;
	}
	$.ajax({
		type: "post",
		url: ctx+"/sys/permission/savePermission?resType=" + resType + "&roleCode=" + nowRoleObj.code + "&rangeLevel=" + rangeLevel,
		dataType: 'json',
		data: {dataStr: dataListJson},
		cache: false,
		success: function(data) {
			$.messager.alert('系统提示',data.msg,'info');
		}
	});
}

//封装权限数据
function getDataListJson(){
	var treeObj = $.fn.zTree.getZTreeObj(nowTreeId);
	var nodes=treeObj.getCheckedNodes(true);
	var dataList = new Array();
	for ( var int = 0; int < nodes.length; int++) {
		var dataItem = {};
		dataItem.resType = resType;
		if(resType == "datarange"){
			dataItem.resMark = nodes[int].id + "-" + nowRangeLevel;
		}else{
			dataItem.resMark = nodes[int].id;
		}
		dataItem.resName = nodes[int].name;
		dataItem.roleCode = nowRoleObj.code;
		dataItem.roleName = nowRoleObj.name;
		dataList.push(dataItem);
	}
	return JSON.stringify(dataList);
}

//生成浏览权限级别
function initRank() {
	var data = [ {
		name : "省厅级",
		value : 10
	}, {
		name : "地市级",
		value : 20
	}, {
		name : "区县级",
		value : 30
	}, {
		name : "机构级",
		value : 40
	}, {
		name : "用户级",
		value : 50
	} ];
	var nowRoleLevel = parseInt($("#roleLevel").val());
	var rank = "";
	for ( var i = 0; i < data.length; i++) {
		if (data[i].value >= nowRoleLevel) {
			rank += '<input type="radio" name="rank" class="rdolist" value="'
					+ data[i].value + '"><label class="rdobox unchecked">'
					+ '<span class="check-image" style="background: url(../../static/jdone/style/sys/imgs/index/input-unchecked.png);"></span>'
					+ '<span class="radiobox-content">' + data[i].name + '</span>' + '</label>';
		}
	}
	$("#rank").html(rank);
	$(".radiobox-content").css("line-height", "2.5");
	$("#rank label").css("width", "60%");
	$(".rdobox").click(function () {
		$(this).prev().prop("checked", "checked");
		rdochecked('rdolist');
		var levelStr = $(".checked").prev().val();
		if(levelStr == "10"){
			nowRangeLevel = "province";
		}else if(levelStr == "20"){
			nowRangeLevel = "city";
		}else if(levelStr == "30"){
			nowRangeLevel = "reg";
		}else if(levelStr == "40"){
			nowRangeLevel = "org";
		}else if(levelStr == "50"){
			nowRangeLevel = "user";
		}
		initAuthTreeData(resType, nowRoleObj.code, nowTreeId);
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

function ajaxLoading() {
	$("<div class=\"datagrid-mask\"></div>").css({
		display : "block",
		width : "100%",
		height : $(window).height()
	}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo(
			"body").css({
		display : "block",
		left : ($(document.body).outerWidth(true) - 190) / 2,
		top : ($(window).height() - 45) / 2
	});
}

function ajaxLoadEnd() {
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}
