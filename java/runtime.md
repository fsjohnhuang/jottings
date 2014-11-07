#`java.lang.Runtime`
  Java API中唯一与JVM运行时环境有关的类。通过`Runtime.getRuntime()`获取运行时环境实例。<br/>

## `Runtime.addShutdownHook(Thread hook)`
  注册一个未使用过的线程供JVM执行Shutdown过程时调用，可注册多个线程，且这些线程将被同时启动的，因此要自行处理线程安全和死锁问题。<br/>
  shutdown触发条件：最后一个主线程退出、收到用户的中断信号、用户登出、系统shutdown或`Runtime.exit()`被调用。当进入shutdown过程后，需要调用halt方法才能中指shutdown过程并退出JVM<br/>
  abort发生后不一定会触发shutdown过程，仅接收到SIGKILL信号、windows中止进程的信号、本地方法发生非法地址一类的错误时才会触发shutdown过程。

## `Runtime.exec()`
  用于调用外部程序,并重定向外部程序的标准输入、标准输出和标准出错到缓冲池。<br/>
  注意：<br/>
  1. 缓冲池的容量是一定的，若缓冲池满了，那么将阻塞外部程序继续往下执行。<br/>
  2. `Runtime.exec()`不是cmd或shell环境，因此无法直接调用dir等命令。<br/>
** 1. 外部程序有大量输出时, 需要及时读取缓冲池的信息**<br/> 
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
  System.out.println(exitVal == 0 ? "成功" : "失败");
}
catch(Exception e){}
````
