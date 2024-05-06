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
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/scq/css/reset.css">
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/scq/css/mapstyle.css">
    <title>视频巡查</title>
</head>
<body class="new-dialog page" style="background-color:transparent!important" >
<div class="title">视频巡查</div>
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
<script src="${ctx}/static/chas/bigscreen/scq/js/jquery.min.js"></script>
<script src="${ctx}/static/chas/bigscreen/scq/js/layer.js"></script>
<script src="${ctx}/static/chas/bigscreen/scq/js/jquery.resize.js"></script>
<script src="${ctx}/static/chas/bigscreen/scq/js/sockjs.js"></script>
<script src="${ctx}/static/chas/bigscreen/scq/js/index.js"></script>
<!-- 以下为当前页脚本 -->
<script type="text/javascript">
    var ctx = "${ctx}";
    var baqid="${baqid}";
    var rybh="",playRybh="",playQyid="";
    var oldw = 1366,oldh=657;
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
        'a946b0b375c0f4210175c43f2a690001':{x:737,y:473,w:269,h:162,fx:0,fy:0},//执法办案管理中心大厅
        'a946b0b375c0f4210175c4409ef90003':{x:420,y:472,w:276,h:120,fx:0,fy:0},//信息登记采集区
        'a946b0b375c0f4210175c44189a50005':{x:294,y:513,w:83,h:79,fx:0,fy:0},//看守区
        'a946b0b375c0f4210175c442008d0007':{x:420,y:592,w:81,h:43,fx:0,fy:0},//等候室1
        'a946b0b375c0f4210175c442262e0009':{x:222,y:594,w:153,h:40,fx:0,fy:0},//等候室2
        'a946b0b375c0f4210175c4424f6a000b':{x:222,y:568,w:69,h:23,fx:0,fy:0},//等候室3
        'a946b0b375c0f4210175c442c19e000d':{x:222,y:541,w:69,h:24,fx:0,fy:0},//醒酒室
        'a946b0b375c0f4210175c4430483000f':{x:222,y:515,w:69,h:24,fx:0,fy:0},//等候室4
        'a946b0b375c0f4210175c44347ed0011':{x:222,y:472,w:69,h:40,fx:0,fy:0},//等候室5
        'a946b0b375c0f4210175c4445f6e0013':{x:1185,y:350,w:154,h:51,fx:0,fy:0},//彩超室心电图
        'a946b0b375c0f4210175c4449fe50015':{x:1035,y:483,w:67,h:48,fx:0,fy:0},//E邮室
        'a946b0b375c0f4210175c445d7620017':{x:504,y:400,w:93,h:31,fx:0,fy:0},//尿检区
        'a946b0b375c0f4210175c4460c780019':{x:562,y:364,w:105,h:32,fx:0,fy:0},//嫌疑人卫生间
        'a946b0b375c0f4210175c4473b81001b':{x:178,y:436,w:533,h:31,fx:0,fy:0},//审讯走廊1
        'a946b0b375c0f4210175c44770e9001d':{x:719,y:436,w:383,h:31,fx:0,fy:0},//审讯走廊2
        'a946b0b375c0f4210175c447e1c3001f':{x:178,y:48,w:67,h:356,fx:0,fy:0},//审讯走廊3
        'a946b0b375c0f4210175c44879420021':{x:84,y:405,w:86,h:62,fx:0,fy:0},//办案区出口
        'a946b0b375c0f4210175c449d1190023':{x:257,y:377,w:137,h:53,fx:0,fy:0},//审讯室1
        'a946b0b375c0f4210175c44a263e0025':{x:257,y:317,w:137,h:53,fx:0,fy:0},//审讯室2
        'a946b0b375c0f4210175c44a68440027':{x:257,y:49,w:138,h:53,fx:0,fy:0},//审讯室3
        'a946b0b375c0f4210175c44aabb10029':{x:31,y:152,w:135,h:53,fx:0,fy:0},//审讯室4
        'a946b0b375c0f4210175c44aeb05002b':{x:31,y:225,w:135,h:51,fx:0,fy:0},//审讯室5
        'a946b0b375c0f4210175c44bac47002d':{x:258,y:172,w:137,h:76,fx:0,fy:0},//特殊讯询问室
        'a946b0b375c0f4210175c44d50930030':{x:31,y:284,w:135,h:51,fx:0,fy:0},//未成年询讯问室
        'a946b0b375c0f4210175c44e72040032':{x:31,y:74,w:135,h:70,fx:0,fy:0},//心理测谎室
        'a946b0b375c0f4210175d0295b9d00c8':{x:514,y:599,w:134,h:36,fx:0,fy:0},//毒品称重室
        'a946b0b375c0f4210175d3c7016700dc':{x:94,y:342,w:74,h:58,fx:0,fy:0},//辨认室
        'a946b0b375c0f4210175d3c77c0700de':{x:1038,y:388,w:144,h:44,fx:0,fy:0},//血检室
        'a946b0b375c0f4210175d3c7a9b000e0':{x:1116,y:467,w:218,h:62,fx:0,fy:0},//X光室
        'a946b0b375c0f4210175d3c804e100e2':{x:1246,y:406,w:93,h:54,fx:0,fy:0}//操作间
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
        $("#map").on("click","div.xyr",function () {
            var qyid=$(this).attr("qyid");
            //console.log(qyid);
            rybh=$(this).attr("rybh");
            openQySxtSp(qyid);
        });
        $('#map').on('mouseenter','.xyr', function(){
            var that = this;
            var title =$(this).attr("ryxm")+
                "当前所在位置:<br/>"+$(this).attr("qymc");
            layer.tips(title, that,{
                tips: [1, '#8da0cc'],
                time: 4000
            });
        });
        $('#map').on('mouseleave','.xyr', function(){
            var that = this;
            var title = $(this).attr("title");
            layer.closeAll();
        });
        interval = setInterval(getPosition,5*1000);

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
