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
<script type="text/javascript" src="${ctx}/static/jdone/js/info/infoRelfileForm.js"></script>
<script type="text/javascript">
    	var ctx = '${ctx}';
</script>
</head>
<body>
		<form id="infoForm" class="form-horizontal"  method="post">
			<input type="hidden" name="id" id="id" value="${infoRelfile.id}"/>
			<div id="main">
			<h2 class="subfild">
	        	<span  style="width:150px">${not empty infoRelfile.id?'修改资料':'添加资料'}</span>
	        </h2>
            </div>
			<table class="table" cellpadding="0" cellspacing="0" width="99%">
				<tr>
					<th width="12%"><label class="control-label">资料名称:</label></th>
					<td>
					<input type="text" name="name" class="required" value="${infoRelfile.name}"/>
					<span class="help-inline">
							<font color="red">*</font>
					</span>
					</td>
					<th width="12%"><label class="control-label">资料标识:</label></th>
					<td>
						<input type="text" name="mark" class="required" value="${infoRelfile.mark}"/>
						<span class="help-inline">
							<font color="red">*</font>
					    </span>
					</td>
				</tr>
				<tr>
				<th width="12%"><label class="control-label">资料分类:</label></th>
					<td>
						<select id="catId" name="catId" class="search-textbox keydownSearch" >
								<c:if test="${catList != null && fn:length(catList) != 0}">
									<option/>
									<c:forEach items="${catList}" var="catItem">
										<option value="${catItem.id}" <c:if test="${infoRelfile.catId == catItem.id}">selected = "selected"</c:if>>${catItem.name}</option>
									</c:forEach>
								</c:if>
						 </select>
						 &nbsp;&nbsp;<a href="javascript:void(0);" onclick="openCatList()">分类管理</a>
					</td>
				</tr>
				<tr>	
					<th width="12%"><label class="control-label">资料描述:</label></th>
					<td colspan="3"  style="height:80px;">
					   <textarea  style="width:95%;height: 60px;"  id="state"  name="state" >${infoRelfile.state}</textarea>
					</td>
				</tr>
			</table>
			<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveInfo()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/info/openInfoRelfileList'">
			</div>
	</form>
	<div id="infoCatDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="分类管理" style="width:65%; height:60%;">
		<iframe scrolling="auto" id='openInfoCatList' name="openInfoCatList" frameborder="0" src="" style="width: 100%; height: 100%;"></iframe>
	</div>
</body>
</html>