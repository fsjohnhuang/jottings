### 请求长度过长 ###
1. IIS7和IIS7.5的请求头长度超过16K（默认值），就会引发`Bad Request - Request Too Long.HTTP Error 400.The size of the request headers is too long.`的错误
	解决办法：
	1. 修改注册表的`HKEY_LOCAL_MACHINE\System\CurrentControlSet\Services\HTTP\Parameters`的`MaxFieldLength(请求头的长度)`和`MaxRequestBytes（请求头和请求体的总长度）`键值。
	2.  重启HTTP服务和iis服务
```cmd
	/* 方式一：比较麻烦 */
	net stop http
	net start http
	net stop iisadmin /y
	net start iis各种服务

	/* 方式二 */
	net stop http
	net start http
	iisreset
```
2. 配置web.config或machine.config(路径：%windir%/Microsoft.Net/Framework/版本号/CONFIG/machine.config)的最大请求长度，`maxRequestLength`单位是KB，默认值为4096KB（4M）
```xml
	<?xml version="1.0" encoding="UTF-8"?>
	<configuration>
		<system.web>
			<httpRuntime maxRequestLength="5"/>
		</sytem.web>
	</configuration>
```


### 请求超时 ###
&emsp;请求超时会抛出`HttpException(0x80004005):Request timed out.`异常。
1. 解决办法：
&emsp;通过配置网站的web.config设置更长的请求时间来处理。executionTimeout值单位为秒，默认是110。
```xml
	<?xml version="1.0" encoding="UTF-8"?>
	<configuration>
		<system.web>
			<httpRuntime executionTimeout="5"/>
		</sytem.web>
	</configuration>
```
2. 排查办法：
	1. 进入管理工具>事件查看器>windows日志>应用程序，查看来源为Asp.Net 版本号的警告日志记录；
