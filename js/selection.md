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
**anchorNode**, 只读,返回最后一个range对象"起点"所在的节点<br/>
**anchorOffset**,只读,返回"起点"在anchorNode中的偏移量<br/>
**focusNode**,只读,返回最后一个range对象"结束点"所在的节点<br/>
**focusOffset**,只读,返回"结束点"在focusNode中的偏移量<br/>
**isCollapsed**,只读,返回起点和结束点在同一个节点上里<br/>
**rangeCount**,只读,selection对象中含有的range对象数目<br/>
**方法：**<br/>
**addRange({Range} range):**将range对象添加到selection当中
**collapse({HTMLElement} parentNode, {unsigned int} offset):**将开始点和结束点合并成一点，位于到parentNode的offset位置上。offset为取值范围是[0(第一个子元素前),1(第一个子元素后),....,parentNode.childNodes.lenth(最后一个子元素后)]。<br/>
**collapseToEnd():**将起点移动到selection的结束点，多个range的话就合并成一个range，且起点和结束点均在最后一个range对象的结束点上。<br/>
**collapseToStart():**将结束点移动到selection的起点,多个range的话就会合并成一个range，且起点和结束点均在第一个range对象的起点上。<br/>
**containsNode({HTMLElement} aNode, {Boolean}aPartlyContained):**用于判断aNode节点是否属于selection的一部分。aPartlyContained为true时,aNode部分属于也返回true;aPartlyContained为false时，aNode部分属则返回false，全部属于则返回true。<br/>
**deleteFromDocument():**将selection下rang对象中的TextNode内容从文档中删除。注意，1.作用于所有range对象;2. 仅删除TextNode的全部或部分内容，而不会删除其他元素<br/>
**extend({HTMLElement} parentNode, {unsigned int} offset):**起点不动，将结束点移动到parentNode的offset位置上。当有多个range对象时，则仅对最后一个range对象生效。<br/> 
**getRangeAt({Number} index):**从当前selection对象中获取一个range对象。 <br/>
**modify({DOMString} alter, {DOMString} direction, {DOMString} granularity):**用于改变焦点的位置，或扩展、缩小selection的大小。alter(改变的方式)取值范围：move（移动焦点）,extend（改变selection大小）;direction（移动的方向）,取值范围：forward、backword或left、right;granularity（移动的单位活尺寸）,取值范围：character,word<br/>
**toString():**仅返回selection不含节点信息的纯文本内容。<br/>
**removeAllRanges():**清除selection中的所有range对象，anchorNode和focusNode将被设置为null。<br/>
**removeRange({Range} range):**从selection中移除指定的range对象，该range对象必须属于该selection对象才行，否则会抛异常。<br/>
**selectAllChildren({HTMLElement} parentNode):**删除selection中所有range对象，然后将parentNode的后代节点作为一个range对象并加入到selection中。<br/>

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
**startContainer:**起点所属的节点<br/>
**endContainer:**结束点所属的节点<br/>
**startOffset:**起点在startContainer中的偏移量，若startContainer是文本节点、注释节点或CDATA节点，则返回startContainer到当前位置的utf-16字符个数（也就是中文和英文的字符数是一样的）;若startContainer是元素节点，则返回起点在startContainer.childNodes中的次序。<br/>
**endOffset:**结束点在endContainer中的偏移量，细节与startOffset一样。<br/>
**commonAncestorContainer:**开始和结束点共同的祖先节点。若collapsed为true时，则返回他们所属的节点，否则返回他们所属节点共同的祖先节点<br/>
**collapsed:**起点和结束点在同一个节点时或range刚初始化时为true。<br/>

**定位方法**
**setStart({Node} node, {unsigned int} offset):**设置起点，node为元素节点时，offset表示起点在node.childNodes的偏移量;node为文本、注释和CDATA节点时，offset表示起点在字符中的偏移量<br/>
**setEnd({Node} node, {unsigned int} offset):**设置结束点，具体与setStart一致。<br/>
<strong style="color:red;">
关于well-formed(fragment的结构完整)<br/>
在使用setStart和setEnd方法设置range起点和结束点时，可能仅截取元素的部分内容，因此截取部分的结构并不完整（非well-formed），此时浏览器会自动隐式将其well-formed化。实例：
````
<div id="d"><b>hello</b>world</div>
var r = document.createRange();
r.setStart(d.firstChild.firstChild, 2);
r.setEnd(d.lastChild, 3);
var dr = r.extractContents();
// 此时dr获取的不是llo</b>wor，而是<b>llo</b>wor
````
</strong>
**setStartBefore({Node} referenceNode):**将起点设置为referenceNode前<br/>
**setStartAfter({Node} referenceNode):**将起点设置为referenceNode后<br/> setEndBefore({Node} referenceNode):将结束点设置为referenceNode前<br/>
**setEndAfter({Node} referenceNode):**将结束点设置为referenceNode后<br/>
**selectNode({Node} referenceNode):**设置range的范围，包含referenceNode和它的后代节点。<br/>
**selectNodeContents({Node} referenceNode):**设置rang的范围，仅包含referenceNode的后代节点<br/>
**collapse({Boolean} toStart):**折叠range，使起点和结束点重合。true时折叠到起点，false时折叠到结束点。<br/>

**修改范围的方法**
**cloneContents():**返回值是HTMLDocumentFragment对象,复制range内容，但不会删除原有的<br/>
**deleteContents():**删除range内容<br/>
**extractContents():**返回值是HTMLDocumentFragment对象,提取range内容，文档中相应的部分会被删除<br/>
**insertNode({Node} node):**将node插入到range起点处<br/>
**surrondContents({Node} node):**将range内容包含在node内,若range对象包含的原内容不是well-formed则会抛BAD_BOUNDARYPOINTS_ERR<br/>

**其他方法**
**cloneRange():**返回值是Range对象，用于复制range对象<br/>
**compareBoundaryPoints({unsigned int} how, {Range} sourceRange):**比较两个range的边界位置。返回值，0(相等),1(当前range在sourceRange之后),-1(当前range在sourceRange之前); how用于设置比较那些边界点，取值范围Range.START_TO_START(比较两个的开始点),Range.END_TO_END(比较两个的结束点),Range.START_TO_END(比较sourceRange的开始点和当前range的结束点),Range.END_TO_START(比较sourceRange的结束点和当前range的开始点)<br/>
**detach():**用于主动释放range资源<br/>
**toString():**返回range的纯文本内容，不含标签信息<br/>


## TextRange
  表示用户选择的区域内容，可包含文本和节点，但其操作方式大多以文字为单位。


## ControlRange
  div，img和object等处于编辑状态时，单击就会选中整个控件。


## HTML5属性
document.activeElement，返回当前获得焦点的元素，若没有则返回body元素。<br/>


document.designMode = 'on'
