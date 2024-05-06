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
    <link rel="stylesheet" href="${ctx}/static/chas/bigscreen/xydzx/css/mapstyle.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/easyui-1.5.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/easyui-1.5.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/My97DatePicker/skin/WdatePicker.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/table/comui.table.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/button.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/form.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/tips_panel.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/dialog_panel.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/map/style/css/mapDialog.css">
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
<%--<div class="wrapper">
    <div class="map-content">
        <img src="${ctx}/static/map/style/images/person-icon1.png" alt="民警" onClick="showMjDialog()">
        <img src="${ctx}/static/map/style/images/person-icon2.png" alt="嫌疑人" onClick="showXyrDialog('R3300000000002023055042')">
        <img src="${ctx}/static/map/style/images/camera-icon.png" alt="摄像头" onClick="showSxtDialog()">
    </div>
</div>--%>

<!--民警信息-->
<div id="mjDialog" class="dialog-pop-wrap" style="display: none;">
    <div class="dialog-pop-content dialog-mjxx-content">
        <div id="mjxxHeader" class="dialog-pop-header">
            <div class="dialog-pop-title">民警信息</div>
            <div class="dialog-pop-close" onClick="closeMjDialog()">关闭</div>
        </div>
    </div>
</div>

<!--访客信息-->
<div id="fkDialog" class="dialog-pop-wrap" style="display: none;">
    <div class="dialog-pop-content dialog-mjxx-content">
        <div id="fkxxHeader" class="dialog-pop-header">
            <div class="dialog-pop-title">访客信息</div>
            <div class="dialog-pop-close" onClick="closeFkDialog()">关闭</div>
        </div>
    </div>
</div>

<!--摄像头-->
<div id="sxtDialog" class="dialog-pop-wrap dialog-sxt-pop-wrap" style="display: none;">
    <div class="dialog-pop-content dialog-sxt-content">
        <div class="dialog-pop-header">
            <div class="dialog-pop-title">办案区区域监控视频</div>
            <div class="dialog-pop-close" onClick="closeSxtDialog()">关闭</div>
        </div>
        <div class="dialog-pop-body dialog-sxt-body">
            <!--摄像头播放代码-->
<%--            <iframe src="${ctx}/api/rest/chasstage/rygjMap/apiService/showBigscreen?qy=hzxs&name=map"></iframe>--%>
        </div>
    </div>
</div>

<!--轨迹地图-->
<div id="gjDialog" class="dialog-pop-wrap dialog-sxt-pop-wrap" style="display: none;">
    <div class="dialog-pop-content dialog-sxt-content">
        <div id="gjmapHeader" class="dialog-pop-header">
            <div class="dialog-pop-title">人员轨迹</div>
            <div class="dialog-pop-close" onClick="closeGjDialog()">关闭</div>
        </div>

    </div>
</div>

<!--嫌疑人信息-->
<div id="xyrDialog" class="dialog-pop-wrap" style="display: none;">
    <div class="dialog-pop-content dialog-xyr-content">
        <div class="dialog-pop-header">
            <div class="dialog-pop-title">嫌疑人信息</div>
            <div class="dialog-pop-close" onClick="closeXyrDialog()">关闭</div>
        </div>
        <div class="dialog-pop-body dialog-xyr-body">
            <div id="xyrTab" class="dialog-tab">
                <div class="actived">基本信息</div>
                <div>随身物品</div>
                <div>安全检查</div>
                <div>采集信息</div>
            </div>
        </div>
    </div>
</div>

<!--审讯记录-->
<div id="sxjlDialog" class="dialog-pop-wrap dialog-sxt-pop-wrap" style="display: none;">
    <div class="dialog-pop-content dialog-sxjl-content">
        <div class="dialog-pop-header">
            <div class="dialog-pop-title">审讯记录</div>
            <div class="dialog-pop-close" onClick="closeSxjlDialog()">关闭</div>
        </div>
        <div class="dialog-pop-body dialog-sxt-body">
            <div id="sxjlGrid"></div>
        </div>
    </div>
</div>
<iframe id="main" style="display: none;"></iframe>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/jquery.min.js"></script>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/layer.js"></script>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/jquery.resize.js"></script>
<script src="${ctx}/static/chas/bigscreen/bjfj/js/sockjs.js"></script>
<script src="${ctx}/static/chas/bigscreen/xydzx/js/index.js"></script>
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
    var grid = null;
    var headIcon = {width: 20, height: 30, mixWidth: 20, mixHeight: 20};
    var qyMeasure = {
        '202305161725156BF4D6A51D6A08A936': {x: 1124, y: 593, w: 422, h: 189, fx:4, fy: 0,oa:0},//信息采集室
        '20230516172534A1DB6890E1E1081A4E': {x: 1087, y: 144, w: 145, h: 84, fx: 0, fy: 0,oa:0},//等候室1
        '202305161726030F2EB7A896D426EC99': {x: 1250, y: 150, w: 66, h: 110, fx: 4, fy: 0,oa:0},//等候室2
        '2023051617261024A1EB40B73A56705C': {x: 1322, y: 150, w: 66, h: 110, fx: 6, fy: 0,oa:0},//等候室3
        '2023051617261643E60DE2DF2D9BC539': {x: 1477, y: 144, w: 173, h: 155, fx: 18, fy: 0,oa:0},//等候室4
        '20230516172642D417C851120793BDA1': {x: 667, y: 558, w: 215, h: 225, fx: -4 ,fy: 0,oa:0},//讯/询问室1
        '202305161726492F70A7752D8E90D95B': {x: 432, y: 558, w: 215, h: 224, fx: -5, fy: 0,oa:0},//讯/询问室2
        '20230516172705649A24A03299ED407D': {x: 211, y: 510, w: 224, h: 303, fx: -18, fy: 0,oa:0},//远程提审室
        '202305161727164559C49BEFF01EE5E9': {x: 279, y: 295, w: 208, h: 158, fx: -15, fy: 0,oa:0},//讯/询问室4
        '20230516172727F24F3BAF0B32B855B4': {x: 563, y: 146, w: 335, h: 130, fx: -6, fy: 0, oa:0},//快办室
        '20230516172747AB7FC62BD4C7984943': {x: 1098, y: 280, w: 360, h: 297, fx: 4, fy: 0, oa:0},//看守区
        '2023051617280144E63CA53C74C40A19': {x: 1507, y: 392, w: 248, h: 188, fx: 20, fy: 0, oa:0},//辨认室
        '202305161728179C84A9A226267B04D8': {x: 918, y: 144, w: 156, h: 128, fx: 0, fy: 0, oa:0},//尿检室
        '20230626165702981D90861E63F7620D': {x: 508, y: 283, w: 576, h: 263, fx: -6, fy: 0, oa:0},//询问室全局
        '2023062616562893180C60529B57549A': {x: 1390, y: 142, w: 83, h: 178, fx: 14, fy: 0, oa:0}//醒酒区
    }
    var qyMeasureBySxt = {
        '202305161725156BF4D6A51D6A08A936': {x: 1115, y: 665, w: 10, h: 10, fx:0, fy: 0,oa:0},//信息采集室
        '20230516172534A1DB6890E1E1081A4E': {x: 1085, y: 192, w: 10, h: 10, fx: 0, fy: 0,oa:0},//等候室1
        '202305161726030F2EB7A896D426EC99': {x: 1245, y: 190, w: 10, h: 10, fx: 0, fy: 0,oa:0},//等候室2
        '2023051617261024A1EB40B73A56705C': {x: 1321, y: 193, w: 10, h: 10, fx: 0, fy: 0,oa:0},//等候室3
        '2023051617261643E60DE2DF2D9BC539': {x: 1689, y: 220, w: 10, h: 10, fx: 0, fy: 0,oa:0},//等候室4
        '20230516172642D417C851120793BDA1': {x: 638, y: 677, w: 10, h: 10, fx: 0 ,fy: 0,oa:0},//讯/询问室1
        '202305161726492F70A7752D8E90D95B': {x: 398, y: 658, w: 10, h: 10, fx: 0, fy: 0,oa:0},//讯/询问室2
        '20230516172705649A24A03299ED407D': {x: 116, y: 684, w: 10, h: 10, fx: 0, fy: 0,oa:0},//远程提审室
        '202305161727164559C49BEFF01EE5E9': {x: 237, y: 347, w: 10, h: 10, fx: 0, fy: 0,oa:0},//讯/询问室4
        '20230516172727F24F3BAF0B32B855B4': {x: 534, y: 215, w: 10, h: 10, fx: 0, fy: 0, oa:0},//快办室
        '20230516172747AB7FC62BD4C7984943': {x: 1100, y: 443, w: 10, h: 10, fx: 0, fy: 0, oa:0},//看守区
        '2023051617280144E63CA53C74C40A19': {x: 1806, y: 488, w: 10, h: 10, fx: 0, fy: 0, oa:0},//辨认室
        '202305161728179C84A9A226267B04D8': {x: 906, y: 226, w: 10, h: 10, fx: 0, fy: 0, oa:0},//尿检室
        '20230626165702981D90861E63F7620D': {x: 511, y: 456, w: 10, h: 10, fx: 0, fy: 0, oa:0},//询问室全局
        '2023062616562893180C60529B57549A': {x: 1424, y: 285, w: 10, h: 10, fx: 0, fy: 0, oa:0}//醒酒区
    }
    $(function () {
        getPosition();
        // getSxtxx();
        $("#track").on("click", "div.button", function () {
            rybh = "";
            playQyid = "";
            playRybh = "";
            $("#track").hide();
            var vlcurl = "vlc://quit";
            $("#main").attr("src", vlcurl);
            //layer.msg("停止视频追踪，请手动关闭播放器",{offset:['500px', '340px']});
        });
        /*$("#map").on("click", "div.xyr", function () {
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
        });*/
        interval = setInterval(getPosition, 5 * 1000);
        // interval = setInterval(getSxtxx, 5 * 1000);
// 嫌疑人信息tab切换
        $('.dialog-tab > div').click(function() {
            var index = $(this).index();
            $(this).addClass('actived').siblings().removeClass('actived');
            $('.dialog-tab-content > div').eq(index).show().siblings().hide();
        })

        //审讯记录列表
        var data = {Rows:[],Total:0};

        /*var obj1 = {};
        data.Rows.push(obj1);
        data.Total = data.Rows.length;*/

        grid = $('#sxjlGrid').ligerTable({
            columns:[
                {field:'id',title:'id',hide:true},
                {field:'ryxm',title:'被提审人', width: '8%',formatter:moreInfo},
                {field:'btsrSfzh',title:'被提审人证件号码', width: '19%',formatter:moreInfo},
                {field:'baqmc',title:'办案单位', width: '17%',formatter:moreInfo1},
                {field:'symj',title:'提审民警', width: '10%',formatter:moreInfo},
                {field:'qymc',title:'审讯室', width: '10%',formatter:moreInfo1},
                {field:'kssj',title:'开始时间', width: '18%',formatter:moreInfo1},
                {field:'xgsj',title:'结束时间', width: '18%',formatter:moreInfo1}
            ],
            width:'100%',
            height:'422',
            checkbox: false,                                       //显示复选框
            rownumbers: false,                                    //显示序号列
            isSingleSelectOnly:false,                             //是否只允许单选
            dataAction:'server',									  //加载数据的方式，local为本地加载，此时url属性失效，server为服务端加载，需要指定后台url
            url:ctx+'/api/rest/chasstage/rygjMap/apiService/getSxjlData',
            parms:{},                             //提交到后台的参数
            data:data,                                            //dataAction为server时，该属性失效
            usePager:false,                                        //使用分页
            pageSize: 10,
            rowHeight: 34,//行默认的高度
            root:'rows',
            record:'total',
            pageParmName:'page',
            pagesizeParmName:'pagesize',
            onAfterShowData:function(currentData,g){
                console.log(g.getSelectedRows().length);
            },
            //一般用于初始化已选择的列,return true表示选中
            isSelected:function(rowdata){
                /*if(rowdata && rowdata["ajbh"]=='360311198803181516'){
                    return false;
                }*/
                return false;
            }
        });
    });

    function moreInfo1(value,row,index){
        if(!value||value=='null'){
            return "";
        }
        return "<span title='"+value+"'>"+value+"</span>";
    }

    function moreInfo(value,row,index){
        var val = value;
        if(!value||value=='null'){
            return "";
        }else if(value.length > 3){
            val = value.substring(0,3) + "...";
        }
        return "<span title='"+value+"'>"+val+"</span>";
    }

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
<script type="text/javascript" src="${ctx}/static/common/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/com/base.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/utils/common.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/table/comui.table.easyui.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/dic/com.dictionary.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/table/comui.table.easyui.js"></script>
</body>
</html>
