
$(function() {
	initDataGrid();
});
//表格绑定
function initDataGrid() {
	$("#datagrid").datagrid({
		url : ctx + "/rmd/notice/findPageList ",
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : false, // 复选框标识
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [{
			field : 'noticeTitle',title : '标题',align : 'center',width : '35%',
			formatter:function(value,index,row){
				return "<a href='#' style='color:blue' onclick='doPreview(\"" + index.id + "\");'>"+value+"</a>";
			}
		}, {
			field : 'createTime',title : '添加时间',align : 'center',width : '30%'
		}, {
			field : 'issuer',title : '发布人',align : 'center',width : '20%'
		}/*,{title: '签收状态', field: 'isSign', width: '15%', align : 'center',formatter:function(row, index, value){
    		var h = "";
    		if(index.isSign ==1){
    			h = "已签收";
    		}else if(index.isSign ==0){
    			h = "未签收";	
    		}
    		return h;
    	}}*/
		] ]
	});
}
//点击查找按钮出发事件
function searchFunc(idList) {
	var data = $("#searchForm").serializeObject();
	data.id = idList;
	$("#datagrid").datagrid('load',data);
}

//清除查询条件
function ClearQuery() {
    $("#searchForm").find("input").val("");
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

/*预览信息*/
function doPreview(id) {
	var url = ctx + "/rmd/notice/previewNotice/" + id;
	window.open(url);
}