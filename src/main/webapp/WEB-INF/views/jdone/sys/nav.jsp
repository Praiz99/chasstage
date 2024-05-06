<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/nav.css" />
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/index/global.js" ></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/index/nav.js" ></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/index/Menu.js" ></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.js" ></script>
<title>底部内容页</title>
</head>

<body>
<div id="container">
	<div id="bd">
    	<div class="sidebar">
        	<div class="sidebar-bg"></div>
            <i class="sidebar-hide"></i>
            <h2><a href="javascript:;"><i class="h2-icon" title="切换到树型结构"></i><span>安全管理</span></a></h2>
            <ul class="nav" id="accordion">
				<li class="nav-li">
					<a href="javascript:;" class="ue-clear"><i class="nav-ivon"></i><span class="nav-text">首页</span></a>
					<ul class="subnav">
						<li class="subnav-li" href="newIndex" data-id="0"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">首页</span></a></li>
					</ul>
				</li>
				<li class="nav-li">
                	<a href="javascript:;" class="ue-clear"><i class="nav-ivon"></i><span class="nav-text">系统管理</span></a>
                	<ul class="subnav">
                        <li class="subnav-li" href="sys/region/regionList" data-id="1"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">区域管理</span></a></li>
                        <li class="subnav-li" href="sys/org/orgList" data-id="2"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">机构管理</span></a></li>
                        <li class="subnav-li" href="sys/user/userList" data-id="3"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">用户管理</span></a></li>
                        <li class="subnav-li" href="sys/menu/menuList" data-id="4"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">菜单管理</span></a></li>
                        <li class="subnav-li" href="admin/sys/paramIndexNew" data-id="5"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">参数管理</span></a></li>
                        <li class="subnav-li" href="sys/dic/dicFrom" data-id="6"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">字典管理</span></a></li>
                        <li class="subnav-li" href="sys/permission/permissionList" data-id="8"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">权限管理</span></a></li>
                        <!-- <li class="subnav-li" href="admin/sys/paramIndexNew" data-id="9"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">参数管理</span></a></li> -->
						<li class="subnav-li" href="sys/cache/configForm" data-id="10"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">缓存容器管理</span></a></li>
						<li class="subnav-li" href="sys/cacheservice/configServiceForm" data-id="11"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">缓存服务管理</span></a></li>
						<li class="subnav-li" href="rmd/notice/noticeList" data-id="14"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">公告管理</span></a></li>
						
						<!-- <li class="lastnav-li">
							<a href="javascript:;" class="ue-clear"><i class="nav-ivon"></i><span class="nav-text">警情管理</span></a>
							<ul class="lastsubnav">
								<li class="lastsubnav-li" href="newIndex" data-id="100"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">首页</span></a></li>
							</ul>
						</li> -->
					</ul>
                </li>
				<li class="nav-li">
                	<a href="javascript:;" class="ue-clear"><i class="nav-ivon"></i><span class="nav-text">任务管理</span></a>
                	<ul class="subnav">
                        <li class="subnav-li" href="task/taskList" data-id="12"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">任务配置</span></a></li>
                        <li class="subnav-li" href="task/taskLogList" data-id="13"><a href="javascript:;" class="ue-clear"><i class="subnav-icon"></i><span class="subnav-text">任务日志</span></a></li>
					</ul>
                </li>
            </ul>
            <div class="tree-list outwindow">
            	<div class="tree ztree"></div>
            </div>
        </div>
        <div class="main">
        	<div class="title">
                <i class="sidebar-show"></i>
                <ul class="tab ue-clear">
                   
                </ul>
                <i class="tab-more"></i>
                <i class="tab-close"></i>
            </div>
            <div class="content">
            </div>
        </div>
    </div>
</div>

<div class="more-bab-list">
	<ul></ul>
    <div class="opt-panel-ml"></div>
    <div class="opt-panel-mr"></div>
    <div class="opt-panel-bc"></div>
    <div class="opt-panel-br"></div>
    <div class="opt-panel-bl"></div>
</div>
</body>

<script type="text/javascript">
	var menu = new Menu({
		defaultSelect: $('.nav').find('li[data-id="0"]')	
	});
	
	// 左侧树结构加载
	var setting = {};

		var zNodes =[
			{ name:"新闻管理",
			   children: [
				 { name:"新闻视频管理",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"新闻频道管理",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"地方新闻管理",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'}
			]},
			{ name:"用户信息设置", open:true,
			   children: [
				 { name:"首页", checked:true,icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"表单",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"表格",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"自定义设置",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'}
			]},
			{ name:"工作安排",
			   children: [
				 { name:"工作安排",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"安排管理",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"类型选择",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"自定义设置",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'}
			]},
			{ name:"数据管理",
			   children: [
				 { name:"工作安排",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"安排管理",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"类型选择",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'},
				 { name:"自定义设置",icon:'${ctx}/static/jdone/style/sys/imgs/index/leftlist.png'}
			]}
		];

	$.fn.zTree.init($(".tree"), setting, zNodes);
	
	
	$('.sidebar h2').click(function(e) {
        $('.tree-list').toggleClass('outwindow');
		$('.nav').toggleClass('outwindow');
    });
	
	$(document).click(function(e) {
		if(!$(e.target).is('.tab-more')){
			 $('.tab-more').removeClass('active');
			 $('.more-bab-list').hide();
		}
    });
</script>
</html>