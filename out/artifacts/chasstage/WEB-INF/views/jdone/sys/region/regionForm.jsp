<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
     <script type="text/javascript" src="${ctx}/static/jdone/js/sys/region/regionForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="regionForm" class="form-horizontal" name="regionForm">
		<input id="id" name="id" type="hidden" value="${sysRegion.id}">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty sysRegion.id?'修改区域':'添加区域'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">区域名称:</label>
					</th>
					<td width="38%">
						<input name="name" class="required" type="text" maxlength="50"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">区域代码:</label>
					</th>
					<td width="38%">
						<input name="code" class="required" type="text" maxlength="50">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">所属地市:</label>
					</th>
					<td width="38%">
						<select name="dsdm">
							<option value="">请选择</option>
							<option value="00">省厅</option>
							<option value="01">杭州</option>
							<option value="02">宁波</option>
							<option value="03">温州</option>								
						</select>
					</td>
					<th width="12%">
						<label class="control-label">区域简称:</label>
					</th>
					<td width="38%">
						<input name="sname" type="text" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">区域简拼:</label>
					</th>
					<td width="38%">
						<input name="spym" type="text" maxlength="50"/>
					</td>
					<th width="12%">
						<label class="control-label">区域全拼:</label>
					</th>
					<td width="38%">
						<input name="fpym" type="text"  maxlength="50"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">系统编号:</label>
					</th>
					<td width="38%" colspan="3">
						<input name="sysCode" id="sysCode" type="text"  maxlength="100"/>
					</td>							
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveRegion()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/sys/region/regionList'">
		</div>				
	</form>
</body>
</html>
