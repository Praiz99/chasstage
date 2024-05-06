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
    	
    	function saveFilePath (){
    		var fileChangeForm = $("#upgradeRecordForm").serializeObject();
            $.ajax({
       			type: "post",
       			url: ctx+"/app/version/updateVer",
       			data: fileChangeForm,
       			dataType: 'json',
       			cache: false,
       			success: function(data) {
       				if(data.success){
       					location.reload();
       				}else{
       					alert(data.msg);
       				}
       			}
       		});
    	}
    </script>
</head>
<body>

	<body  class="easyui-layout new-dialog" style="overflow: hidden">
	<form id="upgradeRecordForm" class="form-horizontal">
				<input name="id" id="id" type="hidden"  value="${opsAppUpgradeRecord.id}">
	<!-- 菜单树  开始 -->
	<div class="easyui-layout" id="layout" style="width:100%;height:100%;">
		<div id="p" data-options="region:'west'" title="打包文件列表" style="width:45%;height:80%;overflow: hidden;border-style: none">
				<table class="table" style="margin-top: 10px;">
					<tbody>
						<tr>
							<td style="border-style: none;">
								<textarea name="filePathList" style="width:95%;height:420px;">${filePathList }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			
		</div>
		<div data-options="region:'center'" title="待删除文件" style="width:100%;height:80%;border-style: none;overflow: hidden">
				<table class="table" style="margin-top: 10px;">
					<tbody>
						<tr>
							<td style="border-style: none;">
								<textarea name="delFilePathList" style="width:95%;height:420px;">${delFilePathList }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
		</div>
	</div>
	</form>
	<div align="center" style="width:100%; margin:20 auto; overflow:hidden; position: fixed; bottom:0;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveFilePath()">&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="button"value="关 闭" onclick="window.parent.closeDialog();">&nbsp;
	</div>
</body>
</body>
</html>
