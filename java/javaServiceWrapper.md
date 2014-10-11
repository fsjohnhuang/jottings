#Java Service Wrapper
## 官网
http://wrapper.tanukisoftware.com/doc/english/download.jsp

## 作用
以守护进程或windows服务的方式运行java程序。JSW提供四种方案改造原有项目，以实现守护进程或windows服务的方式运行。<br/>

## 方式一，`WrapperSimpleApp`
官方推荐使用该方式加工原有项目，好处是简单，且不用修改原有项目的代码。<br/>
1. 下载并解压得到工具包，目录结构如下：<br/>
/<br/>
|-- bin，wrapper控制windows服务的bat文件<br/>
|-- conf，wrapper配置文件<br/>
|-- doc，教程<br/>
|-- lib，wrapper的依赖包<br/>
|-- logs，日志<br/>
|-- src，模板<br/>
&emsp;&emsp;|-- conf<br/>
&emsp;&emsp;|-- bin<br/>
2. 搭建项目结构：新建项目发布目录（假设为agent），然后将src下的conf和bin复制到agent下，并且将conf和bin下的文件重命名，去掉`.in`后缀。然后将bin/wrapper.exe复制到agent/bin/下，再将lib复制到agent下，得到目录结构如下：<br/>
agent<br/>
|-- lib<br/>
&emsp;&emsp;|-- wrapper.dll<br/>
&emsp;&emsp;|-- wrapper.jar<br/>
|-- conf<br/>
&emsp;&emsp;|-- wrapper.conf<br/>
|-- bin<br/>
&emsp;&emsp;|-- wrapper.exe<br/>
&emsp;&emsp;|-- 一堆bat文件<br/>
最后将原有项目的文件复制到bin目录下。<br/>
3. 配置参数(修改agent/conf/wrapper.conf) <br/>
主要需要配置的参数有这些<br/>
````
# 配置java命令路径
wrapper.java.command=jre/bin/java

# 配置CLASSPATH路径（并不会修改全局的环境变量）
# 若原有项目还依赖其他jar包，均需要添加进来
wrapper.java.classpath.1=../lib/wrapper.jar
wrapper.java.classpath.2=.

# 配置lib路径
wrapper.java.library.path.1=../lib

# 配置服务的main class（就是原有项目的程序入口类）
wrapper.app.parameter.1=agent.Daemon

# 配置wrapper日志文件
wrapper.logfile=logs/Agent.log

# 配置系统服务名称
wrapper.ntservice.name=AgentService

# 配置系统服务显示的名称
wrapper.ntservice.displayname=AgentService

# 配置系统服务描述
wrapper.ntservice.description=AgentService

# 配置系统服务的启动方式，取值范围是AUTO_START或DEMAND_START
wrapper.ntservice.starttype=AUTO_START
````
4. 安装、卸载服务<br/>
点击Install.bat和Uninstall.bat就可以了。


## 方式二，`WrapperStartStopApp`
## 方式三，`WrapperListener`
## 方式四，`WrapperJarApp`

## 参考
http://blog.csdn.net/arjick/article/details/4526392

