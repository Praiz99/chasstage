<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<%@ taglib prefix="system" uri="/tags/jdone/sys/system" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>一体化采集</title>
</head>
<body class="new-dialog page" style="background-color:transparent!important">
</body>
<!-- 以下为当前页脚本 -->
<script type="text/javascript">
    var rybh = '${rybh}';
    var address = '${address}';
    var ythcjForm = '${ythcjForm}';

    window.location.href = 'http://' + address + '/chas/chasYthcjController/'+ythcjForm+'?fromSign=baqryxx&rybh=' + rybh;
</script>
</html>
