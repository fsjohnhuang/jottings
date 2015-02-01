Java之父James Gosling博士<br/>

## Java技术体系包括如下部分
1. Java语言
2. 各硬件平台上的Java虚拟机
3. Class文件格式
4. Java API类库
5. 来自商业机构和开源社区的第三方Java类库
1,2和4统称为JDK（Java Development Kit），Java程序开发的最小环境。<br/>
2和4中的Java SE API子集称为JRE（Java Runtime Environment）。<br/>

strictfp关键字

transient关键字

RMI通信协议（通过CORBA IIOP实现）

JDK1.4发布正则表达式、异常链、NIO、日志类、XML解析器和XSLT转换器等。<br/>
JDK1.5发布自动装拆箱、泛型、动态注解、枚举、可边长参数、foreach循环等语法，改进JMM（Java Memory Model）和提供了java.util.concurrent并发包等。window9x的最后一个版本。<br/>
JDK1.6发布动态语言的支持（内置Mozilla JavaScript Rhino引擎）、编译API和微型HTTP服务器API等。JVM中锁与同步、GC和类加载的算法进行大改。<br/>
JDK1.7发布G1收集器，加强对非Java语言的调用支持和升级类加载架构等。<br/>
JDK1.8发布Lambda表达式（FP）
JDK1.9将发布Jigsaw项目（虚拟机模块化支持），Coin项目（语言细节进化）<br/>

JRockit（BEA）和HotSpot（Sun）均被Oracle收购了。并打算合二为一为HotRockit<br/>
J9 VM是IBM开发的。<br/>
从JDK1.6才支持Mac OS<br/>

JavaOne大会首次举办于1996年5月底。<br/>

## Sun Classic/Exact VM
  Classic VM纯用解析器方式执行字节码。通过外挂的方式使用JIT编译器，因此解析器和编译器两者不能配合工作，所有代码（非热点代码）也会被JIT编译器编译为本地代码，由于是程序被调用时才对代码进行编译，因此为保证程序启动时间对于编译耗时的代码则无法使用更好的优化技术。因此编译出来的本地代码与C/C++程度的差很远。<br/>
  Exact VM采用二级即时编译器、编译器与解析器混合工作模式等。并且它使用准确式内存管理（Exact Memory Management，可以判断内存中存放的数据到底是reference类型的内存地址，还是值类型）。Classic VM基于句柄（handler）的对象方式来确定内存空间中数据的类型，因此GC时需要查找句柄速度自然比EMM慢。<br/>
  JDK1.2中默认使用Classic VM，需要通过`java-hotspot`来切换到HotSpot VM。而JDK1.3则将HotSpot VM作为默认VM，需要通过`java-classic`来切换。JDK1.4则将Classic VM移除了。<br/>

## Sun HotSpot VM
  采用准确GC（由于采用了准确内存管理），通过执行计数器对热点代码进行探测，然后以方法为单位对热点代码进行标准编译和OSR（栈上替换）编译。编译器与解析器协同工作。

## BEA JRockit
  应用于服务器硬件和服务端应用场景，因此JRockit VM内部不包含解析器，所有字节码均编译为本地代码执行。JRockit的GC和MissionControl服务套件处于领先水平。<br/>

## IBM J9
  与HotSpot VM相似，IBM WebSphere和AIX、z/OS上应用。<br/>

## Azul VM/ BEA Liquid VM
  针对特定硬件平台的VM，VM直接操作硬件。<br/>
  Azul VM是硬件Vega系统上的JVM，VM可以管理10+CPU和数百GB内存资源。Zing JVM则是软件版的Azul VM<br/>
  Liquid VM在Hypervisor系统的JRockit VM的虚拟化版本（虚拟化版本就是内部虚拟化一个操作系统了），直接控制硬件无需进行内核态/用户态的切换等。<br/>

## Apache Harmony / Google Android Dalvik VM
  TCK（Technology Compatibility Kit），若要宣布自己的运行平台“兼容与Java语言”，则必须通过TCK的兼容性测试。<br/>
  JCP（Java Community Process）组织。<br/>
  Apache Harmony 是 Apache下的一个虚拟机（不单单是Java虚拟机）。
  Dalvik VM并非Java虚拟机，它没有遵循JVM规范，无法直接执行Class文件，而是执行dex（Dalvik Executable）文件。不过Class文件可以被转化为dex文件，而且支持大部分的Java API。Android2.2开始支持JIT编译。Dalvik VM是基于寄存器架构的<br/>

## Microsoft JVM
   由于微软想将Java技术绑定在Windows平台，因此自行开发了在Windows平台下最好的JVM，但最后被Sun公司告了（最好赔付了10亿美元），因此在WindowsXP SP3中移除JVM。<br/>

## Java平台的发展
1. 模块化
  其中有OSGI技术和Jigsaw项目两个方向。OSGI（Java SE动态组件支持）有IBM主导。Jigsaw有Sun主导。<br/>
2. 混合语言
3. 多核并行
  JDK1.5引入的java.util.concurrent为粗粒度的并发框架，而JDK1.7引入的java.uitl.concurrent.forkjoin为总要扩充。<br/>
  Fork/Join模式为处理并行编程的经典方法。<br/>
4. 进一步丰富语法
5. 64位虚拟机
  由于64位虚拟机性能比32位低，占用内容空间也较大。而32为的虚拟机最大只能用4G内存，因此有企业通过虚拟集群等方式来使用超过4GB内存空间。<br/>
  JDK1.6 Update14后提供了普通对象指针压缩功能（-XX:+UseCompressedOops），但这个特性应该有VM的Ergonomics机制自动开启，但效果也不佳。<br/>


