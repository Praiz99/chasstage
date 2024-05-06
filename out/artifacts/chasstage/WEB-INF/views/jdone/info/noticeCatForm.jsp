<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/info/noticeCatForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>

	<form name="noticeCatForm"  id="noticeCatForm" method="post" ><!-- enctype="multipart/form-data"  -->
	<div class="form-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span>${not empty notice.id?'修改公告分类':'添加公告分类'}</span>
			</div>
		</div>
		<div class="panel-body" style="overflow: hidden;">
		<table class="table_form">
			<tbody>
				<tr>
					<th width="20%" class="must"><span style="padding-right:4px">*</span>分类标识:</th>
					<td width="80%">
					<input name="mark" id="mark" class="required" type="text" maxlength="50" value="${noticeCat.mark }"> </td>
				</tr>
				<tr>
					<th width="20%" class="must"><span style="padding-right:4px">*</span>分类名称:</th>
					<td width="80%">
						<input name="name" id="name" class="required" type="text" maxlength="50" value="${noticeCat.name }">
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
		<div class="form-btns" align="center"style="padding-left: 0px;height: 30px">
			<input id="dataFormSave" class="button" type="button" value="确 定" onclick="saveCat()">&nbsp; 
			<input id="btnSubmit" class="button" type="button" value="返 回" onclick="window.location.href='${ctx }/info/notice/noticeCatList'">
		</div>
	
</div>
		<input type="hidden" id="id" name="id" value="${notice.id }"/>  
</form>
</body>
</html>