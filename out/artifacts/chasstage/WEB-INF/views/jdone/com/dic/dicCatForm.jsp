<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
     <script type="text/javascript" src="${ctx}/static/jdone/js/com/dic/dicCatForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="dicCatForm" name="dicCatForm">
		<input id="id" name="id" type="hidden" value="${dicCat.id}">
		<div class="form-panel">
			<div class="panel-top">
				<div class="tbar-title">
					<span>${not empty dicCat.id?'修改分类':'添加分类'}</span>	
				</div>
			</div>
			<div class="panel-body">
				<table class="table_form">
					<tbody>
						<tr>
							<th width="12%" class="must">
								<span style="padding-right:4px">*</span>分类名称：
							</th>
							<td width="38%">
								<input name="catName" class="required" type="text" maxlength="50" value="${dicCat.catName}"/>
							</td>
							<th width="12%" class="must">
								<span style="padding-right:4px">*</span>分类标识：
							</th>
							<td width="38%">
								<input name="catMark" class="required" type="text" maxlength="30" value="${dicCat.catMark}"/>
							</td>
						</tr>
						<tr>
							<th width="12%" class="must">
								<span style="padding-right:4px">*</span>分类描述：
							</th>
							<td colspan="3">
								<textarea name="catDesc" class="required" type="text" style="height: 50px;" maxlength="200">${dicCat.catDesc}</textarea>
							</td>
						</tr>					
					</tbody>
				</table>
				<div class="form-btns">
					<input id="btnSubmit" class="button" type="button" value="保 存" onclick="saveCat()">&nbsp;
					<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/com/dic/dicCatList'">
				</div>				
			</div>
		</div>
	</form>
</body>
</html>
