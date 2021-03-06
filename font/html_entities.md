## HTML 字符实体(character entities)
 由于HTML中某些字符（如<和>等）是预留的，若要正确地显示预留字符，则需要使用**字符实体**来代替。<Br/>
 实体字符有两种表示方式：实体名(entity name)和实体代号(entity number)<br/>
````
// 实体名
&entity_name;

// 实体代号
&#entity_number;
````
实体名便于记忆，但浏览器不一定能识别所有实体名。而所有浏览器均能识别所有实体代号。<br/>
注意：实体名对大小写敏感。<Br/>
字符实体和符号实体表：<br/>
[http://www.w3school.com.cn/tags/html_ref_entities.html](http://www.w3school.com.cn/tags/html_ref_entities.html)<br/>

### 分类
字符实体又分为 **ASCII实体**、**符号实体**和**字符实体**三种。
对于JS操作则分为ASCII实体为一类，而符号实体、字符实体则为另一类。<br/>
#### 对于ASCII实体
````
HTMLElement.innerHTML和HTMLElement.outerHTML均只能获取实体名或实体代号，而无法获取字符。
IE9+/FF则可以通过HTMLElement.textContent来获取字符。
IE可通过HTMLElement.innerText来获取字符。
所有浏览器可通过表单元素的value来获取字符。(如textarea.value)
````
#### 对于符号实体、字符实体
只能获取字符，而无法获取实体名和实体代号。<br/>

## innerText和textContent
由于innerText和textContent是基于innerHTML内容的不同处理后得到的结果，因此要先了解innerHTML的内容。<br/>
innerHTML中，除了会对符号实体和字符实体进行处理外，将保留HTML源码的内容。<br/>
````
<style type="text/css">
 .line3, .line4{
   float: left;
 }
 .line5::after{
   content: "test"
 }
</style>
<div id="view">
  <div>line1</div>
  <div>line2</div><br/>
  <div class="line3">line3</div>
  <div class="line4">line4</div>
  <div style="clear:both;"></div>
  <div class="line5">line5</div>
</div>
````

### innerText
浏览器支持：IE,Chrome.<br/>
对innerHTML的内容进行如下处理：<br/>
1. 对HTML标签进行解析；<br/>
2. 对CSS样式进行带限制的解析和渲染；<br/>
3. 对ASCII字符进行处理；<br/>
4. 剔除格式信息(如\t、\r和\n等)。<br/>
注意：各浏览器进行上述处理的程度是不同的。<br/>
**IE5.5~8**<br/>
执行步骤1、2、3、4，界面显示如下：
````
line1
line2

line3line4
line5
````
而innerText结果为：
````
line1
line2

line3line4line5
````
在进行CSS样式渲染时，不支持伪元素和clear:both等。
**IE9~11**<br/>
执行步骤1、2、3、4，界面显示如下：
````
line1
line2

line3line4
line5test
````
而innerText结果为：
````
line1

line2


line3

line4



line5
````
在进行CSS样式渲染时，只会使用元素的默认样式。<br/>
**Chrome**<br/>
执行步骤1、2、3、4，界面显示如下：
````
line1
line2

line3line4
line5test
````
而innerText结果为：
````
line1
line2

line3line4
line5
````
在进行CSS样式渲染时，不支持伪元素等。

### textContent
浏览器支持：IE9+,FireFox,Chrome.
对textContent的内容进行如下处理：<br/>
1. 对HTML标签进行剔除；
2. 对ASCII字符进行处理；
**所有浏览器**<br/>
界面显示:
````
line1
line2

line3line4
line5test
````
而textSource结果为：
````

  line1
  line2
  line3
  line4

  line5

````

## 设置值时，innerHTML、innerText和textContent的行为特点
三者均为可读可写。
````
<div id="target"></div>
<script type="text/javascript">
  var getDesc = function(){ return Object.getOwnPropertyDescriptor.apply(Object, arguments) }
    , get = function(){ return document.getElementById.apply(docuemnt, arguments) }
  var target = get('target');
  console.log(getDesc(target, 'innerHTML'))   // Object {value: "", writable: true, enumerable: true, configurable: true}
  console.log(getDesc(target, 'innerText'))   // Object {value: "", writable: true, enumerable: true, configurable: true}
  console.log(getDesc(target, 'textContent')) // Object {value: "", writable: true, enumerable: true, configurable: true}
</script>
````
1. 设置innerHTML时，会对值进行如下判断后再赋值（伪代码）
````
var val
function setInnerHTML(newVal){
  // 1. 判断是否为字符实体，是则直接赋值
  // 2. 否则则转换为字符实体后才赋值
  if (isEntity(newVal)){
    val = newVal
    return
  }
     
  val = convert2Entity(newVal)
}
````
2. 设置innerText和textContent时，不作判断直接转换为字符实体后赋值（伪代码）
````
var val
setInnerText = setTextContent = function (newVal){
  // 转换为字符实体后赋值
  val = convert2Entity(newVal)
}
````

## 表单元素的value, innerHTML, innerText, textContent
### textarea

FireFox
1. innerHTML可设置且有效，会影响textContent。另外在通过value设置值前，会影响value；通过value设置后，则value与textCotent/innerHTML是无关系的了。
2. textContent可设置且有效，会影响innerHTML。另外在通过value设置值前，会影响value；通过value设置后，则value与textCotent/innerHTML是无关系的了。
3. value可设置且有效。

Chrome
1. innerText可设置且有效，会影响innerHTML,textContent。另外在通过value设置值前，会影响value；通过value设置后，则value与textCotent/innerHTML是无关系的了。
但取值时发现innerText永远返回空字符串。
2. innerHTML可设置且有效，会影响textContent。另外在通过value设置值前，会影响value；通过value设置后，则value与textCotent/innerHTML是无关系的了。
3. textContent可设置且有效，会影响innerHTML。另外在通过value设置值前，会影响value；通过value设置后，则value与textCotent/innerHTML是无关系的了。
4. value可设置且有效。

IE9~11
innerHTML、value、innerText,textContent均可设置且有效，一直都相互响应。

IE5.5~8
innerHTML、value、innerText均可设置且有效，一直都相互响应。


### intput[type="text"]
Chrome
1. 虽然innerText是可写，但实际写入时会报错。
````
<input type="text" id="target">
<script type="text/javascript">
  var getDesc = function(){ return Object.getOwnPropertyDescriptor.apply(Object, arguments) }
    , get = function(){ return document.getElementById.apply(docuemnt, arguments) }
  var target = get('target');
  console.log(getDesc(target, 'innerText'))   // Object {value: "", writable: true, enumerable: true, configurable: true}
  target.innerText = "1" // NoModificationAllowedError: Failed to set the 'innerText' property on 'HTMLElement': The 'input' element does not support text insertion.
</script>
````

2. innerHTML设置不会报错，但设置无效。保持空字符串。

3. textContent可以设置且有效，但不会影响value,innerHTML和innerText

4. value可以设置，但不会影响textContent,innerHTML和innerText

IE5.5~8
1. innerText设置且有效，会影响value。但无法再次通过innerText来获取刚才设置的值。innerText的值始终为空字符串。innerText的值会被直接转成字符实体再赋值。

2. 虽然innerHTML是可写，但实际写入时会报错。获取时永远是空字符串
````
<input type="text" id="target">
<script type="text/javascript">
  var getDesc = function(){ return Object.getOwnPropertyDescriptor.apply(Object, arguments) }
    , get = function(){ return document.getElementById.apply(docuemnt, arguments) }
  var target = get('target');
  console.log(getDesc(target, 'innerHTML'))   // Object {value: "", writable: true, enumerable: true, configurable: true}
  target.innerText = "1" // 未知的运行时错误
</script>
````

3. value可以设置，但不会影响innerHTML和innerText.

IE9+
1. innerText可设置，且会影响value。不会影响innerHTML、textContent。innerText取值由innerHTML决定。
2. innerHTML可设置且有效，影响innerText和textContent，不影响value。
3. textContent可设置且有效，会影响innerHTML，取值使用innerHTML的值计算而成。
4. value可设置且有效，但不影响innerHTML,innerText和textContent。

FireFox
1. innerHTML可设置且有效，影响和textContent，不影响value。
2. textContent可设置且有效，会影响innerHTML，取值使用innerHTML的值计算而成。
3. value可设置且有效，但不影响innerHTML和textContent。

