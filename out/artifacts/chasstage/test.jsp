<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.wckj.framework.core.utils.IpAddressUtil"%>
<%@ page import="com.wckj.framework.web.WebContext"%>
<html>
<body>
<%
  out.println("服务端ip: "+IpAddressUtil.getLocalHostIpAddress()); 
%> 
<br/>
<%          
  out.println("客户端ip: "+WebContext.getClientIp()); 
%>

</body>
</html>
