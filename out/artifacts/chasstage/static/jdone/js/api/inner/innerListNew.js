//此处是easyui的json格式  
var tree = {  
    id:'',  
    text:'',  
    state:'',  
    checked:'',  
    attributes:'',  
    children:''  
}  
//此处是把后台传过来的json数据转成easyui规定的格式  
function bl(item){
    var tree = new Object();  
    tree.id = item.id;  
    tree.text = item.groupName;  
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
			    onSelect:onNodeSelect,
		        url: ctx + '/sys/group/getInnerTreeData?bizMark='+'nbjk',  
		        loadFilter: function(data){   
		          return jsonbl(data);   
		       }  
		  });
   }
function onNodeSelect(node){
	//为叶子结点
	if($('#operFrame').attr('src') != url){
		$('#operFrame').attr('src', url);
			var url=ctx + '/api/inner/innerList?id='+node.id;  
			if($('#operFrame').attr('src') != url){
				$('#operFrame').attr('src', url);
		}
	}
}
