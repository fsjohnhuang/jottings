## 书籍
>1. O'Reilly - HTTP Pocket Reference：这是一本比较简短的介绍HTTP协议的书，可以作为入门读物<br/>
>2. O'Reilly - HTTP The Definitive Guide：这是一本宝典级别的书，因为它包含的内容实在多，可以作为全面学习的HTTP协议的首选读物<br/>
>3. Sams - HTTP Developers Handbook：这是比HTTP The Definitive Guide稍微比HTTP The Definitive Guide简单。不过从我的感觉，这本书比HTTP The Definitive Guide要好，因为它篇幅比较少，介绍的是HTTP精髓，我认为这本书应该是web程序员的首选读物<br/>

## 请求格式
>HTTP请求行<br/>
>请求头<br/>
>空行<br/>
>可选消息体<br/>

![请求格式](./http.d/reqMsg.png)

__注意__：<br/>
1. 各部分必须以`<CR><LF>`作为结尾（就是回车换行符）；<br/>
2. 空行仅含`<CR><LF>`；<br/>
3. 在HTTP/1.1协议中，请求头中除Host外，其他均为可选。<br/>

示例：

	GET /index.aspx?msg=helloworld HTTP/1.1<CR><LF>
	Host:fsjohnhuang.cnblogs.com<CR><LF>
	User-Agent:Mozilla/5.0<CR><LF>
	Accept:*/*<CR><LF>
	Accept-Language:en-us,en;q=0.5<CR><LF>
	Accept-Encoding:gzip,deflate<CR><LF>
	Accept-Charset:utf-8<CR><LF>
	Keep-Alive:300<CR><LF>
	<CR><LF>

## 响应格式
>HTTP状态行<br/>
>响应头<br/>
>空行<br/>
>可选的消息体<br/>

示例：

	HTTP/1.1 200 OK<CR><LF>
	Cache-Control:private,max-age=30<CR><LF>
	Content-Type:text/html;charset=utf-8<CR><LF>
	Content-Length:12341<CR><LF>
	<CR><LF>
	消息体

![响应示例](./http.d/rspMsg.jpg)


## 上传文件时的请求报文
	POST / HTTP/1.1<CR><LF>
	Host: fsjohnhuang.cnblogs.com<CR><LF>
	Content-Type: multipart/form-data; boundary=----Abcdefg<CR><LF>
	Content-Length: 1294
	Connection: keep-alive<CR><LF>
	<CR><LF>
	----Abcdefg<CR><LF>
	Content-Disposition: form-data; name="txtName"<CR><LF>
	<CR><LF>
	fsjohnhuang<CR><LF>
	----Abcdefg<CR><LF>
	Content-Disposition: form-data; name="file"; filename="upload.txt"<CR><LF>
	Content-Type: application/octet-stream<CR><LF>
	<CR><LF>
	文件的二进制数据<CR><LF>
	----Abcdefg<CR><LF>


__注意：__<br/>
1. `Content-Type`用于设置MIME，multipart是大类，form-data是小类<br/>
2. `boundary`用于标识分割不同filed的边界，其值是随机生成的<br/>
3. `Content-Disposition`是MIME协议的扩展，用于设置文件的处理方式（下载、在当前网页上打开）。值为inline、attachment、form-data<br/>
4. `application/octet-stream`表示任意的二进制数据<br/>
5. `Content-Length`表示整个消息体字节数

## 不含文件上传的表单提交
	POST / HTTP/1.1<CR><LF>
	Host: localhost:53355<CR><LF>
	Connection: keep-alive<CR><LF>
	Content-Length: 254<CR><LF>
	Cache-Control: max-age=0<CR><LF>
	Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8<CR><LF>
	Origin: http://localhost:53355<CR><LF>
	User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36<CR><LF>
	Content-Type: application/x-www-form-urlencoded<CR><LF>
	Referer: http://localhost:53355/<CR><LF>
	Accept-Encoding: gzip,deflate,sdch<CR><LF>
	Accept-Language: zh-CN,zh;q=0.8,en;q=0.6<CR><LF>
	<CR><LF>
	__VIEWSTATE=%2FwEPDwULLTE1MDUxNDI4MjRkZFH4baIyUptiw7TvIIJzeKxOZRz1rqFK%2BRWTfmokhxY%2F&__EVENTVALIDATION=%2FwEdAANCICmcUc84%2Buo4wSo1bd7dwjP1kt%2BseU12iVakwziMpc2EIlRoRDoXrxwdHSKZMyrrVwsDZGTTGIGSGxEHAhAbnbOpmPrsh4LHtOcTI6P2iA%3D%3D&txtCmd=test&txtStr=str


## 请求方式
1. HTTP/1.0<br/>
  [a]. GET：请求指定资源，并返回实体主题<br/>
  [b]. POST：向指定资源提交数据并进行处理请求（如提交表单和上传文件），数据被包含在请求体中。POST请求会导致新资源的建立或已有资源被修改。<br/>
  [c]. HEAD：与GET类似，但仅返回响应报头<br/>
2. HTTP/1.1<br/>
  [a]. PUT：向HTTP Server传送数据取代指定的资源数据<br/>
  [b]. DELETE：请求服务器删除指定的资源<br/>
  [c]. CONNECT：HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器<br/>
  [d]. OPTIONS：允许客户端查看服务器性能<br/>
  [e]. TRACE：回显服务器收到的请求，主要用于测试或诊断<br/>
  


## 请求头类型
1. Client
2. Transport
3. Miscellaneous
4. Cache
5. Entity

通用头：<br/>
`Connection`：仅在HTTP/1.1下有效，告诉HTTP Server完成本次请求的响应后，是马上断开连接还是等待本次连接的后续请求。close表示断开，keepalive表示保持连接。响应头中的`Connection: close`表示链接已断开，`Connection: keepalive`表示等待本次连接的后续请求<br/>
`Date`：表示请求消息和响应消息被创建的时间，使用GMT时间，如Date: Tue, 15 Nov 2007 08:12:31 GMT


请求头：<br/>
`Host`：用于指定客户端所访问的资源所在的主机名和端口号。<br/>
`Keep-Alive`：配置`Connection: keepalive`使用，用于设置保持链接的时间（单位：秒）<br/>
`Range`：客户端通知服务器传输一部分Web资源（用于断点续传）。<br/>
共有三种格式：<br/>
[a]. `Range: bytes=1000-2000`：传输范围从第1000到第2000字节<br/>
[b]. `Range: bytes=1000-`：传输从第1000字节以后的所有数据<br/>
[c]. `Range: bytes=1000`：传输最后的1000个字节<br/>
`If-Modified-Since`：其值为上次响应头的`Last-Modified`的字段值，用于告诉服务器上次请求时，Web资源的修改时间，若服务器检测到该Web资源被修改过，或缓存超时则重新返回该Web资源的完整响应消息;否则仅返回HTTP状态码为304的响应头。浏览器接收到304的HTTP状态码后，会读取本地缓存获取Web资源。格式为GMT时间(如：Fri,12 May 2006 18:53:33 GMT)。
<font style="color:red;">注意：nignx中是只要If-MOdified-Since的值与Web资源的当前修改时间不同则返回完整的响应消息，而apache是Web资源的当前修改时间晚于If-Modified-Since时间才返回完整的响应消息</font>
`If-None-Match`：返回最近一次响应消息的`ETag`响应头字段值，如（W/"50b1c1d4f775c61:dt"）

响应头：<br/>
`Accept-Ranges`：HTTP Server表明自己是否接受获取某个实体的一部分的请求（即断点续传功能）。bytes表示接受，none表示不接受；<br/>
`Content-Range`：表示当前会话中的实体为所请求的资源的字节范围，其值格式是 **bytes 开始字节位置-结束字节位置/资源的总字节数**<br/>
示例：

	// 请求报文
	GET /test.txt HTTP/1.1<CR><LF>
	Host: fsjohnhuang.cnblogs.com<CR><LF>
	Range: bytes=1000-<CR><LF>
	<CR><LF>

	// 响应报文
	HTTP/1.1 206 Partial<CR><LF>
	Content-Type: application/octet-stream<CR><LF>
	Content-Range: bytes 1000-3759/3760<CR><LF>
	Accept-Ranges: bytes<CR><LF>
	Content-Length: 2760<CR><LF>
	<CR><LF>
	文件二进制数据

`Location`：当Web资源更换URL后，当客户端请求旧URL时，通过Location通知客户端从新URL获取Web资源。<br/>
`Content-Encoding`：HTTP Server表示使用哪种压缩方式（gzip，deflate）压缩响应中的消息体。<br/>
`Last-Modified`：当服务器返回一个完整的响应消息时，通过该响应头告诉当前Web资源的最后更新时间以便缓存Web资源。格式为GMT时间。
`ETag`:服务器用于与Web资源版本关联的记号,若请求头的`If-None-Match`头与该Web资源当前的记号不同则返回完整的响应消息，否则返回304HTTP状态码（如，50b1c1d4f775c61:df3）
