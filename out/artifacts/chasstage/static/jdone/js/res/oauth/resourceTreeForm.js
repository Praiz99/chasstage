$(function () {
    initTreegrid();
});

function initTreegrid() {
    var clientId = $("#clientId").val();
    $("#resourceTreegrid").treegrid({
        url: ctx + "/res/oauth/resource/getResourceTree?notInClient=" + clientId,
        idField: 'id',
        treeField: 'name',
        checkbox: true,
        height: 440,
        // onlyLeafCheck: true,
        onSelect: function (node) {
            if (node.checkState == 'unchecked') {
                $("#resourceTreegrid").treegrid("checkNode", node.id);
            } else {
                $("#resourceTreegrid").treegrid("uncheckNode", node.id);
            }
        },
        columns: [[
            {title: '资源名称', field: 'name', width: 180},
            {title: '资源标识', field: 'mark', width: 180},
            {
                title: '资源类型', field: 'resType', width: 140, align: 'center', formatter: function (val, row, index) {
                    var desc = row.desc;
                    var descArray = desc.split(",");
                    var resType = "";
                    if (descArray.length > 1) {
                        resType = descArray[1];
                    }
                    if (resType == 'page') {
                        return "页面功能";
                    }
                    if (resType == "data") {
                        return "数据查询接口";
                    }
                    if (resType == 'biz') {
                        return "业务处理接口";
                    }
                    return "--";
                }
            },
            {
                title: '是否需要传递用户信息',
                field: 'isNeedUserInfo',
                width: 150,
                align: 'center',
                formatter: function (val, row, index) {
                    var desc = row.desc;
                    var descArray = desc.split(",");
                    var isNeed = "";
                    if (descArray.length > 0) {
                        isNeed = descArray[0];
                    }
                    if (1 == isNeed) {
                        return "是";
                    }
                    if (0 == isNeed) {
                        return "否";
                    }
                    return "--";
                }
            },
            {title: '资源地址', field: 'url', width: 300, formatter: formatShowMore}
        ]]
    });

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


/*按区域名称查找*/
function searchFunc(idList) {
    var data = $("#searchForm").serializeObject();
    data.id = idList;
    $("#resourceTreegrid").treegrid("reload", data);//加载和显示数据
    var rows = $("#resourceTreegrid").treegrid("getCheckedNodes");
    if(rows){
        for(var i = 0; i < rows.length; i++){
            $("#resourceTreegrid").treegrid("uncheckNode", rows[i].id);
        }
    }
}

/*清理所有数据*/
function ClearQuery() {
    $("#table_form").find("input").val("");
}

/**
 * 保存授权
 */
function addClientAssign() {
    var rows = $("#resourceTreegrid").treegrid("getCheckedNodes");
    console.log(rows);
    var ids = "";
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        //是否包含URL值来判断是具体资源，而不是分组
        if (row.url != null && row.url.length > 0) {
            ids += row.id + ",";
        }
    }
    ids = ids.substring(0, ids.length - 1);
    if (ids.length < 1) {
        $.messager.alert("提示", "没有具体资源，请重新选择？", "error");
        return;
    }
    $.messager.progress({text: "正在保存..."});
    $.ajax({
        url: ctx + '/res/oauth/resource/assign/saveClientAssign',
        data: {
            ids: ids,
            clientId: $("#clientId").val()
        },
        dataType: 'JSON',
        type: 'POST',
        async: true,//异步true,同步false
        success: function (data) {
            if (data.success) {
                searchFunc();
            }
            $.messager.alert("提示", data.msg, 'info');
            $.messager.progress("close");
        },
        error: function (data) {
            $.messager.alert("提示", "请求失败", 'error');
            $.messager.progress("close");
        }
    });
}

