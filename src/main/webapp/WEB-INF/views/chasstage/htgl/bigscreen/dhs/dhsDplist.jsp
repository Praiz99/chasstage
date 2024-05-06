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
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/common/comui/css/tabs.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/common/comui/css/tips_panel.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/common/css/dialog_panel.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/common/css/easyui2.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/easyui-1.5.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/common/comui/css/reset.css">
    <script src="${ctx}/static/chas/bigscreen/common/js/dialogUtil.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/common/css/YDBigScreen.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/chas/bigscreen/common/css/liMarquee.css">
    <style>
        .jq22 { width: 100%; height: 100%;}
        .str_vertical .str_move, .str_down .str_move {
            white-space: inherit;
            width: 100%;
        }

        @keyframes blink {
            0% {
                opacity: 1;
            }
            50% {
                opacity: 0;
            }
            100% {
                opacity: 1;
            }
        }

        .blinking-colon {
            display: inline-block;
            animation: blink 1s infinite;
        }

        .scrollable-content {
            white-space: nowrap;
            text-overflow: ellipsis;
            animation: marquee 5s linear infinite;
        }

        @keyframes marquee {
            0% {
                transform: translateX(100%);
            }
            100% {
                transform: translateX(-100%);
            }
        }
    </style>
    <title>等候区人员情况</title>
</head>
<body class="yd-big-screen new-dialog new-tips">
<div class="yd-wrapper">
    <div class="yd-header">
        <table id="headTable" style="width: 100%">
            <tr>
                <th id="rsth" style="width: 30%;">
                    <h1 style="text-align:left;margin-left:40px">当前人数</h1>
                </th>
                <th style="width: 40%;"><h1>等候区人员情况</h1></th>
                <th style="width: 491px;">
                    <div class="current-date" style="margin-right: 25px;width: 70%;">
                        <div id="curTime"></div>
                        <div class="date-week">
                            <div id="curDate" style="color: #63E6E2;font-size: 30px"></div>
                            <div id="curWeek"></div>
                        </div>
                    </div>
                </th>
            </tr>
        </table>
    </div>
    <div id="A1" class="yd-container" style="height: 90%">
        <table style="width: 100%;height: 58px;">
            <tr style="background: #DCE3EA;">
                <td style='width: 10%;height: 16%;text-align: center'>序号</td>
                <td style='width: 12%;height: 16%;text-align: center'>姓名</td>
                <td style='width: 12%;height: 16%;text-align: center'>性别</td>
                <td style='width: 20%;height: 16%;text-align: center'>入区时间</td>
                <td style='width: 26%;height: 16%;text-align: center'>当前位置</td>
                <td style='width: 20%;height: 16%;text-align: center'>分配等候室</td>
            </tr>
        </table>
        <table id="jhScreenTable1" width="100%">
            <tbody id="jhrwTbody1" class="jhrwTbody">
            </tbody>
        </table>
        <div id="A2" class="yd-container-center jq22" style="padding:0px 0px">
            <table id="jhScreenTable" width="100%" style="table-layout: fixed;">
                <tbody id="jhrwTbody" class="jhrwTbody"></tbody>

                </tbody>
            </table>
        </div>
    </div>
<%--    <div class="yd-header">--%>

<%--    </div>--%>
</div>
<!-- 以下为通用脚本 -->
<script type="text/javascript" src="${ctx}/static/chas/bigscreen/common/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>

<!--表格滚动-->
<script type="text/javascript" src="${ctx}/static/chas/bigscreen/common/js/jquery.liMarquee.js"></script>
<script src="${ctx}/static/chas/bigscreen/common/js/sockjs.js"></script>
<script type="text/javascript" src="${ctx}/static/chas/bigscreen/common/js/StringParse.js"></script>
<!-- 以下为当前页脚本 -->
<script type="text/javascript">
    var obj;
    var list;
    var ctx = '${ctx}';
    var baqid = '${baqid}';
    var tableHeight = 0;
    var a = null;
    let parent = document.getElementById('A2');
    var o = 0;
    $(function(){
        // currentTime();
        getDpData();
        // setInterval(function () {
        //     // parent.scrollTop + parent.clientHeight = child.scrollHeight;
        //     // child.scrollHeight - parent.scrollTop = parent.clientHeight;
        //     // 让他多滚动 parent 一显示区域的高度。再跳到 最顶部 ，正好 给人一种在不断滚动的错觉
        //     if (parent.scrollTop >= 52.5*o) {
        //         parent.scrollTop = 0;
        //
        //     } else {
        //         parent.scrollTop++;
        //     }
        // }, 50)
    })

    //鼠标离开时，滚动继续

    function getDpData(){
        list = [];
        $.ajax({
            type: "post",
            url: ctx + "/loginDhsDp/getDhsryList?baqid="+baqid,
            async:false,
            dataType: 'json',
            cache: false,
            success: function(data) {
                if(data.code == 0){
                    var str = "";
                    var str2 = "";
                    var dhsryList = dataFormat(data.dhsryList);
                    var dhsryZdList = dataFormat(data.dhsryZdList);
                    var zdCount = dhsryZdList.length;
                    for(j = 0,len=dhsryList.length; j < len; j++) {
                        str+="<tr>"+
                            "<td style='width: 10%;height: 16%;text-align: center'>"+ (zdCount+1) +"</td>"+
                            "<td style='width: 12%;height: 16%;text-align: center'>"+ (dhsryList[j].ryxm ? dhsryList[j].ryxm : "") +"</td>"+
                            "<td style='width: 12%;height: 16%;text-align: center'>"+ (dhsryList[j].ryxbName ? dhsryList[j].ryxbName : "") +"</td>"+
                            "<td style='width: 20%;height: 16%;text-align: center;overflow: hidden; white-space: nowrap;'><div class='scrollable-content'>"+ (dhsryList[j].rssj ? dhsryList[j].rssj : "")+"</div></td>"+
                            "<td style='width: 26%;height: 16%;text-align: center'>"+ (dhsryList[j].dqwz ? dhsryList[j].dqwz : "") +"</td>"+
                            "<td style='width: 20%;height: 16%;text-align: center'>"+(dhsryList[j].qymc ? dhsryList[j].qymc : "") +"</td>"+
                            "</tr>";
                        zdCount++;
                    }

                    for(j = 0,len=dhsryZdList.length; j < len; j++) {
                        str2+="<tr style='height: 105px;background-color: #63E6E2'>"+
                            "<td style='width: 10%;height: 16%;text-align: center'>"+(j+1)+"</td>"+
                            "<td style='width: 12%;height: 16%;text-align: center'>"+ (dhsryZdList[j].ryxm ? dhsryZdList[j].ryxm : "") +"</td>"+
                            "<td style='width: 12%;height: 16%;text-align: center'>"+ (dhsryZdList[j].ryxbName ? dhsryZdList[j].ryxbName : "") +"</td>"+
                            "<td style='width: 20%;height: 16%;text-align: center;overflow: hidden; white-space: nowrap;'><div class='scrollable-content'>"+ (dhsryZdList[j].rssj ? dhsryZdList[j].rssj : "")+"</div></td>"+
                            "<td style='width: 26%;height: 16%;text-align: center'>"+ (dhsryZdList[j].dqwz ? dhsryZdList[j].dqwz : "") +"</td>"+
                            "<td style='width: 20%;height: 16%;text-align: center'>"+(dhsryZdList[j].qymc ? dhsryZdList[j].qymc : "") +"</td>"+
                            "</tr>";
                    }

                    var l1 = document.getElementById("jhrwTbody");
                    var l2 = document.getElementById("jhrwTbody1");
                    l2.innerHTML = str2;
                    var index = dhsryList.length + dhsryZdList.length;
                    o = index;
                    document.getElementById("rsth").innerHTML = "<h1 style='text-align:left;margin-left:20px'>当前人数"+index+"</h1>";
                    var height = 55 * index;
                    var container_height = document.getElementById("A2").offsetHeight;
                    if(tableHeight == 0){
                        tableHeight = container_height;
                    }
                    if(height > tableHeight + 10) {
                        l1.innerHTML = str + str;
                    }else {
                        l1.innerHTML = str;
                        $(".str_origin").css("top","0px")
                    }
                    if(null == a) {
                        a = $('.jq22').liMarquee({
                            runshort: false,
                            direction: 'up'
                        });
                    }else {
                        $('.jq22').liMarquee('update');
                    }
                }else {
                    var str = "";
                    var l1 = document.getElementById("jhrwTbody");
                    document.getElementById("rsth").innerHTML = "<h1 style='text-align:left;margin-left:20px'>当前人数0</h1>";
                    l1.innerHTML = str;
                }

            }
        });
    }
    function currentTime(){
        var d = new Date(), date = '', week = '', time = '';
        date += d.getFullYear() + '年';
        var months = d.getMonth() + 1;
        if(months < 10) {
            months = '0' + months;


        }
        date += months + '月';
        var days = d.getDate();
        if(days < 10) {
            days = '0' + days;
        }
        date += days + '日';
        if(d.getDay()==0) week="周日";
        if(d.getDay()==1) week="周一";
        if(d.getDay()==2) week="周二";
        if(d.getDay()==3) week="周三";
        if(d.getDay()==4) week="周四";
        if(d.getDay()==5) week="周五";
        if(d.getDay()==6) week="周六";
        var hours = d.getHours();
        if(hours < 10) {
            hours = '0' + hours;
        }
        time += hours + ':';
        var minutes = d.getMinutes();
        if(minutes < 10) {
            minutes = '0' + minutes;
        }
        time += minutes + ':';
        var seconds = d.getSeconds();
        if(seconds < 10) {
            seconds = '0' + seconds;
        }
        time += seconds;
        $('#curTime').html(time);
        $('#curWeek').html(week);
        $('#curDate').html(date);
    }
    function currentTime(){
        var d = new Date(), date = '', week = '', time = '';
        date += d.getFullYear() + '年';
        var months = d.getMonth() + 1;
        if(months < 10) {
            months = '0' + months;
        }
        date += months + '月';
        var days = d.getDate();
        if(days < 10) {
            days = '0' + days;
        }
        date += days + '日';
        if(d.getDay()==0) week="周日";
        if(d.getDay()==1) week="周一";
        if(d.getDay()==2) week="周二";
        if(d.getDay()==3) week="周三";
        if(d.getDay()==4) week="周四";
        if(d.getDay()==5) week="周五";
        if(d.getDay()==6) week="周六";
        var hours = d.getHours();
        if(hours < 10) {
            hours = '0' + hours;
        }
        var minutes = d.getMinutes();
        if(minutes < 10) {
            minutes = '0' + minutes;
        }
        time += hours + '<span class="blinking-colon">:</span>' + minutes;
        $('#curDate').html(date+" "+week+"  "+time);
    }

    setInterval(function(){currentTime()},1000);
    setInterval(function(){getDpData()},1000*30);
    // setInterval(function(){test()},5000);

    function openLayout(rybh) {
        opendialog('', ctx + "/chasEt/bigScreen/chasBaqGuideScreenController/layout?rybh="+rybh, '60%', '65%');
        $(".panel-tool-max").remove()
    }
    function closedailog() {
        $('#dailog').dialog('close');
        // getDpData()
    }

    function dataFormat(list) {
        for(j = 0,len=list.length; j < len; j++){
            if(list[j] == null){
                list[j].ryxm = ""
            }
            if(list[j].zbdwBhName == null){
                list[j].zbdwBhName = ""
            }
            if(list[j].mj1Xm == null){
                list[j].mj1Xm = ""
            }
            if(list[j].yytjsj == null){
                list[j].yytjsj = ""
            }else {
                list[j].yytjsj = list[j].yytjsj.substring(0,16).replace('-','/').replace('-','/')
            }
            if(list[j].tjzt == '0'){
                list[j].tjztName = "待体检"
            }else if(list[j].tjzt=='2'){
                list[j].tjztName = "已体检（未上传）"
            }else {
                list[j].tjztName = ""
            }
        }
        return list
    }


    // function test() {
    //     var a = Math.floor(Math.random()*10)+1
    //     for (var k=0; k<list.length; k++){
    //         if(list[k].order == a){
    //             var name = 'ryxl'+a
    //             var xl = document.getElementsByName(name)
    //             for(var i=0; i<xl.length; i++){
    //                 xl[i].innerHTML = '<span style="color: #FF0000">' + (a+10) + '</span>'
    //             }
    //         }
    //     }
    // }
</script>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("${wsPath}socketServer/websocket?org=${baqid}");
    }
    else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("${wsPath}socketServer/websocket?org=${baqid}");
    }
    else {
        websocket = new SockJS("${basePath}socketServer/sockjs?org=${baqid}");
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
        if("haiNingCorridorLocationWaiting"==evt.data){
            getDpData()
        }
    }

    function onError() {}
    function onClose() {
        setTimeout(function(){
            reconnect();
        },5000);
    }

    window.close=function(){
        websocket.onclose();
    }
    //把刚才干的事情重写一遍
    function reconnect (){
        if ('WebSocket' in window) {
            websocket = new WebSocket("${wsPath}socketServer/websocket?org=${baqid}");
        }
        else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("${wsPath}socketServer/websocket?org=${baqid}");
        }
        else {
            websocket = new SockJS("${basePath}socketServer/sockjs?org=${baqid}");
        }
        websocket.onopen = onOpen;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
        websocket.onclose = onClose;
    }
</script>
</body>
</html>
