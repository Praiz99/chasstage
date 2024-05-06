<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/jdone/style/sys/imgs/login" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>执法办案综合应用系统登录界面</title>
<link href="${ctx}/static/jdone/style/sys/css/login/login.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css"/>
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#signup_form").validate();
		$('.rem').click(function() {
			$(this).toggleClass('selected');
		});

		$('#loginAction').click(function() {
			$("#signup_form").submit();
		});

	});
	function KeyDown() {
		if (event.keyCode == 13) {
			$("#signup_form").submit();
		}
	}
</script>
</head>
<body>
	<div class='signup_container'>
		<h1 class='signup_title'>执法办案综合应用系统</h1>
		<img src='${ctxImg}/people.png' style="width: 120px; height: 120px;"
			id='admin' /> <br />提示：需要市级管理员配置之后才可以用账号登陆
		<div id="signup_forms" class="signup_forms clearfix">
			<!-- <form class="signup_form_form" id="signup_form" method="post"
				action="shirologin">
				<div class="form_row first_row">
					<label for="signup_email">请输入用户名</label>
					<input type="text" name="userName" placeholder="请输入用户名"
						id="signup_name" class="required" onkeydown="KeyDown()"></input>
				</div>
				<div class="form_row">
					<label for="signup_password">请输入密码</label>
					<input type="password" name="password" placeholder="请输入密码"
						id="signup_password" class="required" onkeydown="KeyDown()"></input>
				</div>
			</form> -->
			<form class="signup_form_form" id="signup_form"  method="post"
				action="loginCheck">
				<div class="form_row first_row">
					<label for="signup_email">请输入用户名</label>
					<input type="text" name="username" placeholder="请输入用户名"
						id="signup_name" class="required" onkeydown="KeyDown()"></input>
				</div>
				<div class="form_row">
					<label for="signup_password">请输入密码</label>
					<input type="password" name="password" placeholder="请输入密码"
						id="signup_password" class="required" onkeydown="KeyDown()"></input>
				</div>
				<input type="hidden" name="lt" value="${loginTicket}" />  
            	<input type="hidden" name="execution" value="${flowExecutionKey}" />  
           		<input type="hidden" name="_eventId" value="submit" />
           		<input type="hidden" name="service" value="${param.service}" />
			</form>
		</div>
		<div class="login-btn-set">
			<div class='rem'>记住我</div>
			<a href='javascript:void(0)' id="loginAction" class='login-btn'></a>
		</div>
		<p class="copyright">
			<a href="http://jzpt.gat.zj:7001/jwzh/JwzhOpen?type=login_goJzwtYD&sfzh=330226190101010007&token=${token}&reqUrl=page%2Fjzwdx%2FJZ_question_indexcs.jsp%3Fmkid%3Dd3564c32-8651-4462-bf64-21cc092c71e5%26tzwtzl%3D3" target="_blank">常见问题</a><br/>
			有问题请联系系统管理员&nbsp;&nbsp;<br />
			办公室:666983&nbsp;<br />
			移动电话:690475&nbsp;<br />
		</p>
	</div>
	<div class="login-footer">
		<div class="version">
			<span>版本更新：<a href="/rdp/static/zfpt/releasenotes.html"
				target="_blank">点击查看</a></span>&nbsp;&nbsp;&nbsp;&nbsp;<a target="_blank"
				style="cursour: hand" href="/rdp/static/zfpt/qzgx.html">电子签章相关</a>
		</div>
		<div class="download">
			<a href="ftp://10.118.7.2/download/zfptwd" target="_blank">文档下载</a> <a
				href="ftp://10.118.7.2/download/zfptyhczsp" target="_blank">视频下载</a>
			<a href="ftp://10.118.7.2/download/zfptglyczsp" target="_blank">管理员操作视频</a>
			<a href="ftp://10.118.7.2/download/zjblpt.zip" target="_blank">笔录系统</a><br />
			<a href="ftp://10.118.7.2/download/zfptwd/xtycjl.htm" target="_blank">系统异常记录</a>
			<a href="http://zfba.zj/rdp/test.jsp" target="_blank">测试页面</a>
		</div>
		<div class="download">
			<a style="text-decoration: none;">资料下载：</a>
		</div>
	</div>
</body>
</html>