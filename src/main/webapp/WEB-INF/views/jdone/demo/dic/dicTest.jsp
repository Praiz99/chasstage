<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>字典选择器</title>
	<%@ include file="/WEB-INF/views/framework/include/default.jsp"%>  
	<link rel="stylesheet" type="text/css" href="${ctx}/static/framework/plugins/dic/dic.css"/>
	<script src="${ctx}/static/framework/plugins/com/base.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/plugins/dic/com.dictionary.js" type="text/javascript"></script>
	<script src="${ctx}/static/framework/utils/common.js" type="text/javascript"></script>	
	<script type="text/javascript" src="${ctx}/static/dic/ZD_SYS_DSDM.js"></script>	
	<script type="text/javascript">
/*
 		可配置属性
 		
		valueField:'code',	//值字段名称
		labelField:'name',	//选项字段名称
		titleField:'name',	//标题字段名称 title
		data:null,			//json数据 {"Total":10,"Rows":data}
		url:null,			//获取数据的url
		dataAction:'local', //
		searchField:'name', //默认是labelField 多个查询字段以,隔开,例如 searchField = 'name,code,scode'
		pageParmName:'page',//向服务端请求数据时 提交请求当前页的参数名称
		pagesizeParmName:'pagesize', //每页展示记录数参数名称
	    sortorderParmName: 'orderBy', //提交的排序参数名称
		totalName:'Total',
		recordName:'Rows',
		parms: [],			//参数
		width:'auto',		//字典宽度
		resultWidth:null,	//展示结果容器宽度，数字，不带px
		page:1,				//当前页
		pageSize:10,		//每页展示记录数
		order_by:null,		//排序映射 是一个以排序字段名称为键的键值对象, 例如 :{code:'asc',name:'desc'},
		hiddenField:'code',	//如果页面上有id为code且为隐藏域的dom对象则选中的字典值将会赋给该dom对象,如果不存在则新建一隐藏域并把hiddenField的值作为该隐藏域的名称
		multi:false,		//是否多选
		multiSplit:';',		//多选时的分隔符
		initValue:null,		//设置初始值,当为单选时该值可以为:string,object 如果是多选时该值可以为:string Array。
		isFixOptions:true,	//是否固定选项数目
		checkInterval:500,	//联想时间间隔
		method:'POST',		//请求数据的方式
		itemClick:null,		//选中某个选项时执行的函数
		itemRender:null,	//选项渲染函数
		filterRegx:null,	//过滤正则表达式 {code:'^3.*?'} dataAction为server时 该参数无效
		showDirection:'auto',  //展示结果朝向，为auto时会计算位置。设置固定朝向：top、bottom、left、right
		valueChangeHandle:null //值改变处理函数，默认传了当前字典选中的值value,以及字典对象manage
*/

/*
		可使用的方法：
		
        //选中字典项 当为单选时val可以为:string,object 为多选时val可以为:string,Array。
        setValue:function(val);
		
		//获取当前选中的值，多选时以','隔开
		getValue:function();
		
		//获取选中值的文本，多选时以','隔开
		getText:function();
		
        //获取选中的字典项 [object,object]
        getSelectedItemDatas:function();
        
        //获取选中的代码值 value1,value2
        getSelectedValues:function();
        
        //字典的启用和禁用开关;true:禁用字典,false:启用字典
        setDisabled:function(val);
        
      	//修改字典配置属性。
      	changeOptions:function(obj,value)
      	说明：
      		obj参数为string时obj参数表示配置属性名称，value为该属性的值，
      		obj参数为object时obj参数表示包含多个配置属性值的对象，即用jquery.extend()函数
      	例如：
			manager.changeOptions({"data":{Rows:data,Total:data.length}});
			manager.changeOptions("data",{Rows:data,Total:data.length});
			
		//重新加载字典数据
		loadData:function(data)
		例如：
		var data = ZD_ORG_CODE();
			manager.loadData(data);
		var data1 = {Rows:data,Total:data.length};
			manager.loadData(data1);
			
	    //设置字段过滤正则表达式
	    setFieldFilterRegx:function(fieldName,regx)
	   	 说明：
	    	fieldName:需要进行过滤的字段名称
	    	regx:过滤正则表达式
      	例如：manager.setFieldFilterRegx("code","^330000B");
			
		//通过jquery选择器来获取字典js对象
		$.fn.getDicManager:function()
		说明：
			该函数返回一个数组。如果只匹配到一个元素时则返回这个元素对应的字典js对象(manager)
		var manager = $("#dic").getDicManager();
		var managers = $(".dic").getDicManager();
		$.each(managers,function(){
			var manager = this;
		});
*/ 
		
/*
	字典初始化：dicInit:function(isNew)
	dicInit(true);不设置isNew或者isNew!=true时表示旧字典初始化
	
 	说明：
 	
 	(1)：获取字典js对象问题
 		通过调用dicInit(true)初始化的字典，会给配置的隐藏域添加dicId属性，可根据隐藏域的dicId属性判断该隐藏域是否为字典的隐藏域，并可通过dicId属性的值得到对应字典的js对象。
		$.ligerui.get(dicIdValue)
	
		<input type="text" class="dic" dicName="ZD_ORG_CODE" startWith="33" hiddenField='multiTest' filter="01,02" itemClick='dicItemClick' isEnable='false'/>
		
	(2)：字典项过滤问题
	
	通过调用dicInit(true)初始化的字典：
		startWith：只加载代码值以33开头的项。
		filter：排除代码值以01和02开头的字典项。
		
	手动初始化的字典过滤问题：
		配置filterRegx属性，值为Object,各字段的过滤正则表达式映射。
		例如：{code:'^3.*?',name:'^杭州.*?'}则表示只加载代码值以3开头，并且名称以杭州开头的字典项。
		
	(3)：联动触发问题
		通过设置itemClick属性来配置点击字典项时所触发的函数，触发执行函数时会给所配置的函数传入三个参数：(g,item,data)。例如：itemClick = dicItemClick;
		点击选中字典项时会执行dicItemClick(g,item,data);
		g：字典js对象，item：当前点击的字典项的jquery对象，data：选中的数据对象
		
	(4)：字典禁用问题
		统一初始化的字典，即通过调用dicInit(true)初始化的字典通过配置isEnable='false'来禁用字典，不配置或者配置isEnable='true'为启用字典。
		
		手动初始化的字典，通过字典js对象调用setDisabled(true)来禁用字典，调用setDisabled(false)启用字典。
		
	(5)：字典项搜索问题(联想)
		配置searchField属性。例如：searchField='code,name,jpcode,scode'，则表示联想时会去匹配这4个字段的值。
	
	(6)：字典展示问题
	
		展示朝向：
			通过配置showDirection属性来控制字典展示结果朝向。共有5个值,'auto':计算展示位置，'top':朝上，'bottom':朝下，'left':朝左，'right'：朝右
		
		展示结果宽度：
			通过配置resultWidth属性来控制展示结果的宽度。默认值：在ie下，单选字典：230px，多选字典：250px；在谷歌浏览器下，单选字典：270px，多选字典：300px
			需要注意的是配置resultWidth属性时不带px，例如resultWidth = '500'。
			一般情况下，现在字典的样式受一些表单的样式的影响而导致分页条换行的问题，此时可通过resultWidth属性来进行调整。
		
		每页展示条数：
			通过配置pageSize来控制，默认为10条。例如：pageSize='5'
			
	(7)：搜索框宽度问题
		通过调用dicInit(true)初始化的字典：
			通过设置input框的style来控制。例如：<input type="text" class="dic" dicName="ZD_ORG_CODE" style='width:200px;' /> 
			
		手动初始化的字典，通过配置width属性来控制，例如：width = 200;
		
	(8)：动态字典问题
	
		通过调用dicInit(true)初始化的字典：
			例如：<input type=text class="dic" url="/rdp/com/field/query/getQueryFieldPageData?modelId=4FA9F5F101E646E086379188836C80F5" hiddenField='dt_test' valueField='id' labelField='descript' titleField='descript' searchField='descript'/>
		
		手动初始化的字典：
			例如：
			dic = $("#test").ligerDictionary({
				dataAction:'server',
				url :'${ctx}/com/dic/code/getPageData',
				totalName:'totalNum',
				parms:{dicId:'33DBCCD7B73B4FCD9DBD7F955E5FB0BD'},
				width:300,
				hiddenField:'getDicValue'
			});
		
	 (9)：隐藏域问题
	 
	 		通过配置hiddenField属性来设置字典的隐藏域。
	 		
	 		对字典隐藏域的处理两种情况：
	 		
	 		情况一：存在id为hiddenField值的隐藏域。
	 		<input type='text' class='dic' dicName="ZD_ORG_CODE" hiddenField='orgCode' />
	 		存在id='orgCode'的隐藏域
	 		<input type='hidden' name='orgCode' id='orgCode' />
	 		
	 		这种情况，字典初始化时不会再生成隐藏域，会给该dom 追加dicId属性，该属性的作用上面已描述。
	 		<input type='hidden' name='orgCode' id='orgCode' dicId='...'/>
	 		
	 		情况二：字典控件生成隐藏域。
	 		<input type='text' class='dic' dicName="ZD_ORG_CODE" hiddenField='orgCode' />
	 		初始化前不存在id='orgCode'的隐藏域
	 		字典初始化时会生成name='orgCode'的隐藏域，并追加dicId属性。
	 		<input type='hidden' name='orgCode' id='orgCode' dicId='...'/>
*/

		var d = [];
		var data = {"Rows":d,"Total":d.length};
		$(document).ready(function(){
		
			var dic = $("#urlTest").ligerDictionary({
				dataAction:'server',
				url :'${ctx}/com/dic/code/getPageData',
				totalName:'totalNum',
				parms:{dicId:'33DBCCD7B73B4FCD9DBD7F955E5FB0BD'},
				width:300,
				hiddenField:'getDicValue',
				multi:true,
				multiSplit:'、',
				itemClick:function(g,item,data){
					//alert('禁用该字典');
					//g.setDisabled(true);
				},
				itemRender:function(g,item,data){
					$(item).append("<span style='color:blue'>[asdsd]</span>");
				},
				valueChangeHandle:function(value,manage){
					//alert("字典值已改变");
				}
			});
			
			$("#urlTestBtn").bind('click',function(){
				alert(dic.getValue());
			});
			
			$("#manageTestBtn").bind('click',function(){
				var manager = $.ligerui.get("controlTypeName");
				alert(manager.getValue());
			});
			
			$("#manageTestBtn1").bind('click',function(){
				var manager = $.ligerui.get($("#multiTest").attr("dicId"));
				alert(manager.getValue());
			});
			
			//字典初始化
			dicInit(true);
			var managers = $(".dic").getDicManager();
			$.each(managers,function(){
				var manager = this;
			});
			
		});
		
		function itemClick(g,item,data){
			alert("启用第一个字典");
			dic.setDisabled(false);
		}
		
		function regChange(value,manage){
		    var orgDic = $.ligerui.get("orgDic");
		    var regx = "";
		    if(value && value!=''){
		    	regx = "^"+value;
		    }
		    orgDic.clear();
		    orgDic.currentPage = 1;
		    orgDic.setFieldFilterRegx("code",regx);
		}
		
		function wpflChange(value, manage) {
			var wpflmxDic = $.ligerui.get("wpflmx");
			var regx = "";
			if (value && value != '') {
				alert(value);
				regx = "^" + value;
			}
			wpflmxDic.clear();
			wpflmxDic.currentPage = 1;
			wpflmxDic.setFieldFilterRegx("code", regx);
			
		}
	</script>
</head>
<body>
	<center>
	<form action="#" id='testForm'>
		<br />
		<div>【url获取数据】【字典禁用】【触发值改变事件】</div>
		<br />
		<input type="text" id='urlTest' name='urlTest' />
		<br />
		<br />
		<input type="button" value="获取字典值" id="urlTestBtn"/>
		<br />
		<br />
		<div>【通过输入框id获取字典manage对象】【单选】【显示代码值】【字典项点击处理函数】【字典启用】</div>
		<br />
		<input type="text" id='controlTypeName' class="dic" dicName="ZD_SYS_DSDM" style='width:200px;height:22px;' initVal='3301' itemClick='itemClick' pageSize="5" showCode="true"/>
		<br />
		<br />
		<input type="button" value="获取值" id="manageTestBtn"/>
		<br />
		<br />
		<div>【通过隐藏域获取字典manage对象】【多选】【结果区宽度】【每页展示条数】【以...开头进行过滤】【设置检索字段】【设置初值】</div>
		<br />
		<input type="text" class="dic" dicName="jdone_sys_org" startWith="33" hiddenField='multiTest' multi='true' dynamic="true" style='width:140px;' pageSize="10" searchField='name,scode,jpcode' resultWidth="300"/>
		<input type="hidden" name='multiTest' id="multiTest" value="330000012300,330000012400" />
		<input type='hidden' name='multiTest' id='multiTest'/>
		<br />
		<br />
		<input type="button" value="获取值" id="manageTestBtn1"/>
		<br />
		<br />
		<div>【动态字典】</div>
		<input type=text id="dynamicDic" class="dic" dicName="ZD_SYS_DSDM" multi='true' dynamic="true" hiddenField='dynamicTest' showDirection='bottom' searchField='name,scode,jpcode'/>
		<input type="hidden" name='dynamicTest' id="dynamicTest" value="3301,3302"/>
		<br />
		<br />
		<div>【字典联动】</div>
		<br/>
	<%--
		<div>
			区域字典：
			<input type=text id="regDic" class="dic" dicName="ZD_REGION_CODE" hiddenField='regCode' valueChange='regChange'/>
			<input type="hidden" name='regCode' id="regCode"/>
		</div>
		<br />
	--%>
		<div>
			机构字典：
			<input type=text id="orgDic" class="dic" dicName="jdone_sys_org" hiddenField='orgCode' dynamic="true" startWith="3301"/>
			<input type="hidden" name='orgCode' id='orgCode'/>
		</div>	
	<%--
		<div>物品分类</div>
		<div>
			<input type=text id="wpfl" class="dic" dicName="ZD_CASE_WPFL" hiddenField='wpfl' valueChange='wpflChange'/>
			<input type=text id="wpflmx" class="dic" dicName="ZD_CASE_WPFLMX" hiddenField='wpflmx' />
		</div>	
	--%>
	</form>		
	</center>
</body>
</html>
