<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/dic/JDONE_SYS_ORG.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/user/mjSelectDialog.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body oncontextmenu = "return false" >
	<div id="content" class="row-fluid">
		<div class="searchDiv" style="width:700px;height:360px;border: 1px solid #87b3fc;padding-right: 16px;">
			<form id="mj_searchForm">
				<table >
					<tr class="searchTr">
						<th style="text-align: right;width:8%;">姓名：</th>
						<td><input type="text" name="name" style="border: 1px solid #0ae;height: 22px;width: 115px;"/>
						<!-- 判断是民警还是协警 -->
						<!-- <input type="hidden" name="userType"value="1"/> --></td>
						<th style="text-align: right;width:8%;">警号：</th>
						<td><input type="text" name="loginId" style="border: 1px solid #0ae;height: 22px;width: 115px;"/></td>
						<th style="width:12%;">机构单位：</th>
						<td>
				    		<input type="text"  pageSize="8" hiddenField="cldwBh" resultWidth="250" style="width: 150px;" dicName="JDONE_SYS_ORG" searchField="code,name,jpcode,scode" class="dic" initVal="${orgCode}" />
							<input type="hidden" name="orgCode" id="cldwBh">
						</td>
						<td><a class="easyui-linkbutton"   data-options="iconCls:'icon-search'" onclick="search_mj();">查找</a></td>
					</tr>
				</table>
			</form>
			<table class="" width="90%" height="90%" style="margin-top: 1px;">
				<tr class="searchTr">
					<th style="text-align: right;width:13%;">民警选择：</th>
					<td style="width: 10px;">
						&nbsp;
					</td>
			        <td style="width: 180px;" >
			           	<div class="sundun-box" style="width: 200px;padding: 0 0 3px 0;border: solid 1px #87b3fc;border-top: 0;margin: 5px;">
			           		<div class="topcenter" style="width: 100%;height: 26px;margin: 0 0 5px 0;">
			           			<div style="border: 1px solid #AECAF0;height: 26px;padding: 0px 0 0 10px;line-height:2;">可选民警</div>
			           		</div>
		           			<div id="listbox1" style="width: 194px;height: 255px;overflow: auto;margin-left: 2px;">
			           			<table id="listtable1" style="border-collapse:separate; border-spacing:0px 10px;">
			           			</table>
		           			</div>
			           	</div>
			         </td>
			         <td style="width: 100px;text-align:center;font-size: 14px;">
		            	<input type="button" value="&gt;&gt;" onclick="moveToRight();" title="选择民警" style="width:35px;height: 22px;cursor: pointer;">
		            	<br/><br/><br/>
		            	<input type="button" value="&lt;&lt;" onclick="moveToLeft();" title="移除民警" style="width:35px;height: 22px;cursor: pointer;">
			         </td>
			         
			         <td style="width: 180px;">
		            	<div class="sundun-box" style="width: 200px;padding: 0 0 3px 0;border: solid 1px #87b3fc;border-top: 0;margin: 5px;">
		            		<div class="topcenter" style="width: 100%;height: 26px;margin: 0 0 5px 0;">
		            			<div style="border: 1px solid #AECAF0;height: 26px;padding: 0px 0 0 10px;line-height: 2;">待输入民警</div>
		            		</div>
		            			<div id="listbox2" style="width: 194px;height: 255px;overflow: auto;margin-left: 2px;">
		            				<table id="listtable2" style="border-collapse:separate; border-spacing:0px 10px;">
		            				</table>
		            			</div>
		            	</div>
			         </td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
