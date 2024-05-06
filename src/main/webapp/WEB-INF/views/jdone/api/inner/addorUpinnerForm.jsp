<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/api/inner/addorUplicenseForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	$(document).ready(function() {
    		$("#addorUpinnerForm").validate();
    		});
    </script>
</head>
<body>
	<form id="addorUpinnerForm" name="addorUpinnerForm" action="saveinner" method="post" >
		<input id="id" name="id" type="hidden" value="${apiInner.id}">
		<div class="form-panel">
		   <div class="panel-top">
				<div class="tbar-title">
					<span>${bs}内部接口</span>	
				</div>
			</div>
		<div class="panel-body">
	    	<table class="table_form">
				<tr>
					<th width="12%">接口名称:</th>
					<td width="38%">
						<input name="serviceName" class="required" type="text" value="${apiInner.serviceName}" >
					</td>
					<th width="12%">接口标识:</th>
					<td width="38%">
						<input name="serviceMark" id="serviceMark" class="required" type="text" value="${apiInner.serviceMark}">
					</td>
				</tr>
				<tr>
					<th width="12%">接口地址:</th>
					<td width="38%"><input name="serviceUrl" class="required" type="text" value="${apiInner.serviceUrl}"></td>
					<th width="12%">接口分组标识:</th>
					<td width="38%">
					<select id="groupId" name="groupId" class="input-xlarge" >
            	      <c:forEach var="app" items="${apiGroups}">
                        <option value="${app.id}"  <c:if test="${apiInner.groupId==app.id }"> selected</c:if>>${app.groupName}</option>
                      </c:forEach>
                    </select>
					<%-- <input name="serviceGroup" type="text" id="authDesc"  value="${apiInner.serviceGroup}"> --%>
					</td>
				</tr>
				<tr>
				<th width="12%">接口类型:</th>
					<td colspan="3">
						<select name="serviceType" id="serviceType" class="input-xlarge" >
							<option value="01" >请求服务</option>
							<option value="02" >webservice</option>
							<option value="03" >http rest</option>
						</select>
<%-- 						<input name="serviceType" type="text" id="serviceType" maxlength="50" minlength="1" value="${apiInner.serviceType}" >
 --%>					</td>
              <%--  <th width="12%"> 所属应用:</th>
            	<td width="38%">
            	    <select id="appId" name="appId" class="input-xlarge" style="width: 205px;">
            	      <c:forEach var="app" items="${appList}">
                        <option value="${app.id}">${app.name}</option>
                      </c:forEach>
                    </select>
				</td> --%>
				</tr>
				</table>
		<div class="form-btns">
			<input id="btnSubmit" class="button" type="button" onclick="sub()" value="保 存">&nbsp;
			<input id="btnCancel" class="button" type="button" value="返 回" onclick="history.back();"><!-- window.location.href='${ctx}/api/inner/innerList' -->
		</div>
		
		</div>
	</div>	
	</form>
</body>
</html>
