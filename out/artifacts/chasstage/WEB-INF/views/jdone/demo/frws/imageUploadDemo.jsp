<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>照片上传demo</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<%@ include file="/WEB-INF/views/jdone/upload/imgUpload.jsp"%> 
	<script type="text/javascript">
	/*	
	 	//相关参数的详细说明请参uploadify插件照官网:http://www.uploadify.com/documentation
	 
		//可配置属性
		auto
		buttonClass
		buttonCursor
		buttonImage
		buttonText
		checkExisting
		debug
		fileObjName
		fileSizeLimit
		fileTypeDesc
		fileTypeExts
		formData
		height
		itemTemplate
		method
		multi
		overrideEvents
		preventCaching
		progressData
		queueID
		queueSizeLimit
		removeCompleted
		removeTimeout
		requeueErrors
		successTimeout
		swf
		uploader
		uploadLimit
		width
		
		//可触发的事件
		onCancel
		onClearQueue
		onDestroy
		onDialogClose
		onDialogOpen
		onDisable
		onEnable
		onFallback
		onInit
		onQueueComplete
		onSelect
		onSelectError
		onSWFReady
		onUploadComplete
		onUploadError
		onUploadProgress
		onUploadStart
		onUploadSuccess
	*/
	</script>	
	<script type="text/javascript">
			var autoUploadObj;
			var notAutoUploadObj;
			var picLDUploadObj;//照片联动
			var customUploadObj;
			$(document).ready(function($){	
				//系统上传模式，必须要传业务id(bizId)和业务类型(bizType)参数
				autoUploadObj = $("#autoUpload").ligerImageUpload({
					bizId:'1233333',
					bizType:'XYRZP'
				});
				
				notAutoUploadObj = $("#notAutoUpload").ligerImageUpload({
					bizId:'1233333',
					bizType:'XYRZP',
					completeShowType:'1',
					auto:false
				});
				
				onePicUploadObj = $("#onePicUpload").ligerImageUpload({
					bizId:'1234',
					bizType:'XYRZP',
					completeShowType:'2',
					isView:true
				});	
				
				picLDUploadObj = $("#picLDUpload").ligerImageUpload({
					bizId:'1234',
					bizType:'XYRZP',
					completeShowType:'2',
					isView:true,
					operateCallBack:function(g,file){
						onePicUploadObj.setFileData(g.uploadFiles);
					}
				});
				
				//自定义上传模式，必须要指定上传地址，业务id(bizId)和业务类型(bizType)参数不是必须的，可不传
				customUploadObj = $("#customUpload").ligerImageUpload({
					isCustom:true,
					uploader:"后台自己写的controller文件上传地址",//如果用这种模式，请确保上传地址是OK的
					formData:{param1:"参数一",param2:"参数二"}//传给后台的参数
				});
				
			});
			
			function startUpload() {
				notAutoUploadObj.startUpload();
			}
			
	</script>
</head>
<body>
	 	<br />
	 	<br />
	 	<table cellspacing="0" border="0" width="100%">
		 	<tr>
		 		<td align="center">
		 			<span style="font-size:20px;">自动上传</span>
		 			<br>
		 			<br>
		 			<br>
		 			<div id='autoUpload'></div>
		 		</td>
		 		<td align="center">
		 			<span style="font-size:20px;">手动上传</span>
		 			<br>
		 			<br>
		 			<br>		 		
		 			<div id='notAutoUpload'></div>
		 			<input type="button" value="上传" style='width:70px;height:25px;' onclick="startUpload()">
		 		</td>
		 		<td align="center">
		 			<span style="font-size:20px;">单张图片上传</span>
		 			<br>
		 			<br>
		 			<br>		 		
		 			<div id='onePicUpload'></div>
		 		</td>
		 		<td align="center">
		 			<span style="font-size:20px;">照片联动上传</span>
		 			<br>
		 			<br>
		 			<br>		 		
		 			<div id='picLDUpload'></div>
		 		</td>		 				 		
		 		<!-- <td align="center">
		 			<span style="font-size:20px;">自定义上传</span>
		 			<br>
		 			<br>
		 			<br>		 		
		 			<div id='customUpload'></div>
		 		</td>	 -->	 				 		
		 	</tr>		 		
	 	</table>
</body>
</html>