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
	<form id="jdoneLogMsgKafkaReceiveForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width: 85px;">日志详情</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">消息发送服务器IP:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.sendIp}</label>
					</td>
					<th width="12%">
						<label class="control-label">消息发送应用标识:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.sendAppMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">消息发送应用名称:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.sendAppName}</label>
					</td>
					<th width="12%">
						<label class="control-label">消息发送时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${jdoneLogMsgKafkaReceive.sendTime}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">消息容器:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.topic}</label>
					</td>
					<th width="12%">
						<label class="control-label">消息ID:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.msgId}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">接收服务器IP:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.recIp}</label>
					</td>
					<th width="12%">
						<label class="control-label">消息接收应用标识:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.recAppMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">消息接收应用名称:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.recAppName}</label>
					</td>
					<th width="12%">
						<label class="control-label">接收时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${jdoneLogMsgKafkaReceive.recTime}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">接收客户端分组:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.recClientGroup}</label>
					</td>
					<th width="12%">
						<label class="control-label">接收客户端id:</label>
					</th>
					<td width="38%">
						<label>${jdoneLogMsgKafkaReceive.recClientId}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">消息类型:</label>
					</th>
					<td colspan="3">
						<label>${jdoneLogMsgKafkaReceive.msgType}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">执行参数:</label>
					</th>
					<td colspan="3">
						<textarea readonly="readonly" style="width:85%;height: 100px;">${jdoneLogMsgKafkaReceive.msgContent}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/log/kafka/logMsgKafkaReceiveList'">
		</div>
	</form>
</body>
</html>
