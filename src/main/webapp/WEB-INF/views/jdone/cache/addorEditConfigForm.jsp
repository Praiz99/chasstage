<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加缓存容器</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/jdone/js/cache/configFormList.js"></script>
	<script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
	<body>
 <div class="form-panel">
          <div class="panel-top" style="margin: 0 5px 0 5px">
					<div class="tbar-title">
						<span class="tbar-label">添加缓存容器</span>
					</div>
		 </div> 
	<form id="mainForm" name="mainForm"  method="post">
	<div class="panel-body">
	   <input type="hidden" id="id" name="id" value="${cont.id}">
		 	 <table class="table_form" cellpadding="0" cellspacing="0" width="80%">
				<tr>
	    			<th width="20%"><font color="red">*&nbsp;分组名称:</font></th>
	    			<td><input class="required" type="text" width="400px" name="cacheName" value="${cont.cacheName}" ></input></td>
	    		     <th width="15%">分组描述:</th>
	    			<td><input class="required" type="text" width="400px" name="cacheDesc"  value="${cont.cacheDesc}"></input></td>	
	    		</tr>
	    		<tr>
	    			<th width="20%"><font color="red">*&nbsp;分组标识:</font></th>
	    			<td><input class="required" type="text" width="400px" name="cacheMark" value="${cont.cacheMark}" ></input></td>
	    			<th width="15%">缓存类型:</th>
	    			<td>
		    		<select id ='containerType' class="input-xlarge required" name="containerType" >		    	
					   <c:forEach items="${typeValue}" var="str" >					
						      <c:choose>  
						         <c:when test="${str eq cont.containerType}">
						            <option value='${str}' selected="selected">${str}</option>
						         </c:when>
						          <c:otherwise>
						            <option value='${str}'>${str}</option>
						          </c:otherwise>									       
						       </c:choose> 
						</c:forEach>						    
  					</select>	   		
		    		</td>
	    		</tr>
	    		<tr>	    		  		
	    			<th width="15%">启用:</th>
	    			<td>
		    			<input type="radio"  name="isEnable" value="1" 
		    			<c:if test="${cont.isEnable=='1'}"> checked='checked'</c:if> checked='checked'/>是&nbsp;
		    			<input type="radio"  name="isEnable" value="0" 
		    			<c:if test="${cont.isEnable=='0'}"> checked='checked'</c:if>/>否
					</td>
					<th width="15%">清除缓存:</th>
	    			<td>
		    			<input type="radio"  name="enableClear" value="1" 
		    			<c:if test="${cont.enableClear=='1'}"> checked='checked'</c:if> checked='checked'/>是&nbsp;
		    			<input type="radio"  name="enableClear" value="0" 
		    			<c:if test="${cont.enableClear=='0'}"> checked='checked'</c:if>/>否
					</td>
	    		</tr>
	    	</table>
	    	<div class="form-btns">
					<input id="btnSubmit" class="button" type="button" onclick="submitForm()" value="保 存">&nbsp;
					<input id="btnCancel" class="button" type="button" onclick="back()" value="返 回">
			</div>	
	    </div>	
	</form>		
	</div> 
	</body>
</html>