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

##在config文件中配置
在`<configuration>`节点下添加`<configSections>`节点<br/>
````
<configSections>
  <section name="log4net" type="System.Configuration.IgnoreSectionHandler"/>
</configSections>
````
只有添加上述节点，log4net.dll才能读取config文件下`<log4net>节点`下配置信息<br/>

## 框架的核心组件
**1. Appender**<br/>
作用：用于定义日志信息的输出介质（文件、控制台、Windows事件日志和数据库（MSSQL、Acess、Oracle、DB2和SQLite等））<br/>
内置的Appdner组件:<br/>
`ConsoleAppender`，输出介质为控制台<br/>
`FileAppender`，输出介质为文件<br/>
`EventLogAppender`，输出介质为系统日志<br/>
`AdoNetAppender`，输出介质为数据库<br/>
自定义Appender时，需要继承`log4net.Appender.AppenderSkeleton`<br/>
**2. Layout**<br/>
作用：定义向用户显示最终的经格式化的输出信息。<br/>
<span style="color:red;">注意：一个Appender对象仅能对应一个Layout对象</span>
内容的Layout组件:<br/>
`PatternLayout`
自定义Layout时，需要继承`log4net.Layout.LayoutSkeleton`<br/>

**3. Appender Filter**<br/>
作用：默认情况下Appender对象会将所有日志信息都输出到相应的介质中，通过Appender Filter对象（命令空间：log4net.Filter）可以按照不同的标准过滤日志事件或内容。<br/>
内置的Filter组件：<br/>

**4. Repository**<br/>
作用：负责日志对象组织结构的维护。对于非框架扩展者，几乎不会用到该组件。<br/>

## 配置方式
1. 代码中配置<br/>
2. 配置文件<br/>


