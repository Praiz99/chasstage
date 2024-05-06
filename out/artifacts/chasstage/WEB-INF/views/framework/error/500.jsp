<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
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
		//保存用于记录api请求异常
		request.setAttribute("jdone.api.record.error.exception", ex);
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>500 - 系统内部异常</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/error/main.css">
		<script type="text/javascript">
			var detailFlag = false;
			function showDetail(){
				var dom = document.getElementById("main");
				var detailDom = document.getElementById("detailMsg");
				if(!detailFlag){
					dom.setAttribute("style","width:80%;");
					detailDom.setAttribute("style","display:block;");
				}else{
					dom.setAttribute("style","width:50%;");
					detailDom.setAttribute("style","display:none;");
				}
				detailFlag = !detailFlag;
				var detailBtn = document.getElementById("detail");
				if(detailFlag){
					detailBtn.innerHTML = "隐藏详情...";
				}else{
					detailBtn.innerHTML = "显示详情...";
				}
			}
		</script>
</head>
<body style="overflow: auto;">
  <!-- zh -->
    <div id="main" class="zh">
    <div id="header">
      <h1><span class="icon">!</span>500<span class="sub">系统内部异常</span></h1>
    </div>
    <div id="content">
      <h2><br>系统程序发生异常</h2>
      <p>
      		该功能的程序出现异常，异常信息：<%=ex.getMessage()%>
      		<a id="detail" class="button" style="padding-left: 20px;font-style:italic;text-decoration:underline;" href="#" onClick="javascript:showDetail();">显示详情...</a>
      </p>
      <div id="detailMsg" style="display: none;"><p><%=sb.toString() %></p></div>

      <div class="utilities">
        <a class="button right" href="#" onClick="history.go(-1);return true;">返回...</a>
        <div class="clear"></div>
      </div>
    </div>
  </div>
</html>
