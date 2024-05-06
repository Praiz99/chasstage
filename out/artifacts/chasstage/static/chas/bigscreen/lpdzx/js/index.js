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
                    '20230109083425DB807487A5D17B3C2A',//信息登记采集区
                    '20230509100444F82116F3B9ADCE045D',//办案区入口
                    '20230509100500537C6394C50FBDF031',//办案区出口
                    '20230509100559E4DC485E404B5C0B7A',//人身检查室1
                    '20230509100608B8EE99D39384AF8E4C',//人身检查室2
                    '20230509100630AB4F7DA41FCCD71FD9',//声纹采集室
                    '2023050910065250BF7E5131BC2B7F46',//毒品称重室
                    '20230509100747165B7ED63CE2B14A83',//出所通道
                    '2023050910081144113C59C3D8F1255F',//看守区
                    '202305091008302349B485D949CA1445',//等候室1
                    '202305091008379D1FB28148FDDD58E7',//等候室2
                    '20230509100847CAF63D8E43E837E481',//等候室3
                    '202305091008576565A6EFC44E916267',//等候室4
                    '2023050910090717B846476A6FC3A7B0',//等候室5
                    '20230509100915B3A64F0DECE64D6E4E',//等候室6
                    '202305091009235051502B8AEEA5046C',//等候室7
                    '202305091009373B644D8FC72A3E0FAA',//等候室8
                    '202305091009461056C944664B91DEB3',//等候室9
                    '202305091010440D95D88C8140EB8A43',//嫌疑人卫生间
                    '20230509101059F113CDB4434A3AFEAE',//尿检区
                    '20230509101115B9023A8E9897D3144D',//醒酒区
                    '202305091011383FB52088664A8B9128',//审讯室通道1
                    '202305091011571A9B7826FAB95AC404',//审讯室通道2
                    '202305091012313B78E5A10253A49FB6',//讯/询问室1
                    '20230509101243ED9ADC6703AF83C150',//讯/询问室2
                    '20230509101254963CB55223A3F01948',//讯/询问室3
                    '202305091013038F33AFF0431BA61655',//讯/询问室4
                    '20230509101314A0B84C4144A04FCB25',//讯/询问室5
                    '20230509101323990D1A75C73F8B3DA4',//讯/询问室6
                    '20230509101332FF0FC45D0224B47F18',//讯/询问室7
                    '2023050910134288D1CA41BB99DCA19D',//讯/询问室8
                    '2023050910135294A6319998F01B80A9',//讯/询问室10
                    '20230509101458A17CAC05DB43BB7759',//特殊审讯室11
                    '202305091015190F3B7B46153F954B42',//讯/询问室12
                    '202305091015304A155B1B48B47CDB05',//讯/询问室13
                    '20230509101542DB5B8C6FD28C705479',//讯/询问室14
                    '20230509101553CE24149F95AC47E472',//讯/询问室15
                    '2023050910160359F80B19527DC244E2',//讯/询问室16
                    '20230509101628636158C47214670D88',//未成年询讯问室9
                    '20230509101810AE8D93240B85C4FDC7',//速裁区通道
                    '20230509101831431702F2ECB90561AE',//速裁等候区1
                    '2023050910183981F6964A227A84C466',//速裁等候区2
                    '2023050910190414150296BAC65288B1',//速裁法庭
                    '20230509101947AB3AF7D113280B0B0C',//辨认室/律师会见室
                    '20230509102120426A3193D3383A8CF2',//五项检查室
                    '20230509102134841455802633C646C6',//五项检查室通道
                    '20230509102152BBE599BB6131EFE58A',//X光室
                    '20230509102212A2EF5509C4EF884CFC',//民警通道
                    '20230509163617353665F44A108D46F7'//审讯指挥室
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
                    for(var i = 0; i < 1; i++){
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