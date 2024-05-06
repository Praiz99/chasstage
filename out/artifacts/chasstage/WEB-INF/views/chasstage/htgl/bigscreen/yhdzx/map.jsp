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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/yhdzx/css/mapstyle.css">
    <title>视频巡查</title>
</head>
<body class="new-dialog page" style="background-color:transparent!important">
<%--<div class="title">视频巡查</div>
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
</div>--%>
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
<script src="${ctx}/static/chas/bigscreen/yhdzx/js/index.js"></script>
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

</script>
<script type="text/javascript">
    var headIcon = {width: 20, height: 30, mixWidth: 20, mixHeight: 20};
    var qyMeasure = {
        '20221212152023122C4B215C103C1C38': {x: 983, y: 272, w: 73, h: 40, fx:0, fy: 0,oa:0},//等候室1
        '202212121520347A67C3B353746CDBD0': {x: 1059, y: 272, w: 41, h: 26, fx: 2, fy: 0,oa:0},//等候室2
        '20221212152044F54D8E874CCF90C959': {x: 1118, y: 272, w: 37, h: 26, fx: 3, fy: 0,oa:0},//等候室3
        '20221212152055B81F44B1C855871485': {x: 1163, y: 272, w: 36, h: 26, fx: 3, fy: 0,oa:0},//等候室4
        '202212121521057F65B83B0017BBF185': {x: 1251, y: 272, w: 81, h: 25, fx: 4, fy: 0,oa:0},//等候室5
        '20221214100119DCF7CA62442B1C1447': {x: 1346, y: 272, w: 36, h: 25, fx: 6, fy: 0,oa:0},//等候室6
        '2022121410013049AF654EDEF5F4175B': {x: 1389, y: 272, w: 36, h: 25, fx: 6 ,fy: 0,oa:0},//等候室7
        '202212141001393C393A09F4FD0EAD4B': {x: 1430, y: 272, w: 83, h: 25, fx: 7, fy: 0,oa:0},//等候室8
        '2022121410055271F7FB57264B4ABCE5': {x: 1444, y: 481, w: 222, h: 117, fx: 32, fy: 0,oa:0},//待检区
        '20221214100638043C6E3EB7428F6C7C': {x: 1381, y: 338, w: 182, h: 167, fx: 42, fy: 0,oa:0},//信息采集室
        '20221214100758113B74BBDB89B4BC23': {x: 1058, y: 300, w: 447, h: 54, fx: 2, fy: 0, oa:0},//看守区
        '202212141008541792CF3B9ED478D7F7': {x: 1540, y: 295, w: 53, h: 51, fx: 22, fy: 0, oa:0},//尿检区
        //'2022121410094512C441BE820DA8B92A': {x: 777, y: 320, w: 77, h: 70, fx: -7, fy: 0, oa:0},//看守区厕所
        '202212141012138C0C0F1BE4A98A099A': {x: 714, y: 342, w: 77, h: 70, fx: -6, fy: 0, oa:0},//讯/询问室1
        '202212141012568938498EEAAF481232': {x: 623, y: 342, w: 82, h: 70,fx: -8, fy: 0, oa:0},//讯/询问室2
        '20221214101345B4B584BE769A61E3C5': {x: 540, y: 342, w: 75, h: 70, fx: -10, fy: 0, oa:0},//讯/询问室3
        '2022121410151581F8A34B6D0B64B672': {x: 455, y: 342, w: 79, h: 70, fx: -13, fy: 0, oa:0},//讯/询问室4
        '20221214101531C34C568346951B2315': {x: 364, y: 342, w: 87, h: 70, fx: -19, fy: 0, oa:0},//讯/询问室5
        '20221214101547C4A2F69E5976D3CC69': {x: 284, y: 342, w: 72, h: 70, fx: -28, fy: 0, oa:0},//讯/询问室6
        '202212141016014B218B70FD3134AB77': {x: 196, y: 342, w: 83, h: 70, fx: -32, fy: 0, oa:0},//讯/询问室7
        '202212141016173A04B6BD3BF7DAD483': {x: 102, y: 342, w: 90, h: 70, fx: -35, fy: 0, oa:0},//讯/询问室8
        '20221215103446F2FB5B06F049AA60A0': {x: 327, y: 250, w: 87, h: 61, fx: -20, fy: 0, oa:0},//讯/询问室9
        '2022121510345984D978DC4EB7766728': {x: 431, y: 250, w: 103, h: 61,fx: -20, fy: 0, oa:0},//讯/询问室10
        '202212151035174513DDBE4C778E8814': {x: 540, y: 250, w: 100, h: 61,fx: -20, fy: 0, oa:0},//讯/询问室11
        '2022121510362601E0AAAABC37442E43': {x: 184, y: 269, w: 121, h: 42,fx: -20, fy: 0, oa:0},//特殊审讯室
        '202212151037007832A5EF0A3B6750B5': {x: 659, y:250, w: 101, h: 61,fx: -15, fy: 0, oa:0},//未成年询\讯问室
        '20221215103848EEE2664499150EF384': {x: 148, y: 302, w: 648, h: 45,fx: 0, fy: 0, oa:0},//审讯区走廊
        //'202212151040265B8E63B161D9481521': {x: 1214, y: 280, w: 89, h: 86,fx: 10, fy: 0, oa:0},//审讯区走廊后门出入口
        '20221215104121D21FB3A15346248F5F': {x: 1299, y: 368, w: 63, h: 291,fx: 26, fy: 0, oa:0},//取物走廊
        '202212151042333B8315EAA46E696480': {x: 1033, y: 360, w: 110, h: 123,fx: 6, fy: 0, oa:0},//五项检查室
        '20221215104302DAE9FF4B63DDC63C83': {x: 925, y: 342, w: 105, h: 70,fx: 0, fy: 0, oa:0},//彩超室
        '2022121510433786B38F38AC0469EEBB': {x: 1167, y: 426, w: 158, h: 123,fx: 12, fy:0, oa:0},//X光室
        '20221215104507999DCD75EF762B4E34': {x: 1317, y: 600, w: 431, h: 229,fx: 33, fy:0, oa:0},//停车等候区
        '20221215104652754D7418543E018926': {x: 813, y: 250, w: 55, h: 91,fx: -5, fy:0, oa:0},//辨认室1
        '2022121510470445DEC5D7FC968B5925': {x: 766, y: 250, w: 44, h: 91,fx: -5, fy:0, oa:0},//辨认室2
        '20221215105531A7F49EB4729ED4F2C8': {x: 1208, y: 272, w: 36, h: 25, fx: 4, fy: 0,oa:0}//醒酒室
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
