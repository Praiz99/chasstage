<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>raphael流程图-http://my.oschina.net/lichaoqiang/</title>
    <%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/raphael.js"></script>
    <script type="text/javascript" src="${ctx}/static/jdone/js/act/jdoneProcessPic.js"></script>
    <style type="text/css">
        html, fieldet, legend, div, span, a, form, body{ margin: 0;padding: 0;  }
        body{ font-size:12px;}
        #holder{top: 0px;left: 0px;right: 0px;bottom: 0px; position: absolute;z-index: 999;height: auto;}
        .item{position: absolute; top: 0px; z-index: 0; padding: 0px; height: 30px; width: 200px; text-align: center;}
    </style>
    <script type="text/javascript">
    	var ctx = '${ctx}';
    </script>
</head>
<body>
	<input id="nodeId" name="nodeId" type="hidden" value="${nodeId}">
	<input id="modelId" name="modelId" type="hidden" value="${modelId}">
    <div id="holder"></div>
    <div id="item0" class="item startEnd" data-item='{"nodeId":"0","nextNode":""}' title="开始">开始</div>
</body>
</html>
