## Servlet容器
**Servlet容器分类：**<br/>
1. Web服务器进程内的Servlet容器, 由Web服务器插件内部启动Servlet容器，并使用JNI技术使两者通信，适合单进程多线程服务；<br/>
2. Web服务器进程内的Servlet容器，由Web服务器插件通过IPC机制（一般用TCP/IP）与Servlet容器通信，易与横向伸缩；<br/>

**Tomcat**<br/>
1. 目录结构<br/>
`/bin`, 存放启动和关闭Tomcat的脚本文件<br/>
`/conf`, 存放Tomcat的配置文件，包括server.xml(Tomcat的主要配置文件)、tomcat-users.xml和web.xml等<br/>
`/lib`, 存放Tomcat服务器和所有Web应用程序所需的JAR<br/>
`logs`, Tomcat日志文件<br/>
`temp`, 存放Tomcat运行时产生的临时文件<br/>
`webapps`, 应用发布的在该目录下<br/>
其下的每个子目录就是一个独立的Web应用程序。<br/>
ROOT子目录是Tomcat默认的Web应用程序目录，可直接通过`http:localhost:8080/`访问。<br/>
每个子目录下的目录结构：<br/>
WEB-INF, 存放Web应用程序的部署描述符文件web.xml，该目录是用户无法访问的;但对于程序可以通过`ServletContext`的`getResource()`或`getResourceAsStream()`来访问WEB-INF下的资源，也可以通过`RequestDispatcher`将WEB-INF下的内容呈现给用户<br/>
WEB-INF\classes, 存放Servlet和其他有用的类文件<br/>
WEB-INF\lib, 存放依赖的JAR<br/>
WEB-INF\web.xml,包含Web应用程序的配置和部署信息。<br/>
而jsp等文件就与WEB-INF目录同级<br/>
Web应用程序的类加载器首先加载classes目录下的文件，其次才是lib下的，因此若两个目录下由同名的类，则起作用的时classes下的类。<br/>
`work`, Tomcat将JSP生成的Servlet源文件和字节码文件放在这里<br/>
2. Tomcat组成部分<br/>
Server,就是Catalina Servlet容器<br/>
Service,Server内部的中间组件，一个Server可包含多个Service，而Service就是将多个连接器（Connector）绑定到一个单独的引擎（Engine）上。<Br/>
Connector,负责接受用户请求和向用户返回相应结果。<Br/>
Engine,每个Service仅包含一个Engine，引擎表示一个特定的Service请求处理流水线，连接器接收用户请求后将其交给Engine，Engine处理完后再返回结果给连接器。<br/>
Host,表示一个虚拟主机，一个引擎可以包含多个Host。<br/>
Context，表示一个Web应用程序，运行在Host上。一个Host上可以包含多个Context。<br/>
3. 进入GUI管理界面<br/>
需要按提示在$CATALINA/conf/tomcat-users.xml中添加`role标签`和`user标签`<br/>

## Servlet
`java.lang.RuntimeException`及其子类(如java.lang.IllegalStateException)是由JVM自动抛出和自行处理，所以不用我们手工处理或抛出。<br/>
1. **Servlet相关的类**<br/>
`Servlet接口`<br/>
 生命周期为：init,service,destroy<br/>
`ServletConfig接口`<br/>
 Web应用程序的配置信息<br/>
 其中的`getInitParameter方法`，用于配置在web.xml中的初始化参数。<br/>
`ServletContext`<Br/>
 通过`ServletConfig`获取，存放Servlet容器信息。<br/>
`ServletRequest接口`
`ServletResponse接口`

`web.xml`的url-pattern是以Web应用程序的上下文根作为起始，并且大小写敏感。<br/>
