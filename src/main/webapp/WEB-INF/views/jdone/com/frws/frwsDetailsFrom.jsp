<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文件服务详情</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/org/orgForm.css"> 
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
<div class="form-panel">
       	<div class="panel-top">
				<div class="tbar-title">
					<span>${frws.appName}</span>	
				</div>
			</div>
	    	<div class="panel-body">
	    	<table class="table_form">
	    		<tr>
	    			<th width="20%">应用名称:</th>
	    			<td>${frws.appName}</td>
	    			<th width="20%">应用访问根路径:</th>
		    		<td>${frws.appRootUrl}</td>
	    		</tr>
	    		<tr>	    			
	    			<th width="20%">应用标识:</th>
		    		<td>${frws.appMark}</td>
		    		<th width="20%">服务端根路径:</th>
	    			<td>${frws.serverRootUrl}</td> 			
	    		</tr>
	    		<tr>
	    		   <th width="20%">应用描述:</th>
	    			<td colspan="3">${frws.appDesc}</td>
	    		</tr>	    		
	    	</table>
	    </div>
	</div>
</body>
</html>