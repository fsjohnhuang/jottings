## ����֪ʶ
**ע������**<br/>
1. ����<br/>
````
// ����ע��
String type = "����ע��";
````
2. ����<br/>
````
/* 
 * ����ע��
 */
String type = "����ע��";
````
3. �ĵ�ע��<br/>
````
/**
 * �ĵ�ע��
 */
public class Dummy{}
````
javadoc����ȡ�ĵ�ע��<br/>

**��ݼ�**<br/>
1. ��ʽ����`Ctrl+Shift+F`<br/>
2. ���/ȡ����ע�ͣ�`Ctrl+/`<br/>


## javadoc
javadoc������ȡע������API�ĵ�����������ȡע�ͱ������Ὣ���ڵ������ͷ�����һͬ��ȡ��API�ĵ��С�<br/>
javadoc����ֻ�ܳ������ĵ�ע�͵��С�<br/>
��ͨ�����ַ�ʽʹ��javadoc��<br/>
1. Ƕ���HTML<br/>
2. �ĵ����(Doc tags)��������@��ͷ������<br/>
ע�⣺javadoc��Ϊ���ʿ��Ʒ�Ϊpublic��protected�ĳ�Ա����ע���ĵ���<br/>

**2. �ĵ����(Doc tags)**<br/>
`@see`���ο�ת��Ҳ����������⡣javadoc�������ĵ�ʱ���Զ�����һ�������ӡ�see also����Ŀ����javadoc�����鳬���ӵ���Ч��<br/>
ʾ����<br/>
````
/**
 * @see ȫ�޶���������
 */
````
`@version`���汾��Ϣ<br/>
`@author`��������Ϣ, ���`@author`�����Ҫ����һ������<br/>
���ʵ����<br/>
````
/**
 * @author fsjohnhuang
 * @author email: fsjohnhuang@hotmail.com
 * @author tech blog: fsjohnhuang.cnblogs.com
 */
````
`@param`��������Ϣ<br/>
��ʽ��`@param ������ ˵��`, ˵����������һ���ĵ����Ϊ����<br/>
`@return`������ֵ��Ϣ<br/>
��ʽ��`@return ˵��`<br/>
`@exception`�������׳����쳣��Ϣ<br/>
��ʽ��`@exception ȫ�޶��������� ˵��`<br/>
`@deprecated`�����ڽ����û�������ʹ�øù��ܣ�δ�����ܻ�������һ���ܡ�<br/>

**ʾ��**<br/>
````
/**
 * Description:�����ĵ�ע��
 * Copyright(C),2014,fsjohnhuang.github.com
 * Program: Test
 * Date: 2014.09.22
 * @author fsjohnhuang
 * @version 1.0
 */
public class Test{
  /**
   * ��ȡȫ��
   * @param shortName ������д
   * @return ȫ��
   */
  public String getFullName(String shortName){
    return shortName + " Huang";
  }
}
````

**javadoc���������ĵ�**<br/>
�����ʽ��`javadoc [options] [packagenames] [sourcefiles]`<br/>
`[options]`ֵ��<br/>
````
  -public // ����ʾpublic��ͳ�Ա
  -protected // ��ʾprotected��public��ͳ�Ա��ȱʡֵ��
  -package // ��ʾpackage��protected��public��ͳ�Ա
  -private // ��ʾ������ͳ�Ա
  -d <directory> ����ļ���Ŀ��Ŀ¼
  -version ����@version�ֶ�
  -author ����@author�ֶ�
  -splitindex ��������Ϊÿ����ĸ��Ӧһ���ļ�
  -windowtitle <text> �ĵ�����������ڱ���
````
ʾ����<br/>
Ϊ���java�ļ������ĵ�<br/>
````
javadoc demo\Test.java demo\ECommand.java demo\EView.java
````
������������??<br/>






## �ο�
http://blog.csdn.net/Silver6wings/article/details/5394226<br/>
http://kelaocai.iteye.com/blog/227822

## ע��ģ��
**���ã�**�����ļ����ࡢ������Ĭ�ϵ�ע�͸�ʽ<br/>
**����ע��ģ�����ڣ�**`Window->Preference->Java->Code Style->Code Template`��Ȼ��չ��Comments�ڵ㡣<br/>
**Comments�ڵ��µ�Ԫ��**<br/>
`Files`�ڵ㣨�ļ�ע�ͱ�ǩ��<br/>
````
/**   
* @Title: ${file_name} 
* @Package ${package_name} 
* @Description: ${todo}(��һ�仰�������ļ���ʲô) 
* @author fsjohnhuang
* @date ${date} ${time} 
* @version V1.0   
*/
````
`Types`�ڵ㣨��ע�ͱ�ǩ��<br/>
````
/** 
* @ClassName: ${type_name} 
* @Description: ${todo}(������һ�仰��������������) 
* @author fsjohnhuang
* @date ${date} ${time} 
* 
* ${tags} 
*/
````
`Fields`�ڵ㣨�ֶ�ע�ͱ�ǩ��<br/>
````
/** 
* @Fields ${field} : ${todo}(��һ�仰�������������ʾʲô) 
*/ 
````
`Constructor`�ڵ㣨���캯��ע�ͱ�ǩ��<br/>
````
/** 
* <p>Title: </p> 
* <p>Description: </p> 
* ${tags} 
*/
````
`Method`�ڵ㣨����ע�ͱ�ǩ��<br/>
````
/** 
* @Title: ${enclosing_method} 
* @Description: ${todo}(������һ�仰�����������������) 
* @param ${tags}  ����˵�� 
* @return ${return_type}    �������� 
* @throws 
*/
````
`Overriding Methods`�ڵ㣨���Ƿ���ע�ͱ�ǩ��<br/>
````
/*
* Title: ${enclosing_method}
*Description: 
* ${tags} 
* ${see_to_overridden} 
*/
````
`Delegate Methods`�ڵ㣨������ע�ͱ�ǩ��<br/>
````
/** 
* ${tags} 
* ${see_to_target} 
*/ 
````
`getter`�ڵ㣨getter����ע�ͱ�ǩ��<br/>
````
/** 
* @return ${bare_field_name} 
*/
````
`setter`�ڵ㣨setter����ע�ͱ�ǩ��<br/>
````
/** 
* @param ${param} Ҫ���õ� ${bare_field_name} 
*/
````

**���룬����ע��ģ��**<br/>
��`Window->Preference->Java->Code Style->Code Template`�¿ɵ��뵼��ע��ģ��<br/>



## Eclipse�����������
**ʾ��1**<br/>
````
/**
 * @Description TODO ��ȡ����
 * @return ����
 */
public String getName(){
  return "";
}
````
**ʾ��2**<br/>
````
/**
 * FIXME ��queryString�Ŀ�ͷ���?��
 * @Description ��URL����ȡqueryString
 * @param url URL�ַ���
 * @return queryString
 */
public String getQueryString(String url){
  String[] segments = url.split("?");
  return segments.length == 2 ? segments[1] : "";
}
````
ͨ���������������Ѹ�ٲ���TODO��FIXME��ǵĴ���λ��<br/>
![](./todotaks.jpg)<br/>
![](./todotaks2.jpg)<br/>
**�����ǩ**<br/>
1. `FIXME`<br/>
2. `TODO`<br/>
3. `XXX`<br/>
4. �Զ��������ǩ<br/>
**���������ǩ**<br/>
����`Windows->Preference->Java->Compile->Task Tags`�����Զ��������ǩ�������������ǩ�ļ���High,Normal��Low��
![](tasksetting1.jpg)<br/>


## ���
1. **jar**<br/>
���ڽ����class��java�ļ�����������Դ�ļ�ѹ���鵵<br/>
````
jar  [option]* �ļ���
````
**��ѡ���������ҽ���ѡ����һ����**<Br/>
`-c`������һ��jar��<br/>
`-t`����ʾjar���е������б�<br/>
`-x`����ѹjar��<br/>
`-u`������ļ���jar��<br/>
**��ѡ����**<br/>
`-v`��������ϸ���棬���������׼����豸<br/>
`-m`������ָ��manifest.mf�ļ�����Ĭ������»��Զ�����META-INF/MANIFEST.MF�ļ���<br/>
`-O`������jar��ʱ�������ݽ���ѹ��<br/>
`-M`��������manifest.mf�ļ�<br/>
`-i`������jar��ʱ���������ļ�<br/>
`-C`����ʾ�л���ָ��Ŀ¼��ִ��jar����<br/>
`-f`��ָ��jar�����ļ�·��<br/>
���ó���<Br/>
Ŀ¼�ṹ��<br/>
/<Br/>
|--src<Br/>
&emsp;|--test.class<Br/>
|--META-INF<Br/>
&emsp;|--MAINFEST.MF<Br/>
|--other
&emsp;|--additional.class<br/>
1. Ĭ�ϴ��<br/>
````
// ���ɵ�src.jar�оͺ�srcĿ¼��jar�Զ����ɵ�META-INFĿ¼���ں�MAINFEST.MF�嵥�ļ���
jar -cvf src.jar src
````
2. �鿴������(jar����ʱ���޷��鿴)<Br/>
````
jar -tvf src.jar
````
3. ��ѹjar��<br/>
````
jar -xvf src.jar
````
4. ��ȡjar����������<br/>
````
jar -xvf src.jar src\test.class
````
5. ׷�����ݵ�jar��<br/>
````
//׷��MAINFEST.MF�嵥�ļ�������ļ�����׷������Ŀ¼�ṹ
jar -uvf src.jar other\additional.class

//׷���嵥�ļ�����׷������Ŀ¼�ṹ(src.jar�����META-INFĿ¼)
jar -uMvf src.jar META-INF\MAINFEST.MF
````
6. �����Զ���MAINFEST.MF��jar��<br/>
````
jar -cMvf src.jar src META-INF

jar -cmvf MAINFEST.MF src.jar src
````

## MAINFSET.MF�嵥�ļ�
��������jar���İ汾�����ҡ����ߡ�jdk�汾����Ϣ�����Ҷ��ڿ�ִ�е�jar�����ṩִ��ʱ�������ں����ࡢ������ϵ����Ϣ<br/>
````
Mainfest-Version: 1.0
Created-By: 1.5.0_06 (Sun Microsystems Inc.)
Main-Class: com.sample.myapp.MyAppMain
Class-Path: ext/other.jar 
  ext/addition.jar
````
`Main-Class`������ָʾ��ں���������<br/>
`Class-Path`������ָʾ�����İ��������֮���ÿո��������д���ɣ�ע��ÿ��ǰ�����񣩡���·���ָ���������б�ˣ�������windows����unix��<br/>
**ע�⣺MAINFEST.MFÿ�г�������Ϊ72���ַ�**<Br/>
**ע�⣺MAINFEST.MFÿ�к��治���пո��ұ����ûس���β**<Br/>
**ע�⣺MAINFEST.MFÿ�е��������Ϊ�������ֵ�������ױ��뺬2�����ϵĿո�**<Br/>
package��������<br/>
����Ϊ�������ṩ���İ汾����������Ϣ<br/>
````
Name: com/example/myapp/
Specification-Title: MyApp
Specification-Version: 2.4
Specification-Vendor: example.com
Implementation-Title: com.example.myapp
Implementation-Version: 2002-03-05-A
Implementation-Vendor: example.com
````
ע�⣺Name��������package��������һ��Ҫ����б��Ϊ��β��<br/>
�����߿����ڳ�����ͨ��`java.lang.Package`��ȡpackage�������塣<br/>
````
Package.getPackages();
Package.getPackage(String packageName);
Class.getPackage();
````

##META-INF/INDEX.LIST�ļ�
ͨ��`jar`��`-i`ѡ�����ɵġ���ΪJarIndexʵ�ֵ�һ���֣�������������ڼ�����װ�ع��̡�<br/>

## xxx.SF�ļ�
JAR����ǩ���ļ���xxxΪǩ���ߵ�ռλ����<br/>

## xxx.DSA�ļ�
��xxx.SF�ļ�������ǩ��������ļ������洢������ǩ����JAR�ļ��Ĺ���ǩ����<br/>


## eclipse������jar�ķ���
1. �Ҽ����������<br/>
2. "Export"<br/>
3. "java"->"JAR file"<br/>
4. ѡ��Ҫѹ�����ļ�<br/>
�Ϳ����ˡ�<br/>

## ִ��jar��
jar������������class�ļ�������javaִ��<br/>
````
java -jar src.jar
````

## ��̬�����
````
public class Test{
  static{
     // ��������
  }
}
````
�౻����ʱ�Զ�ִ�о�̬������е���䡣<br/>

## �Ǿ�̬�����
````
public class Test{
  {
    // ��������
  }
}
````
ʵ����ʱ���Ǿ�̬������е�������ڹ��캯����ִ�С�<Br/>

## ʵ����ʱ����̬����顢�Ǿ�̬�����͹��캯����ִ��˳��
````
����ľ�̬�����
����ľ�̬�����
����ķǾ�̬�����
����Ĺ��캯��
����ķǾ�̬�����
����Ĺ��캯��
````
