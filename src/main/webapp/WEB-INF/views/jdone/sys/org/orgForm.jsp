<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/org/orgForm.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_DWJB.js"></script>
	<script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_DWLX.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="orgForm" class="form-horizontal" name="orgForm">
		<input type="hidden" id="id" name="id" value="${sysorg.id}">
		<input id="opType" name="opType" type="hidden" value="${opType}">
		<input id="orgWordNo" name="orgWordNo" type="hidden">
		<div id="main">
			<h2 class="subfild">
	        	<span>${not empty sysorg.id?'修改机构':'添加机构'}</span>
	        </h2>
        </div>
		<table class="table">
			<tbody>
				<tr>
					<th width="12%">
						<label class="control-label">名称：</label>
					</th>
					<td width="38%">
						<input name="name" class="required" type="text" maxlength="200">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
					<th width="12%">
						<label class="control-label">代码：</label>
					</th>
					<td width="38%">
						<input name="code" id="code" class="required" type="text" maxlength="30">
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">单位类型：</label>
					</th>
					<td width="38%">
						<input type="text" id="jzflDic" class="dic required" dicName="ZD_SYS_DWLX" hiddenField="orgType"></input>
						<input type="hidden" name="jzfl" id="orgType"/>
					</td>
					<th width="12%">
						<label class="control-label">单位级别：</label>
					</th>
					<td width="38%">
						<input type="text" id="levelDic" class="dic required" dicName="ZD_SYS_DWJB" hiddenField="levelName"></input>
						<input type="hidden" name="level" id="levelName"/>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">简称：</label>
					</th>
					<td width="38%">
						<input name="sname" type="text" maxlength="200">
					</td>
					<th width="12%">
						<label class="control-label">简拼：</label>
					</th>
					<td width="38%">
						<input name="spym" type="text" maxlength="100">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">全拼：</label>
					</th>
					<td width="38%">
						<input name="fpym" type="text" maxlength="200">
					</td>
					<th width="12%">
						<label class="control-label">系统编号：</label>
					</th>
					<td width="38%">
						<input name="sysCode" id="sysCode" type="text" maxlength="30" readonly="readonly">
						<font id="number">
						<a style='text-decoration:underline;' href='javascript:;' onclick='generateNumbering()'>生成编号</a>
						</font>
						
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">上级机构：</label>
					</th>
					<td width="38%">
						<input type="text" id="nameDic" style="width:300px;" onkeyup="orgChange();"/>
						<input type="hidden" id="pid" name="pid">
					</td>
					<th width="12%">
						<label class="control-label">是否办案单位：</label>
					</th>
					<td width="38%">
						<input id="sfbadw_0" name="sfbadw" type="radio" value="0" checked="checked">
						<span class="text">否</span>
						<input id="sfbadw_1" name="sfbadw" type="radio" value="1">
						<span class="text">是</span>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">单位联系方式：</label>
					</th>
					<td width="38%">
						<input name="dwlxfs" class="phone" type="text" maxlength="30">
					</td>
					<th width="12%">
						<label class="control-label">单位地址：</label>
					</th>
					<td width="38%">
						<input name="dwdz" type="text" maxlength="500">
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">单位负责人姓名：</label>
					</th>
					<td width="38%">
						<input name="fzrXm" class="fzrXm" type="text" maxlength="30">
					</td>
					<th width="12%">
						<label class="control-label">负责人身份证号：</label>
					</th>
					<td width="38%">
						<input name="fzrSfz" class="fzrSfz" type="text" maxlength="30">
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions" align="center" style="padding-left: 0px;margin-bottom: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveOrg()">&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.location.href='${ctx}/sys/org/orgList'">
		</div>
	</form>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:264px;; height: 300px;"></ul>
	</div>
</body>
</html>
