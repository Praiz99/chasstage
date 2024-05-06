<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.js"></script>	
<script type="text/javascript">
 $(function (){
		$("#src").html(parent.src);
		if($("#id").val() == ""){  //新增时才给定最大排序值
			$("#orderId").val(parent.desort);
		}
		
	});
 </script>
</head>
<body>
	<!-- 菜单操作 开始 -->
		<form id="mainForm" name="orgForm" class="form-horizontal">
			<input type="hidden" name="id" id="id" value="${menu.id}"/>
			<input id="isGroup" name="isGroup" type="hidden" value="0"/>
			<input type="hidden" name="groupId" id="groupId" value="${pid}" />  <!-- 父菜单ID判断是否添加子菜单或父菜单 -->
			<div id="main">
			<h2 class="subfild">
	        	<span>${not empty menu.id?'修改操作':'添加操作'}</span>
	        </h2>
            </div>
			<table class="table">
				<tr>
					<th width="12%">
					<label class="control-label">操作名称:</label>
					</th>
					<td> 
					<input type="text"  class="required" name="name"  value="${menu.name}"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%"><label class="control-label">操作标识:</label></th>
					<td>
						<input id="mark" name="mark"  class="required" type="text" value="${menu.mark}"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
				   <th width="12%"><label class="control-label">是否设置范围:</label></th>
					<td>
						<input type="radio" <c:if test="${empty menu.isEnableRange}"> checked</c:if> <c:if test="${menu.isEnableRange==0}"> checked</c:if> name="isEnableRange" value="0" /> 否
                        <input type="radio" <c:if test="${menu.isEnableRange==1}"> checked</c:if> name="isEnableRange" value="1" /> 是
					</td>
				    
					<th width="12%"><label class="control-label">是否权限控制:</label></th>
					<td>
						<input type="radio" <c:if test="${menu.isEnableAuth==0}"> checked</c:if> name="isEnableAuth" value="0" /> 否
                        <input type="radio" <c:if test="${empty menu.isEnableAuth}"> checked</c:if>  <c:if test="${menu.isEnableAuth==1}"> checked</c:if> name="isEnableAuth" value="1" /> 是
					</td>
				</tr>
				<tr>
					<th width="12%"><label class="control-label">排序ID:</label></th>
					<td  colspan="3">
						<input id="orderId" name="orderId" ltype="number" class="required"  type="text" value="${menu.orderId}"/>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
			</table>
			</form>
</body>
</html>