<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/jeesite.css">   
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/org/initOrgList.js"></script>
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="addorUpwbjkglForm" name="addorUpwbjkglForm" action="save" method="post" >
		<input id="outerServiceId" name="outerServiceId" type="hidden" value="${apiOuter.id}">
		<input name="serviceName" type="hidden" id="serviceName" value="${apiOuter.serviceName}">
		<input name="serviceMark" type="hidden" id="serviceMark" value="${apiOuter.serviceMark}">
		<input name="serviceUrl" type="hidden" id="serviceUrl" value="${apiOuter.serviceUrl}">
		<input name="serviceType" type="hidden" id="serviceType" value="${apiOuter.serviceType}">
		<div class="form-panel">
         <!-- <div class="panel-top">
				<div class="tbar-title">
					<span>生成内部接口</span>	
				</div>
			</div> -->
		<div class="panel-body">
	    	<table class="table_form">
	    	    <tr>
					<th width="32%">接口分组标识:</th>
					<td width="68%">
					<select id="groupId" name="groupId" class="input-xlarge" >
            	      <c:forEach var="app" items="${apiGroups}">
                        <option value="${app.id}">${app.groupName}</option>
                      </c:forEach>
                    </select>
					</td>
				</tr>
		    </table>
	</div>
	</div>	
	</form>
</body>
</html>
