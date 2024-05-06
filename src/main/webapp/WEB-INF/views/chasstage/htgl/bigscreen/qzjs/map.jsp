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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/qzjs/css/mapstyle.css">
    <title>视频巡查</title>
    <style type="text/css">
        .group_2 {
            height: 343px;
            background: url("${ctx}/static/chas/bigscreen/qzjs/images/pic2.png")
            100% no-repeat;
            background-size: 100% 100%;
            width: 520px;
            margin: 243px 0 0 905px;
        }

        .section_1 {
            width: 489px;
            height: 38px;
            margin: 15px 0 0 20px;
        }

        .text-wrapper_3 {
            height: 38px;
            background: url("${ctx}/static/chas/bigscreen/qzjs/images/pic1.png")
            100% no-repeat;
            background-size: 100% 100%;
            width: 134px;
        }

        .text_1 {
            width: 107px;
            height: 19px;
            overflow-wrap: break-word;
            color: rgba(255, 255, 255, 1);
            font-size: 18px;
            font-family: SourceHanSansCN-Bold;
            font-weight: 700;
            text-align: left;
            white-space: nowrap;
            line-height: 60px;
            margin: 12px 0 0 10px;
        }

        .section_2 {
            width: 466px;
            height: 164px;
            margin: 33px 0 0 27px;
        }

        .box_4 {
            background-color: rgba(113, 152, 186, 1);
            border-radius: 4px;
            width: 132px;
            height: 134px;
            margin-top: 1px;
        }

        .paragraph_1 {
            width: 148px;
            height: 164px;
            overflow-wrap: break-word;
            color: rgba(255, 255, 255, 1);
            font-size: 13px;
            font-family: SourceHanSansCN-Regular;
            font-weight: NaN;
            text-align: left;
            line-height: 30px;
            margin-left: 20px;
        }

        .paragraph_2 {
            width: 141px;
            height: 164px;
            overflow-wrap: break-word;
            color: rgba(255, 255, 255, 1);
            font-size: 13px;
            font-family: SourceHanSansCN-Regular;
            font-weight: NaN;
            text-align: left;
            line-height: 30px;
            margin-left: 32px;
        }

        .section_3 {
            width: 82px;
            height: 35px;
            margin: 30px 0 28px 223px;
        }

        .text-wrapper_2 {
            background-color: rgba(49, 240, 248, 1);
            border-radius: 4px;
            height: 35px;
            border: 1px solid rgba(0, 217, 220, 1);
            width: 82px;
        }

        .text_2 {
            width: 31px;
            height: 16px;
            overflow-wrap: break-word;
            color: rgba(18, 33, 45, 1);
            font-size: 16px;
            font-family: SourceHanSansCN-Bold;
            font-weight: 700;
            text-align: left;
            white-space: nowrap;
            line-height: 48px;
            margin: 10px 0 0 26px;
        }
        .flex-col {
            display: flex;
            flex-direction: column;
        }
        .flex-row {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
        }
        .main .layui-layer-content{
            background-color:white;
            box-shadow:0px 0px 0px;
        }
        .layui-layer-tips{
            left:-23%
        }
    </style>
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
<div id="postition"></div>

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
<script src="${ctx}/static/chas/bigscreen/qzjs/js/index.js"></script>
<!-- 以下为当前页脚本 -->
<script type="text/javascript">
    var ctx = "${ctx}";
    var baqid = "${baqid}";
    var rybh = "", playRybh = "", playQyid = "";
    var oldw = 1920,oldh=934;
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
    var qyMeasure= {
        "202304061313044EC4F7F0F5FBD2581E":{ x: 1349, y: 350, w: 193, h: 140,fx:20,fy:0,oa:0},//登记大厅
        "202304061314231EB125DEDD4B039025":{ x: 1116, y: 390, w: 235, h:92,fx:15,fy:0,oa:0},//信息采集室
        "20230406131523FDC14AC5E8431EF57F":{ x: 1186, y: 280, w: 231, h: 74,fx:10,fy:0,oa:0},//看守区
        "20230406131600554D82E50664CD806A":{ x: 1460, y: 305, w: 63, h:47,fx:10,fy:0,oa:0},//等候室1
        "20230406131639BAC6AD5864044440E6":{ x: 1441, y: 255, w: 59, h: 53,fx:10,fy:0,oa:0},//等候室2
        "20230406131702F73F1D0F71B441FA05":{ x: 1366, y: 255, w: 40, h:28,fx:5,fy:0,oa:0},//等候室3
        "20230406131721BACEE4A38CB387339E":{ x: 1319, y: 255, w: 40, h:25,fx:5,fy:0,oa:0},//等候室4
        "20230406131748758B9E7142841C160A":{ x: 1269, y: 255, w: 40, h:26,fx:5,fy:0,oa:0},//等候室5
        "20230406131810C995B058F56468B54C":{ x: 1223, y: 255, w: 40, h:26,fx:5,fy:0,oa:0},//等候室6
        "2023040613183778196164C4384BE409":{ x: 1175, y: 255, w: 40, h:26,fx:5,fy:0,oa:0},//等候室7
        "202304061319016CA2B9B31C688B4511":{ x: 1135, y: 310, w: 47, h: 43,fx:5,fy:0,oa:0},//等候室8
        "20230406132048D8032C01854360F294":{ x: 1129, y: 255, w: 40, h: 28,fx:5,fy:0,oa:0},//嫌疑人卫生间1
        "202304061322448DA8E680D45B9BFAEE":{ x: 936, y: 285, w: 91, h:70,fx:0,fy:0,oa:0},//尿检区
        "202304061323485F089758564FBA299C":{ x: 983, y: 255, w: 40, h:32,fx:0,fy:0,oa:0},//嫌疑人卫生间2
        "2023040613260491131E8D69E64D674E":{ x: 708, y: 360, w: 63, h: 636,fx:-17,fy:0,oa:0},//审讯室走廊1
        "20230406132650FC539BFF94F7684F8B":{ x: 764, y: 355, w: 574, h: 33,fx:0,fy:0,oa:0},//取物区
        "202304061330307347984C0DD63D6EBC":{ x: 866, y: 390, w: 96, h: 90,fx:-5,fy:0,oa:0},//讯/询问室1
        "2023040613305881A3E86E6370171F41":{ x: 765, y: 390, w: 94, h: 93,fx:-3,fy:0,oa:0},//讯/询问室2
        "202304061331247D37E58C574F63C593":{ x: 743, y: 535, w: 178, h:81,fx:-5,fy:0,oa:0},//讯/询问室3
        "20230406133148996590D5B744D2FB5C":{ x: 731, y: 610, w: 186, h: 90,fx:-5,fy:0,oa:0},//讯/询问室4
        "20230406133214F6A8E6846B850DB460":{ x: 402, y: 700, w: 213, h: 89,fx:-15,fy:0,oa:0},//讯/询问室5
        "2023040613330100B865EA24CA4D3935":{ x: 433, y: 620, w: 202, h: 83,fx:-15,fy:0,oa:0},//讯/询问室6
        "20230406133323D1A443AC8A85B59ED4":{ x: 457, y: 540, w: 192, h: 73,fx:-15,fy:0,oa:0},//讯/询问室7
        "20230406133348888878107C248BEE20":{ x: 484, y: 470, w: 181, h: 71,fx:-15,fy:0,oa:0},//讯/询问室8
        "20230406133412FEFB85852A4385B73A":{ x: 503, y: 420, w: 176, h: 58,fx:-10,fy:0,oa:0},//讯/询问室9
        "2023040613343724E80B3533DC444153":{ x: 524, y: 355, w: 167, h: 60,fx:-10,fy:0,oa:0},//讯/询问室10
        "202304061335228D986E90992AF504A3":{ x: 969, y: 390, w: 88, h: 92,fx:0,fy:0,oa:0},//未成年讯/询问室
        "20230406133600F53D384B4ADC87D848":{ x: 718, y: 700, w: 200, h:100,fx:-5,fy:0,oa:0},//特殊讯/询问室
        "202304061336410C13AF24C4B0B0CE7D":{ x: 1385, y: 500, w: 192, h: 91,fx:20,fy:0,oa:0},//办案区入口
        "202304061337235F58548431347577D7":{ x: 365, y: 800, w: 232, h: 87,fx:-20,fy:0,oa:0},//办案区出口
        "202304061727478A49416B44611EF138":{ x: 541, y: 300, w: 160, h: 58,fx:-8,fy:0,oa:0},//讯/询问室11
        "20230406172822F49E5EB7AEE0254995":{ x: 779, y: 300, w: 108, h: 56,fx:0,fy:0,oa:0}//讯/询问室12
    };
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
        $('#map').on('mouseenter', 'div.xyr', function () {
            var that = this;
            // var title = $(this).attr("ryxm") +
            //     "当前所在位置:<br/>" + $(this).attr("qymc");
            // layer.tips(title, that, {
            //     tips: [1, '#8da0cc'],
            //     time: 4000
            // });
            var zpurl="";
            if($(this).attr("zpurl")=="null"||$(this).attr("zpurl")=="undefined"||$(this).attr("zpurl")==""){
                zpurl=ctx+"/static/framework/plugins/com/images/assets/nopic.jpg";
            }else {
                zpurl=$(this).attr("zpurl");
            }
            var hjdxzqh="";
            if($(this).attr("hjdxzqh")!="null"&&$(this).attr("hjdxzqh")!="undefined"&&$(this).attr("hjdxzqh")!=""){
                hjdxzqh=$(this).attr("hjdxzqh");
            }
            var html='';
            html+="<div id='' style='margin-left: -500px;margin-top: -150px' class='page flex-col'><div class='box_1 flex-col'><div class='group_1 flex-col'>"
            html+="<div class='group_2 flex-col'  style=''>";
            html+="<div class='section_1 flex-row'><div class='text-wrapper_3 flex-col'><span class='text_1'>人员基本信息</span></div><div class='flex-col'><a href='javascript:;' style='color: #1890FF!important;' class='text_1'  onclick='openQySxtSp(&quot;"+$(this).attr("qyid")+"&quot;)'>播放视频</a></div></div>";
            html+="<div class='section_2 flex-row' id='ryxxDialog'><div class='box_4 flex-col'><img style='width: 100%;height: 100%;object-fit: cover' src="+zpurl+"></div>";
            html+="<span class='paragraph_1' >姓名："+$(this).attr("ryxm")+"<br />性别："+$(this).attr("xbname")+"<br />证件类型："+$(this).attr("zjlxname")+"<br />证件号码："+$(this).attr("rysfzh")+"<br />出生日期："+$(this).attr("csrq")+"<br />户籍所在地："+hjdxzqh+"<br/></span>";
            html+="<span class='paragraph_2' >手环ID："+$(this).attr("wdbh")+"<br />入区时间："+$(this).attr("rrssj")+"<br />入区原因："+$(this).attr("ryzaybhname")+"<br />到案方式："+$(this).attr("dafsname")+"<br />人员类型："+$(this).attr("ryrylxname")+"<br />特殊群体："+($(this).attr("sftsqt")=='true'?'是':'否')+"<br /></span>";
            html+="</div></div>";
            html+="</div></div></div>"
            layer.tips(html,$("#postition"), {
                time: 0
            });

        });
        $('#map').not("#ryxxDialog,.xyr").on('mouseenter', function () {
            console.log("111")
            var that = this;
            var title = $(this).attr("title");
            layer.closeAll();
        });
        //     $('#map').on('mouseleave', '.xyr', function () {
        //         var that = this;
        //         var title = $(this).attr("title");
        //         layer.closeAll();
        //     });

        // $('#map').not(".xyr").mouseenter(function() {
        //         var that = this;
        //         var title = $(this).attr("title");
        //         layer.closeAll();
        // });

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
