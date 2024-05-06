/**
 * 视频或直播流人物跟随时间变化
 * Spark2D 场景对象
 * Avatar  人物对象
 * traackArray 人物的行动轨迹数组 [时间节点,人物x坐标,人物y坐标]
 * 例如:[{time:0,x:120,y:100},
 * 		{time:26,x:120,y:200},
 * 		{time:50,x:180,y:200}
 * ]
 */
let player;
let playerContainer;
let initFlag = false;
let speed = 1.0;
function followAvatar(spark2D,trackArray){
	if(!spark2D){
		console.error("spark2D对象不存在");
		return;
	}
	if(!trackArray || trackArray.length <2){
		console.error("轨迹数组不能为空或点的数量小于2");
		return;
	}
    let vArray = [];
    let eArray = [];
    let avatar2D;
    //计算轨迹点之间的线
    for(let i = 0;i<trackArray.length;i++){
        let track = trackArray[i];
        vArray.push([track.x,track.y,i]);
        if(i==trackArray.length-1)break;
        eArray.push([i,i+1,1]);
    }
    //画初始轨迹
    spark2D.drawFullTrack(vArray,eArray,'red');
    //添加人物在初始点
    avatar2D=new Avatar2D({
		start:trackArray[0],
		size:[16/2,20/2],
		kenType:"21",
        trackColor:'#00BFFF',
        onClick:function(){
            //点击人物的时候初始化
            if(!initFlag){
            	initPlayer(spark2D,avatar2D,trackArray);
            }
            else{
            	playerContainer.dialog('open');
            	player.videoPlay();
            }
        }

	});
    spark2D.addAvatar(avatar2D);    
 
}
//初始化一个容器 和ckplayer播放器
function initPlayer(spark2D,avatar2D,trackArray){
	console.log('body...'+$('body').width()+'spark2D....'+spark2D.width);
	playerContainer = $('#player-container')[0];
	if(!playerContainer){
		$('body').append('<div id="player-container" display="none"></div>');
		playerContainer = $('#player-container');
		playerContainer.dialog({
		    title: '播放',
		    width:$('body').width()-spark2D.width+'px',
		    height:spark2D.height+'px',
		    top:'50px',
			left:spark2D.width+'px',
		    closed: false,
		    onClose:function(){
		    	player.videoPause();
		    },
/*		    buttons:[{
				text:'暂停',
				iconCls:'icon-edit',
				handler:function(){player.videoPause()}			    	
		    },
			{
				text:'播放',
				iconCls:'icon-edit',
				handler:function(){player.videoPlay()}			    	
		    }			    ],*/
		    content: '<div id="video" style="width:100%;height:100%"></div>',
		    minimizable:true,
		    maximizable:true,
		    modal: false
		})				
	}
	var videoObject = {
			container: '#video',//“#”代表容器的ID，“.”或“”代表容器的class
			variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象
			autoplay:true,
			flashplayer:false,//如果强制使用flashplayer则设置成true
			video:'../static/vms/spark2D/resources/toobad2.mp4'//视频地址
	};
	player=new ckplayer(videoObject);
	
	 //监听时间跳转
	 player.addListener('seekTime',function(time){
		 if(typeof(time) == 'object'  ){
			 time = 0;
		 }
		 console.log(player.getConfig('config'));
	     spark2D.removeAvatar(avatar2D.id);
	     let flag = false;
	     let countTime = 0;
	     for(let i=1;i<trackArray.length;i++){
	         let track = trackArray[i];
	         let lastTrack = trackArray[i-1];
	         //这里需要初始化设置一下第一个点的坐标时间，防止第一个时间未传
	         if(i == 0 && !lastTrack.time){
	        	 lastTrack.time = 0;
	         }
	         countTime += track.time;
	         if(flag){
	             avatar2D.addPoint({x:track.x,y:track.y,time:track.time/speed,text:'x:'+track.x+'y:'+track.y});
	             continue;
	         }
	         //定位时间段.
	         if(time < countTime ){
	             let p = ( time - countTime + track.time)/track.time;  //计算跳转的时间点的比例
	             //console.log(p);
	             let shengyu = countTime - time;
	             let stepX = lastTrack.x + (track.x-lastTrack.x)*p;
	             let stepY = lastTrack.y + (track.y-lastTrack.y)*p;
	             //console.log('时间节点time'+time+',对应的坐标数组'+JSON.stringify(track)+',重置坐标x:'+track.x+'y:'+track.y);
	             //console.log(stepX+'-----'+stepY);
	             avatar2D=new Avatar2D({
	                 id:avatar2D.id,
	                 speed:avatar2D.speed,
	                 start:{x:stepX,y:stepY,text:"0",time:0},
	                 size:[16/2,20/2],
	                 kenType:"21",
	                 trackColor:'##00BFFF',
	                 onClick:function(){
	                 	playerContainer.dialog('open');
	                     player.videoPlay();
	                 }
	             });
	             spark2D.addAvatar(avatar2D);                    
	             avatar2D.addPoint({x:track.x,y:track.y,time:shengyu/speed,text:'x:'+track.x+',y:'+track.y});
	             flag = true;                            
	
	         }
	     }
	     console.log(trackArray);
	 })    
	
	 //监听速度变化  
	 player.addListener('playbackRate',function(speedVal){
	     console.log('调整播放倍率为....'+speedVal);
	     spark2D.removeAvatar(avatar2D.id);
	     //let time = new Date(trackArray[0].time + player.time*1000);
	     let time = player.time;
	     speed = parseFloat(speedVal);
	     console.log('转化---'+(1000/speed));
	     let flag = false;
	     let countTime = 0;
	     for(let i=1;i<trackArray.length;i++){
	         let track = trackArray[i];
	         let lastTrack = trackArray[i-1];
	         countTime += track.time;
	         if(flag){
	             console.log('houxu'+(track.endTime-track.startTime)/speed);
	            // avatar2D.addPoint([track.x,track.y,(track.time-trackArray[i-1].time)/speed*1000],'x:'+track.x+'y:'+track.y);
	             avatar2D.addPoint({x:track.x,y:track.y,time:track.time/speed,text:'x:'+track.x+'y:'+track.y});
	             continue;
	         }
	         //定位时间段.
	         if(time < countTime){
	             let p = ( time - countTime + track.time)/track.time;  //计算跳转的时间点的比例
	             //console.log(p);
	             let shengyu = countTime - time;
	             //console.log(p);
	             let stepX = avatar2D.step.x;
	             let stepY = avatar2D.step.y;
	             console.log('时间节点time'+time+',对应的坐标数组'+JSON.stringify(track)+',重置坐标x:'+track.x+'y:'+track.y);
	             avatar2D=new Avatar2D({
	                 id:avatar2D.id,
	                 speed:speed,
	                 start:{x:stepX,y:stepY,text:"0",time:0},
	                 size:[16/2,20/2],
	                 kenType:"21",
	                 trackColor:'##00BFFF',
	                 onClick:function(){
	                 	playerContainer.dialog('open');
	                     player.videoPlay();
	                 }
	                 
	             });
	             spark2D.addAvatar(avatar2D);              
	             avatar2D.addPoint({x:track.x,y:track.y,time:shengyu/speed,text:'x:'+track.x+',y:'+track.y});
	             flag = true;                            
	
	         }
	     }
	     console.log(avatar2D.trackPoints);        
	 })         
	 
	 //监听播放状态
	 player.addListener('play',function(){
	     console.log('播放.....');
	     if(!initFlag){
	    	initFlag = true;
            for(let i =1;i<trackArray.length;i++){
            	avatar2D.addPoint({x:trackArray[i].x,y:trackArray[i].y,time:trackArray[i].time});            		
            }
	     }
	     if(avatar2D.tween && !avatar2D.tween._isPlaying){
	         avatar2D.tween.start();
	     }
	     
	 })     
	 //监听暂停状态
	 player.addListener('pause',function(){
	     console.log('暂停.....');
	     avatar2D.tween.stop();
	 })    	
}
