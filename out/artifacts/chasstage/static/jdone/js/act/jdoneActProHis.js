$(document).ready(function(){
	initDataGrid();
});

var resultTypeMap = {"00": "已提交", "01": "同意", "02": "不同意", "03": "退回", "04": "上报"};

function initDataGrid(){
	$("#datagrid").datagrid({
		url : ctx + "/act/pendTask/findProHisList?bizId=" + $("#bizId").val() + "&bizType=" + $("#bizType").val(),
		width : '100%',
		pagination : true,
		rownumbers : true,
		checkOnSelect : true,
		emptyMsg: '<span>无记录</span>',
		columns : [ [
				{
					field : 'proUserIdName',
					title : '处理人',
					align : 'center',
					width : '15%'
				},
				{
					field : 'proOrgSysCodeName',
					title : '处理单位',
					align : 'center',
					width : '20%',
					formatter : function(value, row, index) {
						if(value!=null){
	            			  return "<a title='"+value+"'>"+value+"</a>";
	            		  }else{
	            			  return value;
	            		  }
					}
				},
				{
					field : 'proResultType',
					title : '处理结果',
					align : 'center',
					width : '15%',
					formatter : function(value, row, index) {
						if(row.proType == "02" && value == "01"){
							return resultTypeMap["04"];
						}
						return resultTypeMap[value];
					}
				}, {
					field : 'proOpinion',
					title : '处理意见',
					align : 'center',
					width : '30%',
					formatter : function(value, row, index) {
						if(value!=null){
	            			  return "<a title='"+value+"'>"+value+"</a>";
	            		  }else{
	            			  return value;
	            		  }
					}
				}, {
					field : 'proTime',
					title : '处理时间',
					align : 'center',
					width : '20%'
				} ] ]
	});
}
