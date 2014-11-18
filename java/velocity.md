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
  入参的数据类型为"变量、属性或方法引用"，"数字类型"，"文本字面量"，"布尔字面量"，"list字面量","map字面量","范围操作"<Br/>
  注意：当方法名形如`getMethod(入参)`或`setMethod(入参)`时，可以缩写为`Method(入参)`来引用；若没有入参则直接以属性方式`method`来引用。<Br/>
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
#for($item in $items)
#for($item in ["1","2"])
#for($item in [1..2])
````
在循环体内通过`$foreach.count`获取当前循环的次数。<br/>
通过设置配置项`directive.foreach.maxloops`可以限制循环次数，默认值为-1表示不限制。<br/>
  [d]. 引入外部文件且不被解析处理<br/>
````
#include(arg[ arg]*)
````
`arg`, 是外部文件路径的字面量，或存储外部文件路径的引用。
````
#include($foo $bar)
#include("other.vm" "next.txt")
````
  [e]. 引入外部文件并解析处理<Br/>
````
#parse(arg)
````
`arg`, 是外部文件路径的字面量，或存储外部文件路径的引用。
````
#parse($boo)
#parse("other.vm")
````
由于存在递归引用的问题，因此可通过设置配置项`parse_directive.maxdepth`来限制最大递归引用次数,默认值为10.<br/>
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

