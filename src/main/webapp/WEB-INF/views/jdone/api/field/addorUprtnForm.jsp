<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/api/field/rtnList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form style="height:20%;" id="addorUpparamForm" name="addorUpparamForm" action="savertn" method="post" >
		<input id="id" name="id" type="hidden" value="${apiFieldRtn.id}">
		<input id="apiId" name="apiId" type="hidden" value="${apiFieldRtn.apiId}">
        <div class="form-panel">
            <div class="panel-top">
				<div class="tbar-title">
					<span>${bs}接口返回字段</span>	
				</div>
			</div>
		<div class="panel-body">
	    	<table class="table_form">
				<tr>
					<th width="12%">字段描述:</th>
					<td width="38%">
						<input name="fieldDesc" class="required" type="text" maxlength="50" value="${apiFieldRtn.fieldDesc}" >
					</td>
					<th width="12%">字段名称:</th>
					<td width="38%">
						<input name="fieldName" class="required" type="text" maxlength="50" value="${apiFieldRtn.fieldName}">
					</td>
				</tr>
				<tr>
					<th width="12%">字段类型:</th>
					<td width="38%">
					<select name="fieldType" id="fieldType" class="input-xlarge" >
							<option value="01"  <c:if test="${apiFieldRtn.fieldType==01}"> selected</c:if> >字符</option>
							<option value="02" <c:if test="${apiFieldRtn.fieldType==02}"> selected</c:if> >数字</option>
					</select>
					</td>
					<th width="12%">字段默认值:</th>
					<td width="38%">
						<input name="defaultValue" type="text" id="serviceMark" maxlength="50" minlength="1" value="${apiFieldRtn.defaultValue}">
					</td>
				</tr>
		</table>
		<div class="form-btns">
			<input id="btnSubmit" class="button" type="button" onclick="savertn()" value="保 存">&nbsp;
			<input id="btnCancel" class="button" type="button" value="返 回" onclick="history.back();">
			<%-- <c:if test="${fl==0 }">
		    <input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/api/inner/innerList'">
		    </c:if>
		    <c:if test="${fl==1 }">
		    <input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/api/outer/outerList'">
		    </c:if> --%>
		</div>
		</div>
	</div>	
	</form>
	<br/><br/><br/>
	<div id="content" class="row-fluid" style="height:70%;overflow:auto;">
		<table id="datagrid"></table>
	</div>
</body>
</html>
