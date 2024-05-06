$(function () {
    var id = $("#id").val();
    if (id != null && id.length > 0) {
        //回显授权类型
        var authorizedGrantTypesInput = $("#authorizedGrantTypesInput").val();
        var typeArr = authorizedGrantTypesInput.split(",");
        for (i = 0; i < typeArr.length; i++) {
            var type = typeArr[i];
            $("input[type='checkbox'][value='" + type + "']").attr("checked", "checked");
        }
        //回显 自动放行
        var autoapproveInput = $("#autoapproveInput").val();
        if (autoapproveInput != null && autoapproveInput != '') {
            $("input[name='autoapprove'][value='" + autoapproveInput + "']").attr("checked", "checked");
        }
    }
    // 如果是查看页面，Input都无法编辑
    if ("true" == $("#viewFlag").val()) {
        $("#clientForm input[type='text'],#clientForm input[type='radio'],#clientForm input[type='checkbox'],#clientForm input[type='number']").attr("disabled", "disabled");
        $("#additionalInformation").attr("disabled", "disabled");
        $("#btnSubmit").hide();
        $("#titleSpan").html("查看客户端");
    }

});