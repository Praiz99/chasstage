var initValue=null;
$(document).ready(function (){
	//子窗口的文本框获取父窗口文本框值
	$("#regsNames").text(parent.$("textarea[name='regsNames']").val());
	$("#usersNames").text(parent.$("textarea[name='usersNames']").val());
	$("#orgsNames").text(parent.$("textarea[name='orgsNames']").val());
	$("#rolesNames").text(parent.$("textarea[name='rolesNames']").val());
	
	$("#regs").text(parent.$("input[name='regs']").val());
	$("#users").text(parent.$("input[name='users']").val());
	$("#orgs").text(parent.$("input[name='orgs']").val());
	$("#roles").text(parent.$("input[name='roles']").val());
	//指派用户
	$("#appointUsers").bind('click',appointUsers);
	
	//指派角色
	$("#appointRoles").bind('click',appointRoles);
	
	//指派机构
	$("#appointOrgs").bind('click',appointOrgs);
	
	//指派区域
	$("#appointRegs").bind('click',appointRegs);
	
	//确定
	$("#confirm").bind('click',function(){
		var obj = buildRecieverObject();
		if(initValue==obj){
			parent.win.dialog('close');  
		}else{
			/*alert(JSON.stringify(obj));*/
			window.parent.$("textarea[name=usersNames]",".security").val(obj.usersNames.toString());
			window.parent.$("input[name=users]",".security").val(obj.users.toString());
			
			window.parent.$("textarea[name=orgsNames]",".security").val(obj.orgsNames.toString());
			window.parent.$("input[name=orgs]",".security").val(obj.orgs.toString());
			
			window.parent.$("textarea[name=rolesNames]",".security").val(obj.rolesNames.toString());
			window.parent.$("input[name=roles]",".security").val(obj.roles.toString());
			
			window.parent.$("textarea[name=regsNames]",".security").val(obj.regsNames.toString());
			window.parent.$("input[name=regs]",".security").val(obj.regs.toString());
			parent.win.dialog('close');  
		}
	});
});

/**
 * 指派用户
 */
function appointUsers(){
	UserDialog({
		isSingle: false,
		idField: "id_card",
		ids:$("#users").val(),
		names:$("#usersNames").val(),		
		callback: function(ids, names) {
			$("#users").val(ids);
			$("#usersNames").val(names);			
		}
	});
}

/**
 * 指派角色
 */
function appointRoles(){
	RoleDialog({
		isSingle: false,
		idField: "code",
		ids:$("#roles").val(),
		names:$("#rolesNames").val(),
		callback: function(ids, names) {
			$("#roles").val(ids);
			$("#rolesNames").val(names);
		}
	});
}

/**
 * 指派机构
 */
function appointOrgs(){
	OrgDialog({
		isSingle: false,
		idField: "sysCode",	
		ids:$("#orgs").val(),
		names:$("#orgsNames").val(),		
		callback: function(ids, names) {
			$("#orgs").val(ids);
			$("#orgsNames").val(names);
		}
	});
}


/**
 * 指派区域
 */
function appointRegs(){
	RegionDialog({
		isSingle: false,
		idField: "code",
		ids:$("#orgs").val(),
		names:$("#regsNames").val(),		
		callback: function(ids, names) {
			$("#regs").val(ids);
			$("#regsNames").val(names);
		}
	});
}
//生成接收对象
function buildRecieverObject(){
	var object = {usersNames:"",users:"",orgsNames:"",orgs:"",rolesNames:"",roles:"",regs:"",regsNames:""};
	var usersStr = $("#users").val();
	var orgsStr = $("#orgs").val();
	var rolesStr = $("#roles").val();
	var regsStr = $("#regs").val();
	
	if(usersStr != '')
	{
		object.users = usersStr;
		object.usersNames = $("#usersNames").val();
	}
	
	if(orgsStr != '')
	{
		object.orgs = orgsStr;
		object.orgsNames = $("#orgsNames").val();
	}
	
	if(rolesStr != '')
	{
		object.roles = rolesStr;
		object.rolesNames = $("#rolesNames").val();
	}
	
	if(regsStr != '')
	{
		object.regs = regsStr;
		object.regsNames = $("#regsNames").val();
	}
	return object;
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}