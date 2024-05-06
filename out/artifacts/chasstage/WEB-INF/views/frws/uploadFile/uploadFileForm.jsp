<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="uploadFileForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width: 85px;">文件信息</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">文件真实名称:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.realName}</label>
					</td>
					<th width="12%">
						<label class="control-label">文件存储名称:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.storeName}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">文件存储路径:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.filePath}</label>
					</td>
					<th width="12%">
						<label class="control-label">文件类型:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.fileType}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">文件大小:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.fileSize}</label>
					</td>
					<th width="12%">
						<label class="control-label">业务类型:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.bizType}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">分组标识:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.groupMark}</label>
					</td>
					<th width="12%">
						<label class="control-label">容器标识:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.storageMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">上传时间:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.operTime}</label>
					</td>
					<th width="12%">
						<label class="control-label">操作对象名称:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.operDxmc}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">文件归属单位名称:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.orgName}</label>
					</td>
					<th width="12%">
						<label class="control-label">文件归属单位代码:</label>
					</th>
					<td width="38%">
						<label>${uploadFile.orgSysCode}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">已授权应用标识:</label>
					</th>
					<td colspan="3">
						<label>${uploadFile.authAppMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">文件客户端标识:</label>
					</th>
					<td colspan="3">
						<label>${uploadFile.clientAppMark}</label>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/uploadFile/uploadFileList'">
		</div>
	</form>
</body>
</html>
