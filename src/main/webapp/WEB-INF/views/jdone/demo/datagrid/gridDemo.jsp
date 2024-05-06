<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>数据列表demo</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/com/css/search-grid-all.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/com/com.datagrid.all.js"></script>
	<script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
		var gridManager;
		$(function() {
	
			// 查询控件
			var searchOptions = {
				showType : '2',
				lineNum : 2,
				isHistory : false,
				types : [ 2 ],
				dataAction : 'server',
				fields : [ {
					name : 'name',
					display : '姓名',
					op : 'like',
					editor : {
						type : 'string'
					}
				}, {
					name : 'idCard',
					display : '身份证号',
					op : 'like',
					editor : {
						type : 'string'
					}
				}, {
					name : 'createTime',
					display : '添加时间',
					op : 'greater',
					type:'date',
					format:'yyyy-MM-dd HH:mm:ss',
					editor : {
						type : 'date'
					}
				}]
			};
	
			//右上角的快速筛选
			/*var quickFilters = [ {
				"isGroup" : true,
				"children" : [ {
					"isDefault" : true,
					"isGroup" : false,
					"label" : "全部",
					"mark" : "qb"
				}, {
					"isDefault" : false,
					"isGroup" : false,
					"label" : "分组1-筛选1",
					"mark" : "MJ_SFZH"
				} ],
				"label" : "分组一",
				"mark" : "fzy"
			}, {
				"isGroup" : true,
				"children" : [ {
					"isDefault" : false,
					"isGroup" : false,
					"label" : "分组2-筛选1",
					"mark" : "RYZT"
				}, {
					"isDefault" : false,
					"isGroup" : false,
					"label" : "分组2-筛选2",
					"mark" : "dcs"
				} ],
				"label" : "分组二",
				"mark" : "fze"
			} ];*/
			var quickFilters = [];
			// 列表控件
			var dgOptions = {
				columns : [ {
					display : '姓名',
					name : 'name',
					width : '13%'
				}, {
					display : '登录帐号',
					name : 'loginId',
					width : 'auto'
				}, {
					display : '身份证号',
					name : 'idCard',
					width : '6%'
				}, {
					display : '添加时间',
					name : 'createTime',
					type : 'date',
					format : 'yyyy-MM-dd',
					width : '8%'
				}, {
					display : '电子邮箱',
					name : 'email',
					width : '15%'
				}],
				allowAdjustColWidth : false,
				pageBarType : "1",
				width : "100%",
				pageSize : 10,
				searchAction : "server",
				dataAction : "server",
				rowHeight : 30,
/*				data : {
					Rows : [ {
						'ajbh' : 'ASDFSDFSDF',
						'ajmc' : '李四'
					} ],
					Total : 1
				},*/
				url:"sys/user/getPageData",
				heightDiff : -6,
				frozen : false,
				headerRowHeight : 30,
				alternatingRow : false,
				parms : null,
				isSearch : true,
				checkbox : true
			};
			gridManager = $("#datagrid").ligerDataGrid({
				"dgOptions" : dgOptions,
				"search" : searchOptions,
				"quickFilters" : quickFilters
			});
		});
	</script>
</head>

<body style="overflow-y: hidden" scroll="no">
	<div id='datagrid' />
</body>

</html>
