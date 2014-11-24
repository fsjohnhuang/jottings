## 引入velocity到项目
  在官网(http://velocity.apache.org)上下载velocity-1.7.zip。解压后有velocity-1.7.jar和velocity-1.7-dep.jar两个文件。
 velocity-1.7-dep.jar包含velocity-1.7.jar，commons-collections-3.2.1.jar，commons-lang-2.4.jar和oro-2.0.8.jar文件。因此引入velocity-1.7-dep.jar到工程即可。若仅引入velocity-1.7.jar会抛异常。

## 语法
 采用Velocity Template Language(VTL)。并且主要分为四类语法：reference,directive,comments,unparsed content<br/>
 1. **comments(注释)**<br/>
单行注释：`##注释内容`<br/>
多行注释：<br/>
````
#*
注释内容1
注释内容2
*#
````
 2. **unparsed content**<br/>
  改内容将直接输出到最终结果，而不会经过解析处理。<Br/>
````
#[[
 直接输出的内容1
 直接输出的内容2
]]#
````
 3. **reference**<br/>
 reference(引用)以`$`符号开始，可引用渲染上下文的变量、属性、方法(名称必须以字母为开头，且仅由英文、数字、_和-组成)。并且分为常规写法和正式写法两种引入格式<br/>
 注意：VTL中可以使用的类型多样且均为对象类型（int等会被装箱），但最终输出时会调用`toString()`方法获取字符串值。<br/>
 [a]. **变量**<br/>
  `$变量名`, 常规写法，若渲染上下文中没有对应的变量，则输入字符串"$变量名"<br/> 
  `${变量名}`, 常规写法，若渲染上下文中没有对应的变量，则输入字符串"${变量名}"<br/> 
  `$!变量名`, 常规写法，若渲染上下文中没有对应的变量，则输入空字符串""<br/> 
  `$!{变量名}`, 常规写法，若渲染上下文中没有对应的变量，则输入空字符串""<br/> 
 [b]. **属性**<br/>
  `$变量名.属性`, 常规写法<br/> 
  `${变量名.属性}`, 正规写法<br/> 
 [c]. **方法**<br/>
  `$变量名.方法(入参)`, 常规写法<br/> 
  `${变量名.方法(入参)}`, 正规写法<br/> 
  入参的数据类型为"变量、属性或方法引用"，"数字类型"，"文本字面量"，"布尔字面量"，"list字面量","map字面量","范围操作(如:[1..3]或[$arg1..$arg2])"<Br/>
  注意：当方法名形如`getMethod(入参)`或`setMethod(入参)`时，可以缩写为`Method(入参)`来引用；若没有入参则直接以属性方式`method`来引用。<Br/>
  注意：版本1.6后，对于数组引用可以调用其的`isEmpty()`,`size()`,`get(Integer index)`和`set(Integer index, String val)`的方法操作。<br/>
  而且接受可变入参的方法（如public String test(String[]... args){}）<Br/>
 4. **directive**<Br/>
 [a]. `#set($ref=arg)`:向渲染上下文添加变量，或修改已有变量的值。<Br/>
    `ref`: 必须为变量或对象属性。如`name`或`person.name`<br/>
    `arg`: 当使用""号时，其内部的引用会被解析处理，若使用''号时，其内部的引用将不被解析处理。且可设置的类型如下<br/>
````
// 变量引用
#set($new = $old)
// 不被解析的文本字面量
#set($new = 'monica')
// 被解析的文本字面量，加入$old值为'kk',则$new的值为kkmonica
#set($new = "${old}monica")
// 属性引用
#set($obj1.new = $obj2.old)
// 方法引用
#set($obj1.new = $obj2.old($other))
// 数字类型
#set($new = 123)
// 范围操作
#set($new = [1..3])
// 列表
#set($new = ["one", "two"])
// map
#set($new = {"banana":"good","apple":"very godd"})
// 支持+-*/%的运算
#set($new = 1 + $old)
````
注意：若赋予的值为null或undefined，则会保留变量或属性引用的原值。<Br/>
````
#set($test = "hello")
$test
#set($test = $dummy)
$test
````
  [b]. 条件判断<br/>
````
#if($good)
  ##其他操作语句
#elseif($ok)
  ##其他操作语句
#else
  ##其他操作语句
#end
````
逻辑运算符<br/>
````
== // 用于比较数字、字符串，类（将会调用类的toString()方法,然后比较其结果）
!= // 用于比较数字、字符串，类（将会调用类的toString()方法,然后比较其结果）
>
<
>=
<=
!
````
   [c]. 循环<br/>
````
#foreach($ref in arg)
  ##其他操作语句
#end
````
`ref`,为循环范围内有效的变量<br/>
`arg`,可以为list、collection、map、array或这rang operator<br/>
````
#foreach($item in $items)
#foreach($item in ["1","2"])
#foreach($item in [1..2])
````
在循环体内通过`$foreach.count`获取当前循环的次数。<br/>
通过设置配置项`directive.foreach.maxloops`可以限制循环次数，默认值为-1表示不限制。<br/>
  [d]. 引入外部文件且不被解析处理<br/>
````
#include(arg[ arg]*)
````
`arg`, 是外部文件路径的字面量，或存储外部文件路径的引用。<Br/>
````
#include($foo $bar)
#include("other.vm" "next.txt")
````
  [e]. 引入外部文件并解析处理<Br/>
````
#parse(arg)
````
`arg`, 是外部文件路径的字面量，或存储外部文件路径的引用。注意路径并非以当前模板文件的路径作为参考系。<Br/>
````
#parse($boo)
#parse("other.vm")
````
由于存在递归引用的问题，因此可通过设置配置项`parse.directive.maxdepth`来限制最大递归引用次数,默认值为10.<br/>
   [f]. 中止解析处理<br/>
````
#stop
````
中止后续内容被解析处理，一般用于调试。<br/>
   [g].跳出当前流程<Br/>
````
#break
````
   [h]. 动态计算文本字面量<br/>
````
#evalute('#if(true)yes#{else}no#end')
````
   [i]. 打包一组VTL（不带参数）
````
#define($ref)
  ## 其他语句
#end
````
````
## 调用
$ref
````
   [j]. 打包一组VTL（带参数）
````
#marco(vmname [$arg]*)
````
`vmname`,宏名称<br/>
`$arg`, 形参名。若实参和渲染上下文存在同名变量则使用实参。否则则哪个范围有该变量则使用该范围的<br/>
````
## 调用
#vmname($arg1 $arg2)
#vmname()
````
特殊形式：主题内容<br/>
````
## 定义
#macro(vmname)
$!bodyContent
#end

## 调用
#@vmname()主题内容#end

## 结果
主题内容
````
注意：<br/>
1. 在同一模板文件中，宏并不遵守先定义后使用的规则。<br/>
````
#h()
#macro(h)
hello
#end
````
2. 若通过`#parse()`引入的模板中存在宏，则会被解析。若其它模板文件中引用到该宏，则需要遵守先定义后使用的规则。(由于`#parse()`是运行时执行的)<br/>
若希望脱离先定义后使用的规则，则可使用宏库（设置velocimacro.library），在解析处理前先加载宏，并且多个模板公用。<br/>

## 转义符
通过`\`对`$`和`#`进行转义，导致解析器不对其进行解析处理。<br/>
````
#set($test='hello')
$test ## 结果：hello
\$test ## 结果：$test
\\$test ## 结果：\hello
\\\$test ## 结果：\$test

$!test ## 结果：hello
$\!test ## 结果：$!test
$\\!test ## 结果：$\!test
$\\\!test ## 结果：$\\!test
````
## 严格模式
通过`runtime.references.strict=true`开启严格模式<br/>
从Velocity1.6开始出现了严格模式，与之前普通模式的区别为：<br/>
1. 使用上下文不存在的引用，将报异常，即使以`$!`的方式引用。普通模式下是直接输出占位符而已<br/>
2. 上下文不存在的引用，出现在`#if`或`#elseif`中时，没有逻辑运算符或使用`!`、`||`和`&&`时，作为false使用。若使用`>`等运算符时则抛异常<br/> 
3. 对于`#foreach`指令时，引用必须是可迭代的。<br/>

## 配置项
1. **开发阶段，修改模板文件后自动加载**<br/>
````
file.resource.loader.path = templates
file.resource.loader.cache = false
velocitymacro.library.autoreload = true
````

## 使用
1. 单例模式
全应用仅使用一套配置信息
````
VelocityContext ctx = new VelocityContext();
Velocity.init(); // 若没有配置信息, 则可省略该语句
Template t = Velocity.getTemplate(模板文件路径);
StringWriter sw = new StringWriter();
t.merge(ctx, sw);
System.out.println(sw.toString());
````
2. 实例模式
全应用使用多套配置信息
````
VelocityContext ctx = new VelocityContext();
VelocityEngine ve = new VelocityEngine();
ve.init(); // 若没有配置信息, 则可省略该语句
Template t = ve.getTemplate(模板文件路径);
StringWriter sw = new StringWriter();
t.merge(ctx, sw);
System.out.println(sw.toString());
````
两种方式均位于`org.apache.velocity.app包`下。<br/>
**`org.apache.velocity.app.Velocity`详解**<br/>
提供resource,logging和其他services。仅第一个配置有效，后续配置将被忽略<br/>
这是由于`org.apache.velocity.runtime.RuntimeInstance`实例内部通过`initialized`字段作标记，当为true时则不再执行初始化工作。<br/>
与初始化相关的方法<Br/>
````
// 设置配置项，入参o一般为字符串，若多个值时则使用逗号分隔，如：val1,val2
setProperty(String key, Object o)
// 获取配置项
Object getProperty(String key)
// 使用默认的配置项
init()
// 自定义配置项
init(Properties props)
init(String filename)
````
与渲染模板相关的方法<br/>
````
evaluate(Context ctx, Writer out, String logTag, String instring)
evaluate(Context ctx, Writer out, String logTag, InputStream instream)
invokeVelocimacro(String vmName, String namespace, String params[], Context ctx, Writer writer)
mergeTemplate(String templateName, Context ctx, Writer writer)
boolean tempateExists(String name)
````

**`org.apache.velocity.app.VelocityEngine`详解**<br/>


## 渲染上下文对象`VelocityContext`
特殊情况下可以自行继承`org.apache.velocity.context.AbstractContext`和`java.lang.Cloneable`来自定义上下文对象。<br/>
操作上下文对象的键值对元素<Br/>
````
VelocityContext ctx = new VelocityContext();
ctx.put(String key, Object val);
ctx.get(String key);
````
入参`Object val`可接收的具体类型<br/>
````
Integer等，由于Velocity通过反射获取占位符对应的值，因此int等基本类型需要装箱。
Object[], 数组类型，但Velocity会将其当作`java.util.List`来看待，并在模板中可使用`size()`、`get(int)`和`isEmpty()`方法操作。
java.util.Collection子类,Velocity会调用其iterator方法获取Iterator对象
java.util.Map子类，Velocity会调用value()获取Collection对象，然后调用调用其iterator方法获取Iterator对象
java.util.Iterator对象，直接将该Iterator对象添加到上下文对象中时，由于Iterator对象为只进不退的操作方式，因此无法被多个#foreach指令遍历
java.util.Enumeration对象，直接将该Enumeration对象添加到上下文对象中时，由于Iterator对象为只进不退的操作方式，因此无法被多个#foreach指令遍历
````
示例——使用Vector和Iterator的区别<Br/>
模板<br/>
````
#macro(show)
#foreach($letter in $letters)
$letter
#end
#end
#show()
#show()
````
````
Vector<String> v = new Vector<String>();
v.add("a");
v.add("b");
VelocityContext ctx = new VelocityContext();
ctx.put("letters",v);
Template t = Velocity.getTemplate("模板路径");
StringWriter sw = new StringWriter();
t.merge(ctx,sw);
System.out.println(sw.toString());
// 结果
// a
// b
// a
// b

ctx.put("letters",v.iterator());
// 结果
// a
// b
````
**添加静态方法到上下文**<br/>
````
// 添加java.lang.Math到上下文
ctx.put("Math", Math.class);
````
**上下文链（就是复制上下文）**<br/>
作用跟`jQuery.extend()`一样<br/>
````
Velocity ctx1 = new Velocity();
ctx1.put("a", "a1");
ctx1.put("b", "b1");
Velocity ctx2 = new Velocity(ctx1);
ctx2.put("a", "a2");

// ctx2内的属性
//{a: "a2", b: "b1"}
````
**模板与java代码的数据交互**<br/>
1. 在模板中向上下文对象添加变量<br/>
java代码中通过`VelocityContext对象.get(String key)`来获取模板中添加的变量<Br/>
模板:<Br/>
````
#set($new = "set by template")
````
java代码<br/>
````
String setByTemplate = ctx.get("new");
````
2. 在模板中修改从java传入的对象<br/>
在模板中对java传入的对象的修改会被设置到java对象上。<br/>
模板:<Br/>
````
#set($ignore = $map.put("a", "a1"))
````
java代码<br/>
````
HashMap<String, String> map = new HashMap<String,String>();
map.put("a", "a");
ctx.put("map", map);
String setByTemplate = ctx.get("ignore");
System.out.println(setByTemplate); // a
System.out.println(map.get("a")); // a1
````
**模板中调用java方法**<br/>
在模板中调用java方法，存在一个传参问题。<br/>
模板中`[1..2]`和`[0,1,2]`对应java的java.util.ArrayList<br/>
模板中`{a:"a",b:"b"}`对应java的java.util.Map<br/>
若java方法中的入参类型为int，则模板调用时会自动拆箱。<br/>

**解构`VelocityContext`**<Br/>
内置一个`HashMap context`对象，而其实现的方法（注意：VelocityContext实现的方法并不是我们经常用到的方法）主要就是操作该HashMap对象。我们常用的`put(String key, Object obj)`和`get(String key)`是由父类AbstractContext提供的。<br/>

VelocityContext继承自AbstractContext，采用子类进行具体实现，而父类提供对外接口的设计模式。<br/>
````
// VelocityContext实现如下方法
internalGet(String key);
internalPut(String key, Object value);
````
AbstractContext内置一个`HashMap innerContext`对象，而其实现的`put`、`get`等方法就是维护该HashMap对象和调用子实现类VelocityContext的internal*方法维护子类的HashMap对象。<br/>
AbstractContext源码：<br/>
````
public Object put(String key, Object value)
{
     /*
      * don't even continue if key is null
      */
     if (key == null)
     {
         return null;
     }
        
     return internalPut(key.intern(), value);
}
public Object get(String key)
{
/*
 *  punt if key is null
 */

if (key == null)
{
    return null;
}

/*
 *  get the object for this key.  If null, and we are chaining another Context
 *  call the get() on it.
 */

Object o = internalGet( key );

// 当在子类的HashMap对象中找不到值时，再去父类的HashMap对象中找。而不像jQuery.extend那样首先合并对象。
if (o == null && innerContext != null)
{
    o = innerContext.get( key );
}

return o;
}
// 仅删除子类的HashMap对象的键值
public Object remove(Object key)
{
    if (key == null)
    {
        return null;
    }

    return internalRemove(key);
}
````
## 设置模板文件路径的参考系
配置`file.resource.loader.path`配置项

## 参考
http://velocity.apache.org/engine/devel/user-guide.html#Velocimacros
