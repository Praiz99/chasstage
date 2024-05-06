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
<body style="overflow-y: auto;overflow-x: hidden;">
	<form id="logOperForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width:200px;">版本详情</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">应用名称:</label>
					</th>
					<td width="38%">
						<label>${opsAppUpgradeRecord.appName}</label>
					</td>
					<th width="12%">
						<label class="control-label">应用标识:</label>
					</th>
					<td width="38%">
						<label>${opsAppUpgradeRecord.appMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">版本包名称:</label>
					</th>
					<td width="38%">
						<label>${opsAppUpgradeRecord.packageName }</label>
					</td>
					<th width="12%">
						<label class="control-label">应用版本号:</label>
					</th>
					<td width="38%">
					<label>${opsAppUpgradeRecord.appVersionNo }</label>
					</td>
				</tr>
				
				<tr>
					<th width="12%">
						<label class="control-label">发布时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${opsAppUpgradeRecord.publishTime }" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
					<th width="12%">
						<label class="control-label">上线时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${opsAppUpgradeRecord.onlineTime }" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">升级类型:</label>
					</th>
					<td width="38%">
						<c:if test="${opsAppUpgradeRecord.upgradeType =='full'}">全量更新</c:if>
						<c:if test="${opsAppUpgradeRecord.upgradeType =='update'}">增量更新</c:if>
					</td>
					<th width="12%">
						<label class="control-label">基础版本:</label>
					</th>
					<td width="38%">
						<label>${opsAppUpgradeRecord.appBaseVersionNo }</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">版本描述:</label>
					</th>
					<td colspan="3">
						${packageDesc}
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="关 闭" onclick="window.parent.closeDialog();">
		</div>
	</form>
</body>
</html>
