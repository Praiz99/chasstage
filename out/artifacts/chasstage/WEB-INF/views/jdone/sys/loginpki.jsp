<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/jdone/style/sys/imgs/login" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<title>执法办案综合应用系统登录界面</title>
<link href="${ctx}/static/jdone/style/sys/css/login/login.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.css"/>
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
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
	
	function changeNormalLogin() {
		var loginUrl = window.location.href;
		window.location.href = loginUrl.replace("/loginpki","/login");
	}
	
</script>
</head>
<body>
	<div class='signup_container'>
		<h1 class='signup_title'>执法办案综合应用系统</h1>
		<img src='${ctxImg}/people.png' style="width: 120px; height: 120px;"
			id='admin' /> <br />
		<div id="signup_forms" class="signup_forms clearfix" style="border: none;">
			<form class="signup_form_form" id="signup_form" method="post"
				action="${pkiCheckUrl}">
			</form>
		</div>
		<div class="login-btn-set">
			<a href='javascript:void(0)' id="loginAction" class='login-btn2'></a>
		</div>
		<a style="cursor: pointer;" onclick="changeNormalLogin();">
			<p class="copyright" style="margin-top: 10px;">
				<span style="font-size: 18px; padding-left: 20px;">
				<font color="blue">切换到警号登录</font></span>
			</p>
		</a>
		<p class="copyright">
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