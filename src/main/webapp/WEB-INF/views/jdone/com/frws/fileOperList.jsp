<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文件服务管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_UPLOAD_FILE_TYPE.js"></script>
	<%-- <script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_ID_USER.js"></script> --%>
	<script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_FRWS.js"></script>
	<script type="text/javascript" src="${ctx}/static/jdone/js/com/frws/fileOperList.js"></script>
	<script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
	<body>
	<form id="searchForm">
			<div class="form-panel">
				<div class="panel-body">
					<table id="table_form" class="table_form" style="margin-bottom:10px">
	                    <tr>
			    			<th width="15%">文件名称:</th>
			    			<td width="18%">
			    			<input type="text" name="realName" />
			    				</td>
			    			<th width="15%">文件分类:</th>
				    		<td width="28%">
				    		<input type="text"  pageSize="10" hiddenField="bizType" dicName="ZD_SYS_UPLOAD_FILE_TYPE" class="dic"  />
			    			<input  type="hidden" name="bizType" id="bizType"/>
								
				    		<th class="ptcx">上传时间:</th>
				    		<td>
			    				<input type="text" class="Wdate" style="width:100px" id="scStartTime" name="scStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,maxDate:'#F{$dp.$D(\'scEndTime\')}'});"/>
								-
								<input type="text" class="Wdate" style="width:100px" id="scEndTime" name="scEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'scStartTime\')}'});" />
			    			</td>
	    				</tr>
	    				<tr>
			    			<th>上传人:</th>
			    			<td>
			    			<input type="text"  pageSize="10" hiddenField="createUserId" dynamic="true"  dicName="ZD_SYS_ID_USER" class="dic"  />
			    			<input type="hidden" name="createUserId" id="createUserId"></input></td>
			    			<th>服务名称:</th>
				    		<td>
					    		<input type="text"  pageSize="10" hiddenField="frwsMark" dicName="ZD_SYS_FRWS" class="dic"  />
								<input type="hidden" name="frwsMark" id="frwsMark"></td>
				    	    <th>容器标识:</th>
								<td><input type="text" name="storageMark" id="storageMark"></td>
	    				</tr>
	                 </table>
	                 <div class="form-btns">
	                 	<a id="btnCx" class="easyui-linkbutton"  data-options="iconCls:'icon-search'" onclick="searchFunc();">查询</a>
	                 	<a id="btnCz" class="easyui-linkbutton"  data-options="iconCls:'icon-clear'" onclick="ClearQuery();">重置</a>
					</div>	
				</div>
			</div>
		</form>
	       <div id="frwsForm"></div> 	 
	         <div id="detainfo"></div>         
	</body>
</html>