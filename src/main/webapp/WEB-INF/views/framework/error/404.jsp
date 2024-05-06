<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>404 - 未找到页面</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/error/main.css">
</head>
<body>
  <!-- zh -->
    <div id="main" class="zh">
    <div id="header">
      <h1><span class="icon">!</span>404<span class="sub">页面未找到</span></h1>
    </div>
    <div id="content">
      <h2><br>您所请求的页面无法找到</h2>
      <p>服务器无法正常提供信息。<br>
      目标页面可能已经被更改、删除或移到其他位置，或您所输入页面地址错误。</p>
      <div class="utilities">
        <a class="button right" href="#" onClick="history.go(-1);return true;">返回...</a>
        <div class="clear"></div>
      </div>
    </div>
  </div>
</html>
