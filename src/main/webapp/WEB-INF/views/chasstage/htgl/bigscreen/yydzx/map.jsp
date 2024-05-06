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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/yydzx/css/mapstyle.css">
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
<script src="${ctx}/static/chas/bigscreen/yydzx/js/index.js"></script>
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
        '20221115154101439DC62723EDBB74B4': {x: 150, y: 450, w: 131, h: 100, fx:-20, fy: 0,oa:0},//办案区出入口
        '20221115154219E0CD7C2B5E95486CA0': {x: 252, y: 535, w: 86, h: 165, fx: -20, fy: 0,oa:0},//待检区
        '2022111515425966298BA01A2EEB49F3': {x: 364, y: 520, w: 375, h: 126, fx: -10, fy: 0,oa:0},//信息采集室
        '20221115154551E01040A361A97C14C7': {x: 480, y: 290, w: 250, h: 92, fx: -10, fy: 0,oa:0},//看守区
        '20221115154716E3353ED89E0FFF033C': {x: 508, y: 250, w: 41, h: 25, fx: -7, fy: 0,oa:0},//等候室1
        '2022111515473707CE8A3EAF73B5354F': {x: 555, y: 250, w: 30, h: 25, fx: -7, fy: 0,oa:0},//等候室2
        '2022111515475738579F5800F5B8A534': {x: 581, y: 250, w: 30, h: 25, fx: -7 ,fy: 0,oa:0},//等候室3
        '202211151548177E9E3E0CBF4A203AFF': {x: 615, y: 250, w: 30, h: 25, fx: -7, fy: 0,oa:0},//等候室4
        '202211151549078E7291B83A1E44B336': {x: 642, y: 250, w: 30, h: 25, fx: -7, fy: 0,oa:0},//等候室5
        '20221115154934F0DFF1CDC229144061': {x: 670, y: 250, w: 30, h: 25, fx: -7, fy: 0,oa:0},//等候室6
        '20221115154955C66E84D1E554D445B3': {x: 697, y: 250, w: 41, h: 25, fx: -7, fy: 0, oa:0},//等候室7
        '20221115155017698B1FC4F7C4FFECE6': {x: 784, y: 250, w: 77, h: 70, fx: -7, fy: 0, oa:0},//等候室8
        '20221115155033C30ECAB489A1179FE4': {x: 777, y: 320, w: 77, h: 70, fx: -7, fy: 0, oa:0},//等候室9
        '2022111515505349414A8DE9B0A56705': {x: 768, y: 388, w: 77, h: 65, fx: -7, fy: 0, oa:0},//等候室10
        '20221115155117BB7E474499675B7CEF': {x: 640, y: 385, w: 30, h: 28,fx: -4, fy: 0, oa:0},//等候室11
        '20221115155134D0895A91F1260FAB45': {x: 605, y: 385, w: 30, h: 28, fx: -3, fy: 0, oa:0},//等候室12
        '20221115161347B33486EE380FA6C068': {x: 575, y: 385, w: 30, h: 28, fx: -3, fy: 0, oa:0},//等候室13
        '20221115161414F8A244DC63E7A3C98A': {x: 540, y: 385, w: 30, h: 28, fx: -3, fy: 0, oa:0},//等候室14
        '20221115162812BEDCFCBA0A4BD04DE2': {x: 505, y: 385, w: 30, h: 28, fx: -3, fy: 0, oa:0},//等候室15
        '20221115162838AA97B10A7065949B3C': {x: 460, y: 385, w: 38, h: 28, fx: -3, fy: 0, oa:0},//等候室16
        '202211151628579460C99DAECB38A287': {x: 361, y: 330, w: 80, h: 60, fx: -10, fy: 0, oa:0},//等候室17
        '2022111516295811D1D064FEB8C76E22': {x: 383, y: 289, w: 71, h: 40, fx: -10, fy: 0, oa:0},//尿检区
        '20221115163045A3ADD4358E23F74B92': {x: 405, y: 245, w: 70, h: 44,fx: -10, fy: 0, oa:0},//尿检厕所
        '20221115163241B152E4BD1841AD6281': {x: 298, y: 455, w: 369, h: 50,fx: -10, fy: 0, oa:0},//取物走廊

        '2022111516350257CD068A4BA6A593EB': {x: 931, y: 280, w: 85, h: 84,fx: 0, fy: 0, oa:0},//讯/询问室1
        '202211151635538449E602E61DEC5B90': {x: 1023, y:280, w: 88, h: 86,fx: 1, fy: 0, oa:0},//讯/询问室2
        '20221115163643249F4C90B7B980B0D5': {x: 1119, y: 280, w: 89, h: 86,fx: 3, fy: 0, oa:0},//讯/询问室3
        '20221115163710430A6BCDB4C840ADA4': {x: 1214, y: 280, w: 89, h: 86,fx: 10, fy: 0, oa:0},//讯/询问室4
        '202211151637288D47B9C451FA10946B': {x: 1311, y: 280, w: 85, h: 84,fx: 11, fy: 0, oa:0},//讯/询问室5
        '2022111516375737AA5FE905044CB993': {x: 1408, y: 280, w: 85, h: 84,fx: 16, fy: 0, oa:0},//讯/询问室6
        '202211151638163F64B3C87B92BE455E': {x: 1504, y: 280, w: 85, h: 84,fx: 22, fy: 0, oa:0},//讯/询问室7
        '202211151638396C0288A4317A043C03': {x: 1542, y: 360, w: 94, h: 100,fx: 26, fy:0, oa:0},//讯/询问室8
        '2022111516390028BFA77A9F1B38B485': {x: 1430, y: 360, w: 104, h: 98,fx: 26, fy:0, oa:0},//讯/询问室9
        '20221115163919319E828150F4904E15': {x: 1275, y: 360, w: 104, h: 98,fx: 18, fy:0, oa:0},//讯/询问室10
        '2022111516394104AD954AB62E7AD77C': {x: 1190, y: 360, w: 104, h: 98,fx: 10, fy:0, oa:0},//讯/询问室11
        '20221115164034483B3CBEA44C50D7A0': {x: 1100, y: 360, w: 104, h: 98,fx: 10, fy:0, oa:0},//讯/询问室12
        '20221115164103634D5AAA6D8789DA63': {x: 1015, y: 360, w: 104, h: 98,fx: 7, fy:0, oa:0},//讯/询问室13
        '202211151641484661BBA0DFE9776702': {x: 917, y: 360, w: 115, h: 98,fx: 0, fy:0, oa:0},//讯/询问室14
        '202211151648584A49F40706BF491B35': {x: 1382, y: 375, w: 52, h: 100,fx: 10, fy:0, oa:0},//辨认室及监护人室
        '20221115165029852A1520FB68C3B7B0': {x: 941, y: 520, w: 106, h: 166,fx: 1, fy:0, oa:0},//特殊审讯室
        '202211160100352B1A1BCFAC398CB211': {x: 1737, y: 495, w: 63, h: 50,fx: 16, fy:0, oa:0},//律师会见室
        '202211161658405E8B6B5774243E5C97': {x: 325, y: 385, w: 77, h: 68,fx: -17, fy:0, oa:0},//医疗救护室
        '20221116170044DFF65AF96426411D59': {x: 1330, y: 520, w: 38, h: 92,fx: 10, fy:0, oa:0},//审讯区卫生间走廊
        '20221116170144C985FDBC4D1A8F6614': {x: 1350, y: 580, w: 90, h: 73,fx: 10, fy:0, oa:0},//审讯区男厕
        '202211161702071441486B62A52E010B': {x: 1385, y: 540, w: 42, h: 52,fx: 5, fy:0, oa:0},//审讯区女厕
        '20221116170343544108961C32E40A68': {x: 1428, y: 527, w: 89, h: 171,fx: 19, fy:0, oa:0},//询问室出入口
        '2022111617050564EA302C248E999DA9': {x: 801, y: 450, w: 897, h: 50,fx: 0, fy:0, oa:0},//审讯室11门口走廊
        '202211161707159002ACBC400BDA2207': {x: 916, y: 238, w: 667, h: 39,fx: 0, fy:0, oa:0},//审讯室5门口走廊
        '20221126231228053D82AB3AB134273A': {x: 1598, y: 270, w: 51, h: 230,fx: 25, fy:0, oa:0}//律师会见室门口走廊
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
