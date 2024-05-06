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
    </script>
</head>
<body>
	<form id="logOperForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width: 85px;">日志信息</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">应用名称:</label>
					</th>
					<td width="38%">
						<label>${logOper.appName}</label>
					</td>
					<th width="12%">
						<label class="control-label">应用标识:</label>
					</th>
					<td width="38%">
						<label>${logOper.appMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">模块名称:</label>
					</th>
					<td width="38%">
						<label>${logOper.moduleName}</label>
					</td>
					<th width="12%">
						<label class="control-label">模块标识:</label>
					</th>
					<td width="38%">
						<label>${logOper.moduleMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">页面/操作名称:</label>
					</th>
					<td width="38%">
						<label>${logOper.operName}</label>
					</td>
					<th width="12%">
						<label class="control-label">页面/操作标识:</label>
					</th>
					<td width="38%">
						<label>${logOper.pageOperMark}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">操作类型:</label>
					</th>
					<td width="38%">
						<label>${logOper.operTypeName}</label>
					</td>
					<th width="12%">
						<label class="control-label">操作时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${logOper.trgTime}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">访问地址:</label>
					</th>
					<td width="38%">
						<label>${logOper.visitUrl}</label>
					</td>
					<th width="12%">
						<label class="control-label">操作路径:</label>
					</th>
					<td width="38%">
						<label>${logOper.servletPath}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">操作用户:</label>
					</th>
					<td width="38%">
						<label>${logOper.trgObjName}</label>
					</td>
					<th width="12%">
						<label class="control-label">操作单位:</label>
					</th>
					<td width="38%">
						<label>${logOper.trgObjDwmc}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">访问IP:</label>
					</th>
					<td width="38%">
						<label>${logOper.operIp}</label>
					</td>
					<th width="12%">
						<label class="control-label">响应IP:</label>
					</th>
					<td width="38%">
						<label>${logOper.responseServer}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">操作角色编号:</label>
					</th>
					<td width="38%">
						<label>${logOper.operUserRoleCode}</label>
					</td>
					<th width="12%">
						<label class="control-label">操作角色名称:</label>
					</th>
					<td width="38%">
						<label>${logOper.operUserRoleName}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">请求类型:</label>
					</th>
					<td width="38%">
						<label>${logOper.reqType}</label>
					</td>
					<th width="12%">
						<label class="control-label">请求ID:</label>
					</th>
					<td width="38%">
						<label>${logOper.reqId}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">业务分类:</label>
					</th>
					<td width="38%">
						<label>${logOper.bizLogBigType}</label>
					</td>
					<th width="12%">
						<label class="control-label">业务子分类:</label>
					</th>
					<td width="38%">
						<label>${logOper.bizLogLitType}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">业务标识:</label>
					</th>
					<td width="38%">
						<label>${logOper.bizMark}</label>
					</td>
					<th width="12%">
						<label class="control-label">操作参数:</label>
					</th>
					<td width="38%">
						<label>${logOper.operParams}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">业务操作描述:</label>
					</th>
					<td colspan="3">
						<textarea readonly="readonly" style="width:85%;height: 100px;">${logOper.bizOperDesc}</textarea>>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/log/logOperationList'">
		</div>
	</form>
</body>
</html>
