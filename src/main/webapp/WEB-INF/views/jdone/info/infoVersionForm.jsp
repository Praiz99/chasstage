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
<script type="text/javascript" src="${ctx}/static/jdone/js/info/infoVersionForm.js"></script>
<script type="text/javascript">
    	var ctx = '${ctx}';
</script>
</head>
<body>
	<!-- 菜单操作 开始 -->
		<form id="versionForm" class="form-horizontal"  method="post">
			<!-- 隐藏域   开始-->
			<input type="hidden" name="id" id="id" value="${infoVersion.id}"/>
			<input type="hidden" name="versionId" id="versionId" value="${versionId}"/>
			<input type="hidden" name="infoId" id="infoId" value="${infoId}"/>
			<div id="main">
			<h2 class="subfild">
	        	<span  style="width:150px">${not empty infoVersion.id?'修改版本':'添加版本'}</span>
	        </h2>
            </div>
			<!-- 隐藏域  结束 -->
			<table class="table" cellpadding="0" cellspacing="0" width="99%">
				<tr>
					<th width="12%"><label class="control-label">资料版本号:</label></th>
					<td>
					<input type="text" name="version" class="required" value="${infoVersion.version}"/>
					<span class="help-inline">
							<font color="red">*</font>
					</span>
					</td>
					<th width="12%"><label class="control-label">是否默认版本:</label></th>
					<td>
						<select id="isDefault" name="isDefault" class="search-textbox keydownSearch" >
							
						        <option value="0" <c:if test="${infoVersion.isDefault==0}">selected = "selected"</c:if>>否</option>
						        <option value="1" <c:if test="${infoVersion.isDefault==1}">selected = "selected"</c:if>>是</option>
						 </select>
					</td>
				</tr>
				
			<tr>	
					<th width="12%"><label class="control-label">版本描述:</label></th>
					<td colspan="3"  style="height:80px;">
					   <textarea  style="width:95%;height: 60px;"  id="versionState"  name="versionState" >${infoVersion.versionState}</textarea>
					</td>
				</tr>
				<tr>
					<th width="12%" ><label class="control-label">资料包:</label></th>
					<td colspan="3">
						<div id='fileUpload' style="padding-top: 8px;"></div>
					</td>
				
				</tr> 
		</table>
			<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveInfoVersion()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/info/infoVersionList?id=${infoId}'">&nbsp;
			</div>
	</form>
</body>
</html>