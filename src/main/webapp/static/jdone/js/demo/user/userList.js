$(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#dg").datagrid({
		url:ctx + "/admin/demo/getUserData",
		width:'53%',
		pagination:true,
		checkOnSelect:true,
		toolbar:initToolbar(),
	    columns:[[
	              {field:'id',align:'center',checkbox:true},
	              {field:'loginId',title:'帐号',align:'center',width:'24%'},
	              {field:'name',title:'姓名',align:'center',width:'24%'},
	              {field:'idCard',title:'身份证号',align:'center',width:'24%'},
	              {field:'csrq',title:'出生日期',align:'center',width:'24%'}
	          ]]		
	});
}

function initToolbar(){
    var toolbar = [{
        text:'Add',
        iconCls:'icon-add',
        handler:function(){
        	window.open(ctx+"/admin/demo/userForm");
        }
    },{
        text:'Cut',
        iconCls:'icon-cut',
        handler:function(){alert('cut');}
    },'-',{
        text:'Save',
        iconCls:'icon-save',
        handler:function(){alert('save');}
    }];
    
    return toolbar;
}