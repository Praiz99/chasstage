<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>通知、公告</title>
    <%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <link href="${ctx}/static/jdone/style/sys/css/rmd/news.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/static/jdone/js/rmd/previewNotice.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    	var id = '${notice.id}';
    </script>
</head>
<body>
<center>
		<div class="b-news-view" style="width: 100%">
			<!--蓝色容器B start  -->
			<div class="porlet-1-bframe" style="margin-bottom:0">
				<table>
					<tr>
						<td valign="top" class="porlet-1-bframe-mm">
							<!-- 容器内的部分 start	-->
							<table width="100%">
								<tr>
									<td valign="top" height="500px">
										<table>
											<tr height="450px" valign="top">
												<td>
													<div class="b-news-view-title-div">
														<div class="b-news-view-title">
															${notice.noticeTitle }
														</div>
													</div>
													<div class="b-news-view-info">
														<div class="cb"></div>
														<!-- 清除浮动样式 -->
														<div class="b-news-view-info-single">
															发布人：${notice.issuer }
														</div>
														<div class="b-news-view-info-single">
														<!-- 发布时间？ -->
															发布时间：<fmt:formatDate value="${notice.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
														</div>
														<div class="cb"></div>
														<!-- 清除浮动样式 -->
													</div>
													<div class="b-news-view-content">${notice.noticeContent }</div>
												</td>
											</tr>
										</table>
									</td>
									<td class="b-news-view-right" style="width: 300px">
										<!-- 附件信息 start-->
										<div class="b-news-rel-div" style="width: 300px">
											<div class="list-ul-b">
												<div class="list-ul-b-div">
													<div class="list-ul-b-title">
														<div class="list-ul-b-title-txt">
															附件信息
														</div>
													</div>
													<ul class="attachment"></ul>
												</div>
											</div>
										</div>
										<!-- 附件信息 end-->
									</td>
								</tr>
							</table>
							<!-- 容器内的部分 end	-->
						</td>
						<td class="porlet-1-bframe-mr"></td>
					</tr>
					<tr class="porlet-1-bframe-bm">
						<td class="porlet-1-bframe-bl"></td>
						<td></td>
						<td class="porlet-1-bframe-br"></td>
					</tr>
				</table>
			</div>
			<!--蓝色容器B end  -->
		</div>
		<a class="easyui-linkbutton" href="#" onclick="window.close();" style="width:60px;height:30px;">关闭</a>
		<c:if test="${notice.isConfirm eq '1'}"> 
			<input type="button" class="easyui-linkbutton" style="width:60px;height:30px;" value="签&nbsp;收" id="sign"/>
		</c:if> 
	</center>

</body>
</html>
