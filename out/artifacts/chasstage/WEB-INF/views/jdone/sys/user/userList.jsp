<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/sys/user/userList.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/common/ext/css/grid-search.css">
	<script type="text/javascript" src="${ctx}/static/jdone/js/common/ext/grid-search.js"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<div class="row-fluid">
		<form id="searchForm">
			<table>
                   <tr class="searchTr">
                        <td><label class="search-label">姓名:</label>
                              <input name="name" class="search-textbox keydownSearch"/>
						</td>
	                    <td><label class="search-label">登录名:</label>
	                              <input name="loginId" class="search-textbox keydownSearch"/>
						</td>
	                    <td><label class="search-label">身份证号码:</label>
	                              <input name="idCard" class="search-textbox keydownSearch" onKeyUp="value=value.replace(/[\W]/g,'')"/>
						</td>
						<td><label class="search-label">所属机构:</label>
							<input  class="dic search-textbox keydownSearch" type="text" id="nameDic" onkeyup="orgChange();"/>
							<input type="hidden" id="code" name="orgCode">
						</td>
					</tr>
              </table>
             <div class="form-actions">
				<input id="btnCx" class="btn btn-primary" type="button" value="查询" onclick="searchFunc()">
				<input id="btnCz" class="btn" type="button" value="重置" onclick="ClearQuery();">
			</div>
		</form>
	</div>
	<div class="extend-search"> </div>
	
	<table id="datagrid"></table>
	
	<div id="ReceiveFeedBackDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="分配角色" style="width:920px; height: 550px;">
		<iframe scrolling="auto" id='openReceiveFeedBack' name="openReceiveFeedBack" frameborder="0" src="" style="width: 100%; height:100%;"></iframe>
	</div>
</body>
</html>
