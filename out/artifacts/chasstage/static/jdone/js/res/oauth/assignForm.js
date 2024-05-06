$(function () {
    if ($("#sqlx").val() == null || $("#sqlx").val() == '') {
        $("#sqsxTr").hide();
        $("#sqlx-long").prop("checked", "checked");
    }
    $("input[name='sqlx']").click(function () {
        showOrHideSqsx();
    });
    showOrHideSqsx();
    dicInit();
    var clientMark = $("#clientMark").val();
    if ("client" == clientMark) {
        $("#clientIdDic").attr("disabled", "disabled");
    }
});

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

/**
 * 保存授权
 */
function saveAssign() {
    if (!$("#mainForm").valid()) {
        return;
    }
    var tabledata = $("#mainForm").serializeObject();
    $.ajax({
        url: ctx + '/res/oauth/resource/assign/saveAssign',
        data: tabledata,
        dataType: 'JSON',
        type: 'POST',
        async: true,//异步true,同步false
        success: function (data) {
            $.messager.alert("提示", data.msg, 'info', function () {
                if (data.success) {
                    if ("client" == $("#clientMark").val()) {
                        gobackforType($("#clientId").val());
                    } else {
                        gobackforType($("#resId").val());
                    }
                }
            });
        },
        error: function (data) {

        }
    });

}

function gobackforType(id) {
    if ("client" == $("#clientMark").val()) {
        window.location.href = ctx + '/res/oauth/client/clientAssignList?clientId=' + id;
    } else {
        window.location.href = ctx + '/res/oauth/resource/assign/assignList?resId=' + id;
    }
}