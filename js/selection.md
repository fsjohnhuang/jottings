##由于selection不是W3C标准，因此各浏览器实现的方式不同
selection对象表示当前激活的选中区,可包含文本或其他元素。而且文档同一时间仅有一个选中区(selection对象，但可以有N个range对象)。
##步骤
1. 创建选中区
(a).用户通过拖拽文档内容来创建
(b).脚本通过调用对象的`select`方法创建
2. 从选中区创建文本区域对象
首先获取选中区
IE下
````
document.selection
````
FF下
````
window.getSelection()
````
然后调用createRange方法，返回TextRange或ControlRange对象

selection.type获取选中的类型

##实例
1. 粗体
````
var txtSelection = document.selection.createRange();
txtSelection.execCommand('Bold');
````
2. 清除选中的内容
````
document.selection.clear();
````

文本区域对象类型
[object Range]

选中区对象类型
[object Selection]
FF下
**属性：**<br/>
anchorNode, 只读,返回最后一个range对象"起点"所在的节点<br/>
anchorOffset,只读,返回"起点"在anchorNode中的偏移量<br/>
focusNode,只读,返回最后一个range对象"结束点"所在的节点<br/>
focusOffset,只读,返回"结束点"在focusNode中的偏移量<br/>
isCollapsed,只读,返回起点和结束点在同一个节点上里<br/>
rangeCount,只读,selection对象中含有的range对象数目<br/>
**方法：**<br/>
addRange({Range} range):将range对象添加到selection当中
collapse({HTMLElement} parentNode, {unsigned int} offset):将开始点和结束点合并成一点，位于到parentNode的offset位置上。offset为取值范围是[0(第一个子元素前),1(第一个子元素后),....,parentNode.childNodes.lenth(最后一个子元素后)]。<br/>
collapseToEnd():将起点移动到selection的结束点，多个range的话就合并成一个range，且起点和结束点均在最后一个range对象的结束点上。<br/>
collapseToStart():将结束点移动到selection的起点,多个range的话就会合并成一个range，且起点和结束点均在第一个range对象的起点上。<br/>
containsNode({HTMLElement} aNode, {Boolean}aPartlyContained):用于判断aNode节点是否属于selection的一部分。aPartlyContained为true时,aNode部分属于也返回true;aPartlyContained为false时，aNode部分属则返回false，全部属于则返回true。<br/>
deleteFromDocument():将selection下rang对象中的TextNode内容从文档中删除。注意，1.作用于所有range对象;2. 仅删除TextNode的全部或部分内容，而不会删除其他元素<br/>
extend({HTMLElement} parentNode, {unsigned int} offset):起点不动，将结束点移动到parentNode的offset位置上。当有多个range对象时，则仅对最后一个range对象生效。<br/> 
getRangeAt({Number} index):从当前selection对象中获取一个range对象。 <br/>
modify({DOMString} alter, {DOMString} direction, {DOMString} granularity):用于改变焦点的位置，或扩展、缩小selection的大小。alter(改变的方式)取值范围：move（移动焦点）,extend（改变selection大小）;direction（移动的方向）,取值范围：forward、backword或left、right;granularity（移动的单位活尺寸）,取值范围：character,word<br/>
toString():仅返回selection不含节点信息的纯文本内容。<br/>
removeAllRanges():清除selection中的所有range对象，anchorNode和focusNode将被设置为null。<br/>
removeRange({Range} range):从selection中移除指定的range对象，该range对象必须属于该selection对象才行，否则会抛异常。<br/>
selectAllChildren({HTMLElement} parentNode):删除selection中所有range对象，然后将parentNode的后代节点作为一个range对象并加入到selection中。<br/>

## Range类型
定义：是一种fragment,可包含节点或文本节点的一部分。<br/>
获取方式：
1. 通过selection对象的getRangeAt()<br/>
2. DOM 2 Level中定义了document.createRange()来创建range对象
````
if (document.implementation && 
	document.implementation.hasFeature &&
	document.implementation.hasFeature('Range', '2.0')){
	// 支持
	var range = document.createRange();
}
else{
	// 不支持
}
````
**只读属性**
startContainer
endContainer
startOffset
endOffset
commonAncestorContainer
collapsed


## HTML5属性
document.activeElement，返回当前获得焦点的元素，若没有则返回body元素。<br/>


document.designMode = 'on'
