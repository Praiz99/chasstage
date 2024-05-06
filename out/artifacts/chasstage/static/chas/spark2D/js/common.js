$(function() {
	//查找，删除按钮统一样式
	var buttonSaC = $(".buttonSac");
	if(buttonSaC != null){
		addSaCButton(buttonSaC);
	}
	var mainHight=0;
    if($("#main").height()>0)mainHight=Number($("#main").height())+20;
	/*var buttonHeight=$(".buttonSac").height();*/
	var fullHeight=$(document).height()-18;
	if(mainHight!=null&&mainHight!=undefined){
		if(fullHeight>mainHight){
			fullHeight=fullHeight-mainHight;
		}
	}
/*	if(buttonHeight!=null&&buttonHeight!=undefined){
		fullHeight=Number(fullHeight)-Number(buttonHeight)-35;
	}*/
	$("#datagrid").height(Number(fullHeight));
});

/** 
从视频列表页面跳转到VLC播放页面
注意：
1.本方法目前只适用于4个视频管理页面，其它页面未经测试
2.页面表格ID固定为：datagrid, 搜索表单名固定为：searchForm
index         -- 当前视频表格的序号
backUrl     -- 当前页的URL，用于下一个页面返回到当前页面

2018-01-23 Xing Add ...

*/
function playMedia(index,backUrl) 
{
    $('#datagrid').datagrid('selectRow', index);
	var row = $('#datagrid').datagrid('getSelected');	
    var url;
	url = ctx + "/media/file/play?id="+row.mediaId;
    forward(url,backUrl,"datagrid","searchForm") ;
}

function downloadMedia(index,backUrl) 
{
    $('#datagrid').datagrid('selectRow', index);
	var row = $('#datagrid').datagrid('getSelected');	
    var url;
	url = ctx + "/media/file/download?id="+row.mediaId;
    forward(url,backUrl,"datagrid","searchForm");
}

function forwardTo(forwardUrl,back){
    forward(forwardUrl,back,"datagrid","searchForm") ;
}
    
    /**
 
跳转到下一个页面,并带去返回的URL及参数以保持返回时当前页面的状态

url         -- 下一个要跳转到的页面
datagridId  -- 当前EasyUI的datagrid表格ID
formId      -- 当前列表页datagrid上的相关搜索Form表单
backUrl     -- 当前页的URL，用于下一个页面返回到当前页面

2018-01-23 Xing Add ...

*/

function forward(url,backUrl,datagridId,formId) 
{
    if(datagridId)
    {
        var grid = $('#'+datagridId);  
        var options = grid.datagrid('getPager').data("pagination").options;  
        var page = options.pageNumber;  
        var total = options.total;  
        var max = Math.ceil(total/options.pageSize);  
        var rows = options.pageSize;
    }

    var backUrlParams=null;
    if(page)backUrlParams="page="+page+"&rows="+rows;
    if(formId)
    {
        var formParams=$("#"+formId).find(":input").serialize()
        if(backUrlParams)backUrlParams=backUrlParams+"&"+formParams;
        else backUrlParams=formParams;
    }

    var body = $(document.body),
    form = $("<form method='post'></form>"),
    input;
    form.attr({"action":url});
    input = $("<input type='hidden'>");
    input.attr({"name":"back"});
    input.val(backUrl);
    form.append(input);
    input = $("<input type='hidden'>");
    input.attr({"name":"backParams"});
    if(backUrlParams && backUrlParams!="")input.val(backUrlParams);
    form.append(input);
    form.appendTo(document.body);
    form.submit();
    document.body.removeChild(form[0]);
}


String.prototype.trim=function(){
　　return this.replace(/(^\s*)|(\s*$)/g, "");
 }

$.fn.serializeObject = function() {  
    var o = {};  
    var a = this.serializeArray();  
    $.each(a, function() {  
        if (o[this.name]) {  
            if (!o[this.name].push) {  
                o[this.name] = [ o[this.name] ];  
            }  
            if(this.value && this.value.trim()!='')o[this.name].push(this.value);  
        } else {  
            if(this.value && this.value.trim()!='') o[this.name] = this.value;  
        }  
    });  
    return o;  
}; 

//add by zhou for judge file exist
function isExist(mediaId,fun){
	$.ajax({
		type : "post",
		url : ctx + "/media/file/isExist?mediaId=" + mediaId,
		dataType : 'json',
		cache : false,
		success : function(data) {
			fun(data);
		}
	});
}

function fitWidth(tableName,layoutWidth){
    var fullWidth=$(document).width();

    //var fullWidth=$('#'+tableName).width();
    if(layoutWidth)fullWidth-=parseInt(layoutWidth);
	  var obj = $('#'+tableName).datagrid('options').columns[0];
	  var total=obj.length;
	  var fitNum=0;
	  var totalWidth=0;
	  var fixWidth=new RegExp('px');
	  for(var i=0;i<total;i++){
		    var w=obj[i].width;
		    var checkBox=obj[i].checkbox;
		    var hidden=obj[i].hidden;
    		if(checkBox==undefined&&hidden==undefined){
    		  if(!fixWidth.test(w)){
    			  fitNum+=1;
    		  }else if(hidden!=false){
    			  totalWidth+=Number(w.replace('px',''));
    		  }
    		}
	  }
	  var remain=(fullWidth-totalWidth-(total+1)*2-67)/fitNum;
	  for(var j=0;j<total;j++){
		  var w1=obj[j].width;
		  var checkBox=obj[j].checkbox;
		  if(checkBox==undefined){
        	  if(!fixWidth.test(w1)){
        		  var fitWit=remain+"px";
        		  $("td[field='"+obj[j].field+"'] div").width(fitWit);
    	      }
		 }
	  }
}


function setWidth(fieid,width){
   $("td[field='"+fieid+"'] div").width(width);
}

function toPoint(percent){
    var str=percent.replace("%","");
    str= str/100;
    return str;
}

//添加查找和删除按钮
function addSaCButton(button){
	button.attr("style","padding-left: 0px;margin-top: 10px;margin-bottom: 10px;");
	button.attr("align","center");
	button.prepend('<a id="clear" class="button button-glow button-rounded button-caution button-small" onclick="ClearQuery();">清空 <i class="fas fa-trash-alt"></i></a>');
	button.prepend('<a id="keydownSearch" class="button button-glow button-rounded button-primary button-small" onclick="searchFunc();">查找 <i class="fas fa-search"></i></a>&nbsp&nbsp');
//	<input id="keydownSearch" class="btn btn-primary" type="button" value="查找" onclick="searchFunc();">&nbsp;
//	<input id="clear" class="btn" type="button" value="清空" onclick="ClearQuery();">
}

/**************************************时间格式化处理************************************/
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
} 

function removeOrgNamePrefix(value){
	var result=value;
	var prefix="浙江省公安厅";
	if(value!=null&&value.indexOf(prefix)!= -1){
		result=value.replace(prefix,"");
	}
    return result;
}
//计算字体宽度
function textSize(fontSize,fontFamily,text){
    var span = document.createElement("span");
    var result = {};
    result.width = span.offsetWidth;
    result.height = span.offsetHeight;
    span.style.visibility = "hidden";
    span.style.fontSize = fontSize;
    span.style.fontFamily = fontFamily;
    span.style.display = "inline-block";
    document.body.appendChild(span);
    if(typeof span.textContent != "undefined"){
        span.textContent = text;
    }else{
        span.innerText = text;
    }
    result.width = parseFloat(window.getComputedStyle(span).width) - result.width;
    result.height = parseFloat(window.getComputedStyle(span).height) - result.height;
    return result;
}