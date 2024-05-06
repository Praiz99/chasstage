$(function () {
    initDataGrid();
    //模糊查询enter事件
    $('.keydownSearch').next().bind('keydown', function (e) {
        if (e.keyCode == 13) {
            $("#keydownSearch").click();
        }
    });
});

function initDataGrid() {
    $("#datagrid").datagrid({
        url: ctx + "/res/oauth/client/clientPageList",
        width: '100%',
        pagination: true, // 显示分页栏
        rownumbers: true, // 显示每行列号
        checkOnSelect: true, // 复选框标识
        toolbar: initToolbar(),
        fitColumns: true,
        emptyMsg: '<span>无记录</span>',
        columns: [[{
            field: 'id',
            align: 'center',
            checkbox: true
        }, {
            field: 'clientName',
            title: '终端名称',
            align: 'center',
            width: '10%',
            formatter: formatShowMore
        }, {
            field: 'clientId',
            title: '终端标识',
            align: 'center',
            width: '10%',
            formatter: formatShowMore
        },{
            field: 'clientSecret',
            title: '终端秘钥',
            align: 'center',
            width: '15%',
            hidden: false,
            formatter: formatShowMore
        },  {
            field: 'resourceIds',
            title: '允许访问资源实例',
            align: 'center',
            width: '13%',
            hidden: false,
            formatter: formatShowMore
        }, {
            field: 'yxfwdzy',
            title: '允许访问的资源',
            align: 'center',
            width: '8%',
            formatter: showYxfwdzy
        },{
            field: 'authorizedGrantTypes',
            title: '授权模式',
            align: 'center',
            width: '18%',
            hidden: true,
            formatter: function (val, row, index) {
                var text = val.replace("password", "密码模式")
                    .replace("authorization_code", "授权模式")
                    .replace("client_credentials", "客户端模式")
                    .replace("refresh_token", "刷新模式")
                    .replace("implicit", "简化模式");
                return "<span title='" + text + "'>" + text + "</span>";
            }
        }, {
            field: 'additionalInformation',
            title: '其他配置',
            align: 'center',
            width: '12%',
            formatter: additionalInformationFun
        }, {
            field: 'tjsj',
            title: '添加时间',
            align: 'center',
            width: '10%',
            formatter: formatShowMore
        },{
            field: 'tjxgsj',
            title: '添加修改时间',
            align: 'center',
            width: '10%',
            formatter: formatShowMore
        }, {
            field: '_operate',
            title: '操作',
            align: 'center',
            width: '11%',
            formatter: formatOper
        }]]
    });
}

function initToolbar() {
    var toolbar = [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            window.location.href = ctx + "/res/oauth/client/clientForm";
        }
    }, '-', {
        text: '删除',
        iconCls: 'icon-cut',
        handler: function () {
            var rows = $('#datagrid').datagrid('getSelections');// 返回第一个被选中的行或如果没有选中的行则返回null
            if (rows.length == 0) {
                $.messager.alert("系统提示", "请至少选择一行数据!");
                return false;
            }
            var ids = "";
            for (var i = 0; i < rows.length; i++) {// 组成一个字符串，ID主键之间用逗号隔开
                if (ids == "") {
                    ids = rows[0].id;
                } else {
                    ids += "," + rows[i].id;
                }
            }
            deleteClient(ids);
        }
    }];
    return toolbar;
}

/**
 * 悬浮显示更多
 * @param val
 * @param row
 * @param index
 * @returns {string}
 */
function formatShowMore(val, row, index) {
    val = (val == null || val == 'null') ? "" : val;
    return '<span title="' + val + '" >' + val + '</span>';
}

/* 操作 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
    var opt = "";
    // opt += '<a href="javascript:void(0)" onclick="showClient(' + index + ')">查看 </a>&nbsp;';
    opt += '<a href="javascript:void(0)" onclick="editClient(' + index + ')">编辑 </a>&nbsp;';
    opt += '<a href="javascript:void(0)" onclick="deleteClient(\'' + row.id + '\')">删除 </a>&nbsp;';
    opt += '<a href="javascript:void(0)" onclick="clientAssign(\'' + row.clientId + '\')">授权</a>&nbsp;';
    return opt;
}

/**
 * 编辑
 * @param index
 */
function editClient(index) {
    $('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row) {
        window.location.href = ctx + "/res/oauth/client/clientForm?id=" + row.id;
    }
}

/**
 * 查看
 * @param index
 */
function showClient(index) {
    $('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
    var row = $('#datagrid').datagrid('getRows')[index];
    // if (row) {
    //     window.location.href = ctx + "/res/oauth/client/clientViewForm?id=" + row.id;
    // }
    alertYxfwdzy(row.id);
}


/* 删除 */
function deleteClient(ids) {
    $.messager.confirm('系统提示', '您确定要删除吗?', function (r) {
        if (r) {
            $.ajax({
                type: "post",
                url: ctx + "/res/oauth/client/deleteClients?ids=" + ids,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data.success) {
                        $("#datagrid").datagrid('reload');
                        $.messager.alert('系统提示', data.msg, 'info');
                    } else {
                        $.messager.alert('系统提示', data.msg, 'error');
                    }
                }
            });
        }
    });
}

/*搜索*/
function searchFunc(idList) {
    var data = $("#searchForm").serializeObject();
    data.id = idList;
    $("#datagrid").datagrid("load", data);//加载和显示数据
}

/*清理所有数据*/
function ClearQuery() {
    $("#searchForm").form('clear');
}

function additionalInformationFun(val, row, index) {
    return "<a href='javascript:void(0);' onclick='alterAdditionlInfo(\"" + row.id + "\");'>配置</a>";
}

function alterAdditionlInfo(id) {
    var url = ctx + '/res/oauth/client/additionalForm?id='+id;
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
                    url: ctx + '/res/oauth/client/saveAdditional',
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

/**
 * 允许访问的资源查看
 * @param val
 * @param row
 * @param index
 */
function showYxfwdzy(val,row,index){
    return "<a href='javascript:void(0);' onclick='alertYxfwdzy(\""+row.id+"\");'>查看</a>";
}

/**
 * 打开允许访问资源的页面
 */
function alertYxfwdzy(id){
    var url = ctx + '/res/oauth/client/resourceView?id='+id;
    $('#additional').dialog({
        content: '<iframe id="additionalIframe" name="additionalIframe" src="' + url + '" width="98%" height="99%" frameborder="0" scrolling="no"></iframe>',
        width: '800',
        height: '500',
        modal: true,
        title: "查看资源",
        buttons: []
    });
}

/**
 * 终端授权管理页面
 * @param clientId
 */
function clientAssign(clientId){
    var url = ctx + '/res/oauth/client/clientAssignList?clientId='+clientId;
    $('#additional').dialog({
        content: '<iframe id="assginIframe" name="assginIframe" src="' + url + '" width="99%" height="99%" frameborder="0" scrolling="no"></iframe>',
        width: '1000',
        height: '600',
        modal: true,
        title: "授权管理",
        buttons: []
    });
}