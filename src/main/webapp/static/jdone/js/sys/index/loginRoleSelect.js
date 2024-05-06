var currentSelected;
var userRoles;
$(function() {
	currentSelected = $("#currentSelected").val();
	userRoles = JSON.parse($("#userRoles").val());
	var isShow = true;
	if(currentSelected && currentSelected.length>0){
		if(userRoles){
			for(var i=0;i<userRoles.length;i++){
				if(userRoles[i].id == currentSelected){
					isShow = false;
					break;
				}
			}
		}
	}
	if(isShow==true){
		roleSelect();
	}
});

//选择用户角色
function roleSelect() {
	var content = '';
	for (var i = 0; i < userRoles.length; i++) {
		if(currentSelected == userRoles[i].id){
			content += '<input type="radio" checked="checked" name="role" class="rdolist" id="'
				+ userRoles[i].id
				+ '"  value="'
				+ userRoles[i].roleId
				+ '"> '
				+ '<label class="rdobox checked">'
				+ '<span class="check-image" style="background: url('+ctx+'/static/jdone/style/sys/imgs/index/input-checked.png);"></span>'
				+ '<span class="radiobox-content">'
				+ userRoles[i].roleName
				+ (userRoles[i].orgName == null ? "" : '('
					+ userRoles[i].orgName + ')') + '</span>' + '</label>';
		}else{
			content += '<input type="radio" name="role" class="rdolist" id="'
				+ userRoles[i].id
				+ '"  value="'
				+ userRoles[i].roleId
				+ '"> '
				+ '<label class="rdobox unchecked">'
				+ '<span class="check-image" style="background: url('+ctx+'/static/jdone/style/sys/imgs/index/input-unchecked.png);"></span>'
				+ '<span class="radiobox-content">'
				+ userRoles[i].roleName
				+ (userRoles[i].orgName == null ? "" : '('
					+ userRoles[i].orgName + ')') + '</span>' + '</label>';
		}
	}
	$("#selectRole").dialog({
		content : content,
		width : '45%',
		height : '45%',
		modal : true,
		title : "选择登录身份",
		buttons : [ {
			text : '确认并设为默认',
			left : 50,
			handler : function() {
				var val = $('input:radio[name="role"]:checked').val();
				var userRid = $('input:radio[name="role"]:checked').attr("id");
				if (!val) {
					$.messager.alert('提示', "请选择登录角色!");
					return false;
				}
				$.ajax({
					url : ctx + '/loginRole/set',
					data : {
						urid : userRid,
						dft : true
					},
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							window.location.href = data.redirectUrl;
						}
					}
				});
				$("#selectRole").dialog("close");
			}
		}, {
			text : '确认',
			left : 50,
			handler : function() {
				var val = $('input:radio[name="role"]:checked').val();
				var userRid = $('input:radio[name="role"]:checked').attr("id");
				if (!val) {
					$.messager.alert('提示', "请选择登录角色!");
					return false;
				}
				$.ajax({
					url : ctx + '/loginRole/set',
					data : {
						urid : userRid
					},
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							window.location.href = data.redirectUrl;
						}

					}
				});
				$("#selectRole").dialog("close");
			}
		} ]
	});

	// 隐藏窗口关闭按钮
	$("#selectRole").prev().find("div[class='panel-tool']").bind('click',
			function() {
				$("#selectRole").dialog("close");
			});
	// 绑定角色单选框点击事件
	$(".rdobox").click(function() {
		$(this).prev().prop("checked", "checked");
		rdochecked('rdolist');
	});
}

rdochecked = function(tag) {
	$('.' + tag)
			.each(
					function(i) {
						var rdobox = $('.' + tag).eq(i).next();
						if ($('.' + tag).eq(i).prop("checked") == false) {
							rdobox.removeClass("checked");
							rdobox.addClass("unchecked");
							rdobox
									.find(".check-image")
									.css("background",
											"url("+ctx+"/static/jdone/style/sys/imgs/index/input-unchecked.png)");
						} else {
							rdobox.removeClass("unchecked");
							rdobox.addClass("checked");
							rdobox
									.find(".check-image")
									.css("background",
											"url("+ctx+"/static/jdone/style/sys/imgs/index/input-checked.png)");
						}
					});
};

function openUser(){
	$('#openProcessList')[0].src = ctx + "/sys/user/updateUserPassWord";
	$('#processDialog').dialog('open');
}
function closeDialog(){
	$('#processDialog').dialog("close");
}
