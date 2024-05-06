<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/role/roleUserForm.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>

</head>
<body style="height: 95%;">
	<div class="searchDiv">
		<form id="searchForm">
			<input id="id" name="roleId" type="hidden" value="${roleId}">
			<table>
				<tr class="searchTr">
					<th>姓名：</th>
					<td>
						<input name="username" class="easyui-textbox keydownSearch" style="width:200px;height: 26px;"/>
					</td>
					<th>所属机构：</th>
					<td>
						<input type="text" id="nameDic" style="width:300px;" onkeyup="orgChange();"/>
						<input type="hidden" id="code" name="orgCode">
					</td>
                    <td>
                    	<a class="easyui-linkbutton" href="#" id="keydownSearch" data-options="iconCls:'icon-search'" onclick="searchFunc('roleId', '${roleId}');">查找</a>
                    </td>
                    <td>
                    	<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="ClearQuery();">清空</a>
                    </td>
				</tr>
			</table>
		</form>
	</div>
	<table id="userGrid"></table>
	<div id="allotUserDialog" class="easyui-dialog" closed="true" style="width: 750px; height: 350px;left: 5%;top:5%;" data-options="title:'分配用户',modal:true,
			buttons:[{
				text:'确定',
				handler:function(){saveUserRoleList();}
			},{
				text:'取消',
				handler:function(){closeDialog();}
			}]">
		<iframe scrolling="auto" id='openReceiveFeedBack' name="openReceiveFeedBack" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
	</div>
	
</body>
</html>
