$(function () {
    // 如果是查看页面，Input都无法编辑
    if ("true" == $("#viewFlag").val()) {
        $("#clientForm input[type='text'],#clientForm input[type='radio'],#clientForm input[type='checkbox'],#clientForm input[type='number']").attr("disabled", "disabled");
        $("#additionalInformation").attr("disabled", "disabled");
        $("#btnSubmit").hide();
        $("#titleSpan").html("查看客户端");
    }

});

/**
 * 保存终端数据
 */
function saveClient() {
    if (!$("#clientForm").valid()) {
        return false;
    }
    var tableData = $("#clientForm").serializeObject();
    //将数组元素连接起来以构建一个字符串
    $.ajax({
        type: "post",
        url: ctx + "/res/oauth/client/saveClient",
        data: tableData,
        dataType: 'json',
        cache: false,
        success: function (data) {
            if (data.success) {
                $.messager.alert('系统提示', data.msg, 'info', function () {
                    window.location.href = ctx + '/res/oauth/client/clientList';
                });
            } else {
                $.messager.alert('系统提示', data.msg, 'error');
            }
        }
    });
}

/**
 * 自动生成secret
 */
function autoGenerateSecret() {
    $.ajax({
        url: ctx + '/res/oauth/client/autoGenerateSecret',
        data: {},
        dataType: 'JSON',
        type: 'POST',
        async: true,//异步true,同步false
        success: function (data) {
            if (data.success) {
                var clientSecret = $("#clientSecret").val();
                if (clientSecret == '' || clientSecret == null) {
                    $("#clientSecret").val(data.data);
                } else {
                    $.messager.confirm("提示", "是否覆盖当前值？", function (r) {
                        if (r) {
                            $("#clientSecret").val(data.data);
                        }
                    });
                }
            }else{
                $.messager.alert("提示", data.msg, 'info');
            }
        },
        error: function (data) {
            $.messager.alert("提示", "请求错误...", 'info');
        }
    });

}
