var _crtype = "";
var _prtype = "";
var _valRoleField = "";
var is_CheckRole = true;
var allRoleData = [];
var indexRoleSetting = {};
/*上级机构下拉列表初始化
 * param: ctype 树结构子键，ptype树结构父键，valueField需要传递的字段，url数据请求url，父节点是否可选
 */
function initRoleList(ctype,ptype,valueField,url,isCheck) {
	_crtype = ctype;
	_prtype = ptype;
	_valRoleField = valueField;
	is_CheckRole = isCheck;
	var setting = {
			view : {
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : _crtype,
					pIdKey : _prtype,
					rootPId : 0
				}
			},
			callback : {
				onClick : onRoleClick
			}
	};
	indexRoleSetting = setting;
	$.ajax({
		type : "post",
		url : url,
		dataType : 'json',
		cache : false,
		success : function(treeData) {
			allRoleData = treeData;
			if ($("#rid").val() != "") {
				// 上级机构显示名称
				for ( var i = 0, l = treeData.length; i < l; i++) {
					if (treeData[i][_valRoleField] == $("#rid").val()) {
						$("#roleSel").val(treeData[i].name);
						break;
					}
				}
			}
			$.fn.zTree.init($("#treeRole"), setting, treeData);
			// /根据文本框的关键词输入情况自动匹配树内节点 进行模糊查找
			$("#roleSel").keyup(function() {
				var s = $("#roleSel").val();
				if (s.length > 0) {
					var newNodes = [];
					for ( var i = 0, l = treeData.length; i < l; i++) {
						if (treeData[i].name.indexOf(s) >= 0) {
							if(is_CheckRole==false && treeData[i][_prtype]==null){
								continue;
							}
							newNodes.push(treeData[i]);
						}
					}
					// 将找到的nodelist节点更新至Ztree内
					$.fn.zTree.init($("#treeRole"), setting, newNodes);
					showRoleMenu();
				} else {
					$("#rid").val("");
					$.fn.zTree.init($("#treeRole"), setting, treeData);
				}
			});
			$("#roleSel").click(function() {
				$("#showRole").click();
				$("#roleSel").keyup();
			});
		}
	});
}

// 点击某个节点 然后将该节点的名称赋值值文本框
function onRoleClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeRole");
	// 获得选中的节点
	var nodes = zTree.getSelectedNodes(), v = "", t = "";
	// 根据id排序
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for ( var i = 0, l = nodes.length; i < l; i++) {
		if(is_CheckRole==false && nodes[i].children!=null){
			return false;
		}
		v += nodes[i][_valRoleField];
		t += nodes[i].name;
	}
	// 将选中节点的名称显示在文本框内
	var cityObj = $("#roleSel");
	cityObj.val(t);
	$("#rid").val(v);
	// 隐藏zTree
	hideRoleMenu();
	return false;
} 

// 显示树
function showRoleMenu() {
	$("body").unbind("mousedown", onBodyDownRole);
	var cityObj = $("#roleSel");
	var cityOffset = $("#roleSel").offset();
	$("#menuRoleContent").css({
		left : cityOffset.left + "px",
		top : cityOffset.top + cityObj.outerHeight() + "px"
	}).slideDown("fast");
	$("body").bind("mousedown", onBodyDownRole);
}
       
 // 隐藏树
function hideRoleMenu() {
	$("#menuRoleContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownRole);
}

function onBodyDownRole(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "menuRoleContent" || $(
			event.target).parents("#menuRoleContent").length > 0)) {
		var flog = true;
		for ( var i = 0, l = allRoleData.length; i < l; i++) {
			if(allRoleData[i].name == $("#roleSel").val()){
				flog = false;
				if(allRoleData[i][_valRoleField] != $("#rid").val()){
					$("#roleSel").val(allRoleData[i].name);
					$("#rid").val(allRoleData[i][_valRoleField]);
				}
			}
		}
		if(flog){
			$("#roleSel").val("");
			$("#rid").val("");
		}
		hideRoleMenu();
	}
}

