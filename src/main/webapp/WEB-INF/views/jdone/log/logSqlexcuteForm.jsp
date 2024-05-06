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
	        	<span style="width: 85px;">日志详情</span>
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
						<label class="control-label">sql类型:</label>
					</th>
					<td width="38%">
						<label>${logOper.sqlType}</label>
					</td>
					<th width="12%">
						<label class="control-label">执行Sql语句:</label>
					</th>
					<td width="38%">
						<label>${logOper.excuteSql}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">耗时:</label>
					</th>
					<td width="38%">
						<label>${logOper.cost}ms</label>
					</td>
					<th width="12%">
						<label class="control-label">执行结果:</label>
					</th>
					<td width="38%">
						<label>
     					 	<c:if test="${logOper.excuteResult == '01'}">
                            	         成功
                           	</c:if>
                           	<c:if test="${logOper.excuteResult == '00'}">
                            	         失败
                           	</c:if>
     					</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">sql语句标识:</label>
					</th>
					<td width="38%">
						<label>${logOper.sqlMark}</label>
					</td>
					<th width="12%">
						<label class="control-label">sql功能描述:</label>
					</th>
					<td width="38%">
						<label>${logOper.sqlFuncDesc}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">执行服务器IP:</label>
					</th>
					<td width="38%">
						<label>${logOper.excuteServer}</label>
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
						<label class="control-label">开始执行时间:</label>
					</th>
					<td colspan="3">
						<label><fmt:formatDate value="${logOper.excuteStartTime}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">执行参数:</label>
					</th>
					<td colspan="3">
						<textarea readonly="readonly" style="width:85%;height: 100px;">${logOper.excuteParam}</textarea>>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/log/logSqlexcuteList'">
		</div>
	</form>
</body>
</html>
