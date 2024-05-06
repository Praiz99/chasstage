<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/user/userRoleForm.js?v=1"></script>
   <script type="text/javascript" src="${ctx}/static/jdone/js/sys/org/initOrgList.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/role/initRoleList.js"></script>
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.js"></script>
        <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
    <style type="text/css">
    	body,label,input,button,select,textarea,.uneditable-input,.navbar-search .search-query {font-family:Helvetica, Georgia, Arial, sans-serif, 宋体;font-size:13px;_font-size:12px;}
    	h2 {font-weight: bold;}
    </style>
</head>
<body>
	<form id="userRoleForm" class="form-horizontal" method="post" >
		<input id="id" name="userId" type="hidden" value="${sysuser.id}">
		<div style="margin-left: 5px;width: 98%">
        	<table id="roleGrid" class="table"></table>
        </div>
		<div class="form-panel">
	        <div class="panel-body" style="margin: 2px 2px 0 5px">
				<table class="table_form">
					<tbody>
						<tr height="40px;">
							<th width="30%">
								<label >角色名称：</label>
							</th>
							<td width="70%">
								<input id="roleSel" name="roleSel" style="width: 60%" type="text" placeholder="-----请选择角色-----">
								<input name="rid" id="rid" type="text" style="display: none;" />
								<a href="javascript:" id="showRole" class="btn" onclick="showRoleMenu();" style="text-decoration: none;display: none;">
									<i class="icon-search"></i>&nbsp;
								</a>&nbsp;&nbsp;
							</td>
						</tr>
						<tr height="40px;">
							<th width="30%">
								<label >分管机构：</label>
							</th>
							<td width="70%" id="ifXz">
							<input type="text" id="nameDic" style="width:400px;" onkeyup="orgChange();"/> &nbsp;&nbsp;
							<!-- <input class="btn btn-primary" type="button" onclick="SelectOrgs()" value="选择"> -->
							</td>
							<td width="70%" id="ifBj">
								<input id="orgSel" style="width: 60%" type="text">
								<input name="fgCode" id="pid" type="text" style="display: none;" />
								<a href="javascript:" id="showOrg" class="btn" onclick="showMenu();" style="text-decoration: none;display: none;">
									<i class="icon-search"></i>&nbsp;
								</a>&nbsp;&nbsp;
							</td>
						</tr>
						<tr height="40px;">
							<th width="30%">
								<label>授权类型：</label>
							</th>
							<td width="70%">
								<input id="stateon" type="radio" name="grantType" onClick="timeShow()" value="0" /> 临时
								<input id="stateoff" type="radio" name="grantType" onClick="timeHide()" checked value="1" /> 永久 
							</td>
						</tr>
						<tr id="time"  height="40px;">
							<th width="30%">
								<label>授权时间：</label>
							</th>
							<td width="70%">
								<input type="text" id="kssj" name="kssj" 
		                           class="search-textbox keydownSearch Wdate"
		                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
								至
								 <input type="text" id="jssj" name="jssj" 
		                           class="search-textbox keydownSearch Wdate"
		                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
							</td>
						</tr>
						<tr height="40px;">
							<td colspan="2">
								<div  align="center" >
									<input name="roleId" id="roleId" type="text" style="display: none;" />
									<input id="addBtn" class="btn btn-primary" type="button" onclick="addUserRole()" value="添加">&nbsp;
									<input id="btnCancel" class="btn" type="button" value="关闭" onclick="closeDialog()">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:300px;; height: 220px;"></ul>
	</div>
	<div id="menuRoleContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeRole" class="ztree" style="margin-top:0; width:300px;; height: 220px;"></ul>
	</div>
	</form>
</body>
</html>
