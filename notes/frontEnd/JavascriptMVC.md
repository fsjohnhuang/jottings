## $.Class ##
用于定义类
格式：`$.Class(类名, 实例成员)`、`$.Class(类名, 类成员, 实例成员)`
**注意：**类成员和实例成员会直接被子类继承
### 1. 基本用法 ###
```javascript
	steal('jquery/class', function($){
		// 定义类Todo
		$.Class('Todo', {
			show: function(){
				alert('class property');
			}
		}, {
			show: function(msg){
				alert('instance property' + msg);
			}
		});

		Todo.show();
		var todo = new Todo();
		todo.show();
		
		// 定义子类ChildTodo
		Todo('ChildTodo', {
			// init方法会在实例初始化时调用
			init: function(name){
				this.name = name;
			},
			show: function(msg){
				// 通过this._super可以获取父类实例
				this._super.show(msg + '!child');
			},
			showName: function(){
				aler(this.name);
			}
		});
		var childTodo = new ChildTodo('john');
		childTodo.show('test');
		childTodo.showName();
	});
```
### 2. 通过内部this.callback生成绑定scope的函数 ###
调用`this.callback(Class实例属性名称)`后将返回一个Class实例属性名称指向的函数，且该函数scope中的this被指向Class实例，效果同Function.prototype.bind一样
```javascript
	$.Class('Person', {
		init: function(){
			this.count = 0;
		},
		plus: function(){
			this.count += 1;
		},
		listen： function($el){
			$el.click(this.callback('plus'));
		}
	});
    var person = new Person();
    person.listen($('div'));
    // 点击2次div后
	console.log(person.count); // 输出2
```
### 3. 命名空间 ###
```javascript
	$.Class('Jupiter.Person');
	console.log(Jupiter.Person.shortName); // Person
	console.log(Jupiter.Person.fullName); // Jupiter.Person
	console.log(Jupiter.Person.namespace); // Jupiter

	var person = new Jupiter.Person();
	console.log(person.Class.shortName); // Person
	console.log(person.Class.fullName); // Jupiter.Person
	console.log(person.Class.namespace); // Jupiter
 
```

## $.Model ##
用于处理数据和业务逻辑
### 1. 定义Model类 ###
语法:`$.Model(模型名称, 实例成员)`、`$.Model(模型名称, 类成员, 实例成员)`
```javascript
	steal('jquery/class', 'jquery/model', function($){
		$.Model('Todo', {
			show: function(){
				alert(this.attr('name') + ', ' + this.attr('age'));
			}
		});
		var todo = new Todo({name: 'john'});
		todo.attr('age', 12);
	});
```
### 2. 直接生成Model实例 ###
```javascript
	var paginate = $.Model({
		limit: 20,
		offset:0,
		count: 2000
	});
```
### 3. 操作远程数据 ###
1. 获取单条数据：`Model类.findOne(params, successHandler(model实例), errHandler(jqXH))`;
2. 获取多条数据：`Model类.findAll(params, successHandler(model实例), errHandler(jqXH))`;
3. 新建数据：`Model实例.save(successHandler(model实例), errHandler(jqXH))`;
4. 修改单条数据：`Model实例.save(successHandler(model实例), errHandler(jqXH))`;
5. 删除单条数据：`Model实例.destroy(successHandler(model实例), errHandler(jqXH))`
6. 注意：`findOne`、`findAll`、`save`和`delete`方法都会返回一个`deferred对象`
```javascript
	steal('jquery/class', 'jquery/model', 'jquery.dom.fixture', function($){
		var dummy = [{
			id: 'sn1',
			name: 'John',
			age: 12
		}, {
			id: 'sn2',
			name: 'Maray',
			age: 22
		}];

		/* 使用$.fixture来模拟数据远程访问
		 * orig：传递给$.ajax的设置
		 * settings：$.ajax的标准设置
		 * header：请求头
		 */
		// 模拟findAll所请求的服务端
		$.fixture('GET /todo', function(orig, settings, header){
			return [dummy]; // 要返回[[{},{}]]才能生成Model实体
		});
		// 模拟findOne所请求的服务端
		$.fixture('GET /todo/{id}', function(orig, settings, header){
			return dummy[orig.data.id]; // 要返回{}才能生成Model
		});
		// 模拟create所请求的服务端
		$.fixture('POST /todo', function(orig, settings, header){
			return {id: 'sn3'};
		});
		// 模拟update所请求的服务端
		$.fixture('PUT /todo/', function(orig, settings, header){
			return {};
		});
		// 模拟delete所请求的服务端
		$.fixture('DELETE /todo', function(orig, settings, header){
			return {};
		});

		// 使用Model来访问远程数据
		$.Model('Todo', {
			findOne: 'GET /todo/{id}',
			findAll: 'GET /todo',
			create: 'POST /todo',
			update: 'PUT /todo',
			delete: 'DELETE /todo'
		}, {});
		var todo;
		// 获取数据
		Todo.findOne({id: 'sn1'}, function(model){
			// success
			todo = model;
		}, function(){
			// error
		});
		todo.attr('name', 'Key');
		// 修改数据，save会自动执行create或update请求
		todo.save(function(model){
			// success
		}, function(){
			// error
		});
	});
```
### 4. 监听Model类事件 ###
通过`Model类.bind(事件名, function(evt, model){})`对Model实例的`created`、`updated`、`destroyed`进行监听
```javascript
	Todo.bind('created', function(evt, model){
		// .....
	});
```
### 5. 监听Model实例事件 ###
通过`Model实例.bind(事件名, function(evt, model){})`对Model实例的`created`、`updated`、`destroyed`和其他属性变化进行监听
```javascript
	var todo = new Todo({name: 'test', age: 12});
	todo.bind('name', function(evt, newVal){
		$('#detail').html(newVal);
	});
	todo.bind('attr.age', function(evt, newVal){
		$('#age').html(newVal);
	});
```
### 6. 将模型实例附加到jQuery对象 ###
```javascript
	// 附加
	var todo = new Todo({id: 'sn1'});
	$('li').model(todo);

	// 获取数据
	$('li').model().id;

	// 获取模型对象附加到的jQuery元素
	todo.elements([搜索上下文context]);
```
### 7. 实例成员默认值 ###
通过类属性`defaults`设置其实例成员默认值
```javascript
	$.Model('Todo', {
		defaults: {
			name: 'test',
			age: 12
		}
	}, {});
	var todo = new Todo();
	console.log(todo.name + ',' + todo.age); // 输出test,12
```
### 8. 数据类型转换 ###
### 9. 合法性验证 ###

## $.Model.List ##
作用：用一次请求CUD多个Model实例


## $.View ##
### 1. 定义模板 ###
可使用ejs，jaml，jQuery-tmpl和Mustache等模板引擎
定义方式1：内置script标签
```html
	<script id="tpl" type="text/ejs">
		<ul>
			<% for(var i = 0; i < this.length; i++){ %>
				<li><%= this[i].name%></li>
			<% } %>
		</ul>
	</script>
```
定义方式2：独立模板文件
tpl.ejs
```
<ul>
	<% for(var i = 0; i < this.length; i++){ %>
		<li><%= this[i].name%></li>
	<% } %>
</ul>
```
### 2. 引入模块 ###
```javascript
	steal('jquery/model'，'jquery/view/ejs', function($){
		// .....
	});
```
### 3. 使用$.View(idOrUrl, data)来调用模板 ###
```javascript
	// 通过id获取模板
	$.View('tpl', [{name: 'John'},{name: 'Mary'}]);
	// 通过独立模板文件
	$.View('tpl.ejs', [{name: 'John'},{name: 'Mary'}]);
```
### 4. 使用$的after、append、before、html、prepend、replaceWith和text来调用模板 ###
```javascript
	$(document.body).append('tpl', [{name: 'John'},{name: 'Mary'}]);
```
### 5. 使用$.Model.findAll和findOne加载数据，延迟调用模板 ###
```javascript
	// 单个延迟
	$(document.body).append('tpl', Todo.findAll());

	// 多个延迟
	$(document.body).append('tpl', {
		todos: Todo.findAll()
		, users: User.findAll()});
```
### 6. 将Model数据绑定到View上 ###
使用`$.View.hookup`将Model绑定到View上
语法`<%= ($el) -> $el.model(Model实例)%>`，`$el`是当前的html标签的jQuery对象
**注意：**$.View.hookup的函数仅在模板生成html后才被调用，所以直接使用`for`循环语句时会出现所有索引i的引用均为最后一个i的值，需要用闭包处理。
```ejs
<ul>
	<% $.each(this, function(index, todo){ %>
		<li <%= ($el) -> $el.model(todo) %> ><%= this[i].name%></li>
	<% }) %>
</ul>
```
### 7. 子模板 ###
```ejs
	<ul>
		<% for(var i = 0, len = this.length; i < length; ++i){ %>
			<li>
				<%= $.View('子模板id或url', this[i]) %>
			</li>
		<% } %>
	</ul>
```
### 8. 打包和预加载 ###
默认情况下，$.View会同步加载模板（就是会阻塞其他代码的执行）。
所以要提高性能，我们需要在Javascript编译的时候（将多个Javascript文件打包成一个文件并做混淆和压缩）将应用初始化时需要的模板一同打包，和后续采用的模板就使用预加载的方式
8.1. 打包
```javascript
	steal.views('tasks.ejs', 'task.ejs');
```
8.2. 预加载
```javascript
	$(window).load(function(){
		setTimeout(function(){
			$.get('task.ejs', function(){}, 'view');
			$.get('tasks.ejs', function(){}, 'view');
		}, 500);
	});
```

## $.Controller ##
用于将View和Model关联起来
### 1. 引入模块 ###
```javascript
	steal('jquery/controller', function($){
		// ......
	});
```
### 2. 基本用法 ###
```javascript
	// 创建Todos控制器类
	$.Controller('Todos',
		{
			// 设置this.options的默认属性
			defaults: {
				tpl: 'tpl.ejs'
			}
		}, 
		{
		// 和Model一样在实例化时调用init函数，但入参固定并会对入参进行预处理
		// @param {jQuery} $el jQuery对象
		// @param {Object} opts 静态选项
		init: function($el, opts){
			$el.html(opts.tpl, Todo.findAll());
			// 或使用自动生成的this.element和this.options，this.element与$el是一样，而this.options为默认的options与opts的合并，并且在Controller实例的任何方法中均可调用。
			this.element.html(this.options.tpl, Todo.findAll());
		}
	});
	// 实例化控制器，实现View和Model的关联
	new Todos('#todos', {tpl: 'tpl.ejs'});

	// 或通过属性实例化控制器，jmvc在定义Controller时自动为jQuery对象创建与Controller同名小写的属性来调用该Controller
	$('#todos').todos({tpl: 'tpl.ejs'});

	// 若Controller形如Ns.Name，那么就会自动生成ns_name属性
	// $('#todos').ns_name();

	// 使用默认的this.options
	$('#todos').todos();
```
### 3. 事件绑定 ###
```javascript
	// 创建Todos控制器类
	$.Controller('Todos', {
		init: function($el, opts){
			$el.html(opts.tpl, Todo.findAll());
		},
		/**
		 * 前面是选择器(从绑定的HTML元素开始搜索)，后面是订阅的事件名称
		 * @param {jQuery} $el 事件响应jQuery对象
		 * @param {EventObject} evt 事件对象
		 */
		'li click': function($el, evt){
		},
		// 表示点击绑定的HTML元素时触发
		'click': function($el, evt){
		},
		'li .destroy click': function($el, evt){
			evt.stopPropagation();

			// 通过$.View.hookup将Model绑定到View后就可以如下操作
			var $li = $el.closest('li');
			if (confirm('确定要删除？')){
				$li.model().destroy(function(){
					$li.remove();
				}, function(){
					alert('删除失败！');
				});
			}
		}
	});
	// 实例化控制器，实现View和Model的关联
	new Todos('#todos', {tpl: 'tpl.ejs'});
```
### 4.事件绑定模板 ###
**优点：当元素被移除时，Controller中的DOM和BOM对象的事件绑定都会自动解除，避免内存泄露。**
1. 动态事件名称模板
1.1. 占位符`{占位符名称}`，支持路径查找（如`{Name.Evt}`）
1.2. 变量搜索顺序：1. Controller类实例化时的opts对象中寻找，2. window对象中寻找
```javascript
	// 创建Todos控制器类
	$.Controller('Todos', {
		init: function($el, opts){
			$el.html(opts.tpl, Todo.findAll());
		},
		'li {EvtName}': function($el, evt){
		}
	});
	// 实例化控制器，实现View和Model的关联
	new Todos('#todos', {tpl: 'tpl.ejs', EvtName: 'click'});
```
2. 动态选择器模板
2.1. 占位符`{占位符名称}`，支持路径查找（如`{Name.Evt}`）
2.2. 变量搜索顺序：1. Controller类实例化时的opts对象中寻找，2. window对象中寻找
```javascript
	// 创建Todos控制器类
	$.Controller('Todos', {
		init: function($el, opts){
			$el.html(opts.tpl, Todo.findAll());
		},
		// 订阅window的事件
		'{window} {EvtName}': function($el, evt){
		}
	});
	// 实例化控制器，实现View和Model的关联
	new Todos('#todos', {tpl: 'tpl.ejs', EvtName: 'click'});
```
3. 订阅Controller外的其他对象（如Model）事件
```javascript
	// 创建Todos控制器类
	$.Controller('Todos', {
		init: function($el, opts){
			$el.html(opts.tpl, Todo.findAll());
		},
		/**
		 * @param {Model类} Todo Model类
		 * @param {EventObject} evt
		 * @param {Model} todo Model实例
		 */
		'{Todo} destroyed': function(Todo, evt, todo){
			todo.elements(this.element).remove();
		},
		'{Todo} updated': function(Todo, evt, todo){
			todo.elements(this.element).replaceWith('tpl.ejs', [todo]);
		}
	});
	// 实例化控制器，实现View和Model的关联
	new Todos('#todos', {tpl: 'tpl.ejs');
```

## 路由控制器 ##
支持ie6+的路由控制
### 1. 引入模块 ###
```javascript
	steal('jquery/controller', 'jquery/controller/route', function($){
		// .......
	});
```
### 2.定义并初始化 ###
```javascript
	$.Controller('Routing', {
		'route': function(){
			// 匹配空hash，#或#! 
		},
		'todo/:id route': function(data){
			// 匹配#!todo/1或#todo/1
			console.log(data.id);
		},
		'other/:typeId route': function(data){
			// 匹配#!other/1或#other/1
			console.log(data.typeId);
		}
	});
	new Routing(document.body);
```
### 3. 通过$.route.attr来实现跳转 ###
```javascript
	$.route.attr('id', 1);
```

## Ajax服务指南 ##
1. 当请求多个内嵌对象时
```
	// url
	dummy?include[]=fromuser
	// 返回值
	{
		company: 'xxx',
		fromuser: {
			name: 'xxx',
			age: 12
		}
	}
```

## steal ##
作用：文件加载系统(和seajs不同，seajs是模块加载器)，并配套一系列命令行工具用于项目构建
### 1. 命令行工具 ###
1.1. 压缩：`js steal/buildjs 应用入口文件路径`
1.2. 自动生成jmvc风格项目结构：`js steal/generate/app 应用名称`
1.3. 包管理（和npm作用一样）:`js steal/getjs http://github.com/jupiterjs/mxui/`
1.4. 代码格式化并使用jsLint检验：`js steal/cleanjs 文件路径`
1.5. 生成可供google蜘蛛爬取的Ajax页面：`js steal/htmljs 页面路径`
### 2. 浏览器中的工具 ###
2.1. 开发环境中日志记录:`steal.dev.log(日志内容)`