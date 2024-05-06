<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/log/logMsgKafkaResendForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="logForm" class="form-horizontal">
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">消息容器:</label>
					</th>
					<td >
						<input name="topic" type="text"  class="required" />
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">消息类型:</label>
					</th>
					<td width="38%">
						<input name="msgType" type="text"  class="required" />
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">消息内容:</label>
					</th>
					<td width="38%">
						<input name="msgContent" type="text" class="search-textbox keydownSearch" />
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">发送时间:</label>
					</th>
					<td width="38%">
						 <input type="text" id="kssj" name="kssj" 
                           class="search-textbox keydownSearch Wdate required"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
						--
						 <input type="text" id="jssj" name="jssj" 
                           class="search-textbox keydownSearch Wdate required"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="确 定" onclick="searchFunc()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="关 闭" onclick="closeDialog()">
		</div>
	</form>
</body>
</html>
