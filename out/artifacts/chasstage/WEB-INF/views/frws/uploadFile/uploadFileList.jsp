<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>上传文件管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
	<script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/static/frws/uploadFile/uploadFileList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<div id="content" class="row-fluid">
		<div id="right" style="height: 100%; width: 100%;float: left;">
			<div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>文件名：</th>
                            <td>
                                <input name="realName" class="easyui-textbox keydownSearch"/>
							</td>
                            <th>文件类型：</th>
                            <td>
                                <input name="fileType" class="easyui-textbox keydownSearch"/>
							</td>
                            <th>业务类型：</th>
                            <td>
                                <input name="bizType" class="easyui-textbox keydownSearch"/>
							</td>
                            <th>上传时间：</th>
			    			<td>
			    				<input type="text" class="textbox Wdate" id="uploadStartTime" name="uploadStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,maxDate:'#F{$dp.$D(\'uploadEndTime\')}'});" style="height: 24px;"/>
								-
								<input type="text" class="textbox Wdate" id="uploadEndTime" name="uploadEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'uploadStartTime\')}'});" style="height: 24px;"/>
			    			</td>
                            <td>
                            	<a class="easyui-linkbutton" href="#" id="keydownSearch" data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
                            </td>
                            <td>
                            	<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="ClearQuery();">清空</a>
                            </td>
                        </tr>
                    </table>
				</form>
			</div>
			<table id="datagrid"></table>
		</div>
	</div>
</body>
</html>
