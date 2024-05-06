<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>KAFKA日志管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">	
	<script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/jdone/js/log/logKafkaList.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<div id="content" class="row-fluid">
		<div style="height: 100%; width: 100%;">
			<div class="searchDiv">
				<form id="searchForm">
					<table style="width: 100%;height: 20%;">
                        <tr class="searchTr">
                            <th>应用标识：</th>
                            <td>
                                <input name="appMark" class="easyui-textbox"/>
							</td>
                            <th>应用名称：</th>
                            <td>
                                <input name="appName" class="easyui-textbox"/>
							</td>
							<th>消息类型：</th>
                            <td>
                                <input name="msgType" class="easyui-textbox"/>
							</td>
							<th>发送主题：</th>
                            <td>
                                <input name="sendTopic" class="easyui-textbox"/>
							</td>
							<th>发送结果：</th>
                            <td>
                                <input name="sendResult" class="easyui-textbox"/>
							</td>
                        </tr>
                        <tr class="searchTr">
							<th>发送时间：</th>
                            <td>
                                <input name="sendTime" class="form-textbox Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
							</td>
							<th>发起人IP：</th>
                            <td>
                                <input name="sendIp" class="easyui-textbox"/>
							</td>
                        </tr>
                        <tr style="text-align: center;">
                        	<td colspan="10" style="algin:center">
                            	<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
                            	<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="ClearQuery();">清空</a>
                            </td>
                        </tr>
                    </table>
				</form>
			</div>
			<div id="datagrid"></div>
		</div>
	</div>
	
</body>
</html>
