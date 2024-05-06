<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="height: 95%;">
	<form id="dataSpbForm" name="dataSpbForm" class="form-horizontal">
		<div id="main">
			<h2 class="subfild">
	        	<span style="width: 85px;">记录信息</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">申请人姓名:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.sqrXm}</label>
					</td>
					<th width="12%">
						<label class="control-label">申请人身份证:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.sqrSfzh}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">申请人单位名称:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.sqrDwmc}</label>
					</td>
					<th width="12%">
						<label class="control-label">申请人单位编号:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.sqrDwbh}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${dataSpb.bgsqsj}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
					<th width="12%">
						<label class="control-label">变更原因:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.bgyy}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更申请状态:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.bgsqzt}</label>
					</td>
					<th width="12%">
						<label class="control-label">审批人姓名:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.sprXm}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">审批人身份证:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.sprSfzh}</label>
					</td>
					<th width="12%">
						<label class="control-label">审批人单位名称:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.sprDwmc}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">审批人单位编号:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.sprDwbh}</label>
					</td>
					<th width="12%">
						<label class="control-label">审批时间:</label>
					</th>
					<td width="38%">
						<label><fmt:formatDate value="${dataSpb.spsj}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">审批意见:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.spyj}</label>
					</td>
					<th width="12%">
						<label class="control-label">审批结果:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.spjg}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">流程key:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.actKey}</label>
					</td>
					<th width="12%">
						<label class="control-label">变更类型:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.modifyType}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更业务类型:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.modifyBizType}</label>
					</td>
					<th width="12%">
						<label class="control-label">变更业务表名:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.modifyBizTable}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">业务标识字段名:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.bizMarkField}</label>
					</td>
					<th width="12%">
						<label class="control-label">业务标识值:</label>
					</th>
					<td width="38%">
						<label>${dataSpb.bizMarkValue}</label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">添加时间:</label>
					</th>
					<td colspan="3">
						<label><fmt:formatDate value="${dataSpb.tjxgsj}" type="date" pattern="yyyy-MM-dd HH:ss:mm"/></label>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">变更参数:</label>
					</th>
					<td colspan="3">
						<textarea style="width: 90%;height: 85px;" readonly="readonly">${dataSpb.paramMap}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/data/modifySpb/dataModifySpbList'">
		</div>
	</form>
</body>
</html>
