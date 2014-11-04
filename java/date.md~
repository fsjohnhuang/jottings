## 日期时间类java.util.Date
````
// 当前日期时间
Date now = new Date();
/* 由于程序的默认时区不同，可能显示如下内容：
 * 1. 格林尼治时间，Tue Oct 28 01:24:14 GMT 2014
 * 2. 美国中部标准时间，Tue Oct 27 23:24:14 GST 2014
 */
System.out.println(now);
````

## 时区java.util.TimeZone
````
// 获取默认时区（最初始时由JVM决定的）
TimeZone defaultTZ = TimeZone.getDefault();
// 显示sun.util.calendar.ZoneInfo[id="GMT",offset=0,dstSavings=0,useDaylight=false,transitions=0,lastRule=null]
System.out.println(defaultTZ);
````
````
// 设置东八区为当前时区
TimeZone e8 = TimeZone.getTimeZone("GMT+8");
TimeZone.setDefault(e8);
// 显示sun.util.calendar.ZoneInfo[id="GMT+08:00",offset=28800000,dstSavings=0,useDaylight=false,transitions=0,lastRule=null]
System.out.println(TimeZone.getDefault());
````
`TimeZone.getTimeZone`的入参是时区ID，通过`TimeZone.getAvaliableIDs()`可获取可用的时区ID。<br/>

## java代码格式化
**1. `java.text.SimpleDateFormat extends java.text.DateFormat`**</br>
作用：通过`format()`将日期对象（`java.util.Date`）格式为文本，通过`parse()`将文本解析为日期对象(`java.util.Date`)<br/>
注意：在通过`SimpleDateFormat sdf = new SimpleDateFormat()`实例化时，会以当前的默认时区作为后续操作的时区，即使后续代码中通过`TimeZone.setDefault()`修改默认时区也不会改变`SimpleDateFormat`对象的时区。即使设置了`Locale`也不会影响<br/>
````
// 初始化时设置 日期和时间模式
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

// 修改日期和时间模式
sdf.applyPattern("yyyy/MM/dd HH:mm:ss.SSS");
````
其实`java.text.SimpleDateFormat`内部使用了`java.util.Calendar`来处理格式化。<Br/>

`java.util.Locale`<Br/>
作为标识来代表特定地理位置、政治、文化区域。当某操作需要与特定的地理位置、政治、文化区域关联时，我们称其为locale-sensitive。（如处理货币、日期）<Br/>
`Locale`由“语言码(language code)”和“地区码(country code)”、“补充码（variant 入参）”组成，其中“地区码(country code)”和“补充码（variant 入参）”为可选。<Br/>
“语言码(language code)”，由两个小写字母组成，如zh， en。命名规范：http://www.loc.gov/standards/iso639-2/englangn.html <br/>
“地区码(country code)”，由两个大写字母组成，如CN，US。命名规范：http://www.iso.ch/iso/en/prods-services/iso3166ma/02iso-3166-code-lists/list-en1.html <br/>
“补充码（variant 入参）”，由于是规范之外的选项，因此没有严格的格式规定，只规定多个值时，通过下划线(_)连接各值即可。<br/>
各类型的码采用下滑线（_）连接构成完整的Locale。<br/>
Locale示例（“语言码(language code)”+“地区码(country code)”）:`zh_CN`<br/>
可以自定义Locale对象，也可直接使用`java.util.Locale`内置的Locale对象。<Br/>

`java.text.DateFormatSymbols`<br/>
封装了根据Locale对象来对日期时间本地化资源的操作。一般不直接使用该类，而是通过`DateFormat.getDateInstance()`等方法来获取内置的formatter对象，该formatter对象已经绑定特定的`DateFormatSymbols对象`。

**2. `java.text.DateFormat`**<br/>
内置了多种与特定的`DateFormatSymbols对象`绑定的formatter对象（也就是预设了日期时间模式）。<br/>
好处：方便对日期时间进行本地化。<br/>
缺点：无法直接修改日期时间模式。<br/>
其内部是使用`java.text.SimpleDateFormat`来处理格式化的。<br/>

**3. `java.util.Calendar`**<br/>
用于萃取日期时间中的年、月、日、时、分、秒等信息，至于格式化就有我们自己解决了。它有一个好处就是在初始Calendar实例时可以设置使用的TimeZone。<br/>
````
Date date = new Date();
Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
c1.set(date);
cl.get(Calendar.YEAR);
cl.get(Calendar.MONTH);
cl.get(Calendar.DATE);
````

## jstl格式化
**`<fmt:formatDate/>`**
````
<%
  Date now = new Date();
%>
<%-- 使用自定义日期时间模式来格式化 --%>
<fmt:formatDate value="${now}" timeZone="GMT+8" type="date" pattern="MM-dd"/>

<%-- 采用内置的日期时间模式来格式化 --%>
<fmt:formatDate value="${now}" timeZone="GMT+8" dateStyle="short"/>
````
**`<fmt:parsetDate/>`**
结果输出到JspWriter中<br/>
````
<fmt:parseDate value="2005/12/21" pattern="yyyy/MM/dd"/>
````
