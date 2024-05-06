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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/hzxs/css/mapstyle.css">
    <title>视频巡查</title>
</head>
<body class="new-dialog page" style="background-color:transparent!important">
<div class="title">视频巡查</div>
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
<script src="${ctx}/static/chas/bigscreen/hzxs/js/index.js"></script>
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
        'a948a8827af369d9017af942cd8c0007': {x: 1043, y: 678, w: 229, h: 88, fx:0, fy: 0,oa:0},//信息采集区
        'a948a8827af369d9017af9435fba0009': {x: 633, y: 776, w: 643, h: 90, fx: 0, fy: 0,oa:0},//登记区
        //'a948a8827af369d9017af9556e77000b': {x: 610, y: 153, w: 74, h: 49, fx: 0, fy: 0,oa:0},//待检区
        'a948a8827af369d9017af955c939000d': {x: 1030, y: 477, w: 130, h: 112, fx: 0, fy: 0,oa:0},//看守区
        //'a948a8827af369d9017af9562999000f': {x: 562, y: 219, w: 87, h: 26, fx: 0, fy: 0,oa:0},//办案区出入口
        'a948a8827af369d9017af956cec30011': {x: 935, y: 429, w: 77, h: 358, fx: 0, fy: 0,oa:0},//审讯室走廊1
        'a948a8827af369d9017af95717360013': {x: 938, y: 341, w: 694, h: 37, fx: 0 ,fy: 0,oa:0},//审讯室走廊2
        'a948a8827af369d9017af95761bc0015': {x: 775, y: 387, w: 147, h: 54, fx: 0, fy: 0,oa:0},//尿检区
        'a948a8827af369d9017af95797240017': {x: 685, y: 387, w: 86, h: 54, fx: -5, fy: 0,oa:0},//尿检区卫生间
        'a948a8827af369d9017af958539e0019': {x: 1038, y: 595, w: 84, h: 72, fx: 0, fy: 0,oa:0},//等候室1
        'a948a8827af369d9017af958ced9001c': {x: 1204, y: 583, w: 68, h: 86, fx: 3, fy: 0, oa:0},//等候室2
        'a948a8827af369d9017af958f7dd001e': {x: 1201, y: 547, w: 62, h: 32, fx: 0, fy: 0, oa:0},//等候室3
        'a948a8827af369d9017af9591ab60020': {x: 1195, y: 514, w: 60, h: 30, fx: 0, fy: 0, oa:0},//等候室4
        'a948a8827af369d9017af959432e0022': {x: 1188, y: 481, w: 61, h: 29, fx: 0, fy: 0, oa:0},//等候室5
        'a948a8827af369d9017af95963d50024': {x: 1170, y: 387, w: 60, h: 91,fx: 3, fy: 0, oa:0},//等候室6
        'a948a8827af369d9017af95985d40026': {x: 1026, y: 386, w: 73, h: 50, fx: 0, fy: 0, oa:0},//等候室7
        'a948a8827af369d9017af95a44910028': {x: 761, y: 679, w: 154, h: 87, fx: 0, fy: 0, oa:0},//讯/询问室1
        'a948a8827af369d9017af95a790e002a': {x: 772, y: 592, w: 145, h: 75, fx: 0, fy: 0, oa:0},//讯/询问室2
        'a948a8827af369d9017af95a9aee002c': {x: 781, y: 515, w: 138, h: 68,fx: 0, fy: 0, oa:0},//讯/询问室3
        'a948a8827af369d9017af95abb09002e': {x: 678, y: 447, w: 244, h: 60,fx: 0, fy: 0, oa:0},//讯/询问室4
        'a948a8827af369d9017af95af61e0030': {x: 1171, y: 241, w: 144, h: 94,fx: 3, fy: 0, oa:0},//讯/询问室5
        'a948a8827af369d9017af95b188a0032': {x: 1332, y: 241, w: 133, h: 94,fx: 5, fy: 0, oa:0},//讯/询问室6
        'a948a8827af369d9017af95bc3de0034': {x: 1020, y: 241, w: 147, h: 94,fx: 0, fy: 0, oa:0},//未成年讯/询问室
        'a948a8827af369d9017af95c16340036': {x: 1485, y: 241, w: 130, h: 94,fx: 20, fy: 0, oa:0},//特殊讯/询问室
        'a948a8827af9bb1e017b09245fd5010e': {x: 1128, y: 592, w: 51, h: 77,fx: 3, fy: 0, oa:0},//等候室走廊1
        'a948a8827af9bb1e017b09248d5d0110': {x: 1110, y: 425, w: 46, h: 49,fx: 0, fy: 0, oa:0},//等候室走廊2
        'a948a8827af9bb1e017b0925e2bd0116': {x: 795, y: 339, w: 133, h: 41,fx: 0, fy: 0, oa:0},//民警通道
        'a948a8827af9bb1e017b0926a63c0119': {x: 918, y: 239, w: 88, h: 96,fx: 0, fy:0, oa:0}//嫌疑人卫生间
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
