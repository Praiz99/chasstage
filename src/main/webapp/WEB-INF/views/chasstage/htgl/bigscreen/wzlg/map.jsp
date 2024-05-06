<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<%@ taglib prefix="system" uri="/tags/jdone/sys/system"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String wsPath = "ws://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="path" value="<%=path%>" />
<c:set var="basePath" value="<%=basePath%>" />
<c:set var="wsPath" value="<%=wsPath%>" />
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/wzlg/css/reset.css">
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/wzlg/css/mapstyle.css">
    <title>视频巡查</title>
</head>
<body class="new-dialog page" style="background-color:transparent!important" >
<div class="title">视频巡查</div>
<div class="showSettings">
    <div class="showItem">
        <span class="showBlueColor">111111</span>
        <span class="showBlueText selectItem blueItem">微风险</span>
    </div>
    <div class="showItem">
        <span class="showYellowColor">111111</span>
        <span class="showYellowText selectItem yellowItem">低风险</span>
    </div>
    <div class="showItem">
        <span class="showOrangeColor">111111</span>
        <span class="showOrangeText selectItem orangeItem">中风险</span>
    </div>
    <div class="showItem">
        <span class="showRedColor">111111</span>
        <span class="showRedText selectItem redItem">高风险</span>
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
        <div id="map" class="area-map">

        </div>
    </div>
</div>
<iframe id="main" style="display: none;"></iframe>
<script src="${ctx}/static/chas/bigscreen/wzlg/js/jquery.min.js"></script>
<script src="${ctx}/static/chas/bigscreen/wzlg/js/layer.js"></script>
<script src="${ctx}/static/chas/bigscreen/wzlg/js/jquery.resize.js"></script>
<script src="${ctx}/static/chas/bigscreen/wzlg/js/sockjs.js"></script>
<script src="${ctx}/static/chas/bigscreen/wzlg/js/index.js"></script>
<!-- 以下为当前页脚本 -->
<script type="text/javascript">
    var ctx = "${ctx}";
    var baqid="${baqid}";
    var rybh="",playRybh="",playQyid="";
    var oldw = 1920,oldh=935;
    $(function(){
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
        var h=$("html").outerHeight();
        console.log(h);
        $(".area-map").css("min-height",(h-40)+"px");
        $(".area-map").css("max-height",(h-40)+"px");
    }
</script>
<script type="text/javascript">
    var headIcon={width:20,height:30,mixWidth:20,mixHeight:20};
    var qyMeasure={
        'a956fec37bf32012017bf758de790003':{x:450,y:543,w:169,h:39,fx:0,fy:0},//待检区
        'a956fec37bf32012017bf75b61140006':{x:636,y:614,w:295,h:90,fx:0,fy:0},//信息采集室
        'a956fec37bf32012017bf75e9b9a0008':{x:422,y:756,w:90,h:29,fx:0,fy:0},//隐私检查室 1
        'a956fec37bf32012017bf75ecfd9000a':{x:413,y:694,w:85,h:28,fx:0,fy:0},//隐私检查室 2
        'a956fec37bf32012017bf75fa6e4000e':{x:413,y:641,w:80,h:25,fx:0,fy:0},//隐私检查室 3
        'a956fec37bf32012017bf7709a430011':{x:660,y:529,w:47,h:62,fx:0,fy:0},//尿检区
        'a956fec37bf32012017bf7710c240013':{x:729,y:518,w:78,h:33,fx:0,fy:0},//尿检区厕所
        'a956fec37bf32012017bf778bebb0015':{x:899,y:511,w:200,h:35,fx:0,fy:0},//取物门口走廊
        'a956fec37bf32012017bf77badf70017':{x:1050,y:244,w:55,h:120,fx:5,fy:0},//到达看守区走廊
        'a956fec37bf32012017bf78015340019':{x:1136,y:256,w:66,h:79,fx:0,fy:0},//看守区
        'a956fec37bf32012017bf78bb1af001b':{x:1103,y:189,w:48,h:36,fx:0,fy:0},//等候室1
        'a956fec37bf32012017bf794f732001d':{x:1198,y:192,w:82,h:32,fx:0,fy:0},//等候室2
        'a956fec37bf32012017bf796909f0023':{x:1266,y:242,w:47,h:37,fx:0,fy:0},//等候室3
        'a956fec37bf32012017bf796c2f70025':{x:1273,y:289,w:67,h:36,fx:0,fy:0},//等候室4
        'a956fec37bf32012017bf797c3a00027':{x:1287,y:359,w:90,h:43,fx:0,fy:0},//等候室5
        'a956fec37bf32012017bf797ed810029':{x:1155,y:348,w:51,h:54,fx:0,fy:0},//等候室6
        'a956fec37bf32012017bf7a8c438003e':{x:1021,y:190,w:64,h:19,fx:0,fy:0},//看守区男厕所
        'a956fec37bf32012017bf7a90c580042':{x:904,y:191,w:58,h:19,fx:0,fy:0},//看守区女厕所
        'a956fec37bf32012017bf7a9ec6a0048':{x:972,y:189,w:39,h:27,fx:0,fy:0},//看守区厕所门口
        'a956fec37bf32012017bf7aa8c1a004e':{x:901,y:213,w:149,h:20,fx:0,fy:0},//厕所门口走廊
        'a956fec37bf32012017bf7afdf890068':{x:839,y:141,w:52,h:50,fx:0,fy:0},//大厅通向办案区门口
        'a956fec37bf32012017bf7b10e430070':{x:845,y:255,w:45,h:240,fx:0,fy:0},//审讯室门口走廊
        'a956fec37bf32012017bf7b291530078':{x:910,y:387,w:153,h:45,fx:0,fy:0},//审讯室1
        'a956fec37bf32012017bf7b2d01f007c':{x:911,y:335,w:137,h:39,fx:0,fy:0},//审讯室2
        'a956fec37bf32012017bf7b31537007e':{x:909,y:289,w:126,h:31,fx:0,fy:0},//审讯室3
        'a956fec37bf32012017bf7b34b750080':{x:913,y:243,w:122,h:30,fx:0,fy:0},//审讯室4
        'a956fec37bf32012017bf7b38d5c0084':{x:696,y:278,w:117,h:27,fx:0,fy:0},//审讯室5
        'a956fec37bf32012017bf7b3b7540086':{x:705,y:236,w:113,h:28,fx:0,fy:0},//审讯室6
        'a956fec37bf32012017bf7b3f7cc008a':{x:716,y:201,w:108,h:24,fx:0,fy:0},//审讯室7
        'a956fec37bf32012017bf7b638c90092':{x:667,y:386,w:149,h:27,fx:0,fy:0},//辨认室
        'a956fec37bf32012017bf7b8598a00a0':{x:672,y:315,w:144,h:26,fx:0,fy:0},//律师会见室走廊
        'a956fec37bf32012017bf7b9115b00a6':{x:495,y:387,w:145,h:29,fx:0,fy:0},//律师会见室1
        'a956fec37bf32012017bf7b9635a00aa':{x:526,y:316,w:87,h:27,fx:0,fy:0},//律师会见室2
        'a956fec37bf32012017bf7ba687a00b0':{x:505,y:353,w:146,h:20,fx:0,fy:0},//律师会见室门口
        'a956fec37bf32012017bf7c1ff2100c4':{x:1203,y:464,w:87,h:103,fx:0,fy:0},//血检区
        'a956fec37bf32012017bf7c311cd00c8':{x:1358,y:551,w:100,h:40,fx:0,fy:0},//B超心电图检查区
        'a956fec37bf32012017bf7c44a0a00ce':{x:1322,y:453,w:102,h:81,fx:0,fy:0},//CT检查区
        'a956fec37bf32012017bf7c7b43400e4':{x:1184,y:411,w:210,h:32,fx:0,fy:0},//通向特殊审讯室走廊
        'a956fec37bf32012017bf7c88e6800ea':{x:1433,y:406,w:140,h:33,fx:0,fy:0},//特殊审讯室门口走廊
        'a956fec37bf32012017bf7ca68d100f5':{x:1398,y:330,w:56,h:68,fx:0,fy:0},//特殊审讯室
        'a956fec37bf32012017bf7cbee4b0100':{x:1368,y:299,w:62,h:26,fx:0,fy:0},//特殊审讯室厕所
        'a956fec37bf32012017bf7cea5f3010c':{x:1495,y:329,w:53,h:70,fx:0,fy:0},//未成年人审讯室
        'a956fec37bf32012017bf7d030340111':{x:1606,y:402,w:50,h:36,fx:0,fy:0},//未成年人审讯室旁门口
    }
    $(function () {
        getPosition();
        $("#track").on("click","div.button",function () {
            rybh="";
            playQyid="";
            playRybh="";
            $("#track").hide();
            var vlcurl = "vlc://quit";
            $("#main").attr("src",vlcurl);
            //layer.msg("停止视频追踪，请手动关闭播放器",{offset:['500px', '340px']});
        });
        $("#map").on("click","div.icon",function () {
            var qyid=$(this).attr("qyid");
            //console.log(qyid);
            rybh=$(this).attr("rybh");
            openQySxtSp(qyid);
        });
        $('#map').on('mouseenter','.icon', function(){
            var that = this;
            var title =$(this).attr("ryxm")+
                "当前所在位置:<br/>"+$(this).attr("qymc");
            layer.tips(title, that,{
                tips: [1, '#8da0cc'],
                time: 4000
            });
        });
        $('#map').on('mouseleave','.icon', function(){
            var that = this;
            var title = $(this).attr("title");
            layer.closeAll();
        });
        interval = setInterval(getPosition,5*1000);
        $(".selectItem").click(function (e) {
            if($(this).hasClass("blueItem")){//蓝色
                if($(this).hasClass("showBlueText")){//不显示
                    $(this).removeClass("showBlueText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showBlueColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".blue").hide();
                }else{
                    $(this).addClass("showBlueText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showBlueColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".blue").show();
                }
            }else if($(this).hasClass("yellowItem")){//黄色
                if($(this).hasClass("showYellowText")){//不显示
                    $(this).removeClass("showYellowText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showYellowColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".yellow").hide();
                }else{
                    $(this).addClass("showYellowText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showYellowColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".yellow").show();
                }
            }else if($(this).hasClass("orangeItem")){//橙色
                if($(this).hasClass("showOrangeText")){//不显示
                    $(this).removeClass("showOrangeText");
                    $(this).addClass("showGrayText")
                    $(this).prev().removeClass("showOrangeColor")
                    $(this).prev().addClass("showGrayColor");
                    $(".orange").hide();
                }else{
                    $(this).addClass("showOrangeText");
                    $(this).removeClass("showGrayText")
                    $(this).prev().addClass("showOrangeColor")
                    $(this).prev().removeClass("showGrayColor");
                    $(".orange").show();
                }
            }else if($(this).hasClass("redItem")){//红色
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

        })
    });



    $(window).unload(function(){
        if(interval){
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
    }
    else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("${wsPath}socketServer/websocket?org=${orgCode}");
        //websocket = new MozWebSocket("ws://44.65.81.219:8888/chasEt/socketServer/websocket?org=${orgCode}");
    }
    else {
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
        var data= JSON.parse(evt.data);
        getPosition();
        if(playRybh==data.rybh){
            switchQySxtSp(data.qyid);
        }else{
            console.log("当前移动人员与正在播放的人员不一致，不进行视频切换");
        }
    }

    function onError() {}
    function onClose() {}

    window.close=function()
    {
        websocket.onclose();
    }
</script>
</body>
</html>
