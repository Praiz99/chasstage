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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/jddzx/css/mapstyle.css">
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
<script src="${ctx}/static/chas/bigscreen/jddzx/js/index.js"></script>
<!-- 以下为当前页脚本 -->
<script type="text/javascript">
    var ctx = "${ctx}";
    var baqid = "${baqid}";
    var rybh = "", playRybh = "", playQyid = "";
    var oldw = 1920,oldh=1080;
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
        '20230524110957A182224484112A6F58': {x: 1629, y: 347, w: 70, h: 28, fx:6, fy: 0,oa:0},//等候室1
        '202305241111009C03062E9846E85148': {x: 1617, y: 313, w: 70, h: 28, fx:6, fy: 0,oa:0},//等候室2
        '20230524111109278CEF282AB673174F': {x: 1606, y: 281, w: 70, h: 28, fx:6, fy: 0,oa:0},//等候室3
        '20230524111117EED7467DA24830C426': {x: 1516, y: 220, w: 124, h: 40, fx:6, fy: 0,oa:0},//等候室4
        '20230524111124B94A6600CC567037AD': {x: 1464, y: 222, w: 40, h: 43, fx: 6, fy: 0,oa:0},//等候室5
        '202305241111312982088E7F1546CD54': {x: 1393, y: 221, w: 62, h: 40, fx: 5, fy: 0,oa:0},//醒酒区
        '20230524111138F9A82D8AA047677DFC': {x: 1341, y: 222, w: 40, h: 40, fx: 6 ,fy: 0,oa:0},//等候室6
        '20230524111146A577BB9ECEF0CBF48F': {x: 1288, y: 222, w: 40, h: 40, fx: 6, fy: 0,oa:0},//等候室7
        '202305241111536CDA649505F9A948CB': {x: 1153, y: 222, w: 127, h: 40, fx: 5, fy: 0,oa:0},//等候室8
        '20230524111201B4EA4D38A33B65022E': {x: 1157, y: 281, w: 67, h: 28, fx: 5, fy: 0,oa:0},//等候室9
        '20230524111214787745FC84877E60BC': {x: 1161, y: 314, w: 67, h: 28, fx: 4, fy: 0, oa:0},//等候室10
        '202305241112313BB440CA5924E15D79': {x: 1170, y: 348, w: 63, h: 27, fx: 4, fy: 0, oa:0},//等候室11
        '2023052411192915809C399D0442BBEA': {x: 761, y: 514, w: 132, h: 137, fx: 0, fy: 0, oa:0},//讯/询问室1
        '20230524112124E1A27CC8E31268F680': {x: 616, y: 515, w: 134, h: 138, fx: -1, fy: 0, oa:0},//讯/询问室2
        '2023052411214549C1415BF1551D63B2': {x: 473, y: 515, w: 134, h: 138,fx: -4, fy: 0, oa:0},//讯/询问室3
        '2023052411215757FBB14F4B14312EAC': {x: 329, y: 515, w: 134, h: 138, fx: -9, fy: 0, oa:0},//讯/询问室4
        '2023052411220709AF9DC9DCAF71FE8E': {x: 178, y: 515, w: 141, h: 138, fx: -16, fy: 0, oa:0},//讯/询问室5
        '202305241122184178EFBE848EEFA18C': {x: 112, y: 378, w: 180, h: 78, fx: -11, fy: 0, oa:0},//讯/询问室6
        '20230524112227629697F48E73EC93F7': {x: 149, y: 297, w: 180, h: 78, fx: -11, fy: 0, oa:0},//讯/询问室7
        '20230524112240940B4D5F35FDD0C135': {x: 185, y: 222, w: 180, h: 78, fx: -12, fy: 0, oa:0},//讯/询问室8
        '202305241124058F6139D8D2F2978F52': {x: 788, y: 348, w: 125, h: 105, fx: -2, fy: 0, oa:0},//讯/询问室9
        '20230524112433D1B0930140116064EC': {x: 905, y: 514, w: 132, h: 137, fx: 0, fy: 0, oa:0},//特殊讯/询问室
        '20230524112448E3BF19D954D3985482': {x: 651, y: 348, w: 125, h: 105,fx: -2, fy: 0, oa:0},//未成年讯/询问室
        '202305241244160D4A3A188740DFDDEB': {x: 776, y: 673, w: 944, h: 157,fx: 0, fy: 0, oa:0},//押解区
        '202305241245155B969CF28137B246D4': {x: 1048, y: 515, w: 268, h: 137,fx: 4, fy: 0, oa:0},//登记区
        '20230524124635C2E9E347D3022B6F0A': {x: 1327, y:515, w: 314, h: 137,fx: 6, fy: 0, oa:0},//信息采集区
        //'202305241248338DFA968FE00A4DB60B': {x: 728, y: 368, w: 77, h: 56,fx: -1, fy: 0, oa:0},//取物区
        //'20230524124918FEA546BAD8D0A12099': {x: 368, y: 457, w: 1214, h: 52,fx: 0, fy: 0, oa:0},//走廊1
        //'2023052412493229A734C7AF869FECEC': {x: 555, y: 368, w: 74, h: 56,fx: -5, fy: 0, oa:0}/*,//询问室4门口走廊
        '20230524124939B5A43910BFAFC5743C': {x: 341, y: 268, w: 85, h: 242,fx: -16, fy: 0, oa:0},//走廊2
        '202305241251075AB31A0E9086814E9D': {x: 368, y: 457, w: 1214, h: 52,fx: 0, fy: 0, oa:0},//走廊1
        '2023052412520506FED38BBD607146A8': {x: 1231, y: 267, w: 364, h: 111,fx: 0, fy:0, oa:0},//看守区
        //'202306131440530FF1F15D8EB90134A1': {x: 520, y: 460, w: 89, h: 70,fx: -8, fy:0, oa:0},//登记区
        '2023061315323758D4A1BE8D59B87423': {x: 924, y: 347, w: 106, h: 105,fx: -2, fy:0, oa:0},//律师会见室
        //'20230613153410E1CAB314FD766CD002': {x: 610, y: 566, w: 108, h: 53,fx: -4, fy:0, oa:0},//醒酒室
        '20230613154912A63D5FCEAA4BE565A0': {x: 1563, y: 411, w: 125, h: 41, fx: 6, fy: 0,oa:0},//尿检区卫生间
        '20230613155405B6767B2D4B3480B48C': {x: 358, y: 222, w: 83, h: 42, fx: -8, fy: 0,oa:0},//卫生间
        '20230618152452A2B8394430ACDEAB7F': {x: 1599, y: 380, w: 76, h: 28, fx: 6, fy: 0,oa:0}//尿检区
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
