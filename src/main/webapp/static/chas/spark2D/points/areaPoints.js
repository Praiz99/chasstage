//vArray 点位的实际信息 [{x:185,y:83,text:"1",areaNo:'A_X_1'}]
//eArray 定义点和点之间的通道，对应vArray的下标顺序用于，计算最短路径[[0,15,2]]
//rooms 设置哪些区域的房间需要做排队处理（防止头像堆叠）
//dijk这个依赖dijk路径算法
let PointInfos = function (points_data,orgCode){
	if(!DijkStra){
		console.error('需要依赖DijkStra.js。');
		return;
	}
	this.vArray = null;
	this.eArray = null;
	this.rooms = null;
	this.dijk = null;
	this.orgCode = orgCode;
	this.pointList = points_data[orgCode];
	if(!this.pointList){
		console.error('没有查找到orgCode:',orgCode,'对应的区域信息');
		return;
	}
	this.init();
}

PointInfos.prototype.init = function (){
	let pointInfo = this.pointList;
	this.vArray = pointInfo.vArray;
	this.eArray = pointInfo.eArray;
	this.rooms = pointInfo.rooms;
	this.dijk = new DijkStra(this.vArray.length,this.eArray);
}

//根据区域信息返回实际点位信息
PointInfos.prototype.getPoint = function (areaNo){
	let point;
	this.vArray.filter(function(value,index){
		if(value.areaNo == areaNo ){
			point = value;
			point.index = index;
			return true;
		}
	});
	return point;
}

//根据最短路径算法获取完整路径,tracks为数组
PointInfos.prototype.getFullPathArray = function (peopleNo){
	let trackArray = [];
	let _this = this;
	$.ajax({
		url:ctx+'/spark2D/find/points/'+peopleNo,
		dataType:'json',
		async:false,
		success:function(result){
			let list = result.data.list;
			//这里用来记录计算最短路径后的点位
			let lastMove;
			for (let i in list){
				let data = list[i];
				//设置第一个点
				let point = _this.getPoint(data.areaNo);
				if(!point){
					console.error("areaNo:["+data.areaNo+"]对应的轨迹点信息未在points.js文件中定义，请核对！")
					//trackArray = [];
					continue;
				}
				//计算总的停留时间
				let stayTime = (new Date(data.leaveTime) - new Date(data.enterTime))/1000;
				let parseEnterTime = dateFtt('hh:mm:ss',new Date(data.enterTime));
				if(i == 0){
					trackArray.push({x:point.x,y:point.y,time:0});
					//加入这个点的等待时间

					trackArray.push({x:point.x,y:point.y,time:stayTime,text:parseEnterTime});
					console.debug('初始点'+data.areaNo+'，停留'+stayTime+'秒。');
					lastMove = point.index;
					continue;
				}

				if(stayTime < 5 ){
					stayTime = 5;
				}
				//需要计算最短路径
				let paths = _this.dijk.dijk(lastMove,point.index);
				console.debug(paths);
				//加入移动时间
				for(let j in paths){
					if(j == 0){
						continue;
					}
					trackArray.push({x:_this.vArray[paths[j]].x,y:_this.vArray[paths[j]].y,time:2});
					stayTime = stayTime -2;
				}
				//停留时间
				if(stayTime < 2){
					stayTime = 2;
				}
				trackArray.push({x:point.x,y:point.y,time:stayTime,text:parseEnterTime});
				console.debug('移动到'+data.areaNo+'点需要'+(paths.length-1)*2+'秒，停留'+stayTime+'秒。');
				if(paths.length != 0 ){
					lastMove = paths[paths.length-1];
				}


			}
		}
	})
	return trackArray;

}


PointInfos.prototype.getFullPathArrayByPoints = function (points){
	let trackArray = [];
	let _this = this;

			let list = points;
			//这里用来记录计算最短路径后的点位
			let lastMove;
			for (let i in list){
				let data = list[i];
				//设置第一个点
				let point = _this.getPoint(data.areaNo);
				if(!point){
					console.error("areaNo:["+data.areaNo+"]对应的轨迹点信息未在points.js文件中定义，请核对！")
					//trackArray = [];
					continue;
				}
				//计算总的停留时间
				let stayTime = (new Date(data.leaveTime) - new Date(data.enterTime))/1000;
				let parseEnterTime = dateFtt('hh:mm:ss',new Date(data.enterTime));
				if(i == 0){
					//trackArray.push({x:point.x,y:point.y,time:0,text:data.text});
					//加入这个点的等待时间

					trackArray.push({x:point.x,y:point.y,time:stayTime,text:data.text});
					//console.debug('初始点'+data.areaNo+'，停留'+stayTime+'秒。');
					lastMove = point.index;
					continue;
				}

				if(stayTime < 5 ){
					stayTime = 5;
				}
				//需要计算最短路径
				let paths = _this.dijk.dijk(lastMove,point.index);
				//console.debug(paths);
				//加入移动时间
				for(var j=1;j<paths.length-1;j++){
					trackArray.push({x:_this.vArray[paths[j]].x,y:_this.vArray[paths[j]].y,time:2});
					stayTime = stayTime -2;
				}
				//停留时间
				if(stayTime < 2){
					stayTime = 2;
				}
				trackArray.push({x:point.x,y:point.y,time:stayTime,text:data.text});
				//console.debug('移动到'+data.areaNo+'点需要'+(paths.length-1)*2+'秒，停留'+stayTime+'秒。');
				if(paths.length != 0 ){
					lastMove = paths[paths.length-1];
				}


			}
            //console.log(trackArray)
		
	return trackArray;

}


//获取非完整路径
PointInfos.prototype.getPathArray = function (peopleNo){
	let trackArray = [];
	$.ajax({
		url:ctx+'/spark2D/find/points/'+peopleNo,
		dataType:'json',
		async:false,
		success:function(result){
			if(result.code == 200){
				let list = result.data.list;
				let time = 0;
				for (let i in list){
					let data = list[i];
					//计算和视频对应的时间点
					if (i != 0){
						time += (new Date(list[i-1].leaveTime)-new Date(list[i-1].enterTime))/1000;
					}
					trackArray.push({areaName:data.areaName,time:data.enterTime,seekTime:time,peopleName:data.peopleName});
				}
			}
		}
	})
	return trackArray;

}

//获取areaNo和时间点对应的关系
PointInfos.prototype.getAreaNoToTime = function (peopleNo){
	let trackArray = [];
	let _this = this;
	$.ajax({
		url:ctx+'/spark2D/find/points/'+peopleNo,
		dataType:'json',
		async:false,
		success:function(result){
			let list = result.data.list;
			//记录需要跳转的时间点
			let seekTime = 0;
			for (let i in list){
				let data = list[i];
				let point = _this.getPoint(data.areaNo);
				trackArray.push({x:point.x,y:point.y,seekTime:seekTime});
				seekTime += (new Date(data.leaveTime) - new Date(data.enterTime))/1000;
			}
		}
	})
	return trackArray;
}