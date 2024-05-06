$(document).ready(function(){
	if(envType!=null && envType!="" && envType=='dev'){
		initDataGrid();
	}else{
		alert("当前模式不支持版本发布");
	}
});

//工具条绑定
function initToolbar() {
	//if(mode=="dev" ){
		var toolbar = [ {
			text : '发布版本',
			handler : function() {
				document.getElementById("addUpgradeRecord").innerHTML='<iframe scrolling="no" id="openAddUpgradeRecord" name="openAddUpgradeRecord" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>';
				$('#openAddUpgradeRecord')[0].src = ctx + "/app/version/addVerForm";
				$('#addUpgradeRecord').dialog('open');
			}
		}
		];

		return toolbar;
//	}
}

function initDataGrid(){
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid({
		url:ctx + "/app/version/getVerData",
		width:'100%',
		pagination : true, // 显示分页栏
		checkOnSelect : true, // 复选框标识
		fitColumns : true,
		toolbar : initToolbar(),
		queryParams:{appMark:data.appMark},
		emptyMsg: '<span>无记录</span>',
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'appMark',title:'应用标识',align:'center',width:'10%'},
	              {field:'appVersionNo',title:'应用版本号',align:'center',width:'13%'},
	              {field:'packageName',title:'版本包名称',align:'center',width:'29%'},
	              {field:'upgradeType',title:'升级类型',align:'center',width:'10%',formatter: 
	            	  function(value,row,index){
	            	  var val="";
	            	  if(value=='full'){
	            		  val="全量";
	            	  }if(value=='update'){
	            		  val="增量";
	            	  }
        			  return val;  
	              }},
	              {field:'publishTime',title:'发布时间',align:'center',width:'17%'},
	              {field:'_operate',title:'操作',align:'center',width:'20%',formatter:formatOper}
	          ]]		
	});
}


//增加修改列
function formatOper(val,row,index){  
	var h='<div style="text-align: left;padding-left: 10px;"><a href="JavaScript:void(0)" onclick="viewLog(&quot;'+row.id+'&quot;)">详情</a>&nbsp;&nbsp;';
	if(row.upgradeType=='update'){
		h+='<a href="JavaScript:void(0)" onclick="fileUpgrade(&quot;'+row.id+'&quot;)">程序打包</a>&nbsp;&nbsp;';	
	}
	h+='<a href="JavaScript:void(0)" onclick="downZipFile(&quot;'+index+'&quot;)">版本包下载</a>&nbsp;&nbsp;';
	if(row.upgradeType=='update'){
		h+='<a href="JavaScript:void(0)" onclick="viewFile(&quot;'+row.id+'&quot;)">变更文件列表</a>';	
	}
	h+='</div>';
    return h;
} 

function viewLog(id){
	document.getElementById("processNotice").innerHTML='<iframe scrolling="no" id="openProcessNotice" name="openProcessNotice" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>';
	$('#openProcessNotice')[0].src = ctx + "/app/version/verForm?id="+id;
	$('#processNotice').dialog('open');
}

function viewFile(id){
	document.getElementById("filePathList").innerHTML='<iframe scrolling="no" id="openFilePathList" name="openFilePathList" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>';
	$('#openFilePathList')[0].src = ctx + "/app/version/filePathForm?id="+id;
	$('#filePathList').dialog('open');
}

function fileUpgrade(id){
	document.getElementById("fileChangeRecord").innerHTML='<iframe scrolling="no" id="openFileChangeRecord" name="openFileChangeRecord" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>';
	$('#openFileChangeRecord')[0].src = ctx + "/app/version/fileChangeRecordForm?appVersionId="+id;
	$('#fileChangeRecord').dialog('open');
}

function downZipFile(index) {   
	$('#datagrid').datagrid('selectRow', index);// 选择一行，行索引从0开始。
	var row = $('#datagrid').datagrid('getRows')[index];//返回第一个被选中的行或如果没有选中的行则返回null。
	if(row.appMark==appname){
		window.open(ctx + "/app/version/download?id="+row.id);
	}else{
		var content =  ' <table border="0" width="99%" class="table-detail"  style="margin: 1px;">'
			+'<tbody>'
			+'<tr style="border: 1px solid #7babcf;">'
			+'<th style="width: 25%; border: 1px solid #7babcf; height: 30px; color: #6F8DC6; background-color: #ebf5ff; font-weight: bold;text-align:right;">待打包源程序路径:&nbsp;</th>'
			+'<td style="border: 1px solid #7babcf;font-family: sans-serif;padding-left:5px;text-align: left">'
			+'<input name="filePath" id="filePath" type="text" style="width:420px" value=""></td></tr>';
		content +='</tbody></table>';
	   	$("#selectApp").dialog({
			content : content,
			width : '50%',
			height : '40%',
			modal : true,
			closable:true,
			title : "设置源程序路径",
			buttons : [ {
				text : '确认',
				left : 50,
				handler : function() {
					var filePath = $("#filePath").val();
					if (filePath == null || filePath == "" || typeof filePath == undefined) {
						$.messager.alert('提示', "请输入路径!");
						return false;
					}
					window.open(ctx + "/app/version/download?id="+row.id+"&realPath="+filePath);
					$("#selectApp").dialog("close");
				}
			} ]
		});
		// 隐藏窗口关闭按钮
		$("#selectApp").prev().find("div[class='panel-tool']").bind('click',
				function() {
					$("#selectApp").dialog("close");
				});
	}
}


function closeDialog(){
	$('#processNotice').dialog("close");
	$('#addUpgradeRecord').dialog("close");
	$('#fileChangeRecord').dialog("close");
	$('#filePathList').dialog("close");
}

//点击查找按钮出发事件
function searchFunc() {
	var data = $("#searchForm").serializeObject();
	$("#datagrid").datagrid('load',data);   
}
jQuery.fn.removeSelected = function() {
    this.val("");
};

//清除查询条件
function ClearQuery() {
	$("#searchForm").form('reset');
}

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

