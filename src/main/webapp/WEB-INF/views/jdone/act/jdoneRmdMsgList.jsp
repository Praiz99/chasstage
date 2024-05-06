<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<title>待办消息管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link href="${ctx}/static/jdone/style/act/css/processRmdMsg.css" rel="stylesheet" type="text/css"  />
<script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneRmdMsgList.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body style="overflow: auto;">
	<div id="content" class="rb-list rb-list4">
		<div class="msgtitle">我的消息</div>
		<div class="datagrid-toolbar rb-toolbar rb-toolbar4">
			<ul>
				<li class="rb-toolbar-bg s a" onclick="changeMsgData('ws',this);"><label>文书</label></li>
	            <li class="rb-toolbar-bg m" onclick="changeMsgData('jq',this);"><label>警情</label></li>
	            <li class="rb-toolbar-bg m" onclick="changeMsgData('sla',this);"><label>受立案</label></li>
	            <li class="rb-toolbar-bg m" onclick="changeMsgData('baqry',this);"><label>办案区人员</label></li>            
	            <li class="rb-toolbar-bg m" onclick="changeMsgData('sawp',this);"><label>涉案物品</label></li>
	            <li class="rb-toolbar-bg m" onclick="changeMsgData('aj',this);"><label>案卷</label></li>
	            <li class="rb-toolbar-bg m" onclick="changeMsgData('jdkp',this);"><label>监督考评</label></li>
	            <li class="rb-toolbar-bg m" onclick="changeMsgData('qt',this);"><label>其它</label></li>            
	            <li class="rb-toolbar-bg e" onclick="changeMsgData('tysjbg',this);"><label>通用数据变更</label></li>            
	          </ul>
	    </div>
		<table id="datagrid"></table>
	</div>
</body>
</html>
