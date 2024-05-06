<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>磁盘信息</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/sysForm.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
<script type="text/javascript" src="${ctx}/static/jdone/js/common/ext/grid-search.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/log/diskSpaceUsageList.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
		<div >
<!-- 		<div class="row-fluid">
			<form id="searchForm">
				<table>
					<tr class="searchTr">
						<td><label class="search-label">容器标识:</label>
							<input name="containerMark" type="text" class="search-textbox keydownSearch" />
						</td>
						<td><label class="search-label">应用标识:</label>
							<input name="appMark" type="text" class="search-textbox keydownSearch" />
						</td>
						<td><label class="search-label">服务器ip:</label>
							<input name="serverIp" type="text" class="search-textbox keydownSearch" />
						</td>
					</tr>
				</table>
				<div class="form-actions">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchFunc()">
					<input id="btnCancel" class="btn" type="button" value="重置" onclick="ClearQuery();">
				</div>
			</form>
		</div>
		<div class="extend-search">
		</div> -->
		<table class="table" id="containerApp">
			<tr>
				<th><label style="text-align:center;width:100%">ip</label></th>
				<th><label style="text-align:center;width:100%">磁盘状态</label></th>
				<th><label style="text-align:center;width:100%">磁盘信息</label></th>
			</tr>
			<c:forEach items="${diskSpaceUsageList}" var="keyword" varStatus="id">
					<tr>
						<td><label style="text-align:center;width:100%">${keyword.ip}</label></td>
						<td>
							<c:if test="${keyword.diskStatus==0}"><label style="text-align:center;width:100%">正常</label></c:if>
							<c:if test="${keyword.diskStatus==1}"><label style="text-align:center;background-color:orange;width:100%">预警</label></c:if>
							<c:if test="${keyword.diskStatus==2}"><label style="text-align:center;background-color:red;width:100%">异常</label></c:if>
						</td>
						<td><label style="text-align:center;width:100%"><a style="text-decoration:underline;" onclick="openDiskDetails('${keyword.id}')" href="javascript:;">磁盘信息</a></label></td>
					</tr>
			</c:forEach> 
		</table>
	</div>
	<div id="processDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="磁盘使用情况信息" style="width:80%; height:60%;">
		<iframe scrolling="no" id='openProcessList' name="openProcessList" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
	</div>
</body>
</html>
