## XMLHttpRequest Level1研究
### 基础
1. 创建对象
````
var createXHR = function(){
	if (window.XMLHttpRequest){
		return new XMLHttpRequest(); // W3C标准
	}
	else{
		return new ActiveXObject('Microsoft.XMLHTTP'); // IE独有
	}
};
````
2. 对象状态
  xhr对象有一个readyState属性，用于表示xhr对象当前的请求/响应状态。
  >0（UNSENT）──表示"未初始化"状态，仅仅创建了xhr对象，而未调用open方法。<br/>
  >1（OPENED）──表示"准备"状态，已经调用了open方法。仅在该状态下才能设置请求头的个字段<br/>
  >2（HEADERS_RECEIVED）──表示"发送"状态，已经调用了send方法<br/>
  >3（LOADING）──表示"正在接收响应"状态，此时xhr已经接收到响应消息的起始行和响应头，但响应体还未接收完整<br/>
  >4（DONE）──表示"已接收完成"状态，此时响应消息已经接收完整了。<br/>

3. 方法与属性
**方法**
(a). **open方法**
````
xhr.open(DOMString method, DOMString uri, boolean async, DOMString username, DOMString password)
````
**method**：必填项，用于指定发送请求的HTTP方式，值范围GET POST PUT DELETE HEAD.
**uri**：必填项，用于指定发送请求的web资源，当设置为相对地址时，会根据`document.baseURI`来解析为绝对地址.
**async**：选填项，用于指定该请求的是同步还是异步请求，默认为true（异步请求）.
**username和password**：选填项，用于指定向服务器提供的用户名和口令参数.
<font style="color:red;">注意：</font>
无论xhr当前的readyState为多少， 调用open方法后，responseText、response、status和statusText等属性就会恢复到默认值，而readyState则变成1.

(b).**send方法**
````
xhr.send(DOMString data)
````
**data**：选填项，用于指定请求体内容，当请求的HTTP方式为POST时有用，其他方式时传null或不传任何值即可。
<font style="color:red;">注意：</font>
当open方法的async入参值为true时，调用send方法将立即返回;当async为false时，调用send方法将会阻塞。

(c). **setRequestHeader方法**
````
xhr.setRequestHeader(DOMString header, value)
````
**header**：请求头字段
**value**：请求头字段值
<font style="color:red;">注意：</font>
当xhr的readyState为1时才能设置请求头，否则会抛异常。

(d). **getResponseHeader方法**
````
xhr.getResponseHeader(DOMString header)
````
**header**：请求头字段
<font style="color:red;">注意：</font>
当xhr的readyState为3或4时才能获取响应头，否则会抛异常。

(d). **getAllResponseHeader方法**
````
xhr.getAllResponseHeader()
````

(e). **abort方法**
````
xhr.abort()
````
<font style="color:red;">注意：</font>
调用`abort`后，会暂停与xhr相关联的HTTP请求，并将对象复原为未初始化状态。

**属性**
(a). `xhr.readyState`：xhr对象的状态
(b). `xhr.status`：服务器返回的状态码
(c). `xhr.statusText`：服务器返回的状态文本
(d). `xhr.responseXML`：服务器返回的XML格式的数据，当响应头Content-Type为text/xml,application/xml或application/以+xml结尾，且readyState为4，并且符合xml格式时返回XMLDocument对象；否则为null
(e). `xhr.responseText`：服务器返回的文本格式的数据。
(f). `xhr.response`：服务器返回的响应消息实体，其值类型将根据`xhr.responseType`的指定。如果请求未完成或失败，则返回null。


4. GET请求示例
````
var xhr = createXHR();
xhr.open('GET', 'http://fsjohnhuang.test.com?msg=helloworld');
xhr.onreadystatechange = function(){
	if (xhr.readyState === 4 && xhr.status === 200){
		alert(xhr.responseText);
	}
};
xhr.setRequestHeader('If-Modified-Since', '0'); // 设置缓存
xhr.send();
````
5. POST请求示例
````
var xhr = createXHR();
xhr.open('POST', 'http://fsjohnhuang.test.com');
xhr.onreadystatechange = function(){
	if (xhr.readyState === 4 && xhr.status === 200){
		alert(xhr.responseText);
	}
};
xhr.setRequestHeader('If-Modified-Since', '0'); // 设置缓存
xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); // 设置消息体格式类型
xhr.send("name=fsjohnhuang&age=27");
````

6. 缺点：
(1). 只支持文本数据的传送，无法用来读取和上传二进制文件。<br/>
(2). 传送和接收数据时，没有进度信息，只能提示有没有完成。<br/>
(3). 收到同源策略限制，只能请求同域的资源。（IE下可通过Response Header——Access-Control-Allow-Origin来设置跨域请求）<br/>

### Zepto.js



## XMLHttpRequest Level2研究
### FormData类型
参考：https://developer.mozilla.org/zh-CN/docs/Web/API/XMLHttpRequest/FormData
1. 作用：序列化表单数据。
**构造函数**
`new FormData([HTMLFormElement form])`<br/>
**append方法**
`void append(DOMString name, Blob value [, DOMString filename])`<br/>
`void append(DOMString name, DOMString value)`<br/>
说明：
`name`:键<br/>
`value`:值<br/>
`filename:当value为一个Blob对象或File对象时，该文件名会被发送到服务器。对于Blob对象，该值默认为"blod".<br/>

示例1：将form元素的数据整体序列化
````
var fd = new FormData(doucment.forms[0]);
````
示例2：逐个键值对加入
````
var fd = new FormData();
fd.append("name", "fsjohnhuang");
````
示例3：通过表单元素的`getFormData()`获取
````
var frm = document.forms[0];
var fd = frm.getFormData();
````
2. 浏览器支持：FF4+,Safari5+,chrome,android3+,IE10+,Opera12+

3. jq的serialize方法




### 各浏览器实现的XHR
1. FF
onreadystatechange:默认值null<br/>
readyState:默认值0<br/>
timeout:默认值0<br/>
withCredentials:默认值false<br/>
upload:默认值XMLHttpRequestUpload<br/>
status:默认值0<br/>
statusText:默认值空字符串<br/>
responseType:默认值空字符串<br/>
response:默认值空字符串<br/>
responseText:默认值空字符串<br/>

## Blob
**1. 什么是Blob**
一个Blob对象就是一个包含只读原始数据的类文件对象。其中的数据不一定是js的原生形式。<br/>
File接口也是基于Blob，继承Blob的所有功能并扩展支持用户计算机的本地文件。<br/>

**2. 构造方式**
(a). 构造函数
`Blob Blob([Array parts] [, BlobPropertyBag properties])`
说明：
`parts`：数组，包含将要添加到Blob对象中的数据。数组元素可以是ArrayBuffer，ArrayBufferView(typed array)，Blob或DOMString对象。
`properties`：Blob对象的一些属性。
(b). Blob对象的slice方法
`Blob slice([long start][, long end][, DOMString contentType])`
说明：
`start`：开始索引，可以为负数，语法与数组的slice类似。默认值为0.
`end`：结束索引，可以为负数，语法与数组的slice类似。
`contentType`：设置返回的Blob对象的MIME类型，这个值将成为返回的Blob对象的type属性值，默认为空字符串。
<font>注意：</font>
对于一些浏览器，slice是需要加前缀的，如FF12-就是`mozSlice`,Safari就是`webkitSlice`
(c). canvas对象的toBlob方法
`toBlob(Function callback, DOMString type)`
(d). BlobBuilder方法（已被废除的，仅为知识完整性）
````
var oBuilder = new BlobBuilder();
var aFileParts = ["<a id=\"a\"">helloworld</a>"];
oBuilder.append(aFileParts);
var oMyBlob = oBuider.getBlob("text\/html");
````

**2. 属性**
`size`：类型为unsigned long，Blob对象的大小，只读。
`type：类型DOMString，表示Blob对象的MIME类型，类型未知该值为空字符串，只读。

**3. 方法**
`slice`：与数组的slice相似。

**4. BlobPropertyBag对象**
属性`type`:设置Blob对象的MIME类型
属性`endings`（已废除）:值范围，transparent和native用于BlobBuilder.append中
示例：
```
var aFileParts = ["<a id=\"a\"">helloworld</a>"];
var oMyBlob = new Blob(aFileParts, {"type": "text\/html"});
```

**5. 浏览器支持**
Chrome、FF、IE10+、Opera、Safari

## ArrayBuffer
作用：保存固定长度的二进制数据，并可通过它读写二进制数据。
**属性：**
`byteLength`：类型unsigned long，表示二进制数据的字节数，只读。
**构造函数**
`ArrayBuffer ArrayBuffer(unsigned long length)`，初始化时就要设置容量。
**方法：**
`slice(unsigned long begin[, unsigned long end])`：与Array的slice类似。IE10和IOS6-不支持
``
**浏览器支持**
Chrome、FF、IE10+、Opera、Safari

## ArrayBufferView(typed array——类型数组)
**作用：**ArrayBuffer的局部视图，一个ArrayBuffer可以有多个ArrayBufferView。ArrayBufferView是基类，一般使用其子类。
**属性：**
`buffer`：类型ArrayBuffer,视图指向的ArrayBuffer对象，只读。
`byteLength`：类型unsigned long，视图中数据的字节数，只读。
`byteOffset`：类型unsigined long，视图首字节位于所指向的ArrayBuffer的位置，只读。
**子类：**
Int8Array,Uint8Array,Int16Array,Uint16Array,Int32Array,Uint32Array,Float32Array,Float64Array。


## DOMString
UTF-16的字符串，js就是使用这种编码的字符串，因此DOMString等同于js的String

## File

## URL.createObjectURL(Blob blob)创建一个指向入参blob对象的别名，比Data URI更省内存。

## FileReader
**1. 作用：**用于异步读取用户计算机上的文件或其他原始数据，使用File或Blob类型对象来指定要读取的数据。（File或Blob对象可通过input元素的FileList对象或DataTransfer对象获取）
**2. 构造方法**
`var reader = new FileReader()`
**3. 方法**
`void abort()`：中止读取操作，返回时，readyState值为DONE。
<font style="color:red;">注意：当FileReader对象没有在进行读取操作时，调用`abort()`会抛DOM\_FILE\_ABORT\_ERR异常</font>
`void readAsArrayBuffer(Blob blob)`：读取指定的Blob或File对象，读取完时，readyState值为DONE，并触发onload事件，result属性为ArrayBuffer对象。
`void readAsBinaryString(Blob blob)`：读取完后，result属性为文件的原始二进制数据。
`void readAsDataURL(Blob blob)`：读取完后，result属性为data uri schemem字符串。
`void readAsText(Blob blob[, DOMString encoding="utf-8"])`:读取完后，result属性为所读取文件内容的字符串。
**4. 属性**
`error`：类型DOMError，表示在读取文件时发生的错误，只读。
`readyState`：类型unsigned short，表示FileReader对象的当前状态（EMPTY——0，还没有加载任何数据；LOADING——1，数据正在加载；DONE——2，已完成全部的读取请求），只读。
`result`：读取到的文件内容，只读。
**5. 事件**
`onabort`: 当读取操作被终止时触发
`onerror`: 当读取操作发生错误时触发
`onload`: 当读取操作成功完成时触发
`onloadend`: 当读取操作完成时触发，在onload和onerror后被触发
`onloadstart`: 当读取操作开始前触发
`onprogress`: 在读取数据的过程中周期性触发


http://www.cnblogs.com/suiqirui19872005/archive/2007/06/03/769431.html
**6. 浏览器支持**
FF3.6+，Chrome7+，IE10+

**7. 示例：预览**
html片段
````
<style type="text/css">
#preview{
	width: 100px;
	height: 100px;
}
</style>
<!--[if lte IE 9]>
<style type="text/css">
	#preview{
		filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);
	}
</style>
<![endif]-->

<input type="file" onchange="showPreview(this);"/>
<div id="preview">
</div>
````
js片段
````
var preview = function(el){
	var pv = document.getElementById("preview");
	// IE5.5~9使用滤镜
	if (pv.filters && typeof(pv.filters.item) === 'function'){
		pv.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = el.value;
	}
	else{
		// 其他浏览器和IE10+（不支持滤镜）则使用FileReader
		var fr = new FileReader();
		fr.onload = function(evt){
			var pvImg = new Image();
			pvImg.style.width = pv.offsetWidth + 'px';
			pvImg.style.height = pv.offsetHeight + 'px';
			pvImg.src = evt.target.result;
			pv.removeChild(0);
			pv.appendChild(pvImg);
		};
		fr.readAsDataURL(el.files[0]);
	}
};
````
**8. 相关知识**
**(1). 滤镜：DXImageTransform.Microsoft.AlphaImageLoader**
作用：主要作用是对图片进行透明处理。ie5.5~6是不支持透明的PNG图片
语法：filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(\[enabled=<enabled>\]\[,sizingMethod=<size>\]\[,src=<src>\])
说明：
`enabled`：可选项，设置滤镜是否激活。值范围true(默认)，false<br/>
`sizingMethod`：可选项，设置滤镜作用的图片在容器边界内的显示方式，值范围crop（剪切图片以适应容器尺寸），image（默认值，增大或缩小容器尺寸以适应图片的尺寸），scale（缩放图片以适应容器尺寸）
`src`：必填项，使用绝对或相对URL指向背景图片。当URL为用户计算机本地地址时有效, 而img元素的src为用户计算机本地地址时会抛不允许访问本地文件系统的异常。
**(2). Image类型**
js中对应img标签的类型，使用方式与img标签一致。
````
var i = new Image();
console.log(Object.prototype.toString.call(i)); // 返回 [object HTMLImageElement]
````


**(3). IE条件注释**
非IE浏览器会将整体视为注释，忽略掉。
格式：
````
<!--[if !IE]>
	内容
<![endif]-->
````
示例：
````
<!--[if lte IE 7]>
<script type="text/javascript" src="dummy.js"></scrpt>
<![endif]-->
````

**9. 坑**
1. IE11下，设置文档模式为ie5.5~9
通过点方式、getAttinput和outerHTML获取[type="file"]元素的value值永远是c:\fakepath\文件名。无法获取真实路径。
2. js操作滤镜
````
var span = document.getElementById('span');
span.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='test.jpg')";
````

## HTML5 的File元素
**1. 指定文件类型**
`<input type="file" accept="<MIME>"/>`
通过设置MIME值限定初始化时，仅能选择某些类型的文件。
示例：
````
<input type="file" accept="application/msword"/>

<input type="file" accept="application/msexecl, audio/x-aiff"/>

<input type="file" accept="application/*, audio/*"/>

<input type="file" accept="*/*"/>
````


## 输入法控制
1. IE和FF下
通过ime-mode样式控制。
````
<!-- 不显示输入法，不能切换输入法 -->
<input type="text" style="ime-mode:disabled;"/>
<!-- 自动切换到英文输入法，还可以再手动切换输入法 -->
<input type="text" style="ime-mode:inactive;"/>
<!-- 自动切换到中文输入法，还可以再手动切换输入法 -->
<input type="text" style="ime-mode:active;"/>
<!-- 不干预当前的输入法，默认值 -->
<input type="text" style="ime-mode:auto;"/>
````
脚本访问
````
var name = document.getElementById("name");
name.currentStyle.imemode;
name.style.imemode;
````
2. Webkit下
通过脚本控制
<input type="text" onkeyup="this.value=this.value.replace(/\[\\u4e00-\\u9fa5\]/g, '')"/>
\u4e00-\u9fa5为中文的编码范围