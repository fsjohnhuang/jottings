必须设置rel和添加到渲染树，否则不会触发事件<br/>
IE和Chrome下，当disabled===true时，不会触发事件<br/>
FF下，当disabled===true时，仍然会触发事件<br/>
## test.css
````
<link type="text/css" rel="stylesheet" href="test.css"/>
````
**webkit**<br/>
首次onload延时为5ms，后续读取缓存为0~1ms。<br/>
**IE**<br/>
IE11：首次onload延时9ms，后续读取缓存则为0~1ms。<br/>
IE5~10：<Br/>
首次加载时：<br/>
onreadystatechange会触发两次，从"uninitialized"->"loading"->"complete"。<Br/>
先触发两次onreadystatechange事件，然后才会触发onload事件(17ms)。
后续加载会从缓存加载：<Br/>
onreadystatechange会触发一次，从"unitialized"->"complete"<br/>
触发onreadystatechange事件，然后会触发onload事件(6ms).
**FF**<br/>
首次onload延时为5ms，后续读取缓存为0~1ms。<br/>
## fsjohnhuang.png
````
<link type="text/css" rel="stylesheet" href="fsjohnhuang.png"/>
````
**webkit**<br/>
首次onload延时为4ms，后续读取缓存为0~2ms。<br/>
**IE**<br/>
IE11:<Br/>
onload延时为7~33ms，不会对资源进行缓存。<br/>
IE5~10:<Br/>
onreadystatechange会触发一次，从"unitialized"->"complete"<Br/>
触发onreadystatechange事件，然后会触发onload事件(6~22ms).
**FF**<br/>
onload延时为3~7ms，不会对资源进行缓存。<br/>
## 空字符串
````
<link type="text/css" rel="stylesheet" href=""/>
````
**webkit**<br/>
不发起请求，也不会触发onload和onerror事件<br/>
**IE**<br/>
不发起请求<br/>
IE11:<br/>
触发onload事件(延时0~1ms)<Br/>
IE9~10:<br/>
首次加载，仅会触发onreadystatechange事件，且仅从"unitialized"->"loading"<br/>
后续加载，先触发两次onreadystatechange事件，且从"unitialized"->"complete"，然后再触发onload事件<br/>
IE5~8:<br/>
首次加载，仅会触发onreadystatechange事件，且仅从"unitialized"->"loading"<br/>
后续加载，先触发一次onreadystatechange事件，且从"unitialized"->"complete"，然后再触发onload事件<br/>
**FF**<br/>
不发起请求，也不会触发onload和onerror事件<br/>
## 空白字符
````
<link type="text/css" rel="stylesheet" href="    "/>
````
**webkit**<br/>
不发起请求，也不会触发onload和onerror事件<br/>
**IE**<br/>
发起请求<Br/>
IE11:<br/>
触发onload事件(延时5~22ms)，不会缓存资源<br/>
IE9~10:<Br/>
触发一次onreadystatechange事件，"uninitialized"->"complete"。然后在触发onload事件。不会缓存资源<Br/>
IE5~8:<Br/>
首次加载会发起请求，会触发两次onreadystatechange事件，"uninitialized"->"loading"->"complete"。然后在触发onload事件<br/>
后续加载会从缓存读取资源，触发一次onreadystatechange事件，"uninitialized"->"complete"。然后在触发onload事件<Br/>
**FF**<br/>
onerror延时为3~7ms，不会对资源进行缓存。<br/>
## javascript:void 0
````
<link type="text/css" rel="stylesheet" href="javascript:void 0"/>
````
**webkit**<br/>
发起网络请求，但浏览器会取消该请求。触发onerror事件，延时为0~8ms<Br/>
**IE**<br/>
IE11:<br/>
不会触发onload、onerror事件。<br/>
IE8~10:<br/>
会触发一次onreadystatechage事件(2~4ms)，"uninitialized"->"loading"<br/>
IE5~7:<Br/>
每次加载均会在设置`link.href="javascript:void 0";`时会报“无法设置href属性。已中止操作”的异常，但后面依然会触发一次onreadystatechage事件(2~4ms)，"uninitialized"->"loading"<br/>
**FF**<br/>
发起网络请求，但浏览器会取消该请求。触发onerror事件，延时为0~3ms<Br/>
## //:0
````
<link type="text/css" rel="stylesheet" href="//:0"/>
````
**webkit**<br/>
不发起网络请求，不触发onload和onerror事件<Br/>
**IE**<br/>
发起网络请求, 但浏览器会取消该请求<br/>
IE11:<br/>
触发onload事件，延时为0~1ms
IE9~10:<br/>
触发两次onreadystatechange事件(延时0~2ms, 5~8ms)，readyState均为"complete"，然后在触发onload事件(延时5~9ms)
IE5~8:<br/>
触发一次onreadystatechange事件（延时3ms），readyState为"complete"，然后在触发onload事件(延时5~8ms)
**FF**<br/>
发起请求，触发onerror事件，延时1~4ms<br/>
## :0
````
<link type="text/css" rel="stylesheet" href=":0"/>
````
**webkit**<br/>
发起网络请求，返回404 HTTP CODE<br/>
触发onerror事件，延时6~300+ms<br/>
**IE**<br/>
发起网络请求，且返回404HTTP状态码<Br/>
IE11:<Br/>
触发onload事件，延时为10ms左右<Br/>
IE5~10:<Br/>
触发两次onreadystatechange事件(延时0~2ms, 10~12ms)，"unintialized"->"loading"->"complete"，然后在触发onload事件(延时11~13ms)<Br/>
**FF**<br/>
发起网络请求，返回404 HTTP CODE<br/>
触发onerror事件，延时13~30ms<br/>
## data:image/png,foo
````
<link type="text/css" rel="stylesheet" href="data:image/png,foo"/>
````
**webkit**<br/>
不会触发网络请求<br/>
触发onload事件，延时为0~7ms<br/>
**IE**<br/>
不会触发网络请求<br/>
IE11:<br/>
触发onload事件，延时为0~1ms<br/>
IE9~10:<Br/>
触发两次onreadystatechange事件(延时1~2ms, 5~8ms)，readyState均为"complete"，然后在触发onload事件(延时5~9ms)
IE5~8:<br/>
触发一次onreadystatechange事件（延时3ms），readyState为"complete"，然后在触发onload事件(延时5~8ms)
**FF**<br/>
不会触发网络请求<br/>
触发onerror事件，延时为0~2ms<br/>

