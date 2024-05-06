<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>指定接收对象</title>
		<%@ include file="/WEB-INF/views/framework/include/default.jsp"%> 
		<script type="text/javascript" src="${ctx}/static/jdone/js/rmd/appointRecieverForm.js"></script>
		<script src="${ctx}/static/jdone/js/common/selector/selector.SysDialog.js" type="text/javascript"></script>
		<script type="text/javascript">
			var ctx = '${ctx}';
		</script>
</head>
	<body>
	<div class="form-panel">
				<div class="panel-body">
					<table class="table_form" cellpadding="0" cellspacing="0" border="0">
						<tbody>
							<tr>
								<td colspan="6">
									<div style="width: 100%; border-bottom: #ebebeb 1px solid; padding-bottom: 5px;">
										 <img src="${ctx}/static/framework/plugins/easyui-1.5.1/themes/icons/tip.png"/> 
										<span><b>指定接收对象</b></span>
									</div>
								</td>
							</tr>	
							<tr>
								<th width="15%" style="background-color: #D6E3EF; text-align: center;"><b>【指定用户】</b></th>
								<td width="8%" style="text-align:center;">
								<input type="button" id="appointUsers" style="height: 25px;"value="&nbsp;选择用户&nbsp;"/>
								</td>	
								<th width="8%">用户姓名:<b/></th>
								<td width="22%">
									<textarea name="usersNames" id="usersNames" style="width: 95%; height: 80px;" readonly="readonly"></textarea>
								</td>															
								<th width="8%">用户编号:<b/></th>
								<td width="22%">
									<textarea name="users" id="users" style="width: 95%; height: 80px;" readonly="readonly"></textarea>
								</td>
							</tr>
							<tr>
								<th width="15%" style="background-color: #D6E3EF; text-align: center;"><b>【指定部门】</b></th>
								<td width="8%" style="text-align:center;">
								<input type="button" id="appointOrgs" style="height: 25px;"value="&nbsp;选择部门&nbsp;"/>
								</td>		
								<th width="8%">部门名称:<b/></th>
								<td width="22%">
									<textarea name="orgsNames" id="orgsNames" style="width: 95%; height: 80px;" readonly="readonly"></textarea>
								</td>														
								<th width="8%">部门编号:<b/></th>
								<td width="22%">
									<textarea name="orgs" id="orgs" style="width: 95%; height: 80px;" readonly="readonly"></textarea>
								</td>
							</tr>
							<tr>
								<th width="15%" style="background-color: #D6E3EF; text-align: center;"><b>【指定角色】</b></th>
								<td width="8%" style="text-align:center;">
								<input type="button" id="appointRoles" style="height: 25px;"value="&nbsp;选择角色&nbsp;"/>
								</td>								
								<th width="8%">角色名称:<b/></th>
								<td width="22%">
									<textarea name="rolesNames" id="rolesNames" style="width: 95%; height: 80px;"  readonly="readonly"></textarea>
								</td>
								<th width="8%">角色编号:<b/></th>
								<td width="22%">
									<textarea name="roles" id="roles" style="width: 95%; height:80px;"  readonly="readonly"></textarea>
								</td>								
							</tr>
							
							<tr>
								<th width="15%" style="background-color: #D6E3EF; text-align: center;"><b>【指定区域】</b></th>
								<td width="8%" style="text-align:center;">
								<input type="button" id="appointRegs" style="height: 25px;"value="&nbsp;选择区域&nbsp;"/>
								</td>		
								<th width="8%">区域名称:<b/></th>
								<td width="22%">
									<textarea name="regsNames" id="regsNames" style="width: 95%; height: 80px;" readonly="readonly"></textarea>
								</td>														
								<th width="8%">区域编号:<b/></th>
								<td width="22%">
									<textarea name="regs" id="regs" style="width: 95%; height: 80px;" readonly="readonly"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="form-btns" align="center"style="padding-left: 0px;margin-bottom: 20px;">
				<input id="confirm" class="button" type="submit" value="确定"/>&nbsp;
				<input id="close" class="button" onclick="parent.win.dialog('close');" style="width:35px;height:22px;" value="关闭"/>
		</div>
			</div>
		</div>			
	</body>
</html>
