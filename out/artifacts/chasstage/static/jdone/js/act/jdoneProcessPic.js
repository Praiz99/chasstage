var index = 1;
var maxRy = 0;
var maxRx = 0;
var createdNodes = new Array();
$(document).ready(function() {
	var modelId = $("#modelId").val();
	$.ajax({
		type: "post",
		url: ctx+"/act/actInstance/getProcessPic?modelId=" + modelId,
		dataType: 'json',
		cache: false,
		success: function(data) {
			initProcessPic(data);
		}
	});
});

//初始化流程图
function initProcessPic(data){
	setProcessCon(data);
	printProcessPic();
}

//设置流程连接
function setProcessCon(data){
	for ( var i = 0; i < data.length; i++) {
		if(data[i].id == $("#nodeId").val()){
			$("body").append('<div id="item' + index + '" class="item now" data-item=\'{"nodeId":"' + index + '","nextNode":""}\' title="' + data[i].actNodeName + '">' + data[i].actNodeName + '</div>');
		}else{
			$("body").append('<div id="item' + index + '" class="item" data-item=\'{"nodeId":"' + index + '","nextNode":""}\' title="' + data[i].actNodeName + '">' + data[i].actNodeName + '</div>');
		}
		data[i].gIndex = index;
		if(data[i].transNodeId == null){
			var nextNode = $.parseJSON($("#item0").attr("data-item")).nextNode;
			$("#item0").attr("data-item",'{"nodeId":"0","nextNode":"' + (nextNode == "" ? nextNode : nextNode + ',') + index + '"}');
		}
		index++;
	}
	for ( var j = 0; j < data.length; j++) {
		var transNodeIds = data[j].transNodeId==null?null:data[j].transNodeId.split(',');
		for ( var k = 0; k < data.length; k++) {
			if((transNodeIds != null && transNodeIds.length > 0 && $.inArray(data[k].id, transNodeIds) > -1)){
				var nextNode = $.parseJSON($("#item" + data[k].gIndex).attr("data-item")).nextNode;
				var nodeId = $.parseJSON($("#item" + data[k].gIndex).attr("data-item")).nodeId;
				$("#item" + data[k].gIndex).attr("data-item",'{"nodeId":"' + nodeId + '","nextNode":"' + (nextNode == "" ? nextNode : nextNode + ',') + data[j].gIndex + '"}');
			}
		}
		if(data[j].isApprovalNode == 1){
			var nextNode = $.parseJSON($("#item" + data[j].gIndex).attr("data-item")).nextNode;
			var nodeId = $.parseJSON($("#item" + data[j].gIndex).attr("data-item")).nodeId;
			$("body").append('<div id="item' + index + '" class="item startEnd" data-item=\'{"nodeId":"' + index + '","nextNode":""}\' title="结束">结束</div>');
			$("#item" + data[j].gIndex).attr("data-item",'{"nodeId":"' + nodeId + '","nextNode":"' + (nextNode == "" ? nextNode : nextNode + ',') + index + '"}');
			index++;
		}
	}
	//生成结束节点
	var allItemList = $(".item");
	for ( var endIndex = 0; endIndex < allItemList.length; endIndex++) {
		var endNodeStr = $.parseJSON($(allItemList[endIndex]).attr("data-item")).nextNode;
		var endIdStr = $.parseJSON($(allItemList[endIndex]).attr("data-item")).nodeId;
		var endNodeClass = $(allItemList[endIndex]).attr("class");
		if(endNodeClass != "item startEnd" && endNodeStr == ""){
			$("body").append('<div id="item' + index + '" class="item startEnd" data-item=\'{"nodeId":"' + index + '","nextNode":""}\' title="结束">结束</div>');
			$("#item" + endIdStr).attr("data-item",'{"nodeId":"' + endIdStr + '","nextNode":"' + index + '"}');
			index++;
		}
	}
}

//生成流程图
function printProcessPic(){
	//用来存储节点的顺序
    var connections = [];
    //拖动节点开始时的事件
    var dragger = function () {
        this.ox = this.attr("x");
        this.oy = this.attr("y");
        this.animate({ "fill-opacity": 0.2 }, 500);
    };
    //拖动事件
    var move = function (dx, dy) {
        var att = { x: this.ox + dx, y: this.oy + dy };
        this.attr(att);
        $("#item" + this.id).offset({ top: this.oy + dy + 15, left: this.ox + dx -20 });
        for (var i = connections.length; i--;) { r.drawArr(connections[i]); }
    };
    //拖动结束后的事件
    var up = function () { this.animate({ "fill-opacity": 0 }, 500); };
    //创建绘图对象
    var r = Raphael("holder", $(window).width(), $(window).height());
    //定义元素坐标高宽
    var $nodeList = $(".item"); //节点集合jquery对象
    var _x, _w, _h;
    _x = 250; _h = 60; _w = 40;
    var shapes = new Object(); //节点集合
    var pyNodeList = new Array();
    //存储节点间的顺序
    $nodeList.each(function (i, item) {
         var nextNodeList = $.parseJSON($(item).attr("data-item")).nextNode.split(",");
         if(nextNodeList.length>1){
         	pyNodeList.push(nextNodeList[0]);
         }
    });
    startNodeCon(0, $("#item0"), 0, -30);
    
    //存储节点间的顺序
    $nodeList.each(function (i, item) {
        //节点json数据
        var data_item = $.parseJSON($(item).attr("data-item"));
        if (data_item.nextNode) {
        	var nextNodeList = data_item.nextNode.split(",");
        	for ( var i = 0; i < nextNodeList.length; i++) {
            	connections.push(r.drawArr({ obj1: shapes[data_item.nodeId], obj2: shapes[nextNodeList[i]] }));
			}
        }
    });
	$("svg").css("height", maxRy + 70);
	$("svg").css("width", maxRx + 400);
	
	$(".startEndRect").each(function (ind, seNode) {
		$(seNode).attr("stroke-width", 3);
	});
	
	//设置流程图各节点位置
	function startNodeCon(index, item, fzNum, lastRy) {
		_w = $(item).width()-40; //元素的宽
		_h = $(item).height()+20; //元素的高
		var r_y = _h + 50 + lastRy;
		if(maxRy < r_y) {
			maxRy = r_y;
		}
		//节点json数据
		var other_x = 250 * fzNum;
		if(maxRx < _x + other_x) {
			maxRx = _x + other_x;
		}
		var data_item = $.parseJSON(item.attr("data-item"));
		shapes[data_item.nodeId] = r.rect(_x + other_x, r_y, _w, _h, 4);
		$(item).offset({ top: r_y + 15, left: _x  + other_x - 20 });
		//为节点添加样式和事件，并且绘制节点之间的箭头
		var color = "gray";
		var rectType = "simple";
		if(item.attr("class") == "item now"){
			color = "red";
		}else if(item.attr("class") == "item startEnd"){
			rectType = "startEndRect";
		}
		shapes[data_item.nodeId].attr({ fill: color, stroke: color, "fill-opacity": 0, "stroke-width": 2, cursor: "move", "class": rectType });
		shapes[data_item.nodeId].id = data_item.nodeId;
		shapes[data_item.nodeId].drag(move, dragger, up); //拖动节点事件
		shapes[data_item.nodeId].attr("title", item.title);
		item.innerHTML = data_item.nodeId + item.innerHTML;
		
		var nextNodeList = data_item.nextNode.split(",");
		for ( var s = 0; s < nextNodeList.length; s++) {
			if(nextNodeList[s] != "" && $.inArray(nextNodeList[s], createdNodes) == -1){
				createdNodes.push(nextNodeList[s]);
				index++;
				startNodeCon(index, $("#item" + nextNodeList[s]), s + fzNum, r_y);
			}
		}
	};
}

//随着节点位置的改变动态改变箭头
Raphael.fn.drawArr = function (obj) {
    var point = getStartEnd(obj.obj1, obj.obj2);
    var path1 = getArr(point.start.x, point.start.y, point.end.x, point.end.y, 8);
    if (obj.arrPath) {
        obj.arrPath.attr({ path: path1 });
    } else {
        obj.arrPath = this.path(path1);
    }
    return obj;
};

function getStartEnd(obj1, obj2) {
    var bb1 = obj1.getBBox(),
        bb2 = obj2.getBBox();
    var p = [
            { x: bb1.x + bb1.width / 2, y: bb1.y - 1 },
            { x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1 },
            { x: bb1.x - 1, y: bb1.y + bb1.height / 2 },
            { x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2 },
            { x: bb2.x + bb2.width / 2, y: bb2.y - 1 },
            { x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1 },
            { x: bb2.x - 1, y: bb2.y + bb2.height / 2 },
            { x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2 }
    ];
    var d = {}, dis = [];
    for (var i = 0; i < 4; i++) {
        for (var j = 4; j < 8; j++) {
            var dx = Math.abs(p[i].x - p[j].x),
                dy = Math.abs(p[i].y - p[j].y);
            if (
                 (i == j - 4) ||
                 (((i != 3 && j != 6) || p[i].x < p[j].x) &&
                 ((i != 2 && j != 7) || p[i].x > p[j].x) &&
                 ((i != 0 && j != 5) || p[i].y > p[j].y) &&
                 ((i != 1 && j != 4) || p[i].y < p[j].y))
               ) {
                dis.push(dx + dy);
                d[dis[dis.length - 1]] = [i, j];
            }
        }
    }
    if (dis.length == 0) {
        var res = [0, 4];
    } else {
        res = d[Math.min.apply(Math, dis)];
    }
    var result = {};
    result.start = {};
    result.end = {};
    result.start.x = p[res[0]].x;
    result.start.y = p[res[0]].y;
    result.end.x = p[res[1]].x;
    result.end.y = p[res[1]].y;
    return result;
}

//获取组成箭头的三条线段的路径
function getArr(x1, y1, x2, y2, size) {
    var angle = Raphael.angle(x1, y1, x2, y2); //得到两点之间的角度
    var a45 = Raphael.rad(angle - 45); //角度转换成弧度
    var a45m = Raphael.rad(angle + 45);
    var x2a = x2 + Math.cos(a45) * size;
    var y2a = y2 + Math.sin(a45) * size;
    var x2b = x2 + Math.cos(a45m) * size;
    var y2b = y2 + Math.sin(a45m) * size;
    var result = ["M", x1, y1, "L", x2, y2, "L", x2a, y2a, "M", x2, y2, "L", x2b, y2b];
    return result;
}

