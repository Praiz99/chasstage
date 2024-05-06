<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>字典分类管理</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript">
    var ctx = '${ctx}';
    $(function(){
    });
    </script>
</head>
<body>
	<form id="mainForm" name="mainForm" class="form-horizontal" action="${ctx}/com/dic/saveorUpdateDicCat" method="post">
	<input type="hidden" id="id" name="id" value="${dicCat.id}">
	<div class="form-panel">
	<div class="panel-top"></div>
	    <div class="panel-body">
	      <table class="table_form">
            <tr>
                <th style="width:20%;">
                <label class="control-label">分类名称:</label>
                </th>
                <td>
					<input class="required" type="text" style="width: 200px;" name="catName" id="catName"  value="${dicCat.catName}" onchange="setQP('catName', 'catMark', '不能为空');" onblur="setQP('catName', 'catMark', '不能为空')"/>
			    </td>
			</tr>
			 <tr>
                <th style="width:20%;">
                <label class="control-label">分类标识:</label>
                </th>
                <td>
					<input class="required" type="text" style="width: 200px;" name="catMark" id="catMark"  value="${dicCat.catMark}"/>
			    </td>
			</tr>
			 <tr>
                <th style="width:20%;">
                <label class="control-label">分类描述:</label>
                </th>
                <td>
					<input class="required" type="text" style="width: 400px;" name="catDesc" id="catDesc"  value="${dicCat.catDesc}"/>
			    </td>
			</tr>
			<%-- <tr>					
				 <th style="width:20%;">
				 <label class="control-label">备        注:</label>
				 </th>
				<td>
				   <input type="text" style="width: 400px;height: 60px;" id="remark"  name="remark" value="${dicCat.remark}">
				</td>
            </tr> --%>
          </table>
         </div>
        </div>
	</form>
</body>
</html>