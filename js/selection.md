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
属性：
anchorNode, 只读,返回包含"起点"的节点
anchorOffset,只读,返回"起点"在anchorNode中的偏移量
focusNode,只读,返回包含"结束点"的节点
focusOffset,只读,返回"结束点"在focusNode中的偏移量
isCollapsed,只读,返回起点和结束点在同一个节点上里
rangeCount,只读,selection对象中含有的range对象数目
方法：
addRange(arg1)
collapse({HTMLElement} parentNode, {Number} offset):将开始点和结束点合并成一点，位于到parentNode的offset位置上。offset为取值范围是[0,1,....,parentNode.childNodes.lenth]
collapseToEnd()
collapseToStart()
containsNode(arg1, arg2)
deleteFromDocument()
extend(arg1, arg2)
getRangeAt({Number} index):从当前selection对象中获取一个range对象。
modify(arg1, arg2, arg3)
removeAllRanges()
removeRange(arg1)
selectAllChildren(arg1)
