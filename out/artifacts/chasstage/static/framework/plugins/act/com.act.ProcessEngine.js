/**
 * 流程引擎前端处理组件-com.act.ProcessEngine.js
 */
(function ($)
{
    var l = $.ligerui;

    $.fn.ligerProcessEngine = function (options)
    {
        return $.ligerui.run.call(this, "ligerProcessEngine", arguments);
    };

    $.fn.ligerGetProcessEngineManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetProcessEngineManager", arguments);
    };

    $.fn.getProcessEngineManager = function(){
    	var managers = [];
    	this.each(function(){
    		var ligeruiid = $(this).attr("ligeruiid");
    		if(typeof(ligeruiid)==='string' && $.trim(ligeruiid) != ''){
    			var manager = $.ligerui.get(ligeruiid);
    			if(manager) managers.push(manager);
    		}
    	});

    	return managers.length==1 ? managers[0]:managers;
    };

    //接口方法扩展
    $.ligerMethos.ProcessEngine = $.ligerMethos.ProcessEngine || {};

    $.ligerui.controls.ProcessEngine = function (element, options)
    {
        $.ligerui.controls.ProcessEngine.base.constructor.call(this, element, options);
    };

    $.ligerDefaults.ProcessEngine = {
    		actKey:null,						//流程key--------必填
    		bizId:null,							//业务ID---------必填
    		bizType:null,						//业务类型-------必填
    		instId:null,						//流程实例ID
    		bizName:null,						//业务名称
    		dxbh:null,
    		dxmc:null,
    		msgType:null,						//消息类型
    		msgDealUrl:null,
    		msgTitle:null,
    		defaultProOpinion:null,				//默认处理意见
    		isOpinionRequired:false,            //处理意见是否必填
    		isOnlyStartNewInst:false,			//是否只允许新起一个流程
    		isOnlyCompleteTask:false,			//是否只允许进行流程处理
    		isOnlyChangeProUsers:false,			//是否只允许变更处理人
    		bizFormDom:null,					//业务表单dom表达式，取业务表单数据时将使用$(bizFormDom)
    		getNextProOrgUrl:null,
    		getNextProUserUrl:null,
    		startProcessUrl:null,
    		completeTaskUrl:null,
    		changeProUsersUrl:null,
    		initCallBack:null,
    		proResultSelectCallBack:null,
    		showSubmitButtons:false,
    		submitBefore:null,					//流程处理提交前回调
    		submitCallBack:null,					//流程处理完毕后回调
    		forceUsePrevDealUrl:true	            //是否使用上一次流程地址
	};

    $.ligerDefaults.ProcessEngineString = {

    };

    $.ligerui.controls.ProcessEngine.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.ProcessEngine';
        },
        __idPrev: function ()
        {
            return 'ProcessEngine';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.ProcessEngine;
        },
        _init: function ()
        {
        	$.ligerui.controls.ProcessEngine.base._init.call(this);

        	var g = this, p = this.options;

            //获取根路径
            function getUrlRootPath(){
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

            g.serverUrlRootPath = getUrlRootPath();

        	p.actKey = p.actKey || '';
        	p.bizId = p.bizId || '';
        	p.bizType = p.bizType||'';
        	p.instId = p.instId||'';

        	if(p.instId=='' && (p.actKey=='' || p.bizId=='' || p.bizType=='')){
        		alert("instId、actKey、bizId、bizType都不能为空！");
        		return;
        	}

        	p.bizName = p.bizName||'';
        	p.dxbh = p.dxbh||'';
        	p.dxmc = p.dxmc||'';

        	p.msgType = p.msgType || '';
        	p.msgTitle = p.msgTitle || '';
        	p.msgDealUrl = p.msgDealUrl || '';
        	p.defaultProOpinion = p.defaultProOpinion || '';

        	if($.type(p.isOpinionRequired) != 'boolean'){
        		p.isOpinionRequired = false;
        	}

        	if(typeof(p.isOnlyStartNewInst) != 'boolean'){
        		p.isOnlyStartNewInst = false;
        	}

        	if(typeof(p.isOnlyCompleteTask) != 'boolean'){
        		p.isOnlyCompleteTask = false;
        	}

        	if(typeof(p.isOnlyChangeProUsers) != 'boolean'){
        		p.isOnlyChangeProUsers = false;
        	}

        	if(p.isOnlyChangeProUsers===true){
        		p.isOnlyStartNewInst = false;
        		p.isOnlyCompleteTask = false;
        	}

        	if(typeof(p.forceUsePrevDealUrl) != 'boolean'){
        		p.forceUsePrevDealUrl = true;
        	}

        	p.bizFormDom = p.bizFormDom || '';

    		if(!p.getNextProOrgUrl || p.getNextProOrgUrl==''){
    			p.getNextProOrgUrl = g.serverUrlRootPath + 'act2/engine/getNextNodeProOrgPageData' + '?t=' + new Date().getTime();
    		}
    		if(!p.getNextProUserUrl || p.getNextProUserUrl==''){
    			p.getNextProUserUrl = g.serverUrlRootPath + 'act2/engine/getNextNodeProUserPageData' + '?t=' + new Date().getTime();
    		}
    		if(!p.startProcessUrl || p.startProcessUrl==''){
    			p.startProcessUrl = g.serverUrlRootPath + 'act2/engine/startProcess' + '?t=' + new Date().getTime();
    		}
    		if(!p.completeTaskUrl || p.completeTaskUrl==''){
    			p.completeTaskUrl = g.serverUrlRootPath + 'act2/engine/completeTask' + '?t=' + new Date().getTime();
    		}
    		if(!p.changeProUsersUrl || p.changeProUsersUrl==''){
    			p.changeProUsersUrl = g.serverUrlRootPath + 'act2/engine/changeProUsers' + '?t=' + new Date().getTime();
    		}

        	if(!p.initCallBack || !$.isFunction(p.initCallBack)){
        		p.initCallBack = null;
        	}

        	if(!p.proResultSelectCallBack || !$.isFunction(p.proResultSelectCallBack)){
        		p.proResultSelectCallBack = null;
        	}

        	if(typeof(p.showSubmitButtons) != 'boolean'){
        		p.showSubmitButtons = false;
        	}

        	if(!p.submitBefore || !$.isFunction(p.submitBefore)){
        		p.submitBefore = null;
        	}

        	if(!p.submitCallBack || !$.isFunction(p.submitCallBack)){
        		p.submitCallBack = null;
        	}

        	g.engine = {};

        	//设置初始化参数
        	var initParamsObj = {};

        	if(p.bizFormDom!='' && $(p.bizFormDom).length>0){
        		var initBizFormData = $(p.bizFormDom).serializeObject();
        		if(initBizFormData){
        			for(var pname in initBizFormData){
        				var paramName = 'bizFormData["' + pname + '"]';
        				initParamsObj[paramName] = initBizFormData[pname];
        			}
        		}
        	}

        	initParamsObj["actKey"] = p.actKey;
        	initParamsObj["bizType"] = p.bizType;
        	initParamsObj["bizId"] = p.bizId;
        	initParamsObj["instId"] = p.instId;
        	initParamsObj["isOnlyStartNewInst"] = p.isOnlyStartNewInst;
        	initParamsObj["isOnlyCompleteTask"] = p.isOnlyCompleteTask;
        	initParamsObj["isOnlyChangeProUsers"] = p.isOnlyChangeProUsers;
        	initParamsObj["forceUsePrevDealUrl"] = p.forceUsePrevDealUrl;

        	var initMsg = "";
        	var isInitSucess = false;
           	$.ajax({
        		async:false,
        		cache:false,
        		dataType:'json',
        		type:'POST',
        		url:g.serverUrlRootPath + 'act2/engine/processInit',
        		data:initParamsObj,
        		success:function(response,statu){
        			if(statu){
            			if(response.success){
            				isInitSucess = true;
            				g.engine["initData"]= response.data;
            				g.initType = g.engine["initData"].initType || '';
            				g.isEnablePro = g.engine["initData"].isEnablePro || false;
            				if(g.engine["initData"]["initCmdObj"]){
            					p.actKey = g.engine["initData"]["initCmdObj"].actKey||'';
            					p.bizId = g.engine["initData"]["initCmdObj"].bizId||'';
            					p.bizType = g.engine["initData"]["initCmdObj"].bizType||'';
            					p.instId = g.engine["initData"]["initCmdObj"].instId||'';
            				}
            			}else{
            				initMsg = response.msg;
            			}
            			if(p.initCallBack){
            				p.initCallBack(isInitSucess,response.data);
            			}
        			}else{
        				initMsg = '请检查流程服务是否正常，当前状态为:' + statu;
        			}
        		}
        	});

           	if(!isInitSucess){
           		alert("流程引擎初始化失败:" + initMsg);
           		return false;
           	}
        },
        _render: function ()
        {
        	var g = this, p = this.options;

        	//流程引擎容器
        	g.container = $(g.element);

        	g.container.html(g._buildProcessHtml());

        	//初始化表单
        	g._initProcessForm();
        },

        _buildProcessHtml:function(){
        	var g = this, p = this.options;

        	var htmlArr = [];

        	//构建提交或变更处理人form
        	if(g.initType=='01' || g.initType=='04'){
        		htmlArr.push(g._buildProcessFormHtml(g.initType));
        	}

        	//审核审批form
        	else{
        		var currentNodeId = '';
        		if(g.engine["initData"].currentNode){
        			currentNodeId = g.engine["initData"].currentNode["id"] || '';
        		}
        		htmlArr.push("<div class='l-layout-left' id='leftSide'>");
        		htmlArr.push("  <div class='l-layout-header'>流程审核审批");
        		htmlArr.push("    <input type='button' class='btn-4-word small-btn blue-1' style='float: right; margin-top: 1px; width: 60px; margin-right: 10px;' value='流程状态' onclick='window.open(\"" + g.serverUrlRootPath + "act/pendTask/processPic?modelId=" + g.engine["initData"].model.id+ "&nodeId=" + currentNodeId +"\")'/>");
        		if(g.isEnablePro===false){
        			htmlArr.push("<input type='button' class='btn-4-word small-btn blue-1' style='float: right; margin-top: 1px; width: 60px; margin-right: 10px;' value='流程历史' onclick='window.open(\"" + g.serverUrlRootPath + "act/pendTask/processHistory?instId=" + g.engine["initData"].inst.id + "\")'/>");
        		}
				htmlArr.push("  </div>");

        		if(g.isEnablePro===true){
					//创建tabs
					htmlArr.push("  <div id='actTabsDiv' class='easyui-tabs'>");

					//审核
					if(g.initType.indexOf('02')>-1){
						htmlArr.push(g._buildProcessFormHtml('02'));
					}
					//审批
					if(g.initType.indexOf('03')>-1){
						htmlArr.push(g._buildProcessFormHtml('03'));
					}
					htmlArr.push("  </div>");
        		}

				htmlArr.push("</div>");
        	}

        	//流程处理按钮
        	if(p.showSubmitButtons===true && g.isEnablePro===true){
        		htmlArr.push(g._buildSubmitButtons());
        	}

        	return htmlArr.join('');
        },

        _initProcessForm:function(){
        	var g = this, p = this.options;

        	g._initTranDiv();

        	//审核审批，初始化tabs
        	if(g.initType!='tj' && g.initType!='changeProUsers'){
        		$("#actTabsDiv").tabs({
        			border:false,
        			onSelect:function(title){
        				if(title=='审核'){
        					g.currentOperForm = '02';
        				}else if(title=='审批'){
        					g.currentOperForm = '03';
        				}
        			}
        		});
        	}

        	//选择处理结果点击事件
        	g.container.find(":radio").bind('click',function(e){
        		var val = e.target.value;
        		//审核且处理结果为上报，则需要显示传送div
        		if(g.currentOperForm=='02' && val!='03'){
        			$("#"+g.currentOperForm+"_processForm #tranDiv").remove();
    				$("#"+g.currentOperForm+"_processForm").append(g._buildTranDivHtml());
    				//初始化审核上报
    				if(val=='01'){
    					g._initTranDiv();
    				}
    				//初始化审核流转
    				else if(val=='04'){
    					g._initTranSelfDiv();
    				}
        		}else{
        			//隐藏上报div
        			$("#"+g.currentOperForm+"_processForm #tranDiv").remove();
        		}
        		//处理回调
        		if(p.proResultSelectCallBack){
        			p.proResultSelectCallBack(g,val);
        		}
        	});

        	//流程处理按钮绑定事件
        	if(p.showSubmitButtons===true){
        		g.container.find(".actProcessButton").bind('click',function(e){
        			//处理提交
        			if('确定'==e.target.value){
        				//获取待提交的表单数据
        				var submitData = g.getSubmitData();
        				if(!submitData){
        					return false;
        				}

        				//表单提交，根据当前操作的表单类型确定提交地址
        				var submitUrl = '';
        				//流程发起
        				if(g.currentOperForm=='01'){
        					submitUrl = p.startProcessUrl;
        					//回退到发起人
        					if(g.engine["initData"].inst){
        						submitUrl = p.completeTaskUrl;
        					}
        				}
        				//审核审批
        				else if(g.currentOperForm=='02' || g.currentOperForm=='03'){
        					submitUrl = p.completeTaskUrl;
        				}
        				//变更处理人
        				else if(g.currentOperForm=='04'){
        					submitUrl = p.changeProUsersUrl;
        				}

        				if(!submitUrl || submitUrl==''){
        					alert('处理地址不能为空！');
        					return false;
        				}

						//延后submitBefore事件。
						var submitBeforeRet = null;
						if(p.submitBefore){
							submitBeforeRet = p.submitBefore(g);
						}
						//如果回调函数返回false将不执行流程表单提交
						if(submitBeforeRet===false){
							return false;
						}

        				//禁用点击按钮
        				$(e.target).attr("disabled",true);

        	           	$.ajax({
        	           		cache:false,
        	           		dataType:'json',
        	           		type:'POST',
        	        		url:submitUrl,
        	        		data:submitData,
        	        		success:function(response,statu){
        	        			if(statu){
        	        				//回调处理
    	        					if(p.submitCallBack){
    	        						p.submitCallBack(g,response);
    	        					}
    	        					//默认处理
    	        					else{
            	        				if(response["success"] && response["success"]==true){
            	        					alert("流程处理成功");
            	        					g.reRender();
            	        				}else{
            	        					$(e.target).attr("disabled",false);
            	        					alert("流程处理失败:" + (response["msg"] || ''));
            	        				}
    	        					}
        	        			}else{
        	        				$(e.target).attr("disabled",false);
        	        				alert("流程处理失败:请检查流程处理服务是否正常，当前状态为:" + statu);
        	        			}
        	        		}
        	        	});
        			}
        			//表单重置
        			else if('重置'==e.target.value){
        				g.reRender();
        				/*document.getElementById('proOpinion').value = '';
        			    document.getElementById('proOpinion').focus();*/
//        				var userDataArr = [];
//        				userDataArr.push({id:'CB18DA2D72A44FCEA78AC82D8E268E63',name:'admin',loginId:'admin',orgSysCode:'330102210000',roleCode:'400003'});
//        				userDataArr.push({id:'131C8E0B8F474C55A536E320F024B335',name:'陈明强',loginId:'013408',orgSysCode:'330102210000',roleCode:'400003'});
//        				g.addProUsers(userDataArr);
//        				g.addProUsers({id:'CB18DA2D72A44FCEA78AC82D8E268E63',name:'admin',loginId:'admin',orgSysCode:'330102210000',roleCode:'400003'});
//        				g.addProUsers({id:'131C8E0B8F474C55A536E320F024B335',name:'陈明强',loginId:'013408',orgSysCode:'330102210000',roleCode:'400003'});
//        				g.removeProUsers('CB18DA2D72A44FCEA78AC82D8E268E63,131C8E0B8F474C55A536E320F024B335');
        			}
        		});
        	}
        },

        _buildProcessFormHtml:function(initType){
        	var g = this, p = this.options;

        	var htmlArr = [];

        	if(initType=='01' || initType=='04'){

            	htmlArr.push("<form id='" + initType + "_processForm' name='"　+　initType +　"_processForm'>");

            	htmlArr.push("  <input type='hidden' name='actKey' value='" + p.actKey +"'>");
    			htmlArr.push("  <input type='hidden' name='bizType' value='" + p.bizType +"'>");
    			htmlArr.push("  <input type='hidden' name='bizName' value='" + p.bizName +"'>");
    			htmlArr.push("  <input type='hidden' name='bizId' value='" + p.bizId +"'>");
    			htmlArr.push("  <input type='hidden' name='dxbh' value='" + p.dxbh +"'>");
    			htmlArr.push("  <input type='hidden' name='dxmc' value='" + p.dxmc +"'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.bizType' value='" + p.bizType +"'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.bizId' value='" + p.bizId +"'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.msgType' value='" + p.msgType + "'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.msgTitle' value='" + p.msgTitle + "'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.msgDealUrl' value='" + p.msgDealUrl + "'>");
    			htmlArr.push("  <input type='hidden' name='isOnlyStartNewInst' value='" + p.isOnlyStartNewInst + "'>");
    			htmlArr.push("  <input type='hidden' name='isOnlyCompleteTask' value='" + p.isOnlyCompleteTask + "'>");
    			htmlArr.push("  <input type='hidden' name='isOnlyChangeProUsers' value='" + p.isOnlyChangeProUsers + "'>");
    			htmlArr.push("  <input type='hidden' name='forceUsePrevDealUrl' value='" + p.forceUsePrevDealUrl + "'>");

    			var taskId = '';
    			var instId='';
    			if(g.engine["initData"].task){
    				taskId = g.engine["initData"].task["id"] || '';
    				instId = g.engine["initData"].task["instId"] || '';
    			}

    			htmlArr.push("  <input type='hidden' name='instId' value='" + instId +"'>");
    			htmlArr.push("  <input type='hidden' name='taskId' value='" + taskId +"'>");

    			var prevProType = '';
    			var prevProResultType = '';
    			if(g.engine["initData"].prevNodePro){
        			prevProType = g.engine["initData"].prevNodePro["proType"] || '';
        			prevProResultType = g.engine["initData"].prevNodePro["proResultType"] || '';
    			}
    			htmlArr.push("  <input type='hidden' name='prevProType' value='" + prevProType +"'>");
    			htmlArr.push("  <input type='hidden' name='prevProResultType' value='" + prevProResultType +"'>");

        		var tranDivHtml = g._buildTranDivHtml();
        		if(initType=='01'){
        			htmlArr.push("  <input type='hidden' name='proType' value='01'>");
        			htmlArr.push("  <input type='hidden' name='proResultType2' value='00'>");
        			var tranDiv = $(g._buildTranDivHtml());
        			tranDiv.find("#tranTable tbody").append("         <tr>"+
        													"           <th><label>处理意见：</label></th>"+
        													"           <td>"+
        													// "               <label>处理意见：</label>"+
        													"             <textarea rows='4' class='form-textarea' id='proOpinion' name='proOpinion' maxlength='512' style='width:260px; height:66px;'>" + p.defaultProOpinion + "</textarea>" +
        													"           </td>"+
        													"         </tr>");
        			tranDivHtml = $("<div/>").append(tranDiv).html();
        		}
        		htmlArr.push(tranDivHtml);
        		htmlArr.push("</form>");

        		g.currentOperForm = initType;
        	}
        	//审核审批
        	else{

        		var proName = '审核';
            	var proResultTypeName = '上报';
            	var proTypeValue = '02';

            	var isEnableTranSelf = false;
        		if(g.engine["initData"].currentNode){
        			if(initType=='02' && g.engine["initData"].currentNode["enableTranSelf"]=='1'){
        				isEnableTranSelf = true;
        			}
        		}

            	var isDefaultSelect = false;
            	if(initType=='03'){
            		proName = '审批';
            		proResultTypeName = '同意';
            		proTypeValue = '03';
            		if(g.engine["initData"].currentNode && g.engine["initData"].currentNode["defaultProType"]=='02'){
            			isDefaultSelect = true;
            		}
            	}

            	htmlArr.push("<div title='" + proName + "' id='" + initType + "_TabDiv' data-options='selected:"+isDefaultSelect+"'>");

            	htmlArr.push("<form id='" + initType + "_processForm' name='"　+　initType +　"_processForm'>");

            	htmlArr.push("  <input type='hidden' name='actKey' value='" + p.actKey +"'>");
    			htmlArr.push("  <input type='hidden' name='bizType' value='" + p.bizType +"'>");
    			htmlArr.push("  <input type='hidden' name='bizName' value='" + p.bizName +"'>");
    			htmlArr.push("  <input type='hidden' name='bizId' value='" + p.bizId +"'>");
    			htmlArr.push("  <input type='hidden' name='dxbh' value='" + p.dxbh +"'>");
    			htmlArr.push("  <input type='hidden' name='dxmc' value='" + p.dxmc +"'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.bizType' value='" + p.bizType +"'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.bizId' value='" + p.bizId +"'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.msgType' value='" + p.msgType + "'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.msgTitle' value='" + p.msgTitle + "'>");
    			htmlArr.push("  <input type='hidden' name='rmdMsg.msgDealUrl' value='" + p.msgDealUrl + "'>");
    			htmlArr.push("  <input type='hidden' name='isOnlyStartNewInst' value='" + p.isOnlyStartNewInst + "'>");
    			htmlArr.push("  <input type='hidden' name='isOnlyCompleteTask' value='" + p.isOnlyCompleteTask + "'>");
    			htmlArr.push("  <input type='hidden' name='isOnlyChangeProUsers' value='" + p.isOnlyChangeProUsers + "'>");
    			htmlArr.push("  <input type='hidden' name='forceUsePrevDealUrl' value='" + p.forceUsePrevDealUrl + "'>");

    			var taskId = '';
    			var instId='';
    			if(g.engine["initData"].task){
    				taskId = g.engine["initData"].task["id"] || '';
    				instId = g.engine["initData"].task["instId"] || '';
    			}
    			htmlArr.push("  <input type='hidden' name='instId' value='" + instId +"'>");
    			htmlArr.push("  <input type='hidden' name='taskId' value='" + taskId +"'>");

    			var prevProType = '';
    			var prevProResultType = '';
    			if(g.engine["initData"].prevNodePro){
        			prevProType = g.engine["initData"].prevNodePro["proType"] || '';
        			prevProResultType = g.engine["initData"].prevNodePro["proResultType"] || '';
    			}
    			htmlArr.push("  <input type='hidden' name='prevProType' value='" + prevProType +"'>");
    			htmlArr.push("  <input type='hidden' name='prevProResultType' value='" + prevProResultType +"'>");

            	htmlArr.push("  <input type='hidden' name='proType' value='" + proTypeValue + "'>");

    			htmlArr.push("   <table class='common_table_form process-common-table' style='margin: 0; max-width: 100%;'>");
    			htmlArr.push("     <tbody>");
    			htmlArr.push("       <tr>");
    			htmlArr.push("         <th title='" + p["bizName"] + "'><label>" + p["bizName"] + "&nbsp;&nbsp;</label></td>");
    			htmlArr.push("         <td style='line-height: 25px;'>");

    			//审核时才会有同级流转
    			if(isEnableTranSelf===true){
        			htmlArr.push("           <label>");
        			htmlArr.push("             <input type='radio' style='width: auto;' name='proResultType2' value='04'>");
        			htmlArr.push("             <span>流转</span>");
        			htmlArr.push("           </label>");
    			}

    			htmlArr.push("           <label>");
    			htmlArr.push("             <input type='radio' style='width: auto;' name='proResultType2' class='upTranLoad' value='01'>");
    			htmlArr.push("             <span>" + proResultTypeName + "</span>");
    			htmlArr.push("           </label>");

//    			htmlArr.push("           <label>");
//    			htmlArr.push("             <input type='radio' style='width: auto;' name='proResultType2' value='02'>");
//    			htmlArr.push("             <span >不同意</span>");
//    			htmlArr.push("           </label>");

    			htmlArr.push("           <label>");
    			htmlArr.push("             <input type='radio' style='width: auto;' name='proResultType2' value='03'>");
    			htmlArr.push("             <span>退回</span>");
    			htmlArr.push("           </label>");

                htmlArr.push("           <input type='button' class='btn-4-word small-btn blue-1 process-history-btn' value='流程历史' onclick='window.open(\"" + g.serverUrlRootPath + "act/pendTask/processHistory?instId=" + g.engine["initData"].inst.id + "\")'/>");

    			htmlArr.push("         </td>");
    			htmlArr.push("       </tr>");
    			htmlArr.push("       <tr style='display: none;'>");
    			htmlArr.push("         <th>");
    			htmlArr.push("           <label>退回节点：</label>");
    			htmlArr.push("         </th>");
    			htmlArr.push("         <td>");
    			htmlArr.push("           <select name='nodeBackId'>");
    			htmlArr.push("           </select>");
    			htmlArr.push("           </td>");
    		    htmlArr.push("       </tr>");
    		    htmlArr.push("       <tr>");
    			htmlArr.push("          <th>");
    			htmlArr.push("            <label>" + proName + "意见：</label>");
    			htmlArr.push("          </th>");
    			htmlArr.push("          <td>");
    			htmlArr.push("           <textarea class='required' id='proOpinion' name='proOpinion' maxlength='512' style='width:260px; height: 66px;'>" + p.defaultProOpinion + "</textarea>");
    			htmlArr.push("          </td>");
    			htmlArr.push("       </tr>");
    			htmlArr.push("    </tbody>");
    			htmlArr.push("  </table>");

				//传送div(审核)
    			if(initType=='02'){
    				htmlArr.push(g._buildTranDivHtml());
    			}

    			htmlArr.push("</form>");
    			htmlArr.push("</div>");
        	}

			return htmlArr.join('');
        },

        _buildTranDivHtml:function(){
        	var g = this, p = this.options;

        	//处理form html
        	var htmlArr = [];
			htmlArr.push("   <div id='tranDiv' class='panel-detail'>");
			htmlArr.push("       <table id='tranTable' class='common_table_form process-common-table' style='margin: 0; max-width: 100%;'>");
			htmlArr.push("           <tbody>");
			//htmlArr.push("         <tr class='sub-caption'><td colspan='4'>" + p["bizName"] +"</td></tr>");
			htmlArr.push("               <tr>");
		//	htmlArr.push("                 <td style='width:25%;text-align:center;'>传送给</td>");

			//传送目标节点
            htmlArr.push("                  <th>");
            htmlArr.push("                      <label>目标节点：</label>");
            htmlArr.push("                  </th>");
            htmlArr.push("                  <td>");
            htmlArr.push("                      <select name='nextNodeId' style='width: 260px;'>");
            htmlArr.push("                          <option value=''>=====无=====</option>");
            htmlArr.push("                      </select>");
            htmlArr.push("                  </td>");
            htmlArr.push("               <tr>");

            //是否任意单位审批
            htmlArr.push("               <tr>");
            htmlArr.push("                  <th>");
            htmlArr.push("                      <label>跨部门审批：</label>");
            htmlArr.push("                  </th>");
            htmlArr.push("                  <td>");
            htmlArr.push("                      <div class='search_checkbox'>");
            htmlArr.push("                          <input type='checkbox' name='isAnyOrg' id='switch' class='switch_checkbox' value='1'>");
            htmlArr.push("                          <label for='switch'>跨部门审批</label>");
            htmlArr.push("                      </div>");
            htmlArr.push("                  </td>");
            htmlArr.push("               <tr>");

             //目标角色,于20190725屏蔽
             htmlArr.push("               <tr style='display:none;'>");
             htmlArr.push("                  <th>");
             htmlArr.push("                      <label>目标角色：</label>");
             htmlArr.push("                  <th>");
             htmlArr.push("                  <td>");
             htmlArr.push("                      <select name='nextNodeProRole' onchange='nextNodeChange();'>");
             htmlArr.push("                          <option value=''></option>");
             htmlArr.push("                      </select>");
             htmlArr.push("                  <td>");
             htmlArr.push("               <tr>");

			//下一环节单位
            htmlArr.push("               <tr>");
            htmlArr.push("                  <th>");
            htmlArr.push("                      <label>下一环节单位：</label>");
            htmlArr.push("                  </th>");
            htmlArr.push("                  <td>");
            htmlArr.push("                      <input name='nextNodeProOrg' type='text'/>");
            htmlArr.push("                      <label for='isContainsChildOrg'  style='display:none'><input type='checkbox' name='isContainsChildOrg' id='isContainsChildOrg' value='1'>含下级</label>");
            htmlArr.push("                  </td>");
            htmlArr.push("               <tr>");

            //下一环节单位
            htmlArr.push("               <tr>");
            htmlArr.push("                  <th>");
            htmlArr.push("                      <label>下一环节办理人：</label>");
            htmlArr.push("                  </th>");
            htmlArr.push("                  <td>");
            htmlArr.push("                      <select class='multiple-select' name='nextNodeProUserId' multiple='multiple' style='width: 260px; height: 158px;'></select>");
            htmlArr.push("                  </td>");
            htmlArr.push("               <tr>");

			//htmlArr.push("               <span style='margin-top:5px;'>");
			//htmlArr.push("                 <input type='checkbox' name='rmdMsg.isMobileRmd' value='1'>发短信<input type='checkbox' name='rmdMsg.isMailRmd' value='1'>发邮件");
			//htmlArr.push("               </span'>");
			//htmlArr.push("               <br>");
			htmlArr.push("           </tbody>");
			htmlArr.push("       </table>");
			htmlArr.push("   </div>");

			return htmlArr.join('');
        },

        _initTranDiv:function(){
        	var g = this, p = this.options;
        	g.tranObj={};
        	var nextNodeList = g.engine["initData"].nextNodeList || [];
    		if(nextNodeList.length>0){

    			//初始化目标节点
    			var nextNodeSelector = g.tranObj["nextNodeSelector"] = $("#tranDiv").find("select[name='nextNodeId']");
    			nextNodeSelector.html('');

    			var initNextNode = null;
    			var initNextNodeId = g.engine["initData"].initNextNodeId || '';
    			for(var i=0;i<nextNodeList.length;i++){
    				if(nextNodeList[i].id==initNextNodeId){
    					initNextNode = nextNodeList[i];
    				}
    				nextNodeSelector.append("<option value='" + nextNodeList[i].id + "' " + (nextNodeList[i].id==initNextNodeId ? "selected":"") + ">" + nextNodeList[i].actNodeName + "</option>");
    			}
    			if(!initNextNode){
    				initNextNode = nextNodeList[0];
    			}

    			//下一节点处理角色选择器，初始化时，取第一个
    			var nextNodeProRoleSelector = g.tranObj["nextNodeProRoleSelector"] = $("#tranDiv").find("select[name='nextNodeProRole']");
    			var nodeProRolesArr = initNextNode.proRoleList;
    			if(nodeProRolesArr && nodeProRolesArr.length>0){
    				nextNodeProRoleSelector.html('');
    				nextNodeProRoleSelector.append("<option value=''></option>");
    				//屏蔽通过角色筛选处理人员 --addby dsx 20190725
    				/*var initNextNodeProRoleCode = g.engine["initData"].initProRole || '';
        			for(var i=0;i<nodeProRolesArr.length;i++){
        				nextNodeProRoleSelector.append("<option value='" + nodeProRolesArr[i].code + "' " + (nodeProRolesArr[i].code==initNextNodeProRoleCode ? "selected":"") + ">" + nodeProRolesArr[i].name + "</option>");
        			}*/
    			}

    			//下一节点处理用户选择器
    			var nextNodeProUserSelector = g.tranObj["nextNodeProUserSelector"] = $("#tranDiv").find("select[name='nextNodeProUserId']");

    			//下一节点处理单位选择器
    			var initProOrgSysCode = g.engine["initData"].initProOrgSysCode || '';
    			var nextNodeProOrgSelector = g.tranObj["nextNodeProOrgSelector"] = $("#tranDiv").find("input[name='nextNodeProOrg']").ligerDictionary({
    				dataAction:'server',
    				url:p.getNextProOrgUrl,
    				valueField:'sysCode',
    				nameField:'name',
    				searchField:'code,name',
    				totalName:'total',
    				recordName:'rows',
    				parms:{"actKey":p.actKey,"nextNodeId":nextNodeSelector.val(),"isAnyOrg":false},
    				initValue:initProOrgSysCode,
    				width:236,
    				resultWidth:258,
    				isFixOptions:false,
    				pageSize:5,
    				isInitSelectFirst:true,
    				showDirection:'bottom',
    				hiddenField:'nextNodeProOrgSysCode',
    				valueChangeHandle:function(value,manage){//alert("字典值已改变");
    					if(value != null && value != ""){
    						g._loadProUserData();
    					}else{
    						nextNodeProUserSelector.html('');
    					}
    				}
    			});

    			//绑定节点联动事件
    			nextNodeSelector.bind('change',function(){
    				//清空角色、单位、用户信息
    				nextNodeProRoleSelector.html('');

        			//屏蔽通过角色筛选处理人员 --addby dsx 20190725
        			nextNodeProRoleSelector.append("<option value=''></option>");
//    				var selectedNode = null;
//        			for(var i=0;i<nextNodeList.length;i++){
//        				if(nextNodeList[i].id==nextNodeSelector.val()){
//        					selectedNode = nextNodeList[i];
//        					break;
//        				}
//        			}
//        			if(!selectedNode){
//        				nextNodeProRoleSelector.append("<option value=''></option>");
//        			}else{
//        				var nrArr = selectedNode.proRoleList;
//            			if(nrArr && nrArr.length>0){
//            				nextNodeProRoleSelector.html('');
//                			for(var i=0;i<nrArr.length;i++){
//                				nextNodeProRoleSelector.append("<option value='"+nrArr[i].code+"'>"+nrArr[i].name+"</option>");
//                			}
//            			}
//        			}

        			//清空单位
        			nextNodeProOrgSelector.clear();
        			nextNodeProOrgSelector.setParm("nextNodeId",nextNodeSelector.val());

        			//清空用户
        			nextNodeProUserSelector.html('');
    			});

    			$("#tranDiv").find("input[name='isAnyOrg']").bind('click',function(e){
    				var target = e.target || e.srcElement;
    				nextNodeProOrgSelector.clear();
    				nextNodeProOrgSelector.setParm("nextNodeId",nextNodeSelector.val());

    				//清空用户
        			nextNodeProUserSelector.html('');
    				nextNodeProOrgSelector.setParm("isAnyOrg",$(target).is(":checked"));
    			});
    			g.tranObj["isContainsChildOrg"]=false;
    			$("#tranDiv").find("input[name='isContainsChildOrg']").bind('click',function(e){
    				var target = e.target || e.srcElement;
    				g.tranObj["isContainsChildOrg"]=$(target).is(":checked");
    				//p.isContainsChildOrg=$(target).is(":checked");
    				g._loadProUserData();
    			});
    		}
        },

        //初始化流转传送div
        _initTranSelfDiv:function(){
        	var g = this, p = this.options;
        	g.tranObj={};

        	var nextNodeList = [];

        	var currentNode = g.engine["initData"].currentNode;
        	if(currentNode){
        		nextNodeList.push(currentNode);
        	}

    		if(nextNodeList.length>0){

    			//初始化目标节点
    			var nextNodeSelector = g.tranObj["nextNodeSelector"] = $("#tranDiv").find("select[name='nextNodeId']");
    			nextNodeSelector.html('');

    			var initNextNode = null;
    			var initNextNodeId = '';
    			for(var i=0;i<nextNodeList.length;i++){
    				if(nextNodeList[i].id==initNextNodeId){
    					initNextNode = nextNodeList[i];
    				}
    				nextNodeSelector.append("<option value='" + nextNodeList[i].id + "' " + (nextNodeList[i].id==initNextNodeId ? "selected":"") + ">" + nextNodeList[i].actNodeName + "</option>");
    			}
    			if(!initNextNode){
    				initNextNode = nextNodeList[0];
    			}

    			//下一节点处理角色选择器，初始化时，取第一个
    			var nextNodeProRoleSelector = g.tranObj["nextNodeProRoleSelector"] = $("#tranDiv").find("select[name='nextNodeProRole']");
    			var nodeProRolesArr = initNextNode.proRoleList;
    			if(nodeProRolesArr && nodeProRolesArr.length>0){
    				nextNodeProRoleSelector.html('');
    				//屏蔽通过角色筛选处理人员 --addby dsx 20190725
    				nextNodeProRoleSelector.append("<option value=''></option>");
    				/*var initNextNodeProRoleCode = '';
        			for(var i=0;i<nodeProRolesArr.length;i++){
        				nextNodeProRoleSelector.append("<option value='" + nodeProRolesArr[i].code + "' " + (nodeProRolesArr[i].code==initNextNodeProRoleCode ? "selected":"") + ">" + nodeProRolesArr[i].name + "</option>");
        			}*/
    			}

    			//下一节点处理用户选择器
    			var nextNodeProUserSelector = g.tranObj["nextNodeProUserSelector"] = $("#tranDiv").find("select[name='nextNodeProUserId']");

    			//下一节点处理单位选择器
    			var initProOrgSysCode = '';
    			var nextNodeProOrgSelector = g.tranObj["nextNodeProOrgSelector"] = $("#tranDiv").find("input[name='nextNodeProOrg']").ligerDictionary({
    				dataAction:'server',
    				url:p.getNextProOrgUrl,
    				valueField:'sysCode',
    				nameField:'name',
    				searchField:'code,name',
    				totalName:'total',
    				recordName:'rows',
    				parms:{"actKey":p.actKey,"nextNodeId":nextNodeSelector.val()},
    				initValue:initProOrgSysCode,
    				width:236,
    				resultWidth:258,
    				isFixOptions:false,
    				pageSize:5,
    				isInitSelectFirst:true,
    				showDirection:'bottom',
    				hiddenField:'nextNodeProOrgSysCode',
    				valueChangeHandle:function(value,manage){//alert("字典值已改变");
    					if(value != null && value != ""){
    						g._loadProUserData();
    					}else{
    						nextNodeProUserSelector.html('');
    					}
    				}
    			});

    			//绑定节点联动事件
    			nextNodeSelector.bind('change',function(){
    				//清空角色、单位、用户信息
    				nextNodeProRoleSelector.html('');

        			//屏蔽通过角色筛选处理人员 --addby dsx 20190725
        			nextNodeProRoleSelector.append("<option value=''></option>");
//    				var selectedNode = null;
//        			for(var i=0;i<nextNodeList.length;i++){
//        				if(nextNodeList[i].id==nextNodeSelect.val()){
//        					selectedNode = nextNodeList[i];
//        					break;
//        				}
//        			}
//        			if(!selectedNode){
//        				nextNodeProRoleSelector.append("<option value=''></option>");
//        			}else{
//        				var nrArr = selectedNode.proRoleList;
//            			if(nrArr && nrArr.length>0){
//            				nextNodeProRoleSelect.html('');
//                			for(var i=0;i<nrArr.length;i++){
//                				nextNodeProRoleSelector.append("<option value='"+nrArr[i].code+"'>"+nrArr[i].name+"</option>");
//                			}
//            			}
//        			}

        			//清空单位
        			nextNodeProOrgSelector.clear();
        			nextNodeProOrgSelector.setParm("nextNodeId",nextNodeSelector.val());

        			//清空用户
        			nextNodeProUserSelector.html('');
    			});

    			$("#tranDiv").find("input[name='isAnyOrg']").bind('click',function(e){
    				var target = e.target || e.srcElement;
    				nextNodeProOrgSelector.setParm("isAnyOrg",$(target).is(":checked"));
    			});
    			g.tranObj["isContainsChildOrg"]=false;
    			$("#tranDiv").find("input[name='isContainsChildOrg']").bind('click',function(e){
    				var target = e.target || e.srcElement;
    				g.tranObj["isContainsChildOrg"]=$(target).is(":checked");
    				//p.isContainsChildOrg=$(target).is(":checked");
    				g._loadProUserData();
    			});
    		}
        },

        _buildSubmitButtons:function(){
        	var g = this, p = this.options;

        	var htmlArr = [];

        	htmlArr.push("<div class='form-actions'>");
        	htmlArr.push("  <input class='actProcessButton btn btn-primary' type='button' value='确定'>");
            htmlArr.push("  <input class='actProcessButton btn' type='button' value='重置'>");

        	htmlArr.push("</div>");

        	return htmlArr.join('');
        },

        _loadProUserData:function(){
        	var g = this, p = this.options;
        	if(!g.tranObj["nextNodeProUserSelector"] || g.tranObj["nextNodeProUserSelector"].length<1){
        		return false;
        	}

			//只要下一节点留转到市局，那么默认不全选，而且最多只能选2个。
			var nextSysCode = g.tranObj["nextNodeProOrgSelector"].getValue()
			var selectedHtml = "selected='selected'";
			if(nextSysCode.substring(0,4)+"00000000" == nextSysCode && nextSysCode.substring(0,2)+"0000000000" != nextSysCode){
				selectedHtml = "";
			}
			$.ajax({
				type: "post",
				url: p.getNextProUserUrl,
				data: {"page":-1,"pagesize":-1,"nextNodeProOrgSysCode": g.tranObj["nextNodeProOrgSelector"].getValue(),"nextNodeId":g.tranObj["nextNodeSelector"].val(),"actKey":p.actKey,"nextNodeProRole":g.tranObj["nextNodeProRoleSelector"].val(),isContainsChildOrg:g.tranObj["isContainsChildOrg"]},
				dataType: 'json',
				cache: false,
				success: function(resultData) {
					var data = resultData.rows || [];
					g.tranObj["nextNodeProUserSelector"].html('');
					for ( var j = 0; j < data.length; j++) {
						g.tranObj["nextNodeProUserSelector"].append("<option  value='" + data[j].id + "'  "+selectedHtml+">" + data[j].name + "【" + data[j].loginId + "】</option>");
					}
				}
			});
        },

        reloadProOrgData:function(){
        	var g = this, p = this.options;
        	g.tranObj["nextNodeProOrgSelector"].clear();
        	g.tranObj["nextNodeProOrgSelector"].setParm("nextNodeId",g.tranObj["nextNodeSelector"].val());
        },

        reloadProUserData:function(){
        	var g = this, p = this.options;
        	g._loadProUserData();
        },

        addProUsers:function(data){
        	var g = this, p = this.options;
        	var currentSelectRole = g.tranObj["nextNodeProRoleSelector"].val() || '';
        	if(currentSelectRole==''){
        		return false;
        	}
        	var currentSelectOrg = g.tranObj["nextNodeProOrgSelector"].getValue() || '';
        	if(currentSelectOrg==''){
        		return false;
        	}

        	//数组
			if($.isArray(data)){
				for(var j in data){
					if(data[j].id && data[j].loginId && data[j].orgSysCode && data[j].roleCode){
						var isInList = g.tranObj["nextNodeProUserSelector"].find("option[value='"+data[j].id+"']").length>0 ? true:false;
						if(isInList===false){
							//判断是否满足当前所选的角色和单位
							if(data[j].roleCode==currentSelectRole && data[j].orgSysCode==currentSelectOrg){
								g.tranObj["nextNodeProUserSelector"].append("<option value='" + data[j].id + "' selected='selected'>" + data[j].name + "【" + data[j].loginId + "】</option>");
							}
						}
					}
				}
			}
			//单个对象
			else if(typeof(data) == 'object' && data!=null){
				if(data.id && data.loginId && data.orgSysCode && data.roleCode){
					var isInList = g.tranObj["nextNodeProUserSelector"].find("option[value='"+data.id+"']").length>0 ? true:false;
					if(isInList===false){
						//判断是否满足当前所选的角色和单位
						if(data.roleCode==currentSelectRole && data.orgSysCode==currentSelectOrg){
							g.tranObj["nextNodeProUserSelector"].append("<option value='" + data.id + "' selected='selected'>" + data.name + "【" + data.loginId + "】</option>");
						}
					}
				}
			}
        },

        removeProUsers:function(ids){
        	var g = this, p = this.options;
        	var removeIdsStr = ids || '';
        	var removeIdArr = removeIdsStr.split(',');
        	for(var i in removeIdArr){
        		g.tranObj["nextNodeProUserSelector"].find("option[value='"+removeIdArr[i]+"']").remove();
        	}
        },

        reRender:function(){
        	var g = this, p = this.options;

        	//重置变量值
			g.engine = null;
			g.initType = '';
			g.isEnablePro = false;
			g.currentOperForm = '';

			//清空动态生成的表单
        	g.container.html('');

        	//重新初始化
            this._init();
            this._preRender();
            this.trigger('render');
            this._render();
            this.trigger('rendered');
            this._rendered();
        },

        getSubmitData:function(){
        	var g = this, p = this.options;
        	var actSubmitformData = null;
        	if($("#"+g.currentOperForm+"_processForm").length<1){
        		return actSubmitformData;
        	}else{
        		actSubmitformData = $("#"+g.currentOperForm+"_processForm").serializeObject();
        	}
        	if(!actSubmitformData){
        		return null;
        	}

        	//排除变更处理人时会对处理结果和处理意见进行校验
        	if(g.currentOperForm != '04'){
	        	if(actSubmitformData["proResultType2"] && actSubmitformData["proResultType2"]!=""){
	        		if(actSubmitformData["proResultType2"]=='01'){
	        			actSubmitformData["proResultType"]="approve";
	        		}else if(actSubmitformData["proResultType2"]=='02'){
	        			actSubmitformData["proResultType"]="disagree";
	        		}else if(actSubmitformData["proResultType2"]=='03'){
	        			actSubmitformData["proResultType"]="reject";
	        		}
	        	}else{
	        		alert("请选择处理结果！");
	        		return null;
	        	}

	        	if(!actSubmitformData["proOpinion"] || actSubmitformData["proOpinion"]==''){
	        		if(p.isOpinionRequired===true){
		        		alert("请填写处理意见！");
		        		return null;
	        		}
	        	}
        	}

        	//判断是否选择了下一处理用户

        	//排除审核审批不同意、退回
        	if(actSubmitformData["proResultType2"]!='02' && actSubmitformData["proResultType2"]!='03'){
        		if(g.currentOperForm!='03'){
                	if(!actSubmitformData["nextNodeProUserId"] || actSubmitformData["nextNodeProUserId"]==''){
                		alert("请选择下一处理用户！");
                		return null;
                	}

					//只要下一节点留转到市局，那么默认不全选，而且最多只能选2个。
					var nextSysCode = g.tranObj["nextNodeProOrgSelector"].getValue()
					if(nextSysCode.substring(0,4)+"00000000" == nextSysCode && nextSysCode.substring(0,2)+"0000000000" != nextSysCode ){
						if(actSubmitformData["nextNodeProUserId"] && actSubmitformData["nextNodeProUserId"] != ''){
							var nextUserIdArray = actSubmitformData["nextNodeProUserId"].toString();
							if(nextUserIdArray.split(",").length > 2){
								alert("最多只能选取两位市局领导进行审批！");
								return null;
							}
						}
					}
        		}
        	}

        	var bizFormData = null;
        	if(p.bizFormDom && p.bizFormDom!=''){
        		if($(p.bizFormDom).length>0){
        			bizFormData = $(p.bizFormDom).serializeObject();
        		}
        		if(bizFormData){
        			for(var pname in bizFormData){
        				var paramName = 'bizFormData["' + pname + '"]';
        				actSubmitformData[paramName] = bizFormData[pname];
        			}
        		}
        	}

        	if(actSubmitformData["nextNodeProUserId"]){
        		actSubmitformData["nextNodeProUserId"] = actSubmitformData["nextNodeProUserId"].toString();
        	}

        	return actSubmitformData;
        },

        setProOpinion:function(val){
        	var g = this, p = this.options;
        	if(!val || val=='') return;
        	var actSubmitform = $("#"+g.currentOperForm+"_processForm");
        	if(actSubmitform.length>0){
        		actSubmitform .find("textarea[name='proOpinion']").val(val);
        	}
        }
    });
})(jQuery);
