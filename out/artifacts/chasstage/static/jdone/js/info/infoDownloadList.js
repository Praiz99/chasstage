$(function(){
	$.ajax({
		url: ctx+"/info/getCatDownloadData",
		type:'POST',
		dataType: 'json',
		cache: false,
		success:function(data){
			var catData = eval(data);//json转为数组
			$.ajax({
				url: ctx+"/info/getInfoDownloadData",
				type:'POST',
				dataType: 'json',
				cache: false,
				success:function(data){
					var infoData = eval(data);//json转为数组
					var softDownloadDiv = '';
					$.each(catData, function (catIndex, catItem) {
						softDownloadDiv += '<div class="download_box">';
						var flag = 0;
						var isFold = 0;
						var i = 0;
						var softPartDiv = '';
						$.each(infoData, function (infoIndex, infoItem) {
							if(catData[catIndex].id == infoData[infoIndex].catId){
								flag = 1;
								if(i%3 == 0 && i < 6){
									softPartDiv += '<div class="floor_layer">';
								}else if(i%3 == 0 && i >= 6){
									softPartDiv += '<div class="floor_layer" style="display:none;" id="'+ catData[catIndex].id +'">';
									isFold = 1;
								}
								
								if(i%3 == 2){
									softPartDiv += '<a href="javascript:;" onclick="downloadInfo(\''+ infoData[infoIndex].versionId +'\')">' + infoData[infoIndex].name + infoData[infoIndex].version + '</a></div>';	
								}else{
									softPartDiv += '<a href="javascript:;" style="margin-right:150px;" onclick="downloadInfo(\''+ infoData[infoIndex].versionId +'\')">' + infoData[infoIndex].name + infoData[infoIndex].version + '</a>';
								}
								i++;
							}
			             });
						if(flag == 0){
							softDownloadDiv += '</div></div>';
						}else{
							if(isFold == 0){
								softDownloadDiv += '<div class="top_layer"><span>' + catData[catIndex].name + '</span></div>';
							}else{
								softDownloadDiv += '<div class="top_layer"><span>' + catData[catIndex].name + '<a style="margin-left:595px;font-size:14px;color:#5182D6;cursor:pointer;" onclick=openMore("'+ catData[catIndex].id +'")>更多</a></span></div>';
							}
							softDownloadDiv += softPartDiv;
							softDownloadDiv += '</div></div>';
						}
		             });
					$("#soft_box").html(softDownloadDiv);
				}
			});
		}
	});
});

function downloadInfo(id){
		$.ajax({
		type : "post",
		url : ctx + "/com/frws/getFilesInfoByBizId?bid=" + id
				+ "&btype=RELFILE",
		dataType : 'json',
		cache : false,
		success : function(data) {
			if (data.length > 0) {
				window.location.href = data[0].downUrl;
			} else {
				$.messager.alert("系统提示", "暂无程序包下载!");
				return false;
			}
		}
	});
}

function openMore(catId){
	$(".floor_layer").each(function(){
		if($(this).attr("id") == catId){
			if($(this).is(':hidden')){
				$(this).attr("style","");
			}else{
				$(this).attr("style","display:none;");
			}
		}
	});
}