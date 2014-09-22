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


## javadoc
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


