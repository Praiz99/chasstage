<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/utils/jQuery.Hz2Py-min.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/app/appForm.js"></script>
</head>
<body>
	<form id="mainForm" name="mainForm">
		<input id="id" name="id" type="hidden" value="${app.id}">
		<div class="form-panel">
			<div class="panel-body">
				<table class="table_form">
					<tbody>
						<tr>
							<th width="12%">
								应用名称:
							</th>
							<td width="38%">
								<input name="name" id="name" style="width: 97%;" type="text" maxlength="50"  value="${app.name}"/>
							</td>
							<th width="12%">
								应用标识:
							</th>
							<td width="38%">
								<input name="mark" id="mark" onclick="NametoPy();" style="width: 97%;" type="text" maxlength="50"  value="${app.mark}"/>
							</td>
						</tr>
						<tr>
							<th width="12%">
								排序编号:
							</th>
							<td width="38%">
								<input name="orderId" id="orderId" style="width: 97%;" type="text" maxlength="50"  value="${app.orderId}"/>
							</td>
							<th width="12%">
								是否禁用:
							</th>
							<td width="38%">
								<input  name="isDisabled"   id="isDisabled1"
									<c:if test="${app.isDisabled eq 0}"> checked="checked" </c:if>
									checked="checked"
								  type="radio" value="0" maxlength="50"><label for="isDisabled1">否</label>
								&nbsp;&nbsp;
								<input  name="isDisabled"  id="isDisabled2"
									<c:if test="${app.isDisabled eq 1}"> checked="checked" </c:if>
								 type="radio" value="1" maxlength="50"><label for="isDisabled2">是</label>
							</td>
						</tr>
						<tr>
							<th width="12%">
								应用级别:
							</th>
							<td width="38%">
								<select name="appLevel">
									<option value="10" <c:if test="${app.appLevel == 10}">selected</c:if>>省级应用</option>
									<option value="20" <c:if test="${app.appLevel == 20}">selected</c:if>>地市级应用</option>
									<option value="30" <c:if test="${app.appLevel == 30}">selected</c:if>>区县级应用</option>
									<option value="40" <c:if test="${app.appLevel == 40}">selected</c:if>>单位级应用</option>
								</select>
							</td>
							<th width="12%">
								首页地址:
							</th>
							<td width="38%" colspan="3">
								<input name="indexUrl" style="width: 99%;" type="text" id="indexUrl" value="${app.indexUrl}" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
