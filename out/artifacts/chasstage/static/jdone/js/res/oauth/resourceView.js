$(function () {
    initClick();
});

function initClick() {
    $("a em").bind("click", function () {
        var a = $(this).parent("a");
        var assId = a.attr("id");
        if (assId) {
            $.messager.confirm("提示", "是否删除该授权资源？", function (r) {
                if (r) {
                    deleteAssign(assId, a);
                }
            })
        }
    });

    $("a span").bind("click", function () {
        var a = $(this).parent("a");
        var assId = a.attr("id");
        showResInfo(assId);
    });
}

/**
 * 删除授权
 */
function deleteAssign(assId, node) {
    $.ajax({
        type: "post",
        url: ctx + "/res/oauth/resource/assign/deleteAssignById?id=" + assId,
        dataType: 'json',
        cache: false,
        success: function (data) {
            if (data.success) {
                node.remove();
            }
            $.messager.alert("提示", "删除成功", "info");
        }
    });
}


function showResInfo(assId) {
    $.ajax({
        url: ctx + '/res/oauth/resource/assign/getAssignResInfo',
        data: {
            assId: assId
        },
        dataType: 'JSON',
        type: 'POST',
        async: true,//异步true,同步false
        success: function (data) {
            var info = data.data;
            if (data.success) {
                $("#info_resName").html(info.resName);
                $("#info_resMark").html(info.resMark);
                $("#info_resUri").html(info.resUri);
                var resTypeName = "";
                if ("page" == info.resType) {
                    resTypeName = "页面功能";
                }
                if ("data" == info.resType) {
                    resTypeName = "数据查询接口";
                }
                if ("biz" == info.resType) {
                    resTypeName = "业务处理接口";
                }
                $("#info_resType").html(resTypeName);
                $("#info_isNeedUserInfo").html(info.isNeedUserInfo);
                $("#info_sqsx").html(info.sqlx);
                if("永久" != info.sqlx){
                    $("#info_sqsx_ksjzsj").html(info.sqsxKssj + " -- " + info.sqsxJzsj);
                }else{
                    $("#info_sqsx_ksjzsj").html("");
                }
                $("#info_props").html(info.props);
                $("#info_props").attr("title",info.props);
                $('#assignInfoDiv').dialog({
                    width: '600',
                    height: '300',
                    modal: true,
                    title: "详情"
                });
            }
        },
        error: function (data) {
            $.messager.alert("提示","请求错误", 'info');
        }
    });

}