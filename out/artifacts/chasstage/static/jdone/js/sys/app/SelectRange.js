var regcode = new Array(),orgcode = new Array(),dreg = new Array(),dorg = new Array(),dcity = new Array();
var regold = new Array(),orgold = new Array();
var showTypes;
$(function (){
	InitPageDisplay();
	var regs = $("#regs").val();
	var orgs = $("#orgs").val();
	if(regs != ""){
		$(regs.split(",")).each(function (i,o){
			if(o != ""){
				regold.push(o);
			}
		});
	}
	if(orgs != ""){
		$(orgs.split(",")).each(function (i,o){
			if(o != ""){
				orgold.push(o);
			}
		});
	}
	$("html").css("height","100%");
	$("body").css("height","100%");
});

//初始化范围选择页面
function InitPageDisplay(){
	showTypes = $("#showTypes").val();
	if(showTypes == "" || showTypes == "city"){
		$('#tabs').tabs('add',{
			title: '适用地市',
			content: '<div id="cityChoose" style="padding:20px;height:80%;"></div>',
			closable: false
		});
		$('#tabs').tabs('add',{
			title: '适用区域',
			content: '<div id="regs" style="padding:10px;height:88%;"> '+
			'<a onclick="SelectRegs();" class="easyui-linkbutton" data-options="iconCls:\'icon-add\'">新增</a><a onclick="deleteRegs();"  class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'">删除</a>'+
			'<table id="regsdata"></table></div>',
			closable: false
		});
		$('#tabs').tabs('add',{
			title: '适用机构',
			content: '<div id="orgs" style="padding:10px;height:88%;">'+
			'<a onclick="SelectOrgs();" class="easyui-linkbutton" data-options="iconCls:\'icon-add\'">新增</a><a onclick="deleteOrgs();" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'">删除</a>'+
			'<table id="orgsdata"></table></div>',
			closable: false
		});
		InitCity();
		InitRegs();
		InitOrgs();
	}else if(showTypes == "reg"){
		$('#tabs').tabs('add',{
			title: '适用区域',
			content: '<div id="regs" style="padding:10px;height:88%;">'+
			'<a onclick="SelectRegs();" class="easyui-linkbutton" data-options="iconCls:\'icon-add\'">新增</a><a onclick="deleteRegs();"  class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'">删除</a>'+
			'<table id="regsdata"></table></div>',
			closable: false
		});
		$('#tabs').tabs('add',{
			title: '适用机构',
			content: '<div id="orgs" style="padding:10px;height:88%;">'+
			'<a onclick="SelectOrgs();" class="easyui-linkbutton" data-options="iconCls:\'icon-add\'">新增</a><a onclick="deleteOrgs();" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'">删除</a>'+
			'<table id="orgsdata"></table></div>',
			closable: false
		});
		InitRegs();
		InitOrgs();
	}else if(showTypes == "org"){
		$('#tabs').tabs('add',{
			title: '适用机构',
			content: '<div id="orgs" style="padding:10px;height:88%;">'+
			'<a onclick="SelectOrgs();" class="easyui-linkbutton" data-options="iconCls:\'icon-add\'">新增</a><a onclick="deleteOrgs();" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'">删除</a>'+
			'<table id="orgsdata"></table></div>',
			closable: false
		});
		InitOrgs();
	}
}
//打开区域选择器
function SelectRegs(){
	EasyextDialog( ctx + '/system/regionList?idField=code&hidden=true&isSingle=false','区域','80%','100%',true,true,null,"regs");
}
//打开机构选择器
function SelectOrgs(){
	EasyextDialog( ctx + '/system/sysOrg?idField=code&hidden=true&isSingle=false','机构','80%','100%',true,true,null,"orgs");
}
//初始化
function InitCity(){
	var cityInp = $("#city").val();
	if(cityInp != ""){
		cityInp = cityInp.split(",");
	}
	var city = ZD_SYS_DSDM();
	$(city).each(function (i,o){
		var isHidden=false;
		if(cityInp != ""){
			$(cityInp).each(function (j,k){
				if(o.code == k){
					isHidden=true;
					$("#cityChoose").append('<input type="checkbox" style="margin-left:2px;" checked="checked" onclick="dcityArr(this);" name="city" id="'+o.code+'" title="'+o.name+'" value="'+o.code+'" /><label for="'+o.code+'" style="margin-right:10px;">'+o.name+'</label>');
				}
			});
		}
		if(isHidden==false){
			$("#cityChoose").append('<input type="checkbox" style="margin-left:2px;" name="city" id="'+o.code+'" title="'+o.name+'" value="'+o.code+'" /><label for="'+o.code+'" style="margin-right:10px;">'+o.name+'</label>');
		}
	});
}
//初始化区域选择页面
function InitRegs(){
	$("#regsdata").datagrid({
		url : ctx  + "/system/findPageDate",
		width : '100%',
		height: '90%',
		pagination : true, // 显示分页栏
		rownumbers : true, // 显示每行列号
		checkOnSelect : true, // 复选框标识
		queryParams:{code:"-1"},
		pagination: false,
		emptyMsg: '<span>无记录</span>',
		columns : [ [ 
			{field : 'id',align : 'center',checkbox : true
			}, {field : 'name',title : '区域名称',align : 'center',width : '25%'
			}, {field : 'code',title : '区域代码',align : 'center',width : '25%'
			}, {field : 'dsdm',title : '所属地市',align : 'center',width : '25%'
			}, {field : 'sname',title : '区域简称',align : 'center',width : '24%'
			} 
		] ]
	});
	setTimeout(function (){
		var regsInp = $("#regs").val();
		if(regsInp != ""){
			$("#regsdata").datagrid("load",{code:regsInp});
		}
	},200);
}
//删除区域
function deleteRegs(){
	var rows = $("#regsdata").datagrid("getChecked");
	$(rows).each(function (i,o){
		$(regold).each(function (j,k){
			if(k == o.code){
				regold.splice(j, 1);
			}
		});
		$(regcode).each(function (j,k){
			if(k == o.code){
				regcode.splice(j, 1);
			}
		});
	});
	var code = "";
	if(regcode.length != 0){
		code = regcode.join(",");
	}
	if(regold.length != 0){
		if(code != ""){
			code = code + "," + regold.join(",");
		}else{
			code = regold.join(",");
		}
	}
	if(code != ""){
		$("#regsdata").datagrid("load",{code: code});
	}else{
		$("#regsdata").datagrid("load",{code: "-1"});
	}
}
//初始化机构
function InitOrgs(){
	$("#orgsdata").datagrid({
		url: ctx  + "/system/findPageList",
		width:'100%',
		height:'90%',
		pagination:false,
		rownumbers: true,
		checkOnSelect:true,
		queryParams:{code:"-1"},
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'名称',align:'center',width:'29%'},
	              {field:'sname',title:'简称',align:'center',width:'20%'},
	              {field:'code',title:'代码',align:'center',width:'15%'},
	              {field:'sysCode',title:'系统代码',align:'center',width:'15%'},
	              {field:'createTime',title:'添加时间',align:'center',width:'20%'}
	          ]]		
	});
	
	setTimeout(function (){
		var orgsInp = $("#orgs").val();
		if(orgsInp != ""){
			$("#orgsdata").datagrid("load",{sysCodes:orgsInp});
		}
	},200);
}

//删除机构
function deleteOrgs(){
	var rows = $("#orgsdata").datagrid("getChecked");
	$(rows).each(function (i,o){
		$(orgold).each(function (j,k){
			if(k == o.sysCode){
				orgold.splice(j, 1);
			}
		});
		$(orgcode).each(function (j,k){
			if(k == o.sysCode){
				orgcode.splice(j, 1);
			}
		});
	});
	var sysCodes = "";
	if(orgcode.length != 0){
		sysCodes = orgcode.join(",");
	}
	if(orgold.length != 0){
		if(sysCodes != ""){
			sysCodes = sysCodes + "," + orgold.join(",");
		}else{
			sysCodes = orgold.join(",");
		}
	}
	if(sysCodes != ""){
		$("#orgsdata").datagrid("load",{sysCodes:sysCodes});
	}else{
		$("#orgsdata").datagrid("load",{sysCodes:"-1"});
	}
}

var win,regs,orgs;
function EasyextDialog(url, title, width, height, shadow,flag,fun,type) {  
	var ids = Math.round(Math.random()*1000);
    var content = '<iframe id="org'+ids+'" name="org'+ids+'" src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="'+ids+'" title="' + title + '"></div>';//style="overflow:hidden;"可以去掉滚动条  
    $(parent.document.body).append(boarddiv); 
    if(flag){
    	win = parent.$('#'+ids).dialog({  
    		content: content,  
    		width: width,  
    		height: height,  
    		modal: shadow,  
    		title: title,  
    		onClose: function () {  
    			//关闭后事件
    			if(fun){
    				fun();
    			}
    		},
    		buttons:[{ 
    			text:'保存', 
    			iconCls:'icon-save', 
    			left:50,
    			handler:function(){ 
    				var client = parent.frames.document['org'+ids];
    				if(type == "regs"){
    					regs = null;
    					regs = client.$("#datagridSec").datagrid("getChecked");
    					$(regs).each(function (i,o){
    						if(!regcode.contains(o.code) && !regold.contains(o.code)){
    							regcode.push(o.code);
    						}
    					});
    					$("#regsdata").datagrid('load',{code:regcode.join(",")+","+regold.join(",")});
    				}
    				if(type == "orgs"){
    					orgs = null;
    					orgs = client.$("#divSec").datagrid("getChecked");
    					$(orgs).each(function (i,o){
    						if(!orgcode.contains(o.sysCode) && !orgold.contains(o.sysCode)){
    							orgcode.push(o.sysCode);
    						}
    					});
    					$("#orgsdata").datagrid('load',{sysCodes:orgcode.join(",")+","+orgold.join(",")});
    				}
    				win.dialog('close');
    			}
    		},
    		{ 
    			text:'关闭', 
    			iconCls:'icon-cancel', 
    			left:50,
    			handler:function(){
    				win.dialog('close');  
    			}
    		}
    		]
    	});  
    }else{
    	win = parent.$('#'+ids).dialog({  
    		content: content,  
    		width: width,  
    		height: height,  
    		modal: shadow,  
    		title: title,  
    		onClose: function () {  
    			//关闭后事件
    			if(fun){
    				fun();
    			}
    		}
    	});  
    }
    if(flag){
    	parent.$("#"+ids).next().css("text-align","center");
    }
    win.dialog('open');  
    return ids;
}


//表单提交
function Submit() {
	var resType = $("#resType").val();
	var resName = $("#resName").val();
	var resMark = $("#resMark").val();
	var cityMarks = new Array();
	var regsMarks = new Array();
	var orgsMarks = new Array();
	var cityData = $("input[name='city']:checked");
	
	var regsData = $("#regsdata").datagrid("getData");
	
	var orgsData = $("#orgsdata").datagrid("getData");
	//验证数组中的数据，判断是否勾选了类似数据
	if(cityData.length > 0){
		for(var i =0;i < cityData.length;i ++){
			if(regsData.total > 0){
				for(var j =0;j < regsData.total;j++){
					if(typeof(regsData.rows[j].code)!=undefined && typeof(regsData.rows[j].code)!="undefined"){
						var code = regsData.rows[j].code;
						if(strContains(code,cityData[i].defaultValue)){
							parent.$.messager.alert('提示', '适用区域中选择的"' + regsData.rows[j].name + '"存在已勾选的适用地市"' + cityData[i].title + '"中！');
							return false;
						}
					}
				}
			}
			if(orgsData.total > 0){
				for(var j =0;j < orgsData.total;j ++){
					var sysCode = orgsData.rows[j].sysCode;
					if(strContains(sysCode,cityData[i].defaultValue)){
						parent.$.messager.alert('提示', '适用机构中选择的"' + orgsData.rows[j].name + '"存在已勾选的适用地市"' + cityData[i].title + '"中!');
						return false;
					}
				}
			}
			
		}
	}else{
		if(typeof(regsData)!="undefined"){
			if(regsData.total > 0 && orgsData.total > 0){
				for(var i =0;i < regsData.total;i ++){
					for(var j =0;j < orgsData.total;j ++){
						var sysCode = orgsData.rows[j].sysCode;
						if(strContains(sysCode,regsData.rows[i].code)){
							parent.$.messager.alert('提示', '适用机构中选择的"' + orgsData.rows[j].name + '"已存在适用区域中选择的"' + regsData.rows[i].name + '"中!');
							return false;
						}
					}
				}
			}
		}
		
	}
	//把页面上选择的地市，区域，机构放入数组中
	if(cityData.length >0){
		$(cityData).each(function (i,o){
			cityMarks.push($(o).val());
		});
	}
	if(typeof(regsData)!="undefined"){
		if(regsData.total > 0){
			$(regsData.rows).each(function (i,o){
				regsMarks.push(o.code);
			});
		//	regsMarks.push(regcode);
		//	regsMarks.push(regold);
		}
	}
	if(orgsData.total > 0){
		$(orgsData.rows).each(function (i,o){
			orgsMarks.push(o.sysCode);
		});
		//orgcode
		//orgsMarks.push(orgcode);
		//orgsMarks.push(orgold);
	}
	$.ajax({
		url : ctx + '/sys/per/range/saveRange',
		dataType : 'json',
		type : 'POST',
		data : {
			resType : resType,
			resMark : resMark,
			resName : resName,
			rangeMarks :cityMarks.join(",")+","+regsMarks.join(",")+","+orgsMarks.join(",")
		},
		success : function(data) {
			if(data.success){
				parent.$.messager.alert('提示', data.msg);
				parent.win.dialog('close');
				parent.$.messager.progress('close');
			}else{
				parent.$.messager.alert('提示', data.msg);
			}
		}
	});
}

function dcityArr(obj){
	var flag =  $(obj).is(":checked");
	if(!flag){  //取消勾选
		dcity.push(getUrlParam("appId")+":"+$(obj).val());
	}else{
		$(dcity).each(function (i,o){
			if(getUrlParam("appId")+":"+$(obj).val() == o){
				dcity.splice(i, 1);
			}
		});
	}
}

Array.prototype.contains = function (obj) {
	var i = this.length;
	while (i--) {
		if (this[i] === obj) {
			return true;
		}
	}
	return false;
}

//判断b字符串是否包含c字符串
function strContains(b,c){
	if(b != "" && c != ""){
		if(b.indexOf(c)!=-1){
			return true;
		}else{
			return false;
		}
	}
}




