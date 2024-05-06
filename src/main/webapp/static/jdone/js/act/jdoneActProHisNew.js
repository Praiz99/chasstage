$(document).ready(function(){
	initDataGrid();
});

var resultTypeMap = {"00": "已提交", "01": "同意", "02": "不同意", "03": "退回", "04": "上报", "99": "待处理"};

function initDataGrid(){
	$("#datagrid").datagrid({
		url : ctx + "/act/pendTask/findProHisListNew?bizId=" + $("#bizId").val() + "&bizType=" + $("#bizType").val(),
		width : '100%',
		pagination : true,
		rownumbers : true,
		checkOnSelect : true,
		pagination:true,
        pageSize : 10,
        pageList : [ 10, 20, 40 ],
       // loadFilter : partPurchasePagerFilter,
		emptyMsg: '<span>无记录</span>',
		columns : [ [
				{
					field : 'proOrgSysCodeName',
					title : '处理单位',
					align : 'center',
					width : '25%',
					formatter : function(value, row, index) {
						if(value!=null){
							  return "<span title='"+value+"'>"+value+"</span>";
						  }else{
							  return value;
						  }
					}
				},
				{
					field : 'proUserIdName',
					title : '处理人',
					align : 'center',
					width : '20%',
					formatter : function(value, row, index) {
						if(value!=null){
							if(row.proResultType == "99"){
								return "<span title='"+value+"'>"+getStrIndex(value)+"</span> <a href='javascript::' style='text-decoration:underline;' onclick='changeProUsers("+index+")' >修改</a>";
							}else{
								return "<span title='"+value+"'>"+getStrIndex(value)+"</span>";
							}
	            		  }else{
	            			  return value;
	            		  }
					}
				},
				{
					field : 'proResultType',
					title : '处理结果',
					align : 'center',
					width : '10%',
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
					width : '25%',
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

function partPurchasePagerFilter(data) {
    if (typeof data.length == 'number' && typeof data.splice == 'function') {
        data = {
            total : data.length,
            rows : data
        };
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage : function(pageNum, pageSize) {
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh', {
                pageNumber : pageNum,
                pageSize : pageSize
            });
            dg.datagrid('loadData', data);
        }
    });
    if (!data.originalRows) {
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

function getStrIndex(str){
    var reg = new RegExp(",","gmi");
    var k,b=2;
    while(b-- && (k=reg.exec(str))){}
    var val="";
   if(k!=null && k!="" && k!=undefined){
	   val = str.substring(0,k.index)+".....";
   }else{
	   val=str;
   }
   return  val;
}
function changeProUsers(index) {
	$('#datagrid').datagrid('selectRow',index);// 关键在这里  
	var row = $('#datagrid').datagrid('getRows')[index];
	if (row){  
		var bodyHeight=document.body.clientHeight;
		$("#ReceiveFeedBackDialog").height(bodyHeight*0.8);
		$('#openReceiveFeedBack')[0].src = ctx + "/act/pendTask/changeProUsersForm?bizId="+row.bizId+"&bizType="+row.bizType+"&actKey="+row.actKey+"&bizName="+row.bizName;
		$('#ReceiveFeedBackDialog').dialog('open');
	}  
	/*var url = ctx + "/act/pendTask/changeProUsersForm?bizId="+row.bizId+"&bizType="+row.bizType+"&actKey="+row.actKey+"&bizName="+row.bizName;
	window.open(url);*/
}
