# 管程机制（Monitor Mechanism）
## 起源
  由于信号量机制将大量同步操作分散在各个进程中不便管理，甚至可能导致系统死锁。然后Dijkstra于1971年提出，所有进程对某一种临界资源的同步操作均集中到一个独立的进程（秘书进程）。凡要访问该临界资源的进程，都必须通知秘书进程，然后由秘书进程来控制临界资源的互斥使用。<br/>
  注意：操作临界资源前是通知/报告秘书进程，没有规定操作临界资源的动作由秘书进程来进行。<br/>

每个访问临界资源的进程都必须自备wait(s)和signal(s)同步操作。
wait和signal操作应该是成对出现，遗漏signal操作则造成申请资源的进程被死锁，而遗漏wait操作则可能造成进程访问本不应由它访问的资源，且无法达成同步操作。<br/>

## 管程的定义
Hansan的定义：一个数据结构和一组被并发进程执行的操作。这组操作能同步进程和改变管程中的数据。<br/>
描述共享资源的数据结构，和在数据结构上的共享资源的管理程序。<Br/>

## 模板
````
// 管程名字
type moniter-name=moniter

// 变量
variable declarations

// 管程中的过程，进程通过调用管程的过程来实现同步与互斥
procedure entry P1(...)
begin...end;
...
procedure entry Pn(...)
begin...end;

// 变量的初始化代码
begin initialization code
end
````

## 基本特点
1. 管程内部的变量和数据结构仅能通过管程的过程来操作。<br/>
2. 每次只有一个进程调用管程的过程。就是进程A调用管程的过程A，那么进程B就无法调用管程的过程B了。因此将临界资源的操作放在管程的过程中即可实现多进程的同步互斥<br/>
3. 管程是语言成分，管程的互斥访问由编译器在编译时自动添加。

通过对管程的过程的互斥调用，实现对临界资源的互斥访问。<br/>

## 条件变量
  用于标记进程申请的资源，并对应一个PCB队列（阻塞队列），若申请的资源不满足则通过同步原语`wait`将进程挂起到对应的PCB队列，当资源满足时其他进程则通过同步原语`signal`唤醒PCB队列队首的进程。<br/>

示例：生产者b消费者问题<br/>
````
monitor Producer-Consumer
  // 声明：条件变量。此时就自动生成notfull和notempty两个PCB队列了。
  notfull,notempty:condition;
  // 声明：共享变量
  count:integer;
  
  // 生产过程
  procedure put(item)
  begin
    if count>=n then notfull.wait;
    put_item;
    count:=count+1;
    if notempty.queue then notempty.signal;
  end

  // 消费过程
  produre get(item)
  begin
    if count<=0 then notempty.wait;
    remove_item;
    count:=count-1;
    if notfull.queue then notfull.signal;
  end

  // 变量初始化代码
  count:=0；
  
end monitor;
````


### 条件变量同步原语
`wait`：使调用的进程进入紧急队列。<br/>
`signal`：唤醒紧急队列队首的进程。<br/>

  假设条件变量是c，那么信号量notfull表示缓冲区不满。当一个进程A为往缓冲区写数据，当缓冲区满时则调用`c.wait(notfull)`后进程进入紧急队列；而进程B往缓冲区读数据，读完后则调用`c.signal(notfull)`唤醒紧急队列队首的进程。<br/>
伪代码：<br/>
````
write(ItemType item){
  for(;;){ 
    if(buffer.isNotFull()) break;
    wait(notfull);
  }
  // 写数据...

  // 唤醒另一个等待写的进程
  signal(notfull)
}

ItemType read(){
  // 读数据...
  signal(notfull);
}

// 进程A
for(;;){
  monitor.enter();
  write(数据);
  monitor.leave();
}

// 进程B
for(;;){
  monitor.enter();
  ItemType item = read();
  monitor.leave();
}
````


PCB队列？
原语？
信号量机制（用于解决进程同步问题）？


## 参考
http://baike.baidu.com/link?url=zZfmEgzcHN-ChoEPtWNfenMY0ipzNxicK_Wh77DObi3jVoncj6jG-m-Q8eJv5BhD5Pclb0mq5_QtT8dLQeCSoq
http://www.doc88.com/p-778458668284.html
《现代操作系统》
