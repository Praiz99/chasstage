/**
 * Created by whb on 2017/12/22.
 */
var id = "dailog";
function opendialog(title, url, width, heigth) {
    var content = '<iframe src="' + url + '" width="100%" height="99%" frameborder="0" ></iframe>';
    if ($("#dailog").val() != "") {
        var divContent = '<div id="dailog"></div>';
        $("body").append(divContent);
    }
    $('#dailog').dialog({
        width: width,
        height: heigth,
        modal: true,
        closable: true,
        draggable: true,
        minimizable: false,
        shadow: true,
        maximizable: true,
        content: content,
        title: title,
        onClose: function () {//弹出层关闭事件

        },
        onLoad: function () {//弹出层加载事件
        }
    });
    $('#dailog').window('center');
}
function closedailog() {
    $('#dailog').dialog('close');
    win.dialog('close');
}