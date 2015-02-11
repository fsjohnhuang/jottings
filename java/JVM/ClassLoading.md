## ����ص�����
���أ�Loading��<br/>
���ӣ�Linking������֤��Verification����׼����Preparation����������Resolution��(�����ڳ�ʼ������ִ�У���Ϊ��̬�󶨻����ڰ�)<br/>
��ʼ����Initialization��<br/>
ʹ�ã�Using��<br/>
ж�أ�Unloading��<br/>

���ء����Ӻͳ�ʼ���ǽ�����ʽ���еģ����Ǽ�����ɺ�ִ�����ӣ�Ҳ����������ɺ�ִ�г�ʼ���ġ�<br/>
HotSpot��ͨ��`-XX:+TraceClassLoading`�����������ص���Ϣ��<br/>

## ���أ�Loading��
����`/lib`����rt.jar��������������JVM���������������BootstrapClassLoader�������⣬���������JVM���õļ̳���`java.lang.ClassLoader`���������ʵ�ּ��ز�����<br/>
����������ع��̣������ؽ׶���Java����Ա�ɿ��Ƶģ��������׶�������JVM��ȫ���ơ�<br/>
��Ϊ�������裺<br/>
1. ͨ��һ�����ȫ�޶�������ȡ�������Ķ������ֽ�����Դ�Ƕ����ģ�������JAR��EAR��WAR�������������ݿ��л�ȡ��Ҳ������ͨ����̬����������̬���ɴ����ࣩ��<br/>
  �ڶ�ȡ��Ķ������ֽ���ʱ�����ӽ׶ε���֤�ӽ׶ε��ļ���ʽ��֤�Ѿ���ʼ�ˣ��ļ���ʽ��֤���ڱ�֤��ȡ�������������ܹ���ȷ�������洢�ڷ������У�Class�ļ���ʽ��JVM�淶�涨���������������ݽṹ��JVM���о�������ֻ��ͨ���ļ���ʽ��֤����ܴ洢���������С�<br/>
  ����֤ʧ�����׳�`java.lang.VerifyError`���������쳣��<br/> 
  
2. ���ֽ���������ľ�̬�洢�ṹ��Class�ļ��ṹ��ת��Ϊ������������ʱ���ݽṹ��<br/>

3. ���ڴ�������һ���������ӿڵ�`java.lang.Class`������Ϊ���������ӿ��ڷ����������ݵ���ڡ�<br/>
  HotSpot��Class����洢�ڷ������С�

�����������͵ļ��أ��Ǽ��������������ͣ�Ȼ����JVM�Զ������������͡���SuperClass[]�����LSuperClass�࣬Ȼ����JVM����[LSuperClass�����ࡣҲ����˵Java�������װ��һ���࣬������Ĳ���������װ���������ķ�������size()���ǻ�ȡ����ĳ��ȡ�<br/>


## ���ӣ�Linking��
### ��֤��Verification��
���ȶ��ڱ�����ʹ�ú���֤�����࣬��֤�����ǷǱ�Ҫ�ġ�����ͨ��`-Xverify:none`���ر���֤����������������ص�ʱ�䡣<br/>
1. �ļ���ʽ��֤<br/>
  �������󣺶������ֽ���<br/>
  Ŀ�ģ���֤�Ƿ����Class�ļ���ʽ�Ĺ淶��<br/>
2. Ԫ������֤<br/>
  �������󣺷������е����ӿڵ���Ϣ<br/>
  Ŀ�ģ����ֽ������������Ԫ������Ϣ���������������֤����Java���Թ淶��<br/>
  ���Ԫ������Ϣ������<br/> 
   1. ��ĸ�����Ϣ��ȫ�޶��������η��ȣ�;<br/> 
   2. ��ĸ����ֶΡ�������Ϣ��<br/> 
   3. �����Ϣ��ȫ�޶��������η��ȣ�;<br/> 
   4. ����ֶΡ�������Ϣ��<br/> 
   �ȵȡ�**ע�⣺������������Ϣ��**<br/>
3. �ֽ�����֤<br/>
  �������󣺷������е����ӿ���Ϣ��Code����<br/>
  Ŀ�ģ��Է����������������������֤��������ʱ�������Σ��JVM��ȫ���¼�<br/>
  �����������������Ҫִ�����������еȼ�飬�����Ҫ���������Ƶ���һʮ�ֺ�ʱ�Ĳ�����<br/>
  1. ��������ջ������������ָ��Ĳ��������ͼ��ݣ�<br/>
  2. �����תָ�����ת������������ֽ���ָ���ϣ�<br/>
  3. �������ת���ǰ�ȫ�ġ�<br/>
  JDK1.6��Code�����������һ��StackMapTable�����ԣ������������������л����飨Basic Block������������ֵĴ���飩��ʼʱ���ر�����Ͳ�����ջ���õ�״̬��Ȼ���ֽ�����֤ʱ��������ͼ������������Ƶ����Ӷ������֤�����ܡ���ͨ��`-XX:-UseSplitVerifier`���ر����ͼ��ع鵽�����Ƶ�����ͨ��`-XX:+FailOverToOldVerifier`�����õ����ͼ��ʧ�ܾͲ��������Ƶ���<br/>
  JDK1.7��ֻ�ܲ������ͼ���ˡ�<br/>
  ��StackMapTable��������Ȼ���Ա��۸ģ��������JVM�����Ŷ���Ҫ���ǵ��ˡ�<br/>
  ע�⣺�ֽ�����֤ʱ�ᴥ���������ʵ�ֵĽӿڵķ������õĽ�����Ҳ���ǻᴥ������ع��̣���<br/>
4. ����������֤<br/>
  �������󣺷������е����ӿ���Ϣ<br>
  Ŀ�ģ�����ķ������ú����ʵ����Ϣ���ࡢ�ֶΡ������ȣ�������֤����֤�������ÿɳɹ�����Ϊֱ�����ã�����ǰ����Գɹ�����ֱ������<br/>
  ��ִ�����ӽ׶εĽ����ӽ׶�ʱ����Է������ý��з���������֤����֤�������µ����ݣ�<br/>
  1. ͨ�������������ַ���������ȫ�޶����Ƿ�����ڷ��������ҵ���Ӧ���ࡣ<br/>
  2. ͨ�����������ж��ֶΡ������ļ������������Ƿ�����ڷ������ҵ���Ӧ���ֶκͷ�����<br/>
  3. ��ǰʵ���Ƿ���Ȩ�޷��ʷ������õ��ࡢ�ֶκͷ�����<br/>
  ����֤ʧ������׳�`java.lang.IncompatibleClassChangeError`������`java.lang.IllegalAccessError`��`java.lang.NoSuchFieldError`��`java.lang.NoSuchMethodError`�ȡ�<br/>

### ׼����Preparation��
  �ڷ�����Ϊ����������ڴ�ռ䣬����ʼ��Ϊ0��
````
// ����׼���׶κ�value��������洢�ڷ������У�ֵΪ0��123�ĸ�ֵ�������ڳ�ʼ���׶ν��С�
public static int value = 123;
````
�����͵���ֵ<br/>
````
int 0
long 0L
short (short)0
char '\u0000'
byte (byte)0
boolean false
float 0.0f
double 0.0d
reference null
````
  �����ೣ�����ྲ̬����������ֱ�ӳ�ʼ��ΪConstantValue���Ե�ֵ��
````
// ����׼���׶κ�value��������洢�ڷ������У�ֵΪ123��
public static final int value = 123;
````

### ������Resolution��
  ��һ��Ҫ�������ʱִ�У�����������ʱ����ʱ��ִ�С����ǽ��������ڵķ��������滻Ϊֱ�����á�<br/>
**�������ã�Symbolic References��**<br/>
  ��һ����������������õ�Ŀ�꣨�ࡢ�ӿڡ��������ֶεȣ���ֻҪ��������ض�λ��Ŀ�꼴�ɣ�������JVM��ʵ���ڲ������޹أ������õ�Ŀ��Ҳ��һ���Ѿ����ص��ڴ��С��������õ���ʽ�Ѿ���JVM�淶�涨��<br/>  
**ֱ�����ã�Direct References��**<br/>
  ֱ�����ÿ�����ֱ��ָ��Ŀ���ָ�롢���ƫ������һ���ܼ�Ӷ�λ��Ŀ��ľ�����������ֱ��������Ŀ��ض��Ѿ����ڴ��д����ˡ�<br/>

  ��ִ��newarray,checkcast,getfield,getstatic,instanceof,invokedynamic,invokeinterface,invokespecial,invokestatic,invokevirtual,ldc,ldc_w,multianewarray,new,putfiled��putstatic��16���ֽ���ָ��ִ��ǰ�ȶ�����ʹ�õķ������ý��н�����<br/>
  ����invokedynamicָ���⣬����ָ����������ý���Ϊֱ�����ú󣬽����ֱ����������������ظ������������߲������棬��JVM�ᱣ֤��һ�����ɹ������Ҳ������ɹ���ʧ�����������һ�����յ���ͬ���쳣������invokedynamic��ÿ�ν�������ͬ��<br/>
  ������Ҫ������ӿڣ�CONSTANT_Class_info�����ֶΣ�CONSTANT_Fieldref_info�����෽����CONSTANT_Methodref_info�����ӿڷ�����CONSTANT_InterfaceMethodref_info�����������ͣ�CONSTANT_MethodType_info�������������CONSTANT_MethodHandle_info���͵��õ��޶�����CONSTANT_InvokeDynamic_info��7�ַ������ý��С�����������JDK1.7�����Ķ�̬����֧����Ϣ��أ�<br/>
**1. ���ӿڵĽ���**<br/>
  ����D�еķ�������N����Ϊֱ������C�����Ƚ�N��ȫ�޶������ݸ�D���������ȥ������C��Ȼ��������ء���֤��׼���׶Σ�����Ϊ�ֽ�����֤�����ظ����ʵ�ֵĽӿڡ�һ���κ�һ�����ӿڵļ���ʧ�����������N����Ϊֱ��Ӧ��C�Ĳ����ͻᱻ����ʧ��<br/>
  �ɹ�����������з���������֤�����D�Ƿ�߱�����C��Ȩ�ޡ������߱����׳�`java.lang.IllegalAccessError`<br/>
   
**2. �ֶεĽ���**<br/>
  ���ȶ�`CONSTANT_Fieldref_info`��`class_index`����ָ��ķ������ý������ӿڽ������������ɹ���õ����ӿڵ�ֱ������C������C�в��Ҽ����ƺ��ֶ���������`CONSTANT_Fieldref_info`��`name_index`����ָ���������ƥ���ֱ�����ã���ʧ����������ϵݹ�����C��ʵ�ֵĽӿ����Ƿ���ƥ��ģ���ʧ����������ϵݹ�����C��ʵ�ֵĸ������Ƿ���ƥ��ģ���ʧ�����׳�`java.lang.NoSuchFieldError`��<br/>
  ���ɹ�����ֱ�����ã�����з���������֤��ʧ�����׳�`java.lang.IllegalAccessError`<br/>

**3. �෽���Ľ���**<br/>
  ���ȶ�`CONSTANT_Methodref_info`��`class_index`����ָ��ķ������ý������ӿڽ������������ɹ���õ����ӿڵ�ֱ������C������C�в��Ҽ����ƺ��ֶ���������`CONSTANT_Methodref_info`��`name_index`����ָ���������ƥ���ֱ�����ã���ʧ����������ϵݹ�����C��ʵ�ֵĸ������Ƿ���ƥ��ģ���ʧ����������ϵݹ�����C��ʵ�ֵĽӿ����Ƿ���ƥ��ģ����ɹ�˵��C��һ�������ಢ�׳�`java.lang.AbstractMethodError`������ʧ�����׳�`java.lang.NoSuchMethodError`��<br/>
  ���ɹ�����ֱ�����ã�����з���������֤��ʧ�����׳�`java.lang.IllegalAccessError`<br/>
 
**4. �ӿڷ����Ľ���**<br/>
  ���ȶ�`CONSTANT_InterfaceMethodref_info`��`class_index`����ָ��ķ������ý��нӿڽ������������ɹ���õ����ӿڵ�ֱ������C����C���ǽӿ����׳�`java.lang.IncompatibleClassChangeError`��������C�в��Ҽ����ƺ��ֶ���������`CONSTANT_InterfaceMethodref_info`��`name_index`����ָ���������ƥ���ֱ�����ã���ʧ����������ϵݹ�����C�ĸ��ӿ����Ƿ���ƥ��ģ���ʧ�����׳�`java.lang.NoSuchMethodError`��<br/>
  

## ��ʼ����Initialization��
  ��ͽӿھ��г�ʼ�����̣�ʵ���Ͼ���ִ���ֽ����е�`<clinit>`���캯����<br/>
  ���о�̬�ֶκ;�̬���������������ŵ�`<clinit>`�����н��и�ֵ�Ȳ��������Ҹ�������Ѿ���ʼ�����ٳ�ʼ������<br/>
  �ӿڵľ�̬�ֶ�Ҳ���������ŵ�`<clinit>`�����н��и�ֵ����������Ҫ��ʼ���ýӿ�ǰ�����丸�ӿ�����˳�ʼ��������������ʹ�õ����ӿڣ���̬�����ֶΣ�ʱ�Ŵ�����ʼ����
  JVM���Զ�������̻߳�����`<clinit>`������ͬ������ִ�С��������`<clinit>`ִ�к�ʱ�Ĳ���������������̵߳�ִ��<br/>


### ��������
JVM�淶�涨����5������������ִ�г�ʼ�������ء�������Ȼ����֮ǰ����ִ��״̬��<br/>
1. ����new, getstatic, putstatic��invokestatic��4���ֽ���ָ��ʱ������û�н��й���ʼ��������Ҫ�ȴ�����ʼ������Ӧ��Java����Ϊͨ���ؼ���newһ��ʵ��������дһ��������������෽����<br/>
2. ʹ��`java.lang.reflect`���еķ���������ʱ������û�н��й���ʼ��������Ҫ�ȴ�����ʼ����<br/>
3. ����ʼ��һ����ʱ�����丸�໹û��ʼ������ȳ�ʼ�����ࡣ<br/>
4. �����������ʱ����������ʼ����ں������ڵ��ࡣ<br/>
5. JDK1.7���Ӷ�̬���Ե�֧�֡����һ��`java.lang.invoke.MethodHandle`ʵ�����Ľ��������REF_getStatic,REF_putStatic,REF_invokeStatic�ķ�������������������ڵ���û�н��г�ʼ��������Ҫ�ȴ�����ʼ����<br/>

��������5������⣬����������ķ�ʽ�ǲ��ᴥ����ʼ���ģ�����Ϊ�������á�<br/>
ͨ��������ʸ��ྲ̬�ֶβ��ᵼ�������ʼ���������ᵼ�¸����ʼ����<br/>
Java�����д���������󣬲��ᵼ�����������ࣨ��SuperClass[]�������ΪSuperClass����ʼ������Ϊ������������ֽ���ָ����newarray��<br/>
��A������B�ľ�̬�������ᵼ����B�ĳ�ʼ������Ϊ�ڱ���׶λὫ��ʹ�õ��ĳ���ֱ�Ӵ洢���������ص������У����ʵ��������ʱ��A���ʵ�������ĳ�������B�޹�ϵ��<br/>


## �������
  ��������һ���࣬����Ҫ�ɼ���������������͸��౾��һͬȷ������JVM�е�Ψһ�ԡ�<br/>

### ���� 
1. Bootstrap ClassLoader�����������������HotSpot����C++ʵ�ֲ�����ΪJVM��һ���֣����������������JAVAʵ�ֲ��Ҳ���ΪJVM��һ���֣�������<JAVA_HOME>/jre/libĿ¼�л�-Xbootclasspath������ָ����·���л�-Dsun.boot.class.pathϵͳ������ָ����·�����ض����Ƶ������ص�JVM����rt.jar�ȣ����Լ�ʹ���Լ���������<JAVA_HOME>/jre/lib��Ҳ���ᱻ���أ�<br/>
�鿴Bootstrap ClassLoader���صĺ�����⣺<br/>
````
java.net.URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
for (java.net.URL url : urls){
  System.out.println(url.toExternalForm());
}
// �����
lib/resources.jar
lib/rt.jar
lib/sunrsasign.jar
lib/jsse.jar
lib/jce.jar
lib/charsets.jar
lib/jfr.jar
lib/classes
````
JVM����ʱBootstrapClassLoader�����rt.jar�е�`sun.misc.Launcher`������������ص�ʵ�־�����������档Ȼ��ͨ�������������������Ҫ�����ִ����ں���main<br/>
��ִ��`sun.misc.Launcher`�Ĺ��캯��ʱ����ʵ����һ��ExtClassLoader��AppClassLoaderʵ����Ȼ��ͨ��`Thread.currentThread().setContextClassLoader()`��AppClassLoader����ΪĬ�ϵ��߳����������������Ȼ��ͨ��`Thread.currentThread().getContextClassLoader()`��ȡ�߳��������������������Ҫ���ࡣ����Bootstrap ClassLoader�Ϳ������������������������Ҫ�����ˡ�JNDI��Servlet��������<br/>

ClassLoader
SecureClassLoader
URLClassLoader
ExtClassLoader AppClassLoader

`java.net.URI`
`java.net.URL`
`java.net.URLClassLoader`
StringTokenizer


2. Extension ClassLoader����չ�������������`sun.misc.Launcher$ExtClassLoader`ʵ�֣��������<JAVA_HOME>/jre/lib/extĿ¼�л�java.ext.dirsϵͳ������ָ����·���е��������<br/>
3. Application ClassLoader��System ClassLoader��Ӧ�ó����������/ϵͳ������������`sun.misc.Launcher$AppClassLoader`ʵ�֣���ͨ��`java.lang.ClassLoader.getSystemClassLoader`��ȡ���������ClassPath��-cp��java.class.pathϵͳ������ָ������⡣��������û���Զ����Լ��������������Ĭ��ʹ�ø��������<br/>
````
ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
ClassLoader extClassLoader = appClassLoader.getParent();
ClassLoader bootstrapClassLoader = extClassLoader.getParent(); // ����BootstrapClassLoader�ڳ������ǲ��ɼ��ģ���˷���null
````
4. User ClassLoader���û��Զ��������������<br/>
![](classloader.png)<br/>

����Bootstrap ClassLoader�⣬��������������ɱ�������ֱ��ʹ�á���Bootstrap ClassLoader��ͨ��˫��ί��ģ�������ʹ�á�<br/>

��ȻClassLoader��Ϊ������������������ļ��⻹�������ڼ���ͼƬ����Ƶ����Դ��URL������Դ��Ҫ����ClassPathĿ¼�£���ص�API��<br/>
`URL getResource(String name)`��ͨ����Դ���ƻ�ȡ<br/>
�÷�����ͨ�����������������Դ��ʧ�ܺ�͵���findResource(String)��������Դ��<br/>
`URL getSystemResource(String name)`�൱��`ClassLoader.getSystemClassLoader().getResource(name)`��<br/>

````
// �ᴥ��test.A�ĳ�ʼ����������ִ��<clinit>������
Class.forName("ȫ�޶�����")
Class.forName("ȫ�޶�����", true, this.getClass().getClassLoader())

// ���ᴥ��test.A�ĳ�ʼ������������ִ��<clinit>������
Class.forName("ȫ�޶�����", false, this.getClass().getClassLoader())
ClassLoader.getSystemClassLoader().loadClass("test.A")
Thread.currentThread().getContextClassLoader().loadClass("test.A")
````
````
// User ClassLoader
public class MyCL extends URLClassLoader{
  private static File file = new File("c://classes");
  public MyCL(){
    super(getUrl());
  }
  public static URL[] getUrl(){
    try{
	return new URL[]{file.toURL()};
    }catch(MalformedURLException e){
	return new URL[0];
    }
  }
}
````
````
public class FileSystemClassLoader extends ClassLoader { 

    private String rootDir; 

    public FileSystemClassLoader(String rootDir) { 
        this.rootDir = rootDir; 
    } 

    protected Class<?> findClass(String name) throws ClassNotFoundException { 
        byte[] classData = getClassData(name); 
        if (classData == null) { 
            throw new ClassNotFoundException(); 
        } 
        else { 
            return defineClass(name, classData, 0, classData.length); 
        } 
    } 

    private byte[] getClassData(String className) { 
        String path = classNameToPath(className); 
        try { 
            InputStream ins = new FileInputStream(path); 
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
            int bufferSize = 4096; 
            byte[] buffer = new byte[bufferSize]; 
            int bytesNumRead = 0; 
            while ((bytesNumRead = ins.read(buffer)) != -1) { 
                baos.write(buffer, 0, bytesNumRead); 
            } 
            return baos.toByteArray(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return null; 
    } 

    private String classNameToPath(String className) { 
        return rootDir + File.separatorChar 
                + className.replace('.', File.separatorChar) + ".class"; 
    } 
 }
````
ͬһ���������ʵ�����ظ�����ͬһ�����ͷ�����׳�`java.lang.LinkageError: duplicate class definition`������`java.lang.ClassLoader#loadClass`��Լ��ص�����л��棬����ִ���ظ����ز���ʱ��ֱ�ӷ��ػ����е��࣬���Ĭ���ڲ���дloadClass����ʱ�����׳����쳣�������д��뽫��������<br/>
````
public Class<?> loadClass(String name) throws ClassNotFoundException{
  try{
    // ʹ��AppClassLoader����
    return ClassLoader.getSystemClassLoader().loadClass(name);
  }
  catch(ClassNotFoundException e){
    // �ڲ�ͨ��getResource��ȡ��Դ��ͨ��defineClassִ�и�����֤��
    // �����ַ������Ѿ�������Ϣʱ�����쳣
    return this.findClass(name);
  }
}
````

## ж��
������淶��<br/>
 A class or interface may be unloaded if and only if its class loader is unreachable. The bootstrap class loader is always reachable; as a result �� system classes may never be unloaded.<br/>
ֻ�е����ظ����͵��������ʵ��( �������������) Ϊunreachable ״̬ʱ����ǰ�����ص����Ͳű�ж��. �����������ʵ����ԶΪreachable ״̬������������������ص����Ϳ�����Զ���ᱻж��<br/>
Unreachable״̬�Ľ��ͣ�<br/>
 1 ��A reachable object is any object that can be accessed in any potential continuing computation from any live thread.<br/>
 2 ��finalizer-reachable: A finalizer-reachable object can be reached from some finalizable object through some chain of references, but not from any live thread. An unreachable object cannot be reached by either means.<br/>
Ҳ����˵
1. ����������ʵ���Ѿ������ա�
����Bootstrap��Ext��Sys���������˵����������ǲ��ᱻ���յģ�ֻ���û��Զ�����������ſ��ԡ�ͨ��`java -verbose:class Main`ָ�����´��롣
````
import java.net.*;
import java.io.*;

public class Main{
	public  static class MyURLClassLoader  extends  URLClassLoader { 
	   public  MyURLClassLoader() { 
	      super (getMyURLs()); 
	   } 

	   private   static  URL[] getMyURLs() { 
	    try  { 
	       return   new  URL[]{ new  File ("d:/").toURL()}; 
	    }  catch  (Exception e) { 
	       e.printStackTrace(); 
	       return   null ; 
	    } 
	  } 
	} 

	public static void main(String[] args) throws IOException{
	 try  { 
            MyURLClassLoader classLoader =  new  MyURLClassLoader(); 
            Class classLoaded = classLoader.loadClass("RMDIR"); 
            System.out.println(classLoaded.getName()); 
   
            classLoaded =  null ; 
            classLoader =  null ; 
  
           System.out.println(" ��ʼGC"); 
           System.gc(); 
           System.out.println("GC ���"); 
	   System.in.read();
         }  catch  (Exception e) { 
             e.printStackTrace(); 
         } 
	}
}
````
![](unloading.png)

### ˫��ί��ģ��
  ��һ����������յ�����ص��������ȻὫ����ί�ɸ����������������һ��һ��ί�ɵ�Bootstrap ClassLoader��Ȼ����������������������ͼ����࣬������ʧ���������������������Ϣ��Ȼ������������ų����Լ�ȥ���ء�<br/>
  JAVA�в�����ϵķ�ʽʵ��˫��ί��ģ�ͣ������Ǽ̳еķ�ʽ��<br/>
  ע�⣺�����Ǵ������������������������������ġ�<br/>

### ��˫��ί��ģ��
1. �漰SPI��Service Provider Interface���ļ��ض�����JNDI,JDBC,JCE,JAXB��JBI�ȣ�<br/>
  ����JNDI����Ƚӿڴ����rt.jar����Bootstrap ClassLoader���ء���JNDI��ʵ�����ɸ����������̿�����SPI���Ҳ�����Ӧ�õ�ClassPath·���£���Bootstrap ClassLoader�޷�����ClassPath·���µ���⡣Java����Ŷ������߳����������������Thread Context ClassLoader����JNDI����ͨ���߳������ļ�����������SPI���룬ʵ�ָ����������������������������صĶ�����Υ����˫��ί��ģ�͵�˼�룩��
  �߳������ļ�����ͨ��`java.lang.Thread.setContextClassLoader()`�������ã�Ĭ��ʹ�ø��̵߳��߳������ļ����������ȫ�ַ�Χ�ھ�û�����ù��������Ӧ�ó������������<br/>
2. OSGi��ʵ�ִ������滻��HotSwap����ģ���Ȳ���Hot Deployment���ļ�����<br/>

## �Զ���ClassLoader�������ж����
1. �̳�java.lang.ClassLoader������loadClass��������̬����Class�ļ���ʹ��defineClass������
2. new ClassLoader(��·��)���ɡ�

`URLClassLoader(URL[] urls[, ClassLoader parent = appClassLoader])`

## URI��URL��URN
�ɻ�����֮��Tim Berners-Lee���������ʶ�𡢶�λ��������������Դ��<br/>

URL��URN��URI���ӷ��롣
URI�����ַ�����ʾ��ĳ��ͳһ����Դ��ʶ����ʽΪ`[scheme:]scheme-specific-part[#fragment]`��<br/>
`[scheme:]`��URI�����ƿռ��ʶ����<br/>
`scheme-specific-part`����ʽ�ɾ����`[scheme:]`���������ڱ�ʶ��Դ��<br/>
URI��Ϊ����URI�����URI���֣�����URI���Ǹ�ʽ������`[scheme:]scheme-specific-part`����http://fsjohnhuang.cnblogs.com������URI�ԶԱ�ʶ�����ֵĻ����������ķ�ʽ����ĳ����Դ�������URI������`[scheme:]`Ϊ��ͷ����/articles/articles.html�����URI�ԶԱ�ʶ�����ֵĻ����������ķ�ʽ����ĳ����Դ��
ʵ����<br/>
`````
��ǰ������������http://fsjohnhuang.cnblogs.com
<a href="//test.com">test</a>
//test.com�����URI������ʱ������ʶ�����ֵĻ������õ�http://test.com

<a href="mailto:fsjohnhuang@xxx.com">mailto:fsjohnhuang</a>
mailto:fsjohnhuang@xxx.com�Ǿ���URI
`````
URI�ַ�Ϊ��͸���ͷֲ����֡�<br/>
��͸����`scheme-specific-part`��������б��(/)��ͷ�ľ���URI����`mailto:fsjohnhuang@xxx.com`��`news:com.lang.java`�ȡ����ڲ�͸����URI�����ڷֽ⣬��˲���Ҫ��`scheme-specific-part`����Ϣ������Ч����֤��<br/>
�ֲ㣺`scheme-specific-part`������б��(/)��ʼ�ľ��Ի���Ե�URI��<br/>
��ʽ��`[//authority][path][?query][#fragment]`
`[//authority]`����ʾ��Ȩ�����������һ����б�˿�ͷauthority���Ի�������Ҳ���Ի���ע��ģ�Ȼ������б�ˡ��ʺŻ�û�к���������Ϊauthority�Ľ�������authority��ʽΪ`[userinfo@]host[:port]`����`fsjohnhuang@github.com:80`��fsjohnhuangΪ�û���Ϣ��github.comΪ������80Ϊ�˿ںš�<br/>
`[path]`��path���������Ȩ�������ʶ����Դ��λ�á�`[path]`��һϵ�е�·��Ƭ�Σ�path segment����ɣ�·��Ƭ�μ�����б��(/)���ָ���������һ��·��Ƭ������б�˿�ͷ���ʾ��·��Ϊ����·����������Ϊ���·����<br/>
`[?query]`��query�������ʶ��Ҫ���ݸ���Դ�����ݣ�����Ӱ����Դ����Ϊ�򷵻ص����ݡ�<br/>
`[#fragment]`���ڳɹ���ȡ��Դ��ͨ��fragmemt�۽�����Դĳ�����֡�<br/>

�����ض���URI���ʵ��������<br/>
1. null��ʾδ������ַ����������ע��null����ַ����ǲ�ͬ�ģ�<br/>
2. -1��ʾδ��������������<br/>

��׼����Normalization����ȥ��.��..<br/>
��������Resolution��������ڻ���URI��������һ��URI��������URIΪ���URI�����������Ҳ�����URI�����ڱ�URI�Ǿ���URI�����������Ҳ�Ǿ���URI<br/>
��Ի���Relativization����Resolution�ķ������<br/>
````
For any two normalized URIs u and v, 
u.relativize(u.resolve(v)).equals(v)  and
u.resolve(u.relativize(v)).equals(v)  .
````



URI(ͳһ��Դ��ʶ)���ܶ�λ���ȡ/д����Դ��
URL(ͳһ��Դ��λ)����URI��һ�֡�����URL��`[scheme:]`Ϊ��֪������Э�飬���ҽ�URI��ĳ��Э�鴦�����һ����Դ��λ���͸���Э�齨����Լ����������ԴͨѶ�Ķ�/д���ƣ��󶨡�

��URIһ�㲻��Ϊ��Դ�ṩ�־���Ч�����ƣ����URN��ͳһ��Դ����������ȫ��Ψһ���־ò������Դ������������ʹ��Դ�Ѿ���Ч��Ȼ�ᱣ������Դ�����ơ�<br/>

URL = URI + ��֪������Э����Ϊ`[scheme:]` + ��Դ��λ��<br/>
�����Ǿ���URI������scheme������scheme-specific-part�����ݣ�����scheme����������URI��stream handler��

URN = URI + �־ò������Դ����<br/>
mailto,news,isbn��URN

## `java.net.URI`
���캯��`URI(String uri)`���Ը�ʽ��Ч��uri���׳�`URISyntaxException`��<br/>
�෽��`URI.create(String uri)`���Ը�ʽ��Ч��uri�Ჶ���ڲ��׳���`URISyntaxException`��Ȼ�����׳�unchecked��`IllegalArgumentException`��<br/>
��������һ��URIʵ���󣬾Ϳ���ͨ������API����ȡ��URI�����<br/>
````
getAuthority()
getFragment()
getHost()
getPath()
getPort()
getQuery()
getScheme()
getSchemeSpecificPart()
getUserInfo()

isAbsolute()��URI�Ƿ��Ǿ���URI
isOpaque()��URI�Ƿ�͸��
equals(Object o)��compareTo(Object o)���Ƚ�����URL�Ƿ�һ����
````
URI��֧�ֻ�����URI������ ��׼����normalization���ֽ⣨resolution�� �� ��Ի���relativization����
`normalize()`�����ط��ϱ�׼��URI�¶�����`x/y/../z/./q`->`x/z/q`<br/>
`resolve(String/URI uri)`�����з���������������Ϊ���URI����resolve��������������Ϊ����URI���õ�һ���µı�׼��URI����<br/>
`relativize(URI uri)`����Ի����������ǻ�ȡURI�е����URI<br/>
````
URI uriBase = new URI("http://www.somedomain.com");
URI uriRelative = new URI("x/../y");
URI uriResolve = uriBase.resolve(uriRelative); // http://www.somedomain.com/y
URI uriRelativized = uriBase.relativize(uriResolve); // y
````

`URI#toURL()`����URIת��ΪURL��<br/>

## ϵͳ����
  ָ���û�������ص�OS������Ϣ�������Ϣ�������û������и��⡢��ܵȵ�������Ϣ����<br/>
`Properties System.getProperties()`����ȡ����ϵͳ���ԡ�<br/>

cmd/shell������JVMʱ����ϵͳ����<br/>
````
$ java -Djava.ext.dirs=lib com.test.Test
````
����log4j�������ļ�·��������Խ�log4j.xml�����jar����
````
$ java -Dlog4j.configuration=file:/lib/log4j.xml com.test.Test
````

`{long} System.nanoTime`����ȡ��ǰ΢��ʱ��<br/>

## java.util.StringTokenizer
���ڽ��ַ�����ĳ���ָ���������Ƭ��һ��tokens��<br/>
��ʱ��API������ʹ��java.util.regex�µ����������<br/>

## java.io.StreamTokenizer


NoClassDefFoundError
ClassNotFoundError
