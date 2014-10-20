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

##web.xml
1.**servlet节点的子节点**<br/>
`description`:0~N个，Servlet的文本描述<br/>
`display-name`:0~N个，Servlet的短名称<br/>
`icon`:0~N个，Servlet的图标<br/>
`servlet-name`:1个，Servlet的唯一标识<Br/>
`servlet-class`:1个，Servlet的全限定类名，与`jsp-file`节点排他出现<br/>
`jsp-file`:1个，JSP页面的完整路径，路径以/开始，与`servlet-class`节点排他出现<br/>
`init-param`:0~N个，定义Servlet初始化参数，内部必须包含`param-name`和`param-value`节点，可包含0~N个`description`节点<br/>
`load-on-startup`:0~1个，用于设置servlet的加载次序，小于0则客户端请求时才加载，0表示Web应用启动时加载，大于0则按照数值越小越早加载的次序加载Servlet。<Br/>
`run-as`:0~1个，指定启动web应用的用户角色，内部必须包含1个`role-name`节点用于指向`security-role-ref`下的`role-name`节点值，可以包含0~N个`description`节点<br/>
`security-role-ref`:声明Web应用的安全角色引用。内部必须包含`role-name`和`role-link`节点，可以包含0~N个`description`节点。<Br/>

2.**servlet-mapping节点**<br/>
匹配规则，当匹配成功则不再往后匹配：<br/>
1. 精确匹配<br/>
2. 最长的路径前缀匹配<br/>
3. 如果URL含扩展名，则匹配该扩展名的Servlet<br/>
4. 采用默认的Serlvet处理，若没有定义默认Servlet，则Serlvet容器会返回HTTP 404错误信息<br/>
示例：<br/>
````
<!-- 配置默认Servlet -->
<serlvet-mapping>
  <servlet-name>index</servlet-name>
  <url-pattern>/</url-pattern>
</serlvet-mapping>

<!-- 精确匹配 -->
<serlvet-mapping>
  <servlet-name>extactly</servlet-name>
  <url-pattern>/hello/index</url-pattern>
</serlvet-mapping>

<!-- 最长路径前缀匹配 -->
<serlvet-mapping>
  <servlet-name>maxprefix</servlet-name>
  <url-pattern>/hello/admin/*</url-pattern>
</serlvet-mapping>

<!-- 扩张名匹配 -->
<serlvet-mapping>
  <servlet-name>extsion</servlet-name>
  <url-pattern>*.do</url-pattern>
</serlvet-mapping>
````

## 会话跟踪技术
**1. SSL会话(Secure Socket Layer)**<Br/>
在TCP/IP和HTTP等应用层之间的加密技术，应用于HTTPS协议的加密技术。服务端与客户端采用对称密钥对通信消息进行加密、解密，而密钥本身是通过非对称密钥进行传输。<br/>
**2. Cookies**<Br/>
请求报文头`Cookie`<Br/>
````
Cookie:<name>=<value>[;<name>=<value>]*
````
响应报文头`Set-Cookie`<br/>
````
Set-Cookie: <name>=<value>[;<name>=<value>]* [;comment=<value>] [;domain=<value>] [;path=<value>] [;secure] [;version=<value>] [;expires=<value>][;httponly]
````
`;commnet=<value>`, 用于告知客户端该cookie的使用说明<Br/>
`;domain=<value>`, 用于指定Cookie在哪个域有效，值必须以半角句号(.)开头，默认值是当前域<br/>
`;path=<value>`, 用于指定Cookie在域的基础上，在哪个URL路径下有效，默认值为/，表示根路径下均有效。<br/>
`;secure`, 规定是否通过HTTPS连接来传输Cookie, 若不是通过HTTPS连接传输，则浏览器会忽略该Cookie<br/>
`;version=<value>`, 用于标识Cookie依照的状态管理规范的版本，1对应RFC 2109规范;0对应Netscape规范<br/>
`;expires=<value>`, 用于指定Cookie的有效期，若当前时间迟于该有效期，则Cookie无效。该有效期采用GMT格式（js中采用日期对象.toGMTString()获取）<br/>
`;httponly`, 规定
**注意：用于会话跟踪的Cookie名字必须为JSESSIONID。**<Br/>
**3. URL重写**<Br/>
就是将Cookie中JSESSIONID的值追加到Web应用的URL后。<Br/>
````
httpp://www.sunxin.org/test.jsp;jsessionid=1234?name=johnhuang&key=12
````
**API**<br/>
`javax.servlet.http.HttpSession`接口<br/>
通过`HttpServletRequest.getSession()`获取`HttpSession`对象<br/>
通过`HttpSession.setAttribute`方法就可以设置会话状态键值对，而且会自动将JSESSIONID附加到响应报文头的Set-Cookie字段中。若要添加到URL中，则需要采用`HttpServletResponse.encodeURL(源url)`或`HttpServletResponse.encodeRedirectURL(源url)`处理。<br/>
**Session持久化**<br/>
由Web应用服务器提供服务。而`HttpSession`及其内部数据类型均要实现`Serializable`接口。

## Cookie
`javax.servlet.http.Cookie`类<br/>
示例：<br/>
````
// 添加cookie
Cookie cookie = new Cookie(String 键, String 值);
response.addCookie(cookie);

// 获取cookie
Cookie[] cookies = response.getCookies();
for (Cookie cookie : cookies){
  String name = cookie.getName();
}
````

## Servlet异常处理
**1. 声明式异常处理**<br/>
由Servlet容器作处理，我们无法得知发生异常的具体位置。<br/>
通过web.xml配置文件，设置对HTTP错误代码处理和程序中产生的Java异常处理。<br/>
````
<!-- 对HTTP错误代码处理 -->
<error-page>
  <error-code>404</error-code>
  <location>相对于Web上下文根的路径，必须以/开头</location>
</error-page>

<!-- 对程序中产生的Java异常处理 -->
<error-page>
  <exception-type>javax.servlet.ServletException</exception-type>
  <location>相对于Web上下文根的路径，必须以/开头</location>
</error-page>

<!--
  location指定的处理页面可以通过HttpServletRequest.getAttribute(String key)获取异常信息。key分别为：
  javax.servlet.error.status_code int HTTP状态码
  javax.servlet.error.exception_type Class 未捕获异常的Class类的对象
  javax.servlet.error.message String 通过sendError()传递的消息，或未捕获异常的消息
  javax.servlet.error.exception Throwable 未捕获的异常
  javax.servlet.error.request_uri String 当前请求的URI
  javax.servlet.error.servlet_name String 异常发生的Servlet名字
-->
````
服务器出现未捕获的异常时，均返回HTTP 500，因此可通过配置HTTP 500错误代码处理来统一处理异常，并通过`HttpServletRequest.getAttribute`获取异常发生的位置和调用栈信息。<br/>
**2. 程序式异常处理**<br/>
通过`try...catch`自由处理。并通过`RequestDispatcher.forward`转向统一的异常处理页面作处理。<br/>
需要手动封装异常信息到`HttpServletRequest`上再由统一的异常处理页面提取并记录。<br/>

## JSP
请求时才对JSP进行编译、加载、运行。<br/>
JSP由元素(指令元素、脚本元素和动作元素)和模板数据(非JSP处理的部分)组成<br/>
**1. 指令元素（directive element）**<br/>
用于为转换阶段提供整个JSP页面的相关信息，不会产生任何的输出到当前的输出流中。<br/>
[a]. **page指令**<br/>
定义与页面相关的属性，用于和JSP容器通信。一个JSP页面可以出现N个page指令<br/>
````
<!--指定脚本元素的所用的语言,只能是java-->
<%@ page language="java"%>

<!--引入类,脚本元素中就可以使用该类了-->
<%@ page import="java.util.*"%>

<!--指定JSP页面能否使用session对象-->
<%@ page session="true"%>

<!--指定输出缓存区容量，默认是8kb(kb是唯一可用的容量单位)，若设置为none则不使用缓存-->
<%@ page buffer="8kb"%>

<!--指定输出缓存区满后是否自动刷新，默认值true，若设置为false，当缓存区满时就会报错-->
<%@ page autoFlush="true"%>

<!--指定JSP页面是否线程安全，若为true(默认值)则多个线程可同时访问该JSP页面；若为false则JSP生成的Servlet将继承SingleThreadModel接口，同一个时间仅能单个线程操作该JSP页面-->
<%@ page isThreadSafe="true"%>

<!--指定JSP页面的页面信息，作为getServletInfo()方法的返回值-->
<%@ page info="页面信息"%>

<!--指定JSP页面出错时跳转到的页面，该属性优先级高于web.xml-->
<%@ page errorPage="url"%>

<!--指定当前JSP页面是否为出错页面，默认为false-->
<%@ page isErrorPage="false"%>

<!--指定当前JSP页面MIME类型和字符编码(响应头)-->
<%@ page contentType="text/html; charset=utf-8"%>

<!--指定当前JSP页面字符编码, 优先级高于contentType的字符编码; 若两者均没有设置则采用ISO-8859-1作为字符编码-->
<%@ page pageEncoding="utf-8"%>

<!--指定当前JSP页面是否忽略EL表达式, 若web.xml使用Servlet2.3前默认值为true，之前的默认值为false-->
<%@ page isELIgnored="true|false"%>

<!--指定是否删除指令末端与模板文本间的空白，默认值为false-->
<%@ page trimDirectiveWhitespaces="true"%>
````
[b].**include指令**<br/>
用于将一个JSP页面、HTML网页、文本或java代码静态插入到当前JSP页面。<br/>
当前文件和外部资源可以相互访问变量和方法<Br/>
````
<%@ include file="外部资源相对于当前JSP的URL"%>
````
[c].**taglib指令**<br/>
用于引入用户定制的标签<br/>
````
<%@ taglib (uri="tagLibraryURI" | tagdir="tagDir") prefix="tagPrefix"%>
````
属性说明：<br/>
`uri`: 可为绝对和相对路径，用于定位标签库描述符的位置。<br/>
`tagdir`: 标识在/WEB-INF/tags/目录或其子目录下的标签文件。有三种情况会发生转换错误：1. tagdir属性值不是以/WEB-INF/tags/开始；2. tagdir属性值没有指向一个已经存在的目录；3. tagdir属性与uri属性一起使用。<Br/>
`prefix`: 定义一个prefix:tagname形式的字符串前缀，`jsp:,jspx:,java:,javax:,servlet:,sun:和sunw:`为被保留的前缀。<Br/>

**2. 脚本元素（scripting element）**<br/>
使用Java语法<br/>
[a].**声明(declaration)**<br/>
用于声明变量和方法, 作用域为当前JSP页面<Br/>
````
<%! int i = 0;%>
<%! 
  int j = 0;
  String name = "john";
%>
<%! 
  public String func(int i){ 
    return String.valueOf(i); 
  }
%>
````
[b].**脚本段(scriptlet)**<br/>
用于在请求处理期间处理逻辑。其实就是Servlet的service函数内执行的内容。<br/>
````
<%
  if (Calendar.getInstance().get(Calendar.AM_PM) == Calendar.AM){
%>
  Goode morning<br/>
<%}else{%>
  Goode afternoon<br/>
<%}%>
````
[c].**表达式(expression)**<br/>
表达试的计算结果将被转换为字符串，并插入到当前的输出流中。<br/>
注意：表达式后一定不能出现任何标点符号。<Br/>
````
现在的时间是<%=(new java.util.Date()).toLocaleString()%>
````

**3. 动作元素（action element）**<br/>
在JSP转换为Servlet时，JSP容器遇到这个标签就会用预先定义的Java代码替代该标签。标签语法遵循XML，所以是大小写敏感<br/>
**`<jsp:include>`**<br/>
用于在当前页面包含静态和动态的资源，和在Servlet中使用`javax.servlet.RequestDispatcher.include()`的效果和约束是一样的。被包含的资源不能修改响应头（Set-Cookie、HTTP状态码等）<br/>
````
<jsp:include page="relativeUri" flush="true|false(默认值)"/>
````
`<jsp:include>`和`<%@ include file="relativeUri"%>`的区别:<br/>
`<%@ include file="relativeUri"%>`是在将JSP页面编译为Servlet时，将外部资源包含到当前页面，与当前JSP作为一个整体资源被请求。<br/>
`<jsp:include>`是在请求处理时，当前Servlet动态引入外部资源到当前输出流中，两者仍然是两个资源被请求，但合成为一个输出而已。<br/>

## JSP的隐含对象
````
request HttpServletRequest
response HttpServletResponse 
pageContext javax.servlet.jsp.PageContext 
session HttpSession 
application ServletContext 
out javax.servlet.jsp.JspWriter 
config ServletConfig 
page Object
exception Throwable
````
`pageContext对象`<br/>
提供访问其他隐含对象的方法，也提供`forward`和`include`方法，是作为调用Servlet各方法的入口<br/>
通过`setAttribute(String name, Object value, int scope)`和`getAttribute(String name, int scope)`设置某范围内的属性,scope的取值返回是`PageContext.PAGE_SCOPE(页面范围),PageContext.REQUEST_SCOPE(请求范围),PageContext.SESSION_SCOPE(会话范围),PageContext.APPLICATION_SCOPE(Web应用全局)`, 当调用`pageContext.findAttribute(String name)`获取属性值时，会按照page、request、session、application的顺序搜索，若调用`getAttribute(String name)`则在page范围内获取属性值，注意，即使当前页面通过pageContext.include包含其他资源，其他资源的pageContext和该页面的pageContext是不同的<Br/>

`out对象`<Br/>
是PrinterWriter对象的带缓冲的版本。<br/>

`page对象`<Br/>
指向当前的Servlet对象<Br/>

`exception对象`<Br/>
执行JSP页面运行的异常，仅在`<%@ page isErrorPage="true"%>`的页面该对象才不为空。<br/>

## JSP与JavaBean 
JavaBean用于将UI和业务逻辑分离，可在JSP直接调用JavaBean类的对象，也可以通过动作元素调用。<br/>
**JavaBean的标准**<br/>
1. 是一个public类<Br/>
2. 提供一个默认不带参数的构造方法<Br/>
3. 提供get、set方法访问属性<Br/>
4. 实现了`java.io.Serializable`或`java.io.Externalizable`接口，以支持序列化。<br/>
**属性和实例变量**<br>
````
public class Info{
  // 实例变量
  private int age = 1;
  private int unit = 2;
 
  // 属性
  public int getHeight(){
    return age * unit;
  } 
}
````
**属性类型**<br/>
1. 简单属性(simple property)<br/>
就是接受单个值的属性<br/>
2. 索引属性(indexed property)<br/>
示例：<br/>
````
public String[] getNames(){.....}
public void setNames(){.....}
public String getNames(int index){.....}
public void setNames(int index, String name){.....}
````
3. 绑定属性(bound property)<br/>
4. 约束属性(constrained property)<br/>
**通过动作元素访问JavaBean**<br/>
1. `<jsp:useBean>`<br/>
用于实例化JavaBean或定义一个已存在的JavaBean实例。<Br/>
示例：<br/>
````
<jsp:useBean id="info" scope="page" class="com.test.Info"/>
````
语法<br/>
````
<jsp:useBean id="变量名" scope="page|request|session|application" typeSpec />
typeSpec::= class="全限定类名"|
	class="全限定类名" type="目标类型的全限定类名，默认与class一样"|
	beanName="Bean名，提供给java.beans.Beans类的instantiate()方法来实例化一个JavaBean对象" type="目标类型的全限定类名，默认与class一样"
````
2. `<jsp:setProperty>`<br/>
用于设置JavaBean的简单属性和索引属性。<Br/>
示例：<br/>
````
<!--从请求参数中自动获取与JavaBean实例属性名相同的值-->
<jsp:setProperty name="JavaBean实例变量名" property="*"/> 

<!--从请求参数中获取指定名称的参数值赋予到JavaBean实例指定属性中-->
<jsp:setProperty name="JavaBean实例变量名" property="JavaBean属性名" param="请求参数名"/> 

<!--将值赋予到JavaBean实例指定属性中-->
<jsp:setProperty name="JavaBean实例变量名" property="JavaBean属性名" value="值"/> 
````
3. `<jsp:getProperty>`<br/>
用于获取JavaBean的简单属性和索引属性，并会自动转换为String类型追加到是输出流中<br/>
````
<jsp:setProperty name="JavaBean实例变量名" property="JavaBean属性名"/>
````

## 开发模型
**1.Model1**<br/>
结合JSP和JavaBean开发，JavaBean负责数据封装和主要的业务逻辑处理，JSP负责接收用户请求并调用JavaBean的业务逻辑处理接口，然后控制UI输出。<Br/>
JSP职责过多，前端与业务逻辑耦合度大。<br/>
**2.Model2**<br/>
结合JSP、Servlet和JavaBean开发，Serlvet负责接收用户请求，然后调用JavaBean的业务逻辑接口，并对业务逻辑接口返回的数据进行二次处理，然后将数据传递给相应的JSP，JSP页面则根据数据生成UI返回给用户。JSP仅涉及UI、数据和UI的呈现逻辑，并不涉及业务逻辑。<br/>

## Servlet监听器
用于监听ServletContext、HttpSession、ServletRequest等对象的声明周期事件。<br/>
**Servlet API的8个监听器接口**<br/>
1.`javax.servlet.ServletContextListener接口`：<br/>
含`contextDestroyed方法`和`contextInitialized方法`，当Servlet上下文对象初始化和销毁（就是Web应用启动和关闭时）触发接口中对应的方法。除实现该接口外，还要在web.xml中配置信息才会生效。<br/>
可用于初始化数据连接池等<Br/>
示例：<br/>
````
package com.test;
public class MyServletContextListner implements ServletContextListener{
  @Override
  public void contextInitialized(ServletContextEvent sce){
    ServletContext sc = sce.getServletContext(); 
    sc.setAttribute("greeting", "hello world");
  }
  @Override
  public void contextDestroyed(ServletContextEvet sce){}
}
````
web.xml的配置<br/>
````
<listener>
  <listener-class>
    com.test.MyServletContextListener 
  </listener-class>
</listener>
````

2. `javax.servlet.ServletContextAttributeListener接口`<br/>
含`attributeAdded方法`,`attributeRemoved方法`和`attributeReplaced方法`，当Servlet上下文对象中的属性发生变化时得到通知。除实现该接口外，还要在web.xml中配置信息才会生效。<br/>
3. `javax.servlet.http.HttpSessionListener接口`<br/>
含`sessionCreated方法`和`sessionDestroyed方法`，在Session创建后或Session失效前发起通知。除实现该接口外，还要在web.xml中配置信息才会生效。<br/>
4. `javax.servlet.http.HttpSessionActivationListener接口`<br/>
含`sessionDidActivate方法`和`sessionWillPassivate方法`, 当session被钝化或激活时触发。无需配置web.xml。<br/>
5. `javax.servlet.http.HttpSessionAttributeListener接口`<br/>
含`attributeAdded方法`,`attributeRemoved方法`和`attributeReplaced方法`，当HttpSession对象中的属性发生变化时得到通知。无需配置web.xml。<br/>
6. `javax.servlet.http.HttpSessionBindingListener接口`<br/>
含`valueBound方法`和`valueUnbound方法`，当继承该接口的类实例添加到Session或从Session移除时会得到通知。无需配置web.xml。<br/>
7. `javax.servlet.ServletRequestListener接口`<br/>
含`requestInitialized方法`和`requestDestroyed方法`，请求对象初始化后或销毁前触发。无需配置web.xml。<br/>
8. `javax.servlet.ServletRequestAttributeListener接口`<br/>
含`attributeAdded方法`,`attributeRemoved方法`和`attributeReplaced方法`，当ServletRequest对象中的属性发生变化时得到通知。无需配置web.xml。<br/>

## Filter
Filter Chain中的各个Filter不一定必须将请求传递到下一个Filter或原目标资源，它可以自行对请求作处理并直接返回给客户端，或者转发到另一个目标资源。<br/>
应用场景：<Br/>
统一认证、记录和审核用户请求、过滤用户提交的数据、图片格式转换、压缩响应内容、加密请求和响应、触发资源访问事件、对XML的输出应用XSLT。<br/>
1. **Filter接口**<br/>
所有自定义的过滤器必须继承`javax.servlet.Filter接口`。其含如下方法：<Br/>
`public void init(FilterConfig filterConfig) throws ServletException`，Web容器调用该方法来初始化过滤器，可通过filterConfig获取ServletContext对象, 可在该方法内跑出ServletException异常来通知Web容器过滤器不能正常工作<br/>
`public void doFilter(ServletRequest request, ServletResposne response, FilterChain chain) throws ServletException, IOException`，相当于Servlet的service方法，在这个方法内直接返回响应，或通过`sendRedirect`、`RequestDispatcher.forward`等转发到其他资源，或通过`chain.doFilter(req, res)`将请求传递到下一个过滤器或Servlet处理<br/>
`public void destroy()`，用于释放资源<br/>

2. **过滤器的部署**<br/>
通过`<filter>`和`<filter-mapping>`标签来部署过滤器<br/>
**`<filter>`标签子节点：**<br/>
`description`:0~N个，Servlet的文本描述<br/>
`display-name`:0~N个，Servlet的短名称<br/>
`icon`:0~N个，Servlet的图标<br/>
`filter-name`:1个，Filter的唯一标识<Br/>
`filter-class`:1个，Filter的全限定类名<br/>
`init-param`:0~N个，定义Filter初始化参数，内部必须包含`param-name`和`param-value`节点，可包含0~N个`description`节点<br/>

**`<filter-mapping>`标签子节点：**<br/>
`filter-name`,1个，Filter的唯一标识<Br/>
`url-pattern`,0~N个，当请求对应的URL样式时，该过滤器才会被调用<br/>
`servlet-name`,0~N个，当请求对应的Servlet时，该过滤器才会被调用<br/>
`dispatcher`,0~4个，用于指定过滤器指定的请求方式，可以是REQUEST(默认值)、INCLUDE、FORWARD和ERROR四种之一。<br/>

**过滤请求参数**<br/>
无法直接修改ServletRequest对象中的请求参数，但可以通过`ServletRequestWrapper`或`HttpServletRequestWrapper`对`ServletRequest对象`进行二次封装来过滤请求参数.<br/>
自定义继承`HttpServletRequestWrapper`类<br/>
````
// Servlet中获取的HttpServletRequest对象其实就是MyRequestWrapper对象
public class MyRequestWrapper extends HttpServletRequestWrapper{
  public MyRequestWrapper(HttpServletRequest request){
    super(request);
  }

  public String getQueryString(){
    String strQry = super.getQueryString();
    // .......
    return 处理结果
  }

  public String getParameter(String name){
    String val = super.getParameter(name);
    // .......
    return 处理结果
  }
}
````
Filter对象<br/>
````
public class MyFilter implements Filter{
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException{
    chain.doFilter(new MyRequestWrapper((HttpServletRequest)req, res));
  }
}
````

**替换响应内容**<br/>
首先要理解doFilter函数内部，在调用`chain.doFilter()`前的部分请求过程的处理内容，在调用`chain.doFilter()`后的部分是响应过程的处理内容。因此请求和响应的filter执行顺序是相反的<Br/>
替换响应内容的思路就是Servlet在调用`HttpServletResponse`对象写入输出流时，输出内容均写入我们预设好的缓存中，然后替换缓存中的敏感信息，再写入响应输出流中。<br/>
注意：`HttpServletResponse.getOutputStream()`得到的是`ServletOutputStream`实例，因此需要自定义一个继承`ServletOutputStream`的类。<br>
第一步：自定义一个继承`ServletOutputStream`的类。<br/>
````
public class MyServletOuputStream extends ServletOutputStream{
  ByteArrayOutputStream baos;
  public MyServletOutputStream(ByteArrayOutputStream baos){
    this.baos = baos;
  }
  public void write(int data) throws IOException{
    this.baos.write(data);
  }
}
````
第二步：自定义一个继承`HttpServletResponseWrapper`的类<br/>
````
public class MyHSRW extends HttpServletResponseWrapper{
  ByteArrayOutputStream baos;
  ServletOutputStream sos;
  PrintWriter pw;

  public MyHSRW(HttpServletResponse res){
    super(res); 
    baos = new ByteArrayOutputStream();
    sos = new MyServletOutputStream(baos);
    pw = new PrintWriter(baos);
  }

  public PrintWriter getWriter(){
    return pw;
  }

  public ServletOutputStream getOutputStream(){ 
    return sos;
  }
  
  public byte[] toByteArray(){
    return baos.toByteArray();
  }
}
````
第三步：在doFilter中调用<br/>
````
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain){
  MyHSRW myRes = new MyHSRW(res);
  chain.doFilter(res, myRes); 
  String content = new String(myRes.toByteArray());
  // 一系列过滤
  PrintWriter pw = res.getWriter();
  pw.println(content);
  pw.close();
}
````

## EL(表达式语言)
一般使用`${EL}`在文本模板中使用，若出现在标签属性中还可以使用`#{EL}`,但EL不能在脚本元素中使用。<Br/>
内部可以使用所有算术运算符、关系运算符、逻辑运算符、三元条件运算符（?:）和empty前置运算符(`${empty A}`,返回true|false，用于判断A是否为null或空字符串)<br/>
EL中的变量实际上是采用`pageContext.findAttribute(String name)`的方式去获取，因此会依次序查找`page,request,session,application`的属性。<br/>
**隐含的对象**<br/>

## JSTL(JSP标准标签库)
由5个标签库组成：<br/>
Core标签库，前缀为c<br/>
I18N标签库，前缀为fmt<br/>
SQL标签库，前缀为sql<br/>
XML标签库，前缀为x<br/>
Functions标签库，前缀为fn<br/>
**1.Core标签库**<br/>
包含一般用途的标签、条件标签、迭代标签和URL相关的标签。<br/>
引入标签库:`<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>`<br/>
[a].`<c:out>输出标签`<Br/>
````
<!--
  value: 被计算的表达式
  escapeXml: 计算结果是否需要对<,>,',",&等替换为字符引用或预定义实体引用。默认为true
  default: 当value为null时，返回这个默认值
-->
<c:out value="${customer.address}" default="unknown" escapeXml="true"/>
或者为
<c:out value="${customer.address}" escapeXml="true">
unknown
</c:out>
````
[b].`<c:set>赋值标签`<br/>
用于设置范围变量值或JavaBean对象的属性。<br/>
设置范围变量<Br/>
````
<c:set var="<varName>" value="<value>" [scope="{page(默认值)|request|session|application}"]/>
<!--
  当value为null时，底层就会通过pageContext.removeAttribute(String name, String scope)删除变量。
-->
````
设置JavaBean对象的属性或java.util.Map的键值对<br/>
````
<c:set target="<target>" property="<property>" value="<value>"/>
<!--
  target必须为java.util.Map对象或支持setter方法的JavaBean对象，否则会抛异常 
  value为null时，会删除Map对象的键值对，会将JavaBean对象的属性设置为null
-->
````
[c].`<c:remove>移除范围变量标签`<br/>
````
<c:remove var="<var>" [scope="{page(默认值)|request|session|application}"]/>
````
[d].`<c:catch>异常处理标签`<br/>
捕获异常并将异常对象保存到自定义的范围变量中。<Br/>
````
<c:catch var="ex">
<%
  int k = 1/0;
%>
</c:catch>
<c:out value="${ex}"/>
<c:out value="${ex.message}"/>
````
[e]. URL相关标签<Br/>
**`<c:import>`**<br/>
用于导入不同Web应用程序下的资源（`<jsp:include>`只能导入同一个Web应用程序下的资源）<br/>
资源内容以String对象方式被导出：<br/>
````
<c:import url="<url>" [scope="{page(默认值)|request|session|application}"] [context="<context>"] [var="<var>"] [charEncoding="<encoding>"]>
[<c:param/>]*
</c:import>
````
资源内容以Reader对象方式被导出：<br/>
````
<c:import url="<url>" [context="<context>"] [varReader="<varReader>"] [charEncoding="<encoding>"]>
[<c:param/>]*
</c:import>
<!--
  当url属性值为相对url时，则需要通过context属性来指明url的上下文了。
  如果指明var或varReader值，那么就会将资源写入JspWriter对象中。
  varReader对象仅能在<c:import>标签体内使用。
-->
<!--
  引入同一Web应用的资源，使用相对路径，无需context属性
-->
<c:import url="b.jsp"/>

<!--
  引入不同Web应用的资源，使用相对路径，需要context属性，和设置server.xml文件的crossContext为true.
  <Context path="/otherWeb" docBase="......" crossContext="true"/>
-->
<c:import url="/a.jsp" context="/otherWeb"/>

<!--
  使用绝对路径，被引用的资源无法共享当前资源的Session、Request等环境信息。
-->
<c:import url="http://www.google.com"/>
````
**`<c:url>`**<br/>
重新构造URL<br/>
````
// 重新构造相对路径
<c:url value="<相对路径>" [context="<相对路径的上下文(默认是当前Web应用的上下文根)>"] [scope="{page(默认值)|request|session|application}"] [context="<context>"] [var="<var>"]>
// 设置queryString参数
[<c:param name="<name>" value="<value>">]*
</c:url>

// 重新构造绝对路径
<c:url value="<绝对路径>"  [scope="{page(默认值)|request|session|application}"] [context="<context>"] [var="<var>"]>
// 设置queryString参数
[<c:param name="<name>" value="<value>">]*
</c:url>
````
若设置var属性，则结果会写入JspWriter对象中。<Br/>
**<c:redirect>**<br/>
重定向请求<Br/>
````
<c:redirect url="<url>" [context="<context>"]>
[<c:param/>]*
</c:redirect>
````
**<c:param>**<br/>
为URL添加请求参数<br/>
````
// 如果name为空，则什么都不做
// value为空，则返回空字符串
<c:param name="<name>" value="<value>"/>
````
2. 国际化标签<br/>
引入标签库`<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>`<Br/>
**国际化标签**<br/>
[a].**<fmt:setLocale>**<br/>

**日期、时间、数字格式化标签**<br/>
[a].**`<fmt:timeZone>`**<br/>
````
// 用于指定时区，标签体的时间信息将按照这个时区进行格式化或解析
// 如果value为空，则采用GMT时区
<fmt:timeZone value="String或java.util.TimeZone枚举值">
时间信息
</fmt:timeZone>
````
[b].**`<fmt:setTimeZone>`**<br/>
用于指定时区，并将其保存到范围变量中，或保存到`javax.servlet.jsp.jstl.fmt.timeZone`配置变量中。<br/>
````
<fmt:setTimeZone value="String或java.util.TimeZone枚举值" [var="<var"] [scope="{page|request|session|application}"]/>
````
[c].**`<fmt:formatNumber>`**<br/>
按照地区或定制的方式将数字的值格式化为数字、货币或百分比。<br/>






**`Enumeration`类**<br/>
`hasMoreElements()`：判断是否还有元素未读取。<br/>
