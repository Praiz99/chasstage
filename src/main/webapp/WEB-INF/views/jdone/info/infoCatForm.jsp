<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
<link href="${ctx}/static/framework/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/framework/plugins/uploadify/jquery.uploadify.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/plugins/com/file.upload.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/info/infoCatForm.js"></script>
<script type="text/javascript">
    	var ctx = '${ctx}';
</script>
</head>
<body>
	<!-- 菜单操作 开始 -->
		<form id="catForm" class="form-horizontal"  method="post">
			<!-- 隐藏域   开始-->
			<input type="hidden" name="id" id="id" value="${infoCat.id}"/>
			<div id="main">
			<h2 class="subfild">
	        	<span  style="width:150px">${not empty infoCat.id?'修改分类':'添加分类'}</span>
	        </h2>
            </div>
			<!-- 隐藏域  结束 -->
			<table class="table" cellpadding="0" cellspacing="0" width="99%">
				<tr>
					<th width="12%"><label class="control-label">分类名称:</label></th>
					<td>
					<input type="text" name="name" class="required" value="${infoCat.name}"/>
					<span class="help-inline">
							<font color="red">*</font>
					</span>
					</td>
					<th width="12%"><label class="control-label">分类标识:</label></th>
					<td>		
					<input type="text" name="mark" class="required" value="${infoCat.mark}"/>
					<span class="help-inline">
							<font color="red">*</font>
					</span>
					</td>
				</tr>
				<tr>
					<th width="12%"><label class="control-label">排序序号:</label></th>
					<td colspan="3">
					<input type="text" name="orderNo" class="required" value="${infoCat.orderNo}"/>
					<span class="help-inline">
							<font color="red">*</font>
					</span>
					</td>
				</tr>
		</table>
			<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveInfoCat()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/info/openInfoCatList'">&nbsp;
			</div>
	</form>
</body>
</html>