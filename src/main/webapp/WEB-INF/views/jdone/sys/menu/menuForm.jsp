<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>菜单配置</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/menu/jq22.css"/>
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/static/jdone/js/sys/menu/menuForm.js"></script>
	<script type="text/javascript"src="${ctx}/static/jdone/js/common/selector/selector.socpe.js"></script>
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
</head>
	<body>
		<div id="tt" class="easyui-tabs" style="width:99%;height:100%;">   
			<div title="基本信息" >
				<form id="mainForm" name="mainForm" class="form-horizontal" style="margin-top: 5px;">
					<input type="hidden" name="id" id="id" value="${menu.id}"/>
					<input type="hidden" name="resType" id="resType" value="${resType}"/>
					<input type="hidden" name="businessId" id="businessId" value="${businessId}"/>
					<input type="hidden" name="appId" id="appId" value="${menu.appId}" /> <!-- 新增时使用的应用ID -->
					<input type="hidden" name="pid" id="pid" value="${menu.pid}" /> <!-- 父菜单ID判断是否添加子菜单或父菜单 -->
					<table class="table" style="margin-left: 5px;">
						<tr>
							<th style="width: 15%;">
								<label class="control-label">路径</label>
							</th>
							<td colspan="3">
								<label  style="width:95%;" id="src"></label>
							</td>
						</tr>
						<tr>
							<th style="width: 15%;">
								<label class="control-label">名&nbsp;称</label>
							</th>
							<td style="width: 35%;">
								<input type="text" maxlength="50" name="name" class="required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
							<th style="width: 15%;">
								<label class="control-label">排序ID</label>
							</th>
							<td style="width: 35%;">
								<input name="orderId" class="required" type="text" maxlength="6"/>
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<th style="width: 15%;">
								<label class="control-label">别&nbsp;名</label>
							</th>
							<td colspan="3">
								<input type="text" name="alias" style="width:30%;"/>
								<p style="width: 65%;float: right;margin-top: 7px;color: red;">此字段是用作"资源授权"中展示的菜单名称。</p>
							</td>
						</tr>
						<tr>
							<th style="width: 15%;">
								<label class="control-label">是否禁用</label>
							</th>
							<td style="width: 35%;">
								<input type="radio" value="0" name="isDisabled" checked="checked">否
								<input type="radio" value="1" name="isDisabled" >是
							</td>
							<th style="width: 15%;">
								<label class="control-label">打开方式</label>
							</th>
							<td style="width: 35%;">
								<input type="radio" value="0" name="openmode" checked="checked">操作区域
								<input type="radio" value="1" name="openmode" >新窗口
							</td>
						</tr>
						<tr>
							<th style="width: 15%;">
								<label class="control-label">是否限制权限</label>
							</th>
							<td style="width: 35%;">
								<input type="radio" value="1" name="isEnableAuth" checked="checked">是
								<input type="radio" value="0" name="isEnableAuth" >否
							</td>
							<th style="width: 15%;">
								<label class="control-label">菜单图标</label>
							</th>
							<td style="width: 35%;">
								<input name="menuIco" id="menuIco" style="float: none;width: 50%;" readonly="readonly" type="text" maxlength="30"/>
								<input type="button" value="选择图标" onclick="OptionIcon();" class="btn" />
							</td>
						</tr>
						<tr>
							<th style="width: 15%;">
								<label class="control-label">是否设置范围</label>
							</th>
							<td colspan="3">
								<input type="radio" value="1" name="isEnableRange"  onClick="isCall()">是
								<input type="radio" value="0" name="isEnableRange" checked="checked" onClick="isCalls()">否
								<a href="#" id="range" onclick="openSocpe()">设置范围</a>
							</td>
						</tr>
						<tr>
							<th style="width: 15%;">
								<label class="control-label">URL地址</label>
							</th>
							<td colspan="3" style="height:80px;">
								<textarea style="width:95%; height: 60px;" name="url" id="url" maxlength="100"></textarea>
							</td>
						</tr>
					</table>
				</form>
				<div class="form-actions" align="center" style="margin-top: 10px;margin-bottom: 10px;">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveMenu()">
				</div>
				<div id="menuIconDialog" class="easyui-dialog" closed="true" data-options="title:'菜单图标选择',modal:true,buttons:[{text:'保存',iconCls:'icon-save',handler:function(){$('input[name=\'menuIco\']').val(frames.document['menuIconSelect'].getResult());$('#menuIconDialog').dialog('close');}},{text:'关闭',iconCls:'icon-cancel',handler:function(){$('#menuIconDialog').dialog('close');}}]" style="width: 650px; height: 350px;top:5%; left:10%;">
					<iframe scrolling="auto" id='menuIconSelect' name="menuIconSelect" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
				</div>
			</div>
			<div title="权限资源" id="qxzy" style="overflow:auto;display: block;">
        		<iframe id="szqxzy" width='100%' height='100%' frameborder="0" src="" ></iframe>
        	</div>
		</div>
	</body>
</html>