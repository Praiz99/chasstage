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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/xhfj/css/mapstyle.css">
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
<script src="${ctx}/static/chas/bigscreen/xhfj/js/index.js"></script>
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
        '202208161731152FECD8909294A114BF': {x: 1500, y: 630, w: 353, h: 314, fx:18, fy: 0,oa:0},//登记区
        '202208170946589A7F94571C3058EDEE': {x: 1040, y: 636, w: 506, h: 200, fx: 8, fy: 0,oa:0},//信息采集室
        '20220817095115B654F527B0194A7ACD': {x: 1493, y: 815, w: 57, h: 115, fx: 8, fy: 0,oa:0},//隐私检查区1
        '202208170952044E84A2859AD638EDC6': {x: 1418, y: 825, w: 60, h: 92, fx: 6, fy: 0,oa:0},//隐私检查区2
        '2022081710114608BB42C57518A8E6E5': {x: 1290, y: 816, w: 109, h: 111, fx: 6, fy: 0,oa:0},//声纹采集室
        '2022081710141994CE0904F6259E0E57': {x: 496, y: 641, w: 495, h: 196, fx: 0, fy: 0,oa:0},//看守区
        '2022081710155693898D7F60630544E5': {x: 1008, y: 638, w: 67, h: 38, fx: 2 ,fy: 0,oa:0},//等候室1
        '20220817101806B199408E9CDB68B5B4': {x: 1008, y: 683, w: 67, h: 38, fx: 2, fy: 0,oa:0},//等候室2
        '202208171018384D5E0A49A7351484BE': {x: 958, y: 817, w: 120, h: 112, fx: 2, fy: 0,oa:0},//等候室3
        '20220817101855EBD06507DC943AE09E': {x: 857, y: 829, w: 84, h: 110, fx: 0, fy: 0,oa:0},//等候室4
        '202208171019170A9C410DB90CF530A4': {x: 780, y: 829, w: 61, h: 118, fx: 2, fy: 0, oa:0},//等候室5
        '202208171019515CADC6B7BF106E2916': {x: 705, y: 834, w: 61, h: 108, fx: 0, fy: 0, oa:0},//等候室6
        '20220817102013E0CB0E86A8F2424EDF': {x: 602, y: 830, w: 85, h: 116, fx: 0, fy: 0, oa:0},//等候室7
        '20220817102102F4589782304F22BE9E': {x: 468, y: 813, w: 118, h: 116, fx: 0, fy: 0, oa:0},//等候室8
        '20220817102123E83A54A3DFA42D55DD': {x: 372, y: 721, w: 84, h: 44,fx: -3, fy: 0, oa:0},//等候室9
        '2022081710214844E9C9525F5FADA7A1': {x: 378, y: 673, w: 84, h: 44, fx: -3, fy: 0, oa:0},//等候室10
        '20220817102210BD54BF55416AF8748D': {x: 393, y: 632, w: 72, h: 43, fx: -3, fy: 0, oa:0},//等候室11
        '2022081710230914FBF75B846AD18947': {x: 364, y: 768, w: 86, h: 61, fx: -3, fy: 0, oa:0},//尿检区
        '202208171025476621D41187433BF9D1': {x: 359, y: 824, w: 80, h: 86,fx: -4, fy: 0, oa:0},//看守区厕所
        '2022081710274373D7B4FD783789E892': {x: 58, y: 480, w: 185, h: 91,fx: -7, fy: 0, oa:0},//讯/询问室1
        '20220817102801D58EF2748DE8EC23BB': {x: 85, y: 400, w: 170, h: 84,fx: -7, fy: 0, oa:0},//讯/询问室2
        '2022081710282610DDA8809E25148B69': {x: 340, y:80, w: 88, h: 88,fx: -3, fy: 0, oa:0},//讯/询问室3
        '2022081710284534EC4388B788B58A69': {x: 563, y: 80, w: 117, h: 154,fx: -3, fy: 0, oa:0},//讯/询问室4
        '20220817102901786A942D104403E2BE': {x: 680, y: 80, w: 113, h: 154,fx: -3, fy: 0, oa:0},//讯/询问室5
        '2022081710292568AB4399BF4C4BE9D2': {x: 370, y: 295, w: 170, h: 92,fx: -6, fy: 0, oa:0},//讯/询问室6
        '20220817102942255183BAF2A849005D': {x: 370, y: 380, w: 158, h: 95,fx: -6, fy: 0, oa:0},//讯/询问室7
        '202208171030094FC5AB9010C45E2EC3': {x: 345, y: 470, w: 175, h: 106,fx: -6, fy: 0, oa:0},//讯/询问室8
        '20220817103041E64B553405AD9299FB': {x: 644, y: 240, w: 165, h: 93,fx: -4, fy:0, oa:0},//讯/询问室9
        '20220817103203F9BC431C54323BF02D': {x: 461, y: 80, w: 91, h: 153,fx: -4, fy:0, oa:0},//特殊讯/询问室
        '202208171032355E19F9219543D7549F': {x: 803, y: 80, w: 107, h: 141,fx: -2, fy:0, oa:0},//未成年人询/讯问室
        '20220817103322F6A9D7043BBE8D9DB9': {x: 627, y: 461, w: 178, h: 110,fx: -3, fy:0, oa:0},//重点人员讯/询问室
        '20220817103711F6334306134B0A1D92': {x: 310, y: 287, w: 58, h: 244,fx: -8, fy:0, oa:0},//审讯室1门前走廊
        '2022081710380145991189D844E44177': {x: 562, y: 288, w: 72, h: 362,fx: -6, fy:0, oa:0},//审讯室7门前走廊
        '20220817104137E7097ABBA12C5FA488': {x: 167, y: 200, w: 743, h: 57,fx: 0, fy:0, oa:0},//审讯室4门前走廊
        '2022081710424947AA01275FB4AB83E2': {x: 1082, y: 578, w: 463, h: 68,fx: 0, fy:0, oa:0},//取物走廊
        '2022081710442040AF93FC0C5D688110': {x: 378, y: 240, w: 87, h: 54,fx: -4, fy:0, oa:0},//审讯区卫生间
        //'20220817104550EF568D0EA46944D043': {x: 918, y: 239, w: 88, h: 96,fx: 0, fy:0, oa:0},//机房旁出口
        '20220817104834F460F30D0A58B5CAA1': {x: 922, y: 190, w: 153, h: 57,fx: 0, fy:0, oa:0},//未成年人审讯室旁出入口
        '20220817104940D9051C314CE8225A86': {x: 1273, y: 455, w: 77, h: 162,fx: 4, fy:0, oa:0},//取物旁出入口
        '202208171050306D675219888851394D': {x: 1364, y: 455, w: 102, h: 143,fx: 4, fy:0, oa:0},//医疗救护室
        '2022081816071497AC05F28E95B8E47B': {x: 1103, y: 810, w: 160, h: 108,fx: 3, fy:0, oa:0},//毒品称重室
        //'202208181610320309FB8532B5B04C4D': {x: 918, y: 239, w: 88, h: 96,fx: 0, fy:0, oa:0},//押解车辆出入口
        '20220818161719F46CE8A882294DA99D': {x: 322, y: 578, w: 676, h: 52,fx: 0, fy:0, oa:0}//看守区后走廊
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
