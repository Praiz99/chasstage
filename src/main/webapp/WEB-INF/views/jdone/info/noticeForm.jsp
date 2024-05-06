<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<script src="${ctx}/static/framework/plugins/ckeditor/ckeditor.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <link href="${ctx}/static/framework/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/static/framework/plugins/uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/com/file.upload.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/info/noticeForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>

	<form name="noticeForm"  id="noticeForm" method="post" ><!-- enctype="multipart/form-data"  -->
	<div class="form-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span>${not empty notice.id?'修改公告':'添加公告'}</span>
			</div>
		</div>
		<div class="panel-body" style="overflow: hidden;">
		<table class="table_form">
			<tbody>
				<tr>
					<th width="20%" class="must"><span style="padding-right:4px">*</span>标题:</th>
					<td width="80%">
					<input name="title" id="title" class="required" type="text" maxlength="50" value="${notice.title }"> </td>
				</tr>
				<tr>
					<th width="20%">分类:</th>
					<td width="80%">
						<select id="catId"name="catId" style="width:60px;">
								<option value="" ></option>
						 	<c:forEach items="${noticeCats}" varStatus="noticeCat" var="item" >  
								<option value="${item.id}"  <c:if test="${notice.catId eq item.id}">selected</c:if>>${item.name}</option>
							</c:forEach>  
						</select>
						<span style="line-height:25px">&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="openNoticeCatList()">分类管理</a></span>
					</td>
				</tr>
				<tr>
					<th width="20%">提醒期限:</th>
					<fmt:formatDate value="${rmdStartDate}" var="rmdStartDates" pattern="yyyy-MM-dd HH:mm:ss"/>
					<fmt:formatDate value="${rmdEndDate}" var="rmdEndDates" pattern="yyyy-MM-dd HH:mm:ss"/>
					<td width="80%"><input class="easyui-datetimebox" id="rmdStartDate" name="rmdStartDate"
						style="width:180px" value="${rmdStartDates }"/>
						<span>-</span> 
						<input class="easyui-datetimebox" id="rmdEndDate" name="rmdEndDate"
						style="width:180px" value="${rmdEndDates }"/>
					</td>
				</tr>
				<tr>
				<th width="20%" class="must"><span style="padding-right:4px">*</span>正文:</th>
					<td width="80%">
					<textarea class="ckeditor" name="content" id="content" style="width:95%;height:80px;">${notice.content }</textarea>
					</td>
				</tr>

				<tr>
					<th width="20%">附件：</th>
					<td width="80%">
						<div id='fileUpload' style="padding-top: 8px;"></div>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
		<div class="form-btns" align="center"style="padding-left: 0px;height: 30px">
			<input id="drafSave" class="button" type="button"value="保存草稿">&nbsp; 
			<input id="dataFormSave" class="button" type="button" value="保存并发布">&nbsp; 
			<input id="btnSubmit" class="button" type="button" value="返 回" onclick="window.location.href='${ctx }/info/notice/noticeList'">
		</div>
	
</div>
		<input type="hidden" id="isPublish" name="isPublish" value="${notice.isPublish}" />
		<input type="hidden" id="id" name="id" value="${notice.id }"/>  
		<input type="hidden" id="businessId" name="businessId" value="${businessId}"/>  
</form>
		<div id="noticeCat" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="通知分类" style="width:1000px; height:600px;">
			<iframe scrolling="no" id='openNoticeCat' name="openNoticeCat" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
		</div>
</body>
</html>