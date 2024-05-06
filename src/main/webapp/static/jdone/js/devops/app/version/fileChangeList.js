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
		onAsyncSuccess: initCheck,
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
		url: "getFileTree"
	}
};
var zNodes =[];
$(function (){
	if(appMark==appname){
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
	}else{
		ckeckUrl();
	}
});

function ckeckUrl() {
	var content =  ' <table border="0" width="99%" class="table-detail"  style="margin: 1px;">'
		+'<tbody>'
		+'<tr style="border: 1px solid #7babcf;">'
		+'<th style="width: 25%; border: 1px solid #7babcf; height: 30px; color: #6F8DC6; background-color: #ebf5ff; font-weight: bold;text-align:right;">待打包源程序路径:&nbsp;</th>'
		+'<td style="border: 1px solid #7babcf;font-family: sans-serif;padding-left:5px;">'
		+'<input name="filePath" id="filePath" type="text" style="width:450px" value=""></td></tr>';
	content +='</tbody></table>';
   	$("#selectApp").dialog({
		content : content,
		width : '65%',
		height : '40%',
		modal : true,
		closable:false,
		title : "设置源程序路径",
		buttons : [ {
			text : '确认',
			left : 50,
			handler : function() {
				var filePath = $("#filePath").val();
				if (filePath == null || filePath == "" || typeof filePath == undefined) {
					$.messager.alert('提示', "请输入路径!");
					return false;
				}
				initPageAuto();
				setting.async.otherParam = ["filePath",filePath,"fileName",appMark];
				ztree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
				if (!!window.ActiveXObject || "ActiveXObject" in window){
					var browser=navigator.appName; 
					var b_version=navigator.appVersion; 
					var version=b_version.split(";"); 
					var trim_Version=version[1].replace(/[ ]/g,""); 
					if(trim_Version=="MSIE7.0"){
						$("ul").css("margin-left","14px");
					}
				}
				$("#selectApp").dialog("close");
			}
		} ]
	});
	// 隐藏窗口关闭按钮
	$("#selectApp").prev().find("div[class='panel-tool']").bind('click',
			function() {
				$("#selectApp").dialog("close");
			});
	// 绑定角色单选框点击事件
	$(".rdobox").click(function() {
		$(this).prev().prop("checked", "checked");
		rdochecked('rdolist');
	});
}

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
	ztree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
}
var treenode;
function initCheck(event, treeId, treenode) {
	$.ajax({
		type : "post",
		url : ctx + "/app/version/initCheck?id="+$("#id").val(),
		dataType : 'json',
		cache : false,
		success : function(datas) {
			for(var i = 0;i<datas.length;i++){
				var getNode = ztree.getNodesByParam("desc",datas[i],null);
				if(getNode != null){
					ztree.checkNode(getNode[0],true, true);
			     }
			}
		}
	});
}

function saveFilePath (){
	 var nodes=ztree.getCheckedNodes(true);
	 var checkNode = [];
	 $.each(nodes, function (key, item) {
	        if (item.isParent) {
	            if (!item.getCheckStatus().half)
	                checkNode.push(item);
	        } else {
	        	 if (checkNode.findIndex(function (val) {return val.id === item.pid;}) === -1)
	                checkNode.push(item);
	        }
	    });
	    checkNode.forEach(function (item) {
	        checkNode = checkNode.filter(function (val) {return val.pid !== item.id;});
	    });
     var filePathList="";
     for(var i=0;i<checkNode.length;i++){
             if (filePathList == "") {
				filePathList = checkNode[i].desc;
             }else{
				filePathList += "\r\n" + checkNode[i].desc;
             }
     }
     var fileChangeForm = $("#upgradeRecordForm").serializeObject();
     fileChangeForm.filePathList=filePathList;
     $.ajax({
			type: "post",
			url: ctx+"/app/version/updateVer",
			data: fileChangeForm,
			dataType: 'json',
			cache: false,
			success: function(data) {
				if(data.success){
					location.reload();
				}else{
					alert(data.msg);
				}
			}
		});
}

function sdFilePath (){
	 var nodes=ztree.getCheckedNodes(true);
	 var checkNode = [];
	 $.each(nodes, function (key, item) {
	        if (item.isParent) {
	            if (!item.getCheckStatus().half)
	                checkNode.push(item);
	        } else {
	        	 if (checkNode.findIndex(function (val) {return val.id === item.pid;}) === -1)
	                checkNode.push(item);
	        }
	    });
	    checkNode.forEach(function (item) {
	        checkNode = checkNode.filter(function (val) {return val.pid !== item.id;});
	    });
     var filePathList="";
     for(var i=0;i<checkNode.length;i++){
             if (filePathList == "") {
				filePathList = checkNode[i].desc;
             }else{
				filePathList += "\r\n" + checkNode[i].desc;
             }
     }
     var fileChangeForm = $("#upgradeRecordForm").serializeObject();
     fileChangeForm.filePathList=filePathList;
     $.ajax({
			type: "post",
			url: ctx+"/app/version/updateVer",
			data: fileChangeForm,
			dataType: 'json',
			cache: false,
			success: function(data) {
				if(data.success){
					window.open(ctx + "/app/version/download?id="+fileChangeForm.id);
				}else{
					alert(data.msg);
				}
			}
		});
}