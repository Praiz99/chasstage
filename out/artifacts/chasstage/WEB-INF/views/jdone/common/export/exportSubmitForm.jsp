<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
	<script type="text/javascript" src="${ctx}/static/framework/utils/json2.js"></script>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/export/css/exportSubmitForm.css">
    <script type="text/javascript" src="${ctx}/static/jdone/js/common/export/exportSubmitForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="exportSubmitForm" name="exportSubmitForm" method="post">
		<input type="hidden" id="exportOriUrl" value='${exportUrl}'>
		<input type="hidden" id="exportScope" value='${exportScope}'>
		<input type="hidden" id="exportColumns" value='${exportColumns}'>
		<input type="hidden" id="exportBizParam" value='${exportBizParam}'>
		<input type="hidden" id="exportSelectData" value='${exportSelectData}'>
		<div class="panel-detail" style="margin: 3px;">
			<table id="nextTable" class="common_table_form">
				<tbody>
					<tr>
						<th>导出范围：</th>
						<td>
							<input name="exportScope" type="radio" value="0">
							<span class="text">所选数据</span>
							<input name="exportScope" type="radio" value="1">
							<span class="text">全部数据</span>
						</td>
					</tr>
					<tr>
						<th>文件类型：</th>
						<td>
							<input name="exportFileType" type="radio" value="xls" checked="checked">
							<span class="text">Excel文档(2003)</span>
							<!-- <input name="exportFileType" type="radio" value="xlsx">
							<span class="text">Excel文档(2007)</span> -->
						</td>
					</tr>
					<tr>
						<th>导出文件名称：</th>
						<td>
							<input type='text' name="exportFileName" id="exportFileName" />
						</td>
					</tr>

					<tr>
						<th>选择导出字段：</th>
						<td>
							<div id="fieldList">
								<table cellpadding="0" cellspacing="0" border="0" style="width: 100%;" class="l-checkboxlist-table">
									<tbody id="fieldCheckbox">
									</tbody>
								</table>
							</div>
						</td>
					</tr>
					<tr>
					<th>验证码：</th>
						<td>
							<input id="veryCode" name="veryCode" type="text"/>       
							<img id="imgObj"  alt="" src="${ctx}/com/export/verifyCode"/>       
							<a href="javascript:;" onclick="changeImg()">换一张</a>       
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
		<input id="btnSubmit" class="btn btn-primary" type="button" value="导出" onclick="exportData()">
		<input id="btnCancel" class="btn" type="button" value="关&nbsp;闭" onclick="closeDialog();">
	</div>
</body>
</html>
