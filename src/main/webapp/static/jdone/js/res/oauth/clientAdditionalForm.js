$(function(){
    dicInit();
    //初始化数据
    $("input[name='isRestrictIp']").click(function () {
        showOrHideIpList();
    });
    showOrHideIpList();
});


function showOrHideIpList() {
    var isRestrictIp = $('input[name="isRestrictIp"]:checked').val();
    if (1 == isRestrictIp) {
        $("#ipListTr").show();
    } else {
        $("#ipListTr").hide();
        $("#ipList").val("");
    }
}
function getResult(){
    var tabledata = $("#mainForm").serializeObject();
    var extParams = $("#extParams").val();
    var lines = extParams.split("\n");
    for(var i = 0; i < lines.length; i++){
        try {
            var [key, value] = lines[i].split("=");
            tabledata[key]=value
        } catch (e) {
        }
    }
    return JSON.stringify(tabledata);
}
