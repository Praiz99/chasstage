<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>字典值管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="mainForm" name="mainForm" class="form-horizontal" action="${ctx}/sys/dic/saveorUpdateDicCode" method="post">
	<input type="hidden" id="id" name="id" value="${dicCode.id}" />
	<input type="hidden" id="dicId" name="dicId" value="${dicId}"/>
	<input type="hidden" id="isAdd" name="isAdd" value="${isAdd}"/>
	<div class="form-panel">
	<div class="panel-top"></div>
	    <div class="panel-body">
	      <table class="table_form">
            <tr>
                <th style="width:20%;">
                <label class="control-label">代码:</label>
                </th>
                <td>
					<input class="required" type="text" style="width: 200px;"  id="code" name = "code" value="${dicCode.code}"/>
					<span class="help-inline">
							<font color="red">*</font>
					</span>
			    </td>
			</tr>
			 <tr>
                <th style="width:20%;">
                <label class="control-label">名称:</label>
                </th>
                <td>
					<input class="required" type="text" style="width: 200px;" id="name"  name="name" value="${dicCode.name}" ligerui="width:400" validate="{required:true,minlength:1,maxlength:100}"  onchange="setJP('name', 'spy', '不能为空');setQP('name', 'fpy', '不能为空');" onblur="setJP('name', 'spy', '不能为空');setQP('name', 'fpy', '不能为空')" />
			        <span class="help-inline">
							<font color="red">*</font>
					</span>
			    </td>
			</tr>		
			<tr>					
				 <th style="width:20%;">
				 <label class="control-label">全拼:</label>
				 </th>
				<td>
				   <input class="required" type="text" style="width: 200px;" id="fpy" name="fpy" value="${dicCode.fpy}"/>
				   <span class="help-inline">
							<font color="red">*</font>
					</span>
				</td>
            </tr>
            <tr>					
				 <th style="width:20%;">
				 <label class="control-label">简拼:</label>
				 </th>
				<td>
				   <input class="required" type="text" style="width: 200px;" id="spy"  name="spy" value="${dicCode.spy}"/>
				   <span class="help-inline">
							<font color="red">*</font>
					</span>
				</td>
            </tr>
            <tr>					
				 <th style="width:20%;">
				 <label class="control-label">排序:</label>
				 </th>
				<td>
					<input type="text" style="width: 200px;" id="orderId" name="orderId" value="${dicCode.orderId}"/>
				</td>
            </tr>
          </table>
         </div>
        </div>
	</form>
</body>
</html>