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
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/framework/utils/spell.js" ></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/dic/dicList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body style="margin: 0px;">
<div class="form-panel" style="width: 100%;height:550px;">
             <div class="panel-header" style="height: 20px;font-weight: bold;">操作区域 </div>
             <div id="tt" class="easyui-tabs" style="width:100%;height:100%;">   
                 <div title="字典信息" style="display:none;">   
                    <form id="dicForm" name="dicForm" class="form-horizontal" action="save" method="post">
                    <input type="hidden" id="catId" name="catId" value="${catId}">
                    <input type="hidden" id="catMark" name="catMark" value="${catMark}">
                    <input type="hidden" id="flag" name="flag" value="0">
                    <input type="hidden" id="id" name="id" value="${dic.id}">
                    <input type="hidden" id="enableEdit" name="enableEdit" value="${enableEdit}"> 
	    	         <div class="panel-body">
	                  <table class="table_form" width="80%">
					          <tr>
						         <th>
						            <label class="control-label">中文名称：</label>      
						         </th>
						         <td>
							        <input type="text" id="dicName" name="dicName" style="width: 300px;" value="${dic.dicName}">
						         </td>
						         <th>
						            <label class="control-label">英文名称：</label>
						         </th>
						         <td>
							        <input type="text" id="dicMark" name="dicMark" style="width: 300px;" value="${dic.dicMark}">
						         </td>
							 </tr>
							 <tr>
								<%--  <th>
								   <label class="control-label">过滤条件：</label>
								 </th>
								 <td>
									<input id="filterRule" id="filterRule" name="filterRule" style="width: 300px;" type="text" value="${dic.filterRule}">
								 </td>
								  --%>
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

