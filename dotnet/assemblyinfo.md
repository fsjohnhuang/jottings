##AssemblyInfo.cs�ļ�
**����**<br/>
�������ó���(dll�ļ�)�ĳ�����Ϣ<br/>
````
// ���򼯱���
[assembly:AssemblyTitle("���򼯱���")]
// ��������
[assembly:AssemblyDescription("��������")]
// ��Ʒ����
[assembly:AssemblyProduct("��Ʒ����")]
// ��˾
[assembly:AssemblyCompany("��˾����")]
// �Ϸ��̱�
[assembly:AssemblyTrademark("�Ϸ��̱�")]
// ��Ʒ���ڲ�����
[assembly:AssemblyCulture("��Ʒ���ڲ�����")]
// ��Ȩ
[assembly:AssemblyCopyright("Copyright 2014")]

// ���򼯵İ汾��Ϣ�������ĸ�ֵ���:
//
//      ���汾
//      �ΰ汾 
//      �ڲ��汾��
//      �޶���
//
// ����ָ��������Щֵ��Ҳ����ʹ�á��ڲ��汾�š��͡��޶��š���Ĭ��ֵ��
// �����ǰ�������ʾʹ�á�*��:
[assembly:AssemblyVersion("1.0.0.0")]

// ���Ƴ������������Ͷ�COM�Ŀɷ�����
[assembly:ComVisible(false)]
// �����ļ��������ۡ����������Ե���Ϣ������������ʱ����ʹ�ø���Ϣ
[assembly:AssemblyConfiguration("")]
````
�����ɵ�dll�ļ��Ҽ�������ԣ����ɲ鿴��������Ϣ<br/>
**����˵��**<br/>
`assembly`����ʾ�����Ե����÷�Χ�ǳ��򼯡�<br/>

## ���򼯶�ȡAssemblyInfo.cs�ļ��е�������Ϣ
�Զ�ȡ`[assembly:AssemblyProduct("")]`Ϊ����
````
Type t = typeof(Program);
AssemblyProductAttribute productAttr = t.Assembly.GetCustomAttributes(typeof(AssemblyProductAttribute), true)[0] as AssemblyProductAttribute;
Console.WriteLine(productAttr.Product);
````

## ����assembly��Χ��Ϣ��������AssemblyInfo.cs��
�����������cs�ļ��ж��壬���������Խ����ڳ����б�����һ�Ρ�<br/>
````
[assembly:AssemblyProduct("")]
namespace{
  public class Demo{
  }
}
````

## �ο�
http://www.itwis.com/html/net/aspnet/20091211/7123.html
