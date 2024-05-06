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
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/zjdq/css/reset.css">
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/zjdq/css/mapstyle.css">
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
<script src="${ctx}/static/chas/bigscreen/zjdq/js/jquery.min.js"></script>
<script src="${ctx}/static/chas/bigscreen/zjdq/js/layer.js"></script>
<script src="${ctx}/static/chas/bigscreen/zjdq/js/jquery.resize.js"></script>
<script src="${ctx}/static/chas/bigscreen/zjdq/js/sockjs.js"></script>
<script src="${ctx}/static/chas/bigscreen/zjdq/js/index.js"></script>
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
        'a965aa827a5aedd4017a5af84be20003':{x:422,y:416,w:150,h:60,fx:-8,fy:0},//接待大厅
        'a965aa827a5aedd4017a5af8a5100005':{x:224,y:416,w:120,h:60,fx:-8,fy:0},//信息采集室
        'a965aa827a5aedd4017a5af8f15e0007':{x:122,y:375,w:45,h:13,fx:-3,fy:0},//等候室1
        'a965aa827a5aedd4017a5af92fb10009':{x:135,y:360,w:45,h:13,fx:-3,fy:0},//等候室2
        'a965aa827a5aedd4017a5af9773b000b':{x:147,y:345,w:45,h:13,fx:-3,fy:0},//等候室3
        'a965aa827a5aedd4017a5af9a2ef000d':{x:290,y:323,w:45,h:25,fx:-8,fy:0},//等候室4
        'a965aa827a5aedd4017a5af9d0c6000f':{x:355,y:323,w:45,h:25,fx:-5,fy:0},//等候室5
        'a965aa827a5aedd4017a5afa136e0011':{x:418,y:323,w:15,h:25,fx:-2,fy:0},//等候室6
        'a965aa827a5aedd4017a5afa37620013':{x:453,y:323,w:10,h:25,fx:-2,fy:0},//等候室7
        'a965aa827a5aedd4017a5afa67e90015':{x:485,y:323,w:10,h:25,fx:-2,fy:0},//等候室8
        'a965aa827a5aedd4017a5afa871a0017':{x:518,y:323,w:10,h:25,fx:-2,fy:0},//等候室9
        'a965aa827a5aedd4017a5afaa6d70019':{x:550,y:323,w:10,h:25,fx:-2,fy:0},//等候室10
        'a965aa827a5aedd4017a5afad49f001b':{x:584,y:323,w:10,h:25,fx:-2,fy:0},//等候室11
        'a965aa827a5aedd4017a5afaf96d001d':{x:616,y:323,w:90,h:25,fx:-2,fy:0},//等候室12
        'a965aa827a5aedd4017a5afc3203001f':{x:266,y:378,w:300,h:31,fx:0,fy:0},//看守区
        'a965aa827a5aedd4017a5aff514e0021':{x:735,y:323,w:75,h:45,fx:-3,fy:0},//询问室1远程提审室
        'a965aa827a5aedd4017a5affd7680023':{x:828,y:323,w:75,h:45,fx:-3,fy:0},//询问室2
        'a965aa827a5aedd4017a5b0007530025':{x:919,y:323,w:75,h:45,fx:0,fy:0},//询问室3
        'a965aa827a5aedd4017a5b0055d30027':{x:1011,y:323,w:75,h:45,fx:0,fy:0},//询问室4
        'a965aa827a5aedd4017a5b00738d0029':{x:1105,y:323,w:75,h:45,fx:2,fy:0},//询问室5
        'a965aa827a5aedd4017a5b0096d5002b':{x:1195,y:323,w:75,h:45,fx:3,fy:0},//询问室6
        'a965aa827a5aedd4017a5b00b55a002d':{x:1290,y:323,w:75,h:45,fx:5,fy:0},//询问室7
        'a965aa827a5aedd4017a5b00d69e002f':{x:1380,y:323,w:75,h:45,fx:6,fy:0},//询问室8
        'a965aa827a5aedd4017a5b00f86f0031':{x:1470,y:323,w:75,h:45,fx:8,fy:0},//询问室9
        'a965aa827a5aedd4017a5b0114b20033':{x:1424,y:400,w:60,h:50,fx:8,fy:0},//询问室10
        'a965aa827a5aedd4017a5b0197230035':{x:1320,y:400,w:60,h:50,fx:5,fy:0},//询问室11
        'a965aa827a5aedd4017a5b01c19f0037':{x:1225,y:400,w:60,h:50,fx:3,fy:0},//询问室12
        'a965aa827a5aedd4017a5b01dd750039':{x:1125,y:400,w:60,h:50,fx:3,fy:0},//询问室13
        'a965aa827a5aedd4017a5b020afe003b':{x:1025,y:400,w:60,h:50,fx:0,fy:0},//询问室14
        'a965aa827a5aedd4017a5b022c32003d':{x:925,y:400,w:60,h:50,fx:-2,fy:0},//询问室15
        'a965aa827a5aedd4017a5b0288db003f':{x:825,y:400,w:60,h:50,fx:0,fy:0},//未成年人询问室
        'a965aa827a5aedd4017a5b03092a0041':{x:1570,y:319,w:60,h:60,fx:10,fy:0},//重点人员询讯问室1
        'a965aa827a5aedd4017a5b0348760043':{x:1625,y:396,w:60,h:60,fx:10,fy:0},//重点人员询讯问室2
        'a965aa827a5aedd4017a5b048ee70046':{x:230,y:320,w:30,h:25,fx:-5,fy:0},//尿检区
        'a965aa827a5aedd4017a5b04c8960048':{x:180,y:320,w:25,h:25,fx:-5,fy:0},//卫生间
        'a965aa827a5aedd4017a5b05b1f1004a':{x:107,y:395,w:45,h:60,fx:-10,fy:0},//毒品称重采集室
        'a965aa827a5aedd4017a5b069aee004c':{x:730,y:373,w:600,h:20,fx:0,fy:0},//走廊 1
        'a965aa827a5aedd4017a5b06b0a9004e':{x:1525,y:398,w:30,h:60,fx:8,fy:0},//走廊 2
        'a965aa827a5aedd4017a5b06cc120050':{x:793,y:457,w:650,h:20,fx:0,fy:0},//走廊 3
        'a965aa827a5aedd4017a5b06dd190052':{x:625,y:420,w:60,h:55,fx:-5,fy:0},//走廊 4
        'a965aa827a5aedd4017a5b06f0340054':{x:1368,y:506,w:70,h:180,fx:13,fy:0},//走廊 5
        'a965aa827a748813017a7f3fb6b8003f':{x:1660,y:480,w:110,h:100,fx:15,fy:0},//五项检查区
        'a965aa827a748813017a7f3ffdd80041':{x:1733,y:585,w:80,h:70,fx:10,fy:0},//B超检查区
        'a965aa827a748813017a7f40500a0043':{x:1565,y:661,w:100,h:70,fx:10,fy:0}//律师回见室
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
