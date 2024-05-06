
/**
 * 设置简拼(例如将'应用系统'转换为'yyxt')-源和目标必须均为文本框控件,在页面中必须引入'lib/sundun.spell.js'
 * @param source 源控件的名称
 * @param target 目标控件的名称
 * @param validatorTxt 验证器的提示文本
 */
function setJP(source, target, validatorTxt){
	setPY(source, target, validatorTxt, true, false);
}

/**
 * 设置全拼(例如将'应用系统'转换为'yingyongxitong')-源和目标必须均为文本框控件,在页面中必须引入'lib/sundun.spell.js'
 * @param source 源控件的名称
 * @param target 目标控件的名称
 * @param validatorTxt 验证器的提示文本
 */
function setQP(source, target, validatorTxt){
	setPY(source, target, validatorTxt, false, false);
}

/**
 * 强制设置简拼(例如将'应用系统'转换为'yyxt')-源和目标必须均为文本框控件,在页面中必须引入'lib/sundun.spell.js'
 * @param source 源控件的名称
 * @param target 目标控件的名称
 * @param validatorTxt 验证器的提示文本
 */
function setJPForce(source, target, validatorTxt){
	setPY(source, target, validatorTxt, true, true);
}

/**
 * 强制设置全拼(例如将'应用系统'转换为'yingyongxitong')-源和目标必须均为文本框控件,在页面中必须引入'lib/sundun.spell.js'
 * @param source 源控件的名称
 * @param target 目标控件的名称
 * @param validatorTxt 验证器的提示文本
 */
function setQPForce(source, target, validatorTxt){
	setPY(source, target, validatorTxt, false, true);
}

/**
 * 设置拼音(例如将'应用系统'转换为'yyxt')-源和目标必须均为文本框控件,在页面中必须引入'lib/sundun.spell.js'
 * @param source 源控件的名称
 * @param target 目标控件的名称
 * @param validatorTxt 验证器的提示文本
 * @param isJp 是否简拼
 * @param isForce 是否强制转换(如果是强制转换，则不管目标有没有值，都根据源控件的值转换拼音)
 */
function setPY(source, target, validatorTxt, isJp, isForce){
	var sourceControl = $('input[name=' + source + ']');
	var targetControl = $('input[name=' + target + ']');
	var sourceValue = sourceControl.val();
	var targetValue = targetControl.val();
	
	if(sourceValue != '' && ((targetValue == '' || targetValue == validatorTxt)) || isForce){
		var spellValue = getSpell(sourceValue, isJp);
		targetControl.val(spellValue);
	}
}

/**
 * 字典值模糊查询
 * @param codeSubstring	查询串
 * @param fieldName	查询属性名称
 * @param data	字典数据
 * @returns {Array}	代码值包含codeSubstring串的字典数据
 */
function codeBlurSearch(codeSubstring,fieldName,data){
	if(!data || data.length==0) return [];
	if(!codeSubstring || codeSubstring.length==0){
		alert("请设置查询子串:code、name、scode");
		return;
	}
	if(!fieldName || fieldName.length==0){
		alert("请设置查询字段名称");
		return;
	}	
	codeSubstring = $.trim(codeSubstring);
	
	var regx = [];
	regx.push('return ');
	regx.push('/');
	regx.push(".*?");
	for(var i=0;i<codeSubstring.length;i++){
		var c = codeSubstring.charAt(i);
		regx.push(c);            		
	}	
	regx.push(".*?");
	regx.push('/i.test(');
	regx.push('o["');
	regx.push(fieldName);
	regx.push('"]');
	regx.push(');');
	var funBody = regx.join("");	
	var fun = new Function("o",funBody);
	//过滤结果
	var result = [];
	for(var i in data){
		if(fun(data[i])){
			result.push(data[i]);
		}
	}
	
	return result;
}

/**
 * 查询以codeSubstring串开头的字典值
 * @param codeSubstring	查询串
 * @param fieldName	查询字段名称
 * @param data	字典数据
 * @returns {Array}	fieldName字段以codeSubstring串开头的字典数据
 */
function codeStartWithSearch(codeSubstring,fieldName,data){
	if(!data || data.length==0) return [];
	if(!codeSubstring || codeSubstring.length==0){
		alert("请设置查询子串");
		return ;
	}
	if(!fieldName || fieldName.length==0){
		alert("请设置查询字段:code、name、scode");
		return ;
	}
	
	codeSubstring = $.trim(codeSubstring);	
	var regx = [];
	regx.push('return ');
	regx.push('/');
	regx.push("^");
	for(var i=0;i<codeSubstring.length;i++){
		var c = codeSubstring.charAt(i);
		regx.push(c);            		
	}	
	regx.push(".*?");
	regx.push('/i.test(');
	regx.push('o["');
	regx.push(fieldName);
	regx.push('"]');
	regx.push(');');
	var funBody = regx.join("");	
	var fun = new Function("o",funBody);
	//过滤结果
	var result = [];
	for(var i in data){
		if(fun(data[i])){
			result.push(data[i]);
		}
	}
	
	return result;
}

/**
 * 字典值模糊查询
 * @param codeSubstring	代码值子串
 * @param fieldName	查询字段名称
 * @param dicName	字典名称
 * @returns	{Array} 代码值包含codeSubstring串的字典数据
 */
function dicBlurSearch(codeSubstring,fieldName,dicName){
	if(!dicName){
		alert("请设置字典名称或字典JS函数");
		return;
	}
	var data = null;
	//是否函数
	if(typeof(dicName)=='function'){
		data = dicName();
	}else{
		var isFun=eval("typeof "+dicName+" == 'function'");
		if(!isFun){
			alert("请设置正确的字典名称和引入相应的字典JS");
			return;
		}
		data = eval(dicName+"()");
	}
		
	return codeBlurSearch(codeSubstring,fieldName,data);
}

/**
 * 查询以codeSubstring串开头的字典值
 * @param codeSubstring	代码值子串
 * @param fieldName	查询字段名称
 * @param dicName	字典名称
 * @returns	{Array}	代码值包含codeSubstring串的字典数据
 */
function dicStartWithSearch(codeSubstring,fieldName,dicName){
	if(!dicName){
		alert("请设置字典名称或字典JS函数");
		return;
	}
	var data = null;
	//是否函数
	if(typeof(dicName)=='function'){
		data = dicName();
	}else{
		var isFun=eval("typeof "+dicName+" == 'function'");
		if(!isFun){
			alert("请设置正确的字典名称和引入相应的字典JS");
			return;
		}
		data = eval(dicName+"()");
	}
		
	return codeStartWithSearch(codeSubstring,fieldName,data);
}

/**
 * 字典代码值翻译
 * @param code	代码值
 * @param dicName	字典名称或字典js函数名称
 */
function dicTranslate(code,dicName){
	if(!dicName){
		alert("未设置字典名称或字典JS函数");
		return;
	}
	var data = null;
	//是否函数
	if(typeof(dicName)=='function'){
		data = dicName();
	}else{
		var isFun=eval("typeof "+dicName+" == 'function'");
		if(!isFun){
			alert("请设置正确的字典名称和引入相应的字典JS");
			return;
		}
		data = eval(dicName+"()");
	}
	if(!code || code.length==0){
		return "";
	}
	
	for(var i in data){
		if(code==data[i]['code']){
			return data[i]['name'];
		}
	}
}

/**
 * 字典值过滤
 * @param codeAry	待过滤串，以 "," 隔开
 * @param propName	待过滤的属性名称：code、name、scode
 * @param data	字典数据
 * @returns
 */
function codeFilter(codeAry,propName,data){
	if(!data || data.length==0) return [];
	if(!codeAry || codeAry.length==0){
		alert("请设置过滤串,以,隔开");
		return ;
	}
	if(!propName || propName.length==0){
		alert("请设置过滤字段:code、name、scode");
		return ;
	}
	for(var j=0;j<codeAry.length;j++){
		var codeSubstring = $.trim(codeAry[j]);	
		var regx = [];
		regx.push('return ');
		regx.push('/');
		regx.push("^");
		for(var i=0;i<codeSubstring.length;i++){
			var c = codeSubstring.charAt(i);
			regx.push(c);            		
		}	
		regx.push(".*?");
		regx.push('/i.test(');
		regx.push('o["');
		regx.push(propName);
		regx.push('"]');
		regx.push(');');
		var funBody = regx.join("");	
		var fun = new Function("o",funBody);
		//过滤结果
		for(var i in data){
			if(fun(data[i])){
				data.splice(i,1);
				break;
			}
		}
	}
	
	return data;
}

/**
 * 获取代码值以codeSubstring开头的所有代码
 * @param codeSubstring	代码值前缀
 * @param fieldName	字段名称
 * @param data	字典数据
 * @returns	以codeSubstring开头的字典代码
 */
function dicCodeStartWith(codeSubstring,fieldName,data){
	if(!codeSubstring || codeSubstring.length==0) return null;
	var codeStarts = codeSubstring.split(",");
	var result = new Array();
	for(var i in codeStarts){
		var dt = codeStartWithSearch(codeStarts[i],fieldName,data);
		if(dt && dt.length>0){
			for(var j in dt){
				result.push(dt[j]);
			}			
		}
	}

	return result;
}

//初始化 class属性包含dic的所有字典
/**
 * 初始化字典
 */
function dicInit(){
	var dicary=$(".dic");
	if(dicary.length==0)return;
	for(var i=0;i<dicary.length;i++){
		var dic=dicary[i];
		dicInitByJqueryObj(dic);
	}
}

/**
 * 通过jquery Dom对象初始化新字典
 * @param obj	jquery对象
 */
function dicInitByJqueryObj(obj){
	var dic = obj;
	var manager = $(dic).ligerGetDictionaryManager();
	
	//已经初始化过
	if(manager) return;

	//字典名称
	var dicName=$(dic).attr("dicName");
	
	//是否动态字典
	var dynamic = false;
	if($(dic).attr("dynamic")==='true') dynamic = true;
	
	//获取数据的url
	var url=$(dic).attr("url");
	if(!isNullBlank(url)) dynamic = true;
	
	//动态字典
	if(dynamic===true){
		if(isNullBlank(dicName) && isNullBlank(url)){
			alert("请设置字典名称或url以便获取数据");
			return ;
		}		
	}
	//静态字典
	else{
		var isStaticDic=eval("typeof "+ dicName+" == 'function'");
		if(!isStaticDic){
			alert("请检查是否引入字典"+dicName+"的JS文件以便获取数据");
			return ;
		}		
	}
	
	//初始值
	var initVal=$(dic).attr("initVal");
	
	var valueField = $(dic).attr("valueField");
	var labelField = $(dic).attr("labelField");
	var titleField = $(dic).attr("titleField");
	var searchField = $(dic).attr("searchField");
	var hiddenField = $(dic).attr("hiddenField");
	
	var pageSize = $(dic).attr("pageSize");
	if(pageSize && pageSize.length>0 && /^[1-9]+[0-9]*?$/.test(pageSize)){
		pageSize = Number(pageSize);
	}else{
		pageSize = 10;
	}
	
	var resultWidth = $(dic).attr("resultWidth");
	if(resultWidth && resultWidth.length>0 && /^[1-9]+[0-9]*?$/.test(resultWidth)){
		resultWidth = Number(resultWidth);
	}
	
	//是否禁用
	var isEnable = $(dic).attr("isEnable");
	if(isEnable==='false') isEnable = false;
	
	//是否固定选项数
	var isFixOptions = $(dic).attr("isFixOptions");
	if(isFixOptions==='false') isFixOptions = false;
	
	//结果展示方向
	var showDirection = $(dic).attr("showDirection");
	
	var itemRender = null;
	//是否展示代码
	var showCode = $(dic).attr("showCode");
	if(showCode==='true'){
		itemRender = function(g,item,data){
			$(item).append("<span style='color:blue'>["+data[g.options.valueField]+"]</span>");
		};
	}
	
	//是否多选
	var multi = $(dic).attr("multi");
	if(multi==='true') multi = true;
	
	//字典点击事件
	var itemClick = $(dic).attr("itemClick");
	if(itemClick && itemClick.length>0){
		itemClick = eval("("+itemClick+")");
	}else{
		itemClick = null;
	}
	
	//字典值改变触发函数
	var valueChange = $(dic).attr("valueChange");
	if(valueChange && valueChange!=''){
		valueChange = eval("("+valueChange+")");
		//非法的值改变触发函数
		if(!$.isFunction(valueChange)){
			alert('非法的值改变触发函数');
			valueChange = null;
		}
	}
	
	//字典配置
	var option={};
	
	//匹配以...开头的数据
	var startWith = $(dic).attr("startWith");
	
	//获取静态字典数据
	if(dynamic===false){
		var data = eval(dicName+"()");
		var filter = $(dic).attr("filter");			
		if(filter && filter.length>0){
			var filterArr = filter.split(",");
			data = codeFilter(filterArr,'code',data);
		}
		if(startWith && startWith!=''){
			data = dicCodeStartWith(startWith,'code',data);
		}
		if(data){
			option.data={"Total":data.length,"Rows":data};
		}
	}
	//动态字典
	else{
		option.dataAction = 'server';
		if(isNullBlank(url)) {
			url = getRequestRootPath() + "sys/dic/getDynamicDicData";
			option.parms = {"dicName":dicName};
			if(startWith && startWith!=''){
				option.parms.startWith = startWith;
			}
		}
		option.url=url;
	}
	
	option.initValue=initVal;
	option.valueField = valueField;
	option.labelField = labelField;
	option.titleField = titleField;
	option.searchField = searchField;
	option.multi = multi;
	option.isFixOptions = isFixOptions;
	option.showDirection = showDirection;
	option.isEnable = isEnable;
	option.itemClick = itemClick;
	option.pageSize = pageSize;
	option.resultWidth = resultWidth;
	option.itemRender = itemRender;
	option.valueChangeHandle = valueChange;
	
	//兼容老字典的配置方式
	if(isNullBlank(hiddenField)){
		var domName = $(dic).attr("name");
		var domId = $(dic).attr("id");
		if(typeof(domId)=='string' && endsWith(domId,"Name")){
			hiddenField = domId.substring(0,domId.length-4);
		}
		if(isNullBlank(hiddenField)){
			if(typeof(domName)=='string' && endsWith(domName,"Name")){
				hiddenField = domName.substring(0,domName.length-4);
			}
		}
	}
	
	//设置hiddenField
	if(isNullBlank(hiddenField)){
		alert("您未设置隐藏域名称，提交时将使用默认值code");
	}else{
		option.hiddenField = hiddenField;
	}
	
	$(dic).ligerDictionary(option);
}

//判断字符串是否以suffix串结尾
function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

//判断是否为空串
function isNullBlank(str){
	if(str && str.length > 0) return false;
	 return true;
}

function getRequestRootPath(){
	//获取应用上下文路径
	var contextpath = window.document.location.pathname;
	var i = contextpath.indexOf("/");
	if(i == 0){
		contextpath = contextpath.substr(1);
	}
	i = contextpath.indexOf("/");
	contextpath = "/" + contextpath.substr(0, i + 1);
	
	return contextpath;
}