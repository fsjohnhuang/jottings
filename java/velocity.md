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
  `$变量名.方法`, 常规写法<br/> 
  `${变量名.方法}`, 正规写法<br/> 
  
