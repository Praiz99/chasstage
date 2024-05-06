<%@ page import="com.wckj.framework.core.PropertiesManager" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<%@ include file="/WEB-INF/views/framework/include/test.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/easyui-1.5.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/easyui-1.5.1/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/form.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
<!--[if lte IE 8]>
 <script type="text/javascript" src="${ctx}/static/framework/utils/json2.js"></script>
<![endif]-->

<%
 String matomoEnable = "";
 String matomoUserId = "";
 String matomoUrl = "";
 String matomoSiteId = "";
 try {
  matomoEnable = PropertiesManager.getProp("matomo.enable","false");
  matomoUserId = WebContext.getSessionUser() != null ? WebContext.getSessionUser().getUserId():"";
  matomoUrl = PropertiesManager.getProp("matomo.url");
  matomoSiteId = PropertiesManager.getProp("matomo.siteId");
 } catch (Exception e) {
 }
%>
<script type="text/javascript">
 try {
  $(function(){
   setTimeout(function(){
    var matomoEnable = '<%=matomoEnable %>';
    if("true" == matomoEnable){
     var _paq = window._paq = window._paq || [];
     var matomoUserId = '<%=matomoUserId %>';
     _paq.push(['trackPageView']);
     _paq.push(['enableLinkTracking']);
     (function () {
      var matomoUrl = '<%= matomoUrl %>';
      var matomoSiteId = '<%=matomoSiteId %>';

      var u = "//"+matomoUrl+"/";
      _paq.push(['setTrackerUrl', u + 'matomo.php']);
      _paq.push(['setSiteId', matomoSiteId]);
      var d = document, g = d.createElement('script'), s = d.getElementsByTagName('script')[0];
      g.type = 'text/javascript';
      g.async = true;
      g.src = u + 'matomo.js';
      s.parentNode.insertBefore(g, s);
     })();
     if (matomoUserId != null && matomoUserId != "") {
      _paq.push(['setUserId', matomoUserId]);
     }
    }
   },2000)
  });
 } catch (e) {
 }
</script>
