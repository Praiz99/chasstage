$(document).ready(function(){
	initDataGrid();
});

function initDataGrid(){
	$("#datagrid").datagrid({
		url : ctx + "/act/pendTask/getrmdMsgData?bizId="+ $("#bizId").val() + "&bizType=" + $("#bizType").val(),
		width : '100%',
		pagination : true,
		rownumbers : true,
		checkOnSelect : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [
				{
					field : 'recObjName',
					title : '姓名',
					align : 'center',
					width : '10%'
				},
				{
					field : 'recObjMark',
					title : '身份证号',
					align : 'center',
					width : '15%'
				},
				{
					field : 'recOrgName',
					title : '所属单位',
					align : 'center',
					width : '20%'
				}, {
					field : 'sendObjName',
					title : '发送人',
					align : 'center',
					width : '10%'
				}, {
					field : 'sendOrgName',
					title : '发送人单位',
					align : 'center',
					width : '20%'
				}, {
					field : 'createTime',
					title : '发送时间',
					align : 'center',
					width : '15%'
				}, {
					field : 'isMobileRmd',
					title : '短信提醒',
					align : 'center',
					width : '10%',
					formatter : function(value, row, index) {
						if(row.isMobileRmd == "1" ){
							return "是";
						}
						return "否";
					}
				} ] ]
	});
}
