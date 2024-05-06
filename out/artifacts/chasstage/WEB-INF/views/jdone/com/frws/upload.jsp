<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
		<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<link href="${ctx}/static/framework/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/static/framework/plugins/uploadify/jquery.uploadify.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	 <script type="text/javascript"> 
    $(document).ready(function(){
    	paramValue='${ paramValue }';
    });
</script>
<script src="${ctx}/static/jdone/js/com/frws/uploadcs.js" type="text/javascript"></script>
		<script type="text/javascript">
		var obj1;
		$(document).ready(function($){							
			obj1 = $("#fileUpload1").ligerFileUpload({
						auto:false,
						storePath:'test/abc',
						storeType:'0',
						isSystem:false,//当isSystem为false,则会自动去匹配获取上传地址,需在后台配置上传服务模型。并设置相应的匹配规则
						formData:{isViturPath:true},
						completeShowType:'0',
						removeCompleted:true,
						removeTimeout:1
					});
		});
		
		function upload1() {
			obj1.startUpload();
		}
		
	</script>

</head>
<body>
	 	<br />
	 	<br />
	 	<table cellspacing="0" border="0" width="100%">
		 	<tr>
		 		<td align="center">
		 			<div id='fileUpload1'></div>
		 			<input type="button" value="上传" style='width:70px;height:25px;' onclick="upload1()">
		 		</td>
		 	</tr>	
	 	</table>
	</body>
</html>