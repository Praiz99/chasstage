<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp" %>
<%@ taglib prefix="system" uri="/tags/jdone/sys/system" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String wsPath = "ws://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="path" value="<%=path%>"/>
<c:set var="basePath" value="<%=basePath%>"/>
<c:set var="wsPath" value="<%=wsPath%>"/>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/bjfj/css/reset.css">
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/bjfj/css/mapstyle.css">
    <title>视频巡查</title>
</head>
<body class="new-dialog page" style="background-color:transparent!important">
<div class="title">视频巡查</div>
<div class="showSettings">
    <div class="showItem">
        <span class="showBlueColor">111111</span>
        <span class="showBlueText selectItem blueItem">微风险</span>
    </div>
    <div class="showItem">
        <span class="showYellowColor">111111</span>
        <span class="showYellowText selectItem yellowItem">低风险</span>
    </div>
    <div class="showItem">
        <span class="showOrangeColor">111111</span>
        <span class="showOrangeText selectItem orangeItem">中风险</span>
    </div>
    <div class="showItem">
        <span class="showRedColor">111111</span>
        <span class="showRedText selectItem redItem">高风险</span>
    </div>
    <div class="showItem">
        <span class="showMjColor">111111</span>
        <span class="showMjText selectItem mjItem">民警</span>
    </div>
    <div class="showItem">
        <span class="showFkColor">111111</span>
        <span class="showFkText selectItem fkItem">访客</span>
    </div>
</div>
<div id="track" style="display: none; position: absolute; right: 40px;top: 20px;">
    <div class="button">停止视频追踪</div>
</div>
<div class="pd24 wrapper">
    <div class="call-container">
        <div id="map" class="area-mapbjxd">

        </div>
    </div>
</div>
<iframe id="main" style="display: none;"></iframe>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/jquery.min.js"></script>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/layer.js"></script>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/jquery.resize.js"></script>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/sockjs.js"></script>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/index.js?m=99"></script>
<!-- 以下为当前页脚本 -->
<script type="text/javascript">
    var ctx = "${ctx}";
    var baqid = "${baqid}";
    var rybh = "", playRybh = "", playQyid = "";
    var oldw = 1920, oldh = 935;
    $(function () {
        initHeight();
        resetSize();
        //  $("#map").resize(function() {
        //
        // });
        $("body").resize(function () {
            initHeight();
            resetSize();
        })
    });

    function initHeight() {
        var h = $("html").outerHeight();
        console.log(h);
        $(".area-mapbjxd").css("min-height", (h - 40) + "px");
        $(".area-mapbjxd").css("max-height", (h - 40) + "px");
    }

    21
</script>
<script type="text/javascript">
    var headIcon = {width: 20, height: 30, mixWidth: 20, mixHeight: 20};
    var qyMeasure = {
        'a947c903765fa8320176605f6372002a': {x: 1050, y: 80, w: 68, h: 44, fx:-10, fy: 0,oa:0.4},//讯询问室1
        'a947c903765fa8320176605fee29002d': {x: 942, y: 128, w: 64, h: 38, fx: -10, fy: 0,oa:0.4},//讯询问室2
        'a947c903765fa83201766060399b002f': {x: 824, y: 174, w: 74, h: 49, fx: -10, fy: 0,oa:0.4},//讯询问室3
        'a947c903765fa832017660664ad80037': {x: 600, y: 225, w: 80, h: 30, fx: 5, fy: 0,oa:-0.4},//审讯区卫生间
        'a947c903765fa832017660630e810035': {x: 666, y: 267, w: 80, h: 30, fx: 5, fy: 0,oa:-0.4},//审讯区卫生间楼梯
        'a947c903765fa8320176606068490031': {x: 558, y: 289, w: 82, h: 49, fx: -10, fy: 0,oa:0.5},//讯询问室4
        'a947c903765fa83201766060a6170033': {x: 415, y: 341, w: 91, h: 59, fx: -10 ,fy: 0,oa:0.5},//讯询问室5
        'a947c903765fa83201766029754d0021': {x: 544, y: 395, w: 250, h: 25, fx: 0, fy: 0,oa:-0.4},//审讯室走廊
        'a947c903766e23d101766e6f3e72004e': {x: 1220, y: 185, w: 73, h: 25, fx: -3, fy: 0,oa:0.4},//未成年审讯室走廊
        'a947c903765fa832017660263a56001f': {x: 1351, y: 348, w: 70, h: 35, fx: 2, fy: -2,oa:-0.5},//特殊审讯室走廊
        'a947c903765fa83201766041f0ef0026': {x: 1210, y: 319, w: 67, h: 43, fx: 10, fy: 0, oa: -0.5},//未成年讯询问室
        'a947c903765fa8320176609fcc3300a7': {x: 1083, y: 381, w: 67, h: 30, fx: 5, fy: 0, oa: -0.5},//看守区值班室
        'a947c903765fa83201765ffa32280010': {x: 1176, y: 512, w: 150, h: 95, fx: 15, fy: 0, oa:-0.6},//看守区
        'a947c903765fa83201765ff9a068000e': {x: 1051, y: 600, w: 168, h: 53, fx: -10, fy: 0, oa:0.5},//信息采集区
        'a947c903765fa83201765fef8af40008': {x: 854, y: 633, w: 278, h: 54,fx: -10, fy: 0, oa:0.5},//接待大厅
        'a947c903766e23d101766e4dd87a002c': {x: 1560, y: 324, w: 100, h: 26, fx: 0, fy: 0, oa: 0.5},//特殊审讯室
        'a947c903765fa8320176601fe0100019': {x: 1470, y: 365, w: 80, h: 50, fx: -5, fy: 0, oa: 0.5},//等候室4
        'a947c903765fa8320176602194ba001d': {x: 1627, y: 435, w: 70, h: 15, fx: 0, fy: 0, oa: 0.4},//嫌疑人卫生间
        'a947c903765fa83201766020700d001b': {x: 1570, y: 465, w: 80, h: 40,fx: 0, fy: 0, oa: 0.5},//尿检区
        'a947c903765fa8320176601f77ad0017': {x: 1481, y: 500, w: 89, h: 40,fx: 0, fy: 0, oa: 0.5},//等候室3
        'a947c903765fa83201765ffb8bb10014': {x: 1422, y: 532, w: 88, h: 40,fx: 0, fy: 0, oa: 0.5},//等候室2
        'a947c903765fa83201765ffb41fc0012': {x: 1280, y: 630, w: 100, h: 50,fx: 10, fy: 0, oa: -0.6}//等候室1
    }
    $(function () {
        getPosition();
        $("#track").on("click", "div.button", function () {
            rybh = "";
            playQyid = "";
            playRybh = "";
            $("#track").hide();
            var vlcurl = "vlc://quit";
            $("#main").attr("src", vlcurl);
            //layer.msg("停止视频追踪，请手动关闭播放器",{offset:['500px', '340px']});
        });
        $("#map").on("click", "div.icon", function () {
            var qyid = $(this).attr("qyid");
            //console.log(qyid);
            rybh = $(this).attr("rybh");
            openQySxtSp(qyid);
        });
        $('#map').on('mouseenter', '.icon', function () {
            var that = this;
            var title = $(this).attr("ryxm") +
                "当前所在位置:<br/>" + $(this).attr("qymc");
            layer.tips(title, that, {
                tips: [1, '#8da0cc'],
                time: 4000
            });
        });
        $('#map').on('mouseleave', '.icon', function () {
            var that = this;
            var title = $(this).attr("title");
            layer.closeAll();
        });
        interval = setInterval(getPosition, 5 * 1000);
        $(".selectItem").click(function (e) {
            if($(this).hasClass("blueItem")){//蓝色
                if($(this).hasClass("showBlueText")){//不显示
                    $(this).removeClass("showBlueText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showBlueColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".blue").hide();
                }else{
                    $(this).addClass("showBlueText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showBlueColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".blue").show();
                }
            }else if($(this).hasClass("yellowItem")){//黄色
                if($(this).hasClass("showYellowText")){//不显示
                    $(this).removeClass("showYellowText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showYellowColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".yellow").hide();
                }else{
                    $(this).addClass("showYellowText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showYellowColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".yellow").show();
                }
            }else if($(this).hasClass("orangeItem")){//橙色
                if($(this).hasClass("showOrangeText")){//不显示
                    $(this).removeClass("showOrangeText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showOrangeColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".orange").hide();
                }else{
                    $(this).addClass("showOrangeText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showOrangeColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".orange").show();
                }
            }else if($(this).hasClass("redItem")){//红色
                if($(this).hasClass("showRedText")){//不显示
                    $(this).removeClass("showRedText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showRedColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".red").hide();
                }else{
                    $(this).addClass("showRedText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showRedColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".red").show();
                }
            }else if($(this).hasClass("mjItem")){//民警
                if($(this).hasClass("showMjText")){//不显示
                    $(this).removeClass("showMjText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showMjColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".mj").hide();
                }else{
                    $(this).addClass("showMjText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showMjColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".mj").show();
                }
            }else if($(this).hasClass("fkItem")){//访客
                if($(this).hasClass("showFkText")){//不显示
                    $(this).removeClass("showFkText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showFkColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".fk").hide();
                }else{
                    $(this).addClass("showFkText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showFkColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".fk").show();
                }
            }

        })
    });


    $(window).unload(function () {
        if (interval) {
            //console.log("关闭");
            clearInterval(interval);
        }
    });
</script>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("${wsPath}socketServer/websocket?org=${orgCode}");
        //websocket = new WebSocket("ws://44.65.81.219:8888/chasEt/socketServer/websocket?org=${orgCode}");
    } else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("${wsPath}socketServer/websocket?org=${orgCode}");
        //websocket = new MozWebSocket("ws://44.65.81.219:8888/chasEt/socketServer/websocket?org=${orgCode}");
    } else {
        websocket = new SockJS("${basePath}socketServer/sockjs?org=${orgCode}");
        //websocket = new SockJS("http://44.65.81.219:8888/chasEt/socketServer/sockjs?org=${orgCode}");
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvt) {
        console.log(openEvt);
        //alert(openEvt.Data);
    }

    function onMessage(evt) {
        console.log(evt.data);
        var data = JSON.parse(evt.data);
        getPosition();
        if (playRybh == data.rybh) {
            switchQySxtSp(data.qyid);
        } else {
            console.log("当前移动人员与正在播放的人员不一致，不进行视频切换");
        }
    }

    function onError() {
    }

    function onClose() {
    }

    window.close = function () {
        websocket.onclose();
    }
</script>
</body>
</html>
