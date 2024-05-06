$(function() {
	initDataGrid();
	//模糊查询enter事件
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
});
	//表格绑定
    function initDataGrid() {
    	$("#datagrid").datagrid({
    		url : ctx + "/info/notice/getNoticePageData?isPublish=1",
    		width : '100%',
    		pagination : true, // 显示分页栏
    		rownumbers : true, // 显示每行列号
    		checkOnSelect : true, // 复选框标识
    		fitColumns : true,
    		emptyMsg: '<span>无记录</span>',
    		columns : [ [{
    			field : 'id',align : 'center',checkbox : true
    		}, {
    			field : 'title',title : '标题',align : 'center',width : '35%',
    			formatter:formatTitle
    		},{
    			field : 'tjsj',title : '添加时间',align : 'center',width : '17%'
    		},{
    			field : 'rmdStartDate',title : '提醒开始期限',align : 'center',width : '15%'
    		},{
    			field : 'rmdEndDate',title : '提醒截止时间',align : 'center',width : '15%'
    		}, {
    			field : 'fbrXm',title : '发布人',align : 'center',width : '17%'
    		}
    		] ]
    	});
    }
    
    function formatTitle(value,index,row){
    	return "<a href='#' style='color:blue' onclick='doPreview(\"" + index.id + "\");'>"+value+"</a>";
	}
    
    function fromatOper(value,row,index){  
		var h = "";
		 if(row.id!=undefined){
      	h += "<a href='#' style='color:blue' onclick='doPreview(\"" + row.id + "\");'>预览</a> ";
      	return h;
		 }
} 

    /*按标题查找*/
    function searchFunc(idList){
    	var data = $("#searchForm").serializeObject();
    	data.id = idList;
    	$("#datagrid").datagrid("load",data);//加载和显示数据
    }
    /*清理所有数据*/
    function ClearQuery(){
    	$("#searchForm").form('clear');
    }
    
    /*预览信息*/
    function doPreview(id) {
    	var url = ctx + "/info/notice/previewNotice/" + id;
    	window.open(url);
    }
    

    /* 表单数据传成json 
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

    

