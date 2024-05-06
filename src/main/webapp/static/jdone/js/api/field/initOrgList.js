var _ctype = "";
var _ptype = "";
var _valField = "";
var is_Check = true;
var allData = [];
var indexSetting = {};
/*上级机构下拉列表初始化
 * param: ctype 树结构子键，ptype树结构父键，valueField需要传递的字段，url数据请求url，父节点是否可选
 */
function initOrgList(ctype,ptype,valueField,url,isCheck) {
	_ctype = ctype;
	_ptype = ptype;
	_valField = valueField;
	is_Check = isCheck;
	var setting = {
			view : {
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : _ctype,
					pIdKey : _ptype,
					rootPId : 0
				}
			},
			callback : {
				onClick : onClick
			}
	};
	indexSetting = setting;
	$.ajax({
		type : "post",
		url : url,
		dataType : 'json',
		cache : false,
		success : function(treeData) {
			allData = treeData;
			if ($("#pid").val() != "") {
				// 上级机构显示名称
				for ( var i = 0, l = treeData.length; i < l; i++) {
					if (treeData[i][_valField] == $("#pid").val()) {
						$("#orgSel").val(treeData[i].name);
						break;
					}
				}
			}
			$.fn.zTree.init($("#treeDemo"), setting, treeData);
			// /根据文本框的关键词输入情况自动匹配树内节点 进行模糊查找
			$("#orgSel").keyup(function() {
				var s = $("#orgSel").val();
				if (s.length > 0) {
					var newNodes = [];
					for ( var i = 0, l = treeData.length; i < l; i++) {
						if (treeData[i].name.indexOf(s) >= 0) {
							if(is_Check==false && treeData[i][_ptype]==null){
								continue;
							}
							newNodes.push(treeData[i]);
						}
					}
					// 将找到的nodelist节点更新至Ztree内
					$.fn.zTree.init($("#treeDemo"), setting, newNodes);
					showMenu();
				} else {
					$("#pid").val("");
					$.fn.zTree.init($("#treeDemo"), setting, treeData);
				}
			});
			$("#orgSel").click(function() {
				$("#showOrg").click();
				$("#orgSel").keyup();
			});
		}
	});
}

// 点击某个节点 然后将该节点的名称赋值值文本框
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	// 获得选中的节点
	var nodes = zTree.getSelectedNodes(), v = "", t = "", sysCode = "";
	// 根据id排序
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for ( var i = 0, l = nodes.length; i < l; i++) {
		if(is_Check==false && nodes[i].children!=null){
			return false;
		}
		v += nodes[i][_valField];
		t += nodes[i].name;
		sysCode += nodes[i].sysCode;
	}
	// 将选中节点的名称显示在文本框内
	var cityObj = $("#orgSel");
	cityObj.val(t);
	$("#pid").val(v);
	$("#sysCode").val(sysCode);
	// 隐藏zTree
	hideMenu();
	return false;
} 

// 显示树
function showMenu() {
	$("body").unbind("mousedown", onBodyDown);
	var cityObj = $("#orgSel");
	var cityOffset = $("#orgSel").offset();
	$("#menuContent").css({
		left : cityOffset.left + "px",
		top : cityOffset.top + cityObj.outerHeight() + "px"
	}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
       
 // 隐藏树
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "orgSel" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		var flog = true;
		for ( var i = 0, l = allData.length; i < l; i++) {
			if(allData[i].name == $("#orgSel").val()){
				flog = false;
				if(allData[i][_valField] != $("#pid").val()){
					$("#orgSel").val(allData[i].name);
					$("#pid").val(allData[i][_valField]);
					$("#sysCode").val(allData[i].sysCode);
				}
			}
		}
		if(flog){
			$("#orgSel").val("");
			$("#pid").val("");
			$("#sysCode").val("");
		}
		hideMenu();
	}
}

