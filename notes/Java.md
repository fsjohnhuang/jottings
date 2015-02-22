## 对象序列化 ##
序列化：内存中的Java对象 -> 二进制流
### 标识可序列化的类 ###
1. 实现`Serializable`标记性接口
2. 实现`Externalizable`接口
### 序列化的IO流 ###
1. `ObjectOutputStream`：负责序列化
2. `ObjectInputStream`：负责反序列化
使用示例：
```java
	// 可序列化的类
	public class Account implements Serializable{
		private String name;
		public void setName(name){
			this.name = name;
		}
	}

	// 序列化
	Account account = new Account();
	account.setName("John");
	ObjectOutputStream oos = null;
	try{
		oos = new ObjectOutputStream(new FileOutputStream("f:/test.txt"));
		oos.writeObject(account);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
		try{
			oos.close();
		}
		catch(Exception e2){
			e2.printStackTrace();
		}
	}

	// 反序列化
	ObjectInputStream ois = null;
	try{
		ois = new ObjectInputStream(new FileInputStream("f:/test.txt"));
		Account account = (Account)ois.readObject();
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
		try{
			ois.close();
		}
		catch(Exception e2){
			e2.printStackTrace();
		}
	}
```

### 标识不被序列化的字段 ###
1. 默认情况下，类的所有字段将会被序列化。
2. 通过`transient`（不能与`static`一起用）标识的实例成员字段将不被序列化；
3. 通过`static`标识的类成员字段，因不属于实例成员字段，所以也不会被序列化。

### 序列化编号 ###
1. 默认情况下Java会为序列化每个类生成独立序列化编号，用于标识类与序列化后的二进制流的对应关系。因此当项目中没有二进制流内的序列化编号时在执行`ois.readObject()`就会报错。
2. 自定义序列化编号
```java
	public class Account implements Serializable{
		// 序列化编号
		private static final long serialVersionUID = xxxxxxxxxxxxxL;

		// 其他成员字段
		private String name; 
	}
```

## 线程、线程通讯 ##
`Runtime.getRuntime().avaliableProcessor()`：获取CPU核数
### 启动线程 ###
1. 继承`Thread`类并重写`run`方法
```java
	public class Test extends Thread{
		@Override
		public void run(){
			/* 线程执行主体 */
			// 获取当前正在运行的线程
			Thread.currentThread();
		}
	}

	// 启动线程
	public static void Main(String[] args){
		new Test().start();
	}
```
2. (推荐使用)实现`Runnable`接口并重写`run`方法
```java
	public class Test implements Runnable{
		@Override
		public void run(){
			/* 线程执行主体 */
			// 获取当前正在运行的线程
			Thread.currentThread();
		}
	}
	
	// 启动线程
	public static void Main(String[] args){
		new Thread(new Test()).start();
	}
```
3. （Runnable增强版）实现`Callable`接口并重写`call`方法（有返回值，可声明抛出异常）
```java
	public calss Test implements Callable<Integer>{
		@Override
		public Integer call() throws Exception{
			/* 线程执行主体 */
			// 获取当前正在运行的线程
			Thread.currentThread();
		}
	}

	// 启动线程
	public static void Main(String[] args){
		new Thread(new FutureTask<Integer>(new Test())).start();
	}
```
**使用接口的好处**
>1. 类可继承其他类；
>2. 让多个线程共享同一个Runnable对象。更好地实现代码与数据分离。

### 线程状态 ###
1. 新建
2. 就绪
3. 运行
4. 阻塞：调用`sleep()`，IO阻塞、等待同步锁、等待通知等将进入阻塞状态。
5. 死亡：线程执行完成，或抛出未捕获的异常。

### 控制线程的方法 ###
1. `join`：在多线程并发执行时，调用`join`的线程的状态将立即转换为运行，并且该线程运行完后才释放CPU时间片给其他线程。
```java
	public class Test extends Thread{
		@Override
		public void run(){
			for(int i = 0, len = 100; i < len; ++i){
				System.out.println(Thread.currentThread().getName() + ": " + i);
			}
		}
	}

	// 运行
	public static void Main(String[] args){
		Thread t1 = new Test();
		for(int i = 0, len = 100; i < len; ++i){
			System.out.println("Main: " + i);
			if (i == 10){
				t1.start();
				t1.join(); // 执行完t1才继续执行Main线程
			}
		}
	}
```
2. `setDaemon(true)`：设置后台线程（也叫守护线程、精灵线程），所有前台线程死亡，那么后台线程也会死亡。
3. `sleep(100)`：让线程暂停100ms，线程进入阻塞状态
4. `yield()`：让线程从运行状态转换到就绪状态
5. `setPriority(Thread.MIN_PRIOPRITY)`：设置线程优先级，优先级高则获取CPU时间片机会多。

### 线程同步 ###
当多个线程访问同一个资源时（竞争资源或共享资源），就会出现一段访问共享资源的代码，当一个线程执行了一部分代码后出现线程切换，另一个线程又执行了这段代码，当切换回原来的线程继续执行时，之前的执行结果已被修改了。
通过**同步监视锁(Java实现语言层面的管程（又称为信号量SEMAPHORE或互斥体Mutex）)**实现线程安全，具体方法：
Java的对象从Object类中继承了隐式管程（线程互斥独占的对象），通过关键字synchronized标识线程将进入对象的管程。
1. 同步代码块（将竞争资源作为同步监视锁）
```java
    // 注意：account必须为对象，表示线程需要进入account对象的管程后才能执行该代码块
	synchronized(account){
		account.setBalance(111);
		System.out.println(account.getBalance());
	}
```
2. 同步方法（对于实例方法，则自动将this作为同步监视锁；对于静态方法，则将类作为同步监视锁）
```java
	public synchronized void draw(){
		System.out.println(account.getBalance()); 
	}
```
跳出同步监视锁的情况：
>1. 同步代码或方法执行完成
>2. 代码中遇到break、return
>3. 抛出未捕获异常
>4. 调用了`wait()`方法
*注意：*调用`sleep()`和`yield()`是不会跳出同步监视锁的。

### 线程组 ###
用于统一管理一批线程。
1. 创建
```java
	ThreadGroup tg = new ThreadGroup();
	Thread t1 = new Thread(tg);
```
2. 设置后来加入到线程组的线程为后台线程（不影响线程组现有的线程）
```java
	tg.setDaimon(true)；
```
3. 设置后来加入到线程组的线程的优先级（不影响线程组现有的线程）
```java
	tg.setMaxPriority(优先级);
```

### 线程池 ###
#### 1. 手写线程池 ####
#### 2. JDK自带线程池 ####
1. 相关类型
1.1. 根接口`Executor`
&emsp;方法：`execute(Runnable task)`，执行`Runnable`类型的任务。
1.2. 子接口`ExecutorService implements Executor`
&emsp;方法：`shutdown()`：关闭线程
&emsp;方法：`awaitTemination(int delay, TimeUnit.SECONDS)`：等指定的时间，到时间或线程池关闭后退出阻塞状态。
&emsp;方法：`isTerminated()`：判断线程池是否已经关闭
1.3. 静态工厂类`Executors`，通过下列静态方法生成`ExecutorService`实例
&emsp;静态方法：`newCachedThreadPool()`，有任务时才创建新线程，空闲线程被保留60秒；
&emsp;静态方法：`newFixedThreadPool(int nThreads)`，线程池包含固定的nThreads个线程，且空闲线程将被一直保留；
&emsp;静态方法：`newSingleThreadExecutor()`，仅有一个线程的线程池；
&emsp;静态方法：`newSheduledThreadPool(int corePoolSize)`，允许用户设定几乎执行任务的时间，corePoolSize设定线程池线程的最小数目，当任务增多时会自增长；
&emsp;静态方法：`newSingleThreadScheduledExecutor()`，线程池中仅有一个线程，且可按时间计划来执行任务；

1. 创建线程池
通过`Executors`静态工厂类创建`ExecutorService`或`ScheduledExecutorService`类型的线程池。
```java
	ExecutorService es = Executors.newFixedThreadPool(10);
```
或
```java
	ScheduledExecutorService es = Executors.newScheduledThreadPool(10);
```
2. 启动线程
调用线程池的`submit`或`scheduleAtFixedRate`方法启动线程
```java
	es.submit(new ThreadTest());
	// 或
	es.execute(new ThreadTask());
```
或
```java
	// 延迟5s,以后每隔2s执行一次run方法  
    es.scheduleAtFixedRate(new ThreadPoolTest(), 5, 2, TimeUnit.SECONDS);  
```
3. 关闭线程池
调用线程池的`shutdownNow`方法关闭线程池
```java
	es.shutdownNow();
```
```java
	es.shutdown(); // 请求线程池关闭线程池，此时线程池不在接收新任务，等已接收的任务完成后就会自行关闭。
```

### 等待线程结束 ###
1. `isAlive()`：用于判断线程是否存活，线程处于创建、结束状态时返回`false`，其他状态返回`true`。
通过while/isAlive/sleep来等待线程结束
```java
	Thread t1 = new Thread();
	Thread t2 = new Thread();
	t1.start();
	while (t1.isAlive()){
		t1.sleep(1000);
	}
	t2.start();
```
2. `join()`

### 线程通讯 ###
Java中的每个对象内部不仅有一个对象锁，还有一个线程等待队列，用于存放等待对象锁的线程。
Object的`wait()`、`notify()`和`notifyAll()`就是用来将当前线程加入到对象的等待队列和唤起等待队列中的线程使用该对象。
注意：`wait()`必须在同步方法或同步语句块中使用，而一般使用`notifyAll()`唤醒其他线程。

### 不建议使用的方法 ###
1. 线程的`stop()`：会导致线程结束且释放该线程上的对象锁，但因使得线程的状态不连贯，从而导致错误难以排查；
2. 线程的`suspend()/resume()`：会导致线程阻塞，但不会释放该线程上的对象锁，导致其他线程无法使用该对象，且只能通过原线程的`resume()`方法才能唤起该线程，极不安全。
3. 线程的`destroy()`：会强制终止线程，且不释放对象锁。

### ThreadLocal的使用 ###
与其他局部成员不同，每个线程只能拿到ThreadLocal变量的一个副本。
示例：
```java
	public class MyBean{
		private ThreadLocal<Integer> i = new ThreadLocal<Integer>(){
			@override
			protected Integer initalValue(){
				return 0;
			}
		};

		public Integer getNextNum(){
			i.set(i.get() + 1);
			return i.get();
		}
	}
	
	public class MyThread extends Thread{
		private MyBean myBean;
		public MyThread(MyBean myBean){
			this.myBean = myBean;
		}

		public void run(){
			for (int i = 0; i < 3; ++i){
				System.out.println(Thread.currentThread().getName() + "\t" + myBean.getNextNum());
			}
		}
	}

	// 程序入口
	public static void main(String[] args){
		MyBean myBean = new MyBean();
		MyThread t1 = new MyThread(myBean);
		MyThread t2 = new MyThread(myBean);
		MyThread t3 = new MyThread(myBean);
		t1.start();
		t2.start();
		t3.start();
	}
```


## 网络通讯 ##
端口的约定：(0~65535个端口)
0~1023： 公用端口。80(HTTP)、21(FTP)、110(POP)...
1023~49152：应用程序端口。MySQL：3306；Oracle：1521
49152~65535：动态分配端口。

### 基础类 ###
1. `InetAddress`：表示IP地址，`Inet4Address`和`Inet6Address`是子类。
```java
	InetAddress addr = InetAddress.getByAddress(new byte[]{ (byte)10, (byte)248, (byte)49, (byte)5 });
	System.out.println(addr.getHostAddress());
	
	InetAddress local = InetAddress.getLocalHost();
	InetAddress addr2 = InetAddress.getByName("222.12.5.7");

	// 类似ping命令
	System.out.println(addr.isReachable(3000));
```
2. `InetSocketAddress`：表示IP地址+端口号。
3. `URLEncoder`、`URLDecoder`：用于对非西欧文字进行编码
4. `URL`：代表一个网络地址
```java
	URL url = new URL("http://localhost:8080/default.jsp");
	System.out.println("协议：" + url.getProtocol());  
    System.out.println("主机：" + url.getHost());  
    System.out.println("端口：" + url.getPort());  
    System.out.println("资源文件：" + url.getFile());  
```
5. `URLConnection`：代表与网络地址的连接，而`HttpURLConnection`则表示协议为HTTP的网络连接。
```java
	 // 当我们的协议用的是http时,打开的连接实际上就是HttpURLConnection  
     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
     conn.setRequestProperty("请求头键", "请求头值");
	 conn.setDoInput(true); // 设置输入重定向
     conn.setDoOutput(true); // 设置输出重定向 
     conn.connect();// 建立于远程服务器的连接  
```

### Socket ###
1. Socket是传输层向应用层提供的编程接口，封装了传输层及其底层的细节，从而专注于应用层编程。
2. Socket的关闭是用于释放Socket接口所占用的资源，所以服务端和客户端在使用完后均要关闭Socket
3. `Socket.close()`发出释放资源的信号，并不一定立即关闭底层Socket的。

### Socket选项(和ServerSocket有别) ###
1. TCP_NODELAY：立即发送数据
&emsp;默认情况下采用Negale算法发送数据，Negale算法是将发送数据缓存起来，等到缓存池满后再发送，然后等待接收方响应后再发送下一批数据。适合发送方需发送大量数据，且接收方实时响应的场景；
&emsp;立即发送是发送方持续地发送小批量的数据，并且接收方不一定会实时响应。
`void setTcpNoDelay(boolean tcpNoDelay)`： 设置立即发送数据
`boolean getTcpNoDelay()`：获取是否立即发送
注意：若Socket底层不支持TCP_NODELAY选项，那么就会抛出`SocketException`
2. SO_REUSEADDR：重用Socket所绑定的本地地址
&emsp;*现在的情况：*在调用`socket.close()`后，只释放资源而底层的Socket并不会释放所占端口，而是会等一阵确保收到网络上发送过来的延迟数据，但不会将这些延迟数据提交到应用层处理。这样做是确保下一个使用该端口的进程不会接受到上一个进程的延迟数据；
&emsp;*为何要重用Socket端口*：当一个进程关闭Socket后，另一个进程马上绑定到该端口，就会抛异常；
&emsp;*如何使用：*先设置`setReuseAddress(true)`，再调用`socket.bind()`绑定url和端口即可。
```java
	Socket socket = new Socket();
	socket.setReuseAddress(true);
	SocketAddress localAddr = new InetSocketAddress("localhost",9000);
	socket.bind(localAddr);
```

3. SO_TIMEOUT：设定接收数据的等待超时时间，单位ms，默认值0（表示无限等待）
&emsp;*接收数据时，满足下列条件就会退出等待状态`socket.getInputStream().read(buffer)`*：
&emsp;1. `read()`把输入流的数据读入到buffer中，并返回读取的字节数；
&emsp;2. 输入流被关闭（调用了输入流的`shutdown()`或`Socket.close()`），`read()`读取输入流的末尾，并返回-1； 
&emsp;3. 在输入流未被关闭的情况下断开连接（发送方进程被杀死），抛出`IOException`
&emsp;4. 设置了SO_TIMEOUT，超时就会抛出`SocketTimeoutException`
&emsp;*相关方法*:
```java
	socket.getTimeout();
	socket.setTimeout(Integer timeout);
```

4. SO_LINGER：设置调用`socket.close()`时，是否立即关闭底层Socket。
&emsp;*现状*：发送方调用`socket.close()`时不会立即调用，而是等到所有剩余的数据都被接收完毕才关闭底层Socket。
&emsp;*立即关闭底层Socket*：`socket.setSoLiner(true, 0)`，该方法立即返回，且关闭底层Socket，未被接收的数据将被丢弃；
&emsp;*阻塞等待后再关闭底层Socket*：`socket.setSoLiner(true, 3600)`，该方法会阻塞，要么剩余数据接收完毕，要么超时才会推退出阻塞状态。
&emsp;*相应方法*：
```java
	setSoLiner(boolean on, int seconds);
```
5. SO_SNFBUF：表示发送数据的缓冲区大小。若Socket底层不支持SO_SNFBUF，则在调用`setSendBufferSize()`时会抛`SocketException`
*相应方法*：
```java
	public void setSendBufferSize(int size);
	public void getSendBufferSize();
```
*当发送数据缓冲区容量满时*：当容量不足时，调用`socket.write()`以阻塞方式地发送数据，那么就会一直阻塞程序运行，直到底层的发送数据缓冲区可容纳`socket.write()`的数据为止，那么程序才会继续执行。
6. SO_RCVBUF：表示接收数据的缓冲区大小。若Socket底层不支持SO_RCVBUF，则在调用`setReceiveBufferSize()`时会抛`SocketException`
*相应方法*：
```java
	public void setReceiveBufferSize(int size);
	public void getReceiveBufferSize();
```
7. SO_KEEPALIVE：表示对长时间空闲的Socket，是否自动关闭
&emsp;*现状*：默认SO_KEEPALIVE为false，表示TPC不会监视链接是否有效，让不活动的客户端一直存活；
&emsp;*SO_KEEPALIVE为true时*:当连接处于空闲状态（无数据传输）超过2h，服务端的TCP会发送一个数据包给客户端的Socket，持续11分钟（不同平台的时间不同）上述操作，若依然没有回应则释放连接。
&emsp;*相应方法*：
```java
	public void setKeepAlive(boolean on);
	public boolean getKeepAlive();
```
8. OOBINLINE：表示是否支持发送一个字节的TPC紧急数据
&emsp;*现况*：OOBINLINE默认是false，这时接收方收到紧急数据时不作任何处理，直接丢弃。
&emsp;*需发紧急数据包时*：服务端调用`socket.setOOBInline(true)`，客户端调用`socket.sendUrgentData(int data)`，那么客户端发送的数据会被服务端接收并放在和普通数据一样的队列中，处理方式和普通数据一样，所以需要应用层对普通数据和紧急数据区别处理。

### 客户端请求的服务类型选项 ###
1. *4种服务类型*：低成本（发送成本低）、高可靠性（保证把数据可靠地送达目的地）、最高吞吐量（一次可以接收或发送大批量的数据）和最小延迟（传输数据的速度快）；
2. *相关方法*：
```java
	// 设置服务类型
	public void setTrafficClass(int trafficClass) throws SocketException
	// 读取服务类型
	public int getTrafficClass() throws SocketException
```
3. *服务类型和对应的值*：
低成本：0x02
高可靠性：0x04
最高吞吐量：0x08
最小延迟：0x10
4. *示例*：`socket.setTrafficClass(0x04|0x10); //高可靠性和最小延迟传输服务`

### JDK5中设置连接时间、延迟和带宽的相对重要性 ###
`public void setPerformancePreference(int connectionTime, int latency, int bandWidth)`
`connectionTime`：表示用最小时间建立连接
`latency`：表示最小延迟
`bandWidth`：表示最高带宽
数值越大，重要性越高。

### TCP协议的Socket编程 ###
1. `java.net.Socket`：用于创建客户端的套接字对象
1.1. `getPort()`和`getLocalPort()`：获取链接的远程端口和本地端口；
1.2. `getInputStream()`和`getOutputStream()`：获取Socket对象的输入流和输出流；
1.3. `close()`：关闭Socket对象。

2. `java.net.ServerSocket`: 用于创建服务端的监听
2.1. `new ServerSocket(int port, int backlog, InetAddress bindAddr)`:创建`ServerSocket`对象，`port`表示监听的端口号（0表示任意空闲端口），`backlog`表示请求等待队列的最大数目，`bindAddr`表示绑定到本机的IP地址（但服务器有多个IP时可以通过这个绑定IP）
2.2. `accept()`：等待并接收客户端的链接请求，然后将与客户端的链接封装成一个Socket对象返回。该方法为阻塞方法；
2.3. `close()`：用于关闭`ServerSocket`对象；
2.4. `getLocalPort()`：获取监听的端口号；
2.5. `getInetAddress()`：获取客户端IP地址；

#### ServerSocket用法详解 ####
线程池（java.util.concurrent）：服务端进程向工作队列加入与客户端通信的任务，工作线程不断从工作队列中取出任务并执行。
1. 客户连接请求队列
1.1. *原理*：当客户端发起请求时，服务端会将请求存储在一个FIFO队列中，一般OS会限定队列的最大长度为50。当队列塞满后，服务端会拒绝后来的客户端请求（客户端发起连接时会抛`ConnectionException`异常），但服务端调用`socket.accept()`时就会从请求队列中取出队首的请求对象（socket），那么服务端就可以继续接收其他客户端请求了。
1.2. *设置连接请求队列长度*：在实例化`ServerSocket`时通过入参backlog设置。若backlog的值大于操作系统限定值 或 小于等于0 或 没有设置backlog时就采用操作系统限定的队列长度。
2. 关闭ServerSocket
&emsp;服务端进程关闭后自然会释放占用的端口，所以不调用`ServerSocket.close()`也可以，但如
想马上释放端口则需调用`ServerSocket.close()`.
3. SO_TIMEOUT
&emsp;通过`setSoTimeout(int timeout)`设置调用`accept()`的等待时间，若超时则抛`SocketTimeoutException`
4. 线程池（每个线程大概占1M的内存），好处：减少线程创建、销毁的开销；固定数量的线程数减少内存占用率；固定数量的线程数减少破坏系统固定切换线程周期，降低切换线程的开销。

### 非阻塞通信 ###
&emsp;由`java.nio`下的`ServerSocketChannel`、`SocketChannel`、`Selector`、`SelectionKey`和`ByteBuffer`等提供非阻塞通信


### UDP协议的Socket编程 ###
`DatagramSocket`：表示用于发送和接收数据报的套接字
`DatagramPacket`：用于装载数据报。
`MulticastSocket`：广播
1. 服务端
```java
	DatagramSocket datagramSocket = new DatagramSocket(SERVER_PORT);  
    DatagramPacket datagramPacket = new DatagramPacket(new byte[PACKET_SIZE],  
            PACKET_SIZE);  
    // 使用一个空的packet去装datagramSocket接收到的数据  
    datagramSocket.receive(datagramPacket);  
    // 将datagramPacket中接收到的字节数组转换为字符串,然后输出  
    System.out.println(new String(datagramPacket.getData(), 0,  
            datagramPacket.getLength()));  
```
2. 客户端
```java
	// 发送数据报  
    datagramSocket.send(datagramPacket);  
	DatagramSocket ds = new DatagramSocket();
	DatagramPacket dp = new DatagramPacket(content.getBytes(), content  
                    .getBytes().length, InetAddress.getByAddress(new byte[] {  
                    (byte) 192, (byte) 168, 0, 8 }), SERVER_PORT);
	 // 发送数据报  
     datagramSocket.send(datagramPacket);  
```

## 数据库操作 ##
通过JDBC操作数据库
1. 加载JDBC驱动程序
`Class.forName("com.mysql.jdbc.Driver")`
2. 建立数据库
`Connection conn = DriverManager.getConnection(url, user, password)`


## JSON处理 ##
使用`org.json`

## 执行cmd命令 ##
使用`Runtime.getRuntime().exec`方法
1. 基本示例
```java
	Runtime run = Runtime.getRuntime();
	try{
		run.exec("cmd /c dir c:"); // 执行完dir命令后关闭命令窗口
		// run.exec("cmd /k dir c:"); 执行dir命令后不关闭命令窗口
		// run.exec("cmd /c start dir c:"); 打开新窗口执行dir命令后关闭原来的命令窗口
		// run.exec("cmd /k start dir c:"); 打开新窗口执行dir命令后不关闭原来的命令窗口
	}
	catch(Exception e){
		e.printStackTrace();
	}
```
2. 获取输入流
```java
	Runtime run = Runtime.getRuntime();
	try{
		Process proc = run.exec("cmd /c dir c:"); // 执行完dir命令后关闭命令窗口
		InputSteam input = proc.getInputStream();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream(); // 作为自增长缓存区
		byte[] buf = new byte[1024];
		int count;
		while ((count = input.read(buf) != -1){
			buffer.write(buf, 0, count);
			buf = new byte[1024];
		}
		String echo = buffer.toString("GBK"); // java的默认编码是GBK
		buffer.close();
		input.close();

		System.out.println(echo);
	}
	catch(Exception e){
		e.printStackTrace();
	}
```

## java.nio.Charset编码 ##
```java
	Charset charset = Charset.forName("UTF-8"); // 指定编码字符集
	ByteBuffer encodeBuffer = charset.encode("hello"); // 编码
	CharBuffer decodeBuffer = charset.decode(encodeBuffer); // 解码
```

## IO读缓存区 ##
### 缓冲区
`Buffer`：
1. 缓冲区提高I/O操作效率的表现：
1.1. 减少实际的物理读写次数；
1.2. 缓冲区在创建时分配固定的内存区域，并一直重用，所以减少动态分配和回收内存区域的次数。
2. 成员属性：
2.1. `capacity`：容量，表示缓冲区最多可保存的数据长度；
2.2. `limit`：极限，表示缓冲区的当前终点。值从1开始，且小于等于`capacity`。不能操作大于缓冲区中位置大于`limit`的内容；
2.3. `position`：位置，表示缓冲区下一个读写单元的位置。其值小于`limit`值。
3. 静态构造方法
3.1. `ByteBuffer.allocate(int capacity)`：创建一个普通缓冲区；
3.1. `ByteBuffer.allocateDirect(int capacity)`：创建一个直接缓冲区，该缓冲区与OS耦合高，执行I/O操作效率更高，但创建时开销更大，所以只有长期存活和经常重用时才用这种缓冲区。
4. 成员方法
4.1.`clear()`：把`limit`设置为`capacity`的值，并把`position`设置为0；
4.2.`flip()`：把`limit`设置为`position`的值，并把`position`设置为0；
4.3.`rewind()`：把`position`设置为0；
4.4.`remaining()`：返回剩余可操作容量（`limit`-`position`）；
4.5.`compact()`：先删除0到`position`的内容，然后将`position`到`limit`的内容（先标识`limit`-`position`为len）从0开始粘贴，最后设置`position`为len，`limit`为`capacity`；
4.6.`get()`
4.6.`get(int index)`
4.6.`put()`
4.6.`put(int index)`


### 字符
`CharArrayWriter`
`CharBuffer`
### 字节
`ByteArrayOutputStream`
`ByteBuffer`: 继承自`Buffer`
### 其他
`DoubleBuffer`
`FloatBuffer`
`IntBuffer`
`LongBuffer`
`ShortBuffer`
`MappedByteBuffer`：继承`ByteBuffer`，可把缓冲区和文件的某个取悦直接映射。


## IO操作 ##
数据流的特点：
1. 先进先出，最先写入输出流的数据会最先被输入流读取；
2. 顺序存储，不能随机访问；
3. 数据流要么只读，要么只写，不能同时具备两种功能。

缓冲流（Buffer Stream）：
一个数据流配备一个内存缓冲区，向缓冲流写入或读取数据时，实际上就是向缓冲区写入和读取数据。在写数据时，当缓冲区被写满后就会清空缓冲区并将数据全部发送到对应的流中
；而读取数据时，当缓冲区为空，则系统会自动从流中读取尽量多的数据来填满缓冲区。

命名空间：java.io

### 分类 ###
从功能上分为 *节点流（Node Streams）* 和 *处理流（Processing Streams）*。
*节点流*：用于直接从指定的位置进行读/写操作（如磁盘文件系统、内存区域、网络连接等），功能较单一；
*处理流*：对其他输入/输出流进行封装，对内容进行过滤等处理，一般提供比较强大的读写方法。

从流处理的数据类型分为 *字节流* 和 *字符流*。
字节流：每次读写1个字节

字符流：每次读写1个字节
字符输入流抽象父类：java.io.Reader
子类（节点流）：
  CharArrayReader —— 从字符数组读取的输入流
  InputSteamReader —— 将字节转换到字符的输入流
			FileReader —— 从文件读取的输入流
  StringReader —— 从字符串读取的输入流
  PipeReader —— 输入管道
子类（处理流）：
  BufferReader —— 缓冲区读取的输入流
  			LineNumberReader —— 为输入的数据添加行号
  FilterReader —— 过滤输入流
  			PushbackReader —— 返回一个字符并把此字符返回到输入流
Reader的成员方法
void close() —— 关闭输入流
void mark() —— 标记输入流的当前位置
boolean markSupported() —— 测试是否支持mark操作
boolean ready() —— 输入流是否有可读数据
int read() —— 从输入流读取一个字符, 返回值为读取到的字符
int read(char[] ch) —— 从输入流读取一组字符， 返回值为读取到的字符数量
int read(char[] ch, int off, int len) —— 从输入流读取一组字符， 返回值为读取到的字符数量
void reset() —— 重定位输入流
long skip(long n) —— 跳过流内的n个字符

字符输入流抽象父类：java.io.Writer
子类（节点流）：
  CharArrayWriter —— 向字符数组写数据的输出流
  OutputSteamWriter —— 将字符转换到字节的输出流
			FileWriter —— 写入文件的输出流
  StringWriter —— 输出为字符串的输出流
  PrintWriter —— 包含print()和println的输出流
  PipeWriter —— 输出管道
子类（处理流）：
  BufferWriter —— 写入缓冲区的输出流
  FilterWriter —— 过滤输出流


### 字节流 ###
输入字节流的抽象父类：InputStream
子类（节点流）：
  FileInputStream
  PipeInputStream
  ByteArrayInputStream
  ObjectInputStream
子类（处理流）：
  SequenceInputStream —— 从两个或多个流联合的输入流按顺序读取
  FilterInputStream
  		LineNumberInputStream
  		DataInputStream —— 将输入流的数据转换为Java标准数据类型(boolean,int,String,long,byte等),读取到文件末尾时会抛出IOException的子类EOFException
  		BufferedInputStream
  		PushbackInputStream —— 读取一个字节并把此字节放回输入流
InputStream的成员函数
void close()
void mark()
void reset()
int read() —— 从输入流中当前位置读入一个字节的二进制数据，以此数据为低位字节，补足16位的整型量（0~255）后返回，若输入流中当前位置没有数据，则返回-1
int read(byte b[])
int read(byte b[], int off, int len)
long skip(long n)
boolean markSupported()
int avaliable() —— 返回可用的字节数

输出字节流的抽象父类：OutputStream
子类（节点流）：
  FileOutputStream
  PipeOutputStream
  ByteArrayOutputStream
  ObjectOutputStream
子类（处理流）：
  FilterOutputStream
  		DataOutStream —— 写Java标准数据类型到输出流
  		BufferedOutputStream
  		PrintStream —— 包含print()和println()的输出流
OutputStream的成员函数
void close()
void flush() —— 强制清空缓冲区并执行向外设输出数据
void write(int b) —— 将参数b低位字节写入输出流
void write(byte[] b)
void write(byte[] b, int off, int len)


`java.io.File`：用于对文件和目录进行操作。构造函数的入参是文件路径则操作文件，是目录路径则操作目录。
由于不同OS的目录分隔符不相同，因此可使用System.dirSep来获取平台相关的目录分隔符。

随机文件读写：
java.io.RandomAccessFile，可以跳转到文件的任意位置读写数据。
构造函数：
`RandomAccessFile(String name, String mode)`, name为文件路径，mode为访问模式（r表示以只读的方式打开，rw表示以读写的方式打开）
实例方法：
long length()，文件字节总数
void seek(long pos)，移动到文件的某个位置。pos为从文件开头的偏移字节数。
int skipBytes(int n)，n为希望跳过的字节数，返回值是实际跳过的字节数。
int read()，从文件中读取一个字节，文件结尾返回-1.
final byte readByte()
final char readChar()
final void writeChar(int c)

文件压缩/解压(处理流)
java.util.zip.GZipOutputStream和java.util.zip.GZipInputStream
java.util.zip.ZipOutputStream和java.util.zip.ZipInputStream
通过ZipEntry作为压缩包内部文件的分隔符，ZipEntry的name为文件的路径。




### 文件 ###
1. 文件描述：大小、占用空间、创建时间（仅windows存在）、修改时间

java.io.FileDescriptor
FileDescriptor static in, 键盘标准输入流
FileDescriptor static out, 屏幕标准输出流
FileDescriptor static err, 屏幕标准错误流
不能直接操作，要通过FileInputStream和FileOutputStream来操作


java.util.Vector(向量)
向量属于线性表结构，与数组的区别为容量可变。
只能存储对象类型，而不能存储简单数据类型。
线性安全






