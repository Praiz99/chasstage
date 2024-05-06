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
                    '20221115154101439DC62723EDBB74B4',//办案区出入口
                    '20221115154219E0CD7C2B5E95486CA0',//待检区
                    '2022111515425966298BA01A2EEB49F3',//信息采集室
                    '20221115154551E01040A361A97C14C7',//看守区
                    '20221115154716E3353ED89E0FFF033C',//等候室1
                    '2022111515473707CE8A3EAF73B5354F',//等候室2
                    '2022111515475738579F5800F5B8A534',//等候室3
                    '202211151548177E9E3E0CBF4A203AFF',//等候室4
                    '202211151549078E7291B83A1E44B336',//等候室5
                    '20221115154934F0DFF1CDC229144061',//等候室6
                    '20221115154955C66E84D1E554D445B3',//等候室7
                    '20221115155017698B1FC4F7C4FFECE6',//等候室8
                    '20221115155033C30ECAB489A1179FE4',//等候室9
                    '2022111515505349414A8DE9B0A56705',//等候室10
                    '20221115155117BB7E474499675B7CEF',//等候室11
                    '20221115155134D0895A91F1260FAB45',//等候室12
                    '20221115161347B33486EE380FA6C068',//等候室13
                    '20221115161414F8A244DC63E7A3C98A',//等候室14
                    '20221115162812BEDCFCBA0A4BD04DE2',//等候室15
                    '20221115162838AA97B10A7065949B3C',//等候室16
                    '202211151628579460C99DAECB38A287',//等候室17
                    '2022111516295811D1D064FEB8C76E22',//尿检区
                    '20221115163045A3ADD4358E23F74B92',//尿检厕所
                    '20221115163241B152E4BD1841AD6281',//取物走廊
                    '2022111516350257CD068A4BA6A593EB',//讯/询问室1
                    '202211151635538449E602E61DEC5B90',//讯/询问室2
                    '20221115163643249F4C90B7B980B0D5',//讯/询问室3
                    '20221115163710430A6BCDB4C840ADA4',//讯/询问室4
                    '202211151637288D47B9C451FA10946B',//讯/询问室5
                    '2022111516375737AA5FE905044CB993',//讯/询问室6
                    '202211151638163F64B3C87B92BE455E',//讯/询问室7
                    '202211151638396C0288A4317A043C03',//讯/询问室8
                    '2022111516390028BFA77A9F1B38B485',//讯/询问室9
                    '20221115163919319E828150F4904E15',//讯/询问室10
                    '2022111516394104AD954AB62E7AD77C',//讯/询问室11
                    '20221115164034483B3CBEA44C50D7A0',//讯/询问室12
                    '20221115164103634D5AAA6D8789DA63',//讯/询问室13
                    '202211151641484661BBA0DFE9776702',//讯/询问室14
                    '202211151648584A49F40706BF491B35',//辨认室及监护人室
                    '20221115165029852A1520FB68C3B7B0',//特殊审讯室
                    '202211160100352B1A1BCFAC398CB211',//律师会见室
                    '202211161658405E8B6B5774243E5C97',//医疗救护室
                    '20221116170044DFF65AF96426411D59',//审讯区卫生间走廊
                    '20221116170144C985FDBC4D1A8F6614',//审讯区男厕
                    '202211161702071441486B62A52E010B',//审讯区女厕
                    '20221116170343544108961C32E40A68',//询问室出入口
                    '2022111617050564EA302C248E999DA9',//审讯室11门口走廊
                    '202211161707159002ACBC400BDA2207',//审讯室5门口走廊
                    '20221126231228053D82AB3AB134273A'//律师会见室门口走廊
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