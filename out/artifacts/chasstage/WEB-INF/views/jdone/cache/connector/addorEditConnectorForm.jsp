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
	<script type="text/javascript" src="${ctx}/static/jdone/js/cache/connector/connectorList.js?1=1"></script>
	<script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
	<body>
 <div class="form-panel">
          <div class="panel-top" style="margin: 0 5px 0 5px">
					<div class="tbar-title">
						<span style="width: 85px;">${not empty cont.id?'修改连接器':'添加连接器'}</span>
					</div>
		 </div> 
	<form id="mainForm" name="mainForm"  method="post">
	<div class="panel-body">
	   <input type="hidden" id="id" name="id" value="${cont.id}">
	   <input type="hidden" id="cacheStatus" name="cacheStatus" value="${cont.cacheStatus}">
		 	 <table class="table_form" cellpadding="0" cellspacing="0" width="80%">
				<tr>
	    			<th width="20%"><font color="red">*&nbsp;连接器名称:</font></th>
	    			<td><input class="required" type="text" width="400px" name="name" value="${cont.name}" ></input></td>
	    		     <th width="15%"><font color="red">*&nbsp;连接器标识:</font></th>
	    			<td><input class="required" type="text" width="400px" name="mark"  value="${cont.mark}"></input></td>	
	    		</tr>
	    		<tr>
	    			<th width="15%">缓存类型:</th>
	    			<td>
		    		<select id ='containerType' class="input-xlarge required" name="cacheType" >	
		    			<option value="map" <c:if test="${cont.cacheType=='map'}">selected</c:if>>map</option>		    	
					    <option value="redis" <c:if test="${cont.cacheType=='redis'}">selected</c:if>>redis</option>
  					</select>	   		
		    		</td>
		    		<th width="15%">连接对象类型:</th>
	    			<td>
			    		<select class="input-xlarge required" name="connObjType" >		    
			    				 <option value='Map' <c:if test="${cont.connObjType=='Map'}">selected</c:if>>Map</option>	
							     <option value='RedisTemplate' <c:if test="${cont.connObjType=='RedisTemplate'}">selected</c:if>>RedisTemplate</option>
							     <option value='JredisPool' <c:if test="${cont.connObjType=='JredisPool'}">selected</c:if>>JredisPool</option>
	  					</select>	   		
		    		</td>
	    		</tr>
	    		<tr>	    		  		
	    			<th width="20%">&nbsp;连接对象标识:</th>
	    			<td><input type="text" width="400px" name="connObjMark" value="${cont.connObjMark}" ></input></td>
	    		     <th width="15%">&nbsp;连接地址:</th>
	    			<td><input type="text" width="400px" name="connAddr"  value="${cont.connAddr}"></input></td>	
	    		</tr>
	    		<tr>
					<th width="12%">
						<label class="control-label">连接器描述:</label>
					</th>
					<td colspan="3">
						<textarea name="connDesc" type="text" style="height: 100px;width: 80%;" maxlength="200">${cont.connDesc}</textarea>
					</td>
				</tr>
	    		<tr>
					<th width="12%">
						<label class="control-label">连接授权信息:</label>
					</th>
					<td colspan="3">
						<textarea name="connAuthInfo" type="text" style="height: 100px;width: 80%;" maxlength="200">${cont.connAuthInfo}</textarea>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<label class="control-label">连接配置信息:</label>
					</th>
					<td colspan="3">
						<textarea name="connConfInfo" type="text" style="height: 100px;width: 80%;" maxlength="200">${cont.connConfInfo}</textarea>
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