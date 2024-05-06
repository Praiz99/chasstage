<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>字典管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
    <script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/dic/dicList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	urlpath = ctx ;
    </script>
</head>
<body style="margin: 0px;">
<div class="form-panel" style="width: 100%;height:550px;">
             <div class="panel-header" style="height: 20px;font-weight: bold;">操作区域 </div>
             <div id="tt" class="easyui-tabs" style="width:100%;height:100%;">   
                 <div title="字典信息" style="display:none;">   
                    <form id="dicForm" name="dicForm" class="form-horizontal" action="save" method="post">
                   <%--  <input type="hidden" id="type" name="type" value="${dicRef.enableEdit}"> --%>
                    <input type="hidden" id="catId" name="catId" value="${catId}">
                    <input type="hidden" id="catMark" name="catMark" value="${catMark}">
                    <input type="hidden" id="id" name="id" value="${dic.id}">
                    <input type="hidden" id="flag" name="flag" value="1">
                    <input type="hidden" id="enableEdit" name="enableEdit" value="${enableEdit}">
	    	         <div class="panel-body">
	                  <table class="table_form" width="80%">
					          <tr>
						         <th>
						           <label class="control-label">所属数据源：</label> 
						         </th>
						         <td>
							        <select id="sourceMark" name="sourceMark"  style="width:300px;" onchange="changeDb();"> 
							             <c:forEach var="source" items="${sourceList}">
							         		<option value="${source.sourceMark}" <c:if test="${dic.sourceMark eq source.sourceMark}">selected</c:if>>
								             		${source.sourceName}
								             </option>
							             </c:forEach>
						            </select>
						         </td>
						         <th>
						           <label class="control-label">数据表：</label>       
						         </th>
						         <td>
							        <input type="text" name="tableMarkName" id="tableMarkName" style="width: 300px;" initVal="${dic.tableMark}">
							        
						         </td>
							 </tr>
							 <tr>
								 <th>
								   <label class="control-label">字典中文名：</label>   
								 </th>
								 <td>
									<input id="dicName" name="dicName" style="width: 300px;" type="text" value="${dic.dicName}">
								 </td>
								 <th>
								   <label class="control-label">字典英文名：</label>  
								</th>
								 <td>
									<input id="dicMark" name="dicMark" style="width: 300px;" type="text" value="${dic.dicMark}">
								 </td>
							 </tr>
							  <tr>
								 <th>
								   <label class="control-label">代码列：</label> 
								 </th>
								 <td>
									<input id="fcode" name="fcode" style="width: 300px;" type="text" value="${dic.fcode}">
								 </td>
								 <th>
								   <label class="control-label">名称列：</label> 
								 </th>
								 <td>
									<input id="fname" name="fname" style="width: 300px;" type="text" value="${dic.fname}">
								 </td>
							 </tr>
							  <tr>
								 <th>
								   <label class="control-label">全拼列：</label> 
								 </th>
								 <td>
									<input id="ffpy" name="ffpy" style="width: 300px;" type="text" value="${dic.ffpy}">
								 </td>
								 <th>
								   <label class="control-label">简拼列：</label>  
								 </th>
								 <td>
									<input id="fspy" name="fspy" style="width: 300px;" type="text" value="${dic.fspy}">
								 </td>
							 </tr>
							 <tr>
								 <th>
								   <label class="control-label">主键列：</label> 
								</th>
								 <td>
									<input id="fpk" name="fpk" style="width: 300px;" type="text" value="${dic.fpk}">
								 </td>
								 <th>
								   <label class="control-label">排序列：</label>
								 </th>
								 <td>
									<input id="forders" name="forders" style="width: 300px;" type="text" value="${dic.forders}">
								 </td>
							 </tr>						 
							 <tr>
							    <%--  <th>
							       <label class="control-label">字典项是否可编辑：</label>   
							     </th>
								 <td style="border: 0">
									<input id="enableEdit1" name="enableEdit" type="radio" value="1" checked="checked" <c:if test="${dicRef.enableEdit==1 }"> checked="checked"</c:if> /><label for='enableEdit1'>是</label>
							        <input id="enableEdit0" name="enableEdit" type="radio" value="0" <c:if test="${dicRef.enableEdit==0 }"> checked="checked"</c:if> /><label for='enableEdit0'>否</label>
								 </td> --%>
								 
								 <th>
								   <label class="control-label">排序字符串：</label>
								 </th>
								 <td>
									<input id="orders" name="orders" style="width: 300px;" type="text" value="${dic.orders}">
								 </td>								 
							     <th>
							       <label class="control-label">过滤条件：</label>   
							     </th>
								 <td>
									<input id="filterRule" name="filterRule" style="width: 300px;" type="text" value="${dic.filterRule}">
								 </td>								 								 
							 </tr>
							 <tr>
								 <th>
							       <label class="control-label">字典描述：</label>   
							     </th>
								 <td colspan="3">
									<input id="dicDesc" name="dicDesc" style="width: 300px;" type="text" value="${dic.dicDesc}">
								 </td>								 
							 </tr>
					  </table>
					 </div>
					</form>    
                 </div>
                 <c:if test="${dic.dicMark!=null}">   
                   <div title="字典值信息" data-options="closable:false" style="overflow:auto;display:none;">   
                      <table id="datagrid"></table>
                      <div id="dd"></div>
                   </div>  
                 </c:if> 
              </div> 
       </div>
</body>
</html>

