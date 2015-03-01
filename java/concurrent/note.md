## CAS(Compare-and-Set, 比较并交换)
  内存地址/变量 V,旧值 A,新值 B. 当且仅当V符合A时,才用新值B更新V,否则不更新,但无论更新与否都会返回V的值.
### ABA问题
  当变量V的值在执行CAS操作前从A->B->A,这样在执行CAS操作时依然会更新成功.

## 互斥同步
**同步:**在多个线程并发访问共享数据时,保证共享数据在同一个时刻仅能被一个(或一些,当使用信号量的时候)线程使用<br/>
互斥是实现同步的方式.互斥的具体实现方式有*临界区(Critical Section)*,*互斥量(Mutex)*和*信号量(Semaphore)*<br/>

阻塞同步: 线程被挂起<br/>
非阻塞同步: 线程不被挂起<br/>

## 无同步方案
### 可重入代码(Reentrant Code)
### 线程本地存储(Thread Local Storage)

## 锁
JDK1.5->JDK1.6 HotSpot在锁优化技术上有质的不同.
### 自旋锁与自适应自旋(Spinning Lock & Adaptive Spinning)
**自旋锁: **让线程执行忙循环(自旋)从而进入等待状态,而不是放弃CPU的执行时间(挂起线程)<br/>
优点:降低线程挂起,唤醒的性能消耗.对于多核CPU而言可以降低线程切换的消耗.<br/>
缺点:占用CPU资源执行自旋,阻塞其他业务逻辑获取CPU时间片<br/>
综合结论:当锁被占用的时间很短时,自旋锁的效果好;当锁被占用的时间长,采用阻塞同步的方式挂起线程效果好.<br/>
JDK1.4.2就引入自旋锁,默认关闭,通过`-XX:+UseSpinning`来开启.JDK1.6则默认开启,且通过`-XX:PreBlockSpin`来修改自旋次数的上限(默认是10),若线程执行自旋的次数超过这个上限则会改用阻塞同步的方式.<br/>
JDK1.6开始引入自适应的自旋锁,JVM会根据上一次通过自旋是否成功获取的锁而确定这次是采用非阻塞同步的自旋锁还是阻塞同步.<br/>

### 锁消除(Lock Elimination)
由于编译器会對代码进行优化,如JDK1.5前会采用StringBuffer#append来优化下列代码(JDK1.5后采用StringBuilder#append来优化),也就会出现同步代码替换重入代码的情况.
````
public String concatString(String a, String b){
  return a + b;
}
// 优化后得到
public String concatString(String a, String b){
  StringBuffer sb = new StringBuffer();
  sb.append(a);
  sb.append(b);
  return sb.toString();
}
````
而JVM会通过数据流分析来确定sb的动态作用域,若发现没有出现指针逃逸则清除掉锁.

### 锁粗化(Lock Coarsening)
一般情况下会推荐将同步块的作用范围尽量缩小,这是为了减少需要同步的操作数量,从而让等待的线程尽早获取锁.<br/>
但对于连续操作同一个对象的一系列操作则推荐扩大锁的作用范围,以避免重复加锁和解锁的性能消耗.
````
StringBuffer sb = new StringBuffer();
public String concatString(String a, String b){
  sb.append(a);
  sb.append(b);
  return sb.toString();
}
// 优化为
StringBuilder sb = new StringBuilder();
public synchronized String concatString(String a, String b){
  sb.append(a);
  sb.append(b);
  return sb.toString();
}
````

### 轻量级锁(Lightweight Locking)
  JDK1.6新增的锁机制. 针对没有多线程竞争的场景下,减少重量级锁使用OS互斥量产生的性能消耗.当出现多线程竞争的情况,轻量级锁会膨胀为重量级锁,导致等待锁的线程进入阻塞状态.<br/>
  对象通过对象头的Mark Word中的2bit标志位来标识当前对象的状态(锁定状态)
````
01 为锁定
00 轻量级锁定
10 膨胀(重量级锁定)
11 GC标记
01 可偏向
````
获取锁:<br/>
 1.    
释放锁:<br/>


### 偏向锁(Biased Locking)
JDK1.6引入的锁优化

