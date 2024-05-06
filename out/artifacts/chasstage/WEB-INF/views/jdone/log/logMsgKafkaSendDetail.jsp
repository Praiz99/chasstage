<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="jdoneLogMsgKafkaSendForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width: 85px;">日志详情</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">应用名称:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaSend.appName}</label>
					</td>
					<th width="12%">
						<label class="control-label">应用标识:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaSend.appMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">发送服务器IP:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaSend.sendIp}</label>
					</td>
					<th width="12%">
						<label class="control-label">消息类型:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaSend.msgType}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">消息容器:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaSend.topic}</label>
					</td>
					<th width="12%">
						<label class="control-label">发送时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${jdoneLogMsgKafkaSend.sendTime}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">消息内容:</label>
					</th>
					<td colspan="3">
						<textarea readonly="readonly" style="width:85%;height: 100px;">${jdoneLogMsgKafkaSend.msgContent}</textarea>>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/log/kafka/logMsgKafkaSendList'">
		</div>
	</form>
</body>
</html>
