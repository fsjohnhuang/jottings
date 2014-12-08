## 多任务处理的手段
1. 任务排队<Br/>
2. 新建进程，通过fork命令新建进程处理<br/>
3. 在同一进程中新建线程<br/>
## Event Loop
Wikipedia定义：<Br/>
Event Loop是一个程序结构，用于等待和发送消息和事件。<br/>
实现方式为:<br/>
进程中含两个线程，一个称为“主线程”负责程序本身的运行；另一个成为“Event Loop线程”负责主线程与其他进程（主要为各种I/O操作）的通信。<br/>

## 参考
http://shenxueliang.iteye.com/blog/1677194
http://www.ruanyifeng.com/blog/2013/10/event_loop.html
http://www.ruanyifeng.com/blog/2014/10/event-loop.html
https://github.com/RubyLouvre/avalon/commit/4d601f27a527e8a0cba262f7a6242bd343933233
http://www.blogjava.net/xiaohuzi2008/default.html?page=5
http://www.bitscn.com/school/Javascript/201410/347549.html
http://www.pjhome.net/article/Javascript/setImmediate_requestAnimationFrame.htmlhttp://tid.tenpay.com/?p=2062
http://www.tuicool.com/articles/EfQbqy6
