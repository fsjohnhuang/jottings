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
`@see`�����������ࡣjavadoc�������ĵ�ʱ���Զ�����һ�������ӡ�see also����Ŀ����javadoc�����鳬���ӵ���Ч��<br/>
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

## �ο�
http://blog.csdn.net/Silver6wings/article/details/5394226<br/>

## ע��ģ��
**���ã�**�����ļ����ࡢ������Ĭ�ϵ�ע�͸�ʽ<br/>
**����ע��ģ�����ڣ�**`Window->Preference->Java->Code Style->Code Template`��Ȼ��չ��Comments�ڵ㡣<br/>
**Comments�ڵ��µ�Ԫ��**</br>
`Files`�ڵ㣨�ļ�ע�ͱ�ǩ��</br>
`Types`�ڵ㣨��ע�ͱ�ǩ��</br>
`Fields`�ڵ㣨�ֶ�ע�ͱ�ǩ��</br>
`Constructor`�ڵ㣨���캯��ע�ͱ�ǩ��</br>
`Method`�ڵ㣨����ע�ͱ�ǩ��</br>
`Overriding Methods`�ڵ㣨���Ƿ���ע�ͱ�ǩ��</br>
`Delegate Methods`�ڵ㣨������ע�ͱ�ǩ��</br>
`getter`�ڵ㣨getter����ע�ͱ�ǩ��</br>
`setter`�ڵ㣨setter����ע�ͱ�ǩ��</br>

**����ע��ģ��**<br/>
**����ע��ģ��**<br/>


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
ͨ���������������Ѹ�ٲ���TODO��FIXME��ǵĴ���λ��
[./todotaks.jpg]
[./todotaks2.jpg]
**�����ǩ**<br/>
1. `FIXME`<br/>
2. `TODO`<br/>
3. `XXX`<br/>
4. �Զ��������ǩ<br/>
**���������ǩ**<br/>
����`Windows->Preference->Java->Compile->Task Tags`�����Զ��������ǩ�������������ǩ�ļ���High,Normal��Low��



