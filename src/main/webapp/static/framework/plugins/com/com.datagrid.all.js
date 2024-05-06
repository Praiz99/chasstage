/**
* jQuery ligerUI 1.2.2
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 
*/
(function ($)
{
    //ligerui 继承方法
    Function.prototype.ligerExtend = function (parent, overrides)
    {
        if (typeof parent != 'function') return this;
        //保存对父类的引用
        this.base = parent.prototype;
        this.base.constructor = parent;
        //继承
        var f = function () { };
        f.prototype = parent.prototype;
        this.prototype = new f();
        this.prototype.constructor = this;
        //附加属性方法
        if (overrides) $.extend(this.prototype, overrides);
    };
    //延时加载
    Function.prototype.ligerDefer = function (o, defer, args)
    {
        var fn = this;
        return setTimeout(function () { fn.apply(o, args || []); }, defer);
    };

    // 核心对象
    window.liger = $.ligerui = {
        version: 'V1.2.0',
        managerCount: 0,
        //组件管理器池
        managers: {},
        managerIdPrev: 'ligerui',
        //管理器id已经存在时自动创建新的
        autoNewId: true,
        //错误提示
        error: {
            managerIsExist: '管理器id已经存在'
        },
        pluginPrev: 'liger',
        getId: function (prev)
        {
            prev = prev || this.managerIdPrev;
            var id = prev + (1000 + this.managerCount);
            this.managerCount++;
            return id;
        },
        add: function (manager)
        {
            if (arguments.length == 2)
            {
                var m = arguments[1];
                m.id = m.id || m.options.id || arguments[0].id;
                this.addManager(m);
                return;
            }
            if (!manager.id) manager.id = this.getId(manager.__idPrev());
            if (this.managers[manager.id]) manager.id = this.getId(manager.__idPrev());
            if (this.managers[manager.id])
            {
                throw new Error(this.error.managerIsExist);
            }
            this.managers[manager.id] = manager;
        },
        remove: function (arg)
        {
            if (typeof arg == "string" || typeof arg == "number")
            {
                delete liger.managers[arg];
            }
            else if (typeof arg == "object")
            {
                if (arg instanceof liger.core.Component)
                {
                    delete liger.managers[arg.id];
                }
                else
                {
                    if (!$(arg).attr(this.idAttrName)) return false;
                    delete liger.managers[$(arg).attr(this.idAttrName)];
                }
            }
        },
        //获取ligerui对象
        //1,传入ligerui ID
        //2,传入Dom Object
        get: function (arg, idAttrName)
        {
            idAttrName = idAttrName || "ligeruiid";
            if (typeof arg == "string" || typeof arg == "number")
            {
                return liger.managers[arg];
            }
            else if (typeof arg == "object")
            {
                var domObj = arg.length ? arg[0] : arg;
                var id = domObj[idAttrName] || $(domObj).attr(idAttrName);
                if (!id) return null;
                return liger.managers[id];
            }
            return null;
        },
        //根据类型查找某一个对象
        find: function (type)
        {
            var arr = [];
            for (var id in this.managers)
            {
                var manager = this.managers[id];
                if (type instanceof Function)
                {
                    if (manager instanceof type)
                    {
                        arr.push(manager);
                    }
                }
                else if (type instanceof Array)
                {
                    if ($.inArray(manager.__getType(), type) != -1)
                    {
                        arr.push(manager);
                    }
                }
                else
                {
                    if (manager.__getType() == type)
                    {
                        arr.push(manager);
                    }
                }
            }
            return arr;
        },
        //$.fn.liger{Plugin} 和 $.fn.ligerGet{Plugin}Manager
        //会调用这个方法,并传入作用域(this)
        //parm [plugin]  插件名
        //parm [args] 参数(数组)
        //parm [ext] 扩展参数,定义命名空间或者id属性名
        run: function (plugin, args, ext)
        {
            if (!plugin) return;
            ext = $.extend({
                defaultsNamespace: 'ligerDefaults',
                methodsNamespace: 'ligerMethods',
                controlNamespace: 'controls',
                idAttrName: 'ligeruiid',
                isStatic: false,
                hasElement: true,           //是否拥有element主体(比如drag、resizable等辅助性插件就不拥有)
                propertyToElemnt: null      //链接到element的属性名
            }, ext || {});
            plugin = plugin.replace(/^ligerGet/, '');
            plugin = plugin.replace(/^liger/, '');
            if (this == null || this == window || ext.isStatic)
            {
                if (!liger.plugins[plugin])
                {
                    liger.plugins[plugin] = {
                        fn: $[liger.pluginPrev + plugin],
                        isStatic: true
                    };
                }
                return new $.ligerui[ext.controlNamespace][plugin]($.extend({}, $[ext.defaultsNamespace][plugin] || {}, $[ext.defaultsNamespace][plugin + 'String'] || {}, args.length > 0 ? args[0] : {}));
            }
            if (!liger.plugins[plugin])
            {
                liger.plugins[plugin] = {
                    fn: $.fn[liger.pluginPrev + plugin],
                    isStatic: false
                };
            }
            if (/Manager$/.test(plugin)) return liger.get(this, ext.idAttrName);
            this.each(function ()
            {
                if (this[ext.idAttrName] || $(this).attr(ext.idAttrName))
                {
                    var manager = liger.get(this[ext.idAttrName] || $(this).attr(ext.idAttrName));
                    if (manager && args.length > 0) manager.set(args[0]);
                    //已经执行过 
                    return;
                }
                if (args.length >= 1 && typeof args[0] == 'string') return;
                //只要第一个参数不是string类型,都执行组件的实例化工作
                var options = args.length > 0 ? args[0] : null;
                var p = $.extend({}, $[ext.defaultsNamespace][plugin], $[ext.defaultsNamespace][plugin + 'String'], options);
                if (ext.propertyToElemnt) p[ext.propertyToElemnt] = this;
                if (ext.hasElement)
                {
                    new $.ligerui[ext.controlNamespace][plugin](this, p);
                }
                else
                {
                    new $.ligerui[ext.controlNamespace][plugin](p);
                }
            });
            if (this.length == 0) return null;
            if (args.length == 0) return liger.get(this, ext.idAttrName);
            if (typeof args[0] == 'object') return liger.get(this, ext.idAttrName);
            if (typeof args[0] == 'string')
            {
                var manager = liger.get(this, ext.idAttrName);
                if (manager == null) return;
                if (args[0] == "option")
                {
                    if (args.length == 2)
                        return manager.get(args[1]);  //manager get
                    else if (args.length >= 3)
                        return manager.set(args[1], args[2]);  //manager set
                }
                else
                {
                    var method = args[0];
                    if (!manager[method]) return; //不存在这个方法
                    var parms = Array.apply(null, args);
                    parms.shift();
                    return manager[method].apply(manager, parms);  //manager method
                }
            }
            return null;
        },

        //扩展
        //1,默认参数     
        //2,本地化扩展 
        defaults: {},
        //3,方法接口扩展
        methods: {},
        //命名空间
        //核心控件,封装了一些常用方法
        core: {},
        //命名空间
        //组件的集合
        controls: {},
        //plugin 插件的集合
        plugins: {}
    };


    //扩展对象
    $.ligerDefaults = {};

    //扩展对象
    $.ligerMethos = {};

    //关联起来
    liger.defaults = $.ligerDefaults;
    liger.methods = $.ligerMethos;

    //获取ligerui对象
    //parm [plugin]  插件名,可为空
    $.fn.liger = function (plugin)
    {
        if (plugin)
        {
            return liger.run.call(this, plugin, arguments);
        }
        else
        {
            return liger.get(this);
        }
    };


    //组件基类
    //1,完成定义参数处理方法和参数属性初始化的工作
    //2,完成定义事件处理方法和事件属性初始化的工作
    liger.core.Component = function (options)
    {
        //事件容器
        this.events = this.events || {};
        //配置参数
        this.options = options || {};
        //子组件集合索引
        this.children = {};
    };
    $.extend(liger.core.Component.prototype, {
        __getType: function ()
        {
            return 'liger.core.Component';
        },
        __idPrev: function ()
        {
            return 'ligerui';
        },

        //设置属性
        // arg 属性名    value 属性值 
        // arg 属性/值   value 是否只设置事件
        set: function (arg, value)
        {
            if (!arg) return;
            if (typeof arg == 'object')
            {
                var tmp;
                if (this.options != arg)
                {
                    $.extend(this.options, arg);
                    tmp = arg;
                }
                else
                {
                    tmp = $.extend({}, arg);
                }
                if (value == undefined || value == true)
                {
                    for (var p in tmp)
                    {
                        if (p.indexOf('on') == 0)
                            this.set(p, tmp[p]);
                    }
                }
                if (value == undefined || value == false)
                {
                    for (var p in tmp)
                    {
                        if (p.indexOf('on') != 0)
                            this.set(p, tmp[p]);
                    }
                }
                return;
            }
            var name = arg;
            //事件参数
            if (name.indexOf('on') == 0)
            {
                if (typeof value == 'function')
                    this.bind(name.substr(2), value);
                return;
            }
            if (!this.options) this.options = {};
            if (this.trigger('propertychange', [arg, value]) == false) return;
            this.options[name] = value;
            var pn = '_set' + name.substr(0, 1).toUpperCase() + name.substr(1);
            if (this[pn])
            {
                this[pn].call(this, value);
            }
            this.trigger('propertychanged', [arg, value]);
        },

        //获取属性
        get: function (name)
        {
            var pn = '_get' + name.substr(0, 1).toUpperCase() + name.substr(1);
            if (this[pn])
            {
                return this[pn].call(this, name);
            }
            return this.options[name];
        },

        hasBind: function (arg)
        {
            var name = arg.toLowerCase();
            var event = this.events[name];
            if (event && event.length) return true;
            return false;
        },

        //触发事件
        //data (可选) Array(可选)传递给事件处理函数的附加参数
        trigger: function (arg, data)
        {
            if (!arg) return;
            var name = arg.toLowerCase();
            var event = this.events[name];
            if (!event) return;
            data = data || [];
            if ((data instanceof Array) == false)
            {
                data = [data];
            }
            for (var i = 0; i < event.length; i++)
            {
                var ev = event[i];
                if (ev.handler.apply(ev.context, data) == false)
                    return false;
            }
        },

        //绑定事件
        bind: function (arg, handler, context)
        {
            if (typeof arg == 'object')
            {
                for (var p in arg)
                {
                    this.bind(p, arg[p]);
                }
                return;
            }
            if (typeof handler != 'function') return false;
            var name = arg.toLowerCase();
            var event = this.events[name] || [];
            context = context || this;
            event.push({ handler: handler, context: context });
            this.events[name] = event;
        },

        //取消绑定
        unbind: function (arg, handler)
        {
            if (!arg)
            {
                this.events = {};
                return;
            }
            var name = arg.toLowerCase();
            var event = this.events[name];
            if (!event || !event.length) return;
            if (!handler)
            {
                delete this.events[name];
            }
            else
            {
                for (var i = 0, l = event.length; i < l; i++)
                {
                    if (event[i].handler == handler)
                    {
                        event.splice(i, 1);
                        break;
                    }
                }
            }
        },
        destroy: function ()
        {
            liger.remove(this);
        }
    });


    //界面组件基类, 
    //1,完成界面初始化:设置组件id并存入组件管理器池,初始化参数
    //2,渲染的工作,细节交给子类实现
    //parm [element] 组件对应的dom element对象
    //parm [options] 组件的参数
    liger.core.UIComponent = function (element, options)
    {
        liger.core.UIComponent.base.constructor.call(this, options);
        var extendMethods = this._extendMethods();
        if (extendMethods) $.extend(this, extendMethods);
        this.element = element;
        this._init();
        this._preRender();
        this.trigger('render');
        this._render();
        this.trigger('rendered');
        this._rendered();
    };
    liger.core.UIComponent.ligerExtend(liger.core.Component, {
        __getType: function ()
        {
            return 'liger.core.UIComponent';
        },
        //扩展方法
        _extendMethods: function ()
        {

        },
        _init: function ()
        {
            this.type = this.__getType();
            if (!this.element)
            {
                this.id = this.options.id || liger.getId(this.__idPrev());
            }
            else
            {
                this.id = this.options.id || this.element.id || liger.getId(this.__idPrev());
            }
            //存入管理器池
            liger.add(this);

            if (!this.element) return;

            //读取attr方法,并加载到参数,比如['url']
            var attributes = this.attr();
            if (attributes && attributes instanceof Array)
            {
                for (var i = 0; i < attributes.length; i++)
                {
                    var name = attributes[i];
                    this.options[name] = $(this.element).attr(name);
                }
            }
            //读取ligerui这个属性，并加载到参数，比如 ligerui = "width:120,heigth:100"
            var p = this.options;
            if ($(this.element).attr("ligerui"))
            {
                try
                {
                    var attroptions = $(this.element).attr("ligerui");
                    if (attroptions.indexOf('{') != 0) attroptions = "{" + attroptions + "}";
                    eval("attroptions = " + attroptions + ";");
                    if (attroptions) $.extend(p, attroptions);
                }
                catch (e) { }
            }
        },
        //预渲染,可以用于继承扩展
        _preRender: function ()
        {

        },
        _render: function ()
        {

        },
        _rendered: function ()
        {
            if (this.element)
            {
                $(this.element).attr("ligeruiid", this.id);
            }
        },
        //返回要转换成ligerui参数的属性,比如['url']
        attr: function ()
        {
            return [];
        },
        destroy: function ()
        {
            if (this.element)
            {
                $(this.element).remove();
            }
            this.options = null;
            liger.remove(this);
        }
    });


    //表单控件基类
    liger.controls.Input = function (element, options)
    {
        liger.controls.Input.base.constructor.call(this, element, options);
    };

    liger.controls.Input.ligerExtend(liger.core.UIComponent, {
        __getType: function ()
        {
            return 'liger.controls.Input';
        },
        attr: function ()
        {
            return ['nullText'];
        },
        setValue: function (value)
        {
            return this.set('value', value);
        },
        getValue: function ()
        {
            return this.get('value');
        },
        //设置只读
        _setReadonly: function (readonly)
        {
            var wrapper = this.wrapper || this.text;
            if (!wrapper || !wrapper.hasClass("l-text")) return;
            var inputText = this.inputText;
            if (readonly)
            {
                if (inputText) inputText.attr("readonly", "readonly");
                wrapper.addClass("l-text-readonly");
            } else
            {
                if (inputText) inputText.removeAttr("readonly");
                wrapper.removeClass("l-text-readonly");
            }
        },
        setEnabled: function ()
        {
            return this.set('disabled', false);
        },
        setDisabled: function ()
        {
            return this.set('disabled', true);
        },
        updateStyle: function ()
        {

        },
        resize: function (width, height)
        {
            this.set({ width: width, height: height });
        }
    });

    //全局窗口对象
    liger.win = {
        //顶端显示
        top: false,

        //遮罩
        mask: function (win)
        {
            function setHeight()
            {
                if (!liger.win.windowMask) return;
                var h = $(window).height() + $(window).scrollTop();
                liger.win.windowMask.height(h);
            }
            if (!this.windowMask)
            {
                this.windowMask = $("<div class='l-window-mask' style='display: block;'></div>").appendTo('body');
                $(window).bind('resize.ligeruiwin', setHeight);
                $(window).bind('scroll', setHeight);
            }
            this.windowMask.show();
            setHeight();
            this.masking = true;
        },

        //取消遮罩
        unmask: function (win)
        {
            var jwins = $("body > .l-dialog:visible,body > .l-window:visible");
            for (var i = 0, l = jwins.length; i < l; i++)
            {
                var winid = jwins.eq(i).attr("ligeruiid");
                if (win && win.id == winid) continue;
                //获取ligerui对象
                var winmanager = liger.get(winid);
                if (!winmanager) continue;
                //是否模态窗口
                var modal = winmanager.get('modal');
                //如果存在其他模态窗口，那么不会取消遮罩
                if (modal) return;
            }
            if (this.windowMask)
                this.windowMask.hide();
            this.masking = false;
        },

        //显示任务栏
        createTaskbar: function ()
        {
            if (!this.taskbar)
            {
                this.taskbar = $('<div class="l-taskbar"><div class="l-taskbar-tasks"></div><div class="l-clear"></div></div>').appendTo('body');
                if (this.top) this.taskbar.addClass("l-taskbar-top");
                this.taskbar.tasks = $(".l-taskbar-tasks:first", this.taskbar);
                this.tasks = {};
            }
            this.taskbar.show();
            this.taskbar.animate({ bottom: 0 });
            return this.taskbar;
        },

        //关闭任务栏
        removeTaskbar: function ()
        {
            var self = this;
            self.taskbar.animate({ bottom: -32 }, function ()
            {
                self.taskbar.remove();
                self.taskbar = null;
            });
        },
        activeTask: function (win)
        {
            for (var winid in this.tasks)
            {
                var t = this.tasks[winid];
                if (winid == win.id)
                {
                    t.addClass("l-taskbar-task-active");
                }
                else
                {
                    t.removeClass("l-taskbar-task-active");
                }
            }
        },

        //获取任务
        getTask: function (win)
        {
            var self = this;
            if (!self.taskbar) return;
            if (self.tasks[win.id]) return self.tasks[win.id];
            return null;
        },


        //增加任务
        addTask: function (win)
        {
            var self = this;
            if (!self.taskbar) self.createTaskbar();
            if (self.tasks[win.id]) return self.tasks[win.id];
            var title = win.get('title');
            var task = self.tasks[win.id] = $('<div class="l-taskbar-task"><div class="l-taskbar-task-icon"></div><div class="l-taskbar-task-content">' + title + '</div></div>');
            self.taskbar.tasks.append(task);
            self.activeTask(win);
            task.bind('click', function ()
            {
                self.activeTask(win);
                if (win.actived)
                    win.min();
                else
                    win.active();
            }).hover(function ()
            {
                $(this).addClass("l-taskbar-task-over");
            }, function ()
            {
                $(this).removeClass("l-taskbar-task-over");
            });
            return task;
        },

        hasTask: function ()
        {
            for (var p in this.tasks)
            {
                if (this.tasks[p])
                    return true;
            }
            return false;
        },

        //移除任务
        removeTask: function (win)
        {
            var self = this;
            if (!self.taskbar) return;
            if (self.tasks[win.id])
            {
                self.tasks[win.id].unbind();
                self.tasks[win.id].remove();
                delete self.tasks[win.id];
            }
            if (!self.hasTask())
            {
                self.removeTaskbar();
            }
        },

        //前端显示
        setFront: function (win)
        {
            var wins = liger.find(liger.core.Win);
            for (var i in wins)
            {
                var w = wins[i];
                if (w == win)
                {
                    $(w.element).css("z-index", "9200");
                    this.activeTask(w);
                }
                else
                {
                    $(w.element).css("z-index", "9100");
                }
            }
        }
    };


    //窗口基类 window、dialog
    liger.core.Win = function (element, options)
    {
        liger.core.Win.base.constructor.call(this, element, options);
    };

    liger.core.Win.ligerExtend(liger.core.UIComponent, {
        __getType: function ()
        {
            return 'liger.controls.Win';
        },
        mask: function ()
        {
            if (this.options.modal)
                liger.win.mask(this);
        },
        unmask: function ()
        {
            if (this.options.modal)
                liger.win.unmask(this);
        },
        min: function ()
        {
        },
        max: function ()
        {
        },
        active: function ()
        {
        }
    });


    liger.draggable = {
        dragging: false
    };

    liger.resizable = {
        reszing: false
    };


    liger.toJSON = typeof JSON === 'object' && JSON.stringify ? JSON.stringify : function (o)
    {
        var f = function (n)
        {
            return n < 10 ? '0' + n : n;
        },
		escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
		quote = function (value)
		{
		    escapable.lastIndex = 0;
		    return escapable.test(value) ?
				'"' + value.replace(escapable, function (a)
				{
				    var c = meta[a];
				    return typeof c === 'string' ? c :
						'\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
				}) + '"' :
				'"' + value + '"';
		};
        if (o === null) return 'null';
        var type = typeof o;
        if (type === 'undefined') return undefined;
        if (type === 'string') return quote(o);
        if (type === 'number' || type === 'boolean') return '' + o;
        if (type === 'object')
        {
            if (typeof o.toJSON === 'function')
            {
                return liger.toJSON(o.toJSON());
            }
            if (o.constructor === Date)
            {
                return isFinite(this.valueOf()) ?
                   this.getUTCFullYear() + '-' +
                 f(this.getUTCMonth() + 1) + '-' +
                 f(this.getUTCDate()) + 'T' +
                 f(this.getUTCHours()) + ':' +
                 f(this.getUTCMinutes()) + ':' +
                 f(this.getUTCSeconds()) + 'Z' : null;
            }
            var pairs = [];
            if (o.constructor === Array)
            {
                for (var i = 0, l = o.length; i < l; i++)
                {
                    pairs.push(liger.toJSON(o[i]) || 'null');
                }
                return '[' + pairs.join(',') + ']';
            }
            var name, val;
            for (var k in o)
            {
                type = typeof k;
                if (type === 'number')
                {
                    name = '"' + k + '"';
                } else if (type === 'string')
                {
                    name = quote(k);
                } else
                {
                    continue;
                }
                type = typeof o[k];
                if (type === 'function' || type === 'undefined')
                {
                    continue;
                }
                val = liger.toJSON(o[k]);
                pairs.push(name + ':' + val);
            }
            return '{' + pairs.join(',') + '}';
        }
    };

    //获取 默认的编辑构造器
    liger.getEditor = function (e)
    {
        var type = e.type, control = e.control, master = e.master;
        if (!type) return null;
        if (control) control = control.substr(0, 1).toUpperCase() + control.substr(1);
        return $.extend({
            create: function (container, editParm, controlOptions)
            {
                //field in form , column in grid
                var field = editParm.field || editParm.column, options = controlOptions || {};
                var p = $.extend({}, e.options);
                var inputBody = $("<input type='" + (e.password ? "password" : "text") + "'/>");
                if (e.body)
                {
                    inputBody = e.body.clone();
                }
                inputBody.appendTo(container);
                if (editParm.field)
                {
                    var txtInputName = field.name;
                    var prefixID = $.isFunction(options.prefixID) ? options.prefixID(master) : (options.prefixID || "");
                    p.id = field.id || (prefixID + field.name);
                    if ($.inArray(type, ["select", "combobox", "autocomplete", "popup"]) != -1)
                    {
                        txtInputName = field.textField || field.comboboxName;
                        if (field.comboboxName && !field.id) 
                            p.id = (options.prefixID || "") + field.comboboxName;
                    }
                    if ($.inArray(type, ["select", "combobox", "autocomplete", "popup", "radiolist", "checkboxlist", "listbox"]) != -1)
                    {
                        p.valueFieldID = prefixID + field.name;
                    } 
                    if (!e.body)
                    {
                        var inputName = prefixID  + txtInputName;
                        inputBody.attr($.extend({
                            //id: p.id,
                            name: inputName
                        }, field.attr));
                        if (field.cssClass)
                        {
                            inputBody.addClass(field.cssClass);
                        }
                        if (field.validate && !master.options.unSetValidateAttr)
                        {
                            inputBody.attr('validate', liger.toJSON(field.validate));
                        }
                    }
                    $.extend(p, field.options);
                }
                if (field.editor)
                {
                    $.extend(p, field.editor.options);
                    if (field.editor.valueColumnName) p.valueField = field.editor.valueColumnName;
                    if (field.editor.displayColumnName) p.textField = field.editor.displayColumnName;
                    if (control)
                    {
                        var defaults = liger.defaults[control];
                        for (var proName in defaults)
                        {
                            if (proName in field.editor)
                            {
                                p[proName] = field.editor[proName];
                            }
                        }
                    }
                    //可扩展参数,支持动态加载
                    var ext = field.editor.p || field.editor.ext;
                    ext = typeof (ext) == 'function' ? ext(editParm) : ext;
                    $.extend(p, ext);
                }
                //返回的是ligerui对象
                return inputBody['liger' + control](p);
            },
            getValue: function (editor, editParm)
            {
                if (editor.getValue)
                {
                    return editor.getValue();
                }
            },
            setValue: function (editor, value, editParm)
            {
                if (editor.setValue)
                {
                    editor.setValue(value);
                }
            },
            getText: function (editor, editParm)
            {
                if (editor.getText)
                {
                    return editor.getText();
                }
            },
            setText: function (editor, value, editParm)
            {
                if (editor.setText)
                {
                    editor.setText(value);
                }
            },
            getSelected: function (editor, editParm)
            {
                if (editor.getSelected)
                {
                    return editor.getSelected();
                }
            },
            resize: function (editor, width, height, editParm)
            {
                if (editParm.field) width = width - 2;
                if (editor.resize) editor.resize(width, height);
            },
            destroy: function (editor, editParm)
            {
                if (editor.destroy) editor.destroy();
            }
        }, e);
    }
    //几个默认的编辑器构造函数
    liger.editors = {
        "text": {
            control: 'TextBox'
        },
        "date": {
            control: 'DateEditor',
            setValue: function (editor, value, editParm)
            {
                // /Date(1328423451489)/
                if (typeof value == "string" && /^\/Date/.test(value))
                {
                    value = value.replace(/^\//, "new ").replace(/\/$/, "");
                    eval("value = " + value);
                }
                editor.setValue(value);
            }
        },
        "combobox": {
            control: 'ComboBox'
        },
        "spinner": {
            control: 'Spinner'
        },
        "checkbox": {
            control: 'CheckBox'
        },
        "checkboxlist": {
            control: 'CheckBoxList',
            body: $('<div></div>'),
            resize: function (editor, width, height, editParm)
            {
                editor.set('width', width - 2);
            }
        },
        "radiolist": {
            control: 'RadioList',
            body: $('<div></div>'),
            resize: function (editor, width, height, editParm)
            {
                editor.set('width', width - 2);
            }
        },
        "listbox": {
            control: 'ListBox',
            body: $('<div></div>'),
            resize: function (editor, width, height, editParm)
            {
                editor.set('width', width - 2);
            }
        },
        "popup": {
            control: 'PopupEdit'
        },
        "number": {
            control: 'TextBox',
            options: { number: true }
        },
        "currency": {
            control: 'TextBox',
            options: { currency: true }
        },
        "digits": {
            control: 'TextBox',
            options: { digits: true }
        },
        "password": {
            control: 'TextBox',
            password: true
        }
    };
    liger.editors["string"] = liger.editors["text"];
    liger.editors["select"] = liger.editors["combobox"];
    liger.editors["int"] = liger.editors["digits"];
    liger.editors["float"] = liger.editors["number"];
    liger.editors["chk"] = liger.editors["checkbox"];
    liger.editors["popupedit"] = liger.editors["popup"];

    //jQuery version fix
    $.fn.live = $.fn.on ? $.fn.on : $.fn.live;
    if (!$.browser)
    {
        var userAgent = navigator.userAgent.toLowerCase();
        $.browser = {
            version: (userAgent.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [0, '0'])[1],
            safari: /webkit/.test(userAgent),
            opera: /opera/.test(userAgent),
            msie: /msie/.test(userAgent) && !/opera/.test(userAgent),
            mozilla: /mozilla/.test(userAgent) && !/(compatible|webkit)/.test(userAgent)
        };
    }
})(jQuery);

/**
* jQuery ligerUI 1.2.0
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 
*/

(function ($)
{
    var l = $.ligerui;

    $.fn.ligerGrid = function (options)
    {
        return $.ligerui.run.call(this, "ligerGrid", arguments);
    };

    $.fn.ligerGetGridManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetGridManager", arguments);
    };

    $.ligerDefaults.Grid = {
        title: null,
        width: 'auto',                          //宽度值
        height: 'auto',                          //宽度值
        fixHeight: false,                          //宽度值
        columnWidth: null,                      //默认列宽度
        resizable: true,                        //table是否可伸缩
        url: false,                             //ajax url
        data: null,                            //初始化数据
        usePager: true,                         //是否分页
        page: 1,                                //默认当前页 
        pageSize: 10,                           //每页默认的结果数
        pageSizeOptions: [10, 20, 30, 40, 50],  //可选择设定的每页结果数
        parms: [],                         //提交到服务器的参数
        columns: [],                          //数据源
        minColToggle: 1,                        //最小显示的列
        dataType: 'server',                     //数据源：本地(local)或(server),本地是将读取p.data。不需要配置，取决于设置了data或是url
        dataAction: 'server',                    //提交数据的方式：本地(local)或(server),选择本地方式时将在客服端分页、排序。 
        showTableToggleBtn: false,              //是否显示'显示隐藏Grid'按钮 
        switchPageSizeApplyComboBox: false,     //切换每页记录数是否应用ligerComboBox
        allowAdjustColWidth: true,              //是否允许调整列宽     
        checkbox: false,                         //是否显示复选框
        allowHideColumn: true,                 //是否显示'切换列层'按钮
        enabledEdit: false,                      //是否允许编辑
        isScroll: true,                         //是否滚动 
        dateFormat: 'yyyy-MM-dd',              //默认时间显示格式
        inWindow: true,                        //是否以窗口的高度为准 height设置为百分比时可用
        statusName: '__status',                    //状态名
        method: 'post',                         //提交方式
        async: true,
        fixedCellHeight: true,                       //是否固定单元格的高度
        heightDiff: 0,                         //高度补差,当设置height:100%时，可能会有高度的误差，可以通过这个属性调整
        cssClass: null,                    //类名
        root: 'Rows',                       //数据源字段名
        record: 'Total',                     //数据源记录数字段名
        pageParmName: 'page',               //页索引参数名，(提交给服务器)
        pagesizeParmName: 'pagesize',        //页记录数参数名，(提交给服务器)
        sortnameParmName: 'sortname',        //页排序列名(提交给服务器)
        sortorderParmName: 'sortorder',      //页排序方向(提交给服务器) 
        allowUnSelectRow: false,           //是否允许反选行 
        alternatingRow: true,           //奇偶行效果
        mouseoverRowCssClass: 'l-grid-row-over',
        enabledSort: true,                      //是否允许排序
        rowAttrRender: null,                  //行自定义属性渲染器(包括style，也可以定义)
        groupColumnName: null,                 //分组 - 列名
        groupColumnDisplay: '分组',             //分组 - 列显示名字
        groupRender: null,                     //分组 - 渲染器
        totalRender: null,                       //统计行(全部数据)
        delayLoad: false,                        //初始化时是否不加载
        where: null,                           //数据过滤查询函数,(参数一 data item，参数二 data item index)
        selectRowButtonOnly: false,            //复选框模式时，是否只允许点击复选框才能选择行 
        whenRClickToSelect: false,                //右击行时是否选中
        contentType: null,                     //Ajax contentType参数
        checkboxColWidth: 27,                  //复选框列宽度
        detailColWidth: 29,                     //明细列宽度
        clickToEdit: true,                      //是否点击单元格的时候就编辑
        detailToEdit: false,                     //是否点击明细的时候进入编辑
        onEndEdit: null,
        minColumnWidth: 80,
        tree: null,                            //treeGrid模式
        isChecked: null,                       //复选框 初始化函数
        isSelected: null,                       //选择 初始化函数
        frozen: true,                          //是否固定列
        frozenDetail: false,                    //明细按钮是否在固定列中
        frozenCheckbox: true,                  //复选框按钮是否在固定列中
        detail: null,
        detailHeight: 260,
        rownumbers: false,                         //是否显示行序号
        frozenRownumbers: true,                  //行序号是否在固定列中
        rownumbersColWidth: 26,
        colDraggable: false,                       //是否允许表头拖拽
        rowDraggable: false,                         //是否允许行拖拽
        rowDraggingRender: null,
        autoCheckChildren: true,                  //是否自动选中子节点
        onRowDragDrop: null,                    //行拖拽事件
        rowHeight: 28,                           //行默认的高度
        headerRowHeight: 30,                    //表头行的高度
        toolbar: null,                           //工具条,参数同 ligerToolbar的
        headerImg: null,                        //表格头部图标
        onDragCol: null,                       //拖动列事件
        onToggleCol: null,                     //切换列事件
        onChangeSort: null,                    //改变排序事件
        onSuccess: null,                       //成功获取服务器数据的事件
        onDblClickRow: null,                     //双击行事件
        onSelectRow: null,                    //选择行事件
        onUnSelectRow: null,                   //取消选择行事件
        onBeforeCheckRow: null,                 //选择前事件，可以通过return false阻止操作(复选框)
        onCheckRow: null,                    //选择事件(复选框) 
        onBeforeCheckAllRow: null,              //选择前事件，可以通过return false阻止操作(复选框 全选/全不选)
        onCheckAllRow: null,                    //选择事件(复选框 全选/全不选)onextend
        onBeforeShowData: null,                  //显示数据前事件，可以通过reutrn false阻止操作
        onAfterShowData: null,                 //显示完数据事件
        onError: null,                         //错误事件
        onSubmit: null,                         //提交前事件
        onReload: null,                    //刷新事件，可以通过return false来阻止操作
        onToFirst: null,                     //第一页，可以通过return false来阻止操作
        onToPrev: null,                      //上一页，可以通过return false来阻止操作
        onToNext: null,                      //下一页，可以通过return false来阻止操作
        onToLast: null,                      //最后一页，可以通过return false来阻止操作
        onAfterAddRow: null,                     //增加行后事件
        onBeforeEdit: null,                      //编辑前事件
        onBeforeSubmitEdit: null,               //验证编辑器结果是否通过
        onAfterEdit: null,                       //结束编辑后事件
        onLoading: null,                        //加载时函数
        onLoaded: null,                          //加载完函数
        onContextmenu: null,                   //右击事件
        onBeforeCancelEdit: null,                 //取消编辑前事件
        onAfterSubmitEdit: null,                   //提交后事件
        onRowDragDrop: null,                       //行拖拽后事件
        onGroupExtend: null,                        //分组展开事件
        onGroupCollapse: null,                     //分组收缩事件
        onLoadData : null                       //加载数据前事件
    };
    $.ligerDefaults.GridString = {
        errorMessage: '发生错误',
        pageStatMessage: '共 {total} 条数据，每页显示',//'加载更多，共 {total} 条 ',
        pageTextMessage: 'Page',
        loadingMessage: '加载中...',
        findTextMessage: '查找',
        noRecordMessage: '没有符合条件的记录存在',
        isContinueByDataChanged: '数据已经改变,如果继续将丢失数据,是否继续?',
        cancelMessage: '取消',
        saveMessage: '保存',
        applyMessage: '应用',
        draggingMessage: '{count}行'
    };

    $.ligerDefaults.Grid_columns = {
        id: null,
        name: null,
        totalSummary: null,
        display: null,
        headerRender: null,
        isAllowHide: true,
        isSort: false,
        type: null,
        columns: null,
        width: null,
        minWidth: null,
        format: null,
        align: 'center',
        hide: false,
        editor: null,
        render: null,
        textField: null  //真正显示的字段名,如果设置了，在编辑状态时,会调用创建编辑器的setText和getText方法
    };
    $.ligerDefaults.Grid_editor = {
        type: null,
        ext: null,
        onChange: null,
        onChanged: null
    };
    //接口方法扩展
    $.ligerMethos.Grid = $.ligerMethos.Grid || {};

    //排序器扩展
    $.ligerDefaults.Grid.sorters = $.ligerDefaults.Grid.sorters || {};

    //格式化器扩展
    $.ligerDefaults.Grid.formatters = $.ligerDefaults.Grid.formatters || {};

    //编辑器扩展
    $.ligerDefaults.Grid.editors = $.ligerDefaults.Grid.editors || {};


    $.ligerDefaults.Grid.sorters['date'] = function (val1, val2)
    {
        return val1 < val2 ? -1 : val1 > val2 ? 1 : 0;
    };
    $.ligerDefaults.Grid.sorters['int'] = function (val1, val2)
    {
        return parseInt(val1) < parseInt(val2) ? -1 : parseInt(val1) > parseInt(val2) ? 1 : 0;
    };
    $.ligerDefaults.Grid.sorters['float'] = function (val1, val2)
    {
        return parseFloat(val1) < parseFloat(val2) ? -1 : parseFloat(val1) > parseFloat(val2) ? 1 : 0;
    };
    $.ligerDefaults.Grid.sorters['string'] = function (val1, val2)
    {
        if (!val1) return false;
        return val1.localeCompare(val2);
    };


    $.ligerDefaults.Grid.formatters['date'] = function (value, column)
    {
        function getFormatDate(date, dateformat)
        {
            var g = this, p = this.options;
            if (isNaN(date)) return null;
            var format = dateformat;
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds()
            }
            if (/(y+)/.test(format))
            {
                format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            for (var k in o)
            {
                if (new RegExp("(" + k + ")").test(format))
                {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return format;
        }
        if (!value) return "";
        // /Date(1328423451489)/
        if (typeof (value) == "string")
        {
        	if(/^\/Date/.test(value)){
        		value = value.replace(/^\//, "new ").replace(/\/$/, "");
        		eval("value = " + value);
        	}else{
        		value = value.replace(/-+?/g,"/");
        		value = new Date(value);
        	}
        }
        if (value instanceof Date)
        {
            var format = column.format || this.options.dateFormat || "yyyy-MM-dd";
            return getFormatDate(value, format);
        }
        else
        {
            return value.toString();
        }
    }
    //获取 默认的编辑器构造函数
    function getEditorBuilder(e)
    {
        var type = e.type, control = e.control;
        if (!type || !control) return null;
        control = control.substr(0, 1).toUpperCase() + control.substr(1);
        return $.extend({
            create: function (container, editParm)
            {
                var column = editParm.column;
                var input = $("<input type='text'/>").appendTo(container);
                var p = $.extend({}, column.editor.options);
                if (column.editor.valueColumnName) p.valueField = column.editor.valueColumnName;
                if (column.editor.displayColumnName) p.textField = column.editor.displayColumnName;
                var defaults = liger.defaults[control];
                for (var proName in defaults)
                {
                    if (proName in column.editor)
                    {
                        p[proName] = column.editor[proName];
                    }
                }
                //可扩展参数,支持动态加载
                var ext = column.editor.p || column.editor.ext;
                ext = typeof (ext) == 'function' ? ext(editParm) : ext;
                $.extend(p, ext);
                input['liger' + control](p);
                return input;
            },
            getValue: function (input, editParm)
            {
                var obj = liger.get(input);
                if (obj) return obj.getValue(); 
            },
            setValue: function (input, value, editParm)
            {
                var obj = liger.get(input);
                if (obj) obj.setValue(value); 
            },
            resize: function (input, width, height, editParm)
            {
                var obj = liger.get(input);
                if (obj) obj.resize(width, height); 
            },
            destroy: function (input, editParm)
            {
                var obj = liger.get(input);
                if (obj) obj.destroy();
            }
        }, e); 
    }
    //几个默认的编辑器构造函数
    var defaultEditorBuilders = {
        "text": {
            control: 'TextBox'
        },
        "date": {
            control: 'DateEditor',
            setValue: function (input, value, editParm)
            {
                // /Date(1328423451489)/
                if (typeof value == "string" && /^\/Date/.test(value))
                {
                    value = value.replace(/^\//, "new ").replace(/\/$/, "");
                    eval("value = " + value);
                }
                liger.get(input).setValue(value);
            }
        },
        "combobox": {
            control: 'ComboBox',
            getText: function (input, editParm)
            { 
                return liger.get(input).getText();
            },
            setText: function (input, value, editParm)
            { 
                liger.get(input).setText(value);
            }
        },
        "int": {
            control: 'Spinner',
            getValue: function (input, editParm)
            {
                return parseInt(input.val(), 10);
            }
        },
        "spinner": {
            control: 'Spinner',
            getValue: function (input, editParm)
            {
                return parseFloat(input.val());
            }
        },
        "checkbox": {
            control: 'CheckBox',
            getValue: function (input, editParm)
            {
                return input[0].checked ? 1 : 0;
            },
            setValue: function (input, value, editParm)
            {
                input.val(value ? true : false);
            }
        },
        "popup": {
            control: 'PopupEdit',
            getText: function (input, editParm)
            {
                return liger.get(input).getText();
            },
            setText: function (input, value, editParm)
            { 
                liger.get(input).setText(value);
            }
        }
    };
    defaultEditorBuilders["string"] = defaultEditorBuilders["text"];
    defaultEditorBuilders["select"] = defaultEditorBuilders["combobox"];
    defaultEditorBuilders["float"] = defaultEditorBuilders["spinner"];
    defaultEditorBuilders["chk"] = defaultEditorBuilders["checkbox"];

    //页面初始化以后才加载
    $(function ()
    {
        for (var type in defaultEditorBuilders)
        {
            var p = defaultEditorBuilders[type];
            if (!p || !p.control || type in $.ligerDefaults.Grid.editors) continue;
            $.ligerDefaults.Grid.editors[type] = getEditorBuilder($.extend({
                type: type
            }, p));
        }
    });

    $.ligerui.controls.Grid = function (element, options)
    {
        $.ligerui.controls.Grid.base.constructor.call(this, element, options);
    };

    $.ligerui.controls.Grid.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.Grid';
        },
        __idPrev: function ()
        {
            return 'grid';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.Grid;
        },
        _init: function ()
        {
            $.ligerui.controls.Grid.base._init.call(this);
            var g = this, p = this.options;
            p.dataType = p.url ? "server" : "local";
            if (p.dataType == "local")
            {
                p.data = p.data || [];
                p.dataAction = "local";
            }
            if (p.isScroll == false)
            {
                p.height = 'auto';
            }
            if (!p.frozen)
            {
                p.frozenCheckbox = false;
                p.frozenDetail = false;
                p.frozenRownumbers = false;
            }
            if (p.detailToEdit)
            {
                p.enabledEdit = true;
                p.clickToEdit = false;
                p.detail = {
                    height: 'auto',
                    onShowDetail: function (record, container, callback)
                    {
                        $(container).addClass("l-grid-detailpanel-edit");
                        g.beginEdit(record, function (rowdata, column)
                        {
                            var editContainer = $("<div class='l-editbox'></div>");
                            editContainer.width(120).height(p.rowHeight + 1);
                            editContainer.appendTo(container);
                            return editContainer;
                        });
                        function removeRow()
                        {
                            $(container).parent().parent().remove();
                            g.collapseDetail(record);
                        }
                        $("<div class='l-clear'></div>").appendTo(container);
                        $("<div class='l-button'>" + p.saveMessage + "</div>").appendTo(container).click(function ()
                        {
                            g.endEdit(record);
                            removeRow();
                        });
                        $("<div class='l-button'>" + p.applyMessage + "</div>").appendTo(container).click(function ()
                        {
                            g.submitEdit(record);
                        });
                        $("<div class='l-button'>" + p.cancelMessage + "</div>").appendTo(container).click(function ()
                        {
                            g.cancelEdit(record);
                            removeRow();
                        });
                    }
                };
            }
            if (p.tree)//启用分页模式
            {
                p.tree.childrenName = p.tree.childrenName || "children";
                p.tree.isParent = p.tree.isParent || function (rowData)
                {
                    var exist = p.tree.childrenName in rowData;
                    return exist;
                };
                p.tree.isExtend = p.tree.isExtend || function (rowData)
                {
                    if ('isextend' in rowData && rowData['isextend'] == false)
                        return false;
                    return true;
                };
            }
            
            if(typeof(p.pageBarType) != 'string'){
            	if(typeof(p.pageBarType)=='number'){
            		p.pageBarType = ""+p.pageBarType;
            	}else{
            		p.pageBarType = "1";
            	}
            }
        },
        _render: function ()
        {
            var g = this, p = this.options;
            g.grid = $(g.element);
            if(p.pageBarType=='1'){
            	g.grid.addClass("l-panel");
            }
           
            var gridhtmlarr = [];
            gridhtmlarr.push("        <div class='l-panel-header'><span class='l-panel-header-text'></span></div>");
            gridhtmlarr.push("        <div class='l-grid-loading'></div>");
            gridhtmlarr.push("        <div class='l-panel-topbar'></div>");
            
            gridhtmlarr.push("        <div class='l-panel-bwarp'>");
            gridhtmlarr.push("                    <div class='l-grid-popup'><table cellpadding='0' cellspacing='0'><tbody></tbody></table></div>");
            gridhtmlarr.push("            <div class='l-panel-body'>");
            gridhtmlarr.push("                <div class='l-grid'>");
            gridhtmlarr.push("                    <div class='l-grid-dragging-line'></div>");

            gridhtmlarr.push("                  <div class='sundun-grid-new'>");
            gridhtmlarr.push("                      <div class='sundun-grid-body'>");
            gridhtmlarr.push("                          <div style='zoom: 1;'>");//防止ie7出现横向滚动条
            gridhtmlarr.push("                            <table class='content_table' cellpadding='0' cellspacing='0' style='width:100%;'><thead class='grid-head'></thead><tbody class='grid-body'></tbody></table>");
            gridhtmlarr.push("                          </div>");
            gridhtmlarr.push("                      </div>");
            gridhtmlarr.push("                  </div>");

            gridhtmlarr.push("               </div>");
            gridhtmlarr.push("           </div>");
            gridhtmlarr.push("       </div>");
            
          if(p.pageBarType=='1'){
              gridhtmlarr.push("         <div class='l-panel-bar'>");
              gridhtmlarr.push("            <div class='l-panel-bbar-inner'>");
              gridhtmlarr.push("                <div style='float:left;margin-left:30px;'><span class='l-bar-text'></span></div>");
			  gridhtmlarr.push("                <div class='l-bar-group l-bar-selectpagesize'></div>");
			  gridhtmlarr.push("                <div class='l-bar-group l-bar-selectpagesize-unit'>条</div>");
			  
              gridhtmlarr.push("                <div class='l-bar-group' style='float:right;margin-right:30px;'>");
              gridhtmlarr.push("                     <div class='l-bar-button l-bar-btnnext'><span>下一页</span></div>");
              gridhtmlarr.push("                    <div class='l-bar-button l-bar-btnlast'><span>末页</span></div>");
              gridhtmlarr.push("                </div>");			  
              gridhtmlarr.push("                <div class='l-bar-separator' style='float:right;'></div>");
              gridhtmlarr.push("                <div class='l-bar-group' style='float:right;'>");
              gridhtmlarr.push("                    <div class='l-bar-button l-bar-btnfirst'><span>首页</span></div>");
              gridhtmlarr.push("                    <div class='l-bar-button l-bar-btnprev'><span>上一页</span></div>");
              gridhtmlarr.push("                </div>");
              gridhtmlarr.push("                <div class='l-bar-separator' style='float:right;'></div>");
              gridhtmlarr.push("                <div class='l-bar-group' style='float:right;margin-top:2px'><span class='pcontrol'> <input type='text' size='4' value='1' style='width:20px' maxlength='3' /> / <span></span></span></div>");              
              gridhtmlarr.push("                <div class='l-bar-separator' style='float:right;'></div>");
              gridhtmlarr.push("                <div class='l-bar-group' style='float:right;'>");
              gridhtmlarr.push("                     <div class='l-bar-button l-bar-btnload'><span>刷新</span></div>");
              gridhtmlarr.push("                </div>");

              gridhtmlarr.push("                <div class='l-clear'></div>");
              gridhtmlarr.push("            </div>");
			  
              gridhtmlarr.push("         </div>");
         }
          else if(p.pageBarType=='0')
         {
              gridhtmlarr.push("       <div class='sundun-panel-bar'>");
              gridhtmlarr.push("                <div class='sundun-grid-message sundun-grid-loadmore'><span class='l-bar-text'></span></div>");
              gridhtmlarr.push("                <div class='l-clear'></div>");
              gridhtmlarr.push("       </div>");  
         }

            g.grid.html(gridhtmlarr.join(''));
            //头部
            g.header = $(".l-panel-header:first", g.grid);
            //主体
            g.body = $(".l-panel-body:first", g.grid);
            //底部工具条  
            if(p.pageBarType=='0'){
            	g.toolbar = $(".sundun-panel-bar:first", g.grid);
            }else if(p.pageBarType=='1'){
            	g.toolbar = $(".l-panel-bar:first", g.grid);
            }
            //显示/隐藏列      
            g.popup = $(".l-grid-popup:first", g.grid);
            //加载中
            g.gridloading = $(".l-grid-loading:first", g.grid);
            //调整列宽层 
            g.draggingline = $(".l-grid-dragging-line", g.grid);
            //顶部工具栏
            g.topbar = $(".l-panel-topbar:first", g.grid);
            
            g.table = $(".sundun-grid-body:first", g.grid);
            
            //表头     
            g.gridheader = $("thead:first", g.table);
            //表主体     
            g.gridbody = $("tbody:first", g.table);
            
            g.currentData = null;
            g.changedCells = {};
            g.editors = {};                 //多编辑器同时存在
            g.editor = { editing: false };  //单编辑器,配置clickToEdit

            var pc = $.extend({}, p);

            this._bulid();
            this._setColumns(p.columns);

            delete pc['columns'];
            delete pc['data'];
            delete pc['url'];
            g.set(pc);
            if (!p.delayLoad)
            {
                if (p.url)
                    g.set({ url: p.url });
                else if (p.data)
                    g.set({ data: p.data });
            }
        },
        _setFrozen: function (frozen)
        {
            if (frozen)
                this.grid.addClass("l-frozen");
            else
                this.grid.removeClass("l-frozen");
        },
        _setCssClass: function (value)
        {
            this.grid.addClass(value);
        },
        _setLoadingMessage: function (value)
        {
            this.gridloading.html(value);
        },
        _setToolbar: function (value)
        {
            var g = this, p = this.options;
            if (value && $.fn.ligerToolBar)
            {
                g.topbar.show();
                g.toolbarManager = g.topbar.ligerToolBar(value);
            }
        },
        _setHeight: function (h)
        {
            var g = this, p = this.options;
            if (typeof h == "string" && h.indexOf('%') > 0)
            {
                if (p.inWindow)
                    h = $(window).height() * parseFloat(h) * 0.01;
                else
                    h = g.grid.parent().height() * parseFloat(h) * 0.01;
            }
            if (p.title) h -= 24;
            if (p.usePager) h -= 32;
            if (p.totalRender) h -= 25;
            if (p.toolbar) h -= g.topbar.outerHeight();
            if (h > 0 && p.fixHeight)
            {
                g.table.height(h);
            }
        },
        _updateFrozenWidth: function ()
        {
            var g = this, p = this.options;
        },
        _setWidth: function (value)
        {
            var g = this;
            g.grid.css('width',value);
        },
        _setUrl: function (value)
        {
            this.options.url = value;
            if (value)
            {
                this.options.dataType = "server";
                this.loadData(true);
            }
            else
            {
                this.options.dataType = "local";
            }
        },
        removeParm : function(name)
        {
            var g = this;
            var parms = g.get('parms');
            if (!parms) parms = {};
            if (parms instanceof Array)
            {
                removeArrItem(parms, function (p) { return p.name == name; }); 
            } else
            {
                delete parms[name];
            }
            g.set('parms', parms);
        },
        setParm: function (name, value)
        {
            var g = this;
            var parms = g.get('parms');
            if (!parms) parms = {};
            if (parms instanceof Array)
            {
                removeArrItem(parms, function (p) { return p.name == name; });
                parms.push({ name: name, value: value });
            } else
            {
                parms[name] = value;
            }
            g.set('parms', parms); 
        },
        _setData: function (value)
        {
            this.loadData(this.options.data);
        },
        //刷新数据
        loadData: function (loadDataParm)
        {
            var g = this, p = this.options;
            g.loading = true; 
            g.trigger('loadData');
            var clause = null;
            var loadServer = true;
            if (typeof (loadDataParm) == "function")
            {
                clause = loadDataParm;
                loadServer = false;
            }
            else if (typeof (loadDataParm) == "boolean")
            {
                loadServer = loadDataParm;
            }
            else if (typeof (loadDataParm) == "object" && loadDataParm)
            {
                loadServer = false;
                p.dataType = "local";
                p.data = loadDataParm;
            }
            //参数初始化
            if (!p.newPage) p.newPage = 1;
            if (p.dataAction == "server")
            {
                if (!p.sortOrder) p.sortOrder = "asc";
            }
            var param = [];
            if (p.parms)
            {
                if (p.parms.length)
                {
                    $(p.parms).each(function ()
                    {
                        param.push({ name: this.name, value: this.value });
                    });
                }
                else if (typeof p.parms == "object")
                {
                    for (var name in p.parms)
                    {
                        param.push({ name: name, value: p.parms[name] });
                    }
                }
            }
            if (p.dataAction == "server")
            {
            	
                if (p.usePager)
                {
                    param.push({ name: p.pageParmName, value: p.newPage });
                    param.push({ name: p.pagesizeParmName, value: p.pageSize });
                }
                if (p.sortName)
                {
                    param.push({ name: p.sortnameParmName, value: p.sortName });
                    param.push({ name: p.sortorderParmName, value: p.sortOrder });
                }
            };
            $(".l-bar-btnload span", g.toolbar).addClass("l-disabled");
            if (p.dataType == "local")
            {
                //g.filteredData = g.data = p.data;            	
                g.filteredData = g.data = $.extend({},p.data);
                
                if (clause){
                	g.filteredData[p.root] = g._searchData(p.data[p.root],clause);//(g.filteredData[p.root], clause);
                }    
                
                if (p.usePager){
                	p.newPage=1;
                	g.currentData = g._getCurrentPageData(g.filteredData);
                }else{
                    g.currentData = g.filteredData;
                }
                g._showData();
            }
            else if (p.dataAction == "local" && !loadServer)
            {
                if (g.data && g.data[p.root])
                {
                    g.filteredData = g.data;
                    if (clause)
                        g.filteredData[p.root] = g._searchData(g.filteredData[p.root], clause);
                    g.currentData = g._getCurrentPageData(g.filteredData);
                    g._showData();
                }
            }
            else
            {
                g.loadServerData(param, clause);
                //g.loadServerData.ligerDefer(g, 10, [param, clause]);
            }
            g.loading = false;
        },
        loadServerData: function (param, clause)
        {
            var g = this, p = this.options;
            var ajaxOptions = {
                type: p.method,
                url: p.url,
                data: param,
                async: p.async,
                dataType: 'json',
                beforeSend: function ()
                {
                    if (g.hasBind('loading'))
                    {
                        g.trigger('loading');
                    }
                    else
                    {
                        g.toggleLoading(true);
                    }
                },
                success: function (data)
                {
                    g.trigger('success', [data, g]);
                    if (!data || !data[p.root] || !data[p.root].length)
                    {
                        g.currentData = g.data = {};
                        g.currentData[p.root] = g.data[p.root] = [];
                        if (data && data[p.record])
                        {
                            g.currentData[p.record] = g.data[p.record] = data[p.record];
                        } else
                        {
                            g.currentData[p.record] = g.data[p.record] = 0;
                        }
                        g._showData();
                        return;
                    }
                    g.data = data;
                    if (p.dataAction == "server")
                    {
                        g.currentData = g.data;
                    }
                    else
                    {
                        g.filteredData = g.data;
                        if (clause) g.filteredData[p.root] = g._searchData(g.filteredData[p.root], clause);
                        if (p.usePager)
                            g.currentData = g._getCurrentPageData(g.filteredData);
                        else
                            g.currentData = g.filteredData;
                    }
                    g._showData.ligerDefer(g, 10, [g.currentData]);
                },
                complete: function ()
                {
                    g.trigger('complete', [g]);
                    if (g.hasBind('loaded'))
                    {
                        g.trigger('loaded', [g]);
                    }
                    else
                    {
                        g.toggleLoading.ligerDefer(g, 10, [false]);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown)
                {
                    g.currentData = g.data = {};
                    g.currentData[p.root] = g.data[p.root] = [];
                    g.currentData[p.record] = g.data[p.record] = 0;
                    g.toggleLoading.ligerDefer(g, 10, [false]);
                    $(".l-bar-btnload span", g.toolbar).removeClass("l-disabled");
                    g.trigger('error', [XMLHttpRequest, textStatus, errorThrown]);
                }
            };
            if (p.contentType) ajaxOptions.contentType = p.contentType;
            $.ajax(ajaxOptions);
        },
        toggleLoading: function (show)
        {
            this.gridloading[show ? 'show' : 'hide']();
        },
        _createEditor: function (editor, container, editParm, width, height)
        {
            var editorInput = editor.create.call(this, container, editParm); 
            if (editor.setValue) editor.setValue.call(this, editorInput, editParm.value, editParm);
            if (editor.setText && editParm.column.textField) editor.setText.call(this, editorInput, editParm.text, editParm);
            if (editor.resize) editor.resize.call(this, editorInput, width, height, editParm);
            return editorInput;
        },
        /*
        @description 使一行进入编辑状态
        @param  {rowParm} rowindex或者rowdata
        @param {containerBulider} 编辑器填充层构造器
        */
        beginEdit: function (rowParm, containerBulider)
        {
            var g = this, p = this.options;
            if (!p.enabledEdit || p.clickToEdit) return;
            var rowdata = g.getRow(rowParm);
            if (rowdata._editing) return;
            if (g.trigger('beginEdit', { record: rowdata, rowindex: rowdata['__index'] }) == false) return;
            g.editors[rowdata['__id']] = {};
            rowdata._editing = true;
            g.reRender({ rowdata: rowdata });
            containerBulider = containerBulider || function (rowdata, column)
            {
                var cellobj = g.getCellObj(rowdata, column);
                var container = $(cellobj).html("");
                g.setCellEditing(rowdata, column, true);
                return container;
            };
            for (var i = 0, l = g.columns.length; i < l; i++)
            {
                var column = g.columns[i];
                if (!column.name || !column.editor || !column.editor.type || !p.editors[column.editor.type]) continue;
                var editor = p.editors[column.editor.type];
                var editParm = {
                    record: rowdata,
                    value: g._getValueByName(rowdata, column.name),
                    column: column,
                    rowindex: rowdata['__index'],
                    grid: g
                };
                var container = containerBulider(rowdata, column);
                var width = container.width(), height = container.height();
                var editorInput = g._createEditor(editor, container, editParm, width, height);
                g.editors[rowdata['__id']][column['__id']] = { editor: editor, input: editorInput, editParm: editParm, container: container };
            }
            g.trigger('afterBeginEdit', { record: rowdata, rowindex: rowdata['__index'] });

        },
        cancelEdit: function (rowParm)
        {
            var g = this;
            if (rowParm == undefined)
            {
                for (var rowid in g.editors)
                {
                    g.cancelEdit(rowid);
                }
            }
            else
            {
                var rowdata = g.getRow(rowParm);
                if (!g.editors[rowdata['__id']]) return;
                if (g.trigger('beforeCancelEdit', { record: rowdata, rowindex: rowdata['__index'] }) == false) return;
                for (var columnid in g.editors[rowdata['__id']])
                {
                    var o = g.editors[rowdata['__id']][columnid];
                    if (o.editor.destroy) o.editor.destroy(o.input, o.editParm);
                }
                delete g.editors[rowdata['__id']];
                delete rowdata['_editing'];
                g.reRender({ rowdata: rowdata });
            }
        },
        addEditRow: function (rowdata)
        {
            this.submitEdit();
            rowdata = this.add(rowdata);
            this.beginEdit(rowdata);
        },
        submitEdit: function (rowParm)
        {
            var g = this, p = this.options;
            if (rowParm == undefined)
            {
                for (var rowid in g.editors)
                {
                    g.submitEdit(rowid);
                }
            }
            else
            {
                var rowdata = g.getRow(rowParm);
                var newdata = {};
                if (!g.editors[rowdata['__id']]) return; 
                for (var columnid in g.editors[rowdata['__id']])
                {
                    var o = g.editors[rowdata['__id']][columnid];
                    var column = o.editParm.column;
                    if (column.name)
                    {
                        newdata[column.name] = o.editor.getValue(o.input, o.editParm);
                    }
                    if (column.textField && o.editor.getText)
                    {
                        newdata[column.textField] = o.editor.getText(o.input, o.editParm);
                    }
                }
                if (g.trigger('beforeSubmitEdit', { record: rowdata, rowindex: rowdata['__index'], newdata: newdata }) == false)
                    return false;
                g.updateRow(rowdata, newdata);
                g.trigger('afterSubmitEdit', { record: rowdata, rowindex: rowdata['__index'], newdata: newdata });
            }
        },
        endEdit: function (rowParm)
        {
            var g = this, p = this.options; 
            if (g.editor.editing)
            {
                var o = g.editor;
                g.trigger('sysEndEdit', [g.editor.editParm]);
                g.trigger('endEdit', [g.editor.editParm]);
                if (o.editor.destroy) o.editor.destroy(o.input, o.editParm);
                g.editor.container.remove();
                g.reRender({ rowdata: g.editor.editParm.record, column: g.editor.editParm.column });
                g.trigger('afterEdit', [g.editor.editParm]);
                g.editor = { editing: false };
            }
            else if (rowParm != undefined)
            {
                var rowdata = g.getRow(rowParm);
                if (!g.editors[rowdata['__id']]) return;
                if (g.submitEdit(rowParm) == false) return false;
                for (var columnid in g.editors[rowdata['__id']])
                {
                    var o = g.editors[rowdata['__id']][columnid];
                    if (o.editor.destroy) o.editor.destroy(o.input, o.editParm);
                }
                delete g.editors[rowdata['__id']];
                delete rowdata['_editing'];
                g.trigger('afterEdit', { record: rowdata, rowindex: rowdata['__index'] });
            }
            else
            {
                for (var rowid in g.editors)
                {
                    g.endEdit(rowid);
                }
            }
        },
        setWidth: function (w)
        {
            return this._setWidth(w);
        },
        setHeight: function (h)
        {
            return this._setHeight(h);
        },
        //是否启用复选框列
        enabledCheckbox: function ()
        {
            return this.options.checkbox ? true : false;
        },
        //是否固定列
        enabledFrozen: function ()
        {
            var g = this, p = this.options;
            if (!p.frozen) return false;
            var cols = g.columns || [];
            if (g.enabledDetail() && p.frozenDetail || g.enabledCheckbox() && p.frozenCheckbox
            || p.frozenRownumbers && p.rownumbers) return true;
            for (var i = 0, l = cols.length; i < l; i++)
            {
                if (cols[i].frozen)
                {
                    return true;
                }
            }
            this._setFrozen(false);
            return false;
        },
        //是否启用明细编辑
        enabledDetailEdit: function ()
        {
            if (!this.enabledDetail()) return false;
            return this.options.detailToEdit ? true : false;
        },
        //是否启用明细列
        enabledDetail: function ()
        {
            if (this.options.detail && this.options.detail.onShowDetail) return true;
            return false;
        },
        //是否启用分组
        enabledGroup: function ()
        {
            return this.options.groupColumnName ? true : false;
        },
        deleteSelectedRow: function ()
        {
            if (!this.selected) return;
            for (var i in this.selected)
            {
                var o = this.selected[i];
                if (o['__id'] in this.records)
                    this._deleteData.ligerDefer(this, 10, [o]);
            }
            this.reRender.ligerDefer(this, 20);
        },
        removeRange: function (rowArr)
        {
            var g = this, p = this.options;
            $.each(rowArr, function ()
            {
                g._removeData(this);
            });
            g.reRender();
        },
        remove: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            g._removeData(rowParm);
            g.reRender();
        },
        deleteRange: function (rowArr)
        {
            var g = this, p = this.options;
            $.each(rowArr, function ()
            {
                g._deleteData(this);
            });
            g.reRender();
        },
        deleteRow: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            if (!rowdata) return;
            g._deleteData(rowdata);
            g.reRender();
            g.isDataChanged = true;
        },
        _deleteData: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            rowdata[p.statusName] = 'delete';
            if (p.tree)
            {
                var children = g.getChildren(rowdata, true);
                if (children)
                {
                    for (var i = 0, l = children.length; i < l; i++)
                    {
                        children[i][p.statusName] = 'delete';
                    }
                }
            }
            g.deletedRows = g.deletedRows || [];
            g.deletedRows.push(rowdata);
            g._removeSelected(rowdata);
        },
        /*
        @param  {arg} column index、column name、column、单元格
        @param  {value} 值
        @param  {rowParm} rowindex或者rowdata
        */
        updateCell: function (arg, value, rowParm)
        {
            var g = this, p = this.options;
            var column, cellObj, rowdata;
            if (typeof (arg) == "string") //column name
            {
                for (var i = 0, l = g.columns.length; i < l; i++)
                {
                    if (g.columns[i].name == arg)
                    {
                        g.updateCell(i, value, rowParm);
                    }
                }
                return;
            }
            if (typeof (arg) == "number")
            {
                column = g.columns[arg];
                rowdata = g.getRow(rowParm);
                cellObj = g.getCellObj(rowdata, column);
            }
            else if (typeof (arg) == "object" && arg['__id'])
            {
                column = arg;
                rowdata = g.getRow(rowParm);
                cellObj = g.getCellObj(rowdata, column);
            }
            else
            {
                cellObj = arg;
                var ids = cellObj.id.split('|');
                var columnid = ids[ids.length - 1];
                column = g._columns[columnid];
                var row = $(cellObj).parent();
                rowdata = rowdata || g.getRow(row[0]);
            }
            if (value != null && column.name)
            {
                g._setValueByName(rowdata, column.name, value);
                if (rowdata[p.statusName] != 'add')
                    rowdata[p.statusName] = 'update';
                g.isDataChanged = true;
            }
            g.reRender({ rowdata: rowdata, column: column });
        },
        addRows: function (rowdataArr, neardata, isBefore, parentRowData)
        {
            var g = this, p = this.options;
            $(rowdataArr).each(function ()
            {
                g.addRow(this, neardata, isBefore, parentRowData);
            });
        },
        _createRowid: function ()
        {
            return "r" + (1000 + this.recordNumber);
        },
        _isRowId: function (str)
        {
            return (str in this.records);
        },
        _addNewRecord: function (o, previd, pid)
        {
            var g = this, p = this.options;
            g.recordNumber++;
            o['__id'] = g._createRowid();
            o['__previd'] = previd;
            if (previd && previd != -1)
            {
                var prev = g.records[previd];
                if (prev['__nextid'] && prev['__nextid'] != -1)
                {
                    var prevOldNext = g.records[prev['__nextid']];
                    if (prevOldNext)
                        prevOldNext['__previd'] = o['__id'];
                }
                prev['__nextid'] = o['__id'];
                o['__index'] = prev['__index'] + 1;
            }
            else
            {
                o['__index'] = 0;
            }
            if (p.tree)
            {
                if (pid && pid != -1)
                {
                    var parent = g.records[pid];
                    o['__pid'] = pid;
                    o['__level'] = parent['__level'] + 1;
                }
                else
                {
                    o['__pid'] = -1;
                    o['__level'] = 1;
                }
                o['__hasChildren'] = o[p.tree.childrenName] ? true : false;
            }
            if (o[p.statusName] != "add")
                o[p.statusName] = "nochanged";
            g.rows[o['__index']] = o;
            g.records[o['__id']] = o;
            return o;
        },
        //将原始的数据转换成适合 grid的行数据 
        _getRows: function (data)
        {
            var g = this, p = this.options;
            var targetData = [];
            function load(data)
            {
                if (!data || !data.length) return;
                for (var i = 0, l = data.length; i < l; i++)
                {
                    var o = data[i];
                    targetData.push(o);
                    if (o[p.tree.childrenName])
                    {
                        load(o[p.tree.childrenName]);
                    }
                }
            }
            load(data);
            return targetData;
        },
        _updateGridData: function ()
        {
            var g = this, p = this.options;
            g.recordNumber = 0;
            g.rows = [];
            g.records = {};
            var previd = -1;
            function load(data, pid)
            {
                if (!data || !data.length) return;
                for (var i = 0, l = data.length; i < l; i++)
                {
                    var o = data[i];
                    g.formatRecord(o);
                    if (o[p.statusName] == "delete") continue;
                    g._addNewRecord(o, previd, pid);
                    previd = o['__id'];
                    if (o['__hasChildren'])
                    {
                        load(o[p.tree.childrenName], o['__id']);
                    }
                }
            }
            load(g.currentData[p.root], -1);
            return g.rows;
        },
        _moveData: function (from, to, isAfter)
        {
            var g = this, p = this.options;
            var fromRow = g.getRow(from);
            var toRow = g.getRow(to);
            var fromIndex, toIndex;
            var listdata = g._getParentChildren(fromRow);
            fromIndex = $.inArray(fromRow, listdata);
            listdata.splice(fromIndex, 1);
            listdata = g._getParentChildren(toRow);
            toIndex = $.inArray(toRow, listdata);
            listdata.splice(toIndex + (isAfter ? 1 : 0), 0, fromRow);
        },
        move: function (from, to, isAfter)
        {
            this._moveData(from, to, isAfter);
            this.reRender();
        },
        moveRange: function (rows, to, isAfter)
        {
            for (var i in rows)
            {
                this._moveData(rows[i], to, isAfter);
            }
            this.reRender();
        },
        up: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            var listdata = g._getParentChildren(rowdata);
            var index = $.inArray(rowdata, listdata);
            if (index == -1 || index == 0) return;
            var selected = g.getSelected();
            g.move(rowdata, listdata[index - 1], false);
            g.select(selected);
        },
        down: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            var listdata = g._getParentChildren(rowdata);
            var index = $.inArray(rowdata, listdata);
            if (index == -1 || index == listdata.length - 1) return;
            var selected = g.getSelected();
            g.move(rowdata, listdata[index + 1], true);
            g.select(selected);
        },
        addRow: function (rowdata, neardata, isBefore, parentRowData)
        {
            var g = this, p = this.options;
            rowdata = rowdata || {};
            g._addData(rowdata, parentRowData, neardata, isBefore);
            g.reRender();
            //标识状态
            rowdata[p.statusName] = 'add';
            if (p.tree)
            {
                var children = g.getChildren(rowdata, true);
                if (children)
                {
                    for (var i = 0, l = children.length; i < l; i++)
                    {
                        children[i][p.statusName] = 'add';
                    }
                }
            }
            g.isDataChanged = true;
            p.total = p.total ? (p.total + 1) : 1;
            p.pageCount = Math.ceil(p.total / p.pageSize);
            g._buildPager();
            g.trigger('SysGridHeightChanged');
            g.trigger('afterAddRow', [rowdata]);
            return rowdata;
        },
        updateRow: function (rowDom, newRowData)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowDom);
            //标识状态
            g.isDataChanged = true;
            $.extend(rowdata, newRowData || {});
            if (rowdata[p.statusName] != 'add')
                rowdata[p.statusName] = 'update';
            g.reRender.ligerDefer(g, 10, [{ rowdata: rowdata }]);
            return rowdata;
        },
        setCellEditing: function (rowdata, column, editing)
        {
            var g = this, p = this.options;
            var cell = g.getCellObj(rowdata, column);
            var methodName = editing ? 'addClass' : 'removeClass';
            $(cell)[methodName]("l-grid-row-cell-editing");
            if (rowdata['__id'] != 0)
            {
                var prevrowobj = $(g.getRowObj(rowdata['__id'])).prev();
                if (!prevrowobj.length) return;
                var prevrow = g.getRow(prevrowobj[0]);
                var cellprev = g.getCellObj(prevrow, column);
                if (!cellprev) return;
                $(cellprev)[methodName]("l-grid-row-cell-editing-topcell");
            }
            if (column['__previd'] != -1 && column['__previd'] != null)
            {
                var cellprev = $(g.getCellObj(rowdata, column)).prev();
                $(cellprev)[methodName]("l-grid-row-cell-editing-leftcell");
            }
        },
        reRender: function (e)
        {
            var g = this, p = this.options;
            e = e || {};
            var rowdata = e.rowdata, column = e.column;
            if (column && (column.isdetail || column.ischeckbox)) return;
            if (rowdata && rowdata[p.statusName] == "delete") return;
            if (rowdata && column)
            {
                var cell = g.getCellObj(rowdata, column);
                $(cell).html(g._getCellHtml(rowdata, column));
                if (!column.issystem)
                    g.setCellEditing(rowdata, column, false);
            }
            else if (rowdata)
            {
                $(g.columns).each(function () { g.reRender({ rowdata: rowdata, column: this }); });
            }
            else if (column)
            {
                for (var rowid in g.records) { g.reRender({ rowdata: g.records[rowid], column: column }); }
                for (var i = 0; i < g.totalNumber; i++)
                {
                    var tobj = document.getElementById(g.id + "|total" + i + "|" + column['__id']);
                    $("div:first", tobj).html(g._getTotalCellContent(column, g.groups && g.groups[i] ? g.groups[i] : g.currentData[p.root]));
                }
            }
            else
            {
                g._showData();
            }
        },
        getData: function (status, removeStatus)
        {
            var g = this, p = this.options;
            var data = [];
            if (removeStatus == undefined) removeStatus = true;
            for (var rowid in g.records)
            {
                var o = $.extend(true, {}, g.records[rowid]);
                if (o[p.statusName] == status || status == undefined)
                {
                    data.push(g.formatRecord(o, removeStatus));
                }
            }
            return data;
        },
        //格式化数据
        formatRecord: function (o, removeStatus)
        {
            delete o['__id'];
            delete o['__previd'];
            delete o['__nextid'];
            delete o['__index'];
            if (this.options.tree)
            {
                delete o['__pid'];
                delete o['__level'];
                delete o['__hasChildren'];
            }
            if (removeStatus) delete o[this.options.statusName];
            return o;
        },
        getUpdated: function ()
        {
            return this.getData('update', true);
        },
        getDeleted: function ()
        {
            return this.deletedRows;
        },
        getAdded: function ()
        {
            return this.getData('add', true);
        },
        getChanges: function ()
        {
            var g = this, p = this.options;
            var data = [];
            if (this.deletedRows)
            {
                $(this.deletedRows).each(function ()
                {
                    var o = $.extend(true, {}, this);
                    data.push(g.formatRecord(o, true));
                });
            }
            $.merge(data, g.getUpdated());
            $.merge(data, g.getAdded());
            return data;
        },
        getColumn: function (columnParm)
        {
            var g = this, p = this.options;
            if (typeof columnParm == "string") // column id
            {
                if (g._isColumnId(columnParm))
                    return g._columns[columnParm];
                else
                    return g.columns[parseInt(columnParm)];
            }
            else if (typeof (columnParm) == "number") //column index
            {
                return g.columns[columnParm];
            }
            else if (typeof columnParm == "object" && columnParm.nodeType == 1) //column header cell
            {
                var ids = columnParm.id.split('|');
                var columnid = ids[ids.length - 1];
                return g._columns[columnid];
            }
            return columnParm;
        },
        getColumnType: function (columnname)
        {
            var g = this, p = this.options;
            for (i = 0; i < g.columns.length; i++)
            {
                if (g.columns[i].name == columnname)
                {
                    if (g.columns[i].type) return g.columns[i].type;
                    return "string";
                }
            }
            return null;
        },
        //是否包含汇总
        isTotalSummary: function ()
        {
            var g = this, p = this.options;
            for (var i = 0; i < g.columns.length; i++)
            {
                if (g.columns[i].totalSummary) return true;
            }
            return false;
        },
        //根据层次获取列集合
        //如果columnLevel为空，获取叶节点集合
        getColumns: function (columnLevel)
        {
            var g = this, p = this.options;
            var columns = [];
            for (var id in g._columns)
            {
                var col = g._columns[id];
                if (columnLevel != undefined)
                {
                    if (col['__level'] == columnLevel) columns.push(col);
                }
                else
                {
                    if (col['__leaf']) columns.push(col);
                }
            }
            return columns;
        },
        //改变排序
        changeSort: function (columnName, sortOrder)
        {
            var g = this, p = this.options;
            if (g.loading) return true;
            if (p.dataAction == "local")
            {
                var columnType = g.getColumnType(columnName);
                if (!g.sortedData)
                    g.sortedData = g.filteredData;
                if (!g.sortedData || !g.sortedData[p.root])
                    return;
                if (p.sortName == columnName)
                {
                    g.sortedData[p.root].reverse();
                } else
                {
                    g.sortedData[p.root].sort(function (data1, data2)
                    {
                        return g._compareData(data1, data2, columnName, columnType);
                    });
                }
                if (p.usePager)
                    g.currentData = g._getCurrentPageData(g.sortedData);
                else
                    g.currentData = g.sortedData;
                g._showData();
            }
            p.sortName = columnName;
            p.sortOrder = sortOrder;
            if (p.dataAction == "server")
            {
                g.loadData(p.where);
            }
        },
        //改变分页
        changePage: function (ctype)
        {
            var g = this, p = this.options;
            if (g.loading) return true;
            if (p.dataAction != "local" && g.isDataChanged && !confirm(p.isContinueByDataChanged))
                return false;
            switch (ctype)
            {
                case 'first': if (p.page == 1) return; p.newPage = 1; break;
                case 'prev': if (p.page == 1) return; if (p.page > 1) p.newPage = parseInt(p.page) - 1; break;
                case 'next': if (p.page >= p.pageCount) return; p.newPage = parseInt(p.page) + 1; break;
                case 'last': if (p.page >= p.pageCount) return; p.newPage = p.pageCount; break;
                case 'input':
                    var nv = parseInt($('.pcontrol input', g.toolbar).val());
                    if (isNaN(nv)) nv = 1;
                    if (nv < 1) nv = 1;
                    else if (nv > p.pageCount) nv = p.pageCount;
                    $('.pcontrol input', g.toolbar).val(nv);
                    p.newPage = nv;
                    break;
            }

            if (p.newPage == p.page) return false;
            if (p.newPage == 1)
            {
                $(".l-bar-btnfirst span", g.toolbar).addClass("l-disabled");
                $(".l-bar-btnprev span", g.toolbar).addClass("l-disabled");
            }
            else
            {
                $(".l-bar-btnfirst span", g.toolbar).removeClass("l-disabled");
                $(".l-bar-btnprev span", g.toolbar).removeClass("l-disabled");
            }
            if (p.newPage == p.pageCount)
            {
                $(".l-bar-btnlast span", g.toolbar).addClass("l-disabled");
                if(p.pageBarType=='0'){
                	$(".sundun-grid-loadmore span", g.toolbar).addClass("l-disabled");
                }else if(p.pageBarType=='1'){
                	$(".l-bar-btnnext span", g.toolbar).addClass("l-disabled");
                }
            }
            else
            {
                $(".l-bar-btnlast span", g.toolbar).removeClass("l-disabled");
                if(p.pageBarType=='0'){
                	$(".sundun-grid-loadmore span", g.toolbar).removeClass("l-disabled");
                }else if(p.pageBarType=='1'){
                	$(".l-bar-btnnext span", g.toolbar).removeClass("l-disabled");
                }
            }
            g.trigger('changePage', [p.newPage]);
            if (p.dataAction == "server")
            {
            	if(p.pageBarType=='0'){
            		g._sundunLoadData(p.where);
            	}else if(p.pageBarType=='1'){
            		g.loadData(p.where);
            	}
            }
            else
            {
            	if(p.pageBarType=='0'){
	            	if(!g.currentData){
	            		g.currentData = {};
	            		g.currentData[p.root] = [];
	            	}
	            	var dt = g._getCurrentPageData(g.filteredData);
	            	g.currentData[p.record] = dt[p.record];
	            	$.each(dt[p.root],function(){
	            		g.currentData[p.root].push(this);
	            	});
	                g._showData();
            	}else if(p.pageBarType=='1'){
                    g.currentData = g._getCurrentPageData(g.filteredData);
                    g._showData();
            	}
            }
        },
        getSelectedRow: function ()
        {
            for (var i in this.selected)
            {
                var o = this.selected[i];
                if (o['__id'] in this.records)
                    return o;
            }
            return null;
        },
        getSelectedRows: function ()
        {
            var arr = [];
            for (var i in this.selected)
            {
                var o = this.selected[i];
                if (o['__id'] in this.records)
                    arr.push(o);
            }
            return arr;
        },
        getSelectedRowObj: function ()
        {
            for (var i in this.selected)
            {
                var o = this.selected[i];
                if (o['__id'] in this.records)
                    return this.getRowObj(o);
            }
            return null;
        },
        getSelectedRowObjs: function ()
        {
            var arr = [];
            for (var i in this.selected)
            {
                var o = this.selected[i];
                if (o['__id'] in this.records)
                    arr.push(this.getRowObj(o));
            }
            return arr;
        },
        getCellObj: function (rowParm, column)
        {
            var rowdata = this.getRow(rowParm);
            column = this.getColumn(column);
            return document.getElementById(this._getCellDomId(rowdata, column));
        },
        getRowObj: function (rowParm, frozen)
        {
            var g = this, p = this.options;
            if (rowParm == null) return null;
            if (typeof (rowParm) == "string")
            {
                if (g._isRowId(rowParm))
                    return document.getElementById(g.id + (frozen ? "|1|" : "|2|") + rowParm);
                else
                    return document.getElementById(g.id + (frozen ? "|1|" : "|2|") + g.rows[parseInt(rowParm)]['__id']);
            }
            else if (typeof (rowParm) == "number")
            {
                return document.getElementById(g.id + (frozen ? "|1|" : "|2|") + g.rows[rowParm]['__id']);
            }
            else if (typeof (rowParm) == "object" && rowParm['__id']) //rowdata
            {
                return g.getRowObj(rowParm['__id'], frozen);
            }
            return rowParm;
        },
        getRow: function (rowParm)
        {
            var g = this, p = this.options;
            if (rowParm == null) return null;
            if (typeof (rowParm) == "string")
            {
                if (g._isRowId(rowParm))
                    return g.records[rowParm];
                else
                    return g.rows[parseInt(rowParm)];
            }
            else if (typeof (rowParm) == "number")
            {
                return g.rows[parseInt(rowParm)];
            }
            else if (typeof (rowParm) == "object" && rowParm.nodeType == 1 && !rowParm['__id']) //dom对象
            {
                return g._getRowByDomId(rowParm.id);
            }
            return rowParm;
        },
        _setColumnVisible: function (column, hide)
        {
            var g = this, p = this.options;
            if (!hide)  //显示
            {
                column._hide = false;
                document.getElementById(column['__domid']).style.display = "";
                //判断分组列是否隐藏,如果隐藏了则显示出来
                if (column['__pid'] != -1)
                {
                    var pcol = g._columns[column['__pid']];
                    if (pcol._hide)
                    {
                        document.getElementById(pcol['__domid']).style.display = "";
                        this._setColumnVisible(pcol, hide);
                    }
                }
            }
            else //隐藏
            {
                column._hide = true;
                document.getElementById(column['__domid']).style.display = "none";
                //判断同分组的列是否都隐藏,如果是则隐藏分组列
                if (column['__pid'] != -1)
                {
                    var hideall = true;
                    var pcol = this._columns[column['__pid']];
                    for (var i = 0; pcol && i < pcol.columns.length; i++)
                    {
                        if (!pcol.columns[i]._hide)
                        {
                            hideall = false;
                            break;
                        }
                    }
                    if (hideall)
                    {
                        pcol._hide = true;
                        document.getElementById(pcol['__domid']).style.display = "none";
                        this._setColumnVisible(pcol, hide);
                    }
                }
            }
        },
        //显示隐藏列
        toggleCol: function (columnparm, visible, toggleByPopup)
        {
            var g = this, p = this.options;
            var column;
            if (typeof (columnparm) == "number")
            {
                column = g.columns[columnparm];
            }
            else if (typeof (columnparm) == "object" && columnparm['__id'])
            {
                column = columnparm;
            }
            else if (typeof (columnparm) == "string")
            {
                if (g._isColumnId(columnparm)) // column id
                {
                    column = g._columns[columnparm];
                }
                else  // column name
                {
                    $(g.columns).each(function ()
                    {
                        if (this.name == columnparm)
                            g.toggleCol(this, visible, toggleByPopup);
                    });
                    return;
                }
            }
            if (!column) return;
            var columnindex = column['__leafindex'];
            var headercell = document.getElementById(column['__domid']);
            if (!headercell) return;
            headercell = $(headercell);
            var cells = [];
            for (var i in g.rows)
            {
                var obj = g.getCellObj(g.rows[i], column);
                if (obj) cells.push(obj);
            }
            for (var i = 0; i < g.totalNumber; i++)
            {
                var tobj = document.getElementById(g.id + "|total" + i + "|" + column['__id']);
                if (tobj) cells.push(tobj);
            }
            var colwidth = column._width;
            //显示列
            if (visible && column._hide)
            {
                g.gridtablewidth += (parseInt(colwidth) + 1);
                g._setColumnVisible(column, false);
                $(cells).show();
            }
                //隐藏列
            else if (!visible && !column._hide)
            {
                g.gridtablewidth -= (parseInt(colwidth) + 1);
                g._setColumnVisible(column, true);
                $(cells).hide();
            }
            
            if (column.frozen)
            {
                //$("div:first", g.f.gridheader).width(g.f.gridtablewidth);
                //$("div:first", g.f.gridbody).width(g.f.gridtablewidth);
            }
            else
            {
                //$("div:first", g.gridheader).width(g.gridtablewidth + 40);
                //$("div:first", g.gridbody).width(g.gridtablewidth);
            }
            g._updateFrozenWidth();
            if (!toggleByPopup)
            {
                $(':checkbox[columnindex=' + columnindex + "]", g.popup).each(function ()
                {
                    this.checked = visible;
                    if ($.fn.ligerCheckBox)
                    {
                        var checkboxmanager = $(this).ligerGetCheckBoxManager();
                        if (checkboxmanager) checkboxmanager.updateStyle();
                    }
                });
            }
        },
        //设置列宽
        setColumnWidth: function (columnparm, newwidth)
        {
            var g = this, p = this.options;
            if (!newwidth) return;
            newwidth = parseInt(newwidth, 10);
            var column;
            if (typeof (columnparm) == "number")
            {
                column = g.columns[columnparm];
            }
            else if (typeof (columnparm) == "object" && columnparm['__id'])
            {
                column = columnparm;
            }
            else if (typeof (columnparm) == "string")
            {
                if (g._isColumnId(columnparm)) // column id
                {
                    column = g._columns[columnparm];
                }
                else  // column name
                {
                    $(g.columns).each(function ()
                    {
                        if (this.name == columnparm)
                            g.setColumnWidth(this, newwidth);
                    });
                    return;
                }
            }
            if (!column) return;
            var mincolumnwidth = p.minColumnWidth;
            if (column.minWidth) mincolumnwidth = column.minWidth;
            newwidth = newwidth < mincolumnwidth ? mincolumnwidth : newwidth;
            var diff = newwidth - column._width;
            if (g.trigger('beforeChangeColumnWidth', [column, newwidth]) == false) return;
            
            column._width = newwidth;
            g.gridtablewidth += diff;
            
            $(document.getElementById(column['__domid'])).css('width', newwidth);
            var cells = [];
            for (var rowid in g.records)
            {
                var obj = g.getCellObj(g.records[rowid], column);
                if (obj) cells.push(obj);

                if (!g.enabledDetailEdit() && g.editors[rowid] && g.editors[rowid][column['__id']])
                {
                    var o = g.editors[rowid][column['__id']];
                    if (o.editor.resize) o.editor.resize(o.input, newwidth, o.container.height(), o.editParm);
                }
            }
            for (var i = 0; i < g.totalNumber; i++)
            {
                var tobj = document.getElementById(g.id + "|total" + i + "|" + column['__id']);
                if (tobj) cells.push(tobj);
            }
            $(cells).css('width', newwidth).find("> div.l-grid-row-cell-inner:first").css('width', newwidth - 8);

            g._updateFrozenWidth();


            g.trigger('afterChangeColumnWidth', [column, newwidth]);
        },
        //改变列表头内容
        changeHeaderText: function (columnparm, headerText)
        {
            var g = this, p = this.options;
            var column;
            if (typeof (columnparm) == "number")
            {
                column = g.columns[columnparm];
            }
            else if (typeof (columnparm) == "object" && columnparm['__id'])
            {
                column = columnparm;
            }
            else if (typeof (columnparm) == "string")
            {
                if (g._isColumnId(columnparm)) // column id
                {
                    column = g._columns[columnparm];
                }
                else  // column name
                {
                    $(g.columns).each(function ()
                    {
                        if (this.name == columnparm)
                            g.changeHeaderText(this, headerText);
                    });
                    return;
                }
            }
            if (!column) return;
            var columnindex = column['__leafindex'];
            var headercell = document.getElementById(column['__domid']);
            $(".l-grid-hd-cell-text", headercell).html(headerText);
            if (p.allowHideColumn)
            {
                $(':checkbox[columnindex=' + columnindex + "]", g.popup).parent().next().html(headerText);
            }
        },
        //改变列的位置
        changeCol: function (from, to, isAfter)
        {
            var g = this, p = this.options;
            if (!from || !to) return;
            var fromCol = g.getColumn(from);
            var toCol = g.getColumn(to);
            fromCol.frozen = toCol.frozen;
            var fromColIndex, toColIndex;
            var fromColumns = fromCol['__pid'] == -1 ? p.columns : g._columns[fromCol['__pid']].columns;
            var toColumns = toCol['__pid'] == -1 ? p.columns : g._columns[toCol['__pid']].columns;
            fromColIndex = $.inArray(fromCol, fromColumns);
            toColIndex = $.inArray(toCol, toColumns);
            var sameParent = fromColumns == toColumns;
            var sameLevel = fromCol['__level'] == toCol['__level'];
            toColumns.splice(toColIndex + (isAfter ? 1 : 0), 0, fromCol);
            if (!sameParent)
            {
                fromColumns.splice(fromColIndex, 1);
            }
            else
            {
                if (isAfter) fromColumns.splice(fromColIndex, 1);
                else fromColumns.splice(fromColIndex + 1, 1);
            }
            g._setColumns(p.columns);
            g.reRender();
        },


        collapseDetail: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            if (!rowdata) return;
            for (var i = 0, l = g.columns.length; i < l; i++)
            {
                if (g.columns[i].isdetail)
                {
                    var row = g.getRowObj(rowdata);
                    var cell = g.getCellObj(rowdata, g.columns[i]);
                    $(row).next("tr.l-grid-detailpanel").hide();
                    $(".l-grid-row-cell-detailbtn:first", cell).removeClass("l-open");
                    g.trigger('SysGridHeightChanged');
                    return;
                }
            }
        },
        extendDetail: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            if (!rowdata) return;
            for (var i = 0, l = g.columns; i < l; i++)
            {
                if (g.columns[i].isdetail)
                {
                    var row = g.getRowObj(rowdata);
                    var cell = g.getCellObj(rowdata, g.columns[i]);
                    $(row).next("tr.l-grid-detailpanel").show();
                    $(".l-grid-row-cell-detailbtn:first", cell).addClass("l-open");
                    g.trigger('SysGridHeightChanged');
                    return;
                }
            }
        },
        getParent: function (rowParm)
        {
            var g = this, p = this.options;
            if (!p.tree) return null;
            var rowdata = g.getRow(rowParm);
            if (!rowdata) return null;
            if (rowdata['__pid'] in g.records) return g.records[rowdata['__pid']];
            else return null;
        },
        getChildren: function (rowParm, deep)
        {
            var g = this, p = this.options;
            if (!p.tree) return null;
            var rowData = g.getRow(rowParm);
            if (!rowData) return null;
            var arr = [];
            function loadChildren(data)
            {
                if (data[p.tree.childrenName])
                {
                    for (var i = 0, l = data[p.tree.childrenName].length; i < l; i++)
                    {
                        var o = data[p.tree.childrenName][i];
                        if (o['__status'] == 'delete') continue;
                        arr.push(o);
                        if (deep)
                            loadChildren(o);
                    }
                }
            }
            loadChildren(rowData);
            return arr;
        },
        isLeaf: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            if (!rowdata) return;
            return rowdata['__hasChildren'] ? false : true;
        },
        hasChildren: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = this.getRow(rowParm);
            if (!rowdata) return;
            return (rowdata[p.tree.childrenName] && rowdata[p.tree.childrenName].length) ? true : false;
        },
        existRecord: function (record)
        {
            for (var rowid in this.records)
            {
                if (this.records[rowid] == record) return true;
            }
            return false;
        },
        _removeSelected: function (rowdata)
        {
            var g = this, p = this.options;
            if (p.tree)
            {
                var children = g.getChildren(rowdata, true);
                if (children)
                {
                    for (var i = 0, l = children.length; i < l; i++)
                    {
                        var index2 = $.inArray(children[i], g.selected);
                        if (index2 != -1) g.selected.splice(index2, 1);
                    }
                }
            }
            var index = $.inArray(rowdata, g.selected);
            if (index != -1) g.selected.splice(index, 1);
        },
        _getParentChildren: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            var listdata;
            if (p.tree && g.existRecord(rowdata) && rowdata['__pid'] in g.records)
            {
                listdata = g.records[rowdata['__pid']][p.tree.childrenName];
            }
            else
            {
                listdata = g.currentData[p.root];
            }
            return listdata;
        },
        _removeData: function (rowdata)
        {
            var g = this, p = this.options;
            var listdata = g._getParentChildren(rowdata);
            var index = $.inArray(rowdata, listdata);
            if (index != -1)
            {
                listdata.splice(index, 1);
            }
            g._removeSelected(rowdata);
        },
        _addData: function (rowdata, parentdata, neardata, isBefore)
        {
            var g = this, p = this.options;
            if (!g.currentData) g.currentData = {};
            if (!g.currentData[p.root]) g.currentData[p.root] = [];
            var listdata = g.currentData[p.root];
            if (neardata)
            {
                if (p.tree)
                {
                    if (parentdata)
                        listdata = parentdata[p.tree.childrenName];
                    else if (neardata['__pid'] in g.records)
                        listdata = g.records[neardata['__pid']][p.tree.childrenName];
                }
                var index = $.inArray(neardata, listdata);
                listdata.splice(index == -1 ? -1 : index + (isBefore ? 0 : 1), 0, rowdata);
            }
            else
            {
                if (p.tree && parentdata)
                {
                    listdata = parentdata[p.tree.childrenName];
                }
                listdata.push(rowdata);
            }
        },
        //移动数据(树)
        //@parm [parentdata] 附加到哪一个节点下级
        //@parm [neardata] 附加到哪一个节点的上方/下方
        //@parm [isBefore] 是否附加到上方
        _appendData: function (rowdata, parentdata, neardata, isBefore)
        {
            var g = this, p = this.options;
            rowdata[p.statusName] = "update";
            g._removeData(rowdata);
            g._addData(rowdata, parentdata, neardata, isBefore);
        },
        appendRange: function (rows, parentdata, neardata, isBefore)
        {
            var g = this, p = this.options;
            var toRender = false;
            $.each(rows, function (i, item)
            {
                if (item['__id'] && g.existRecord(item))
                {
                    if (g.isLeaf(parentdata)) g.upgrade(parentdata);
                    g._appendData(item, parentdata, neardata, isBefore);
                    toRender = true;
                }
                else
                {
                    g.appendRow(item, parentdata, neardata, isBefore);
                }
            });
            if (toRender) g.reRender();

        },
        appendRow: function (rowdata, parentdata, neardata, isBefore)
        {
            var g = this, p = this.options;
            if ($.isArray(rowdata))
            {
                g.appendRange(rowdata, parentdata, neardata, isBefore);
                return;
            }
            if (rowdata['__id'] && g.existRecord(rowdata))
            {
                g._appendData(rowdata, parentdata, neardata, isBefore);
                g.reRender();
                return;
            }
            if (parentdata && g.isLeaf(parentdata)) g.upgrade(parentdata);
            g.addRow(rowdata, neardata, isBefore ? true : false, parentdata);
        },
        upgrade: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            if (!rowdata || !p.tree) return;
            rowdata[p.tree.childrenName] = rowdata[p.tree.childrenName] || [];
            rowdata['__hasChildren'] = true;
            var rowobjs = [g.getRowObj(rowdata)];
            if (g.enabledFrozen()) rowobjs.push(g.getRowObj(rowdata, true));
            $("> td > div > .l-grid-tree-space:last", rowobjs).addClass("l-grid-tree-link l-grid-tree-link-open");
        },
        demotion: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            if (!rowdata || !p.tree) return;
            var rowobjs = [g.getRowObj(rowdata)];
            if (g.enabledFrozen()) rowobjs.push(g.getRowObj(rowdata, true));
            $("> td > div > .l-grid-tree-space:last", rowobjs).removeClass("l-grid-tree-link l-grid-tree-link-open l-grid-tree-link-close");
            if (g.hasChildren(rowdata))
            {
                var children = g.getChildren(rowdata);
                for (var i = 0, l = children.length; i < l; i++)
                {
                    g.deleteRow(children[i]);
                }
            }
            rowdata['__hasChildren'] = false;
        },
        collapse: function (rowParm)
        {
            var g = this, p = this.options;
            var targetRowObj = g.getRowObj(rowParm);
            var linkbtn = $(".l-grid-tree-link", targetRowObj);
            if (linkbtn.hasClass("l-grid-tree-link-close")) return;
            g.toggle(rowParm);
        },
        expand: function (rowParm)
        {
            var g = this, p = this.options;
            var targetRowObj = g.getRowObj(rowParm);
            var linkbtn = $(".l-grid-tree-link", targetRowObj);
            if (linkbtn.hasClass("l-grid-tree-link-open")) return;
            g.toggle(rowParm);
        },
        toggle: function (rowParm)
        {
            if (!rowParm) return;
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            var targetRowObj = [g.getRowObj(rowdata)];
            if (g.enabledFrozen()) targetRowObj.push(g.getRowObj(rowdata, true));
            var level = rowdata['__level'], indexInCollapsedRows;
            var linkbtn = $(".l-grid-tree-link:first", targetRowObj);
            var opening = true;
            g.collapsedRows = g.collapsedRows || [];
            if (linkbtn.hasClass("l-grid-tree-link-close")) //收缩
            {
                linkbtn.removeClass("l-grid-tree-link-close").addClass("l-grid-tree-link-open");
                indexInCollapsedRows = $.inArray(rowdata, g.collapsedRows);
                if (indexInCollapsedRows != -1) g.collapsedRows.splice(indexInCollapsedRows, 1);
            }
            else //折叠
            {
                opening = false;
                linkbtn.addClass("l-grid-tree-link-close").removeClass("l-grid-tree-link-open");
                indexInCollapsedRows = $.inArray(rowdata, g.collapsedRows);
                if (indexInCollapsedRows == -1) g.collapsedRows.push(rowdata);
            }
            var children = g.getChildren(rowdata, true);
            for (var i = 0, l = children.length; i < l; i++)
            {
                var o = children[i];
                var currentRow = $([g.getRowObj(o['__id'])]);
                if (g.enabledFrozen()) currentRow = currentRow.add(g.getRowObj(o['__id'], true));
                if (opening)
                {
                    $(".l-grid-tree-link", currentRow).removeClass("l-grid-tree-link-close").addClass("l-grid-tree-link-open");
                    currentRow.show();
                }
                else
                {
                    $(".l-grid-tree-link", currentRow).removeClass("l-grid-tree-link-open").addClass("l-grid-tree-link-close");
                    currentRow.hide();
                }
            }
        },
        _bulid: function ()
        {
            var g = this;
            g._clearGrid();
            //创建头部
            g._initBuildHeader();
            //宽度高度初始化
            g._initHeight();
            //创建底部工具条
            g._initFootbar();
            //创建分页
            g._buildPager();
            //创建事件
            g._setEvent();
        },
        _setColumns: function (columns)
        {
            var g = this;
            //初始化列
            g._initColumns();
            //创建表头
            g._initBuildGridHeader();
            //创建 显示/隐藏 列 列表
            //g._initBuildPopup();
        },
        _initBuildHeader: function ()
        {
            var g = this, p = this.options;
            if (p.title)
            {
                $(".l-panel-header-text", g.header).html(p.title);
                if (p.headerImg)
                    g.header.append("<img src='" + p.headerImg + "' />").addClass("l-panel-header-hasicon");
            }
            else
            {
                g.header.hide();
            }
            if (p.toolbar)
            {
                if ($.fn.ligerToolBar)
                    g.toolbarManager = g.topbar.ligerToolBar(p.toolbar);
            }
            else
            {
                g.topbar.remove();
            }
        },
        _createColumnId: function (column)
        {
            if (column.id != null && column.id != "") return column.id.toString();
            return "c" + (100 + this._columnCount);
        },
        _isColumnId: function (str)
        {
            return (str in this._columns);
        },
        _initColumns: function ()
        {
            var g = this, p = this.options;
            g._columns = {};             //全部列的信息  
            g._columnCount = 0;
            g._columnLeafCount = 0;
            g._columnMaxLevel = 1;
            if (!p.columns) return;
            function removeProp(column, props)
            {
                for (var i in props)
                {
                    if (props[i] in column)
                        delete column[props[i]];
                }
            }
            //设置id、pid、level、leaf，返回叶节点数,如果是叶节点，返回1
            function setColumn(column, level, pid, previd)
            {
                removeProp(column, ['__id', '__pid', '__previd', '__nextid', '__domid', '__leaf', '__leafindex', '__level', '__colSpan', '__rowSpan']);
                if (level > g._columnMaxLevel) g._columnMaxLevel = level;
                g._columnCount++;
                column['__id'] = g._createColumnId(column);
                column['__domid'] = g.id + "|hcell|" + column['__id'];
                g._columns[column['__id']] = column;
                if (!column.columns || !column.columns.length)
                    column['__leafindex'] = g._columnLeafCount++;
                column['__level'] = level;
                column['__pid'] = pid;
                column['__previd'] = previd;
                if (!column.columns || !column.columns.length)
                {
                    column['__leaf'] = true;
                    return 1;
                }
                var leafcount = 0;
                var newid = -1;
                for (var i = 0, l = column.columns.length; i < l; i++)
                {
                    var col = column.columns[i];
                    leafcount += setColumn(col, level + 1, column['__id'], newid);
                    newid = col['__id'];
                }
                column['__leafcount'] = leafcount;
                return leafcount;
            }
            var lastid = -1;
            //行序号
            if (p.rownumbers)
            {
                var frozenRownumbers = g.enabledGroup() ? false : p.frozen && p.frozenRownumbers;
                var col = { isrownumber: true, issystem: true, width: p.rownumbersColWidth, frozen: frozenRownumbers };
                setColumn(col, 1, -1, lastid);
                lastid = col['__id'];
            }
            //明细列
            if (g.enabledDetail())
            {
                var frozenDetail = g.enabledGroup() ? false : p.frozen && p.frozenDetail;
                var col = { isdetail: true, issystem: true, width: p.detailColWidth, frozen: frozenDetail };
                setColumn(col, 1, -1, lastid);
                lastid = col['__id'];
            }
            //复选框列
            if (g.enabledCheckbox())
            {
                var frozenCheckbox = g.enabledGroup() ? false : p.frozen && p.frozenCheckbox;
                var col = { ischeckbox: true, issystem: true, width: p.detailColWidth, frozen: frozenCheckbox };
                setColumn(col, 1, -1, lastid);
                lastid = col['__id'];
            }
            for (var i = 0, l = p.columns.length; i < l; i++)
            {
                var col = p.columns[i];
                setColumn(col, 1, -1, lastid);
                lastid = col['__id'];
            }
            //设置colSpan和rowSpan
            for (var id in g._columns)
            {
                var col = g._columns[id];
                if (col['__leafcount'] > 1)
                {
                    col['__colSpan'] = col['__leafcount'];
                }
                if (col['__leaf'] && col['__level'] != g._columnMaxLevel)
                {
                    col['__rowSpan'] = g._columnMaxLevel - col['__level'] + 1;
                }
            }
            //叶级别列的信息  
            g.columns = g.getColumns();
            $(g.columns).each(function (i, column)
            {
                column.columnname = column.name;
                column.columnindex = i;
                column.type = column.type || "string";
                column.islast = i == g.columns.length - 1;
                column.isSort = column.isSort == true ? true : false;
                column.frozen = column.frozen ? true : false;
                column._width = g._getColumnWidth(column);
                column._hide = column.hide ? true : false;
            });
        },
        _getColumnWidth: function (column)
        {
            var g = this, p = this.options;
            if (column._width) return column._width;
            var colwidth;
            if (column.width)
            {
                colwidth = column.width;
            }
            else if (p.columnWidth)
            {
                colwidth = p.columnWidth;
            }
            if (!colwidth)
            {
                var lwidth = 4;
                if (g.enabledCheckbox()) lwidth += p.checkboxColWidth;
                if (g.enabledDetail()) lwidth += p.detailColWidth;
                colwidth = parseInt((g.grid.width() - lwidth) / g.columns.length);
            }
            if (typeof (colwidth) == "string" && colwidth.indexOf('%') > 0)
            {
                column._width = colwidth = parseInt(parseInt(colwidth) * 0.01 * (g.grid.width() - g.columns.length));
            }
            if (column.minWidth && colwidth < column.minWidth) colwidth = column.minWidth;
            if (column.maxWidth && colwidth > column.maxWidth) colwidth = column.maxWidth;
            column._width = colwidth;
            return colwidth;
        },
        
        _formatColumnWidth:function(column){
            var g = this, p = this.options;
            var colwidth = column.width || p.columnWidth;
            if (!colwidth || colwidth == "auto")
            {
            	return "auto";
            }
            if (typeof (colwidth) == "string")
            {
                if(!(colwidth.indexOf('%')>0) && !(colwidth.indexOf('px')>0)){
                	colwidth = colwidth+"px";
                }
            }else if(typeof(colwidth)=="number"){
            	colwidth = colwidth+"px";
            }

            return colwidth;
        },
        
        _createHeaderCell: function (column)
        {
            var g = this, p = this.options;
            var jcell = $("<td class='l-grid-hd-cell'><div class='l-grid-hd-cell-inner'><span class='l-grid-hd-cell-text'></span></div></td>");
            jcell.attr("id", column['__domid']);
            if (!column['__leaf'])
                jcell.addClass("l-grid-hd-cell-mul");
            if (column.columnindex == g.columns.length - 1)
            {
                jcell.addClass("l-grid-hd-cell-last");
            }
            if (column.isrownumber)
            {
                jcell.addClass("l-grid-hd-cell-rownumbers");
                jcell.html("<div class='l-grid-hd-cell-inner'></div>");
            }
            if (column.ischeckbox)
            {
                jcell.addClass("l-grid-hd-cell-checkbox");
                jcell.html("<div class='l-grid-hd-cell-inner' style='max-height:16px;'><div class='l-grid-hd-cell-text l-grid-hd-cell-btn-checkbox'></div></div>");
            }
            if (column.isdetail)
            {
                jcell.addClass("l-grid-hd-cell-detail");
                jcell.html("<div class='l-grid-hd-cell-inner'><div class='l-grid-hd-cell-text l-grid-hd-cell-btn-detail'></div></div>");
            }
            if (column.align)
            {
                //$(".l-grid-hd-cell-inner:first", jcell).css("text-align", column.align);
            	$(".l-grid-hd-cell-inner:first", jcell).css("text-align", "center");
            }
            if (column['__colSpan']) jcell.attr("colSpan", column['__colSpan']);
            if (column['__rowSpan'])
            {
                jcell.attr("rowSpan", column['__rowSpan']);
                jcell.height(p.headerRowHeight * column['__rowSpan']);
            } else
            {
                jcell.height(p.headerRowHeight);
            }
            if (column['__leaf'])
            {
            	jcell.css('width',g._formatColumnWidth(column));
                jcell.attr("columnindex", column['__leafindex']);
            }
            var cellHeight = jcell.height();
            if(cellHeight > 10) $(">div:first", jcell).height(cellHeight);
            if (column._hide) jcell.hide();
            if (column.name) jcell.attr({ columnname: column.name });
            var headerText = "";
            if (column.display && column.display != "")
                headerText = column.display;
            else if (column.headerRender)
                headerText = column.headerRender(column);
            else
                headerText = "&nbsp;";
            
            var width = g._formatColumnWidth(column);
            if(width.indexOf('px')>0){
            	$(".l-grid-hd-cell-inner:first", jcell).css("width",g._formatColumnWidth(column));
            }
            $(".l-grid-hd-cell-inner:first", jcell).css("line-height", jcell.height()+"px");
            
            $(".l-grid-hd-cell-text:first", jcell).html(headerText);
            if (!column.issystem && column['__leaf'] && column.resizable !== false && $.fn.ligerResizable && p.allowAdjustColWidth)
            {
                g.colResizable[column['__id']] = jcell.ligerResizable({
                    handles: 'e',
                    onStartResize: function (e, ev)
                    {
                        this.proxy.hide();
                        g.draggingline.css({ height: g.body.height(), top: 0, left: ev.pageX - g.grid.offset().left + parseInt(g.body[0].scrollLeft) }).show();
                    },
                    onResize: function (e, ev)
                    {
                        g.colresizing = true;
                        g.draggingline.css({ left: ev.pageX - g.grid.offset().left + parseInt(g.body[0].scrollLeft) });
                        $('body').add(jcell).css('cursor', 'e-resize');
                    },
                    onStopResize: function (e)
                    {
                        g.colresizing = false;
                        $('body').add(jcell).css('cursor', 'default');
                        g.draggingline.hide();
                        g.setColumnWidth(column, parseInt(column._width) + e.diffX);
                        return false;
                    }
                });
            }
            return jcell;
        },
        _initBuildGridHeader: function ()
        {
            var g = this, p = this.options;
            g.gridtablewidth = 0;
            if (g.colResizable)
            {
                for (var i in g.colResizable)
                {
                    g.colResizable[i].destroy();
                }
                g.colResizable = null;
            }
            g.colResizable = {};
            g.gridheader.html("");
            for (var level = 1; level <= g._columnMaxLevel; level++)
            {
                var columns = g.getColumns(level);           //获取level层次的列集合
                
                var islast = level == g._columnMaxLevel;     //是否最末级
                var tr = $("<tr class='sundun-grid-header-column'></tr>");
                if (!islast) tr.addClass("l-grid-hd-mul");
                g.gridheader.append(tr);
                for(var i in columns){
                	var column = columns[i];
                    tr.append(g._createHeaderCell(column));
                    if (column['__leaf'])
                    {
                        var colwidth = column['_width'];
                        g.gridtablewidth += (parseInt(colwidth) ? parseInt(colwidth) : 0) + 1;
                    }                	
                }
            }
        },
        _initBuildPopup: function ()
        {
            var g = this, p = this.options;
            $(':checkbox', g.popup).unbind();
            $('tbody tr', g.popup).remove();
            $(g.columns).each(function (i, column)
            {
                if (column.issystem) return;
                if (column.isAllowHide == false) return;
                var chk = 'checked="checked"';
                if (column._hide) chk = '';
                var header = column.display;
                $('tbody', g.popup).append('<tr><td class="l-column-left"><input type="checkbox" ' + chk + ' class="l-checkbox" columnindex="' + i + '"/></td><td class="l-column-right">' + header + '</td></tr>');
            });
            if ($.fn.ligerCheckBox)
            {
                $('input:checkbox', g.popup).ligerCheckBox(
                {
                    onBeforeClick: function (obj)
                    {
                        if (!obj.checked) return true;
                        if ($('input:checked', g.popup).length <= p.minColToggle)
                            return false;
                        return true;
                    }
                });
            }
            //表头 - 显示/隐藏'列控制'按钮事件
            if (p.allowHideColumn)
            {
                $('tr', g.popup).hover(function ()
                {
                    $(this).addClass('l-popup-row-over');
                },
                function ()
                {
                    $(this).removeClass('l-popup-row-over');
                });
                var onPopupCheckboxChange = function ()
                {
                    if ($('input:checked', g.popup).length + 1 <= p.minColToggle)
                    {
                        return false;
                    }
                    g.toggleCol(parseInt($(this).attr("columnindex")), this.checked, true);
                };
                if ($.fn.ligerCheckBox)
                    $(':checkbox', g.popup).bind('change', onPopupCheckboxChange);
                else
                    $(':checkbox', g.popup).bind('click', onPopupCheckboxChange);
            }
        },
        _initHeight: function ()
        {
        	
            var g = this, p = this.options;
            //设置宽度
            g.setWidth(p.width);
            //设置高度
            g._onResize.call(g);
        },
        _initFootbar: function ()
        {
            var g = this, p = this.options;
            if(p.pageBarType=='0'){
	            if (p.usePager)
	            {
		           //创建底部工具条 - 选择每页显示记录数
		           g.toolbar.show();
	            }
	            else
	            {
	                g.toolbar.hide();
	            }
            }
            else if(p.pageBarType=='1'){
                if (p.usePager)
                {
                    //创建底部工具条 - 选择每页显示记录数
                    var optStr = "";
                    var selectedIndex = -1;
                    $(p.pageSizeOptions).each(function (i, item)
                    {
                        var selectedStr = "";
                        if (p.pageSize == item) selectedIndex = i;
                        optStr += "<option value='" + item + "' " + selectedStr + " >" + item + "</option>";
                    });

                    $('.l-bar-selectpagesize', g.toolbar).append("<select name='rp'>" + optStr + "</select>");
                    if (selectedIndex != -1) $('.l-bar-selectpagesize select', g.toolbar)[0].selectedIndex = selectedIndex;
                    if (p.switchPageSizeApplyComboBox && $.fn.ligerComboBox)
                    {
                        $(".l-bar-selectpagesize select", g.toolbar).ligerComboBox(
                        {
                            onBeforeSelect: function ()
                            {
                                if (p.url && g.isDataChanged && !confirm(p.isContinueByDataChanged))
                                    return false;
                                return true;
                            },
                            width: 45
                        });
                    }
                }
                else
                {
                    g.toolbar.hide();
                }
            }
        },
        _searchData: function (data, clause)
        {
            var g = this, p = this.options;
            var newData = new Array();
            for (var i = 0; i < data.length; i++)
            {
                if (clause(data[i], i))
                {
                    newData[newData.length] = data[i];
                }
            }
            return newData;
        },
        _clearGrid: function ()
        {
            var g = this, p = this.options;
            for (var i in g.rows)
            {
                var rowobj = $(g.getRowObj(g.rows[i]));
                if (g.enabledFrozen())
                    rowobj = rowobj.add(g.getRowObj(g.rows[i], true));
                rowobj.unbind();
            }
            //清空数据
            g.gridbody.html("");
            g.recordNumber = 0;
            g.records = {};
            g.rows = [];
            //清空选择的行
            g.selected = [];
            g.totalNumber = 0;
            //编辑器计算器
            g.editorcounter = 0;
        },
        _fillGridBody: function (data, frozen)
        {
            var g = this, p = this.options;
            //加载数据 
            var gridhtmlarr = [];
            if (g.enabledGroup()) //启用分组模式
            {
                var groups = []; //分组列名数组
                var groupsdata = []; //切成几块后的数据
                g.groups = groupsdata;
                for (var rowparm in data)
                {
                    var item = data[rowparm];
                    var groupColumnValue = item[p.groupColumnName];
                    var valueIndex = $.inArray(groupColumnValue, groups);
                    if (valueIndex == -1)
                    {
                        groups.push(groupColumnValue);
                        valueIndex = groups.length - 1;
                        groupsdata.push([]);
                    }
                    groupsdata[valueIndex].push(item);
                }
                $(groupsdata).each(function (i, item)
                {
                    if (groupsdata.length == 1)
                        gridhtmlarr.push('<tr class="l-grid-grouprow l-grid-grouprow-last l-grid-grouprow-first"');
                    if (i == groupsdata.length - 1)
                        gridhtmlarr.push('<tr class="l-grid-grouprow l-grid-grouprow-last"');
                    else if (i == 0)
                        gridhtmlarr.push('<tr class="l-grid-grouprow l-grid-grouprow-first"');
                    else
                        gridhtmlarr.push('<tr class="l-grid-grouprow"');
                    gridhtmlarr.push(' groupindex"=' + i + '" >');
                    gridhtmlarr.push('<td colSpan="' + g.columns.length + '" class="l-grid-grouprow-cell">');
                    gridhtmlarr.push('<span class="l-grid-group-togglebtn">&nbsp;&nbsp;&nbsp;&nbsp;</span>');
                    if (p.groupRender)
                        gridhtmlarr.push(p.groupRender(groups[i], item, p.groupColumnDisplay));
                    else
                        gridhtmlarr.push(p.groupColumnDisplay + ':' + groups[i]);


                    gridhtmlarr.push('</td>');
                    gridhtmlarr.push('</tr>');

                    gridhtmlarr.push(g._getHtmlFromData(item, frozen));
                    //汇总
                    if (g.isTotalSummary())
                        gridhtmlarr.push(g._getTotalSummaryHtml(item, "l-grid-totalsummary-group", frozen));
                });
            }
            else
            {
                gridhtmlarr.push(g._getHtmlFromData(data, frozen));
            }
            var tr = '<TR class=l-grid-row  id="nodata"><TD  colspan="100" class="l-grid-row-cell l-grid-row-cell-checkbox"  id="nodata"><DIV style="TEXT-ALIGN: center; LINE-HEIGHT: 30px; MIN-HEIGHT: 30px; HEIGHT: 30px" class=l-grid-row-cell-inner >暂无数据</DIV></TD></TR>';
			
			if(gridhtmlarr.length > 0){
				tr = gridhtmlarr.join('');
			};
            g.gridbody.html(tr);
            if(p.alternatingRow){
            	g.gridbody.find("tr:odd").addClass("l-grid-row-even");
            }
            //分组时不需要            
            if (!g.enabledGroup())
            {
                //创建汇总行
                g._bulidTotalSummary(frozen);
            }
            $("> div:first", g.gridbody).width(g.gridtablewidth);
        },
        _showData: function ()
        {
            var g = this, p = this.options;
            g.changedCells = {};
            var data = g.currentData[p.root];
            if (p.usePager)
            {
                //更新总记录数
                if (p.dataAction == "server" && g.data && g.data[p.record])
                    p.total = g.data[p.record];
                else if (g.filteredData && g.filteredData[p.root])
                    p.total = g.filteredData[p.root].length;
                else if (g.data && g.data[p.root])
                    p.total = g.data[p.root].length;
                else if (data)
                    p.total = data.length;

                p.page = p.newPage;
                if (!p.total) p.total = 0;
                if (!p.page) p.page = 1;
                p.pageCount = Math.ceil(p.total / p.pageSize);
                if (!p.pageCount) p.pageCount = 1;
                //更新分页
                g._buildPager();
            }
            //加载中
            $('.l-bar-btnloading:first', g.toolbar).removeClass('l-bar-btnloading');
            if (g.trigger('beforeShowData', [g.currentData]) == false) return;
            g._clearGrid();
            g.isDataChanged = false;
            if (!data) return;
            $(".l-bar-btnload:first span", g.toolbar).removeClass("l-disabled");
            g._updateGridData();
            g._fillGridBody(g.rows, false);
            g.trigger('SysGridHeightChanged');
            if (p.totalRender)
            {
                $(".l-panel-bar-total", g.element).remove();
                if(p.pageBarType=='1'){
                	$(".l-panel-bar", g.element).before('<div class="l-panel-bar-total">' + p.totalRender(g.data, g.filteredData) + '</div>');
                }else{
                	$(".sundun-panel-bar", g.element).before('<div class="l-panel-bar-total">' + p.totalRender(g.data, g.filteredData) + '</div>');
                }
            }
            if (p.mouseoverRowCssClass)
            {
                for (var i in g.rows)
                {
                    var rowobj = $(g.getRowObj(g.rows[i]));
                    if (g.enabledFrozen())
                        rowobj = rowobj.add(g.getRowObj(g.rows[i], true));
                    rowobj.bind('mouseover.gridrow', function ()
                    {
                        g._onRowOver(this, true);
                    }).bind('mouseout.gridrow', function ()
                    {
                        g._onRowOver(this, false);
                    });
                }
            }
            g._fixHeight();
            g.table.trigger('sundun.scroll.grid'); 
            g.trigger('afterShowData', [g.currentData]);
        },
        _fixHeight : function()
        {
            var g = this, p = this.options;
            if (p.fixedCellHeight || !p.frozen) return;
            var column1,column2;
            for (var i in g.columns)
            {
                var column = g.columns[i];
                if(column1 && column2) break;
                if (column.frozen && !column1)
                { 
                    column1 = column; 
                    continue;
                }
                if (!column.frozen && !column2)
                {
                    column2 = column; 
                    continue;
                } 
            }
            if (!column1 || !column2) return;
            for (var rowid in g.records)
            {
                var cell1 = g.getCellObj(rowid, column1), cell2 = g.getCellObj(rowid, column2);
                var height = Math.max($(cell1).height(), ($(cell2).height()));
                $(cell1).add(cell2).height(height);
            }
        },
        _getRowDomId: function (rowdata, frozen)
        {
            return this.id + "|" + (frozen ? "1" : "2") + "|" + rowdata['__id'];
        },
        _getCellDomId: function (rowdata, column)
        {
            return this._getRowDomId(rowdata, column.frozen) + "|" + column['__id'];
        },
        _getHtmlFromData: function (data, frozen)
        {
        	
            if (!data) return "";
            var g = this, p = this.options;
            var gridhtmlarr = [];
			if(data.length == 0){
				gridhtmlarr =  '<TR class=l-grid-row  id="' + this.id + '|2|r0001"><TD  colspan="100" class="l-grid-row-cell l-grid-row-cell-checkbox"  id="' + this.id + '|2|r0001|c001"><DIV style="TEXT-ALIGN: center; LINE-HEIGHT: 30px; MIN-HEIGHT: 30px; HEIGHT: 30px" class=l-grid-row-cell-inner >暂无数据</DIV></TD></TR>';
				return gridhtmlarr;
			};
            for (var i = 0, l = data.length; i < l; i++)
            {
                var item = data[i];
                var rowid = item['__id'];
                if (!item) continue;
                gridhtmlarr.push('<tr');
                gridhtmlarr.push(' id="' + g._getRowDomId(item, frozen) + '"');
                gridhtmlarr.push(' class="l-grid-row'); //class start 
                if (!frozen && g.enabledCheckbox() && p.isChecked && p.isChecked(item))
                {
                    g.select(item);
                    gridhtmlarr.push(' l-selected');
                }
                else if (g.isSelected(item))
                {
                    gridhtmlarr.push(' l-selected');
                }
                else if (p.isSelected && p.isSelected(item))
                {
                    g.select(item);
                    gridhtmlarr.push(' l-selected');
                }
                if (item['__index'] % 2 == 1 && p.alternatingRow)
                    gridhtmlarr.push(' l-grid-row-alt');
                gridhtmlarr.push('" ');  //class end
                if (p.rowAttrRender) gridhtmlarr.push(p.rowAttrRender(item, rowid));
                if (p.tree && g.collapsedRows && g.collapsedRows.length)
                {
                    var isHide = function ()
                    {
                        var pitem = g.getParent(item);
                        while (pitem)
                        {
                            if ($.inArray(pitem, g.collapsedRows) != -1) return true;
                            pitem = g.getParent(pitem);
                        }
                        return false;
                    };
                    if (isHide()) gridhtmlarr.push(' style="display:none;" ');
                }
                gridhtmlarr.push('>');
                $(g.columns).each(function (columnindex, column)
                {
                    //判断是否合并列
                    var step = g._getRowSpan(column,i,data);
                    if(step==0) return;
                    
                    gridhtmlarr.push('<td');
                    gridhtmlarr.push(' id="' + g._getCellDomId(item, this) + '"');
                    //如果是行序号(系统列)
                    if (this.isrownumber)
                    {
                        gridhtmlarr.push(' class="l-grid-row-cell l-grid-row-cell-rownumbers" style="width:' + this._width + 'px"><div class="l-grid-row-cell-inner"');
                        if (p.fixedCellHeight)
                        	gridhtmlarr.push(' style = "height:' + p.rowHeight + 'px;line-height:'+p.rowHeight+'px;" ');
                        else
                        	gridhtmlarr.push(' style = "min-height:' + p.rowHeight + 'px;line-height:'+p.rowHeight+'px;" ');
                        gridhtmlarr.push('>' + (parseInt(item['__index']) + 1) + '</div></td>');
                        return ;
                    }
                    //如果是复选框(系统列)
                    if (this.ischeckbox)
                    {
                        gridhtmlarr.push(' class="l-grid-row-cell l-grid-row-cell-checkbox" style="width:' + this._width + 'px"><div class="l-grid-row-cell-inner"');
                        gridhtmlarr.push(' style = "max-height:13px;height:13px;"');                        
                        gridhtmlarr.push('>');
                        gridhtmlarr.push('<span class="l-grid-row-cell-btn-checkbox"></span>');
                        gridhtmlarr.push('</div></td>');
                        return ;
                    }
                        //如果是明细列(系统列)
                    else if (this.isdetail)
                    {
                        gridhtmlarr.push(' class="l-grid-row-cell l-grid-row-cell-detail" style="width:' + this.width + 'px"><div class="l-grid-row-cell-inner"');
                        if (p.fixedCellHeight)
                            gridhtmlarr.push(' style = "height:' + p.rowHeight + 'px;" ');
                        else
                            gridhtmlarr.push(' style = "min-height:' + p.rowHeight + 'px;" ');
                        gridhtmlarr.push('><span class="l-grid-row-cell-detailbtn"></span></div></td>');
                        return;
                    }

                    var colwidth = this._width;
                    gridhtmlarr.push(' class="l-grid-row-cell ');
                    if (g.changedCells[rowid + "_" + this['__id']]) gridhtmlarr.push("l-grid-row-cell-edited ");
                    if (this.islast)
                        gridhtmlarr.push('l-grid-row-cell-last ');
                    gridhtmlarr.push('"');
                    
                    gridhtmlarr.push(' style = "');
                    
                    gridhtmlarr.push('width:' + g._formatColumnWidth(column) + '; ');
                    if (column._hide)
                    {
                        gridhtmlarr.push('display:none;');
                    }
                    if(step>1){
                    	gridhtmlarr.push('" rowspan="'+step);
                    }
                    gridhtmlarr.push('" columnname="'+column.name);
                    gridhtmlarr.push('">');
                    gridhtmlarr.push(g._getCellHtml(item, column));
                    gridhtmlarr.push('</td>');
                });
                gridhtmlarr.push('</tr>');
            }
            return gridhtmlarr.join('');
        },
        
        //获取步长
        _getRowSpan:function(column,rowid,data){
        	var g = this;
        	var step = 1;
        	rowid = Number(rowid);
        	if(!rowid) rowid = 0;
        	if(column.isMerger===true)
        	{
        		var tempValue = data[rowid][column.name];
        		//判断是否与上一行的数据是否相等
        		if(rowid>0){
        			if(data[rowid-1][column.name]==tempValue){
        				step = 0;
        				return step;
        			}
        		}
        		
            	for(var i=rowid+1;i<data.length;i++){
            		var obj = data[i];
            		if(obj[column.name]!=tempValue) break;
            		step++;
            		tempValue = obj[column.name];
            	}
        	}
        	
        	return step;
        },
        
        _getCellHtml: function (rowdata, column)
        {
            var g = this, p = this.options;
            if (column.isrownumber)
                return '<div class="l-grid-row-cell-inner">' + (parseInt(rowdata['__index']) + 1) + '</div>';
            var htmlarr = [];
            htmlarr.push('<div class="l-grid-row-cell-inner"');
            htmlarr.push(' style = "');
            if (p.fixedCellHeight) htmlarr.push('height:' + p.rowHeight + 'px; ');
            htmlarr.push('min-height:' + p.rowHeight + 'px; ');
            var width = g._formatColumnWidth(column);
            if(width.indexOf('px')>0){
            	htmlarr.push('width:' + g._formatColumnWidth(column) + '; ');
            }
            htmlarr.push('line-height:' + p.rowHeight + 'px; ');
            if (column.align) {
            	htmlarr.push('text-align:' + column.align + ';');
            	//向左对齐
            	if(column.align=='left'){
            		htmlarr.push('margin-left:4px;');
            	}
            	//向右对齐
            	else if(column.align=='right'){
            		htmlarr.push('margin-right:4px;');
            	}
            }
            var content = g._getCellContent(rowdata, column);
            if(column.useTitle===true){
            	var value = g._getValueByName(rowdata,column.name);
            	htmlarr.push('" title="'+value);
            }
            htmlarr.push('">' + content + '</div>');
            
            return htmlarr.join('');
        },
        _setValueByName: function (rowdata, columnname, value)
        {
            if (!rowdata || !columnname) return null;
            try
            {
                new Function("rowdata,value", "rowdata." + columnname + "=value;")(rowdata, value);
            }
            catch (e)
            {
            }
        },
        _getValueByName: function (rowdata, columnname)
        {
            if (!rowdata || !columnname) return null;
            try
            {
                return new Function("rowdata", "return rowdata." + columnname + ";")(rowdata);
            }
            catch (e)
            {
                return null;
            }
        },
        _getCellContent: function (rowdata, column)
        {
            var g = this, p = this.options;
            if (!rowdata || !column) return "";
            if (column.isrownumber) return parseInt(rowdata['__index']) + 1;
            var rowid = rowdata['__id'];
            var rowindex = rowdata['__index'];
            var value = g._getValueByName(rowdata, column.name);
            var text = g._getValueByName(rowdata, column.textField);
            var content = "";
            if (column.render)
            {
                content = column.render.call(g, rowdata, rowindex, value, column);
            }
            else if (p.formatters[column.type])
            {
                content = p.formatters[column.type].call(g, value, column);
            }
            else if (text != null)
            {
                content = text.toString();
            }
            else if (value != null)
            {
                content = value.toString();
            }
            if (p.tree && (p.tree.columnName != null && p.tree.columnName == column.name || p.tree.columnId != null && p.tree.columnId == column.id))
            {
                content = g._getTreeCellHtml(content, rowdata);
            }
            return content || "";
        },
        _getTreeCellHtml: function (oldContent, rowdata)
        {
            var level = rowdata['__level'];
            var g = this, p = this.options;
            //var isExtend = p.tree.isExtend(rowdata);
            var isExtend = $.inArray(rowdata, g.collapsedRows || []) == -1;
            var isParent = p.tree.isParent(rowdata);
            var content = "";
            level = parseInt(level) || 1;
            for (var i = 1; i < level; i++)
            {
                content += "<div class='l-grid-tree-space'></div>";
            }
            if (isExtend && isParent)
                content += "<div class='l-grid-tree-space l-grid-tree-link l-grid-tree-link-open'></div>";
            else if (isParent)
                content += "<div class='l-grid-tree-space l-grid-tree-link l-grid-tree-link-close'></div>";
            else
                content += "<div class='l-grid-tree-space'></div>";
            content += "<span class='l-grid-tree-content'>" + oldContent + "</span>";
            return content;
        },
        _applyEditor: function (obj)
        {
            var g = this, p = this.options;
            var rowcell = obj, ids = rowcell.id.split('|');
            var columnid = ids[ids.length - 1], column = g._columns[columnid];
            var row = $(rowcell).parent(), rowdata = g.getRow(row[0]), rowid = rowdata['__id'], rowindex = rowdata['__index'];
            if (!column || !column.editor) return;
            var columnname = column.name, columnindex = column.columnindex;
            if (column.editor.type && p.editors[column.editor.type])
            {
                var currentdata = g._getValueByName(rowdata, columnname);
                var editParm = { record: rowdata, value: currentdata, column: column, rowindex: rowindex };
                if (column.textField) editParm.text = g._getValueByName(rowdata, column.textField);
                if (g.trigger('beforeEdit', [editParm]) == false) return false;
                g.lastEditRow = rowdata;
                var editor = p.editors[column.editor.type];
                var jcell = $(rowcell), offset = $(rowcell).offset();
                jcell.html("");
                g.setCellEditing(rowdata, column, true);
                var width = $(rowcell).width(), height = $(rowcell).height();
                var container = $("<div class='l-grid-editor'></div>").appendTo(g.grid);
                var left = 0, top = 0;
                var pc = jcell.position();
                var pb = g.gridbody.position();
                var pv = g.gridview2.position();
                var topbarHeight = p.toolbar ? g.topbar.height() : 0;
                left = pc.left + pb.left + pv.left;
                top = pc.top + pb.top + pv.top + topbarHeight;

                if ($.browser.mozilla)
                    container.css({ left: left, top: top }).show();
                else
                    container.css({ left: left, top: top + 1 }).show();
                if (column.textField) editParm.text = g._getValueByName(rowdata, column.textField);
                var editorInput = g._createEditor(editor, container, editParm, width, height);
                g.editor = { editing: true, editor: editor, input: editorInput, editParm: editParm, container: container };
                g.unbind('sysEndEdit');
                g.bind('sysEndEdit', function ()
                {
                    var newValue = editor.getValue(editorInput, editParm);
                    if (newValue != currentdata)
                    {
                        $(rowcell).addClass("l-grid-row-cell-edited");
                        g.changedCells[rowid + "_" + column['__id']] = true; 
                        editParm.value = newValue;
                        if (column.textField && editor.getText)
                        {
                            editParm.text = editor.getText(editorInput, editParm);
                        }
                        if (editor.onChange) editor.onChange(editParm);
                        if (g._checkEditAndUpdateCell(editParm))
                        {
                            if (editor.onChanged) editor.onChanged(editParm);
                        }
                    }
                });
            }
        },
        _checkEditAndUpdateCell: function (editParm)
        {
            var g = this, p = this.options;
            if (g.trigger('beforeSubmitEdit', [editParm]) == false) return false;
            var column = editParm.column;
            if (editParm.text && column.textField) g._setValueByName(editParm.record, column.textField, editParm.text);
            g.updateCell(column, editParm.value, editParm.record);
            if (column.render || g.enabledTotal()) g.reRender({ column: column });
            g.reRender({ rowdata: editParm.record });
            return true;
        },
        _getCurrentPageData: function (source)
        {
            var g = this, p = this.options;
            var data = {};
            data[p.root] = [];
            if (!source || !source[p.root] || !source[p.root].length)
            {
                data[p.record] = 0;
                return data;
            }
            data[p.record] = source[p.root].length;
            if (!p.newPage) p.newPage = 1;
            for (i = (p.newPage - 1) * p.pageSize; i < source[p.root].length && i < p.newPage * p.pageSize; i++)
            {
                data[p.root].push(source[p.root][i]);
            }
            return data;
        },
        //比较某一列两个数据
        _compareData: function (data1, data2, columnName, columnType)
        {
            var g = this, p = this.options;
            var val1 = data1[columnName], val2 = data2[columnName];
            if (val1 == null && val2 != null) return 1;
            else if (val1 == null && val2 == null) return 0;
            else if (val1 != null && val2 == null) return -1;
            if (p.sorters[columnType])
                return p.sorters[columnType].call(g, val1, val2);
            else
                return val1 < val2 ? -1 : val1 > val2 ? 1 : 0;
        },
        _getTotalCellContent: function (column, data)
        {
            var g = this, p = this.options;
            var totalsummaryArr = [];
            if (column.totalSummary)
            {
                var isExist = function (type)
                {
                    for (var i = 0; i < types.length; i++)
                        if (types[i].toLowerCase() == type.toLowerCase()) return true;
                    return false;
                };
                var sum = 0, count = 0, avg = 0;
                var max = parseFloat(data[0][column.name]);
                var min = parseFloat(data[0][column.name]);
                for (var i = 0; i < data.length; i++)
                {
                    count += 1;
                    var value = parseFloat(data[i][column.name]);
                    if (!value) continue;
                    sum += value;
                    if (value > max) max = value;
                    if (value < min) min = value;
                }
                avg = sum * 1.0 / data.length;
                if (column.totalSummary.render)
                {
                    var renderhtml = column.totalSummary.render({
                        sum: sum,
                        count: count,
                        avg: avg,
                        min: min,
                        max: max
                    }, column, g.data);
                    totalsummaryArr.push(renderhtml);
                }
                else if (column.totalSummary.type)
                {
                    var types = column.totalSummary.type.split(',');
                    if (isExist('sum'))
                        totalsummaryArr.push("<div>Sum=" + sum.toFixed(2) + "</div>");
                    if (isExist('count'))
                        totalsummaryArr.push("<div>Count=" + count + "</div>");
                    if (isExist('max'))
                        totalsummaryArr.push("<div>Max=" + max.toFixed(2) + "</div>");
                    if (isExist('min'))
                        totalsummaryArr.push("<div>Min=" + min.toFixed(2) + "</div>");
                    if (isExist('avg'))
                        totalsummaryArr.push("<div>Avg=" + avg.toFixed(2) + "</div>");
                }
            }
            return totalsummaryArr.join('');
        },
        _getTotalSummaryHtml: function (data, classCssName, frozen)
        {
            var g = this, p = this.options;
            var totalsummaryArr = [];
            if (classCssName)
                totalsummaryArr.push('<tr class="l-grid-totalsummary ' + classCssName + '">');
            else
                totalsummaryArr.push('<tr class="l-grid-totalsummary">');
            $(g.columns).each(function (columnindex, column)
            {
                if (this.frozen != frozen) return;
                //如果是行序号(系统列)
                if (this.isrownumber)
                {
                    totalsummaryArr.push('<td class="l-grid-totalsummary-cell l-grid-totalsummary-cell-rownumbers" style="width:' + this.width + 'px"><div>&nbsp;</div></td>');
                    return;
                }
                //如果是复选框(系统列)
                if (this.ischeckbox)
                {
                    totalsummaryArr.push('<td class="l-grid-totalsummary-cell l-grid-totalsummary-cell-checkbox" style="width:' + this.width + 'px"><div>&nbsp;</div></td>');
                    return;
                }
                    //如果是明细列(系统列)
                else if (this.isdetail)
                {
                    totalsummaryArr.push('<td class="l-grid-totalsummary-cell l-grid-totalsummary-cell-detail" style="width:' + this.width + 'px"><div>&nbsp;</div></td>');
                    return;
                }
                totalsummaryArr.push('<td class="l-grid-totalsummary-cell');
                if (this.islast)
                    totalsummaryArr.push(" l-grid-totalsummary-cell-last");
                totalsummaryArr.push('" ');
                totalsummaryArr.push('id="' + g.id + "|total" + g.totalNumber + "|" + column.__id + '" ');
                totalsummaryArr.push('width="' + this._width + '" ');
                columnname = this.columnname;
                if (columnname)
                {
                    totalsummaryArr.push('columnname="' + columnname + '" ');
                }
                totalsummaryArr.push('columnindex="' + columnindex + '" ');
                totalsummaryArr.push('><div class="l-grid-totalsummary-cell-inner"');
                if (column.align)
                    totalsummaryArr.push(' style="text-Align:' + column.align + ';"');
                totalsummaryArr.push('>');
                totalsummaryArr.push(g._getTotalCellContent(column, data));
                totalsummaryArr.push('</div></td>');
            });
            totalsummaryArr.push('</tr>');
            if (!frozen) g.totalNumber++;
            return totalsummaryArr.join('');
        },
        _bulidTotalSummary: function (frozen)
        {
            var g = this, p = this.options;
            if (!g.isTotalSummary()) return false;
            if (!g.currentData || g.currentData[p.root].length == 0) return false;
            var totalRow = $(g._getTotalSummaryHtml(g.currentData[p.root], null, frozen));
            $("tbody:first", frozen ? g.f.gridbody : g.gridbody).append(totalRow);
        },
        _buildPager: function ()
        {
            var g = this, p = this.options;
            $('.pcontrol input', g.toolbar).val(p.page);
            if (!p.pageCount) p.pageCount = 1;
            $('.pcontrol span', g.toolbar).html(p.pageCount);
            var r1 = parseInt((p.page - 1) * p.pageSize) + 1.0;
            var r2 = parseInt(r1) + parseInt(p.pageSize) - 1;
            if (!p.total) p.total = 0;
            if (p.total < r2) r2 = p.total;
            if (!p.total) r1 = r2 = 0;
            if (r1 < 0) r1 = 0;
            if (r2 < 0) r2 = 0;
            var stat = p.pageStatMessage;
            if(p.pageBarType=='0') stat = '加载更多，共 {total} 条 ';
            stat = stat.replace(/{from}/, r1);
            stat = stat.replace(/{to}/, r2);
            stat = stat.replace(/{total}/, p.total);
            stat = stat.replace(/{pagesize}/, p.pageSize);
            $('.l-bar-text', g.toolbar).html(stat);
            if (!p.total)
            {
            	if(p.pageBarType=='0'){
                    $(".l-bar-btnfirst span,.l-bar-btnprev span,.sundun-grid-loadmore span,.l-bar-btnlast span", g.toolbar)
                    .addClass("l-disabled");
            	}else if(p.pageBarType=='1'){
                    $(".l-bar-btnfirst span,.l-bar-btnprev span,.l-bar-btnnext span,.l-bar-btnlast span", g.toolbar)
                    .addClass("l-disabled");
            	}
            }
            if (p.page == 1)
            {
                $(".l-bar-btnfirst span", g.toolbar).addClass("l-disabled");
                $(".l-bar-btnprev span", g.toolbar).addClass("l-disabled");
            }
            else if (p.page > p.pageCount && p.pageCount > 0)
            {
                $(".l-bar-btnfirst span", g.toolbar).removeClass("l-disabled");
                $(".l-bar-btnprev span", g.toolbar).removeClass("l-disabled");
            }
            if (p.page == p.pageCount)
            {
                $(".l-bar-btnlast span", g.toolbar).addClass("l-disabled");
                if(p.pageBarType=='0'){
                	$(".sundun-grid-loadmore span", g.toolbar).addClass("l-disabled");
                }else if(p.pageBarType=='1'){
                	$(".l-bar-btnnext span", g.toolbar).addClass("l-disabled");
                }
            }
            else if (p.page < p.pageCount && p.pageCount > 0)
            {
                $(".l-bar-btnlast span", g.toolbar).removeClass("l-disabled");
                if(p.pageBarType=='0'){
                	$(".sundun-grid-loadmore span", g.toolbar).removeClass("l-disabled");
                }else if(p.pageBarType=='1'){
                	$(".l-bar-btnnext span", g.toolbar).removeClass("l-disabled");
                }
            }
        },
        _getRowIdByDomId: function (domid)
        {
            var ids = domid.split('|');
            var rowid = ids[2];
            return rowid;
        },
        _getRowByDomId: function (domid)
        {
            return this.records[this._getRowIdByDomId(domid)];
        },
        //在外部点击的时候，判断是否在编辑状态，比如弹出的层点击的，如果自定义了编辑器，而且生成的层没有包括在grid内部，建议重载
        _isEditing: function (jobjs)
        {
            return jobjs.hasClass("l-box-dateeditor") || jobjs.hasClass("l-box-select");
        },
        _getSrcElementByEvent: function (e)
        {
            var g = this,p=this.options;
            var obj = (e.target || e.srcElement);
            var jobjs = $(obj).parents().add(obj);
            var fn = function (parm)
            {
                for (var i = 0, l = jobjs.length; i < l; i++)
                {
                    if (typeof parm == "string")
                    {
                        if ($(jobjs[i]).hasClass(parm)) return jobjs[i];
                    }
                    else if (typeof parm == "object")
                    {
                        if (jobjs[i] == parm) return jobjs[i];
                    }
                }
                return null;
            };
            if (fn("l-grid-editor")) return { editing: true, editor: fn("l-grid-editor") };
            if (jobjs.index(this.element) == -1)
            {
                if (g._isEditing(jobjs)) return { editing: true };
                else return { out: true };
            }
            var indetail = false;
            if (jobjs.hasClass("l-grid-detailpanel") && g.detailrows)
            {
                for (var i = 0, l = g.detailrows.length; i < l; i++)
                {
                    if (jobjs.index(g.detailrows[i]) != -1)
                    {
                        indetail = true;
                        break;
                    }
                }
            }
            var r = {
                grid: fn("l-panel"),
                indetail: indetail,
                header: fn("l-panel-header"), //标题
                gridheader: fn("grid-head"), //表格头 
                gridbody: fn("grid-body"),
                total: fn("l-panel-bar-total"), //总汇总 
                popup: fn("l-grid-popup"),
                toolbar: fn("sundun-panel-bar")
            };
            if(p.pageBarType=='1') r.toolbar = fn("l-panel-bar");
            if (r.gridheader)
            {
                r.hrow = fn("sundun-grid-header-column");
                r.hcell = fn("l-grid-hd-cell");
                r.hcelltext = fn("l-grid-hd-cell-text");
                r.checkboxall = fn("l-grid-hd-cell-checkbox");
                if (r.hcell)
                {
                    var columnid = r.hcell.id.split('|')[2];
                    r.column = g._columns[columnid];
                }
            }
            if (r.gridbody)
            {
                r.row = fn("l-grid-row");
                r.cell = fn("l-grid-row-cell");
                r.checkbox = fn("l-grid-row-cell-btn-checkbox");
                r.groupbtn = fn("l-grid-group-togglebtn");
                r.grouprow = fn("l-grid-grouprow");
                r.detailbtn = fn("l-grid-row-cell-detailbtn");
                r.detailrow = fn("l-grid-detailpanel");
                r.totalrow = fn("l-grid-totalsummary");
                r.totalcell = fn("l-grid-totalsummary-cell");
                r.rownumberscell = $(r.cell).hasClass("l-grid-row-cell-rownumbers") ? r.cell : null;
                r.detailcell = $(r.cell).hasClass("l-grid-row-cell-detail") ? r.cell : null;
                r.checkboxcell = $(r.cell).hasClass("l-grid-row-cell-checkbox") ? r.cell : null;
                r.treelink = fn("l-grid-tree-link");
                r.editor = fn("l-grid-editor");
                if (r.row) r.data = this._getRowByDomId(r.row.id);
                if (r.cell) r.editing = $(r.cell).hasClass("l-grid-row-cell-editing");
                if (r.editor) r.editing = true;
                if (r.editing) r.out = false;
            }
            if (r.toolbar)
            {
                r.first = fn("l-bar-btnfirst");
                r.last = fn("l-bar-btnlast");
                if(p.pageBarType=='0'){
                	r.next = fn("sundun-grid-loadmore");
                }else if(p.pageBarType=='1'){
                	r.next = fn("l-bar-btnnext");
                }
                r.prev = fn("l-bar-btnprev");
                r.load = fn("l-bar-btnload");
                r.button = fn("l-bar-button");
            }

            return r;
        },
        _setEvent: function ()
        {
            var g = this, p = this.options;
            g.grid.bind("mousedown.grid", function (e)
            {
                g._onMouseDown.call(g, e);
            });
            g.grid.bind("dblclick.grid", function (e)
            {
                g._onDblClick.call(g, e);
            });
            g.grid.bind("contextmenu.grid", function (e)
            {
                return g._onContextmenu.call(g, e);
            });
            $(document).bind("mouseup.grid", function (e)
            {
                g._onMouseUp.call(g, e);
            });
            $(document).bind("click.grid", function (e)
            {
                g._onClick.call(g, e);
            });
            $(window).bind("resize.grid", function (e)
            {

            });
            $(document).bind("keydown.grid", function (e)
            {
                if (e.ctrlKey) g.ctrlKey = true;
            });
            $(document).bind("keyup.grid", function (e)
            {
                delete g.ctrlKey;
            });
            //表体 - 滚动联动事件 
            g.gridbody.bind('scroll.grid', function ()
            {
                var scrollLeft = g.gridbody.scrollLeft();
                var scrollTop = g.gridbody.scrollTop();
                
                if (scrollLeft != null)
                    g.gridheader[0].scrollLeft = scrollLeft;
                if (scrollTop != null)
                	g.f.gridbody[0].scrollTop = scrollTop;
                g.endEdit();
                g.trigger('SysGridHeightChanged');
            });
            
            //表体 - 滚动联动事件 
            g.table.bind('sundun.scroll.grid', function ()
            {
            	if(p.isScroll){
	                var scrollLeft = g.table.scrollLeft();
	                var currScrollTop = g.table.scrollTop();
	                
	                if (scrollLeft != null)
	                	g.table.scrollLeft(scrollLeft);
	                if (currScrollTop != null)
	                	g.table.scrollTop(currScrollTop);
            	}else{
            		$(window).scrollTop($(window).height()+$(window).scrollTop());
            	}
            });
            
            //工具条 - 切换每页记录数事件
            $('select', g.toolbar).change(function ()
            {
                if (g.isDataChanged && p.dataAction != "local" && !confirm(p.isContinueByDataChanged))
                    return false;
                p.newPage = 1;
                p.pageSize = this.value;
                g.loadData(p.where);
            });
            //工具条 - 切换当前页事件
            $('span.pcontrol :text', g.toolbar).blur(function (e)
            {
                g.changePage('input');
            });
			$('span.pcontrol :text', g.toolbar).keyup(function (e)
            {
				if(e.keyCode == 13){
                	g.changePage('input');
				};
            });
            $("div.l-bar-button", g.toolbar).hover(function ()
            {
                $(this).addClass("l-bar-button-over");
            }, function ()
            {
                $(this).removeClass("l-bar-button-over");
            });
            //列拖拽支持
            if ($.fn.ligerDrag && p.colDraggable)
            {
                g.colDroptip = $("<div class='l-drag-coldroptip' style='display:none'><div class='l-drop-move-up'></div><div class='l-drop-move-down'></div></div>").appendTo('body');
                g.gridheader.ligerDrag({
                    revert: true, animate: false,
                    proxyX: 0, proxyY: 0,
                    proxy: function (draggable, e)
                    {
                        var src = g._getSrcElementByEvent(e);
                        if (src.hcell && src.column)
                        {
                            var content = $(".l-grid-hd-cell-text:first", src.hcell).html();
                            var proxy = $("<div class='l-drag-proxy' style='display:none'><div class='l-drop-icon l-drop-no'></div></div>").appendTo('body');
                            proxy.append(content);
                            return proxy;
                        }
                    },
                    onRevert: function () { return false; },
                    onRendered: function ()
                    {
                        this.set('cursor', 'default');
                        g.children[this.id] = this;
                    },
                    onStartDrag: function (current, e)
                    {
                        if (e.button == 2) return false;
                        if (g.colresizing) return false;
                        this.set('cursor', 'default');
                        var src = g._getSrcElementByEvent(e);
                        if (!src.hcell || !src.column || src.column.issystem || src.hcelltext) return false;
                        if ($(src.hcell).css('cursor').indexOf('resize') != -1) return false;
                        this.draggingColumn = src.column;
                        g.coldragging = true;

                        var gridOffset = g.grid.offset();
                        this.validRange = {
                            top: gridOffset.top,
                            bottom: gridOffset.top + g.gridheader.height(),
                            left: gridOffset.left - 10,
                            right: gridOffset.left + g.grid.width() + 10
                        };
                    },
                    onDrag: function (current, e)
                    {
                        this.set('cursor', 'default');
                        var column = this.draggingColumn;
                        if (!column) return false;
                        if (g.colresizing) return false;
                        if (g.colDropIn == null)
                            g.colDropIn = -1;
                        var pageX = e.pageX;
                        var pageY = e.pageY;
                        var visit = false;
                        var gridOffset = g.grid.offset();
                        var validRange = this.validRange;
                        if (pageX < validRange.left || pageX > validRange.right
                            || pageY > validRange.bottom || pageY < validRange.top)
                        {
                            g.colDropIn = -1;
                            g.colDroptip.hide();
                            this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes").addClass("l-drop-no");
                            return;
                        }
                        for (var colid in g._columns)
                        {
                            var col = g._columns[colid];
                            if (column == col)
                            {
                                visit = true;
                                continue;
                            }
                            if (col.issystem) continue;
                            var sameLevel = col['__level'] == column['__level'];
                            var isAfter = !sameLevel ? false : visit ? true : false;
                            if (column.frozen != col.frozen) isAfter = col.frozen ? false : true;
                            if (g.colDropIn != -1 && g.colDropIn != colid) continue;
                            var cell = document.getElementById(col['__domid']);
                            var offset = $(cell).offset();
                            var range = {
                                top: offset.top,
                                bottom: offset.top + $(cell).height(),
                                left: offset.left - 10,
                                right: offset.left + 10
                            };
                            if (isAfter)
                            {
                                var cellwidth = $(cell).width();
                                range.left += cellwidth;
                                range.right += cellwidth;
                            }
                            if (pageX > range.left && pageX < range.right && pageY > range.top && pageY < range.bottom)
                            {
                                var height = p.headerRowHeight;
                                if (col['__rowSpan']) height *= col['__rowSpan'];
                                g.colDroptip.css({
                                    left: range.left + 5,
                                    top: range.top - 9,
                                    height: height + 9 * 2
                                }).show();
                                g.colDropIn = colid;
                                g.colDropDir = isAfter ? "right" : "left";
                                this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no").addClass("l-drop-yes");
                                break;
                            }
                            else if (g.colDropIn != -1)
                            {
                                g.colDropIn = -1;
                                g.colDroptip.hide();
                                this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes").addClass("l-drop-no");
                            }
                        }
                    },
                    onStopDrag: function (current, e)
                    {
                        var column = this.draggingColumn;
                        g.coldragging = false;
                        if (g.colDropIn != -1)
                        {
                            g.changeCol.ligerDefer(g, 0, [column, g.colDropIn, g.colDropDir == "right"]);
                            g.colDropIn = -1;
                        }
                        g.colDroptip.hide();
                        this.set('cursor', 'default');
                    }
                });
            }
            //行拖拽支持
            if ($.fn.ligerDrag && p.rowDraggable)
            {
                g.rowDroptip = $("<div class='l-drag-rowdroptip' style='display:none'></div>").appendTo('body');
                g.gridbody.ligerDrag({
                    revert: true, animate: false,
                    proxyX: 0, proxyY: 0,
                    proxy: function (draggable, e)
                    {
                        var src = g._getSrcElementByEvent(e);
                        if (src.row)
                        {
                            var content = p.draggingMessage.replace(/{count}/, draggable.draggingRows ? draggable.draggingRows.length : 1);
                            if (p.rowDraggingRender)
                            {
                                content = p.rowDraggingRender(draggable.draggingRows, draggable, g);
                            }
                            var proxy = $("<div class='l-drag-proxy' style='display:none'><div class='l-drop-icon l-drop-no'></div>" + content + "</div>").appendTo('body');
                            return proxy;
                        }
                    },
                    onRevert: function () { return false; },
                    onRendered: function ()
                    {
                        this.set('cursor', 'default');
                        g.children[this.id] = this;
                    },
                    onStartDrag: function (current, e)
                    {
                        if (e.button == 2) return false;
                        if (g.colresizing) return false;
                        if (!g.columns.length) return false;
                        this.set('cursor', 'default');
                        var src = g._getSrcElementByEvent(e);
                        if (!src.cell || !src.data || src.checkbox) return false;
                        var ids = src.cell.id.split('|');
                        var column = g._columns[ids[ids.length - 1]];
                        if (src.rownumberscell || src.detailcell || src.checkboxcell || column == g.columns[0])
                        {
                            if (g.enabledCheckbox())
                            {
                                this.draggingRows = g.getSelecteds();
                                if (!this.draggingRows || !this.draggingRows.length) return false;
                            }
                            else
                            {
                                this.draggingRows = [src.data];
                            }
                            this.draggingRow = src.data;
                            this.set('cursor', 'move');
                            g.rowdragging = true;
                            this.validRange = {
                                top: g.gridbody.offset().top,
                                bottom: g.gridbody.offset().top + g.gridbody.height(),
                                left: g.grid.offset().left - 10,
                                right: g.grid.offset().left + g.grid.width() + 10
                            };
                        }
                        else
                        {
                            return false;
                        }
                    },
                    onDrag: function (current, e)
                    {
                        var rowdata = this.draggingRow;
                        if (!rowdata) return false;
                        var rows = this.draggingRows ? this.draggingRows : [rowdata];
                        if (g.colresizing) return false;
                        if (g.rowDropIn == null) g.rowDropIn = -1;
                        var pageX = e.pageX;
                        var pageY = e.pageY;
                        var visit = false;
                        var validRange = this.validRange;
                        if (pageX < validRange.left || pageX > validRange.right
                            || pageY > validRange.bottom || pageY < validRange.top)
                        {
                            g.rowDropIn = -1;
                            g.rowDroptip.hide();
                            this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes l-drop-add").addClass("l-drop-no");
                            return;
                        }
                        for (var i in g.rows)
                        {
                            var rd = g.rows[i];
                            var rowid = rd['__id'];
                            if (rowdata == rd) visit = true;
                            if ($.inArray(rd, rows) != -1) continue;
                            var isAfter = visit ? true : false;
                            if (g.rowDropIn != -1 && g.rowDropIn != rowid) continue;
                            var rowobj = g.getRowObj(rowid);
                            var offset = $(rowobj).offset();
                            var range = {
                                top: offset.top - 4,
                                bottom: offset.top + $(rowobj).height() + 4,
                                left: g.grid.offset().left,
                                right: g.grid.offset().left + g.grid.width()
                            };
                            if (pageX > range.left && pageX < range.right && pageY > range.top && pageY < range.bottom)
                            {
                                var lineTop = offset.top;
                                if (isAfter) lineTop += $(rowobj).height();
                                g.rowDroptip.css({
                                    left: range.left,
                                    top: lineTop,
                                    width: range.right - range.left
                                }).show();
                                g.rowDropIn = rowid;
                                g.rowDropDir = isAfter ? "bottom" : "top";
                                if (p.tree && pageY > range.top + 5 && pageY < range.bottom - 5)
                                {
                                    this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no l-drop-yes").addClass("l-drop-add");
                                    g.rowDroptip.hide();
                                    g.rowDropInParent = true;
                                }
                                else
                                {
                                    this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no l-drop-add").addClass("l-drop-yes");
                                    g.rowDroptip.show();
                                    g.rowDropInParent = false;
                                }
                                break;
                            }
                            else if (g.rowDropIn != -1)
                            {
                                g.rowDropIn = -1;
                                g.rowDropInParent = false;
                                g.rowDroptip.hide();
                                this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes  l-drop-add").addClass("l-drop-no");
                            }
                        }
                    },
                    onStopDrag: function (current, e)
                    {
                        var rows = this.draggingRows;
                        g.rowdragging = false;
                        for (var i = 0; i < rows.length; i++)
                        {
                            var children = rows[i].children;
                            if (children)
                            {
                                rows = $.grep(rows, function (node, i)
                                {
                                    var isIn = $.inArray(node, children) == -1;
                                    return isIn;
                                });
                            }
                        }
                        if (g.rowDropIn != -1)
                        {
                            if (p.tree)
                            {
                                var neardata, prow;
                                if (g.rowDropInParent)
                                {
                                    prow = g.getRow(g.rowDropIn);
                                }
                                else
                                {
                                    neardata = g.getRow(g.rowDropIn);
                                    prow = g.getParent(neardata);
                                }
                                g.appendRange(rows, prow, neardata, g.rowDropDir != "bottom");
                                g.trigger('rowDragDrop', {
                                    rows: rows,
                                    parent: prow,
                                    near: neardata,
                                    after: g.rowDropDir == "bottom"
                                });
                            }
                            else
                            {
                                g.moveRange(rows, g.rowDropIn, g.rowDropDir == "bottom");
                                g.trigger('rowDragDrop', {
                                    rows: rows,
                                    parent: prow,
                                    near: g.getRow(g.rowDropIn),
                                    after: g.rowDropDir == "bottom"
                                });
                            }

                            g.rowDropIn = -1;
                        }
                        g.rowDroptip.hide();
                        this.set('cursor', 'default');
                    }
                });
            }
        },
        _onRowOver: function (rowParm, over)
        {
            if (l.draggable.dragging) return;
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            var methodName = over ? "addClass" : "removeClass";
            if (g.enabledFrozen())
                $(g.getRowObj(rowdata, true))[methodName](p.mouseoverRowCssClass);
            $(g.getRowObj(rowdata, false))[methodName](p.mouseoverRowCssClass);
        },
        _onMouseUp: function (e)
        {
            var g = this, p = this.options;
            if (l.draggable.dragging)
            {
                var src = g._getSrcElementByEvent(e);

                //drop in header cell
                if (src.hcell && src.column)
                {
                    g.trigger('dragdrop', [{ type: 'header', column: src.column, cell: src.hcell }, e]);
                }
                else if (src.row)
                {
                    g.trigger('dragdrop', [{ type: 'row', record: src.data, row: src.row }, e]);
                }
            }
        },
        _onMouseDown: function (e)
        {
            var g = this, p = this.options;
        },
        _onContextmenu: function (e)
        {
            var g = this, p = this.options;
            var src = g._getSrcElementByEvent(e);
            if (src.row)
            {
                if (p.whenRClickToSelect)
                    g.select(src.data);
                if (g.hasBind('contextmenu'))
                {
                    return g.trigger('contextmenu', [{ data: src.data, rowindex: src.data['__index'], row: src.row }, e]);
                }
            }
            else if (src.hcell)
            {
                if (!p.allowHideColumn) return true;
                var columnindex = $(src.hcell).attr("columnindex");
                if (columnindex == undefined) return true;
                var left = (e.pageX - g.body.offset().left + parseInt(g.body[0].scrollLeft));
                if (columnindex == g.columns.length - 1) left -= 50;
                g.popup.css({ left: left, top: g.gridheader.height() + 1 });
                g.popup.toggle();
                return false;
            }
        },
        _onDblClick: function (e)
        {
            var g = this, p = this.options;
            var src = g._getSrcElementByEvent(e);
			if(!src["data"]){
				return false;
			};
            if (src.row)
            {
                g.trigger('dblClickRow', [src.data, src.data['__id'], src.row]);
            }
        },
        _onClick: function (e)
        {
			
            var obj = (e.target || e.srcElement);
            var g = this, p = this.options;
            var src = g._getSrcElementByEvent(e);
			
			
            if (src.out)
            {
                if (g.editor.editing && !$.ligerui.win.masking) g.endEdit();
                if (p.allowHideColumn) g.popup.hide();
                return;
            }
            if (src.indetail || src.editing)
            {
                return;
            }
            if (g.editor.editing)
            {
                g.endEdit();
            }
            if (p.allowHideColumn)
            {
                if (!src.popup)
                {
                    g.popup.hide();
                }
            }
            if (src.checkboxall) //复选框全选
            {
                var row = $(src.hrow);
 
                var uncheck = row.hasClass("l-checked");

                if (g.trigger('beforeCheckAllRow', [!uncheck, g.element]) == false) return false;
                if (uncheck)
                {
                	row.removeClass("l-checked");
                }
                else
                {
                	row.addClass("l-checked");
                }
                g.selected = [];
                for (var rowid in g.records)
                {
                    if (uncheck)
                        g.unselect(g.records[rowid]);
                    else
                        g.select(g.records[rowid]);
                }

                g.trigger('checkAllRow', [!uncheck, g.element]);
            }
            else if (src.hcelltext) //排序
            {
                var hcell = $(src.hcelltext).parent().parent();
                if (!p.enabledSort || !src.column) return;
                if (src.column.isSort == false) return;
                if (p.url && p.dataAction != "local" && g.isDataChanged && !confirm(p.isContinueByDataChanged)) return;
                var sort = $(".l-grid-hd-cell-sort:first", hcell);
                var columnName = src.column.name;
                if(src.column.isDic===true){
                	columnName = columnName.substring(0,columnName.length-4);
                }
                if (!columnName) return;
                if (sort.length > 0)
                {
                    if (sort.hasClass("l-grid-hd-cell-sort-asc"))
                    {
                        sort.removeClass("l-grid-hd-cell-sort-asc").addClass("l-grid-hd-cell-sort-desc");
                        hcell.removeClass("l-grid-hd-cell-asc").addClass("l-grid-hd-cell-desc");
                        g.changeSort(columnName, 'desc');
                    }
                    else if (sort.hasClass("l-grid-hd-cell-sort-desc"))
                    {
                        sort.removeClass("l-grid-hd-cell-sort-desc").addClass("l-grid-hd-cell-sort-asc");
                        hcell.removeClass("l-grid-hd-cell-desc").addClass("l-grid-hd-cell-asc");
                        g.changeSort(columnName, 'asc');
                    }
                }
                else
                {
                    hcell.removeClass("l-grid-hd-cell-desc").addClass("l-grid-hd-cell-asc");
                    $(src.hcelltext).after("<span class='l-grid-hd-cell-sort l-grid-hd-cell-sort-asc'>&nbsp;&nbsp;</span>");
                    g.changeSort(columnName, 'asc');
                }
                $(".l-grid-hd-cell-sort", g.gridheader).add($(".l-grid-hd-cell-sort", g.gridheader)).not($(".l-grid-hd-cell-sort:first", hcell)).remove();
            }
                //明细
            else if (src.detailbtn && p.detail)
            {
                var item = src.data;
                var row = $([g.getRowObj(item, false)]);
                var rowid = item['__id'];
                if ($(src.detailbtn).hasClass("l-open"))
                {
                	var nextrow = row.next("tr.l-grid-detailpanel");
                    if (p.detail.onCollapse)
                        p.detail.onCollapse(item, $(".l-grid-detailpanel-inner:first", nextrow)[0]);
                    row.next("tr.l-grid-detailpanel").hide();
                    $(src.detailbtn).removeClass("l-open");
                }
                else
                {
                    var nextrow = row.next("tr.l-grid-detailpanel");
                    if (nextrow.length > 0)
                    {
                        nextrow.show();
                        if (p.detail.onExtend)
                            p.detail.onExtend(item, $(".l-grid-detailpanel-inner:first", nextrow)[0]);
                        $(src.detailbtn).addClass("l-open");
                        g.trigger('SysGridHeightChanged');
                        return;
                    }
                    $(src.detailbtn).addClass("l-open");
                    var frozenColNum = 0;
                    var detailRow = $("<tr class='l-grid-detailpanel'><td><div class='l-grid-detailpanel-inner' style='display:none'></div></td></tr>");
                    var detailFrozenRow = $("<tr class='l-grid-detailpanel'><td><div class='l-grid-detailpanel-inner' style='display:none'></div></td></tr>");
                    detailRow.attr("id", g.id + "|detail|" + rowid);
                    g.detailrows = g.detailrows || [];
                    g.detailrows.push(detailRow[0]);

                    var detailRowInner = $("div:first", detailRow);
                    detailRowInner.parent().attr("colSpan", g.columns.length - frozenColNum);
                    row.eq(0).after(detailRow);

                    if (p.detail.onShowDetail)
                    {
                        p.detail.onShowDetail(item, detailRowInner[0], function ()
                        {
                        	
                            g.trigger('SysGridHeightChanged');
                        });
                        detailRowInner.height(p.detail.height || p.detailHeight);
                        detailRowInner.show();
                    }
                    else if (p.detail.render)
                    {
                        detailRowInner.append(p.detail.render());
                        detailRowInner.show();
                    }
                   
                }
                g.trigger('SysGridHeightChanged');
            }
            else if (src.groupbtn)
            {
                var grouprow = $(src.grouprow);
                var opening = true;
                if ($(src.groupbtn).hasClass("l-grid-group-togglebtn-close"))
                {
                    $(src.groupbtn).removeClass("l-grid-group-togglebtn-close");

                    if (grouprow.hasClass("l-grid-grouprow-last"))
                    {
                        $("td:first", grouprow).width('auto');
                    }
                }
                else
                {
                    opening = false;
                    $(src.groupbtn).addClass("l-grid-group-togglebtn-close");
                    if (grouprow.hasClass("l-grid-grouprow-last"))
                    {
                        $("td:first", grouprow).width(g.gridtablewidth);
                    }
                }
                var currentRow = grouprow.next(".l-grid-row,.l-grid-totalsummary-group,.l-grid-detailpanel");
                while (true)
                {
                    if (currentRow.length == 0) break;
                    if (opening)
                    {
                        currentRow.show();
                        //如果是明细展开的行，并且之前的状态已经是关闭的，隐藏之
                        if (currentRow.hasClass("l-grid-detailpanel") && !currentRow.prev().find("td.l-grid-row-cell-detail:first span.l-grid-row-cell-detailbtn:first").hasClass("l-open"))
                        {
                            currentRow.hide();
                        }
                    }
                    else
                    {
                        currentRow.hide();
                    }
                    currentRow = currentRow.next(".l-grid-row,.l-grid-totalsummary-group,.l-grid-detailpanel");
                }
                g.trigger(opening ? 'groupExtend' : 'groupCollapse');
                g.trigger('SysGridHeightChanged');
            }
                //树 - 伸展/收缩节点
            else if (src.treelink)
            {
                g.toggle(src.data);
            }
            else if (src.row && g.enabledCheckbox()) //复选框选择行
            {
                //复选框
                var selectRowButtonOnly = p.selectRowButtonOnly ? true : false;
                if (p.enabledEdit) selectRowButtonOnly = true;
                if (src.checkbox || !selectRowButtonOnly)
                {
                    var row = $(src.row);
                    var uncheck = row.hasClass("l-selected");
					if(!src["data"]){
						return false;
					};
                    if (g.trigger('beforeCheckRow', [!uncheck, src.data, src.data['__id'], src.row]) == false)
                        return false;
                    var met = uncheck ? 'unselect' : 'select';
                    g[met](src.data);
                    if (p.tree && p.autoCheckChildren)
                    {
                        var children = g.getChildren(src.data, true);
                        for (var i = 0, l = children.length; i < l; i++)
                        {
                            g[met](children[i]);
                        }
                    }
                    g.trigger('checkRow', [!uncheck, src.data, src.data['__id'], src.row]);
                }
                if (!src.checkbox && src.cell && p.enabledEdit && p.clickToEdit)
                {
                    g._applyEditor(src.cell);
                }
            }
            else if (src.row && !g.enabledCheckbox())
            {
                if (src.cell && p.enabledEdit && p.clickToEdit)
                {
                    g._applyEditor(src.cell);
                }

                //选择行
                if ($(src.row).hasClass("l-selected"))
                {
                    if (!p.allowUnSelectRow)
                    {
                        $(src.row).addClass("l-selected-again");
                        return;
                    }
                    g.unselect(src.data);
                }
                else
                {
                    g.select(src.data);
                }
            }
            else if (src.toolbar)
            {
                if (src.first)
                {
                    if (g.trigger('toFirst', [g.element]) == false) return false;
                    g.changePage('first');
                }
                else if (src.prev)
                {
                    if (g.trigger('toPrev', [g.element]) == false) return false;
                    g.changePage('prev');
                }
                else if (src.next)
                {
                    if (g.trigger('toNext', [g.element]) == false) return false;
                    g.changePage('next');
                }
                else if (src.last)
                {
                    if (g.trigger('toLast', [g.element]) == false) return false;
                    g.changePage('last');
                }
                else if (src.load)
                {
                    if ($("span", src.load).hasClass("l-disabled")) return false;
                    if (g.trigger('reload', [g.element]) == false) return false;
                    if (p.url && g.isDataChanged && !confirm(p.isContinueByDataChanged))
                        return false;
                    g.loadData(p.where);
                }
            }
        },
        select: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            var rowid = rowdata['__id'];
            var rowobj = g.getRowObj(rowid);
            var rowobj1 = g.getRowObj(rowid, true);
            if (!g.enabledCheckbox() && !g.ctrlKey) //单选
            {
                for (var i in g.selected)
                {
                    var o = g.selected[i];
                    if (o['__id'] in g.records)
                    {
                        $(g.getRowObj(o)).removeClass("l-selected l-selected-again");
                        if (g.enabledFrozen())
                            $(g.getRowObj(o, true)).removeClass("l-selected l-selected-again");
                    }
                }
                g.selected = [];
            }
            if (rowobj) $(rowobj).addClass("l-selected");
            if (rowobj1) $(rowobj1).addClass("l-selected");
            g.selected[g.selected.length] = rowdata;
            g.trigger('selectRow', [rowdata, rowid, rowobj]);
        },
        unselect: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            var rowid = rowdata['__id'];
            var rowobj = g.getRowObj(rowid);
            var rowobj1 = g.getRowObj(rowid, true);
            $(rowobj).removeClass("l-selected l-selected-again");
            if (g.enabledFrozen())
                $(rowobj1).removeClass("l-selected l-selected-again");
            g._removeSelected(rowdata);
            g.trigger('unSelectRow', [rowdata, rowid, rowobj]);
        },
        isSelected: function (rowParm)
        {
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            for (var i in g.selected)
            {
                if (g.selected[i] == rowdata) return true;
            }
            return false;
        },
        _onResize: function ()
        {
            var g = this, p = this.options;
            if (p.height && p.height != 'auto')
            {
                var windowHeight = $(window).height();
                
                var h = 0;
                var parentHeight = null;
                if (typeof (p.height) == "string" && p.height.indexOf('%') > 0)
                {
                    var gridparent = g.grid.parent();
                    if (p.inWindow)
                    {
                        parentHeight = windowHeight;
                        parentHeight -= parseInt($('body').css('paddingTop'));
                        parentHeight -= parseInt($('body').css('paddingBottom'));
                    }
                    else
                    {
                        parentHeight = gridparent.height();
                    }
                    h = parentHeight * parseFloat(p.height) * 0.01;
                    if (p.inWindow || gridparent[0].tagName.toLowerCase() == "body")
                        h -= (g.grid.offset().top - parseInt($('body').css('paddingTop')));
                }
                else
                {
                    h = parseInt(p.height);
                }

                h += p.heightDiff;
                g.windowHeight = windowHeight;
                g._setHeight(h);
            }  

            g.trigger('SysGridHeightChanged');
        },
        
        _sundunLoadData:function(loadDataParm){
            var g = this, p = this.options;
            g.loading = true; 
            g.trigger('loadData');
            var clause = null;
            var loadServer = true;
            if (typeof (loadDataParm) == "function")
            {
                clause = loadDataParm;
                loadServer = false;
            }
            else if (typeof (loadDataParm) == "boolean")
            {
                loadServer = loadDataParm;
            }
            else if (typeof (loadDataParm) == "object" && loadDataParm)
            {
                loadServer = false;
                p.dataType = "local";
                p.data = loadDataParm;
            }
            //参数初始化
            if (!p.newPage) p.newPage = 1;
            if (p.dataAction == "server")
            {
                if (!p.sortOrder) p.sortOrder = "asc";
            }
            var param = [];
            if (p.parms)
            {
                if (p.parms.length)
                {
                    $(p.parms).each(function ()
                    {
                        param.push({ name: this.name, value: this.value });
                    });
                }
                else if (typeof p.parms == "object")
                {
                    for (var name in p.parms)
                    {
                        param.push({ name: name, value: p.parms[name] });
                    }
                }
            }
            if (p.dataAction == "server")
            {
                if (p.usePager)
                {
                    param.push({ name: p.pageParmName, value: p.newPage });
                    param.push({ name: p.pagesizeParmName, value: p.pageSize });
                }
                if (p.sortName)
                {
                    param.push({ name: p.sortnameParmName, value: p.sortName });
                    param.push({ name: p.sortorderParmName, value: p.sortOrder });
                }
            };
            $(".l-bar-btnload span", g.toolbar).addClass("l-disabled");
            if (p.dataType == "local")
            {
                g.filteredData = g.data = p.data;
                if (clause){
                	g.filteredData[p.root] = g._searchData(g.filteredData[p.root], clause);
                }    
                if (p.usePager){
                	p.newPage=1;
                	
                	if(!g.currentData){
                		g.currentData = {};
                		g.currentData[p.root] = [];
                	}
                	
                	var dt = g._getCurrentPageData(g.filteredData);
                	g.currentData[p.record] = dt[p.record];
                	
                	$.each(dt[p.root],function(){
                		g.currentData[p.root].push(this);
                	});
                	
                }else{
                    g.currentData = g.filteredData;
                }
                g._showData();
            }
            else if (p.dataAction == "local" && !loadServer)
            {
                if (g.data && g.data[p.root])
                {
                    g.filteredData = g.data;
                    if (clause)
                        g.filteredData[p.root] = g._searchData(g.filteredData[p.root], clause);
                    
                	if(!g.currentData){
                		g.currentData = {};
                		g.currentData[p.root] = [];
                	}
                	
                	var dt = g._getCurrentPageData(g.filteredData);
                	g.currentData[p.record] = dt[p.record];
                	
                	$.each(dt[p.root],function(){
                		g.currentData[p.root].push(this);
                	});
                	
                    g._showData();
                }
            }
            else
            {
                g.loadServerDataSundun(param, clause);
                //g.loadServerData.ligerDefer(g, 10, [param, clause]);
            }
            g.loading = false;        	
        },
        
        loadServerDataSundun:function(param, clause){
            var g = this, p = this.options;
            var ajaxOptions = {
                type: p.method,
                url: p.url,
                data: param,
                async: p.async,
                dataType: 'json',
                beforeSend: function ()
                {
                    if (g.hasBind('loading'))
                    {
                        g.trigger('loading');
                    }
                    else
                    {
                        g.toggleLoading(true);
                    }
                },
                success: function (data)
                {
                    g.trigger('success', [data, g]);
                    if (!data || !data[p.root] || !data[p.root].length)
                    {
                        g.currentData = g.data = {};
                        g.currentData[p.root] = g.data[p.root] = [];
                        if (data && data[p.record])
                        {
                            g.currentData[p.record] = g.data[p.record] = data[p.record];
                        } else
                        {
                            g.currentData[p.record] = g.data[p.record] = 0;
                        }
                        g._showData();
                        
                        return;
                    }
                    
                    g.data = data;
                    
                	if(!g.currentData){
                		g.currentData = {};
                		g.currentData[p.root] = [];
                	}
                	
                    if (p.dataAction == "server")
                    {             	
                        g.currentData[p.record] = g.data[p.record];
                        
                        $(g.data[p.root]).each(function (){
                           g.currentData[p.root].push(this);
                        });                        
                    }
                    else
                    {
                        g.filteredData = g.data;
                        if (clause) g.filteredData[p.root] = g._searchData(g.filteredData[p.root], clause);
                        if (p.usePager){
                        	
                        	var dt = g._getCurrentPageData(g.filteredData);
                        	g.currentData[p.record] = dt[p.record];
                        	
                        	$.each(dt[p.root],function(){
                        		g.currentData[p.root].push(this);
                        	});

                        }else{
                            g.currentData = g.filteredData;
                        }
                    }
                    
                    g._showData.ligerDefer(g, 10, [g.currentData]);
                },
                complete: function ()
                {
                    g.trigger('complete', [g]);
                    if (g.hasBind('loaded'))
                    {
                        g.trigger('loaded', [g]);
                    }
                    else
                    {
                        g.toggleLoading.ligerDefer(g, 10, [false]);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown)
                {
                    g.currentData = g.data = {};
                    g.currentData[p.root] = g.data[p.root] = [];
                    g.currentData[p.record] = g.data[p.record] = 0;
                    g.toggleLoading.ligerDefer(g, 10, [false]);
                    $(".l-bar-btnload span", g.toolbar).removeClass("l-disabled");
                    g.trigger('error', [XMLHttpRequest, textStatus, errorThrown]);
                }
            };
            if (p.contentType) ajaxOptions.contentType = p.contentType;
            $.ajax(ajaxOptions);        	
        },
        
        refresh:function(){
            var g = this, p = this.options;
            p.newPage = 1;
            g.loadData(p.where);
        }
    });

    $.ligerui.controls.Grid.prototype.enabledTotal = $.ligerui.controls.Grid.prototype.isTotalSummary;
    $.ligerui.controls.Grid.prototype.add = $.ligerui.controls.Grid.prototype.addRow;
    $.ligerui.controls.Grid.prototype.update = $.ligerui.controls.Grid.prototype.updateRow;
    $.ligerui.controls.Grid.prototype.append = $.ligerui.controls.Grid.prototype.appendRow;
    $.ligerui.controls.Grid.prototype.getSelected = $.ligerui.controls.Grid.prototype.getSelectedRow;
    $.ligerui.controls.Grid.prototype.getSelecteds = $.ligerui.controls.Grid.prototype.getSelectedRows;
    $.ligerui.controls.Grid.prototype.getCheckedRows = $.ligerui.controls.Grid.prototype.getSelectedRows;
    $.ligerui.controls.Grid.prototype.getCheckedRowObjs = $.ligerui.controls.Grid.prototype.getSelectedRowObjs;
    $.ligerui.controls.Grid.prototype.setOptions = $.ligerui.controls.Grid.prototype.set;
    $.ligerui.controls.Grid.prototype.reload = $.ligerui.controls.Grid.prototype.loadData;
    $.ligerui.controls.Grid.prototype.refreshSize = $.ligerui.controls.Grid.prototype._onResize;

    function removeArrItem(arr, filterFn)
    {
        for (var i = arr.length - 1; i >= 0; i--)
        {
            if (filterFn(arr[i]))
            {
                arr.splice(i, 1);
            }
        }
    }
})(jQuery);

/**
* jQuery ligerUI 1.2.0
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 
*/
(function ($)
{
    $.fn.ligerFilter = function ()
    {
        return $.ligerui.run.call(this, "ligerFilter", arguments);
    };

    $.fn.ligerGetFilterManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetFilterManager", arguments);
    };

    $.ligerDefaults.Filter = {
        //字段列表
        fields: [],
        //字段类型 - 运算符 的对应关系
        operators: {},
        //自定义输入框(如下拉框、日期)
        editors: {}
    };
    $.ligerDefaults.FilterString = {
        strings: {
            "and": "并且",
            "or": "或者",
            "equal": "等于",
            "notequal": "不等于",
            "startwith": "以..开始",
            "endwith": "以..结束",
            "like": "相似",
            "greater": "大于",
            "greaterorequal": "大于或等于",
            "less": "小于",
            "lessorequal": "小于或等于",
            "in": "在...之内",
            "notin": "不在...之内",
            "addgroup": "",
            "addrule": "",
            "deletegroup": ""
        }
    };

    $.ligerDefaults.Filter.operators['string'] =
    $.ligerDefaults.Filter.operators['text'] =
    ["equal", "notequal", "startwith", "endwith", "like", "greater", "greaterorequal", "less", "lessorequal", "in", "notin"];

    $.ligerDefaults.Filter.operators['number'] =
    $.ligerDefaults.Filter.operators['int'] =
    $.ligerDefaults.Filter.operators['float'] =
    $.ligerDefaults.Filter.operators['date'] =
    ["equal", "notequal", "greater", "greaterorequal", "less", "lessorequal", "in", "notin"];

    $.ligerDefaults.Filter.editors['string'] =
    {
        create: function (container, field)
        {
            var input = $("<input type='text'/>");
            var attrs = getEditorStyle(field);
            if(attrs.length>0){
            	input.attr("ligerui",attrs);
            }
            container.append(input);
            input.ligerTextBox(field.editor.options || {});
            return input;
        },
        setValue: function (input, value)
        {
            input.val(processValue(value));
        },
        getValue: function (input)
        {
            return input.liger('option', 'value');
        },
        destroy: function (input)
        {
            input.liger('destroy');
        }
    };

    $.ligerDefaults.Filter.editors['date'] =
    {
        create: function (container, field)
        {
        	var htmlArr = [];
        	htmlArr.push("<input type='text' style='");
        	var width = "178";
        	if(field.width) width = field.width;
        	if((width.indexOf('px') > 0) || (width.indexOf('%') > 0)){
        		htmlArr.push("width:"+width+";");
        	}else{
        		htmlArr.push("width:"+width+"px;'");
        	}
        	htmlArr.push(" class='l-text Wdate'");
        	var format = "yyyy-MM-dd";
        	if(field.format && $.trim(field.format)!='') format = field.format;
        	htmlArr.push(" onfocus='WdatePicker({dateFmt:\""+format+"\"})'");
        	htmlArr.push(" />");
            var input = $(htmlArr.join(""));
            container.append(input);
            return input;
        },
        setValue: function (input, value)
        {
        	input.val(processValue(value));
        },
        getValue: function (input, field)
        {
            return input.val();
        },
        destroy: function (input)
        {
            input.remove();
        }    	
    };
    
    $.ligerDefaults.Filter.editors['date-between'] =
    {
        create: function (container, field)
        {
        	var time = new Date().getTime();
        	var range = null;
        	if(field.dateQueryRange){
        		range = eval("("+field.dateQueryRange+")");
        		var isJson = typeof(range) == "object" && Object.prototype.toString.call(range).toLowerCase() == "[object object]" && !range.length;
        		if(!isJson){
        			range = null;
        		}
        	}
        	
        	var htmlArr = [];
        	htmlArr.push("<div name='date-between'>");
        	htmlArr.push(getDateEditorHtml(field,"start",time,range));
        	htmlArr.push("<span class='date-end'>-");
        	htmlArr.push(getDateEditorHtml(field,"end",time,range));
        	htmlArr.push("</span>");
//        	htmlArr.push("&nbsp;<a name='changeBtn' href='javascript:void(0)'>切换</a>");
        	htmlArr.push("</div>");
        	
            var input = $(htmlArr.join(""));
            container.append(input);
            
//            $(".date-end",input).hide();
//            $("a[name='changeBtn']",input).bind('click',function(){
//            	if($(".date-end",input).is(':visible')){
//            		$(".date-end",input).hide();
//            	}else{
//            		$(".date-end",input).show();
//            	}
//            });
            
            return input;
        },
        setValue: function (input, value)
        {
        	var val = [];
        	if(typeof(value)=='string' && value != '' && value != '|') val = value.split('|');
        	if(val.length==0) return ;
        	$("input[name='start']",input).val(processValue(val[0]));
        	if(val.length > 1){
//        		if($(".date-end",input).is(':hidden')){
//        			$(".date-end",input).show();
//        		}
        		$("input[name='end']",input).val(processValue(val[1]));
        	}
        },
        getValue: function (input, field)
        {
        	var start = $("input[name='start']",input).val();
        	var end = $("input[name='end']",input).val();
//    		if($(".date-end",input).is(':hidden')){
//    			end = '';
//    		}
        	var val = start + "|" + end;
            return val;
        },
        destroy: function (input)
        {
            input.remove();
        }    	
    };    

    $.ligerDefaults.Filter.editors['number'] =
    {
        create: function (container, field)
        {
            var input = $("<input type='text'/>");
            var attrs = getEditorStyle(field);
            if(attrs.length>0){
            	input.attr("ligerui",attrs);
            }
            container.append(input);
            var options = {
                minValue: field.editor.minValue,
                maxValue: field.editor.maxValue
            };
            input.ligerSpinner($.extend(options, field.editor.options || {}));
            return input;
        },
        setValue: function (input, value)
        {
            input.val(processValue(value));
        },
        getValue: function (input, field)
        {
            var isInt = field.editor.type == "int";
            if (isInt)
                return parseInt(input.val(), 10);
            else
                return parseFloat(input.val());
        },
        destroy: function (input)
        {
            input.liger('destroy');
        }
    };
    
    $.ligerDefaults.Filter.editors['DataDictionary'] =
    {
        create: function (container, field)
        {
            var input = $("<input id='"+field.name+"Name' type='text'/>");
            container.append(input);
            
            var options = {
            		width:178,
            		isFixOptions:false,
            		hiddenField:field.name,
            		searchField:"name,code,scode,jpcode"
            };
            if(field.editor.options && field.editor.options.width){
            	options.width = field.editor.options.width;
            }
            
            if(field.dicType && field.dicInfo && field.dicInfo != ''){
            	//系统静态js字典、自定义字典
            	if(field.dicType=='1' || field.dicType=='2'){
	            	var source = null;
	            	//系统静态js字典
	            	if(field.dicType == '1'){
	                	try{
	                		source = eval("("+field.dicInfo+"())");
	                	}catch(e){
	                		alert("请检查是否正确引入所需要的字典JS，并确保引入的字典JS已生成文件");
	                	}
	            	}
	            	//自定义字典
	            	if(field.dicType == '2'){
	            		source = field.dicInfo;
	                	if(!$.isArray(source)){
	                		alert("请检查所配置的自定义字典项是否正确");
	                	}
	            	}
	            	if($.isArray(source)){
	            		options.data = {Rows:source,Total:source.length};
	            	}
            	}
            	
            	//系统动态字典
            	else if(field.dicType=='3'){
            		var dicName = field.dicInfo;
            		options.dataAction = 'server';
            		options.parms = {"dicName":dicName};
            		options.url = urlpath + "com/dic/getDynamicDicData";
            	}
            	
            	//url字典
            	else if(field.dicType=='4'){
            		var dicUrl = field.dicInfo;
            		if(dicUrl.indexOf("http")!=0) dicUrl = urlpath + dicUrl;
            		options.dataAction = 'server';
            		options.url = dicUrl;
            	}
            }
            
        	//值改变触发函数
        	if(field.dicValueChange && field.dicValueChange!=''){
        		var valueChangeHandle = null;
        		eval("valueChangeHandle="+field.dicValueChange);
        		if($.isFunction(valueChangeHandle)){
        			options["valueChangeHandle"]=valueChangeHandle;
        		}
        	}
        	
        	//字典过滤
        	var filterText = null;
        	//过滤函数
        	if(field.startWithFunc && field.startWithFunc!=''){
        		var startWithFunc = null;
        		eval("startWithFunc="+field.startWithFunc);
        		if($.isFunction(startWithFunc)){
        			filterText = startWithFunc();
        		}
        	}
        	if(filterText && filterText!=''){
        		if(field.dicType=='1' || field.dicType=='2'){
        			options.filterRegx = {code:filterText};
        		}else{
        			options.parms["startWith"] = filterText;
        		}
        	}
        	
            var dic = input.ligerDictionary(options);
            
            return input;
        },
        setValue: function (input, value)
        {
        	input.ligerGetDictionaryManager().setValue(processValue(value));
        },
        getValue: function (input, field)
        {
        	var dic = input.ligerGetDictionaryManager();
        	//如果搜索框为空则清空字典隐藏域的值
        	if($.trim(dic.searchField.val())=='') dic.clear();
        	return input.ligerGetDictionaryManager().getSelectedValues();
        },
        destroy: function (input)
        {
        	var dic = input.ligerGetDictionaryManager();
        	dic.searchField.remove();
        	dic.hiddenField.remove();
        	dic.resultContainer.remove();
        }
    };
    
    $.ligerDefaults.Filter.editors['PeopleSelector'] =
    {
        create: function (container, field)
        {
        	var obj = $("<div id='"+field.editor.id+"'></div>");
        	var nameField = $("<input type='text' style='height:19px;' readonly='readonly' id='nameField_"+field.editor.id+"'/>");
        	obj.append(nameField); 
        	var btn = $('<a class="link search" style="margin-left:3px;margin-top:3px;margin-bottom: 2px;height:18px;"  href="javascript:void(0)" id="btn_'+field.editor.id+'">选择人员</a>')
            obj.append(btn);
        	var valueField = $("<input type='hidden' id='valueField_"+field.editor.id+"' />");
        	obj.append(valueField);
        	btn.bind('click',function(){
//        		UserDialog({
//        			isSingle: true,
//        			idField: field.editor.valueField,
//        			ids:$("#valueField_"+field.editor.id).val(),
//        			callback: function(ids, names) {
//        				$("#nameField_"+field.editor.id).val(names);
//        				$("#valueField_"+field.editor.id).val(ids);
//        			}
//        		});
        		$.ryxz.show({fhzd:[field.editor.valueField,"name"],jszd:["valueField_"+field.editor.id,"nameField_"+field.editor.id],numOper:"="})
        	});
        	container.append(obj);
            return obj;
        },
        setValue: function (obj, value)
        {
        	var id = obj.attr("id");
        	$("#valueField_"+id).val(processValue(value));
        },
        getValue: function (obj, field)
        {
        	var id = obj.attr("id");
        	return $("#valueField_"+id).val();
        },
        destroy: function (input)
        {
        	input.remove();
        }
    };
    
    $.ligerDefaults.Filter.editors['OrganizationSelector'] =
    {
        create: function (container, field)
        {
        	var obj = $("<div id='"+field.editor.id+"'></div>");
        	var nameField = $("<input type='text' style='height:19px;' readonly='readonly' id='nameField_"+field.editor.id+"'/>");
        	obj.append(nameField); 
        	var btn = $('<a class="link search" style="margin-left:3px;margin-top:3px;margin-bottom: 2px;height:18px;"  href="javascript:void(0)" id="btn_'+field.editor.id+'">选择机构</a>')
            obj.append(btn);
        	var valueField = $("<input type='hidden' id='valueField_"+field.editor.id+"' />");
        	obj.append(valueField);
        	btn.bind('click',function(){
        		OrgDialog({
        			isSingle: true,
        			idField: field.editor.valueField,
        			ids:$("#valueField_"+field.editor.id).val(),
        			callback: function(ids, names) {
        				$("#nameField_"+field.editor.id).val(names);
        				$("#valueField_"+field.editor.id).val(ids);
        			}
        		});
        	});
        	container.append(obj);
            return obj;
        },
        setValue: function (obj, value)
        {
        	var id = obj.attr("id");
        	$("#valueField_"+id).val(processValue(value));
        },
        getValue: function (obj, field)
        {
        	var id = obj.attr("id");
        	return $("#valueField_"+id).val();
        },
        destroy: function (input)
        {
        	input.remove();
        }
    };    
    
    $.ligerDefaults.Filter.editors['AyabSelector'] =
    {
        create: function (container, field)
        {
        	var obj = $("<div id='"+field.editor.id+"'></div>");
        	var nameField = $("<input type='text' style='height:19px;' id='nameField_"+field.editor.id+"'/>");
        	obj.append(nameField); 
        	var btn = $('<a class="link search" style="margin-left:3px;margin-top:3px;margin-bottom: 2px;height:18px;"  href="javascript:void(0)" id="btn_'+field.editor.id+'">选择</a>')
            obj.append(btn);
        	var options = {rfdm:'valueField_'+field.editor.id,rftext:'nameField_'+field.editor.id,isempty:true,single:true,sfhy:false,sfbcay:false};
        	if(field.editor.isCode===true){
        		nameField.attr("readonly","readonly");
	        	var valueField = $("<input type='hidden' id='valueField_"+field.editor.id+"' />");
	        	obj.append(valueField);
        	}else{
        		nameField.attr("id","valueField_"+field.editor.id);
        		options.rftext = options.rfdm;
        		options.rfdm = '';
        	}
        	btn.bind('click',function(){
        		$.ayab.show(options);
        	});
        	container.append(obj);
            return obj;
        },
        setValue: function (obj, value)
        {
        	var id = obj.attr("id");
        	$("#valueField_"+id).val(processValue(value));
        },
        getValue: function (obj, field)
        {
        	var id = obj.attr("id");
        	return $("#valueField_"+id).val();
        },
        destroy: function (input)
        {
        	input.remove();
        }
    };
    
    $.ligerDefaults.Filter.editors['combobox'] =
    {
        create: function (container, field)
        {
            var input = $("<input type='text'/>");
            var attrs = getEditorStyle(field);
            if(attrs.length>0){
            	input.attr("ligerui",attrs);
            }
            container.append(input);
            var options = {
                data: field.data,
                slide: false,
                valueField: field.editor.valueField || field.editor.valueColumnName,
                textField: field.editor.textField || field.editor.displayColumnName
            };
            $.extend(options, field.editor.options || {});
            input.ligerComboBox(options);
            return input;
        },
        setValue: function (input, value)
        {
            input.liger('option', 'value', processValue(value));
        },
        getValue: function (input)
        {
            return input.liger('option', 'value');
        },
        destroy: function (input)
        {
            input.liger('destroy');
        }
    };

    //过滤器组件
    $.ligerui.controls.Filter = function (element, options)
    {
        $.ligerui.controls.Filter.base.constructor.call(this, element, options);
    };

    $.ligerui.controls.Filter.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return 'Filter';
        },
        __idPrev: function ()
        {
            return 'Filter';
        },
        _init: function ()
        {
            $.ligerui.controls.Filter.base._init.call(this);
            
            var g=this,p =this.options;
            
            //处理每行展示控件数
            if(typeof(p.lineNum) == 'string' || typeof(p.lineNum) == 'number'){
            	p.lineNum = Number(p.lineNum);
            }else{
            	p.lineNum = 1;
            }
            //给没有操作符的默认都为like
            $.each(p.fields,function(){
            	if(!this.op){
            		this.op = 'like';
            	}
            });
        },
        _render: function ()
        {
            var g = this, p = this.options;
            g.set(p);
//            //事件：增加分组
//            $("#" + g.id + " .addgroup").live('click', function ()
//            {
//                var jtable = $(this).parent().parent().parent().parent();
//                g.addGroup(jtable);
//            });
//            //事件：删除分组
//            $("#" + g.id + " .deletegroup").live('click', function ()
//            {
//                var jtable = $(this).parent().parent().parent().parent();
//                g.deleteGroup(jtable);
//            });
//            //事件：增加条件
//            $("#" + g.id + " .addrule").live('click', function ()
//            {
//                var jtable = $(this).parent().parent().parent().parent();
//                g.addRule(jtable);
//            });
//            //事件：删除条件
//            $("#" + g.id + " .deleterole").live('click', function ()
//            {
//                var rulerow = $(this).parent().parent();
//                g.deleteRule(rulerow);
//            });
            
            //事件：增加分组
            $("#" + g.id).delegate('.addgroup','click', function ()
            {
                var jtable = $(this).parent().parent().parent().parent();
                g.addGroup(jtable);
            });
            //事件：删除分组
            $("#" + g.id).delegate('.deletegroup','click', function ()
            {
                var jtable = $(this).parent().parent().parent().parent();
                g.deleteGroup(jtable);
            });
            //事件：增加条件
            $("#" + g.id).delegate('.addrule','click', function ()
            {
                var jtable = $(this).parent().parent().parent().parent();
                g.addRule(jtable);
            });
            //事件：删除条件
            $("#" + g.id).delegate('.deleterole','click', function ()
            {
                var rulerow = $(this).parent().parent();
                g.deleteRule(rulerow);
            });            
            
            if(p.type=="1" || p.type=="2" || p.type=="3" || p.type=='4'){
            	$("#" + g.id + " .addrule").trigger("click");
            }
        },

        //设置字段列表
        _setFields: function (fields)
        {
            var g = this, p = this.options;
            if (g.group) g.group.remove();
            g.group = $(g._bulidGroupTableHtml()).appendTo(g.element);
        },

        //输入框列表
        editors: {},

        //输入框计算器
        editorCounter: 0,

        //增加分组
        //@parm [jgroup] jQuery对象(主分组的table dom元素)
        addGroup: function (jgroup)
        {
            var g = this, p = this.options;
            jgroup = $(jgroup || g.group);
            var lastrow = $(">tbody:first > tr:last", jgroup);
            var groupHtmlArr = [];
            groupHtmlArr.push('<tr class="l-filter-rowgroup"><td class="l-filter-cellgroup" colSpan="4">');
            var altering = !jgroup.hasClass("l-filter-group-alt");
            groupHtmlArr.push(g._bulidGroupTableHtml(altering, true));
            groupHtmlArr.push('</td></tr>');
            var row = $(groupHtmlArr.join(''));
            lastrow.before(row);
            return row.find("table:first");
        },

        //删除分组 
        deleteGroup: function (group)
        {
            var g = this, p = this.options;
            $("td.l-filter-value", group).each(function ()
            {
                var rulerow = $(this).parent();
                $("select.fieldsel", rulerow).unbind();
                g.removeEditor(rulerow);
            });
            $(group).parent().parent().remove();
        },


        //删除编辑器
        removeEditor: function (rulerow)
        {
            var g = this, p = this.options;
            var type = $(rulerow).attr("editortype");
            var id = $(rulerow).attr("editorid");
            var editor = g.editors[id];
            if (editor) p.editors[type].destroy(editor);
            $("td.l-filter-value:first", rulerow).html("");
        },

        //设置规则
        //@parm [group] 分组数据
        //@parm [jgruop] 分组table dom jQuery对象
        setData: function (group, jgroup)
        {
            var g = this, p = this.options;
            jgroup = jgroup || g.group;
            var lastrow = $(">tbody:first > tr:last", jgroup);
            var ary = jgroup.find(">tbody:first > tr");
            $.each(ary,function(i,tr){
            	if(tr != lastrow[0]){
            		$(tr).remove();
            	}
            });
            
            $("select:first", lastrow).val(group.op);
            
            if (group.rules)
            {
            	if(p.type=="2" || p.type=="3" || p.type=='4'){
	            	var rules = group.rules;
	            	var rulerow = g.addRule(jgroup);
	            	
	            	var fieldNameTd = $(".l-filter-column",rulerow);
	            	var opTd = $(".l-filter-op",rulerow);
	            	var valueTd = $(".l-filter-value",rulerow);
	            	if(group.rules.length>0){
		            	for(var i=0,l=fieldNameTd.length;i<l;i++){
		            		var rule = rules[i];
		            		if(!rule) continue;
		            		//设置操作符
		            		$(".opsel:first", opTd[i]).val(rule.op);
		            		//设置自定义控件的值
		            		var td = $(valueTd[i]);
		            		var editorid = td.attr("editorid");
                            if (editorid && g.editors[editorid])
                            {
                                var field = g.getField(rule.field);
                                if (field && field.editor)
                                {
                                    p.editors[field.editor.type].setValue(g.editors[editorid], rule.value, field);
                                }
                            }
                            //非自定义控件赋值
                            else
                            {
                            	$(":text",td).val(rule.value);
                            }
		            	}
	            	}
            	}else{
                    $(group.rules).each(function ()
                            {
                                var rulerow = g.addRule(jgroup);
                                rulerow.attr("fieldtype", this.type || "string");
                                $("select.opsel", rulerow).val(this.op);
                                $("select.fieldsel", rulerow).val(this.field).trigger('change');
                                var editorid = rulerow.attr("editorid");
                                if (editorid && g.editors[editorid])
                                {
                                    var field = g.getField(this.field);
                                    if (field && field.editor)
                                    {
                                        p.editors[field.editor.type].setValue(g.editors[editorid], this.value, field);
                                    }
                                }
                                else
                                {
                                    $(":text", rulerow).val(this.value);
                                }
                     });
            	}
            }
            if (group.groups)
            {
                $(group.groups).each(function ()
                {
                    var subjgroup = g.addGroup(jgroup);
                    g.setData(this, subjgroup);
                });
            }
        },

        //增加一个条件
        //@parm [jgroup] 分组的jQuery对象
        addRule: function (jgroup)
        {
            var g = this, p = this.options;
            jgroup = jgroup || g.group;
            var lastrow = $(">tbody:first > tr:last", jgroup);
            var rulerow = null;
            //简易查询
            if(p.type=='1'){
            	rulerow =$(g._bulidSimpleRuleHtml());            	
            }
            //普通查询p.type='2',高级查询p.type='3'
            else if(p.type=='2' || p.type=="3"){
            	rulerow =$(g._bulidHighLevelRuleHtml());            	
            }
            //高级自定义查询
            else if(p.type=="4"){
            	rulerow =$(g._bulidSimpleCustomRuleHtml());
            }
            //自定义查询
            else{
            	rulerow = $(g._bulidRuleRowHtml());
            }
            
            lastrow.before(rulerow);
            if (p.fields.length)
            {
                //如果第一个字段启用了自定义输入框
            	//if(p.type=='2' || p.type=="3"){ 2014-03-21 dengshaoxiang
            	if(p.type=='2' || p.type=="3" || p.type=="4"){
	            	var valueTd = $(".l-filter-value",rulerow); 
	            	var fieldNameTd = $(".l-filter-column",rulerow);
	            	for(var i=0;i<valueTd.length;i++){
	            		var fieldName = $(fieldNameTd[i]).attr("column");
	            		var field = g.getField(fieldName);
	            		g.appendEditor($(valueTd[i]), field);
	            	}
            	}else{
            			g.appendEditor(rulerow, p.fields[0]); 
            	}
            }

            if(p.type!='2' && p.type!="3" && p.type!="4"){
	            //事件：字段列表改变时
	            $("select.fieldsel", rulerow).bind('change', function ()
	            {
	                var jopsel = $(this).parent().next().find("select:first");
	                var fieldName = $(this).val();
	                if (!fieldName) return;
	                var field = g.getField(fieldName);
	                //字段类型处理
	                var fieldType = field.type || "string";
	                var oldFieldtype = rulerow.attr("fieldtype");
	                if (fieldType != oldFieldtype)
	                {
	                    jopsel.html(g._bulidOpSelectOptionsHtml(fieldType));
	                    rulerow.attr("fieldtype", fieldType);
	                }
	                
	                if(p.type=='1'){
	                	jopsel.html(g._bulidOpOptionHtml(field));
	                }
	                
	                //当前的编辑器
	                var editorType = null;
	                //上一次的编辑器
	                var oldEditorType = rulerow.attr("editortype");
	                if (g.enabledEditor(field)) editorType = field.editor.type;
	                if (oldEditorType)
	                {
	                    //如果存在旧的输入框 
	                    g.removeEditor(rulerow);
	                }
	                if (editorType)
	                {
	                    //如果当前选择的字段定义了输入框
	                    g.appendEditor(rulerow, field);
	                } else
	                {
	                    rulerow.removeAttr("editortype").removeAttr("editorid");
	                    $("td.l-filter-value:first", rulerow).html('<input type="text" class="filter_valtxt" />');                    
	                }
	            });
	            $("select.fieldsel",rulerow).ligerComboBox();
            }
            if(p.type!='2' && p.type!="3"){
            	$("select.opsel",rulerow).ligerComboBox();
            }
            
            return rulerow;
        },

        //删除一个条件
        deleteRule: function (rulerow)
        {
            $("select.fieldsel", rulerow).unbind();
            this.removeEditor(rulerow);
            $(rulerow).remove();
        },

        //附加一个输入框
        appendEditor: function (rulerow, field)
        {
            var g = this, p = this.options;
            if (g.enabledEditor(field))
            {
                var cell = $("td.l-filter-value:first", rulerow).html("");
                if(p.type=="2" || p.type=="3" || p.type=='4'){
            		cell = $(rulerow).html("");
            	}
                var editor = p.editors[field.editor.type];
                g.editors[++g.editorCounter] = editor.create(cell, field);
                rulerow.attr("editortype", field.editor.type).attr("editorid", g.editorCounter);
            }
        },

        //获取分组数据
        getData: function (group)
        {
            var g = this, p = this.options;
            group = group || g.group;
            var groupData = {};

            $("> tbody > tr", group).each(function (i, row)
            {
                var rowlast = $(row).hasClass("l-filter-rowlast");
                var rowgroup = $(row).hasClass("l-filter-rowgroup");
                if (rowgroup)
                {
                    var groupTable = $("> td:first > table:first", row);
                    if (groupTable.length)
                    {
                        if (!groupData.groups) groupData.groups = [];
                        groupData.groups.push(g.getData(groupTable));
                    }
                }
                else if (rowlast)
                {
                    groupData.op = $(".groupopsel:first", row).val();
                }
                else
                {
                	if(p.type=="2" || p.type=="3" || p.type=='4'){
	                	var lineNum = p.type;
	                	if(lineNum<1) lineNum = 1;
	                	
	                	var fieldNameTd = $(".l-filter-column",row);
	                	var opTd = $(".l-filter-op",row);
	                	var valueTd = $(".l-filter-value",row);
	                	
	                	for(var i=0,l=fieldNameTd.length;i<l;i++){
	                		var fieldName = $(fieldNameTd[i]).attr("column");
	                		var type = $(fieldNameTd[i]).attr("fieldtype") || "string";
	                		var field = g.getField(fieldName);
	                		var op = $(".opsel:first", opTd[i]).val();
	                		var value = g._getRuleValue(valueTd[i], field);
	                        if (!groupData.rules) groupData.rules = [];
	                        groupData.rules.push({
	                            field: fieldName, op: op, value: $.trim(value), type: type,format:field.format || ""
	                        });
	                	}
                	}else{
                        var fieldName = $("select.fieldsel:first", row).val();
                        var field = g.getField(fieldName);
                        var op = $(".opsel:first", row).val();
                        var value = g._getRuleValue(row, field);
                        var type = $(row).attr("fieldtype") || "string";
                        if (!groupData.rules) groupData.rules = [];
                        groupData.rules.push({
                            field: fieldName, op: op, value: $.trim(value), type: type,format:field.format || ""
                        });
                	}
                }
            });

            return groupData;
        },

        _getRuleValue: function (rulerow, field)
        {
            var g = this, p = this.options;
            var editorid = $(rulerow).attr("editorid");
            var editortype = $(rulerow).attr("editortype");
            var editor = g.editors[editorid];
            if (editor)
                return p.editors[editortype].getValue(editor, field);
            return $(".filter_valtxt:first", rulerow).val();
        },

        //判断某字段是否启用自定义的输入框  
        enabledEditor: function (field)
        {
            var g = this, p = this.options;
            if (!field.editor || !field.editor.type) return false;
            return (field.editor.type in p.editors);
        },

        //根据fieldName 获取 字段
        getField: function (fieldname)
        {
            var g = this, p = this.options;
            for (var i = 0, l = p.fields.length; i < l; i++)
            {
                var field = p.fields[i];
                if (field.name == fieldname) return field;
            }
            return null;
        },

        //获取一个分组的html
        _bulidGroupTableHtml: function (altering, allowDelete)
        {
            var g = this, p = this.options;
            var tableHtmlArr = [];
            if(Number(p.type)>0 && Number(p.type)<4){
            	if(p.type=='2' || p.type=='3'){
            		tableHtmlArr.push('<table cellpadding="0" cellspacing="0" border="0" width="100%" class="l-filter-group common_table');
            	}else{
            		tableHtmlArr.push('<table cellpadding="0" cellspacing="0" border="0" width="100%" class="l-filter-group');
            	}
            }else{
            	tableHtmlArr.push('<table cellpadding="0" cellspacing="0" border="0" align="center" width="auto" class="l-filter-group');
            }
            
            if (altering)
                tableHtmlArr.push(' l-filter-group-alt');
            tableHtmlArr.push('"><tbody>');
            
            if(p.type=="1" || p.type=="2" || p.type=="3" || p.type=='4'){
            	tableHtmlArr.push('<tr class="l-filter-rowlast" style="display:none"><td class="l-filter-rowlastcell" align="right" colSpan="4">');
            }else{
            	tableHtmlArr.push('<tr class="l-filter-rowlast"><td class="l-filter-rowlastcell" align="right" colSpan="4">');
            }
           
            //and or
            tableHtmlArr.push('<select class="groupopsel">');
            tableHtmlArr.push('<option value="and">' + p.strings['and'] + '</option>');
            tableHtmlArr.push('<option value="or">' + p.strings['or'] + '</option>');
            tableHtmlArr.push('</select>');

            //add group
            tableHtmlArr.push('<input type="button" value="' + p.strings['addgroup'] + '" class="addgroup">');
            //add rule
            tableHtmlArr.push('<input type="button" value="' + p.strings['addrule'] + '" class="addrule">');
            if (allowDelete)
                tableHtmlArr.push('<input type="button" value="' + p.strings['deletegroup'] + '" class="deletegroup">');

            tableHtmlArr.push('</td></tr>');

            tableHtmlArr.push('</tbody></table>');
            return tableHtmlArr.join('');
        },

        //获取字段值规则的html
        _bulidRuleRowHtml: function (fields)
        {
            var g = this, p = this.options;
            fields = fields || p.fields;
            var rowHtmlArr = [];
            var fieldType = fields[0].type || "string";
            rowHtmlArr.push('<tr fieldtype="' + fieldType + '"><td class="l-filter-column">');
            rowHtmlArr.push('<select class="fieldsel">');
            for (var i = 0, l = fields.length; i < l; i++)
            {
                var field = fields[i];
                rowHtmlArr.push('<option value="' + field.name + '"');
                if (i == 0) rowHtmlArr.push(" selected ");
                rowHtmlArr.push('>');
                rowHtmlArr.push(field.display);
                rowHtmlArr.push('</option>');
            }
            rowHtmlArr.push("</select>");
            rowHtmlArr.push('</td>');

            rowHtmlArr.push('<td class="l-filter-op">');
            rowHtmlArr.push('<select class="opsel">');
            rowHtmlArr.push(g._bulidOpSelectOptionsHtml(fieldType));
            rowHtmlArr.push('</select>');
            rowHtmlArr.push('</td>');
            rowHtmlArr.push('<td class="l-filter-value">');
            rowHtmlArr.push('<input type="text" class="filter_valtxt" />');
            rowHtmlArr.push('</td>');
            rowHtmlArr.push('<td>');
            rowHtmlArr.push('<div class="l-icon-cross deleterole"></div>');
            rowHtmlArr.push('</td>');
            rowHtmlArr.push('</tr>');
            return rowHtmlArr.join('');
        },
        
        //获取一个运算符选择框的html
        _bulidOpSelectOptionsHtml: function (fieldType)
        {
            var g = this, p = this.options;
            var ops = p.operators[fieldType];
            var opHtmlArr = [];
            for (var i = 0, l = ops.length; i < l; i++)
            {
                var op = ops[i];
                opHtmlArr[opHtmlArr.length] = '<option value="' + op + '">';
                opHtmlArr[opHtmlArr.length] = p.strings[op];
                opHtmlArr[opHtmlArr.length] = '</option>';
            }
            return opHtmlArr.join('');
        },
        //获取构建简单查询的html
        _bulidSimpleRuleHtml: function (fields)
        {
            var g = this, p = this.options;
            fields = fields || p.fields;
            var rowHtmlArr = [];
            var fieldType = fields[0].type || "string";
            rowHtmlArr.push('<tr fieldtype="' + fieldType + '"><td align="right" bgcolor="#f1f5f9" width="120" class="l-filter-column">');
            rowHtmlArr.push('<select class="fieldsel">');
            for (var i = 0, l = fields.length; i < l; i++)
            {
                var field = fields[i];
                rowHtmlArr.push('<option value="' + field.name + '"');
                if (i == 0) rowHtmlArr.push(" selected ");
                rowHtmlArr.push('>');
                rowHtmlArr.push(field.display);
                rowHtmlArr.push('</option>');
            }
            rowHtmlArr.push("</select>");
            rowHtmlArr.push('</td>');

            rowHtmlArr.push('<td class="l-filter-op" style="display:none">');
            rowHtmlArr.push(' <select class="opsel">');
            rowHtmlArr.push('   <option value="' + field.op + '">');
            rowHtmlArr.push('    ' + p.strings[field.op]);
            rowHtmlArr.push('   </option>');
            rowHtmlArr.push(' </select>');
            rowHtmlArr.push('</td>');
            
            rowHtmlArr.push('<td class="l-filter-value" align="left">');
            rowHtmlArr.push('<input type="text" class="filter_valtxt"/>');                       
            rowHtmlArr.push('</td>');
            
            rowHtmlArr.push('</tr>');
            return rowHtmlArr.join('');
        },
        
        //获取高级查询的html
        _bulidHighLevelRuleHtml: function (fields)
        {
            var g = this, p = this.options;
            fields = fields || p.fields;
            var rowHtmlArr = [];
            var lineNum = p.lineNum;
            rowHtmlArr.push('<tr>');
            for(var j=0;j<fields.length;j++){
	            var fieldType = fields[j].type || "string";
	            var field = fields[j];
	            if(j>0 && j%lineNum==0 ){
	            	rowHtmlArr.push('</tr>');
	            	rowHtmlArr.push('<tr>');
	            }
	            
	            rowHtmlArr.push('<td class="l-filter-column" align="right" bgcolor="#f1f5f9" width="120" fieldType="'+fieldType+'" column="'+field.name+'">');
	            rowHtmlArr.push(field.display+"：");
	            rowHtmlArr.push('</td>');
	
	            rowHtmlArr.push('<td class="l-filter-op" style="display:none">');
	            rowHtmlArr.push(' <select class="opsel">');
	            rowHtmlArr.push('   <option value="' + field.op + '">');
	            rowHtmlArr.push('    ' + p.strings[field.op]);
	            rowHtmlArr.push('   </option>');
	            rowHtmlArr.push(' </select>');
	            rowHtmlArr.push('</td>');
	            
	            if(j==fields.length-1){
	            	var colspan = 1;
	            	var k = j%lineNum+1;
	            	if(k!=lineNum) colspan = (lineNum-k)*2+1;
		            rowHtmlArr.push('<td class="l-filter-value" align="left" colspan="'+colspan+'">');
		            rowHtmlArr.push('<input type="text" class="filter_valtxt" />');
		            rowHtmlArr.push('</td>');
	            }else{
		            rowHtmlArr.push('<td class="l-filter-value" align="left">');
		            rowHtmlArr.push('<input type="text" class="filter_valtxt" />');
		            rowHtmlArr.push('</td>');
	            }
	            
	            if(j==fields.length-1){
//	            	if(p.isHistory){
//		            	rowHtmlArr.push('<td>');
//		            	rowHtmlArr.push('<a name="saveHistory" href="javascript:void(0)">保存方案</a>      ');
//		            	rowHtmlArr.push('<a name="history" href="javascript:void(0)">历史方案</a>');
//		            	rowHtmlArr.push('</td>');
//		            	
//	            	}
	            	rowHtmlArr.push("</tr>");
	            }
            }
            
            return rowHtmlArr.join('');
        },        

        //获取自定义查询的html
        _bulidSimpleCustomRuleHtml: function (fields)
        {
            var g = this, p = this.options;
            fields = fields || p.fields;
            var lineNum = p.lineNum;
            var rowHtmlArr = [];
            rowHtmlArr.push('<tr>');
            for(var j=0;j<fields.length;j++){
	            var fieldType = fields[j].type || "string";
	            var field = fields[j];
	            if(j>0 && j%lineNum==0 ){
	            	rowHtmlArr.push('</tr>');
	            	rowHtmlArr.push('<tr>');
	            }
	            
	            rowHtmlArr.push('<td class="l-filter-column" align="right" bgcolor="#f1f5f9" width="120" fieldType="'+fieldType+'" column="'+field.name+'">');
	            rowHtmlArr.push(field.display+"：");
	            rowHtmlArr.push('</td>');
	
	            rowHtmlArr.push('<td class="l-filter-op" width="3%">');
	            rowHtmlArr.push('<select class="opsel" ligerui="width:80">');
	            rowHtmlArr.push(g._bulidOpSelectOptionsHtml(fieldType));
	            rowHtmlArr.push('</select>');
	            rowHtmlArr.push('</td>');
	            rowHtmlArr.push('<td class="l-filter-value" align="left">');
	            rowHtmlArr.push('<input type="text" class="filter_valtxt"/>');
	            rowHtmlArr.push('</td>');
	            
	            if(j==fields.length-1){
	            	if(p.isHistory){
		            	rowHtmlArr.push('<td align="left">');
		            	rowHtmlArr.push('<a name="saveHistory" href="javascript:void(0)">保存方案</a>      ');
		            	rowHtmlArr.push('<a name="history" href="javascript:void(0)">历史方案</a>');
		            	rowHtmlArr.push('</td>');
		            	
	            	}
	            	rowHtmlArr.push("</tr>");
	            }
            }
            
            return rowHtmlArr.join('');
        },
        //获取一个运算符option的html
        _bulidOpOptionHtml: function (field)
        {
            var g = this, p = this.options;
            var opHtmlArr = [];
            opHtmlArr.push('<option value="' + field.op + '">');
            opHtmlArr.push(p.strings[field.op]);
            opHtmlArr.push('</option>');
            
            return opHtmlArr.join('');
        }
    });
    
    //获取控件相关属性
    function getEditorStyle(field){
        var styleAttr = [];
        if(field.editor.options && field.editor.options.width){
        	var width = "width:"+field.editor.options.width;
        	styleAttr.push(width);
        }
        if(field.editor.options && field.editor.options.height){
        	var height = "height:"+field.editor.options.height;
        	styleAttr.push(height);
        }
        
        return styleAttr.join(",");
    }
    
    //获取日期控件html代码
    function getDateEditorHtml(field,name,time,range){
    	
    	var htmlArr = [];
    	htmlArr.push("<input type='text' id='"+name+time+"'");
    	
    	var width = "178";
    	if(field.width) width = field.width;
    	if((width.indexOf('px') > 0) || (width.indexOf('%') > 0)){
    		htmlArr.push(" style='width:"+width+";'");
    	}else{
    		htmlArr.push(" style='width:"+width+"px;'");
    	}
    	
    	htmlArr.push(" class='l-text Wdate'");
    	
    	var format = "yyyy-MM-dd";
    	if(field.format && $.trim(field.format)!='') format = field.format;
    	
    	if(range){
    		var dpOps = {};
    		dpOps.dateFmt = field.format;
	    	if(name=="start"){
	    		var sRange = {};
	    		for(var i in range){
	    			sRange[i] = -range[i];
	    		}
	    		var sRangeStr = JSON2.stringify(sRange);
	    		dpOps.minDate = '#F{$dp.$D("end'+time+'",'+sRangeStr+')}';
	    		dpOps.maxDate = '#F{$dp.$D("end'+time+'")}';
	    	}else{
	    		var rangeStr = JSON2.stringify(range);
	    		dpOps.maxDate = '#F{$dp.$D("start'+time+'",'+rangeStr+')}';
	    		dpOps.minDate = '#F{$dp.$D("start'+time+'")}';
	    	}
    		var opStr = JSON2.stringify(dpOps);
    		opStr = opStr.replace(/"/g, "'");
	    	htmlArr.push(" onfocus=\"WdatePicker("+opStr+")\"");
    	}else{
    		htmlArr.push(" onfocus='WdatePicker({dateFmt:\""+format+"\"})'");
    	}
    	
    	if(typeof(name)=='string' && name!=''){
    		htmlArr.push(" name='"+name+"'");
    	}
    	
    	htmlArr.push(" />");
    	
    	return htmlArr.join('');
    }
    
    function processValue(value){
    	var v = "";
    	if(!value || 'null'==value) return v;
    	return value;
    }
    
})(jQuery);

/**
* jQuery ligerUI 1.2.2
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 
*/
(function ($)
{
    $.ligerMenu = function (options)
    {
        return $.ligerui.run.call(null, "ligerMenu", arguments);
    };

    $.ligerDefaults.Menu = {
        width: 120,
        top: 0,
        left: 0,
        items: null,
        shadow: true
    };

    $.ligerMethos.Menu = {};

    $.ligerui.controls.Menu = function (options)
    {
        $.ligerui.controls.Menu.base.constructor.call(this, null, options);
    };
    $.ligerui.controls.Menu.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return 'Menu';
        },
        __idPrev: function ()
        {
            return 'Menu';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.Menu;
        },
        _render: function ()
        {
            var g = this, p = this.options;
            g.menuItemCount = 0;
            //全部菜单
            g.menus = {};
            //顶级菜单
            g.menu = g.createMenu();
            g.element = g.menu[0];
            g.menu.css({ top: p.top, left: p.left, width: p.width });

            p.items && $(p.items).each(function (i, item)
            {
                g.addItem(item);
            });

            $(document).bind('click.menu', function ()
            {
                for (var menuid in g.menus)
                {
                    var menu = g.menus[menuid];
                    if (!menu) return;
                    menu.hide();
                    if (menu.shadow) menu.shadow.hide();
                }
            });
            g.set(p);
        },
        show: function (options, menu)
        {
            var g = this, p = this.options;
            if (menu == undefined) menu = g.menu;
            if (options && options.left != undefined)
            {
                menu.css({ left: options.left });
            }
            if (options && options.top != undefined)
            {
                menu.css({ top: options.top });
            }
            menu.show();
            g.updateShadow(menu);
        },
        updateShadow: function (menu)
        {
            var g = this, p = this.options;
            if (!p.shadow) return;
            menu.shadow.css({
                left: menu.css('left'),
                top: menu.css('top'),
                width: menu.outerWidth(),
                height: menu.outerHeight()
            });
            if (menu.is(":visible"))
                menu.shadow.show();
            else
                menu.shadow.hide();
        },
        hide: function (menu)
        {
            var g = this, p = this.options;
            if (menu == undefined) menu = g.menu;
            g.hideAllSubMenu(menu);
            menu.hide();
            g.updateShadow(menu);
        },
        toggle: function ()
        {
            var g = this, p = this.options;
            g.menu.toggle();
            g.updateShadow(g.menu);
        },
        removeItem: function (itemid)
        {
            var g = this, p = this.options;
            $("> .l-menu-item[menuitemid=" + itemid + "]", g.menu.items).remove();
        },
        setEnabled: function (itemid)
        {
            var g = this, p = this.options;
            $("> .l-menu-item[menuitemid=" + itemid + "]", g.menu.items).removeClass("l-menu-item-disable");
        },
        setDisabled: function (itemid)
        {
            var g = this, p = this.options;
            $("> .l-menu-item[menuitemid=" + itemid + "]", g.menu.items).addClass("l-menu-item-disable");
        },
        isEnable: function (itemid)
        {
            var g = this, p = this.options;
            return !$("> .l-menu-item[menuitemid=" + itemid + "]", g.menu.items).hasClass("l-menu-item-disable");
        },
        getItemCount: function ()
        {
            var g = this, p = this.options;
            return $("> .l-menu-item", g.menu.items).length;
        },
        addItem: function (item, menu)
        {
            var g = this, p = this.options;
            if (!item) return;
            if (menu == undefined) menu = g.menu;

            if (item.line)
            {
                menu.items.append('<div class="l-menu-item-line"></div>');
                return;
            }
            var ditem = $('<div class="l-menu-item"><div class="l-menu-item-text"></div> </div>');
            var itemcount = $("> .l-menu-item", menu.items).length;
            menu.items.append(ditem);
            ditem.attr("ligeruimenutemid", ++g.menuItemCount);
            item.id && ditem.attr("menuitemid", item.id);
            item.text && $(">.l-menu-item-text:first", ditem).html(item.text);
            item.icon && ditem.prepend('<div class="l-menu-item-icon l-icon-' + item.icon + '"></div>');
            item.img && ditem.prepend('<div class="l-menu-item-icon"><img style="width:16px;height:16px;margin:2px;" src="' + item.img + '" /></div>');
            if (item.disable || item.disabled)
                ditem.addClass("l-menu-item-disable");
            if (item.children)
            {
                ditem.append('<div class="l-menu-item-arrow"></div>');
                var newmenu = g.createMenu(ditem.attr("ligeruimenutemid"));
                g.menus[ditem.attr("ligeruimenutemid")] = newmenu;
                newmenu.width(p.width);
                newmenu.hover(null, function ()
                {
                    if (!newmenu.showedSubMenu)
                        g.hide(newmenu);
                });
                $(item.children).each(function ()
                {
                    g.addItem(this, newmenu);
                });
            }
            item.click && ditem.click(function ()
            {
                if ($(this).hasClass("l-menu-item-disable")) return;
                item.click(item, itemcount);
            });
            item.dblclick && ditem.dblclick(function ()
            {
                if ($(this).hasClass("l-menu-item-disable")) return;
                item.dblclick(item, itemcount);
            });

            var menuover = $("> .l-menu-over:first", menu);
            ditem.hover(function ()
            {
                if ($(this).hasClass("l-menu-item-disable")) return;
                var itemtop = $(this).offset().top;
                var top = itemtop - menu.offset().top;
                menuover.css({ top: top });
                g.hideAllSubMenu(menu);
                if (item.children)
                {
                    var ligeruimenutemid = $(this).attr("ligeruimenutemid");
                    if (!ligeruimenutemid) return;
                    if (g.menus[ligeruimenutemid])
                    {
                        g.show({ top: itemtop, left: $(this).offset().left + $(this).width() - 5 }, g.menus[ligeruimenutemid]);
                        menu.showedSubMenu = true;
                    }
                }
            }, function ()
            {
                if ($(this).hasClass("l-menu-item-disable")) return;
                var ligeruimenutemid = $(this).attr("ligeruimenutemid");
                if (item.children)
                {
                    var ligeruimenutemid = $(this).attr("ligeruimenutemid");
                    if (!ligeruimenutemid) return;
                };
            });
        },
        hideAllSubMenu: function (menu)
        {
            var g = this, p = this.options;
            if (menu == undefined) menu = g.menu;
            $("> .l-menu-item", menu.items).each(function ()
            {
                if ($("> .l-menu-item-arrow", this).length > 0)
                {
                    var ligeruimenutemid = $(this).attr("ligeruimenutemid");
                    if (!ligeruimenutemid) return;
                    g.menus[ligeruimenutemid] && g.hide(g.menus[ligeruimenutemid]);
                }
            });
            menu.showedSubMenu = false;
        },
        createMenu: function (parentMenuItemID)
        {
            var g = this, p = this.options;
            var menu = $('<div class="l-menu" style="display:none"><div class="l-menu-yline"></div><div class="l-menu-over"><div class="l-menu-over-l"></div> <div class="l-menu-over-r"></div></div><div class="l-menu-inner"></div></div>');
            parentMenuItemID && menu.attr("ligeruiparentmenuitemid", parentMenuItemID);
            menu.items = $("> .l-menu-inner:first", menu);
            menu.appendTo('body');
            if (p.shadow)
            {
                menu.shadow = $('<div class="l-menu-shadow"></div>').insertAfter(menu);
                g.updateShadow(menu);
            }
            menu.hover(null, function ()
            {
                if (!menu.showedSubMenu)
                    $("> .l-menu-over:first", menu).css({ top: -24 });
            });
            if (parentMenuItemID)
                g.menus[parentMenuItemID] = menu;
            else
                g.menus[0] = menu;
            return menu;
        }
    });
    //旧写法保留
    $.ligerui.controls.Menu.prototype.setEnable = $.ligerui.controls.Menu.prototype.setEnabled;
    $.ligerui.controls.Menu.prototype.setDisable = $.ligerui.controls.Menu.prototype.setDisabled;



})(jQuery);

/**
* jQuery ligerUI 1.2.2
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 
*/

(function ($)
{
    var l = $.ligerui;

    //全局事件
    $(".l-dialog-btn").live('mouseover', function ()
    {
        $(this).addClass("l-dialog-btn-over");
    }).live('mouseout', function ()
    {
        $(this).removeClass("l-dialog-btn-over");
    });
    $(".l-dialog-tc .l-dialog-close").live('mouseover', function ()
    {
        $(this).addClass("l-dialog-close-over");
    }).live('mouseout', function ()
    {
        $(this).removeClass("l-dialog-close-over");
    });


    $.ligerDialog = function ()
    {
        return l.run.call(null, "ligerDialog", arguments, { isStatic: true });
    };

    //dialog 图片文件夹的路径 预加载
    $.ligerui.DialogImagePath = "../../lib/ligerUI/skins/Aqua/images/win/";

    function prevImage(paths)
    {
        for (var i in paths)
        {
            $('<img />').attr('src', l.DialogImagePath + paths[i]);
        }
    }
    //prevImage(['dialog.gif', 'dialog-winbtns.gif', 'dialog-bc.gif', 'dialog-tc.gif']);

    $.ligerDefaults.Dialog = {
        cls: null,       //给dialog附加css class
        id: null,        //给dialog附加id
        buttons: null, //按钮集合 
        isDrag: true,   //是否拖动
        width: 280,     //宽度
        height: null,   //高度，默认自适应 
        content: '',    //内容
        target: null,   //目标对象，指定它将以appendTo()的方式载入
        url: null,      //目标页url，默认以iframe的方式载入
        load: false,     //是否以load()的方式加载目标页的内容 
        type: 'none',   //类型 warn、success、error、question
        left: null,     //位置left
        top: null,      //位置top
        modal: true,    //是否模态对话框
        name: null,     //创建iframe时 作为iframe的name和id 
        isResize: false, // 是否调整大小
        allowClose: true, //允许关闭
        opener: null,
        timeParmName: null,  //是否给URL后面加上值为new Date().getTime()的参数，如果需要指定一个参数名即可
        closeWhenEnter: null, //回车时是否关闭dialog
        isHidden: false,        //关闭对话框时是否只是隐藏，还是销毁对话框
        show: true,          //初始化时是否马上显示
        title: '提示',        //头部 
        showMax: false,                             //是否显示最大化按钮 
        showToggle: false,                          //是否显示收缩窗口按钮
        showMin: false,                             //是否显示最小化按钮
        slide: $.browser.msie ? false : true,        //是否以动画的形式显示 
        fixedType: null,            //在固定的位置显示, 可以设置的值有n, e, s, w, ne, se, sw, nw
        showType: null,             //显示类型,可以设置为slide(固定显示时有效) 
        onLoaded: null,
        onExtend: null,
        onExtended: null,
        onCollapse: null,
        onCollapseed: null,
        onContentHeightChange: null,
        onClose: null,
        onClosed: null,
        onStopResize: null
    };
    $.ligerDefaults.DialogString = {
        titleMessage: '提示',                     //提示文本标题
        ok: '确定',
        yes: '是',
        no: '否',
        cancel: '取消',
        waittingMessage: '正在等待中,请稍候...'
    };

    $.ligerMethos.Dialog = $.ligerMethos.Dialog || {};


    l.controls.Dialog = function (options)
    {
        l.controls.Dialog.base.constructor.call(this, null, options);
    };
    l.controls.Dialog.ligerExtend(l.core.Win, {
        __getType: function ()
        {
            return 'Dialog';
        },
        __idPrev: function ()
        {
            return 'Dialog';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.Dialog;
        },
        _render: function ()
        {
            var g = this, p = this.options;
            var tmpId = "";
            g.set(p, true);
            
           /* var ifr = '<iframe id="ttttttt"  frameborder="0" src="" style="position:absolute;top:0;left:0;width:'+$(window).width()+';height:'+$(window).height()+'" scrolling="auto" src="/rdp/static/ligerdialog.html" allowtransparency="true"></iframe>'
            $('body').append(ifr);*/
            
            var dialog = $('<div class="l-dialog"><table class="l-dialog-table" cellpadding="0" cellspacing="0" border="0"><tbody><tr><td class="l-dialog-tl"></td><td class="l-dialog-tc"><div class="l-dialog-tc-inner"><div class="l-dialog-icon"></div><div class="l-dialog-title"></div><div class="l-dialog-winbtns"><div class="l-dialog-winbtn l-dialog-close"></div></div></div></td><td class="l-dialog-tr"></td></tr><tr><td class="l-dialog-cl"></td><td class="l-dialog-cc"><div class="l-dialog-body"><div class="l-dialog-image"></div> <div class="l-dialog-content"></div><div class="l-dialog-buttons"><div class="l-dialog-buttons-inner"></div></td><td class="l-dialog-cr"></td></tr><tr><td class="l-dialog-bl"></td><td class="l-dialog-bc"></td><td class="l-dialog-br"></td></tr></tbody></table></div>');
            $('body').append(dialog);
            g.dialog = dialog;
            g.element = dialog[0];
            g.dialog.body = $(".l-dialog-body:first", g.dialog);
            g.dialog.header = $(".l-dialog-tc-inner:first", g.dialog);
            g.dialog.winbtns = $(".l-dialog-winbtns:first", g.dialog.header);
            g.dialog.buttons = $(".l-dialog-buttons:first", g.dialog);
            g.dialog.content = $(".l-dialog-content:first", g.dialog);
            g.set(p, false);

            if (p.allowClose == false) $(".l-dialog-close", g.dialog).remove();
            if (p.target || p.url || p.type == "none")
            {
                p.type = null;
                g.dialog.addClass("l-dialog-win");
            }
            if (p.cls) g.dialog.addClass(p.cls);
            if (p.id) g.dialog.attr("id", p.id);
            //设置锁定屏幕、拖动支持 和设置图片
            g.mask();
            if (p.isDrag)
                g._applyDrag();
            if (p.isResize)
                g._applyResize();
            if (p.type)
                g._setImage();
            else
            {
                $(".l-dialog-image", g.dialog).remove();
                g.dialog.content.addClass("l-dialog-content-noimage");
            }
            if (!p.show)
            {
                g.unmask();
                g.dialog.hide();
            }
            //设置主体内容
            if (p.target)
            {
                g.dialog.content.prepend(p.target);
                $(p.target).show();
            }
            else if (p.url)
            {
                if (p.timeParmName)
                {
                    p.url += p.url.indexOf('?') == -1 ? "?" : "&";
                    p.url += p.timeParmName + "=" + new Date().getTime();
                }
                if (p.load)
                {
                    g.dialog.body.load(p.url, function ()
                    {
                        g._saveStatus();
                        g.trigger('loaded');
                    });
                }
                else
                {
                    g.jiframe = $("<iframe frameborder='0'></iframe>");
                    var framename = p.name ? p.name : "ligerwindow" + new Date().getTime();
                    g.jiframe.attr("name", framename);
                    g.jiframe.attr("id", framename);
                    g.dialog.content.prepend(g.jiframe);
                    g.dialog.content.addClass("l-dialog-content-nopadding");
                    setTimeout(function ()
                    {
                        g.jiframe.attr("src", p.url);
                        g.frame = window.frames[g.jiframe.attr("name")];
                    }, 0);
                    // 为了解决ie下对含有iframe的div窗口销毁不正确，进而导致第二次打开时焦点不在当前图层的问题
                    // 加入以下代码 
                    tmpId = 'jquery_ligerui_' + new Date().getTime();
                    g.tmpInput = $("<input></input>");
                    g.tmpInput.attr("id", tmpId);
                    g.dialog.content.prepend(g.tmpInput);
                }
            }
            if (p.opener) g.dialog.opener = p.opener;
            //设置按钮
            if (p.buttons)
            {
                $(p.buttons).each(function (i, item)
                {
                    var btn = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner"></div></div>');
                    $(".l-dialog-btn-inner", btn).html(item.text);
                    //add by liuzhengyong 给按钮ID属性
                    if(item.id)$(".l-dialog-btn-inner", btn).attr("id",item.id);
                    //add by zhaolong 给按钮加一个hide隐藏属性
                    if(item.hide) $(btn).hide();
                    $(".l-dialog-buttons-inner", g.dialog.buttons).prepend(btn);
                    item.width && btn.width(item.width);
                    item.onclick && btn.click(function () { item.onclick(item, g, i) });
                });
            } else
            {
                g.dialog.buttons.remove();
            }
            $(".l-dialog-buttons-inner", g.dialog.buttons).append("<div class='l-clear'></div>");


            $(".l-dialog-title", g.dialog)
            .bind("selectstart", function () { return false; });
            g.dialog.click(function ()
            {
                l.win.setFront(g);
            });
            //设置事件
            $(".l-dialog-tc .l-dialog-close", g.dialog).click(function ()
            {
                if (p.isHidden)
                    g.hide();
                else
                    g.close();
            });
            if (!p.fixedType)
            {
                //位置初始化
                var left = 0;
                var top = 0;
                var width = p.width || g.dialog.width();
                if (p.slide == true) p.slide = 'fast';
                if (p.left != null) left = p.left;
                else p.left = left = 0.5 * ($(window).width() - width);
                if (p.top != null) top = p.top;
                else p.top = top = 0.5 * ($(window).height() - g.dialog.height()) + $(window).scrollTop() - 10;
                if (left < 0) p.left = left = 0;
                if (top < 0) p.top = top = 0;
                g.dialog.css({ left: left, top: top });
            }
            g.show();
            $('body').bind('keydown.dialog', function (e)
            {
                var key = e.which;
                if (key == 13)
                {
                    g.enter();
                }
                else if (key == 27)
                {
                    g.esc();
                }
            });

            g._updateBtnsWidth();
            g._saveStatus();
            g._onReisze();
            if (tmpId != "")
            {
                $("#" + tmpId).focus();
                $("#" + tmpId).remove();
            }
        },
        _borderX: 12,
        _borderY: 32,
        doMax: function (slide)
        {
            var g = this, p = this.options;
            var width = $(window).width(), height = $(window).height(), left = 0, top = 0;
            if (l.win.taskbar)
            {
                height -= l.win.taskbar.outerHeight();
                if (l.win.top) top += l.win.taskbar.outerHeight();
            }
            if (slide)
            {
                g.dialog.body.animate({ width: width - g._borderX }, p.slide);
                g.dialog.animate({ left: left, top: top }, p.slide);
                g.dialog.content.animate({ height: height - g._borderY - g.dialog.buttons.outerHeight() }, p.slide, function ()
                {
                    g._onReisze();
                });
            }
            else
            {
                g.set({ width: width, height: height, left: left, top: top });
                g._onReisze();
            }
        },
        //最大化
        max: function ()
        {
            var g = this, p = this.options;
            if (g.winmax)
            {
                g.winmax.addClass("l-dialog-recover");
                g.doMax(p.slide);
                if (g.wintoggle)
                {
                    if (g.wintoggle.hasClass("l-dialog-extend"))
                        g.wintoggle.addClass("l-dialog-toggle-disabled l-dialog-extend-disabled");
                    else
                        g.wintoggle.addClass("l-dialog-toggle-disabled l-dialog-collapse-disabled");
                }
                if (g.resizable) g.resizable.set({ disabled: true });
                if (g.draggable) g.draggable.set({ disabled: true });
                g.maximum = true;

                $(window).bind('resize.dialogmax', function ()
                {
                    g.doMax(false);
                });
            }
        },

        //恢复
        recover: function ()
        {
            var g = this, p = this.options;
            if (g.winmax)
            {
                g.winmax.removeClass("l-dialog-recover");
                if (p.slide)
                {
                    g.dialog.body.animate({ width: g._width - g._borderX }, p.slide);
                    g.dialog.animate({ left: g._left, top: g._top }, p.slide);
                    g.dialog.content.animate({ height: g._height - g._borderY - g.dialog.buttons.outerHeight() }, p.slide, function ()
                    {
                        g._onReisze();
                    });
                }
                else
                {
                    g.set({ width: g._width, height: g._height, left: g._left, top: g._top });
                    g._onReisze();
                }
                if (g.wintoggle)
                {
                    g.wintoggle.removeClass("l-dialog-toggle-disabled l-dialog-extend-disabled l-dialog-collapse-disabled");
                }

                $(window).unbind('resize.dialogmax');
            }
            if (this.resizable) this.resizable.set({ disabled: false });
            if (g.draggable) g.draggable.set({ disabled: false });
            g.maximum = false;
        },

        //最小化
        min: function ()
        {
            var g = this, p = this.options;
            var task = l.win.getTask(this);
            if (p.slide)
            {
                g.dialog.body.animate({ width: 1 }, p.slide);
                task.y = task.offset().top + task.height();
                task.x = task.offset().left + task.width() / 2;
                g.dialog.animate({ left: task.x, top: task.y }, p.slide, function ()
                {
                    g.dialog.hide();
                });
            }
            else
            {
                g.dialog.hide();
            }
            g.unmask();
            g.minimize = true;
            g.actived = false;
        },

        active: function ()
        {
            var g = this, p = this.options;
            if (g.minimize)
            {
                var width = g._width, height = g._height, left = g._left, top = g._top;
                if (g.maximum)
                {
                    width = $(window).width();
                    height = $(window).height();
                    left = top = 0;
                    if (l.win.taskbar)
                    {
                        height -= l.win.taskbar.outerHeight();
                        if (l.win.top) top += l.win.taskbar.outerHeight();
                    }
                }
                if (p.slide)
                {
                    g.dialog.body.animate({ width: width - g._borderX }, p.slide);
                    g.dialog.animate({ left: left, top: top }, p.slide);
                }
                else
                {
                    g.set({ width: width, height: height, left: left, top: top });
                }
            }
            g.actived = true;
            g.minimize = false;
            l.win.setFront(g);
            g.show();
        },

        //展开 收缩
        toggle: function ()
        {

            var g = this, p = this.options;
            if (!g.wintoggle) return;
            if (g.wintoggle.hasClass("l-dialog-extend"))
                g.extend();
            else
                g.collapse();
        },

        //收缩
        collapse: function ()
        {
            var g = this, p = this.options;
            if (!g.wintoggle) return;
            if (p.slide)
                g.dialog.content.animate({ height: 1 }, p.slide);
            else
                g.dialog.content.height(1);
            if (this.resizable) this.resizable.set({ disabled: true });
        },

        //展开
        extend: function ()
        {
            var g = this, p = this.options;
            if (!g.wintoggle) return;
            var contentHeight = g._height - g._borderY - g.dialog.buttons.outerHeight();
            if (p.slide)
                g.dialog.content.animate({ height: contentHeight }, p.slide);
            else
                g.dialog.content.height(contentHeight);
            if (this.resizable) this.resizable.set({ disabled: false });
        },
        _updateBtnsWidth: function ()
        {
            var g = this;
            var btnscount = $(">div", g.dialog.winbtns).length;
            g.dialog.winbtns.width(22 * btnscount);
        },
        _setLeft: function (value)
        {
            if (!this.dialog) return;
            if (value != null)
                this.dialog.css({ left: value });
        },
        _setTop: function (value)
        {
            if (!this.dialog) return;
            if (value != null)
                this.dialog.css({ top: value });
        },
        _setWidth: function (value)
        {
            if (!this.dialog) return;
            if (value >= this._borderX)
            {
                this.dialog.body.width(value - this._borderX);
            }
        },
        _setHeight: function (value)
        {
            var g = this, p = this.options;
            if (!this.dialog) return;
            if (value >= this._borderY)
            {
                var height = value - this._borderY - g.dialog.buttons.outerHeight();
                if (g.trigger('ContentHeightChange', [height]) == false) return;
                g.dialog.content.height(height);
                g.trigger('ContentHeightChanged', [height]);
            }
        },
        _setShowMax: function (value)
        {
            var g = this, p = this.options;
            if (value)
            {
                if (!g.winmax)
                {
                    g.winmax = $('<div class="l-dialog-winbtn l-dialog-max"></div>').appendTo(g.dialog.winbtns)
                    .hover(function ()
                    {
                        if ($(this).hasClass("l-dialog-recover"))
                            $(this).addClass("l-dialog-recover-over");
                        else
                            $(this).addClass("l-dialog-max-over");
                    }, function ()
                    {
                        $(this).removeClass("l-dialog-max-over l-dialog-recover-over");
                    }).click(function ()
                    {
                        if ($(this).hasClass("l-dialog-recover"))
                            g.recover();
                        else
                            g.max();
                    });
                }
            }
            else if (g.winmax)
            {
                g.winmax.remove();
                g.winmax = null;
            }
            g._updateBtnsWidth();
        },
        _setShowMin: function (value)
        {
            var g = this, p = this.options;
            if (value)
            {
                if (!g.winmin)
                {
                    g.winmin = $('<div class="l-dialog-winbtn l-dialog-min"></div>').appendTo(g.dialog.winbtns)
                    .hover(function ()
                    {
                        $(this).addClass("l-dialog-min-over");
                    }, function ()
                    {
                        $(this).removeClass("l-dialog-min-over");
                    }).click(function ()
                    {
                        g.min();
                    });
                    l.win.addTask(g);
                }
            }
            else if (g.winmin)
            {
                g.winmin.remove();
                g.winmin = null;
            }
            g._updateBtnsWidth();
        },
        _setShowToggle: function (value)
        {
            var g = this, p = this.options;
            if (value)
            {
                if (!g.wintoggle)
                {
                    g.wintoggle = $('<div class="l-dialog-winbtn l-dialog-collapse"></div>').appendTo(g.dialog.winbtns)
                   .hover(function ()
                   {
                       if ($(this).hasClass("l-dialog-toggle-disabled")) return;
                       if ($(this).hasClass("l-dialog-extend"))
                           $(this).addClass("l-dialog-extend-over");
                       else
                           $(this).addClass("l-dialog-collapse-over");
                   }, function ()
                   {
                       $(this).removeClass("l-dialog-extend-over l-dialog-collapse-over");
                   }).click(function ()
                   {
                       if ($(this).hasClass("l-dialog-toggle-disabled")) return;
                       if (g.wintoggle.hasClass("l-dialog-extend"))
                       {
                           if (g.trigger('extend') == false) return;
                           g.wintoggle.removeClass("l-dialog-extend");
                           g.extend();
                           g.trigger('extended');
                       }
                       else
                       {
                           if (g.trigger('collapse') == false) return;
                           g.wintoggle.addClass("l-dialog-extend");
                           g.collapse();
                           g.trigger('collapseed')
                       }
                   });
                }
            }
            else if (g.wintoggle)
            {
                g.wintoggle.remove();
                g.wintoggle = null;
            }
        },
        //按下回车
        enter: function ()
        {
            var g = this, p = this.options;
            var isClose;
            if (p.closeWhenEnter != undefined)
            {
                isClose = p.closeWhenEnter;
            }
            else if (p.type == "warn" || p.type == "error" || p.type == "success" || p.type == "question")
            {
                isClose = true;
            }
            if (isClose)
            {
                g.close();
            }
        },
        esc: function ()
        {

        },
        _removeDialog: function ()
        {
            var g = this, p = this.options;
            if (p.showType && p.fixedType)
            {
                g.dialog.animate({ bottom: -1 * p.height }, function ()
                {
                    remove();
                });
            }
            else
            {
                remove();
            }
            function remove()
            {
                var jframe = $('iframe', g.dialog);
                if (jframe.length)
                {
                    var frame = jframe[0];
//                  frame.src = "about:blank";
//                  frame.contentWindow.document.write('');
                    //解决关闭dialog时，浏览器页签总在转的问题
                    frame.src = "";
                    $.browser.msie && CollectGarbage();
                    jframe.remove();
                }
                g.dialog.remove();
            }
        },
        close: function ()
        {
            var g = this, p = this.options;
            if (g.trigger('Close') == false) return;
            g.doClose();
            if (g.trigger('Closed') == false) return;
        },
        doClose: function ()
        {
            var g = this;
            l.win.removeTask(this);
            g.unmask();
            g._removeDialog();
            //销毁对话模
            g.destroy.ligerDefer(g);
            $('body').unbind('keydown.dialog');
        },
        _getVisible: function ()
        {
            return this.dialog.is(":visible");
        },
        _setUrl: function (url)
        {
            var g = this, p = this.options;
            p.url = url;
            if (p.load)
            {
                g.dialog.body.html("").load(p.url, function ()
                {
                    g.trigger('loaded');
                });
            }
            else if (g.jiframe)
            {
                g.jiframe.attr("src", p.url);
            }
        },
        _setContent: function (content)
        {
            this.dialog.content.html(content);
        },
        _setTitle: function (value)
        {
            var g = this; var p = this.options;
            if (value)
            {
                $(".l-dialog-title", g.dialog).html(value);
            }
        },
        _hideDialog: function ()
        {
            var g = this, p = this.options;
            if (p.showType && p.fixedType)
            {
                g.dialog.animate({ bottom: -1 * p.height }, function ()
                {
                    g.dialog.hide();
                });
            } else
            {
                g.dialog.hide();
            }
        },
        hidden: function ()
        {
            var g = this;
            l.win.removeTask(g);
            g.dialog.hide();
            g.unmask();
        },
        show: function ()
        {
            var g = this, p = this.options;
            g.mask();
            if (p.fixedType)
            {
                if (p.showType)
                {
                    g.dialog.css({ bottom: -1 * p.height }).addClass("l-dialog-fixed");
                    g.dialog.show().animate({ bottom: 0 });
                }
                else
                {
                    g.dialog.show().css({ bottom: 0 });
                }
            }
            else
            {
                g.dialog.show();
            }
            //前端显示 
            $.ligerui.win.setFront.ligerDefer($.ligerui.win, 100, [g]);
        },
        setUrl: function (url)
        {
            this._setUrl(url);
        },
        _saveStatus: function ()
        {
            var g = this;
            g._width = g.dialog.body.width();
            g._height = g.dialog.body.height();
            var top = 0;
            var left = 0;
            if (!isNaN(parseInt(g.dialog.css('top'))))
                top = parseInt(g.dialog.css('top'));
            if (!isNaN(parseInt(g.dialog.css('left'))))
                left = parseInt(g.dialog.css('left'));
            g._top = top;
            g._left = left;
        },
        _applyDrag: function ()
        {
            var g = this, p = this.options;
            if ($.fn.ligerDrag)
            {
                g.draggable = g.dialog.ligerDrag({
                    handler: '.l-dialog-title', animate: false,
                    onStartDrag: function ()
                    {
                        l.win.setFront(g);
                        var mask = $("<div class='l-dragging-mask' style='display:block'></div>").height(g.dialog.height());
                        g.dialog.append(mask);
                        g.dialog.content.addClass('l-dialog-content-dragging');
                    },
                    onDrag: function (current, e)
                    {
                        var pageY = e.pageY || e.screenY;
                        if (pageY < 0) return false;
                    },
                    onStopDrag: function ()
                    {
                        g.dialog.find("div.l-dragging-mask:first").remove();
                        g.dialog.content.removeClass('l-dialog-content-dragging');
                        if (p.target)
                        {
                            var triggers1 = l.find($.ligerui.controls.DateEditor);
                            var triggers2 = l.find($.ligerui.controls.ComboBox);
                            //更新所有下拉选择框的位置
                            $($.merge(triggers1, triggers2)).each(function ()
                            {
                                if (this.updateSelectBoxPosition)
                                    this.updateSelectBoxPosition();
                            });
                        }
                        g._saveStatus();
                    }
                });
            }
        },
        _onReisze: function ()
        {
            var g = this, p = this.options;
            if (p.target)
            {
                var manager = $(p.target).liger();
                if (!manager) manager = $(p.target).find(":first").liger();
                if (!manager) return;
                var contentHeight = g.dialog.content.height();
                var contentWidth = g.dialog.content.width();
                manager.trigger('resize', [{ width: contentWidth, height: contentHeight }]);
            }
        },
        _applyResize: function ()
        {
            var g = this, p = this.options;
            if ($.fn.ligerResizable)
            {
                g.resizable = g.dialog.ligerResizable({
                    onStopResize: function (current, e)
                    {
                        var top = 0;
                        var left = 0;
                        if (!isNaN(parseInt(g.dialog.css('top'))))
                            top = parseInt(g.dialog.css('top'));
                        if (!isNaN(parseInt(g.dialog.css('left'))))
                            left = parseInt(g.dialog.css('left'));
                        if (current.diffLeft)
                        {
                            g.set({ left: left + current.diffLeft });
                        }
                        if (current.diffTop)
                        {
                            g.set({ top: top + current.diffTop });
                        }
                        if (current.newWidth)
                        {
                            g.set({ width: current.newWidth });
                            g.dialog.body.css({ width: current.newWidth - g._borderX });
                        }
                        if (current.newHeight)
                        {
                            g.set({ height: current.newHeight });
                        }
                        g._onReisze();
                        g._saveStatus();
                        g.trigger('stopResize');
                        return false;
                    }, animate: false
                });
            }
        },
        _setImage: function ()
        {
            var g = this, p = this.options;
            if (p.type)
            {
                if (p.type == 'success' || p.type == 'donne' || p.type == 'ok')
                {
                    $(".l-dialog-image", g.dialog).addClass("l-dialog-image-donne").show();
                    g.dialog.content.css({ paddingLeft: 64, paddingBottom: 30 });
                }
                else if (p.type == 'error')
                {
                    $(".l-dialog-image", g.dialog).addClass("l-dialog-image-error").show();
                    g.dialog.content.css({ paddingLeft: 64, paddingBottom: 30 });
                }
                else if (p.type == 'warn')
                {
                    $(".l-dialog-image", g.dialog).addClass("l-dialog-image-warn").show();
                    g.dialog.content.css({ paddingLeft: 64, paddingBottom: 30 });
                }
                else if (p.type == 'question')
                {
                    $(".l-dialog-image", g.dialog).addClass("l-dialog-image-question").show();
                    g.dialog.content.css({ paddingLeft: 64, paddingBottom: 40 });
                }
            }
        }
    });
    l.controls.Dialog.prototype.hide = l.controls.Dialog.prototype.hidden;



    $.ligerDialog.open = function (p)
    {
        return $.ligerDialog(p);
    };
    $.ligerDialog.close = function ()
    {
        var dialogs = l.find(l.controls.Dialog.prototype.__getType());
        for (var i in dialogs)
        {
            var d = dialogs[i];
            d.destroy.ligerDefer(d, 5);
        }
        l.win.unmask();
    };
    //add by liuzhengyong 通过ID关闭对话模
    $.ligerDialog.closeDialog = function (id)
    {
    	var dialogs = l.find(l.controls.Dialog.prototype.__getType());
        for (var i in dialogs)
        {
            var d = dialogs[i];
            if(d.id ==id){
            	d.close();
            	//d.unmask();
            }
        }        
    };
    $.ligerDialog.show = function (p)
    {
        var dialogs = l.find(l.controls.Dialog.prototype.__getType());
        if (dialogs.length)
        {
            for (var i in dialogs)
            {
                dialogs[i].show();
                return;
            }
        }
        return $.ligerDialog(p);
    };
    $.ligerDialog.hide = function ()
    {
        var dialogs = l.find(l.controls.Dialog.prototype.__getType());
        for (var i in dialogs)
        {
            var d = dialogs[i];
            d.hide();
        }
    };
    $.ligerDialog.tip = function (options)
    {
        options = $.extend({
            showType: 'slide',
            width: 240,
            modal: false,
            height: 100
        }, options || {});

        $.extend(options, {
            fixedType: 'se',
            type: 'none',
            isDrag: false,
            isResize: false,
            showMax: false,
            showToggle: false,
            showMin: false
        });
        return $.ligerDialog.open(options);
    };
    $.ligerDialog.alert = function (content, title, type, callback)
    {
        content = content || "";
        if (typeof (title) == "function")
        {
            callback = title;
            type = null;
        }
        else if (typeof (type) == "function")
        {
            callback = type;
        }
        var btnclick = function (item, Dialog, index)
        {
            Dialog.close();
            if (callback)
                callback(item, Dialog, index);
        };
        p = {
            content: content,
            buttons: [{ text: $.ligerDefaults.DialogString.ok, onclick: btnclick }]
        };
        if (typeof (title) == "string" && title != "") p.title = title;
        if (typeof (type) == "string" && type != "") p.type = type;
        $.extend(p, {
            showMax: false,
            showToggle: false,
            showMin: false
        });
        return $.ligerDialog(p);
    };

    $.ligerDialog.confirm = function (content, title, callback)
    {
        if (typeof (title) == "function")
        {
            callback = title;
            type = null;
        }
        var btnclick = function (item, Dialog)
        {
            Dialog.close();
            if (callback)
            {
                callback(item.type == 'ok');
            }
        };
        p = {
            type: 'question',
            content: content,
            buttons: [{ text: $.ligerDefaults.DialogString.yes, onclick: btnclick, type: 'ok' }, { text: $.ligerDefaults.DialogString.no, onclick: btnclick, type: 'no' }]
        };
        if (typeof (title) == "string" && title != "") p.title = title;
        $.extend(p, {
            showMax: false,
            showToggle: false,
            showMin: false
        });
        return $.ligerDialog(p);
    };
    $.ligerDialog.warning = function (content, title, callback)
    {
        if (typeof (title) == "function")
        {
            callback = title;
            type = null;
        }
        var btnclick = function (item, Dialog)
        {
            Dialog.close();
            if (callback)
            {
                callback(item.type);
            }
        };
        p = {
            type: 'question',
            content: content,
            buttons: [{ text: $.ligerDefaults.DialogString.yes, onclick: btnclick, type: 'yes' }, { text: $.ligerDefaults.DialogString.no, onclick: btnclick, type: 'no' }, { text: $.ligerDefaults.DialogString.cancel, onclick: btnclick, type: 'cancel' }]
        };
        if (typeof (title) == "string" && title != "") p.title = title;
        $.extend(p, {
            showMax: false,
            showToggle: false,
            showMin: false
        });
        return $.ligerDialog(p);
    };
    $.ligerDialog.waitting = function (title)
    {
        title = title || $.ligerDefaults.Dialog.waittingMessage;
        return $.ligerDialog.open({ cls: 'l-dialog-waittingdialog', type: 'none', content: '<div style="padding:4px">' + title + '</div>', allowClose: false });
    };
    $.ligerDialog.closeWaitting = function ()
    {
        var dialogs = l.find(l.controls.Dialog);
        for (var i in dialogs)
        {
            var d = dialogs[i];
            if (d.dialog.hasClass("l-dialog-waittingdialog"))
                d.close();
        }
    };
    $.ligerDialog.success = function (content, title, onBtnClick)
    {
        return $.ligerDialog.alert(content, title, 'success', onBtnClick);
    };
    $.ligerDialog.error = function (content, title, onBtnClick)
    {
        return $.ligerDialog.alert(content, title, 'error', onBtnClick);
    };
    $.ligerDialog.warn = function (content, title, onBtnClick)
    {
        return $.ligerDialog.alert(content, title, 'warn', onBtnClick);
    };
    $.ligerDialog.question = function (content, title)
    {
        return $.ligerDialog.alert(content, title, 'question');
    };


    $.ligerDialog.prompt = function (title, value, multi, callback)
    {
        var target = $('<input type="text" class="l-dialog-inputtext"/>');
        if (typeof (multi) == "function")
        {
            callback = multi;
        }
        if (typeof (value) == "function")
        {
            callback = value;
        }
        else if (typeof (value) == "boolean")
        {
            multi = value;
        }
        if (typeof (multi) == "boolean" && multi)
        {
            target = $('<textarea class="l-dialog-textarea"></textarea>');
        }
        if (typeof (value) == "string" || typeof (value) == "int")
        {
            target.val(value);
        }
        var btnclick = function (item, Dialog, index)
        {
            Dialog.close();
            if (callback)
            {
                callback(item.type == 'yes', target.val());
            }
        }
        p = {
            title: title,
            target: target,
            width: 320,
            buttons: [{ text: $.ligerDefaults.DialogString.ok, onclick: btnclick, type: 'yes' }, { text: $.ligerDefaults.DialogString.cancel, onclick: btnclick, type: 'cancel' }]
        };
        return $.ligerDialog(p);
    };


})(jQuery);

/**
* jQuery ligerUI 1.2.2
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 
*/
(function ($)
{
    $.fn.ligerTextBox = function ()
    {
        return $.ligerui.run.call(this, "ligerTextBox", arguments);
    };

    $.fn.ligerGetTextBoxManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetTextBoxManager", arguments);
    };

    $.ligerDefaults.TextBox = {
        onChangeValue: null,
        onMouseOver: null,
        onMouseOut: null,
        onBlur: null,
        onFocus: null,
        width: null,
        disabled: false,
        value: null,     //初始化值 
        nullText: null,   //不能为空时的提示
        digits: false,     //是否限定为数字输入框
        number: false,    //是否限定为浮点数格式输入框
        currency: false,     //是否显示为货币形式
        readonly: false              //是否只读
    };


    $.ligerui.controls.TextBox = function (element, options)
    {
        $.ligerui.controls.TextBox.base.constructor.call(this, element, options);
    };

    $.ligerui.controls.TextBox.ligerExtend($.ligerui.controls.Input, {
        __getType: function ()
        {
            return 'TextBox'
        },
        __idPrev: function ()
        {
            return 'TextBox';
        },
        _init: function ()
        {
            $.ligerui.controls.TextBox.base._init.call(this);
            var g = this, p = this.options;
            if (!p.width)
            {
                p.width = $(g.element).width();
            }
            if ($(this.element).attr("readonly"))
            {
                p.readonly = true;
            } else if (p.readonly)
            {
                $(this.element).attr("readonly", true);
            }
        },
        _render: function ()
        {
            var g = this, p = this.options;
            g.inputText = $(this.element);
            //外层
            g.wrapper = g.inputText.wrap('<div class="l-text" style="float:left;"></div>').parent();
            g.wrapper.append('<div class="l-text-l"></div><div class="l-text-r"></div>');
            if (!g.inputText.hasClass("l-text-field"))
                g.inputText.addClass("l-text-field");
            this._setEvent();
            if (p.digits || p.number || p.currency)
            {
                g.inputText.addClass("l-text-field-number");
            }
            g.set(p);
            g.checkValue();
        },
        destroy: function ()
        {
            var g = this;
            if (g.wrapper)
            {
                g.wrapper.remove();
            }
            g.options = null;
            liger.remove(this);
        },
        _getValue: function ()
        {
            return this.inputText.val();
        },
        _setNullText: function ()
        {
            this.checkNotNull();
        },
        checkValue: function ()
        {
            var g = this, p = this.options;
            var v = g.inputText.val() || "";
            if (p.currency) v = v.replace(/\$|\,/g, '');
            var isFloat = p.number || p.currency, isDigits = p.digits;
            if (isFloat && !/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(v) || isDigits && !/^\d+$/.test(v))
            {
                //不符合,恢复到原来的值
                g.inputText.val(g.value || 0);
                p.currency && g.inputText.val(currencyFormatter(g.value));
                return;
            }
            g.value = v;
            p.currency && g.inputText.val(currencyFormatter(g.value));
        },
        checkNotNull: function ()
        {
            var g = this, p = this.options;
            if (p.nullText && !p.disabled)
            {
                if (!g.inputText.val())
                {
                    g.inputText.addClass("l-text-field-null").val(p.nullText);
                }
            }
        },
        _setEvent: function ()
        {
            var g = this, p = this.options;
            g.inputText.bind('blur.textBox', function ()
            {
                g.trigger('blur');
                g.checkNotNull();
                g.checkValue();
                g.wrapper.removeClass("l-text-focus");
            }).bind('focus.textBox', function ()
            {
                g.trigger('focus');
                if (p.nullText)
                {
                    if ($(this).hasClass("l-text-field-null"))
                    {
                        $(this).removeClass("l-text-field-null").val("");
                    }
                }
                g.wrapper.addClass("l-text-focus");
            })
            .change(function ()
            {
                g.trigger('changeValue', [this.value]);
            });
            g.wrapper.hover(function ()
            {
                g.trigger('mouseOver');
                g.wrapper.addClass("l-text-over");
            }, function ()
            {
                g.trigger('mouseOut');
                g.wrapper.removeClass("l-text-over");
            });
        },
        _setDisabled: function (value)
        {
            var g = this, p = this.options;
            if (value)
            {
                this.inputText.attr("readonly", "readonly");
                this.wrapper.addClass("l-text-disabled");
            }
            else if (!p.readonly)
            {
                this.inputText.removeAttr("readonly");
                this.wrapper.removeClass('l-text-disabled');
            }
        },
        _setWidth: function (value)
        {
            if (value > 20)
            {
                this.wrapper.css({ width: value });
                this.inputText.css({ width: value - 4 });
            }
        },
        _setHeight: function (value)
        {
            if (value > 10)
            {
                this.wrapper.height(value);
                this.inputText.height(value - 2);
            }
        },
        _setValue: function (value)
        {
            if (value != null)
                this.inputText.val(value);
        },
        _setLabel: function (value)
        {
            var g = this, p = this.options;
            if (!g.labelwrapper)
            {
                g.labelwrapper = g.wrapper.wrap('<div class="l-labeltext"></div>').parent();
                var lable = $('<div class="l-text-label" style="float:left;">' + value + ':&nbsp</div>');
                g.labelwrapper.prepend(lable);
                g.wrapper.css('float', 'left');
                if (!p.labelWidth)
                {
                    p.labelWidth = lable.width();
                }
                else
                {
                    g._setLabelWidth(p.labelWidth);
                }
                lable.height(g.wrapper.height());
                if (p.labelAlign)
                {
                    g._setLabelAlign(p.labelAlign);
                }
                g.labelwrapper.append('<br style="clear:both;" />');
                g.labelwrapper.width(p.labelWidth + p.width + 2);
            }
            else
            {
                g.labelwrapper.find(".l-text-label").html(value + ':&nbsp');
            }
        },
        _setLabelWidth: function (value)
        {
            var g = this, p = this.options;
            if (!g.labelwrapper) return;
            g.labelwrapper.find(".l-text-label").width(value);
        },
        _setLabelAlign: function (value)
        {
            var g = this, p = this.options;
            if (!g.labelwrapper) return;
            g.labelwrapper.find(".l-text-label").css('text-align', value);
        },
        updateStyle: function ()
        {
            var g = this, p = this.options;
            if (g.inputText.attr('readonly'))
            {
                g.wrapper.addClass("l-text-readonly");
                p.disabled = true;
            }
            else
            {
                g.wrapper.removeClass("l-text-readonly");
                p.disabled = false;
            }
            if (g.inputText.attr('disabled'))
            {
                g.wrapper.addClass("l-text-disabled");
                p.disabled = true;
            }
            else
            {
                g.wrapper.removeClass("l-text-disabled");
                p.disabled = false;
            }
            if (g.inputText.hasClass("l-text-field-null") && g.inputText.val() != p.nullText)
            {
                g.inputText.removeClass("l-text-field-null");
            }
            g.checkValue();
        }
    });

    function currencyFormatter(num)
    {
        if (!num) return "0.00";
        num = num.toString().replace(/\$|\,/g, '');
        if (isNaN(num))
            num = "0.00";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num * 100 + 0.50000000001);
        cents = num % 100;
        num = Math.floor(num / 100).toString();
        if (cents < 10)
            cents = "0" + cents;
        for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3) ; i++)
            num = num.substring(0, num.length - (4 * i + 3)) + ',' +
            num.substring(num.length - (4 * i + 3));
        return "" + (((sign) ? '' : '-') + '' + num + '.' + cents);
    }

})(jQuery);

/**
* jQuery ligerUI 1.2.2
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 
*/
(function ($)
{
    $.fn.ligerCheckBox = function (options)
    {
        return $.ligerui.run.call(this, "ligerCheckBox", arguments);
    };
    $.fn.ligerGetCheckBoxManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetCheckBoxManager", arguments);
    };
    $.ligerDefaults.CheckBox = {
        disabled: false,
        readonly : false //只读
    };

    $.ligerMethos.CheckBox = {};

    $.ligerui.controls.CheckBox = function (element, options)
    {
        $.ligerui.controls.CheckBox.base.constructor.call(this, element, options);
    };
    $.ligerui.controls.CheckBox.ligerExtend($.ligerui.controls.Input, {
        __getType: function ()
        {
            return 'CheckBox';
        },
        __idPrev: function ()
        {
            return 'CheckBox';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.CheckBox;
        },
        _render: function ()
        {
            var g = this, p = this.options;
            g.input = $(g.element);
            g.link = $('<a class="l-checkbox"></a>');
            g.wrapper = g.input.addClass('l-hidden').wrap('<div class="l-checkbox-wrapper"></div>').parent();
            g.wrapper.prepend(g.link);
            g.link.click(function ()
            {
                if (g.input.attr('disabled') || g.input.attr('readonly')) { return false; }
                if (p.disabled || p.readonly) return false;
                if (g.trigger('beforeClick', [g.element]) == false) return false; 
                if ($(this).hasClass("l-checkbox-checked"))
                {
                    g._setValue(false);
                }
                else
                {
                    g._setValue(true);
                }
                g.input.trigger("change");
            });
            g.wrapper.hover(function ()
            {
                if (!p.disabled)
                    $(this).addClass("l-over");
            }, function ()
            {
                $(this).removeClass("l-over");
            });
            this.set(p);
            this.updateStyle();
        },
        _setCss: function (value)
        {
            this.wrapper.css(value);
        },
        _setValue: function (value)
        {
            var g = this, p = this.options;
            if (!value)
            {
                g.input[0].checked = false;
                g.link.removeClass('l-checkbox-checked');
            }
            else
            {
                g.input[0].checked = true;
                g.link.addClass('l-checkbox-checked');
            }
        },
        _setDisabled: function (value)
        {
            if (value)
            {
                this.input.attr('disabled', true);
                this.wrapper.addClass("l-disabled");
            }
            else
            {
                this.input.attr('disabled', false);
                this.wrapper.removeClass("l-disabled");
            }
        },
        _getValue: function ()
        {
            return this.element.checked;
        },
        updateStyle: function ()
        {
            if (this.input.attr('disabled'))
            {
                this.wrapper.addClass("l-disabled");
                this.options.disabled = true;
            }
            if (this.input[0].checked)
            {
                this.link.addClass('l-checkbox-checked');
            }
            else
            {
                this.link.removeClass('l-checkbox-checked');
            }
        }
    });
})(jQuery);

/**
* jQuery ligerUI 1.2.2
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 
*/
(function ($)
{

    $.fn.ligerComboBox = function (options)
    {
        return $.ligerui.run.call(this, "ligerComboBox", arguments);
    };

    $.fn.ligerGetComboBoxManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetComboBoxManager", arguments);
    };

    $.ligerDefaults.ComboBox = {
        resize: true,           //是否调整大小
        isMultiSelect: false,   //是否多选
        isShowCheckBox: false,  //是否选择复选框
        columns: null,       //表格状态
        selectBoxWidth: null, //宽度
        selectBoxHeight: null, //高度
        onBeforeSelect: false, //选择前事件
        onSelected: null, //选择值事件 
        initValue: null,
        initText: null,
        valueField: 'id',
        textField: 'text',
        valueFieldID: null,
        slide: false,           //是否以动画的形式显示
        split: ";",
        data: null,
        tree: null,            //下拉框以树的形式显示，tree的参数跟LigerTree的参数一致 
        treeLeafOnly: true,   //是否只选择叶子
        condition: null,       //列表条件搜索 参数同 ligerForm
        grid: null,              //表格 参数同 ligerGrid
        onStartResize: null,
        onEndResize: null,
        hideOnLoseFocus: true,
        hideGridOnLoseFocus : false,
        url: null,              //数据源URL(需返回JSON)
        onSuccess: null,
        onError: null,
        onBeforeOpen: null,      //打开下拉框前事件，可以通过return false来阻止继续操作，利用这个参数可以用来调用其他函数，比如打开一个新窗口来选择值
        render: null,            //文本框显示html函数
        absolute: true,         //选择框是否在附加到body,并绝对定位
        cancelable: true,      //可取消选择
        css: null,            //附加css
        parms: null,         //ajax提交表单 
        renderItem : null,   //选项自定义函数
        autocomplete: false,  //自动完成 
        readonly: false,              //是否只读
        ajaxType: 'post',
        alwayShowInTop: false,      //下拉框是否一直显示在上方
        valueFieldCssClass : null
    };

    $.ligerDefaults.ComboBoxString = {
        Search : "搜索"
    };

    //扩展方法
    $.ligerMethos.ComboBox = $.ligerMethos.ComboBox || {};


    $.ligerui.controls.ComboBox = function (element, options)
    {
        $.ligerui.controls.ComboBox.base.constructor.call(this, element, options);
    };
    $.ligerui.controls.ComboBox.ligerExtend($.ligerui.controls.Input, {
        __getType: function ()
        {
            return 'ComboBox';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.ComboBox;
        },
        _init: function ()
        {
            $.ligerui.controls.ComboBox.base._init.call(this);
            var p = this.options;
            if (p.columns)
            {
                p.isShowCheckBox = true;
            }
            if (p.isMultiSelect)
            {
                p.isShowCheckBox = true;
            } 
        }, 
        _render: function ()
        {
            var g = this, p = this.options; 
            g.data = p.data;
            g.inputText = null;
            g.select = null;
            g.textFieldID = "";
            g.valueFieldID = "";
            g.valueField = null; //隐藏域(保存值) 
            //文本框初始化
            if (this.element.tagName.toLowerCase() == "input")
            {
                this.element.readOnly = true;
                g.inputText = $(this.element);
                g.textFieldID = this.element.id;
            }
            else if (this.element.tagName.toLowerCase() == "select")
            {
                $(this.element).hide();
                g.select = $(this.element);
                p.isMultiSelect = false;
                p.isShowCheckBox = false;
                p.cancelable = false;
                g.textFieldID = this.element.id + "_txt";
                g.inputText = $('<input type="text" readonly="true"/>');
                g.inputText.attr("id", g.textFieldID).insertAfter($(this.element));
            }  
            if (g.inputText[0].name == undefined) g.inputText[0].name = g.textFieldID; 
            //隐藏域初始化
            g.valueField = null;
            if (p.valueFieldID)
            {
                g.valueField = $("#" + p.valueFieldID + ":input,[name=" + p.valueFieldID + "]:input").filter("input:hidden"); 
                if (g.valueField.length == 0) g.valueField = $('<input type="hidden"/>');
                g.valueField[0].id = g.valueField[0].name = p.valueFieldID;
            }
            else
            {
                g.valueField = $('<input type="hidden"/>');
                g.valueField[0].id = g.valueField[0].name = g.textFieldID + "_val";
            }
            if (p.valueFieldCssClass)
            {
                g.valueField.addClass(p.valueFieldCssClass);
            }
            if (g.valueField[0].name == undefined) g.valueField[0].name = g.valueField[0].id;
            g.valueField.attr("data-ligerid", g.id);
            //开关
            g.link = $('<div class="l-trigger"><div class="l-trigger-icon"></div></div>');
           
            //下拉框
            g.selectBox = $('<div class="l-box-select" style="display:none"><div class="l-box-select-inner"><table cellpadding="0" cellspacing="0" border="0" class="l-box-select-table"></table></div></div>');
            g.selectBox.table = $("table:first", g.selectBox);
            //外层
            g.wrapper = g.inputText.wrap('<div class="l-text l-text-combobox"></div>').parent();
            g.wrapper.append('<div class="l-text-l"></div><div class="l-text-r"></div>'); 
            g.wrapper.append(g.link);
            //添加个包裹，
            g.textwrapper = g.wrapper.wrap('<div class="l-text-wrapper"></div>').parent();

            if (p.absolute)
                g.selectBox.appendTo('body').addClass("l-box-select-absolute");
            else
                g.textwrapper.append(g.selectBox);

            g.textwrapper.append(g.valueField);
            g.inputText.addClass("l-text-field");
            if (p.isShowCheckBox && !g.select)
            {
                $("table", g.selectBox).addClass("l-table-checkbox");
            } else
            {
                p.isShowCheckBox = false;
                $("table", g.selectBox).addClass("l-table-nocheckbox");
            }  
            //开关 事件
            g.link.hover(function ()
            {
                if (p.disabled || p.readonly) return;
                this.className = "l-trigger-hover";
            }, function ()
            {
                if (p.disabled || p.readonly) return;
                this.className = "l-trigger";
            }).mousedown(function ()
            {
                if (p.disabled || p.readonly) return;
                this.className = "l-trigger-pressed";
            }).mouseup(function ()
            {
                if (p.disabled || p.readonly) return;
                this.className = "l-trigger-hover";
            }).click(function ()
            {
                if (p.disabled || p.readonly) return;
                if (g.trigger('beforeOpen') == false) return false;
                g._toggleSelectBox(g.selectBox.is(":visible"));
            });
            g.inputText.click(function ()
            {
                if (p.disabled || p.readonly) return;
                if (g.trigger('beforeOpen') == false) return false;
                g._toggleSelectBox(g.selectBox.is(":visible"));
            }).blur(function ()
            {
                if (p.disabled) return;
                g.wrapper.removeClass("l-text-focus");
            }).focus(function ()
            {
                if (p.disabled || p.readonly) return;
                g.wrapper.addClass("l-text-focus");
            });
            g.wrapper.hover(function ()
            {
                if (p.disabled || p.readonly) return;
                g.wrapper.addClass("l-text-over");
            }, function ()
            {
                if (p.disabled || p.readonly) return;
                g.wrapper.removeClass("l-text-over");
            });
            g.resizing = false;
            g.selectBox.hover(null, function (e)
            {
                if (p.hideOnLoseFocus && g.selectBox.is(":visible") && !g.boxToggling && !g.resizing)
                {
                    g._toggleSelectBox(true);
                }
            }); 
            //下拉框内容初始化
            g.bulidContent();

            g.set(p); 
            //下拉框宽度、高度初始化   
            if (p.selectBoxWidth)
            {
                g.selectBox.width(p.selectBoxWidth);
            }
            else
            {
                g.selectBox.css('width', g.wrapper.css('width'));
            }
            if (p.grid) {
                g.bind('show', function () {
                    if (!g.grid) {
                        g.setGrid(p.grid);
                        g.set('SelectBoxHeight', p.selectBoxHeight);
                    }
                });
            } 
            g.updateSelectBoxPosition();
            $(document).bind("click.combobox", function (e)
            {
                var tag = (e.target || e.srcElement).tagName.toLowerCase(); 
                if (tag == "html" || tag == "body")
                {
                    g._toggleSelectBox(true);
                }
            });
        },
        destroy: function ()
        {
            if (this.wrapper) this.wrapper.remove();
            if (this.selectBox) this.selectBox.remove();
            this.options = null;
            $.ligerui.remove(this);
        },
        clear : function()
        {
            this._changeValue("", "");
            this.trigger('clear');
        },
        _setSelectBoxHeight: function (height) {
            if (!height) return;
            var g = this, p = this.options;
            if (p.grid) {
                g.grid && g.grid.set('height', g.getGridHeight(height));
            } else if(!p.table) {
                var itemsleng = $("tr", g.selectBox.table).length;
                if (!p.selectBoxHeight && itemsleng < 8) p.selectBoxHeight = itemsleng * 30;
                if (p.selectBoxHeight) {
                    g.selectBox.height(p.selectBoxHeight);
                }
            }
        }, 
        _setCss : function(css)
        {
            if (css) {
                this.wrapper.addClass(css);
            } 
        }, 
        //取消选择 
        _setCancelable: function (value)
        {
            var g = this, p = this.options;
            if (!value && g.unselect) {
                g.unselect.remove();
                g.unselect = null;
            }
            if (!value && !g.unselect) return;
            g.unselect = $('<div class="l-trigger l-trigger-cancel"><div class="l-trigger-icon"></div></div>').hide();
            g.wrapper.hover(function () { 
                g.unselect.show();
            }, function () { 
                g.unselect.hide();
            })
            if (!p.disabled && !p.readonly && p.cancelable) {
                g.wrapper.append(g.unselect);
            }
            g.unselect.hover(function () {
                this.className = "l-trigger-hover l-trigger-cancel";
            }, function () {
                this.className = "l-trigger l-trigger-cancel";
            }).click(function () {
                g.clear();
                if(g.tree){
                	var checkedNotes = g.treeManager.getChecked();
                	if(checkedNotes && checkedNotes != null && checkedNotes.length > 0){
                		for (var i = 0; i < checkedNotes.length; i++){
                			g.treeManager.cancelCheck(checkedNotes[i].data);
                		}
                	}
                }
            });
        },
        _setDisabled: function (value)
        {
            //禁用样式
            if (value)
            {
                this.wrapper.addClass('l-text-disabled');
            } else
            {
                this.wrapper.removeClass('l-text-disabled');
            }
        },
        _setReadonly: function (readonly)
        { 
            if (readonly)
            { 
                this.wrapper.addClass("l-text-readonly");
            } else
            { 
                this.wrapper.removeClass("l-text-readonly");
            }
        },
        _setLable: function (label)
        {
            var g = this, p = this.options;
            if (label)
            {
                if (g.labelwrapper)
                {
                    g.labelwrapper.find(".l-text-label:first").html(label + ':&nbsp');
                }
                else
                {
                    g.labelwrapper = g.textwrapper.wrap('<div class="l-labeltext"></div>').parent();
                    g.labelwrapper.prepend('<div class="l-text-label" style="float:left;display:inline;">' + label + ':&nbsp</div>');
                    g.textwrapper.css('float', 'left');
                }
                if (!p.labelWidth)
                {
                    p.labelWidth = $('.l-text-label', g.labelwrapper).outerWidth();
                }
                else
                {
                    $('.l-text-label', g.labelwrapper).outerWidth(p.labelWidth);
                }
                $('.l-text-label', g.labelwrapper).width(p.labelWidth);
                $('.l-text-label', g.labelwrapper).height(g.wrapper.height());
                g.labelwrapper.append('<br style="clear:both;" />');
                if (p.labelAlign)
                {
                    $('.l-text-label', g.labelwrapper).css('text-align', p.labelAlign);
                }
                g.textwrapper.css({ display: 'inline' });
                g.labelwrapper.width(g.wrapper.outerWidth() + p.labelWidth + 2);
            }
        },
        _setWidth: function (value)
        {
            var g = this, p = this.options;
            if (value > 20)
            {
                g.wrapper.css({ width: value });
                g.inputText.css({ width: value - 20 });
                g.textwrapper.css({ width: value });
                if (!p.selectBoxWidth) {
                    g.selectBox.css({ width: value });
                }
            }
        },
        _setHeight: function (value)
        {
            var g = this;
            if (value > 10)
            {
                g.wrapper.height(value);
                g.inputText.height(value - 2); 
                g.textwrapper.css({ width: value });
            }
        },
        _setResize: function (resize)
        {
            var g = this, p = this.options; 
            if (p.columns) {
                return;
            }
            //调整大小支持
            if (resize && $.fn.ligerResizable)
            { 
                g.selectBox.ligerResizable({ handles: 'se,s,e', onStartResize: function ()
                {
                    g.resizing = true;
                    g.trigger('startResize');
                }, onEndResize: function ()
                {
                    g.resizing = false;
                    if (g.trigger('endResize') == false)
                        return false;
                }, onStopResize: function (current, e) {
                    if (g.grid) {
                        if (current.newWidth) {
                            g.selectBox.width(current.newWidth);
                        }
                        if (current.newHeight) {
                            g.set({ selectBoxHeight: current.newHeight });
                        }
                        g.grid.refreshSize();
                        g.trigger('endResize');
                        return false;
                    }
                    return true;
                }
                });
                g.selectBox.append("<div class='l-btn-nw-drop'></div>");
            }
        },
        //查找Text,适用多选和单选
        findTextByValue: function (value)
        {
            var g = this, p = this.options;
            if (value == null) return "";
            var texts = "";
            var contain = function (checkvalue)
            {
                var targetdata = value.toString().split(p.split);
                for (var i = 0; i < targetdata.length; i++)
                {
                    if (targetdata[i] == checkvalue) return true;
                }
                return false;
            };
            $(g.data).each(function (i, item)
            {
                var val = item[p.valueField];
                var txt = item[p.textField];
                if (contain(val))
                {
                    texts += txt + p.split;
                }
            });
            if (texts.length > 0) texts = texts.substr(0, texts.length - 1);
            return texts;
        },
        //查找Value,适用多选和单选
        findValueByText: function (text)
        {
            var g = this, p = this.options;
            if (!text && text == "") return "";
            var contain = function (checkvalue)
            {
                var targetdata = text.toString().split(p.split);
                for (var i = 0; i < targetdata.length; i++)
                {
                    if (targetdata[i] == checkvalue) return true;
                }
                return false;
            };
            var values = "";
            $(g.data).each(function (i, item)
            {
                var val = item[p.valueField];
                var txt = item[p.textField];
                if (contain(txt))
                {
                    values += val + p.split;
                }
            });
            if (values.length > 0) values = values.substr(0, values.length - 1);
            return values;
        },
        removeItem: function ()
        {
        },
        insertItem: function ()
        {
        },
        addItem: function ()
        {

        },
        _setValue: function (value,text)
        {
            var g = this, p = this.options;  
            text = g.findTextByValue(value);
            if (p.tree)
            {
                g.selectValueByTree(value);
            }
            else if (!p.isMultiSelect)
            {
                g._changeValue(value, text);
                $("tr[value='" + value + "'] td", g.selectBox).addClass("l-selected");
                $("tr[value!='" + value + "'] td", g.selectBox).removeClass("l-selected");
            }
            else
            {
                g._changeValue(value, text);
                if (value != null) {
                    var targetdata = value.toString().split(p.split);
                    $("table.l-table-checkbox :checkbox", g.selectBox).each(function () { this.checked = false; });
                    for (var i = 0; i < targetdata.length; i++) {
                        $("table.l-table-checkbox tr[value=" + targetdata[i] + "] :checkbox", g.selectBox).each(function () { this.checked = true; });
                    }
                }
            }
        },
        selectValue: function (value)
        {
            this._setValue(value);
        },
        bulidContent: function ()
        {
            var g = this, p = this.options;
            this.clearContent();
            if (g.select)
            {
                g.setSelect();
            } 
            else if (p.tree)
            {
                g.setTree(p.tree);
            }
        },
        reload: function ()
        {
            var g = this, p = this.options;
            if (p.url)
            {
                g.set('url', p.url);
            }
            else if (g.grid)
            {
                g.grid.reload();
            }
        },
        _setUrl: function (url) {
            if (!url) return;
            var g = this, p = this.options;
            var parms = $.isFunction(p.parms) ? p.parms() : p.parms;
            $.ajax({
                type: p.ajaxType,
                url: url,
                data: parms,
                cache: false,
                dataType: 'json',
                success: function (data) { 
                    g.setData(data);
                    g.trigger('success', [data]);
                },
                error: function (XMLHttpRequest, textStatus) {
                    g.trigger('error', [XMLHttpRequest, textStatus]);
                }
            });
        },
        setUrl: function (url) {
            return this._setUrl(url);
        },
        setParm: function (name, value) {
            if (!name) return;
            var g = this;
            var parms = g.get('parms');
            if (!parms) parms = {};
            parms[name] = value;
            g.set('parms', parms); 
        },
        clearContent: function ()
        {
            var g = this, p = this.options;
            $("table", g.selectBox).html("");
            //g.inputText.val("");
            //g.valueField.val("");
        },
        setSelect: function ()
        {
            var g = this, p = this.options;
            this.clearContent();
            $('option', g.select).each(function (i)
            {
                var val = $(this).val();
                var txt = $(this).html();
                var tr = $("<tr><td index='" + i + "' value='" + val + "' text='" + txt + "'>" + txt + "</td>");
                $("table.l-table-nocheckbox", g.selectBox).append(tr);
                $("td", tr).hover(function ()
                {
                    $(this).addClass("l-over");
                }, function ()
                {
                    $(this).removeClass("l-over");
                });
            });
            $('td:eq(' + g.select[0].selectedIndex + ')', g.selectBox).each(function ()
            {
                if ($(this).hasClass("l-selected"))
                {
                    g.selectBox.hide();
                    return;
                }
                $(".l-selected", g.selectBox).removeClass("l-selected");
                $(this).addClass("l-selected");
                if (g.select[0].selectedIndex != $(this).attr('index') && g.select[0].onchange)
                {
                    g.select[0].selectedIndex = $(this).attr('index'); g.select[0].onchange();
                }
                var newIndex = parseInt($(this).attr('index'));
                g.select[0].selectedIndex = newIndex;
                g.select.trigger("change");
                g.selectBox.hide();
                var value = $(this).attr("value");
                var text = $(this).html();
                if (p.render)
                {
                    g.inputText.val(p.render(value, text));
                }
                else
                {
                    g.inputText.val(text);
                }
            });
            g._addClickEven();
        },
        _setData : function(data)
        {
            this.setData(data);
        },
        setData: function (data)
        {
            var g = this, p = this.options; 
            if (!data || !data.length) return;
            if (g.data != data) g.data = data;
            this.clearContent();
            if (p.columns)
            {
                g.selectBox.table.headrow = $("<tr class='l-table-headerow'><td width='18px'></td></tr>");
                g.selectBox.table.append(g.selectBox.table.headrow);
                g.selectBox.table.addClass("l-box-select-grid");
                for (var j = 0; j < p.columns.length; j++)
                {
                    var headrow = $("<td columnindex='" + j + "' columnname='" + p.columns[j].name + "'>" + p.columns[j].header + "</td>");
                    if (p.columns[j].width)
                    {
                        headrow.width(p.columns[j].width);
                    }
                    g.selectBox.table.headrow.append(headrow);

                }
            }
            var out = [];
            for (var i = 0; i < data.length; i++)
            {
                var val = data[i][p.valueField];
                var txt = data[i][p.textField];
                if (!p.columns)
                {
                    out.push("<tr value='" + val + "'>");
                    if(p.isShowCheckBox){
                        out.push("<td style='width:18px;'  index='" + i + "' value='" + val + "' text='" + txt + "' ><input type='checkbox' /></td>");
                    }
                    var itemHtml = txt;
                    if (p.renderItem) {
                        itemHtml = p.renderItem.call(g, {
                            data: data[i],
                            value: val,
                            text: txt,
                            key: g.inputText.val()
                        });
                    } else if (p.autocomplete)
                    {
                        itemHtml = g._highLight(txt, g.inputText.val());
                    }
                    out.push("<td index='" + i + "' value='" + val + "' text='" + txt + "' align='left'>" + itemHtml + "</td></tr>");
                } else
                {
                    out.push("<tr value='" + val + "'><td style='width:18px;'  index='" + i + "' value='" + val + "' text='" + txt + "' ><input type='checkbox' /></td>");
                    for (var j = 0; j < p.columns.length; j++) {
                        var columnname = p.columns[j].name;
                        out.push("<td>" + data[i][columnname] + "</td>");
                    }
                    out.push('</tr>');  
                }
            } 
            if (!p.columns) {
                if (p.isShowCheckBox) {
                    $("table.l-table-checkbox", g.selectBox).append(out.join(''));
                }else{
                    $("table.l-table-nocheckbox", g.selectBox).append(out.join(''));
                }
            } else { 
                g.selectBox.table.append(out.join(''));
            }
            //自定义复选框支持
            if (p.isShowCheckBox && $.fn.ligerCheckBox)
            {
                $("table input:checkbox", g.selectBox).ligerCheckBox();
            }
            $(".l-table-checkbox input:checkbox", g.selectBox).change(function ()
            {
                if (this.checked && g.hasBind('beforeSelect'))
                {
                    var parentTD = null;
                    if ($(this).parent().get(0).tagName.toLowerCase() == "div")
                    {
                        parentTD = $(this).parent().parent();
                    } else
                    {
                        parentTD = $(this).parent();
                    }
                    if (parentTD != null && g.trigger('beforeSelect', [parentTD.attr("value"), parentTD.attr("text")]) == false)
                    {
                        g.selectBox.slideToggle("fast");
                        return false;
                    }
                }
                if (!p.isMultiSelect)
                {
                    if (this.checked)
                    {
                        $("input:checked", g.selectBox).not(this).each(function ()
                        {
                            this.checked = false;
                            $(".l-checkbox-checked", $(this).parent()).removeClass("l-checkbox-checked");
                        });
                        g.selectBox.slideToggle("fast");
                    }
                }
                g._checkboxUpdateValue();
            });
            $("table.l-table-nocheckbox td", g.selectBox).hover(function ()
            {
                $(this).addClass("l-over");
            }, function ()
            {
                $(this).removeClass("l-over");
            });
            g._addClickEven();
            //选择项初始化
            if (!p.autocomplete) {
                g.updateStyle();
            }
        },
        //树
        setTree: function (tree)
        {
            var g = this, p = this.options;
            this.clearContent();
            g.selectBox.table.remove();
            if (tree.checkbox != false)
            {
                tree.onCheck = function ()
                {
                    var nodes = g.treeManager.getChecked();
                    var value = [];
                    var text = [];
                    $(nodes).each(function (i, node)
                    {
                        if (p.treeLeafOnly && node.data.children) return;
                        value.push(node.data[p.valueField]);
                        text.push(node.data[p.textField]);
                    });
                    g._changeValue(value.join(p.split), text.join(p.split));
                };
            }
            else
            {
                tree.onSelect = function (node)
                {
                    if (g.trigger('BeforeSelect'[node]) == false) return;
                    if (p.treeLeafOnly && node.data.children) return;
                    var value = node.data[p.valueField];
                    var text = node.data[p.textField];
                    g._changeValue(value, text);
                };
                tree.onCancelSelect = function (node)
                {
                    g._changeValue("", "");
                };
            }
            tree.onAfterAppend = function (domnode, nodedata)
            {
                if (!g.treeManager) return;
                var value = null;
                if (p.initValue) value = p.initValue;
                else if (g.valueField.val() != "") value = g.valueField.val();
                g.selectValueByTree(value);
            };
            g.tree = $("<ul></ul>");
            $("div:first", g.selectBox).append(g.tree);
            g.tree.ligerTree(tree);
            g.treeManager = g.tree.ligerGetTreeManager();
            
            //默认选择对应项目
            var selectValue=tree.selectValue;
            if(selectValue != null && selectValue != undefined && selectValue != ""){
            	var aryValues = selectValue.split(",");
            	var aryTxt = new Array();
            	for(var i = 0;i < aryValues.length; i ++){
            		var value = aryValues[i];
            		var txt = g.treeManager.getTextByID(value);
            		aryTxt.push(txt);
            		
            		//带Checkbox
            		if(tree.checkbox){
            			g.treeManager.checkNode(value);
            		}
            		//不带CheckBox
            		else{
            			g.treeManager.selectNode(value);
            		}
            	}
            	g._changeValue(selectValue, aryTxt.join(","));
            }
        },
        selectValueByTree: function (value)
        {
            var g = this, p = this.options;
            if (value != null)
            {
                var text = "";
                var valuelist = value.toString().split(p.split);
                $(valuelist).each(function (i, item)
                {
                    g.treeManager.selectNode(item.toString());
                    text += g.treeManager.getTextByID(item);
                    if (i < valuelist.length - 1) text += p.split;
                });
                g._changeValue(value, text);
            }
        },
        //表格
        setGrid: function (grid)
        {
            var g = this, p = this.options;
            if (g.grid) return; 
            p.hideOnLoseFocus = p.hideGridOnLoseFocus ? true : false;
            this.clearContent();
            g.selectBox.addClass("l-box-select-lookup");
            g.selectBox.table.remove();
            var panel = $("div:first", g.selectBox);
            var conditionPanel = $("<div></div>").appendTo(panel);
            var gridPanel = $("<div></div>").appendTo(panel);
            g.conditionPanel = conditionPanel;
            //搜索框
            if (p.condition) {
                var conditionParm = $.extend({
                    labelWidth: 60,
                    space: 20
                }, p.condition); 
                g.condition = conditionPanel.ligerForm(conditionParm);
            } else {
                conditionPanel.remove();
            }
            //列表
            grid = $.extend({
                columnWidth: 120,
                alternatingRow: true,
                frozen: true,
                rownumbers: true,
                allowUnSelectRow:true
            }, grid, {
                width: "100%",
                height: g.getGridHeight(),
                inWindow: false,
                parms : p.parms,
                isChecked: function (rowdata) {
                    var value = g.getValue();
                    if (!value) return false;
                    if (!p.valueField || !rowdata[p.valueField]) return false;
                    return $.inArray(rowdata[p.valueField].toString(), value.split(p.split)) != -1;
                }
            });
            g.grid = g.gridManager = gridPanel.ligerGrid(grid);
            g.grid.bind('afterShowData', function ()
            { 
                g.updateSelectBoxPosition();
            });
            var selecteds = [], onGridSelect = function () { 
                var value = [], text = [];
                $(selecteds).each(function (i, rowdata) {
                    value.push(rowdata[p.valueField]);
                    text.push(rowdata[p.textField]);
                });
                if (grid.checkbox)
                    g.selected = selecteds;
                else if (selecteds.length)
                    g.selected = selecteds[0];
                else
                    g.selected = null;
                g._changeValue(value.join(p.split), text.join(p.split));
                g.trigger('gridSelect', {
                    value: value.join(p.split),
                    text: text.join(p.split),
                    data: selecteds
                });
            }, removeSelected = function (rowdata) {
                for (var i = selecteds.length - 1; i >= 0; i--) {
                    if (selecteds[i][p.valueField] == rowdata[p.valueField]) {
                        selecteds.splice(i, 1);
                    }
                }
            }, addSelected = function (rowdata) {
                for (var i = selecteds.length - 1; i >= 0; i--) {
                    if (selecteds[i][p.valueField] == rowdata[p.valueField]) {
                        return;
                    }
                }
                selecteds.push(rowdata);
            };
            if (grid.checkbox)
            {
                var onCheckRow = function (checked, rowdata) {
                    checked && addSelected(rowdata);
                    !checked && removeSelected(rowdata);
                };
                g.grid.bind('CheckAllRow', function (checked) {
                    $(g.grid.rows).each(function (rowdata) {
                        onCheckRow(checked, rowdata);
                    });
                    onGridSelect();
                });
                g.grid.bind('CheckRow', function (checked, rowdata) {
                    onCheckRow(checked, rowdata);
                    onGridSelect();
                });
            }
            else
            {
                g.grid.bind('SelectRow', function (rowdata) {
                    selecteds = [rowdata]; 
                    onGridSelect();
                    g._toggleSelectBox(true);
                });
                g.grid.bind('UnSelectRow', function () {
                    selecteds = [];
                    onGridSelect();
                });
            }
            g.bind('show', function () {
                g.grid.refreshSize();
            });
            g.bind("clear", function () {
                selecteds = [];
                g.grid.selecteds = [];
                g.grid._showData();
            });
            if (p.condition) {
                var containerBtn1 = $('<li style="margin-right:9px"><div></div></li>');
                var containerBtn2 = $('<li style="margin-right:9px;float:right"><div></div></li>');
                $("ul:first", conditionPanel).append(containerBtn1).append(containerBtn2).after('<div class="l-clear"></div>');
                $("div", containerBtn1).ligerButton({
                    text: p.Search, width: 40,
                    click: function () { 
                        var rules = g.condition.toConditions();
                        g.grid.setParm(grid.conditionParmName || 'condition', $.ligerui.toJSON(rules));
                        g.grid.reload();
                    }
                });
                $("div", containerBtn2).ligerButton({
                    text: '关闭',width:40,
                    click: function () {
                        g.selectBox.hide();
                    }
                });
            }
            g.grid.refreshSize();
        },
        getGridHeight: function (height) {
            var g = this, p = this.options;
            height = height || g.selectBox.height();
            height -= g.conditionPanel.height();
            return height;
        },
        _getValue: function ()
        {
            return $(this.valueField).val();
        },
        getValue: function ()
        {
            //获取值
            return this._getValue();
        },
        getSelected : function()
        {
            return this.selected;
        },
        getText: function () {
            return this.inputText.val();
        },
        setText: function (value) {
            this.inputText.val(value);
        },
        updateStyle: function ()
        {
            var g = this, p = this.options;
            p.initValue = g._getValue();
            g._dataInit();
        },
        _dataInit: function ()
        {
            var g = this, p = this.options;
            var value = null; 
            if (p.initValue != null && p.initText != null)
            {
                g._changeValue(p.initValue, p.initText);
            }
            //根据值来初始化
            if (p.initValue != null)
            {
                value = p.initValue;
                if (p.tree)
                {
                    if(value)
                        g.selectValueByTree(value);
                }
                else if (g.data)
                {
                    var text = g.findTextByValue(value);
                    g._changeValue(value, text);
                }
            } 
            else if (g.valueField.val() != "")
            {
                value = g.valueField.val();
                if (p.tree)
                {
                    if(value)
                        g.selectValueByTree(value);
                }
                else if(g.data)
                {
                    var text = g.findTextByValue(value);
                    g._changeValue(value, text);
                }
            }
            if (!p.isShowCheckBox)
            {
                $("table tr", g.selectBox).find("td:first").each(function ()
                {
                    if (value != null && value == $(this).attr("value"))
                    {
                        $(this).addClass("l-selected");
                    } else
                    {
                        $(this).removeClass("l-selected");
                    }
                });
            }
            else
            { 
                $(":checkbox", g.selectBox).each(function ()
                {
                    var parentTD = null;
                    var checkbox = $(this);
                    if (checkbox.parent().get(0).tagName.toLowerCase() == "div")
                    {
                        parentTD = checkbox.parent().parent();
                    } else
                    {
                        parentTD = checkbox.parent();
                    }
                    if (parentTD == null) return;
                    $(".l-checkbox", parentTD).removeClass("l-checkbox-checked");
                    checkbox[0].checked = false;
                    var valuearr = (value || "").toString().split(p.split);
                    $(valuearr).each(function (i, item)
                    {
                        if (value != null && item == parentTD.attr("value"))
                        {
                            $(".l-checkbox", parentTD).addClass("l-checkbox-checked");
                            checkbox[0].checked = true;
                        }
                    });
                });
            }
        },
        //设置值到 文本框和隐藏域
        _changeValue: function (newValue, newText)
        {
            var g = this, p = this.options; 
            g.valueField.val(newValue);
            if (p && p.render)
            {
                g.inputText.val(p.render(newValue, newText));
            }
            else
            {
                g.inputText.val(newText);
            }
            g.selectedValue = newValue;
            g.selectedText = newText;
            g.inputText.trigger("change").focus(); 
            g.trigger('selected', [newValue, newText]); 
        },
        //更新选中的值(复选框)
        _checkboxUpdateValue: function ()
        {
            var g = this, p = this.options;
            var valueStr = "";
            var textStr = "";
            $("input:checked", g.selectBox).each(function ()
            {
                var parentTD = null;
                if ($(this).parent().get(0).tagName.toLowerCase() == "div")
                {
                    parentTD = $(this).parent().parent();
                } else
                {
                    parentTD = $(this).parent();
                }
                if (!parentTD) return;
                valueStr += parentTD.attr("value") + p.split;
                textStr += parentTD.attr("text") + p.split;
            });
            if (valueStr.length > 0) valueStr = valueStr.substr(0, valueStr.length - 1);
            if (textStr.length > 0) textStr = textStr.substr(0, textStr.length - 1);
            g._changeValue(valueStr, textStr);
        },
        _addClickEven: function ()
        {
            var g = this, p = this.options;
            //选项点击
            $(".l-table-nocheckbox td", g.selectBox).click(function ()
            {
                var value = $(this).attr("value");
                var index = parseInt($(this).attr('index'));
                var text = $(this).attr("text");
                if (g.hasBind('beforeSelect') && g.trigger('beforeSelect', [value, text]) == false)
                {
                    if (p.slide) g.selectBox.slideToggle("fast");
                    else g.selectBox.hide();
                    return false;
                }
                if ($(this).hasClass("l-selected"))
                {
                    if (p.slide) g.selectBox.slideToggle("fast");
                    else g.selectBox.hide();
                    return;
                }
                $(".l-selected", g.selectBox).removeClass("l-selected");
                $(this).addClass("l-selected");
                if (g.select)
                {
                    if (g.select[0].selectedIndex != index)
                    {
                        g.select[0].selectedIndex = index;
                        g.select.trigger("change");
                    }
                }
                if (p.slide)
                {
                    g.boxToggling = true;
                    g.selectBox.hide("fast", function ()
                    {
                        g.boxToggling = false;
                    })
                } else g.selectBox.hide();
                g._changeValue(value, text);
            });
        },
        updateSelectBoxPosition: function ()
        {
            var g = this, p = this.options; 
            if (p && p.absolute)
            {
                var contentHeight = $(document).height();
                if (p.alwayShowInTop || Number(g.wrapper.offset().top + 1 + g.wrapper.outerHeight() + g.selectBox.height()) > contentHeight
            			&& contentHeight > Number(g.selectBox.height() + 1))
                {
                    //若下拉框大小超过当前document下边框,且当前document上留白大于下拉内容高度,下拉内容向上展现
                    g.selectBox.css({ left: g.wrapper.offset().left, top: g.wrapper.offset().top - 1 - g.selectBox.height() });
                } else
                {
                    g.selectBox.css({ left: g.wrapper.offset().left, top: g.wrapper.offset().top + 1 + g.wrapper.outerHeight() });
                } 
            }
            else
            {
                var topheight = g.wrapper.offset().top - $(window).scrollTop();
                var selfheight = g.selectBox.height() + textHeight + 4;
                if (topheight + selfheight > $(window).height() && topheight > selfheight)
                {
                    g.selectBox.css("marginTop", -1 * (g.selectBox.height() + textHeight + 5));
                }
            }
        },
        _toggleSelectBox: function (isHide)
        {
            var g = this, p = this.options;
            var textHeight = g.wrapper.height();
            g.boxToggling = true;
            if (isHide)
            {
                if (p.slide)
                {
                    g.selectBox.slideToggle('fast', function ()
                    {
                        g.boxToggling = false;
                    });
                }
                else
                {
                    g.selectBox.hide();
                    g.boxToggling = false;
                }
            }
            else
            {
                g.updateSelectBoxPosition();
                if (p.slide)
                {
                    g.selectBox.slideToggle('fast', function ()
                    {
                        g.boxToggling = false;
                        if (!p.isShowCheckBox && $('td.l-selected', g.selectBox).length > 0)
                        {
                            var offSet = ($('td.l-selected', g.selectBox).offset().top - g.selectBox.offset().top);
                            $(".l-box-select-inner", g.selectBox).animate({ scrollTop: offSet });
                        }
                    });
                }
                else
                {
                    g.selectBox.show();
                    g.boxToggling = false;
                    if (!g.tree && !g.grid && !p.isShowCheckBox && $('td.l-selected', g.selectBox).length > 0)
                    {
                        var offSet = ($('td.l-selected', g.selectBox).offset().top - g.selectBox.offset().top);
                        $(".l-box-select-inner", g.selectBox).animate({ scrollTop: offSet });
                    }
                }
            }
            g.isShowed = g.selectBox.is(":visible");
            g.trigger('toggle', [isHide]);
            g.trigger(isHide ? 'hide' : 'show');
        }, 
        _highLight: function (str, key)
        {
            var index = str.indexOf(key);
            if (index == -1) return str;
            return str.substring(0, index) + "<span class='l-highLight'>" + key + "</span>" + str.substring(key.length + index);
        },
        _setAutocomplete: function (value) {
            var g = this, p = this.options;
            if (!value) return;
            g.inputText.removeAttr("readonly");
            var lastText = g.inputText.val();
            g.inputText.keyup(function ()
            {
                setTimeout(function ()
                {
                    if (lastText == g.inputText.val()) return;
                    p.initValue = "";
                    g.valueField.val("");
                    if (p.url)
                    {
                        g.setParm('key', g.inputText.val());
                        g.set('url', p.url);
                        g.selectBox.show();
                    } else if (p.grid)
                    {
                        g.grid.setParm('key', g.inputText.val());
                        g.grid.reload();
                    } 
                    lastText = g.inputText.val();
                }, 1);
            });
        }
    });

    $.ligerui.controls.ComboBox.prototype.setValue = $.ligerui.controls.ComboBox.prototype.selectValue;
    //设置文本框和隐藏控件的值
    $.ligerui.controls.ComboBox.prototype.setInputValue = $.ligerui.controls.ComboBox.prototype._changeValue;
    

})(jQuery);

//获取应用路径
var urlpath = window.document.location.pathname;
var i = urlpath.indexOf("/");
if(i==0)
{
	urlpath=urlpath.substr(1);
}
i = urlpath.indexOf("/");
urlpath = "/" + urlpath.substr(0,i + 1);   
   

/**json**/
if (!this.JSON2)
{
    this.JSON = this.JSON2 = {};
}

(function () {
    "use strict";

    function f(n) {
        // Format integers to have at least two digits.
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function (key) {

            return isFinite(this.valueOf()) ?
                   this.getUTCFullYear()   + '-' +
                 f(this.getUTCMonth() + 1) + '-' +
                 f(this.getUTCDate())      + 'T' +
                 f(this.getUTCHours())     + ':' +
                 f(this.getUTCMinutes())   + ':' +
                 f(this.getUTCSeconds())   + 'Z' : null;
        };

        String.prototype.toJSON =
        Number.prototype.toJSON =
        Boolean.prototype.toJSON = function (key) {
            return this.valueOf();
        };
    }

    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = {    // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        },
        rep;


    function quote(string) {

        escapable.lastIndex = 0;
        return escapable.test(string) ?
            '"' + string.replace(escapable, function (a) {
                var c = meta[a];
                return typeof c === 'string' ? c :
                    '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) + '"' :
            '"' + string + '"';
    }

    function str(key, holder) {
        var i,          // The loop counter.
            k,          // The member key.
            v,          // The member value.
            length,
            mind = gap,
            partial,
            value = holder[key];
        if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }
        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }
        switch (typeof value) {
        case 'string':
            return quote(value);
        case 'number':
            return isFinite(value) ? String(value) : 'null';
        case 'boolean':
        case 'null':
            return String(value);
        case 'object':
            if (!value) {
                return 'null';
            }
            gap += indent;
            partial = [];
            if (Object.prototype.toString.apply(value) === '[object Array]') {
                length = value.length;
                for (i = 0; i < length; i += 1) {
                    partial[i] = str(i, value) || 'null';
                }
                v = partial.length === 0 ? '[]' :
                    gap ? '[\n' + gap +
                            partial.join(',\n' + gap) + '\n' +
                                mind + ']' :
                          '[' + partial.join(',') + ']';
                gap = mind;
                return v;
            }
            if (rep && typeof rep === 'object') {
                length = rep.length;
                for (i = 0; i < length; i += 1) {
                    k = rep[i];
                    if (typeof k === 'string') {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            } else {
                for (k in value) {
                    if (Object.hasOwnProperty.call(value, k)) {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            }
            v = partial.length === 0 ? '{}' :
                gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' +
                        mind + '}' : '{' + partial.join(',') + '}';
            gap = mind;
            return v;
        }
    }
    if (typeof JSON2.stringify !== 'function') {
        JSON2.stringify = function (value, replacer, space) {
            var i;
            gap = '';
            indent = '';
            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }
            } else if (typeof space === 'string') {
                indent = space;
            }
            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                     typeof replacer.length !== 'number')) {
                throw new Error('JSON2.stringify');
            }
            return str('', {'': value});
        };
    }
    if (typeof JSON2.parse !== 'function') {
        JSON2.parse = function (text, reviver) {
            var j;
            function walk(holder, key) {
                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }
            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }
            if (/^[\],:{}\s]*$/
.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
.replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
                j = eval('(' + text + ')');
                return typeof reviver === 'function' ?
                    walk({'': j}, '') : j;
            }
            throw new SyntaxError('JSON2.parse');
        };
    }
}());

//字典
(function ($)
{
    var l = $.ligerui;

    $.fn.ligerDictionary = function (options)
    {
        return $.ligerui.run.call(this, "ligerDictionary", arguments);
    };

    $.fn.ligerGetDictionaryManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetDictionaryManager", arguments);
    };
	
    $.fn.getDicManager = function(){
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
    $.ligerMethos.Dictionary = $.ligerMethos.Dictionary || {};
    
    $.ligerui.controls.Dictionary = function (element, options)
    {
        $.ligerui.controls.Dictionary.base.constructor.call(this, element, options);
    };
    
    $.ligerDefaults.Dictionary = {
    		valueField:'code',	//值字段名称
    		labelField:'name',	//选项字段名称
    		titleField:'name',	//标题字段名称 title
    		data:null,			//json数据 {"Total":10,"Rows":data}
    		url:null,			//获取数据的url
    		dataAction:'local', //
    		searchField:'name,code,scode,jpcode', //默认是labelField 多个查询字段以,隔开,例如 searchField = 'name,code,scode'
    		pageParmName:'page',//向服务端请求数据时 提交请求当前页的参数名称
    		pagesizeParmName:'pagesize', //每页展示记录数参数名称
            sortorderParmName: 'orderBy', //提交的排序参数名称
            queryFieldParamName:'queryFields',//向服务端提交的查询字段串参数名称
            queryValueParamName:'queryValue',//向服务端提交的查询值参数名称
    		totalName:'Total',
    		recordName:'Rows',
    		parms: [],			//参数
    		width:null,			//字典宽度
    		resultWidth:null,	//结果容器宽度
    		page:1,				//当前页
    		pageSize:10,		//每页展示记录数
    		order_by:null,		//排序映射 是一个数组, 例如 :[['code','asc']],
    		hiddenField:'code',	//如果页面上有id为code且为隐藏域的dom对象则选中的字典值将会赋给该dom对象,如果不存在则新建一隐藏域并把hiddenField的值作为该隐藏域的名称
    		multi:false,		//是否多选
    		multiSplit:';',		//多选时的分隔符
    		initValue:null,		//设置初始值,当为单选时该值可以为:string,object 如果是多选时该值可以为:string Array。
    		isFixOptions:false,	//是否固定选项数目
    		checkInterval:300,	//联想时间间隔
    		method:'POST',		//请求数据的方式
    		itemClick:null,		//选中某个选项时执行的函数
    		itemRender:null,	//选项渲染函数
    		filterRegx:null,	//过滤正则表达式 {code:'^3.*?'} dataAction为server时 该参数无效
    		isEnable:true,		//字典启用、禁用开关,true:启用  false:禁用
    		showDirection:'auto',	//展示方向 auto:根据实际情况计算出展示位置,left:在左边展示,right:在右边展示,top:向上展示,bottom:向下展示
    		valueChangeHandle:null	//值改变处理事件
	};
    
    $.ligerDefaults.DictionaryString = {
    		pageStatMessage:"第<span class='red'>{currentPage}</span>页，共<span class='red'>{totalPage}</span>页"
    };
    
    $.ligerui.controls.Dictionary.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.Dictionary';
        },
        __idPrev: function ()
        {
            return 'Dictionary';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.Dictionary;
        },
        _init: function ()
        {
        	$.ligerui.controls.Dictionary.base._init.call(this);
        	var g = this, p = this.options;
        	//值字段名称
        	if(!p.valueField || $.trim(p.valueField)==''){
        		p.valueField = 'code';
        	}
        	//label字段名称
        	if(!p.labelField || $.trim(p.labelField)==''){
        		p.labelField = 'name';
        	}
        	
        	//隐藏域名称
        	if(!p.hiddenField || $.trim(p.hiddenField)==''){
        		p.hiddenField = p.valueField;
        	}
        	
        	//查询字段
        	if(!p.searchField || $.trim(p.searchField)==''){
        		p.searchField = p.labelField;
        	}
        	
        	//ajax请求方式
        	if(!p.method || $.trim(p.method)==''){
        		p.method = 'POST';
        	}
        	
        	if(typeof(p.multi) != 'boolean'){
        		p.multi = false;
        	}
        	
        	if(typeof(p.isEnable) != 'boolean'){
        		p.isEnable = true;
        	}
        	g.isEnable = p.isEnable;
        	
        	if(typeof(p.isFixOptions) != 'boolean'){
        		p.isFixOptions = true;
        	}
        	
        	if((typeof(p.showDirection) != 'string') || p.showDirection==''){
        		p.showDirection = 'auto';
        	}
        	
        	//联想时间间隔
        	if(typeof(p.checkInterval) != 'number'){
        		p.checkInterval = 500;
        	}
        	
        	//设置多选时的分隔符
        	if(!p.multiSplit || p.multiSplit.length==0) p.multiSplit=';';
        	
        	//当前查询的值
        	g.prev_value = '';
        	
        	//选中的值
        	g.selectedValues = [];
        	g.selectedDatas = [];
        	
        	//匹配到的值
        	g.matchedData = [];
        	//当前展示的选项
        	g.currentShowData = [];
        	
        	g.currentPage = 1;
        	g.totalPage = 0;
        	g.total = 0;
        	
        	//是否正在联想
        	g.is_suggest = false;
        },
        _render: function ()
        {
        	var g = this, p = this.options;
        	//输入框
        	g.searchField = $(g.element);
        	//启用
        	if(g.isEnable===true){
        		g.searchField.addClass('dic-input-enable');
        	}
        	//禁用
        	else{
        		g.searchField.addClass('dic-input-disabled');
        		g.searchField.attr("disabled",true);
        	}
        	
        	if(p.width && p.width != 'auto'){
        		var w = p.width;
        		if(typeof(p.width)=='number') w = w + 'px';
        		g.searchField.css('width',w);
        	}
        	//输入框中的文字居中
        	g.searchField.css('line-height',g.searchField.height()+"px");
        	
        	//隐藏域,设置了隐藏域的id
        	if($("input[type='hidden'][id='"+p.hiddenField+"']").length>0){
        		g.hiddenField = $("input[type='hidden'][id='"+p.hiddenField+"']");
        		g.hiddenField.addClass('dic-value');
        		g.hiddenField.attr("dicId",g.id);
        	}
        	//构建名称为p.hiddenField的隐藏域
        	else{
        		g.hiddenField = $("<input type='hidden' name='"+p.hiddenField+"' />");
        		g.hiddenField.addClass('dic-value');
        		g.hiddenField.attr("dicId",g.id);
        		g.hiddenField.insertAfter(g.searchField);
        	}
        	
        	//构建结果DIV
        	var htmlArr = [];
        	htmlArr.push("<div class='result-container' tabindex='0' hidefocus='true' style='outline:0;'>");
        	htmlArr.push("  <div class='item-container'></div>");
        	htmlArr.push("  <div class='page-container'>");
        	htmlArr.push("    <div class='page-container-num'></div>");
        	htmlArr.push("    <div class='page-container-btn'>");
        	
        	htmlArr.push("      <a class='page-btn' name='first' href='javascript:void(0)'>首页</a>");
        	htmlArr.push("      <a class='page-btn' name='prev' href='javascript:void(0)'>上页</a>");
        	htmlArr.push("      <a class='page-btn' name='next' href='javascript:void(0)'>下页</a>");
        	htmlArr.push("      <a class='page-btn' name='last' href='javascript:void(0)'>末页</a>");
        	
        	if(p.multi===true){
        		htmlArr.push("      <a class='page-btn' href='javascript:void(0)' name='clear'>清除</a>");
        	}
        	
        	htmlArr.push("    </div>");
        	htmlArr.push("  </div>");
        	htmlArr.push("</div>");
        	
        	//结果容器
        	g.resultContainer = $(htmlArr.join(''));
        	g.resultContainer.insertAfter(g.searchField);
        	
        	//设置结果容器宽度
        	var w = 0;
        	
        	if(p.resultWidth){
        		if(typeof(p.resultWidth)=='number') w = p.resultWidth;
        		if(typeof(p.resultWidth)=='string') w = parseInt(p.resultWidth);
        	}
        	
        	if(w==0)
        	{
        		w = g.searchField.width();
            	//默认宽度
            	var dw = 230;
            	//ie浏览器
            	if($.browser.msie){
            		if(p.multi===true) dw=250;
            		if(w<dw) w=dw;
            	}
            	//其他浏览器
            	else{
            		dw = 270;
            		if(p.multi===true) dw=300;
            		if(w<dw) w=dw;
            	}        		
        	}
        	
        	//浏览器边框兼容问题
        	if($.browser.msie) w+=2;
        	
        	g.resultContainer.width(w);
        	
        	g.itemContainer = $(".item-container",g.resultContainer);
        	//分页条
        	g.pageContainer = $(".page-container",g.resultContainer);
        	//页码描述容器
        	g.pageDescript = $(".page-container-num",g.resultContainer);
        	//翻页按钮容器
        	g.pageBtnContainer = $(".page-container-btn",g.resultContainer);
        	
        	//隐藏结果框
        	g.resultContainer.hide();
        	
        	//绑定字典事件
        	g._bindDicEvent();
        	
        	//绑定分页按钮事件
        	$(".page-btn",g.pageBtnContainer).bind('click',function(){
        		var btnName = $(this).attr('name');
        		g._pageBtnClick(btnName);
        	});
        	
        	//设置初值
        	var initValue = g.hiddenField.val();
        	if(p.initValue){
        		initValue = p.initValue;
        	}
        	g.setValue(initValue);
        },
        
        //匹配值
        _matchValue:function(value){
        	var g = this, p = this.options;
        	
        	g.selectedDatas = [];
        	
        	if(!value || value==''){
        		g.trigger('matchComplete');
        		return;
        	}
        	
        	if(p.dataAction=='local'){
        		if(!p.data || !p.data[p.recordName]){
        			g.trigger('matchComplete');
        			return;
        		}
	        	//单选
	        	if(p.multi===false){
        			for(var i in p.data[p.recordName]){
        				var item = p.data[p.recordName][i];
        				if(item[p.valueField]==value){
        					g.selectedDatas.push(item);
        					break;
        				}
        			}
	        	}
	        	//多选
	        	else if(p.multi===true){
	        		var ary = value.split(',');
        			for(var i in p.data[p.recordName]){
        				var item = p.data[p.recordName][i];
        				var isMatch = false;
        				for(var j in ary){
							if(item[p.valueField]==ary[j]){
								isMatch = true;
								break;
							}
        				}
        				if(isMatch){
        					g.selectedDatas.push(item);
        				}
        			}
	        	}
	        	g.trigger('matchComplete');
        	}
        	
        	else if(p.dataAction=='server'){
				if(!p.url || p.url.length==0){
	        		g.trigger('matchComplete');
	        		return;
				}
				
				var qr_word = value;
				
				//终止前面的请求
				g._abortAjax();
				
				//获取请求参数
				var params = g._getAjaxSubmitParams();
				
				//移除联想提交参数
				removeArrItem(params,function(param){
					return param.name == p.queryFieldParamName;
				});
				removeArrItem(params,function(param){
					return param.name == p.queryValueParamName;
				});
				
				//重新设置联想提交参数
				params.push({name:p.queryFieldParamName,value:p.valueField});      //提交查询字段 2014/02/28
				params.push({name:p.queryValueParamName,value:qr_word});		   //提交查询值 2014/02/28
				
				//请求数据
				g.xhr = $.ajax({
					url:p.url,
					type:p.method,
					data:params,
					dataType:'json',
					success:function(json){
						if(!json) json={};
						if(!json[p.totalName]) json[p.totalName] = 0;
						if(!json[p.recordName]) json[p.recordName] = [];
						g.selectedDatas = g.selectedDatas.concat(json[p.recordName]);
						g.trigger('matchComplete');
					}
				});
        	}
        },
        
        //选中字典项 当为单选时val可以为:string,object 为多选时val可以为:string,Array。
        setValue:function(value){
        	var g = this, p = this.options;
        	var val = "";
        	if(value){
        		//字符串、数字
        		if($.type(value)=='string' || $.type(value)=='number'){
        			val = value;
        		}
        		//数组
        		else if($.isArray(value) && value.length>0){
        			//类似：['1','2','3']
        			if($.type(value[0])=='string' || $.type(value[0])=='number'){
        				val = value.join(",");
        			}
        			//类似：[{code:'1',name:'a'},{code:'2',name:'b'}]
        			else{
        				var vls = [];
        				for(var j in value){
        					if(value[j][p.valueField]){
        						vls.push(value[j][p.valueField]);
        					}
        				}
        				val = vls.join(",");
        			}
        		}
        		//对象
        		else{
        			if(value[p.valueField]){
        				val = value[p.valueField];
        			}
        		}
        	}
        	
        	//旧值
        	var oldValue = g.selectedValues.join(",");
        	
        	g.bind('matchComplete',function(){
        		//单选
        		g.selectedValues = [];
        		var ary = [];
        		for(var i in g.selectedDatas){
        			g.selectedValues.push(g.selectedDatas[i][p.valueField]);
        			ary.push(g.selectedDatas[i][p.labelField]);
        		}
        		g.hiddenField.val(g.selectedValues.join(','));
        		var label = ary.join(';');
        		//多选
        		if(p.multi===true) if(label.length>0) label = label + ';';
        		
        		g.searchField.val(label);
        		g.prev_value = label;
        		
        		//处理校验
    			if(g.searchField.hasClass('error')){
    				g.searchField.removeClass('error');
    				g.searchField.removeClass('dic-input-error');
    				g.searchField.unbind('mouseover');
    			}
    			
        		g.unbind('matchComplete');
        		
        		if(oldValue != val) g.trigger('valueChange');
        	});
        	
        	//匹配值
        	g._matchValue(val);
        },
        
        //获取选中字典项的值
        getValue:function(){
        	var g = this;
        	return g.getSelectedValues();        	
        },
        
        //获取选中字典项的名称
        getText:function(){
        	var g = this, p = this.options;
    		if(g.selectedDatas.length==0) return "";
    		var ary = [];
    		for(var i in g.selectedDatas){
    			ary.push(g.selectedDatas[i][p.labelField]);
    		}
    		
        	return ary.join(',');        	
        },
        
        //字典的启用和禁用开关;true:禁用字典,false:启用字典
        setDisabled:function(val){
        	var g = this, p = this.options;
        	if(typeof(val) != 'boolean') return;
        	//禁用
        	if(val===true){
        		if(g.isEnable===true){
        			if(g.searchField.hasClass('dic-input-enable')) g.searchField.removeClass('dic-input-enable');
        			if(!g.searchField.hasClass('dic-input-disabled')) g.searchField.addClass('dic-input-disabled');
        			g.searchField.attr("disabled",true);
                	g.isEnable = false;
        		}
        	}
        	//启用
        	else{
        		if(g.isEnable===false){
        			if(g.searchField.hasClass('dic-input-disabled')) g.searchField.removeClass('dic-input-disabled');
        			if(!g.searchField.hasClass('dic-input-enable')) g.searchField.addClass('dic-input-enable');
        			g.searchField.attr("disabled",false);
        			g.isEnable = true;
        		}
        	}
        },
        
        //获取选中的代码值 value1,value2
        getSelectedValues:function(){
        	var g = this;
        	return g.selectedValues.join(',');
        },
        
        //获取选中的字典项 [object,object]
        getSelectedItemDatas:function(){
        	var g = this;
        	return g.selectedDatas;
        },
        
        //绑定字典事件
        _bindDicEvent:function(){
        	var g = this, p = this.options;
        	//获取焦点时
        	g.searchField.bind('focus',function(){
        		if(g.resultContainer.is(':hidden')){
        			if(g.searchField.hasClass('error')){
        				g.searchField.addClass('dic-input-error');
        			}
        			g.is_suggest = false;
        			g.suggest();
        		}
        	});
        	
        	//输入值时进行联想
        	g.searchField.keyup(function(event){
        		g._keyPress(event);
        		//修正不能再次给输入框绑定keyup事件,2015-4-14 add by dsx
        		//return false;
        	});
        	
        	//结果容器绑定keyup事件
        	g.resultContainer.keyup(function(e){
        		g._pageKeyPress(e.keyCode,e.shiftKey);
        		//修正不能再次给g.resultContainer绑定keyup事件,2015-4-14 add by dsx
        		//return false;
        	});
        	
			var stop_hide = false;
			//如果点的是结果容器则不隐藏结果
			g.resultContainer.mouseup(function(e) { stop_hide = true; });
			//点空白处隐藏结果
			$('html').mouseup(function(e) {
				//当前点击的DOM对象
				var target = e.target || e.srcElement;
				var jqObj = $(target);
				//如果是字典输入框则不隐藏结果
				if((jqObj.hasClass('dic-input-enable') || jqObj.hasClass('dic-input-disabled')) && g.id==jqObj.attr('ligeruiid')){
					stop_hide = true;
				}
				if (stop_hide){
					stop_hide = false;
				}else{
					g._hideReuslt();
				}
			});
			
			//处理值改变事件
			if($.isFunction(p.valueChangeHandle)){
				g.bind('valueChange',function(){
					var val = g.selectedValues.join(",");
					p.valueChangeHandle(val,g);
				});
			}
			
			//窗口大小发生改变，重新定位
            $(window).bind("resize.Dictionary", function (e){
            	if(g.resultContainer.is(':visible')){
                	//计算展示位置
                	var position = g._adjustShowPosition();
                	//重新定位
            		g.resultContainer.offset(position);
            	}
            });
        },
        
        _pageBtnClick:function(btnName){
        	var g = this, p = this.options;
        	if(g.matchedData.length==0 && btnName!='clear') return;
        	if(btnName=='clear'){
        		if(g.selectedDatas.length > 0){
        			//旧值
        			var oldValue = g.selectedValues.join(",");
        			//去除选中状态
        			$.each(g.selectedValues,function(){
        				var value = this;
	        			$("input[value="+value+"]:checked").parent().removeClass('selected');
	        			$("input[value="+value+"]:checked").attr('checked',false);
        			});
        			//清空
        			g.clear();
        			//触发值改变
        			if(oldValue != '') g.trigger('valueChange');
        		}
        	}
        	else{
    			if(btnName=='first'){
    				if(g.currentPage == 1) return;
    				g.currentPage = 1;
    			}
    			else if(btnName=='prev'){
    				if(g.currentPage < 2) return;
    				g.currentPage -= 1;
    			}
    			else if(btnName=='next'){
    				if(g.currentPage == g.totalPage) return;
    				g.currentPage += 1;
    			}
    			else if(btnName=='last'){
    				if(g.currentPage == g.totalPage) return;
    				g.currentPage = g.totalPage;
    			}
    			//展示当前页的数据
    			g._showCurrentPageData();
        	}
        },
        
        //处理按键事件
        _keyPress:function(e){
        	var g = this, p = this.options;
			if ($.inArray(e.keyCode, [27,38,40,9]) > -1 && g.resultContainer.is(':visible')) {
				
					e.preventDefault();
					e.stopPropagation();
					e.cancelBubble = true;
					e.returnValue  = false;
					
					switch (e.keyCode) {
						case 38: 
							//上一行
							g.resultContainer.focus();
							g._pageKeyPress(e.keyCode, e.shiftKey);
							break;
						case 40: 
							g.resultContainer.focus();
							g._pageKeyPress(e.keyCode, e.shiftKey);
							//下一行
							break;
						case 9:  
							//按下tab 关闭结果
							g._hideReuslt();
							g.searchField.blur();
							break;
						case 27: 
							//按下esc 关闭结果
							g._hideReuslt();
							g.searchField.blur();
							break;
					}
				} else {
					
					//按键进行联想
					if(g.timer_valchange) clearTimeout(g.timer_valchange);
					g._setTimerCheckValue();
					
					//按下enter触发选中事件
					if(e.keyCode==13){
						$(".over",g.resultContainer).trigger("click");
						//多选，搜索输入框需要获取焦点
						if(p.multi===true) g.searchField.focus();
					}
				}
        },
        
        //翻页按键处理
        _pageKeyPress:function(keyCode,hasShiftKey){
        	var g = this, p = this.options;
			if ($.inArray(keyCode, [37,38,39,40,13]) > -1 && g.resultContainer.is(':visible'))
			{
				switch (keyCode) 
				{
					case 37: 
						
						//按了shift + ← 就直接转到第一页
						if (hasShiftKey){
							g._pageBtnClick("first");
						}
						//只按了← 向上翻页
						else{
							g._pageBtnClick("prev");
						}
						break;
	
					case 38: 
						
						if($(".over",g.resultContainer).length==0){
							$(".dic-item[index='0']",g.resultContainer).addClass('over');
						}
						else{
							var index = Number($(".over",g.resultContainer).attr("index"));
							if(index == 0) return;
							$(".over",g.resultContainer).removeClass("over");
							$(".dic-item[index='"+(index-1)+"']",g.resultContainer).addClass('over');
						}
						//上一行
						break;
						
					case 39: 
						
						//按了shift + → 就直接转到第最后一页
						if(hasShiftKey){
							g._pageBtnClick("last");
						}
						//只按了→ 向下翻页
						else{
							g._pageBtnClick("next");
						}
						break;
	
					case 40: 
						if($(".over",g.resultContainer).length==0){
							$(".dic-item[index='0']",g.resultContainer).addClass('over');
						}
						else{
							var index = Number($(".over",g.resultContainer).attr("index"));
							if((index+1) == g.currentShowData.length) return;
							$(".over",g.resultContainer).removeClass("over");
							$(".dic-item[index='"+(index+1)+"']",g.resultContainer).addClass('over');
						}
						break;
					case 13: 
						//按下enter 选中，并关闭结果
						$(".over",g.resultContainer).trigger("click");
						break;
				}
			}
        },
        
        //定时进行联想
        _setTimerCheckValue:function(){
        	var g = this, p = this.options;
        	g.timer_valchange = setTimeout(function(){
        		g._checkValue();
        	},p.checkInterval);
        },
        
        _checkValue:function(){
        	var g = this, p = this.options;
			var now_value = g.searchField.val();
			if (now_value != g.prev_value) {
				g.prev_value = now_value;
				g.is_suggest = true;
				g.suggest();
			} 
        },
        
        //联想
        suggest:function(){
        	var g = this, p = this.options;
        	
        	g.total = 0;
        	g.totalPage = 0;
        	g.matchedData = [];
        	g.currentShowData = [];
        	
        	if(g.is_suggest===true){
        		g.currentPage = 1;
        		if(p.multi===false){
        			var oldValue = g.selectedValues.join(",");
        			g.selectedDatas=[];
        			g.selectedValues=[];
        			g.hiddenField.val('');
        			if(oldValue!='') g.trigger('valueChange');
        		}
        	}
        	
			var q_word = (g.is_suggest===true) ? $.trim(g.searchField.val()) : '';
			//多选就用分隔符分割查询串
			if(p.multi===true && q_word.length>0){
        		var sAry = q_word.split(p.multiSplit);
        		q_word = sAry[sAry.length-1];
        	}
			
			if (q_word.length < 1 && g.is_suggest===true) {
				if(p.multi===true && $.trim(g.searchField.val())==''){
					var oldValue = g.selectedValues.join(",");
        			g.selectedDatas=[];
        			g.selectedValues=[];
        			g.hiddenField.val('');
        			if(oldValue!='') g.trigger('valueChange');
				}
				
				//隐藏结果
				g._hideReuslt();
				
				//检查是否有校验
    			if(g.searchField.hasClass('error')){
    				g.searchField.addClass('dic-input-error');
    			}
    			
				return;
			}
			
			if(p.dataAction == 'local'){
				if(!p.data || !p.data[p.recordName]) return;
				//构建正则表达式函数
				var fun = g._createRegxFunction(q_word);
				//设置了过滤
				if(fun){
					for(var i in p.data[p.recordName]){
						if(fun(p.data[p.recordName][i])){
							g.matchedData.push(p.data[p.recordName][i]);
						}
					}
				}
				//没有设置过滤
				else{
					g.matchedData = g.matchedData.concat(p.data[p.recordName]);
				}
				
				if(g.matchedData.length > 0) {
					g.total = g.matchedData.length;
					g.totalPage = g.total%p.pageSize==0 ? g.total/p.pageSize:(g.total-g.total%p.pageSize)/p.pageSize+1;
					//排序
					g._sortMatchData();
					//计算当前选中项所在的页码
					g._calculateCurrentPage();
					//展示当前数据
					g._showCurrentPageData();
				}else{
					g._showResult();
				}
			}
			else if(p.dataAction=='server'){
				g._showCurrentPageData();
			}
        },
        //展示结果
        _showResult:function(){
        	var g = this, p = this.options;
        	//重置联想标识位
        	g.is_suggest = false;
        	//构建展示结果数据
    		g._buildResult();
    		//构建分页信息
    		g._buildPageInfo();
    		//计算位置，并展示
    		var position = {left:0,top:0};
        	if(g.resultContainer.is(':hidden')){
        		g.resultContainer.show();
            	//计算展示位置
            	position = g._adjustShowPosition();
        		g.resultContainer.offset(position);
        	}
        	//计算展示位置，防止出现滚动条还需要重新计算位置
        	position = g._adjustShowPosition();
        	//重新定位
        	g.resultContainer.offset(position);
        },
        //隐藏结果
        _hideReuslt:function(){
        	var g = this, p = this.options;
        	if(g.resultContainer.is(':visible')){
        		g.resultContainer.hide();
            	g.itemContainer.html('');
            	g.pageDescript.html('');
            	//隐藏结果时重新给搜索框赋值，以防止未选择还有值在上面
            	var text = g.getText();
            	if(p.multi===true && text!=''){
            		text = text.replace(/,/g,";");
            		text += ";";
            	}
            	g.searchField.val(text);
            	g.prev_value = text;
        	}
        },
        //构建页码信息
        _buildPageInfo:function(){
        	var g = this, p = this.options;
        	var stat = p.pageStatMessage;
        	stat = stat.replace(/{currentPage}/, g.currentPage);
        	stat = stat.replace(/{totalPage}/, g.totalPage);
        	g.pageDescript.html(stat);
        	
        	//是否使用分页条
        	var usePageBar = true;
        	
        	//本地数据
        	if(p.dataAction == 'local'){
        		if(g.matchedData.length <= p.pageSize) usePageBar = false;
        	}
        	//服务端数据
        	else if(p.dataAction == 'server'){
        		if(g.total <= p.pageSize) usePageBar = false;
        	}
        	
        	//使用分页条
        	if(usePageBar===true){
        		g.pageContainer.show();
        	}
        	//不使用分页条
        	else{
        		g.pageContainer.hide();
        	}
        },
        
        //构建结果
        _buildResult:function(){
        	var g = this, p = this.options;
        	g.itemContainer.html('');
        	//没有匹配到数据
        	if(g.currentShowData.length==0) {
        		var noMatch = $("<div class='dic-item' style='text-align:center;'>没有匹配的数据项</div>");
        		g.itemContainer.append(noMatch);
        		return;
        	}
        	
        	for(var i in g.currentShowData){
        		var item = $("<div class='dic-item' index='"+i+"'></div>");
        		//第一条高亮
        		if(i==0) item.addClass('over');
        		if(p.multi===true){
        			item.append("<input type='checkbox' value='"+g.currentShowData[i][p.valueField]+"' />");
        		}
        		item.append(g.currentShowData[i][p.labelField]);
        		g.itemContainer.append(item);
        		//有设置标题
        		if(p.titleField && p.titleField.length>0){
        			item.attr('title',g.currentShowData[i][p.titleField]);
        		}
        		//有设置itemRender函数
        		if($.isFunction(p.itemRender)){
        			p.itemRender(g,item,g.currentShowData[i]);
        		}
        		
        		if($.inArray(g.currentShowData[i][p.valueField],g.selectedValues) > -1){
        			item.addClass('selected');
        			$(":checkbox",item).attr('checked',true);
        		}
        		
        		item.mouseover(function(){
        			$(".over",g.resultContainer).removeClass("over");
        			$(this).addClass('over');
        		}).mouseout(function(){
        			$(this).removeClass('over');
        		}).click(function(e){
        			//旧值
        			var oldValue = g.selectedValues.join(",");
        			//当前点击的dom对象
        			var target = e.target || e.srcElement;
        			var index = $(this).attr("index");
        			//当前选中的值
        			var value = g.currentShowData[index][p.valueField];
        			if(p.multi===false){
        				g.selectedDatas = [];
        				g.selectedValues = [];        				
        				g.selectedDatas.push(g.currentShowData[index]);
        				g.selectedValues.push(value);	
        				g.hiddenField.val(value);
        				g.searchField.val(g.currentShowData[index][p.labelField]);
        				g.prev_value = g.searchField.val();
        				g._hideReuslt();
        			}
        			else if(p.multi===true){
            			if($(this).hasClass('selected')){
            				$(this).removeClass('selected');
            				if(target.tagName.toLowerCase() != 'input'){
            					$(":checkbox",$(this)).attr('checked',false);
            				}
            				var pos = $.inArray(value,g.selectedValues);
            				if(pos < 0) return ;
            				g.selectedDatas.splice(pos, 1);
            				g.selectedValues.splice(pos, 1);
            				g.hiddenField.val(g.selectedValues.join(','));
                			var labelAry = [];
                			for(var i in g.selectedDatas){
                				var label = g.selectedDatas[i][p.labelField];
                				labelAry.push(label);
                			}
                			var label = labelAry.join(';');
                			if(label.length>0) label = label + ';';
                			g.searchField.val(label);
            				g.prev_value = label;
            			}
            			else{
	    	        		$(this).addClass('selected');
            				if(target.tagName.toLowerCase() != 'input'){
            					$(":checkbox",$(this)).attr('checked',true);
            				}
	        				g.selectedDatas.push(g.currentShowData[index]);
	        				g.selectedValues.push(value);
	        				g.hiddenField.val(g.selectedValues.join(','));
	        				
                			var labelAry = [];
                			for(var i in g.selectedDatas){
                				var label = g.selectedDatas[i][p.labelField];
                				labelAry.push(label);
                			}
                			var label = labelAry.join(';');
                			if(label.length>0) label = label + ';';
                			
                			g.searchField.val(label);
            				g.prev_value = label;
            			}
        			}
        			
        			//有设置点击后的处理函数
        			if($.isFunction(p.itemClick)){
        				p.itemClick(g,item,g.currentShowData[index]);
        			}
        			
        			if(g.searchField.hasClass('error')){
        				g.searchField.removeClass('error');
        				g.searchField.removeClass('dic-input-error');
        				g.searchField.unbind('mouseover');
        			}
        			
        			//重新触发失去焦点事件
        			g.searchField.trigger('blur');
        			g.searchField.trigger('change');
        			
        			//触发值改变事件
        			var newValue = g.selectedValues.join(",");
        			if(oldValue != newValue) g.trigger('valueChange');
        		});
        	}
        	if(p.isFixOptions===true){
        		if(g.currentShowData.length < p.pageSize){
        			for(var i=g.currentShowData.length;i<p.pageSize;i++ ){
        				g.itemContainer.append("<div class='dic-item' index='"+i+"'>&nbsp;</div>");
        			}
        		}
        	}
        },
        
        //展示当前页的数据
        _showCurrentPageData:function(){
        	var g = this, p = this.options;
        	g.currentShowData = [];
        	if(p.dataAction == 'local'){
            	//设置展示数据
    			var sPos = (g.currentPage-1) * p.pageSize;
    			var ePos = sPos + p.pageSize;
    			if(ePos > g.total){
    				ePos = g.total;
    			}
    			var tempData = g.matchedData.slice(sPos,ePos);
    			g.currentShowData = g.currentShowData.concat(tempData);
    			//展示
    			g._showResult();
        	}
        	else if(p.dataAction == 'server'){
				if(!p.url || p.url.length==0) return ;
				//终止前面的请求
				g._abortAjax();
				//获取请求参数
				var params = g._getAjaxSubmitParams();
				//请求数据
				g.xhr = $.ajax({
					url:p.url,
					type:p.method,
					data:params,
					dataType:'json',
					success:function(json){
						if(!json) json={};
						if(!json[p.totalName]) json[p.totalName] = 0;
						if(!json[p.recordName]) json[p.recordName] = [];
						
						//计算总页数
						g.total = json[p.totalName];
						g.totalPage = g.total%p.pageSize==0 ? g.total/p.pageSize:(g.total-g.total%p.pageSize)/p.pageSize+1;
						
						//匹配到的数据
						g.matchedData = json[p.recordName];
						//排序
						g._sortMatchData();
						//待展示的数据
						g.currentShowData = g.currentShowData.concat(g.matchedData);
						
		    			//展示
		    			g._showResult();
					}
				});
        	}
        },
        
        //创建正则匹配函数
        _createRegxFunction:function(str){
        	var g = this, p = this.options;
        	var fields = p.searchField.split(",");
        	//联想过滤
        	var regx = "";
        	if(fields.length>0 && str && str.length>0){
	        	for(var i in fields){
	        		var s = " /.*?"+str+".*?/gi.test(o['"+fields[i]+"']) ||";
	        		regx += s;
	        	}
	        	if(regx.length>0){
	        		regx = regx.substring(0,regx.length-2);
	        	}
        	}
        	
        	//默认过滤
    		if(typeof(p.filterRegx)=='string'){
    			if(isJsonString(p.filterRegx)){
    				p.filterRegx = eval("("+p.filterRegx+")");
    			}
    		}
    		var filterStr = '';
    		if(p.filterRegx && typeof(p.filterRegx)=='object'){
    			for(var ele in p.filterRegx){
    				if(p.filterRegx[ele] && p.filterRegx[ele].length>0){
    					filterStr = "/" + p.filterRegx[ele] + "/gi.test(o['"+ele+"']) &&";
    				}
    			}
    			if(filterStr.length>0){
    				filterStr = filterStr.substring(0,filterStr.length-2);
    			}
    		}
    		
    		if(filterStr.length>0){
    			if(regx.length>0){
    				regx = "(" + regx + ") && (" + filterStr + ")";
    			}
    			else{
    				regx = filterStr;
    			}
    		}
    		
    		if(regx.length==0) return null;
    		
        	regx = "return " + regx + ";";
        	
        	var fun = new Function("o",regx);
        	
        	return fun;
        },
        //排序
        _sortMatchData:function(){
        	var g = this, p = this.options;
        	if(g.matchedData.length==0) return;
        	
			//排序
			if(p.order_by && typeof(p.order_by)=='object'){
				var d = p.order_by;
				for(var field in d){
					//升序
					if(/^asc$/i.test(d[field])){
						g.matchedData.sort(function(a,b){
							return a[field] < b[field]?-1:1;
						});
					}
					else if(/^desc$/i.test(d[field])){
						g.matchedData.sort(function(a,b){
							return a[field] > b[field]?-1:1;
						});
					}
				}
			}
/*			
			//相似度排序
			else{
				var q_word = g.is_suggest===true ? $.trim(g.searchField.val()) : '';
	        	if(p.multi===true && q_word.length>0){
	        		var sAry = q_word.split(p.multiSplit);
	        		q_word = sAry[sAry.length-1];
	        	}
	        	if(q_word=='') return ;
	        	
	        	//所有查询字段
	        	var fields = p.searchField.split(",");
	        	
	        	//计算最小相似度
	        	for(var i in g.matchedData){
	        		var leval = 0;
	        		for(var j in fields){
	        			var temp = g._calculateSimilarity(q_word,g.matchedData[i][fields[j]]);
	        			if(temp > leval) leval = temp;
	        		}
	        		g.matchedData[i]['leval'] = leval;
	        	}
	        	
	        	//相似度排序
				g.matchedData.sort(function(a,b){
					return a['leval'] > b['leval'] ? -1:1;
				});
			}
*/						
        },
        
        //终止ajax请求
        _abortAjax:function(){
        	var g = this, p = this.options;
        	if(g.xhr){
        		g.xhr.abort();
        		g.xhr = false;
        	}
        },
        //获取查询时需提交的参数
        _getAjaxSubmitParams:function(){
        	var g = this, p = this.options;
    		var params = [];
    		//处理设置的参数
    		if(p.parms){
    			if(p.parms instanceof Array){
    				$(p.parms).each(function(){
    					params.push({name:this.name,value:this.value});
    				});
    			}
    			else if (typeof p.parms == "object")
                {
                    for (var name in p.parms)
                    {
                    	params.push({ name: name, value: p.parms[name] });
                    }
                }
    		}
			//设置当前请求页码
			params.push({name:p.pageParmName,value:g.currentPage});
			params.push({name:p.pagesizeParmName,value:p.pageSize});
			//查询字段以及对应的查询值,只支持模糊查询
			var q_word = (g.is_suggest) ? $.trim(g.searchField.val()) : '';
			
			//处理动态字典 翻页时不带查询条件 2015-04-18 
			if(g.is_suggest===false){
				if(p.multi===false){
					if(g.selectedDatas.length==0){
						q_word = $.trim(g.searchField.val());
					}
				}else{
					var ary = $.trim(g.searchField.val()).split(p.multiSplit);
					if(g.selectedDatas.length!=ary.length){
						q_word = $.trim(g.searchField.val());
					}
				}
			}
			
        	if(p.multi===true && q_word.length>0){
        		var sAry = q_word.split(p.multiSplit);
        		q_word = sAry[sAry.length-1];
        	}
        	
			params.push({name:p.queryFieldParamName,value:p.searchField});//提交查询字段 2014 02 28
			params.push({name:p.queryValueParamName,value:q_word});		  //提交查询值 2014 02 28
			
			//处理排序
			if(p.order_by && typeof(p.order_by)=='object'){
				var order_by_value = null;
				var d = p.order_by;
				for(var field in d){
					//升序
					if((/^asc$/i.test(d[field])) || (/^desc$/i.test(d[field]))){
						if(!order_by_value) order_by_value = {};
						order_by_value[field] = d[field];
					}
				}
				if(order_by_value){
					params.push({name:p.sortorderParmName,value:JSON.stringify(order_by_value)});
				}
			}
			
			return params;
        },
        //加载服务端数据
        _loadServerData:function(){
        	var g = this, p = this.options;
        },
        //计算结果容器的展示坐标
        _adjustShowPosition:function(){
        	var g = this,p = this.options;
        	
        	//计算偏移
    		var posX = g.searchField.offset().left;
    		var posY = g.searchField.offset().top + g.searchField.outerHeight()+1;
    		var h = g.resultContainer.height();
    		//自动计算
    		if(p.showDirection=='auto'){
	    		if(h<$(window).height()){
	        		//如果向下浮出现滚动条
	        		if((posY+h)>$(window).height()){
	        			//上浮动超过窗口上限
	        			if((g.searchField.offset().top-h-3)<0){
	        				//超过上限则则Y居中偏移，计算X偏移
	        				if((posX-g.resultContainer.width()-5)<0){
	        					//向右偏移
	        					if((posX+g.searchField.outerWidth()+5+g.resultContainer.width())<$(window).width()){
	        						posX = posX+g.searchField.outerWidth()+5;
	        						posY = ($(window).height()-g.resultContainer.height())/2;
	        					}
	        				}
	        				//向左偏移
	        				else{
	        					posX = posX-g.resultContainer.width()-5;
	        					posY = ($(window).height()-g.resultContainer.height())/2;
	        				}
	        			}
	        			//向上偏移
	        			else{
	        				posY = g.searchField.offset().top-h-3;
	        			}
	        		}    			
	    		}
    		}
    		
    		//向上
    		else if(p.showDirection=='top'){
    			posY = g.searchField.offset().top-h-3;
    		}
    		
    		//向左
    		else if(p.showDirection=='left'){
				posX = posX-g.resultContainer.width()-5;
				posY = ($(window).height()-g.resultContainer.height())/2;
    		}
    		
    		//向右
    		else if(p.showDirection=='right'){
				posX = posX+g.searchField.outerWidth()+5;
				posY = ($(window).height()-g.resultContainer.height())/2;
    		}
    		
    		var position = {};
    		position.left = posX;
    		position.top = posY;
    		
    		return position;
        },
        
        //清空、即重置
        clear:function(){
        	var g = this;
        	//当前查询的值
        	g.prev_value = '';
        	//选中的值
        	g.selectedValues = [];
        	g.selectedDatas = [];
    		g.hiddenField.val('');
    		g.searchField.val('');
        },
        
        //移除参数
        removeParm : function(name)
        {
            var g = this;
            var parms = g.get('parms');
            if (!parms) parms = {};
            if (parms instanceof Array)
            {
                removeArrItem(parms, function (p) { return p.name == name; }); 
            } else
            {
                delete parms[name];
            }
            g.set('parms', parms);
        },
        //设置参数
        setParm: function (name, value)
        {
            var g = this;
            var parms = g.get('parms');
            if (!parms) parms = {};
            if (parms instanceof Array)
            {
                removeArrItem(parms, function (p) { return p.name == name; });
                parms.push({ name: name, value: value });
            } else
            {
                parms[name] = value;
            }
            g.set('parms', parms); 
        },
        
        //修改字典配置属性
        changeOptions:function(obj,value){
        	var g = this, p = this.options;
        	//设置单个属性
        	if(typeof(obj)=='string'){
        		this.options[obj] = value;
        	}
        	//批量设置属性
        	else if(typeof(obj)=='object' && !value){
        		this.options = $.extend({},p,obj);
        	}
        },
        
        //加载数据
        loadData:function(data){
        	var g = this, p = this.options;
        	
        	//清空选中的值
        	g.clear();
        	
        	g.matchedData = [];
        	g.currentShowData = [];
        	
        	g.currentPage = 1;
        	g.totalPage = 0;
        	g.total = 0;
        	
        	//是否正在联想
        	g.is_suggest = false;
        	
        	//server
        	if(p.dataAction==='server' && typeof(data)=='string'){
        		p.url = data;
        	}
        	//local
        	else if(p.dataAction==='local'){
        		p.data = null;
        		if(!data) return ;
        		//array
        		if($.isArray(data)){
        			p.data = {Rows:data,Total:data.length};
        		}
        		//object
        		else if(typeof(data)=='object'){
        			p.data = data;
        		}
        	}
        },
        
        //设置字段过滤正则表达式
        setFieldFilterRegx:function(fieldName,regx){
        	var g = this, p = this.options;
        	var filterRegx = p.filterRegx;
        	if(!p.filterRegx) p.filterRegx = {};
        	if(typeof(fieldName)=='string' && fieldName!='' && typeof(regx)=='string'){
        		p.filterRegx[fieldName] = regx;
        	}
        },
        
        //计算当前选中项所在页
        _calculateCurrentPage:function(){
        	var g = this, p = this.options;
        	
        	//如果为联想则不计算。
			if(g.is_suggest===true) return;
			
			//匹配到的字典项个数不大于每页展示数则展示第一页
			if( g.matchedData.length <= p.pageSize) return;
			
			//当前选中的值
			var selectValues = g.getSelectedValues();
			if(selectValues=='') return;
			
			//单选
			var value = selectValues;
			//多选则取第一个
			if(p.multi===true){
				value = selectValues.split(',')[0];
			}
			
			//计算选中项的索引
			var pos = 0;
			for(var i in g.matchedData){
				if(value==g.matchedData[i][p.valueField]){
					pos = i;
					break;
				}
			}
			//第一页
			if(pos < p.pageSize) return;
			
			//有小数部分则整数部分加1，返回整数值
			g.currentPage = Math.ceil((Number(pos)+1)/p.pageSize);
        },
        
        //计算字符串的相似度，返回的值越大则相似度越高
        //source:待计算的字符串，target:待比较的字符串
        _calculateSimilarity:function(source,target){
        	
        	if(!source || $.trim(source)=='') return 0;
        	if(!target || $.trim(target)=='') return 0;
        	
        	//都转为大写
        	var s = source.toUpperCase();
        	var t = target.toUpperCase();
        	
        	var d = new Array(s.length+1);
        	for(var i=0;i<d.length;i++){
        		d[i] = new Array(t.length+1);
        		for(var j=0;j<d[i].length;j++){
        			d[i][j] = 0;
        		}
        	}
        	
        	//初始化相似度计算
        	for(var i=0;i<=s.length;i++){
        		d[i][0] = i;
        	}
        	for(var j=0;j<=t.length;j++){
        		d[0][j] = j;
        	}
        	
        	//计算相似度
    	    for(var i = 1; i <= s.length; i++)
    	    {
	    	    for(j = 1; j <= t.length; j++)
	    	    {
	    	    	//source串第i个字符target串第j个字符相等则不需要编辑操作
		            if(s.charAt(i-1) == t.charAt(j-1))
		            {
		                 d[i][j] = d[i - 1][j - 1]; 
		            }
		            //需要编辑操作
		            else
		            {
		            	//source 插入字符
		                var edIns = d[i][j - 1] + 1;
		                //source 删除字符
		                var edDel = d[i - 1][j] + 1;
		              	//source 替换字符
		                var edRep = d[i - 1][j - 1] + 1;
		                //去最小值
		                d[i][j] = Math.min(Math.min(edIns, edDel), edRep);
		             }
	    	     }
    	    }
    	    
    	    return (1-d[s.length][t.length]/Math.max(s.length,t.length));
        }
    });
    
    function removeArrItem(arr, filterFn)
    {
        for (var i = arr.length - 1; i >= 0; i--)
        {
            if (filterFn(arr[i]))
            {
                arr.splice(i, 1);
            }
        }
    }
    
    /**
     * 判断字符串是不是标准的json串,其中属性名称都必须带上双引号
     * @param str 待判断的字符串
     */
    function isJsonString(str){
    	var result = true;
    	try{
    		JSON.parse(str);
    	}catch(e){
    		result = false;
    	}
    	
    	return result;
    }
    
})(jQuery);

/**
* jQuery ligerUI 1.2.0
* http://ligerui.com
* Author daomi 2013 [ gd_star@163.com ] 
* 数据列表插件 dengshaoxiang
*/
(function ($)
{
    var l = $.ligerui;

    $.fn.ligerDataGrid = function (options)
    {
        return $.ligerui.run.call(this, "ligerDataGrid", arguments);
    };

    $.fn.ligerGetDataGridManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetDataGridManager", arguments);
    };
	
    //接口方法扩展
    $.ligerMethos.DataGrid = $.ligerMethos.DataGrid || {};
    
    $.ligerui.controls.DataGrid = function (element, options)
    {
        $.ligerui.controls.DataGrid.base.constructor.call(this, element, options);
    };
    
    $.ligerDefaults.DataGrid = {
    		iconsPath:'/rdp/static/lib/Framework/icons/',
    		opers:[],
    		quickFilters:[],
    		dgOptions:{},
			search:null,
    		resource:null,
    		context:null
        };
    
    $.ligerui.controls.DataGrid.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.DataGrid';
        },
        __idPrev: function ()
        {
            return 'DataGrid';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.DataGrid;
        },
        _init: function ()
        {
            $.ligerui.controls.DataGrid.base._init.call(this);
            var g = this, p = this.options;
            
    		if(!p.dgOptions.columns || p.dgOptions.columns==0){
    			alert("请设定需要展示的字段");
    			return;
    		}
    		
    		//合并 列表参数 和 resource属性
    		if(p.resource){
    			p.dgOptions.parms = $.extend({},p.dgOptions.parms,p.resource);
    		}
    		
    		if(!p.dgOptions.width) p.dgOptions.width = 'auto';
    		
    		/*
    		该例子实现 表单分页多选
    		即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
    		*/
    		p.checkedIds = [];
    		function findCheckedField(fieldId)
    		{
    		  for(var i =0;i<p.checkedIds.length;i++)
    		  {
    		      if(p.checkedIds[i] == fieldId) return i;
    		  }
    		  return -1;
    		}
    		
    		function addCheckedField(fieldId)
    		{
    		  if(findCheckedField(fieldId) == -1)
    		  	p.checkedIds.push(fieldId);
    		}
    		
    		function removeCheckedField(fieldId)
    		{
    		  var i = findCheckedField(fieldId);
    		  if(i==-1) return;
    		  p.checkedIds.splice(i,1);
    		}
    		
    		function f_isChecked(rowdata)
    		{
    		  if (findCheckedField(rowdata.__id) == -1)
    		      return false;
    		  return true;
    		}

    		function f_onCheckRow(checked, data)
    		{
    		  if (checked) addCheckedField(data.__id);
    		  else removeCheckedField(data.__id);
    		}
    		
    		function f_onCheckAllRow(checked)
    		{
    		  for (var rowid in this.records)
    		  {
    		      if(checked)
    		          addCheckedField(this.records[rowid]['__id']);
    		      else
    		          removeCheckedField(this.records[rowid]['__id']);
    		  }
    		}
//    		//分页记忆
//            p.dgOptions.isChecked=f_isChecked;
//            p.dgOptions.onCheckRow=f_onCheckRow;
//            p.dgOptions.onCheckAllRow=f_onCheckAllRow;
    		
    		//备份列表配置信息
    		g.dgOptions = $.extend({},p.dgOptions);
    		if(g.dgOptions.url){
    			g.dgOptions.url = g._getUrlRootPath() + g.dgOptions.url;
    			p.dgOptions.url = g.dgOptions.url;
    		}
    		
    		//需要输入条件才能查询
    		if(g.dgOptions.isNeedSearchValue===true){
    			var queryFlag = false;
    			//没有默认查询条件
    			if(g.dgOptions.parms.where && g.dgOptions.parms.where!=''){
    				var initRule = eval("("+g.dgOptions.parms.where+")");
    				queryFlag = g._isCanQuery(initRule.rules);
    			}
    			if(queryFlag===false){
    				if(g.dgOptions.parms.quickFilter && g.dgOptions.parms.quickFilter!=''){
    					queryFlag = true;
    				}
    			}
    			if(queryFlag===false){
        			p.dgOptions.url = null;
        			p.dgOptions.dataAction = 'local';
        			p.dgOptions.dataType = 'local';      				
    			}
    		}
        },
        _render: function ()
        {
            var g = this, p = this.options;
            g.datagrid = $(g.element);
            
    		//保存的查询状态
            var queryStatus = null;
            
    		var funcId = null;
    		if(p.resource && p.resource.funcId){
    			funcId = p.resource.funcId;
    		}
    		
    		if(funcId && funcId!='' && __conditionMap){
    			queryStatus = __conditionMap[funcId];
    		}
    		
    		var queryFlag = false;
    		
    		//创建查询
            if(p.search && p.dgOptions.isSearch){
            	//构建查询控件
            	g._createSearch();
            	//处理保存的查询状态
    			if(queryStatus){
    				if(!queryStatus.where)queryStatus.where = "";
    				p.dgOptions.parms.where = queryStatus.where;
    				if(queryStatus.where != ""){
    					var initRule = eval("("+queryStatus.where+")");
    					g.search.filter.setData(initRule);
        				queryFlag = g._isCanQuery(initRule.rules);
    				}
    			}
            }
			
    		//创建操作工具栏
            g._createToolBar();
    		
    		//创建操作
            g._createOpers();
    		
            if(queryStatus){
            	if(!queryStatus.quickFilter)queryStatus.quickFilter = "";
            	p.dgOptions.parms.quickFilter = queryStatus.quickFilter;
            	if(queryFlag===false){
            		if(queryStatus.quickFilter!=""){
            			queryFlag = true;
            		}
            	}
            }
            
    		//创建快速筛选
    		g._createQuickFilters();
    		
			if(queryFlag===true && g.dgOptions.isNeedSearchValue===true){
    			p.dgOptions.url = g.dgOptions.url;
    			p.dgOptions.dataAction = 'server';
    			p.dgOptions.dataType = 'server';
			}
			
            if(queryStatus && queryStatus.page && queryStatus.page!=""){
            	p.dgOptions.newPage = Number(queryStatus.page);
            	p.dgOptions.page = p.dgOptions.newPage;
            }
            
    		//创建列表
    		g._createGrid(); 
        },
        //创建工具栏
        _createToolBar:function(){
        	var g = this, p = this.options;
    		if(p.opers && p.opers["headOpers"] || (p.quickFilters && p.quickFilters.length>0)){
    			g.toolbar = $("<div class='sundun-grid-topbar' style='z-index:-1;width:"+p.dgOptions.width+";'><div class='left oper'></div><div class='right quickFilter'></div></div>");
    			g.datagrid.append(g.toolbar);
    			g.operDiv = $("div.oper",g.toolbar);
    			g.quickFiltersDiv = $("div.quickFilter",g.toolbar);
    		}        	
        },
        
        //创建操作
        _createOpers:function(){
        	var g = this, p = this.options;
    		if(!p.opers || p.opers.length==0) return;
    		//表头操作
    		if(p.opers.headOpers){
	    		$.each(p.opers.headOpers,function(i,data){	
	    			if(g._headOperFilter(data)===true) return;
					var t_length = String(this.name).length;
					if(t_length == 2){
						t_length = "l2";
					}else if(t_length > 2 && t_length < 5){
						t_length = "l4";
					}else if(t_length > 4){
						t_length = "l6";
					}
	    			var oper = $("<a class='icon btn-gray-"+t_length+"' id='oper_"+(i+1)+"'></a>");
	    			if(!g._isNullBlank(this.css)){
	    				oper.addClass(this.css);
	    			}
	    			
	    			if(!g._isNullBlank(this.iconPath)){
	    				var icon = $("<i class='fa "+this.iconPath+"'></i>");
	    				oper.append(icon);
	    			}
	    			
	    			if(!g._isNullBlank(this.name)){
	    				oper.append("<label>"+this.name+"</label>");
	    			}
	    			g.operDiv.append(oper);
	    			
	    			oper.bind('click',function(e){
	    				g._headOperClick(e, data);
	    			});
	    		});
    		}
    		
    		//行操作
    		if(p.opers.rowOpers){
        		var len = p.dgOptions.columns.length;
        		p.dgOptions.columns[len-1].render = function(rowdata,rowid,value){
	        		var renderHtml = [];
	        		$.each(p.opers.rowOpers,function(i,data){
	        			if(g._operFilter(data, rowdata)==false){
	        				renderHtml.push(g._createRowOper(data, i, rowid));
	        			}
	        		});	
	        		return renderHtml.join("");
	    		};
    		}
        },        
    	//创建筛选项
    	_createQuickFilters:function(){
    		var g = this,p = this.options;
    		if(!p.quickFilters || p.quickFilters==0) return;
    		
    		var defaultGroup = null;
    		$.each(p.quickFilters,function(){
    			var filter = this;
    			if(filter.isGroup===true && filter.children){
    				var fieldset = $("<fieldset style='height: 23px;line-height: 25px;margin-bottom: 4px;display: inline;border-right:1px solid #ccc;border-left:1px solid #fff;padding-left:8px;'></fieldset>");
    				$.each(filter.children,function(){
    					var label = $("<label></label>");
    					var radio = $("<input type='radio' class='filter' name='"+filter.mark+"' value='"+this.id+"' isAbleCancel='"+this.isAbleCancel+"' />");
    					label.append(radio);
    					label.append(this.label);
    					fieldset.append(label);
    				});
    				g.quickFiltersDiv.append(fieldset);
    			}else{
    				if(!defaultGroup){
    					defaultGroup = $("<fieldset name='default' style='height: 23px;line-height: 25px;margin-bottom: 4px;display: inline;border-right:1px solid #ccc;border-left:1px solid #fff;padding-left:8px;'></fieldset>");
    				}
    				var label = $("<label></label>");
    				var radio = $("<input type='radio' class='filter' name='default' value='"+filter.id+"' isAbleCancel='"+filter.isAbleCancel+"' />");
    				label.append(radio);
    				label.append(filter.label);
    				defaultGroup.append(label);
    			}
    		});
    		
    		if(defaultGroup) g.quickFiltersDiv.append(defaultGroup);
			g.quickFiltersDiv.find("fieldset:first").css("borderLeft","0");
			g.quickFiltersDiv.find("fieldset:last").css("borderRight","0");
    		
			//处理默认筛选项
			var filterIds = "";
			if(p.dgOptions.parms && p.dgOptions.parms.quickFilter){
				filterIds = p.dgOptions.parms.quickFilter;
			}
			var filterIdArr=[];
			if(filterIds && filterIds!='') filterIdArr = filterIds.split(",");
			$.each(filterIdArr,function(i,id){
				$(":radio[value='"+id+"']",g.quickFiltersDiv).attr("checked",true);
				$(":radio[value='"+id+"']",g.quickFiltersDiv).attr("isChecked",true);
			});
			
    		$(":radio[class='filter']",g.quickFiltersDiv).bind('click',function(){
    			if($(this).attr("isChecked")=='true'){
    				if($(this).attr("isAbleCancel")=='true'){
	    				$(this).attr("checked",false);
	    				$(this).removeAttr("isChecked");
    				}
    			}else{
    				$(":radio[class='filter'][name='"+this.name+"']",g.quickFiltersDiv).attr("checked",false);
    				$(":radio[class='filter'][name='"+this.name+"']",g.quickFiltersDiv).removeAttr("isChecked");
    				$(this).attr("checked",true);
    				$(this).attr("isChecked",true);
    			}
    			
            	//快速过滤
    			var checkIds = [];
    			$(".filter:checked",g.quickFiltersDiv).each(function(){
    				checkIds.push(this.value);
    			});
    			
    			var ids = checkIds.join(",");
    			
    			if(g.dgOptions.isNeedSearchValue===true && ids==''){
    				g.grid.options.url = null;
    				p.dgOptions.url = null;
    				g.grid.options.dataAction = 'local';
        			p.dgOptions.dataAction = 'local';
        			g.grid.options.dataType = 'local';
        			p.dgOptions.dataType = 'local'; 
    			}else{
    				
        			//处理前端分页、后台查数据的情况
        			g.grid.options.url = g.dgOptions.url;
        			p.dgOptions.url = g.dgOptions.url;
        			g.grid.options.dataAction = "server";
        			p.dgOptions.dataAction = "server";
        			g.grid.options.dataType = 'server';
        			p.dgOptions.dataType = 'server';
        			
        			var parms = g.grid.get('parms');
                	parms.quickFilter = ids;
                	parms.where = '';
                	//数据来源查询库标识
                	if(g.search){
                		parms.datafromcxk = g.search.datafromcxk;
                	}
                	g.grid.set('parms', parms);
    			}
            	
            	g.grid.options.newPage = 1;
                g.grid.loadData();
                
//    			//有查询
//    			if(p.search && p.dgOptions.isSearch){
//    				g.search.searchBtn.trigger('click');
//    			}
//    			//无查询
//    			else{
//    				g._serverSearch();
//    			}
    		});
    	},
    	
    	//创建列表
    	_createGrid:function(){
    		var g=this,p=this.options;
    		
    		//处理dgOption,处理表头字段
    		$.each(p.dgOptions.columns,function(i,column){
    			p.dgOptions.columns[i] = g._evalRender(column);
    		});	
    		
    		//处理其他属性
    		p.dgOptions = g._evalOptions(p.dgOptions);
    		
    		//成功显示数据后
    		p.dgOptions.onAfterShowData = function(currentData){
    			var grid = this;
    			//操作处理
    			$(".row-oper").bind('click',function(e){
    				var rowid = $(this).attr("id").split("_")[2];
    				var operId = $(this).attr("id").split("_")[1];
    				var oper = p.opers.rowOpers[operId];
    				g._rowOperClick(e, oper, rowid);
    			});
    			
    			$(".row-oper-batch").bind('click',function(e){
    				var rowid = $(this).attr("id").split("_")[2];
    				var operId = $(this).attr("id").split("_")[1];
    				var oper = p.opers.rowOpers[operId];
    				g._rowOperClick(e, oper, rowid);
    			});
    			
    			//链接处理
    			$(".column-link").bind('click',function(){
    				var id = $(this).attr("id").split("|");
    				var rowid = id[1];
    				var column = id[0];
    				var row = grid.getRow(rowid);
    				for(var i in grid._columns){
    					if(grid._columns[i].name == column){
    						column = grid._columns[i];
    						break;
    					}
    				}
					var url = "";
					eval("url='"+column.linkUrl+"'");
					url = g._getUrlRootPath() + url;
					g._windowOpen(url, {openMode:column.linkOpenMode});
					//取消事件冒泡
					return false;
    			});
    			
    			//处理行操作按钮居中
				$(".l-grid-row-cell[columnname=oper]").each(function(){
					var td = $(this);
					var h = td.height();
					var margin = (h-25)/2;
					$(".common-btn",td).css("margin-top",margin+"px");
				});
    		};
    		
    		//成功加载数据后，处理前端分页
    		p.dgOptions.onSuccess=function(data,grid){
				if(g.dgOptions.dataAction=='local'){
					grid.options.url = null;
					p.dgOptions.url = null;
					grid.options.dataAction = "local";
					p.dgOptions.dataAction = 'local';
        			grid.options.dataType = 'local';
        			p.dgOptions.dataType = 'local';
				}
    		};
    		
    		g.gridDiv = $("<div></div>");
    		g.datagrid.append(g.gridDiv);
    		g.grid = g.gridDiv.ligerGrid(p.dgOptions);
    	},
    	
    	//递归把render函数串转成JS能识别的函数
    	_evalRender:function(column){
    		var g=this,p=this.options;
    		eval("column.headerRender="+column.headerRender);
    		if(column.render){
    			var render=null;
    			eval("render="+column.render);
    			if(column.hasLink===true){
    				var columnRender = function(rowdata,rowid,value){
    					var html = [];
    					html.push("<a style='");
    					if(column.linkCss && column.linkCss.length>0){
    						html.push(column.linkCss);
    					}else{
    						html.push("color:blue;text-decoration:none;border-bottom:1px solid;cursor:pointer;");
    					}
    					html.push("' class='column-link'");
    					html.push("' id='"+column.name+"|"+rowid+"'");
    					html.push(">");
    					html.push(value);
    					html.push("</a>");
    					
    					value = html.join("");
    					var row = rowdata;
    					return render(row,rowid,value);
    				};
    				column.render = columnRender;
    			}else{
    				column.render = render;
    			}
    			
    			return column;
    		}else{    			
    			if(column.columns){
    				for(i in column.columns){
    					g._evalRender(column.columns[i]);
    				}
    			}else{
        			if(column.hasLink){
        				column.render = function(rowdata,rowid,value){
        					var html = [];
        					html.push("<a style='");
        					if(column.linkCss && column.linkCss.length>0){
        						html.push(column.linkCss);
        					}else{
        						html.push("color:blue;text-decoration:none;border-bottom:1px solid;cursor:pointer;");
        					}
        					html.push("' class='column-link'");
        					html.push("' id='"+column.name+"|"+rowid+"'");
        					html.push(">");
        					html.push(value);
        					html.push("</a>");
        					
        					return html.join("");
        				};
        			}
    			}
    		}
    		
    		return column;
    	},
    	/**
    	 * 把相应属性字符串执行eval处理
    	 */
    	_evalOptions:function(obj){
    		if(obj.rowAttrRender){
    			eval("obj.rowAttrRender="+obj.rowAttrRender);
    		}else if(obj.groupRender){
    			eval("obj.groupRender="+obj.groupRender);
    		}else if(obj.totalRender){
    			eval("obj.totalRender="+obj.totalRender);
    		}else if(obj.pageSizeOptions){
    			eval("obj.pageSizeOptions="+obj.pageSizeOptions);
    		}
    		//处理ligerUI自带工具条toolbar的click函数
    		if(obj.toolbar){
    			var toolbar = obj.toolbar;
    			var items = toolbar.items;
    			for(var i in obj.toolbar.items){
    				eval("obj.toolbar.items["+i+"].click="+obj.toolbar.items[i].click);
    			}
    		}
    		
    		return obj;
    	},    	
        
    	_isNullBlank:function (obj){
    		if(obj && obj.length>0) return false;
    		return true;
    	},
    	
        _getUrlRootPath:function(){
        	//获取应用上下文路径
        	var contextpath = window.document.location.pathname;
        	var i = contextpath.indexOf("/");
        	if(i == 0){
        		contextpath = contextpath.substr(1);
        	}
        	i = contextpath.indexOf("/");
        	contextpath = "/" + contextpath.substr(0, i + 1);
        	
        	return contextpath;
        },
        //操作点击处理
        _operClick:function(oper,rowid){
        	var g=this,p=this.options;
        	if(!oper) return;
        	
        	//设置了处理函数
        	if($.isFunction(oper.pclick)){
        		oper.pclick(g,rowid);
        	}
        	
        	//未设置处理函数，一般是配置的列表
        	else{
    			var row = null,url=oper.url;
    			if(rowid){
    				row = g.grid.getRow(rowid);
    				eval("url="+oper.url);
    			}
    			
        		var urlpath = g._getUrlRootPath();
				if($.type(url)=='string' && $.trim(url)!=''){
					if(url.indexOf("http")!=0){
						url = urlpath + url;
					}
				}else{
					url = null;
				}
    			
    			//是否配置了JS函数
    			if(oper.jsFunName && oper.jsFunName.length>0 && oper.jsFilePath && oper.jsFilePath.length>0){
    				var func = eval("(window."+oper.jsFunName+")");
    				func(g,rowid);
    			}else{
    				if(oper.operType=='delete'){
    					if(url){
    						g._urlDelete(url,rowid);
    					}else{
    						url = urlpath + "com/datagrid/deleteDatas";
    						g._dataDelete(url);	
    					}				
    				}else if(oper.operType=='refresh'){
    					g.grid.refresh();
    				}else if(oper.operType=='export'){				
    					if(!url){
    						url = urlpath + "com/ine/export/open";
    					}
    					//会默认加上ids = ?参数 资源名称、组件id、资源类型、组件标识
    					g._dataExport(url,oper);				
    				}else if(oper.operType=='add'){
    					if(!url) return ;
    					g._windowOpen(url,oper);	
    				}else if(oper.operType=='modify'){
    					if(!url) return;
    					g._dataModify(url,oper,rowid);
    				}else if(oper.operType=='detail' || oper.operType=='print'){
    					if(!url) return;
    					g._windowOpen(url,oper);	
    				}else if(oper.operType=='import'){
    					if(!url){
    						url = urlpath + "com/ine/import/open";
    					}
    					//会默认加上ids = ?参数 资源名称、组件id、资源类型、组件标识
    					g._dataImport(url,oper);
    				}
    			}        		
        	}
        },
        //创建查询
        _createSearch:function(){
        	var g=this,p=this.options;
            if(!p.search || !p.dgOptions.isSearch) return;
            
        	g.searchDiv = $("<div style='padding-top:3px;padding-bottom:3px;'></div>");
        	g.datagrid.append(g.searchDiv);
        	p.search.width = p.dgOptions.width;
        	g.search=g.searchDiv.ligerSearch(p.search);
        	
        	g.search.setSearchClickFun(function(obj,op,rules){
        		//判断是否设置有查询控制函数
        		var __searchControlFunc = g.dgOptions.searchControlFunc;
        		if(!$.isFunction(__searchControlFunc)){
        			if(__searchControlFunc){
        				__searchControlFunc = eval("__searchControlFunc="+__searchControlFunc);
        				if(!$.isFunction(__searchControlFunc)){
        					__searchControlFunc = null;
        				}
        				g.dgOptions.searchControlFunc = __searchControlFunc;
        			}
        		}
        		
        		var ___searchFlag = false;
        		if(__searchControlFunc){
        			var __fieldsMapper = {};
        			$.each(rules.rules,function(i,o){
        				__fieldsMapper[o.field] = o;
        			});
        			___searchFlag = __searchControlFunc(g,__fieldsMapper);
        		}
        		
        		if(___searchFlag===true){
            		g._gridSearch(rules);
        		}else{
            		//需要输入条件才能查询
            		if(g.dgOptions.isNeedSearchValue===true){
            			var queryFlag =g._isCanQuery(rules.rules);
            			if(queryFlag===false){
            				alert("请输入查询条件");
            				return false;
            			}
            		}
            		//处理日期查询范围
            		if(g._validateQueryRange(rules.rules)===false){
            			alert("必须选择日期范围");
            			return false;
            		}
            		g._gridSearch(rules);            		
        		}
        	});
        },
        
        //校验查询范围
        _validateQueryRange:function(rules){
        	var g = this;
        	var queryFlag = true;
        	if(rules){
        		$.each(rules,function(i,o){
        			var field = g.search.filter.getField(o.field);
        			if(field.dateQueryRange && field.dateQueryRange!=''){
        				if(o.value && o.value.indexOf('|')>-1){
        					var vals = o.value.split('|');
        					if(vals[0]=='' || vals[1]=='' || vals[0]=='null' || vals[1]=='null'){
        						queryFlag = false;
        					}
        				}else{
        					queryFlag = false;
        				}
        			}
        			if(queryFlag==false)
        				return false;
        		});        		
        	}
        	return queryFlag;
        },
        
        //是否可以查询
        _isCanQuery:function(rules){
			var queryFlag = false;
			if(rules){
				$.each(rules,function(i,o){
					if(o.value && o.value!='' && o.value!='|' && o.value!='null|null'){
						queryFlag = true;
						return false;
					}
				});
			}
			return queryFlag;
        },
        
        //列表查询
        _gridSearch:function(rules){
        	var g=this,p=this.options;
        	if(!g.search) return;
        	if(p.dgOptions.searchAction == 'local'){
        		g._localSearch(rules);
        	}else if(p.dgOptions.searchAction == 'server'){        		
    			//处理前端分页、后台查数据的情况
    			g.grid.options.url = g.dgOptions.url;
    			p.dgOptions.url = g.dgOptions.url;
    			g.grid.options.dataAction = "server";
    			p.dgOptions.dataAction = "server";
    			g.grid.options.dataType = 'server';
    			p.dgOptions.dataType = 'server';
    			
        		g._serverSearch(rules);
        	}
        },
        //列表服务端查询
        _serverSearch:function(rules){
        	var g=this,p=this.options;        	
        	var parms = g.grid.get('parms') || {};
        	//列表查询条件
        	if(rules){
            	parms.where = $.ligerui.toJSON(rules);	
        	}
        	parms.quickFilter = '';   
        	
//        	//快速过滤
//			var checkIds = [];
//			$(".filter:checked",g.quickFiltersDiv).each(function(){
//				checkIds.push(this.value);
//			});
//        	parms.quickFilter = checkIds.join(",");
        	
        	//数据来源查询库标识
        	if(g.search){
        		parms.datafromcxk = g.search.datafromcxk;
        	}
        	
        	g.grid.set('parms', parms);
        	g.grid.options.newPage = 1;
            g.grid.loadData();           
        },
        //客户端查询
    	_localSearch:function(data)
        {
    		var g=this,p = this.options;
            var fnbody = ' return  ' + data;
            g.grid.loadData(new Function("o", fnbody));
        },
        //列表通用删除操作
        _dataDelete:function(url){
        	var g=this,p=this.options;
			if(!p.resource) {
				$.ligerDialog.error("请设置列表所关联的资源");
				return ;
			}
			if(p.resource.resType != '01'){
				$.ligerDialog.error("非数据表资源不允许使用通用删除");
				return ;        						
			}
			if(!p.resource.pkName){
				$.ligerDialog.error("请设置数据表主键名称");
				return ;        						
			}
			
			var gridManager = g.grid;
			var rows = gridManager.getSelecteds();
			if(!rows || rows.length==0){
				$.ligerDialog.error("请选择行");
				return;
			} 
			var ids = [];
			for(var i in rows){
				var id = rows[i][p.resource.pkName];
				ids.push(id);
			}
			//通用删除操作，ids,resId,resType,pkName
			var deleteUrl = url;
	        $.ligerDialog.confirm('您确定要删除吗?', function (yesOrNo){
	        	if(yesOrNo){
	        		var waitBox=$.ligerDialog.waitting('正在删除,请稍候...');
		        	$.ajax({
		                type: "get",
		                cache: false,
		                url: deleteUrl,
		                dataType: "json",
		                data:{resId:p.resource.resId,pkName:p.resource.pkName,ids:ids.join(",")},
		                success: function(data){
		                	waitBox.close();
		                    if(data.success){
		                    	$.ligerDialog.success(data.msg,"操作提示",function(){
		                    		gridManager.refresh();
		                    	});        				                    	
		                    }
		                    else{
		                    	$.ligerDialog.error(data.msg);
		                    }
		                }
		            });
	        	}
	        });
        },
        //列表导出操作
        _dataExport:function(url,oper){
        	var g=this,p=this.options;
			if(!p.resource) {
				$.ligerDialog.error("请设置列表所关联的资源");
				return ;
			}
			var gridManager = g.grid;
			var rows = gridManager.getSelecteds();
			var idsStr = "";
			if(rows && rows.length>0){
				var ids = [];
				for(var i in rows){
					var id = "'" + rows[i][p.resource.pkName] + "'";
					ids.push(id);
				}
				idsStr = ids.join(",");	    					
			}
			
			var exportUrl = url;
			if(url.indexOf("?")>0){
				exportUrl = exportUrl + "&ids=" + idsStr;
			}else{
				exportUrl = exportUrl + "?ids=" + idsStr;
			}
			
			var params = [],paramString="";
			for(var str in p.resource){
				var pm = str + "=" + p.resource[str];
				params.push(pm);
			}
			
			if(params && params.length>0){
				paramString = params.join("&");
			}
			
			if(paramString.length>0){
				exportUrl = exportUrl + "&" + paramString;
			}
			
			var gridParams = g.grid.get('parms');
			var exportParams = {};
			for(var pm in gridParams){
				if(pm in p.resource) continue;
				exportParams[pm] = gridParams[pm];
			}
			//导出时只能用openMode='2'的方式，因为要传参数
			oper.openMode = "2";
			oper.pageHeight = 300;
			g._windowOpen(exportUrl,oper,exportParams);
        },
        
        //数据导入
        _dataImport:function(url,oper){
        	var g=this,p=this.options;
        	
			if(!p.resource) {
				$.ligerDialog.error("请设置列表所关联的资源");
				return ;
			}
			
			var importUrl = url;
			
			var params = [],paramString="";
			for(var str in p.resource){
				var pm = str + "=" + p.resource[str];
				params.push(pm);
			}
			
			if(params && params.length>0){
				paramString = params.join("&");
			}
			
			if(paramString.length>0){
				if(url.indexOf("?")>0){
					importUrl = importUrl + "&" + paramString;
				}else{
					importUrl = importUrl + "?" + paramString;
				}
			}
			g._windowOpen(importUrl,oper);
        },
        
        //修改操作
        _dataModify:function(url,oper,rowid){
        	var g=this,p=this.options;
			var gridManager = g.grid;
			var row=null;
			if(rowid){
				row = gridManager.getRow(rowid);
			}else{
				var rows = gridManager.getSelecteds();
				if(rows && rows.length>0){
					row = rows[0];
				}
			}
			if(!row){
				$.ligerDialog.error("请选择行");
				return;
			}
			
			g._windowOpen(url,oper);
        },
        //打开窗口
        _windowOpen:function(url,oper,params){
    		var width = oper.pageWidth;
    		var height = oper.pageHeight;    		
        	if(!width || Number(width)==0){
        		width = 800;
        	}
        	if(!height || Number(height)==0){
        		height = 550;
        	}
        	
        	if(oper.openMode=='1'){	        	
	        	top.$.ligerDialog.open({
	        		id:'oper_modal_dlg',
	        		url:url,
	        		width:width,
	        		height:height,
	        		isResize:true
	        	});
        	}else if(oper.openMode=='2'){
        		var showProps = "dialogWidth="+width+"px;dialogHeight="+height+"px";
        		window.showModalDialog(url,params,showProps);
        	}else if(oper.openMode=='3'){
        		window.open(url);
        	}else if(oper.openMode=='4'){
        		window.location.href = url;
        	}       	
        },
        //处理参数
        _buildParams:function(oper){
        	var paramNames = oper.paramName;
        	var paramValues = oper.paramValue;
        	if(!paramNames || paramNames.length==0) return null;
        	var paramAry = [];
        	for(var i=0;i<paramNames.length;i++){
        		var s = paramNames[i] + "=" + paramValues[i];
        		paramAry.push(s);
        	}
        	
        	return paramAry.join("&");
        },
        //行操作点击
        _rowOperClick:function(e,oper,rowid){
        	var g=this,p=this.options;
        	//阻止事件冒泡，防止选中数据行
			if(e && e.stopPropagation){
				e.stopPropagation();
			}else{
				e.cancelBubble = true;
			}
        	if(oper.isBatch){
				if(oper.isBatch && oper.children && oper.children.length==1){
					oper = oper.children[0];
					g._operClick(oper,rowid);
				}else{
					g._rowBatchOperClick(e, oper, rowid);
				}
        	}else{
        		g._operClick(oper,rowid);
        	}
        },
        //行批量操作点击
		_rowBatchOperClick:function(e,oper,rowid){
	       	var g=this,p=this.options;
			var row = g.grid.getRow(rowid);
			//创建行操作
			var items = [];
			$.each(oper.children,function(i,data){
				if(!g._operFilter(data, row)){
					var item = {text:data.name,click:itemClick};
					item = $.extend(item,data);		
					items.push(item);				
				}
			});			
			function itemClick(oper){
				g.menu.hide();
				g._operClick(oper,rowid);
			}			
			if(items.length>0){
				$(".l-menu").remove();
				$('.l-menu-shadow').remove();
				//创建菜单需要时间，需延迟几毫秒
				g.menu = $.ligerMenu({top:50,left:10,width:130,items:items});
				window.setTimeout(function(){
					//计算展示位置
					var posX = e.pageX;
					if((posX+130) > $(window).width()){
						posX = $(window).width()-150;
					}
					var posY = e.pageY;
					if((posY+(items.length * 23)) >= $(window).height()){
						posY = posY-(items.length * 23)-5;
					}
					g.menu.show({ top: posY, left: posX });					
				}, 10);
			}
		},
		//头操作过滤
		_headOperFilter:function(oper){
			var g=this,p=this.options;
			var user=null,req=null;
			if(p.context){
				if(p.context.user) user = p.context.user;
				if(p.context.req) req = p.context.req;
			}
			var isFilter = false;
			if(oper.filterNames && oper.filterNames.length>0){
				var filterFlag = true;
				var filterValues = oper.filterValues;
				if(filterValues && filterValues.length>0){
					for(var i in oper.filterNames){
						var value = eval("("+oper.filterNames[i]+")");
						if(filterValues[i]=='NULL'){
							if(!value || value.length==0){
								filterFlag = false;
							}
						}else if(filterValues[i]=='NON-NULL'){
							if(value && value.length>0){
								filterFlag = false;
							}
						}
						if(filterFlag == false) break;
						
						var values = filterValues[i].split("|");
						for(var j in values){
							if(value==values[j]){
								filterFlag = false;
								break;
							}
						}
						if(filterFlag == true)break;
					}
				}
				isFilter = filterFlag;
			}
			//配置了过滤函数
			else if(oper.filterFunction && oper.filterFunction.length>0){
				var fun = null;
				eval("fun="+oper.filterFunction);
				if(fun(user,req)===false){
					isFilter = true;
				}
			}
			
			return isFilter;
		},		
		//行操作过滤
		_operFilter:function(oper,rowdata){
			var row = rowdata;
			var isFilter = false;
			if(oper.filterNames && oper.filterNames.length>0){
				var filterFlag = true;
				var filterValues = oper.filterValues;
				if(filterValues && filterValues.length>0){
					for(var i in oper.filterNames){
						var value = eval("("+oper.filterNames[i]+")");
						if(filterValues[i]=='NULL'){
							if(!value || value.length==0){
								filterFlag = false;
							}
						}else if(filterValues[i]=='NON-NULL'){
							if(value && value.length>0){
								filterFlag = false;
							}
						}
						if(filterFlag == false) break;
						
						var values = filterValues[i].split("|");
						for(var j in values){
							if(value==values[j]){
								filterFlag = false;
								break;
							}
						}
						if(filterFlag == true)break;
					}
				}
				isFilter = filterFlag;
			}
			//配置了过滤函数
			else if(oper.filterFunction && oper.filterFunction.length>0){
				var fun = null;
				eval("fun="+oper.filterFunction);
				if(fun(row)===false){
					isFilter = true;
				}
			}
			
			return isFilter;
		},
		//创建行操作
		_createRowOper:function(oper,operindex,rowid){
			var g=this,p=this.options;
			var renderHtml = [];
			var rowHeight = p.dgOptions.rowHeight;
			if(!rowHeight || rowHeight==0)rowHeight=25;
			if(!oper.isBatch){       				        				
    			if(!g._isNullBlank(oper.iconPath)){
    				renderHtml.push("<a class='icon iconbg row-oper' id='oper_"+operindex+"_"+rowid+"'>");
    				renderHtml.push("<img src='"+p.iconsPath + oper.iconPath+"' width='16' height='16'>");
    				renderHtml.push(oper.name);
    				renderHtml.push("</a>");
    			}else{
    				renderHtml.push("<input style='height:25px;padding:2px 2px;margin-left:3px;' ");
    				renderHtml.push("class='common-btn row-oper' value='"+oper.name+"' ");
    				renderHtml.push("type='button' id='oper_"+operindex+"_"+rowid+"'/>");
    			}
			}else{
				if(oper.children && oper.children.length>0){
					if(oper.children.length<2){
						var temp = oper.children[0];
						if(!g._isNullBlank(temp.iconPath)){
    	    				renderHtml.push("<a class='icon iconbg row-oper' id='oper_"+operindex+"_"+rowid+"'>");
    	    				renderHtml.push("<img src='"+p.iconsPath + temp.iconPath+"' width='16' height='16'>");
    	    				renderHtml.push(temp.name);
    	    				renderHtml.push("</a>");
						}else{
							renderHtml.push("<input style='height:25px;padding:2px 2px;margin-left:3px;' ");
		    				renderHtml.push("class='common-btn row-oper' value='"+temp.name+"' ");
		    				renderHtml.push("type='button' id='oper_"+operindex+"_"+rowid+"'/>");
						}
					}else{
						if(!g._isNullBlank(oper.iconPath)){
    	    				renderHtml.push("<a class='icon iconbg row-oper-batch' id='oper_"+operindex+"_"+rowid+"'>");
    	    				renderHtml.push("<img src='"+p.iconsPath + oper.iconPath+"' width='16' height='16'>");
    	    				renderHtml.push(oper.name);
    	    				renderHtml.push("</a>");
						}else{
							renderHtml.push("<input style='height:25px;padding:2px 2px;margin-left:3px;' ");
		    				renderHtml.push("class='common-btn row-oper' value='"+oper.name+"' ");
		    				renderHtml.push("type='button' id='oper_"+operindex+"_"+rowid+"'/>");
						}
					}
				}	        				
			}
			
			return renderHtml.join("");
		},
		//头操作点击
		_headOperClick:function(e,oper){
			var g=this,p=this.options;
			if(oper.isBatch){
				if(oper.children && oper.children.length>0){
					g._headBatchOperClick(e, oper);
				}
			}else{
    			g._operClick(oper); 
			}
		},
		//头部批量操作点击
		_headBatchOperClick:function(e,oper){
			var g=this,p=this.options;
			var items = [];
			$.each(oper.children,function(i,child){				
				var item = {text:child.name,click:itemClick};
				item = $.extend(item,child);			
				items.push(item);
			});
			
			function itemClick(oper){
				g.menu.hide();
				g._operClick(oper);
			}
			
			//创建菜单需要时间，需延迟几毫秒
			g.menu = $.ligerMenu({top:50,left:50,width:120,items:items});						
			window.setTimeout(function(){g.menu.show({ top: e.pageY, left: e.pageX });}, 10);
		},
		//url地址删除
		_urlDelete:function(url,rowid){
			var g=this,p=this.options;
			if(!p.resource) {
				$.ligerDialog.error("请设置列表所关联的资源");
				return ;
			}
			if(!p.resource.pkName){
				$.ligerDialog.error("请设置列表行标识名称！");
				return ;        						
			}			
			var gridManager = g.grid;
			var rows = [];
			if(rowid){
				var row = gridManager.getRow(rowid);
				rows.push(row);
			}else{
				rows = gridManager.getSelecteds();
			}
			if(!rows || rows.length==0){
				$.ligerDialog.error("请选择行");
				return;
			} 
			var ids = [];
			for(var i in rows){
				var id = rows[i][p.resource.pkName];
				ids.push(id);
			}
			var deleteUrl = url + ids.join(",");
	        $.ligerDialog.confirm('您确定要删除所选中的数据吗?', function (yesOrNo){
	        	if(yesOrNo){
	        		var waitBox=$.ligerDialog.waitting('正在删除,请稍候...');
		        	$.ajax({
		                type: "get",
		                cache: false,
		                url: deleteUrl,
		                dataType: "json",
		                success: function(data){
		                	waitBox.close();
		                    if(data.success){
		                    	$.ligerDialog.success(data.msg,"操作提示",function(){
		                    		gridManager.refresh();
		                    	});        				                    	
		                    }
		                    else{
		                    	$.ligerDialog.error(data.msg);
		                    }
		                }
		            });
	        	}
	        });			
		}
    });   
})(jQuery);

/**
* jQuery ligerUI 1.2.0
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 查询插件 dengshaoxiang
*/

(function ($)
{
    var l = $.ligerui;

    $.fn.ligerSearch = function (options)
    {
        return $.ligerui.run.call(this, "ligerSearch", arguments);
    };

    $.fn.ligerGetSearchManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetSearchManager", arguments);
    };
	
    //接口方法扩展
    $.ligerMethos.Search = $.ligerMethos.Search || {};
    
    $.ligerui.controls.Search = function (element, options)
    {
        $.ligerui.controls.Search.base.constructor.call(this, element, options);
    };
    
    $.ligerDefaults.Search = {
            //字段列表
    		fields:[],
            dataAction: 'local',
            click:null,
            types:[1],           
            lineNum:1,
            showType:'1',
            filters:{},
            initRule:null,
            width:'auto',
            enableDataFromCxk:false
        };
    $.ligerDefaults.Search.typeNames = [{en:'simple',cn:'简单查询'},{en:'normal_level',cn:'普通查询'},{en:'high_level',cn:'高级查询'},{en:'custom',cn:'自定义查询'}];
    
    $.ligerui.controls.Search.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.controls.Search';
        },
        __idPrev: function ()
        {
            return 'search';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.Search;
        },
        _init: function ()
        {
            $.ligerui.controls.Search.base._init.call(this);
            var g = this, p = this.options;
            //没有设置字段直接退出
            if(!p.fields || p.fields.length==0) return;
            
            //处理json
            if(typeof(p.types)=="string"){
            	p.types = eval("("+p.types+")");
            }
            if(!p.types || !p.types instanceof Array) p.types=[1];
            
            //当前展示类型
            g.currShowType = null;
            g.filter = null;
            //设置重置的值
            g.initRule = null;
            if(p.initRule){
            	if(typeof(p.initRule)=='string'){
            		p.initRule = eval("("+p.initRule+")");
            	}
            	g.initRule = p.initRule;
            }
            
            //处理宽度
            p.width = p.width || 'auto';
            
            p.enableDataFromCxk = p.enableDataFromCxk || false;
            g.datafromcxk = false;
        },
        _render: function ()
        {
            var g = this, p = this.options;
            g.search = $(g.element);
            g.search.html("<table cellpadding='0' style='width:100%;' cellspacing='0' border='0' name='search_table' id='table_"+g.id+"'></table>");            
            g.table = $("table:first",g.search);
            
            //创建查询控件
            g._buildFilter(p.showType);
            
            g.width(p.width);
            
            //普通文本框enter后触发查询
            g.search.find('.l-filter-value').delegate('input[type=text]','keyup',function(e){
   				if(e.keyCode==13){
   					g.searchBtn.trigger('click');
   				}
   			});
            
            //字典enter选中后触发查询
            g.search.find('.l-filter-value').delegate('.result-container','keyup',function(e){
   				if(e.keyCode==13){
   					g.searchBtn.trigger('click');
   				}
   			});
        },
        //构建查询器
        _buildFilter:function(type){
        	var g = this, p = this.options;
        	
        	if(!type || type=='') return ;
        	
        	//查询器类别名称
        	var typeNameCn = p.typeNames[type-1]['cn'];
        	
        	//待构建查询器的查询字段
        	var filterFields = g._getFilterFields(type);
        	if(filterFields.length==0){
        		alert("请设置"+typeNameCn+"控件的字段");
        		return ;
        	}
        	
        	//清空之前的
        	if(g.filter){
        		g.filter.destroy();
        		g.filter = null;
        		g.table.empty();
        	}
        	
        	//生成控件id
			var date = new Date();
			date = "" + date.getTime();
			var filterId = date.substring(date.length-6, date.length);
			
			//查询器类别名称
        	var typeName = p.typeNames[type-1]['en'];
        	 
	   		var trHtml = [];
	   		trHtml.push("                <tr class='filter_"+type+"'>");
	   		trHtml.push("                  <td>");
	   		trHtml.push("                     <div name='"+typeName+"' id='"+filterId+"'>");        
	   		trHtml.push("                     </div>");         
	   		trHtml.push("                  </td>");        
	   		trHtml.push("                </tr>");
	   		
	   		var tr = $(trHtml.join(""));
	   		g.table.append(tr);
	   		
	   		var filterOption = {};
			filterOption.fields = g._getFilterFields(type);
			filterOption.type = type;
			filterOption.lineNum = p.lineNum;

			g.filter = $("#"+filterId).ligerFilter(filterOption);
			
	   		//处理初始规则
	   		if(g.initRule && g.initRule == p.initRule && p.showType==type){
	   			g.filter.setData(p.initRule);
			}else{
				//设置初始规则
				g.initRule = g.filter.getData();
			}
	   		
	   		g.currShowType = type;
	   		
            //构建查询按钮
            g. _createSearchBtn();
        },
        
        //构建切换按钮
        _createChangeBtn:function(){
        	var g = this, p = this.options;
        	if(p.types.length<2) return;
        	$.each(p.types,function(i,type){
        		if(g.currShowType == type) return;
        		var btn = $("<input name='changeBtn_"+type+"' style='margin-left:5px;' class='common-btn-new' type='button' value='"+p.typeNames[type-1]['cn']+"' />");
        		g.btnContainer.append(btn);
        		if(g.currShowType == type) btn.hide();
        		btn.bind('click',function(){
        			var showType = this.name.split("_")[1];
        			//显示查询控件
        			g._buildFilter(showType);
        		});
        	});
        },
        
        //构建查询按钮
        _createSearchBtn:function(){
        	var g = this, p = this.options;
        	var btnhtmlarr = [];
        	btnhtmlarr.push("<span class='btn_"+g.id+"'>");
        	btnhtmlarr.push("   <input name='search' class='common-btn-new' type='button' value='查 询' id='subBtn_"+g.id+"'/>");       
        	btnhtmlarr.push("   <input name='reset' class='common-btn-new' type='button' value='重 置' id='resetBtn_"+g.id+"'/>");
        	btnhtmlarr.push("</span>");
        	var btnSpan = $(btnhtmlarr.join(""));
        	
        	var type = g.currShowType;
        	
        	var tr = $("<tr></tr>");
        	var td = $("<td align='center' style='padding-bottom: 5px; padding-top: 5px;'></td>");
        	
        	if(type=='1'){
        		tr = $("tr:last",g.filter.group).prev();
        		td = $("<td align='left' style='min-width:180px;'></td>");
        		td.append(btnSpan);
        		tr.append(td);	
        	}else{
        		td.append(btnSpan);
        		tr.append(td);
        		g.table.append(tr);
        	}
        	
        	g.btnContainer = td;
        	
        	//创建切换按钮
        	g._createChangeBtn();
        	
    		if(p.isHistory===true && g.currShowType != '1'){
    			//保存方案按钮
    			g.btnContainer.append("<a name='saveHistory' href='javascript:void(0)' style='color:blue;margin-left:5px;'>保存方案</a>");
    			$("a[name='saveHistory']",g.btnContainer).bind('click',function(){
    				var ruleGroup = JSON2.stringify(g.getRules("server"));
    				var saveUrl = g._getUrlRootPath() + "com/model/saveSearchSchm";
    				$.ligerDialog.prompt('保存方案','请输入方案名称', function (yes,value) {
    					if(yes){
    						if(!value || value=='') return ;
    						$.ajax({
    							url:saveUrl,
    							data:{modelId:p.modelId,searchRule:ruleGroup,name:value},
    							dataType:'json',
    							type:'post',
    							success:function(responseText,statuText){
    								if(statuText=='success'){
    									if(responseText.success){
    										$.ligerDialog.success(responseText.msg);
    									}
    								}
    							}
    						});
    					}
    				});
    			});
    			
    			//选择历史方案
    			g.btnContainer.append("<a name='history' href='javascript:void(0)' style='color:blue;margin-left:5px;'>历史方案</a>");
    			$("a[name='history']",g.btnContainer).bind('click',function(){
    				var div = $("<div></div>");
    	            var historyDgOptions = {
    	                    columns: [
    	                    { display: '方案名称', name: 'name',width:160},
    	                    { display: '查询规则', name: 'searchRule', width: 280,useTitle:true},
    	                    { display: '添加时间', name: 'addtime', width: 130}
    	                    ],
    	                    url: g._getUrlRootPath() + "com/model/getSearchShcm",
    	                    pageSize: 10,
    	                    checkbox: false,
    	                    parms:{modelId:p.modelId},
    	                    rownumbers:true,
    	                    pageBarType:'1',
    	                    toolbar:{items: [{text: '删除方案', id:'delete', click: function(){
    	                    	var row = dg.getSelected();
                        		if(row){
                        			$.ajax({
                        				url:g._getUrlRootPath()+"com/model/deleteSchm/"+row.id,
                        				dataType:'json',
                        				type:'GET',
                        				success:function(data, textStatus){
                        					if(textStatus=='success'){
                        						if(data.success){
                        							$.ligerDialog.success(data.msg);
                        							dg.reload();
                        						}else{
                        							$.ligerDialog.error(data.msg);
                        						}
                        					}
                        				}
                        			});
                        		}
    	                    }, icon:'delete'}]},
    	                    width:620,
    	                    height:300
    	                };
    	            var dg = div.ligerGrid(historyDgOptions);
    				$.ligerDialog.open({ 
    					target: div,
    					title:'历史方案',
    					width:650,
    					height: 400,
    					buttons: [
                                    { text: '选择', onclick: function (item, dialog) {
                                    		var row = dg.getSelected();
                                    		if(!row){
                                    			dialog.close();
                                    			return;
                                    		}
                                    		var group = eval("("+row.searchRule+")");
                                    		g.filter.setData(group);
                                    		dialog.close();
                                       }
                                    },
                                    { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
                                 ]
    				});
    			});
    			
    		}
    		
    		//是否允许数据来源于查询库
    		if(p.enableDataFromCxk===true){//查询按钮为6个月内数据，勾选更多点击查询为全部数据
    			g.btnContainer.append("<input type='checkbox' style='margin-left:15px;margin-right:2px;' name='datafromcxk' id='datafromcxk' value='true'/><label for='datafromcxk' style='font-weight:bold;color:red;right:20px;'>更多</label><span style='margin-left:10px;font-weight:bold;color:red;'>(默认“查询”为6个月内数据，勾选“更多”点击“查询”为全部数据)</span>");
    			$("#datafromcxk",g.btnContainer).bind('click',function(){
    				g.datafromcxk = this.checked;
    			});
    		}
    		
        	//绑定按钮事件
            g.searchBtn = $(".common-btn-new[name='search']",g.btnContainer);
            g.resetBtn = $(".common-btn-new[name='reset']",g.btnContainer);
            
            g.searchBtn.bind('click',function(){
            	var rules = g.getRules(p.dataAction);
            	if(!p.click) return;
            	p.click(g,p,rules);
            });
            
            g.resetBtn.bind('click',function(){
            	if(g.currShowType == '1'){
            		g._buildFilter(g.currShowType);
            	}
            	else{
            		g.filter.setData(g.initRule);
            	}
            });
        },
        
    	//翻译规则
        translateGroup:function(group)
        {
        	var g = this;
            var out = [];
            if (group == null) return " 1==1 ";
            var appended = false;
            out.push('(');
            if (group.rules != null)
            {
                for (var i in group.rules)
                {
                    var rule = group.rules[i];
                    if (appended)
                        out.push(g.getOperatorQueryText(group.op));
                    out.push(g.translateRule(rule));
                    appended = true;
                }
            }
            if (group.groups != null)
            {
                for (var j in group.groups)
                {
                    var subgroup = group.groups[j];
                    if (appended)
                        out.push(g.getOperatorQueryText(group.op));
                    out.push(g.translateGroup(subgroup));
                    appended = true;
                }
            }
            out.push(')');
            if (appended == false) return " 1==1 ";
            return out.join('');
        },

        translateRule:function(rule)
        {
        	var g = this;
            var out = [];
            if (rule == null) return " 1==1 ";
            if(rule.op == "like"){
            	if(rule.op == "like" && $.trim(rule.value).length==0){
            		return "true";
            	}
            	out.push('/');
            	out.push(".*?");
            	var value = $.trim(rule.value);
            	for(var i=0;i<value.length;i++){
            		var c = value.charAt(i);
            		out.push(c);            		
            	}
            	out.push(".*?");
                out.push('/i.test(');
                out.push('o["');
                out.push(rule.field);
                out.push('"]');
                out.push(')');
                
            	return out.join('');
            }
            else if (rule.op == "startwith" || rule.op == "endwith")
            {
                out.push('/');
                if (rule.op == "startwith")
                    out.push('^');
                out.push(rule.value);
                if (rule.op == "endwith")
                    out.push('$');
                out.push('/i.test(');
                out.push('o["');
                out.push(rule.field);
                out.push('"]');
                out.push(')');
                return out.join('');
            }
            
            out.push('o["');
            out.push(rule.field);
            out.push('"]');
            out.push(g.getOperatorQueryText(rule.op));
            out.push('"');
            out.push(rule.value);
            out.push('"');
            return out.join('');
        },

        getOperatorQueryText:function (op)
        {
            switch (op)
            {
                case "equal":
                    return " == ";
                case "notequal":
                    return " != ";
                case "greater":
                    return " > ";
                case "greaterorequal":
                    return " >= ";
                case "less":
                    return " < ";
                case "lessorequal":
                    return " <= ";
                case "and":
                    return " && ";
                case "or":
                    return " || ";
                default:
                    return " == ";
            }
        },
        
        //获取应用上下文路径
        _getUrlRootPath:function(){
        	var contextpath = window.document.location.pathname;
        	var i = contextpath.indexOf("/");
        	if(i == 0){
        		contextpath = contextpath.substr(1);
        	}
        	i = contextpath.indexOf("/");
        	contextpath = "/" + contextpath.substr(0, i + 1);
        	
        	return contextpath;
        },
        
        //获取查询控件的字段信息
        _getFilterFields:function(type){
        	var p = this.options;
        	
        	var fields = [];
        	if(!type || type=='') return fields;
        	if(!$.isArray(p.fields)) return fields;
        	
        	for(var i in p.fields){
        		var beLongTypes = ['1','2'];
        		if(p.fields[i].beLongType && p.fields[i].beLongType != '' ){
        			beLongTypes = p.fields[i].beLongType.split(',');
        		}
        		if($.inArray(type,beLongTypes) != -1){
        			fields.push(p.fields[i]);
        		}
        	}
        	
        	return fields;
        },
        
        //获取规则:server端 则直接返回规则josn对象,local端直接返回过滤正则表达式串
        getRules:function(actionType){
        	var g = this, p = this.options;
        	actionType = actionType || 'local';
        	var data = g.filter.getData();
        	if(actionType == 'server') return data;
        	if(actionType == 'local') return g.translateGroup(data);
        },
        
        //设置点击查询回调函数
        setSearchClickFun:function(func){
        	var g=this,p=this.options;
        	if(!func || typeof(func) != 'function') return;
        	p.click = func;
        },
        //设置控件宽度
        width:function(w){
        	var g=this,p=this.options;
        	if(!g.search || !g.table || !w) return;
        	g.search.width(w);
        }
    });
    
})(jQuery);
