#`java.lang.Runtime`
  Java API��Ψһ��JVM����ʱ�����йص��ࡣͨ��`Runtime.getRuntime()`��ȡ����ʱ����ʵ����<br/>

## `Runtime.addShutdownHook(Thread hook)`
  ע��һ��δʹ�ù����̹߳�JVMִ��Shutdown����ʱ���ã���ע�����̣߳�����Щ�߳̽���ͬʱ�����ģ����Ҫ���д����̰߳�ȫ���������⡣<br/>
  shutdown�������������һ�����߳��˳����յ��û����ж��źš��û��ǳ���ϵͳshutdown��`Runtime.exit()`�����á�������shutdown���̺���Ҫ����halt����������ָshutdown���̲��˳�JVM<br/>
  abort������һ���ᴥ��shutdown���̣������յ�SIGKILL�źš�windows��ֹ���̵��źš����ط��������Ƿ���ַһ��Ĵ���ʱ�Żᴥ��shutdown���̡�

## `Runtime.exec()`
  ���ڵ����ⲿ����,���ض����ⲿ����ı�׼���롢��׼����ͱ�׼��������ء�<br/>
  ע�⣺<br/>
  1. ����ص�������һ���ģ�����������ˣ���ô�������ⲿ�����������ִ�С�<br/>
  2. `Runtime.exec()`����cmd��shell����������޷�ֱ�ӵ���dir�����<br/>
** 1. �ⲿ�����д������ʱ, ��Ҫ��ʱ��ȡ����ص���Ϣ**<br/> 
````
try{
  Runtime runtime = Runtime.getRuntime();
  Process proc = runtime.exec("cmd -c javac");
  ByteArrayOutputStream pool = new ByteArrayOutputStream();
  byte[] buffer = new byte[1024];
  int count = -1;
  while ((count = proc.getInputStream().read(buffer)) != -1){
    pool.write(buffer, 0, count);
    buffer = new byte[1024];
  }
  System.out.println(pool.toString("gbk"));
  int exitVal = proc.waitFor();
  System.out.println(exitVal == 0 ? "�ɹ�" : "ʧ��");
}
catch(Exception e){}
````
