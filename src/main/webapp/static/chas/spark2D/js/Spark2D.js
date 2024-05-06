/*
2.5D scene
*/
let Spark2D=function(options)
{
    this.id=options.id || "stage"
    this.onVertexClick=options.onVertexClick;
    this.width=options.width || 100;
    this.height=options.height || 100;
    this.margin=options.margin || "0 0";
    this.top = options.top || 0;
    this.avatarArray=[];
    this.pan=options.pan ||false;
    this.pointInfos = options.pointInfos || {};
    this.vertexArray = options.vertexArray || this.pointInfos.vArray || [];
    this.backgroundImage = options.backgroundImage;

    this.followedAvatar=null;

    let _this=this;
    if(!$('#'+this.id)[0]){
        console.error('id对应的div不存在。');
        return;
    }
    //初始化场景
    let stage = $('#'+this.id);
    //stage.css('position','absolute');
    stage.css('top',this.top);
    stage.css('width',this.width);
    stage.css('height',this.height);
    stage.css('margin',this.margin);
    //stage.css('background-color','#cccccc');
    stage.css('background-image','url("'+this.backgroundImage+'")');
    stage.css('background-size','100% 100%');
    //添加画布
    stage.append('<div style="position:absolute;"><canvas id="canvas_full_track"  width="'+this.width+'" height="'+this.height+'" style="position:absolute;"></canvas></div>');
    stage.append(' <div id="canvas_panel"  width="'+this.width+'" height="'+this.height+'" style="position:absolute;"> </div>');
    stage.append('<div id="ken" style="position: absolute"></div>');
    //scene click event callback, is a vertex clicked, -1:no, 0...vertex index
    let clickedVertex=function(x,y){
        for(let i=0;i<_this.vertexArray.length;i++)
        {
            if(dis({x:x,y:y},_this.vertexArray[i])<=5)return i;
        }
        return -1;
    };

    let dis=function(p1,p2)
    {
        let dx = Math.abs(p2.x - p1.x);
        let dy = Math.abs(p2.y - p1.y);
        let distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return parseInt(distance);
    }

    //scene click event
    $("#"+this.id).click(function(e){
        let p=$("#"+this.id).offset();//position();
        //console.debug('p.left:',p.left,',clientX',e.clientX,',top:',p.top);
        let x=e.pageX-p.left;
        let y=e.pageY-p.top;
        //$('#zuobiao').html("cliecked: x="+x+",y="+y);
        console.log("cliecked: x="+x+",y="+y);
        console.log("{x:"+x+",y:"+y+",text:\"\",areaNo:''},//");
        let pos=clickedVertex(x,y);
        console.log(pos)
        if(pos!=-1 && _this.onVertexClick)_this.onVertexClick(pos);
    });
    if(this.pan)
    {
        let box = $("#"+this.id).get(0); //获取元素
        let boxParent=$("#"+this.id).parent().get(0);
        //console.log(boxParent)
        let x, y; //鼠标相对与div左边，上边的偏移
        let isDrop = false; //移动状态的判断鼠标按下才能移动
        box.onmousedown = function(event) {
            let e = event || window.event; //要用event这个对象来获取鼠标的位置
            x = e.clientX - box.offsetLeft;
            y = e.clientY - box.offsetTop;
            isDrop = true; //设为true表示可以移动
        }

        document.onmousemove = function(e) {
            if(isDrop) {
                let e = e || window.event;
                let moveX = e.clientX - x; //得到距离左边移动距离                    　　
                let moveY = e.clientY - y; //得到距离上边移动距离
                //可移动最大距离
                let maxX = box.offsetWidth;
                let maxY = box.offsetHeight;

                //范围限定  当移动的距离最小时取最大  移动的距离最大时取最小
                if(moveX < -maxX+10) {
                    moveX = -maxX+10
                } else if(moveX > boxParent.offsetWidth-10) {
                    moveX = boxParent.offsetWidth-10;
                }
                if(moveY < -maxY+10) {
                    moveY = -maxY+10;
                } else if(moveY > boxParent.offsetHeight-10) {
                    moveY =  boxParent.offsetHeight-10;
                }
                box.style.left = moveX + "px";
                box.style.top = moveY + "px";
            } else {
                return;
            }

        }

        document.onmouseup = function() {
            isDrop = false; //设置为false不可移动
        }
    }

}

//find a avatar
Spark2D.prototype.getAvatar=function(id)
{
    for(let i=0;i<this.avatarArray.length;i++)
    {
        if(this.avatarArray[i].id===id)return this.avatarArray[i];
    }
    return null;
}

Spark2D.prototype.removeAvatar=function(id)
{
    for(let i=0;i<this.avatarArray.length;i++)
    {
        if(this.avatarArray[i].id===id)
        {
            let ava= this.avatarArray[i];
            if(ava.tween){
                TWEEN.remove(ava.tween);
            }
            document.getElementById(ava.id).remove();
            document.getElementById('canvas_'+ava.id).remove();
            this.avatarArray.splice(i,1);
        }
    }
}

Spark2D.prototype.removeAllAvatar=function()
{
    for(let i=0;i<this.avatarArray.length;i++)
    {
        let ava= this.avatarArray[i];
        document.getElementById(ava.id).remove();
        document.getElementById('canvas_'+ava.id).remove();
        this.avatarArray.splice(i,1);
    }
}



Spark2D.prototype.animate=function()
{
    let _this=this;
    function Spark2d_Animate()
    {
        requestAnimationFrame(Spark2d_Animate);
        for(let i=0;i<_this.avatarArray.length;i++)_this.avatarArray[i].move();
        _this.updateFollow();
        TWEEN.update();
    }
    Spark2d_Animate();
}

Spark2D.prototype.addAvatar=function(avatar)
{
    avatar.init(this,this.width,this.height);
    $("#ken").append(avatar.ken);
    //console.debug($(avatar.ken)[0]);
    $("#canvas_panel").append(avatar.canvas);
    this.avatarArray.push(avatar);
    this.autoLocateAvatarPositonInRoom(avatar.startRoom);
}

Spark2D.prototype.getAvatarsInRoom=function(roomId)
{
    var room=this.pointInfos.rooms[roomId];
    var selectedAvatar=[];
    for(var i=0;i<this.avatarArray.length;i++)
    {
        var avatar=this.avatarArray[i];
        var x=parseInt(avatar.ken.css("left"));
        var y=parseInt(avatar.ken.css("top"));
        // if(x>=room.x && y>=room.y && x-room.x<=room.width && y-room.y<=room.height)
        // {
            selectedAvatar.push(avatar);
        // }
    }
    //console.log(selectedAvatar)
    return selectedAvatar;
}

Spark2D.prototype.autoLocateAvatarPositonInRoom=function(roomId)
{
    var room=this.pointInfos.rooms[roomId];
    if(!room)return;
    var selectedAvatars=this.getAvatarsInRoom(roomId);
    //        console.log(selectedAvatars)
    if(selectedAvatars.length==1)
    {
            selectedAvatars[0].ken.css("left",room.x+room.width/2);
            selectedAvatars[0].ken.css("top",room.y+room.height/2);
    }
    else if(selectedAvatars.length==2)
    {
            selectedAvatars[0].ken.css("left",room.x+room.width/2 -selectedAvatars[0].ken.width()/2);
            selectedAvatars[1].ken.css("left",room.x+room.width/2 +selectedAvatars[1].ken.width()/2);
            if(parseInt(selectedAvatars[0].ken.css("left"))<room.x)selectedAvatars[0].ken.css("left",room.x);
            if(parseInt(selectedAvatars[1].ken.css("left"))>room.x+room.width)selectedAvatars[1].ken.css("left",room.x+room.width-1);
    }
    else if(selectedAvatars.length==3)
    {
            selectedAvatars[0].ken.css("left",room.x+room.width/2 -selectedAvatars[0].ken.width());
            selectedAvatars[2].ken.css("left",room.x+room.width/2 +selectedAvatars[1].ken.width());
            if(parseInt(selectedAvatars[0].ken.css("left"))<room.x)selectedAvatars[0].ken.css("left",room.x);
            if(parseInt(selectedAvatars[2].ken.css("left"))>room.x+room.width)selectedAvatars[2].ken.css("left",room.x+room.width-1);
    }
    else if(selectedAvatars.length==4 || selectedAvatars.length==5)
    {
            selectedAvatars[0].ken.css("left",room.x+room.width/2 - selectedAvatars[0].ken.width()/2);
            selectedAvatars[2].ken.css("left",room.x+room.width/2 + selectedAvatars[2].ken.width()/2);
            if(parseInt(selectedAvatars[0].ken.css("left"))<room.x)selectedAvatars[0].ken.css("left",room.x);
            if(parseInt(selectedAvatars[2].ken.css("left"))>room.x+room.width)selectedAvatars[2].ken.css("left",room.x+room.width-1);
            selectedAvatars[1].ken.css("left",selectedAvatars[0].ken.css("left"));
            selectedAvatars[3].ken.css("left",selectedAvatars[2].ken.css("left"));
            
            if(selectedAvatars[4])
            {
                selectedAvatars[4].ken.css("left",room.x+room.width/2);
                selectedAvatars[4].ken.css("top",room.y+room.height/2);
            }

            selectedAvatars[0].ken.css("top",room.y+room.height/2-selectedAvatars[0].ken.height()/2);
            selectedAvatars[1].ken.css("top",room.y+room.height/2+selectedAvatars[1].ken.height()/2);
            if(parseInt(selectedAvatars[0].ken.css("top"))<room.x)selectedAvatars[0].ken.css("top",room.y);
            if(parseInt(selectedAvatars[1].ken.css("top"))>room.x+room.height)selectedAvatars[1].ken.css("top",room.y+room.height -1);
            selectedAvatars[2].ken.css("top",selectedAvatars[0].ken.css("top"));
            selectedAvatars[3].ken.css("top",selectedAvatars[1].ken.css("top"));
    }

    else //if(selectedAvatars.length==6)
    {
            selectedAvatars[0].ken.css("left",room.x+room.width/2 - selectedAvatars[0].ken.width()/3*2);
            selectedAvatars[4].ken.css("left",room.x+room.width/2 + selectedAvatars[4].ken.width()/3*2);
            if(parseInt(selectedAvatars[0].ken.css("left"))<room.x)selectedAvatars[0].ken.css("left",room.x);
            if(parseInt(selectedAvatars[4].ken.css("left"))>room.x+room.width)selectedAvatars[4].ken.css("left",room.x+room.width-1);
            selectedAvatars[1].ken.css("left",selectedAvatars[0].ken.css("left"));
            selectedAvatars[5].ken.css("left",selectedAvatars[4].ken.css("left"));
            selectedAvatars[2].ken.css("left",room.x+room.width/2 - selectedAvatars[0].ken.width()/3);
            selectedAvatars[3].ken.css("left",room.x+room.width/2 - selectedAvatars[3].ken.width()/3);

            selectedAvatars[0].ken.css("top",room.y+room.height/2-selectedAvatars[0].ken.height()/2);
            selectedAvatars[1].ken.css("top",room.y+room.height/2+selectedAvatars[1].ken.height()/2);
            if(parseInt(selectedAvatars[0].ken.css("top"))<room.x)selectedAvatars[0].ken.css("top",room.y);
            if(parseInt(selectedAvatars[1].ken.css("top"))>room.x+room.height)selectedAvatars[1].ken.css("top",room.y+room.height -1);
            selectedAvatars[2].ken.css("top",selectedAvatars[0].ken.css("top"));
            selectedAvatars[4].ken.css("top",selectedAvatars[0].ken.css("top"));
            selectedAvatars[3].ken.css("top",selectedAvatars[1].ken.css("top"));
            selectedAvatars[5].ken.css("top",selectedAvatars[1].ken.css("top"));
    }

}

Spark2D.prototype.getRoomAroundPoint=function(x,y)
{
    for(var roomId in this.pointInfos.rooms)
    {
       var room=this.pointInfos.rooms[roomId];
       if(x>=room.x && y>=room.y && x-room.x<=room.width && y-room.y<=room.height)
        {
            return roomId;
        }
    }
    return null;
}


//display all the track line on a canvas
Spark2D.prototype.drawFullTrack=function(vertexArray,edgeArray,color,radius,lineWidth)
{
    for(let i =0;i< edgeArray.length;i++)
    {
        let p1=edgeArray[i][0];
        let p2=edgeArray[i][1];
        let trackPoint=[];
        trackPoint[0]=vertexArray[p1];
        trackPoint[1]=vertexArray[p2];
        //points.push(trackPoint)
        //console.log(trackPoint)
        this.drawTrackLine(trackPoint,color,radius,lineWidth)
    }

};

Spark2D.prototype.drawTrackLine=function(points,color,radius,lineWidth)
{
    let arcRadius=radius || 5;
    let c=$("#canvas_full_track").get(0);
    let ctx=c.getContext("2d");
    ctx.strokeStyle=color;
    ctx.fillStyle=color;
    ctx.lineWidth = lineWidth || 2;
    for(let i=0;i< points.length-1;i++)
    {
        let p1=points[i];
        let p2=points[i+1];
        ctx.moveTo(p1.x,p1.y);
        ctx.lineTo(p2.x,p2.y);
        ctx.stroke();
    }

    for(let i=0;i< points.length;i++)
    {
        ctx.beginPath();
        let p1=points[i];
        ctx.moveTo(p1.x,p1.y);
        ctx.arc(p1.x,p1.y,arcRadius,0,2*Math.PI);
        ctx.fill();
        if(p1[2])
        {
            ctx.font="30px,Arial";
            ctx.fillText(p1.text,p1.x-5,p1.y-10);
        }
    }

};


Spark2D.prototype.clearFullTrack=function()
{
    let c=$("#canvas_full_track").get(0);
    let ctx=c.getContext("2d");
    ctx.clearRect(0,0,c.width,c.height);
    c.height=c.height;

}
Spark2D.prototype.updateFollow=function()
{
    if(!this.followedAvatar)return;
    let pos=this.followedAvatar.getPosition();
    let box = $("#stage").get(0);
    let boxParent=$("#stage").parent().get(0);
    console.log(pos)
    //let moveX=-(box.offsetWidth-boxParent.offsetWidth)/2+pos.x
    //let moveY=-(box.offsetHeight-boxParent.offsetHeight)/2+pos.y

    //let moveX=boxParent.offsetWidth/2-box.offsetWidth/2-pos.x
    let moveX=boxParent.offsetWidth/2-pos.x
    let moveY=boxParent.offsetHeight/2-pos.y
    box.style.left = moveX + "px";
    box.style.top = moveY + "px";

}

Spark2D.prototype.follow=function(avatar)
{
    this.followedAvatar=avatar;
}
Spark2D.prototype.cancelFollow=function()
{
    this.followedAvatar=null;
}

/*
2.5D avatar, which walks on the 2.5D scene
*/
let Avatar2D=function(options)
{
    //点映射的数组,图上点的坐标应该与数组下标一一对应
    this.id=options.id || "Ava_"+Math.floor(Math.random()*16777215).toString(16);
    this.title=options.title || this.id;
    this.width=options.size[0] || 10;
    this.height=options.size[1] || 20;
    this.speed = options.speed || 1;
    //默认起始点为0点
    this.startRoom=options.startRoom || null;
    this.startRoomText=options.startRoomText || null;
    this.start=options.start || {x:185,y:83,text:'0',time:0};
    this.startText=options.startText || null;
    this.painedTrackPoints= [];
    this.initTrackPoints=options.trackPoints || [];
    this.trackPoints=[];  //trackPoints的格式  [{x:0,y:0,text:'',time:yyyy-MM-dd HH:mm}]
    this.isDrawTrack=options.isDrawTrack || false;       //轨迹线
    this.isDrawTrackTitle = options.isDrawTrackTitle || false;
    this.tween=null;
    this.ken=null;
    //角色头顶字体
    this.kenTitle=null;
    //角色头顶字体
    this.kenTitleSize = options.kenTitleSize || 12;
    //角色类型
    this.kenType=options.kenType;
    this.canvas=null;
    this.color=options.trackColor || '#'+Math.floor(Math.random()*16777215).toString(16);
    this.lastTrackVertex=null;
    this.font="30px,Arial";
    //轨迹线宽度
    this.trackLineWidth = options.trackLineWidth|| 1;
    //轨迹线上的点字体
    this.trackPointFont = options.trackPointFont || 14
    //轨迹点的半径
    this.trackPointRadius = options.trackPointRadius || 5
    //放大系数
    this.fontRatio = options.fontRatio || 1;
    this.onClick = options.onClick;
    //this.lastMovePoint;   //记录上一次的移动的位置
    //this.addAvatar(options.left,options.top);
    this.spark2D=null;
    this.lastRoomId=this.startRoom;
}
//will be invoked by scene while the avatar was added to the scene
Avatar2D.prototype.init=function(spark2D,stageWidth,stageHeight)
{
    this.spark2D=spark2D;
    if(this.startRoom)
    {
        var sp=spark2D.pointInfos.getPoint(this.startRoom);
        this.start={x:sp.x,y:sp.y,time:sp.time,text:this.startRoomText};
    }
    if(this.startText)this.start.text=this.startText;
    if(!this.start.time){
        this.start.time = 0;
    }
    //第一位：1：民警  2：嫌疑人    第二位  1：男 2：女
    let kenTu = {
        "11" : "mj",
        "12" : "mj",
        "21" : "ken",
        "22" : "nfk"
    }
    //默认为嫌疑人
    this.kenType = kenTu[this.kenType] || "21";
    this.setRatio(this.fontRatio);
    //console.log(startPoint);
    let ken=$('<div id="'+this.id+'" class="ken '+this.kenType+'_down" ></div>');
    let titleArea = textSize(this.kenTitleSize,'',this.title);
    // let kenTitle='<span class="title" style="position:absolute;top:-'+this.height/2+'px;left:-'+this.width/2+';text-align:center;color:'+this.color+';display: inline-block;width: '+titleArea.width+';font-size: '+this.kenTitleSize+'">'+this.title+'</span>';
    // ken.append(kenTitle)
    let kenTitle=$('<span class="title">'+this.title+'</span>');
    kenTitle.css('position','absolute');
    kenTitle.css('top','-'+titleArea.height/0.8);
    kenTitle.css('left','-'+titleArea.width/4);
    kenTitle.css('text-align','center');
    kenTitle.css('color',this.color);
    kenTitle.css('display','inline-block');
    kenTitle.css('width',titleArea.width);
    kenTitle.css('font-size',this.kenTitleSize);
    this.kenTitle=kenTitle;
    kenTitle.appendTo(ken);
    this.kenTitle=kenTitle;
    ken.css('height',this.height);
    ken.css('width',this.width);
    ken.css('cursor','pointer')
    ken.css("left",(this.start.x-this.width/2));
    ken.css("top",(this.start.y-this.height/2));
    this.ken=ken;
    let canvas=$('<canvas id="canvas_'+this.id+'"  width="'+stageWidth+'" height="'+stageHeight+'" style="position:absolute"></canvas>');
    this.canvas=canvas;
    let c=this.canvas.get(0);
    let ctx=c.getContext("2d");
    ctx.font="18px,Arial,bold";
    ctx.strokeStyle=this.color;
    ctx.fillStyle=this.color;

    //draw the init position
    if(this.isDrawTrack)
    {
        if(this.initTrackPoints)this.drawTrack(this.initTrackPoints,true);
        ctx.moveTo(this.start.x,this.start.y);
        ctx.arc(this.start.x,this.start.y,this.trackPointRadius,0,2*Math.PI);
        ctx.fill();
        if(this.start.text)
        {
            ctx.fillText(this.start.text,this.start.x-this.start.text.length/2,this.start.y-this.trackPointRadius*1.5);

        }

        if(this.startRoom && (this.initTrackPoints.length==0))
        {
            //this.addPoint(this.start);
            this.drawTrack([this.start],true);
        }
    }
    if(this.onClick){
        var _click=this.onClick;
        var _this=this;
        this.ken.on('click',function(){_click(_this)});
    }

}

/**
 * 设置各个字体乘以系数后的指
 * @param ratio
 */
Avatar2D.prototype.setRatio=function(ratio){
    this.kenTitleSize = (this.kenTitleSize*ratio)+'px';
    this.width = this.width*ratio;
    this.height = this.height*ratio;
    this.trackLineWidth = this.trackLineWidth*ratio;
    this.trackPointFont = (this.trackPointFont*ratio)+'px Arial bold';
    this.trackPointRadius = this.trackPointRadius * ratio;
}

Avatar2D.prototype.getPosition=function()
{
    let ken=this.ken;
    return {x:parseInt(ken.css('left'))+this.width,y:parseInt(ken.css('top'))+this.height};
}
Avatar2D.prototype.setTrackColor=function(color)
{
    this.color=color;
    let c=this.canvas.get(0);
    let ctx=c.getContext("2d");
    ctx.strokeStyle=this.color;
    ctx.fillStyle=this.color;

}

//add track vertex points for the avatar
Avatar2D.prototype.addPoints=function(points)
{
    for(let i=0;i<points.length;i++)
    {
        this.trackPoints.push(points[i]);
        //this.painedTrackPoints.push(points[i]);
    }
    if(this.isDrawTrack)this.drawTrack(points,false);
}
Avatar2D.prototype.addPoint=function(point)
{
    this.trackPoints.push(point);
    //this.painedTrackPoints.push(point);
    if(this.isDrawTrack)this.drawTrack([point],false);
}
Avatar2D.prototype.moveToRoomByTrack=function(roomId,text)
{
    let trackPoints=this.spark2D.pointInfos.getFullPathArrayByPoints([{areaNo:this.lastRoomId},{areaNo:roomId,text:text}]);
    //console.debug(trackPoints.slice(2,trackPoints.length-1))
    //console.log(this.painedTrackPoints);
	this.addPoints(trackPoints.slice(1,trackPoints.length));
    this.lastRoomId=roomId
}

Avatar2D.prototype.moveToRoom=function(roomId)
{
    let point=this.spark2D.pointInfos.getPoint(roomId);
    this.addPoint(point);
    this.lastRoomId=roomId
}



//setInterval(randomWalk,2000);
//deprecated
function move_all()
{

    //drawTrack(points);
    let     tween;
    let     oldTween=tween;
    let     initTween=tween;
    for(let i=0;i<points.length-1;i++)
    {
        let p1=points[i];
        let p2=points[i+1];
        tween=new TWEEN.Tween({x:p1[0],y:p1[1],z:0});
        if(i==0)initTween=tween;

        let endPos={x:p2[0],y:p2[1],z:0};
        tween.to(endPos, 2000);
        //tween.easing(TWEEN.Easing.Exponential.InOut);
        tween.easing(TWEEN.Easing.Linear.None);
        tween.onUpdate(function() {
            console.log(this.x);
            let ken=document.getElementById("ken");
            ken.style.left=(this.x-this.width/2)+"px";
            ken.style.top=(this.y-this.height/2)+"px";
        });

        if(i>=1)oldTween.chain(tween);
        oldTween=tween;
    }
    initTween.start();

}
//move the avatar on the track while has new vertexes
Avatar2D.prototype.move=function()
{
    //有正在执行中的轨迹动画
    if(this.tween)return;
    //has no new track vertex
    if(this.trackPoints.length==0)return;
    let nextVertex=this.trackPoints.shift();
    this.moveTo(nextVertex);


}

//move to am absolute position of the scene
Avatar2D.prototype.moveTo=function(point)
{
    let _this=this;
    let ken=this.ken;
    let startPoint={x:this.start.x,y:this.start.y};
    //let time = point.time - _this.lastMovePoint.time;
    let time = point.time*1000 || 2000;
    //设置播放的速度倍率
    //time = time/_this.speed;
    let prex = parseInt(ken.css('left'));
    let prey = parseInt(ken.css('top'));
    let dire = direction(prex,prey,point.x,point.y);

    ken.removeClass();
    ken.addClass("ken");
    ken.addClass(this.kenType+"_"+dire);
    

    //console.log(x+'--'+y);
    let tween=new TWEEN.Tween(this.start).to({x:point.x,y:point.y,z:0},time).easing(TWEEN.Easing.Linear.None);
    _this.tween=tween;
    tween.onUpdate(function(obj) {
        ken.css('left',(obj.x - _this.width/2));
        ken.css('top',(obj.y - _this.height/2));
    });
     tween.onStart(function (){
     });
    // tween.onStop(function (){
    // });
    tween.onComplete(function(){
        _this.tween=null;
        var roomId=_this.spark2D.getRoomAroundPoint(startPoint.x,startPoint.y);
        _this.spark2D.autoLocateAvatarPositonInRoom(roomId)

         var roomId=_this.spark2D.getRoomAroundPoint(point.x,point.y)
        _this.spark2D.autoLocateAvatarPositonInRoom(roomId)
    })
    tween.start();
    //console.log('从',this.start,'移动到[',point.x,',',point.y,'],耗时:',point.time,'tween对象',tween);
};

Avatar2D.prototype.drawHistoryTrack=function()
{
    var hisTrack=[];
    for(var i=0;i<this.painedTrackPoints.length;i++)hisTrack.push(this.painedTrackPoints[i]);
    //for(var i=0;i<this.trackPoints.length;i++)hisTrack.push(this.trackPoints[i]);
    //console.log(this.painedTrackPoints);
    //console.log(this.trackPoints);
    this.painedTrackPoints=[];
    this.drawTrack(hisTrack,true);
};

//draw the track line of the avatar
Avatar2D.prototype.drawTrack=function(points,startFromFirst,isTempTracks)
{
    //console.log(points);
    //console.log(this.painedTrackPoints);
    var copiedPoints=[];
   // for(let i=0;i<points.length;i++)copiedPoints.push(points[i]);
    copiedPoints = JSON.parse(JSON.stringify(points))
    //这里取单个字体占据的高度，估计值
    let fontHeight = parseInt(this.trackPointFont.substr(0,this.trackPointFont.indexOf("p")));
    let c=this.canvas.get(0);
    let ctx=c.getContext("2d");
    let point;
    let cp=this.lastTrackVertex;
    if(startFromFirst)cp=copiedPoints[0];
        if(copiedPoints.length>0 && cp)
        {
            point = copiedPoints[0];
            ctx.moveTo(cp.x, cp.y);
            ctx.lineTo(point.x,point.y);
            ctx.lineWidth = this.trackLineWidth;
            ctx.stroke();
            if(point.text && this.isDrawTrackTitle)
            {
                let pc=this.vertexPainedTimes(copiedPoints);
                //ctx.fillText(point.text,point.x-5,copiedPoints.y-10-pc*10);
                ctx.font = this.trackPointFont;
                ctx.fillText(point.text,point.x-fontHeight*2,point.y-fontHeight-pc*fontHeight);

            }
            //this.lastTrackVertex=cp;
        }
    if( copiedPoints[0] && !isTempTracks)
        this.painedTrackPoints.push(copiedPoints[0]);
    for(let j=0;j<copiedPoints.length-1;j++)
    {
        let p1=copiedPoints[j];
        let p2=copiedPoints[j+1]
        ctx.moveTo(p1.x,p1.y);
        ctx.lineTo(p2.x,p2.y);
        ctx.lineWidth = this.trackLineWidth;
        ctx.stroke();
        if(!isTempTracks)this.painedTrackPoints.push(p2);
        if(p2.text && this.isDrawTrackTitle)
        {

            let pc=this.vertexPainedTimes(p2);
            //ctx.fillText(p2.text,p2.x-5,p2.y-10-pc*10);
            ctx.font = this.trackPointFont;
            ctx.fillText(p2.text,p2.x-fontHeight*2,p2.y-pc*fontHeight);
        }
    }
    if(copiedPoints.length>0) this.lastTrackVertex=copiedPoints[copiedPoints.length-1];
    for(let i=0;i< copiedPoints.length;i++)
    {
        ctx.beginPath();
        let p1=copiedPoints[i];
        ctx.moveTo(p1.x,p1.y);
        ctx.arc(p1.x,p1.y,this.trackPointRadius,0,2*Math.PI);
        ctx.fill();
    }

};
Avatar2D.prototype.vertexPainedTimes=function(point)
{
    let count=0;
    for (let i=0; i<this.painedTrackPoints.length;i++ )
    {
        if(this.painedTrackPoints[i].x==point.x && this.painedTrackPoints[i].y==point.y)count++;
    }
    return count;
}

Avatar2D.prototype.distoryTrack=function()
{
    let c=this.canvas.get(0);
    let ctx=c.getContext("2d");
    ctx.clearRect(0,0,c.width,c.height);
    c.height=c.height;
    ctx.font="30px,Arial";
    ctx.strokeStyle=this.color;
    ctx.fillStyle=this.color;
    this.painedTrackPoints=[];
}

Avatar2D.prototype.clearTrack=function()
{
    let c=this.canvas.get(0);
    let ctx=c.getContext("2d");
    ctx.clearRect(0,0,c.width,c.height);
}

Avatar2D.prototype.hideTrack=function()
{
    this.canvas.hide();
}

Avatar2D.prototype.showTrack=function()
{
    this.canvas.show();
}
Avatar2D.prototype.isTrackShowed=function()
{
   return this.canvas.is(':visible');
}
Avatar2D.prototype.stopDrawTrack=function()
{
    this.isDrawTrack=false;
}
Avatar2D.prototype.startDrawTrack=function()
{
    this.isDrawTrack=true;
}

function direction(x0,y0,x1,y1){
    let dx = Math.abs(x0-x1);
    let dy = Math.abs(y0-y1);
    if(dx>dy){//横向移动距离大于纵向距离，判断左右
        if(x0<x1){//往右走
            return "right";
        }else{//往左走
            return "left";
        }
    }else{//纵向移动距离大于横向距离，判断上下
        if(y0<y1){//往下走
            return "down";
        }else {//往上走
            return "up";
        }
    }


}


Element.prototype.remove = function() {
    this.parentElement.removeChild(this);
}
NodeList.prototype.removeElement = HTMLCollection.prototype.removeElement = function() {
    for(let i = this.length - 1; i >= 0; i--) {
        if(this[i] && this[i].parentElement) {
            this[i].parentElement.removeChild(this[i]);
        }
    }
}

/**************************************时间格式化处理************************************/
/*
function dateFtt(fmt,date)
{
    var o = {
        "M+" : date.getMonth()+1,                 //月份
        "d+" : date.getDate(),                    //日
        "h+" : date.getHours(),                   //小时
        "m+" : date.getMinutes(),                 //分
        "s+" : date.getSeconds(),                 //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S"  : date.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}*/
