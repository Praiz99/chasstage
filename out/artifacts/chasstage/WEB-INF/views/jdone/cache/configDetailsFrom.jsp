<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>缓存详情</title>
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
					<span>${cont.cacheName}</span>	
				</div>
			</div>
	    	<div class="panel-body">
	    	<table class="table_form">
	    		<tr>
	    			<th width="20%">缓存名称:</th>
	    			<td>${cont.cacheName}</td>
	    			<th width="20%">缓存标识:</th>
		    		<td>${cont.cacheMark}</td>
	    		</tr>
	    		<tr>	    			
	    			<th width="20%">缓存类型</th>
		    		<td>${cont.containerType}</td>
		    		<th width="20%">创建时间:</th>
	    			<td>
	    				<fmt:formatDate value="${cont.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    			</td> 			
	    		</tr>
	    		<tr>
	    		   <th width="20%">缓存描述:</th>
	    			<td colspan="3">${cont.cacheDesc}</td>
	    		</tr>	    		
	    		<tr>
		    		<th width="20%">部署信息:</th>
		    		<td colspan="3">${typeValue.keyValue}</td>
	    		</tr>
	    	</table>
	    </div>
	</div>
</body>
</html>