原地址：http://blog.jamesdbloom.com/JVMInternals.html<br/>

##前言
  本文将介绍JVM内部架构。下图展示符合Java7规范的JVM内部主要组件。<br/>
![](JVM_Internal_Architecture_small.png)<br/>
  下面我们将上述组件分为线程相关和线程独立两种类型来介绍。<br/>
##目录
Thread
 &emsp;Per Thread
 &emsp;program Counter (PC)
 &emsp;Stack
 &emsp;Native Stack
 &emsp;Stack Restrictions
 &emsp;Frame
 &emsp;Local Variables Array
 &emsp;Operand Stack
 &emsp;Dynamic Linking
Shared Between Threads
 &emsp;Heap
 &emsp;Memory Management
 &emsp;Non-Heap Memory
 &emsp;Just In Time (JIT) Compilation
 &emsp;Method Area
 &emsp;Class File Structure
 &emsp;Classloader
 &emsp;Faster Class Loading
 &emsp;Where Is The Method Area
 &emsp;Classloader Reference
 &emsp;Run Time Constant Pool
 &emsp;Exception Table
 &emsp;Symbol Table
 &emsp;Interned Strings (String Table)

## Thread
  JVM允许进程包含多个并发的线程。Hotspot JVM中的Java线程与OS线程是一一对应的。当线程工作存储区（thread-local storage）、配置缓存（allocation buffers）、同步对象（synchronized objects）、栈和本地栈（stacks）和程序计数器（pragram counter）等Java线程相关的状态均准备好后，就会启动OS线程并有OS线程执行run函数。OS负责线程的调度。当以正常方式或异常抛出的方式退出run函数，OS线程均会判断当前Java线程的终止是否会导致进程的终止（进程的工作线程是否都终止了？），若要终止进程的化，则释放Java线程和OS线程所占的资源，否则就释放Java线程的资源，并回收OS线程。<br/>

### JVM System Threads  
  若你用过jconsole或其他调试工具，你会发现除了主线程外还存在数个有JVM创建的系统线程。Hotspot JVM的系统线程有这5个：<br/>
####1. VM thread（虚拟机线程）
  VM thread 用于为一些需要防止堆变化操作提供执行环境，当要执行防止堆变化的操作时，就是要求JVM启动安全点（safe-point）,此时将会暂停GC、线程栈操作、线程恢复和偏向锁解除。
####2. Periodic task thread（周期性任务线程）
  Periodic task thread负责定时事件（如interrupts），用于周期性执行计划任务。
####3. GC threads（垃圾回收线程）
  GC threads 负责不同类型垃圾回收活动。
####4. Compiler threads（编译器线程）
  Compiler threads用于在运行时将字节码编译为CPU本地代码。
####5. Signal dispatcher thread（信号量分发线程）
  Singal dispatcher thread用于接收发送给JVM的信号量，并将其分发到合适的JVM方法来处理。

### Per Thread
  每个线程的执行环境均有以下的组件。
####1. Program Counter(PC)（程序计数器）
  用于存放当前指令（或操作码）的地址，若该指令为本地指令那么PC为undefined。当执行完当前指令后PC会自增（根据当前指令的定义自增1或N）从而指向下一个指令的地址，那么JVM就可以知道接下来要执行哪个指令了。事实上PC存放的是方法区（Method Area）中的内存地址。
####2. Stack（堆栈）
  每个线程有自定独立的堆栈用于存放在该线程执行的方法。堆栈是一个后进先出（LIFO）的数据结构，元素称为栈帧（frame）。当将要在线程上执行某方法时，则需要将代表该方法的栈帧压栈，当方法执行完毕后（正常退出或抛出未处理的异常）则将栈帧弹栈。栈帧可能分配在堆上（heap），而堆栈并不需要连续的存储空间。
####3. Native Stack（本地堆栈）
  不是每种JVM都支持本地方法，对于支持本地方法的JVM它门会提供线程本地堆栈。若JVM实现了通过C链接模型（C-linkage Model）来实现JNI，那么本地堆栈实质就是C堆栈（入参顺序和返回值均与C程序一致）。本地方法一般都可以调用Java方法，此时会在Java的堆栈中压入一个栈帧并按执行Java方法的流程处理。
##### Stack Restrictions（堆栈约束）
  堆栈的容量有动态和固定两种。当栈帧数量大于堆栈容量时就会抛出StackOverflowError；当堆中没有足够内存来分配新栈帧时则抛出OutOfMemoryError。
####4. Frame（堆栈的元素——栈帧）
#####1. Local Varibles Array（局部变量表）
  局部变量表用于存放方法执行过程中this引用、方法入参和局部变量。对于静态方法而言方法参数从局部变量表的第一位开始（下标为0），对于实例方法而言方法参数从局部变量表的第二位开始（下标为1，第一位是this引用）。局部变量表内可包含以下类型数据，boolean/byte/char/long/short/int/float/double/reference/returnAddress。<br/>
  局部变量表的每个元素占32bit，每32bit称为1个slot。上述所支持的类型中除了long和double外均占1个slot，而它俩就占2个slot。
#####2. Operand Stack（操作数栈）
  在执行方法内部的字节码指令时需要使用操作数栈，大多数JVM的字节码指令是用于操作操作数栈（压栈、弹栈、赋值栈帧、栈帧互换位置或执行方法操作栈帧），实现数据在操作数栈和局部变量表之间频繁移动。示例如下：
````
//java code
int i;

// bytecode
0: iconst_0 // 将0压栈
1: istore_1 // 弹栈并将值赋值到局部变量表的第二个Slot槽中
````
#####3. Dynamic Linking（动态链接）
  每个栈帧均包含一个指向运行时常量池（runtime constant pool）的引用。通过运行时常量池来实现动态链接。<br/>
  C/C++的代码会被编译成一个一个独立的对象文件，并通过静态链接将对多个对象文件生成一个执行文件或dll类库。在链接阶段所有的符号引用会被直接引用取代，而直接引用则为相对于可执行文件的进程入口地址的相对地址。而Java的链接阶段是在运行时动态发生的。<br/>
  当将Java类编译成字节码时，所有对变量和方法的引用将被保存为常量池表中的一条条符号引用表项，这些符号引用为逻辑引用而不是指向物理内存地址的引用。JVM可以选择不同的时刻将符号引用转换为直接引用。一种是当class文件加载并验证通过后，这种称为静态处理（eager or static resolution）；另一种是在使用时才转换为直接引用，这种称为懒处理（lazy or late resolution）。对于字段通过绑定来处理，对于对象或类则通过将符号引用转换直接引用来识别，动态链接后原有的符号引用将被直接引用替换，因此对于同一个符号引用，动态链接的操作仅发生一次。假如直接引用的类还未加载，则会加载该类。而直接引用所包含的地址相对于变量和方法在运行时的地址。

## Shared Between Threads
### Heap（堆）
  堆用于在运行时分配对象和数组。由于栈帧的容量是固定的，因此无法将对象和数组等容量可变的数据存放到堆栈中，而是将对象和数组在堆中的地址存放在栈帧中从而操作对象和数组。由于对象和数组是存放在堆，因此需要通过垃圾回收器来回收它们所占的内存空间。垃圾回收机制将堆分成3部分：<br/>
  1. 新生代（再细分为初生空间和幸存空间）
  2. 老年代
  3. 永久代
### Memory Management（内存管理）
  对象和数组不能被显式地释放，必须通过垃圾回收器来自动回收。一般的工作步骤如下：  1. 新创建的对象和数组被存放在新生代;
  2. 次垃圾回收将会对新生代作操作，存活下来的将从初生空间移至幸存空间;
  3. 主垃圾回收（一般会导致应用的其他所有线程挂起），会将新生代的对象爱嗯挪动到老年代;
  4. 每次回收老年代对象时均会回收永久代的对象。当他们满的时候就会触发回收操作。
### Non-Heap Memory（非堆内存）
  非堆内存包含下列这些:<br/>
  1. 永久代
   1.1. 方法区
   1.2. 字符串区
  2. 代码缓存
   用于存放被JIT编译器编译为本地代码的方法。
### Just In Time (JIT) Compilation（JIT编译）
  Java的字节码是解析执行的，速度比CPU本地代码差远了。为了提高Java程序的执行效率，Oracle的Hotspot虚拟机将需要经常执行的字节码编译成本地代码并存放在代码缓存当中。Hotspot虚拟机会自动权衡解析执行字节码和将字节码编译成本地代码再执行之间的效率，然后选择最优方案。
### Method Area（方法区）
  方法区存放每个类的信息，具体如下：<br/>
  1. 类加载器引用
  2. 运行时常量池
    2.1. 数字常量
    2.2. 字段引用
    2.3. 方法引用
    2.4. 属性
  3. 字段数据，每个字段包含以下信息
    3.1. 名称
    3.2. 类型
    3.3. 修饰符
    3.4. 属性
  4. 方法数据，每个方法包含以下信息
    4.1. 名称
    4.2. 返回值类型
    4.3. 入参的数据类型（保持入参的次序）
    4.4. 修饰符
    4.5. 属性
  5. 方法代码，每个方法包含以下信息
    5.1. 字节码
    5.2. 操作数栈容量
    5.3. 局部变量表容量
    5.4. 局部变量表
    5.5. 异常表，每个异常表项包含以下信息
       5.5.1. 起始地址
       5.5.2. 结束地址
       5.5.3. 异常处理代码的地址
       5.5.4. 异常类在常量池的地址
  所有线程均访问同一个方法区，因此方法区的数据访问和动态链接操作必须是线程安全才行。假如两个线程试图访问某个未加载的类的字段或方法时，则会先挂起这两个线程，等该类加载完后继续执行。
### Class File Structure（文件结构）
  字节码文件结构如下：<br/>
````
ClassFile {
    u4			magic;
    u2			minor_version;
    u2			major_version;
    u2			constant_pool_count;
    cp_info		contant_pool[constant_pool_count – 1];
    u2			access_flags;
    u2			this_class;
    u2			super_class;
    u2			interfaces_count;
    u2			interfaces[interfaces_count];
    u2			fields_count;
    field_info		fields[fields_count];
    u2			methods_count;
    method_info		methods[methods_count];
    u2			attributes_count;
    attribute_info	attributes[attributes_count];
}
````
magic、minor_version、major_version：用于声明JDK版本<br/>
constant_pool：类似符号表，但包含更多的信息<br/>
access_flags：存放该类的描述符列表<br/>
this_class：指向constant_pool中CONSTANT_Class_info类型常量的索引，该常量存放的是符号引用到当前类（如org/jamesdbloom/foo/bar）<br/>
super_class：指向constant_pool中CONSTANT_Class_info类型常量的索引，该常量存放的是符号引用到超类（如java/lang/Object）<br/>
interfaces：一组指向constant_pool中CONSTANT_Class_info类型常量的索引，该类常量存放的是符号引用到接口<br/>
fields：字段表，一个表项代表一个字段，表项的子项信息均有constant_pool提供。<br/>
methods：方法表，一个表项代表一个方法，表项的子项信息均有constant_pool提供。<br/>
attributes：属性表，表项用于类提供额外的信息。java代码中通过注解（约束为RetentionPolicy.CLASS或RetentionPolicy.RUNTIME的annotation）提供<br/>
通过`javap`命令我们可以查看解析后的字节码<br/>
````
// java
package org.jvminternals;

public class SimpleClass {

    public void sayHello() {
        System.out.println("Hello");
    }

}

// shell or cmd
javap -v -p -s -sysinfo -constants classes/org/jvminternals/SimpleClass.class

// Bytecodes
public class org.jvminternals.SimpleClass
  SourceFile: "SimpleClass.java"
  minor version: 0
  major version: 51
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #6.#17         //  java/lang/Object."<init>":()V
   #2 = Fieldref           #18.#19        //  java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #20            //  "Hello"
   #4 = Methodref          #21.#22        //  java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #23            //  org/jvminternals/SimpleClass
   #6 = Class              #24            //  java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lorg/jvminternals/SimpleClass;
  #14 = Utf8               sayHello
  #15 = Utf8               SourceFile
  #16 = Utf8               SimpleClass.java
  #17 = NameAndType        #7:#8          //  "<init>":()V
  #18 = Class              #25            //  java/lang/System
  #19 = NameAndType        #26:#27        //  out:Ljava/io/PrintStream;
  #20 = Utf8               Hello
  #21 = Class              #28            //  java/io/PrintStream
  #22 = NameAndType        #29:#30        //  println:(Ljava/lang/String;)V
  #23 = Utf8               org/jvminternals/SimpleClass
  #24 = Utf8               java/lang/Object
  #25 = Utf8               java/lang/System
  #26 = Utf8               out
  #27 = Utf8               Ljava/io/PrintStream;
  #28 = Utf8               java/io/PrintStream
  #29 = Utf8               println
  #30 = Utf8               (Ljava/lang/String;)V
{
  public org.jvminternals.SimpleClass();
    Signature: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
        0: aload_0
        1: invokespecial #1    // Method java/lang/Object."<init>":()V
        4: return
    LineNumberTable:
        line 3: 0
    LocalVariableTable:
        Start  Length  Slot  Name   Signature
          0      5      0    this   Lorg/jvminternals/SimpleClass;

  public void sayHello();
    Signature: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
        0: getstatic      #2    // Field java/lang/System.out:Ljava/io/PrintStream;
        3: ldc            #3    // String "Hello"
        5: invokevirtual  #4    // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        8: return
    LineNumberTable:
        line 6: 0
        line 7: 8
    LocalVariableTable:
        Start  Length  Slot  Name   Signature
          0      9      0    this   Lorg/jvminternals/SimpleClass;
}
````
字节码显示三个主要的区域：常量池、构造函数和sayHello方法。<br/>
**常量池**：提供类似于符号表的信息。<br/>
**方法**：每个方法均含四个区域<br/>
1. 签名和访问标志；
2. 方法体的字节码；
3. 行号表：为调试器提供Java代码与字节码的行号映射关系信息。
4. 局部变量表：罗列当且当前方法的所有局部变量名。
### Classloader（类加载器）
  JVM启动时通过bootstrap classloader加载初始类。在执行`public static void main(String[])`方法前，这个类需要经过链接、初始化操作。然后在执行这个方法时就会触发其他类和接口的加载、链接和初始化操作。<br/>
  **加载**，通过特定的名称搜索类或接口文件，并将其内容加载为字节数组。（译者语：这里加载的工作已经完成了，后面内容是加载+链接的内容）然后字节数组被解析为符合Java版本号的类对象（如Object.class），而该类或接口的直接父类和直接父接口也会被加载。<br/>
  **链接**，由验证Class文件合法性、准备和可选的解析三个步骤组成。<br/>
     1. **验证**，就是要根据Java和JVM规范对类或接口字节码的格式和语义进行校验。下面罗列部分校验项：
        1.1. 符号表具有一致和合法的格式；
        1.2. 不可更改的方法和类没有被重写；
        1.3. 方法含有效的访问控制关键字；
        1.4. 方法含有效的入参类型和数目；
	1.5. 字节码没有对操作数栈进行非法操作；
	1.6. 变量先初始化后使用；
	1.7. 变量值与变量类型匹配。
        在类加载阶段进行验证虽然会减慢加载速度，但可以减少运行时对同一类或接口进行重复验证。<br/>
     2. **准备**，为静态字段、静态方法和如方法表等JVM使用的数据分配内存空间，并对静态字段进行初始化。但这个时候该类或接口的构造函数、静态构造函数和方法均没有被执行。<br/>
     3. **解析（可选项）**，检查符号引用并加载所引用的类或接口（加载直接父类和直接接口）。当没有执行这一步骤时，则在运行时中调用这个类或接口时在执行。<br/>
   **初始化**，执行类的静态构造函数<clinit>。
![](Class_Loading_Linking_Initializing.png)<br/>
   JVM中有多个不同类型的类加载器。bootstrap classloader是顶层的类加载器，其他类加载器均继承自它。<br/>
   1. **Bootstrap Classloader**，由于在JVM加载时初始化，因此Bootstrap Classloader是用C++编写的。用于加载Java的核心API，如rt.jar等位于boot类路径的高信任度的类，而这些类在链接时需要的校验步骤比一般类要少不止一点点。<br/>
   2. **Extenson Classloader**，用于加载Java的扩展APIs。<br/>
   3. **System Classloader**，默认的应用类加载器，用于从classpath中加载应用的类。<br/>
   4. **User Defined Classloaders**，应用内部按一定的需求将对类分组加载或对类进行重新加载。
![](class_loader_hierarchy.png)<br/>
### Faster Class Loading（更快的类加载）
从HotSpot5.0开始引入了共享类数据（CDS）特性。在安装JVM时则会将如rt.jar中的类加载到一个内存映射共享文档中。然后各JVM实例启动时直接读取该内存中的类，提高JVM的启动速度。<br/>
### Where Is The Method Area（方法区在哪？）
《Java Virtual Machine Specification Java SE 7 Edition》明确声明：“虽然方法区逻辑上位于堆中，简单的实现方式应该是被垃圾回收。”矛盾的是Oracle JVM的jconsole告知我们方法区和代码缓存是位于非堆内存空间中的。而OpenJDK则将代码缓存设置为虚拟机外的ObjectHeap中。<br/>
### Classloader Reference（类加载器引用）
每个类都持有一个指向加载它的类加载器指针，同样每个类加载都持有一组由它加载的类的指引。
### Run Time Constant Pool（运行时常量池）
  每个类都对应一个运行时常量池（有Class文件中的常量池生成）。运行时常量池与符号表类似但包含更多的信息。字节码指令中需要对数据进行操作，但由于数据太大无法直接存放在字节码指令当中，于是通过将数据存放在常量池，而字节码指令存放数据位于常量池的索引值来实现指令对数据的操作。动态链接也是通过运行时常量池来实现的。<br/>
  运行时常量池包含以下的类型的数据：<br/>
  1. 数字字面量；
  2. 字符串字面量；
  3. 类引用；
  4. 字段引用；
  5. 方法引用。
举个栗子：
````
// java
Object foo = new Object();

// bytecodes
0: 	new #2 		    // Class java/lang/Object
1:	dup
2:	invokespecial #3    // Method java/ lang/Object "<init>"( ) V
````
`new`操作码后的#2操作数就是常量池第2项的索引，该项为类型引用，内含一个缩略UTF8类型的常量来存放类的全限定名（java/lang/Object）。在进行动态符号链接时则通过该名称来查找类对象`java.lang.Object`。而`new`操作码会创建一个类的实例、初始化实例的字段，并将该对象压入操作数栈。`dup`复制栈顶元素并压栈，然后`invokespecial`则弹出操作数栈顶的一个元素执行对象的构造函数。<br/>
再举个栗子：<br/>
````
// java
package org.jvminternals;

public class SimpleClass {

    public void sayHello() {
        System.out.println("Hello");
    }

}

// Bytecodes
Constant pool:
   #1 = Methodref          #6.#17         //  java/lang/Object."<init>":()V
   #2 = Fieldref           #18.#19        //  java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #20            //  "Hello"
   #4 = Methodref          #21.#22        //  java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #23            //  org/jvminternals/SimpleClass
   #6 = Class              #24            //  java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lorg/jvminternals/SimpleClass;
  #14 = Utf8               sayHello
  #15 = Utf8               SourceFile
  #16 = Utf8               SimpleClass.java
  #17 = NameAndType        #7:#8          //  "<init>":()V
  #18 = Class              #25            //  java/lang/System
  #19 = NameAndType        #26:#27        //  out:Ljava/io/PrintStream;
  #20 = Utf8               Hello
  #21 = Class              #28            //  java/io/PrintStream
  #22 = NameAndType        #29:#30        //  println:(Ljava/lang/String;)V
  #23 = Utf8               org/jvminternals/SimpleClass
  #24 = Utf8               java/lang/Object
  #25 = Utf8               java/lang/System
  #26 = Utf8               out
  #27 = Utf8               Ljava/io/PrintStream;
  #28 = Utf8               java/io/PrintStream
  #29 = Utf8               println
  #30 = Utf8               (Ljava/lang/String;)V
````
Class的常量池包含以下类型：<br/>
````
Integer       一个4bytes的整型常量
Long          一个8bytes的长整型常量
Float         一个4bytes的浮点型常量
Double        一个4bytes的双精度浮点型常量
String	      字符串引用，指向一个缩略Utf8常量
Utf8          缩略Utf8编码的字符串	
Class	      类型引用，指向一个缩略Utf8常量，存放类全限定名（用于动态链接）
NameAndType   存放两个引用，一个指向用于存放字段或方法名的缩略Utf8常量，一个指向存放字段数据类型或方法返回值类型和入参的缩略Utf8常量
Fieldref,     存放两个引用，一个指向表示所属类或接口的Class常量，一个指向描述字段、方法名称和描述符的NameAndType常量
Methodref, 
InterfaceMethodref 
````
### Exception Table（异常表）
  异常表的每一项表示一项异常处理，表项字段如下：起始位置、结束位置、处理代码的起始位置和指向常量池Class常量的位置索引。<br/>
  只要Java代码中出现try-catch或try-finally的异常处理时，就会创建异常表，异常表的表项用于存放try语句块在字节码指令集中的范围、捕捉的异常类和相应的字节码处理指令的起始位置。（译者注：try-finally所创建的表项的异常类引用为0）<br/>
  当发生异常并没有被捕获处理，则会从线程栈的当前栈帧抛出并触发弹栈操作，再栈顶栈帧接收，直到异常被某个栈帧捕获处理或该线程栈为空并退出线程然后异常有系统异常处理机制捕获。<br/>
  finally语句块的代码无论是否抛出异常均会执行。<br/>
### Symbol Table（符号表）
  HotSpot虚拟机在永久代中增加了符号表。该表为哈希表用于将直接引用与运行时常量池的符号引用作映射。<br/>
  另外每个表项还有个引用计数器，用来记录有多少个符号引用指向同一个直接引用。假如某个类被卸载了那么类中的所有符号引用将无效，则对应的符号表表项的引用计数器减1，当计数器为0时则将该表项移除。
### Interned Strings (String Table)（字符串表）
  Java语言说明中要求字符串字面量必须唯一，一样的字符串字面量必须为同一个String实例。<br/>
  HotSport虚拟机通过字符串表来实现。字符串表位于永久代中，表项为String实例地址与字符串字面量的映射关系信息。加载类时成功执行链接的准备阶段时，Class文件常量池下的CONSTANT_String_info常量的信息均加载到字符串表中。而执行阶段可以通过String#intern()方法将字符串字面量加入到字符串表中。如：
````
new String("jvm") == "jvm"; // false
(new String("jvm")).intern() == "jvm"; // true
````
String#intern()，会先去字符串表查找字面量相同的表项，有则返回对应的对象引用，没有则先将新的字符串对象和字面量添加到表中，然后再返回对象引用。<br/>


biased locking?
linking and dynamic linking?


内存：
http://www.bdqn.cn/news/201304/8591.shtml
http://blog.csdn.net/guofansen/article/details/7265966
http://www.matrix.org.cn/resource/upload/article/2006_10_07_093827_SujcOugNNd.pdf
http://www.cnblogs.com/redcreen/archive/2011/03/29/1999032.html
http://www.blogjava.net/calvin/archive/2005/10/14/15527.html
