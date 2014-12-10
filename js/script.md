## 同步加载script资源
````
<script type="text/javascript" src="test.js"></script>
````
当浏览器在解析HTML文件时遇到外部script资源加载请求，那么解析过程被暂停(包含解析HTML、执行其他脚本和展示CSS布局)，直至script资源加载成功并被解析执行后，再继续解析HTML文件的后续内容。<Br/>
IE8+支持并行加载script资源，但依然会暂停HTML解析、图片资源下载。<br/>
## 异步加载script资源
1. defer标准属性<br/>
IE4开始支持，异步加载script资源，并且在DOM解析完成后和`document的DOMContentLoaded事件`触发前，按照script元素的顺序自上而下执行script资源。<br/>
2. async标准属性<br/>
异步加载script资源，并且资源下载完后马上执行，因此script资源不一定按照script元素的排序子上而下被执行。<Br/>

## 动态加载script
只有script元素添加到文档后才会发起资源下载请求。<br/>
异步加载<Br/>
1. 通过`document.write`<Br/>
````
document.write('<script src="test.js"></script>')
````
2. 改变已有的script元素的src属性<br/>
````
var currScript = document.getElementById('s1')
s1.src = 'test.js'
````
3. 动态创建script元素
该方式下载和运行均不会阻塞其他操作。就是占用HTTP连接<br/>
脚本下载完会马上执行（FF和Opera会等带其他script均下载完才执行）<Br/>
IE9+和其他浏览器可通过onload事件监控脚本下载是否完成。<br/>
IE5~8则通过onreadystatechange事件监控。<br/>
readyState有这些状态：<br/>
uninitialized, 默认状态<Br/>
loading, 下载开始<Br/>
loaded, 下载完成<Br/>
interactive, 下载完成但不可用<Br/>
complete, 所有数据已经准备好<br/>
````

var needU = function(url){
  var scriptEl = document.createElement('script');
  scriptEl.type = 'text/javascript'
  scriptEL.src = url
  document.body.appendChild(scriptEl)
}
````
同步加载<br/>
1. 通过ajax获取

##参考
http://www.58lou.com/separticle.php?artid=239
http://www.cnblogs.com/JeffreyZhao/archive/2007/01/25/break_the_browsers_restrictions_4.html
