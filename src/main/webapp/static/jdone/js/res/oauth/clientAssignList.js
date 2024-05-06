$(function () {
    initDataGrid();
    $("input[name='sqlx']").click(function () {
        showOrHideSqsx();
    });
    // dicInit();
});

function initDataGrid() {
    $("#datagrid").datagrid({
        url: ctx + "/res/oauth/resource/assign/getAssignPageData",
        width: '100%',
        pagination: true, // 显示分页栏
        rownumbers: true, // 显示每行列号
        checkOnSelect: true, // 复选框标识
        queryParams: {clientId: $("#clientId").val()},
        toolbar: initToolbar(),
        emptyMsg: '<span>无记录</span>',
        columns: [[
            {field: 'id', align: 'center', checkbox: true},
            {field: 'resName', title: '资源名称', align: 'center', width: '10%', formatter: formatShowMore},
            {
                field: 'resMark', title: '资源名称', align: 'center', width: '10%', formatter:function(val, row, index) {
                    return "<span title='" + val + "'>" + val + "</span>";
                }
            },
            {field: 'resUri', title: '资源标识', align: 'center', width: '13%', formatter: formatShowMore},
            {
                field: 'sqlx', title: '授权类型', align: 'center', width: '10%', formatter: function (val, row, index) {
                    var sqlxName = "";
                    if ('long' == val) {
                        sqlxName = "永久";
                    }
                    if ('temp' == val) {
                        sqlxName = "固定期限";
                    }
                    return sqlxName;
                }
            },
            {field: 'sqsxKssj', title: '授权开始时间', align: 'center', width: '15%', formatter: formatShowMore},
            {field: 'sqsxJzsj', title: '授权时效时间', align: 'center', width: '15%', formatter: formatShowMore},
            {field: 'attach_props', title: '其他设置', align: 'center', width: '10%', formatter: attachPropsFunction},
            {field: 'tjsj', title: '添加时间', hidden: true, align: 'center', width: '10%', formatter: formatShowMore},
            {field: 'tjxgsj', title: '添加修改时间', hidden: true, align: 'center', width: '10%', formatter: formatShowMore},
            {field: '_operate', title: '编辑', align: 'center', width: '15%', formatter: formatOper}
        ]]
    });
}

function initToolbar() {
    var toolbar = [{
        text: '添加资源',
        iconCls: 'icon-add',
        handler: function () {
            window.location.href = ctx + "/res/oauth/client/resourceTreeForm?clientId=" + $("#clientId").val();
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
    }, {
        text: '授权时限批量修改',
        iconCls: 'icon-edit',
        handler: function () {
            openBatchUpdate()
        }
    }, {
        text: '刷新终端授权信息',
        iconCls: 'icon-reload',
        handler: function () {
            updateClientAssign()
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
        window.location.href = ctx + "/res/oauth/resource/assign/assignForm?clientMark=client&resId=" + row.resId + "&id=" + row.id;
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
function attachPropsFunction(val, row, index) {
    if ('page' == row.resType) {
        return "--";
    }
    return "<a href='javascript:void(0);' onclick='alterAdditionlInfo(\"" + row.id + "\");'>配置</a>";
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


function alterAdditionlInfo(id) {
    var url = ctx + '/res/oauth/resource/assign/additionalForm?id=' + id;
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


function openBatchUpdate() {
    var rows = $("#datagrid").datagrid("getSelections");
    if (rows.length < 1) {
        $.messager.alert("提示", "请至少选择一行数据", "error");
        return;
    }
    $("#sqlx-long").prop("checked", "checked");
    $("#sqsxTr").hide();
    $("#sqsxJzsj").val("");
    $("#sqsxKssj").val("");
    $('#updateSqsxDiv').dialog({
        width: '600',
        height: '300',
        modal: true,
        title: "授权时限",
        buttons: [{
            text: '保存',
            handler: function () {
                batchUpdateAssign();
            }
        }, {
            text: '关闭',
            handler: function () {
                $('#updateSqsxDiv').dialog('close');
            }
        }]
    });
}

function showOrHideSqsx() {
    var checked = $('input[name="sqlx"]:checked').val();
    if ("temp" == checked) {
        $("#sqsxTr").show();
    } else {
        $("#sqsxTr").hide();
        $("#sqsxJzsj").val("");
        $("#sqsxKssj").val("");
    }
}

function batchUpdateAssign() {
    if (!$("#mainForm").valid()) {
        return;
    }
    var tabledata = $("#mainForm").serializeObject();
    var rows = $("#datagrid").datagrid("getSelections");
    var ids = "";
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        ids += row.id + ","
    }
    tabledata.ids = ids;
    tabledata.clientId = $("#clientId").val();
    $.messager.progress({text: "正在保存..."});
    $.ajax({
        url: ctx + '/res/oauth/resource/assign/batchUpdateAssign',
        data: tabledata,
        dataType: 'JSON',
        type: 'POST',
        async: true,//异步true,同步false
        success: function (data) {
            if (data.success) {
                initDataGrid();
                $('#updateSqsxDiv').dialog('close');
            }
            $.messager.alert("提示", data.msg, 'info');
            $.messager.progress("close");
        },
        error: function (data) {
            $.messager.alert("提示", "请求异常", 'error');
            $.messager.progress("close");
        }
    });
}

function updateClientAssign() {
    $.messager.confirm("提示", "是否刷新终端授权信息？", function (r) {
        if (r) {
            $.messager.progress({text: "正在刷新..."});
            $.ajax({
                url: ctx + '/res/oauth/client/updateClientAssign',
                data: {
                    clientId: $("#clientId").val()
                },
                dataType: 'JSON',
                type: 'POST',
                async: true,//异步true,同步false
                success: function (data) {
                    $.messager.alert("提示", data.msg, 'info');
                    $.messager.progress("close");
                },
                error: function (data) {

                    $.messager.alert("提示", "请求异常", 'error');
                    $.messager.progress("close");
                }
            });
        }
    })

}
