<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<script src="${ctx}/static/framework/plugins/ckeditor/ckeditor.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/rmd/noticeForm.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>

	<form name="noticeForm"  id="noticeForm" method="post"><!-- enctype="multipart/form-data"  -->
		<div class="form-panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span>${not empty notice.id?'修改公告':'添加公告'}</span>
			</div>
		</div>
		<div class="panel-body" style="overflow: hidden;">
		<table class="table_form">
			<tbody>
				<tr>
					<th width="20%" class="must"><span style="padding-right:4px">*</span>标题:</th>
					<td width="80%">
					<input name="noticeTitle" id="noticeTitle" class="required" type="text" maxlength="50" value="${notice.noticeTitle }"> </td>
				</tr>
				<tr>
					<th width="20%">紧急程度:</th>
					<td width="80%">
					<select id="urgentLevel"name="urgentLevel" style="width:60px;">
						<option value="0"  <c:if test="${notice.urgentLevel == '0'}">selected</c:if>>一般</option>
						<option value="1" <c:if test="${notice.urgentLevel == '1'}">selected</c:if>>紧急</option>
						<option value="2" <c:if test="${notice.urgentLevel == '2'}">selected</c:if>>特急</option>
					</select></td>
				</tr>
				 <tr>
								<th width="20%">秘密程度:</th>
								<td width="80%">
								<select id="isEnableAuth" name="isEnableAuth" style="width:60px;">
									<option value="0" <c:if test="${notice.isEnableAuth == 0}">selected</c:if>>公开</option>  
									<option value="1" <c:if test="${notice.isEnableAuth == 1}">selected</c:if>>保密</option>
							</select>
								</td>
							</tr> 
				<tr class="security">
								<th width="20%">接收对象:</th>
								<td>
									<table  width="98%" cellSpacing="0" cellPadding="0" style="margin: 2px 2px;">
										<thead>
												<tr style="height: 25px;">
													<td style="background-color: #D6E3EF; text-align: center;height:23px;">
														<b>【用户】</b>
													</td>
													<td style="background-color: #D6E3EF; text-align: center;height:23px;">
														<b>【机构】</b>
													</td>
													<td  style="background-color: #D6E3EF; text-align: center;height:23px;">
														<b>【角色】</b>
													</td>
													<td style="background-color: #D6E3EF; text-align: center;height:23px;">
														<b>【区域】</b>
													</td>
													<td style="background-color: #D6E3EF; text-align: center;height:23px;">
														<b>【操作】</b>
													</td>																																							
												</tr>
										</thead>
										<tbody  class='mapper-body'>
											<tr rowid='0'>
												<td style="text-align: center;height:30px; width: 19%;">
												  	<textarea style="height:33px;width:95%;"  name="usersNames" readonly="readonly">${map.usersNames }</textarea>
												  	<input type="hidden" name='users' value="${map.users }"/>
												</td>
												<td style="text-align: center;height:33px; width: 19%;" >
													<textarea style="height:33px;width:95%;"  name="orgsNames" readonly="readonly">${map.orgsNames }</textarea>
												  	<input type="hidden" name='orgs' value="${map.orgs }"/>											  	
												</td>
												<td style="text-align: center;height:33px;width: 19%;" >
													<textarea style="height:33px;width:95%;"  name="rolesNames" readonly="readonly">${map.rolesNames }</textarea>
												  	<input type="hidden" name='roles' value="${map.roles }"/>													
												</td>
												<td style="text-align: center;height:33px;width: 19%;" >
													<textarea style="height:33px;width:95%;"  name="regsNames" readonly="readonly">${map.regsNames }</textarea>
													<input type="hidden" name='regs' value="${map.regs }"/>	
												</td>
												<td style="text-align: center;height:33px; width: 19%;">
													<input type="button" style="width:50px;height:24px;" id="editReciever" onclick="EasyextDialog(null,'_','95%','85%','15px',true,true);" value="编辑">
													<input type="button" style="width:50px;height:24px;" id="clearReciever" onclick="clearHandle()" value="清空">
												</td>												
										    </tr>									    									    
									    </tbody>												
									</table>									
								</td>							
							</tr>	
				<tr>
					<th width="20%">是否需要签收：</th>
					<td width="80%">
					<input type="checkbox" name="isConfirm" value="1"
						<c:if test="${notice.isConfirm eq '1'}"> checked="checked"</c:if> />
					</td>
				</tr>
				<tr>
					<th width="20%">起效时间：</th>
					<fmt:formatDate value="${notice.startDate}" var="startDates" pattern="yyyy-MM-dd HH:mm:ss"/>
					<fmt:formatDate value="${notice.expireDate}" var="expireDates" pattern="yyyy-MM-dd HH:mm:ss"/>
					<td width="80%"><input class="easyui-datetimebox" id="startDate" name="startDate"
						style="width:180px" value="${startDates }"/>
						<span>-</span> 
						<input class="easyui-datetimebox" id="expireDate" name="expireDate"
						style="width:180px" value="${expireDates }"/>
					</td>
				</tr>
				<tr>
				<th width="20%" class="must"><span style="padding-right:4px">*</span>正文:</th>
					<td width="80%">
					<textarea class="ckeditor" name="noticeContent" id="noticeContent" style="width:95%;height:80px;">${notice.noticeContent }</textarea>
					</td>
				</tr>

				<tr>
					<th width="20%">附件：</th>
					<td width="80%">
						<input type="file" class="easyui-filebox" id="fileImport">
					</td>
				</tr>
			</tbody>
		</table>
		
		<div class="form-btns" align="center"style="padding-left: 0px;height: 30px">
			<input id="drafSave" class="button" type="button"value="保存草稿">&nbsp; 
			<input id="dataFormSave" class="button" type="button" value="发布">&nbsp; 
			<input id="btnSubmit" class="button" type="button" value="返 回" onclick="window.location.href='${ctx }/rmd/notice/noticeList'">
		</div>
	</div>
</div>
		<input type="hidden" id="isRelease" name="isRelease" value="${notice.isRelease }" />
		<input type="hidden" id="id" name="id" value="${notice.id }"/>  
		<!-- <input type="hidden" id="rmdObj" /> -->
	</form>
</body>
</html>