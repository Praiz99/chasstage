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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/tldzx/css/mapstyle.css">
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
<script src="${ctx}/static/chas/bigscreen/tldzx/js/index.js"></script>
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
        '202301121557281298F64CA4CD91BC6C': {x: 330, y: 502, w: 215, h: 186, fx:-20, fy: 0,oa:0},//押解区
        '202301121558034341D881E66AF47BF9': {x: 178, y: 480, w: 118, h: 162, fx: -30, fy: 0,oa:0},//待检区
        '2023011215591437DD26AE0EFFDC44CB': {x: 223, y: 370, w: 239, h: 82, fx: -9, fy: 0,oa:0},//信息登记采集区
        //'202301121600370AE16430F727B15609': {x: 1163, y: 272, w: 36, h: 26, fx: 3, fy: 0,oa:0},//取物区
        '20230112165829E4A76CD0C4B1A2E13B': {x: 637, y: 425, w: 358, h: 43, fx: 0, fy: 0,oa:0},//看守区
        '20230112165903F70672E49978A5F57A': {x: 614, y: 375, w: 83, h: 43, fx: -2, fy: 0,oa:0},//等候室1
        '20230112165930E1DA864D40C07C5DB7': {x: 702, y: 380, w: 38, h: 43, fx: -1 ,fy: 0,oa:0},//等候室2
        '20230112165938CDC0C8DEBA859B6569': {x: 745, y: 380, w: 38, h: 43, fx: 0, fy: 0,oa:0},//等候室3
        '20230112165946A7B1546EA539251CB2': {x: 789, y: 380, w: 38, h: 43, fx: 0, fy: 0,oa:0},//等候室4
        '202301121659556CDFD1B9B14F23552F': {x: 832, y: 380, w: 38, h: 43, fx: 0, fy: 0,oa:0},//等候室5
        '2023011217000532A6F3FF2999614816': {x: 875, y: 380, w: 38, h: 43, fx: 0, fy: 0, oa:0},//等候室6
        '20230112170016A716447E96C1B04912': {x: 917, y: 380, w: 37, h: 43, fx: 0, fy: 0, oa:0},//等候室7
        '2023011217002533B21F3BCC00711B48': {x: 962, y: 380, w: 37, h: 43, fx: 0, fy: 0, oa:0},//等候室8
        '20230112170034A70F68F1AB4AECB55D': {x: 586, y: 470, w: 79, h: 54, fx: -2, fy: 0, oa:0},//等候室9
        '20230112170044B748F7651EE9B505A6': {x: 673, y: 476, w: 69, h: 44,fx: -3, fy: 0, oa:0},//等候室10
        '20230112170058B792317EC3A0A640C2': {x: 867, y: 476, w: 96, h: 44, fx: 0, fy: 0, oa:0},//等候室11
        '20230112170105E650029D1B4EBA1B04': {x: 971, y: 476, w: 80, h: 44, fx: 0, fy: 0, oa:0},//等候室12
        '20230112170257356F572D416C2B6BEB': {x: 749, y: 476, w: 94, h: 44, fx: -2, fy: 0, oa:0},//醒酒室
        '20230112170645C497F886D465997699': {x: 1058, y: 378, w: 55, h: 44, fx: 0, fy: 0, oa:0},//尿检区
        '202301121707483BDFDF4DD689EF94AA': {x: 584, y: 530, w: 471, h: 37, fx: 0, fy: 0, oa:0},//审讯室1-4走廊
        '20230112170845698AA5FB4D814DDABD': {x: 1128, y: 454, w: 544, h: 35, fx: 0, fy: 0, oa:0},//审讯室7门口走廊
        '202301121709299A7C99BF5B71D64A5D': {x: 1619, y: 342, w: 51, h: 127, fx: 17, fy: 0, oa:0},//机房门口走廊
        '202301121711017F01BC6839674E8493': {x: 562, y: 560, w: 126, h: 114,fx: -14, fy: 0, oa:0},//讯/询问室1
        '20230112171238DD0EFB26B138763E54': {x: 703, y: 560, w: 127, h: 114,fx: -10, fy: 0, oa:0},//讯/询问室2兼远程讯问室1
        '2023011217221914DCE18B456CB4617A': {x: 858, y: 560, w: 126, h: 114,fx: -6, fy: 0, oa:0},//讯/询问室3兼远程讯问室2
        '20230112172232E6FF67548BA5ED6A02': {x: 998, y:560, w: 122, h: 114,fx: -2, fy: 0, oa:0},//讯/询问室4
        '20230112172242452C8481E2CD024968': {x: 1123, y: 375, w: 93, h: 82,fx: 10, fy: 0, oa:0},//讯/询问室5
        '2023011217225177E77CA3F23B3E4C1F': {x: 1227, y: 375, w: 126, h: 54,fx: 6, fy: 0, oa:0},//讯/询问室6
        '20230112172302B0605A98F75AAB80F6': {x: 1365, y: 375, w: 95, h: 81,fx: 13, fy: 0, oa:0},//讯/询问室7
        '202301121723169D42795CB3AB1A4742': {x: 1346, y: 510, w: 104, h: 118,fx: 13, fy: 0, oa:0},//讯/询问室8
        '2023011217232695A0E77BD44831FF4D': {x: 1723, y: 410, w: 131, h: 63,fx: 16, fy: 0, oa:0},//讯/询问室9
        '20230112172600047247BE3D9CC9A1C7': {x: 1465, y: 510, w: 103, h: 120,fx: 13, fy:0, oa:0},//未成年讯/询问室1
        '20230112172618DC4C71CA192CB643E8': {x: 1592, y: 495, w: 114, h: 120,fx: 30, fy:0, oa:0},//未成年讯/询问室2
        '20230112172651FE4453C3248C4C0A2E': {x: 1534, y: 370, w: 97, h: 83,fx: 16, fy:0, oa:0},//特殊讯/询问室1
        '202301121727304DF6A79A09FE2D8064': {x: 481, y: 380, w: 127, h: 43,fx: -4, fy:0, oa:0},//毒品称量室
        '20230112172809FA16A8BC22511C5D49': {x: 1471, y: 390, w: 54, h: 83, fx: 14, fy: 0,oa:0},//审讯区卫生间
        //'20230112172911DEE04CBDBF94D3BD9E': {x: 1238, y: 430, w: 134, h: 25, fx: 0, fy: 0,oa:0},//询问室5门口走廊
        '20230112173628EC567646731409D77D': {x: 1241, y: 510, w: 109, h: 117, fx: 13, fy: 0,oa:0},//审讯室8旁出口
        '2023011217390193396341C9BA5B6926': {x: 1722, y: 514, w: 63, h: 105, fx: 18, fy: 0,oa:0},//监护人观察出口
        '202301121739279A49F833EF72DED44D': {x: 1449, y: 340, w: 139, h: 34, fx: 0, fy: 0,oa:0},//办案区机房出口
        '202301121740498ECDD45DC0C529A8F3': {x: 1010, y: 378, w: 42, h: 43, fx: 0, fy: 0,oa:0},//尿检卫生间
        //'202301121741280C9C3CFA443D827685': {x: 1208, y: 272, w: 36, h: 25, fx: 4, fy: 0,oa:0},//洗手处
        '2023020311043087A9F287BDC5D438FE': {x: 149, y: 340, w: 62, h: 62, fx: -18, fy: 0,oa:0},//手机信息采集室
        '2023020311051267DC39509AB05A8B46': {x: 113, y: 395, w: 60, h: 58, fx: -22, fy: 0,oa:0},//隐私检查室
        '20230207104916DA71E613B8E9E4B19A': {x: 1238, y: 430, w: 134, h: 25, fx: 0, fy: 0,oa:0}//审讯室5门口走廊
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
