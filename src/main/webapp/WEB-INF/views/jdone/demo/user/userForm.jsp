<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<form id="baqryxxForm" action="${ctx}/zfpt/aq/baqryxx/saveBaqryxx" method="post">
		<table class="table_form" border=0 cellSpacing=0 cellPadding=0 align="center">
			<tr>
				<th>姓名：</th>
				<td>
					<input type="text" class="commom-txt" name="rsyy" id="rsyy" style="width:150px;"/>
				</td>
				<th>性别：</th>
				<td>
					<input type="text" name="xb" id="xb" style="width:150px;"/>
				</td>				
			</tr>
			<tr>
				<th>姓名：</th>
				<td>
					<input type="text" name="rsyy" id="rsyy" style="width:150px;"/>
				</td>
				<th>性别：</th>
				<td>
					<input type="text" name="xb" id="xb" style="width:150px;"/>
				</td>				
			</tr>
			<tr>
				<th>姓名：</th>
				<td>
					<input type="text" name="rsyy" id="rsyy" style="width:150px;"/>
				</td>
				<th>性别：</th>
				<td>
					<input type="text" name="xb" id="xb" style="width:150px;"/>
				</td>				
			</tr>
			<tr>
				<th>姓名：</th>
				<td>
					<input type="text" name="rsyy" id="rsyy" style="width:150px;"/>
				</td>
				<th>性别：</th>
				<td>
					<input type="text" name="xb" id="xb" style="width:150px;"/>
				</td>				
			</tr>
									<tr>
				<th>姓名：</th>
				<td>
					<input type="text" name="rsyy" id="rsyy" style="width:150px;"/>
				</td>
				<th>性别：</th>
				<td>
					<input type="text" name="xb" id="xb" style="width:150px;"/>
				</td>				
			</tr>
		</table>
	</form>
</body>
</html>
