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
    var ryxm=ryxx.xm.substr(0,1)+"*";
    var lx = ryxx.rybh.substr(0,1);
    var picName='/static/chas/bigscreen/bjfj/images/red.png';
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

            '                    <img width="100%" height="100%" src="'+ctx+picName+'" alt="">\n' +
            '                </div>');
        // $("#map").append(name);
        $("#map").append(icon);
    }else if("M"==lx) {
        picName='/static/chas/bigscreen/bjfj/images/mj.png';
        var name =$('<div class="plname mj" style="left:'+x+'px;top:'+name_top+'px;">'+ryxm+'</div>');
        var icon=$('<div class="icon mj" rybh="'+ryxx.rybh+'" ryxm="'+ryxx.xm+'" qymc="'+ryxx.qymc+'" qyid="'+ryxx.qyid+'" style="position: absolute; left: '+x+'px;top: '+y+'px; width: '+iw+'px; height: '+ih+'px;">\n' +

            '                    <img width="100%" height="100%" src="'+ctx+picName+'" alt="">\n' +
            '                </div>');
        // $("#map").append(name);
        $("#map").append(icon);
    }else if("F"==lx){
        picName='/static/chas/bigscreen/bjfj/images/fk.png';
        var name =$('<div class="plname fk" style="left:'+x+'px;top:'+name_top+'px;">'+ryxm+'</div>');
        var icon=$('<div class="icon fk" rybh="'+ryxx.rybh+'" ryxm="'+ryxx.xm+'" qymc="'+ryxx.qymc+'" qyid="'+ryxx.qyid+'" style="position: absolute; left: '+x+'px;top: '+y+'px; width: '+iw+'px; height: '+ih+'px;">\n' +

            '                    <img width="100%" height="100%" src="'+ctx+picName+'" alt="">\n' +
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
            if(data.success&&data.data){
                //测试数据
                /*var qyArray = [
                    '20230524110957A182224484112A6F58',//等候室1
                    '202305241111009C03062E9846E85148',//等候室2
                    '20230524111109278CEF282AB673174F',//等候室3
                    '20230524111117EED7467DA24830C426',//等候室4
                    '20230524111124B94A6600CC567037AD',//等候室5
                    '202305241111312982088E7F1546CD54',//醒酒区
                    '20230524111138F9A82D8AA047677DFC',//等候室6
                    '20230524111146A577BB9ECEF0CBF48F',//等候室7
                    '202305241111536CDA649505F9A948CB',//等候室8
                    '20230524111201B4EA4D38A33B65022E',//等候室9
                    '20230524111214787745FC84877E60BC',//等候室10
                    '202305241112313BB440CA5924E15D79',//等候室11
                    '2023052411192915809C399D0442BBEA',//讯/询问室1
                    '20230524112124E1A27CC8E31268F680',//讯/询问室2
                    '2023052411214549C1415BF1551D63B2',//讯/询问室3
                    '2023052411215757FBB14F4B14312EAC',//讯/询问室4
                    '2023052411220709AF9DC9DCAF71FE8E',//讯/询问室5
                    '202305241122184178EFBE848EEFA18C',//讯/询问室6
                    '20230524112227629697F48E73EC93F7',//讯/询问室7
                    '20230524112240940B4D5F35FDD0C135',//讯/询问室8
                    '202305241124058F6139D8D2F2978F52',//讯/询问室9
                    '20230524112433D1B0930140116064EC',//特殊讯/询问室
                    '20230524112448E3BF19D954D3985482',//未成年讯/询问室
                    '202305241244160D4A3A188740DFDDEB',//押解区
                    '202305241245155B969CF28137B246D4',//登记区
                    '20230524124635C2E9E347D3022B6F0A',//信息采集区
                    '202305241248338DFA968FE00A4DB60B',//取物区
                    '20230524124918FEA546BAD8D0A12099',//走廊1
                    '2023052412493229A734C7AF869FECEC',//询问室4门口走廊
                    '20230524124939B5A43910BFAFC5743C',//走廊2
                    '202305241251075AB31A0E9086814E9D',//走廊1
                    '2023052412520506FED38BBD607146A8',//看守区
                    '202306131440530FF1F15D8EB90134A1',//登记区
                    '2023061315323758D4A1BE8D59B87423',//律师会见室
                    '20230613153410E1CAB314FD766CD002',//醒酒室
                    '20230613154912A63D5FCEAA4BE565A0',//尿检区卫生间
                    '20230613155405B6767B2D4B3480B48C',//卫生间
                    '20230618152452A2B8394430ACDEAB7F'//尿检区
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
                        "baqid": "092378358AD8407F9760074FE1CE0E6F",
                        "baqmc": "西湖分局",
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
                    for(var i = 0; i < 4; i++){
                        list.push(ry)
                    }
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
                initShow();
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