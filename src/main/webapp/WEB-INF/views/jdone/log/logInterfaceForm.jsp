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
	<form id="logOperForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width:105px;">接口日志详情</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">应用名称:</label>
					</th>
					<td colspan="3">
						<label>${interfaceCall.appName}</label>
					</td>
				</tr>
			
				<tr>
					<th width="12%">
						<label class="control-label">用户名称:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.userName}</label>
					</td>
					<th width="12%">
						<label class="control-label">用户身份证号:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.userIdCard}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">用户单位名称:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.userOrgName}</label>
					</td>
					<th width="12%">
						<label class="control-label">用户单位代码:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.userOrgCode}</label>
					</td>
				</tr>
				
				<tr>
					<th width="12%">
						<label class="control-label">请求IP:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.reqClientIp}</label>
					</td>
					<th width="12%">
						<label class="control-label">请求时间:</label>
					</th>
					<td width="38%">
					<label><fmt:formatDate value="${interfaceCall.reqTime}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">请求接口名称:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.interfaceName}</label>
					</td>
					<th width="12%">
						<label class="control-label">请求结果:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.reqResult}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">失败原因代码:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.errorCode}</label>
					</td>
					<th width="12%">
						<label class="control-label">请求方名称:</label>
					</th>
					<td width="38%">
						<label>${interfaceCall.requester}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">请求参数:</label>
					</th>
					<td colspan="3">
						<textarea readonly="readonly" style="width:85%;height: 100px;">${interfaceCall.reqParams}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/interface/log/logInterfaceList'">
		</div>
	</form>
</body>
</html>
