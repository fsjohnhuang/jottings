# �����޹���
Java�Ĺ淶�����ΪJava���Թ淶��The Java Language Specification����Java������淶��The Java Virtual Machine Specification��<br/>
����A+����A�ı�����->class�ļ����ֽ��룩��class+jvm->��ִ�г���Java�б������ؼ��ֺ�������ŵ����ջ�����1~N���ֽ���������Java��֧�ֵ��﷨�������ֽ����޷�֧�֡�<br/>
# Class�ļ�
Class�ļ�����ӦΨһһ�����ӿڡ�����ͽӿڲ�һ���������ļ��У���ͨ���������ֱ�����ɣ���<br/>

## �ļ��ṹ
Class�ļ���һ�����ֽ�Ϊ������λ�Ķ������������������û�зָ�������������ļ��������ǳ������еı�Ҫ���ݡ�<br/>
������ռ��1���ֽ����ϵĿռ�ʱ������Big-Endian˳����д洢��<br/>
Class�ļ���ʽ����������C���Խṹ���α�ṹ�����洢���ݣ��ڲ����������������ͣ��޷������ͱ�<br/>
�޷������������������֡��������á�����ֵ��UTF-8������ַ���<br/>
�������������ͣ���N���޷�������N������ɣ����б������ϰ����"_info"��β<br/>
![](class.png)
Class�ļ������ʽ��ע�⣺ÿ�����ݼ�û�зָ���<br/>
````
���� 		       ����      	    ����
 u4  		       magic      	     1
 u2  		   minor_version             1
 u2  		   major_version             1
 u2  		constant_pool_count          1
 cp_info	   constant_pool	 constant_pool_count - 1
 u2		   access_flags		     1
 u2		   this_class		     1
 u2		   super_class		     1
 u2	         interfaces_count	     1
interface_info     interfaces		 interfaces_count
 u2		  fields_count               1
field_info	     fields		 fields_count
 u2               method_count               1
method_info	     methods		 method_count
 u2		 attributes_count            1
attribute_info       attributes	         attributes_count
````
**ħ����Magic Number��**������ȷ�����ļ��Ƿ�Ϊһ���ɱ�JVM���ܵ�Class�ļ���gif��jpeg���ļ��о�����ħ�������ڱ�ʶ�ļ����͡���ֻҪħ��ֵ��û���㷺ʹ�ã��򿪷��߿��Զ����ֵ����Class�ļ���ħ��ֵΪ0xCAFEBABE��������ΪOak��<br/>
**�ΰ汾�ţ�Minor Version�������汾�ţ�Major Version��**�����ڱ�ʶClass�ļ���ͨ��JDK�ĸ��汾��������ġ�JDK1.0Ϊ45, �����汾��+1��JDK1.7����֧�ְ汾��Ϊ51.0�����µġ�<br/>
**������**��<br/>
�ɳ����ؼ�������constant_pool_count���ͳ����ر�constant_pool�����ɡ������صĵ�0����ճ�����ʾ���������κ�һ����������Ŀ������˳����ص�������1��ʼ��<br/>
�����ش���������͵ĳ��������ߵ��������;�Ϊ����������Literal���ͷ������ã�Symbolic References��<br/>
���������ı��ַ���������Ϊfinal�ĳ���ֵ��<br/>
�������ã���ͽӿڵ�ȫ�޶�����Fully Qualified Name�����ֶε����ƺ���������Descriptor�������������ƺ���������JVMͨ������������Ϊ��������ڴ�ռ䡣<br/>
JDK7��ǰ��11�ֳ����ṹ���ͣ���JDK7���õ�֧�ֶ�̬�������׷��3�֡�14�ֳ����ṹ���͵Ľṹ����ͬ������һ���ֽڶ�����Ϊ��־����<br/>
````
�����ṹ����                  	��־               ����
CONSTANT_Utf8_info		  1             UTF-8���Ա�����ַ���
CONSTANT_Integer_info		  3		����������
CONSTANT_Float_info		  4		������������
CONSTANT_Long_info		  5		������������
CONSTANT_Double_info		  6		˫���ȸ�����������
CONSTANT_Class_info		  7		���ӿڵķ�������
CONSTANT_String_info		  8		�ַ�������������
CONSTANT_Fieldref_info		  9		�ֶεķ�������
CONSTANT_Methodref_info		 10		�����ķ�������
CONSTANT_InterfaceMethodref_info 11		�ӿڷ����ķ�������
CONSTANT_NameAndType_info	 12		�ֶλ򷽷��Ĳ��ַ�������
CONSTANT_MethodHandle_info	 15		�������
CONSTANT_MethodType_info	 16		��ʶ��������
CONSTANT_InvokeDynamic_info	 18		��ʾһ����̬�������õ�
````
CONSTANT_Utf8_info�Ľṹ<br/>
````
tag��u1����־��
length��u2���ַ�������Ϊ���ٸ��ֽ�
bytes���ַ�����UTF8���Ա������ֽ�����
````
�ࡢ�ӿڡ��������ֶ�������CONSTANT_Utf8_info�Ľṹ�洢����length�ĳ���Ϊ2���ֽڣ�65535����������ࡢ�ӿڡ��������ֶ������ȳ���65535��������ᱨ��<br/>
��UTF-8���Ա���ı��뷽ʽ�ǣ�`\u0001`��`\u007f`��1byte�洢����1~127��ASCII��洢��ʽ����`\u0080`��`\u07ff`��2bytes�洢����`\u0800`��`\uffff`����UTF-8��ʽ�洢��<br/>
CONSTANT_Class_info�Ľṹ<br/>
````
tag��u1����־��
name_index��u2��ָ��������һ��CONSTANT_Utf8_info�ṹ���͵ĳ������������ӿڵ�ȫ�޶����ơ�
````
ͨ��`javap -verbose <�ࡢ�ӿ�ȫ�޶���>`����ȡ�������ֽ������ݡ�<br/>
CONSTANT_Integer_info�Ľṹ<br/>
````
tag��u1����־��
bytes��u4��Big-endian�洢��intֵ
````
CONSTANT_Float_info�Ľṹ<br/>
````
tag��u1����־��
bytes��u4��Big-endian�洢��floatֵ
````
CONSTANT_Long_info�Ľṹ<br/>
````
tag��u1����־��
bytes��u8��Big-endian�洢��longֵ
````
CONSTANT_Double_info�Ľṹ<br/>
````
tag��u1����־��
bytes��u8��Big-endian�洢��doubleֵ
````
CONSTANT_String_info�Ľṹ<br/>
````
tag��u1����־��
name_index��u2��ָ��������һ��CONSTANT_Utf8_info�ṹ���͵ĳ����������ַ�����������
````
CONSTANT_Fieldref_info�Ľṹ<br/>
````
tag��u1����־��
name_index��u2��ָ��������һ��CONSTANT_Class_info�ṹ���͵ĳ����������ֶ��������ӿڵķ������á�
name_index��u2��ָ��������һ��CONSTANT_NameAndType_info�ṹ���͵ĳ����������ֶ���������
````
CONSTANT_Methodref_info�Ľṹ<br/>
````
tag��u1����־��
name_index��u2��ָ��������һ��CONSTANT_Class_info�ṹ���͵ĳ������������������ӿڵķ������á�
name_index��u2��ָ��������һ��CONSTANT_NameAndType_info�ṹ���͵ĳ�������������������
````
CONSTANT_InterfaceMethodref_info�Ľṹ<br/>
````
tag��u1����־��
name_index��u2��ָ��������һ��CONSTANT_Class_info�ṹ���͵ĳ����������������ӿڵķ������á�
name_index��u2��ָ��������һ��CONSTANT_NameAndType_info�ṹ���͵ĳ�������������������
````
CONSTANT_NameAndType_info�Ľṹ<br/>
````
tag��u1����־��
name_index��u2��ָ��������һ��CONSTANT_Utf8_info�ṹ���͵ĳ��������������ֶ����Ƶ��ַ�����������
name_index��u2��ָ��������һ��CONSTANT_Utf8_info�ṹ���͵ĳ��������������ֶ����������ַ�����������������������Ϊ���+����ֵ����(Z)Ljava/lang/String;�����ֶε�������Ϊ�ֶε������͡�
````
CONSTANT_MethodHandle_info�Ľṹ<br/>
````
tag��u1����־��
reference_kind��u1��ֵ1~9�������������ͣ���ͬ���;��в�ͬ���ֽ�����Ϊ��
reference_index��u2��
````
CONSTANT_MethodType_info�Ľṹ<br/>
````
tag��u1����־��
descriptor_index��u2��ָ��������һ��CONSTANT_Utf8_info�ṹ���͵ĳ�������ʾ��������������
````
CONSTANT_InvokeDynamic_info�Ľṹ<br/>
````
tag��u1����־��
bootstrap_method_attr_index��u2��ֵΪָ������������bootstrap_methods[]�������Ч������
name_and_type_index��u2��ָ��������һ��CONSTANT_NameAndType_info�ṹ���͵ĳ�������ʾ����������������
````
**���ʱ�־��access_flags��**<br/>
���ڱ�ʶ���ӿڵķ�����Ϣ���������Ƿ������η�����<br/>
````
��־����          ��־ֵ            ����
ACC_PUBLIC        0x0001         public����
ACC_FINAL	  0x0010         ��ʹ����final���η�
ACC_SUPER	  0x0020         ����ʹ��invokespecial�ֽ���ָ�JDK1.0.2������������ñ�־����Ϊtrue
ACC_INTERFACE     0x0200	 �ӿ�
ACC_ABSTRACT	  0x0400	 �������ӿ�
ACC_SYNTHETIC	  0x1000	 ������û��������ɵ�
ACC_ANNOTATION    0x2000    	 ����Ϊע��
ACC_ENUM	  0x4000	 ����Ϊö��
````
������־ͨ���������ȡ����ֵ��<br/>
**��������this_class��������������super_class���ͽӿ��������ϣ�interfaces��**<br/>
��������this_class����ָ��������һ��CONSTANT_Class_info�ṹ���͵ĳ���<br/>
����������super_class����ָ��������һ��CONSTANT_Class_info�ṹ���͵ĳ�����Java����������и��࣬����`java.lang.Object`�⡣<br/>
�ӿ��������ϣ�interfaces����interface_countΪ�ӿڼ���������������Ϊ0���ʾû�м̳нӿڣ����interfaces����ռ�ռ䡣<br/>
**�ֶα�field_info��**��<br/>
���ڴ�����ӿڵ��ֶΡ�ע�⣺<br/>
1. ����ͽӿ��м̳ж������ֶξ�������������<br/>
2. Java������û�г��ֵ��ֶο��ܻ��������������ڲ�����ֽ��������ⲿ����ֶΡ�<br/>
3. Java������ֶ������벻ͬ�����ֽ�����ֻҪ�ֶε���������һ�������ֶ���������ͬ��<br/>
��������ṹ���£�<br/>
````
access_flag��u2��������Ϣ�����ʱ�־ͨ���������Ľ����
name_index��u2��ָ��������һ��CONSTANT_Utf8_info�ṹ���͵ĳ����������ֶεļ����ơ�
descriptor_index��u2��ָ��������һ��CONSTANT_Utf8_info�ṹ���͵ĳ����������ֶε��������͡�
attribute_acount��u2���ֶε����Ա��ȣ�Ϊ0ʱattributes����ռ�ռ䡣���Ա����ڴ洢�ֶεĶ�����Ϣ��
attributes��acctirbute_info�����Ա��������java����������Ϊfinal static int m=123;�������ConstantValue���ԣ���ָ����123��
````
access_flag��ֵ<br/>
````
��־����          ��־ֵ            ����
ACC_PUBLIC        0x0001         public����
ACC_PRIVATE       0x0002         private����
ACC_PROTECTED     0x0004         protected����
ACC_STATIC        0x0008         static����
ACC_FINAL         0x0010         final����
ACC_VOLATILE      0x0040         volatile
ACC_TRANSIENT     0x0080         transient 
ACC_SYNTHETIC     0x1000         �ֶ��б������Զ������� 
ACC_ENUM      	  0x4000         enum
````
**������method_info��**��<br/>
**���Ա�attribute_info��**��<br/>

ȫ�޶�������ȫ�޶�������,����/����ĩβ���;��Ϊ�ָ���������`java.lang.String`����ȫ�޶���Ϊ`java/lang/String;`<br/>
�����ƣ���ȥ���ͺͲ������ε��ֶ����ͷ�����������`public static void main(String[] args)`�ļ�������`main`<br/>
�������������ֶε��������ͣ������Ĳ����б��������������ͺ�˳�򣩺ͷ���ֵ��<br/>
���������������͵ı�ʶ�ַ���<br/>
````
��ʶ�ַ�         ��������
B                  byte
C		   char
D		   double
F                  float
I		   int
J                  long
S                  short
Z	 	   boolean
V		   void
L		   �������ͣ�L+�����ȫ�޶���������java.lang.Object��ΪLjava/lang/Object;
````
�������͵ı�ʾ��ʽ��ÿһά��ʹ��һ��ǰ�õ�`[`������`java.lang.String[][]`���ʾΪ`[[Ljava/lang/String;`��<br/>
�����ı�ʾ��ʽ��`(��������)����ֵ����`������`int makeArray(java.lang.String source, char[] dest, int destOffset)`���ʾΪ`(Ljava/lang/String;[CI)I`<br/>

### Byte Endian
���ֽ��������е��ֽ����ڴ�Ĵ洢˳��Ҳ����Byte Ordering��Byte Order��<br/>
Big-Endian����˴洢��:ָ�͵�ַ��������Ч�ֽڣ�MSB������λ�ֽ�д���λ��ַ����λ�ֽ�д���λ��ַ���ߵ͡��͸ߣ�����������˼ά��<br/>
Little-Endian��С�˴洢��:ָ��λ��ַ��������Ч�ֽڣ�LSB������λ�ֽ�д���λ��ַ����λ�ֽ�д���λ��ַ���߸ߡ��͵ͣ������ϼ��������<br/>
ʾ������0x123456<br/>
````
�ڴ�ṹ          Big-Endian    Little-Endian 
----------	   --------       --------
| ...... | ��λ    | .... |       | .... |
| 0x1002 | 	   | 0x56 |       | 0x12 |
| 0x1001 |	   | 0x34 |       | 0x34 |
| 0x1000 | ��λ    | 0x12 |       | 0x56 |
----------	   --------       --------
````
MSB(Most Significant Byte)�������Ч�ֽ�<br/>
LSB(Least Significant Byte)�������Ч�ֽ�<br/>
ʾ������0x123456
````
MSB����0x12
LSB����0x56
````
����ַ���λָ��MSB����LSB�������Big-Endian����Little-Endian�ˡ�<br/>
**ע�⣺**<br/>
1. ��������Э�������Big-endian��ʽ�������ݣ����Big-endianҲ��Ϊ�����ֽ��򡣵���Big-endian����0x123456��Little-endianʱ��õ�0x563412<br/>
2. x86CPU����Little-endian����PowerPC����Big-endian��<br/>
3. ��linux�µ�/usr/include��endian.h��machine/endian.h��feature.h��ȷ��Byte Endian����BYTE_ORDER(��_BTYE_ORDER��__BYTE_ORDER)Ϊ1234����LE����Ϊ4321��ΪBE<br/>


