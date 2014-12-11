浏览器限制，对同一个Domain最多为2个连接数.<br/>
通过将资源分布在不同的Domain中就可以突破2个连接数的上限了。但不是越多连接数越好，一般在6~7个为最佳。现在浏览器上限提高到6个连接<br/>
被浏览器判断为不同域名的情况：<br/>
1. 域名及其子域名<br/>
2. 同一域名但端口号不同<br/>

## 同步加载script资源
````
<script type="text/javascript" src="test.js"></script>
````
当浏览器在解析HTML文件时遇到外部script资源加载请求，那么解析过程被暂停(包含解析HTML、执行其他脚本和展示CSS布局)，直至script资源加载成功并被解析执行后，再继续解析HTML文件的后续内容。<Br/>
IE8+支持并行加载script资源，但依然会暂停HTML解析、图片资源下载。<br/>
## 异步加载script资源
1. defer标准属性(比较少用)<br/>
定义如下：<br/>
````
When set, this boolean attribute provides a hint to the user agent that the script is not going to generate any document content (e.g., no "document.write" in javascript) and thus, the user agent can continue parsing and rendering.
````
IE4开始支持，异步加载script资源，并且在DOM解析完成后和`document的DOMContentLoaded事件`触发前，按照script元素的顺序自上而下执行script资源。<br/>
设置defer属性的script元素，完全不会阻塞后续的HTML解析。因此会存在脚本未加载完，用户点击页面某按钮导致操作失败的异常情况<br/>
FF不支持<Br/>
2. async标准属性<br/>
异步加载script资源，并且资源下载完后马上执行，因此script资源不一定按照script元素的排序子上而下被执行。<Br/>

## 动态加载script
只有script元素添加到文档后才会发起资源下载请求。<br/>
异步加载<Br/>
1. 通过`document.write`<Br/>
脚本的执行顺序与脚本文件出现的顺序相同。<br/>
FF已经支持通过`document.write`是实现并发请求了<br/>
`document.write`的异步加载仅针对同一script元素内，也就是同一script元素内的`document.write`请求将并发处理，浏览器会暂停HTML解析、图片请求和其他script元素的解析。<Br/>
````
// 为避免</script>标签的错误解析，因此要拆分字符串
document.write('<script src="test.js"><' + '/script>')
````
2. 改变已有的script元素的src属性<br/>
````
var currScript = document.getElementById('s1')
s1.src = 'test.js'
````
3. 动态创建script元素<br/>
该方式下载和运行均不会阻塞其他操作(HTML文档解析)。就是占用HTTP连接<br/>
IE下脚本下载完会马上执行（FF和Opera会等待其他script均下载完才执行,然后按发起请求的顺序执行）<Br/>
IE9+和其他浏览器可通过onload事件监控脚本下载是否完成。<br/>
IE5~8则通过onreadystatechange事件监控。<br/>
readyState有这些状态：<br/>
uninitialized, 默认状态<Br/>
loading, 下载开始<Br/>
loaded, 下载完成<Br/>
interactive, 下载完成但不可用<Br/>
complete, 所有数据已经准备好<br/>
这些状态不会全部出现，有时会出现loaded，有时则出现complete，有时两者均会出现按如下方式处理较为恰当<Br/>
````
var needU = function(url, cb){
  var scriptEl = document.createElement('script');
  scriptEl.type = 'text/javascript'
  scriptEL.src = url
  if (scriptEl.readyState)
    // IE5~10
    scriptEl.onreadystatechange = function(){
	  	if (!/loaded|complete/i.test(scriptEl.readyState)) return

	  	scriptEl.onreadystatechange = null
	  	cb()
    }
   else
   	scriptEl.onload = cb

  document.body.appendChild(scriptEl)
}
````
4. 通过XHR<br/>
可控制script的执行顺序<Br/>
````
var getXHR = function(){
	if (window.XMLHttpRequest) 
		return new XMLHttpRequest()
	var progIDs = ['Msxml2.XMLHTTP', 'Microsoft.XMLHTTP']
	for (var i = 0, progID; progID = progIDs[i++];)
		try
			return new window.ActiveXObject(progID)
		catch(e);
}
var xhr = getXHR()
xhr.open('get', 'test.js', true)
xhr.onreadystatechange = function(){
	if (xhr.readyState !== 4) return

	if (xhr.status >= 200 && xhr.status < 300 || xhr.status === 304){
		var el = document.createElement('script')	
		el.type = 'text/javascript'
		el.text = xhr.responseText
		document.body.appendChild(el)
		// 或者
		// eval(xhr.reponseText)
	}
}
xhr.send(null)
````
当服务器无法访问（不是503状态码，而是网络连接断了等），则反问xhr.readyState时会抛异常。<br/>
5. iframe方式<br/>
iframe加载资源时不会暂停后续HTML内容的解析和图片资源加载等操作。但整个页面的onload事件会在所有iframe触发onload事件后才会触发，且浏览器会一直显示繁忙的状态。<br/>
新建一个空白的iframe并在iframe中调用脚本加载，相当于多一个页面加载数据<br/>
````
var iframe = document.createElement('iframe'); 
document.body.appendChild(iframe); 
var doc = iframe.contentWindow.document; 
doc.open().write('<body onload="insertJS()">'); 
doc.close();
````

开源库：
[yepnop.js](https://github.com/SlexAxton/yepnope.js)
require.js

## 跨域请求
1. iframe+document.domain<br/>
主页可直接操作子域名的iframe。因此可以通过设置iframe页面的`document.domain="主域名"`。然后主页调用iframe页的XHR发起对子域名资源的请求，达到跨域请求的目的。<br/>

##参考
http://www.cnblogs.com/JeffreyZhao/archive/2007/01/20/Break_the_Browsers_Restrictions_2.html
