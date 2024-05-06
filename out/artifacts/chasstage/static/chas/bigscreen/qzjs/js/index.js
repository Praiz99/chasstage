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
        //var icon=$('<div class="icon xyr '+clazz+'"  rybh="'+ryxx.rybh+'" ryxm="'+ryxx.xm+'" qymc="'+ryxx.qymc+'" qyid="'+ryxx.qyid+'" style="position: absolute; left: '+x+'px;top: '+y+'px; width: '+iw+'px; height: '+ih+'px;">\n' +
        var icon=$('<div class="icon xyr '+clazz+'"  xbName="'+ryxx.xbName+'" zjlxName="'+ryxx.zjlxName+'" rySfzh="'+ryxx.rySfzh+'" csrq="'+ryxx.csrq.substr(0,10)+'" hjdXzqh="'+ryxx.hjdXzqh+'" wdbh="'+ryxx.wdbh+'" rrssj="'+ryxx.rrssj+'" ryZaybhName="'+ryxx.ryZaybhName+'" dafsName="'+ryxx.dafsName+'" dafsName="'+ryxx.dafsName+'"  ryRylxName="'+ryxx.ryRylxName+'" sftsqt="'+ryxx.sftsqt+'" ryxm="'+ryxx.xm+'" qymc="'+ryxx.qymc+'" qyid="'+ryxx.qyid+'" zpurl="'+ryxx.zpurl+'" style="position: absolute; left: '+x+'px;top: '+y+'px; width: '+iw+'px; height: '+ih+'px;">\n' +

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
                // var qyArray = [
                //     "202304061313044EC4F7F0F5FBD2581E",//登记大厅
                //     "202304061314231EB125DEDD4B039025",//信息采集室
                //     "20230406131523FDC14AC5E8431EF57F",//看守区
                //     "20230406131600554D82E50664CD806A",//等候室1
                //     "20230406131639BAC6AD5864044440E6",//等候室2
                //     "20230406131702F73F1D0F71B441FA05",//等候室3
                //     "20230406131721BACEE4A38CB387339E",//等候室4
                //     "20230406131748758B9E7142841C160A",//等候室5
                //     "20230406131810C995B058F56468B54C",//等候室6
                //     "2023040613183778196164C4384BE409",//等候室7
                //     "202304061319016CA2B9B31C688B4511",//等候室8
                //     "20230406132048D8032C01854360F294",//嫌疑人卫生间1
                //     "202304061322448DA8E680D45B9BFAEE",//尿检区
                //     "202304061323485F089758564FBA299C",//嫌疑人卫生间2
                //     "2023040613260491131E8D69E64D674E",//审讯室走廊1
                //     "20230406132650FC539BFF94F7684F8B",//取物区
                //     "202304061330307347984C0DD63D6EBC",//讯/询问室1
                //     "2023040613305881A3E86E6370171F41",//讯/询问室2
                //     "202304061331247D37E58C574F63C593",//讯/询问室3
                //     "20230406133148996590D5B744D2FB5C",//讯/询问室4
                //     "20230406133214F6A8E6846B850DB460",//讯/询问室5
                //     "2023040613330100B865EA24CA4D3935",//讯/询问室6
                //     "20230406133323D1A443AC8A85B59ED4",//讯/询问室7
                //     "20230406133348888878107C248BEE20",//讯/询问室8
                //     "20230406133412FEFB85852A4385B73A",//讯/询问室9
                //     "2023040613343724E80B3533DC444153",//讯/询问室10
                //     "202304061335228D986E90992AF504A3",//未成年讯/询问室
                //     "20230406133600F53D384B4ADC87D848",//特殊讯/询问室
                //     "202304061336410C13AF24C4B0B0CE7D",//办案区入口
                //     "202304061337235F58548431347577D7",//办案区出口
                //     "202304061727478A49416B44611EF138",//讯/询问室11
                //     "20230406172822F49E5EB7AEE0254995"//讯/询问室12
                // ]
                // for(var j = 0; j < qyArray.length; j++){
                //     var test = {}
                //     test.qyid = qyArray[j]
                //     var list = []
                //     var ry = {
                //         "id": "R3400000000002020070005",
                //         "isdel": 0,
                //         "dataflag": "0",
                //         "lrrSfzh": null,
                //         "lrsj": null,
                //         "xgrSfzh": null,
                //         "xgsj": null,
                //         "baqid": "20230317162350B8989EC40F4E6BA0DA",
                //         "baqmc": "宁海办案区",
                //         "ryid": null,
                //         "wdbh": null,
                //         "qyid": "20220826124854467BB04F0078A600B0",
                //         "xm": "冯满艺",
                //         "kssj": "2021-07-03 08:33:03",
                //         "jssj": null,
                //         "fzrxm": null,
                //         "hdnr": null,
                //         "sbid": null,
                //         "qymc": "登记区",
                //         "rybh": "R3400000000002020070005",
                //         "bz": null,
                //         "tsqt": "99",
                //         "sfzh": null,
                //         "zpid":"",
                //         "xbName":"男",
                //         "zjlxName":"身份证",
                //         "rySfzh":"330326199905173232",
                //         "csrq":"2018-02-02 11:11:11",
                //         "hjdXzqh":"杭州",
                //         "wdbh":"1111",
                //         "rrssj":"2018-02-02 11:11:11",
                //         "ryZaybhName":"啊啊啊",
                //         "dafsName":"饮酒",
                //         "rylxName":"嫌疑人",
                //         "sftsqt":"true",
                //         "zpurl":"https://img0.baidu.com/it/u=1545524896,2572136233&fm=253&fmt=auto&app=120&f=JPEG?w=517&h=290",
                //     }
                //     for(var i = 0; i < 1; i++){
                //         list.push(ry)
                //     }
                //     test.list = list
                //     data.data.push(test)
                // }
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