<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
 <script type="text/javascript">
 $(function (){
		$("#src").html(parent.src);
		if($("#id").val() == ""){  //新增时才给定最大排序值
			$("#orderId").val(parent.desort);
		}
		$("#groupId").val(parent.pid);
	});
 </script>
</head>
<body>
	<!-- 菜单操作 开始 -->
		<form id="mainForm" action="" class="form-horizontal"  method="post">
			<input type="hidden" name="id" id="id" value="${menu.id}"/>
			<input id="isGroup" name="isGroup" type="hidden" value="1"/>
			<input type="hidden" name="groupId" id="groupId" value="" /> <!-- 父菜单ID判断是否添加子菜单或父菜单 -->
			<div id="main">
			<h2 class="subfild">
	        	<span>${not empty menu.id?'修改分组':'添加分组'}</span>
	        </h2>
            </div>
			<table class="table" cellpadding="0" cellspacing="0" width="99%">
				<tr>
					<th width="12%"><label class="control-label">分组名称:</label></th>
					<td>
					<input type="text" name="name" class="commom-txt required" value="${menu.name}"/>
					<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%"><label class="control-label">分组标识:</label></th>
					<td>
						<input id="mark" name="mark"  class="commom-txt required"  type="text" value="${menu.mark}"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
				    <th width="12%"><label class="control-label">排序ID:</label></th>
					<td  colspan="3">
						<input id="orderId" name="orderId" ltype="number" class="commom-txt required" type="text" value="${menu.orderId}"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
			</table>
			</form>
</body>
</html>