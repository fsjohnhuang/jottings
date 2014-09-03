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
drag.ondragstart = function(e){
	e.dataTransfer.setData('text', e.target.innerHTML);
};
````
**关键点：**<br/>
1. `draggable="true"`,是用于启动HTML5 drag & drop 功能;<br/>
2. 必须在`drag.ondragstart`事件处理函数中有效调用`e.dataTransfer.setData()`。<br/>

## 生命周期
**被拖拽元素的生命周期**<br/>
1. `dragstart`:当被拖拽元素被开始拖拽时触发;<br/>
2. `dragend`:当被拖拽元素被释放且拖拽操作完成后触发(在`drop`后触发）<br/>
**目标元素的生命周期**<br/>
1. `dragenter`:当被拖拽元素进入目标元素时触发<br/>
2. `dragover`:当被拖拽元素在目标元素上移动时触发<br/>
3. `drop`:当被拖拽元素在目标元素上而且释放鼠标时触发<br/>

**整体生命周期**<br/>
`dragstart` -> `dragenter` -> `dragover` -> `drop` -> `dragend`<br/>

<font style="color:red">注意</font><br/>
dragover中必须调用evt.preventDefault()来阻止事件的默认行为，否则无法触发drop事件。<br/>
对于图片或其他文件而言，drop的默认行为是显示图片或打开文件，所以drop下也要调用evt.preventDefault()<br/>


