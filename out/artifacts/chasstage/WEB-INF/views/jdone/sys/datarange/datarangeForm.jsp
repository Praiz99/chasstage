<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
var ctx = '${ctx}';
 $(function (){
		$("#src").html(parent.src);
		if($("#id").val() == ""){  //新增时才给定最大排序值
			$("#orderId").val(parent.desort);
		}
		openroles();
		mjDialog = new mjDialog({ target : parent, title : "人员选择", confirmCallBack:mjCallBack,singleSelect:true });
	});
 function mjCallBack(obj){
		if(obj != null){
			$("#"+obj.mjName).val(obj.mjNameStr);
			$("#clrSfzh").val(obj.mjSfzhStr);
			  $.ajax({
				type: "post",
				url: ctx+"/sys/user/findByIdCard",
				data:{idCard:obj.mjSfzhStr},
				dataType: 'json',
				cache: false,
				success: function(data) {
					if(data!=null){
						 $.ajax({
								type: "post",
								url: ctx+"/sys/userRole/findPageList",
								data:{userId:data.id},
								dataType: 'json',
								cache: false,
								success: function(data) {
									$("#role").find("option").remove();
									for(var i=0;i<data.rows.length;i++){
										var obj=document.getElementById('role'); 
										obj.add(new Option(data.rows[i].roleName,data.rows[i].roleCode));
									}
								}
							});
					}
				}
			}); 
		}
		return true;
	}
 function openMjChooser(obj1){
		mjDialog.show($("#"+obj1+"").val(),obj1);
	}
 function ClearQuery(){
		$("#table_form").find("input").val("");
		$("#role").find("option").remove();
		document.getElementById('csjg').innerHTML="";
		openroles();
	}
 function opentest(){
	 var role = document.getElementById("role").value;
	 var mark= document.getElementById("mark").value;
	 $.ajax({
			type: "post",
			url: ctx+"/sys/datarange/getDataRangeType",
			data:{role:role,mark:mark},
			dataType: 'json',
			cache: false,
			success: function(data) {
				document.getElementById('csjg').innerHTML=data;
			}
		});
	}
 
 function openroles(){
	 $.ajax({
			type: "post",
			url: ctx+"/sys/role/findPageList",
			dataType: 'json',
			data:{page:1,rows:500},
			cache: false,
			success: function(data) {
				$("#role").find("option").remove();
				for(var i=0;i<data.rows.length;i++){
					var obj=document.getElementById('role'); 
					obj.add(new Option(data.rows[i].name,data.rows[i].code));
				}
			}
		});
 }
 </script>
 <script type="text/javascript" src="${ctx}/static/jdone/js/sys/user/mjselects.js"></script>
</head>
<body>
	<!-- 菜单操作 开始 -->
		<form id="mainForm" class="form-horizontal" method="post">
			<!-- 隐藏域   开始-->
			<input type="hidden" name="id" id="id" value="${menu.id}"/>
			<input id="isGroup" name="isGroup" type="hidden" value="0"/>
			<input type="hidden" name="groupId" id="groupId" value="${pid}" />  <!-- 父菜单ID判断是否添加子菜单或父菜单 -->
			<div id="main">
			<h2 class="subfild">
	        	<span>${not empty menu.id?'修改功能':'添加功能'}</span>
	        </h2>
            </div>
			<!-- 隐藏域  结束 -->
			<table class="table" cellpadding="0" cellspacing="0" width="99%">
				<tr>
					<th width="12%"><label class="control-label">功能名称:</label></th>
					<td>
					<input type="text" name="name" class="required" value="${menu.name}"/>
					<span class="help-inline">
							<font color="red">*</font>
					</span>
					</td>
					<th width="12%"><label class="control-label">数据权限:</label></th>
					<td>
						<input type="radio" <c:if test="${menu.defaultRange=='org'}"> checked</c:if> name="defaultRange" value="org" /> 单位
                        <input type="radio" <c:if test="${menu.defaultRange=='reg'}"> checked</c:if> name="defaultRange" value="reg" /> 区县
                        <input type="radio" <c:if test="${menu.defaultRange=='city'}"> checked</c:if> name="defaultRange" value="city" />地市
                        <input type="radio" <c:if test="${menu.defaultRange=='province'}"> checked</c:if> name="defaultRange" value="province" /> 全省
					</td>
				</tr>
				<tr>
				   <th width="12%"><label class="control-label">是否设置范围:</label></th>
					<td>
						<input type="radio" <c:if test="${empty menu.isEnableRange}"> checked</c:if> <c:if test="${menu.isEnableRange==0}"> checked</c:if> name="isEnableRange" value="0" /> 否
                        <input type="radio" <c:if test="${menu.isEnableRange==1}"> checked</c:if> name="isEnableRange" value="1" /> 是
					</td>
				    
					<th width="12%"><label class="control-label">是否权限控制:</label></th>
					<td>
						<input type="radio"  <c:if test="${menu.isEnableAuth==0}"> checked</c:if> name="isEnableAuth" value="0" /> 否
                        <input type="radio" <c:if test="${empty menu.isEnableAuth}"> checked</c:if> <c:if test="${menu.isEnableAuth==1}"> checked</c:if> name="isEnableAuth" value="1" /> 是
					</td>
				</tr>
				<tr>
					<th width="12%"><label class="control-label">排序ID:</label></th>
					<td colspan="3">
						<input id="orderId" name="orderId" ltype="number" class="required"  type="text" value="${menu.orderId}"/>
					<span class="help-inline">
							<font color="red">*</font>
					</span>
					</td>
				</tr>
				<tr>
				<th width="12%"><label class="control-label">功能标识:</label></th>
					<td colspan="3" style="height:80px;">
					   <textarea  style="width:95%;height: 60px;" class="required" id="mark"  name="mark" >${menu.mark}</textarea>
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
			</table>
			<c:if test="${not empty menu.id}">
			<table id="table_form" class="table" cellpadding="0" cellspacing="0" width="99%">
				<tr>
				<th width="12%"><label class="control-label">权限测试:</label></th>
					<td colspan="3">
						当前所选用户：
						<input id="clrXm" name="clrXm" style="width:100px" type="text" />
						<input type="hidden" name="clrSfzh" id="clrSfzh">
						当前所选角色：
						<select id="role">
						 </select>
						<a onclick="openMjChooser('clrXm');" href="javascript:;" style="text-decoration:underline">选择用户</a>&nbsp;&nbsp;&nbsp;
						<a onclick="ClearQuery();" href="javascript:;" style="text-decoration:underline">清空</a>&nbsp;&nbsp;&nbsp;
						<a onclick="opentest();" href="javascript:;" style="text-decoration:underline">测试</a>
					</td>
				</tr>
				<tr>
				<th width="12%"><label class="control-label">测试结果:</label></th>
					<td colspan="3">
						<span id="csjg" style="color:red;font-weight:bold;font-size:20px"></span>
					</td>
				</tr>
			</table>
			</c:if>
			</form>
</body>
</html>