function openDiskDetails(id){
	$('#openProcessList')[0].src = ctx + "/log/diskSpaceUsage/openDiskSpaceUsageView?id="+id;
	$('#processDialog').dialog('open');
}

//点击查找按钮出发事件
function searchFunc() {
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid('load',data);   
}
jQuery.fn.removeSelected = function() {
    this.val("");
};

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('clear');
}

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

