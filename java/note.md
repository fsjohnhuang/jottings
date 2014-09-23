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
2. 添加行注释：`Ctrl+/`<br/>
3. 取消行注释：`Ctrl+\`<br/>


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
http://blog.csdn.net/Silver6wings/article/details/5394226

