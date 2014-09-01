##一、参考<br/>
JAVA操作properties文件[http://www.cnblogs.com/panjun-Donet/archive/2009/07/17/1525597.html]<br/>

##二、properties文件<br/>
  配置文件的一种，后缀为“.properties”，格式为文本文件，内容为“键=值”的格式。<br/>
  #号用于注释。<br/>

##三、示例<br/>
````
# ip and port of server socket
ip=127.0.0.1
port=9999
````

##四、操作properties文件
1. 读
````
Properties props = new Properties(); // java.util.Properties对象继承自Hashtable，专门用于操作properties文件
InputStream in = getClass().getResouceAsStream("properties文件相对于当前类加载路径的文件目录"); // 读取properties文件
props.load(in); // load(InputStream in)方法将从输入流获取属性来初始化java.util.Properties对象

// 读取特定属性
String k = "ip";
String ip = props.getProperty(k);

// 遍历所有属性，方法一
Set keys = props.keySet();
for (Iterator it = keys.iterator(); it.hasNext();){
	String k = it.next();
	System.out.println(k + ": " + props.getProperty(k));
}

// 遍历所有属性，方法二
Enumeration en = props.propertyNames();
while(en.hasMoreElements()){
	String k = en.nextElement();
	System.out.println(k + ": " + props.getProperty(k));	
}
````
2. 写
````
Properties props = new Properties();
InputStream in = getClass().getResouceAsStream("properties文件相对于当前类加载路径的文件目录");
props.load(in);

OutputStream output = new FileOutputStream("properties文件路径");
props.setProperty("ip", "10.248.112.123"); // 修改或新增属性键值对
props.store(output, "modify ip value"); // store(OutputStream output, String comment)将修改结果写入输出流
output.close();
````
