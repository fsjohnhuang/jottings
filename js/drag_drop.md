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

## Safari4下启动拖拽效果
需要添加CSS样式触发行为
````
[draggable=true]{ -webkit-user-drag: element; }
````

## draggable属性
用于指定标签是否可以被拖拽。<br/>
属性值如下：<br/>
1. `true`：表示可拖拽<br/>
2. `false`：表示不可拖拽<br/>
3. `auto`：img和带href的a标签下表示可拖拽，其他标签表示不可拖拽<br/>
4. 其他值：表示不可拖拽<br/>

## 生命周期
**被拖拽元素的生命周期**<br/>
1. `dragstart`:当被拖拽元素被开始拖拽时触发;<br/>
>1. 在该事件中初始化拖拽信息，如effectAllowed和setData<br/>
>2. 若调用evt.preventDefault()则会阻止拖拽操作<br/>

2. `drag`: 当被拖拽元素被拖拽时触发；<br/>
3. `dragend`:当被拖拽元素被释放且拖拽操作完成后触发(在`drop`后触发）<br/>
**目标元素的生命周期**<br/>
1. `dragenter`:当被拖拽元素进入目标元素时触发<br/>
>1. 可以在这设置dropEffect的值<br/>

2. `dragover`:当被拖拽元素在目标元素上移动时触发<br/>
>1. 默认情况下，无法将数据/元素放置到其他元素中的（即不触发drop事件）。如果需要设置允许放置，则须要再dragover事件中通过evt.preventDefault()来阻止默认行为。（经测试，IE、Chrome和FF的默认行为均与此相符）<br/>
>2. 可以在这设置dropEffect的值，默认行为是将dropEffect设置为none<br/>

3. `drop`:当被拖拽元素在目标元素上而且释放鼠标时触发<br/>
>1. drop事件的默认行为是以链接形式打开，因此需要调用evt.preventDefault()阻止默认行为。（当从外部拖拽图片源、文件源或链接源时，drop的默认行为是令浏览器重定向，所以即使目标对象为iframe或frameset效果当前文档也会被重定向都被拖拽的资源上。）<br/>

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

**IE5~9整体生命周期**<br/>
`dragstart` -> `drag` -> `dragenter` -> `drop` -> `dragend`<br/>

<font style="color:red">注意</font><br/>
1. `dragover`中必须调用`evt.preventDefault()`来阻止事件的默认行为，否则无法触发drop事件。<br/>
2. 对于图片或其他文件而言，`drop`的默认行为是显示图片或打开文件，所以`drop`下也要调用`evt.preventDefault()`<br/>
3. 触发`dragstart`后，其他元素的`mousemove`，`mouseover`，`mouseenter`、`mouseleave`、`mouseout`均不会触发了。<br/>

**mouse事件的顺序**<br/>
Chrome36：`mouseenter`->`mouseover`->`mousemove`->`mouseleave`->`mouseout`<br/>
IE：`mousemove`->`mouseover`->`mouseenter`->`mouseout`->`mouseleave`<br/>
FF31：`mouseover`->`mouseenter`->`mousemove`->`mouseout`->`mouseleave`<br/>

## \[object DragEvent\]对象
  继承自`MouseEvent`,拖拽过程中的所有事件对象均为该类型。
**从UIEvent继承的属性**<br/>
`Window view = null`<br/>
`long detail = 0`<br/>
**从MouseEvent继承的属性**<br/>
`long screenX = 0`<br/>
`long screenY = 0`<br/>
`long clientX = 0`<br/>
`long clientY = 0`<br/>
`boolean ctrlKey = false`<br/>
`boolean shiftKey = false`<br/>
`boolean metaKey = false`<br/>
`unsigined short button = 0`<br/>
`unsigined short buttons = 0`<br/>
`EventTarget relatedTarget = null`<br/>
**从DragEvent独有的属性**<br/>
`DataTransfer dataTransfer`<br/>

## \[object DataTransfer\]对象
  拖拽生命周期的各个事件的事件对象中均有DataTransfer对象，用于在拖拽过程中传递信息。而它可以存储两种数据类型：字符串、文件。DataTransfer对象在dragend事件触发后将被销毁。<br/>
**数据的存储模式**<br/>
1. Read/Write mode：在`dragstart`事件中，可读写数据<br/>
2. Read-only mode：在`drop`事件中，仅能读取数据<br/>
3. Protected mode：在其他DnD事件中，仅能枚举数据<br/>

**属性**<br/>
`effectAllowed`和`dropEffect`用于设置鼠标在目标元素上的样式以便用户通过鼠标样式了解可执行的操作，在不同的浏览器和操作系统上得到的样式是不同的。<br/>
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

<font style="color:red;">注意:仅能在`dragover`中设置`dropEffect`的值</font><br/>

**浏览器下的实测**<br/>
**IE5~9下**<br/>
仅支持图片、选中的文字(页面文字和input/textarea元素中的文字)和超链接(普通的和锚点)。<br/>
1. img<br/>
默认值是uninitialized, `drop`事件中的`dropEffect`为copy<br/>
2. a和input<br/>
默认值是uninitialized, `drop`事件中的`dropEffect`为none<br/>

共性：<br/>
copyLink，默认使用link, `drop`事件中的`dropEffect`为none<br/>
copyMove，默认使用copy, `drop`事件中的`dropEffect`为none<br/>
linkMove，默认使用link, `drop`事件中的`dropEffect`为none<br/>
all，默认使用link, `drop`事件中的`dropEffect`为copy<br/>
none，就不会触发drop事件<br/>
move,`dropEffect`为move<br/>
link,`dropEffect`为link<br/>
copy,`dropEffect`为copy<br/>
无法通过`shift`键切换copyLink、copyMove和linkMove的样式<br/>
在`dragover`下可设置`dropEffect`值, 若设置的值与effectAllowed不对应，则`drop`事件仍然会被触发;<br/>
若effectAllowed设置为copyLink、copyMove或linkMove，且dropEffect与之对应，则鼠标样式将为dropEffect所设置的样式<br/>

**IE10+下**<br/>
`effectAllowed`<br/>
默认值是uninitialized, `drop`事件中的`dropEffect`为copy<br/>
copyLink，默认使用link, `drop`事件中的`dropEffect`为none<br/>
copyMove，默认使用copy, `drop`事件中的`dropEffect`为none<br/>
linkMove，默认使用link, `drop`事件中的`dropEffect`为none<br/>
all，默认使用link, `drop`事件中的`dropEffect`为copy<br/>
none，就不会触发drop事件<br/>
move,`dropEffect`为move<br/>
link,`dropEffect`为link<br/>
copy,`dropEffect`为copy<br/>
无法通过`shift`键切换copyLink、copyMove和linkMove的样式<br/>
在`dragover`下可设置`dropEffect`值, 若设置的值与effectAllowed不对应，则`drop`事件仍然会被触发;<br/>
若effectAllowed设置为copyLink、copyMove或linkMove，且dropEffect与之对应，则鼠标样式将为dropEffect所设置的样式<br/>

**Chrome29下**<br/>
默认值是all<br/>
all，默认使用move, `drop`事件中的`dropEffect`为copy<br/>
copyLink，默认使用copy, `drop`事件中的`dropEffect`为none<br/>
copyMove，默认使用move, `drop`事件中的`dropEffect`为none<br/>
linkMove，默认使用move, `drop`事件中的`dropEffect`为none<br/>
move,`drop`事件中的`dropEffect`为move<br/>
link,`drop`事件中的`dropEffect`为link<br/>
copy,`drop`事件中的`dropEffect`为copy<br/>
无法通过`shift`键切换copyLink、copyMove和linkMove的样式<br/>
在`dragover`下可设置`dropEffect`值, 若设置的值与effectAllowed不对应，则`drop`事件将不会被触发<br/>
若effectAllowed设置为copyLink、copyMove或linkMove，且dropEffect与之对应，则鼠标样式将为dropEffect所设置的样式<br/>

**FF31 for window下**<br/>
默认值是uninitialized, `drop`事件中的`dropEffect`为move<br/>
copyLink，默认使用copy, `drop`事件中的`dropEffect`为copy<br/>
copyMove，默认使用move, `drop`事件中的`dropEffect`为move<br/>
linkMove，默认使用move, `drop`事件中的`dropEffect`为move<br/>
move,`drop`事件中的`dropEffect`为move<br/>
link,`drop`事件中的`dropEffect`为link<br/>
copy,`drop`事件中的`dropEffect`为copy<br/>
`effectAllowed`为copyLink、copyMove和linkMove时,可通过`shift`键切换样式<br/>
在`dragover`下可设置`dropEffect`值, 若设置的值与effectAllowed不对应，则`drop`事件将不会被触发<br/>
若effectAllowed设置为copyLink、copyMove或linkMove，虽然dropEffect与之对应，但依然显示默认样式，需要通过shift键来切换样式<br/>

**FF33 for linux下**<br/>
仅可触发dragstart事件,其他事件一律无效<br/>


`items`：类型为DataTransferItemList，代表DataTransfer对象存储的所有数据项<br/>
>1. FF33 for linux是没有该属性的<br/>
>1. IE是没有该属性的<br/>

`files`：类型为FileList（ie5~9没有该属性）<br/>
`types`：类型为DOMStringList，代表DataTransfer对象存储的所有数据类型（ie5~9没有该属性）<br/>
>1. 仅能在`dragenter`,`dragover`和`drop`中获取types属性<br/>

**方法**<br/>
`void addElement({Element} element)`：添加一起跟随拖拽的元素。<br/>
`void setDragImage({Element} image, {long} x, {long} y)`：设置拖动时跟随鼠标移动的图片，用来替代默认的元素，若image不是图片元素则会元素临时转换为图片；x用于设置图标与鼠标在水平方向上的距离，y设置图标与鼠标在垂直方向上的距离。<br/>
>1. 仅在`dragstart`下调用<br/>

`boolean setData({DOMString} format, {DOMString} data)`：将指定格式的数据赋值给dataTransfer或clipboardData，format值范围为URL、Text（或text）和各种MIME类型，其实Text会被自动映射为text/plain，URL会被自动映射为text/uri-list类型<br/>
>1. FF5-是不会将text映射为text/plain，而仅仅支持Text映射为text/plain，因此使用Text或直接使用text/plain<br/>
>2. IE仅支持Text和URL两种类型，不支持text/plain、text/uri-list等类型<br/>
>3. text/plain类型则不会对数据进行额外处理，而text/uri-list类型则会将数据视为url来使用<br/>
>4. Chrome和FF支持非空字符串作为format<br/>
>5. 仅在dragstart事件有效<br/>
>6. 当没有填写第二个入参时，则会根据format来删除相应的数据项。<br/>
>7. 当设置text/uri-list类型的数据时，数据必须带协议名，如http://fsjohnhuang.cnblogs.com；若仅写为fsjohnhuang.cnblogs.com，那么dataTransfer将自动弃除，在`dragstart`事件还能获取到，但在`drop`事件中将无法获取。

`DOMString getData({DOMString} format)`：从dataTransfer或clipboardData中获取指定格式的数据<br/>
`void clearData([{DOMString} format])`：从dataTransfer或clipboardData中删除指定格式的数据<br/>
>1. 清除所有kind为string的数据项<br/>
>2. 仅在dragstart事件中有效，其他事件中调用会报InvalidStateError<br/>

## [object ClipboardData]
Chrome29提供undefined的evt.clipboardData属性，而IE和FF并提供。

## [object DataTransferItemList]
`readonly unsigned long length`<br/>
`getter DataTransferItem(unsigned long index)`,如items\[1\]<br/>
`deleter void(unsigned long index)`,如delete items\[1\]<br/>
`void clear()`<br/>
`DataTransferItem add(DOMString data, DOMString type)`<br/>
`DataTransferItem add(File data)`<br/>
**说明**<br/>
在非读写模式下调用`deleter void(unsigned long index)`会报InvalidStateError，添加数据则会不执行。

## [object DataTransferItem]
`readonly attribute DOMString kind`<br/>
`readonly attribute DOMString type`<br/>
`void getAsString(FunctionStringCallback cb)`<br/>
`File getAsFile()`<br/>
**说明**<br/>
`kind`：表示数据的类型，只能为string或file<br/>
`type`：实际数据的类型或格式，一般使用mimetype表示,但不强制规定要是mimetype<br/>
`getAsString(cb)`：当kind为string时，则在只读或读写模式下，则可用该方法读取数据。<br/>
`getAsFile()`：当kind为file时，则在只读或读写模式下，可用该方法读取数据，无则返回null<br/>

## [object FunctionStringCallback]
`void handleEvent(DOMString data)`<br/>

## 特征检测
参考Modernizr.draganddrop的实现！<br/>
https://github.com/Modernizr/Modernizr/blob/master/feature-detects/draganddrop.js<br/>
````
var supportDnD = function(){
		var div = document.createElement('div');
		return ('draggable' in div) || ('ondragstart' in div && 'ondrop' in div);
	};
````

## 参考
http://www.w3school.com.cn/html5/html_5_draganddrop.asp<br/>
http://www.cnblogs.com/wpfpizicai/archive/2012/04/07/2436454.html<br/>
http://www.kankanews.com/ICkengine/archives/82862.shtml<br/>
http://jingyan.baidu.com/article/6dad5075cf6e62a123e36e11.html<br/>
http://www.zhangxinxu.com/wordpress/2011/02/html5-drag-drop-%E6%8B%96%E6%8B%BD%E4%B8%8E%E6%8B%96%E6%94%BE%E7%AE%80%E4%BB%8B/<br/>
http://my.oschina.net/caixw/blog/102845<br/>
http://www.cnblogs.com/birdshome/archive/2006/07/22/Drag_Drop.html<br/>
《HTML5实战》第11章、HTML5中元素的拖放<br/>
《HTML5用户指南》第8章、拖放<br/>
http://msdn.microsoft.com/en-us/library/ff974353(v=vs.85).aspx<br/>
《HTML5与CSS3权威指南》4.5.拖放<br/>
《论道HTML5》3.3.Drag & Drop API<br/>


## 勘误
《HTML5实战》P292 setData的format参数格式包含text/url-list，**应更正为text/uri-list**<br/>

## 书评
《HTML5实战》第11章、HTML5中元素的拖放，这一章感觉就一笔带过，纯属印象派。<br/>
《HTML5用户指南》第8章、拖放，除了简单介绍HTML5 DnD API外，还介绍起源和IE上DnD的特点和作者对DnD API不完美的抱怨，比《HTML5实战》更值得拜读。<br/>
《HTML5与CSS3权威指南》4.5.拖放,内容，深度与《HTML5实战》相似<br/>
《论道HTML5》3.3.Drag & Drop API,对比上述三本书，它提及到使用Modernizr作DnD特征检测,其他基本相似<br/>

## HTML4 下实现拖拽的知识点
pageX, pageY：以页面左上角为参考点，表示当前元素左上角离页面页面左上角的水平和垂直距离。<br/>
clientX, clientY：<br/>
screenX
offsetX
