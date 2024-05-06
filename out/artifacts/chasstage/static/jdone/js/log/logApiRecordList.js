$(function(){
    initDataGrid();
    //模糊查询enter事件
    $('.keydownSearch').next().bind('keydown', function(e){
        if (e.keyCode == 13) {
            $("#keydownSearch").click();
        }
    });
});

function initDataGrid(){
    $("#datagrid").datagrid({
        url:ctx + "/rest/log/apiRecord/getDatagrid",
        width:'100%',
        pagination : true, // 显示分页栏
        checkOnSelect : true, // 复选框标识
        // toolbar : initToolbar(),
        fitColumns : true,
        emptyMsg: '<span>无记录</span>',
        columns:[[
            {field:'id',align:'center',checkbox:true},
            {field:'reqIp',title:'请求IP',align:'center',width:'10%'},
            {field:'apiMark',title:'接口标识',align:'center',width:'10%',formatter:function(value,row,index){
                    return "<span title='"+value+"'>"+value+"</span>"
                }},
            {field:'reqTime',title:'请求时间',align:'center',width:'10%'},
            {field:'apiType',title:'请求类型',align:'center',width:'10%'},
            {field:'reqAppClientId',title:'请求授权终端ID',align:'center',width:'10%'},
            {field:'respAppMark',title:'响应应用标识',align:'center',width:'10%'},
            {field:'respIp',title:'响应IP',align:'center',width:'10%'},
            {field:'respCode',title:'响应码',align:'center',width:'10%'},
            {field:'respMessage',title:'响应信息',align:'center',width:'10%',formatter:function(value,row,index){
                return "<span title='"+value+"'>"+value+"</span>"
                }}
        ]]
    });
}


function searchFunc(){
    var data = $("#searchForm").serializeObject();
    $("#datagrid").datagrid("load",data);//加载和显示数据
}

function ClearQuery(){
    $("#searchForm").form('clear');
}
