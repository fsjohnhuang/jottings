## 插件开发
**`$.extend(target[,obj]*)方法`**<br/>
 特性：合并多个对象到第一个入参上，并返回第一个入参。若仅有一个入参则将该入参合并到extend方法的宿主对象上。<br/>
 注意：
 1. 合并多个对象到第一个入参时，第一个入参的属性将被改变。<Br/>
 2. 默认情况下为浅层复制，当第一个入参为true时，则为深度复制。<br/>
 
**开发方式**<br/>
1. **jQuery静态插件**<br/>
````
// 方式1
jQuery.myplugin = function(){};

// 方式2
jQuery.extend({myplugin:function(){}});
````
2. **jQuery实例插件**<br/>
````
;(function($){
  var exports = function(options){
	var $self = this;
        var opts = $.extends({}, exports.defults, options);
	// 处理逻辑

        return $self;
    };
  exports.defaults = {
    color: '#fff'
  };
  $.fn.extend({myplugin: exports});
}(jQuery));
````

## jquery-metadata.js
https://github.com/jquery-orphans/jquery-metadata<Br/>
作用：用于从标签中获取元数据(不单单从attribute中获取)<br/>
注意：元数据只能被获取，而不能在运行过程中被修改、删除<br/>
元数据的定义方式：<Br/>
````
// 方式1：
<li class="someclass {some:'data'} anotherclass"></li>
// 方式2：
<li data="{some:'data',data:true}"></li>
// 方式3：
<li><script type="data">{some:"data",data:true}</script></li>
````
