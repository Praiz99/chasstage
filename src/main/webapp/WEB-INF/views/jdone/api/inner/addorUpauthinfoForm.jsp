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
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	var ips='${apiAuthinfo.isIpFilter }';
    	var call='${apiAuthinfo.isCallCountLimit }';
    	var occurs='${apiAuthinfo.isOccursLimit }';
    </script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/api/inner/addorUpauthinfoForm.js"></script>
</head>
<body>
	<form id="addorUpauthForm" name="addorUpauthForm" action="saveauthinfo" method="post" >
		<input id="id" name="id" type="hidden" value="${apiAuthinfo.id}">
		<div class="form-panel">
            <div class="panel-top">
				<div class="tbar-title">
					<span>${bs}接口授权信息</span>	
				</div>
			</div>
		<div class="panel-body">
	    	<table class="table_form">
				<tr>
					<th width="12%">接口名称:</th>
					<td width="38%">
						<select id="apiId" name="apiId" class="input-xlarge"  >
            	      <c:forEach var="app" items="${inners}">
                        <option value="${app.id}" <c:if test="${ apiAuthinfo.apiId== app.id }"> selected</c:if>>${app.serviceName}</option>
                       <%--   <input id="apiMark" name="apiMark" type="hidden" value="${app.apiMark}"> --%>
                      </c:forEach>
                    </select>
					</td>
					<th width="12%">授权码描述:</th>
					<td width="38%">
						<select id="snNo" name="snNo" class="input-xlarge" >
            	      <c:forEach var="app" items="${appList}">
                        <option value="${app.snNo}" <c:if test="${ apiAuthinfo.snNo== app.snNo }"> selected</c:if>>${app.authDesc}</option>
                      </c:forEach>
                    </select>
					</td>
				</tr>
				<tr>
					<th width="12%">
						是否启用IP调用限制:
					</th>
					<td width="38%">
						<input type="radio" onClick="isIpClick()" <c:if test="${empty apiAuthinfo.isIpFilter}"> checked</c:if>  <c:if test="${apiAuthinfo.isIpFilter==0}"> checked</c:if> id="isIpFilter" name="isIpFilter" value="0" > 否
                        <input type="radio" onClick="isIpClicks()" <c:if test="${apiAuthinfo.isIpFilter==1}"> checked</c:if> id="isIpFilters" name="isIpFilter" value="1"> 是
					</td>
					<th width="12%">
						可调用IP列表:
					</th>
					<td width="38%">
						<input name="ipList" type="text" id="ipList" maxlength="50" minlength="1" value="${apiAuthinfo.ipList}">
					</td>
				</tr>
				<tr>
				    <th width="12%">
						是否启用每日调用次数限制:
					</th>
					<td width="38%">
						<input type="radio" onClick="isCall()" <c:if test="${empty apiAuthinfo.isCallCountLimit}"> checked</c:if> <c:if test="${apiAuthinfo.isCallCountLimit==0}"> checked</c:if>  name="isCallCountLimit" value="0" > 否
                        <input type="radio" onClick="isCalls()" <c:if test="${apiAuthinfo.isCallCountLimit==1}"> checked</c:if> name="isCallCountLimit" value="1"> 是
					</td>
					
					<th width="12%">
						每日最大调用次数:
					</th>
					<td width="38%">
						<input name="maxCallCount" type="text" id="maxCallCount"  value="${apiAuthinfo.maxCallCount}">
					</td>
				</tr>
				<tr>
				    <th width="12%">
						是否启用并发数限制:
					</th>
					<td width="38%">
						<input type="radio" onClick="isOccurs()" <c:if test="${empty apiAuthinfo.isOccursLimit}"> checked</c:if> <c:if test="${apiAuthinfo.isOccursLimit==0}"> checked</c:if> name="isOccursLimit" value="0" > 否
                        <input type="radio" onClick="isOccurss()" <c:if test="${apiAuthinfo.isOccursLimit==1}"> checked</c:if> name="isOccursLimit" value="1"> 是
					</td>
					
					<th width="12%">
						最大并发数:
					</th>
					<td width="38%">
						<input name="maxOccurs" type="text" id="maxOccurs" maxlength="50" minlength="1" value="${apiAuthinfo.maxOccurs}">
					</td>
				</tr>
				<tr>
				    <th width="12%">
						授权状态:
					</th>
					<td width="38%">
						<input type="radio" <c:if test="${apiAuthinfo.authStatus==0}"> checked</c:if> name="authStatus" value="0" > 否
                        <input type="radio" <c:if test="${empty apiAuthinfo.authStatus}"> checked</c:if> <c:if test="${apiAuthinfo.authStatus==1}"> checked</c:if> name="authStatus" value="1"> 是
					</td>
					
					<th width="12%">
						结果类型:
					</th>
					<td width="38%">
						<select name="resultType" id="resultType" class="input-xlarge">
							<option value="01" >JSON</option>
							<option value="02" >XML</option>
						</select>
					</td>
				</tr>
				<tr>
				    <th width="12%">
						结果字段列表:
					</th>
					<td colspan="3">
					<div class="controls">
						<div class="input-append">
							<input type="text" id="jsrtxt" name="resultFieldList" class="tip"  value="${apiAuthinfo.resultFieldList}"/>
							<span class="tip add-on"  id="jieshouren" >
								<input id="btnSubmit" style="width:50px;height:24px;" type="button" onclick="sc()" value="选择">
								<div style="display:none" id="selectlxr"></div>
							</span>
						</div>
					</div>
		
				<%-- 	<input name="resultFieldList" type="text" id="resultFieldList" maxlength="50" minlength="1" value="${apiAuthinfo.resultFieldList}"> --%>
					</td>
				</tr>
		</table>
		<div class="form-btns">
			<input id="btnSubmit" class="button" type="button" onclick="sub()" value="保 存">&nbsp;
			<input id="btnCancel" class="button" type="button" value="返 回" onclick="window.location.href='${ctx}/api/inner/authinfoList'">
		</div>
	  </div>
	</div>
	</form>
</body>
</html>
