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
     <script type="text/javascript" src="${ctx}/static/jdone/js/api/outer/addorUpwbjkglForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	initOrgList('id','pid','code',ctx +'/api/inner/treeData',true);
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
	<form id="addorUpwbjkglForm" name="addorUpwbjkglForm" action="save" method="post" >
		<input id="id" name="id" type="hidden" value="${apiOuter.id}">
		<div class="form-panel">
         <div class="panel-top">
				<div class="tbar-title">
					<span>${bs}外部接口</span>	
				</div>
			</div>
		<div class="panel-body">
	    	<table class="table_form">
	    	<tr>
	    	   <th width="12%">提供方单位名称:</th>
					<td width="38%">
						<div class="input-append">
							<input name="pvrDwmc" id="orgSel" type="text" class="required"  value="${apiOuter.pvrDwmc}"/>
							<input name="pvrDwdm" id="pid" type="text" style="display: none;" />
							<a href="javascript:" id="showOrg" class="btn" onclick="showMenu();" style="text-decoration: none;">
								<i class="icon-search"></i>&nbsp;
							</a>&nbsp;&nbsp;
						</div>
					</td>
					
				<th width="12%">
						<label class="control-label">提供系统名称:</label>
					</th>
					<td width="38%">
						<input name="pvrSysName" class="required" type="text" maxlength="50" value="${apiOuter.pvrSysName}" >
					</td>
	    	</tr>
				<tr>
					
					<th width="12%">
						<label class="control-label">提供系统标识:</label>
					</th>
					<td width="38%">
						<input name="pvrSysMark" class="required" type="text" maxlength="50" value="${apiOuter.pvrSysMark}">
					</td>
					
					<th width="12%">
						<label class="control-label">接口实现类:</label>
					</th>
					<td width="38%">
						<input name="serviceImpClass" id="serviceImpClass" type="text" class="required" maxlength="100" value="${apiOuter.serviceImpClass}">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">接口名称:</label>
					</th>
					<td width="38%">
					<input name="serviceName" type="text" id="serviceName" class="required" minlength="1" value="${apiOuter.serviceName}">
					</td>
					<th width="12%">
						<label class="control-label">接口标识:</label>
					</th>
					<td width="38%">
						<input name="serviceMark" type="text" id="serviceMark" class="required" minlength="1" value="${apiOuter.serviceMark}">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">接口地址:</label>
					</th>
					<td width="38%">
						<input name="serviceUrl" class="required" type="text" id="serviceUrl" minlength="1" value="${apiOuter.serviceUrl}">
					</td>
					<th width="12%">
						<label class="control-label">接口类型:</label>
					</th>
					<td width="38%">
						<select name="serviceType" id="serviceType" class="required">
							<option value="01" >请求服务</option>
							<option value="02" >webservice</option>
							<option value="03" >http rest</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">授权码:</label>
					</th>
					<td width="38%">
						<input name="snNo" id="snNo" type="text" class="required" maxlength="100" value="${apiOuter.snNo}">
						<!--  readonly="readonly" --> 
					</td>
					<th width="12%">
						<label class="control-label">接口分组标识:</label>
					</th>
					<td width="38%">
						<select id="groupId" name="groupId" >
            	      <c:forEach var="app" items="${apiGroups}">
                        <option value="${app.id}" <c:if test="${apiOuter.groupId==app.id }"> selected</c:if>>${app.groupName}</option>
                      </c:forEach>
                    </select>
					</td>
				</tr>
				
				<tr>
					<th width="12%">
						<label class="control-label">备注:</label>
					</th>
					<td colspan="3">
						<textarea name="remark" type="text" style="height: 50px;"  >${apiOuter.remark}</textarea>
					</td>
					<%-- <td width="38%">
						<input name="remark" id="remark" type="text"  maxlength="100" value="${apiOuter.remark}">
					</td> --%>
				</tr>
		</table>
		<div class="form-btns">
			<input id="btnSubmit" class="button" type="button" onclick="sub()" value="保 存">&nbsp;
			<input id="btnCancel" class="button" type="button" value="返 回" onclick="history.back();"><%-- window.location.href='${ctx}/api/outer/outerList' --%>
		</div>
	</div>
	</div>	
	</form>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;; height: 210px;"></ul>
	</div>
</body>
</html>
