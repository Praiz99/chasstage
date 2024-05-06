$(function(){
    var resType = $("#resType").val();
    if('page' == resType){
        $("#dataRangTr").remove();
    }
});

function getResult(){
    var tabledata = $("#mainForm").serializeObject();
    return JSON.stringify(tabledata);
}