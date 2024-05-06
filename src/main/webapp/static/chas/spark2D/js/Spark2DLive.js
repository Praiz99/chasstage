let player;
//记录当前播放地址
let playerUrl;
let Spark2DLive = function (spark2D,pointInfos){
    this.spark2D = spark2D;
    //区域办公的点位信息,类型统一为PointInfos
    this.pointInfos = pointInfos;
    //最短路径算法
    this.dijk = new DijkStra(this.pointInfos.vArray.length,this.pointInfos.eArray);
    //记录点位的历史信息
    this.recordAvatarTime = {};
    this.websocket = null;

    this.webSocketInit();
}

Spark2DLive.prototype.webSocketInit = function (){
    let _this = this;
    let websocket = this.websocket;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        $.ajax({
            url:ctx+'/spark2D/get/server/info',
            post:'get',
            dataType:'json',
            async: false,
            success(result){
                console.debug('获取服务器ip和端口号',result);
                if(result.code == '200'){
                    websocket = new WebSocket('ws://'+result.data.url+':'+result.data.port+'/vms/websocket/spark2D/live');
                }
            }
        })
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    $('.tabs-close',window.parent.document).on('click',function(){
        websocket.close();
    });

    //连接发生错误的回调方法
    websocket.onerror = function () {
        //setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        //setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        //setMessageInnerHTML(event.data);
        console.log(event.data);
        let result = JSON.parse(event.data);
        if(result.message == "pointInfo"){
            //更新点击人物的摄像头信息
            let data = result.data;
            data.onClick = function () {
                _this.openDialog(data.areaNo,data.vFrags,data.org);
            }
            _this.movePeople(data);
        }
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        //setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        websocket.close();
    }
}

Spark2DLive.prototype.loadRecentPeople = function (){
    let _this = this;
    let spark2D = _this.spark2D;
    //获取最近24小时的人员
    $.ajax({
        url:ctx+'/spark2D/find/people/recent',
        type:'get',
        dataType:'json',
        data:{orgCode:_this.pointInfos.orgCode},
        success:function(result){
            let list = result.data.list;
            //头像重叠,默认每行三个人
            let rooms = {};
            for(let i in list){
                let d = list[i];
                let point = _this.pointInfos.getPoint(d.areaNo);
                let nPoint = {};
                if(point != - 1){
                    let room = rooms[d.areaNo];
                    if(room){
                        let n = room.length;
                        //余1和2还有位置，余0就是满了需要加一排
                        nPoint.x = point.x-8 +(n%3)*16;
                        nPoint.y = point.y-10+(Math.floor(n/3))*20;
                        //console.debug('point.x-8 +(n%3)*16',point.x-8 +(n%3)*16,'point.y-10+(Math.floor(n/3))*20',point.y-10+(Math.floor(n/3))*20);
                        room.push(d.peopleNo);
                    }
                    else{
                        rooms[d.areaNo] = [];
                        rooms[d.areaNo].push(d.peopleNo);
                        nPoint.x = point.x - 8;
                        nPoint.y = point.y - 10;
                    }
                    // if(d.areaNo == 'A_X_5'){
                    // 	console.debug('n/3:',Math.floor(rooms[d.areaNo].length/3),'n%3',rooms[d.areaNo].length%3);
                    // 	console.debug(nPoint);
                    // }
                    let vFrags = JSON.parse(d.vFrags);
                    console.debug(vFrags[0]);
                    let avatar2D=new Avatar2D({
                        id:d.peopleName,
                        start:nPoint,
                        size:[16/2,20/2],
                        kenType:""+d.pType+d.sex,
                        trackColor:'#cdf5f8',
                        onClick:function(){
                            _this.openDialog(d.areaNo,vFrags[0],d.org);
                        }
                    });
                    spark2D.addAvatar(avatar2D);
                }
            }
        }


    })
}

Spark2DLive.prototype.openDialog = function (liveName,nvrParams,org){
    /* 打开播放窗口 */
    let ckDia = $('#ck-dialog');
    if(!ckDia[0]){
        $('body').append('<div id="ck-dialog" display="none"></div>');
        ckDia = $('#ck-dialog');
        ckDia.dialog({
            title: '播放',
            width: '90%',
            height: '90%',
            closed: true,
            collapsible:true,
            onClose:function(){
                //判断是否应该关闭直播流
                if(playerUrl){
                    $.ajax({
                        url:'close/live',
                        dataType:'json',
                        data:{
                            liveName:liveName,
                            liveId:playerUrl
                        },
                        success:function(result){
                            //在线人数小于1关闭直播流
                            console.debug("在线人数为",result.data.online);
                            if(result.data.online <= 1){
                                console.debug("关闭直播流");
                                $.ajax({
                                    url:ctx+'/api/ext/cam/live/stop',
                                    dataType:'json',
                                    data:{
                                        liveName:liveName
                                    },
                                    async:false,
                                    success:function(result){
                                        if(result.code == '200'){
                                            $.messager.alert('系统提示','停止直播流:'+result.message,'info');
                                        }
                                        else{
                                            $.messager.alert('系统提示',result.message,'error');
                                        }
                                    }


                                })
                            }
                            //置空
                            playerUrl = null;
                        }


                    })
                }
            },
            content: '<div id="ck-container"></div>'
        })
    }
    //console.log(ckDia.dialog('options'));
    //如果是最小化状态则还原
    if(ckDia.dialog('options').minimized){
        ckDia.dialog('open');
        return;
    }
    $.ajax({
        url:ctx+'/api/ext/cam/live/start',
        dataType:'json',
        beforeSend: function () {
            $.messager.progress({'msg':'正在启动直播流','interval':1000});
        },
        complete: function () {
            $.messager.progress('close');
        },
        data:{	org:org,
            liveName:liveName,
            nvrParams:JSON.stringify(nvrParams)
        },
        success:function(result){
            if(result.code == '200'){
                var videoObject = {
                    container: '#ck-container',//“#”代表容器的ID，“.”或“”代表容器的class
                    variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象
                    autoplay:true,//是否自动播放
                    live:true,//是否是直播视频
                    video:result.url//视频地址

                };
                player=new ckplayer(videoObject);
                ckDia.dialog('open');
                playerUrl = result.rsId;
            }
            else{
                $.messager.alert('系统提示',result.message,'error');
            }
        }


    })

}

//moveData包括的信息应该有  peopleName，areaNo，enterTime(leaveTime),onClick方法
Spark2DLive.prototype.movePeople = function(moveData){
    let areaNo = moveData.areaNo;
    let peopleName= moveData.peopleName;
    let enterTime = moveData.enterTime;
    let leaveTime = moveData.leaveTime;
    let status = moveData.status;
    let people = this.spark2D.getAvatar(peopleName);
    let sex = moveData.sex;
    let pType = moveData.pType;
    //离开消息  1:已离开
    if(status && status == 1){
        this.spark2D.removeAvatar(people.id);
        this.recordAvatarTime[peopleName] = {};
        return;
    }
    //获取areaNo对应的坐标信息
    let pointInfo = this.pointInfos.getPoint(areaNo);
    if(pointInfo == -1){
        console.error('没有查询到该areaNo：'+areaNo+'对应的点信息');
        return;
    }
    //console.log('people的id为'+people);
    if(!people){
        people =new Avatar2D({
            id:peopleName.substr(0,1),
            start:pointInfo,
            size:[16/2,20/2],
            isDrawTrack:true,
            kenType:pType+""+sex,
            trackColor:'#'+Math.floor(Math.random()*16777215).toString(16),
        });
        this.spark2D.addAvatar(people);
    }
    //更新点击人物的摄像头信息
    if(moveData.onClick){
        people.ken.off('click').on('click',function(){
            moveData.onClick();
        });
    }

    if(people){
        //上次的点的信息
        let lastPoint = this.recordAvatarTime[peopleName];
        //先缓存启动好live直播流
        $.ajax({
            url:ctx+'/api/ext/cam/live/start',
            dataType:'json',
            data:{	org:moveData.org,
                liveName:moveData.areaNo,
                nvrParams:JSON.stringify(moveData.vFrags)
            },
            success:function(result){
                if(result.code == '200'){
                    //判断是否进入的还是上次的点位,更换摄像头播放地址
                    console.debug('系统提示:','更换当前播放的摄像头地址');
                    if(player
                        && lastPoint
                        && lastPoint.pointInfo.areaNo != pointInfo.areaNo){
                        player.newVideo({video:result.url});
                    }
                }
                else{
                    console.error('系统提示:',result.message);
                }
            }
        })

        //接收的是进入areaNo的信息
        if(enterTime){
            enterTime = new Date(enterTime);
            //console.log('1.设置时间enterTime:'+moveData.enterTime);
            //console.log('2.获取上次的区域信息:--------');
            //console.log(lastAreaNo);
            //这里要减去记录的上次进入时间，如果上次记录不存在或者离开时间没有记录，说明还没有接收到离开的消息
            if(lastPoint && lastPoint.leaveTime){
                let paths = this.dijk.dijk(lastPoint.pointInfo.index,pointInfo.index);
                console.debug(paths);
                this.roomsDec(areaNo, people,peopleName);
                for(let i in paths){
                    let p = this.pointInfos.vArray[paths[i]];
                    people.addPoint({x:p.x,y:p.y,time:2});
                }
                this.roomsAdd(areaNo, people, peopleName);
                //重新记录进入该区域的时间
                this.recordAvatarTime[peopleName] = {enterTime:enterTime,pointInfo:pointInfo};
            }
            //发送的第一个点
            else{
                //记录进入区域的信息
                console.debug('10.设置时间enterTime:'+enterTime);
                this.recordAvatarTime[peopleName] = {enterTime:enterTime,pointInfo: pointInfo};
            }

        }
        //接收的是离开信息
        else if(leaveTime){
            this.recordAvatarTime[peopleName] = {leaveTime:new Date(leaveTime),pointInfo: pointInfo};
        }
    }
}

//房间减少一人
Spark2DLive.prototype.roomsDec = function (avatar,areaNo,peopleName){
    let room = this.pointInfos.rooms[areaNo];
    let toPoint = this.pointInfos.getPoint(areaNo);
    if(room){
        let addr = room.addr;
        for(let i in addr){
            if(addr[i] == peopleName){
                addr[i] = -1;
                avatar.addPoint({time:2,x:toPoint.x,y:toPoint.y});
                break;
            }
        }
    }
}

//房间增加一人
Spark2DLive.prototype.roomsAdd = function (avatar,areaNo,peopleName){
    let room = this.pointInfos.rooms[areaNo];
    let flag = false;
    if(room){
        //找座位，空座位是-1
        let addr = room.addr;
        for(let i =0;i<10;i++){
            for(let j = 0;j<4;j++){
                let r = addr[i+'_'+j];
                if(!r || r == -1){
                    addr[i+'_'+j] = peopleName;
                    avatar.addPoint({time:2,x:room.x+10*j+5,y:room.y+10*i+5});
                    flag = true;
                    break;
                }
            }
            if(flag)break;
        }
    }
}