$(function(){
		/*var filesInfo = "${filesInfo}";
		$(".attachment").html('');
		for(var i in filesInfo){
			var div = $("<div></div>");
			div.append("<font color='blue'>■ </font>");
			div.append("<a title='下载' href='"+filesInfo[i].downUrl+"'>"+filesInfo[i].name+"</a>");
			$(".attachment").append(div);
		}*/
		
		//签收按钮
		$("#sign").bind('click',function(){
			$.ajax({
				type:"GET",
				url:ctx+'/rmd/notice/sign/'+id,
				dataType:"json",
				success:function(data){
						$("#sign").attr("disabled",true);
					}
			});
		});
	});