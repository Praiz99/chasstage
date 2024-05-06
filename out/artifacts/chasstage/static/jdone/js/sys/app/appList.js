$(function (){
	initAppDataGrid();
	if (!!window.ActiveXObject || "ActiveXObject" in window){
		//兼容IE给body添加高度。
		$("html").css({"height":"98%","width":"99%","overflow":"hidden"});
		$("body").css({"height":"98%","width":"99%","overflow":"hidden"});
	}
});

var datagrid;
function initAppDataGrid(){
	datagrid = $('#datagrid').datagrid({
		url:"getAppPageData",
		width:'100%',
		pagination:true,
		rownumbers: true,
		checkOnSelect:true,
		singleSelect: false,
		toolbar : initToolbar(),
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'name', title:'应用名称',align:'center',width:'15%'},
	              {field:'mark',title:'应用标识',align:'center',width:'10%'},
	              {field:'indexUrl',title:'首页地址',align:'center',width:'29%'},
	              {field:'orderId',title:'排序编号',align:'center',width:'5%'},
	              {field:'isDisabled',title:'是否禁用',align:'center',width:'5%',formatter:function (value,row,index){
	            	  if(value){
	            		  return "是";
	            	  }else{
	            		  return "否";
	            	  }
	              }},
	              {field:'createTime',title:'添加时间',align:'center',width:'18%'},
	              {field:'1',title:'操作',align:'center',width:'17%',formatter:function (value,row,index){
	            	  if(row.isDisabled){
	            		  return "<a href='javaScript:void(0);' onclick='updateAppById(\""+row.id+"\");'>修改</a> " +
	            		  		" &nbsp;<a href='javaScript:void(0);' onclick='openSelectSocpe(\""+row.id+"\",\""+row.name+"\",\""+row.appLevel+"\");'>范围</a>"+
	            		  	    " &nbsp;<a href='javaScript:void(0);' onclick='disableApp(\""+row.id+"\",\"0\");'>解禁</a>"+
	            		  	    " &nbsp;<a href='javaScript:void(0);' onclick='settingPage(\""+row.id+"\");'>配置首页</a>";
	            	  }
	            	  return "<a href='javaScript:void(0);' onclick='updateAppById(\""+row.id+"\");'>修改</a> " +
	            	  		" &nbsp;<a href='javaScript:void(0);' onclick='openSelectSocpe(\""+row.id+"\",\""+row.name+"\",\""+row.appLevel+"\");'>范围</a>"+
	            	  		" &nbsp;<a href='javaScript:void(0);' onclick='disableApp(\""+row.id+"\",\"1\");'>禁用</a>"+
	            	  		" &nbsp;<a href='javaScript:void(0);' onclick='settingPage(\""+row.id+"\");'>配置首页</a>";
	              }}
	          ]]		
	});
}

function disableApp(id,disableFlag){
	 $.messager.confirm('Confirm','是否禁用/解禁?',function(r){
	    if (r){
	    	$.ajax({ 
				url:'disableApp',
				type:'POST',
				dataType:'json',
				data:{id:id,disableFlag:disableFlag},
				success : function (data){
					$('#datagrid').datagrid('reload');
					if(data.success){
						$.messager.alert('success',data.msg);
					}else{
						$.messager.alert('Warning',data.msg);
					}
				}
			});
	    }
	});
}

function updateAppById(obj){
	var url = 'appForm?id='+obj;
	EasyextDialog(url,'修改应用','60%','60%',true,true);
}

function InvolvedResForm(obj){
	 var url = 'InvolvedResForm?appId='+obj;
	 EasyextDialog(url,'关联资源','60%','80%',true,true);
}

function settingPage(obj){
	$('#hp')[0].src = ctx+'/sys/hpset/SettingAddressList?appId='+obj;
	$('#hpSetDialog').dialog('open');
}

function initToolbar() {
	var toolbar = [{
		text : '新增',
		iconCls : 'icon-add',
		handler : function() {
			EasyextDialog('appForm','添加应用','60%','60%',true,true);
		}
	},
	{
		text : '删除',
		iconCls : 'icon-remove',
		handler : function() {
		var rows = $('#datagrid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("系统提示", "请至少选择一行数据!");
			return false;
		}
		var rids = "";
		$(rows).each(function (i,o){
			rids += o.id+",";
		});
		$.messager.confirm('Confirm','删除后不可恢复,是否删除?',function(r){
			    if (r){
			    	$.ajax({ 
						url:'deleteApp',
						type:'POST',
						dataType:'json',
						data:{ids:rids},
						success : function (data){
							$('#datagrid').datagrid('reload');
							if(data.success){
								$.messager.alert('success',data.msg);
							}else{
								$.messager.alert('Warning',data.msg);
							}
						}
					});
			    }
			});
		}
	}];
	return toolbar;
}

function openSelectDialog(obj){
	var datas = datagrid.datagrid('getRows');
	var applevel = null;
	$(datas).each(function (i,o){
		if(o.id == obj){
			applevel = o.appLevel;
		}
	});
	if(applevel == 0 || applevel == undefined){
		$.messager.alert('提示',"应用级别不符!");
		return false;
	}
	
	EasyextDialog('SelectRange?ranges='+applevel+'&appId='+obj,'选择范围','80%','95%',true,true);
}

//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  ,flag 是否加载按钮组并自处理返回结果,fun 关闭回调事件
var win;
function EasyextDialog(url, title, width, height, shadow,flag,fun) {  
	var ids = Math.round(Math.random()*1000);
    var content = '<iframe id="dialog'+ids+'" name="dialog'+ids+'" src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="'+ids+'" title="' + title + '"></div>';//style="overflow:hidden;"可以去掉滚动条  
    $(document.body).append(boarddiv); 
    if(flag){
    	win = $('#'+ids).dialog({  
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
    				var client = frames.document['dialog'+ids];
    				client.Submit();
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
    	win = $('#'+ids).dialog({  
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
    	$("#"+ids).next().css("text-align","center");
    }
    win.dialog('open');  
}
