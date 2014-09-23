## 基础知识
**注释类型**<br/>
1. 单行<br/>
````
// 单行注释
String type = "单行注释";
````
2. 多行<br/>
````
/* 
 * 多行注释
 */
String type = "多行注释";
````
3. 文档注释<br/>
````
/**
 * 文档注释
 */
public class Dummy{}
````
javadoc仅提取文档注释<br/>

**快捷键**<br/>
1. 格式化：`Ctrl+Shift+F`<br/>
2. 添加/取消行注释：`Ctrl+/`<br/>


## javadoc
javadoc用于提取注释生成API文档，它不仅提取注释本身，还会将毗邻的类名和方法名一同提取到API文档中。<br/>
javadoc命令只能出现在文档注释当中。<br/>
可通过两种方式使用javadoc。<br/>
1. 嵌入的HTML<br/>
2. 文档标记(Doc tags)，就是以@开头的命令<br/>
注意：javadoc仅为访问控制符为public和protected的成员生成注释文档。<br/>

**2. 文档标记(Doc tags)**<br/>
`@see`：引用其他类。javadoc在生成文档时会自动加入一个超链接“see also”条目，且javadoc不会检查超链接的有效性<br/>
示例：<br/>
````
/**
 * @see 全限定的类名称
 */
````
`@version`：版本信息<br/>
`@author`：作者信息, 多个`@author`标记需要放在一起声明<br/>
最佳实践：<br/>
````
/**
 * @author fsjohnhuang
 * @author email: fsjohnhuang@hotmail.com
 * @author tech blog: fsjohnhuang.cnblogs.com
 */
````
`@param`：参数信息<br/>
格式：`@param 参数名 说明`, 说明内容以下一个文档标记为结束<br/>
`@return`：返回值信息<br/>
格式：`@return 说明`<br/>
`@exception`：可能抛出的异常信息<br/>
格式：`@exception 全限定的类名称 说明`<br/>
`@deprecated`：用于建议用户不必再使用该功能，未来可能会屏蔽这一功能。<br/>

**示例**<br/>
````
/**
 * Description:这是文档注释
 * Copyright(C),2014,fsjohnhuang.github.com
 * Program: Test
 * Date: 2014.09.22
 * @author fsjohnhuang
 * @version 1.0
 */
public class Test{
  /**
   * 获取全名
   * @param shortName 名字缩写
   * @return 全名
   */
  public String getFullName(String shortName){
    return shortName + " Huang";
  }
}
````

## 参考
http://blog.csdn.net/Silver6wings/article/details/5394226<br/>

## 注释模板
**作用：**定义文件、类、方法等默认的注释格式<br/>
**设置注释模板的入口：**`Window->Preference->Java->Code Style->Code Template`，然后展开Comments节点。<br/>
**Comments节点下的元素**</br>
`Files`节点（文件注释标签）</br>
`Types`节点（类注释标签）</br>
`Fields`节点（字段注释标签）</br>
`Constructor`节点（构造函数注释标签）</br>
`Method`节点（方法注释标签）</br>
`Overriding Methods`节点（覆盖方法注释标签）</br>
`Delegate Methods`节点（代理方法注释标签）</br>
`getter`节点（getter方法注释标签）</br>
`setter`节点（setter方法注释标签）</br>

**导出注释模板**<br/>
**导入注释模板**<br/>


## Eclipse的任务管理器
**示例1**<br/>
````
/**
 * @Description TODO 获取姓名
 * @return 姓名
 */
public String getName(){
  return "";
}
````
**示例2**<br/>
````
/**
 * FIXME 在queryString的开头添加?号
 * @Description 从URL中萃取queryString
 * @param url URL字符串
 * @return queryString
 */
public String getQueryString(String url){
  String[] segments = url.split("?");
  return segments.length == 2 ? segments[1] : "";
}
````
通过任务管理器可以迅速查找TODO和FIXME标记的代码位置
![](./todotaks.jpg)
![](./todotaks2.jpg)
**任务标签**<br/>
1. `FIXME`<br/>
2. `TODO`<br/>
3. `XXX`<br/>
4. 自定义任务标签<br/>
**管理任务标签**<br/>
进入`Windows->Preference->Java->Compile->Task Tags`，可自定义任务标签，和设置任务标签的级别（High,Normal和Low）



