<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户签收公告列表</title>
		<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
		<script type="text/javascript" src="${ctx}/static/jdone/js/rmd/userSignNoticeList.js"></script>	
		<script type="text/javascript">var ctx='${ctx}';</script>
		</head>
	<body>
	<div class="searchDiv">
				<form id="searchForm">
					<table>
                        <tr class="searchTr">
                            <th>标题：</th>
                            <td>
                                <input name="noticeTitle" class="easyui-textbox"/>
							</td>
                            <td>
                            	&nbsp;<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search'" onclick="searchFunc('');">查找</a>
                            </td>
                            <td>
                            	&nbsp;<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-clear'" onclick="ClearQuery();">清空</a>
                            </td>
                        </tr>
                    </table>
				</form>
			</div>		
	<div id="datagrid"></div>
	</body>
</html>
