<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>磁盘信息</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
<script type="text/javascript" src="${ctx}/static/jdone/js/common/ext/grid-search.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
	<div >
		<table class="table" id="containerApp">
			<tr>
				<th><label style="text-align:center;width:100%">路径</label></th>
				<th><label style="text-align:center;width:100%">内存大小</label></th>
				<th><label style="text-align:center;width:100%">已使用</label></th>
				<th><label style="text-align:center;width:100%">未使用</label></th>
				<th><label style="text-align:center;width:100%">使用率</label></th>
			</tr>
			<c:forEach items="${diskPropslist}" var="keyword" varStatus="id">
					<tr>
						<td><label style="text-align:center;width:100%">${keyword.url}</label></td>
						<td><label style="text-align:center;width:100%">${keyword.size}</label></td>
						<td><label style="text-align:center;width:100%">${keyword.used}</label></td>
						<td><label style="text-align:center;width:100%">${keyword.avail}</label></td>
						<td><label style="text-align:center;width:100%">${keyword.use}</label></td>
					</tr>
			</c:forEach> 
		</table>
	</div>
</body>
</html>
