$(function() {
	initDataGrid();
	
	$('.keydownSearch').next().bind('keydown', function(e){
	    if (e.keyCode == 13) {
	    	$("#keydownSearch").click();
	    }
	});
});
//表格绑定
function initDataGrid() {
	$("#datagrid").datagrid({
		url : ctx + "/rmd/notice/finduserNoticeList",
		width : '100%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		singleSelect: true,
		fitColumns : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [
		{
			field : 'issuer',title : '发布人',align : 'center',width : '25%'
		}, {
			field : 'noticeTitle',title : '标题',align : 'center',width : '44%',formatter:formatTitle
		},{
			field : 'createTime',title : '添加时间',align : 'center',width : '30%'
		}
		] ]
	});
}
function formatTitle(value,index,row){
	if(index.id != undefined){
	return "<a href='#' style='color:blue' onclick='doPreview(\"" + index.id + "\");'>"+value+"</a>";
}
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