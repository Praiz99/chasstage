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
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/oper/operForm.js"></script>
</head>
<body>
	<form id="mainForm" name="mainForm"> 
		<input id="id" name="id" type="hidden" value="${oper.id}">
		<div class="form-panel">
			<div class="panel-body">
				<table class="table_form">
					<tbody>
						<tr>
							<th width="12%">
								操作名称:
							</th>
							<td width="78%"> 
								<input class="required" name="name" style="width: 99%;" onblur="CheckedName();" type="text" id="name" value="${oper.name}" />
								<span class="help-inline" style="position: absolute;top: 10%;left: 96%;">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<th width="12%">
								操作代码:
							</th>
							<td width="78%">
								<input type="text" id="mark" name="mark"  onclick="NametoPy();" onblur="CheckedIsalike();" value="${oper.mark}"  style="width: 99%;"/>
								<span class="help-inline" style="position: absolute;top: 20%;left: 96%;">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<th width="12%">
								是否权限:
							</th>
							<td width="78%">
								<input type="radio" name="isEnableAuth" 
									<c:if test="${oper.isEnableAuth == 1}"> checked="checked" </c:if>
								value="1"  checked="checked" />是
								<input type="radio" name="isEnableAuth" 
									<c:if test="${oper.isEnableAuth == 0}"> checked="checked" </c:if>
								value="0" />否
							</td>
						</tr>
						<tr>
							<th width="12%">
								是否禁用:
							</th>
							<td width="78%">
								<input type="radio" name="isDisabled" 
									<c:if test="${oper.isDisabled == 0}"> checked="checked" </c:if>
								value="0" checked="checked" />否
								<input type="radio" name="isDisabled" 
									<c:if test="${oper.isDisabled == 1}"> checked="checked" </c:if>
								value="1" />是
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div style="text-align:center;padding:5px;bottom: 0px;margin-bottom: 0px;position: absolute;width: 97%;">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="Submit();">保存</a>
	    </div>
	</form>
</body>
</html>
