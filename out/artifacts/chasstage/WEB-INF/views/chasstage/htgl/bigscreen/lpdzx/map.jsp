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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/lpdzx/css/mapstyle.css">
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
<script src="${ctx}/static/chas/bigscreen/lpdzx/js/index.js"></script>
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
        '20230109083425DB807487A5D17B3C2A': {x: 1169, y: 585, w: 330, h: 83, fx:0, fy: 0,oa:0},//信息登记采集区
        '20230509100444F82116F3B9ADCE045D': {x: 1522, y: 608, w: 92, h: 136, fx: 18, fy: 0,oa:0},//办案区入口
        '20230509100500537C6394C50FBDF031': {x: 1490, y: 534, w: 82, h: 63, fx: 18, fy: 0,oa:0},//办案区出口
        '20230509100559E4DC485E404B5C0B7A': {x: 1356, y: 669, w: 37, h: 54, fx: 6, fy: 0,oa:0},//人身检查室1
        '20230509100608B8EE99D39384AF8E4C': {x: 1314, y: 670, w: 37, h: 54, fx: 6, fy: 0,oa:0},//人身检查室2
        '20230509100630AB4F7DA41FCCD71FD9': {x: 1252, y: 660, w: 60, h: 53, fx: 5, fy: 0,oa:0},//声纹采集室
        '2023050910065250BF7E5131BC2B7F46': {x: 1185, y: 660, w: 65, h: 54, fx: 4 ,fy: 0,oa:0},//毒品称重室
        '20230509100747165B7ED63CE2B14A83': {x: 1154, y: 532, w: 321, h: 32, fx: 0, fy: 0,oa:0},//出所通道
        '2023050910081144113C59C3D8F1255F': {x: 1195, y: 407, w: 221, h: 91, fx: 8, fy: 0,oa:0},//看守区
        '202305091008302349B485D949CA1445': {x: 1155, y: 490, w: 56, h: 35, fx: 4, fy: 0,oa:0},//等候室1
        '202305091008379D1FB28148FDDD58E7': {x: 1151, y: 452, w: 56, h: 35, fx: 4, fy: 0, oa:0},//等候室2
        '20230509100847CAF63D8E43E837E481': {x: 1135, y: 364, w: 65, h: 38, fx: 2, fy: 0, oa:0},//等候室3
        '202305091008576565A6EFC44E916267': {x: 1205, y: 364, w: 31, h: 37, fx: 2, fy: 0, oa:0},//等候室4
        '2023050910090717B846476A6FC3A7B0': {x: 1243, y: 364, w: 31, h: 37, fx: 2, fy: 0, oa:0},//等候室5
        '20230509100915B3A64F0DECE64D6E4E': {x: 1427, y: 410, w: 80, h: 49,fx: 16, fy: 0, oa:0},//等候室6
        '202305091009235051502B8AEEA5046C': {x: 1447, y: 462, w: 91, h: 68, fx: 16, fy: 0, oa:0},//等候室7
        '202305091009373B644D8FC72A3E0FAA': {x: 1319, y: 495, w: 81, h: 35, fx: 4, fy: 0, oa:0},//等候室8
        '202305091009461056C944664B91DEB3': {x: 1261, y: 495, w: 51, h: 35, fx: 4, fy: 0, oa:0},//等候室9
        '202305091010440D95D88C8140EB8A43': {x: 1433, y: 362, w: 55, h: 37, fx: 12, fy: 0, oa:0},//嫌疑人卫生间
        '20230509101059F113CDB4434A3AFEAE': {x: 1383, y: 366, w: 48, h: 46, fx: 12, fy: 0, oa:0},//尿检区
        '20230509101115B9023A8E9897D3144D': {x: 1279, y: 382, w: 52, h: 32, fx: 4, fy: 0, oa:0},//醒酒区
        '202305091011383FB52088664A8B9128': {x: 303, y: 426, w: 841, h: 30, fx: 0, fy: 0, oa:0},//审讯室通道1
        '202305091011571A9B7826FAB95AC404': {x: 275, y: 530, w: 475, h: 30,fx: 0, fy: 0, oa:0},//审讯室通道2
        '202305091012313B78E5A10253A49FB6': {x: 981, y: 368, w: 79, h: 57,fx: 0, fy: 0, oa:0},//讯/询问室1
        '20230509101243ED9ADC6703AF83C150': {x: 893, y: 368, w: 82, h: 58,fx: 0, fy: 0, oa:0},//讯/询问室2
        '20230509101254963CB55223A3F01948': {x: 810, y:374, w: 78, h: 51,fx: 0, fy: 0, oa:0},//讯/询问室3
        '202305091013038F33AFF0431BA61655': {x: 728, y: 368, w: 77, h: 56,fx: -1, fy: 0, oa:0},//讯/询问室4
        '20230509101314A0B84C4144A04FCB25': {x: 645, y: 368, w: 74, h: 56,fx: -4, fy: 0, oa:0},//讯/询问室5
        '20230509101323990D1A75C73F8B3DA4': {x: 555, y: 368, w: 74, h: 56,fx: -5, fy: 0, oa:0},//讯/询问室6
        '20230509101332FF0FC45D0224B47F18': {x: 470, y: 368, w: 74, h: 56,fx: -6, fy: 0, oa:0},//讯/询问室7
        '2023050910134288D1CA41BB99DCA19D': {x: 383, y: 368, w: 74, h: 56,fx: -8, fy: 0, oa:0},//讯/询问室8
        '2023050910135294A6319998F01B80A9': {x: 426, y: 460, w: 86, h: 69,fx: -8, fy:0, oa:0},//讯/询问室10
        '20230509101458A17CAC05DB43BB7759': {x: 520, y: 460, w: 89, h: 70,fx: -8, fy:0, oa:0},//特殊审讯室11
        '202305091015190F3B7B46153F954B42': {x: 617, y: 461, w: 81, h: 70,fx: -7, fy:0, oa:0},//讯/询问室12
        '202305091015304A155B1B48B47CDB05': {x: 610, y: 566, w: 108, h: 53,fx: -4, fy:0, oa:0},//讯/询问室13
        '20230509101542DB5B8C6FD28C705479': {x: 498, y: 566, w: 108, h: 53, fx: -6, fy: 0,oa:0},//讯/询问室14
        '20230509101553CE24149F95AC47E472': {x: 382, y: 566, w: 108, h: 53, fx: -8, fy: 0,oa:0},//讯/询问室15
        '2023050910160359F80B19527DC244E2': {x: 267, y: 566, w: 108, h: 53, fx: -9, fy: 0,oa:0},//讯/询问室16
        '20230509101628636158C47214670D88': {x: 235, y: 460, w: 91, h: 69, fx: -18, fy: 0,oa:0},//未成年询讯问室9
        '20230509101810AE8D93240B85C4FDC7': {x: 81, y: 424, w: 180, h: 33, fx: -6, fy: 0,oa:0},//速裁区通道
        '20230509101831431702F2ECB90561AE': {x: 202, y: 383, w: 76, h: 40, fx: -10, fy: 0,oa:0},//速裁等候区1
        '2023050910183981F6964A227A84C466': {x: 158, y: 381, w: 38, h: 42, fx: -10, fy: 0,oa:0},//速裁等候区2
        '2023050910190414150296BAC65288B1': {x: 200, y: 283, w: 141, h: 97, fx: -30, fy: 0,oa:0},//速裁法庭
        '20230509101947AB3AF7D113280B0B0C': {x: 173, y: 461, w: 53, h: 98, fx: -18, fy: 0,oa:0},//辨认室/律师会见室
        '20230509102120426A3193D3383A8CF2': {x: 916, y: 563, w: 233, h: 58, fx: 0, fy: 0,oa:0},//五项检查室
        '20230509102134841455802633C646C6': {x: 757, y: 533, w: 388, h: 30, fx: 0, fy: 0,oa:0},//五项检查室通道
        '20230509102152BBE599BB6131EFE58A': {x: 767, y: 460, w: 146, h: 70, fx: -2, fy: 0,oa:0},//X光室
        '20230509102212A2EF5509C4EF884CFC': {x: 187, y: 530, w: 90, h: 30, fx: 0, fy: 0,oa:0},//民警通道
        '20230509163617353665F44A108D46F7': {x: 332, y: 458, w: 86, h: 74, fx: -10, fy: 0,oa:0}//审讯指挥室
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
