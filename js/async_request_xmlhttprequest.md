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
  >0──表示"未初始化"状态，仅仅创建了xhr对象，而未调用open方法。
  >1──表示"准备"状态，已经调用了open方法。仅在该状态下才能设置请求头的个字段
  >2──表示"发送"状态，已经调用了send方法
  >3──表示"正在接收响应"状态，此时xhr已经接收到响应消息的起始行和响应头，但响应体还未接收完整
  >4──表示"已接收完成"状态，此时响应消息已经接收完整了。

3. 方法
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

### 各浏览器实现的XHR
1. FF
onreadystatechange:默认值null
readyState:默认值0
timeout:默认值0
withCredentials:默认值false
upload:默认值XMLHttpRequestUpload
status:默认值0
statusText:默认值空字符串
responseType:默认值空字符串
response:默认值空字符串
responseText:默认值空字符串

