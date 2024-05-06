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
		<title>无权访问</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script type="text/javascript">
			 function closeDialog(){
				if (self.frameElement && self.frameElement.tagName == "IFRAME") {
					history.go(-1);
					return true;
				}else{
					if (navigator.userAgent.indexOf("Firefox") != -1 || navigator.userAgent.indexOf("Chrome") !=-1) {
				        window.location.href="about:blank";
				        window.close();
				    } else {
				        window.opener = null;
				        window.open("", "_self");
				        window.close();
				    }
				}
			}
	   </script>
	</head>
	  <style>
              html,body{
                margin: 0px;
                padding: 0px;
                height: 100%;
                width: 100%;
            }
            .div1{
                height: 50%;
                width: 60%;

                position:absolute;
                left: 0;
                right: 0;
                top: 0;
                bottom: 0;
                margin:auto;
            }
            .div2{
			     float: left;
            }
          .div3{
                 float: left;
   				 padding-left: 10%;
            }
            .button{
                 width:100px;
				 height:40px;
				 background:rgba(81,130,214,1);
				 border-radius:2px;
				 font-size:16px;
				 font-family:华文仿宋;
				 font-weight:400;
				 color:rgba(255,255,255,1);
            }
    </style>
<body>
  <div class="div1">
	<div class="div2"><img style="height:320px" src="${ctx}/static/framework/style/imgs/error/sorry.png"></div>
	<div class="div3"><p style="padding-top:50px;font-size:40px;font-family:Arial;font-weight:400;color:rgba(51,51,51,1);line-height:40px;width:400px"><%=ex.getMessage()%><br></p>
      <%-- <p style="margin-top: -50px;font-size:20px;font-family: 华文仿宋;font-weight:300;color:rgba(153,153,153,1);line-height:40px;"><%=ex.getMessage()%><br></p> --%>
      <input class="button" type="button" value="关闭返回" onclick="closeDialog()">
</div>
  </div>
</html>
