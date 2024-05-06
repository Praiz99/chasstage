function openSelectSocpe(resMark, resName, applevel) {
	var resType = $("#resType").val();
	if (applevel == "10") {
		$.messager.alert('提示', "省级别不需设置应用范围!");
		return false;
	}
	var showTypes = "";
	if(applevel == "20"){
		showTypes = "city";
	}else if(applevel == "30"){
		showTypes = "reg";
	}else if(applevel == "40"){
		showTypes = "org";
	}
	var url=ctx + '/sys/per/range/SelectRange?resMark=' + resMark
	+ '&resName=' + resName + '&showTypes=' + showTypes + '&resType=' + resType;
	url = encodeURI(url);
	SelectSocpeDialog(url, '选择范围', '80%', '95%', true, true);
}

var win;
function SelectSocpeDialog(url, title, width, height, shadow, flag, fun) {
	var ids = Math.round(Math.random() * 1000);
	var content = '<iframe id="dialog'
			+ ids
			+ '" name="dialog'
			+ ids
			+ '" src="'
			+ url
			+ '" width="100%" height="99%" frameborder="0" scrolling="no" ></iframe>';
	var boarddiv = '<div id="' + ids + '" title="' + title + '" style="overflow:hidden;"></div>';//style="overflow:hidden;"可以去掉滚动条  
	$(document.body).append(boarddiv);
	if (flag) {
		win = $('#' + ids).dialog({
			content : content,
			width : width,
			height : height,
			modal : shadow,
			title : title,
			onClose : function() {
				//关闭后事件
				if (fun) {
					fun();
				}
			},
			buttons : [ {
				text : '保存',
				left : 50,
				handler : function() {
					var client = frames.document['dialog' + ids];
					client.Submit();
				}
			}, {
				text : '关闭',
				left : 50,
				handler : function() {
					win.dialog('close');
				}
			} ]
		});
	} else {
		win = $('#' + ids).dialog({
			content : content,
			width : width,
			height : height,
			modal : shadow,
			title : title,
			onClose : function() {
				//关闭后事件
				if (fun) {
					fun();
				}
			}
		});
	}
	if (flag) {
		$("#" + ids).next().css("text-align", "center");
	}
	win.dialog('open');
}