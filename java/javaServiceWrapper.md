#Java Service Wrapper
## 官网
http://wrapper.tanukisoftware.com/doc/english/download.jsp

## 作用
以守护进程或windows服务的方式运行java程序。JSW提供四种方案改造原有项目，以实现守护进程或windows服务的方式运行。<br/>

## 方式一，`WrapperSimpleApp`
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
2. 新建项目发布目录（假设为agent），然后将src下的conf和bin复制到agent下，并且将conf和bin下的文件重命名，去掉`.in`后缀。然后将bin/wrapper.exe复制到agent/bin/下，再将lib复制到agent下，得到目录结构如下：<br/>
agent<br/>
|-- lib<br/>
&emsp;&emsp;|-- wrapper.dll<br/>
&emsp;&emsp;|-- wrapper.jar<br/>
|-- conf<br/>
&emsp;&emsp;|-- wrapper.conf<br/>
|-- bin<br/>
&emsp;&emsp;|-- wrapper.exe<br/>
&emsp;&emsp;|-- 一堆bat文件<br/>


## 方式二，`WrapperStartStopApp`
## 方式三，`WrapperListener`
## 方式四，`WrapperJarApp`

## 参考
http://blog.csdn.net/arjick/article/details/4526392

