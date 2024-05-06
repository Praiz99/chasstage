var ztree;
var setting = {
	check: {
		enable: true,
		chkboxType: { "Y": "s", "N": "s" }  //取消勾选关联父节点
	},
	data: {
		simpleData: {
			enable: true
		}
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
	callback: {
		onClick: clickTree,
		onNodeCreated: function (event, treeId, treeNode){
			if(treenode != null && treenode != undefined){
				if(treenode.id == treeNode.id){  //父节点展开
					ztree.selectNode(treeNode);  //选择当前操作的节点.
					ztree.expandNode(treeNode,true,true,false);  //并展开当前操作的节点.
				}else{  //子节点展开
					ztree.selectNode(treenode);  //选择当前操作的节点.
					ztree.expandNode(treenode,true,true,false);  //并展开当前操作的节点.
				}
			}
		}
	},
	async: {
		enable: true,
		type:'post',
		dataType:'text',
		url: "getParamTree"
	}
};
var zNodes =[];
$(function (){
	initPageAuto();
	initMenuData();
	if (!!window.ActiveXObject || "ActiveXObject" in window){
		var browser=navigator.appName; 
		var b_version=navigator.appVersion; 
		var version=b_version.split(";"); 
		var trim_Version=version[1].replace(/[ ]/g,""); 
		if(trim_Version=="MSIE7.0"){
			$("ul").css("margin-left","14px");
		}
	}
});

$(window).resize(function(){  
	initPageAuto(); 
});    
function initPageAuto(){ 
	$("#layout").css({"height":(window.screen.availHeight-210)+"px"});
	$("#p").css({"height":(window.screen.availHeight-210)+"px"});
	$("#layout").layout("resize",{  
        width:"100%",  
        height:(window.screen.availHeight-210)+"px"  
    }); 
}

function initMenuData(){
	appId = $('#apps option:selected').val();
	setting.async.otherParam = ["appId",appId];
	ztree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
}

function doSearch(){
	var menuName = $("#menuName").val();
	if(!menuName){
		setting.async.otherParam = ["appId",appId];
		ztree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
		return;
	}
	$("#menuTree").html("");
	setting.async.url = 'getParamTree';
	setting.async.otherParam = ["name",menuName,"appId",appId];
	ztree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
}
//修改菜单
var src = "";  //节点路径
var treenode;  //当前被点击的菜单
function clickTree(event, treeId, treeNode){
	treenode = treeNode;
	src = "";
	var srcarr = new Array();
	(function r(obj){
		if(obj == null) return;
		srcarr.push(obj.name);
		r(obj.getParentNode());
	}(treeNode));
	srcarr.reverse();  //翻转
	$(srcarr).each(function (i,o){
		if((i+1) == srcarr.length){
			src += o;
		}else{
			src += o +'-->';
		}
	});
	if(treeNode.pid == '-1'){
		//return false;
	}
	var url;
	if(treeNode.type==1){
		url = 'paramList?groupLevel='+treeNode.mark+"&pid="+treeNode.id;
	}else{
		url = 'paramForm?id='+treeNode.id;
	}
	$("#mainForm").attr("src",url);
}
function UpdateTree(obj){
	if(!obj){
		if(ztree.getSelectedNodes().length == 0){
			obj = "parent";
		}else{
			obj = "child";
		}
	}
	sortarr = new Array(); //每次清空排序数组.
	if(obj == "parent"){
		$.messager.alert('提示','请选择分组!');
		return;
	}else if(obj == "child"){
    var url;
		url = 'paramgroupForm?id='+treenode.id;
	$("#mainForm").attr("src",url);
	}
}
//打开新增菜单页面
var desort = 0;
var pid = ""; //父菜单ID，如果有就是子菜单，如果没有就是根菜单.
var appId = "";
function openMenuSettings(obj){
	if(!obj){
		if(ztree.getSelectedNodes().length == 0){
			obj = "parent";
		}else{
			obj = "child";
		}
	}
	sortarr = new Array(); //每次清空排序数组.
	if(obj == "parent"){
		var url = 'paramgroupForm?id=';
		$("#mainForm").attr("src",url);
		var sortarr = getMenuMaxSort(ztree.getNodes());
		desort =  Math.max.apply(null, sortarr)+1;
	}else if(obj == "child"){
		if(treenode.type==0){
			$.messager.alert('提示','此为功能，不可增加分组!');
			return;
		}else{
			var url ='paramgroupForm?pid='+treenode.id;
			pid = treenode.id;
			$("#mainForm").attr("src",url);
			var sortarr = getMenuMaxSort(ztree.getNodes());
			desort =  Math.max.apply(null, sortarr)+1;
	}
	}
}

//得到菜单最大排序值
var sortarr = new Array();
function getMenuMaxSort(obj){
	$(obj).each(function (i,o){
		if(o.children != undefined){
			getMenuMaxSort(o.children);
		}else{
			sortarr.push(o.sort);
		}
	});
	if(sortarr.length == 0){
		sortarr.push(0);
	}
	return sortarr;
}
//保存菜单
function saveMenuSettings(){
	var menuForm = window.frames['mainForm'];
	if(!menuForm.$("#mainForm").valid()){
		return ;
	}
	if($("#mainForm").attr("src").indexOf("paramList")<0){
		$.ajax({
			url:'saveParam',
			data:menuForm.$("#mainForm").serialize(),
			dataType:'json',
			type:'POST',
			success:function (data){
				menuForm.location.reload(true);  //刷新页面
				if(data.success){
					$.messager.alert('提示',data.msg);
					ztree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
				}else{
					$.messager.alert('提示',data.msg);
				}
			}
		});
	}
}

function deleteMenuBytreeId(){
	var nodes = ztree.getCheckedNodes(true);
	if(nodes.length == 0){
		$.messager.alert('提示','请选择节点!');
		return;
	}
	var nodeids = new Array();
	$(nodes).each(function (i,o){
		nodeids.push(o.id);
		treenode = o.getParentNode();
		//ztree.removeNode(o); 
	});
	$.ajax({
		url:'deleteparamById',
		dataType:'json',
		data:{id:nodeids.join(","),appId:appId},
		type:'POST',
		success:function (data){
			if(data.success){
				$.messager.alert('提示',data.msg);
				$(nodes).each(function (i,o){
					ztree.removeNode(o);
				});
				$("#mainForm").attr("src","");
				$("#mainForm").html("");
				ztree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			}else{
				$.messager.alert('提示',data.msg);
			}
		}
	});
}


/*========================================浏览器监听事件开始========================================*/
function keyUp(e) {   
    var currKey=0,e=e||event;   
    currKey=e.keyCode||e.which||e.charCode;   
    var keyName = String.fromCharCode(currKey);   
    if(currKey == 13){  //回车键
    	doSearch();  //检索菜单树
    }
}   
document.onkeyup = keyUp; 
/*========================================浏览器监听事件结束========================================*/
/*========================================工具 开始==============================================*/
function randomNum(n){ 
    var t=''; 
    for(var i=0;i<n;i++){ 
        t+=Math.floor(Math.random()*10); 
    } 
    return t; 
}

/*========================================工具 结束==============================================*/