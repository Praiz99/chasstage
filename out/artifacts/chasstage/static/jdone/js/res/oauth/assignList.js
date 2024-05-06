var resId;
var pid;
$(function () {
    resId = $("#resId").val();
    pid = $("#pid").val();
    initDataGrid();
    dicInit();
});

function initDataGrid() {
    $("#datagrid").datagrid({
        url: ctx + "/res/oauth/resource/assign/pageList",
        width: '100%',
        pagination: true, // 显示分页栏
        rownumbers: true, // 显示每行列号
        checkOnSelect: true, // 复选框标识
        queryParams: {resId: resId},
        toolbar: initToolbar(),
        emptyMsg: '<span>无记录</span>',
        columns: [[
            {field: 'id', align: 'center', checkbox: true},
            {field: 'clientIdName', title: '终端名称', align: 'center', width: '15%'},
            {field: 'clientId', title: '终端标识', align: 'center', width: '19%'},
            {field: 'sqlx', title: '授权类型', align: 'center', width: '10%',formatter:function(val,row,index){
                var sqlxName = "";
                if('long' == val){
                    sqlxName = "永久";
                }
                if('temp' == val){
                    sqlxName = "固定期限";
                }
                return sqlxName;
                }},
            {field: 'sqsxKssj', title: '授权开始时间', align: 'center', width: '15%'},
            {field: 'sqsxJzsj', title: '授权时效时间', align: 'center', width: '15%'},
            {field: 'attach_props', title: '其他设置', align: 'center', width: '10%',formatter:attachPropsFunction},
            {field: 'tjsj', title: '添加时间', hidden:true ,align: 'center', width: '10%'},
            {field: 'tjxgsj', title: '添加修改时间',hidden:true, align: 'center', width: '10%'},
            {field: '_operate', title: '编辑', align: 'center', width: '15%', formatter: formatOper}
        ]]
    });
}

function initToolbar() {
    var toolbar = [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            window.location.href = ctx + "/res/oauth/resource/assign/assignForm?resId=" + resId;
        }
    }, {
        text: '删除',
        iconCls: 'icon-cut',
        handler: function () {
            var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
            if (rows.length == 0) {
                $.messager.alert("系统提示", "请至少选择一行数据!");
                return false;
            }
            $.messager.confirm('系统提示', '您确定要删除吗?', function (r) {
                if (r) {
                    deleteAssign(rows);
                }
            });
        }
    }];

    return toolbar;
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
    return '<a href="javascript:void(0)" onclick="editAssign(' + index + ')">修改</a> ';
}

function editAssign(index) {
    $('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row) {
        window.location.href = ctx+"/res/oauth/resource/assign/assignForm?resId=" + row.resId+"&id="+row.id;
    }
}

/* 删除样式 */
function deleteAssign(rows) {
    var ids = "";
    for (var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
        if (ids == "") {
            ids = rows[0].id;
        } else {
            ids += "," + rows[i].id;
        }
    }
    $.ajax({
        type: "post",
        url: ctx + "/res/oauth/resource/assign/deleteAssignById?id=" + ids,
        dataType: 'json',
        cache: false,
        success: function (data) {
            $("#datagrid").datagrid('reload');
        }
    });
}

/*按区域名称查找*/
function searchFunc(idList) {
    var data = $("#searchForm").serializeObject();
    data.id = idList;
    $("#datagrid").datagrid("load", data);//加载和显示数据
}

/*清理所有数据*/
function ClearQuery() {
    $("#table_form").find("input").val("");
}

/* 表单数据传成json
 *
 * $.fn是指jquery的命名空间，加上fn上的方法及属性，会对jquery实例每一个有效
 *
 * push在js中是压栈的意思
 * */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
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

/**
 * 授权
 * @param resId
 */
function attachPropsFunction(val,row,index){
    if('page' == $("#resType").val()){
        return "--";
    }
    return "<a href='javascript:void(0);' onclick='alterAdditionlInfo(\"" + row.id + "\");'>配置</a>";
}


function alterAdditionlInfo(id) {
    var url = ctx + '/res/oauth/resource/assign/additionalForm?id='+id;
    $('#additional').dialog({
        content: '<iframe id="additionalIframe" name="additionalIframe" src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>',
        width: '500',
        height: '400',
        modal: true,
        title: "配置",
        buttons: [{
            text: '保存',
            handler: function () {
                var additional = $("#additionalIframe")[0].contentWindow.getResult();
                $.ajax({
                    url: ctx + '/res/oauth/resource/assign/saveAdditional',
                    data: {id: id, additionalInformation: additional},
                    dataType: 'JSON',
                    type: 'POST',
                    async: true,//异步true,同步false
                    success: function (data) {
                        $.messager.alert("提示", data.msg, 'info');
                        $('#additional').dialog('close');
                    },
                    error: function (data) {

                    }
                });

            }
        }, {
            text: '关闭',
            handler: function () {
                $('#additional').dialog('close');
            }
        }]
    });
}