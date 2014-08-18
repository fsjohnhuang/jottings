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
(1). 只支持文本数据的传送，无法用来读取和上传二进制文件。
(2). 传送和接收数据时，没有进度信息，只能提示有没有完成。
(3). 收到同源策略限制，只能请求同域的资源。（IE下可通过Response Header——Access-Control-Allow-Origin来设置跨域请求）

### Zepto.js



## XMLHttpRequest Level2研究
### FormData类型
1. 作用：序列化表单数据。
示例1：将form元素的数据整体序列化
```
var fd = new FormData(doucment.forms[0]);
```
示例2：逐个键值对加入
```
var fd = new FormData();
fd.append("name", "fsjohnhuang");
```
2. 浏览器支持：FF4+,Safari5+,chrome,android3+



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

