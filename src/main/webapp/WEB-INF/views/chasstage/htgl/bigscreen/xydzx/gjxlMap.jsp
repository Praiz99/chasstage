<%@ include file="/WEB-INF/views/framework/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<%--<%@ include file="/WEB-INF/views/chasEt/common/default.jsp"%>--%>
<%--<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/daterangepicker/css/daterangepicker.css">--%>
<%--<link rel="stylesheet" type="text/css" href="${ctx}/static/chasEt/style/htgl/common/css/common.css">--%>
<%--<link rel="stylesheet" type="text/css" href="${ctx}/static/chasEt/style/htgl/common/css/page.css">--%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/easyui-1.5.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/easyui-1.5.1/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/base.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/css/default/form.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/tabs.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/tips_panel.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/dialog_panel.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/form.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/style/comui/css/button.css">
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/plugins/easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
<%--<script src="${ctx}/static/agxt/js/ext/common/window.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/static/chasEt/js/common/jquery-3.4.1.js"></script>--%>
<script type="text/javascript" src="${ctx}/static/chasEt/js/common/chas.et.base.js"></script>

<link rel="stylesheet" href="${ctx}/static/chas/spark2D/css/normalize.css">
<link rel="stylesheet" href="${ctx}/static/chas/spark2D/css/style.css" media="screen" type="text/css" />
<script src="${ctx}/static/chas/spark2D/js/tween.umd.js"></script>
<script src="${ctx}/static/chas/spark2D/js/RequestAnimationFrame.js"></script>
<script src="${ctx}/static/chas/spark2D/js/DijkStra.js"></script>
<script src="${ctx}/static/chas/spark2D/js/Spark2D.js"></script>
<script src="${ctx}/static/chas/spark2D/points/areaPoints.js"></script>
<script src="${ctx}/static/chas/spark2D/js/common.js"></script>
<script type="text/javascript" src="${ctx}/static/framework/utils/common.js"></script>
<%--<script type="text/javascript" src="${ctx}/static/framework/plugins/daterangepicker/js/moment.min.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/static/framework/plugins/daterangepicker/js/daterangepicker.min.js"></script>--%>
<!--<script src="${ctx}/static/vms/spark2D/points/points.js"></script>-->
<%--<script src="${ctx}/static/chas/spark2D/fqMap.js"></script>--%>
<style type="text/css">
	.activeMap{
		background: #328cdfbf!important;
	}
	.activeMap div{
		color: white;
	}
</style>
<script type="text/javascript">
	var gjDataObj = '${gjDataObj}';
	<%--var ryid = '${ryxx.id}';--%>
	<%--var rybh = '${ryxx.rybh}';--%>
</script>
<head>
<title>轨迹地图</title>
</head>
<body style="width: 100%;height: 100%;overflow-y: hidden;">
<%--<div class="mglr20 common-tab tab-navm" style="height: 5%!important;padding-top: 0px;margin: 0px!important;">
	<ul style="float: left;width: 40%;">
		<c:if test="${gjlb}">
		<li bs="gjlb" style="border: white;background: white;"><a href="${ctx}/chasEt/chasBaqryxxController/rygjList?use=baq&ryid=${ryxx.id}&rybh=${ryxx.rybh}&&sfznbaq=1" style="color: black;">${use eq 'baq' ? '人员轨迹' : '轨迹列表'}</a></li>
		</c:if>
		<c:if test="${gjsp}">
		<li style="border: white;background: white;"><a href="${ctx}/chasEt/chasBaqryxxController/rygjList?use=baq&ryid=${ryxx.id}&rybh=${ryxx.rybh}&&sfznbaq=1&bs=gjsp" style="color: black;">轨迹视频</a></li>
		</c:if>
		<li style="border: white;background: white;border-bottom:2px solid #1890FF"><a href="${ctx}/chasEt/chasRygjController/goRygjFqMap?use=baq&ryid=${ryxx.id}&rybh=${ryxx.rybh}&gjlb=${gjlb}&gjsp=${gjsp}" style="color: black;">轨迹地图</a></li>
	</ul>
	<ul style="float: right;font-size: 20px;height: 76%;margin-left: 45%;">
		<a>姓名</a>
		<a style="margin-right: 15px;">${ryxx.ryxm}</a>
		<a>进入时间</a>
		<a style="margin-right: 15px;">${rssj}</a>
		<a>离开时间</a>
		<a style="margin-right: 15px;">${cssj}</a>
		<a>停留时长</a>
		<a style="margin-right: 15px;">${tlsc}</a>
	</ul>
</div>--%>
<div style="position:relative;width:100%;height:100%;background:rgba(211,211,211,0.6);">
	<%--场景画布--%>
	<div id="stage" class="stage"  style="position:relative;float:left;width:100%;background:rgba(211,211,211,0.0);"></div>
	<div class="commands" style="z-index:1000l;position:relative;float:right;width:20%;height:100%;background:#ffffff">
		<%--<li class="pdrb32" style="margin: 10px;height: 8%">
			<label for="kssj" style="font-size: 20px;float: left">时间</label>
			<label style="font-size: 20px;float: right;margin-right: 25px;margin-bottom: 10px;">
				<div class="clearfix submit-btns">
					<input type="button" value="查询" class="btn blue-1" style="width: 70px;background: #1890FF;border: #1890FF;" onclick="queryDate()">
					<input type="button" value="重置" class="btn blue-1" style="width: 70px;background: white;border: 1px solid #a4a4a7;color: black!important;" onclick="clearDate()">
				</div>
			</label>
			<div class="ipt-item">
				<input type="text" id="kssj" class="common-ipt date-ipt" value="" placeholder="请选择开始/截止时间" style="width: 340px;">
				<input type="hidden" id = "kssj1" name="kssjStart">
				<input type="hidden" id = "kssj2" name="jssjEnd">
			</div>
		</li>--%>
		<input type="button" value="Hide Track" onclick="hideTrack()">
		<input type="button" value="Show Track" onclick="showTrack()">
		<br>
		<input type="button" value="Show Full Path" onclick="showFullPath()">
		<input type="button" value="Clear Full Path" onclick="clearFullPath()">
		<br>
		<input type="button" value="Add Room Track" onclick="addRoomTrack()">
		<br>
		<input type="button" value="Clear Track" onclick="clearPath()">
		<input type="button" value="Reload Track" onclick="reloadPath()">
		<br>
		<%--<div id="gjList" style="overflow:auto;height: 85%">
		<c:forEach items="${gjData}" var="gjxx" varStatus="gjStatus">
			<div id="${gjxx.id}" class="dateShow" style="margin: 10px;">
				${gjxx.kssj}<br>
				<div id="${gjStatus.index}" style="background:#d3d3d37d;margin:2px;font-size: 15px;" onclick="showAreaPath(${gjStatus.index})">
					<div style="margin-bottom: 10px;padding-top: 10px;padding-left: 10px;">所在区域：${gjxx.qymc}</div>
					<div style="padding-left: 10px;padding-bottom: 10px;">停留时长：${gjxx.tlsc}</div>
				</div>
			</div>
		</c:forEach>
		</div>--%>
	</div>
</div>
<script>

//办案区全定位轨迹规划,需预先编辑
let points_data = {
        	'xydzx':	//机构代码
			{
				//定位点、关键点（避免穿墙的路线途经点）：坐标，场景中的相对offset值，区域编码
				vArray:[
					{x:517,y:382,text:"0",areaNo:'202305161725156BF4D6A51D6A08A936'},//登记区
					{x:467,y:354,text:"1",areaNo:'Q_DJQM_1'},//
					{x:523,y:241,text:"2",areaNo:'20230516172747AB7FC62BD4C7984943'},//看守区
					{x:646,y:224,text:"3",areaNo:'2023051617280144E63CA53C74C40A19'},//辨认室
					{x:473,y:138,text:"4",areaNo:'20230516172534A1DB6890E1E1081A4E'},//等候室1
					{x:518,y:138,text:"5",areaNo:'202305161726030F2EB7A896D426EC99'},//2
					{x:549,y:142,text:"6",areaNo:'2023051617261024A1EB40B73A56705C'},//3
					{x:626,y:132,text:"7",areaNo:'2023051617261643E60DE2DF2D9BC539'},//4
					{x:451,y:168,text:"8",areaNo:'Q_NJSM_1'},//
					{x:416,y:139,text:"9",areaNo:'202305161728179C84A9A226267B04D8'},//尿检室
					{x:455,y:204,text:"10",areaNo:'Q_Q_1'},//
					{x:368,y:250,text:"11",areaNo:'20230626165702981D90861E63F7620D'},//询问室全局
					{x:386,y:381,text:"12",areaNo:'JF'},//机房(无)
					{x:323,y:380,text:"13",areaNo:'20230516172642D417C851120793BDA1'},//讯/询问室1
					{x:266,y:251,text:"14",areaNo:'Q_Q_3'},//
					{x:235,y:212,text:"15",areaNo:'Q_Q_4'},//
					{x:220,y:290,text:"16",areaNo:'Q_Q_5'},//
					{x:344,y:207,text:"17",areaNo:'Q_Q_6'},//
					{x:225,y:372,text:"18",areaNo:'202305161726492F70A7752D8E90D95B'},//讯/询问室2
					{x:132,y:319,text:"19",areaNo:'20230516172705649A24A03299ED407D'},//远程审讯室
					{x:158,y:206,text:"20",areaNo:'202305161727164559C49BEFF01EE5E9'},//讯/询问室4
					{x:335,y:143,text:"21",areaNo:'20230516172727F24F3BAF0B32B855B4'},//快办室
					{x:576,y:157,text:"22",areaNo:'2023062616562893180C60529B57549A'},//醒酒区
				],
				//轨迹点间连线，即路径：【起点下标，终点，线长】//[37,38,1]
				eArray:[
					[0,1,1],[1,2,1],[2,3,1],[2,4,1],[2,5,1],[2,6,1],[2,8,1],[8,9,1],
					[2,10,1],[10,11,1],[11,12,1],[11,13,1],[11,14,1],[14,15,1],[14,16,1],[11,17,1],
						[14,18,1],[16,19,1],[15,20,1],[17,21,1],[2,22,1],[22,7,1]
			    ],
			//可能多人会同时所在的区域，一般用于定义等候室
			rooms:{
				"202305161725156BF4D6A51D6A08A936":{x:496,y:363,width:50,height:22}//区域的左上角坐标，区域大小
				// "Q_DJQM_1":{x:452,y:337,width:50,height:22}//区域的左上角坐标，区域大小
				}
			},
}
</script>
<script type="text/javascript">
	let ctx = '${ctx}';
	let spark2D;
	let spark2DLive;
	let ryxm = '${ryxm}';
		//非常重要，spark2D和live都需要依赖pointInfos 中坐标和areaNo的对应关系，务必保证数据的正确
		//这里需要根据orgCode从areaPoints中获取点位坐标数据
		let orgCode = 'xydzx';//地图名字
		/*points_data[orgCode].vArray.forEach(function (i,o) {

		})*/

	var h=$("html").outerHeight();
	var w=$("html").outerWidth();

		let pointInfos = new PointInfos(points_data,orgCode);
		let settingWidths = "780";
		let settingHeight = "550";
		let percentWidths = "1";
		let percentHeight = "1";
		let realWidths = "780";
		let realHeight = "550";
		percentWidths = settingWidths/realWidths;
		percentHeight = settingHeight/realHeight;
		// console.log(percentWidths+"---"+percentHeight);
		// console.log(points_data);
		var vArray = points_data[orgCode].vArray
		for (let i in vArray) {
			/*var x = vArray[i].x;
			var y = vArray[i].y;
			console.log(x+"---"+y);*/
			vArray[i].x = vArray[i].x*percentWidths;
			vArray[i].y = vArray[i].y*percentHeight;
		}
		//创建场景
		spark2D=new Spark2D(
			{
				id:"stage",//屏幕画布的id，
                width:settingWidths+"px",//画布宽度
                height:settingHeight+"px",//画布的宽
                margin:"0 0 0 0", //画布相对于容器的位置调整
                pointInfos:pointInfos,//areaNo对应的点位信息
				isDrawTrackTitle:true,
                backgroundImage:ctx+'/static/chas/bigscreen/xydzx/images/bg.png',   //场景地图
				//点击场景内坐标的回调函数，cameraIndex为点的坐标信息
				onVertexClick:function(cameraIndex)
				{
					//在vertexArray中的下标，点击范围在5个像素距离之内的,即图中顶点处的圆点
					//值为-1时，为空事件，没有点击在项点上
					console.log("camera:"+cameraIndex)
				}
			}
		);
		//开启场景渲染
		spark2D.animate();


		
		let historyPoints=[];
		var gjData = eval('(' + gjDataObj + ')');
		// console.log(gjData);
		for (let i = 0; i < gjData.length; i++) {
			var sz ={
				areaNo:gjData[i].qyid,
				text:''
			}
			// console.log(sz);
			historyPoints.push(sz);
		}
		/*for (let gj in gjData) {
			var sz ={
				areaNo:gj.qyid,
				text:gj.kssj
			}
			console.log(sz);
			historyPoints.push(sz);
		}*/
		// console.log(historyPoints);

		// let trackPoints=pointInfos.getFullPathArrayByPoints(historyPoints);

		//创建人员 
		let avatar2D=new Avatar2D({
                        id:ryxm,														//人员姓名
                        startRoom:historyPoints[0].areaNo,			//开始所在区域编号
                        // startRoom:historyPoints[historyPoints.length-1].areaNo,			//开始所在区域编号
						startRoomText:"",   										//开始区域的标示文本信息
                        size:[16,20],													//人物图标尺寸
                        kenType:"21",													//人物图标类型，21--男性人员， 22--女性人员
						trackPoints:[],										//历史轨迹区域信息
                        trackColor:'#ff0000',											//轨迹线颜色
					    trackPointRadius:4,											    //轨迹（定位）点半径
						trackLineWidth:1,												//轨迹点宽
						isDrawTrack:true,												//是否画轨迹线
 						isDrawTrackTitle:true,											//是否显示轨迹关键点（区域）文本信息
                        onClick:function(avatar){										//点击人物图标时的回调方法
                            if(avatar.isTrackShowed()) avatar.hideTrack();				//显示或隐藏轨迹线	
                            else avatar.showTrack();
                        }
                    });
       spark2D.addAvatar(avatar2D);				 				 						//在场景中加入人员
		// avatar2D.moveToRoomByTrack(gjData[1].qyid,"");

	// spark2D.drawFullTrack(pointInfos.vArray,pointInfos.eArray,"black",5,2);

	   //动态把人员移动到下一下区域，该方法会自动启动人物移动动画，把人物从当前点移动到该抒写点
		// for (let i = gjData.length-1; i >= 0; i--) {
		// 	avatar2D.moveToRoomByTrack(gjData[i].qyid,"");
		// }
		for (let i = 1; i < gjData.length; i++) {
			avatar2D.moveToRoomByTrack(gjData[i].qyid,"");
		}
	/*avatar2D.moveToRoomByTrack("aca7ea847273512d01727d1e3362003e","");
    avatar2D.moveToRoomByTrack("aca7ea84775bae22017766057cbf000a","");
    avatar2D.moveToRoomByTrack("aca7ea84730e58c501730e91e37d0044","");
    avatar2D.moveToRoomByTrack("aca7ea84758d58d40175978df2ec0118","");*/
	  // avatar2D.moveToRoomByTrack("aca7ea84775bae22017766057cbf000a","12:06:00");

	  //在场景中加入更多人员
	   /*let avatar2D2=new Avatar2D({
                        id:"王",
                        startRoom:"aca7ea847273512d0172798538f30011",
                        size:[16,20],
                        kenType:"22",
                        trackColor:'#ff0000',
						isDrawTrack:true
                    });

       spark2D.addAvatar(avatar2D2);


	   let avatar2D3=new Avatar2D({
                        id:"张",
                        startRoom:"aca7ea847273512d0172798538f30011",
                        size:[16,20],
                        kenType:"22",
                        trackColor:'#ff0000',
						isDrawTrack:true
                    });

       spark2D.addAvatar(avatar2D3);

	   let avatar2D4=new Avatar2D({
                        id:"T0",
                        startRoom:"aca7ea847273512d0172798538f30011",
                        size:[16,20],
                        kenType:"22",
                        trackColor:'#ff0000',
						isDrawTrack:true
                    });

       spark2D.addAvatar(avatar2D4);
	   let avatar2D5=new Avatar2D({
                        id:"T1",
                        startRoom:"aca7ea847273512d0172798538f30011",
                        size:[16,20],
                        kenType:"22",
						//trackPoints:trackPoints,
                        trackColor:'#ff0000',
						isDrawTrack:true
                    });

       spark2D.addAvatar(avatar2D5);

	   let avatar2D6=new Avatar2D({
                        id:"T2",
                        startRoom:"aca7ea847273512d0172798538f30011",
                        size:[16,20],
                        kenType:"22",
						//trackPoints:trackPoints,
                        trackColor:'#ff0000',
						isDrawTrack:true
                    });

       //spark2D.addAvatar(avatar2D6);

	   let avatar2D7=new Avatar2D({
                        id:"Test",
                        startRoom:"aca7ea847273512d0172798538f30011",
						startRoomText:"11:50:59",
                        size:[16,20],
                        kenType:"22",
						//trackPoints:trackPoints,
                        trackColor:'#00ff00',
						isDrawTrack:true,
						isDrawTrackTitle:true,
						onClick:function(avatar){
                            if(avatar.isTrackShowed()) avatar.hideTrack();
                            else avatar.showTrack();
                        }
                    });
       spark2D.addAvatar(avatar2D7);*/

	   //avatar2D7.moveToRoomByTrack("b56a80e67d55ed2d017d55ff04720024")

	/*$(function () {
		spark2D.drawFullTrack(pointInfos.vArray,pointInfos.eArray,"black",5,2);
	});*/


	//清除人员轨迹	
	function clearPath()
	{
		avatar2D.clearTrack();
	}
	//重绘人员全部轨迹
	function reloadPath()
	{
		avatar2D.drawHistoryTrack();
	}

	var lastIndex = '-1';
	//显示一段临时路径
	function showAreaPath(index)
	{
		$("#"+lastIndex).removeClass("activeMap");
		if(lastIndex == index){
			lastIndex = '-1';
			reloadPath();
		}else{
			lastIndex = index;
			avatar2D.clearTrack();
			let ShowPoints=[];
			ShowPoints.push(historyPoints[index]);
			ShowPoints.push(historyPoints[index+1]);
			$("#"+index).attr("class","activeMap")
			let trackPoints=pointInfos.getFullPathArrayByPoints(ShowPoints);
			avatar2D.drawTrack(trackPoints,true,true);
		}
	}

	function showTracks()
	{
		console.log(avatar2D7.trackPoints);
		console.log(avatar2D7.painedTrackPoints);
	}

	// 显示全路径规划图
    function showFullPath()
	{
            spark2D.drawFullTrack(pointInfos.vArray,pointInfos.eArray,"black",5,2);
	}
    function clearFullPath()
	{
            spark2D.clearFullTrack();
	}


	let avaPos=5;
	//动态增加下一个区域
	function addRoomTrack() {
       avaPos++;
	   avatar2D7.moveToRoomByTrack(pointInfos.vArray[avaPos].areaNo,"12:00:00");
	}

	//隐藏所有人物轨迹
	function hideTrack() {
		for(var i=0;i<spark2D.avatarArray.length;i++)spark2D.avatarArray[i].hideTrack();
	}

	//显示所有人物轨迹
	function showTrack() {
		for(var i=0;i<spark2D.avatarArray.length;i++)spark2D.avatarArray[i].showTrack();
	}
	
	/*function imageSet() {

	}*/
</script>

</body>
</html>