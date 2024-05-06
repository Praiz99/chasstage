<%@ page language="java" contentType="text/javascript; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.wckj.framework.web.WebContext" %>
<%@ page import="com.wckj.framework.json.jackson.JsonUtil" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

//设置ContextPath	
var __ctx = '<%=request.getContextPath()%>';

//设置会话ID
var __jsessionId = '<%=request.getSession().getId()%>';

//设置用户信息
<%
	Object user = WebContext.getSessionUser();
	if(user!=null){
		System.out.println(user);
%>
var __sessionUser = <%=JsonUtil.getJsonString(user) %>;
<%	
	}
%>;

//查询条件
<%
String condition = (String)request.getSession().getAttribute("last_condition");
if(condition==null) condition = "";
%>
var __condition = '<%=condition %>';

//查询上下文
<%
Map<String,Object> mapper = (Map<String,Object>)request.getSession().getAttribute("conditionsMapper");
String lastMenuId = (String)request.getSession().getAttribute("last_menuId");
if(lastMenuId==null) lastMenuId = "";
Object conditionsMap = null;
if(mapper!=null)conditionsMap = mapper.get(lastMenuId);
if(conditionsMap==null) conditionsMap = new HashMap<String,Object>(0);
%>
var __conditionMap = <%=JsonUtil.getJsonString(conditionsMap) %>;