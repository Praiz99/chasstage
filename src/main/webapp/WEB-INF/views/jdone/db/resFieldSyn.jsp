<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>同步字段</title>
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/db/css/resFieldSyn.css">
<script type="text/javascript" src="${ctx}/static/jdone/js/db/resFieldSyn.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
<style type="text/css">
.panel-body{border-color: #ffffff;}
.datagrid-header{border-color: #ffffff;}
</style>
</head>
<body>
	<form id="resFieldSynForm" name="resFieldSynForm">
		<input id="resId" name="resId" type="hidden" value="${resField.resId}">
		<input id="tableMark" name="tableMark" type="hidden" value="${resField.tableMark}">
		<div id="content" style="margin: 4px;">
			<table class="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
		    			<td class="sdp-border-tab-top">
							<div class="tm">
								元数据管理-同步表 --&gt;${resField.tableMark}
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="hf-form">
								<!-- 表单 开始 -->
								<table cellspacing="0" width="100%">
									<tbody>
										<tr>
											<td style="text-align: center;">
												元数据
											</td>
											<td style="text-align: center;">
											</td>
											<td style="text-align: center;">
												物理表
											</td>
										</tr>
										<tr>
											<td width="50%">
												<div style="margin: 2px;">
													<table id="fieldGrid">
													</table>
												</div>
											</td>
											<td style="width: 30px; vertical-align: middle; padding: 0 3px;">
												<a href="#" style="width: 30px;" title="将所有物理列覆盖同步到元数据列" onclick="batchSynField()">
													<img style="margin-left: -3px;" src="${ctx}/static/framework/plugins/easyui-1.5.1/themes/icons/back.png" ></img>
													<br>覆<br>盖<br>左<br>边
												</a>
											</td>
											<td width="50%">
												<div style="margin: 2px;">
													<table id="tableGrid">
													</table>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</td>
					</tr>
					
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
