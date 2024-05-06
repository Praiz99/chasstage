<%@ page import="com.wckj.framework.core.utils.IpAddressUtil"%>
<%@ page import="com.wckj.framework.web.WebContext"%>
<%
  out.println("<meta name='serverIp' content='" + IpAddressUtil.getLocalHostIpAddress() + "'/>");
  out.println("<meta name='clientIp' content='" + WebContext.getClientIp() + "'/>");
%> 
