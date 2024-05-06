<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript"
	src="${ctx}/static/jdone/js/api/field/paramList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form style="height:20%;" id="addorUpparamForm" name="addorUpparamForm" action="saveparam" method="post" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${apiFieldParam.id}">
		<input id="apiId" name="apiId" type="hidden" value="${apiFieldParam.apiId}">
		<div class="form-panel">
         <div class="panel-top">
				<div class="tbar-title">
					<span>${bs}接口调用参数</span>	
				</div>
			</div>
		<div class="panel-body">
	    	<table class="table_form">
				<tr>
					<th width="12%">
						参数描述:
					</th>
					<td width="38%">
						<input name="paramDesc" class="required" type="text" maxlength="50" value="${apiFieldParam.paramDesc}" >
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						参数名称:
					</th>
					<td width="38%">
						<input name="paramName" class="required" type="text" maxlength="50" value="${apiFieldParam.paramName}">
					</td>
				</tr>
				<tr>
					<th width="12%">
						参数类型:
					</th>
					<td width="38%">
					<select name="paramType" id="paramType" class="input-xlarge" >
							<option value="01"  <c:if test="${apiFieldParam.paramType==01}"> selected</c:if> >字符</option>
							<option value="02" <c:if test="${apiFieldParam.paramType==02}"> selected</c:if> >数字</option>
						</select>
					<%-- <input name="paramType" type="text" id="paramType" maxlength="50" minlength="1" value="${apiFieldParam.paramType}"> --%>
					</td>
					<th width="12%">
						参数默认值:
					</th>
					<td width="38%">
						<input name="defaultValue" type="text" id="serviceMark" maxlength="50" minlength="1" value="${apiFieldParam.defaultValue}">
					</td>
				</tr>
				<tr>
					<th width="12%">
						是否必须:
					</th>
					<td colspan="3">
						<input type="radio" <c:if test="${empty apiFieldParam.isMust}"> checked</c:if> <c:if test="${apiFieldParam.isMust==0}"> checked</c:if> name="isMust" value="0" > 否
                        <input type="radio"  <c:if test="${apiFieldParam.isMust==1}"> checked</c:if> name="isMust" value="1"> 是
					</td>
				</tr>
		</table>
		<div class="form-btns">
			<input id="btnSubmit" class="button" type="button" onclick="saveparam()" value="保 存">&nbsp;
		    <input id="btnCancel" class="button" type="button" value="返 回" onclick="history.back();"><%-- window.location.href='${ctx}/api/inner/innerList' --%>
		</div>
		</div>
	</div>	
	</form>
	<br/><br/><br/>
	<div id="content" class="row-fluid" style="height:70%;overflow:auto;">
		<table id="datagrid" ></table>
	</div>
</body>
</html>
