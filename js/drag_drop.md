# HTML5 Drag and Drop (DnD) API
## 开启拖拽效果
html
````
<div id="drag" draggable="true" style="width:100px;height:50px;background-color:red;">
test
</div>
````
js
````
var drag = document.getElementById('drag');
drag.onselectstart = function(){return false;};
// FF下拖拽时，默认不会生成一个被拖拽元素的阴影并跟随鼠标移动
// 需通过e.dataTransfer.setData来启动该效果
drag.ondragstart = function(e){
	e.dataTransfer.setData('text', e.target.innerHTML);
};
````
**关键点：**<br/>
1. `draggable="true"`,是用于启动HTML5 drag & drop 功能;<br/>
2. FF下拖拽时，默认不会生成一个被拖拽元素的阴影并跟随鼠标移动，需通过e.dataTransfer.setData来启动该效果。（IE10+和Chrome默认均有阴影效果）<br/>

## 生命周期
**被拖拽元素的生命周期**<br/>
1. `dragstart`:当被拖拽元素被开始拖拽时触发;<br/>
2. `drag`: 当被拖拽元素被拖拽时触发；<br/>
3. `dragend`:当被拖拽元素被释放且拖拽操作完成后触发(在`drop`后触发）<br/>
**目标元素的生命周期**<br/>
1. `dragenter`:当被拖拽元素进入目标元素时触发<br/>
2. `dragover`:当被拖拽元素在目标元素上移动时触发<br/>
>1. 默认情况下，无法将数据/元素放置到其他元素中的（即不触发drop事件）。如果需要设置允许放置，则须要再dragover事件中通过evt.preventDefault()来阻止默认行为。（经测试，IE、Chrome和FF的默认行为均与此相符）<br/>

3. `drop`:当被拖拽元素在目标元素上而且释放鼠标时触发<br/>
>1. drop事件的默认行为是以链接形式打开，因此需要调用evt.preventDefault()阻止默认行为。（经测试，FF31.0下默认行为是以链接形式打开，而IE和Chrome的默认行为是没有任何操作）<br/>

示例代码<br/>
````
<div id="drag" style="width:50px;height:50px;background-color:red;">Test</div>
<div id="drop" style="width:100px;height:100px;border:solid 1px red;"></div>
<script type="text/javascript">
  var drag = document.getElementById('drag'), drop = document.getElementById('drop');
  drag.ondragstart = function(evt){
  	evt.dataTransfer.setData('Text', 'www.baidu.com');
  };
  drop.ondragover = function(evt){
  	evt.preventDefault(); // 这样才能触发drop的drop事件
  };
</script>
````

**整体生命周期**<br/>
`dragstart` -> `drag` -> `dragenter` -> `dragover` -> `drop` -> `dragend`<br/>

<font style="color:red">注意</font><br/>
1. `dragover`中必须调用`evt.preventDefault()`来阻止事件的默认行为，否则无法触发drop事件。<br/>
2. 对于图片或其他文件而言，`drop`的默认行为是显示图片或打开文件，所以`drop`下也要调用`evt.preventDefault()`<br/>
3. 触发`dragstart`后，其他元素的`mousemove`，`mouseover`，`mouseenter`、`mouseleave`、`mouseout`均不会触发了。<br/>

**mouse事件的顺序**<br/>
Chrome36：`mouseenter`->`mouseover`->`mousemove`->`mouseleave`->`mouseout`<br/>
IE：`mousemove`->`mouseover`->`mouseenter`->`mouseout`->`mouseleave`<br/>
FF31：`mouseover`->`mouseenter`->`mousemove`->`mouseout`->`mouseleave`<br/>

## \[object DragEvent\]对象
继承

## \[object DataTransfer\]对象
  拖拽生命周期的各个事件的事件对象中均有DataTransfer对象，用于在拖拽过程中传递信息。而它可以存储两种数据类型：字符串、文件。DataTransfer对象在dragend事件触发后将被销毁。<br/>
**数据的访问模式**<br/>
1. Read/Write mode：在`dragstart`事件中，可读写数据<br/>
2. Read-only mode：在`drop`事件中，仅能读取数据<br/>
3. Protected mode：在其他DnD事件中，仅能枚举数据<br/>

**属性**<br/>
`effectAllowed`和`dropEffect`用于设置鼠标在目标元素上的样式，在不同的浏览器和操作系统上得到的样式是不同的。<br/>
`effectAllowed`设置允许在目标元素上的样式，而`dropEffect`则表示为鼠标在目标元素上的实际样式。<br/>
当`dropEffect`<br/>

`effectAllowed`：类型为DOMString，用于设置或返回被拖拽元素允许发生的拖动行为。取值范围如下：<br/>
>copy：只允许值为copy的dropEffect<br/>
>link：只允许值位link的dropEffect<br/>
>move：只允许值为move的dropEffect<br/>
>copylink：只允许值为copy或link的dropEffect<br/>
>copyMove：只允许值为copy或move的dropEffect<br/>
>linkmove：只允许值为move或link的dropEffect<br/>
>all：允许任意值的dropEffect<br/>
>none：被拖拽元素不能有任何行为<br/>
>uninitialized：没有为被拖拽元素设置任何放置的行为<br/>

<font style="color:red;">注意：仅能在`dragstart`中设置`effectAllowed`的值</font><br/>

`dropEffect`：类型为DOMString，设置或返回拖放目录上允许发生的拖放行为。如果与effectAllowed不一致则drop动作将会失败<br/>
>copy：应该把被拖拽元素复制到目标元素内<br/>
>link：在目标元素内释放时，会打开被拖拽的元素（被拖拽的元素必须是个超链接或含url地址）<br/>
>move：应该把被拖拽元素放置在目标元素内<br/>
>none：不能把被拖拽元素放在这里。除文本框外其他元素默认为none<br/>

<font style="color:red;">注意：`dropEffect`的值不能由js代码来设置的</font><br/>
IE10+下
`effectAllowed`
默认值是uninitialized, `dropEffect`为copy
copyLink，默认使用link, `dropEffect`为copy
copyMove，默认使用copy, `dropEffect`为copy
linkMove，默认使用link, `dropEffect`为copy
all，默认使用link, `dropEffect`为copy
none，就不会触发drop事件
move,`dropEffect`为move
link,`dropEffect`为link
copy,`dropEffect`为copy
无法通过`shift`键切换copyLink、copyMove和linkMove的样式


Chrome29下
默认值是all
all，默认使用move, `dropEffect`为copy
无法通过`shift`键切换copyLink、copyMove和linkMove的样式


FF33 for linux下






`items`：类型为DataTransferItems，代表DataTransfer对象存储的所有数据项<br/>
`files`：类型为FileList，<br/>
`types`：类型为DOMStringList，代表DataTransfer对象存储的所有数据类型<br/>

**方法**<br/>
`void addElement({Element} element)`：添加一起跟随拖拽的元素。<br/>
`void setDragImage({Element} image, {long} x, {long} y)`：设置拖动时跟随鼠标移动的图片，用来替代默认的元素，若image不是图片元素则会元素临时转换为图片；x用于设置图标与鼠标在水平方向上的距离，y设置图标与鼠标在垂直方向上的距离。<br/>
`void setData({DOMString} format, {DOMString} data)`：将指定格式的数据赋值给dataTransfer或clipboardData，format值范围为URL、Text（或text）和各种MIME类型，其实Text会被自动映射为text/plain，URL会被自动映射为text/uri-list类型<br/>
>1. FF5-是不会将text映射为text/plain，而仅仅支持Text映射为text/plain，因此使用Text或直接使用text/plain<br/>
>2. IE仅支持Text和URL两种类型，不支持text/plain、text/uri-list等类型<br/>
>3. text/plain类型则不会对数据进行额外处理，而text/uri-list类型则会将数据视为url来使用<br/>
>4. Chrome和FF支持非空字符串作为format<br/>

`DOMString getData({DOMString} format)`：从dataTransfer或clipboardData中获取指定格式的数据<br/>
`void clearData([{DOMString} format])`：从dataTransfer或clipboardData中删除指定格式的数据<br/>

## [object ClipboardData]
Chrome29提供undefined的evt.clipboardData属性，而IE和FF并提供。

## 参考
http://www.w3school.com.cn/html5/html_5_draganddrop.asp
http://www.cnblogs.com/wpfpizicai/archive/2012/04/07/2436454.html
http://www.kankanews.com/ICkengine/archives/82862.shtml
http://jingyan.baidu.com/article/6dad5075cf6e62a123e36e11.html
http://www.zhangxinxu.com/wordpress/2011/02/html5-drag-drop-%E6%8B%96%E6%8B%BD%E4%B8%8E%E6%8B%96%E6%94%BE%E7%AE%80%E4%BB%8B/

http://my.oschina.net/caixw/blog/102845
http://blog.csdn.net/shyleoking/article/details/7344514
http://www.cnblogs.com/birdshome/archive/2006/07/22/Drag_Drop.html