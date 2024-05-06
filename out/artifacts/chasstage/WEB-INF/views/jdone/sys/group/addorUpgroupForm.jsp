<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
</head>
<body>
	<form id="addorUpgroupForm" name="addorUpgroupForm" action="savegroup" method="post" >
		<input id="id" name="id" type="hidden" value="${sysGroup.id}">
		<input name="bizMark" type="hidden"  value="${sysGroup.bizMark}">
		<div class="form-panel">
		    <div class="panel-top">
				<div class="tbar-title">
					<span>${bs}通用业务</span>	
				</div>
			</div>
		<div class="panel-body">
	    	<table class="table_form">
	    	<c:if test="${empty  sysGroup.bizMark}">
	    	   <tr>	
				  <th width="12%">
						业务标识:
					</th>
					<td colspan="3">
					 <input name="bizMark" type="text" minlength="1" value="${sysGroup.bizMark}">
					</td>
				</tr>
	    	</c:if>
				<tr>
					<th width="12%">
						分组名称:
					</th>
					<td width="38%">
						<input name="groupName" id="groupName" class="required" type="text"  value="${sysGroup.groupName}">
					</td>
					<th width="12%">
						分组标识:
					</th>
					<td width="38%">
					<input name="groupMark" type="text" id="groupMark" class="required" minlength="1" value="${sysGroup.groupMark}">
					</td>
				</tr>
				<tr>
				  <th width="12%">
						分组描述:
					</th>
					<td colspan="3">
						<textarea name="groupDesc" type="text" style="height: 50px;"  >${sysGroup.groupDesc}</textarea>
					</td>
				</tr>
		</table>
		<%-- <div class="form-btns">
			<input id="btnSubmit" class="button" type="submit" value="保 存">&nbsp;
			<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/api/field/groupList'">
		</div> --%>
	</div>
	</div>	
	</form>
</body>
</html>
