##����
�ṩһ����¼��־�Ŀ�ܣ����Խ���־��Ϣ��¼���ļ�������̨��Windows�¼���־�����ݿ⣨MSSQL��Acess��Oracle��DB2��SQLite�ȣ���<br/>

## ʾ��
config�����ļ�<br/>
````
<?xml version="1.0" encoding="utf-8"?>
<configuration>
<configSections>
  <section name="log4net" type="System.Configuration.IgnoreSectionHandler"/>
</configSections>
<log4net>
  <!-- ������־�����ý�� -->
  <root>
    <appender-ref ref="ConsoleAppender"/>
    <appender-ref ref="R"/>
  </root>
  <!-- �������������̨ -->
  <appender name="ConsoleAppender" type="log4net.Appender.ConsoleAppender">
    <layout type="log4net.Layout.PatternLayout">
	<conversionPattern value="%date [%thread] %-5level %logger [%property{NDC}] - %message%newline"/>
    </layout> 
  </appender>
  <!-- �����������־�ļ� -->
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
.cs�ļ�<br/>
````
/**
 * ָ��log4netʹ��.config�ļ�����ȡ������Ϣ
 * ��ΪWinform���ٶ�����ΪDemo.exe��, ��ô�����ļ���ΪDemo.exe.config
 * ��ΪWebform����Ϊweb.config
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


## ��ܵĺ������
**1. Appender**<br/>
���ã����ڶ�����־��Ϣ��������ʣ��ļ�������̨��Windows�¼���־�����ݿ⣨MSSQL��Acess��Oracle��DB2��SQLite�ȣ���<br/>
���õ�Appdner���:<br/>
`ConsoleAppender`���������Ϊ����̨<br/>
`FileAppender`���������Ϊ�ļ�<br/>
ʾ��<br/>
````
<appender name="FileAppender" type="log4net.Appender.FileAppender"
 file="logs/log.txt" appendToFile="true">
  <layout type="log4net.Layout.PatternLayout">
    ..........
  </layout>
</appender>
````
`RollingFileAppender`,����־�Իع��ļ�����ʽд���ļ��С�<br/>
����ָ���ļ����������������������һ�����ļ�����¼���ҿ���ָ�����������־�ļ�������������ʱ�򸲸Ǵӵ�һ����־�ļ���ʼѭ�����ǡ�<br/>
ʾ��1<br/>
````
<appender name="RollingFileAppender" type="log4net.Appender.RollingFileAppender" 
 file="logs/log.txt" appendToFile="true" rollingStyle="Size" maxSizeRollBackups="10" maximumFileSize="100KB" staticLogFileName="true">
  <layout type="log4net.Layout.PatternLayout">
    ..........
  </layout>
</appender>
````
��������Ϊ��ÿ����־�ļ����Ϊ100KB���������10����־�ļ���<br/>
ʾ��2<br/>
````
<appender name="RollingFileAppender" type="log4net.Appender.RollingFileAppender" 
file="logs/log.txt" appendToFile="true" rollingStyle="Date" datePattern="yyyyMMdd">
  <layout type="log4net.Layout.PatternLayout">
    ..........
  </layout>
</appender>
````
��������Ϊ��ÿһ�춼�ᴴ��һ����־�ļ����ҵ�ǰ��������־��Ϣ��д�����־�ļ�<br/>

`EventLogAppender`���������Ϊϵͳ��־<br/>
`AdoNetAppender`���������Ϊ���ݿ�<br/>
ʾ��<br/>
````
<appender name="AdoNetAppender_Oracle" type="log4net.Appender.AdoNetAppender"> 
      <connectionType value="System.Data.OracleClient.OracleConnection, System.Data.OracleClient" /> 
      <connectionString value="data source=[mydatabase];User ID=[user];Password=[password]" /> 
      <commandText value="INSERT INTO Log (Datetime,Thread,Log_Level,Logger,Message) VALUES (:log_date, :thread, :log_level, :logger, :message)" /> 
      <bufferSize value="128" /> 
      <parameter> 
        <parameterName value=":log_date" /> 
        <dbType value="DateTime" /> 
        <layout type="log4net.Layout.RawTimeStampLayout" /> 
      </parameter> 
      <parameter> 
        <parameterName value=":thread" /> 
        <dbType value="String" /> 
        <size value="255" /> 
        <layout type="log4net.Layout.PatternLayout"> 
          <conversionPattern value="%thread" /> 
        </layout> 
      </parameter> 
      <parameter> 
        <parameterName value=":log_level" /> 
        <dbType value="String" /> 
        <size value="50" /> 
        <layout type="log4net.Layout.PatternLayout"> 
          <conversionPattern value="%level" /> 
        </layout> 
      </parameter> 
      <parameter> 
        <parameterName value=":logger" /> 
        <dbType value="String" /> 
        <size value="255" /> 
        <layout type="log4net.Layout.PatternLayout"> 
          <conversionPattern value="%logger" /> 
        </layout> 
      </parameter> 
      <parameter> 
        <parameterName value=":message" /> 
        <dbType value="String" /> 
        <size value="4000" /> 
        <layout type="log4net.Layout.PatternLayout"> 
          <conversionPattern value="%message" /> 
        </layout> 
      </parameter> 
    </appender> 
````
�Զ���Appenderʱ����Ҫ�̳�`log4net.Appender.AppenderSkeleton`<br/>
**2. Layout**<br/>
���ã��������û���ʾ���յľ���ʽ���������Ϣ��<br/>
**ע�⣺һ��Appender������ܶ�Ӧһ��Layout����**<br/>
���ݵ�Layout���:<br/>
`PatternLayout`���û��Զ����ʽ<br/>
����<br/>
`%m`(message),�������־��Ϣ<br/>
`%n`(newline),����<br/>
`%d`(datetime),�����ǰ������е�ʱ��<br/>
`%r`(runtime),�����������е���ǰ���ʱ���ĵĺ�����<br/>
`%t`(thread id),�����ǰ������е��߳�ID<br/>
`%p`(level),�����־������־�¼���<br/>
`%c`(class),�����ǰ������ڵĶ�������<br/>
`%M`(method),�����ǰ������ڵķ�������<br/>
`%f`(file),�����ǰ������ڵ��ļ�����<br/>
`%L`(line),�����ǰ���λ�����ڵ��ļ��е��к�<br/>
`%l`(location),�����ǰ���λ�ڵ�ȫ�޶��������Լ�Դ�ļ����к�<br/>
`%����`,��ʾ�������С���ȣ����������������ÿո���䡣�磺`%5p`,��ʾ�����־�����ҳ�����СΪ5���ַ�<br/>
`%-����`,��ʾ�������С���ȣ�������������ұ��ÿո���䡣�磺`%-5p`,��ʾ�����־�����ҳ�����СΪ5���ַ�<br/>
`%.����`,��ʾ�������󳤶ȣ����������ض�<br/>
`%����.����`,��ʾ����ı���λ����С����󳤶�֮�䣬���������ض�, �������ÿո����<br/>
**���ʵ��**<br/>
````
%-d{yyyy-MM-dd HH\:mm\:ss}  [%L] [%c]-[%p] %m%n
````

`SimpleLayout`��<br/>
`ExceptionLayout`�����ʱ����Message��Trace��Ϣ��Logger�������봫��Exception���󣬷���ʲô���������<br/>
`XmlLayout`�����Ļ�������<br/>
�Զ���Layoutʱ����Ҫ�̳�`log4net.Layout.LayoutSkeleton`<br/>

**3. Appender Filter**<br/>
���ã�Ĭ�������Appender����Ὣ������־��Ϣ���������Ӧ�Ľ����У�ͨ��Appender Filter��������ռ䣺log4net.Filter�����԰��ղ�ͬ�ı�׼������־�¼������ݡ�<br/>
���õ�Filter�����<br/>
`DenyAllFilter`����ֹ���е���־�¼�����¼<br/>
`LevelMatchFilter`��ֻ��ָ���ȼ�����־�¼��ű���¼<br/>
`LevelRangeFilter`����־�ȼ���ָ����Χ�ڵ��¼��ű���¼<br/>
`LoggerMatchFilter`��Logger����ƥ��ű���¼<br>
`PropertyFilter`����Ϣƥ��ָ��������ֵ�ű���¼<br/>
`StringMatchFilter`����Ϣƥ��ָ�����ַ����ű���¼<br/>

**4. Logger**<br/>
���ã�ֱ����Ӧ�ý�������������ڴ�����־�¼�<br/>
������־�¼���     ���ȼ�<br/>
OFF&emsp;&emsp;                    6<br/>
FATAL&emsp;&emsp;                  5<br/>
ERROR&emsp;&emsp;                  4<br/>
WARN&emsp;&emsp;                   3<br/>
INFO&emsp;&emsp;       	       2<br/>
DEBUG&emsp;&emsp;                  1<br/>
ALL&emsp;&emsp;                    0<br/>

**5. Object Render**<br/>
���ã��������Layout��ʽ������־��Ϣ��Render����ʵ��log4net.ObjectRender.IObjectRender�ӿ�<br/>
**6. Repository**<br/>
���ã�������־������֯�ṹ��ά�������ڷǿ����չ�ߣ����������õ��������<br/>

## ���÷�ʽ
һ. ����������<br/>
ͨ��`log4net.Config.BasicConfigurator.Configre`���ø���־��ֻ�����ø���־���ѡ�<br/>
ʾ��<br/>
````
// ��PatternLayoutһ��ʹ��FileAppender

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
��. �����ļ�(�Ƽ�)<br/>
  log4net��ܻ���`AppDomain.CurrentDomain.BaseDirectory`ָ���Ŀ¼·���²��������ļ���

**��config�ļ�������**<br/>
��`<configuration>`�ڵ������`<configSections>`�ڵ�<br/>
````
<configSections>
  <section name="log4net" type="System.Configuration.IgnoreSectionHandler"/>
</configSections>
````
ֻ����������ڵ㣬log4net.dll���ܶ�ȡconfig�ļ���`<log4net>�ڵ�`��������Ϣ<br/>

**����־`<root>�ڵ�`**<br/>
�����������־�����Ǹ���־�ĺ���������־������û����ʽ����ʱ���ʹ�ø���־��������Ϣ��<br/>
````
<root>
  <!-- level�ڵ����ڶ��崦���ĸ��������־�¼���ȱʡֵΪDEBUG -->
  <level>INFO</level>
  <!-- appender-ref�ڵ����ڶ�����־������ʹ�õ�Appender���� -->
  <appender-ref ref="appender�ڵ��name����ֵ"/>
</root>
````
**��־����`<logger>�ڵ�`**<br/>
��ʽ������־����<br/>
````
<!-- additivity��������Ϊfalseʱ����־���󽫲��̳и���־��appender-ref�ڵ���Ϣ��ȱʡֵΪtrue -->
<logger name="test.Logging" additivity="false">
  <!-- ���Ǹ���־��level���� -->
  <level value="WARN"/>
</logger>
````
.cs�ļ��еĵ��÷�ʽ<br/>
````
log4net.LogManager.GetLogger("test.Logging");
````
**Appender����`<appender>�ڵ�`**<br/>
````
<appender name="appender1" type="log4net.Appender.FileAppender">
  <layout type="log4net.Laytout.PatternLayout">
    <conversionPattern value=".........."/>
  </layout>
  <filter type="log4net.Filter.LevelRangeFilter" levelMin="DEBUG" levelMax="WARN">
  </filter>
</appender>
````

## ���������ļ�
ÿ���ɶ���ִ�еĳ��򼯾����Թ����Լ��������ļ�����������ʹ�õ����ߵ������ļ����ˣ�<br/>
��`AssemblyInfo.cs�ļ�`�����`[assembly:log4net.Config.DOMConfigurator([ConfigFile="�����ļ���"|ConfigFileExtension="����������ļ�����չ��"][Watch=true/false])`<br/>
����˵����
`ConfigFile`��ָ�������ļ�����չ����·�������Ϊ���·������`AppDomain.CurrentDomain.BaseDirectory`Ϊ��ǰ·��;<br/>
`ConfigFileExtension`�����������������ļ�ʹ���˲�ͬ����չ��,��ͨ��������ָ����Ĭ��ֵΪ`config`�������ļ�����������Ϊ"Ӧ�ó�����.exe.config";<br/>
**ע�⣺ConfigFile��ConfigFileExtension�����ǻ���ģ�������������һ��**<br/>
`Watch`�������Ƿ���Ҫ����ʱ�����ļ����޸ġ���������ɾ�����¼���������Ϊtrue����ʹ��FileSystemWatcher�����������ļ���<br/>

## �����־���Ż���ʽ
````
/**
 * ���ڴ�����־�¼�ʱ��������־����ļ����Ƿ�������־�¼��ļ���
 * �ȼ����־����ļ��𣬲Ŵ�����־�¼� 
 */
if (log.IsDebugEnabled) log.Debug("message.....");
````

## �ο�
http://www.cnblogs.com/jams742003/archive/2009/12/10/1620861.html
http://zhoufoxcn.blog.51cto.com/792419/429988/
