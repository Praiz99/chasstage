<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%response.setStatus(500);%>

<%
	Throwable ex = null;
	if (exception != null){
		ex = exception;
	}
	
	if(request.getAttribute("javax.servlet.error.exception") != null){
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
	}
	
	// 编译错误信息
	StringBuilder sb = new StringBuilder("错误信息：");
	if (ex != null) {
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));	
		sb.append(stringWriter.toString());
	} else {
		sb.append("未知错误.");
	}	

	//记录日志
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>403状态页面</title>
<style type="text/css">
body {margin:0;padding:0;font-size:14px;line-height:1.231;color:#555;text-align:center;font-family:"\5fae\8f6f\96c5\9ed1","\9ed1\4f53",tahoma,arial,sans-serif;}
a {color:#555;text-decoration:none;}
a:hover {color:#1abc9c;}
#container {width:684px;height:315px;margin:100px auto 0px auto;border:#2c3e50 solid 6px;background-color:#2c3e50;}
#container #title {overflow:hidden; padding-top:30px;}
#container #title h1 {font-size:36px;text-align:center;color:#FFFFFF;}
#content p{ font-size:18px;}
#footer {width:100%;padding:20px 0px;font-size:16px;color:#555;text-align:center;}
</style>
</head>

<body>
<div id="container">
<div id="title"><h1>{禁止} <%=ex.getMessage()%> <br/></h1></div>
<!-- <div id="content">
<p><a href="javascript:history.go(-1)" style="color:#F00">尝试返回上一页</a></p>
<br />
<p style="font-size:24px;font-weight:bold;color:#1abc9c">403状态页面</p>
</div> -->
</div>

</body>
</html>