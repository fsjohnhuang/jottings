/*
 * Main.java�ļ�
 */
import java.net.*;
import java.lang.reflect.*;

class Main{
  public static void main(String[] args) 
	  throws ClassNotFoundException, MalformedURLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException{
    ClassLoader pClassLoader = ClassLoader.getSystemClassLoader(); // ��System ClassLoader��Ϊ���������
    URL[] baseUrls = {new URL("file:/d:/testLib/")}; // ��������Ŀ¼
    final String binaryName = "com.fsjohnhuang.HelloWorld"; // ��Ҫ���ص���Ķ���������

    int[] i = new int[3];
    System.out.println(i.length);
    ClassLoader userClassLoader1 = new URLClassLoader(baseUrls, pClassLoader);
    ClassLoader userClassLoader2 = new URLClassLoader(baseUrls, pClassLoader);
    Class clazz1 = userClassLoader1.loadClass(binaryName);
    Class clazz2 = userClassLoader2.loadClass(binaryName);
    Object instance1 = clazz1.newInstance();
    Object instance2 = clazz2.newInstance();
    // ����say����
    clazz1.getMethod("say").invoke(instance1);
    clazz2.getMethod("say").invoke(instance2);
    // �����Ķ���������
    System.out.println(clazz1.toString());
    System.out.println(clazz2.toString());

    // �Ƚ�������ĵ�ַ�Ƿ���ͬ
    System.out.println(clazz1 == clazz2);
    // �Ƚ��������Ƿ���ͬ���Ƿ�Ϊ�̳й�ϵ
    System.out.println(clazz1.isAssignableFrom(clazz2));
    // �鿴����ת���Ƿ�ɹ�
    boolean ret = true;
    try{
	    clazz2.cast(instance1);
    }
    catch(ClassCastException e){
	    ret = false;
    }
    System.out.println(ret);
  } 
}