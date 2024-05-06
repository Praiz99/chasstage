<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件管理</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/devops/app/version/fileChangeList.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
<script type="text/javascript">
	var ctx = '${ctx}';
	var filePath = '${filePath}';
	var fileName = '${fileName}';
	var appname = '${appname}';
	var appMark = '${appMark}';
</script>
<style type="text/css"> 
	.dialog-button { 
		padding: 5px; 
		text-align: center; 
	} 
</style>
</head>
<body  class="easyui-layout new-dialog" style="overflow: hidden">
	<!-- 菜单树  开始 -->
	<div class="easyui-layout" id="layout" style="width:100%;height:80%;">
		<div id="p" data-options="region:'west'" title="打包文件列表" style="width:35%;height:80%;overflow: hidden;border-style: none">
			<ul id="menuTree" class="ztree" style="width:98%;height:450px;background-color:white"></ul>
		</div>
		<div data-options="region:'center'" title="待删除文件" style="width:100%;height:80%;border-style: none;overflow: hidden">
			<form id="upgradeRecordForm" class="form-horizontal">
				<input name="id" id="id" type="hidden"  value="${appVersionId}">
				<table class="table" style="margin-top: 10px;">
					<tbody>
						<tr>
							<td style="border-style: none;">
								<textarea name="delFilePathList" style="width:95%;height:450px">${delFilePathList }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<div align="center" style="width:100%; height:2.8em; margin:0 auto; overflow:hidden; position: fixed; bottom:0;">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveFilePath()">&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保存并下载" onclick="sdFilePath()">&nbsp;
	</div>
	<div id="selectApp" ></div>
	<!-- 菜单树 结束 -->
</body>
</html>