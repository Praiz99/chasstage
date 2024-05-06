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
	<script type="text/javascript" src="${ctx}/static/jdone/js/cache/biz/bizList.js?1=1"></script>
	<script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
	<body>
 <div class="form-panel">
          <div class="panel-top" style="margin: 0 5px 0 5px">
					<div class="tbar-title">
						<span style="width: 85px;">${not empty biz.id?'修改业务缓存':'添加业务缓存'}</span>
					</div>
		 </div> 
	<form id="mainForm" name="mainForm"  method="post">
	<div class="panel-body">
	   <input type="hidden" id="id" name="id" value="${biz.id}">
		 	 <table class="table_form" cellpadding="0" cellspacing="0" width="80%">
				<tr>
	    			<th width="20%"><font color="red">*&nbsp;业务名称:</font></th>
	    			<td><input class="required" type="text" width="400px" name="bizName" value="${biz.bizName}" ></input></td>
	    		     <th width="15%"><font color="red">*&nbsp;业务类型:</font></th>
	    			<td><input class="required" type="text" width="400px" name="bizType"  value="${biz.bizType}"></input></td>	
	    		</tr>
	    		<tr>
	    			<th width="15%">缓存容器:</th>
	    			<td>
		    		<select class="input-xlarge required" name="cacheMark" >		    	
					    <c:forEach items="${containers}" var="str" >					
						      <c:choose>  
						         <c:when test="${str.containerMark eq biz.cacheMark}">
						            <option value='${str.containerMark}' selected="selected">${str.containerName}</option>
						         </c:when>
						          <c:otherwise>
						            <option value='${str.containerMark}'>${str.containerName}</option>
						          </c:otherwise>									       
						       </c:choose> 
						</c:forEach>							    
  					</select>	   		
		    		</td>
		    		<th width="15%">是否启用:</th>
	    			<td>
			    		<select class="input-xlarge required" name="isEnable" >		    	
							     <option value='1' <c:if test="${cont.isEnable=='1'}">selected</c:if>>已启用</option>
							     <option value='0' <c:if test="${cont.isEnable=='0'}">selected</c:if>>已停用</option>
	  					</select>	   		
		    		</td>
	    		</tr>
	    		<tr>
					<th width="12%">
						<label class="control-label">业务描述:</label>
					</th>
					<td colspan="3">
						<textarea name="bizDesc" type="text" style="height: 100px;width: 80%;" maxlength="200">${biz.bizDesc}</textarea>
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