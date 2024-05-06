<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<%@ taglib prefix="system" uri="/tags/jdone/sys/system"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<title><system:app id="${aid}"/></title>
		<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/menu/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-menu/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-menu/css/menu.css">
		<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-menu/menu.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/style.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/main.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/composition.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/side-toolbar.css" />
		<script type="text/javascript" src="${ctx}/static/jdone/js/sys/index/index.js"></script>
		<script type="text/javascript" src="${ctx}/static/jdone/js/sys/index/composition.js"></script>
		<script type="text/javascript" src="${ctx}/static/jdone/js/sys/index/loginRoleSelect.js"></script>
		<style>
			.zjzp-iframe {
				position: absolute;
				z-index: 9999;
			}
		</style>
		<script type="text/javascript">
			var ctx = "${ctx}";
			var appMenus = <system:app id="${aid}" out="menu"/>;
			var homepageUrl = "${indexUrl}";
		</script>
		<%
			String zjzpEnable = "";
			String zjzpXtbs = "";
			boolean isSuperAdmin = false;
			try {
				zjzpEnable = PropertiesManager.getProp("zjzp.enable","true");
				zjzpXtbs = PropertiesManager.getProp("zjzp.xtbs","A3300001800002016070001");
				isSuperAdmin = WebContext.getSessionUser() != null ? WebContext.getSessionUser().getIsSuperAdmin():false;
			} catch (Exception e) {
			}
		%>
		<script type="text/javascript">
			$(function(){
				if('true' == '<%=zjzpEnable%>'){
					addZjzp('<%=zjzpXtbs%>');
				}
			})
		</script>
		<script type="text/javascript">
		function fl(){

			var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
            var isOpera = userAgent.indexOf("Opera") > -1;
            if (userAgent.indexOf("Chrome") > -1){
				 window.open("http://sfzsk.gat.zj/#/?darparyment=ga")
            }//判断是否为谷歌
            if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
			   var objShell=new ActiveXObject("WScript.Shell");
			   objShell.Run("cmd.exe /c start %USERPROFILE%/AppData/Local/Google/Chrome/Application/chrome.exe sfzsk.gat.zj/#/?darparyment=ga",0,true)
            };//判断是否为IE
			}
		</script>
		  <script language="javascript">
               function showPic(e,sUrl){
                            var x,y;
                            x = e.clientX;
                            y = e.clientY;
                            document.getElementById("Layer1").style.left = x+2+'px';
                            document.getElementById("Layer1").style.top = y+2+'px';
                            document.getElementById("Layer1").innerHTML = "<img border='0' src=\"" + sUrl + "\">";
                            document.getElementById("Layer1").style.display = "";
                            }
                            function hiddenPic(){
                            document.getElementById("Layer1").innerHTML = "";
                            document.getElementById("Layer1").style.display = "none";
                        }
    </script>
	</head>
	<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
		<noscript>
			<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
			    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
			</div>
		</noscript>
         <div id="Layer1" style="display: none; position: absolute; z-index: 100;">
        </div>

		<!-- 头部 -->
		<div id="container" region="north" split="true" border="false" style="overflow: hidden;height:40px;">
			<div id="hd">
		    	<div class="hd-top">
		            <h1 class="logo"><span style="position: absolute;margin-top:6px; margin-left: 50px;color: #fff;font-family: 'Microsoft YaHei';font-size: 12px;"><system:app id="${aid}"/></span></h1>
		           	<div class="container_horn" style="cursor:pointer" id="indexNotice" onclick="openNotice();">
				    	<!--<div class="horn_info"><marquee loop="-1" id="notice"></marquee></div> -->
				    </div>
		            <div class="setting ue-clear">
		            <div class="user-info">
		                <a href="javascript:void(0);" class="user-avatar" onclick="roleSelect();"><span></span></a>
		                <span class="user-name"><system:user/>：<system:user out="roleName"/></span>
			            <c:if test="${userAppList!=null}">
			                <a class="more-info"></a>
			            </c:if>
		            </div>
		                <ul class="setting-main ue-clear">
		                <!-- 	<li><a href="javascript:;">桌面</a></li> -->
		                    <li  style="width:80px;"><a href="javascript:;"  onclick="openUser();">用户设置</a></li>
		                    <li><a href="http://10.118.7.9/" target="_blank">下载</a></li>
							<li><a href="javascript:;" title="IE需11以上，谷歌需58版以上"  onclick="fl()">知识库</a></li>
							<li style="width:80px;" ><a href="" onmouseout="hiddenPic();" onmousemove="showPic(event,'${ctx}/static/jdone/style/sys/imgs/index/dingding.png')">执法维护</a></li>
		                    <li style="width:100px;" ><a href="http://41.190.112.246:8080/user-center" target="_blank" >全省智能执法监督管理平台</a></li>
		                    <c:if test="<%=isSuperAdmin==true%>">                   
			                    <li style="width:60px;" ><a href="http://41.190.20.212:38300/zjzfm/static/zfm/dp/dwdp/unitIndex.html#/unitIndex?dwbh=330100000000&bs=1" target="_blank" >执法码</a></li>
			                    <li style="width:70px;" ><a href="https://fjgl.hzos.hzs.zj/ssl-login" target="_blank" >非羁押码</a></li>
			                    <li style="width:80px;" ><a href="http://41.204.4.57:11000/" target="_blank" >法律法规库</a></li>
			                    <li style="width:80px;" ><a href="https://41.204.4.57:10203/gfxwjzg/login" target="_blank" >法治融在线</a></li>
			                </c:if>	  
		                    <li  style="width:80px;"><a href="javascript:;" onclick="openNotice()">通知公告</a></li>
		                    <li><a href="javascript:;" class="close-btn exit"></a></li>
		                </ul>
		            </div>
		        </div>
			</div>
		</div>

	    <!-- 左边菜单导航 -->
	    <div region="west" split="true" title=" " style="width:220px;" id="menu-nav" >
			<div id="left-menu"></div>
	    </div>

	    <!-- 工作区 -->
	    <div id="mainPanle" region="center" style="background-color:#fff; overflow-y:hidden">
	        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
				<div title="首页" style="overflow:hidden;" id="home">
				    <iframe style="width: 100%;height: 100%" id="mainIframe" src="" frameborder="0" scrolling="no"></iframe>
				</div>
			</div>
	    </div>

	    <!-- 选项卡右键菜单 -->
		<div id="mm" class="easyui-menu" style="width:150px;display:none;">
			<div id="mm-tabclose">关闭</div>
			<div id="mm-tabcloseall">全部关闭</div>
			<div id="mm-tabcloseother">除此之外全部关闭</div>
			<div class="menu-sep"></div>
			<div id="mm-tabcloseright">当前页右侧全部关闭</div>
			<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
			<div class="menu-sep"></div>
			<div id="mm-exit">退出</div>
		</div>

		<c:if test="${userAppList!=null}">
			<!-- 切换系统 -->
			<div class="opt-panel user-opt" style="top:52px;margin-left:79%; position: absolute;z-index: 9;">
				 <ul id="apps">
				     <c:forEach items="${userAppList}" var="app">
	        			<li <c:if test="${app.id==aid}"> style="background-color:#0d89c3"</c:if> >
	        				<a id ="${app.id}" onclick='javascript:changeApp(this);' class='text'>${app.name}</a>
	        			</li>
	    			</c:forEach>
				 </ul>
			     <div class="opt-panel-tl"></div>
			     <div class="opt-panel-tc"></div>
			     <div class="opt-panel-tr"></div>
			     <div class="opt-panel-ml"></div>
			     <div class="opt-panel-mr"></div>
			     <div class="opt-panel-bl"></div>
			     <div class="opt-panel-bc"></div>
			     <div class="opt-panel-br"></div>
			     <div class="opt-panel-arrow"></div>
			</div>
		</c:if>
		<div class="app-qr-box" id="app-qr-box">
	       <div class="bg-box" style="position:relative">
    		<div class="panel-tool" style="top: auto;"><a href="javascript:noImg();" class="panel-tool-close"></a></div>
    			<div class="" style="text-align:center">
		        <img src="${ctx}/static/jdone/style/sys/imgs/index/dingding.png" style="width:110px;height:110px">
		        <p class="desc" style="background-color: white;">钉钉扫一扫，您的24小时办案系统客服</p>
		      </div>
<%-- 		      <div class="qr-item-box">
		        <img src="${ctx}/static/jdone/style/sys/imgs/index/dingding.png" alt="钉钉扫一扫，您的智能客服">
		        <p class="desc">钉钉扫一扫，您的24小时办案系统客服</p>
		      </div> --%>
	        </div>
		</div>
		<!-- 弹窗 -->
		<c:if test="${fn:length(notices) >0}">
			<div id="winpop" style="position:fixed;height:0px;">
				  <div class="title">
				 			<div class="com_proxy"></div>
				 			<div style="padding-left:37px;float:left">系统通知</div>
					        <div class="com_close" onclick="tips_pop()"></div>
				  </div>
				  <div class="div_content">
					    <div class="notice_content">
					    <c:forEach var="notice" varStatus="noticeStatus" items="${notices}" end="2">
					    	<a href="javascript:void(0);" onclick="doPreview('${notice.id}')">${noticeStatus.index+1}.${notice.title}</a>
					       <br/>
					    </c:forEach>
					    </div>
					    <div style="width:80px;height:80px;padding-left: 300px;font-size: 12px;line-height: 23px;font-weight:400;color:rgba(153,153,153,1);">
						   	 <c:forEach var="notice" varStatus="noticeStatus" items="${notices}" end="2">
						   	 <fmt:formatDate value="${notice.fbsj}" pattern="yyyy-MM-dd" var="fasjsx"/>
								${fasjsx}<br/>
						     </c:forEach>
					    </div>
				  </div>
				  <div style="width:380px;height:1px;background:#bbb;"></div>
				  <div style="text-align:right;margin-top:8px">
				  		<input class="bztx_button" type="button" value="不再提醒" onclick="notrmd('${noticeIds}')">&nbsp; &nbsp;
						<input class="ckgd_button" type="button" value="查看更多"  onclick="openNotice();" >&nbsp; &nbsp;
				  </div>
			</div>
		</c:if>
		<!-- 选择角色 -->
		<div id="selectRole" ></div>
		<input id="currentSelected" type="hidden" value="${currentSelected}" />
		<input id="userRoles" type="hidden" value='${userRoles}' />
		<div id="processDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="修改密码" style="width:1000px; height:600px;overflow:hidden">
			<iframe scrolling="auto" id='openProcessList' name="openProcessList" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
		</div>
		<div id="processNotice" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="公告信息" style="width:1000px; height:600px;overflow:hidden">
			<iframe scrolling="no" id='openProcessNotice' name="openProcessNotice" frameborder="0" src="" style="width: 100%; height: 98%;"></iframe>
		</div>
		<div id="helpDialog" class="easyui-dialog" closed="true" buttons="#dlg-buttons" modal="true" title="常用资料下载" style="width:750px; height:500px;overflow:hidden;">
			<iframe scrolling="auto" id='openDownloadList' name="openDownloadList" frameborder="0" src="" style="width: 102%; height: 100%;"></iframe>
		</div>

		<div style="position: absolute;z-index: 9999">
			<!-- 浙警智评-V1.0-202208 BEGIN -->
			<div id="zjzp" class="zjwd-zjzp"></div>
			<!-- 浙警智评-V1.0-202208 END-->
		</div>
	</body>
</html>
