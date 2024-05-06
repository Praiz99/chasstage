<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加文件服务</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<script type="text/javascript"
	src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/jdone/js/com/frws/frwsFileForm.js"></script>
<script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<div class="form-panel">
		<div class="panel-top" style="margin: 0 5px 0 5px">
			<div class="tbar-title">
				<span class="tbar-label">添加文件服务</span>
			</div>
		</div>
		<form id="mainForm" name="mainForm" method="post">
			<div class="panel-body">
				<input type="hidden" id="id" name="id" value="${frws.id}">
				<table class="table_form" cellpadding="0" cellspacing="0"
					width="80%">
					<tr>
						<th width="20%"><font color="red">*&nbsp;应用名称:</font></th>
						<td><input class="required" type="text" width="400px"
							name="appName" value="${frws.appName}"></input></td>
						<th width="20%"><font color="red">*应用访问根路径:</font></th>
						<td><input class="required" type="text" width="400px" style="width:200px"
							name="appRootUrl" value="${frws.appRootUrl}"></input></td>
					</tr>
					<tr>
						<th width="20%"><font color="red">*&nbsp;应用标识:</font></th>
						<td><input class="required" type="text" width="400px" 
							name="appMark" value="${frws.appMark}"></input></td>
						<th width="20%"><font color="red">*&nbsp;服务端根路径:</font></th>
						<td><input class="required" type="text" width="400px" style="width:200px"
							name="serverRootUrl" value="${frws.serverRootUrl}"></input></td>
					</tr>
					<tr>
						<th width="20%">应用描述:</th>
						<td><input class="required" type="text" width="400px"
							name="appDesc" value="${frws.appDesc}"></input></td>
						<th width="20%">启用:</th>
						<td><input type="radio" name="isEnable" value="1"
							<c:if test="${frws.isEnable=='1'}"> checked='checked'</c:if>
							checked='checked' />是&nbsp; <input type="radio" name="isEnable"
							value="0"
							<c:if test="${frws.isEnable=='0'}"> checked='checked'</c:if> />否
						</td>
					</tr>
					<tr>
						<th width="20%">路由类型:</th>
						<td>
							<select name="routeType">
								<option value="ORG">机构路由</option>
							</select>
						</td>
						<th width="20%">匹配区分值:</th>
						<td>
							<input type="text" name="matchValue" value="${frws.matchValue}" />
						</td>
					</tr>					
				</table>
				<div class="form-btns">
					<input id="btnSubmit" class="button" type="button"
						onclick="submitForm()" value="保 存">&nbsp; <input
						id="btnCancel" class="button" type="button"
						onclick="window.history.go(-1);" value="返 回">
				</div>
			</div>
		</form>
	</div>
</body>
</html>