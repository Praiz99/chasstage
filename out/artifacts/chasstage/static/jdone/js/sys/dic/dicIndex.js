var toolBarManager;
var treeManager;
var ctx;
var currentNode;
var toolbar;
var flag;
var url = '/sys/dic/getDicTreeData';

$(function(){
	$("#toptoolbar").datagrid({
		width:'100%',
		toolbar:initToolbar(),
		onBeforeLoad: function(data){
			$(".datagrid-view").css("min-height","0px");
			$(".datagrid-view").css("height","0px");
		}
	});
	bindTree();
});


function initToolbar(){
	var enableEditFlag = $("#enableEdit").val()=="true" ? true : false;
	toolbar = [];
	if(enableEditFlag){
		toolbar = toolbar.concat([
/*		{
	         text:'字典分类',
	         id:'createAllJS',
	         disabled:false,
	         iconCls:'icon-man',
	         handler:function(){
	        	 $('#operFrame').attr('src', 'dicCatFrom');
	         }
	    },*/
	    
	    {
	        text:'保存字典',
	        id:'save',
	        iconCls:'icon-save',
	        handler:function(){
	        	saveDic();
	        }
	    },{
	        text:'删除字典',
	        id:'delete',
	        iconCls:'icon-cut',
	        handler:function(){
	        	deleteDic();
	        }
	    },{
	        text:'引用字典',
	        id:'refDic',
	        iconCls:'icon-add',
	        handler:function(){
	        	refDic();
	        }
	     },'-']);
	}
	
	toolbar = toolbar.concat( [
						       {
						         text:'生成字典JS',
						         id:'createJS',
						         iconCls:'icon-add',
						         handler:function(){
						        	 createJS();
						         }
						       },{
						           text:'生成所有字典JS',
						           id:'createAllJS',
						           iconCls:'icon-add',
						           handler:function(){
						        	   createAllJS();
						           }
						    },{
						           text:'导出字典',
						           id:'exportDic',
						           iconCls:'icon-add',
						           handler:function(){
						        	   exportDic();
						           }
						    }]);
    
    return toolbar;
}

//此处是easyui的json格式  
var tree = {  
    id:'',  
    text:'',  
    state:'',  
    checked:'',  
    attributes:'',  
    children:''  
};

//此处是把后台传过来的json数据转成easyui规定的格式  
function bl(item){
    var tree = new Object();  
    tree.id = item.id;  
    tree.text = item.dicName;  
    tree.state = 'open';  
    tree.checked = false;
    if(item.children!=null){  
        tree.children = jsonbl(item.children);  
    }else{  
        tree.children = [];  
    }  
    return tree;  
}  

function jsonbl(data){  
    var easyTree = [];  
    $.each(data,function(index,item){  
     easyTree[index] = bl(item);  
     });  
    return easyTree;  
}  
//绑定字典树
function bindTree(){
	treeManager=$('#tree1').tree({  
			    checkbox:'true',
			    onCheck:onNodeCheck,
			    onSelect:onNodeSelect,
		        url: ctx + url,  
		        loadFilter: function(data){ 
		          return jsonbl(data);   
		        },  
	            onLoadSuccess:function(){ 
	        	    $("#tree1").tree("collapseAll");  
	            } 
		  });
   }


function onNodeSelect(node){
	
	var father = $('#tree1').tree("getParent",node.target);
	if(father==null){
		var url=ctx + '/sys/dic/goDicView?catId='+node.id+"&flag="+1; 
		if($('#operFrame').attr('src') != url){
			$('#operFrame').attr('src', url);
		}
	}else{
		var url=ctx + '/sys/dic/goDicView?catId='+father.id+"&dicId="+node.id+"&flag="+2; 
		if($('#operFrame').attr('src') != url){
			$('#operFrame').attr('src', url);
		}
	}
}

function onNodeCheck(node, checked){
}

//保存字典
function saveDic(){
	 $('#operFrame')[0].contentWindow.saveDic();
}

function deleteDic(){
	var checkedNotes = $('#tree1').tree('getChecked');
	if(checkedNotes.length==0)return $.messager.alert("温馨提示", "请选择要删除的字典!");
	var ids=[];
	$.each(checkedNotes,function(i,node){
		if(checkedNotes.children>0){
			$.messager.alert("不能删除字典分类");
			return;
		}
		ids.push(node.id);
	});
	
	$.messager.confirm('温馨提示', '您确定要删除该字典', function(r) {
    	if(r){
        	$.ajax({
                type: "get",
                url: ctx + '/com/dic/deleteDic?ids='+ ids.join(","),
                dataType: "json",
                success: function(data){
                	if(data.success){
    					$.messager.alert("温馨提示",data.msg);
    					$('#tree1').tree("reload");
    					$('#operFrame').attr('src', '');
    				}else{
    					$.messager.alert("温馨提示",data.msg);
    				}
                }
            });
    	}
    }); 
	
}

/**引用字典*/
function refDic(){
	var selectedNode = $('#tree1').tree('getSelected');
	var father = $('#tree1').tree("getParent",selectedNode.target);
	if(!selectedNode || father!=null){
		$.messager.alert("温馨提示","请选择分类");
		return;
	}
	var id=selectedNode.id;
	var url=ctx+"/sys/dic/addRefDic?catId="+id;
	$('#operFrame').attr('src', url);
}

//弹出加载层
function showLoading() {  
    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
}

//取消加载层  
function disLoading() {  
    $(".datagrid-mask").remove();  
    $(".datagrid-mask-msg").remove();  
}

/**创建字典JS*/
function createJS(item){
	
	var checkNodes = $('#tree1').tree('getChecked');
	if (checkNodes==""||checkNodes.length == 0) {
		$.messager.alert("温馨提示","请选择字典！！");
		return;
	}
	
	var ids = [];
	$.each(checkNodes,function(i,node){
		if(checkNodes.children>0){
			$.messager.alert("温馨提示","不能生成字典分类JS");
			return;
		}
		ids.push(node.id);
	});
	//var waitBox=$.ligerDialog.waitting('正在创建字典,请稍候...'); 
	showLoading();
	$.ajax({
		url:ctx+"/sys/dic/createJS?id="+ids.join(","),
		type:"GET",
		dataType:'json',
		success:function(data){
			disLoading();
			if(data.success){
				$.messager.alert("温馨提示",data.msg);
			}else{
				$.messager.alert("温馨提示",data.msg);
			}
		}
	});
}

function createAllJS(){
	//var waitBox=$.ligerDialog.waitting('正在创建字典,请稍候...'); 
	showLoading();
	$.ajax({
		url:ctx+"/sys/dic/createAllJS",
		type:"GET",
		dataType:'json',
		success:function(data){
			disLoading();
			if(data.success){
				$.messager.alert("温馨提示",data.msg);
			}else{
				$.messager.alert("温馨提示",data.msg);
			}
		}
	});
}	

//查询
function searchData(){
	var searchParam = $('#searchInput').val();
	url = '/sys/dic/getDicTreeData?searchParam='+encodeURI(encodeURI(searchParam));
	bindTree();
}

//刷新树
function refreshTree(){
	$('#tree1').tree("reload");
}

function exportDic(item){
	var checkNodes = $('#tree1').tree('getChecked');
	if (checkNodes==""||checkNodes.length == 0) {
		$.messager.alert("温馨提示","请选择字典！！");
		return;
	}
	var ids = [];
	$.each(checkNodes,function(i,node){
		if(checkNodes.children>0){
			$.messager.alert("温馨提示","字典分类无法创建字典JS");
			return;
		}
		ids.push(node.id);
	});
	window.location.href=ctx+"/sys/dic/exportDic?ids="+ids.join(",");
	/*$.ajax({
		url:ctx+"/sys/dic/exportDic?ids="+ids.join(","),
		type:"GET",
		dataType:'json',
		success:function(data){
			if(data.success){
				$.messager.alert("温馨提示",data.msg);
			}else{
				$.messager.alert("温馨提示",data.msg);
			}
		}
	});*/	
}
