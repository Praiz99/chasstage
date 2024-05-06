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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/hzqtfj/css/mapstyle.css">
    <title>视频巡查</title>
</head>
<body class="new-dialog page" style="background-color:transparent!important">
<div class="title">视频巡查</div>
<div class="showSettings">
    <div class="showItem">
        <span class="showRedColor">111111</span>
        <span class="showRedText selectItem redItem">嫌疑人</span>
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
<script src="${ctx}/static/chas/bigscreen/hzqtfj/js/index.js"></script>
<!-- 以下为当前页脚本 -->
<script type="text/javascript">
    var ctx = "${ctx}";
    var baqid = "${baqid}";
    var rybh = "", playRybh = "", playQyid = "";
    var oldw = 1920,oldh=935;
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
        'ff8080817d802da3017d8412d71b0005': {x: 1550, y: 540, w: 170, h: 130, fx:20, fy: 0,oa:0},//待检区
        'ff8080817d802da3017d84473b8b000a': {x: 1123, y: 523, w: 349, h: 109, fx: 5, fy: 0,oa:0},//信息登记检查区
        'ff8080817d802da3017d84480e36000c': {x: 1445, y: 641, w: 80, h: 35, fx: 2, fy: 0,oa:0},//隐私检查区1
        'ff8080817d802da3017d84488ceb000f': {x: 1332, y: 641, w: 82, h: 35, fx: 2, fy: 0,oa:0},//隐私检查区2
        'ff8080817d802da3017d844943820011': {x: 1130, y: 643, w: 162, h: 31, fx: 0, fy: 0,oa:0},//尿检区
        'ff8080817d802da3017d844b10db0013': {x: 917, y: 492, w: 155, h: 128, fx: 0, fy: 0,oa:0},//看守区1
        'ff8080817d802da3017d844b819a0015': {x: 915, y: 636, w: 179, h: 40, fx: 0 ,fy: 0,oa:0},//醒酒区
        'ff8080817d802da3017d844c05ea0017': {x: 726, y: 640, w: 171, h: 34, fx: 0, fy: 0,oa:0},//等候室1
        'ff8080817d802da3017d844c3b220019': {x: 737, y: 597, w: 162, h: 39, fx: 0, fy: 0,oa:0},//等候室2
        'ff8080817d802da3017d844c731a001b': {x: 751, y: 536, w: 151, h: 24, fx: 0, fy: 0,oa:0},//等候室3
        'ff8080817d802da3017d844c9005001d': {x: 761, y: 494, w: 140, h: 27, fx: 3, fy: 0, oa:0},//等候室4
        'ff8080817d802da3017d844ca816001f': {x: 777, y: 424, w: 31, h: 20, fx: 0, fy: 0, oa:0},//等候室5
        'ff8080817d802da3017d844cc0270021': {x: 818, y: 425, w: 30, h: 20, fx: 0, fy: 0, oa:0},//等候室6
        'ff8080817d802da3017d844cdb3c0023': {x: 859, y: 424, w: 30, h: 20, fx: 0, fy: 0, oa:0},//等候室7
        'ff8080817d802da3017d844cf55a0025': {x: 899, y: 422, w: 30, h: 23,fx: 0, fy: 0, oa:0},//等候室8
        'ff8080817d802da3017d844d17f40027': {x: 938, y: 422, w: 35, h: 21, fx: 0, fy: 0, oa:0},//等候室9
        'ff8080817d802da3017d844d5e1d0029': {x: 979, y: 420, w: 34, h: 22, fx: 0, fy: 0, oa:0},//等候室10
        'ff8080817d8da311017d8da928030003': {x: 291, y: 469, w: 464, h: 21, fx: 0, fy: 0, oa:0},//审讯区A走廊
        'ff8080817d8da311017d8da962aa0005': {x: 147, y: 562, w: 578, h: 30,fx: 0, fy: 0, oa:0},//审讯区B走廊
        'ff8080817d8da311017d8de397be001e': {x: 774, y: 452, w: 297, h: 36,fx: 0, fy: 0, oa:0},//看守区2
        'ff8080817d8da311017d8e42bef80022': {x: 744, y: 565, w: 145, h: 26,fx: 0, fy: 0, oa:0},//审讯区B走廊入口
        'ff8080817d8da311017d8e4f8f130024': {x: 360, y: 601, w: 86, h: 75,fx: 0, fy: 0, oa:0},//毒品称重室
        'ff8080817d8da311017d8e500cf50026': {x: 689, y: 417, w: 70, h: 46,fx: -2, fy: 0, oa:0},//讯/询问室1
        'ff8080817d8da311017d8e502eb90028': {x: 603, y: 418, w: 64, h: 45,fx: -3, fy: 0, oa:0},//讯/询问室2
        'ff8080817d8da311017d8e50ad69002a': {x: 215, y: 494, w: 85, h: 64,fx: -10, fy: 0, oa:0},//特殊讯/询问室
        'ff8080817d8da311017d8e513834002c': {x: 512, y: 417, w: 58, h: 46,fx: -3, fy: 0, oa:0},//讯/询问室3
        'ff8080817d8da311017d8e51504a002e': {x: 422, y: 419, w: 65, h: 45,fx: -6, fy: 0, oa:0},//讯/询问室4
        'ff8080817d8da311017d8e5173820030': {x: 668, y: 495, w: 64, h: 62,fx: -2, fy: 0, oa:0},//讯/询问室5
        'ff8080817d8da311017d8e5197990032': {x: 563, y: 496, w: 80, h: 61,fx: -4, fy: 0, oa:0},//讯/询问室6
        'ff8080817d8da311017d8e51ee810034': {x: 333, y: 417, w: 70, h: 47,fx: -8, fy: 0, oa:0},//未成年讯/询问室
        'ff8080817d8da311017d8e522cce0036': {x: 456, y: 495, w: 80, h: 63,fx: -8, fy: 0, oa:0},//讯/询问室7
        'ff8080817d8da311017d8e5247b40038': {x: 338, y: 496, w: 80, h: 61,fx: -8, fy: 0, oa:0},//讯/询问室8
        'ff8080817d8da311017d8e5260c4003a': {x: 245, y: 605, w: 80, h: 81,fx: -10, fy: 0, oa:0},//讯/询问室9
        'ff8080817d8da311017d8e528823003c': {x: 120, y: 605, w: 80, h: 81,fx: -12, fy: 0, oa:0},//讯/询问室10
        'ff8080817d8da311017d8e5465e3003e': {x: 670, y: 605, w: 45, h: 75,fx: -5, fy: 0, oa:0},//卫生间
        'ff8080817d8da311017d8eaed9fd0041': {x: 1098, y: 492, w: 152, h: 28,fx: 0, fy: 0, oa:0},//取物区通道
        'ff8080817d8da311017d8eaf490d0043': {x: 1024, y: 411, w: 51, h: 29,fx: 0, fy: 0, oa:0},//民警通道
        'ff8080817d8da311017d8eaf72de0045': {x: 1287, y: 492, w: 157, h: 27,fx: 0, fy:0, oa:0}//办案区出口
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
        $("#map").on("click", "div.xyr", function () {
            var qyid = $(this).attr("qyid");
            //console.log(qyid);
            rybh = $(this).attr("rybh");
            openQySxtSp(qyid);
        });
        $('#map').on('mouseenter', '.xyr', function () {
            var that = this;
            var title = $(this).attr("ryxm") +
                "当前所在位置:<br/>" + $(this).attr("qymc");
            layer.tips(title, that, {
                tips: [1, '#8da0cc'],
                time: 4000
            });
        });
        $('#map').on('mouseleave', '.xyr', function () {
            var that = this;
            var title = $(this).attr("title");
            layer.closeAll();
        });
        interval = setInterval(getPosition, 5 * 1000);

    });

    $(".selectItem").click(function (e) {
       if($(this).hasClass("redItem")){//红色
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
