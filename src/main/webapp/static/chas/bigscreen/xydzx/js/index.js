//当页面宽高发生变化时，重新计算顶点坐标和宽高
function calcVertexs(width,height,newWidth,newHeight) {
    //console.log("width="+width+",height="+height+",newWidth="+newWidth+",newHeight="+newHeight);
    var keys = Object.keys(qyMeasure);
    for(var i=0;i<keys.length;i++){
        var vertex=qyMeasure[keys[i]];
        //console.log(vertex)
        if(vertex.x){
            vertex.x = Math.round((vertex.x/width)*newWidth);
            vertex.y = Math.round((vertex.y/height)*newHeight);
            vertex.w = Math.round((vertex.w/width)*newWidth);
            vertex.h = Math.round((vertex.h/height)*newHeight);
            vertex.fx = Math.round((vertex.fx/width)*newWidth);
            vertex.fy = Math.round((vertex.fy/height)*newHeight);
        }

    }
    var keys = Object.keys(qyMeasureBySxt);
    for(var i=0;i<keys.length;i++){
        var vertex=qyMeasureBySxt[keys[i]];
        //console.log(vertex)
        if(vertex.x){
            vertex.x = Math.round((vertex.x/width)*newWidth);
            vertex.y = Math.round((vertex.y/height)*newHeight);
            vertex.w = Math.round((vertex.w/width)*newWidth);
            vertex.h = Math.round((vertex.h/height)*newHeight);
            vertex.fx = Math.round((vertex.fx/width)*newWidth);
            vertex.fy = Math.round((vertex.fy/height)*newHeight);
        }
    }
    //avatarOffsetX=(avatarOffsetX/width)*newWidth;
    //avatarOffsetY=(avatarOffsetY/height)*newHeight;
    //console.log(vertexArray);
}

function resetSize() {
    var h=$("html").outerHeight();
    var w=$("html").outerWidth();
    calcVertexs(oldw,oldh,w,h);
    oldw =w;
    oldh=h;
    //containerW=w;
    //containerH=h;
    getPosition();
}
function addRyIcon(ryxx,x,y,iw,ih) {
    var name_top=y-15;
    var ryxm = '';
    var lx = '';
    if(ryxx.rybh != undefined){
        ryxm=ryxx.xm.substr(0,1)+"*";
        lx = ryxx.rybh.substr(0,1);
    }
    if(ryxx.sbmc != undefined){
        lx = 'S';
    }
    // var picName='/static/chas/bigscreen/bjfj/images/red.png';
    var picName='/static/map/style/images/person-icon2.png';
    if("R"==lx){
        var clazz = "red";
        // if(ryxx.tsqt.indexOf("03") != -1||ryxx.tsqt.indexOf("05") != -1||ryxx.tsqt.indexOf("11") != -1||ryxx.tsqt.indexOf("14") != -1){
        //     picName='/static/chas/bigscreen/bjfj/images/red.png';
        //     clazz = "red";
        // }else if(ryxx.tsqt.indexOf("04") != -1||ryxx.tsqt.indexOf("06") != -1||ryxx.tsqt.indexOf("10") != -1){
        //     picName='/static/chas/bigscreen/bjfj/images/orange.png';
        //     clazz = "orange";
        // }else if(ryxx.tsqt.indexOf("01") != -1||ryxx.tsqt.indexOf("02") != -1||ryxx.tsqt.indexOf("07") != -1||ryxx.tsqt.indexOf("08") != -1
        //     ||ryxx.tsqt.indexOf("12") != -1){
        //     picName='/static/chas/bigscreen/bjfj/images/yellow.png';
        //     clazz = "yellow";
        // }else{
        //     picName='/static/chas/bigscreen/bjfj/images/blue.png';
        //     clazz = "blue";
        // }
        var name =$('<div class="plname '+clazz+'" style="left:'+x+'px;top:'+name_top+'px;">'+ryxm+'</div>');
        var icon=$('<div class="icon xyr '+clazz+'" rybh="'+ryxx.rybh+'" ryxm="'+ryxx.xm+'" qymc="'+ryxx.qymc+'" qyid="'+ryxx.qyid+'" style="position: absolute; left: '+x+'px;top: '+y+'px; width: '+iw+'px; height: '+ih+'px;">\n' +
            '                    <img width="100%" height="100%" src="'+ctx+picName+'" alt="嫌疑人" onClick="showXyrDialog(\''+ryxx.rybh+'\',\''+ryxx.qyid+'\')">\n' +
            '                </div>');
        // $("#map").append(name);
        $("#map").append(icon);
    }else if("M"==lx) {
        picName='/static/map/style/images/person-icon1.png';
        var name =$('<div class="plname mj" style="left:'+x+'px;top:'+name_top+'px;">'+ryxm+'</div>');
        var icon=$('<div class="icon mj" rybh="'+ryxx.rybh+'" ryxm="'+ryxx.xm+'" qymc="'+ryxx.qymc+'" qyid="'+ryxx.qyid+'" style="position: absolute; left: '+x+'px;top: '+y+'px; width: '+iw+'px; height: '+ih+'px;">\n' +
            '                    <img width="100%" height="100%" src="'+ctx+picName+'" alt="民警" onClick="showMjDialog(\''+ryxx.rybh+'\',\''+ryxx.qyid+'\')">\n' +
            '                </div>');
        // $("#map").append(name);
        $("#map").append(icon);
    }else if("F"==lx){
        picName='/static/map/style/images/person-icon4.png';
        var name =$('<div class="plname fk" style="left:'+x+'px;top:'+name_top+'px;">'+ryxm+'</div>');
        var icon=$('<div class="icon fk" rybh="'+ryxx.rybh+'" ryxm="'+ryxx.xm+'" qymc="'+ryxx.qymc+'" qyid="'+ryxx.qyid+'" style="position: absolute; left: '+x+'px;top: '+y+'px; width: '+iw+'px; height: '+ih+'px;">\n' +
            '                    <img width="100%" height="100%" src="'+ctx+picName+'" alt="访客" onClick="showFkDialog(\''+ryxx.rybh+'\',\''+ryxx.qyid+'\')">\n' +
            '                </div>');
        // $("#map").append(name);
        $("#map").append(icon);
    }else if("S"==lx){
        picName='/static/map/style/images/camera-icon.png';
        var name =$('<div class="plname sxt" style="left:'+x+'px;top:'+name_top+'px;">'+ryxm+'</div>');
        var icon=$('<div class="icon sxt" qyid="'+ryxx.qyid+'" style="position: absolute; left: '+x+'px;top: '+y+'px; width: '+iw+'px; height: '+ih+'px;">\n' +
            '                    <img width="100%" height="100%" src="'+ctx+picName+'" alt="摄像头" onClick="openQySxtSp(\''+ryxx.qyid+'\')">\n' +
            '                </div>');
        // $("#map").append(name);
        $("#map").append(icon);
    }



}


//获取办案区人员定位信息
function getPosition() {
    $.ajax({
        url: ctx + '/api/rest/chasstage/rygjMap/apiService/getPosBybaqid',
        data:{baqid:baqid},
        dataType:'json',
        type:'POST',
        success:function (data) {
            console.log(data);
            $("#map").empty();
            getSxtxx();
            if(data.success&&data.data){
                //测试数据
                /*var qyArray = [
                    '202305161725156BF4D6A51D6A08A936',//信息采集室
                    '20230516172534A1DB6890E1E1081A4E',//等候室1
                    '202305161726030F2EB7A896D426EC99',//等候室2
                    '2023051617261024A1EB40B73A56705C',//等候室3
                    '2023051617261643E60DE2DF2D9BC539',//等候室4
                    '20230516172642D417C851120793BDA1',//讯/询问室1
                    '202305161726492F70A7752D8E90D95B',//讯/询问室2
                    '20230516172705649A24A03299ED407D',//远程提审室
                    '202305161727164559C49BEFF01EE5E9',//讯/询问室4
                    '20230516172727F24F3BAF0B32B855B4',//快办室
                    '20230516172747AB7FC62BD4C7984943',//看守区
                    '2023051617280144E63CA53C74C40A19',//辨认室
                    '202305161728179C84A9A226267B04D8',//尿检室
                    '2023062616562893180C60529B57549A',//询问室全局
                    '20230626165702981D90861E63F7620D'//醒酒区
                ]
                for(var j = 0; j < qyArray.length; j++){
                    var test = {}
                    test.qyid = qyArray[j]
                    var list = []
                    var ry = {
                        "id": "R3400000000002020070005",
                        "isdel": 0,
                        "dataflag": "0",
                        "lrrSfzh": null,
                        "lrsj": null,
                        "xgrSfzh": null,
                        "xgsj": null,
                        "baqid": "57BBEB0A95954695967AE423306120DB",
                        "baqmc": "孝感派出所",
                        "ryid": null,
                        "wdbh": null,
                        "qyid": "202208161731152FECD8909294A114BF",
                        "xm": "冯满艺",
                        "kssj": "2021-07-03 08:33:03",
                        "jssj": null,
                        "fzrxm": null,
                        "hdnr": null,
                        "sbid": null,
                        "qymc": "登记区",
                        "rybh": "R3400000000002020070005",
                        "bz": null,
                        "tsqt": "99",
                        "sfzh": null
                    }
                    var mj = {
                        "id": "M3400000000002020070005",
                        "isdel": 0,
                        "dataflag": "0",
                        "lrrSfzh": null,
                        "lrsj": null,
                        "xgrSfzh": null,
                        "xgsj": null,
                        "baqid": "57BBEB0A95954695967AE423306120DB",
                        "baqmc": "孝感派出所",
                        "ryid": null,
                        "wdbh": null,
                        "qyid": "202208161731152FECD8909294A114BF",
                        "xm": "冯满艺",
                        "kssj": "2021-07-03 08:33:03",
                        "jssj": null,
                        "fzrxm": null,
                        "hdnr": null,
                        "sbid": null,
                        "qymc": "登记区",
                        "rybh": "M3400000000002020070005",
                        "bz": null,
                        "tsqt": "99",
                        "sfzh": null
                    }
                    var fk = {
                        "id": "F3400000000002020070005",
                        "isdel": 0,
                        "dataflag": "0",
                        "lrrSfzh": null,
                        "lrsj": null,
                        "xgrSfzh": null,
                        "xgsj": null,
                        "baqid": "57BBEB0A95954695967AE423306120DB",
                        "baqmc": "孝感派出所",
                        "ryid": null,
                        "wdbh": null,
                        "qyid": "202208161731152FECD8909294A114BF",
                        "xm": "冯满艺",
                        "kssj": "2021-07-03 08:33:03",
                        "jssj": null,
                        "fzrxm": null,
                        "hdnr": null,
                        "sbid": null,
                        "qymc": "登记区",
                        "rybh": "F3400000000002020070005",
                        "bz": null,
                        "tsqt": "99",
                        "sfzh": null
                    }
                    for(var i = 0; i < 2; i++){
                        list.push(ry)
                    }
                    list.push(mj)
                    list.push(fk)
                    test.list = list
                    data.data.push(test)
                }*/
                data.data.forEach(function (posInfo, index, arr) {
                    var measure = qyMeasure[posInfo.qyid];
                    //console.log(measure);
                    if(measure){
                        var zsrs = data.zsrs;
                        if(zsrs){
                            $(".nowinfo").text("当前人数："+zsrs+"人");
                        }
                        drawPosAdjustRate(measure,posInfo.list,posInfo.qyid);
                    }

                })
                // initShow();
            }
        }
    });
}

function getSxtxx() {
    $.ajax({
        url: ctx + '/api/rest/chasstage/rygjMap/apiService/getSxtBybaqid',
        data:{baqid:baqid},
        dataType:'json',
        type:'POST',
        async: false,//异步true,同步false
        success:function (data) {
            console.log(data);
            // $("#map").empty();
            if(data.success&&data.data){
                //测试数据
                /*var qyArray = [
                    '202305161725156BF4D6A51D6A08A936',//信息采集室
                    '20230516172534A1DB6890E1E1081A4E',//等候室1
                    '202305161726030F2EB7A896D426EC99',//等候室2
                    '2023051617261024A1EB40B73A56705C',//等候室3
                    '2023051617261643E60DE2DF2D9BC539',//等候室4
                    '20230516172642D417C851120793BDA1',//讯/询问室1
                    '202305161726492F70A7752D8E90D95B',//讯/询问室2
                    '20230516172705649A24A03299ED407D',//远程提审室
                    '202305161727164559C49BEFF01EE5E9',//讯/询问室4
                    '20230516172727F24F3BAF0B32B855B4',//快办室
                    '20230516172747AB7FC62BD4C7984943',//看守区
                    '2023051617280144E63CA53C74C40A19',//辨认室
                    '202305161728179C84A9A226267B04D8'//尿检室
                ]
                for(var j = 0; j < qyArray.length; j++){
                    var test = {}
                    test.qyid = qyArray[j]
                    var list = []
                    var ry = {
                        "id": "R3400000000002020070005",
                        "isdel": 0,
                        "dataflag": "0",
                        "lrrSfzh": null,
                        "lrsj": null,
                        "xgrSfzh": null,
                        "xgsj": null,
                        "baqid": "57BBEB0A95954695967AE423306120DB",
                        "baqmc": "孝感派出所",
                        "ryid": null,
                        "wdbh": null,
                        "qyid": "202208161731152FECD8909294A114BF",
                        "xm": "摄像头",
                        "kssj": "2021-07-03 08:33:03",
                        "jssj": null,
                        "fzrxm": null,
                        "hdnr": null,
                        "sbid": null,
                        "qymc": "登记区",
                        "rybh": "S3400000000002020070005",
                        "bz": null,
                        "tsqt": "99",
                        "sfzh": null
                    }
                    for(var i = 0; i < 1; i++){
                        list.push(ry)
                    }
                    test.list = list
                    data.data.push(test)
                }
                console.log(2);*/
                data.data.forEach(function (posInfo, index, arr) {
                    var measure = qyMeasureBySxt[posInfo.qyid];
                    //console.log(measure);
                    if(measure){
                        drawPosAdjustRate(measure,posInfo.list,posInfo.qyid);
                    }

                })
                // initShow();
            }
        }
    });
}

//播放区域摄像头视频
function openQySxtSp(qyid) {
    $.ajax({
        url: ctx + '/api/rest/chasstage/rygjMap/apiService/getSxtByQyid',
        data:{qyid:qyid},
        dataType:'json',
        type:'POST',
        success:function (data) {
            console.log("打开视频"+qyid);
            console.log(data);
            if(data.success&&data.data){
                if(data.playType=='sxt'){
                    var sxt = data.data;
                    var vlcurl = "rtsp://"+sxt.kzcs4+"@"+sxt.kzcs2+":554/h264/ch1/main/av_stream";
                    $("#main").attr("src",vlcurl);
                    playRybh = rybh;
                    playQyid=qyid;
                    console.log(playRybh+"打开视频："+vlcurl);
                    $("#track").show();
                }else if(data.playType=='nvr'){
                    var nvr = data.data;
                    var vlcurl = "rtsp://"+nvr.kzcs3+":"+nvr.kzcs4+"@"+nvr.kzcs1+":554/h264/CH"+nvr.kzcs5+"/main/av_stream";
                    $("#main").attr("src",vlcurl);
                    playRybh = rybh;
                    playQyid=qyid;
                    console.log(playRybh+"打开视频："+vlcurl);
                    $("#track").show();
                }
            }else{
                rybh = "";
                layer.msg("获取视频地址失败,无法播放!",{offset:['85%', '42%']});
            }
        }
    });
}
//切换区域视频
function switchQySxtSp(qyid) {
    $.ajax({
        url: ctx + '/api/rest/chasstage/rygjMap/apiService/getSxtByQyid',
        data:{qyid:qyid},
        dataType:'json',
        type:'POST',
        success:function (data) {
            console.log("切换视频"+qyid);
            console.log(data);
            if(data.success&&data.data){
                if(data.playType=='sxt'){
                    var sxt = data.data;
                    //var vlcurl = "rtsp://"+sxt.kzcs4+"@"+sxt.kzcs2+":554/cam/realmonitor?channel=1&subtype=0";
                    var vlcurl = "rtsp://"+sxt.kzcs4+"@"+sxt.kzcs2+":554/h264/ch1/main/av_stream";
                    $("#main").attr("src",vlcurl);
                    playRybh = rybh;
                    playQyid=qyid;
                    console.log(playRybh+"打开视频："+vlcurl);
                    $("#track").show();
                }else if(data.playType=='nvr'){
                    var nvr = data.data;
                    //var vlcurl = "rtsp://"+nvr.kzcs3+":"+nvr.kzcs4+"@"+nvr.kzcs1+":554/cam/realmonitor?channel="+nvr.kzcs5+"&subtype=0";
                    var vlcurl = "rtsp://"+nvr.kzcs3+":"+nvr.kzcs4+"@"+nvr.kzcs1+":554/h264/CH"+nvr.kzcs5+"/main/av_stream";
                    $("#main").attr("src",vlcurl);
                    playRybh = rybh;
                    playQyid=qyid;
                    console.log(playRybh+"打开视频："+vlcurl);
                    $("#track").show();
                }
            }else{
                layer.msg("获取视频地址失败,无法播放!");
            }
        }
    });
}
//适配人员图标位置
function drawPosAdjust(ms,gjList,qyid) {
    if(!ms||!gjList||gjList.length<=0){
        return;
    }
    var obj=adjust(ms,gjList.length);
    if(obj){
        var row = obj.row;
        var col = obj.col;
        if(row&&col){
            //计算出的显示人员图标宽高
            var iW=Math.floor(ms.w/col);
            var iH=Math.floor(ms.h/row);
            //实际显示人员图标宽高
            var icSw ,icSh;
            if(iW>headIcon.width){
                icSw=headIcon.width;
            }else if(iW>headIcon.mixWidth){
                icSw=iW;
            }else{
                icSw=headIcon.mixWidth;
            }
            if(iH>headIcon.height){
                icSh=headIcon.height;
            }else if(iH>headIcon.mixHeight){
                icSh=iH;
            }else{
                icSh=headIcon.mixHeight;
            }

            for(var j=0;j<row;j++){
                for(var i=0;i<col;i++){
                    if(j*col+i<gjList.length){
                        //addRyIcon(gjList[j*col+i],ms.x+i*iW-icSw/2+iW/2,ms.y+j*iH-icSh+iH/2,icSw,icSh);
                        addRyIcon(gjList[j*col+i],ms.x+i*iW+iW/2-icSw/2+j*ms.fx,ms.y+j*iH+iH/2-icSh/2+i*ms.fy,icSw,icSh);
                    }else{
                        break;
                    }

                }
            }
        }
    }

}
//根据区域宽高比和当前区域人数计算人员图标排列行列数
function adjust(ms, size) {
    if(size==1){
        return {row:1,col:1};
    }

    var row=Math.ceil(Math.sqrt(size*ms.h/ms.w));
    var col = Math.ceil(size/row);
    return {row:row,col:col};
}

function drawPosAdjustRate(ms, gjList, qyid) {
    if (!ms || !gjList || gjList.length <= 0) {
        return;
    }
    var obj = adjust(ms, gjList.length);
    if (obj) {
        var row = obj.row;
        var col = obj.col;
        if (row && col) {
            //计算出的显示人员图标宽高
            var iW = Math.floor(ms.w / col);
            var iH = Math.floor(ms.h / row);
            //实际显示人员图标宽高
            var icSw, icSh;
            if (iW > headIcon.width) {
                icSw = headIcon.width;
            } else if (iW > headIcon.mixWidth) {
                icSw = iW;
            } else {
                icSw = headIcon.mixWidth;
            }
            if (iH > headIcon.height) {
                icSh = headIcon.height;
            } else if (iH > headIcon.mixHeight) {
                icSh = iH;
            } else {
                icSh = headIcon.mixHeight;
            }

            for (var j = 0; j < row; j++) {
                for (var i = 0; i < col; i++) {
                    if (j * col + i < gjList.length) {
                        var pos = vectorRotate({
                            x: ms.x + i * iW + iW / 2 - icSw / 2 + ms.fx * j,
                            y: ms.y + j * iH + iH / 2 - icSh / 2 + ms.fy * i
                        }, ms.oa, {x: ms.x, y: ms.y})
                        addRyIcon(gjList[j * col + i], pos.x, pos.y, icSw, icSh);
                        // if(playRybh&&playRybh==gjList[j * col + i].rybh){
                        //     if(playQyid&&playQyid!=qyid){
                        //         switchQySxtSp(qyid);
                        //     }
                        // }
                    } else {
                        break;
                    }

                }
            }
        }
    }

}

function vectorRotate(vector, angle, origin) {
    if(!origin){
        origin={x: 0, y: 0}
    }
    var cosA = Math.cos(angle);
    var sinA = Math.sin(angle);
    var x1 = (vector.x - origin.x) * cosA - (vector.y - origin.y) * sinA;
    var y1 = (vector.x - origin.x) * sinA + (vector.y - origin.y) * cosA;
    return {x: origin.x + x1, y: origin.y + y1}
}
function initShow() {
    $(".selectItem").each(function () {
        if($(this).hasClass("blueItem")){//蓝色
            if($(this).hasClass("showBlueText")){//不显示
                $(".blue").show();
            }else{
                $(".blue").hide();
            }
        }else if($(this).hasClass("yellowItem")){//黄色
            if($(this).hasClass("showYellowText")){//不显示
                $(".yellow").show();
            }else{
                $(".yellow").hide();
            }
        }else if($(this).hasClass("orangeItem")){//橙色
            if($(this).hasClass("showOrangeText")){//不显示
                $(".orange").show();
            }else{
                $(".orange").hide();
            }
        }else if($(this).hasClass("redItem")){//红色
            if($(this).hasClass("showRedText")){//不显示
                $(".red").show();
            }else{
                $(".red").hide();
            }
        }else if($(this).hasClass("mjItem")){//民警
            if($(this).hasClass("showMjText")){//不显示
                $(".mj").show();
            }else{
                $(".mj").hide();
            }
        }else if($(this).hasClass("fkItem")){//访客
            if($(this).hasClass("showFkText")){//不显示
                $(".fk").show();
            }else{
                $(".fk").hide();
            }
        }

    })
}


/*弹窗处理*/
//点击民警图标
function showMjDialog(rybh,qyid) {
    $('#mjxxStr').remove();
    $.ajax({
        url: ctx + '/api/rest/chasstage/rygjMap/apiService/getMapMjxxByRybh?rybh='+rybh,
        data:{},
        dataType:'json',
        type:'POST',
        async:false,
        success:function (data) {
            var mjrq = data.mjrq;
            var xb = data.xb;
            var zjlx = data.zjlx;
            var str = "<div id=\"mjxxStr\" class=\"dialog-pop-body dialog-mjxx-body\">\n" +
                "            <div class=\"person-info\">\n" +
                "                <div class=\"person-head\">\n" +
                "                    <img src=\""+ctx+"/static/map/style/images/mj-head.png\" alt=\"\" >\n" +
                "                </div>\n" +
                "                <div class=\"person-msg\">\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">民警姓名：</div>\n" +
                "<div class=\"person-value\">"+displayText(mjrq.mjxm)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">民警性别：</div>\n" +
                "<div class=\"person-value\">"+displayText(xb)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">证件类型：</div>\n" +
                "<div class=\"person-value\">"+displayText(zjlx)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">证件号码：</div>\n" +
                "<div class=\"person-value\">"+displayText(mjrq.mjsfzh)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">入区时间：</div>\n" +
                "<div class=\"person-value\">"+displayText(mjrq.jrsj)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">入区原因：</div>\n" +
                "<div class=\"person-value\" title=\""+displayText(mjrq.fwyy)+"\">"+displayText(mjrq.fwyy)+"</div>\n" +
                "</div>" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"operate-btns\">\n" +
                "                <div class=\"rygj-btn\" onClick=\"showGjDialog('"+rybh+"')\">人员轨迹</div>\n" +
                "                <div class=\"gjsp-btn\" onClick=\"openQySxtSp('"+qyid+"')\">轨迹视频</div>\n" +
                "            </div>\n" +
                "        </div>";
            $('#mjxxHeader').after(str);
        }
    });
    $('#mjDialog').show();
}

//关闭民警弹窗
function closeMjDialog() {
    $('#mjDialog').hide();
}

//点击访客图标
function showFkDialog(rybh,qyid) {
    $('#fkxxStr').remove();
    $.ajax({
        url: ctx + '/api/rest/chasstage/rygjMap/apiService/getMapFkxxByRybh?rybh='+rybh,
        data:{},
        dataType:'json',
        type:'POST',
        async:false,
        success:function (data) {
            var fkdj = data.fkdj;
            var xb = data.xb;
            var zjlx = data.zjlx;
            var str = "<div id=\"fkxxStr\" class=\"dialog-pop-body dialog-mjxx-body\">\n" +
                "            <div class=\"person-info\">\n" +
                "                <div class=\"person-head\">\n" +
                "                    <img src=\""+ctx+"/static/map/style/images/mj-head.png\" alt=\"\" >\n" +
                "                </div>\n" +
                "                <div class=\"person-msg\">\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">访客姓名：</div>\n" +
                "<div class=\"person-value\">"+displayText(fkdj.fkxm)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">访客性别：</div>\n" +
                "<div class=\"person-value\">"+displayText(xb)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">证件类型：</div>\n" +
                "<div class=\"person-value\">"+displayText(zjlx)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">证件号码：</div>\n" +
                "<div class=\"person-value\">"+displayText(fkdj.fksfzh)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">入区时间：</div>\n" +
                "<div class=\"person-value\">"+displayText(fkdj.jrsj)+"</div>\n" +
                "</div>\n" +
                "<div class=\"person-item\">\n" +
                "<div class=\"person-label\">入区原因：</div>\n" +
                "<div class=\"person-value\" title=\""+displayText(fkdj.fwyy)+"\">"+displayText(fkdj.fwyy)+"</div>\n" +
                "</div>" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"operate-btns\">\n" +
                "                <div class=\"rygj-btn\" onClick=\"showGjDialog('"+rybh+"')\">人员轨迹</div>\n" +
                "                <div class=\"gjsp-btn\" onClick=\"openQySxtSp('"+qyid+"')\">轨迹视频</div>\n" +
                "            </div>\n" +
                "        </div>";
            $('#fkxxHeader').after(str);
        }
    });
    $('#fkDialog').show();
}

//关闭访客弹窗
function closeFkDialog() {
    $('#fkDialog').hide();
}

//点击摄像头弹窗
function showSxtDialog() {
    $('#sxtDialog').show();
}

//关闭摄像头弹窗
function closeSxtDialog() {
    $('#sxtDialog').hide();
}

//点击摄像头弹窗
function showGjDialog(rybh) {
    $("#gjmapStr").remove();
    var str = "<div id='gjmapStr' class=\"dialog-pop-body dialog-sxt-body\">\n" +
        "            <!--摄像头播放代码-->\n" +
        "            <iframe style=\"width: 100%;height: 100%;\" src=\""+ctx+"/api/rest/chasstage/rygjMap/apiService/goGjxlMap?qy=xydzx&name=gjxlMap&rybh="+rybh+"\"></iframe>\n" +
        "        </div>";
    $("#gjmapHeader").after(str);
    $('#gjDialog').show();
}

//关闭摄像头弹窗
function closeGjDialog() {
    $('#gjDialog').hide();
}

//点击嫌疑人弹窗
function showXyrDialog(rybh,qyid) {
    $("#xyrxx").remove();
    $.ajax({
        url: ctx + '/api/rest/chasstage/rygjMap/apiService/getMapRyxxByRybh?rybh='+rybh,
        data:{},
        dataType:'json',
        type:'POST',
        async:false,
        success:function (data) {
            var ryxx = data.ryxx;
            var xb = data.xb;
            var zjlx = data.zjlx;
            var sswps = data.sswps;
            var chasythcjQk = data.chasythcjQk;
            var str = "<div id=\"xyrxx\" class=\"dialog-tab-content\">\n" +
                "                <div class=\"jbxx-content\" style=\"display: block\">\n" +
                "                    <div class=\"person-info\">\n" +
                "                        <div class=\"person-head\">\n" +
                "                            <img src=\""+ctx+"/static/map/style/images/xyr-head.png\" alt=\"\">\n" +
                "                            <div class=\"person-status\">在区</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"person-msg\">\n" +
                "                            <div class=\"person-item\">\n" +
                "                                <div class=\"person-label\">人员姓名：</div>\n" +
                "                                <div class=\"person-value\">"+displayText(ryxx.ryxm)+"</div>\n" +
                "                            </div>\n" +
                "                            <div class=\"person-item\">\n" +
                "                                <div class=\"person-label\">人员性别：</div>\n" +
                "                                <div class=\"person-value\">"+displayText(xb)+"</div>\n" +
                "                            </div>\n" +
                "                            <div class=\"person-item\">\n" +
                "                                <div class=\"person-label\">证件类型：</div>\n" +
                "                                <div class=\"person-value\">"+displayText(zjlx)+"</div>\n" +
                "                            </div>\n" +
                "                            <div class=\"person-item\">\n" +
                "                                <div class=\"person-label\">证件号码：</div>\n" +
                "                                <div class=\"person-value\">"+displayText(ryxx.rysfzh)+"</div>\n" +
                "                            </div>\n" +
                "                            <div class=\"person-item\">\n" +
                "                                <div class=\"person-label\">户籍所在地：</div>\n" +
                "                                <div class=\"person-value\">"+displayText(ryxx.hjdxzqh)+"</div>\n" +
                "                            </div>\n" +
                "                            <div class=\"person-item\">\n" +
                "                                <div class=\"person-label\">入区时间：</div>\n" +
                "                                <div class=\"person-value\">"+displayText(ryxx.rrssj)+"</div>\n" +
                "                            </div>\n" +
                "                            <div class=\"person-item\">\n" +
                "                                <div class=\"person-label\">主办民警：</div>\n" +
                "                                <div class=\"person-value\">"+displayText(ryxx.mjXm)+"</div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"operate-btns\">\n" +
                "                        <div class=\"rygj-btn\" onClick=\"showGjDialog('"+rybh+"')\">人员轨迹</div>\n" +
                "                        <div class=\"gjsp-btn\" onClick=\"openQySxtSp('"+qyid+"')\">轨迹视频</div>\n" +
                "                        <div class=\"sxjl-btn\" onClick=\"showSxjlDialog('"+rybh+"')\">审讯记录</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"sswp-content\" style=\"display: none\">\n" +
                "                    <table width=\"100%\">\n" +
                "                        <tr>\n" +
                "                            <th width=\"15%\">序号</th>\n" +
                "                            <th width=\"25%\">物品名称</th>\n" +
                "                            <th width=\"25%\">物品类型</th>\n" +
                "                            <th width=\"35%\">存放位置</th>\n" +
                "                        </tr>\n" ;
            if(sswps != undefined || sswps!= null){
                for (let i = 0; i < sswps.length; i++) {
                    str +="                        <tr>\n" +
                        "                            <td>"+i+1+"</td>\n" +
                        "                            <td>"+sswps[i].mc+"</td>\n" +
                        "                            <td>"+sswps[i].lbName+"</td>\n" +
                        "                            <td>"+sswps[i].cfwzName+"</td>\n" +
                        "                        </tr>\n";
                }
            }
            str +="                    </table>\n" +
                "                </div>\n" +
                "                <div class=\"aqjc-content\" style=\"display: none\">\n" +
                "                    <div class=\"aqjc-item\">\n" +
                "                        <div class=\"aqjc-label\">人身安全检查时间： </div>\n" +
                "                        <div class=\"aqjc-value\">\n" +
                displayText(ryxx.aqjckssj)+"<span>-</span>"+displayText(ryxx.aqjcjssj)+"\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"aqjc-item\">\n" +
                "                        <div class=\"aqjc-label\">自诉有无疾病、伤情及受伤原因：</div>\n" +
                "                        <div class=\"aqjc-value\">\n" +
                displayText(ryxx.rYzjb) +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"aqjc-item\">\n" +
                "                        <div class=\"aqjc-label\">检查情况：</div>\n" +
                "                        <div class=\"aqjc-value\">\n" +
                "                            <div>"+jcqkText(ryxx.rSfyzjb)+"病史情况</div>\n" +
                "                            <div>"+jcqkText(ryxx.rSfssjc)+"伤情情况</div>\n" +
                "                            <div>"+jcqkText(ryxx.rSfyj)+"饮酒情况</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"cjxx-content\" style=\"display: none\">\n" +
                "                    <div class=\"bzhxx-detail\">\n" +
                "                        <div class=\"cjxx-title\">标准化采集</div>\n" +
                "                        <div class=\"cjxx-item\">\n" +
                "                            <div class=\"cjxx-label\">指纹采集：</div>\n" +
                "                            <div class=\"cjxx-value\">"+cjDic1(chasythcjQk != undefined?chasythcjQk.zwcj : '9')+"</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"cjxx-item\">\n" +
                "                            <div class=\"cjxx-label\">DNA采集：</div>\n" +
                "                            <div class=\"cjxx-value\">"+cjDic1(chasythcjQk != undefined?chasythcjQk.dnacj:'9')+"</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"bzhxx-detail\">\n" +
                "                        <div class=\"cjxx-title\">涉毒采集</div>\n" +
                "                        <div class=\"cjxx-item\">\n" +
                "                            <div class=\"cjxx-label\">毛发检测：</div>\n" +
                "                            <div class=\"cjxx-value\">"+cjDic2(chasythcjQk!=undefined?chasythcjQk.mfcj:'9')+"</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"cjxx-item\">\n" +
                "                            <div class=\"cjxx-label\">尿液检测：</div>\n" +
                "                            <div class=\"cjxx-value\">"+cjDic2(chasythcjQk!=undefined?chasythcjQk.nyjc:'9')+"</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>";
            $('#xyrTab').after(str);
        }
    });
    $('#xyrDialog').show();
}

//关闭嫌疑人弹窗
function closeXyrDialog() {
    $('#xyrDialog').hide();
}

//点击审讯记录弹窗
function showSxjlDialog(rybh) {
    grid.reLoad({"rybh":rybh});
    $('#sxjlDialog').show();
}

//关闭审讯记录弹窗
function closeSxjlDialog() {
    $('#sxjlDialog').hide();
}

function displayText(value) {
    if(value == null||value == undefined){
        return '';
    }else{
        return value;
    }
}
function jcqkText(value) {
    if(value == '1'){
        return '确认有';
    }else {
        return '确认无';
    }
}

function cjDic1(value) {
    if(value == '1'){
        return '已采集';
    }else{
        return '未采集';
    }
}

function cjDic2(value) {
    if(value == '1'){
        return '阴性';
    }else if(value == '2'){
        return '阳性';
    }else{
        return '未采集';
    }
}