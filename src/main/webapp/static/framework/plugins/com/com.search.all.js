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

/**
 * 数据字典 Dictionary.js
 */
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
