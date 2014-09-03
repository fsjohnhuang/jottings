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
3. `drop`:当被拖拽元素在目标元素上而且释放鼠标时触发<br/>

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

## \[object DataTransfer\]对象
  拖拽生命周期的各个事件的事件对象中均有DataTransfer对象，用于在拖拽过程中传递信息。而它可以存储两种数据类型：字符串、文件<br/>
**数据的访问模式**<br/>
1. Read/Write mode：在`dragstart`事件中，可读写数据<br/>
2. Read-only mode：在`drop`事件中，仅能读取数据<br/>
3. Protected mode：在其他DnD事件中，仅能枚举数据<br/>

****<br/>
`effectAllowed`：类型为DOMString，初始化`dragenter`和`dragover`事件中的`dropEffect`属性。取值范围如下：<br/>
>copy<br/>
>link<br/>
>move<br/>
>copylink<br/>
>linkmove<br/>
>all<br/>
>none<br/>
>uninitialized<br/>

`dropEffect`：类型为DOMString，如果与effectAllowed不一致则drop动作将会失败<br/>
>copy<br/>
>link<br/>
>move<br/>
>none<br/>

`items`：类型为DataTransferItems<br/>
`files`：类型为FileList<br/>
`types`：类型为DOMStringList<br/>

`void addElement({Element} element)`：添加一起跟随拖拽的元素。<br/>
`void setDataImage({Element} image, {long} x, {long} y)`：设置拖动时跟随鼠标移动的图片，用来替代默认的元素。<br/>
`void setData({DOMString} format, {DOMString} data)`：将指定格式的数据赋值给dataTransfer或clipboardData，format值范围为URL、Text、File、HTML和Image<br/>
`DOMString getData({DOMString} format)`：从dataTransfer或clipboardData中获取指定格式的数据<br/>
`void clearData([{DOMString} format])`：从dataTransfer或clipboardData中删除指定格式的数据<br/>

## 参考
http://www.cnblogs.com/wpfpizicai/archive/2012/04/07/2436454.html
