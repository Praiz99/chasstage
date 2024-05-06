(function ($){
	$.fn.jdonePermissionMenu = function (options){
		var defaults = {  //自定义默认方法
				init:function (){
					$.ajax({
						url:settings.ctx + '/index/geteasyUiTreeMenuData',
						dataType:'json',
						type:'POST',
						data:{appId:settings.appId,roleId:settings.roleId},
						async:false,
						success:function (data){
							$("#mainIframe").attr("src",data.indexUrl);
							if(data.ztree.length == 0){
								return;
							}
							new Menu({
						    	bindDom:settings.option,
						    	data:data.ztree,
						    	//dataType:'1',
						    	midField:'menuid',
						    	murlField:'url',
						    	nameField:'menuname',
						    	itemClick:function(mgr,menu){
						    		if(menu.target && menu.target != ""){
						    			window.open(menu.url,menu.target);
						    			return false;
						    		}
						    		if(menu && menu[mgr.murlField] && menu[mgr.murlField]!=''){
						    			addTab(menu[mgr.nameField],menu[mgr.murlField]);
						    		}
						    	}
						    });
						}
					});
				}
		};
		
		var params = {  //自定义默认变量
			option : this,
			appId : "",
			roleId : "",
			ctx : "${ctx}"
		};
		
		render = function (){
			$.ajax({
				url: settings.ctx + '/index/geteasyUiTreeMenuData',
				dataType:'json',
				type:'POST',
				data:{appId:settings.appId,roleId:settings.roleId},
				async:false,
				success:function (data){
					if(data.ztree.length == 0){
						return;
					}
					new Menu({
				    	bindDom:settings.option,
				    	data:data.ztree,
				    	//dataType:'1',
				    	midField:'menuid',
				    	murlField:'url',
				    	nameField:'menuname',
				    	itemClick:function(mgr,menu){
				    		if(menu.target && menu.target != ""){
				    			window.open(menu.url,menu.target);
				    			return false;
				    		}
				    		if(menu && menu[mgr.murlField] && menu[mgr.murlField]!=''){
				    			addTab(menu[mgr.nameField],menu[mgr.murlField]);
				    		}
				    	}
				    });
				}
			});
		};
		
		_clear = function (){
			$(settings.option).html('');
		};
		
		setParams = function(obj){
			settings = $.extend(params,obj);
		};
		
		getQueryString = function (name){
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r != null) return unescape(r[2]); return null; 
		};
		
		var settings = $.extend(defaults,params,options);
		defaults.init();
		
		
		return settings; 
	};
	
	$.fn.jdonePermissionApp = function(options){
		var defaults = {
				init : function (){
					$(settings.option).html('');
					$.ajax({
						url: settings.ctx + '/getAppsData',
						dataType:'json',
						type:'POST',
						data:{roleId:settings.roleId},
						success:function (data){
							$(data).each(function (i,o){
								var li = "<li value="+o.id+" ><a value="+o.id+" onclick='ChangeApp(this);' class='text'>"+o.name+"</a></li>";
								$(settings.option).append(li);
							});
							initAppSelect();  //初始化应用选择。
							settings.callBack(settings);
						}
					});
				}
		};
		
		var params = {
			roleId : "",  //角色ID
			option : this,  //当前对象
			appId : "",
			ctx : "${ctx}",  //
			callBack : function (){
				
			}
		};
		
		initAppSelect = function (){
			var appId = settings.appId;
		    var lis = $(settings.option).find("li");
		    if(getQueryString("appId")){
				appId = getQueryString("appId");
				settings.appId = appId;
			}
		    $(lis).each(function (i,o){
		    	$(o).css("background-color","");
		    });
		    if(appId == null || appId == ""){
		    	 $(".logo").find("span").html($(lis).eq(0).text());  //改变心态LOGO文字,默认初始化第一个系统的LOGO文字.
		    	lis.eq(0).css("background-color","#0d89c3");
		    	settings.appId = lis.eq(0).attr("value");
		    	return;
		    }
		    $(lis).each(function (i,o){
		    	if($(o).attr("value") == appId){
		    		$(".logo").find("span").html($(o).text());  //改变心态LOGO文字.
		    		$(o).css("background-color","#0d89c3");
		    		return;
		    	}
		    });
		};
		
		ChangeApp = function (obj){
			if(location.href.indexOf("?") == -1){
				location.href = location.href+"?appId="+$(obj).attr('value');
			}else{
				var url = location.href.substring(0,location.href.indexOf("?"));
				location.href = url + "?appId="+$(obj).attr('value');
			}
			//不刷新页面则启用此段。
			/*settings.appId = $(obj).attr('value');  
			setParams({appId:settings.appId,roleId:settings.roleId,ctx:settings.ctx});
			_clear();
			render();
			initAppSelect();*/
		};
		
		getQueryString = function (name){
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r != null) return unescape(r[2]); return null; 
		};
		
		var settings = $.extend(defaults,params,options);
		defaults.init();
		return settings; 
	};
	
	$.fn.jdoneRoleSelect = function (options){
		var defaults = {
				init : function (){
					$.ajax({
						url: settings.ctx + '/getRoleSelectData',
						dataType:'json',
						type:'POST',
						aysnc:false,
						success:function (data){
							var roles = jQuery.parseJSON(data.roles);
							var user = jQuery.parseJSON(data.user);
							var roleId = data.roleId || '';
							var userRoleId = data.userRoleId || '';
							settings.roles = roles;
							settings.user = user;
							settings.roleId = roleId;
							settings.userRoleId = userRoleId;
							settings.isSuperAdmin = data.isSuperAdmin;
							settings.userObj.html(user.name);
							settings.clickRole.bind('click',openSelectRoleDialog);
							if(roles.length == 1 && settings.roleId == ""){  //如果只有一个那么就默认选择这一个.
								$.ajax({
									url: settings.ctx + '/saveSessionParamByRoleId',
									data:{roleId:roles[0].roleId,userRoleId:roles[0].id},
									type:'POST',
									dataType:'json',
									async:false,
									success:function (data){
										if(data.success){
											settings.roleId = roles[0].roleId;
											settings.userRoleId = roles[0].id;
										}
									}
								});
							}
							if(settings.roleId == "" && (!data.isSuperAdmin)  && (!data.isAdmin)){
								var content = '',isdefault = false;
								$(roles).each(function (i,o){
									if(o.id == settings.user.defaultUserRoleId){
										//如果有默认的那么就默认选择该角色登录
										isdefault = true;
										roleId = o.roleId;
										userRoleId = o.id;
										$.ajax({
											url: settings.ctx + '/saveSessionParamByRoleId',
											data:{roleId:roleId,userRoleId:userRoleId},
											type:'POST',
											dataType:'json',
											success:function (data){
												if(data.success){
													settings.roleId = roleId;
													settings.userRoleId = userRoleId;
													settings.callBack(settings);
												}
											}
										});
										//已经使用默认角色登录后则无需在弹出选择角色窗口了.
										return false;
									}
									if((roleId == o.roleId && o.id == userRoleId) || settings.isflag){
										content += '<input type="radio" name="role" class="rdolist" checked="checked" id="'+o.id+'"  value="'+o.roleId+'"> '+
										'<label class="rdobox checked">'+
										'<span class="check-image" style="background: url(static/jdone/style/sys/imgs/index/input-checked.png);"></span>'+
										'<span class="radiobox-content">'+o.roleName+(o.orgName == null ? "" : '('+o.orgName+')')+'</span>'+   
										'</label>';
									}else{
										content += '<input type="radio" name="role" class="rdolist" id="'+o.id+'"  value="'+o.roleId+'"> '+
										'<label class="rdobox unchecked">'+
										'<span class="check-image" style="background: url(static/jdone/style/sys/imgs/index/input-unchecked.png);"></span>'+
										'<span class="radiobox-content">'+o.roleName+(o.orgName == null ? "" : '('+o.orgName+')')+'</span>'+   
										'</label>';
									}
								});
								if(isdefault) return false;
								$(settings.option).dialog({  
									content: content,  
									width: '45%',  
									height: '45%',  
									modal: true,  
									title: "选择登录身份",  
									buttons:[
										{
											text:'确认并设为默认', 
											left:50,
											handler:function(){ 
												var val=$('input:radio[name="role"]:checked').val();
												var userRid = $('input:radio[name="role"]:checked').attr("id");
												if(!val){
													$.messager.alert('提示',"请选择登录角色!");
													return false;
												}
												$.ajax({
													url: settings.ctx + '/saveUserDefaultRoleLogin',
													data:{userId:settings.user.userId,userRoleId:userRid},
													type:'POST',
													dataType:'json',
													success:function (data){
														if(!data.success){
															$.messager.alert('提示',"设置默认失败:"+data.msg);
															return;
														}else{
															$.ajax({
																url: settings.ctx + '/saveSessionParamByRoleId',
																data:{roleId:val,userRoleId:userRid},
																type:'POST',
																dataType:'json',
																success:function (data){
																	if(data.success){
																		/*settings.roleId = val;
																		settings.userRoleId = userRid;
																		settings.callBack(settings);
																		return;*/
																		location.reload(true);
																	}
																}
															});
															settings.user.defaultUserRoleId = userRid;
														}
													}
												});
												$(settings.option).dialog("close");
											}
										},
										{
											text:'确认', 
											left:50,
											handler:function(){ 
												var val=$('input:radio[name="role"]:checked').val();
												var userRid = $('input:radio[name="role"]:checked').attr("id");
												if(!val){
													$.messager.alert('提示',"请选择登录角色!");
													return false;
												}
												$.ajax({
													url: settings.ctx + '/saveSessionParamByRoleId',
													data:{roleId:val,userRoleId:userRid},
													type:'POST',
													dataType:'json',
													success:function (data){
														if(data.success){
															settings.roleId = val;
															settings.userRoleId = userRid;
															settings.callBack(settings);
															return;
														}
													}
												});
												$(settings.option).dialog("close");
											}
										}
										]
								});  
								//隐藏窗口关闭按钮
								$(settings.option).prev().find("div[class='panel-tool']").bind('click',function (){
									location.href= settings.ctx + '/index/loginOut';
								});
								//绑定角色单选框点击事件
								$(".rdobox").click(function () {
									$(this).prev().prop("checked", "checked");
									rdochecked('rdolist');
								});
							}
							if((settings.roleId || (data.isSuperAdmin) || (data.isAdmin))){  //超级管理员 或 管理员 或者 选定角色后进入
								//如果超级管理员或者管理员有默认值那么则使用默认值
								if(settings.user.defaultUserRoleId && !settings.roleId){
									$(settings.roles).each(function (j,k){
										if(k.id == settings.user.defaultUserRoleId){
											$.ajax({
												url: settings.ctx + '/saveSessionParamByRoleId',
												data:{roleId:k.roleId,userRoleId:k.id},
												type:'POST',
												dataType:'json',
												success:function (data){
													if(data.success){
														settings.roleId = k.roleId;
														settings.userRoleId = k.id;
														settings.callBack(settings);
													}
												}
											});
										}
									});
									return false;
								}
								if(settings.roleId){
									settings.callBack(settings);
								}
								if(data.isSuperAdmin && !settings.roleId){  //如果是超级管理员则无论是否多角色都不弹出框选择，默认使用最高角色登录.
									$(settings.roles).each(function (j,k){
										if(k.isSuperAdmin){
											$.ajax({
												url: settings.ctx + '/saveSessionParamByRoleId',
												data:{roleId:k.roleId,userRoleId:k.id},
												type:'POST',
												dataType:'json',
												success:function (data){
													if(data.success){
														settings.roleId = k.roleId;
														settings.userRoleId = k.id;
														settings.callBack(settings);
													}
												}
											});
											return false;
										}
									});
								}
								if(data.isAdmin && !data.isSuperAdmin && !settings.roleId){  //如果是管理员则无论是否多角色都不弹出框选择，默认使用最高角色登录.
									$(settings.roles).each(function (j,k){
										if(k.isAdmin){
											$.ajax({
												url: settings.ctx + '/saveSessionParamByRoleId',
												data:{roleId:k.roleId,userRoleId:k.id},
												type:'POST',
												dataType:'json',
												success:function (data){
													if(data.success){
														settings.roleId = k.roleId;
														settings.userRoleId = k.id;
														settings.callBack(settings);
													}
												}
											});
											return false;
										}
									});
								}
							}
						}
					});
				}
		};
		
		var params = {
			ctx : "${ctx}",
			option : this,  //当前对象
			target : null,  //其他组件对象
			userObj : null,  //显示元素用户对象
			clickRole :null,  //点击选择角色对象
			roleId : "",  //角色ID
			user: null,  //用户对象
			roles : null,  //该用户所有角色
			isflag : false,  //如果只有一个角色那么就默认选中.
			isSuperAdmin : false,
			callBack : function (){
				
			}
		};
		
		rdochecked = function(tag) {
		    $('.' + tag).each(function (i) {
		        var rdobox = $('.' + tag).eq(i).next();
		        if ($('.' + tag).eq(i).prop("checked") == false) {
		            rdobox.removeClass("checked");
		            rdobox.addClass("unchecked");
		            rdobox.find(".check-image").css("background", "url(static/jdone/style/sys/imgs/index/input-unchecked.png)");
		        }
		        else {
		            rdobox.removeClass("unchecked");
		            rdobox.addClass("checked");
		            rdobox.find(".check-image").css("background", "url(static/jdone/style/sys/imgs/index/input-checked.png)");
		        }
		    });
		};
		
		openSelectRoleDialog = function(){
			var roles = settings.roles;
			if(settings.isSuperAdmin){  //如果是超级管理员就无需切换用户.
				//$.messager.alert('提示',"当前用户是超级管理员无需切换角色!");
				//return;
			}
			var roleId = settings.roleId;
			var userRoleId = settings.userRoleId;
			var content = '',isdefault = false;
			$(roles).each(function (i,o){
				if(o.id == settings.user.defaultUserRoleId){
					//isdefault = true;
					isdefault = false;  //默认角色（暂时停用区别边框）。
				}
				if((roleId == o.roleId && userRoleId == o.id) || settings.isflag){
					content += '<input type="radio" name="role" class="rdolist" checked="checked" id="'+o.id+'" value="'+o.roleId+'"> '+
					'<label class="rdobox checked" style="'+(isdefault ? "border:4px solid black" : "")+'">'+
					'<span class="check-image" style="background: url(static/jdone/style/sys/imgs/index/input-checked.png);"></span>'+
					'<span class="radiobox-content">'+o.roleName+(o.orgName == null ? "" : '('+o.orgName+')')+'</span>'+   
					'</label>';
				}else{
					content += '<input type="radio" name="role" class="rdolist" id="'+o.id+'" value="'+o.roleId+'"> '+
					'<label class="rdobox unchecked" style="'+(isdefault ? "border:4px solid black" : "")+'">'+
					'<span class="check-image" style="background: url(static/jdone/style/sys/imgs/index/input-unchecked.png);"></span>'+
					'<span class="radiobox-content">'+o.roleName+(o.orgName == null ? "" : '('+o.orgName+')')+'</span>'+   
					'</label>';
				}
				isdefault = false;
			});
			$(settings.option).dialog({  
				content: content,  
				width: '45%',  
				height: '45%',  
				modal: true,  
				title: "选择登录身份",  
				buttons:[
			     	{
						text:'确认并设为默认', 
						left:50,
						handler:function(){
							var val=$('input:radio[name="role"]:checked').val();
							var userRid = $('input:radio[name="role"]:checked').attr("id");
							$.ajax({
								url: settings.ctx + '/saveUserDefaultRoleLogin',
								data:{userId:settings.user.userId,userRoleId:userRid},
								type:'POST',
								dataType:'json',
								success:function (data){
									if(!data.success){
										$.messager.alert('提示',"设置默认失败:"+data.msg);
										return;
									}else{
										$.ajax({
											url: settings.ctx + '/saveSessionParamByRoleId',
											data:{roleId:val,userRoleId:userRid},
											type:'POST',
											dataType:'json',
											success:function (data){
												if(data.success){
													location.reload(true);
												}
											}
										});
									}
								}
							});
						}
					},
			     	{
						text:'确认', 
						left:50,
						handler:function(){
							var val=$('input:radio[name="role"]:checked').val();
							var userRid = $('input:radio[name="role"]:checked').attr("id");
							$.ajax({
								url: settings.ctx + '/saveSessionParamByRoleId',
								data:{roleId:val,userRoleId:userRid},
								type:'POST',
								dataType:'json',
								success:function (data){
									if(data.success){
										location.reload(true);
									}
								}
							});
						}
			     	}
			     	]
			}); 
			//绑定角色单选框点击事件
			$(".rdobox").click(function () {
				$(this).prev().prop("checked", "checked");
				rdochecked('rdolist');
			});
		};
		
		var settings = $.extend(defaults,params,options);
		defaults.init();
		return settings; 
	};
})(jQuery);