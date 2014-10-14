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
 其中的`getInitParameter方法`，用于配置在web.xml中的初始化参数,在init初生效，若在Servlet的构造函数中调用将报错。若在init方法中抛ServletException异常，则表示获取初始化参数失败<br/>
````
// 初始化参数的定义
<servlet>
<servlet-name></servlet-name>
<servlet-class></servlet-class>
<init-param>
  <param-name>初始化参数名</param-name>
  <param-value>初始化参数值</param-value>
</init-param>
</servlet>
````
1. 加载和实例化<br/>
默认情况下Servlet实例都是按需加载的，Servlet容器根据请求从本地文件系统、远程文件系统或网络服务中通过类加载器加载Servlet类，然后通过反射调用默认不带参数的构造函数实例一个Servlet实例（因此不提供带参数的构造函数）<br/>
2. 初始化，调用init方法<br/>
3. 请求处理，调用service方法<br/>
4. 销毁，调用destroy方法，注意Servlet实例会在容器中保留一段时间，就是多个请求会共用一个Servlet实例<br/>
`ServletContext`, Web应用上下文<Br/>
 通过`ServletConfig.getServletContext()`或`GenericServlet.getServletContext()`获取，存放Servlet容器信息。同一Web应用的所有Servlet共用一个ServletContext实例<br/>
方法：<br/>
````
// 获取Web应用程序的上下文根，如应用在$CATALINA/webapps/test下，那么就返回/test;如果部署在root下，则返回空字符串
public String getContextPath()

// 获取web.xml中<context-param></context-param>的值
public String getInitParameter(String name)

// 对指定路径(以/开始，且相对于Web上下文根)上的资源封装为RequestDispatcher对象。而RequestDispatcher对象可以将一个请求转发给其他资源处理，或在响应中包含资源。?
public RequestDispatcher getRequestDispatcher(String path)

// 将相对于Web上下文根的路径转换为文件系统的真实绝对路径
public String getRealPath(String path)

// 返回包含元信息（内容长度、内容类型等）资源的URL?
public java.net.URL getResource(String path) throws java.net.MalformedURLException

// 返回不含元信息的资源二进制输入流
public java.io.InputStream getResourceAsStream(String path)
````
`ServletRequest接口`
`ServletResponse接口`

`web.xml`的url-pattern是以Web应用程序的上下文根作为起始，并且大小写敏感。<br/>
Servlet2.4之前使用DTD定义web.xml文档结构，因此元素间的次序是有规定的。<br/>
````
<!DOCTYPE web-app
  PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
  "http://java.sun.com/dtd/web-app_2_3.dtd">
````
Servlet2.4开始使用Schema定义web.xml文档结构，元素间的次序不再固定。<Br/>
````
<web-app version="2.5"
  xmlns="http://java.sum.com/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLoaction="http://java.sun.com/xml/ns/javaee
  http://java.sum.com/xml/ns/javaee/web-app_2_5.xsd">
````

HttpServletResponse.sendRedirect(String url)`:用于资源重定向<br/>
`HttpServletResponse.sendError(int status, String msg)`:用于返回异常的响应状态码（HttpServletResponse下有很多响应状态码）<Br/>

**Servlet的异常**<br/>
`javax.servlet`下定义了`ServletException`和`UnavaliableException`<br/>
`ServletException`可以被`init`,`service`和`doXXX`方法抛出。这时返回给客户端的是HTTP 404(请求的资源不可用)<br/>
`UnavaliableException`是`ServletException`的子类，表示抛出该异常的Servlet暂时或永久不可用。这时返回给客户端的是HTTP 503(服务器暂时忙，不能处理请求)<br/>

**请求转发（RequestDispatcher）**<br/>
`RequestDispatcher`对象用于封装一个由路由所标识的服务器资源（Servlet、JSP、静态HTML）,然后将请求转发给该资源处理，或包含该资源的处理结果到当前输出流。<br/>
方法：<br/>
````
// 将请求转发给该资源处理并由改资源响应客户端，当前在响应缓存中未提交的内容将被自动清除
public void forward(ServletRequest req, ServletResponse res)

// 将请求转发给该资源处理，该资源的处理结果会追加到响应缓存中，而当前在响应缓存中未提交的内容将被保留，而且由当前资源响应客户端
public void include(ServletRequest req, ServletResponse res)
````
获取方式<br/>
除了通过`ServletContext`获取外，还可以通过`ServletRequest.getRequestDispatcher(String path)`获取，而且该方法的path入参除了可以相对Web上下文根外，还可以相对当前Servlet路径。<br/>

## 部署
**可部署的目录结构**<br/>
````
/
|-- META-INF
    	|-- MANIFEST.MF
        |-- context.xml
|-- WEB-INF
        |-- web.xml
        |-- lib
        |-- classes
|-- jsp,html等资源

````
**1. 配置任意目录下的Web应用程序**<br/>
通过配置虚拟目录（虚拟目录可以映射到任意的物理目录）达成效果。
[a]. 在$CATALINA/conf/server.xml的Host节点下添加Context节点。<br/>
````
<Host>
  <Context 
	path="虚拟目录, 空字符串表示为默认的Web应用上下文根" 
	docBase="物理目录或war包路径，当为相对路径时则以Host的appBase属性值作为基准" 
	reloadable="true时，tomcat运行时一旦WEB-INF/classes和WEB-INF/lib发生变化则重新加载Web应用，但会增加运行时开销，因此生产环境应设置为false"
	className="指定实现了org.apache.catalina.Context接口的类名，不设置时默认为org.apache.catalina.core.StandardContext"
	cookies="指示Cookie应用于Session, 默认值为true"
	crossContext="指示是否可以通过ServletContext.getContext(String path)获取同一主机的其他Web应用的Web上下文对象，为确保安全默认为false，则上述方法返回null"
	unpackWAR="指示Tomcat在运行Web应用前是否展开Web应用，默认是true"
  />
</Host>
````
由于server.xml文件是全局配置文件，因此Tomcat仅在启动时加载一次，因此在Tomcat运行时对该文件的修改需要重启Tomcat才能生效。不建议这种部署方式。<Br/>
[b]. 添加$CATALINA/conf/[enginename]/[hostname]/应用上下文根.xml<br/>
上述路径对大小写敏感，Tomcat5.5开始以xml文件名为应用上下文根，以下版本需要在Context节点添加path属性<Br/>
````
<?xml version="1.0" encoding="utf-8"?>
<Context docBase="物理目录或war包路径，当为相对路径时则以Host的appBase属性值作为基准">
</Context>
````
**2. WAR文件**<Br/>
JAR文件的目录是将类和相关资源压缩为归档文件；WAR文件是将整个Web应用压缩为归档文件。<br/>




**`Enumeration`类**<br/>
`hasMoreElements()`：判断是否还有元素未读取。<br/>
