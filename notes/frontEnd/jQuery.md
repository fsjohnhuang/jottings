### 1.插件开发 ###
    $.fn.插件名 = function(){
		var dom = this; // this指向jQuery选择符匹配到的dom对象
		// other snippets...
	};

	/*调用方式*/
	$('选择符').插件名();
    
### 2.防止enter提交表单 ###
    $('#form').keypress(function(evt){
		if (evt.which === 13) return false;
	});

### 3.启用/禁用表单控件 ###
    $('#form-control').attr('disabled', true);
	$('#form-control').removeAttr('disabled');

### 4.防止表单重复提交 ###
    $('form').submit(function(){
		if (typeof $.data(this, 'disabledOnSubmit') === 'undefined'){
			$('input[type=submit], input[type=button]', this)
				.each(function(){
					$(this).attr('disabled', true);
				});
			return true;
		}
		else{
			return false;
		}
	});

### 5.attribute 和 prop的区别 ###

### 6.使用data来保存非js原生类型数据到dom元素 ###
在ie8及以下版本的IE上，以prop形式设置非js原生类型数据到dom元素，当在移除dom元素时不手工设置prop为空时，会出现内存泄露的问题。通过data来设置就不会出现该问题。

### $.map() ###
```javascript
	var orig = [1,2,3];
	var newOne = $.map(orig, function(item, i){
		if (item < 3){
			return item;
		}
		return null; // 返回null表示该值不添加到新的数组中
	});
	console.log(orig); // 输出[1,2,3]
	console.log(newOne); // 输出[1,2]
```

### 解决$冲突 ###
当其他库使用了$时,jQuery的$将出错。可通过jQuery.noConflict()来解决冲突
```javascript
	// 方式一：
	jQuery.noConflict();
	// 使用jQuery代替$
	jQuery('div');

	// 方式二：
	var j = jQuery.noConflict();
	// 使用j代替$
	j('div');

	// 方式三（推荐）：
	jQuery.onConflict();
	(function($){
		$('div');
	})(jQuery);
```

### 插件 ###
1. 文件名规范: jQuery.插件名.js
2. 函数
```javascript
	// jQuery.fn指向jQuery.prototype
	jQuery.fn.插件名 = function(){
		/* 注意点
		 * this指向jQuery('选择器')获取的jQuery对象
		 * 必须return jQuery对象
		 */
	};
```

### 获取选择器和选择上下文 ###
```html
	<body>
		<div><a>Hello world!</a></div>
	</body>
```
```javascript
	$('div', 'body').find('a').on('click', function(evt){
		var $self = $(this);
		console.log($self.selector); // 输出字符串div a
		console.log($self.context); // 输出DOM对象object HTMLBody
	});
```

### 合并数组 ###
```javascript
	var arr1 = [1,2];
	var arr2 = [3,4];
	var newArr = $.merge(arr1, arr2); // [1,2,3,4]
```

### 将伪数组转换为数组 ###
```javascript
	var doStuff = function(){
		var array = $.makeArray(arguments);
		// TODO
	};
```

### 去除重复元素（DOM对象，js对象） ###
```javascript
	var arr1 = [1,2,2];
	var newArr = $.unique(arr1); // [1,2]
```

### 附加信息到DOM中 ###
```html
	<div></div>
```
```javascript
	// 使用$().data()可以避免直接将js对象附加在dom对象上，造成某些浏览器的内存泄露问题
	// 注意：在查看html时，不会看到附加的信息，因为并不是直接附加到dom上
	$('div').data('myObj', {
		name: 'john'
	});
	var myName = $('div').data('myObj').name;

	// $().data()会触发setData和getData事件，从而实现视图数据绑定
	$('div').on('setData', function(evt, key, val){
		if (key === 'myObj'){
			$(this).html(val.name);
		}
	});
```

### 回到上一个jQuery元素 ###
```javascript
	var div = $('div')
				.find('a')
					.addClass('visited')
				.end();
```

### $().html() 和原生innerHTML的区别 ###
1. `$().html()`和`$().empty()`是除了清除HTML外，还会清理event处理器和附加到dom上的js对象信息，防止内存泄露；
2. `$().html()`内部调用`$.clean()`来对不规范的html标签进行调整（例如：将`<div/>`转换为`<div></div>`）；
3. 结论：
3.1. 若要清除的HTML上有event处理器和附加信息就要使用`$().html()`和`$().empty()`
3.2. 若仅清理或替换HTML代码，使用原生innerHTML效率更高

### 序列化表单内容 ###
```javascript
	var dataStr = $('form').serialize();
```

## 维度 ##
### window、document和body的高宽关系 ###
1. `$(window).width()`和`$(window).height()`获取浏览器可视区宽度和高度；`$(document).width()`和`$(document).height()`获取html元素的宽度和高度；`$(body).width()`和`$(body).height()`获取body元素的宽度和高度；
2. 关系：
2.1. `$(window).width() <= $(document).width()；$(window).height() <= $(document).height()`
2.2. `$(body).width() <= $(document).width()；$(body).height() <= $(document).height()`
2.3. `$(body).width()`与`$(window).width()`没固定的关系

### 元素高宽 ###
```javascript
	var $div = $('div');
	$div.width(); // 不包含margin,border,padding的宽度
	$div.height(); // 不包含margin,border,padding的高度
	$div.innerWidth(); // 不包含margin,border, 包含padding的宽度
	$div.innerHeight(); // 不包含margin,border, 包含padding的高度
	$div.outerWidth(); // 不包含margin,包含border, padding的宽度
	$div.outerHeight(); // 不包含margin,包含border, padding的高度
```
注意：`innerWidth、innerHeight、outerWidth和outerHeight`在`document`和`window`调用会返回`NaN`

### 元素位移 ###
1. 获取位移
```html
	<div id="parent" style="position: absolute; left: 100px; top: 100px;">
		<div id="child" style="position: relative; left: 10px;"></div>
	</div>
```
```javascript
	var $div = $('#child');
	$div.offset(); // 返回{left: 111, top: 101}
	$div.position(); // 返回{left: 10, top: 0}
	$div.offsetParent(); // 返回[<div id="parent" style="position: absolute; left: 100px; top: 100px;"></div>]jQuery对象
```
2. 滚动到指定位移
```javascript
	$(document).scrollTop($div.offset().top);
	或则
	$(window).scrollTop($div.offset().top);
```

#### 动画效果 ####
1. `$().animate()`用于自定义动画效果
1.1. 可控制数字类型的CSS属性和`scrollTop`和`scrollLeft`的变化；
1.2. 对于仅含两种状态的属性值（如hide和show），可以使用预设值'toggle'(使用'toggle'会先获取当前元素的属性值作为初始值（即使display:none），然后取0作为终值)；
1.3. 预设时间`slow`(600ms)和`fast`(200ms),默认时间是400ms。
2. `$().show()`、`$().hide()`和`$().toggle()`
&emsp;同时缩小宽高，最后元素隐藏。
3. `$().fadeTo(during, alpha)`
&emsp;元素透明度动态变化。
4. `$().fadeIn()`和`$().fadeOut()`
&emsp;元素透明度动态变化，最后元素隐藏
5. `$().slideUp()`、`$().slideDown()`和`$().slideToggle()`
&emsp;`$().slideUp()`缩小高，最后元素隐藏。`$().slideDown()`增加高，最后显示完整的元素
6. `$().stop()`终止匹配元素当前的动画效果,但不清空动画队列。`$().stop(true)`终止匹配元素当前的动画效果,并清空动画队列
7. 动画效果增强库：jquery.easing.1.3.js
8. 禁用动画效果：`$.fx.off = true;`
```javascript
	$.fx.off = true;
	$().animate({width: '+=200'}, 1000); // 效果跟$().css({width: '+=200'});
```

### 选择器使用 ###
1. `$().next()`获取每个匹配元素后紧跟的兄弟元素
```html
	<div id="1"></div>
	<div id="2"></div>
	<div id="3"></div>
	<div id="4"></div>
```
```javascript
	$("#1,#3").next(); // 返回id为2和4的div
```
2. 选择非第一位的匹配元素`filter(:not(:first))`


## 事件 ##
1. 预附加数据`$().on(eventName, presetData, function(evt){})`
```javascript
	$('div').on('click', { msg: 'helloworld' }, function(evt){
		console.log(evt.data.msg);
	});
```
2. 动态附加数据
2.1. 方法1：调用`trigger`时附加数据
```javascript
	$('div').on('click', function(evt, name, age, state){});
	$('div').trigger('click', ['john', 12, { msg: 'hello world' }]);
```
2.2. 方法2：自定义event object
```javascript
	$('div').on('click', function(evt){
		console.log(evt.name);
		console.log(evt.age);
		console.log(evt.state.msg);
	});
	$('div').trigger({
		type: 'click',
		name: 'john',
		age: 12,
		state: {
			msg: 'hello world'
		}
	});
	或者
	var e = $.event('click');
	e.name = 'john';
	e.age = 12;
	e.state = {
		msg: 'hello world'
	};
	$('div').trigger(e);
```
3. 事件命名空间:用于对事件进行分类，并批量操作某命名空间下的事件
```javascript
	$('div1').on('click.ns', function(){});
	$('div1').on('mousedown.ns', function(){});
	$('div1').off('.ns');
```
4. 触发特定命名空间事件，触发非命名空间事件
```javascript
	$('div1').on('click', function(){console.log(1);});
	$('div1').on('click', function(){console.log(2);});
	$('div1').on('click.ns', function(){console.log(3);});
	$('div1').trigger('click'); // 输出1、2、3
	$('div1').trigger('click.ns'); // 输出3
	$('div1').trigger('click!'); // 输出1、2
	$('div1').trigger('.ns'); // 无效果
```
5. 获取触发事件的元素`evt.target`和处理事件的元素`evt.currentTarget`
```javascript
	$('div').click(function(evt){
		console.log(evt.target);
		console.log(evt.currentTarget);
	});
```
6. 事件委托(event delegation)
```html
	<div id="container">
		<div id="child">child</div>
	</div>
```
```javascript
	$('#container').click(function(evt){
		console.log($(evt.target).html());
	});
	('#container').append('<div>new</div>');
```
当点击`<div>new</div>`时就可以触发事件了。
这是利用事件冒泡的原理，所以像`focus`和`blur`这样不会冒泡的原生事件是无法使用事件委托的，可以使用jQuery提供的`focusIn`和`focusOut`来代替。
7. `trigger`和`triggerHandler`的区别
7.1. `trigger`会调用jQuery所有匹配元素的事件处理函数，而`triggerHandler`则只调用jQuery匹配的第一个元素的事件处理函数；
7.2. `trigger`会调用事件对象的默认动画和事件冒泡，而`triggerHandler`不会
7.3. `trigger`会返回jQuery对象本身，而`triggerHandler`返回最后一个事件处理函数的结果，若没有事件处理函数则返回`undefined`
8. 自定义对象上绑定事件响应函数
```javascript
	var $obj = $({});
	$obj.on('click', function(evt){});
	$obj.trigger('fire');
```
9. 通过$.event.special API 自定义或为已有的事件对象（EventObject）附加功能
9.1. 作用：
>1. 如IE8没有hashchange和input事件，就通过自定义事件对象来标准化事件
>2. 对已有事件附加操作，如dblclick的定义为4秒内双击即可

9.2. `.on`函数的底层实现是调用`$.event.add(DOM对象, 事件名称, 事件处理函数, 预设参数,选择器字符串)`;`.off`函数的底层实现是调用`$.event.remove(DOM对象,事件名称,事件处理函数,选择器字符串, mappedTypes)`
9.3. 自定义结构
```javascript
	$.event.special['user-definedEvent'] = {
		setup: function(data, namespaces, eventHandle){
			// 在第一次事件绑定时调用
			// return false 或 不实现本方法，jQuery将调用原生的事件绑定函数来处理
			// this指向事件绑定的DOM元素
		},
		teardown: function(namespaces){
			// 最后一次事件解除时调用
			// return false 或 不实现本方法，jQuery将调用原生的事件绑定函数来处理
			// this指向事件绑定的DOM元素
		},
		add: function(handleObj){
			// 每次事件绑定时都调用，一般用于加工事件回调函数和evt的属性
			// this指向事件绑定的DOM元素
		},
		remove: function(handleObj){
			//	每次事件解除时都调用，一般用于回收资源
			// this指向事件绑定的DOM元素
		},
		_default: function(evt){
			// 定义默认动作，和原生的默认动作相同，都是待完成DOM树冒泡和所有回调函数执行完毕后执行，也可以通过evt.preventDefault()来阻止默认动作。
		}
	};
````
第一事件绑定时，先调用`setup`，然后是`add`；
最后一次事件解除时，先调用`remove`，然后是`teardown`；
`setup`的参数
- `data`：{除Function外所有数据类型}，事件绑定时预设的参数
- `namespaces`：{Array}，事件绑定时事件的命名空间
- `eventHandle`:{Function}，jQuery的内部方法，不用理会

`teardown`的参数
- `namespacecs`：{Array}，事件绑定时事件的命名空间

`add`和`remove`的参数
- `handleObj`
&emsp;`type`：{String}，事件名称
&emsp;`data`：{除Function外所有数据类型}，事件绑定时预设的参数
&emsp;`namespaces`：{Array}，事件绑定时事件的命名空间
&emsp;`handler`：{Function}，事件处理函数
&emsp;`guid`：{Number}，jQuery内部生产的唯一编号
&emsp;`selector`：{String}，事件绑定时的选择器字符串

`_default`的参数
- `event`：jQuery的evt
9.4. 重新定义双击事件
```javascript
(function($){
    var _threshold = 2000; // 两次连续点击的间隔时间
	// 判断两次的连续点击是否有效
    var _handler = function(evt){
	// 防止冒泡和执行默认动作
    evt.stopPropagation();
    evt.preventDefault();

    var $elem = $(this);
    var conf = $elem.data('dblclick');
    if (conf.timeStamp === 0){
      conf.timeStamp = evt.timeStamp;
      $elem.data('dblclick', conf);
    }
    else {
      if (evt.timeStamp - conf.timeStamp <= _threshold){
        $elem.trigger('dblclick'); // 触发dblclick事件
      }
      
      $elem.data('dblclick', {
        timeStamp: 0
      });
    }
  };

  $.event.special.dblclick = {
    setup: function(data, namespaces){
             $(this)
              .attr('title', '双击')
              .css('cursor', 'pointer');
           },
    teardown: function(namespaces){
               $(this)
                .attr('title', '')
                .css('cursor', 'default');
              },
    add: function(handleObj){
          var $elem = $(this);
          $elem.data('dblclick', {
            timeStamp: 0
          });
          
          $elem.on('click.dblclick.redefine', _handler);
         },
    remove: function(handleObj){
              var $elem = $(this);
              $elem.removeData('dblclick');
              $elem.off('.dblclick.redefine');
            }
  };
})(jQuery);
```

10.jQuery自动解除绑定的注意事项
jQuery仅在移除HTML元素时会自动解除元素上的事件，但对于document等BOM是不会自动解除对象上的事件的，所以会造成内存泄露。

    