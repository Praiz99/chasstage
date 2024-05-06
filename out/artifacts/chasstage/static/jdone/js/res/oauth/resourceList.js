var markLevel;
var pid;
$(function () {
    markLevel = $("#markLevel").val();
    pid = $("#pid").val();
    initDataGrid();
});

function initDataGrid() {
    $("#datagrid").datagrid({
        url: ctx + "/res/oauth/resource/findPageList",
        width: '100%',
        pagination: true, // 显示分页栏
        rownumbers: true, // 显示每行列号
        checkOnSelect: true, // 复选框标识
        queryParams: {markLevel: markLevel},
        toolbar: initToolbar(),
        emptyMsg: '<span>无记录</span>',
        columns: [[
            {field: 'id', align: 'center', checkbox: true},
            {field: 'name', title: '资源名称', align: 'center', width: '15%'},
            {field: 'resUri', title: '资源地址', align: 'center', width: '24%'},
            {field: 'mark', title: '资源标识', align: 'center', width: '10%'},
            {
                field: 'resType', title: '资源类型', align: 'center', width: '10%', formatter: function (val, row, index) {
                    if(val == 'page'){
                        return "页面功能";
                    }
                    if(val == "data"){
                        return "数据查询接口";
                    }
                    if(val == 'biz'){
                        return "业务处理接口";
                    }
                }
            },
            {
                field: 'isNeedUserInfo',
                title: '是否需要传递用户信息',
                align: 'center',
                width: '15%',
                formatter: function (val, row, index) {
                    return val == 1 ? "是" : "否";
                }
            },
            {field: 'appMark', title: '所属应用', align: 'center', width: '10%'},
            {field: 'tjsj', title: '添加时间', hidden: true, align: 'center', width: '10%'},
            {field: 'tjxgsj', title: '添加修改时间', hidden: true, align: 'center', width: '10%'},
            {field: '_operate', title: '编辑', align: 'center', width: '15%', formatter: formatOper}
        ]]
    });
}

function initToolbar() {
    var toolbar = [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            parent.$("#mainForm").attr("src", "resourceForm?id=" + pid);
            window.location.href = ctx + "/res/oauth/resource/resourceForm?pid=" + pid;
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
                    deleteResource(rows);
                }
            });
        }
    }];

    return toolbar;
}

/* 修改样式 */
function formatOper(val, row, index) { // val:值  row与此相对应的记录行 index：该行索引从0开始
    return '<a href="javascript:void(0)" onclick="editResource(' + index + ')">修改</a> ' +
        '&nbsp;<a href="javascript:void(0)" onclick="assignResource(\'' + row.id + '\')">授权</a>';
}

function editResource(index) {
    $('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
    var row = $('#datagrid').datagrid('getRows')[index];
    if (row) {
        parent.$("#mainForm").attr("src", "resourceForm?id=" + row.id);
    }
}

/* 删除样式 */
function deleteResource(rows) {
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
        url: ctx + "/res/oauth/resource/deleteResourceById?id=" + ids,
        dataType: 'json',
        cache: false,
        success: function (data) {
            $("#datagrid").datagrid('reload');
            parent.window.doSearch();
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
function assignResource(resId) {
    var url = ctx + "/res/oauth/resource/assign/assignList?resId=" + resId;
    $('#assignWindow').dialog({
        content: '<iframe id="additionalIframe" name="additionalIframe" src="' + url + '" width="98%" height="99%" frameborder="0" scrolling="no"></iframe>',
        width: '800',
        height: '500',
        modal: true,
        title: "资源授权",
    });
}