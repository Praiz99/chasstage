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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/jhjnone/css/mapstyle.css">
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
<script src="${ctx}/static/chas/bigscreen/jhjnone/js/index.js"></script>
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
        '20230715133425DB807487A5D17B3C2A': {x: 231, y: 412, w: 91, h: 130, fx:-10, fy: 0,oa:0},//登记区
        '20230715135737B214C0D996DDE31D18': {x: 539, y: 464, w: 195, h: 84, fx:-5, fy: 0,oa:0},//信息采集室
        '202307151359511EABAE738460256C1B': {x: 480, y: 435, w: 43, h: 54, fx:-3, fy: 0,oa:0},//人身安全检查室
        '202307151401354987FEFA4104032885': {x: 469, y: 492, w: 34, h: 26, fx:-3, fy: 0,oa:0},//隐私检查室1
        '2023071514020221EFA12FB805C11E64': {x: 464, y: 518, w: 34, h: 26, fx: -3, fy: 0,oa:0},//隐私检查室2
        '20230715140339C21B48ABAD965EB90A': {x: 730, y: 480, w: 48, h: 54, fx: -3, fy: 0,oa:0},//声纹采集室2
        '202307151404199AFDDD04F73043A549': {x: 734, y: 430, w: 48, h: 54, fx: -3 ,fy: 0,oa:0},//声纹采集室1
        '20230715140604F641B3F0C08B5C4123': {x: 781, y: 450, w: 48, h: 116, fx: -3, fy: 0,oa:0},//辨认室
        '202307151409080FC1B365C6F601A2A3': {x: 783, y: 312, w: 294, h: 61, fx: 5, fy: 0,oa:0},//看守区
        '2023071514094704A42AB455A8F5552F': {x: 720, y: 353, w: 38, h: 20, fx: 0, fy: 0,oa:0},//等候室1
        '20230715141013381C54FF9B9DC7D701': {x: 723, y: 340, w: 38, h: 20, fx: 0, fy: 0, oa:0},//等候室2
        '202307151410304361149B688D366078': {x: 725, y: 317, w: 38, h: 20, fx: 0, fy: 0, oa:0},//等候室3
        '2023071514110512494C6BE19E99ABF2': {x: 733, y: 230, w: 70, h: 50, fx: -2, fy: 0, oa:0},//等候室4
        '202307151411309A3A0DC9E238BD4BBE': {x: 807, y: 238, w: 20, h: 36, fx: -1, fy: 0, oa:0},//等候室5
        '202307151411464A43BE66C9C0B8F639': {x: 828, y: 243, w: 23, h: 30,fx: -1, fy: 0, oa:0},//等候室6
        '202307151412109E7C8FC431AD6DB1D2': {x: 852, y: 238, w: 20, h: 36, fx: -1, fy: 0, oa:0},//等候室7
        '20230715141230C4BC3CF1A75EBAAF08': {x: 875, y: 238, w: 20, h: 36, fx: -1, fy: 0, oa:0},//等候室8
        '202307151412544D005AC34494E5363F': {x: 896, y: 238, w: 20, h: 36, fx: -1, fy: 0, oa:0},//等候室9
        '202307151413177F3934B0E80C25386E': {x: 918, y: 238, w: 20, h: 36, fx: -1, fy: 0, oa:0},//等候室10
        '20230715141351843B85D6C4215A6D1A': {x: 939, y: 238, w: 20, h: 36, fx: -1, fy: 0, oa:0},//等候室11
        '20230715141403F0B581C467C0648BA5': {x: 963, y: 243, w: 23, h: 30, fx: -1, fy: 0, oa:0},//等候室12
        '2023071514141691FA5B524DCCC021CA': {x: 985, y: 238, w: 20, h: 36, fx: -1, fy: 0, oa:0},//等候室13
        '202307151415228C4494A57715D8A0F4': {x: 1007, y: 230, w: 98, h: 36,fx: 0, fy: 0, oa:0},//等候室14
        '202307151417372B78A829FBF49742D1': {x: 1079, y: 270, w: 29, h: 39,fx: 0, fy: 0, oa:0},//等候室15
        '202307151418494BB22C2D7114EC8A80': {x: 1081, y: 310, w: 72, h: 54,fx: 0, fy: 0, oa:0},//等候室16
        '202307151420104249AE5ACBD63434C7': {x: 653, y:330, w: 68, h: 53,fx: -1, fy: 0, oa:0},//医疗室
        '20230715142105612F5BF4DAB3ED62D7': {x: 672, y: 250, w: 59, h: 78,fx: -1, fy: 0, oa:0},//检测室
        '2023071514232587E679A9EF45CDA374': {x: 830, y: 451, w: 70, h: 116,fx: 0, fy: 0, oa:0},//审讯室1
        '202307151423574C2585BA7818EBF1D4': {x: 900, y: 451, w: 70, h: 116,fx: 0, fy: 0, oa:0},//审讯室2
        '202307151424578B711F000DBFC0AF45': {x: 974, y: 451, w: 70, h: 116,fx: 0, fy: 0, oa:0},//审讯室3
        '20230715142510E48E05F0D59075E458': {x: 1045, y: 451, w: 70, h: 116,fx: 0, fy: 0, oa:0},//审讯室4
        '202307151427100E1A042B6DD649DB98': {x: 1117, y: 451, w: 70, h: 116,fx: 0, fy:0, oa:0},//审讯室5
        '202307151427596A6F0C17B0464D7B62': {x: 1187, y: 451, w: 70, h: 116,fx: 1, fy:0, oa:0},//审讯室6
        '2023071514285883F5E13A29F4BF27D7': {x: 1110, y: 232, w: 94, h: 70,fx: 1, fy:0, oa:0},//特殊审讯室7
        '2023071514295341A7896170CAEA8F12': {x: 1433, y: 542, w: 65, h: 106,fx: 6, fy:0, oa:0},//审讯室8
        '202307151430125DB9CEB8207B84D258': {x: 1450, y: 650, w: 65, h: 106, fx: 6, fy: 0,oa:0},//审讯室9
        '20230715143102265ECF4B952D35ED1E': {x: 1558, y: 555, w: 114, h: 78, fx: 6, fy: 0,oa:0},//远程审讯室10
        '20230715143117F81583C4251DF18777': {x: 1572, y: 641, w: 114, h: 78, fx: 6, fy: 0,oa:0},//远程审讯室11
        '2023071514313749D4894DA709E08109': {x: 1590, y: 728, w: 114, h: 78, fx:7, fy: 0,oa:0},//等候室12
        '202307151432212452DA4B92CE0FE67C': {x: 1607, y: 818, w: 114, h: 78, fx:8, fy: 0,oa:0},//审讯室13
        '202307151432523BD84BDA03B808E2DE': {x: 1528, y: 312, w: 88, h: 69, fx:6, fy: 0,oa:0},//审讯室14
        '2023071514331308049D8A1B09F39916': {x: 1616, y: 312, w: 94, h: 69, fx:6, fy: 0,oa:0},//审讯室15
        '2023071514333256344B2BDFF1762DBB': {x: 1545, y: 457, w: 64, h: 121, fx: 8, fy: 0,oa:0},//审讯室16
        '202307151457427F70084FD2D08A6E4D': {x: 1598, y: 437, w: 122, h: 120, fx: 9, fy: 0,oa:0},//审讯室17
        '20230715145809B3A9A76DA141E91BB4': {x: 1654, y: 555, w: 98, h: 77, fx: 6 ,fy: 0,oa:0},//审讯室18
        '202307151458241AF25202BD3D4FA8F9': {x: 1679, y: 635, w: 82, h: 82, fx: 9, fy: 0,oa:0},//审讯室19
        '2023071514583983FF2EAC444376193B': {x: 1705, y: 729, w: 82, h: 82, fx: 8, fy: 0,oa:0},//审讯室20
        '202307151459018AEF9288F26C8AED42': {x: 1719, y: 817, w: 82, h: 82, fx: 9, fy: 0,oa:0},//审讯室21
        '2023071514595741DA3979CFCE8FDF30': {x: 1462, y: 302, w: 65, h: 75, fx: 6, fy: 0, oa:0},//律师会见室
        '20230715150207FDFE155437234518D7': {x: 599, y: 324, w: 51, h: 37, fx: -1, fy: 0, oa:0},//出区通道
        '20230715150357240DCB6D4A8DEA3D81': {x: 411, y: 325, w: 67, h: 45, fx: -1, fy: 0, oa:0},//前室（送风）
        '20230715150452C8A4FBAF5A5521E837': {x: 357, y: 265, w: 64, h: 50, fx: -1, fy: 0, oa:0},//电梯厅（送风）
        //'20230715150702164FA5AE1210F88B4A': {x: 473, y: 515, w: 134, h: 138,fx: -4, fy: 0, oa:0},//楼梯口
        '20230720143949649AFA049BF1B06581': {x: 401, y: 388, w: 163, h: 50, fx: -1, fy: 0, oa:0},//登记区通道
        '202307201442358887A0591B2BB0D733': {x: 574, y: 388, w: 161, h: 50, fx: -1, fy: 0, oa:0},//取物通道
        //'202307201443536EFB1B34728B09E49A': {x: 653, y: 240, w: 68, h: 53, fx: -1, fy: 0, oa:0},//医务室
        '202307201446348F55C534DD274EABB9': {x: 566, y: 285, w: 37, h: 92, fx: -2, fy: 0, oa:0},//出区电梯口
        '20230720145142E54E944BED1294A89C': {x: 197, y: 310, w: 215, h: 74, fx: -3, fy: 0, oa:0},//待检区通道
        '202307201455343F088C28B495704554': {x: 213, y: 232, w: 150, h: 76, fx: -3, fy: 0, oa:0},//待检区楼梯口
        '20230720150516E7D2581489B6782847': {x: 733, y: 388, w: 238, h: 50, fx: -1, fy: 0, oa:0},//看守区通道
        '202307201506200FCD3A4139FAAF0186': {x: 651, y: 438, w: 125, h: 105,fx: -2, fy: 0, oa:0},//审讯区1通道
        '202307201508463E0F134D055E8E2F84': {x: 1154, y: 315, w: 40, h: 87,fx: 2, fy: 0, oa:0},//审讯室7通道
        //'202307201511590102E2824A42868F4D': {x: 1048, y: 515, w: 268, h: 137,fx: 4, fy: 0, oa:0},//教育疏导室
        '20230720151226F44A4BF1AA89933BBE': {x: 1309, y:380, w: 43, h: 156,fx: 2, fy: 0, oa:0},//教育疏导室通道
        '20230720151346651FDE44A106F0A95C': {x: 1365, y: 465, w: 149, h: 50,fx: 1, fy: 0, oa:0},//审讯室8通道
        '202307201514245CFBBA0C7A2509E4E4': {x: 1427, y: 380, w: 87, h: 88,fx: 3, fy: 0, oa:0},//保洁室通道
        '2023072015150546EA5E60F9887C0D96': {x: 1355, y: 380, w: 69, h: 46,fx: 2, fy: 0, oa:0},//保洁电梯口
        '20230720151602244DAC5A528EE01195': {x: 1517, y: 380, w: 229, h: 50,fx: 0, fy: 0, oa:0},//审讯室17通道
        '2023072015173133996F83B8C9E48827': {x: 1501, y: 525, w: 43, h: 434,fx: 13, fy: 0, oa:0},//审讯区2通道
        '2023072015174398F34AE41996D73185': {x: 1722, y: 430, w: 40, h: 498,fx: 20, fy:0, oa:0},//审讯区3通道
        '202307201518498B32844B863A60AC94': {x: 1625, y: 906, w: 213, h: 41,fx: 0, fy:0, oa:0},//审讯室13通道
        '202307201525193138AAB4A4849CCBE7': {x: 1471, y: 763, w: 78, h: 212,fx: 8, fy:0, oa:0}//审讯室9楼梯口
        //'20230720152644421DC939CC4C809457': {x: 610, y: 566, w: 108, h: 53,fx: -4, fy:0, oa:0},//审讯室7客梯
        //'20230720153121CFC4887639780DDA85': {x: 1563, y: 411, w: 125, h: 41, fx: 6, fy: 0,oa:0},//一楼门厅
        //'20230720154245D0B83332134FD75809': {x: 358, y: 222, w: 83, h: 42, fx: -8, fy: 0,oa:0}//一楼脱逃通道
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

        $('#map').on('mouseenter', '.mj', function () {
            var that = this;
            var title = $(this).attr("ryxm") +
                "当前所在位置:<br/>" + $(this).attr("qymc");
            layer.tips(title, that, {
                tips: [1, '#8da0cc'],
                time: 4000
            });
        });
        $('#map').on('mouseleave', '.mj', function () {
            var that = this;
            var title = $(this).attr("title");
            layer.closeAll();
        });

        $('#map').on('mouseenter', '.fk', function () {
            var that = this;
            var title = $(this).attr("ryxm") +
                "当前所在位置:<br/>" + $(this).attr("qymc");
            layer.tips(title, that, {
                tips: [1, '#8da0cc'],
                time: 4000
            });
        });
        $('#map').on('mouseleave', '.fk', function () {
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
