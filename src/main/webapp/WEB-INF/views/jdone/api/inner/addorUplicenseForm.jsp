<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/api/field/initOrgList.js"></script>
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.js"></script>
     <script type="text/javascript" src="${ctx}/static/jdone/js/api/inner/addorUplicenseForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	initOrgList('id','pid','code',ctx +'/api/inner/treeData',true);
    	$(document).ready(function() {
    		$("#addorUplicenseForm").validate();
    		});
    </script>
 <style type="text/css">
        ul.ztree {
	margin-top: 10px;
	border: 1px solid #617775;
	background: #f0f6e4;
	width: 220px;
	height: 360px;
	overflow-y: scroll;
	overflow-x: auto;
}
   </style>
</head>
<body>
	<form id="addorUplicenseForm" name="addorUplicenseForm" action="save" method="post">
		<input id="id" name="id" type="hidden" value="${apiLicense.id}">
		<div class="form-panel">
        <div class="panel-top">
				<div class="tbar-title">
					<span>${bs}授权码</span>	
				</div>
			</div>
		<div class="panel-body">
		<table class="table_form">
				<tr>
					<th width="12%">
						授权系统名称:
					</th>
					<td width="38%">
						<input name="authSysName" class="required" type="text" value="${apiLicense.authSysName}" >
					</td>
					<th width="12%">
						授权系统标识:
					</th>
					<td width="38%">
						<input name="authSysMark" class="required" type="text"  value="${apiLicense.authSysMark}">
					</td>
				</tr>
				<tr>
					<th width="12%">
						授权单位名称:
					</th>
					<c:if test="${ empty apiLicense.snNo}">
					<td width="38%">
						<div class="input-append">
							<input name="authDwmc" id="orgSel" type="text" class="required"  value="${apiLicense.authDwmc}"/>
							<input name="authDwdm" id="pid" type="text" style="display: none;" />
							<a href="javascript:" id="showOrg" class="btn" onclick="showMenu();" style="text-decoration: none;">
								<i class="icon-search"></i>&nbsp;
							</a>&nbsp;&nbsp;
						</div>
					</td>
					</c:if>
					<c:if test="${not empty apiLicense.snNo}">
					<td width="38%">
							${apiLicense.authDwmc}
					</td>
					</c:if>
					<th width="12%">授权码状态:</th>
					<td width="38%">
						<input type="radio" <c:if test="${empty apiLicense.snNoStatus}"> checked</c:if> <c:if test="${apiLicense.snNoStatus==0}"> checked</c:if> name="snNoStatus" value="0" > 启用
                        <input type="radio" <c:if test="${apiLicense.snNoStatus==1}"> checked</c:if> name="snNoStatus" value="1"> 禁用
					</td>
				</tr>
				
				<c:if test="${not empty apiLicense.snNo}">
				  <tr>
				    <th width="12%">授权码:</th>
					<td colspan="3">${apiLicense.snNo}</td>
				  </tr>
				</c:if>
				<tr>
				   <th width="12%">
						授权描述:
					</th>
					<td colspan="3">
					<textarea name="authDesc" class="required" type="text" style="height: 50px;" maxlength="200">${apiLicense.authDesc}</textarea>
					</td>
				</tr>
				
				
		</table>
		<div class="form-btns">
			<input id="btnSubmit" class="button" type="button" onclick="licensesub()" value="保 存">&nbsp;
			<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/api/inner/licenseList'">
		</div>
		</div>
		</div>
	</form>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;; height: 210px;"></ul>
	</div>
</body>
</html>
