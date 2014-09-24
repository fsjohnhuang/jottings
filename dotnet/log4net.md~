##作用
提供一个记录日志的框架，可以将日志信息记录到文件、控制台、Windows事件日志和数据库（MSSQL、Acess、Oracle、DB2和SQLite等）。<br/>

## 示例
config配置文件<br/>
````
<?xml version="1.0" encoding="utf-8"?>
<configuration>
<configSections>
  <section name="log4net" type="System.Configuration.IgnoreSectionHandler"/>
</configSections>
<log4net>
  <!-- 定义日志的输出媒介 -->
  <root>
    <appender-ref ref="ConsoleAppender"/>
    <appender-ref ref="R"/>
  </root>
  <!-- 定义输出到控制台 -->
  <appender name="ConsoleAppender" type="log4net.Appender.ConsoleAppender">
    <layout type="log4net.Layout.PatternLayout">
	<conversionPattern value="%date [%thread] %-5level %logger [%property{NDC}] - %message%newline"/>
    </layout> 
  </appender>
  <!-- 定义输出到日志文件 -->
  <appender name="R" type="log4net.Appender.FileAppender" rollingStyle="Date" datePattern="yyyyMMdd-HH:mm:ss">
    <file value="logs/log.txt"></file>
    <appendToFile value="true"></appendToFile>
    <layout type="log4net.Layout.PatternLayout">
      <conversionPattern value="%-d{yyyy-MM-dd HH\:mm\:ss}  [%L] [%c]-[%p] %m%n"></conversionPattern> 
    </layout>
  </appender>
</log4net>
</configuration>
````
.cs文件<br/>
````
/**
 * 指定log4net使用.config文件来读取配置信息
 * 若为Winform（假定程序为Demo.exe）, 那么配置文件则为Demo.exe.config
 * 若为Webform，则为web.config
 */
[assembly:log4net.Config.XmlConfigurator(Watch=true)]
namespace Demo{
  public class MainClass{
    public static void Main(String[] args){
	ILog log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
        log.Error("error", new Exception("there is an exception"));
        log.Fatal("fatal", new Exception("there is a deadly exception occurs"));
	log.Info("info");
	log.Debug("debug");
	log.Warn("warn");
	Console.Read();
    }
  }
}
````


## 框架的核心组件
**1. Appender**<br/>
作用：用于定义日志信息的输出介质（文件、控制台、Windows事件日志和数据库（MSSQL、Acess、Oracle、DB2和SQLite等））<br/>
内置的Appdner组件:<br/>
`ConsoleAppender`，输出介质为控制台<br/>
`FileAppender`，输出介质为文件<br/>
`RollingFileAppender`,将日志以回滚文件的形式写入文件中。<br/>
可以指定文件最大容量，当超过就生成一个新文件来记录，且可以指定最多生成日志文件数量，当超过时则覆盖从第一个日志文件开始循环覆盖。<br/>
示例1<br/>
````
<appender name="RollingFileAppender" type="log4net.Appender.RollingFileAppender" 
file="logs/log.txt" appendToFile="true" rollingStyle="Size" maxSizeRollBackups="10" maximumFileSize="100KB" staticLogFileName="true">
  <layout type="log4net.Layout.PatternLayout">
    ..........
  </layout>
</appender>
````
上述配置为，每个日志文件最大为100KB，且最多有10个日志文件。<br/>
示例2<br/>
````
<appender name="RollingFileAppender" type="log4net.Appender.RollingFileAppender" 
file="logs/log.txt" appendToFile="true" rollingStyle="Date" datePattern="yyyyMMdd">
  <layout type="log4net.Layout.PatternLayout">
    ..........
  </layout>
</appender>
````
上述配置为，每一天都会创建一个日志文件，且当前的所有日志信息均写入该日志文件<br/>

`EventLogAppender`，输出介质为系统日志<br/>
`AdoNetAppender`，输出介质为数据库<br/>
自定义Appender时，需要继承`log4net.Appender.AppenderSkeleton`<br/>
**2. Layout**<br/>
作用：定义向用户显示最终的经格式化的输出信息。<br/>
**注意：一个Appender对象仅能对应一个Layout对象**<br/>
内容的Layout组件:<br/>
`PatternLayout`，用户自定义格式<br/>
参数<br/>
`%m`(message),输出的日志消息<br/>
`%n`(newline),换行<br/>
`%d`(datetime),输出当前语句运行的时刻<br/>
`%r`(runtime),输出程序从运行到当前语句时消耗的毫秒数<br/>
`%t`(thread id),输出当前语句运行的线程ID<br/>
`%p`(level),输出日志级别（日志事件）<br/>
`%c`(class),输出当前语句所在的对象名称<br/>
`%M`(method),输出当前语句所在的方法名称<br/>
`%f`(file),输出当前语句所在的文件名称<br/>
`%L`(line),输出当前语句位于所在的文件中的行号<br/>
`%l`(location),输出当前语句位于的全限定类名，以及源文件和行号<br/>
`%数字`,表示该项的最小长度，如果不够则在左边用空格填充。如：`%5p`,表示输出日志级别，且长度最小为5个字符<br/>
`%-数字`,表示该项的最小长度，如果不够则在右边用空格填充。如：`%-5p`,表示输出日志级别，且长度最小为5个字符<br/>
`%.数字`,表示该项的最大长度，如果超出则截断<br/>
`%数字.数字`,表示该项的必须位于最小和最大长度之间，如果超出则截断, 不够则用空格填充<br/>
**最佳实践**<br/>
````
%-d{yyyy-MM-dd HH\:mm\:ss}  [%L] [%c]-[%p] %m%n
````

`SimpleLayout`，<br/>
`ExceptionLayout`，输出时包含Message和Trace信息。Logger方法必须传入Exception对象，否则什么都不输出。<br/>
`XmlLayout`，中文会有问题<br/>
自定义Layout时，需要继承`log4net.Layout.LayoutSkeleton`<br/>

**3. Appender Filter**<br/>
作用：默认情况下Appender对象会将所有日志信息都输出到相应的介质中，通过Appender Filter对象（命令空间：log4net.Filter）可以按照不同的标准过滤日志事件或内容。<br/>
内置的Filter组件：<br/>
`DenyAllFilter`，阻止所有的日志事件被记录<br/>
`LevelMatchFilter`，只有指定等级的日志事件才被记录<br/>
`LevelRangeFilter`，日志等级在指定范围内的事件才被记录<br/>
`LoggerMatchFilter`，Logger名称匹配才被记录<br>
`PropertyFilter`，消息匹配指定的属性值才被记录<br/>
`StringMatchFilter`，消息匹配指定的字符串才被记录<br/>

**4. Logger**<br/>
作用：直接与应用交互的组件，用于触发日志事件<br/>
级别（日志事件）     优先级<br/>
OFF&emsp;&emsp;                    6<br/>
FATAL&emsp;&emsp;                  5<br/>
ERROR&emsp;&emsp;                  4<br/>
WARN&emsp;&emsp;                   3<br/>
INFO&emsp;&emsp;       	       2<br/>
DEBUG&emsp;&emsp;                  1<br/>
ALL&emsp;&emsp;                    0<br/>

**5. Object Render**<br/>
作用：输出根据Layout格式化的日志消息。Render必须实现log4net.ObjectRender.IObjectRender接口<br/>
**6. Repository**<br/>
作用：负责日志对象组织结构的维护。对于非框架扩展者，几乎不会用到该组件。<br/>

## 配置方式
一. 代码中配置<br/>
通过`log4net.Config.BasicConfigurator.Configre`配置根日志且只能配置根日志而已。<br/>
示例<br/>
````
// 和PatternLayout一起使用FileAppender

log4net.Config.BasicConfigurator.Configure(

  new log4net.Appender.FileAppender(

     new log4net.Layout.PatternLayout("%d

       [%t]%-5p %c [%x] - %m%n"),"testfile.log"));

// using a FileAppender with an XMLLayout

log4net.Config.BasicConfigurator.Configure(

  new log4net.Appender.FileAppender(

    new log4net.Layout.XMLLayout(),"testfile.xml"));
 
// using a ConsoleAppender with a PatternLayout

log4net.Config.BasicConfigurator.Configure(

  new log4net.Appender.ConsoleAppender(

    new log4net.Layout.PatternLayout("%d [%t] %-5p %c - %m%n")));
 
// using a ConsoleAppender with a SimpleLayout

log4net.Config.BasicConfigurator.Configure(

  new log4net.Appender.ConsoleAppender(new

    log4net.Layout.SimpleLayout()));
````
二. 配置文件(推荐)<br/>
  log4net框架会在`AppDomain.CurrentDomain.BaseDirectory`指向的目录路径下查找配置文件。

**在config文件中配置**<br/>
在`<configuration>`节点下添加`<configSections>`节点<br/>
````
<configSections>
  <section name="log4net" type="System.Configuration.IgnoreSectionHandler"/>
</configSections>
````
只有添加上述节点，log4net.dll才能读取config文件下`<log4net>节点`下配置信息<br/>

**根日志`<root>节点`**<br/>
框架中所有日志对象都是根日志的后代，因此日志对象若没有显式配置时则会使用根日志的配置信息。<br/>
````
<root>
  <!-- level节点用于定义处理哪个级别的日志事件，缺省值为DEBUG -->
  <level>INFO</level>
  <!-- appender-ref节点用于定义日志对象所使用的Appender对象 -->
  <appender-ref ref="appender节点的name属性值"/>
</root>
````
**日志对象`<logger>节点`**<br/>
显式配置日志对象。<br/>
````
<!-- additivity特性设置为false时，日志对象将不继承根日志的appender-ref节点信息。缺省值为true -->
<logger name="test.Logging" additivity="false">
  <!-- 覆盖根日志的level设置 -->
  <level value="WARN"/>
</logger>
````
.cs文件中的调用方式<br/>
````
log4net.LogManager.GetLogger("test.Logging");
````
**Appender对象`<appender>节点`**<br/>
````
<appender name="appender1" type="log4net.Appender.FileAppender">
  <layout type="log4net.Laytout.PatternLayout">
    <conversionPattern value=".........."/>
  </layout>
  <filter type="log4net.Filter.LevelRangeFilter" levelMin="DEBUG" levelMax="WARN">
  </filter>
</appender>
````

## 关联配置文件
每个可独立执行的程序集均可以关联自己的配置文件。（组件库就使用调用者的配置文件好了）<br/>
在`AssemblyInfo.cs文件`中添加`[assembly:log4net.Config.DOMConfigurator([ConfigFile="配置文件名"|ConfigFileExtension="编译后配置文件的扩展名"][Watch=true/false])`<br/>
参数说明：
`ConfigFile`：指定配置文件含扩展名的路径，如果为相对路径则以`AppDomain.CurrentDomain.BaseDirectory`为当前路径;<br/>
`ConfigFileExtension`：若程序编译后配置文件使用了不同的扩展名,则通过该属性指定，默认值为`config`，配置文件的最终名称为"应用程序名.exe.config";<br/>
**注意：ConfigFile和ConfigFileExtension属性是互斥的，仅能设置其中一个**<br/>
`Watch`：设置是否需要运行时监视文件的修改、重命名和删除等事件，若设置为true，则使用FileSystemWatcher来监视配置文件。<br/>

## 输出日志的优化方式
````
/**
 * 由于触发日志事件时，会检查日志对象的级别是否满足日志事件的级别
 * 先检测日志对象的级别，才触发日志事件 
 */
if (log.IsDebugEnabled) log.Debug("message.....");
````

## 参考
http://www.cnblogs.com/jams742003/archive/2009/12/10/1620861.html
