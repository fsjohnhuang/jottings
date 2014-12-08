#图片加载
## 加载有效路径（图片大小为12KB）和无效路径
````
<img title="fsjohnhuang.png"/>
<img title=":0"/>
````
**webkit**<Br/>
将自动更具当前url补全，若路径为http://localhost:9000/test.html则自动补全为http://localhost:9000/fsjohnhuang.png；若路径为http://localhost:9000/test.html则自动补全为http://localhost:9000/test.html/fsjohnhuang.png<Br/>
onload事件处理函数的首次触发延时为2~5ms，后面请求同一资源会读取缓存，因此延时仅为0~1ms。<br/>
onerror事件处理函数的触发延时为5ms,而有时会出现300ms以上。<br/>
**IE**<br/>
onload事件处理函数的首次触发延时为3~9ms，若请求频率越高触发延时越高。后面请求同一资源时，由于是直接读取缓存，因此延时仅为0~1ms。<br/>
onerror事件处理函数的首次触发延时为16ms左右,后面会直接读取缓存，因此延时仅为0~1ms。<br/>
onreadystatechange事件仅在加载成功时触发，并且发生在onload事件后，延时为2~3ms。<br/>
**FF**<br/>
onload事件处理函数的首次触发延时为4~10ms，后面请求同一资源会读取缓存，因此延时仅为0~1ms。
onerror事件处理函数的触发延时为16ms~60ms之间。
## 加载无效路径`javascript:void 0`
````
<img src="javascript:void 0"/>
````
**webkit**<Br/>
会发起请求但会比浏览器取消掉，因此请求不会到达服务器。但img元素会显示加载失败的图片<Br/>
onerror事件处理函数延迟1ms~2ms。<br/>
**IE**<br/>
直接设置`img.src="javascript:void 0"`会抛“拒绝访问”异常。其他方式设置则不会抛出该异常。<br/>
不会触发onload和onerror事件。<br/>
**FF**<br/>
会发起请求但会比浏览器取消掉，因此请求不会到达服务器。但img元素会显示加载失败的图片<Br/>
onerror事件处理函数延迟1ms~2ms。<br/>
## 加载无效路径`空字符串`
````
<img src=""/>
<img src="     "/>
````
**webkit**<Br/>
不会发起请求。img元素不显示。两种形式均不显示img元素<br/>
onerror事件处理函数的延时为0~1ms<br/>
**IE**<Br/>
对于空字符串，onerror事件处理函数的延时为0~1ms,且不显示img元素;对于空格字符串，onerror事件处理函数的首次延时为14ms左右，后续的延时则为0~1ms,显示img元素加载失败的状态。<br/>
**FF**<br/>
两种情况均显示img元素，且显示加载失败的状态。<br/>
对于空字符串，不会触发onload和onerror事件；对于空格字符串，onerror事件处理函数的触发延时为16ms~60ms之间<Br/>
## 加载无效路径`//:0`
参考：http://stackoverflow.com/questions/5775469/whats-the-valid-way-to-include-an-image-with-no-src<br/>
````
<img src="//:0"/>
````
**webkit**<Br/>
不会发起请求。img元素不显示<Br/>
onerror事件处理函数的延时为0~1ms<br/>
**IE**<br/>
会发起请求，但会比浏览器取消掉，因此请求不会到达服务器。但img元素会显示加载失败的图片<br/>
onerror事件处理函数的延时为0~1ms<br/>
**FF**<br/>
不会发起请求。但img元素会显示加载失败的图片<Br/>
onerror事件处理函数的延时为0~1ms<br/>
## 加载无效路径`data:image/png,foo`
````
<img src="data:image/png,foo"/>
````
**webkit**<Br/>
不会发起请求,但img元素会显示加载失败的图片<br/>
onerror事件处理函数的延时为0~1ms<br/>
**IE9+**<br/>
IE11下将文档模式设置为5~8，依然可以正确解析Data URI Scheme<br/>
不会发起请求,但img元素会显示加载失败的图片<br/>
onerror事件处理函数的延时为0~1ms<br/>
**IE5~8**<br/>

**FF**<br/>
不会发起请求,但img元素会显示加载失败的图片<br/>
onerror事件处理函数的延时为1~2ms<br/>
