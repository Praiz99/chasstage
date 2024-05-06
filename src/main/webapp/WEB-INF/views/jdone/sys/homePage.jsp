<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jdone/style/sys/css/index/newIndex.css" />
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/ui/jquery-ui-1.9.2.custom.min.js" ></script>
<script type="text/javascript" src="${ctx}/static/jdone/js/sys/index/global.js" ></script>

<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>
<script src="${ctx}/static/jdone/js/sys/index/homePage.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/jquery-plugins/vticker/jquery.vticker-min.js" ></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
<title>首页</title>
</head>
<body>
		<input type="hidden" id="noticeId" value="${notices.id}"/>
		<input type="hidden" id="falg" value="${falg}"/>
		<input type="hidden" id="noticeTitle" value="${notices.noticeTitle}"/>
		<input type="hidden" id="noticeContent" value="${notices.noticeContent}"/>
<div id="container">
	<div id="hd">
    </div>
    <div id="bd">
    	<div id="main">
            	<ul class="nav-list ue-clear">
            	<li class="nav-item desk">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">我的桌面</p>
                    </a>
                </li>
                <li class="nav-item news">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">新闻资讯</p>
                    </a>
                </li>
                <li class="nav-item notice">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">公告通知</p>
                    </a>
                </li>
                
                <li class="nav-item plan">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">工作计划</p>
                    </a>
                </li>
                <li class="nav-item contacts">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">通讯录</p>
                    </a>
                </li>
                <li class="nav-item mail">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">我的邮件</p>
                    </a>
                </li>
                <li class="nav-item logs">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">我的日志</p>
                    </a>
                </li>
                <li class="nav-item dosthings">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">待办事宜</p>
                    </a>
                </li>
                <li class="nav-item fav">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">收藏夹</p>
                    </a>
                </li>
                
                <li class="nav-item browser">
                	<a href="javascript:;" >
                        <p class="icon"></p>
                        <p class="title">浏览器</p>
                    </a>
                </li>
            </ul> 
            
            <ul class="content-list">
            	<li class="content-item system">
                	<h2 class="content-hd">
                    	<span class="opt">
                        	<span class="refresh" title="刷新"></span>
                            <span class="setting" title="设置"></span>
                            <span class="report" title="导出"></span>
                            <span class="close" title="关闭"></span>
                        </span>
                    	<span class="title">系统概况</span>
                        
                    </h2>
                    <div class="content-bd">
                    	<img src="${ctx}/static/jdone/style/sys/imgs/index/pic1.png" />
                    </div>
                </li>
                <li class="content-item dothings">
                	<h2 class="content-hd">
                    	<span class="opt">
                        	<span class="refresh" title="刷新"></span>
                            <span class="setting" title="设置"></span>
                            <span class="report" title="导出"></span>
                            <span class="close" title="关闭"></span>
                        </span>
                    	<span class="title">待办事项</span>
                    </h2>
                    <div class="content-bd">
                    	<ul class="content-list things">
                        	<li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">查干湖冬捕壮观景象</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">江西新余：一座城为重病男童圆梦</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">学生建4.5平米1室1厅1厨1卫 欲申请专利</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">央视直播曝光中纪委办公区</a>
                            </li>
                        </ul>
                    </div>
                </li> 
                <li class="content-item richeng">
                	<h2 class="content-hd">
                    	<span class="opt">
                        	<span class="refresh" title="刷新"></span>
                            <span class="setting" title="设置"></span>
                            <span class="report" title="导出"></span>
                            <span class="close" title="关闭"></span>
                        </span>
                    	<span class="title">日程安排</span>
                    </h2>
                    <div class="content-bd">
                    	<ul class="content-list things">
                        	<li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">查干湖冬捕壮观景象</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">江西新余：一座城为重病男童圆梦</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">学生建4.5平米1室1厅1厨1卫 欲申请专利</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">央视直播曝光中纪委办公区</a>
                            </li>
                        </ul>
                    </div>
                </li>
                
                <li class="content-item system">
                	<h2 class="content-hd">
                    	<span class="opt">
                        	<span class="refresh" title="刷新"></span>
                            <span class="setting" title="设置"></span>
                            <span class="report" title="导出"></span>
                            <span class="close" title="关闭"></span>
                        </span>
                    	<span class="title">数据统计</span>
                    </h2>
                    <div class="content-bd">
                    	<img src="${ctx}/static/jdone/style/sys/imgs/index/pic2.png" />
                    </div>
                </li>
                
                <li class="content-item news">
                	<h2 class="content-hd">
                    	<span class="opt">
                        	<span class="refresh" title="刷新"></span>
                            <span class="setting" title="设置"></span>
                            <span class="report" title="导出"></span>
                            <span class="close" title="关闭"></span>
                        </span>
                    	<span class="title">新闻资讯</span>
                    </h2>
                    <div class="content-bd">
                    	<ul class="content-list things">
                        	<li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">查干湖冬捕壮观景象</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">江西新余：一座城为重病男童圆梦</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">学生建4.5平米1室1厅1厨1卫 欲申请专利</a>
                            </li>
                            <li class="content-list-item">
                            	<i class="icon"></i>
                                <a href="javascript:;">央视直播曝光中纪委办公区</a>
                            </li>
                        </ul>
                    </div>
                </li>

					<li class="content-item news" id="notice">
						<h2 class="content-hd">
							<span class="opt">
							    <span class="refresh" title="刷新"onclick="refreshs();"></span>
								<span class="setting" title="设置"></span>
                           		<span class="report" title="导出"></span>
							 	<span class="close" title="关闭"></span>
							</span>
						 <span class="title">通知公告</span>
						</h2>
						<div class="content-bd" id="content">
						<div class="divs" style="width: 95%; height:145px; margin-top: 10px; margin-left: 10px;">
							<ul class="content-list things" id="contents">
	                        </ul>
	                    </div>
	                    <a href="javascript:;" onclick="theMore()" style="float:right;">[更多]</a>
						<!-- <a href="javascript:;" onclick="sotSingNotice()">[未签收公告]</a> -->
						</div> 
					</li>
				</ul>
        </div>
    </div>
</div>
</body>
</html>